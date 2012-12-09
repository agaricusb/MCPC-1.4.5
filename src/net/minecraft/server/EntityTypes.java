package net.minecraft.server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityBat;
import net.minecraft.server.EntityBlaze;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityCaveSpider;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityEnderCrystal;
import net.minecraft.server.EntityEnderDragon;
import net.minecraft.server.EntityEnderPearl;
import net.minecraft.server.EntityEnderSignal;
import net.minecraft.server.EntityEnderman;
import net.minecraft.server.EntityExperienceOrb;
import net.minecraft.server.EntityFallingBlock;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityGiantZombie;
import net.minecraft.server.EntityIronGolem;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityItemFrame;
import net.minecraft.server.EntityLargeFireball;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMagmaCube;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.EntityMushroomCow;
import net.minecraft.server.EntityOcelot;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntityPotion;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySilverfish;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.EntitySmallFireball;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.EntitySnowman;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntitySquid;
import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.EntityThrownExpBottle;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.EntityWitch;
import net.minecraft.server.EntityWither;
import net.minecraft.server.EntityWitherSkull;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.MonsterEggInfo;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntityTypes {

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


   public static void a(Class var0, String var1, int var2) {
      b.put(var1, var0);
      c.put(var0, var1);
      d.put(Integer.valueOf(var2), var0);
      e.put(var0, Integer.valueOf(var2));
      f.put(var1, Integer.valueOf(var2));
   }

   public static void a(Class var0, String var1, int var2, int var3, int var4) {
      a(var0, var1, var2);
      a.put(Integer.valueOf(var2), new MonsterEggInfo(var2, var3, var4));
   }

   public static Entity createEntityByName(String var0, World var1) {
      Entity localEntity = null;

      try {
         Class localClass = (Class)b.get(var0);
         if(localClass != null) {
            localEntity = (Entity)localClass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{var1});
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return localEntity;
   }

   public static Entity a(NBTTagCompound var0, World var1) {
      Entity var2 = null;
      Class var3 = null;
      try {
         var3 = (Class)b.get(var0.getString("id"));
         if(var3 != null) {
            var2 = (Entity)var3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{var1});
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      if(var2 != null) 
      {
    	  try
          {
              var2.e(var0);
          }
          catch (Exception e)
          {
              FMLLog.log(Level.SEVERE, e,
                      "An Entity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
                      var0.getString("id"), var3.getName());
              var2 = null;
          }
      } 
      else {
         System.out.println("Skipping Entity with id " + var0.getString("id"));
      }

      return var2;
   }

   public static Entity a(int var0, World var1) {
      Entity var2 = null;

      try {
         Class var3 = (Class)d.get(Integer.valueOf(var0));
         if(var3 != null) {
            var2 = (Entity)var3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{var1});
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      if(var2 == null) {
         System.out.println("Skipping Entity with id " + var0);
      }

      return var2;
   }

   public static int a(Entity var0) {
      Class var1 = var0.getClass();
      return e.containsKey(var1)?((Integer)e.get(var1)).intValue():0;
   }

   public static Class a(int var0) {
      return (Class)d.get(Integer.valueOf(var0));
   }

   public static String b(Entity var0) {
      return (String)c.get(var0.getClass());
   }

   public static String b(int var0) {
      Class var1 = (Class)d.get(Integer.valueOf(var0));
      return var1 != null?(String)c.get(var1):null;
   }

   static {
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
      a(EntityZombie.class, "Zombie", 54, '\uafaf', 7969893);
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