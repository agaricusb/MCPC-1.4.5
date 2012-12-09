package net.minecraftforge.event.entity.living;

import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityLiving;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class LivingDeathEvent extends LivingEvent
{
    public final DamageSource source;
    public LivingDeathEvent(EntityLiving entity, DamageSource source)
    {
        super(entity);
        this.source = source;
    }

}
