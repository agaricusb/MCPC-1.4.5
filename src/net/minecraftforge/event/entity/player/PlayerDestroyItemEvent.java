package net.minecraftforge.event.entity.player;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;

public class PlayerDestroyItemEvent extends PlayerEvent
{
    public final ItemStack original;
    public PlayerDestroyItemEvent(EntityHuman player, ItemStack original)
    {
        super(player);
        this.original = original;
    }

}
