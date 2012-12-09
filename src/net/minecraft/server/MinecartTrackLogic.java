package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MinecartTrackLogic
{
    /** Reference to the World object. */
    private World b;
    private int c;
    private int d;
    private int e;

    /**
     * A boolean value that is true if the rail is powered, and false if its not.
     */
    private final boolean f;
    private List g;
    final BlockMinecartTrack a;
    private final boolean canMakeSlopes;

    public MinecartTrackLogic(BlockMinecartTrack var1, World var2, int var3, int var4, int var5)
    {
        this.a = var1;
        this.g = new ArrayList();
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
        int var6 = var2.getTypeId(var3, var4, var5);
        BlockMinecartTrack var7 = (BlockMinecartTrack)Block.byId[var6];
        int var8 = var7.getBasicRailMetadata(var2, (EntityMinecart)null, var3, var4, var5);
        this.f = !var7.isFlexibleRail(var2, var3, var4, var5);
        this.canMakeSlopes = var7.canMakeSlopes(var2, var3, var4, var5);
        this.a(var8);
    }

    private void a(int var1)
    {
        this.g.clear();

        if (var1 == 0)
        {
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (var1 == 1)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
        }
        else if (var1 == 2)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c + 1, this.d + 1, this.e));
        }
        else if (var1 == 3)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d + 1, this.e));
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
        }
        else if (var1 == 4)
        {
            this.g.add(new ChunkPosition(this.c, this.d + 1, this.e - 1));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (var1 == 5)
        {
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
            this.g.add(new ChunkPosition(this.c, this.d + 1, this.e + 1));
        }
        else if (var1 == 6)
        {
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (var1 == 7)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (var1 == 8)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
        }
        else if (var1 == 9)
        {
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
        }
    }

    /**
     * Neighboring tracks have potentially been broken, so prune the connected track list
     */
    private void a()
    {
        for (int var1 = 0; var1 < this.g.size(); ++var1)
        {
            MinecartTrackLogic var2 = this.a((ChunkPosition)this.g.get(var1));

            if (var2 != null && var2.b(this))
            {
                this.g.set(var1, new ChunkPosition(var2.c, var2.d, var2.e));
            }
            else
            {
                this.g.remove(var1--);
            }
        }
    }

    private boolean a(int var1, int var2, int var3)
    {
        return BlockMinecartTrack.e_(this.b, var1, var2, var3) ? true : (BlockMinecartTrack.e_(this.b, var1, var2 + 1, var3) ? true : BlockMinecartTrack.e_(this.b, var1, var2 - 1, var3));
    }

    private MinecartTrackLogic a(ChunkPosition var1)
    {
        return BlockMinecartTrack.e_(this.b, var1.x, var1.y, var1.z) ? new MinecartTrackLogic(this.a, this.b, var1.x, var1.y, var1.z) : (BlockMinecartTrack.e_(this.b, var1.x, var1.y + 1, var1.z) ? new MinecartTrackLogic(this.a, this.b, var1.x, var1.y + 1, var1.z) : (BlockMinecartTrack.e_(this.b, var1.x, var1.y - 1, var1.z) ? new MinecartTrackLogic(this.a, this.b, var1.x, var1.y - 1, var1.z) : null));
    }

    private boolean b(MinecartTrackLogic var1)
    {
        Iterator var2 = this.g.iterator();
        ChunkPosition var3;

        do
        {
            if (!var2.hasNext())
            {
                return false;
            }

            var3 = (ChunkPosition)var2.next();
        }
        while (var3.x != var1.c || var3.z != var1.e);

        return true;
    }

    /**
     * Returns true if the specified block is in the same railway.
     */
    private boolean b(int var1, int var2, int var3)
    {
        Iterator var4 = this.g.iterator();
        ChunkPosition var5;

        do
        {
            if (!var4.hasNext())
            {
                return false;
            }

            var5 = (ChunkPosition)var4.next();
        }
        while (var5.x != var1 || var5.z != var3);

        return true;
    }

    private int b()
    {
        int var1 = 0;

        if (this.a(this.c, this.d, this.e - 1))
        {
            ++var1;
        }

        if (this.a(this.c, this.d, this.e + 1))
        {
            ++var1;
        }

        if (this.a(this.c - 1, this.d, this.e))
        {
            ++var1;
        }

        if (this.a(this.c + 1, this.d, this.e))
        {
            ++var1;
        }

        return var1;
    }

    /**
     * Determines whether or not the track can bend to meet the specified rail
     */
    private boolean c(MinecartTrackLogic var1)
    {
        if (this.b(var1))
        {
            return true;
        }
        else if (this.g.size() == 2)
        {
            return false;
        }
        else if (this.g.isEmpty())
        {
            return true;
        }
        else
        {
            ChunkPosition var2 = (ChunkPosition)this.g.get(0);
            return true;
        }
    }

    /**
     * The specified neighbor has just formed a new connection, so update accordingly
     */
    private void d(MinecartTrackLogic var1)
    {
        this.g.add(new ChunkPosition(var1.c, var1.d, var1.e));
        boolean var2 = this.b(this.c, this.d, this.e - 1);
        boolean var3 = this.b(this.c, this.d, this.e + 1);
        boolean var4 = this.b(this.c - 1, this.d, this.e);
        boolean var5 = this.b(this.c + 1, this.d, this.e);
        byte var6 = -1;

        if (var2 || var3)
        {
            var6 = 0;
        }

        if (var4 || var5)
        {
            var6 = 1;
        }

        if (!this.f)
        {
            if (var3 && var5 && !var2 && !var4)
            {
                var6 = 6;
            }

            if (var3 && var4 && !var2 && !var5)
            {
                var6 = 7;
            }

            if (var2 && var4 && !var3 && !var5)
            {
                var6 = 8;
            }

            if (var2 && var5 && !var3 && !var4)
            {
                var6 = 9;
            }
        }

        if (var6 == 0 && this.canMakeSlopes)
        {
            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e - 1))
            {
                var6 = 4;
            }

            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e + 1))
            {
                var6 = 5;
            }
        }

        if (var6 == 1 && this.canMakeSlopes)
        {
            if (BlockMinecartTrack.e_(this.b, this.c + 1, this.d + 1, this.e))
            {
                var6 = 2;
            }

            if (BlockMinecartTrack.e_(this.b, this.c - 1, this.d + 1, this.e))
            {
                var6 = 3;
            }
        }

        if (var6 < 0)
        {
            var6 = 0;
        }

        int var7 = var6;

        if (this.f)
        {
            var7 = this.b.getData(this.c, this.d, this.e) & 8 | var6;
        }

        this.b.setData(this.c, this.d, this.e, var7);
    }

    /**
     * Determines whether or not the target rail can connect to this rail
     */
    private boolean c(int var1, int var2, int var3)
    {
        MinecartTrackLogic var4 = this.a(new ChunkPosition(var1, var2, var3));

        if (var4 == null)
        {
            return false;
        }
        else
        {
            var4.a();
            return var4.c(this);
        }
    }

    /**
     * Completely recalculates the track shape based on neighboring tracks and power state
     */
    public void a(boolean var1, boolean var2)
    {
        boolean var3 = this.c(this.c, this.d, this.e - 1);
        boolean var4 = this.c(this.c, this.d, this.e + 1);
        boolean var5 = this.c(this.c - 1, this.d, this.e);
        boolean var6 = this.c(this.c + 1, this.d, this.e);
        byte var7 = -1;

        if ((var3 || var4) && !var5 && !var6)
        {
            var7 = 0;
        }

        if ((var5 || var6) && !var3 && !var4)
        {
            var7 = 1;
        }

        if (!this.f)
        {
            if (var4 && var6 && !var3 && !var5)
            {
                var7 = 6;
            }

            if (var4 && var5 && !var3 && !var6)
            {
                var7 = 7;
            }

            if (var3 && var5 && !var4 && !var6)
            {
                var7 = 8;
            }

            if (var3 && var6 && !var4 && !var5)
            {
                var7 = 9;
            }
        }

        if (var7 == -1)
        {
            if (var3 || var4)
            {
                var7 = 0;
            }

            if (var5 || var6)
            {
                var7 = 1;
            }

            if (!this.f)
            {
                if (var1)
                {
                    if (var4 && var6)
                    {
                        var7 = 6;
                    }

                    if (var5 && var4)
                    {
                        var7 = 7;
                    }

                    if (var6 && var3)
                    {
                        var7 = 9;
                    }

                    if (var3 && var5)
                    {
                        var7 = 8;
                    }
                }
                else
                {
                    if (var3 && var5)
                    {
                        var7 = 8;
                    }

                    if (var6 && var3)
                    {
                        var7 = 9;
                    }

                    if (var5 && var4)
                    {
                        var7 = 7;
                    }

                    if (var4 && var6)
                    {
                        var7 = 6;
                    }
                }
            }
        }

        if (var7 == 0 && this.canMakeSlopes)
        {
            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e - 1))
            {
                var7 = 4;
            }

            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e + 1))
            {
                var7 = 5;
            }
        }

        if (var7 == 1 && this.canMakeSlopes)
        {
            if (BlockMinecartTrack.e_(this.b, this.c + 1, this.d + 1, this.e))
            {
                var7 = 2;
            }

            if (BlockMinecartTrack.e_(this.b, this.c - 1, this.d + 1, this.e))
            {
                var7 = 3;
            }
        }

        if (var7 < 0)
        {
            var7 = 0;
        }

        this.a(var7);
        int var8 = var7;

        if (this.f)
        {
            var8 = this.b.getData(this.c, this.d, this.e) & 8 | var7;
        }

        if (var2 || this.b.getData(this.c, this.d, this.e) != var8)
        {
            this.b.setData(this.c, this.d, this.e, var8);
            Iterator var9 = this.g.iterator();

            while (var9.hasNext())
            {
                ChunkPosition var10 = (ChunkPosition)var9.next();
                MinecartTrackLogic var11 = this.a(var10);

                if (var11 != null)
                {
                    var11.a();

                    if (var11.c(this))
                    {
                        var11.d(this);
                    }
                }
            }
        }
    }

    /**
     * get number of adjacent tracks
     */
    public static int a(MinecartTrackLogic var0)
    {
        return var0.b();
    }
}
