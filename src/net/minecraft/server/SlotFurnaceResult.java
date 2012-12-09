package net.minecraft.server;

import cpw.mods.fml.common.registry.GameRegistry;

public class SlotFurnaceResult extends Slot
{
    /** The player that is using the GUI where this slot resides. */
    private EntityHuman a;
    private int field_75228_b;

    public SlotFurnaceResult(EntityHuman var1, IInventory var2, int var3, int var4, int var5)
    {
        super(var2, var3, var4, var5);
        this.a = var1;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack var1)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack a(int var1)
    {
        if (this.d())
        {
            this.field_75228_b += Math.min(var1, this.getItem().count);
        }

        return super.a(var1);
    }

    public void a(EntityHuman var1, ItemStack var2)
    {
        this.b(var2);
        super.a(var1, var2);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void b(ItemStack var1, int var2)
    {
        this.field_75228_b += var2;
        this.b(var1);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void b(ItemStack var1)
    {
        var1.a(this.a.world, this.a, this.field_75228_b);

        if (!this.a.world.isStatic)
        {
            int var2 = this.field_75228_b;
            float var3 = RecipesFurnace.getInstance().c(var1.id);
            int var4;

            if (var3 == 0.0F)
            {
                var2 = 0;
            }
            else if (var3 < 1.0F)
            {
                var4 = MathHelper.d((float)var2 * var3);

                if (var4 < MathHelper.f((float)var2 * var3) && (float)Math.random() < (float)var2 * var3 - (float)var4)
                {
                    ++var4;
                }

                var2 = var4;
            }

            while (var2 > 0)
            {
                var4 = EntityExperienceOrb.getOrbValue(var2);
                var2 -= var4;
                this.a.world.addEntity(new EntityExperienceOrb(this.a.world, this.a.locX, this.a.locY + 0.5D, this.a.locZ + 0.5D, var4));
            }
        }

        this.field_75228_b = 0;
        GameRegistry.onItemSmelted(this.a, var1);

        if (var1.id == Item.IRON_INGOT.id)
        {
            this.a.a(AchievementList.k, 1);
        }

        if (var1.id == Item.COOKED_FISH.id)
        {
            this.a.a(AchievementList.p, 1);
        }
    }
}
