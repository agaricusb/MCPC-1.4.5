package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnchantmentManager
{
    /** Is the random seed of enchantment effects. */
    private static final Random random = new Random();

    /**
     * Used to calculate the extra armor of enchantments on armors equipped on player.
     */
    private static final EnchantmentModifierProtection b = new EnchantmentModifierProtection((EmptyClass)null);

    /**
     * Used to calculate the (magic) extra damage done by enchantments on current equipped item of player.
     */
    private static final EnchantmentModifierDamage c = new EnchantmentModifierDamage((EmptyClass)null);

    /**
     * Returns the level of enchantment on the ItemStack passed.
     */
    public static int getEnchantmentLevel(int var0, ItemStack var1)
    {
        if (var1 == null)
        {
            return 0;
        }
        else
        {
            NBTTagList var2 = var1.getEnchantments();

            if (var2 == null)
            {
                return 0;
            }
            else
            {
                for (int var3 = 0; var3 < var2.size(); ++var3)
                {
                    short var4 = ((NBTTagCompound)var2.get(var3)).getShort("id");
                    short var5 = ((NBTTagCompound)var2.get(var3)).getShort("lvl");

                    if (var4 == var0)
                    {
                        return var5;
                    }
                }

                return 0;
            }
        }
    }

    /**
     * Return the enchantments for the specified stack.
     */
    public static Map a(ItemStack var0)
    {
        LinkedHashMap var1 = new LinkedHashMap();
        NBTTagList var2 = var0.getEnchantments();

        if (var2 != null)
        {
            for (int var3 = 0; var3 < var2.size(); ++var3)
            {
                short var4 = ((NBTTagCompound)var2.get(var3)).getShort("id");
                short var5 = ((NBTTagCompound)var2.get(var3)).getShort("lvl");
                var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
            }
        }

        return var1;
    }

    /**
     * Set the enchantments for the specified stack.
     */
    public static void a(Map var0, ItemStack var1)
    {
        NBTTagList var2 = new NBTTagList();
        Iterator var3 = var0.keySet().iterator();

        while (var3.hasNext())
        {
            int var4 = ((Integer)var3.next()).intValue();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setShort("id", (short)var4);
            var5.setShort("lvl", (short)((Integer)var0.get(Integer.valueOf(var4))).intValue());
            var2.add(var5);
        }

        if (var2.size() > 0)
        {
            var1.a("ench", var2);
        }
        else if (var1.hasTag())
        {
            var1.getTag().o("ench");
        }
    }

    /**
     * Returns the biggest level of the enchantment on the array of ItemStack passed.
     */
    private static int getEnchantmentLevel(int var0, ItemStack[] var1)
    {
        int var2 = 0;
        ItemStack[] var3 = var1;
        int var4 = var1.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            ItemStack var6 = var3[var5];
            int var7 = getEnchantmentLevel(var0, var6);

            if (var7 > var2)
            {
                var2 = var7;
            }
        }

        return var2;
    }

    /**
     * Executes the enchantment modifier on the ItemStack passed.
     */
    private static void a(EnchantmentModifier var0, ItemStack var1)
    {
        if (var1 != null)
        {
            NBTTagList var2 = var1.getEnchantments();

            if (var2 != null)
            {
                for (int var3 = 0; var3 < var2.size(); ++var3)
                {
                    short var4 = ((NBTTagCompound)var2.get(var3)).getShort("id");
                    short var5 = ((NBTTagCompound)var2.get(var3)).getShort("lvl");

                    if (Enchantment.byId[var4] != null)
                    {
                        var0.a(Enchantment.byId[var4], var5);
                    }
                }
            }
        }
    }

    /**
     * Executes the enchantment modifier on the array of ItemStack passed.
     */
    private static void a(EnchantmentModifier var0, ItemStack[] var1)
    {
        ItemStack[] var2 = var1;
        int var3 = var1.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ItemStack var5 = var2[var4];
            a(var0, var5);
        }
    }

    /**
     * Returns the modifier of protection enchantments on armors equipped on player.
     */
    public static int a(ItemStack[] var0, DamageSource var1)
    {
        b.a = 0;
        b.b = var1;
        a(b, var0);

        if (b.a > 25)
        {
            b.a = 25;
        }

        return (b.a + 1 >> 1) + random.nextInt((b.a >> 1) + 1);
    }

    /**
     * Return the (magic) extra damage of the enchantments on player equipped item.
     */
    public static int a(EntityLiving var0, EntityLiving var1)
    {
        c.a = 0;
        c.b = var1;
        a(c, var0.bD());
        return c.a > 0 ? 1 + random.nextInt(c.a) : 0;
    }

    /**
     * Returns the knockback value of enchantments on equipped player item.
     */
    public static int getKnockbackEnchantmentLevel(EntityLiving var0, EntityLiving var1)
    {
        return getEnchantmentLevel(Enchantment.KNOCKBACK.id, var0.bD());
    }

    /**
     * Return the fire aspect value of enchantments on equipped player item.
     */
    public static int getFireAspectEnchantmentLevel(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.FIRE_ASPECT.id, var0.bD());
    }

    /**
     * Returns the 'Water Breathing' modifier of enchantments on player equipped armors.
     */
    public static int getOxygenEnchantmentLevel(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.OXYGEN.id, var0.getEquipment());
    }

    /**
     * Return the extra efficiency of tools based on enchantments on equipped player item.
     */
    public static int getDigSpeedEnchantmentLevel(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.DIG_SPEED.id, var0.bD());
    }

    /**
     * Returns the unbreaking enchantment modifier on current equipped item of player.
     */
    public static int getDurabilityEnchantmentLevel(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.DURABILITY.id, var0.bD());
    }

    /**
     * Returns the silk touch status of enchantments on current equipped item of player.
     */
    public static boolean hasSilkTouchEnchantment(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.SILK_TOUCH.id, var0.bD()) > 0;
    }

    /**
     * Returns the fortune enchantment modifier of the current equipped item of player.
     */
    public static int getBonusBlockLootEnchantmentLevel(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, var0.bD());
    }

    /**
     * Returns the looting enchantment modifier of the current equipped item of player.
     */
    public static int getBonusMonsterLootEnchantmentLevel(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS.id, var0.bD());
    }

    /**
     * Returns the aqua affinity status of enchantments on current equipped item of player.
     */
    public static boolean hasWaterWorkerEnchantment(EntityLiving var0)
    {
        return getEnchantmentLevel(Enchantment.WATER_WORKER.id, var0.getEquipment()) > 0;
    }

    /**
     * Returns the enchantability of itemstack, it's uses a singular formula for each index (2nd parameter: 0, 1 and 2),
     * cutting to the max enchantability power of the table (3rd parameter)
     */
    public static int a(Random var0, int var1, int var2, ItemStack var3)
    {
        Item var4 = var3.getItem();
        int var5 = var4.c();

        if (var5 <= 0)
        {
            return 0;
        }
        else
        {
            if (var2 > 15)
            {
                var2 = 15;
            }

            int var6 = var0.nextInt(8) + 1 + (var2 >> 1) + var0.nextInt(var2 + 1);
            return var1 == 0 ? Math.max(var6 / 3, 1) : (var1 == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, var2 * 2));
        }
    }

    /**
     * Adds a random enchantment to the specified item. Args: random, itemStack, enchantabilityLevel
     */
    public static ItemStack a(Random var0, ItemStack var1, int var2)
    {
        List var3 = b(var0, var1, var2);

        if (var3 != null)
        {
            Iterator var4 = var3.iterator();

            while (var4.hasNext())
            {
                EnchantmentInstance var5 = (EnchantmentInstance)var4.next();
                var1.addEnchantment(var5.enchantment, var5.level);
            }
        }

        return var1;
    }

    /**
     * Create a list of random EnchantmentData (enchantments) that can be added together to the ItemStack, the 3rd
     * parameter is the total enchantability level.
     */
    public static List b(Random var0, ItemStack var1, int var2)
    {
        Item var3 = var1.getItem();
        int var4 = var3.c();

        if (var4 <= 0)
        {
            return null;
        }
        else
        {
            var4 /= 2;
            var4 = 1 + var0.nextInt((var4 >> 1) + 1) + var0.nextInt((var4 >> 1) + 1);
            int var5 = var4 + var2;
            float var6 = (var0.nextFloat() + var0.nextFloat() - 1.0F) * 0.15F;
            int var7 = (int)((float)var5 * (1.0F + var6) + 0.5F);

            if (var7 < 1)
            {
                var7 = 1;
            }

            ArrayList var8 = null;
            Map var9 = b(var7, var1);

            if (var9 != null && !var9.isEmpty())
            {
                EnchantmentInstance var10 = (EnchantmentInstance)WeightedRandom.a(var0, var9.values());

                if (var10 != null)
                {
                    var8 = new ArrayList();
                    var8.add(var10);

                    for (int var11 = var7; var0.nextInt(50) <= var11; var11 >>= 1)
                    {
                        Iterator var12 = var9.keySet().iterator();

                        while (var12.hasNext())
                        {
                            Integer var13 = (Integer)var12.next();
                            boolean var14 = true;
                            Iterator var15 = var8.iterator();

                            while (true)
                            {
                                if (var15.hasNext())
                                {
                                    EnchantmentInstance var16 = (EnchantmentInstance)var15.next();

                                    if (var16.enchantment.a(Enchantment.byId[var13.intValue()]))
                                    {
                                        continue;
                                    }

                                    var14 = false;
                                }

                                if (!var14)
                                {
                                    var12.remove();
                                }

                                break;
                            }
                        }

                        if (!var9.isEmpty())
                        {
                            EnchantmentInstance var17 = (EnchantmentInstance)WeightedRandom.a(var0, var9.values());
                            var8.add(var17);
                        }
                    }
                }
            }

            return var8;
        }
    }

    /**
     * Creates a 'Map' of EnchantmentData (enchantments) possible to add on the ItemStack and the enchantability level
     * passed.
     */
    public static Map b(int var0, ItemStack var1)
    {
        Item var2 = var1.getItem();
        HashMap var3 = null;
        Enchantment[] var4 = Enchantment.byId;
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Enchantment var7 = var4[var6];

            if (var7 != null && var7.canEnchantItem(var1))
            {
                for (int var8 = var7.getStartLevel(); var8 <= var7.getMaxLevel(); ++var8)
                {
                    if (var0 >= var7.a(var8) && var0 <= var7.b(var8))
                    {
                        if (var3 == null)
                        {
                            var3 = new HashMap();
                        }

                        var3.put(Integer.valueOf(var7.id), new EnchantmentInstance(var7, var8));
                    }
                }
            }
        }

        return var3;
    }
}
