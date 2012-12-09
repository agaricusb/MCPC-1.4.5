package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Iterator;
import java.util.Random;
import net.minecraftforge.common.ForgeDirection;

public class BlockChest extends BlockContainer
{
    private Random a = new Random();

    protected BlockChest(int var1)
    {
        super(var1, Material.WOOD);
        this.textureId = 26;
        this.a(CreativeModeTab.c);
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
        return 22;
    }

   public void updateShape(IBlockAccess var1, int var2, int var3, int var4) {
      if(var1.getTypeId(var2, var3, var4 - 1) == this.id) {
         this.a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
      } else if(var1.getTypeId(var2, var3, var4 + 1) == this.id) {
         this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
      } else if(var1.getTypeId(var2 - 1, var3, var4) == this.id) {
         this.a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      } else if(var1.getTypeId(var2 + 1, var3, var4) == this.id) {
         this.a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
      } else {
         this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      }

   }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World var1, int var2, int var3, int var4)
    {
        super.onPlace(var1, var2, var3, var4);
        this.d_(var1, var2, var3, var4);
        int var5 = var1.getTypeId(var2, var3, var4 - 1);
        int var6 = var1.getTypeId(var2, var3, var4 + 1);
        int var7 = var1.getTypeId(var2 - 1, var3, var4);
        int var8 = var1.getTypeId(var2 + 1, var3, var4);

        if (var5 == this.id)
        {
            this.d_(var1, var2, var3, var4 - 1);
        }

        if (var6 == this.id)
        {
            this.d_(var1, var2, var3, var4 + 1);
        }

        if (var7 == this.id)
        {
            this.d_(var1, var2 - 1, var3, var4);
        }

        if (var8 == this.id)
        {
            this.d_(var1, var2 + 1, var3, var4);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = var1.getTypeId(var2, var3, var4 - 1);
        int var7 = var1.getTypeId(var2, var3, var4 + 1);
        int var8 = var1.getTypeId(var2 - 1, var3, var4);
        int var9 = var1.getTypeId(var2 + 1, var3, var4);
        byte var10 = 0;
        int var11 = MathHelper.floor((double)(var5.yaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var11 == 0)
        {
            var10 = 2;
        }

        if (var11 == 1)
        {
            var10 = 5;
        }

        if (var11 == 2)
        {
            var10 = 3;
        }

        if (var11 == 3)
        {
            var10 = 4;
        }

        if (var6 != this.id && var7 != this.id && var8 != this.id && var9 != this.id)
        {
            var1.setData(var2, var3, var4, var10);
        }
        else
        {
            if ((var6 == this.id || var7 == this.id) && (var10 == 4 || var10 == 5))
            {
                if (var6 == this.id)
                {
                    var1.setData(var2, var3, var4 - 1, var10);
                }
                else
                {
                    var1.setData(var2, var3, var4 + 1, var10);
                }

                var1.setData(var2, var3, var4, var10);
            }

            if ((var8 == this.id || var9 == this.id) && (var10 == 2 || var10 == 3))
            {
                if (var8 == this.id)
                {
                    var1.setData(var2 - 1, var3, var4, var10);
                }
                else
                {
                    var1.setData(var2 + 1, var3, var4, var10);
                }

                var1.setData(var2, var3, var4, var10);
            }
        }
    }

    /**
     * Turns the adjacent chests to a double chest.
     */
    public void d_(World var1, int var2, int var3, int var4)
    {
        if (!var1.isStatic)
        {
            int var5 = var1.getTypeId(var2, var3, var4 - 1);
            int var6 = var1.getTypeId(var2, var3, var4 + 1);
            int var7 = var1.getTypeId(var2 - 1, var3, var4);
            int var8 = var1.getTypeId(var2 + 1, var3, var4);
            boolean var9 = true;
            int var10;
            int var11;
            boolean var12;
            byte var13;
            int var14;

            if (var5 != this.id && var6 != this.id)
            {
                if (var7 != this.id && var8 != this.id)
                {
                    var13 = 3;

                    if (Block.q[var5] && !Block.q[var6])
                    {
                        var13 = 3;
                    }

                    if (Block.q[var6] && !Block.q[var5])
                    {
                        var13 = 2;
                    }

                    if (Block.q[var7] && !Block.q[var8])
                    {
                        var13 = 5;
                    }

                    if (Block.q[var8] && !Block.q[var7])
                    {
                        var13 = 4;
                    }
                }
                else
                {
                    var10 = var1.getTypeId(var7 == this.id ? var2 - 1 : var2 + 1, var3, var4 - 1);
                    var11 = var1.getTypeId(var7 == this.id ? var2 - 1 : var2 + 1, var3, var4 + 1);
                    var13 = 3;
                    var12 = true;

                    if (var7 == this.id)
                    {
                        var14 = var1.getData(var2 - 1, var3, var4);
                    }
                    else
                    {
                        var14 = var1.getData(var2 + 1, var3, var4);
                    }

                    if (var14 == 2)
                    {
                        var13 = 2;
                    }

                    if ((Block.q[var5] || Block.q[var10]) && !Block.q[var6] && !Block.q[var11])
                    {
                        var13 = 3;
                    }

                    if ((Block.q[var6] || Block.q[var11]) && !Block.q[var5] && !Block.q[var10])
                    {
                        var13 = 2;
                    }
                }
            }
            else
            {
                var10 = var1.getTypeId(var2 - 1, var3, var5 == this.id ? var4 - 1 : var4 + 1);
                var11 = var1.getTypeId(var2 + 1, var3, var5 == this.id ? var4 - 1 : var4 + 1);
                var13 = 5;
                var12 = true;

                if (var5 == this.id)
                {
                    var14 = var1.getData(var2, var3, var4 - 1);
                }
                else
                {
                    var14 = var1.getData(var2, var3, var4 + 1);
                }

                if (var14 == 4)
                {
                    var13 = 4;
                }

                if ((Block.q[var7] || Block.q[var10]) && !Block.q[var8] && !Block.q[var11])
                {
                    var13 = 5;
                }

                if ((Block.q[var8] || Block.q[var11]) && !Block.q[var7] && !Block.q[var10])
                {
                    var13 = 4;
                }
            }

            var1.setData(var2, var3, var4, var13);
        }
    }

    @SideOnly(Side.CLIENT)
    public int d(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return 4;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int var1)
    {
        return 4;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        if (var1.getTypeId(var2 - 1, var3, var4) == this.id)
        {
            ++var5;
        }

        if (var1.getTypeId(var2 + 1, var3, var4) == this.id)
        {
            ++var5;
        }

        if (var1.getTypeId(var2, var3, var4 - 1) == this.id)
        {
            ++var5;
        }

        if (var1.getTypeId(var2, var3, var4 + 1) == this.id)
        {
            ++var5;
        }

        return var5 > 1 ? false : (this.l(var1, var2 - 1, var3, var4) ? false : (this.l(var1, var2 + 1, var3, var4) ? false : (this.l(var1, var2, var3, var4 - 1) ? false : !this.l(var1, var2, var3, var4 + 1))));
    }

    /**
     * Checks the neighbor blocks to see if there is a chest there. Args: world, x, y, z
     */
    private boolean l(World var1, int var2, int var3, int var4)
    {
        return var1.getTypeId(var2, var3, var4) != this.id ? false : (var1.getTypeId(var2 - 1, var3, var4) == this.id ? true : (var1.getTypeId(var2 + 1, var3, var4) == this.id ? true : (var1.getTypeId(var2, var3, var4 - 1) == this.id ? true : var1.getTypeId(var2, var3, var4 + 1) == this.id)));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        super.doPhysics(var1, var2, var3, var4, var5);
        TileEntityChest var6 = (TileEntityChest)var1.getTileEntity(var2, var3, var4);

        if (var6 != null)
        {
            var6.h();
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        TileEntityChest var7 = (TileEntityChest)var1.getTileEntity(var2, var3, var4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSize(); ++var8)
            {
                ItemStack var9 = var7.getItem(var8);

                if (var9 != null)
                {
                    float var10 = this.a.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.a.nextFloat() * 0.8F + 0.1F;
                    EntityItem var12;

                    for (float var13 = this.a.nextFloat() * 0.8F + 0.1F; var9.count > 0; var1.addEntity(var12))
                    {
                        int var14 = this.a.nextInt(21) + 10;

                        if (var14 > var9.count)
                        {
                            var14 = var9.count;
                        }

                        var9.count -= var14;
                        var12 = new EntityItem(var1, (double)((float)var2 + var10), (double)((float)var3 + var11), (double)((float)var4 + var13), new ItemStack(var9.id, var14, var9.getData()));
                        float var15 = 0.05F;
                        var12.motX = (double)((float)this.a.nextGaussian() * var15);
                        var12.motY = (double)((float)this.a.nextGaussian() * var15 + 0.2F);
                        var12.motZ = (double)((float)this.a.nextGaussian() * var15);

                        if (var9.hasTag())
                        {
                            var12.itemStack.setTag((NBTTagCompound)var9.getTag().clone());
                        }
                    }
                }
            }
        }

        super.remove(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5, int var6, float var7, float var8, float var9)
    {
        Object var10 = (TileEntityChest)var1.getTileEntity(var2, var3, var4);

        if (var10 == null)
        {
            return true;
        }
        else if (var1.isBlockSolidOnSide(var2, var3 + 1, var4, ForgeDirection.DOWN))
        {
            return true;
        }
        else if (n(var1, var2, var3, var4))
        {
            return true;
        }
        else if (var1.getTypeId(var2 - 1, var3, var4) == this.id && (var1.isBlockSolidOnSide(var2 - 1, var3 + 1, var4, ForgeDirection.DOWN) || n(var1, var2 - 1, var3, var4)))
        {
            return true;
        }
        else if (var1.getTypeId(var2 + 1, var3, var4) == this.id && (var1.isBlockSolidOnSide(var2 + 1, var3 + 1, var4, ForgeDirection.DOWN) || n(var1, var2 + 1, var3, var4)))
        {
            return true;
        }
        else if (var1.getTypeId(var2, var3, var4 - 1) == this.id && (var1.isBlockSolidOnSide(var2, var3 + 1, var4 - 1, ForgeDirection.DOWN) || n(var1, var2, var3, var4 - 1)))
        {
            return true;
        }
        else if (var1.getTypeId(var2, var3, var4 + 1) == this.id && (var1.isBlockSolidOnSide(var2, var3 + 1, var4 + 1, ForgeDirection.DOWN) || n(var1, var2, var3, var4 + 1)))
        {
            return true;
        }
        else
        {
            if (var1.getTypeId(var2 - 1, var3, var4) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)var1.getTileEntity(var2 - 1, var3, var4), (IInventory)var10);
            }

            if (var1.getTypeId(var2 + 1, var3, var4) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (IInventory)var10, (TileEntityChest)var1.getTileEntity(var2 + 1, var3, var4));
            }

            if (var1.getTypeId(var2, var3, var4 - 1) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)var1.getTileEntity(var2, var3, var4 - 1), (IInventory)var10);
            }

            if (var1.getTypeId(var2, var3, var4 + 1) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (IInventory)var10, (TileEntityChest)var1.getTileEntity(var2, var3, var4 + 1));
            }

            if (var1.isStatic)
            {
                return true;
            }
            else
            {
                var5.openContainer((IInventory)var10);
                return true;
            }
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World var1)
    {
        return new TileEntityChest();
    }

    /**
     * Looks for a sitting ocelot within certain bounds. Such an ocelot is considered to be blocking access to the
     * chest.
     */
    private static boolean n(World var0, int var1, int var2, int var3)
    {
        Iterator var4 = var0.a(EntityOcelot.class, AxisAlignedBB.a().a((double)var1, (double)(var2 + 1), (double)var3, (double)(var1 + 1), (double)(var2 + 2), (double)(var3 + 1))).iterator();

        while (var4.hasNext())
        {
            EntityOcelot var6 = (EntityOcelot)var4.next();

            if (var6.isSitting())
            {
                return true;
            }
        }

        return false;
    }
}
