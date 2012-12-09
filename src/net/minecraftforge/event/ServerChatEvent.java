package net.minecraftforge.event;

import net.minecraft.server.EntityHuman;

@Cancelable
public class ServerChatEvent extends Event {
    public final String message, username;
    public final EntityHuman player;
    public String line;
    public ServerChatEvent(EntityHuman player, String message, String line)
    {
        super();
        this.message = message;
        this.player = player;
        this.username = player.name;
        this.line = "<" + username + "> " + message;
    }
}
