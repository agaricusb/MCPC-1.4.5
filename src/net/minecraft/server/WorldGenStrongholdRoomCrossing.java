package net.minecraft.server;

import java.util.List;
import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenStrongholdRoomCrossing extends WorldGenStrongholdPiece
{
    /**
     * Items that could generate in the chest that is located in Stronghold Room Crossing.
     */
    public static final StructurePieceTreasure[] c = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.COAL.id, 0, 3, 8, 10), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 1)};
    protected final WorldGenStrongholdDoorType a;
    protected final int b;

    public WorldGenStrongholdRoomCrossing(int var1, Random var2, StructureBoundingBox var3, int var4)
    {
        super(var1);
        this.f = var4;
        this.a = this.a(var2);
        this.e = var3;
        this.b = var2.nextInt(5);
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece var1, List var2, Random var3)
    {
        this.a((WorldGenStrongholdStart)var1, var2, var3, 4, 1);
        this.b((WorldGenStrongholdStart)var1, var2, var3, 1, 4);
        this.c((WorldGenStrongholdStart)var1, var2, var3, 1, 4);
    }

    public static WorldGenStrongholdRoomCrossing a(List var0, Random var1, int var2, int var3, int var4, int var5, int var6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 11, 7, 11, var5);
        return a(var7) && StructurePiece.a(var0, var7) == null ? new WorldGenStrongholdRoomCrossing(var6, var1, var7, var5) : null;
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
            this.a(var1, var3, 0, 0, 0, 10, 6, 10, true, var2, WorldGenStrongholdPieces.b());
            this.a(var1, var2, var3, this.a, 4, 1, 0);
            this.a(var1, var3, 4, 1, 10, 6, 3, 10, 0, 0, false);
            this.a(var1, var3, 0, 1, 4, 0, 3, 6, 0, 0, false);
            this.a(var1, var3, 10, 1, 4, 10, 3, 6, 0, 0, false);
            int var4;

            switch (this.b)
            {
                case 0:
                    this.a(var1, Block.SMOOTH_BRICK.id, 0, 5, 1, 5, var3);
                    this.a(var1, Block.SMOOTH_BRICK.id, 0, 5, 2, 5, var3);
                    this.a(var1, Block.SMOOTH_BRICK.id, 0, 5, 3, 5, var3);
                    this.a(var1, Block.TORCH.id, 0, 4, 3, 5, var3);
                    this.a(var1, Block.TORCH.id, 0, 6, 3, 5, var3);
                    this.a(var1, Block.TORCH.id, 0, 5, 3, 4, var3);
                    this.a(var1, Block.TORCH.id, 0, 5, 3, 6, var3);
                    this.a(var1, Block.STEP.id, 0, 4, 1, 4, var3);
                    this.a(var1, Block.STEP.id, 0, 4, 1, 5, var3);
                    this.a(var1, Block.STEP.id, 0, 4, 1, 6, var3);
                    this.a(var1, Block.STEP.id, 0, 6, 1, 4, var3);
                    this.a(var1, Block.STEP.id, 0, 6, 1, 5, var3);
                    this.a(var1, Block.STEP.id, 0, 6, 1, 6, var3);
                    this.a(var1, Block.STEP.id, 0, 5, 1, 4, var3);
                    this.a(var1, Block.STEP.id, 0, 5, 1, 6, var3);
                    break;

                case 1:
                    for (var4 = 0; var4 < 5; ++var4)
                    {
                        this.a(var1, Block.SMOOTH_BRICK.id, 0, 3, 1, 3 + var4, var3);
                        this.a(var1, Block.SMOOTH_BRICK.id, 0, 7, 1, 3 + var4, var3);
                        this.a(var1, Block.SMOOTH_BRICK.id, 0, 3 + var4, 1, 3, var3);
                        this.a(var1, Block.SMOOTH_BRICK.id, 0, 3 + var4, 1, 7, var3);
                    }

                    this.a(var1, Block.SMOOTH_BRICK.id, 0, 5, 1, 5, var3);
                    this.a(var1, Block.SMOOTH_BRICK.id, 0, 5, 2, 5, var3);
                    this.a(var1, Block.SMOOTH_BRICK.id, 0, 5, 3, 5, var3);
                    this.a(var1, Block.WATER.id, 0, 5, 4, 5, var3);
                    break;

                case 2:
                    for (var4 = 1; var4 <= 9; ++var4)
                    {
                        this.a(var1, Block.COBBLESTONE.id, 0, 1, 3, var4, var3);
                        this.a(var1, Block.COBBLESTONE.id, 0, 9, 3, var4, var3);
                    }

                    for (var4 = 1; var4 <= 9; ++var4)
                    {
                        this.a(var1, Block.COBBLESTONE.id, 0, var4, 3, 1, var3);
                        this.a(var1, Block.COBBLESTONE.id, 0, var4, 3, 9, var3);
                    }

                    this.a(var1, Block.COBBLESTONE.id, 0, 5, 1, 4, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 5, 1, 6, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 5, 3, 4, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 5, 3, 6, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 4, 1, 5, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 6, 1, 5, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 4, 3, 5, var3);
                    this.a(var1, Block.COBBLESTONE.id, 0, 6, 3, 5, var3);

                    for (var4 = 1; var4 <= 3; ++var4)
                    {
                        this.a(var1, Block.COBBLESTONE.id, 0, 4, var4, 4, var3);
                        this.a(var1, Block.COBBLESTONE.id, 0, 6, var4, 4, var3);
                        this.a(var1, Block.COBBLESTONE.id, 0, 4, var4, 6, var3);
                        this.a(var1, Block.COBBLESTONE.id, 0, 6, var4, 6, var3);
                    }

                    this.a(var1, Block.TORCH.id, 0, 5, 3, 5, var3);

                    for (var4 = 2; var4 <= 8; ++var4)
                    {
                        this.a(var1, Block.WOOD.id, 0, 2, 3, var4, var3);
                        this.a(var1, Block.WOOD.id, 0, 3, 3, var4, var3);

                        if (var4 <= 3 || var4 >= 7)
                        {
                            this.a(var1, Block.WOOD.id, 0, 4, 3, var4, var3);
                            this.a(var1, Block.WOOD.id, 0, 5, 3, var4, var3);
                            this.a(var1, Block.WOOD.id, 0, 6, 3, var4, var3);
                        }

                        this.a(var1, Block.WOOD.id, 0, 7, 3, var4, var3);
                        this.a(var1, Block.WOOD.id, 0, 8, 3, var4, var3);
                    }

                    this.a(var1, Block.LADDER.id, this.c(Block.LADDER.id, 4), 9, 1, 3, var3);
                    this.a(var1, Block.LADDER.id, this.c(Block.LADDER.id, 4), 9, 2, 3, var3);
                    this.a(var1, Block.LADDER.id, this.c(Block.LADDER.id, 4), 9, 3, 3, var3);
                    this.a(var1, var3, var2, 3, 4, 8, ChestGenHooks.getItems("strongholdCrossing"), ChestGenHooks.getCount("strongholdCrossing", var2));
            }

            return true;
        }
    }
}
