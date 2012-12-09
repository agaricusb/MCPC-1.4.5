package net.minecraft.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class WorldGenStronghold extends StructureGenerator
{
	public static ArrayList<BiomeBase> allowedBiomes = new ArrayList<BiomeBase>(Arrays.asList(BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.TAIGA, BiomeBase.ICE_PLAINS, BiomeBase.ICE_MOUNTAINS, BiomeBase.DESERT_HILLS, BiomeBase.FOREST_HILLS, BiomeBase.SMALL_MOUNTAINS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS)); 
    private BiomeBase[] e;

    /**
     * is spawned false and set true once the defined BiomeBases were compared with the present ones
     */
    private boolean f;
    private ChunkCoordIntPair[] g;
    private double field_82671_h;
    private int field_82672_i;

    public WorldGenStronghold()
    {
        //this.e = new BiomeBase[] {BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.TAIGA, BiomeBase.ICE_PLAINS, BiomeBase.ICE_MOUNTAINS, BiomeBase.DESERT_HILLS, BiomeBase.FOREST_HILLS, BiomeBase.SMALL_MOUNTAINS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS};
        this.e = allowedBiomes.toArray(new BiomeBase[0]);
        this.g = new ChunkCoordIntPair[3];
        this.field_82671_h = 32.0D;
        this.field_82672_i = 3;
    }

    public WorldGenStronghold(Map var1)
    {
       // this.e = new BiomeBase[] {BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.TAIGA, BiomeBase.ICE_PLAINS, BiomeBase.ICE_MOUNTAINS, BiomeBase.DESERT_HILLS, BiomeBase.FOREST_HILLS, BiomeBase.SMALL_MOUNTAINS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS};
        this.e = allowedBiomes.toArray(new BiomeBase[0]);
        this.g = new ChunkCoordIntPair[3];
        this.field_82671_h = 32.0D;
        this.field_82672_i = 3;
        Iterator var2 = var1.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();

            if (((String)var3.getKey()).equals("distance"))
            {
                this.field_82671_h = MathHelper.a((String)var3.getValue(), this.field_82671_h, 1.0D);
            }
            else if (((String)var3.getKey()).equals("count"))
            {
                this.g = new ChunkCoordIntPair[MathHelper.a((String)var3.getValue(), this.g.length, 1)];
            }
            else if (((String)var3.getKey()).equals("spread"))
            {
                this.field_82672_i = MathHelper.a((String)var3.getValue(), this.field_82672_i, 1);
            }
        }
    }

    protected boolean a(int var1, int var2)
    {
        if (!this.f)
        {
            Random var3 = new Random();
            var3.setSeed(this.c.getSeed());
            double var4 = var3.nextDouble() * Math.PI * 2.0D;
            int var6 = 1;

            for (int var7 = 0; var7 < this.g.length; ++var7)
            {
                double var8 = (1.25D * (double)var6 + var3.nextDouble()) * this.field_82671_h * (double)var6;
                int var10 = (int)Math.round(Math.cos(var4) * var8);
                int var11 = (int)Math.round(Math.sin(var4) * var8);
                ArrayList var12 = new ArrayList();
                Collections.addAll(var12, this.e);
                ChunkPosition var13 = this.c.getWorldChunkManager().a((var10 << 4) + 8, (var11 << 4) + 8, 112, var12, var3);

                if (var13 != null)
                {
                    var10 = var13.x >> 4;
                    var11 = var13.z >> 4;
                }

                this.g[var7] = new ChunkCoordIntPair(var10, var11);
                var4 += (Math.PI * 2D) * (double)var6 / (double)this.field_82672_i;

                if (var7 == this.field_82672_i)
                {
                    var6 += 2 + var3.nextInt(5);
                    this.field_82672_i += 1 + var3.nextInt(2);
                }
            }

            this.f = true;
        }

        ChunkCoordIntPair[] var14 = this.g;
        int var15 = var14.length;

        for (int var5 = 0; var5 < var15; ++var5)
        {
            ChunkCoordIntPair var16 = var14[var5];

            if (var1 == var16.x && var2 == var16.z)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List p_()
    {
        ArrayList var1 = new ArrayList();
        ChunkCoordIntPair[] var2 = this.g;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ChunkCoordIntPair var5 = var2[var4];

            if (var5 != null)
            {
                var1.add(var5.a(64));
            }
        }

        return var1;
    }

    protected StructureStart b(int var1, int var2)
    {
        WorldGenStronghold2Start var3;

        for (var3 = new WorldGenStronghold2Start(this.c, this.b, var1, var2); var3.b().isEmpty() || ((WorldGenStrongholdStart)var3.b().get(0)).b == null; var3 = new WorldGenStronghold2Start(this.c, this.b, var1, var2))
        {
            ;
        }

        return var3;
    }
}
