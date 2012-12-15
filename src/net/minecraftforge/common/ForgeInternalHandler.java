package net.minecraftforge.common;

import java.util.UUID;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.server.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.world.WorldEvent;

public class ForgeInternalHandler
{
    @ForgeSubscribe(priority = EventPriority.HIGHEST)
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isStatic)
        {
            if (event.entity.getPersistentID() == null)
            {
                event.entity.generatePersistentID();
            }
            else
            {
                ForgeChunkManager.loadEntity(event.entity);
            }
        }

        Entity entity = event.entity;
        if (entity.getClass().equals(EntityItem.class))
        {
            ItemStack stack = ((EntityItem)entity).itemStack;

            if (stack == null)
            {
                entity.die();
                event.setCanceled(true);
                return;
            }

            Item item = stack.getItem();
            if (item == null)
            {
                FMLLog.warning("Attempted to add a EntityItem to the world with a invalid item: ID %d at " +
                    "(%2.2f,  %2.2f, %2.2f), this is most likely a config issue between you and the server. Please double check your configs",
                    stack.id, entity.locX, entity.locY, entity.locZ);
                entity.die();
                event.setCanceled(true);
                return;
            }

            if (item.hasCustomEntity(stack))
            {
                Entity newEntity = item.createEntity(event.world, entity, stack);
                if (newEntity != null)
                {
                    entity.die();
                    event.setCanceled(true);
                    event.world.addEntity(newEntity);
                }
            }
        }
    }

    @ForgeSubscribe(priority = EventPriority.HIGHEST)
    public void onDimensionLoad(WorldEvent.Load event)
    {
        ForgeChunkManager.loadWorld(event.world);
    }

    @ForgeSubscribe(priority = EventPriority.HIGHEST)
    public void onDimensionSave(WorldEvent.Save event)
    {
    	ForgeChunkManager.saveWorld(event.world);
    }

    @ForgeSubscribe(priority = EventPriority.HIGHEST)
    public void onDimensionUnload(WorldEvent.Unload event)
    {
        ForgeChunkManager.unloadWorld(event.world);
    }
}