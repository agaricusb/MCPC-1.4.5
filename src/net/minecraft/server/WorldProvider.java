package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraftforge.common.DimensionManager;

public abstract class WorldProvider
{
    /** world object being used */
    public World a;
    public WorldType type;
    public String c;

    /** World chunk manager being used to generate chunks */
    public WorldChunkManager d;

    /**
     * States whether the Hell world provider is used(true) or if the normal world provider is used(false)
     */
    public boolean e = false;

    /**
     * A boolean that tells if a world does not have a sky. Used in calculating weather and skylight
     */
    public boolean f = false;

    /** Light to brightness conversion table */
    public float[] g = new float[16];

    /** The id for the dimension (ex. -1: Nether, 0: Overworld, 1: The End) */
    public int dimension = 0;

    /** Array for sunrise/sunset colors (RGBA) */
    private float[] i = new float[4];

    /**
     * associate an existing world with a World provider, and setup its lightbrightness table
     */
    public final void a(World var1)
    {
        this.a = var1;
        this.type = var1.getWorldData().getType();
        this.c = var1.getWorldData().getGeneratorOptions();
        this.b();
        this.a();
    }

    /**
     * Creates the light to brightness table
     */
    protected void a()
    {
        float var1 = 0.0F;

        for (int var2 = 0; var2 <= 15; ++var2)
        {
            float var3 = 1.0F - (float)var2 / 15.0F;
            this.g[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }

    /**
     * creates a new world chunk manager for WorldProvider
     */
    protected void b()
    {
        this.d = this.type.getChunkManager(this.a);
    }

    /**
     * Returns the chunk provider back for the world provider
     */
    public IChunkProvider getChunkProvider()
    {
        return this.type.getChunkGenerator(this.a, this.c);
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canSpawn(int var1, int var2)
    {
        int var3 = this.a.b(var1, var2);
        return var3 == Block.GRASS.id;
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float a(long var1, float var3)
    {
        int var4 = (int)(var1 % 24000L);
        float var5 = ((float)var4 + var3) / 24000.0F - 0.25F;

        if (var5 < 0.0F)
        {
            ++var5;
        }

        if (var5 > 1.0F)
        {
            --var5;
        }

        float var6 = var5;
        var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean d()
    {
        return true;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean e()
    {
        return true;
    }

    public static WorldProvider byDimension(int var0)
    {
        return DimensionManager.createProviderFor(var0);
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates h()
    {
        return null;
    }

    public int getSeaLevel()
    {
        return this.type.getMinimumSpawnHeight(this.a);
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public abstract String getName();

    public void setDimension(int var1)
    {
        this.dimension = var1;
    }
    
    public boolean isForgeOnlyWorld()
    {
    	return this.a instanceof SecondaryWorldServer && (this.dimension < -1 || this.dimension > 1);
    }

    public String getSaveFolder()
    {
        return isForgeOnlyWorld() ? "DIM" + this.dimension : null;
    }

    public String getWelcomeMessage()
    {
        return this instanceof WorldProviderTheEnd ? "Entering the End" : (this instanceof WorldProviderHell ? "Entering the Nether" : null);
    }

    public String getDepartMessage()
    {
        return this instanceof WorldProviderTheEnd ? "Leaving the End" : (this instanceof WorldProviderHell ? "Leaving the Nether" : null);
    }

    public double getMovementFactor()
    {
        return this instanceof WorldProviderHell ? 8.0D : 1.0D;
    }


    public ChunkCoordinates getRandomizedSpawnPoint()
    {
        ChunkCoordinates var1 = new ChunkCoordinates(this.a.getSpawn());
        boolean var2 = this.a.getWorldData().getGameType() != EnumGamemode.ADVENTURE;
        int var3 = this.type.getSpawnFuzz();
        int var4 = var3 / 2;

        if (!this.f && !var2)
        {
            var1.x += this.a.random.nextInt(var3) - var4;
            var1.z += this.a.random.nextInt(var3) - var4;
            var1.y = this.a.i(var1.x, var1.z);
        }

        return var1;
    }

    public BiomeBase getBiomeGenForCoords(int var1, int var2)
    {
        return this.a.getBiomeGenForCoordsBody(var1, var2);
    }

    public boolean isDaytime()
    {
        return this.a.j < 4;
    }


    public void setAllowedSpawnTypes(boolean var1, boolean var2)
    {
        this.a.allowMonsters = var1;
        this.a.allowAnimals = var2;
    }

    public void calculateInitialWeather()
    {
        this.a.calculateInitialWeatherBody();
    }

    public void updateWeather()
    {
        this.a.updateWeatherBody();
    }

    public void toggleRain()
    {
        this.a.worldData.setWeatherDuration(1);
    }

    public boolean canBlockFreeze(int var1, int var2, int var3, boolean var4)
    {
        return this.a.canBlockFreezeBody(var1, var2, var3, var4);
    }

    public boolean canSnowAt(int var1, int var2, int var3)
    {
        return this.a.canSnowAtBody(var1, var2, var3);
    }

    public void setWorldTime(long var1)
    {
        this.a.worldData.setDayTime(var1);
    }

    public long getSeed()
    {
        return this.a.worldData.getSeed();
    }

    public long getWorldTime()
    {
        return this.a.worldData.getDayTime();
    }

    public ChunkCoordinates getSpawnPoint()
    {
        WorldData var1 = this.a.worldData;
        return new ChunkCoordinates(var1.c(), var1.d(), var1.e());
    }

    public void setSpawnPoint(int var1, int var2, int var3)
    {
        this.a.worldData.setSpawn(var1, var2, var3);
    }

    public boolean canMineBlock(EntityHuman var1, int var2, int var3, int var4)
    {
        return this.a.canMineBlockBody(var1, var2, var3, var4);
    }

    public boolean isBlockHighHumidity(int var1, int var2, int var3)
    {
        return this.a.getBiome(var1, var3).e();
    }

    public int getHeight()
    {
        return 256;
    }

    public int getActualHeight()
    {
        return this.f ? 128 : 256;
    }

    public double getHorizon()
    {
        return this.a.worldData.getType().getHorizon(this.a);
    }

    public void resetRainAndThunder()
    {
        this.a.worldData.setWeatherDuration(0);
        this.a.worldData.setStorm(false);
        this.a.worldData.setThunderDuration(0);
        this.a.worldData.setThundering(false);
    }

    public boolean canDoLightning(Chunk var1)
    {
        return true;
    }

    public boolean canDoRainSnowIce(Chunk var1)
    {
        return true;
    }
}
