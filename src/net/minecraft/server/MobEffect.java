package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MobEffect
{
    /** ID value of the potion this effect matches. */
    private int effectId;

    /** The duration of the potion effect */
    private int duration;

    /** The amplifier of the potion effect */
    private int amplification;
    private boolean splash;
    private boolean ambient;
    private List curativeItems;

    public MobEffect(int var1, int var2)
    {
        this(var1, var2, 0);
    }

    public MobEffect(int var1, int var2, int var3)
    {
        this(var1, var2, var3, false);
    }

    public MobEffect(int var1, int var2, int var3, boolean var4)
    {
        this.effectId = var1;
        this.duration = var2;
        this.amplification = var3;
        this.ambient = var4;
        this.curativeItems = new ArrayList();
        this.curativeItems.add(new ItemStack(Item.MILK_BUCKET));
    }

    public MobEffect(MobEffect var1)
    {
        this.effectId = var1.effectId;
        this.duration = var1.duration;
        this.amplification = var1.amplification;
        this.curativeItems = var1.getCurativeItems();
    }

    /**
     * merges the input PotionEffect into this one if this.amplifier <= tomerge.amplifier. The duration in the supplied
     * potion effect is assumed to be greater.
     */
    public void a(MobEffect var1)
    {
        if (this.effectId != var1.effectId)
        {
            System.err.println("This method should only be called for matching effects!");
        }

        if (var1.amplification > this.amplification)
        {
            this.amplification = var1.amplification;
            this.duration = var1.duration;
        }
        else if (var1.amplification == this.amplification && this.duration < var1.duration)
        {
            this.duration = var1.duration;
        }
        else if (!var1.ambient && this.ambient)
        {
            this.ambient = var1.ambient;
        }
    }

    /**
     * Retrieve the ID of the potion this effect matches.
     */
    public int getEffectId()
    {
        return this.effectId;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public int getAmplifier()
    {
        return this.amplification;
    }

    public List getCurativeItems()
    {
        return this.curativeItems;
    }

    public boolean isCurativeItem(ItemStack var1)
    {
        boolean var2 = false;
        Iterator var3 = this.curativeItems.iterator();

        while (var3.hasNext())
        {
            ItemStack var4 = (ItemStack)var3.next();

            if (var4.doMaterialsMatch(var1))
            {
                var2 = true;
            }
        }

        return var2;
    }

    public void setCurativeItems(List var1)
    {
        this.curativeItems = var1;
    }

    public void addCurativeItem(ItemStack var1)
    {
        boolean var2 = false;
        Iterator var3 = this.curativeItems.iterator();

        while (var3.hasNext())
        {
            ItemStack var4 = (ItemStack)var3.next();

            if (var4.doMaterialsMatch(var1))
            {
                var2 = true;
            }
        }

        if (!var2)
        {
            this.curativeItems.add(var1);
        }
    }

    /**
     * Set whether this potion is a splash potion.
     */
    public void setSplash(boolean var1)
    {
        this.splash = var1;
    }

    public boolean isAmbient()
    {
        return this.ambient;
    }

    public boolean tick(EntityLiving var1)
    {
        if (this.duration > 0)
        {
            if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification))
            {
                this.b(var1);
            }

            this.g();
        }

        return this.duration > 0;
    }

    private int g()
    {
        return --this.duration;
    }

    public void b(EntityLiving var1)
    {
        if (this.duration > 0)
        {
            MobEffectList.byId[this.effectId].tick(var1, this.amplification);
        }
    }

    public String f()
    {
        return MobEffectList.byId[this.effectId].a();
    }

    public int hashCode()
    {
        return this.effectId;
    }

    public String toString()
    {
        String var1 = "";

        if (this.getAmplifier() > 0)
        {
            var1 = this.f() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else
        {
            var1 = this.f() + ", Duration: " + this.getDuration();
        }

        if (this.splash)
        {
            var1 = var1 + ", Splash: true";
        }

        return MobEffectList.byId[this.effectId].i() ? "(" + var1 + ")" : var1;
    }

    public boolean equals(Object var1)
    {
        if (!(var1 instanceof MobEffect))
        {
            return false;
        }
        else
        {
            MobEffect var2 = (MobEffect)var1;
            return this.effectId == var2.effectId && this.amplification == var2.amplification && this.duration == var2.duration && this.splash == var2.splash && this.ambient == var2.ambient;
        }
    }

    /**
     * Write a custom potion effect to a potion item's NBT data.
     */
    public NBTTagCompound a(NBTTagCompound var1)
    {
        var1.setByte("Id", (byte)this.getEffectId());
        var1.setByte("Amplifier", (byte)this.getAmplifier());
        var1.setInt("Duration", this.getDuration());
        var1.setBoolean("Ambient", this.isAmbient());
        return var1;
    }

    /**
     * Read a custom potion effect from a potion item's NBT data.
     */
    public static MobEffect b(NBTTagCompound var0)
    {
        byte var1 = var0.getByte("Id");
        byte var2 = var0.getByte("Amplifier");
        int var3 = var0.getInt("Duration");
        boolean var4 = var0.getBoolean("Ambient");
        return new MobEffect(var1, var3, var2, var4);
    }
}
