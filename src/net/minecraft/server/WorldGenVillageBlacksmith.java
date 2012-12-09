package net.minecraft.server;

import java.util.List;
import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenVillageBlacksmith extends WorldGenVillagePiece
{
    /** List of items that Village's Blacksmith chest can contain. */
    public static final StructurePieceTreasure[] a = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_SWORD.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_CHESTPLATE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_HELMET.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_LEGGINGS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_BOOTS.id, 0, 1, 1, 5), new StructurePieceTreasure(Block.OBSIDIAN.id, 0, 3, 7, 5), new StructurePieceTreasure(Block.SAPLING.id, 0, 3, 7, 5)};
    private int b = -1;
    private boolean c;

    public WorldGenVillageBlacksmith(WorldGenVillageStartPiece var1, int var2, Random var3, StructureBoundingBox var4, int var5)
    {
        super(var1, var2);
        this.f = var5;
        this.e = var4;
    }

    public static WorldGenVillageBlacksmith a(WorldGenVillageStartPiece var0, List var1, Random var2, int var3, int var4, int var5, int var6, int var7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 10, 6, 7, var6);
        return a(var8) && StructurePiece.a(var1, var8) == null ? new WorldGenVillageBlacksmith(var0, var7, var2, var8, var6) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World var1, Random var2, StructureBoundingBox var3)
    {
        if (this.b < 0)
        {
            this.b = this.b(var1, var3);

            if (this.b < 0)
            {
                return true;
            }

            this.e.a(0, this.b - this.e.e + 6 - 1, 0);
        }

        this.a(var1, var3, 0, 1, 0, 9, 4, 6, 0, 0, false);
        this.a(var1, var3, 0, 0, 0, 9, 0, 6, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(var1, var3, 0, 4, 0, 9, 4, 6, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(var1, var3, 0, 5, 0, 9, 5, 6, Block.STEP.id, Block.STEP.id, false);
        this.a(var1, var3, 1, 5, 1, 8, 5, 5, 0, 0, false);
        this.a(var1, var3, 1, 1, 0, 2, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(var1, var3, 0, 1, 0, 0, 4, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(var1, var3, 3, 1, 0, 3, 4, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(var1, var3, 0, 1, 6, 0, 4, 6, Block.LOG.id, Block.LOG.id, false);
        this.a(var1, Block.WOOD.id, 0, 3, 3, 1, var3);
        this.a(var1, var3, 3, 1, 2, 3, 3, 2, Block.WOOD.id, Block.WOOD.id, false);
        this.a(var1, var3, 4, 1, 3, 5, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(var1, var3, 0, 1, 1, 0, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(var1, var3, 1, 1, 6, 5, 3, 6, Block.WOOD.id, Block.WOOD.id, false);
        this.a(var1, var3, 5, 1, 0, 5, 3, 0, Block.FENCE.id, Block.FENCE.id, false);
        this.a(var1, var3, 9, 1, 0, 9, 3, 0, Block.FENCE.id, Block.FENCE.id, false);
        this.a(var1, var3, 6, 1, 4, 9, 4, 6, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(var1, Block.LAVA.id, 0, 7, 1, 5, var3);
        this.a(var1, Block.LAVA.id, 0, 8, 1, 5, var3);
        this.a(var1, Block.IRON_FENCE.id, 0, 9, 2, 5, var3);
        this.a(var1, Block.IRON_FENCE.id, 0, 9, 2, 4, var3);
        this.a(var1, var3, 7, 2, 4, 8, 2, 5, 0, 0, false);
        this.a(var1, Block.COBBLESTONE.id, 0, 6, 1, 3, var3);
        this.a(var1, Block.FURNACE.id, 0, 6, 2, 3, var3);
        this.a(var1, Block.FURNACE.id, 0, 6, 3, 3, var3);
        this.a(var1, Block.DOUBLE_STEP.id, 0, 8, 1, 1, var3);
        this.a(var1, Block.THIN_GLASS.id, 0, 0, 2, 2, var3);
        this.a(var1, Block.THIN_GLASS.id, 0, 0, 2, 4, var3);
        this.a(var1, Block.THIN_GLASS.id, 0, 2, 2, 6, var3);
        this.a(var1, Block.THIN_GLASS.id, 0, 4, 2, 6, var3);
        this.a(var1, Block.FENCE.id, 0, 2, 1, 4, var3);
        this.a(var1, Block.WOOD_PLATE.id, 0, 2, 2, 4, var3);
        this.a(var1, Block.WOOD.id, 0, 1, 1, 5, var3);
        this.a(var1, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 3), 2, 1, 5, var3);
        this.a(var1, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 1), 1, 1, 4, var3);
        int var4;
        int var5;

        if (!this.c)
        {
            var4 = this.a(1);
            var5 = this.a(5, 5);
            int var6 = this.b(5, 5);

            if (var3.b(var5, var4, var6))
            {
                this.c = true;
                this.a(var1, var3, var2, 5, 1, 5, ChestGenHooks.getItems("villageBlacksmith"), ChestGenHooks.getCount("villageBlacksmith", var2));
            }
        }

        for (var4 = 6; var4 <= 8; ++var4)
        {
            if (this.a(var1, var4, 0, -1, var3) == 0 && this.a(var1, var4, -1, -1, var3) != 0)
            {
                this.a(var1, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), var4, 0, -1, var3);
            }
        }

        for (var4 = 0; var4 < 7; ++var4)
        {
            for (var5 = 0; var5 < 10; ++var5)
            {
                this.b(var1, var5, 6, var4, var3);
                this.b(var1, Block.COBBLESTONE.id, 0, var5, -1, var4, var3);
            }
        }

        this.a(var1, var3, 7, 1, 1, 1);
        return true;
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int b(int var1)
    {
        return 3;
    }
}
