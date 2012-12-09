package net.minecraftforge.event.entity.player;

import java.util.ArrayList;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EnchantmentManager;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

/**
 * Child class of LivingDropEvent that is fired specifically when a
 * player dies.  Canceling the event will prevent ALL drops from entering the
 * world.
 */
@Cancelable
public class PlayerDropsEvent extends LivingDropsEvent
{
    public final EntityHuman entityPlayer;

    /**
     * Creates a new event containing all the items that will drop into the
     * world when a player dies.
     * @param entity The dying player. 
     * @param source The source of the damage which is killing the player.
     * @param drops List of all drops entering the world.
     */
    public PlayerDropsEvent(EntityHuman entity, DamageSource source, ArrayList<EntityItem> drops, boolean recentlyHit)
    {
        super(entity, source, drops, 
            (source.getEntity() instanceof EntityHuman) ? 
                EnchantmentManager.getBonusMonsterLootEnchantmentLevel(((EntityHuman)source.getEntity())) : 0,
            recentlyHit, 0);
        
        this.entityPlayer = entity;
    }
}
