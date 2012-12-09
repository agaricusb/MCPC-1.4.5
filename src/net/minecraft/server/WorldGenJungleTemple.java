package net.minecraft.server;

import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenJungleTemple extends WorldGenScatteredPiece
{
    private boolean field_74947_h;
    private boolean field_74948_i;
    private boolean field_74945_j;
    private boolean field_74946_k;

    /** List of Chest contents to be generated in the Jungle Pyramid chests. */
    public static final StructurePieceTreasure[] l = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 2, 7, 15), new StructurePieceTreasure(Item.EMERALD.id, 0, 1, 3, 2), new StructurePieceTreasure(Item.BONE.id, 0, 4, 6, 20), new StructurePieceTreasure(Item.ROTTEN_FLESH.id, 0, 3, 7, 16)};

    /**
     * List of Dispenser contents to be generated in the Jungle Pyramid dispensers.
     */
    public static final StructurePieceTreasure[] m = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.ARROW.id, 0, 2, 7, 30)};

    /** List of random stones to be generated in the Jungle Pyramid. */
    private static WorldGenJungleTemplePiece n = new WorldGenJungleTemplePiece((WorldGenJungleTempleUnknown)null);

    public WorldGenJungleTemple(Random var1, int var2, int var3)
    {
        super(var1, var2, 64, var3, 12, 10, 15);
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World var1, Random var2, StructureBoundingBox var3)
    {
        if (!this.a(var1, var3, 0))
        {
            return false;
        }
        else
        {
            int var4 = this.c(Block.COBBLESTONE_STAIRS.id, 3);
            int var5 = this.c(Block.COBBLESTONE_STAIRS.id, 2);
            int var6 = this.c(Block.COBBLESTONE_STAIRS.id, 0);
            int var7 = this.c(Block.COBBLESTONE_STAIRS.id, 1);
            this.a(var1, var3, 0, -4, 0, this.a - 1, 0, this.c - 1, false, var2, n);
            this.a(var1, var3, 2, 1, 2, 9, 2, 2, false, var2, n);
            this.a(var1, var3, 2, 1, 12, 9, 2, 12, false, var2, n);
            this.a(var1, var3, 2, 1, 3, 2, 2, 11, false, var2, n);
            this.a(var1, var3, 9, 1, 3, 9, 2, 11, false, var2, n);
            this.a(var1, var3, 1, 3, 1, 10, 6, 1, false, var2, n);
            this.a(var1, var3, 1, 3, 13, 10, 6, 13, false, var2, n);
            this.a(var1, var3, 1, 3, 2, 1, 6, 12, false, var2, n);
            this.a(var1, var3, 10, 3, 2, 10, 6, 12, false, var2, n);
            this.a(var1, var3, 2, 3, 2, 9, 3, 12, false, var2, n);
            this.a(var1, var3, 2, 6, 2, 9, 6, 12, false, var2, n);
            this.a(var1, var3, 3, 7, 3, 8, 7, 11, false, var2, n);
            this.a(var1, var3, 4, 8, 4, 7, 8, 10, false, var2, n);
            this.a(var1, var3, 3, 1, 3, 8, 2, 11);
            this.a(var1, var3, 4, 3, 6, 7, 3, 9);
            this.a(var1, var3, 2, 4, 2, 9, 5, 12);
            this.a(var1, var3, 4, 6, 5, 7, 6, 9);
            this.a(var1, var3, 5, 7, 6, 6, 7, 8);
            this.a(var1, var3, 5, 1, 2, 6, 2, 2);
            this.a(var1, var3, 5, 2, 12, 6, 2, 12);
            this.a(var1, var3, 5, 5, 1, 6, 5, 1);
            this.a(var1, var3, 5, 5, 13, 6, 5, 13);
            this.a(var1, 0, 0, 1, 5, 5, var3);
            this.a(var1, 0, 0, 10, 5, 5, var3);
            this.a(var1, 0, 0, 1, 5, 9, var3);
            this.a(var1, 0, 0, 10, 5, 9, var3);
            int var8;

            for (var8 = 0; var8 <= 14; var8 += 14)
            {
                this.a(var1, var3, 2, 4, var8, 2, 5, var8, false, var2, n);
                this.a(var1, var3, 4, 4, var8, 4, 5, var8, false, var2, n);
                this.a(var1, var3, 7, 4, var8, 7, 5, var8, false, var2, n);
                this.a(var1, var3, 9, 4, var8, 9, 5, var8, false, var2, n);
            }

            this.a(var1, var3, 5, 6, 0, 6, 6, 0, false, var2, n);

            for (var8 = 0; var8 <= 11; var8 += 11)
            {
                for (int var9 = 2; var9 <= 12; var9 += 2)
                {
                    this.a(var1, var3, var8, 4, var9, var8, 5, var9, false, var2, n);
                }

                this.a(var1, var3, var8, 6, 5, var8, 6, 5, false, var2, n);
                this.a(var1, var3, var8, 6, 9, var8, 6, 9, false, var2, n);
            }

            this.a(var1, var3, 2, 7, 2, 2, 9, 2, false, var2, n);
            this.a(var1, var3, 9, 7, 2, 9, 9, 2, false, var2, n);
            this.a(var1, var3, 2, 7, 12, 2, 9, 12, false, var2, n);
            this.a(var1, var3, 9, 7, 12, 9, 9, 12, false, var2, n);
            this.a(var1, var3, 4, 9, 4, 4, 9, 4, false, var2, n);
            this.a(var1, var3, 7, 9, 4, 7, 9, 4, false, var2, n);
            this.a(var1, var3, 4, 9, 10, 4, 9, 10, false, var2, n);
            this.a(var1, var3, 7, 9, 10, 7, 9, 10, false, var2, n);
            this.a(var1, var3, 5, 9, 7, 6, 9, 7, false, var2, n);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 5, 9, 6, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 6, 9, 6, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var5, 5, 9, 8, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var5, 6, 9, 8, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 4, 0, 0, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 5, 0, 0, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 6, 0, 0, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 7, 0, 0, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 4, 1, 8, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 4, 2, 9, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 4, 3, 10, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 7, 1, 8, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 7, 2, 9, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var4, 7, 3, 10, var3);
            this.a(var1, var3, 4, 1, 9, 4, 1, 9, false, var2, n);
            this.a(var1, var3, 7, 1, 9, 7, 1, 9, false, var2, n);
            this.a(var1, var3, 4, 1, 10, 7, 2, 10, false, var2, n);
            this.a(var1, var3, 5, 4, 5, 6, 4, 5, false, var2, n);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var6, 4, 4, 5, var3);
            this.a(var1, Block.COBBLESTONE_STAIRS.id, var7, 7, 4, 5, var3);

            for (var8 = 0; var8 < 4; ++var8)
            {
                this.a(var1, Block.COBBLESTONE_STAIRS.id, var5, 5, 0 - var8, 6 + var8, var3);
                this.a(var1, Block.COBBLESTONE_STAIRS.id, var5, 6, 0 - var8, 6 + var8, var3);
                this.a(var1, var3, 5, 0 - var8, 7 + var8, 6, 0 - var8, 9 + var8);
            }

            this.a(var1, var3, 1, -3, 12, 10, -1, 13);
            this.a(var1, var3, 1, -3, 1, 3, -1, 13);
            this.a(var1, var3, 1, -3, 1, 9, -1, 5);

            for (var8 = 1; var8 <= 13; var8 += 2)
            {
                this.a(var1, var3, 1, -3, var8, 1, -2, var8, false, var2, n);
            }

            for (var8 = 2; var8 <= 12; var8 += 2)
            {
                this.a(var1, var3, 1, -1, var8, 3, -1, var8, false, var2, n);
            }

            this.a(var1, var3, 2, -2, 1, 5, -2, 1, false, var2, n);
            this.a(var1, var3, 7, -2, 1, 9, -2, 1, false, var2, n);
            this.a(var1, var3, 6, -3, 1, 6, -3, 1, false, var2, n);
            this.a(var1, var3, 6, -1, 1, 6, -1, 1, false, var2, n);
            this.a(var1, Block.TRIPWIRE_SOURCE.id, this.c(Block.TRIPWIRE_SOURCE.id, 3) | 4, 1, -3, 8, var3);
            this.a(var1, Block.TRIPWIRE_SOURCE.id, this.c(Block.TRIPWIRE_SOURCE.id, 1) | 4, 4, -3, 8, var3);
            this.a(var1, Block.TRIPWIRE.id, 4, 2, -3, 8, var3);
            this.a(var1, Block.TRIPWIRE.id, 4, 3, -3, 8, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 7, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 6, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 5, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 4, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 3, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 2, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 5, -3, 1, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 4, -3, 1, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 3, -3, 1, var3);
            ChestGenHooks var11 = ChestGenHooks.getInfo("pyramidJungleDispenser");
            ChestGenHooks var10 = ChestGenHooks.getInfo("pyramidJungleChest");

            if (!this.field_74945_j)
            {
                this.field_74945_j = this.a(var1, var3, var2, 3, -2, 1, 2, var11.getItems(), var11.getCount(var2));
            }

            this.a(var1, Block.VINE.id, 15, 3, -2, 2, var3);
            this.a(var1, Block.TRIPWIRE_SOURCE.id, this.c(Block.TRIPWIRE_SOURCE.id, 2) | 4, 7, -3, 1, var3);
            this.a(var1, Block.TRIPWIRE_SOURCE.id, this.c(Block.TRIPWIRE_SOURCE.id, 0) | 4, 7, -3, 5, var3);
            this.a(var1, Block.TRIPWIRE.id, 4, 7, -3, 2, var3);
            this.a(var1, Block.TRIPWIRE.id, 4, 7, -3, 3, var3);
            this.a(var1, Block.TRIPWIRE.id, 4, 7, -3, 4, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 8, -3, 6, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 9, -3, 6, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 9, -3, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 9, -3, 4, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 9, -2, 4, var3);

            if (!this.field_74946_k)
            {
                this.field_74946_k = this.a(var1, var3, var2, 9, -2, 3, 4, var11.getItems(), var11.getCount(var2));
            }

            this.a(var1, Block.VINE.id, 15, 8, -1, 3, var3);
            this.a(var1, Block.VINE.id, 15, 8, -2, 3, var3);

            if (!this.field_74947_h)
            {
                this.field_74947_h = this.a(var1, var3, var2, 8, -3, 3, var10.getItems(), var10.getCount(var2));
            }

            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 9, -3, 2, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 8, -3, 1, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 4, -3, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 5, -2, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 5, -1, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 6, -3, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 7, -2, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 7, -1, 5, var3);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 8, -3, 5, var3);
            this.a(var1, var3, 9, -1, 1, 9, -1, 5, false, var2, n);
            this.a(var1, var3, 8, -3, 8, 10, -1, 10);
            this.a(var1, Block.SMOOTH_BRICK.id, 3, 8, -2, 11, var3);
            this.a(var1, Block.SMOOTH_BRICK.id, 3, 9, -2, 11, var3);
            this.a(var1, Block.SMOOTH_BRICK.id, 3, 10, -2, 11, var3);
            this.a(var1, Block.LEVER.id, BlockLever.d(this.c(Block.LEVER.id, 2)), 8, -2, 12, var3);
            this.a(var1, Block.LEVER.id, BlockLever.d(this.c(Block.LEVER.id, 2)), 9, -2, 12, var3);
            this.a(var1, Block.LEVER.id, BlockLever.d(this.c(Block.LEVER.id, 2)), 10, -2, 12, var3);
            this.a(var1, var3, 8, -3, 8, 8, -3, 10, false, var2, n);
            this.a(var1, var3, 10, -3, 8, 10, -3, 10, false, var2, n);
            this.a(var1, Block.MOSSY_COBBLESTONE.id, 0, 10, -2, 9, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 8, -2, 9, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 8, -2, 10, var3);
            this.a(var1, Block.REDSTONE_WIRE.id, 0, 10, -1, 9, var3);
            this.a(var1, Block.PISTON_STICKY.id, 1, 9, -2, 8, var3);
            this.a(var1, Block.PISTON_STICKY.id, this.c(Block.PISTON_STICKY.id, 4), 10, -2, 8, var3);
            this.a(var1, Block.PISTON_STICKY.id, this.c(Block.PISTON_STICKY.id, 4), 10, -1, 8, var3);
            this.a(var1, Block.DIODE_OFF.id, this.c(Block.DIODE_OFF.id, 2), 10, -2, 10, var3);

            if (!this.field_74948_i)
            {
                this.field_74948_i = this.a(var1, var3, var2, 9, -3, 10, var10.getItems(), var10.getCount(var2));
            }

            return true;
        }
    }
}
