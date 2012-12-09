package net.minecraft.server;

final class EnchantmentModifierProtection
implements EnchantmentModifier
{
	public EnchantmentModifierProtection() {}
	public EnchantmentModifierProtection(EmptyClass e) {}

	public int a;
	public DamageSource b;

	public void a(Enchantment paramEnchantment, int paramInt)
	{
		this.a += paramEnchantment.a(paramInt, this.b);
	}
}