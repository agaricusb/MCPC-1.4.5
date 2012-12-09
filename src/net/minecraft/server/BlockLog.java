package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.List;
import java.util.Random;

public class BlockLog extends Block
{
    /** The type of tree this log came from. */
    public static final String[] a = new String[] {"oak", "spruce", "birch", "jungle"};

    protected BlockLog(int var1)
    {
        super(var1, Material.WOOD);
        this.textureId = 20;
        this.a(CreativeModeTab.b);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 31;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random var1)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int var1, Random var2, int var3)
    {
        return Block.LOG.id;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        byte var7 = 4;
        int var8 = var7 + 1;

        if (var1.d(var2 - var8, var3 - var8, var4 - var8, var2 + var8, var3 + var8, var4 + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        int var12 = var1.getTypeId(var2 + var9, var3 + var10, var4 + var11);

                        if (Block.byId[var12] != null)
                        {
                            Block.byId[var12].beginLeavesDecay(var1, var2 + var9, var3 + var10, var4 + var11);
                        }
                    }
                }
            }
        }
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public int getPlacedData(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var9) {
        int var10 = var9 & 3;
        byte var11 = 0;
        switch(var5) {
        case 0:
        case 1:
           var11 = 0;
           break;
        case 2:
        case 3:
           var11 = 8;
           break;
        case 4:
        case 5:
           var11 = 4;
        }

        return var10 | var11;
     }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int var1, int var2)
    {
        int var3 = var2 & 12;
        int var4 = var2 & 3;
        return var3 == 0 && (var1 == 1 || var1 == 0) ? 21 : (var3 == 4 && (var1 == 5 || var1 == 4) ? 21 : (var3 == 8 && (var1 == 2 || var1 == 3) ? 21 : (var4 == 1 ? 116 : (var4 == 2 ? 117 : (var4 == 3 ? 153 : 20)))));
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int var1)
    {
        return var1 & 3;
    }

    /**
     * returns a number between 0 and 3
     */
    public static int e(int var0)
    {
        return var0 & 3;
    }

    @SideOnly(Side.CLIENT)
    public void a(int var1, CreativeModeTab var2, List var3)
    {
        var3.add(new ItemStack(var1, 1, 0));
        var3.add(new ItemStack(var1, 1, 1));
        var3.add(new ItemStack(var1, 1, 2));
        var3.add(new ItemStack(var1, 1, 3));
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack f_(int var1)
    {
        return new ItemStack(this.id, 1, e(var1));
    }

    public boolean canSustainLeaves(World var1, int var2, int var3, int var4)
    {
        return true;
    }

    public boolean isWood(World var1, int var2, int var3, int var4)
    {
        return true;
    }
}
