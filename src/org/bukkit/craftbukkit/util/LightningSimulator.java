package org.bukkit.craftbukkit.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.server.EntityLightning;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.weather.ThunderChangeEvent;

public class LightningSimulator {

    private static final int MAX_LIGHTNING_BRANCHES = 5;
    final World world;
    final HashMap<EntityPlayer, Integer> playerCountdown = new HashMap<EntityPlayer, Integer>();
    Intensity stormIntensity = null;
    boolean canceled = false;

    public LightningSimulator(World world) {
        this.world = world;
    }

    public static void configure(YamlConfiguration configuration) {
        Bukkit.getLogger().info("--------Setting up Storm Configuration--------");
        for (Intensity intensity : Intensity.values()) {
            String nameFormatted = intensity.name().toLowerCase().replaceAll("_", "-");
            intensity.chance = configuration.getInt("storm-settings." + nameFormatted + ".chance", intensity.chance);
            intensity.baseTicks = configuration.getInt("storm-settings." + nameFormatted + ".lightning-delay", intensity.baseTicks);
            intensity.randomTicks = configuration.getInt("storm-settings." + nameFormatted + ".lightning-random-delay", intensity.randomTicks);
            Bukkit.getLogger().info("    Storm Type: " + nameFormatted);
            Bukkit.getLogger().info("        Chance: " + intensity.chance);
            Bukkit.getLogger().info("        Lightning Delay Ticks: " + intensity.baseTicks);
            Bukkit.getLogger().info("        Lightning Random Delay Ticks: " + intensity.randomTicks);
        }
        Bukkit.getLogger().info("--------Finished Storm Configuration--------");
    }

    public void onTick() {
        try {
            updatePlayerTimers();
        } catch (Exception e) {
            System.out.println("Spigot failed to calculate lightning for the server");
            System.out.println("Please report this to md_5");
            System.out.println("Spigot Version: " + Bukkit.getBukkitVersion());
            e.printStackTrace();
        }
    }

    public void updatePlayerTimers() {
        if (world.getWorld().hasStorm()) {
            if (canceled) {
                return;
            }
            if (stormIntensity == null) {
                ThunderChangeEvent thunder = new ThunderChangeEvent(world.getWorld(), true);
                Bukkit.getPluginManager().callEvent(thunder);
                if (thunder.isCancelled()) {
                    canceled = true;
                    return;
                }
                stormIntensity = Intensity.getRandomIntensity(world.random);
                System.out.println("Started a storm of type " + stormIntensity.name() + " in world [" + world.worldData.getName() + "]");
            }
            List<EntityPlayer> toStrike = new ArrayList<EntityPlayer>();
            for (Object o : world.players) {
                if (o instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) o;
                    Integer ticksLeft = playerCountdown.get(player);
                    if (ticksLeft == null) {
                        playerCountdown.put(player, getTicksBeforeNextLightning(world.random));
                    } else if (ticksLeft == 1) {
                        //weed out dc'd players
                        if (!player.netServerHandler.disconnected) {
                            toStrike.add(player);
                            playerCountdown.put(player, getTicksBeforeNextLightning(world.random));
                        }
                    } else {
                        playerCountdown.put(player, ticksLeft - 1);
                    }
                }
            }
            strikePlayers(toStrike);
        } else {
            stormIntensity = null;
            canceled = false;
        }
    }

    public void strikePlayers(List<EntityPlayer> toStrike) {
        for (EntityPlayer player : toStrike) {
            final int posX = MathHelper.floor(player.locX);
            final int posY = MathHelper.floor(player.locY);
            final int posZ = MathHelper.floor(player.locZ);
            for (int tries = 0; tries < 10; tries++) {
                //pick a random chunk between -4, -4, to 4, 4 relative to the player's position to strike at
                int cx = (world.random.nextBoolean() ? -1 : 1) * world.random.nextInt(5);
                int cz = (world.random.nextBoolean() ? -1 : 1) * world.random.nextInt(5);

                //pick random coords to try to strike at inside the chunk (0, 0) to (15, 15)
                int rx = world.random.nextInt(16);
                int rz = world.random.nextInt(16);

                //pick a offset from the player's y position to strike at (-15 - +15) of their position
                int offsetY = (world.random.nextBoolean() ? -1 : 1) * world.random.nextInt(15);

                int x = cx * 16 + rx + posX;
                int y = posY + offsetY;
                int z = cz * 16 + rz + posZ;

                if (isRainingAt(x, y, z)) {
                    int lightning = 1;
                    //30% chance of extra lightning at the spot
                    if (world.random.nextInt(10) < 3) {
                        lightning += world.random.nextInt(MAX_LIGHTNING_BRANCHES);
                    }
                    for (int strikes = 0; strikes < lightning; strikes++) {
                        double adjustX = 0.5D;
                        double adjustY = 0.0D;
                        double adjustZ = 0.5D;
                        //if there are extra strikes, tweak their placement slightly
                        if (strikes > 0) {
                            adjustX += (world.random.nextBoolean() ? -1 : 1) * world.random.nextInt(2);
                            adjustY += (world.random.nextBoolean() ? -1 : 1) * world.random.nextInt(8);
                            adjustZ += (world.random.nextBoolean() ? -1 : 1) * world.random.nextInt(2);
                        }
                        EntityLightning lightningStrike = new EntityLightning(world, x + adjustX, y + adjustY, z + adjustZ);
                        world.strikeLightning(lightningStrike);
                    }
                    //success, go to the next player
                    break;
                }
            }
        }
    }

    public int getTicksBeforeNextLightning(Random rand) {
        return stormIntensity.baseTicks + rand.nextInt(stormIntensity.randomTicks);
    }

    public boolean isRainingAt(int x, int y, int z) {
        return world.D(x, y, z);
    }
}

enum Intensity {

    STRONG_ELECTRICAL_STORM(5, 10, 20),
    ELECTRICAL_STORM(15, 40, 150),
    STRONG_THUNDERSTORM(30, 60, 250),
    THUNDERSTORM(50, 100, 500),
    WEAK_THUNDERSTORM(75, 300, 1000),
    RAINSTORM(100, 500, 2000);
    int chance, baseTicks, randomTicks;

    Intensity(int chance, int baseTicks, int randomTicks) {
        this.chance = chance;
        this.baseTicks = baseTicks;
        this.randomTicks = randomTicks;
    }

    public static Intensity getRandomIntensity(Random rand) {
        int r = rand.nextInt(100);
        if (r < STRONG_ELECTRICAL_STORM.chance) {
            return STRONG_ELECTRICAL_STORM;
        }
        if (r < ELECTRICAL_STORM.chance) {
            return ELECTRICAL_STORM;
        }
        if (r < STRONG_THUNDERSTORM.chance) {
            return STRONG_THUNDERSTORM;
        }
        if (r < THUNDERSTORM.chance) {
            return THUNDERSTORM;
        }
        if (r < WEAK_THUNDERSTORM.chance) {
            return WEAK_THUNDERSTORM;
        }
        return RAINSTORM;
    }
}
