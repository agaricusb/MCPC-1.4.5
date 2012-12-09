package cpw.mods.fml.common;

import java.util.Map;

import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldNBTStorage;
import net.minecraft.server.WorldData;

public interface WorldAccessContainer
{
    public NBTTagCompound getDataForWriting(WorldNBTStorage handler, WorldData info);
    public void readData(WorldNBTStorage handler, WorldData info, Map<String,NBTBase> propertyMap, NBTTagCompound tag);
}
