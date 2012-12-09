package net.minecraft.server;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityTypes
{
	/** Provides a mapping between entity classes and a string */
	public static Map b = new HashMap();

	/** Provides a mapping between a string and an entity classes */
	public static Map c = new HashMap();

	/** provides a mapping between an entityID and an Entity Class */
	public static Map d = new HashMap();

	/** provides a mapping between an Entity Class and an entity ID */
	private static Map e = new HashMap();

	/** Maps entity names to their numeric identifiers */
	private static Map f = new HashMap();

	/** This is a HashMap of the Creative Entity Eggs/Spawners. */
	public static HashMap a = new LinkedHashMap();

	public static void a(Class paramClass, String paramString, int paramInt) {
		b.put(paramString, paramClass);
		c.put(paramClass, paramString);
		d.put(Integer.valueOf(paramInt), paramClass);
		e.put(paramClass, Integer.valueOf(paramInt));
		f.put(paramString, Integer.valueOf(paramInt));
	}

	public static void a(Class paramClass, String paramString, int paramInt1, int paramInt2, int paramInt3) {
		a(paramClass, paramString, paramInt1);

		a.put(Integer.valueOf(paramInt1), new MonsterEggInfo(paramInt1, paramInt2, paramInt3));
	}

	public static Entity createEntityByName(String paramString, World paramWorld)
	{
		Entity localEntity = null;
		try {
			Class localClass = (Class)b.get(paramString);
			if (localClass != null) localEntity = (Entity)localClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld }); 
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
		return localEntity;
	}

	public static Entity a(NBTTagCompound paramNBTTagCompound, World paramWorld) {
		Entity localEntity = null;
		try {
			Class localClass = (Class)b.get(paramNBTTagCompound.getString("id"));
			if (localClass != null) localEntity = (Entity)localClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld }); 
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
		if (localEntity != null)
			localEntity.e(paramNBTTagCompound);
		else {
			System.out.println("Skipping Entity with id " + paramNBTTagCompound.getString("id"));
		}
		return localEntity;
	}

	public static Entity a(int paramInt, World paramWorld) {
		Entity localEntity = null;
		try {
			Class localClass = (Class)d.get(Integer.valueOf(paramInt));
			if (localClass != null) localEntity = (Entity)localClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld }); 
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
		if (localEntity == null)
		{
			System.out.println("Skipping Entity with id " + paramInt);
		}
		return localEntity;
	}

	public static int a(Entity paramEntity) {
		Class localClass = paramEntity.getClass();

		return e.containsKey(localClass) ? ((Integer)e.get(localClass)).intValue() : 0;
	}

	public static String b(Entity paramEntity) {
		return (String)c.get(paramEntity.getClass());
	}

	public static String a(int paramInt)
	{
		Class localClass = (Class)d.get(Integer.valueOf(paramInt));

		if (localClass != null) {
			return (String)c.get(localClass);
		}

		return null;
	}

   public static String b(int var0) {
      Class var1 = (Class)d.get(Integer.valueOf(var0));
      return var1 != null?(String)c.get(var1):null;
   }

	static
	{
		a(EntityItem.class, "Item", 1);
		a(EntityExperienceOrb.class, "XPOrb", 2);

		a(EntityPainting.class, "Painting", 9);
		a(EntityArrow.class, "Arrow", 10);
		a(EntitySnowball.class, "Snowball", 11);
		a(EntityLargeFireball.class, "Fireball", 12);
		a(EntitySmallFireball.class, "SmallFireball", 13);
		a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
		a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
		a(EntityPotion.class, "ThrownPotion", 16);
		a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
		a(EntityItemFrame.class, "ItemFrame", 18);
		a(EntityWitherSkull.class, "WitherSkull", 19);

		a(EntityTNTPrimed.class, "PrimedTnt", 20);
		a(EntityFallingBlock.class, "FallingSand", 21);

		a(EntityMinecart.class, "Minecart", 40);
		a(EntityBoat.class, "Boat", 41);

		a(EntityLiving.class, "Mob", 48);
		a(EntityMonster.class, "Monster", 49);

		a(EntityCreeper.class, "Creeper", 50, 894731, 0);
		a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
		a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
		a(EntityGiantZombie.class, "Giant", 53);
		a(EntityZombie.class, "Zombie", 54, 44975, 7969893);
		a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
		a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
		a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
		a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
		a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
		a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
		a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
		a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
		a(EntityEnderDragon.class, "EnderDragon", 63);
		a(EntityWither.class, "WitherBoss", 64);
		a(EntityBat.class, "Bat", 65, 4996656, 986895);
		a(EntityWitch.class, "Witch", 66, 3407872, 5349438);

		a(EntityPig.class, "Pig", 90, 15771042, 14377823);
		a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
		a(EntityCow.class, "Cow", 92, 4470310, 10592673);
		a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
		a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
		a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
		a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
		a(EntitySnowman.class, "SnowMan", 97);
		a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
		a(EntityIronGolem.class, "VillagerGolem", 99);

		a(EntityVillager.class, "Villager", 120, 5651507, 12422002);

		a(EntityEnderCrystal.class, "EnderCrystal", 200);
	}
}