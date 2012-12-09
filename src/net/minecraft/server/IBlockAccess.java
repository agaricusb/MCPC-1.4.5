package net.minecraft.server;

public abstract interface IBlockAccess
{
  public abstract int getTypeId(int paramInt1, int paramInt2, int paramInt3);

  public abstract TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3);

  public abstract int getData(int paramInt1, int paramInt2, int paramInt3);

  public abstract Material getMaterial(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean s(int paramInt1, int paramInt2, int paramInt3);

  public abstract Vec3DPool getVec3DPool();

  public abstract boolean isBlockFacePowered(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract BiomeBase getBiome(int i, int j);
}