package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ChunkCache implements IBlockAccess
{
    private int a;
    private int b;
    private Chunk[][] c;

    /** set by !chunk.getAreLevelsEmpty */
    private boolean d;

    /** Reference to the World object. */
    private World e;

    public ChunkCache(World var1, int var2, int var3, int var4, int var5, int var6, int var7)
    {
        this.e = var1;
        this.a = var2 >> 4;
        this.b = var4 >> 4;
        int var8 = var5 >> 4;
        int var9 = var7 >> 4;
        this.c = new Chunk[var8 - this.a + 1][var9 - this.b + 1];
        this.d = true;

        for (int var10 = this.a; var10 <= var8; ++var10)
        {
            for (int var11 = this.b; var11 <= var9; ++var11)
            {
                Chunk var12 = var1.getChunkAt(var10, var11);

                if (var12 != null)
                {
                    this.c[var10 - this.a][var11 - this.b] = var12;

                    if (!var12.c(var3, var6))
                    {
                        this.d = false;
                    }
                }
            }
        }
    }

    /**
     * Returns the block ID at coords x,y,z
     */
    public int getTypeId(int var1, int var2, int var3)
    {
        if (var2 < 0)
        {
            return 0;
        }
        else if (var2 >= 256)
        {
            return 0;
        }
        else
        {
            int var4 = (var1 >> 4) - this.a;
            int var5 = (var3 >> 4) - this.b;

            if (var4 >= 0 && var4 < this.c.length && var5 >= 0 && var5 < this.c[var4].length)
            {
                Chunk var6 = this.c[var4][var5];
                return var6 == null ? 0 : var6.getTypeId(var1 & 15, var2, var3 & 15);
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * Returns the TileEntity associated with a given block in X,Y,Z coordinates, or null if no TileEntity exists
     */
    public TileEntity getTileEntity(int var1, int var2, int var3)
    {
        int var4 = (var1 >> 4) - this.a;
        int var5 = (var3 >> 4) - this.b;

        if (var4 >= 0 && var4 < this.c.length && var5 >= 0 && var5 < this.c[var4].length)
        {
            Chunk var6 = this.c[var4][var5];
            return var6 == null ? null : var6.e(var1 & 15, var2, var3 & 15);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the block metadata at coords x,y,z
     */
    public int getData(int var1, int var2, int var3)
    {
        if (var2 < 0)
        {
            return 0;
        }
        else if (var2 >= 256)
        {
            return 0;
        }
        else
        {
            int var4 = (var1 >> 4) - this.a;
            int var5 = (var3 >> 4) - this.b;

            if (var4 >= 0 && var4 < this.c.length && var5 >= 0 && var5 < this.c[var4].length)
            {
                Chunk var6 = this.c[var4][var5];
                return var6 == null ? 0 : var6.getData(var1 & 15, var2, var3 & 15);
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * Returns the block's material.
     */
    public Material getMaterial(int var1, int var2, int var3)
    {
        int var4 = this.getTypeId(var1, var2, var3);
        return var4 == 0 ? Material.AIR : Block.byId[var4].material;
    }

    /**
     * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
     */
    public boolean s(int var1, int var2, int var3)
    {
        Block var4 = Block.byId[this.getTypeId(var1, var2, var3)];
        return var4 == null ? false : var4.material.isSolid() && var4.b();
    }

    /**
     * Return the Vec3Pool object for this world.
     */
    public Vec3DPool getVec3DPool()
    {
        return this.e.getVec3DPool();
    }

    /**
     * Is this block powering in the specified direction Args: x, y, z, direction
     */
    public boolean isBlockFacePowered(int var1, int var2, int var3, int var4)
    {
        int var5 = this.getTypeId(var1, var2, var3);
        return var5 == 0 ? false : Block.byId[var5].c(this, var1, var2, var3, var4);
    }

	@Override
	public BiomeBase getBiome(int i, int j) 
	{
		return e.getBiome(i, j);
	}
}
