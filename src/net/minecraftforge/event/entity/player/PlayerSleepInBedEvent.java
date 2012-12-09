package net.minecraftforge.event.entity.player;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumBedResult;

public class PlayerSleepInBedEvent extends PlayerEvent
{
    public EnumBedResult result = null;
    public final int x;
    public final int y;
    public final int z;

    public PlayerSleepInBedEvent(EntityHuman player, int x, int y, int z)
    {
        super(player);
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
