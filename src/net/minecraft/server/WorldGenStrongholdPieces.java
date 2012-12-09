package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPieces
{
  private static final WorldGenStrongholdPieceWeight[] b = { new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs.class, 40, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdPrison.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdLeftTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRightTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRoomCrossing.class, 10, 6), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairsStraight.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs2.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdCrossing.class, 5, 4), new WorldGenStrongholdPieceWeight(WorldGenStrongholdChestCorridor.class, 5, 4), new WorldGenStrongholdUnknown(WorldGenStrongholdLibrary.class, 10, 2), new WorldGenStrongholdPiece2(WorldGenStrongholdPortalRoom.class, 20, 1) };
  private static List<WorldGenStrongholdPieceWeight> c;
  private static Class d;
  static int a = 0;

  private static final WorldGenStrongholdStones e = new WorldGenStrongholdStones();

  //public static void a(Class cla) { a(); }
  public static Class a(Class cla) { a(); return cla; }
  public static void a() // sig from: WorldGenStrongholdStairs2.a(SourceFile:439)
  {
    c = new ArrayList<WorldGenStrongholdPieceWeight>();
    for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : b) {
      localWorldGenStrongholdPieceWeight.c = 0;
      c.add(localWorldGenStrongholdPieceWeight);
    }
    d = null;
  }
  
  // forge - missing function?
  public static WorldGenStrongholdStones b() { return e; }

  private static boolean c() {
    boolean i = false;
    a = 0;
    for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : c) {
      if ((localWorldGenStrongholdPieceWeight.d > 0) && (localWorldGenStrongholdPieceWeight.c < localWorldGenStrongholdPieceWeight.d)) {
        i = true;
      }
      a += localWorldGenStrongholdPieceWeight.b;
    }
    return i;
  }

  private static WorldGenStrongholdPiece a(Class paramClass, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Object localObject = null;

    if (paramClass == WorldGenStrongholdStairs.class)
      localObject = WorldGenStrongholdStairs.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdPrison.class)
      localObject = WorldGenStrongholdPrison.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdLeftTurn.class)
      localObject = WorldGenStrongholdLeftTurn.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdRightTurn.class)
      localObject = WorldGenStrongholdRightTurn.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdRoomCrossing.class)
      localObject = WorldGenStrongholdRoomCrossing.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdStairsStraight.class)
      localObject = WorldGenStrongholdStairsStraight.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdStairs2.class)
      localObject = WorldGenStrongholdStairs2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdCrossing.class)
      localObject = WorldGenStrongholdCrossing.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdChestCorridor.class)
      localObject = WorldGenStrongholdChestCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdLibrary.class)
      localObject = WorldGenStrongholdLibrary.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    else if (paramClass == WorldGenStrongholdPortalRoom.class) {
      localObject = WorldGenStrongholdPortalRoom.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }

    return (WorldGenStrongholdPiece)localObject;
  }
  
  /*
	java.lang.NoSuchMethodError: net.minecraft.server.WorldGenStrongholdPieces.a(Lnet/minecraft/server/WorldGenStrongholdStart;Ljava/util/List;Ljava/util/Random;IIIII)Lnet/minecraft/server/StructurePiece;
		at net.minecraft.server.WorldGenStrongholdPiece.a(SourceFile:276)
   
    java.lang.NoSuchMethodError: net.minecraft.server.WorldGenStrongholdPieces.a(Lnet/minecraft/server/WorldGenStrongholdStart;Ljava/util/List;Ljava/util/Random;IIIII)Lnet/minecraft/server/StructurePiece;
    	at net.minecraft.server.WorldGenStrongholdPiece.a(SourceFile:280)
   */

  private static WorldGenStrongholdPiece b(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (!c()) {
      return null;
    }

    if (d != null)
    {
      WorldGenStrongholdPiece localWorldGenStrongholdPiece1 = a(d, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      d = null;

      if (localWorldGenStrongholdPiece1 != null) {
        return localWorldGenStrongholdPiece1;
      }
    }

    int i = 0;
    int j;
    while (i < 5) {
      i++;

      j = paramRandom.nextInt(a);
      for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : c) {
        j -= localWorldGenStrongholdPieceWeight.b;
        if (j < 0)
        {
          if ((!localWorldGenStrongholdPieceWeight.a(paramInt5)) || (localWorldGenStrongholdPieceWeight == paramWorldGenStrongholdStart.a))
          {
            break;
          }
          WorldGenStrongholdPiece localWorldGenStrongholdPiece2 = a(localWorldGenStrongholdPieceWeight.a, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
          if (localWorldGenStrongholdPiece2 != null) {
            localWorldGenStrongholdPieceWeight.c += 1;
            paramWorldGenStrongholdStart.a = localWorldGenStrongholdPieceWeight;

            if (!localWorldGenStrongholdPieceWeight.a()) {
              c.remove(localWorldGenStrongholdPieceWeight);
            }
            return localWorldGenStrongholdPiece2;
          }
        }
      }
    }

    StructureBoundingBox localStructureBoundingBox = WorldGenStrongholdCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((localStructureBoundingBox != null) && (localStructureBoundingBox.b > 1)) {
      return new WorldGenStrongholdCorridor(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
    }

    return null;
  }

  public static StructurePiece a(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt5 > 50) {
      return null;
    }
    if ((Math.abs(paramInt1 - paramWorldGenStrongholdStart.b().a) > 112) || (Math.abs(paramInt3 - paramWorldGenStrongholdStart.b().c) > 112)) {
      return null;
    }

    WorldGenStrongholdPiece localWorldGenStrongholdPiece = b(paramWorldGenStrongholdStart, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5 + 1);
    if (localWorldGenStrongholdPiece != null) {
      paramList.add(localWorldGenStrongholdPiece);
      paramWorldGenStrongholdStart.c.add(localWorldGenStrongholdPiece);
    }
    return localWorldGenStrongholdPiece;
  }
}