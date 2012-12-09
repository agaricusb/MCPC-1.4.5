package net.minecraft.server;

import java.util.Random;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockFlower extends Block implements IPlantable
{
    protected BlockFlower(int var1, int var2, Material var3)
    {
        super(var1, var3);
        this.textureId = var2;
        this.b(true);
        float var4 = 0.2F;
        this.a(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
        this.a(CreativeModeTab.c);
    }

    protected BlockFlower(int var1, int var2)
    {
        this(var1, var2, Material.PLANT);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return super.canPlace(var1, var2, var3, var4) && this.d(var1, var2, var3, var4);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int var1)
    {
        return var1 == Block.GRASS.id || var1 == Block.DIRT.id || var1 == Block.SOIL.id;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        super.doPhysics(var1, var2, var3, var4, var5);
        this.c(var1, var2, var3, var4);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World var1, int var2, int var3, int var4, Random var5)
    {
        this.c(var1, var2, var3, var4);
    }

    protected final void c(World var1, int var2, int var3, int var4)
    {
        if (!this.d(var1, var2, var3, var4))
        {
            this.c(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
            var1.setTypeId(var2, var3, var4, 0);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World var1, int var2, int var3, int var4)
    {
        Block var5 = byId[var1.getTypeId(var2, var3 - 1, var4)];
        return ((var1.l(var2, var3, var4) >= 8) || var1.k(var2, var3, var4)) && var5 != null && var5.canSustainPlant(var1, var2, var3 - 1, var4, ForgeDirection.UP, this);
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
        return 1;
    }

    public EnumPlantType getPlantType(World var1, int var2, int var3, int var4)
    {
        return this.id == CROPS.id ? EnumPlantType.Crop : (this.id == DEAD_BUSH.id ? EnumPlantType.Desert : (this.id == WATER_LILY.id ? EnumPlantType.Water : (this.id == RED_MUSHROOM.id ? EnumPlantType.Cave : (this.id == BROWN_MUSHROOM.id ? EnumPlantType.Cave : (this.id == NETHER_WART.id ? EnumPlantType.Nether : (this.id == SAPLING.id ? EnumPlantType.Plains : (this.id == MELON_STEM.id ? EnumPlantType.Crop : (this.id == PUMPKIN_STEM.id ? EnumPlantType.Crop : (this.id == LONG_GRASS.id ? EnumPlantType.Plains : EnumPlantType.Plains)))))))));
    }

    public int getPlantID(World var1, int var2, int var3, int var4)
    {
        return this.id;
    }

    public int getPlantMetadata(World var1, int var2, int var3, int var4)
    {
        return var1.getData(var2, var3, var4);
    }
}
