package net.minecraft.server;

import cpw.mods.fml.common.registry.VillagerRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenVillagePieces
{
    public static ArrayList a(Random var0, int var1)
    {
        ArrayList var2 = new ArrayList();
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse.class, 4, MathHelper.nextInt(var0, 2 + var1, 4 + var1 * 2)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageTemple.class, 20, MathHelper.nextInt(var0, 0 + var1, 1 + var1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageLibrary.class, 20, MathHelper.nextInt(var0, 0 + var1, 2 + var1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageHut.class, 3, MathHelper.nextInt(var0, 2 + var1, 5 + var1 * 3)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageButcher.class, 15, MathHelper.nextInt(var0, 0 + var1, 2 + var1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm2.class, 3, MathHelper.nextInt(var0, 1 + var1, 4 + var1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm.class, 3, MathHelper.nextInt(var0, 2 + var1, 4 + var1 * 2)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(var0, 0, 1 + var1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse2.class, 8, MathHelper.nextInt(var0, 0 + var1, 3 + var1 * 2)));
        VillagerRegistry.addExtraVillageComponents(var2, var0, var1);
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            if (((WorldGenVillagePieceWeight)var3.next()).d == 0)
            {
                var3.remove();
            }
        }

        return var2;
    }

    private static int func_75079_a(List var0)
    {
        boolean var1 = false;
        int var2 = 0;
        WorldGenVillagePieceWeight var3;

        for (Iterator var4 = var0.iterator(); var4.hasNext(); var2 += var3.b)
        {
            var3 = (WorldGenVillagePieceWeight)var4.next();

            if (var3.d > 0 && var3.c < var3.d)
            {
                var1 = true;
            }
        }

        return var1 ? var2 : -1;
    }

    private static WorldGenVillagePiece func_75083_a(WorldGenVillageStartPiece var0, WorldGenVillagePieceWeight var1, List var2, Random var3, int var4, int var5, int var6, int var7, int var8)
    {
        Class var9 = var1.a;
        Object var10 = null;

        if (var9 == WorldGenVillageHouse.class)
        {
            var10 = WorldGenVillageHouse.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageTemple.class)
        {
            var10 = WorldGenVillageTemple.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageLibrary.class)
        {
            var10 = WorldGenVillageLibrary.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageHut.class)
        {
            var10 = WorldGenVillageHut.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageButcher.class)
        {
            var10 = WorldGenVillageButcher.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageFarm2.class)
        {
            var10 = WorldGenVillageFarm2.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageFarm.class)
        {
            var10 = WorldGenVillageFarm.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageBlacksmith.class)
        {
            var10 = WorldGenVillageBlacksmith.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else if (var9 == WorldGenVillageHouse2.class)
        {
            var10 = WorldGenVillageHouse2.a(var0, var2, var3, var4, var5, var6, var7, var8);
        }
        else
        {
            var10 = VillagerRegistry.getVillageComponent(var1, var0, var2, var3, var4, var5, var6, var7, var8);
        }

        return (WorldGenVillagePiece)var10;
    }

    /**
     * attempts to find a next Village Component to be spawned
     */
    private static WorldGenVillagePiece c(WorldGenVillageStartPiece var0, List var1, Random var2, int var3, int var4, int var5, int var6, int var7)
    {
        int var8 = func_75079_a(var0.h);

        if (var8 <= 0)
        {
            return null;
        }
        else
        {
            int var9 = 0;

            while (var9 < 5)
            {
                ++var9;
                int var10 = var2.nextInt(var8);
                Iterator var11 = var0.h.iterator();

                while (var11.hasNext())
                {
                    WorldGenVillagePieceWeight var12 = (WorldGenVillagePieceWeight)var11.next();
                    var10 -= var12.b;

                    if (var10 < 0)
                    {
                        if (!var12.a(var7) || var12 == var0.d && var0.h.size() > 1)
                        {
                            break;
                        }

                        WorldGenVillagePiece var13 = func_75083_a(var0, var12, var1, var2, var3, var4, var5, var6, var7);

                        if (var13 != null)
                        {
                            ++var12.c;
                            var0.d = var12;

                            if (!var12.a())
                            {
                                var0.h.remove(var12);
                            }

                            return var13;
                        }
                    }
                }
            }

            StructureBoundingBox var14 = WorldGenVillageLight.a(var0, var1, var2, var3, var4, var5, var6);

            if (var14 != null)
            {
                return new WorldGenVillageLight(var0, var7, var2, var14, var6);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned, private Village function
     */
    private static StructurePiece d(WorldGenVillageStartPiece var0, List var1, Random var2, int var3, int var4, int var5, int var6, int var7)
    {
        if (var7 > 50)
        {
            return null;
        }
        else if (Math.abs(var3 - var0.b().a) <= 112 && Math.abs(var5 - var0.b().c) <= 112)
        {
            WorldGenVillagePiece var8 = c(var0, var1, var2, var3, var4, var5, var6, var7 + 1);

            if (var8 != null)
            {
                int var9 = (var8.e.a + var8.e.d) / 2;
                int var10 = (var8.e.c + var8.e.f) / 2;
                int var11 = var8.e.d - var8.e.a;
                int var12 = var8.e.f - var8.e.c;
                int var13 = var11 > var12 ? var11 : var12;

                if (var0.d().a(var9, var10, var13 / 2 + 4, WorldGenVillage.e))
                {
                    var1.add(var8);
                    var0.i.add(var8);
                    return var8;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    private static StructurePiece e(WorldGenVillageStartPiece var0, List var1, Random var2, int var3, int var4, int var5, int var6, int var7)
    {
        if (var7 > 3 + var0.c)
        {
            return null;
        }
        else if (Math.abs(var3 - var0.b().a) <= 112 && Math.abs(var5 - var0.b().c) <= 112)
        {
            StructureBoundingBox var8 = WorldGenVillageRoad.a(var0, var1, var2, var3, var4, var5, var6);

            if (var8 != null && var8.b > 10)
            {
                WorldGenVillageRoad var9 = new WorldGenVillageRoad(var0, var7, var2, var8, var6);
                int var10 = (var9.e.a + var9.e.d) / 2;
                int var11 = (var9.e.c + var9.e.f) / 2;
                int var12 = var9.e.d - var9.e.a;
                int var13 = var9.e.f - var9.e.c;
                int var14 = var12 > var13 ? var12 : var13;

                if (var0.d().a(var10, var11, var14 / 2 + 4, WorldGenVillage.e))
                {
                    var1.add(var9);
                    var0.j.add(var9);
                    return var9;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned
     */
    static StructurePiece a(WorldGenVillageStartPiece var0, List var1, Random var2, int var3, int var4, int var5, int var6, int var7)
    {
        return d(var0, var1, var2, var3, var4, var5, var6, var7);
    }

    static StructurePiece b(WorldGenVillageStartPiece var0, List var1, Random var2, int var3, int var4, int var5, int var6, int var7)
    {
        return e(var0, var1, var2, var3, var4, var5, var6, var7);
    }
}
