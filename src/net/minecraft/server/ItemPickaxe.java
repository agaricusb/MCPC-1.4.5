package net.minecraft.server;

public class ItemPickaxe extends ItemTool
{
  public static Block[] c = { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.RAILS, Block.DETECTOR_RAIL, Block.GOLDEN_RAIL };

  protected ItemPickaxe(int paramInt, EnumToolMaterial paramEnumToolMaterial)
  {
    super(paramInt, 2, paramEnumToolMaterial, c);
  }

  public boolean canDestroySpecialBlock(Block paramBlock)
  {
    if (paramBlock == Block.OBSIDIAN) return this.b.d() == 3;
    if ((paramBlock == Block.DIAMOND_BLOCK) || (paramBlock == Block.DIAMOND_ORE)) return this.b.d() >= 2;
    if (paramBlock == Block.EMERALD_ORE) return this.b.d() >= 2;
    if ((paramBlock == Block.GOLD_BLOCK) || (paramBlock == Block.GOLD_ORE)) return this.b.d() >= 2;
    if ((paramBlock == Block.IRON_BLOCK) || (paramBlock == Block.IRON_ORE)) return this.b.d() >= 1;
    if ((paramBlock == Block.LAPIS_BLOCK) || (paramBlock == Block.LAPIS_ORE)) return this.b.d() >= 1;
    if ((paramBlock == Block.REDSTONE_ORE) || (paramBlock == Block.GLOWING_REDSTONE_ORE)) return this.b.d() >= 2;
    if (paramBlock.material == Material.STONE) return true;
    if (paramBlock.material == Material.ORE) return true;
    return paramBlock.material == Material.HEAVY;
  }

  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    if ((paramBlock != null) && ((paramBlock.material == Material.ORE) || (paramBlock.material == Material.HEAVY) || (paramBlock.material == Material.STONE))) {
      return this.a;
    }
    return super.getDestroySpeed(paramItemStack, paramBlock);
  }
}