package cpw.mods.fml.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NetHandler;
import net.minecraft.server.INetworkManager;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import mcpc.com.google.common.io.ByteArrayDataInput;
import mcpc.com.google.common.io.ByteArrayDataOutput;
import mcpc.com.google.common.io.ByteStreams;

public class EntitySpawnPacket extends FMLPacket
{

    public int networkId;
    public int modEntityId;
    public int entityId;
    public double scaledX;
    public double scaledY;
    public double scaledZ;
    public float scaledYaw;
    public float scaledPitch;
    public float scaledHeadYaw;
    public List metadata;
    public int throwerId;
    public double speedScaledX;
    public double speedScaledY;
    public double speedScaledZ;
    public ByteArrayDataInput dataStream;
    public int rawX;
    public int rawY;
    public int rawZ;

    public EntitySpawnPacket()
    {
        super(Type.ENTITYSPAWN);
    }

    @Override
    public byte[] generatePacket(Object... data)
    {
        EntityRegistration er = (EntityRegistration) data[0];
        Entity ent = (Entity) data[1];
        NetworkModHandler handler = (NetworkModHandler) data[2];
        ByteArrayDataOutput dat = ByteStreams.newDataOutput();

        dat.writeInt(handler.getNetworkId());
        dat.writeInt(er.getModEntityId());
        // entity id
        dat.writeInt(ent.id);

        // entity pos x,y,z
        dat.writeInt(MathHelper.floor(ent.locX * 32D));
        dat.writeInt(MathHelper.floor(ent.locY * 32D));
        dat.writeInt(MathHelper.floor(ent.locZ * 32D));

        // yaw, pitch
        dat.writeByte((byte) (ent.yaw * 256.0F / 360.0F));
        dat.writeByte((byte) (ent.pitch * 256.0F / 360.0F));

        // head yaw
        if (ent instanceof EntityLiving)
        {
            dat.writeByte((byte) (((EntityLiving)ent).ay * 256.0F / 360.0F));
        }
        else
        {
            dat.writeByte(0);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        ent.getDataWatcher().a(dos);

        dat.write(bos.toByteArray());

        if (ent instanceof IThrowableEntity)
        {
            Entity owner = ((IThrowableEntity)ent).getThrower();
            dat.writeInt(owner == null ? ent.id : owner.id);
            double maxVel = 3.9D;
            double mX = ent.motX;
            double mY = ent.motY;
            double mZ = ent.motZ;
            if (mX < -maxVel) mX = -maxVel;
            if (mY < -maxVel) mY = -maxVel;
            if (mZ < -maxVel) mZ = -maxVel;
            if (mX >  maxVel) mX =  maxVel;
            if (mY >  maxVel) mY =  maxVel;
            if (mZ >  maxVel) mZ =  maxVel;
            dat.writeInt((int)(mX * 8000D));
            dat.writeInt((int)(mY * 8000D));
            dat.writeInt((int)(mZ * 8000D));
        }
        else
        {
            dat.writeInt(0);
        }
        if (ent instanceof IEntityAdditionalSpawnData)
        {
            ((IEntityAdditionalSpawnData)ent).writeSpawnData(dat);
        }

        return dat.toByteArray();
    }

    @Override
    public FMLPacket consumePacket(byte[] data)
    {
        ByteArrayDataInput dat = ByteStreams.newDataInput(data);
        networkId = dat.readInt();
        modEntityId = dat.readInt();
        entityId = dat.readInt();
        rawX = dat.readInt();
        rawY = dat.readInt();
        rawZ = dat.readInt();
        scaledX = rawX / 32D;
        scaledY = rawY / 32D;
        scaledZ = rawZ / 32D;
        scaledYaw = dat.readByte() * 360F / 256F;
        scaledPitch = dat.readByte() * 360F / 256F;
        scaledHeadYaw = dat.readByte() * 360F / 256F;
        ByteArrayInputStream bis = new ByteArrayInputStream(data, 27, data.length - 27);
        DataInputStream dis = new DataInputStream(bis);

        metadata = DataWatcher.a(dis);

        dat.skipBytes(data.length - bis.available() - 27);
        throwerId = dat.readInt();
        if (throwerId != 0)
        {
            speedScaledX = dat.readInt() / 8000D;
            speedScaledY = dat.readInt() / 8000D;
            speedScaledZ = dat.readInt() / 8000D;
        }

        this.dataStream = dat;
        return this;
    }

    @Override
    public void execute(INetworkManager network, FMLNetworkHandler handler, NetHandler netHandler, String userName)
    {
        NetworkModHandler nmh = handler.findNetworkModHandler(networkId);
        ModContainer mc = nmh.getContainer();
        EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(mc, modEntityId);
        Class<? extends Entity> cls =  registration.getEntityClass();
        if (cls == null)
        {
            FMLLog.log(Level.WARNING, "Missing mod entity information for %s : %d", mc.getModId(), modEntityId);
            return;
        }
    
        Entity entity = FMLCommonHandler.instance().spawnEntityIntoClientWorld(registration, this);
    }

}
