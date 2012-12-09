package net.minecraftforge.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.ChunkProviderGenerate;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.WorldGenStronghold;
import net.minecraft.server.WorldGenVillage;
import net.minecraft.server.WorldChunkManager;

import com.google.common.collect.Lists;

public class BiomeManager
{
    public static void addVillageBiome(BiomeBase biome, boolean canSpawn)
    {
        if (!WorldGenVillage.e.contains(biome))
        {
            ArrayList<BiomeBase> biomes = new ArrayList<BiomeBase>(WorldGenVillage.e);
            biomes.add(biome);
            WorldGenVillage.e = biomes;
        }
    }

    public static void removeVillageBiome(BiomeBase biome)
    {
        if (WorldGenVillage.e.contains(biome))
        {
            ArrayList<BiomeBase> biomes = new ArrayList<BiomeBase>(WorldGenVillage.e);
            biomes.remove(biome);
            WorldGenVillage.e = biomes;
        }
    }

    public static void addStrongholdBiome(BiomeBase biome)
    {
        if (!WorldGenStronghold.allowedBiomes.contains(biome))
        {
            WorldGenStronghold.allowedBiomes.add(biome);
        }
    }

    public static void removeStrongholdBiome(BiomeBase biome)
    {
        if (WorldGenStronghold.allowedBiomes.contains(biome))
        {
            WorldGenStronghold.allowedBiomes.remove(biome);
        }
    }

    public static void addSpawnBiome(BiomeBase biome)
    {
        if (!WorldChunkManager.allowedBiomes.contains(biome))
        {
            WorldChunkManager.allowedBiomes.add(biome);
        }
    }

    public static void removeSpawnBiome(BiomeBase biome)
    {
        if (WorldChunkManager.allowedBiomes.contains(biome))
        {
            WorldChunkManager.allowedBiomes.remove(biome);
        }
    }
}