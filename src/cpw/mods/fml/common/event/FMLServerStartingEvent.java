package cpw.mods.fml.common.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.CommandHandler;
import net.minecraft.server.ICommand;
import cpw.mods.fml.common.LoaderState.ModState;

public class FMLServerStartingEvent extends FMLStateEvent
{

    private MinecraftServer server;

    public FMLServerStartingEvent(Object... data)
    {
        super(data);
        this.server = (MinecraftServer) data[0];
    }
    @Override
    public ModState getModState()
    {
        return ModState.AVAILABLE;
    }

    public MinecraftServer getServer()
    {
        return server;
    }

    public void registerServerCommand(ICommand command)
    {
        CommandHandler ch = (CommandHandler) getServer().getCommandHandler();
        ch.a(command);
    }
}
