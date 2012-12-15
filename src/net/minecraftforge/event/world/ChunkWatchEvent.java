package net.minecraftforge.event.world;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.PlayerInstance;
import net.minecraft.server.WorldServer;
import net.minecraftforge.event.Event;

public class ChunkWatchEvent extends Event
{
    public final ChunkCoordIntPair chunk;
    public final EntityPlayer player;
    
    public ChunkWatchEvent(ChunkCoordIntPair chunk, EntityPlayer player)
    {
        this.chunk = chunk;
        this.player = player;
    }
    
    public static class Watch extends ChunkWatchEvent
    {
        public Watch(ChunkCoordIntPair chunk, EntityPlayer player) { super(chunk, player); }        
    }
    
    public static class UnWatch extends ChunkWatchEvent
    {
        public UnWatch(ChunkCoordIntPair chunkLocation, EntityPlayer player) { super(chunkLocation, player); }        
    }
}
