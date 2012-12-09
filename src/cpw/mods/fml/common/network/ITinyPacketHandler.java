package cpw.mods.fml.common.network;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet131ItemData;

public interface ITinyPacketHandler
{
    void handle(NetHandler handler, Packet131ItemData mapData);
}
