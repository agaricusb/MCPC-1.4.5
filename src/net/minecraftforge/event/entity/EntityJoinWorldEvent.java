package net.minecraftforge.event.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.World;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class EntityJoinWorldEvent extends EntityEvent
{

    public final World world;

    public EntityJoinWorldEvent(Entity entity, World world)
    {
        super(entity);
        this.world = world;
    }
}
