package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;

public class CommandHandler implements ICommandHandler
{
    /** Map of Strings to the ICommand objects they represent */
    private final Map a = new HashMap();

    /** The set of ICommand objects currently loaded. */
    private final Set b = new HashSet();

    public void a(ICommandListener var1, String var2)
    {
        if (var2.startsWith("/"))
        {
            var2 = var2.substring(1);
        }

        String[] var3 = var2.split(" ");
        String var4 = var3[0];
        var3 = a(var3);
        ICommand var5 = (ICommand)this.a.get(var4);
        int var6 = this.a(var5, var3);

        try
        {
            if (var5 == null)
            {
                throw new ExceptionUnknownCommand();
            }

            if (var5.b(var1))
            {
                CommandEvent var7 = new CommandEvent(var5, var1, var3);

                if (MinecraftForge.EVENT_BUS.post(var7))
                {
                    if (var7.exception != null)
                    {
                        throw var7.exception;
                    }

                    return;
                }

                if (var6 > -1)
                {
                    EntityPlayer[] var8 = PlayerSelector.getPlayers(var1, var3[var6]);
                    String var9 = var3[var6];
                    EntityPlayer[] var10 = var8;
                    int var11 = var8.length;

                    for (int var12 = 0; var12 < var11; ++var12)
                    {
                        EntityPlayer var13 = var10[var12];
                        var3[var6] = var13.getLocalizedName();

                        try
                        {
                            var5.b(var1, var3);
                        }
                        catch (ExceptionPlayerNotFound var15)
                        {
                            var1.sendMessage("\u00a7c" + var1.a(var15.getMessage(), var15.a()));
                        }
                    }

                    var3[var6] = var9;
                }
                else
                {
                    var5.b(var1, var3);
                }
            }
            else
            {
                var1.sendMessage("\u00a7cYou do not have permission to use this command.");
            }
        }
        catch (ExceptionUsage var16)
        {
            var1.sendMessage("\u00a7c" + var1.a("commands.generic.usage", new Object[] {var1.a(var16.getMessage(), var16.a())}));
        }
        catch (CommandException var17)
        {
            var1.sendMessage("\u00a7c" + var1.a(var17.getMessage(), var17.a()));
        }
        catch (Throwable var18)
        {
            var1.sendMessage("\u00a7c" + var1.a("commands.generic.exception", new Object[0]));
            var18.printStackTrace();
        }
    }

    /**
     * adds the command and any aliases it has to the internal map of available commands
     */
    public ICommand a(ICommand var1)
    {
        List var2 = var1.b();
        this.a.put(var1.c(), var1);
        this.b.add(var1);

        if (var2 != null)
        {
            Iterator var3 = var2.iterator();

            while (var3.hasNext())
            {
                String var4 = (String)var3.next();
                ICommand var5 = (ICommand)this.a.get(var4);

                if (var5 == null || !var5.c().equals(var4))
                {
                    this.a.put(var4, var1);
                }
            }
        }

        return var1;
    }

    /**
     * creates a new array and sets elements 0..n-2 to be 0..n-1 of the input (n elements)
     */
    private static String[] a(String[] var0)
    {
        String[] var1 = new String[var0.length - 1];

        for (int var2 = 1; var2 < var0.length; ++var2)
        {
            var1[var2 - 1] = var0[var2];
        }

        return var1;
    }

    /**
     * Performs a "begins with" string match on each token in par2. Only returns commands that par1 can use.
     */
    public List b(ICommandListener var1, String var2)
    {
        String[] var3 = var2.split(" ", -1);
        String var4 = var3[0];

        if (var3.length == 1)
        {
            ArrayList var8 = new ArrayList();
            Iterator var6 = this.a.entrySet().iterator();

            while (var6.hasNext())
            {
                Entry var7 = (Entry)var6.next();

                if (CommandAbstract.a(var4, (String)var7.getKey()) && ((ICommand)var7.getValue()).b(var1))
                {
                    var8.add(var7.getKey());
                }
            }

            return var8;
        }
        else
        {
            if (var3.length > 1)
            {
                ICommand var5 = (ICommand)this.a.get(var4);

                if (var5 != null)
                {
                    return var5.a(var1, a(var3));
                }
            }

            return null;
        }
    }

    /**
     * returns all commands that the commandSender can use
     */
    public List a(ICommandListener var1)
    {
        ArrayList var2 = new ArrayList();
        Iterator var3 = this.b.iterator();

        while (var3.hasNext())
        {
            ICommand var4 = (ICommand)var3.next();

            if (var4.b(var1))
            {
                var2.add(var4);
            }
        }

        return var2;
    }

    /**
     * returns a map of string to commads. All commands are returned, not just ones which someone has permission to use.
     */
    public Map a()
    {
        return this.a;
    }

    /**
     * Return a command's first parameter index containing a valid username.
     */
    private int a(ICommand var1, String[] var2)
    {
        if (var1 == null)
        {
            return -1;
        }
        else
        {
            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                if (var1.a(var3) && PlayerSelector.isList(var2[var3]))
                {
                    return var3;
                }
            }

            return -1;
        }
    }
}
