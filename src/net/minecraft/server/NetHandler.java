package net.minecraft.server;

public abstract class NetHandler
{
    /**
     * determine if it is a server handler
     */
    public abstract boolean a();

    /**
     * Handle Packet51MapChunk (full chunk update of blocks, metadata, light levels, and optionally biome data)
     */
    public void a(Packet51MapChunk var1) {}

    /**
     * Default handler called for packets that don't have their own handlers in NetServerHandler; kicks player from the
     * server.
     */
    public void onUnhandledPacket(Packet var1) {}

    public void a(String var1, Object[] var2) {}

    public void a(Packet255KickDisconnect var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet1Login var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet10Flying var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet52MultiBlockChange var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet14BlockDig var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet53BlockChange var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet20NamedEntitySpawn var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet30Entity var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet34EntityTeleport var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet15Place var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet16BlockItemSwitch var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet29DestroyEntity var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet21PickupSpawn var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet22Collect var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet3Chat var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet23VehicleSpawn var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet18ArmAnimation var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    public void a(Packet19EntityAction var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet2Handshake var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet253KeyRequest var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet252KeyResponse var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet24MobSpawn var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet4UpdateTime var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet6SpawnPosition var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Packet handler
     */
    public void a(Packet28EntityVelocity var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Packet handler
     */
    public void a(Packet40EntityMetadata var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Packet handler
     */
    public void a(Packet39AttachEntity var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet7UseEntity var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Packet handler
     */
    public void a(Packet38EntityStatus var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Recieves player health from the server and then proceeds to set it locally on the client.
     */
    public void a(Packet8UpdateHealth var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * respawns the player
     */
    public void a(Packet9Respawn var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet60Explosion var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet100OpenWindow var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void handleContainerClose(Packet101CloseWindow var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet102WindowClick var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet103SetSlot var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet104WindowItems var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Updates Client side signs
     */
    public void a(Packet130UpdateSign var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet105CraftProgressBar var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet5EntityEquipment var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet106Transaction var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Packet handler
     */
    public void a(Packet25EntityPainting var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet54PlayNoteBlock var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * runs registerPacket on the given Packet200Statistic
     */
    public void a(Packet200Statistic var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet17EntityLocationAction var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet70Bed var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handles weather packet
     */
    public void a(Packet71Weather var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Contains logic for handling packets containing arbitrary unique item data. Currently this is only for maps.
     */
    public void a(Packet131ItemData var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet61WorldEvent var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a server ping packet.
     */
    public void a(Packet254GetInfo var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle an entity effect packet.
     */
    public void a(Packet41MobEffect var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a remove entity effect packet.
     */
    public void a(Packet42RemoveMobEffect var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a player information packet.
     */
    public void a(Packet201PlayerInfo var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a keep alive packet.
     */
    public void a(Packet0KeepAlive var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle an experience packet.
     */
    public void a(Packet43SetExperience var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a creative slot packet.
     */
    public void a(Packet107SetCreativeSlot var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a entity experience orb packet.
     */
    public void a(Packet26AddExpOrb var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet108ButtonClick var1) {}

    public void a(Packet250CustomPayload var1) {}

    public void a(Packet35EntityHeadRotation var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet132TileEntityData var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * Handle a player abilities packet.
     */
    public void a(Packet202Abilities var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet203TabComplete var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet204LocaleAndViewDistance var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet62NamedSoundEffect var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet55BlockBreakAnimation var1)
    {
        this.onUnhandledPacket(var1);
    }

    public void a(Packet205ClientCommand var1) {}

    public void a(Packet56MapChunkBulk var1)
    {
        this.onUnhandledPacket(var1);
    }

    /**
     * packet.processPacket is only called if this returns true
     */
    public boolean b()
    {
        return false;
    }

    public abstract void handleVanilla250Packet(Packet250CustomPayload var1);

    public abstract EntityHuman getPlayerH();
}
