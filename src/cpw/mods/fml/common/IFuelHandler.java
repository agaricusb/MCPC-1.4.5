package cpw.mods.fml.common;

import net.minecraft.server.ItemStack;

public interface IFuelHandler
{
    int getBurnTime(ItemStack fuel);
}