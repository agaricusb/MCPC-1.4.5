package net.minecraft.server;

import java.util.Random;

class WorldGenJungleTemplePiece extends StructurePieceBlockSelector
{
	public WorldGenJungleTemplePiece() {}
	public WorldGenJungleTemplePiece(WorldGenJungleTempleUnknown e) {}

	public void a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
	{
		if (paramRandom.nextFloat() < 0.4F)
			this.a = Block.COBBLESTONE.id;
		else
			this.a = Block.MOSSY_COBBLESTONE.id;
	}
}