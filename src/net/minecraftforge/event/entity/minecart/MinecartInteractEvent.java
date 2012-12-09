package net.minecraftforge.event.entity.minecart;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMinecart;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class MinecartInteractEvent extends MinecartEvent
{
    public final EntityHuman player;

    public MinecartInteractEvent(EntityMinecart minecart, EntityHuman player)
    {
        super(minecart);
        this.player = player;
    }
}
