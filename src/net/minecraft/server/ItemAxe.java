package net.minecraft.server;

public class ItemAxe extends ItemTool
{
  public static Block[] c = new Block[]{ Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.DOUBLE_STEP, Block.STEP, Block.PUMPKIN, Block.JACK_O_LANTERN };

  protected ItemAxe(int paramInt, EnumToolMaterial paramEnumToolMaterial)
  {
    super(paramInt, 3, paramEnumToolMaterial, c);
  }

  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    return paramBlock != null && (paramBlock.material == Material.WOOD || paramBlock.material == Material.PLANT || paramBlock.material == Material.REPLACEABLE_PLANT)?this.a:super.getDestroySpeed(paramItemStack, paramBlock);
    }
}