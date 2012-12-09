package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftPieces
{
  public static final StructurePieceTreasure[] a = { new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.INK_SACK.id, 4, 4, 9, 5), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 2, 3), new StructurePieceTreasure(Item.COAL.id, 0, 3, 8, 10), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 1), new StructurePieceTreasure(Block.RAILS.id, 0, 4, 8, 1), new StructurePieceTreasure(Item.MELON_SEEDS.id, 0, 2, 4, 10), new StructurePieceTreasure(Item.PUMPKIN_SEEDS.id, 0, 2, 4, 10) };

  public static StructurePiece a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i = paramRandom.nextInt(100);
    StructureBoundingBox localStructureBoundingBox;
    if (i >= 80) {
      localStructureBoundingBox = WorldGenMineshaftCross.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4);
      if (localStructureBoundingBox != null)
        return new WorldGenMineshaftCross(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
    }
    else if (i >= 70) {
      localStructureBoundingBox = WorldGenMineshaftStairs.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4);
      if (localStructureBoundingBox != null)
        return new WorldGenMineshaftStairs(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
    }
    else {
      localStructureBoundingBox = WorldGenMineshaftCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4);
      if (localStructureBoundingBox != null) {
        return new WorldGenMineshaftCorridor(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
      }
    }

    return null;
  }

  // was initially called "b". Had to change to "a" because all were referencing to a instead.
  public static StructurePiece a(StructurePiece paramStructurePiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt5 > 8) {
      return null;
    }
    if ((Math.abs(paramInt1 - paramStructurePiece.b().a) > 80) || (Math.abs(paramInt3 - paramStructurePiece.b().c) > 80)) {
      return null;
    }

    StructurePiece localStructurePiece = a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5 + 1);
    if (localStructurePiece != null) {
      paramList.add(localStructurePiece);
      localStructurePiece.a(paramStructurePiece, paramList, paramRandom);
    }
    return localStructurePiece;
  }
}