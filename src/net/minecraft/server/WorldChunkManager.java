package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static net.minecraft.server.BiomeBase.*;

public class WorldChunkManager
{
	public static ArrayList<BiomeBase> allowedBiomes = new ArrayList<BiomeBase>(Arrays.asList(FOREST, PLAINS, TAIGA, TAIGA_HILLS, FOREST_HILLS, JUNGLE, JUNGLE_HILLS));
    private GenLayer d;

    /** A GenLayer containing the indices into BiomeBase.biomeList[] */
    private GenLayer e;

    /** The biome list. */
    private BiomeCache f;

    /** A list of biomes that the player can spawn in. */
    private List g;

    protected WorldChunkManager()
    {
        this.f = new BiomeCache(this);
        this.g = new ArrayList();
        this.g.add(BiomeBase.FOREST);
        this.g.add(BiomeBase.PLAINS);
        this.g.add(BiomeBase.TAIGA);
        this.g.add(BiomeBase.TAIGA_HILLS);
        this.g.add(BiomeBase.FOREST_HILLS);
        this.g.add(BiomeBase.JUNGLE);
        this.g.add(BiomeBase.JUNGLE_HILLS);
    }

    public WorldChunkManager(long var1, WorldType var3)
    {
        this();
        GenLayer[] var4 = GenLayer.a(var1, var3);
        this.d = var4[0];
        this.e = var4[1];
    }

    public WorldChunkManager(World var1)
    {
        this(var1.getSeed(), var1.getWorldData().getType());
    }

    /**
     * Gets the list of valid biomes for the player to spawn in.
     */
    public List a()
    {
        return this.g;
    }

    /**
     * Returns the BiomeBase related to the x, z position on the world.
     */
    public BiomeBase getBiome(int var1, int var2) // CPCM - getBiome, mapped to a in reobf
    {
        return this.f.b(var1, var2);
    }
    
    /**
     * Proxy for bukkit
     */
    /* CPCM - remove - duplicate method after remap 
    public BiomeBase getBiome(int var1, int var2)
    {
        return this.a(var1, var2);
    }
    */
    
    

    /**
     * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
     */
    public float[] getWetness(float[] var1, int var2, int var3, int var4, int var5)
    {
        IntCache.a();

        if (var1 == null || var1.length < var4 * var5)
        {
            var1 = new float[var4 * var5];
        }

        int[] var6 = this.e.a(var2, var3, var4, var5);

        for (int var7 = 0; var7 < var4 * var5; ++var7)
        {
            float var8 = (float)BiomeBase.biomes[var6[var7]].g() / 65536.0F;

            if (var8 > 1.0F)
            {
                var8 = 1.0F;
            }

            var1[var7] = var8;
        }

        return var1;
    }

    @SideOnly(Side.CLIENT)
    public float a(float var1, int var2)
    {
        return var1;
    }

    /**
     * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
     */
    public float[] getTemperatures(float[] var1, int var2, int var3, int var4, int var5)
    {
        IntCache.a();

        if (var1 == null || var1.length < var4 * var5)
        {
            var1 = new float[var4 * var5];
        }

        int[] var6 = this.e.a(var2, var3, var4, var5);

        for (int var7 = 0; var7 < var4 * var5; ++var7)
        {
            float var8 = (float)BiomeBase.biomes[var6[var7]].h() / 65536.0F;

            if (var8 > 1.0F)
            {
                var8 = 1.0F;
            }

            var1[var7] = var8;
        }

        return var1;
    }

    /**
     * Returns an array of biomes for the location input.
     */
    public BiomeBase[] getBiomes(BiomeBase[] var1, int var2, int var3, int var4, int var5)
    {
        IntCache.a();

        if (var1 == null || var1.length < var4 * var5)
        {
            var1 = new BiomeBase[var4 * var5];
        }

        int[] var6 = this.d.a(var2, var3, var4, var5);

        for (int var7 = 0; var7 < var4 * var5; ++var7)
        {
            var1[var7] = BiomeBase.biomes[var6[var7]];
        }

        return var1;
    }

    /**
     * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager Args: oldBiomeList, x, z, width, depth
     */
    public BiomeBase[] getBiomeBlock(BiomeBase[] var1, int var2, int var3, int var4, int var5)
    {
        return this.a(var1, var2, var3, var4, var5, true);
    }

    /**
     * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
     * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
     */
    public BiomeBase[] a(BiomeBase[] var1, int var2, int var3, int var4, int var5, boolean var6)
    {
        IntCache.a();

        if (var1 == null || var1.length < var4 * var5)
        {
            var1 = new BiomeBase[var4 * var5];
        }

        if (var6 && var4 == 16 && var5 == 16 && (var2 & 15) == 0 && (var3 & 15) == 0)
        {
            BiomeBase[] var9 = this.f.e(var2, var3);
            System.arraycopy(var9, 0, var1, 0, var4 * var5);
            return var1;
        }
        else
        {
            int[] var7 = this.e.a(var2, var3, var4, var5);

            for (int var8 = 0; var8 < var4 * var5; ++var8)
            {
                var1[var8] = BiomeBase.biomes[var7[var8]];
            }

            return var1;
        }
    }

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    public boolean a(int var1, int var2, int var3, List var4)
    {
        IntCache.a();
        int var5 = var1 - var3 >> 2;
        int var6 = var2 - var3 >> 2;
        int var7 = var1 + var3 >> 2;
        int var8 = var2 + var3 >> 2;
        int var9 = var7 - var5 + 1;
        int var10 = var8 - var6 + 1;
        int[] var11 = this.d.a(var5, var6, var9, var10);

        for (int var12 = 0; var12 < var9 * var10; ++var12)
        {
            BiomeBase var13 = BiomeBase.biomes[var11[var12]];

            if (!var4.contains(var13))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds a valid position within a range, that is once of the listed biomes.
     */
    public ChunkPosition a(int var1, int var2, int var3, List var4, Random var5)
    {
        IntCache.a();
        int var6 = var1 - var3 >> 2;
        int var7 = var2 - var3 >> 2;
        int var8 = var1 + var3 >> 2;
        int var9 = var2 + var3 >> 2;
        int var10 = var8 - var6 + 1;
        int var11 = var9 - var7 + 1;
        int[] var12 = this.d.a(var6, var7, var10, var11);
        ChunkPosition var13 = null;
        int var14 = 0;

        for (int var15 = 0; var15 < var10 * var11; ++var15)
        {
            int var16 = var6 + var15 % var10 << 2;
            int var17 = var7 + var15 / var10 << 2;
            BiomeBase var18 = BiomeBase.biomes[var12[var15]];

            if (var4.contains(var18) && (var13 == null || var5.nextInt(var14 + 1) == 0))
            {
                var13 = new ChunkPosition(var16, 0, var17);
                ++var14;
            }
        }

        return var13;
    }

    /**
     * Calls the WorldChunkManager's biomeCache.cleanupCache()
     */
    public void b()
    {
        this.f.a();
    }
}
