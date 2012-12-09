package net.minecraft.server;

final class EnchantmentModifierDamage
implements EnchantmentModifier
{
	public EnchantmentModifierDamage() {}
	public EnchantmentModifierDamage(EmptyClass e) {}

	public int a;
	public EntityLiving b;

	public void a(Enchantment paramEnchantment, int paramInt)
	{
		this.a += paramEnchantment.a(paramInt, this.b);
	}
}