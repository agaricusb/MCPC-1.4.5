package net.minecraft.server;

import java.io.File;

public class ServerNBTManager extends WorldNBTStorage
{
    public ServerNBTManager(File var1, String var2, boolean var3)
    {
        super(var1, var2, var3);
    }

    /**
     * initializes and returns the chunk loader for the specified world provider
     */
    public IChunkLoader createChunkLoader(WorldProvider var1)
    {
        File var2 = this.getDirectory();

        if (var1.getSaveFolder() != null)
        {
            File var3 = new File(var2, var1.getSaveFolder());
            var3.mkdirs();
            return new ChunkRegionLoader(var3);
        }
        else
        {
            return new ChunkRegionLoader(var2);
        }
    }

    /**
     * Saves the given World Info with the given NBTTagCompound as the Player.
     */
    public void saveWorldData(WorldData var1, NBTTagCompound var2)
    {
        var1.e(19133);
        super.saveWorldData(var1, var2);
    }

    /**
     * Called to flush all changes to disk, waiting for them to complete.
     */
    public void a() {
        FileIOThread.a.a();

        RegionFileCache.a();
     }
}
