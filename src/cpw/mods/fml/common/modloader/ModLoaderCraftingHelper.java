package cpw.mods.fml.common.modloader;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class ModLoaderCraftingHelper implements ICraftingHandler
{

    private BaseModProxy mod;

    public ModLoaderCraftingHelper(BaseModProxy mod)
    {
        this.mod = mod;
    }

    @Override
    public void onCrafting(EntityHuman player, ItemStack item, IInventory craftMatrix)
    {
        mod.takenFromCrafting(player, item, craftMatrix);
    }

    @Override
    public void onSmelting(EntityHuman player, ItemStack item)
    {
        mod.takenFromFurnace(player, item);
    }

}
