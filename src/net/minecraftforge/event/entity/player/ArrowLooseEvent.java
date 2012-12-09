package net.minecraftforge.event.entity.player;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class ArrowLooseEvent extends PlayerEvent
{
    public final ItemStack bow;
    public int charge;
    
    public ArrowLooseEvent(EntityHuman player, ItemStack bow, int charge)
    {
        super(player);
        this.bow = bow;
        this.charge = charge;
    }
}
