package net.minecraft.server;

public class ItemMilkBucket extends Item
{
    public ItemMilkBucket(int var1)
    {
        super(var1);
        this.d(1);
        this.a(CreativeModeTab.f);
    }

    public ItemStack b(ItemStack var1, World var2, EntityHuman var3)
    {
        if (!var3.abilities.canInstantlyBuild)
        {
            --var1.count;
        }

        if (!var2.isStatic)
        {
            var3.curePotionEffects(var1);
        }

        return var1.count <= 0 ? new ItemStack(Item.BUCKET) : var1;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int a(ItemStack var1)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAnimation d_(ItemStack var1)
    {
        return EnumAnimation.c;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
    {
        var3.a(var1, this.a(var1));
        return var1;
    }
}
