package net.minecraft.server;

import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;

public class WorldType
{
    public static final BiomeBase[] base11Biomes = new BiomeBase[] {BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA};
    public static final BiomeBase[] base12Biomes = (BiomeBase[])ObjectArrays.concat(base11Biomes, BiomeBase.JUNGLE);

    /** List of world types. */
    public static final WorldType[] types = new WorldType[16];

    /** Default world type. */
    public static final WorldType NORMAL = (new WorldType(0, "default", 1)).g();

    /** Flat world type. */
    public static final WorldType FLAT = new WorldType(1, "flat");

    /** Large Biome world Type. */
    public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");

    /** Default (1.1) world type. */
    public static final WorldType NORMAL_1_1 = (new WorldType(8, "default_1_1", 0)).a(false);

    /** ID for this world type. */
    private final int f;
    private final String name;

    /** The int version of the ChunkProvider that generated this world. */
    private final int version;

    /**
     * Whether this world type can be generated. Normally true; set to false for out-of-date generator versions.
     */
    private boolean i;

    /** Whether this WorldType has a version or not. */
    private boolean j;
    protected BiomeBase[] biomesForWorldType;

    public WorldType(int var1, String var2)
    {
        this(var1, var2, 0);
    }

    public WorldType(int var1, String var2, int var3)
    {
        this.name = var2;
        this.version = var3;
        this.i = true;
        this.f = var1;
        types[var1] = this;

        switch (var1)
        {
            case 8:
                this.biomesForWorldType = base11Biomes;
                break;

            default:
                this.biomesForWorldType = base12Biomes;
        }
    }

    public String name()
    {
        return this.name;
    }

    /**
     * Returns generatorVersion.
     */
    public int getVersion()
    {
        return this.version;
    }

    public WorldType a(int var1)
    {
        return this == NORMAL && var1 == 0 ? NORMAL_1_1 : this;
    }

    /**
     * Sets canBeCreated to the provided value, and returns this.
     */
    private WorldType a(boolean var1)
    {
        this.i = var1;
        return this;
    }

    /**
     * Flags this world type as having an associated version.
     */
    private WorldType g()
    {
        this.j = true;
        return this;
    }

    /**
     * Returns true if this world Type has a version associated with it.
     */
    public boolean e()
    {
        return this.j;
    }

    public static WorldType getType(String var0)
    {
        WorldType[] var1 = types;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            WorldType var4 = var1[var3];

            if (var4 != null && var4.name.equalsIgnoreCase(var0))
            {
                return var4;
            }
        }

        return null;
    }

    public WorldChunkManager getChunkManager(World var1)
    {
        if (this == FLAT)
        {
            WorldGenFlatInfo var2 = WorldGenFlatInfo.a(var1.getWorldData().getGeneratorOptions());
            return new WorldChunkManagerHell(BiomeBase.biomes[var2.a()], 0.5F, 0.5F);
        }
        else
        {
            return new WorldChunkManager(var1);
        }
    }

    public IChunkProvider getChunkGenerator(World var1, String var2)
    {
        return (IChunkProvider)(this == FLAT ? new ChunkProviderFlat(var1, var1.getSeed(), var1.getWorldData().shouldGenerateMapFeatures(), var2) : new ChunkProviderGenerate(var1, var1.getSeed(), var1.getWorldData().shouldGenerateMapFeatures()));
    }

    public int getMinimumSpawnHeight(World var1)
    {
        return this == FLAT ? 4 : 64;
    }

    public double getHorizon(World var1)
    {
        return this == FLAT ? 0.0D : 63.0D;
    }

    public boolean hasVoidParticles(boolean var1)
    {
        return this != FLAT && !var1;
    }

    public double voidFadeMagnitude()
    {
        return this == FLAT ? 1.0D : 0.03125D;
    }

    public BiomeBase[] getBiomesForWorldType()
    {
        return this.biomesForWorldType;
    }

    public void addNewBiome(BiomeBase var1)
    {
        LinkedHashSet var2 = Sets.newLinkedHashSet(Arrays.asList(this.biomesForWorldType));
        var2.add(var1);
        this.biomesForWorldType = (BiomeBase[])var2.toArray(new BiomeBase[0]);
    }

    public void removeBiome(BiomeBase var1)
    {
        LinkedHashSet var2 = Sets.newLinkedHashSet(Arrays.asList(this.biomesForWorldType));
        var2.remove(var1);
        this.biomesForWorldType = (BiomeBase[])var2.toArray(new BiomeBase[0]);
    }

    public boolean handleSlimeSpawnReduction(Random var1, World var2)
    {
        return this == FLAT ? var1.nextInt(4) != 1 : false;
    }

    public void onGUICreateWorldPress() {}

    public int getSpawnFuzz()
    {
        return 20;
    }
}
