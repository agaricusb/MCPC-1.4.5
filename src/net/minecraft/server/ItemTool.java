package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraftforge.common.ForgeHooks;

public class ItemTool extends Item
{
    /** Array of blocks the tool has extra effect against. */
    private Block[] c;
    public float a = 4.0F;

    /** Damage versus entities. */
    public int cl;

    /** The material this tool is made from. */
    protected EnumToolMaterial b;

    protected ItemTool(int var1, int var2, EnumToolMaterial var3, Block[] var4)
    {
        super(var1);
        this.b = var3;
        this.c = var4;
        this.maxStackSize = 1;
        this.setMaxDurability(var3.a());
        this.a = var3.b();
        this.cl = var2 + var3.c();
        this.a(CreativeModeTab.i);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack var1, Block var2)
    {
        Block[] var3 = this.c;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            Block var6 = var3[var5];

            if (var6 == var2)
            {
                return this.a;
            }
        }

        return 1.0F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
    {
        var1.damage(2, var3);
        return true;
    }

    public boolean a(ItemStack var1, World var2, int var3, int var4, int var5, int var6, EntityLiving var7)
    {
        if ((double)Block.byId[var3].m(var2, var4, var5, var6) != 0.0D)
        {
            var1.damage(1, var7);
        }

        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int a(Entity var1)
    {
        return this.cl;
    }

    @SideOnly(Side.CLIENT)
    public boolean n_()
    {
        return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int c()
    {
        return this.b.e();
    }

    /**
     * Return the name for this tool's material.
     */
    public String g()
    {
        return this.b.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean a(ItemStack var1, ItemStack var2)
    {
        return this.b.f() == var2.id ? true : super.a(var1, var2);
    }

    public float getStrVsBlock(ItemStack var1, Block var2, int var3)
    {
        return ForgeHooks.isToolEffective(var1, var2, var3) ? this.a : this.getDestroySpeed(var1, var2);
    }
}
