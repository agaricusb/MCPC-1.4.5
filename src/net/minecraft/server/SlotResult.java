package net.minecraft.server;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class SlotResult extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final IInventory a;

    /** The player that is using the GUI where this slot resides. */
    private EntityHuman b;

    /**
     * The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset.
     */
    private int c;

    public SlotResult(EntityHuman var1, IInventory var2, IInventory var3, int var4, int var5, int var6)
    {
        super(var3, var4, var5, var6);
        this.b = var1;
        this.a = var2;
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
            this.c += Math.min(var1, this.getItem().count);
        }

        return super.a(var1);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void b(ItemStack var1, int var2)
    {
        this.c += var2;
        this.b(var1);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void b(ItemStack var1)
    {
        var1.a(this.b.world, this.b, this.c);
        this.c = 0;

        if (var1.id == Block.WORKBENCH.id)
        {
            this.b.a(AchievementList.h, 1);
        }
        else if (var1.id == Item.WOOD_PICKAXE.id)
        {
            this.b.a(AchievementList.i, 1);
        }
        else if (var1.id == Block.FURNACE.id)
        {
            this.b.a(AchievementList.j, 1);
        }
        else if (var1.id == Item.WOOD_HOE.id)
        {
            this.b.a(AchievementList.l, 1);
        }
        else if (var1.id == Item.BREAD.id)
        {
            this.b.a(AchievementList.m, 1);
        }
        else if (var1.id == Item.CAKE.id)
        {
            this.b.a(AchievementList.n, 1);
        }
        else if (var1.id == Item.STONE_PICKAXE.id)
        {
            this.b.a(AchievementList.o, 1);
        }
        else if (var1.id == Item.WOOD_SWORD.id)
        {
            this.b.a(AchievementList.r, 1);
        }
        else if (var1.id == Block.ENCHANTMENT_TABLE.id)
        {
            this.b.a(AchievementList.D, 1);
        }
        else if (var1.id == Block.BOOKSHELF.id)
        {
            this.b.a(AchievementList.F, 1);
        }
    }

    public void a(EntityHuman var1, ItemStack var2)
    {
        GameRegistry.onItemCrafted(var1, var2, this.a);
        this.b(var2);

        for (int var3 = 0; var3 < this.a.getSize(); ++var3)
        {
            ItemStack var4 = this.a.getItem(var3);

            if (var4 != null)
            {
                this.a.splitStack(var3, 1);

                if (var4.getItem().s())
                {
                    ItemStack var5 = var4.getItem().getContainerItemStack(var4);

                    if (var5.f() && var5.getData() > var5.k())
                    {
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(this.b, var5));
                        var5 = null;
                    }

                    if (var5 != null && (!var4.getItem().h(var4) || !this.b.inventory.pickup(var5)))
                    {
                        if (this.a.getItem(var3) == null)
                        {
                            this.a.setItem(var3, var5);
                        }
                        else
                        {
                            this.b.drop(var5);
                        }
                    }
                }
            }
        }
    }
}
