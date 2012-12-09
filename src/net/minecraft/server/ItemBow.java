package net.minecraft.server;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemBow extends Item {

    public ItemBow(int i) {
        super(i);
        this.maxStackSize = 1;
        this.setMaxDurability(384);
        this.a(CreativeModeTab.j);
    }
    

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void a(ItemStack var1, World var2, EntityHuman var3, int var4)
    {
        int var5 = this.a(var1) - var4;
        ArrowLooseEvent var6 = new ArrowLooseEvent(var3, var1, var5);
        MinecraftForge.EVENT_BUS.post(var6);

        if (!var6.isCanceled())
        {
            var5 = var6.charge;
            boolean var7 = var3.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, var1) > 0;

            if (var7 || var3.inventory.e(Item.ARROW.id))
            {
                float var8 = (float)var5 / 20.0F;
                var8 = (var8 * var8 + var8 * 2.0F) / 3.0F;

                if ((double)var8 < 0.1D)
                {
                    return;
                }

                if (var8 > 1.0F)
                {
                    var8 = 1.0F;
                }

                EntityArrow var9 = new EntityArrow(var2, var3, var8 * 2.0F);

                if (var8 == 1.0F)
                {
                    var9.e(true);
                }

                int var10 = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, var1);

                if (var10 > 0)
                {
                    var9.b(var9.c() + (double)var10 * 0.5D + 0.5D);
                }

                int var11 = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, var1);

                if (var11 > 0)
                {
                    var9.a(var11);
                }

                if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, var1) > 0)
                {
                    var9.setOnFire(100);
                }
                
                // CraftBukkit start
                org.bukkit.event.entity.EntityShootBowEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callEntityShootBowEvent(var3, var1, var9, var8);
                if (event.isCancelled()) {
                    event.getProjectile().remove();
                    return;
                }

                if (event.getProjectile() == var9.getBukkitEntity()) {
                    var2.addEntity(var9);
                }
                // CraftBukkit end

                var1.damage(1, var3);
                var2.makeSound(var3, "random.bow", 1.0F, 1.0F / (d.nextFloat() * 0.4F + 1.2F) + var8 * 0.5F);

                if (var7)
                {
                    var9.fromPlayer = 2;
                }
                else
                {
                    var3.inventory.d(Item.ARROW.id);
                }
            }
        }
    }

    public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return itemstack;
    }

    public int a(ItemStack itemstack) {
        return 72000;
    }

    public EnumAnimation d_(ItemStack itemstack) {
        return EnumAnimation.e;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
    {
        ArrowNockEvent var4 = new ArrowNockEvent(var3, var1);
        MinecraftForge.EVENT_BUS.post(var4);

        if (var4.isCanceled())
        {
            return var4.result;
        }
        else
        {
            if (var3.abilities.canInstantlyBuild || var3.inventory.e(Item.ARROW.id))
            {
                var3.a(var1, this.a(var1));
            }

            return var1;
        }
    }

    public int c() {
        return 1;
    }
}
