package net.minecraft.server;

import java.util.Random;

class WorldGenStrongholdStones extends StructurePieceBlockSelector
{
  public void a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.a = Block.SMOOTH_BRICK.id;

      float f = paramRandom.nextFloat();
      if (f < 0.2F) {
        this.b = 2;
      } else if (f < 0.5F) {
        this.b = 1;
      } else if (f < 0.55F) {
        this.a = Block.MONSTER_EGGS.id;
        this.b = 2;
      } else {
        this.b = 0;
      }
    } else {
      this.a = 0;
      this.b = 0;
    }
  }
}