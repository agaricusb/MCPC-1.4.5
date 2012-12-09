package net.minecraftforge.common;

import java.io.File;
import java.util.UUID;

import net.minecraft.server.ExceptionWorldConflict;
import net.minecraft.server.IChunkLoader;
import net.minecraft.server.IDataManager;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PlayerFileData;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldServer;

//Class used internally to provide the world specific data directories. 

public class WorldSpecificSaveHandler implements IDataManager
{
    private WorldServer world;
    private IDataManager parent;
    private File dataDir;

    public WorldSpecificSaveHandler(WorldServer world, IDataManager parent)
    {
        this.world = world;
        this.parent = parent;
        dataDir = new File(world.getChunkSaveLocation(), "data");
        dataDir.mkdirs();
    }

    @Override public WorldData getWorldData() { return parent.getWorldData(); }
    @Override public void checkSession() throws ExceptionWorldConflict { parent.checkSession(); }
    @Override public IChunkLoader createChunkLoader(WorldProvider var1) { return parent.createChunkLoader(var1); }
    @Override public void saveWorldData(WorldData var1, NBTTagCompound var2) { parent.saveWorldData(var1, var2); }
    @Override public void saveWorldData(WorldData var1){ parent.saveWorldData(var1); }
    @Override public PlayerFileData getPlayerFileData() { return parent.getPlayerFileData(); }
    @Override public void a() { parent.a(); }
    @Override public String g() { return parent.g(); }

    @Override
    public File getDataFile(String name)
    {
        return new File(dataDir, name + ".dat");
    }

	@Override
	public UUID getUUID() {
		return parent.getUUID();
	}
}