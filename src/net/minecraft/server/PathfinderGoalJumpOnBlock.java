package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalJumpOnBlock extends PathfinderGoal
{
  private final EntityOcelot a;
  private final float b;
  private int c = 0;
  private int d = 0;
  private int e = 0;
  private int f = 0;
  private int g = 0;
  private int h = 0;

  public PathfinderGoalJumpOnBlock(EntityOcelot paramEntityOcelot, float paramFloat) {
    this.a = paramEntityOcelot;
    this.b = paramFloat;
    a(5);
  }

  public boolean a()
  {
    return (this.a.isTamed()) && (!this.a.isSitting()) && (this.a.aB().nextDouble() <= 0.006500000134110451D) && (f());
  }

  public boolean b()
  {
    return (this.c <= this.e) && (this.d <= 60) && (a(this.a.world, this.f, this.g, this.h));
  }

  public void c()
  {
    this.a.getNavigation().a(this.f + 0.5D, this.g + 1, this.h + 0.5D, this.b);
    this.c = 0;
    this.d = 0;
    this.e = (this.a.aB().nextInt(this.a.aB().nextInt(1200) + 1200) + 1200);
    this.a.q().a(false);
  }

  public void d()
  {
    this.a.setSitting(false);
  }

  public void e()
  {
    this.c += 1;
    this.a.q().a(false);
    if (this.a.e((double)this.f, (double)this.g + 1d, (double)this.h) > 1.0D) {
      this.a.setSitting(false);
      this.a.getNavigation().a(this.f + 0.5D, this.g + 1, this.h + 0.5D, this.b);
      this.d += 1;
    } else if (!this.a.isSitting()) {
      this.a.setSitting(true);
    } else {
      this.d -= 1;
    }
  }

  private boolean f() {
    int i = (int)this.a.locY;
    double d1 = 2147483647.0D;

    for (int j = (int)this.a.locX - 8; j < this.a.locX + 8.0D; j++) {
      for (int k = (int)this.a.locZ - 8; k < this.a.locZ + 8.0D; k++) {
        if ((a(this.a.world, j, i, k)) && (this.a.world.isEmpty(j, i + 1, k))) {
          double d2 = this.a.e((double)j, (double)i, (double)k);

          if (d2 < d1) {
            this.f = j;
            this.g = i;
            this.h = k;
            d1 = d2;
          }
        }
      }
    }

    return d1 < 2147483647.0D;
  }

  protected boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
    int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);

    if (i == Block.CHEST.id) {
      TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

      if (localTileEntityChest.h < 1)
        return true;
    } else {
      if (i == Block.BURNING_FURNACE.id)
        return true;
      if ((i == Block.BED.id) && (!BlockBed.b_(j))) {
        return true;
      }
    }
    return false;
  }
}