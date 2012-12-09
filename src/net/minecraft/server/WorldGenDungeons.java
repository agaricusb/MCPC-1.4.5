package net.minecraft.server;

import java.util.Random;
import net.minecraftforge.common.DungeonHooks;

public class WorldGenDungeons extends WorldGenerator
{
    public boolean a(World var1, Random var2, int var3, int var4, int var5)
    {
        byte var6 = 3;
        int var7 = var2.nextInt(2) + 2;
        int var8 = var2.nextInt(2) + 2;
        int var9 = 0;
        int var10;
        int var11;
        int var12;

        for (var10 = var3 - var7 - 1; var10 <= var3 + var7 + 1; ++var10)
        {
            for (var11 = var4 - 1; var11 <= var4 + var6 + 1; ++var11)
            {
                for (var12 = var5 - var8 - 1; var12 <= var5 + var8 + 1; ++var12)
                {
                    Material var13 = var1.getMaterial(var10, var11, var12);

                    if (var11 == var4 - 1 && !var13.isBuildable())
                    {
                        return false;
                    }

                    if (var11 == var4 + var6 + 1 && !var13.isBuildable())
                    {
                        return false;
                    }

                    if ((var10 == var3 - var7 - 1 || var10 == var3 + var7 + 1 || var12 == var5 - var8 - 1 || var12 == var5 + var8 + 1) && var11 == var4 && var1.isEmpty(var10, var11, var12) && var1.isEmpty(var10, var11 + 1, var12))
                    {
                        ++var9;
                    }
                }
            }
        }

        if (var9 >= 1 && var9 <= 5)
        {
            for (var10 = var3 - var7 - 1; var10 <= var3 + var7 + 1; ++var10)
            {
                for (var11 = var4 + var6; var11 >= var4 - 1; --var11)
                {
                    for (var12 = var5 - var8 - 1; var12 <= var5 + var8 + 1; ++var12)
                    {
                        if (var10 != var3 - var7 - 1 && var11 != var4 - 1 && var12 != var5 - var8 - 1 && var10 != var3 + var7 + 1 && var11 != var4 + var6 + 1 && var12 != var5 + var8 + 1)
                        {
                            var1.setTypeId(var10, var11, var12, 0);
                        }
                        else if (var11 >= 0 && !var1.getMaterial(var10, var11 - 1, var12).isBuildable())
                        {
                            var1.setTypeId(var10, var11, var12, 0);
                        }
                        else if (var1.getMaterial(var10, var11, var12).isBuildable())
                        {
                            if (var11 == var4 - 1 && var2.nextInt(4) != 0)
                            {
                                var1.setTypeId(var10, var11, var12, Block.MOSSY_COBBLESTONE.id);
                            }
                            else
                            {
                                var1.setTypeId(var10, var11, var12, Block.COBBLESTONE.id);
                            }
                        }
                    }
                }
            }

            var10 = 0;

            while (var10 < 2)
            {
                var11 = 0;

                while (true)
                {
                    if (var11 < 3)
                    {
                        label111:
                        {
                            var12 = var3 + var2.nextInt(var7 * 2 + 1) - var7;
                            int var19 = var5 + var2.nextInt(var8 * 2 + 1) - var8;

                            if (var1.isEmpty(var12, var4, var19))
                            {
                                int var14 = 0;

                                if (var1.getMaterial(var12 - 1, var4, var19).isBuildable())
                                {
                                    ++var14;
                                }

                                if (var1.getMaterial(var12 + 1, var4, var19).isBuildable())
                                {
                                    ++var14;
                                }

                                if (var1.getMaterial(var12, var4, var19 - 1).isBuildable())
                                {
                                    ++var14;
                                }

                                if (var1.getMaterial(var12, var4, var19 + 1).isBuildable())
                                {
                                    ++var14;
                                }

                                if (var14 == 1)
                                {
                                    var1.setTypeId(var12, var4, var19, Block.CHEST.id);
                                    TileEntityChest var15 = (TileEntityChest)var1.getTileEntity(var12, var4, var19);

                                    if (var15 != null)
                                    {
                                        for (int var16 = 0; var16 < DungeonHooks.getDungeonLootTries(); ++var16)
                                        {
                                            ItemStack var17 = DungeonHooks.getRandomDungeonLoot(var2);

                                            if (var17 != null)
                                            {
                                                var15.setItem(var2.nextInt(var15.getSize()), var17);
                                            }
                                        }
                                    }

                                    break label111;
                                }
                            }

                            ++var11;
                            continue;
                        }
                    }

                    ++var10;
                    break;
                }
            }

            var1.setTypeId(var3, var4, var5, Block.MOB_SPAWNER.id);
            TileEntityMobSpawner var18 = (TileEntityMobSpawner)var1.getTileEntity(var3, var4, var5);

            if (var18 != null)
            {
                var18.a(DungeonHooks.getRandomDungeonMob(var2));
            }
            else
            {
                System.err.println("Failed to fetch mob spawner entity at (" + var3 + ", " + var4 + ", " + var5 + ")");
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Picks potentially a random item to add to a dungeon chest.
     */
    private ItemStack a(Random var1)
    {
        int var2 = var1.nextInt(11);
        return var2 == 0 ? new ItemStack(Item.SADDLE) : (var2 == 1 ? new ItemStack(Item.IRON_INGOT, var1.nextInt(4) + 1) : (var2 == 2 ? new ItemStack(Item.BREAD) : (var2 == 3 ? new ItemStack(Item.WHEAT, var1.nextInt(4) + 1) : (var2 == 4 ? new ItemStack(Item.SULPHUR, var1.nextInt(4) + 1) : (var2 == 5 ? new ItemStack(Item.STRING, var1.nextInt(4) + 1) : (var2 == 6 ? new ItemStack(Item.BUCKET) : (var2 == 7 && var1.nextInt(100) == 0 ? new ItemStack(Item.GOLDEN_APPLE) : (var2 == 8 && var1.nextInt(2) == 0 ? new ItemStack(Item.REDSTONE, var1.nextInt(4) + 1) : (var2 == 9 && var1.nextInt(10) == 0 ? new ItemStack(Item.byId[Item.RECORD_1.id + var1.nextInt(2)]) : (var2 == 10 ? new ItemStack(Item.INK_SACK, 1, 3) : null))))))))));
    }

    /**
     * Randomly decides which spawner to use in a dungeon
     */
    private String b(Random var1)
    {
        int var2 = var1.nextInt(4);
        return var2 == 0 ? "Skeleton" : (var2 == 1 ? "Zombie" : (var2 == 2 ? "Zombie" : (var2 == 3 ? "Spider" : "")));
    }
}
