package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraftforge.common.IShearable;

public class ItemShears extends Item
{
    public ItemShears(int var1)
    {
        super(var1);
        this.d(1);
        this.setMaxDurability(238);
        this.a(CreativeModeTab.i);
    }

   public boolean a(ItemStack var1, World var2, int var3, int var4, int var5, int var6, EntityLiving var7) {
      if(var3 != Block.LEAVES.id && var3 != Block.WEB.id && var3 != Block.LONG_GRASS.id && var3 != Block.VINE.id && var3 != Block.TRIPWIRE.id) {
         return super.a(var1, var2, var3, var4, var5, var6, var7);
      } else {
         var1.damage(1, var7);
         return true;
      }
   }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canDestroySpecialBlock(Block var1)
    {
        return var1.id == Block.WEB.id || var1.id == Block.REDSTONE_WIRE.id || var1.id == Block.TRIPWIRE.id;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack var1, Block var2)
    {
        return var2.id != Block.WEB.id && var2.id != Block.LEAVES.id ? (var2.id == Block.WOOL.id ? 5.0F : super.getDestroySpeed(var1, var2)) : 15.0F;
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public boolean a(ItemStack var1, EntityLiving var2)
    {
        if (var2.world.isStatic)
        {
            return false;
        }
        else if (!(var2 instanceof IShearable))
        {
            return false;
        }
        else
        {
            IShearable var3 = (IShearable)var2;

            if (var3.isShearable(var1, var2.world, (int)var2.locX, (int)var2.locY, (int)var2.locZ))
            {
                ArrayList var4 = var3.onSheared(var1, var2.world, (int)var2.locX, (int)var2.locY, (int)var2.locZ, EnchantmentManager.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, var1));
                EntityItem var7;

                for (Iterator var5 = var4.iterator(); var5.hasNext(); var7.motZ += (double)((var2.random.nextFloat() - var2.random.nextFloat()) * 0.1F))
                {
                    ItemStack var6 = (ItemStack)var5.next();
                    var7 = var2.a(var6, 1.0F);
                    var7.motY += (double)(var2.random.nextFloat() * 0.05F);
                    var7.motX += (double)((var2.random.nextFloat() - var2.random.nextFloat()) * 0.1F);
                }

                var1.damage(1, var2);
            }

            return true;
        }
    }

    public boolean onBlockStartBreak(ItemStack var1, int var2, int var3, int var4, EntityHuman var5)
    {
        if (var5.world.isStatic)
        {
            return false;
        }
        else
        {
            int var6 = var5.world.getTypeId(var2, var3, var4);

            if (Block.byId[var6] instanceof IShearable)
            {
                IShearable var7 = (IShearable)Block.byId[var6];

                if (var7.isShearable(var1, var5.world, var2, var3, var4))
                {
                    ArrayList var8 = var7.onSheared(var1, var5.world, var2, var3, var4, EnchantmentManager.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, var1));
                    Iterator var9 = var8.iterator();

                    while (var9.hasNext())
                    {
                        ItemStack var10 = (ItemStack)var9.next();
                        float var11 = 0.7F;
                        double var12 = (double)(var5.random.nextFloat() * var11) + (double)(1.0F - var11) * 0.5D;
                        double var14 = (double)(var5.random.nextFloat() * var11) + (double)(1.0F - var11) * 0.5D;
                        double var16 = (double)(var5.random.nextFloat() * var11) + (double)(1.0F - var11) * 0.5D;
                        EntityItem var18 = new EntityItem(var5.world, (double)var2 + var12, (double)var3 + var14, (double)var4 + var16, var10);
                        var18.pickupDelay = 10;
                        var5.world.addEntity(var18);
                    }

                    var1.damage(1, var5);
                    var5.a(StatisticList.C[var6], 1);
                }
            }

            return false;
        }
    }
}
