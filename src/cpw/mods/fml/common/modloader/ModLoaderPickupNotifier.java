package cpw.mods.fml.common.modloader;

import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityHuman;
import cpw.mods.fml.common.IPickupNotifier;

public class ModLoaderPickupNotifier implements IPickupNotifier
{

    private BaseModProxy mod;

    public ModLoaderPickupNotifier(BaseModProxy mod)
    {
        this.mod = mod;
    }

    @Override
    public void notifyPickup(EntityItem item, EntityHuman player)
    {
        mod.onItemPickup(player, item.itemStack);
    }

}
