package cpw.mods.fml.common.modloader;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.INetworkManager;
import net.minecraft.server.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ModLoaderPacketHandler implements IPacketHandler
{
    private BaseModProxy mod;

    public ModLoaderPacketHandler(BaseModProxy mod)
    {
        this.mod = mod;
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        if (player instanceof EntityPlayer)
        {
            mod.serverCustomPayload(((EntityPlayer)player).netServerHandler, packet);
        }
        else
        {
            ModLoaderHelper.sidedHelper.sendClientPacket(mod, packet);
        }
    }

}
