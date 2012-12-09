package net.minecraftforge.event.entity.minecart;

import net.minecraft.server.EntityMinecart;
import net.minecraftforge.event.entity.EntityEvent;

public class MinecartEvent extends EntityEvent
{
    public final EntityMinecart minecart;

    public MinecartEvent(EntityMinecart minecart)
    {
        super(minecart);
        this.minecart = minecart;
    }
}
