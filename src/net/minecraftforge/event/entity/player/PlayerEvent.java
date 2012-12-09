package net.minecraftforge.event.entity.player;

import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PlayerEvent extends LivingEvent
{
    public final EntityHuman entityPlayer;
    public PlayerEvent(EntityHuman player)
    {
        super(player);
        entityPlayer = player;
    }
    
    public static class HarvestCheck extends PlayerEvent
    {
        public final Block block;
        public boolean success;

        public HarvestCheck(EntityHuman player, Block block, boolean success)
        {
            super(player);
            this.block = block;
            this.success = success;
        }
    }

    @Cancelable
    public static class BreakSpeed extends PlayerEvent
    {
        public final Block block;
        public final int metadata;
        public final float originalSpeed;
        public float newSpeed = 0.0f;

        public BreakSpeed(EntityHuman player, Block block, int metadata, float original)
        {
            super(player);
            this.block = block;
            this.metadata = metadata;
            this.originalSpeed = original;
            this.newSpeed = original;
        }
    }
}
