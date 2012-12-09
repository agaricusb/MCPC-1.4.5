package net.minecraft.server;

import java.util.List;
import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenStrongholdChestCorridor extends WorldGenStrongholdPiece
{
    /** List of items that Stronghold chests can contain. */
    public static final StructurePieceTreasure[] a = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.ENDER_PEARL.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_SWORD.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_CHESTPLATE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_HELMET.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_LEGGINGS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_BOOTS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.GOLDEN_APPLE.id, 0, 1, 1, 1)};
    private final WorldGenStrongholdDoorType b;
    private boolean c;

    public WorldGenStrongholdChestCorridor(int var1, Random var2, StructureBoundingBox var3, int var4)
    {
        super(var1);
        this.f = var4;
        this.b = this.a(var2);
        this.e = var3;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece var1, List var2, Random var3)
    {
        this.a((WorldGenStrongholdStart)var1, var2, var3, 1, 1);
    }

    public static WorldGenStrongholdChestCorridor a(List var0, Random var1, int var2, int var3, int var4, int var5, int var6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 7, var5);
        return a(var7) && StructurePiece.a(var0, var7) == null ? new WorldGenStrongholdChestCorridor(var6, var1, var7, var5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World var1, Random var2, StructureBoundingBox var3)
    {
        if (this.a(var1, var3))
        {
            return false;
        }
        else
        {
            this.a(var1, var3, 0, 0, 0, 4, 4, 6, true, var2, WorldGenStrongholdPieces.b());
            this.a(var1, var2, var3, this.b, 1, 1, 0);
            this.a(var1, var2, var3, WorldGenStrongholdDoorType.a, 1, 1, 6);
            this.a(var1, var3, 3, 1, 2, 3, 1, 4, Block.SMOOTH_BRICK.id, Block.SMOOTH_BRICK.id, false);
            this.a(var1, Block.STEP.id, 5, 3, 1, 1, var3);
            this.a(var1, Block.STEP.id, 5, 3, 1, 5, var3);
            this.a(var1, Block.STEP.id, 5, 3, 2, 2, var3);
            this.a(var1, Block.STEP.id, 5, 3, 2, 4, var3);
            int var4;

            for (var4 = 2; var4 <= 4; ++var4)
            {
                this.a(var1, Block.STEP.id, 5, 2, 1, var4, var3);
            }

            if (!this.c)
            {
                var4 = this.a(2);
                int var5 = this.a(3, 3);
                int var6 = this.b(3, 3);

                if (var3.b(var5, var4, var6))
                {
                    this.c = true;
                    this.a(var1, var3, var2, 3, 2, 3, ChestGenHooks.getItems("strongholdCorridor"), ChestGenHooks.getCount("strongholdCorridor", var2));
                }
            }

            return true;
        }
    }
}
