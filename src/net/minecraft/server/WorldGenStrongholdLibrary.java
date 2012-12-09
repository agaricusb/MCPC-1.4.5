package net.minecraft.server;

import java.util.List;
import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenStrongholdLibrary extends WorldGenStrongholdPiece
{
    /** List of items that Stronghold Library chests can contain. */
    public static final StructurePieceTreasure[] b = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.BOOK.id, 0, 1, 3, 20), new StructurePieceTreasure(Item.PAPER.id, 0, 2, 7, 20), new StructurePieceTreasure(Item.MAP_EMPTY.id, 0, 1, 1, 1), new StructurePieceTreasure(Item.COMPASS.id, 0, 1, 1, 1)};
    protected final WorldGenStrongholdDoorType a;
    private final boolean c;

    public WorldGenStrongholdLibrary(int var1, Random var2, StructureBoundingBox var3, int var4)
    {
        super(var1);
        this.f = var4;
        this.a = this.a(var2);
        this.e = var3;
        this.c = var3.c() > 6;
    }

    public static WorldGenStrongholdLibrary a(List var0, Random var1, int var2, int var3, int var4, int var5, int var6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 14, 11, 15, var5);

        if (!a(var7) || StructurePiece.a(var0, var7) != null)
        {
            var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 14, 6, 15, var5);

            if (!a(var7) || StructurePiece.a(var0, var7) != null)
            {
                return null;
            }
        }

        return new WorldGenStrongholdLibrary(var6, var1, var7, var5);
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
            byte var4 = 11;

            if (!this.c)
            {
                var4 = 6;
            }

            this.a(var1, var3, 0, 0, 0, 13, var4 - 1, 14, true, var2, WorldGenStrongholdPieces.b());
            this.a(var1, var2, var3, this.a, 4, 1, 0);
            this.a(var1, var3, var2, 0.07F, 2, 1, 1, 11, 4, 13, Block.WEB.id, Block.WEB.id, false);
            int var5;

            for (var5 = 1; var5 <= 13; ++var5)
            {
                if ((var5 - 1) % 4 == 0)
                {
                    this.a(var1, var3, 1, 1, var5, 1, 4, var5, Block.WOOD.id, Block.WOOD.id, false);
                    this.a(var1, var3, 12, 1, var5, 12, 4, var5, Block.WOOD.id, Block.WOOD.id, false);
                    this.a(var1, Block.TORCH.id, 0, 2, 3, var5, var3);
                    this.a(var1, Block.TORCH.id, 0, 11, 3, var5, var3);

                    if (this.c)
                    {
                        this.a(var1, var3, 1, 6, var5, 1, 9, var5, Block.WOOD.id, Block.WOOD.id, false);
                        this.a(var1, var3, 12, 6, var5, 12, 9, var5, Block.WOOD.id, Block.WOOD.id, false);
                    }
                }
                else
                {
                    this.a(var1, var3, 1, 1, var5, 1, 4, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                    this.a(var1, var3, 12, 1, var5, 12, 4, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);

                    if (this.c)
                    {
                        this.a(var1, var3, 1, 6, var5, 1, 9, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                        this.a(var1, var3, 12, 6, var5, 12, 9, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                    }
                }
            }

            for (var5 = 3; var5 < 12; var5 += 2)
            {
                this.a(var1, var3, 3, 1, var5, 4, 3, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                this.a(var1, var3, 6, 1, var5, 7, 3, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                this.a(var1, var3, 9, 1, var5, 10, 3, var5, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
            }

            if (this.c)
            {
                this.a(var1, var3, 1, 5, 1, 3, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
                this.a(var1, var3, 10, 5, 1, 12, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
                this.a(var1, var3, 4, 5, 1, 9, 5, 2, Block.WOOD.id, Block.WOOD.id, false);
                this.a(var1, var3, 4, 5, 12, 9, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
                this.a(var1, Block.WOOD.id, 0, 9, 5, 11, var3);
                this.a(var1, Block.WOOD.id, 0, 8, 5, 11, var3);
                this.a(var1, Block.WOOD.id, 0, 9, 5, 10, var3);
                this.a(var1, var3, 3, 6, 2, 3, 6, 12, Block.FENCE.id, Block.FENCE.id, false);
                this.a(var1, var3, 10, 6, 2, 10, 6, 10, Block.FENCE.id, Block.FENCE.id, false);
                this.a(var1, var3, 4, 6, 2, 9, 6, 2, Block.FENCE.id, Block.FENCE.id, false);
                this.a(var1, var3, 4, 6, 12, 8, 6, 12, Block.FENCE.id, Block.FENCE.id, false);
                this.a(var1, Block.FENCE.id, 0, 9, 6, 11, var3);
                this.a(var1, Block.FENCE.id, 0, 8, 6, 11, var3);
                this.a(var1, Block.FENCE.id, 0, 9, 6, 10, var3);
                var5 = this.c(Block.LADDER.id, 3);
                this.a(var1, Block.LADDER.id, var5, 10, 1, 13, var3);
                this.a(var1, Block.LADDER.id, var5, 10, 2, 13, var3);
                this.a(var1, Block.LADDER.id, var5, 10, 3, 13, var3);
                this.a(var1, Block.LADDER.id, var5, 10, 4, 13, var3);
                this.a(var1, Block.LADDER.id, var5, 10, 5, 13, var3);
                this.a(var1, Block.LADDER.id, var5, 10, 6, 13, var3);
                this.a(var1, Block.LADDER.id, var5, 10, 7, 13, var3);
                byte var6 = 7;
                byte var7 = 7;
                this.a(var1, Block.FENCE.id, 0, var6 - 1, 9, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6, 9, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6 - 1, 8, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6, 8, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6 - 1, 7, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6, 7, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6 - 2, 7, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6 + 1, 7, var7, var3);
                this.a(var1, Block.FENCE.id, 0, var6 - 1, 7, var7 - 1, var3);
                this.a(var1, Block.FENCE.id, 0, var6 - 1, 7, var7 + 1, var3);
                this.a(var1, Block.FENCE.id, 0, var6, 7, var7 - 1, var3);
                this.a(var1, Block.FENCE.id, 0, var6, 7, var7 + 1, var3);
                this.a(var1, Block.TORCH.id, 0, var6 - 2, 8, var7, var3);
                this.a(var1, Block.TORCH.id, 0, var6 + 1, 8, var7, var3);
                this.a(var1, Block.TORCH.id, 0, var6 - 1, 8, var7 - 1, var3);
                this.a(var1, Block.TORCH.id, 0, var6 - 1, 8, var7 + 1, var3);
                this.a(var1, Block.TORCH.id, 0, var6, 8, var7 - 1, var3);
                this.a(var1, Block.TORCH.id, 0, var6, 8, var7 + 1, var3);
            }

            ChestGenHooks var8 = ChestGenHooks.getInfo("strongholdLibrary");
            this.a(var1, var3, var2, 3, 3, 5, var8.getItems(), var8.getCount(var2));

            if (this.c)
            {
                this.a(var1, 0, 0, 12, 9, 1, var3);
                this.a(var1, var3, var2, 12, 8, 1, var8.getItems(), var8.getCount(var2));
            }

            return true;
        }
    }
}
