package net.minecraftforge.event.entity.player;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class AttackEntityEvent extends PlayerEvent
{
    public final Entity target;

    public AttackEntityEvent(EntityHuman var1, Entity var2)
    {
        super(var1);
        this.target = var2;
    }
}
