package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.CreativeModeTab;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItemFrame;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EnumAnimation;
import net.minecraft.server.EnumArmorMaterial;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemArmor;
import net.minecraft.server.ItemAxe;
import net.minecraft.server.ItemBed;
import net.minecraft.server.ItemBoat;
import net.minecraft.server.ItemBookAndQuill;
import net.minecraft.server.ItemBow;
import net.minecraft.server.ItemBucket;
import net.minecraft.server.ItemCarrotStick;
import net.minecraft.server.ItemCoal;
import net.minecraft.server.ItemDoor;
import net.minecraft.server.ItemDye;
import net.minecraft.server.ItemEgg;
import net.minecraft.server.ItemEnderEye;
import net.minecraft.server.ItemEnderPearl;
import net.minecraft.server.ItemExpBottle;
import net.minecraft.server.ItemFireball;
import net.minecraft.server.ItemFishingRod;
import net.minecraft.server.ItemFlintAndSteel;
import net.minecraft.server.ItemFood;
import net.minecraft.server.ItemGlassBottle;
import net.minecraft.server.ItemGoldenApple;
import net.minecraft.server.ItemHanging;
import net.minecraft.server.ItemHoe;
import net.minecraft.server.ItemMapEmpty;
import net.minecraft.server.ItemMilkBucket;
import net.minecraft.server.ItemMinecart;
import net.minecraft.server.ItemMonsterEgg;
import net.minecraft.server.ItemNetherStar;
import net.minecraft.server.ItemPickaxe;
import net.minecraft.server.ItemPotion;
import net.minecraft.server.ItemRecord;
import net.minecraft.server.ItemRedstone;
import net.minecraft.server.ItemReed;
import net.minecraft.server.ItemSaddle;
import net.minecraft.server.ItemSeedFood;
import net.minecraft.server.ItemSeeds;
import net.minecraft.server.ItemShears;
import net.minecraft.server.ItemSign;
import net.minecraft.server.ItemSkull;
import net.minecraft.server.ItemSnowball;
import net.minecraft.server.ItemSoup;
import net.minecraft.server.ItemSpade;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;
import net.minecraft.server.ItemWorldMap;
import net.minecraft.server.ItemWrittenBook;
import net.minecraft.server.LocaleI18n;
import net.minecraft.server.LocaleLanguage;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MobEffectList;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.PotionBrewer;
import net.minecraft.server.StatisticList;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class Item {

   private CreativeModeTab a = null;
   protected static Random d = new Random();
   public static Item[] byId = new Item[32000];
   public static Item IRON_SPADE = (new ItemSpade(0, EnumToolMaterial.IRON)).b(2, 5).b("shovelIron");
   public static Item IRON_PICKAXE = (new ItemPickaxe(1, EnumToolMaterial.IRON)).b(2, 6).b("pickaxeIron");
   public static Item IRON_AXE = (new ItemAxe(2, EnumToolMaterial.IRON)).b(2, 7).b("hatchetIron");
   public static Item FLINT_AND_STEEL = (new ItemFlintAndSteel(3)).b(5, 0).b("flintAndSteel");
   public static Item APPLE = (new ItemFood(4, 4, 0.3F, false)).b(10, 0).b("apple");
   public static Item BOW = (new ItemBow(5)).b(5, 1).b("bow");
   public static Item ARROW = (new Item(6)).b(5, 2).b("arrow").a(CreativeModeTab.j);
   public static Item COAL = (new ItemCoal(7)).b(7, 0).b("coal");
   public static Item DIAMOND = (new Item(8)).b(7, 3).b("diamond").a(CreativeModeTab.l);
   public static Item IRON_INGOT = (new Item(9)).b(7, 1).b("ingotIron").a(CreativeModeTab.l);
   public static Item GOLD_INGOT = (new Item(10)).b(7, 2).b("ingotGold").a(CreativeModeTab.l);
   public static Item IRON_SWORD = (new ItemSword(11, EnumToolMaterial.IRON)).b(2, 4).b("swordIron");
   public static Item WOOD_SWORD = (new ItemSword(12, EnumToolMaterial.WOOD)).b(0, 4).b("swordWood");
   public static Item WOOD_SPADE = (new ItemSpade(13, EnumToolMaterial.WOOD)).b(0, 5).b("shovelWood");
   public static Item WOOD_PICKAXE = (new ItemPickaxe(14, EnumToolMaterial.WOOD)).b(0, 6).b("pickaxeWood");
   public static Item WOOD_AXE = (new ItemAxe(15, EnumToolMaterial.WOOD)).b(0, 7).b("hatchetWood");
   public static Item STONE_SWORD = (new ItemSword(16, EnumToolMaterial.STONE)).b(1, 4).b("swordStone");
   public static Item STONE_SPADE = (new ItemSpade(17, EnumToolMaterial.STONE)).b(1, 5).b("shovelStone");
   public static Item STONE_PICKAXE = (new ItemPickaxe(18, EnumToolMaterial.STONE)).b(1, 6).b("pickaxeStone");
   public static Item STONE_AXE = (new ItemAxe(19, EnumToolMaterial.STONE)).b(1, 7).b("hatchetStone");
   public static Item DIAMOND_SWORD = (new ItemSword(20, EnumToolMaterial.DIAMOND)).b(3, 4).b("swordDiamond");
   public static Item DIAMOND_SPADE = (new ItemSpade(21, EnumToolMaterial.DIAMOND)).b(3, 5).b("shovelDiamond");
   public static Item DIAMOND_PICKAXE = (new ItemPickaxe(22, EnumToolMaterial.DIAMOND)).b(3, 6).b("pickaxeDiamond");
   public static Item DIAMOND_AXE = (new ItemAxe(23, EnumToolMaterial.DIAMOND)).b(3, 7).b("hatchetDiamond");
   public static Item STICK = (new Item(24)).b(5, 3).o().b("stick").a(CreativeModeTab.l);
   public static Item BOWL = (new Item(25)).b(7, 4).b("bowl").a(CreativeModeTab.l);
   public static Item MUSHROOM_SOUP = (new ItemSoup(26, 6)).b(8, 4).b("mushroomStew");
   public static Item GOLD_SWORD = (new ItemSword(27, EnumToolMaterial.GOLD)).b(4, 4).b("swordGold");
   public static Item GOLD_SPADE = (new ItemSpade(28, EnumToolMaterial.GOLD)).b(4, 5).b("shovelGold");
   public static Item GOLD_PICKAXE = (new ItemPickaxe(29, EnumToolMaterial.GOLD)).b(4, 6).b("pickaxeGold");
   public static Item GOLD_AXE = (new ItemAxe(30, EnumToolMaterial.GOLD)).b(4, 7).b("hatchetGold");
   public static Item STRING = (new ItemReed(31, Block.TRIPWIRE)).b(8, 0).b("string").a(CreativeModeTab.l);
   public static Item FEATHER = (new Item(32)).b(8, 1).b("feather").a(CreativeModeTab.l);
   public static Item SULPHUR = (new Item(33)).b(8, 2).b("sulphur").c(PotionBrewer.k).a(CreativeModeTab.l);
   public static Item WOOD_HOE = (new ItemHoe(34, EnumToolMaterial.WOOD)).b(0, 8).b("hoeWood");
   public static Item STONE_HOE = (new ItemHoe(35, EnumToolMaterial.STONE)).b(1, 8).b("hoeStone");
   public static Item IRON_HOE = (new ItemHoe(36, EnumToolMaterial.IRON)).b(2, 8).b("hoeIron");
   public static Item DIAMOND_HOE = (new ItemHoe(37, EnumToolMaterial.DIAMOND)).b(3, 8).b("hoeDiamond");
   public static Item GOLD_HOE = (new ItemHoe(38, EnumToolMaterial.GOLD)).b(4, 8).b("hoeGold");
   public static Item SEEDS = (new ItemSeeds(39, Block.CROPS.id, Block.SOIL.id)).b(9, 0).b("seeds");
   public static Item WHEAT = (new Item(40)).b(9, 1).b("wheat").a(CreativeModeTab.l);
   public static Item BREAD = (new ItemFood(41, 5, 0.6F, false)).b(9, 2).b("bread");
   public static Item LEATHER_HELMET = (new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0)).b(0, 0).b("helmetCloth");
   public static Item LEATHER_CHESTPLATE = (new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1)).b(0, 1).b("chestplateCloth");
   public static Item LEATHER_LEGGINGS = (new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2)).b(0, 2).b("leggingsCloth");
   public static Item LEATHER_BOOTS = (new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3)).b(0, 3).b("bootsCloth");
   public static Item CHAINMAIL_HELMET = (new ItemArmor(46, EnumArmorMaterial.CHAIN, 1, 0)).b(1, 0).b("helmetChain");
   public static Item CHAINMAIL_CHESTPLATE = (new ItemArmor(47, EnumArmorMaterial.CHAIN, 1, 1)).b(1, 1).b("chestplateChain");
   public static Item CHAINMAIL_LEGGINGS = (new ItemArmor(48, EnumArmorMaterial.CHAIN, 1, 2)).b(1, 2).b("leggingsChain");
   public static Item CHAINMAIL_BOOTS = (new ItemArmor(49, EnumArmorMaterial.CHAIN, 1, 3)).b(1, 3).b("bootsChain");
   public static Item IRON_HELMET = (new ItemArmor(50, EnumArmorMaterial.IRON, 2, 0)).b(2, 0).b("helmetIron");
   public static Item IRON_CHESTPLATE = (new ItemArmor(51, EnumArmorMaterial.IRON, 2, 1)).b(2, 1).b("chestplateIron");
   public static Item IRON_LEGGINGS = (new ItemArmor(52, EnumArmorMaterial.IRON, 2, 2)).b(2, 2).b("leggingsIron");
   public static Item IRON_BOOTS = (new ItemArmor(53, EnumArmorMaterial.IRON, 2, 3)).b(2, 3).b("bootsIron");
   public static Item DIAMOND_HELMET = (new ItemArmor(54, EnumArmorMaterial.DIAMOND, 3, 0)).b(3, 0).b("helmetDiamond");
   public static Item DIAMOND_CHESTPLATE = (new ItemArmor(55, EnumArmorMaterial.DIAMOND, 3, 1)).b(3, 1).b("chestplateDiamond");
   public static Item DIAMOND_LEGGINGS = (new ItemArmor(56, EnumArmorMaterial.DIAMOND, 3, 2)).b(3, 2).b("leggingsDiamond");
   public static Item DIAMOND_BOOTS = (new ItemArmor(57, EnumArmorMaterial.DIAMOND, 3, 3)).b(3, 3).b("bootsDiamond");
   public static Item GOLD_HELMET = (new ItemArmor(58, EnumArmorMaterial.GOLD, 4, 0)).b(4, 0).b("helmetGold");
   public static Item GOLD_CHESTPLATE = (new ItemArmor(59, EnumArmorMaterial.GOLD, 4, 1)).b(4, 1).b("chestplateGold");
   public static Item GOLD_LEGGINGS = (new ItemArmor(60, EnumArmorMaterial.GOLD, 4, 2)).b(4, 2).b("leggingsGold");
   public static Item GOLD_BOOTS = (new ItemArmor(61, EnumArmorMaterial.GOLD, 4, 3)).b(4, 3).b("bootsGold");
   public static Item FLINT = (new Item(62)).b(6, 0).b("flint").a(CreativeModeTab.l);
   public static Item PORK = (new ItemFood(63, 3, 0.3F, true)).b(7, 5).b("porkchopRaw");
   public static Item GRILLED_PORK = (new ItemFood(64, 8, 0.8F, true)).b(8, 5).b("porkchopCooked");
   public static Item PAINTING = (new ItemHanging(65, EntityPainting.class)).b(10, 1).b("painting");
   public static Item GOLDEN_APPLE = (new ItemGoldenApple(66, 4, 1.2F, false)).j().a(MobEffectList.REGENERATION.id, 5, 0, 1.0F).b(11, 0).b("appleGold");
   public static Item SIGN = (new ItemSign(67)).b(10, 2).b("sign");
   public static Item WOOD_DOOR = (new ItemDoor(68, Material.WOOD)).b(11, 2).b("doorWood");
   public static Item BUCKET = (new ItemBucket(69, 0)).b(10, 4).b("bucket").d(16);
   public static Item WATER_BUCKET = (new ItemBucket(70, Block.WATER.id)).b(11, 4).b("bucketWater").a(BUCKET);
   public static Item LAVA_BUCKET = (new ItemBucket(71, Block.LAVA.id)).b(12, 4).b("bucketLava").a(BUCKET);
   public static Item MINECART = (new ItemMinecart(72, 0)).b(7, 8).b("minecart");
   public static Item SADDLE = (new ItemSaddle(73)).b(8, 6).b("saddle");
   public static Item IRON_DOOR = (new ItemDoor(74, Material.ORE)).b(12, 2).b("doorIron");
   public static Item REDSTONE = (new ItemRedstone(75)).b(8, 3).b("redstone").c(PotionBrewer.i);
   public static Item SNOW_BALL = (new ItemSnowball(76)).b(14, 0).b("snowball");
   public static Item BOAT = (new ItemBoat(77)).b(8, 8).b("boat");
   public static Item LEATHER = (new Item(78)).b(7, 6).b("leather").a(CreativeModeTab.l);
   public static Item MILK_BUCKET = (new ItemMilkBucket(79)).b(13, 4).b("milk").a(BUCKET);
   public static Item CLAY_BRICK = (new Item(80)).b(6, 1).b("brick").a(CreativeModeTab.l);
   public static Item CLAY_BALL = (new Item(81)).b(9, 3).b("clay").a(CreativeModeTab.l);
   public static Item SUGAR_CANE = (new ItemReed(82, Block.SUGAR_CANE_BLOCK)).b(11, 1).b("reeds").a(CreativeModeTab.l);
   public static Item PAPER = (new Item(83)).b(10, 3).b("paper").a(CreativeModeTab.f);
   public static Item BOOK = (new Item(84)).b(11, 3).b("book").a(CreativeModeTab.f);
   public static Item SLIME_BALL = (new Item(85)).b(14, 1).b("slimeball").a(CreativeModeTab.f);
   public static Item STORAGE_MINECART = (new ItemMinecart(86, 1)).b(7, 9).b("minecartChest");
   public static Item POWERED_MINECART = (new ItemMinecart(87, 2)).b(7, 10).b("minecartFurnace");
   public static Item EGG = (new ItemEgg(88)).b(12, 0).b("egg");
   public static Item COMPASS = (new Item(89)).b(6, 3).b("compass").a(CreativeModeTab.i);
   public static Item FISHING_ROD = (new ItemFishingRod(90)).b(5, 4).b("fishingRod");
   public static Item WATCH = (new Item(91)).b(6, 4).b("clock").a(CreativeModeTab.i);
   public static Item GLOWSTONE_DUST = (new Item(92)).b(9, 4).b("yellowDust").c(PotionBrewer.j).a(CreativeModeTab.l);
   public static Item RAW_FISH = (new ItemFood(93, 2, 0.3F, false)).b(9, 5).b("fishRaw");
   public static Item COOKED_FISH = (new ItemFood(94, 5, 0.6F, false)).b(10, 5).b("fishCooked");
   public static Item INK_SACK = (new ItemDye(95)).b(14, 4).b("dyePowder");
   public static Item BONE = (new Item(96)).b(12, 1).b("bone").o().a(CreativeModeTab.f);
   public static Item SUGAR = (new Item(97)).b(13, 0).b("sugar").c(PotionBrewer.b).a(CreativeModeTab.l);
   public static Item CAKE = (new ItemReed(98, Block.CAKE_BLOCK)).d(1).b(13, 1).b("cake").a(CreativeModeTab.h);
   public static Item BED = (new ItemBed(99)).d(1).b(13, 2).b("bed");
   public static Item DIODE = (new ItemReed(100, Block.DIODE_OFF)).b(6, 5).b("diode").a(CreativeModeTab.d);
   public static Item COOKIE = (new ItemFood(101, 2, 0.1F, false)).b(12, 5).b("cookie");
   public static ItemWorldMap MAP = (ItemWorldMap)(new ItemWorldMap(102)).b(12, 3).b("map");
   public static ItemShears SHEARS = (ItemShears)(new ItemShears(103)).b(13, 5).b("shears");
   public static Item MELON = (new ItemFood(104, 2, 0.3F, false)).b(13, 6).b("melon");
   public static Item PUMPKIN_SEEDS = (new ItemSeeds(105, Block.PUMPKIN_STEM.id, Block.SOIL.id)).b(13, 3).b("seeds_pumpkin");
   public static Item MELON_SEEDS = (new ItemSeeds(106, Block.MELON_STEM.id, Block.SOIL.id)).b(14, 3).b("seeds_melon");
   public static Item RAW_BEEF = (new ItemFood(107, 3, 0.3F, true)).b(9, 6).b("beefRaw");
   public static Item COOKED_BEEF = (new ItemFood(108, 8, 0.8F, true)).b(10, 6).b("beefCooked");
   public static Item RAW_CHICKEN = (new ItemFood(109, 2, 0.3F, true)).a(MobEffectList.HUNGER.id, 30, 0, 0.3F).b(9, 7).b("chickenRaw");
   public static Item COOKED_CHICKEN = (new ItemFood(110, 6, 0.6F, true)).b(10, 7).b("chickenCooked");
   public static Item ROTTEN_FLESH = (new ItemFood(111, 4, 0.1F, true)).a(MobEffectList.HUNGER.id, 30, 0, 0.8F).b(11, 5).b("rottenFlesh");
   public static Item ENDER_PEARL = (new ItemEnderPearl(112)).b(11, 6).b("enderPearl");
   public static Item BLAZE_ROD = (new Item(113)).b(12, 6).b("blazeRod").a(CreativeModeTab.l);
   public static Item GHAST_TEAR = (new Item(114)).b(11, 7).b("ghastTear").c(PotionBrewer.c).a(CreativeModeTab.k);
   public static Item GOLD_NUGGET = (new Item(115)).b(12, 7).b("goldNugget").a(CreativeModeTab.l);
   public static Item NETHER_STALK = (new ItemSeeds(116, Block.NETHER_WART.id, Block.SOUL_SAND.id)).b(13, 7).b("netherStalkSeeds").c("+4");
   public static ItemPotion POTION = (ItemPotion)(new ItemPotion(117)).b(13, 8).b("potion");
   public static Item GLASS_BOTTLE = (new ItemGlassBottle(118)).b(12, 8).b("glassBottle");
   public static Item SPIDER_EYE = (new ItemFood(119, 2, 0.8F, false)).a(MobEffectList.POISON.id, 5, 0, 1.0F).b(11, 8).b("spiderEye").c(PotionBrewer.d);
   public static Item FERMENTED_SPIDER_EYE = (new Item(120)).b(10, 8).b("fermentedSpiderEye").c(PotionBrewer.e).a(CreativeModeTab.k);
   public static Item BLAZE_POWDER = (new Item(121)).b(13, 9).b("blazePowder").c(PotionBrewer.g).a(CreativeModeTab.k);
   public static Item MAGMA_CREAM = (new Item(122)).b(13, 10).b("magmaCream").c(PotionBrewer.h).a(CreativeModeTab.k);
   public static Item BREWING_STAND = (new ItemReed(123, Block.BREWING_STAND)).b(12, 10).b("brewingStand").a(CreativeModeTab.k);
   public static Item CAULDRON = (new ItemReed(124, Block.CAULDRON)).b(12, 9).b("cauldron").a(CreativeModeTab.k);
   public static Item EYE_OF_ENDER = (new ItemEnderEye(125)).b(11, 9).b("eyeOfEnder");
   public static Item SPECKLED_MELON = (new Item(126)).b(9, 8).b("speckledMelon").c(PotionBrewer.f).a(CreativeModeTab.k);
   public static Item MONSTER_EGG = (new ItemMonsterEgg(127)).b(9, 9).b("monsterPlacer");
   public static Item EXP_BOTTLE = (new ItemExpBottle(128)).b(11, 10).b("expBottle");
   public static Item FIREBALL = (new ItemFireball(129)).b(14, 2).b("fireball");
   public static Item BOOK_AND_QUILL = (new ItemBookAndQuill(130)).b(11, 11).b("writingBook").a(CreativeModeTab.f);
   public static Item WRITTEN_BOOK = (new ItemWrittenBook(131)).b(12, 11).b("writtenBook");
   public static Item EMERALD = (new Item(132)).b(10, 11).b("emerald").a(CreativeModeTab.l);
   public static Item ITEM_FRAME = (new ItemHanging(133, EntityItemFrame.class)).b(14, 12).b("frame");
   public static Item FLOWER_POT = (new ItemReed(134, Block.FLOWER_POT)).b(13, 11).b("flowerPot").a(CreativeModeTab.c);
   public static Item CARROT = (new ItemSeedFood(135, 4, 0.6F, Block.CARROTS.id, Block.SOIL.id)).b(8, 7).b("carrots");
   public static Item POTATO = (new ItemSeedFood(136, 1, 0.3F, Block.POTATOES.id, Block.SOIL.id)).b(7, 7).b("potato");
   public static Item POTATO_BAKED = (new ItemFood(137, 6, 0.6F, false)).b(6, 7).b("potatoBaked");
   public static Item POTATO_POISON = (new ItemFood(138, 2, 0.3F, false)).a(MobEffectList.POISON.id, 5, 0, 0.6F).b(6, 8).b("potatoPoisonous");
   public static ItemMapEmpty MAP_EMPTY = (ItemMapEmpty)(new ItemMapEmpty(139)).b(13, 12).b("emptyMap");
   public static Item CARROT_GOLDEN = (new ItemFood(140, 6, 1.2F, false)).b(6, 9).b("carrotGolden").c(PotionBrewer.l);
   public static Item SKULL = (new ItemSkull(141)).b("skull");
   public static Item CARROT_STICK = (new ItemCarrotStick(142)).b(6, 6).b("carrotOnAStick");
   public static Item NETHER_STAR = (new ItemNetherStar(143)).b(9, 11).b("netherStar").a(CreativeModeTab.l);
   public static Item PUMPKIN_PIE = (new ItemFood(144, 8, 0.3F, false)).b(8, 9).b("pumpkinPie").a(CreativeModeTab.h);
   public static Item RECORD_1 = (new ItemRecord(2000, "13")).b(0, 15).b("record");
   public static Item RECORD_2 = (new ItemRecord(2001, "cat")).b(1, 15).b("record");
   public static Item RECORD_3 = (new ItemRecord(2002, "blocks")).b(2, 15).b("record");
   public static Item RECORD_4 = (new ItemRecord(2003, "chirp")).b(3, 15).b("record");
   public static Item RECORD_5 = (new ItemRecord(2004, "far")).b(4, 15).b("record");
   public static Item RECORD_6 = (new ItemRecord(2005, "mall")).b(5, 15).b("record");
   public static Item RECORD_7 = (new ItemRecord(2006, "mellohi")).b(6, 15).b("record");
   public static Item RECORD_8 = (new ItemRecord(2007, "stal")).b(7, 15).b("record");
   public static Item RECORD_9 = (new ItemRecord(2008, "strad")).b(8, 15).b("record");
   public static Item RECORD_10 = (new ItemRecord(2009, "ward")).b(9, 15).b("record");
   public static Item RECORD_11 = (new ItemRecord(2010, "11")).b(10, 15).b("record");
   public static Item RECORD_12 = (new ItemRecord(2011, "wait")).b(11, 15).b("record");
   public final int id;
   protected int maxStackSize = 64;
   private int durability = 0;
   protected int textureId;
   protected boolean cj = false;
   protected boolean ck = false;
   private Item craftingResult = null;
   private String cl = null;
   private String name;
   protected boolean canRepair = true;
   public boolean isDefaultTexture = true;
   private String currentTexture = "/gui/items.png";

   protected Item(int var1) {
      this.id = 256 + var1;
      if(byId[256 + var1] != null) {
         System.out.println("CONFLICT @ " + var1 + " item slot already occupied by " + byId[256 + var1] + " while adding " + this);
      }

      byId[256 + var1] = this;
      
      /* 
       * This is required for mods that use custom materials or else plugins such as ModifyWorld will receive NPE's 
       * when looking up the custom material name.
       */
      org.bukkit.Material.addMaterial(256 + var1);
      
      if (!(this instanceof ItemBlock))
      {
          this.isDefaultTexture = "/gui/items.png".equals(this.getTextureFile());
      }
   }

   public Item c(int var1) {
      this.textureId = var1;
      return this;
   }

   public Item d(int var1) {
      this.maxStackSize = var1;
      return this;
   }

   public Item b(int var1, int var2) {
      this.textureId = var1 + var2 * 16;
      return this;
   }

   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10) {
      return false;
   }

   public float getDestroySpeed(ItemStack var1, Block var2) {
      return 1.0F;
   }

   public ItemStack a(ItemStack var1, World var2, EntityHuman var3) {
      return var1;
   }

   public ItemStack b(ItemStack var1, World var2, EntityHuman var3) {
      return var1;
   }

   public int getMaxStackSize() {
      return this.maxStackSize;
   }

   public int filterData(int var1) {
      return 0;
   }

   public boolean l() {
      return this.ck;
   }

   protected Item a(boolean var1) {
      this.ck = var1;
      return this;
   }

   public int getMaxDurability() {
      return this.durability;
   }

   public Item setMaxDurability(int var1) {
      this.durability = var1;
      return this;
   }

   public boolean n() {
      return this.durability > 0 && !this.ck;
   }

   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3) {
      return false;
   }

   public boolean a(ItemStack var1, World var2, int var3, int var4, int var5, int var6, EntityLiving var7) {
      return false;
   }

   public int a(Entity var1) {
      return 1;
   }

   public boolean canDestroySpecialBlock(Block var1) {
      return false;
   }

   public boolean a(ItemStack var1, EntityLiving var2) {
      return false;
   }

   public Item o() {
      this.cj = true;
      return this;
   }

   public Item b(String var1) {
      this.name = "item." + var1;
      return this;
   }

   public String g(ItemStack var1) {
      String var2 = this.c_(var1);
      return var2 == null?"":LocaleI18n.get(var2);
   }

   public String getName() {
      return this.name;
   }

   public String c_(ItemStack var1) {
      return this.name;
   }

   public Item a(Item var1) {
      this.craftingResult = var1;
      return this;
   }

   public boolean h(ItemStack var1) {
      return true;
   }

   public boolean q() {
      return true;
   }

   public Item r() {
      return this.craftingResult;
   }

   public boolean s() {
      return this.craftingResult != null;
   }

   public String t() {
      return LocaleI18n.get(this.getName() + ".name");
   }

   public String i(ItemStack var1) {
      return LocaleI18n.get(this.c_(var1) + ".name");
   }

   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {}

   public void d(ItemStack var1, World var2, EntityHuman var3) {}

   public boolean f() {
      return false;
   }

   public EnumAnimation d_(ItemStack var1) {
      return EnumAnimation.a;
   }

   public int a(ItemStack var1) {
      return 0;
   }

   public void a(ItemStack var1, World var2, EntityHuman var3, int var4) {}

   protected Item c(String var1) {
      this.cl = var1;
      return this;
   }

   public String u() {
      return this.cl;
   }

   public boolean v() {
      return this.cl != null;
   }

   public String j(ItemStack var1) {
      return ("" + LocaleLanguage.a().c(this.g(var1))).trim();
   }

   public boolean k(ItemStack var1) {
      return this.getMaxStackSize() == 1 && this.n();
   }

   protected MovingObjectPosition a(World var1, EntityHuman var2, boolean var3) {
      float var4 = 1.0F;
      float var5 = var2.lastPitch + (var2.pitch - var2.lastPitch) * var4;
      float var6 = var2.lastYaw + (var2.yaw - var2.lastYaw) * var4;
      double var7 = var2.lastX + (var2.locX - var2.lastX) * (double)var4;
      double var9 = var2.lastY + (var2.locY - var2.lastY) * (double)var4 + 1.62D - (double)var2.height;
      double var11 = var2.lastZ + (var2.locZ - var2.lastZ) * (double)var4;
      Vec3D var13 = var1.getVec3DPool().create(var7, var9, var11);
      float var14 = MathHelper.cos(-var6 * 0.017453292F - 3.1415927F);
      float var15 = MathHelper.sin(-var6 * 0.017453292F - 3.1415927F);
      float var16 = -MathHelper.cos(-var5 * 0.017453292F);
      float var17 = MathHelper.sin(-var5 * 0.017453292F);
      float var18 = var15 * var16;
      float var20 = var14 * var16;
      double var21 = 5.0D;
      if (var2 instanceof EntityPlayer)
      {
          var21 = ((EntityPlayer)var2).itemInWorldManager.getBlockReachDistance();
      }
      Vec3D var23 = var13.add((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
      return var1.rayTrace(var13, var23, var3, !var3);
   }

   public int c() {
      return 0;
   }

   public Item a(CreativeModeTab var1) {
      this.a = var1;
      return this;
   }

   public boolean x() {
      return true;
   }

   public boolean a(ItemStack var1, ItemStack var2) {
      return false;
   }

     /**
     * Called when a player drops the item into the world,
     * returning false from this will prevent the item from
     * being removed from the players inventory and spawning
     * in the world
     *
     * @param player The player that dropped the item
     * @param item The item stack, before the item is removed.
     */
    public boolean onDroppedByPlayer(ItemStack item, EntityHuman player)
    {
        return true;
    }

    /**
     * This is called when the item is used, before the block is activated.
     * @param stack The Item Stack
     * @param player The Player that used the item
     * @param world The Current World
     * @param x Target X Position
     * @param y Target Y Position
     * @param z Target Z Position
     * @param side The side of the target hit
     * @return Return true to prevent any further processing.
     */
    public boolean onItemUseFirst(ItemStack stack, EntityHuman player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return onItemUseFirst(stack, player, world, x, y, z, side);
    }

    /**
     * See onItemUseFirst above, this is deprecated in favor of the more aware version.
     * Only here for compaibility.
     */
    @Deprecated
    public boolean onItemUseFirst(ItemStack stack, EntityHuman player, World world, int x, int y, int z, int side)
    {
        return false;
    }

    /**
     * Metadata-sensitive version of getStrVsBlock
     * @param itemstack The Item Stack
     * @param block The block the item is trying to break
     * @param metadata The items current metadata
     * @return The damage strength
     */
    public float getStrVsBlock(ItemStack itemstack, Block block, int metadata)
    {
        return getDestroySpeed(itemstack, block);
    }

    /**
     * Called by CraftingManager to determine if an item is reparable.
     * @return True if reparable
     */
    public boolean isRepairable()
    {
        return canRepair && this.n();
    }

    /**
     * Call to disable repair recipes.
     * @return The current Item instance
     */
    public Item setNoRepair()
    {
        canRepair = false;
        return this;
    }

    /**
     * Called before a block is broken.  Return true to prevent default block harvesting.
     *
     * Note: In SMP, this is called on both client and server sides!
     *
     * @param itemstack The current ItemStack
     * @param X The X Position
     * @param Y The X Position
     * @param Z The X Position
     * @param player The Player that is wielding the item
     * @return True to prevent harvesting, false to continue as normal
     */
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityHuman player)
    {
        return false;
    }

    /**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    public void onUsingItemTick(ItemStack stack, EntityHuman player, int count)
    {
    }

    /**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     *
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    public boolean onLeftClickEntity(ItemStack stack, EntityHuman player, Entity entity)
    {
        return false;
    }

    /**
     * Grabs the current texture file used for this block
     */
    public String getTextureFile()
    {
        if (this instanceof ItemBlock)
        {
            return Block.byId[((ItemBlock)this).g()].getTextureFile();
        }
        return currentTexture;
    }

    /**
     * Sets the current texture file for this item, used when rendering.
     * Default is "/gui/items.png"
     *
     * @param texture The texture file
     */
    public Item setTextureFile(String texture)
    {
        currentTexture = texture;
        isDefaultTexture = false;
        return this;
    }

    /**
     * ItemStack sensitive version of getContainerItem.
     * Returns a full ItemStack instance of the result.
     *
     * @param itemStack The current ItemStack
     * @return The resulting ItemStack
     */
    public ItemStack getContainerItemStack(ItemStack itemStack)
    {
        if (!this.s())
        {
            return null;
        }
        return new ItemStack(this.r());
    }

    /**
     * Retrieves the normal 'lifespan' of this item when it is dropped on the ground as a EntityItem.
     * This is in ticks, standard result is 6000, or 5 mins.
     *
     * @param itemStack The current ItemStack
     * @param world The world the entity is in
     * @return The normal lifespan in ticks.
     */
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 6000;
    }

    /**
     * Determines if this Item has a special entity for when they are in the world.
     * Is called when a EntityItem is spawned in the world, if true and Item#createCustomEntity
     * returns non null, the EntityItem will be destroyed and the new Entity will be added to the world.
     *
     * @param stack The current item stack
     * @return True of the item has a custom entity, If true, Item#createCustomEntity will be called
     */
    public boolean hasCustomEntity(ItemStack stack)
    {
        return false;
    }

    /**
     * This function should return a new entity to replace the dropped item.
     * Returning null here will not kill the EntityItem and will leave it to function normally.
     * Called when the item it placed in a world.
     *
     * @param world The world object
     * @param location The EntityItem object, useful for getting the position of the entity
     * @param itemstack The current item stack
     * @return A new Entity object to spawn or null
     */
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        return null;
    }

    /**
     * Determines the base experience for a player when they remove this item from a furnace slot.
     * This number must be between 0 and 1 for it to be valid.
     * This number will be multiplied by the stack size to get the total experience.
     *
     * @param item The item stack the player is picking up.
     * @return The amount to award for each item.
     */
    public float getSmeltingExperience(ItemStack item)
    {
        return -1; //-1 will default to the old lookups.
    }

   static {
      StatisticList.c();
   }
}