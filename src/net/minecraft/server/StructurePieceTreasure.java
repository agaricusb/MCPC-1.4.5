package net.minecraft.server;

import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class StructurePieceTreasure extends WeightedRandomChoice
{
    /** The Item/Block ID to generate in the Chest. */
    private int b;

    /** The Item damage/metadata. */
    private int c;

    /** The minimum chance of item generating. */
    private int d;

    /** The maximum chance of item generating. */
    private int e;
    public final ItemStack itemStack;

    public StructurePieceTreasure(int var1, int var2, int var3, int var4, int var5)
    {
        super(var5);
        this.b = var1;
        this.c = var2;
        this.d = var3;
        this.e = var4;
        this.itemStack = new ItemStack(var1, 1, var2);
    }

    public StructurePieceTreasure(ItemStack var1, int var2, int var3, int var4)
    {
        super(var4);
        this.itemStack = var1;
        this.d = var2;
        this.e = var3;
    }

    /**
     * Generates the Chest contents.
     */
    public static void a(Random var0, StructurePieceTreasure[] var1, TileEntityChest var2, int var3)
    {
        for (int var4 = 0; var4 < var3; ++var4)
        {
            StructurePieceTreasure var5 = (StructurePieceTreasure)WeightedRandom.a(var0, var1);
            ItemStack[] var6 = ChestGenHooks.generateStacks(var0, var5.itemStack, var5.d, var5.d);
            ItemStack[] var7 = var6;
            int var8 = var6.length;

            for (int var9 = 0; var9 < var8; ++var9)
            {
                ItemStack var10 = var7[var9];
                var2.setItem(var0.nextInt(var2.getSize()), var10);
            }
        }
    }

    /**
     * Generates the Dispenser contents.
     */
    public static void a(Random var0, StructurePieceTreasure[] var1, TileEntityDispenser var2, int var3)
    {
        for (int var4 = 0; var4 < var3; ++var4)
        {
            StructurePieceTreasure var5 = (StructurePieceTreasure)WeightedRandom.a(var0, var1);
            ItemStack[] var6 = ChestGenHooks.generateStacks(var0, var5.itemStack, var5.d, var5.d);
            ItemStack[] var7 = var6;
            int var8 = var6.length;

            for (int var9 = 0; var9 < var8; ++var9)
            {
                ItemStack var10 = var7[var9];
                var2.setItem(var0.nextInt(var2.getSize()), var10);
            }
        }
    }
}
