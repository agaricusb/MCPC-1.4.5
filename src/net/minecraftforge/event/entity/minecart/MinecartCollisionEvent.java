package net.minecraftforge.event.entity.minecart;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityMinecart;

public class MinecartCollisionEvent extends MinecartEvent
{
    public final Entity collider;

    public MinecartCollisionEvent(EntityMinecart minecart, Entity collider)
    {
        super(minecart);
        this.collider = collider;
    }
}
