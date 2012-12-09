package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Random;
import net.minecraftforge.common.ForgeDirection;

public class BlockTorch extends Block
{
    protected BlockTorch(int var1, int var2)
    {
        super(var1, var2, Material.ORIENTABLE);
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 2;
    }

    /**
     * Gets if we can place a torch on a block.
     */
    private boolean l(World var1, int var2, int var3, int var4)
    {
        if (var1.t(var2, var3, var4))
        {
            return true;
        }
        else
        {
            int var5 = var1.getTypeId(var2, var3, var4);
            return Block.byId[var5] != null && Block.byId[var5].canPlaceTorchOnTop(var1, var2, var3, var4);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST, true) || var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST, true) || var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH, true) || var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH, true) || this.l(var1, var2, var3 - 1, var4);
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void postPlace(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        int var9 = var1.getData(var2, var3, var4);

        if (var5 == 1 && this.l(var1, var2, var3 - 1, var4))
        {
            var9 = 5;
        }

        if (var5 == 2 && var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH, true))
        {
            var9 = 4;
        }

        if (var5 == 3 && var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH, true))
        {
            var9 = 3;
        }

        if (var5 == 4 && var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST, true))
        {
            var9 = 2;
        }

        if (var5 == 5 && var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST, true))
        {
            var9 = 1;
        }

        var1.setData(var2, var3, var4, var9);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World var1, int var2, int var3, int var4, Random var5)
    {
        super.b(var1, var2, var3, var4, var5);

        if (var1.getData(var2, var3, var4) == 0)
        {
            this.onPlace(var1, var2, var3, var4);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World var1, int var2, int var3, int var4)
    {
        if (var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST, true))
        {
            var1.setData(var2, var3, var4, 1);
        }
        else if (var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST, true))
        {
            var1.setData(var2, var3, var4, 2);
        }
        else if (var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH, true))
        {
            var1.setData(var2, var3, var4, 3);
        }
        else if (var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH, true))
        {
            var1.setData(var2, var3, var4, 4);
        }
        else if (this.l(var1, var2, var3 - 1, var4))
        {
            var1.setData(var2, var3, var4, 5);
        }

        this.n(var1, var2, var3, var4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (this.n(var1, var2, var3, var4))
        {
            int var6 = var1.getData(var2, var3, var4);
            boolean var7 = false;

            if (!var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST, true) && var6 == 1)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST, true) && var6 == 2)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH, true) && var6 == 3)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH, true) && var6 == 4)
            {
                var7 = true;
            }

            if (!this.l(var1, var2, var3 - 1, var4) && var6 == 5)
            {
                var7 = true;
            }

            if (var7)
            {
                this.c(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
                var1.setTypeId(var2, var3, var4, 0);
            }
        }
    }

    /**
     * Tests if the block can remain at its current location and will drop as an item if it is unable to stay. Returns
     * True if it can stay and False if it drops. Args: world, x, y, z
     */
    private boolean n(World var1, int var2, int var3, int var4)
    {
        if (!this.canPlace(var1, var2, var3, var4))
        {
            if (var1.getTypeId(var2, var3, var4) == this.id)
            {
                this.c(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
                var1.setTypeId(var2, var3, var4, 0);
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
    {
        int var7 = var1.getData(var2, var3, var4) & 7;
        float var8 = 0.15F;

        if (var7 == 1)
        {
            this.a(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 2)
        {
            this.a(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 3)
        {
            this.a(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
        }
        else if (var7 == 4)
        {
            this.a(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
        }
        else
        {
            var8 = 0.1F;
            this.a(0.5F - var8, 0.0F, 0.5F - var8, 0.5F + var8, 0.6F, 0.5F + var8);
        }

        return super.a(var1, var2, var3, var4, var5, var6);
    }

    @SideOnly(Side.CLIENT)
    public void a(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = var1.getData(var2, var3, var4);
        double var7 = (double)((float)var2 + 0.5F);
        double var9 = (double)((float)var3 + 0.7F);
        double var11 = (double)((float)var4 + 0.5F);
        double var13 = 0.2199999988079071D;
        double var15 = 0.27000001072883606D;

        if (var6 == 1)
        {
            var1.addParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
            var1.addParticle("flame", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 2)
        {
            var1.addParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
            var1.addParticle("flame", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 3)
        {
            var1.addParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
            var1.addParticle("flame", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 4)
        {
            var1.addParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
            var1.addParticle("flame", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            var1.addParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
            var1.addParticle("flame", var7, var9, var11, 0.0D, 0.0D, 0.0D);
        }
    }
}
