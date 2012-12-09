package net.minecraft.server;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet
{
    /** The player's entity ID */
    public int a = 0;
    public WorldType b;
    public boolean c;
    public EnumGamemode d;

    /** -1: The Nether, 0: The Overworld, 1: The End */
    public int e;

    /** The difficulty setting byte. */
    public byte f;

    /** Defaults to 128 */
    public byte g;

    /** The maximum players. */
    public byte h;
    private boolean vanillaCompatible;

    public Packet1Login()
    {
        this.vanillaCompatible = FMLNetworkHandler.vanillaLoginPacketCompatibility();
    }

    public Packet1Login(int var1, WorldType var2, EnumGamemode var3, boolean var4, int var5, int var6, int var7, int var8)
    {
        this.a = var1;
        this.b = var2;
        this.e = var5;
        this.f = (byte)var6;
        this.d = var3;
        this.g = (byte)var7;
        this.h = (byte)var8;
        this.c = var4;
        this.vanillaCompatible = false;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream var1) throws IOException
    {
        this.a = var1.readInt();
        String var2 = a(var1, 16);
        this.b = WorldType.getType(var2);

        if (this.b == null)
        {
            this.b = WorldType.NORMAL;
        }

        byte var3 = var1.readByte();
        this.c = (var3 & 8) == 8;
        int var4 = var3 & -9;
        this.d = EnumGamemode.a(var4);

        if (this.vanillaCompatible)
        {
            this.e = var1.readByte();
        }
        else
        {
            this.e = var1.readInt();
        }

        this.f = var1.readByte();
        this.g = var1.readByte();
        this.h = var1.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.a);
        a(this.b == null ? "" : this.b.name(), var1);
        int i = this.d.a();

        if (this.c)  i |= 8;

        var1.writeByte(i);

        if (this.vanillaCompatible)
        {
            var1.writeByte(this.e);
        }
        else
        {
            var1.writeInt(this.e);
        }

        var1.writeByte(this.f);
        var1.writeByte(this.g);
        var1.writeByte(this.h);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(NetHandler var1)
    {
        var1.a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        int var1 = 0;

        if (this.b != null)
        {
            var1 = this.b.name().length();
        }

        return 6 + 2 * var1 + 4 + 4 + 1 + 1 + 1 + (this.vanillaCompatible ? 0 : 3);
    }
}
