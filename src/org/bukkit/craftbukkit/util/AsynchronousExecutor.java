package org.bukkit.craftbukkit.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.Validate;

public final class AsynchronousExecutor<P, T, C, E extends Throwable> {

    public static interface CallBackProvider<P, T, C, E extends Throwable> extends ThreadFactory {

        /**
         * Asynchronous call
         *
         * @param parameter parameter object provided
         * @return the created object
         */
        T callStage1(P parameter) throws E;

        /**
         * Synchronous call
         *
         * @param parameter parameter object provided
         * @param object    the previously created object
         */
        void callStage2(P parameter, T object) throws E;

        /**
         * Synchronous call, called multiple times, once per registered callback
         *
         * @param parameter parameter object provided
         * @param object    the previously created object
         * @param callback  the current callback to execute
         */
        void callStage3(P parameter, T object, C callback) throws E;
    }

    class Task implements Runnable {
        static final byte PENDING = 0x0;
        static final byte ACTIVE = 0x1;
        static final byte DEAD = 0x2;

        volatile byte state = PENDING;
        final P parameter;
        T object;
        final List<C> callbacks = new LinkedList<C>();
        E t = null;

        Task(final P parameter) {
            this.parameter = parameter;
        }

        public void run() {
            if (initAsync()) {
                finished.add(this);
            }
        }

        boolean initAsync() {
            if (state != PENDING) {
                return false;
            }

            synchronized (this) {
                if (state != PENDING) {
                    return false;
                }

                try {
                    object = provider.callStage1(parameter);
                } catch (final Throwable t) {
                    this.t = (E) t;
                } finally {
                    state = ACTIVE;
                }
            }
            return true;
        }

        T get() throws E {
            initAsync();
            finish();
            return object;
        }

        void finish() throws E {
            switch (state) {
                case PENDING:
                    throw new IllegalStateException("Pending task");
                case ACTIVE:
                    try {
                        if (t != null) {
                            throw t;
                        }

                        final CallBackProvider<P, T, C, E> provider = AsynchronousExecutor.this.provider;
                        final P parameter = this.parameter;
                        final T object = this.object;

                        provider.callStage2(parameter, object);
                        for (C callback : callbacks) {
                            provider.callStage3(parameter, object, callback);
                        }

                    } finally {
                        tasks.remove(parameter);
                        state = DEAD;
                    }
                case DEAD:
                    return;
                default:
                    throw new AssertionError("Unknown state " + state);
            }
        }
    }

    final CallBackProvider<P, T, C, E> provider;
    final Queue<Task> finished = new ConcurrentLinkedQueue<Task>();
    final Map<P, Task> tasks = new HashMap<P, Task>();
    final ThreadPoolExecutor pool;

    public AsynchronousExecutor(final CallBackProvider<P, T, C, E> provider, final int coreSize) {
        Validate.notNull(provider, "Provider cannot be null");
        this.provider = provider;

        // We have an unbound queue size so do not need a max thread size
        pool = new ThreadPoolExecutor(coreSize, Integer.MAX_VALUE, 60l, TimeUnit.SECONDS, (BlockingQueue) new LinkedBlockingQueue<Task>(), provider);
    }

    public void add(P parameter, C callback) {
        Task task = tasks.get(parameter);
        if (task == null) {
            tasks.put(parameter, task = new Task(parameter));
            pool.execute(task);
        }
        task.callbacks.add(callback);
    }

    public T get(P parameter) throws E {
        final Task task = tasks.get(parameter);
        if (task == null) {
            throw new IllegalStateException("Unknown " + parameter);
        }
        return task.get();
    }

    public void finishActive() throws E {
        final Queue<Task> finished = this.finished;
        while (!finished.isEmpty()) {
            finished.poll().finish();
        }
    }

    public void setActiveThreads(final int coreSize) {
        pool.setCorePoolSize(coreSize);
    }
}
