package net.minecraft.server;

import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;

public class SecondaryWorldServer extends WorldServer
{
	  public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, WorldServer worldserver, MethodProfiler methodprofiler, Environment env, ChunkGenerator gen)
	  {
	    super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, env, gen);

	    this.worldMaps = worldserver.worldMaps;
	  }
	  
	  public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, WorldServer worldserver, MethodProfiler methodprofiler, WorldProvider wp, Environment env, ChunkGenerator gen)
	  {
	    super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, wp, env, gen);

	    this.worldMaps = worldserver.worldMaps;
	  }
}