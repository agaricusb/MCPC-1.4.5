package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.VillagerRegistry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class EntityVillager extends EntityAgeable implements NPC, IMerchant
{
    private int profession;
    private boolean f;
    private boolean g;
    Village village;

    /** This villager's current customer. */
    private EntityHuman h;

    /** Initialises the MerchantRecipeList.java */
    private MerchantRecipeList i;
    private int j;

    /** addDefaultEquipmentAndRecipies is called if this is true */
    private boolean bI;
    private int bJ;

    /** Last player to trade with this villager, used for aggressivity. */
    private String bK;
    private boolean bL;
    private float bM;

    /** Selling list of Villagers items. */
    public static final Map bN = new HashMap();

    /** Selling list of Blacksmith items. */
    public static final Map bO = new HashMap();

    public EntityVillager(World var1)
    {
        this(var1, 0);
    }

    public EntityVillager(World var1, int var2)
    {
        super(var1);
        this.profession = 0;
        this.f = false;
        this.g = false;
        this.village = null;
        this.setProfession(var2);
        this.texture = "/mob/villager/villager.png";
        this.bG = 0.5F;
        this.getNavigation().b(true);
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalAvoidPlayer(this, EntityZombie.class, 8.0F, 0.3F, 0.35F));
        this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
        this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
        this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
        this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.3F));
        this.goalSelector.a(6, new PathfinderGoalMakeLove(this));
        this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));
        this.goalSelector.a(8, new PathfinderGoalPlay(this, 0.32F));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityVillager.class, 5.0F, 0.02F));
        this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.3F));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityLiving.class, 8.0F));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void bm()
    {
        if (--this.profession <= 0)
        {
            this.world.villages.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
            this.profession = 70 + this.random.nextInt(50);
            this.village = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);

            if (this.village == null)
            {
                this.aL();
            }
            else
            {
                ChunkCoordinates var1 = this.village.getCenter();
                this.b(var1.x, var1.y, var1.z, (int)((float)this.village.getSize() * 0.6F));

                if (this.bL)
                {
                    this.bL = false;
                    this.village.b(5);
                }
            }
        }

        if (!this.p() && this.j > 0)
        {
            --this.j;

            if (this.j <= 0)
            {
                if (this.bI)
                {
                    if (this.i.size() > 1)
                    {
                        Iterator var3 = this.i.iterator();

                        while (var3.hasNext())
                        {
                            MerchantRecipe var2 = (MerchantRecipe)var3.next();

                            if (var2.g())
                            {
                                var2.a(this.random.nextInt(6) + this.random.nextInt(6) + 2);
                            }
                        }
                    }

                    this.t(1);
                    this.bI = false;

                    if (this.village != null && this.bK != null)
                    {
                        this.world.broadcastEntityEffect(this, (byte)14);
                        this.village.a(this.bK, 1);
                    }
                }

                this.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 200, 0));
            }
        }

        super.bm();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman var1)
    {
      ItemStack var2 = var1.inventory.getItemInHand();
      boolean var3 = var2 != null && var2.id == Item.MONSTER_EGG.id;
        if (!var3 && this.isAlive() && !this.p() && !this.isBaby())
        {
            if (!this.world.isStatic)
            {
                this.b_(var1);
                var1.openTrade(this);
            }

            return true;
        }
        else
        {
            return super.a(var1);
        }
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Integer.valueOf(0));
    }

    public int getMaxHealth()
    {
        return 20;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        var1.setInt("Profession", this.getProfession());
        var1.setInt("Riches", this.bJ);

        if (this.i != null)
        {
            var1.setCompound("Offers", this.i.a());
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        this.setProfession(var1.getInt("Profession"));
        this.bJ = var1.getInt("Riches");

        if (var1.hasKey("Offers"))
        {
            NBTTagCompound var2 = var1.getCompound("Offers");
            this.i = new MerchantRecipeList(var2);
        }
    }


    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean bj()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.villager.default";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.villager.defaulthurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.villager.defaultdeath";
    }

    public void setProfession(int var1)
    {
        this.datawatcher.watch(16, Integer.valueOf(var1));
    }

    public int getProfession()
    {
        return this.datawatcher.getInt(16);
    }

    public boolean n()
    {
        return this.f;
    }

    public void f(boolean var1)
    {
        this.f = var1;
    }

    public void g(boolean var1)
    {
        this.g = var1;
    }

    public boolean o()
    {
        return this.g;
    }

    public void c(EntityLiving var1)
    {
        super.c(var1);

        if (this.village != null && var1 != null)
        {
            this.village.a(var1);

            if (var1 instanceof EntityHuman)
            {
                byte var2 = -1;

                if (this.isBaby())
                {
                    var2 = -3;
                }

                this.village.a(((EntityHuman)var1).getName(), var2);

                if (this.isAlive())
                {
                    this.world.broadcastEntityEffect(this, (byte)13);
                }
            }
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource var1)
    {
        if (this.village != null)
        {
            Entity var2 = var1.getEntity();

            if (var2 != null)
            {
                if (var2 instanceof EntityHuman)
                {
                    this.village.a(((EntityHuman)var2).getName(), -2);
                }
                else if (var2 instanceof IMonster)
                {
                    this.village.h();
                }
            }
            else if (var2 == null)
            {
                EntityHuman var3 = this.world.findNearbyPlayer(this, 16.0D);

                if (var3 != null)
                {
                    this.village.h();
                }
            }
        }

        super.die(var1);
    }

    public void b_(EntityHuman var1)
    {
        this.h = var1;
    }

    public EntityHuman m_()
    {
        return this.h;
    }

    public boolean p()
    {
        return this.h != null;
    }

    public void a(MerchantRecipe var1)
    {
        var1.f();

        if (var1.a((MerchantRecipe)this.i.get(this.i.size() - 1)))
        {
            this.j = 40;
            this.bI = true;

            if (this.h != null)
            {
                this.bK = this.h.getName();
            }
            else
            {
                this.bK = null;
            }
        }

        if (var1.getBuyItem1().id == Item.EMERALD.id)
        {
            this.bJ += var1.getBuyItem1().count;
        }
    }

    public MerchantRecipeList getOffers(EntityHuman var1)
    {
        if (this.i == null)
        {
            this.t(1);
        }

        return this.i;
    }

    private float j(float var1)
    {
        float var2 = var1 + this.bM;
        return var2 > 0.9F ? 0.9F - (var2 - 0.9F) : var2;
    }

    /**
     * based on the villagers profession add items, equipment, and recipies adds par1 random items to the list of things
     * that the villager wants to buy. (at most 1 of each wanted type is added)
     */
    private void t(int var1)
    {
        if (this.i != null)
        {
            this.bM = MathHelper.c((float)this.i.size()) * 0.2F;
        }
        else
        {
            this.bM = 0.0F;
        }

        MerchantRecipeList var2;
        var2 = new MerchantRecipeList();
        VillagerRegistry.manageVillagerTrades(var2, this, this.getProfession(), this.random);
        label49:

        switch (this.getProfession())
        {
            case 0:
                a(var2, Item.WHEAT.id, this.random, this.j(0.9F));
                a(var2, Block.WOOL.id, this.random, this.j(0.5F));
                a(var2, Item.RAW_CHICKEN.id, this.random, this.j(0.5F));
                a(var2, Item.COOKED_FISH.id, this.random, this.j(0.4F));
                b(var2, Item.BREAD.id, this.random, this.j(0.9F));
                b(var2, Item.MELON.id, this.random, this.j(0.3F));
                b(var2, Item.APPLE.id, this.random, this.j(0.3F));
                b(var2, Item.COOKIE.id, this.random, this.j(0.3F));
                b(var2, Item.SHEARS.id, this.random, this.j(0.3F));
                b(var2, Item.FLINT_AND_STEEL.id, this.random, this.j(0.3F));
                b(var2, Item.COOKED_CHICKEN.id, this.random, this.j(0.3F));
                b(var2, Item.ARROW.id, this.random, this.j(0.5F));

                if (this.random.nextFloat() < this.j(0.5F))
                {
                    var2.add(new MerchantRecipe(new ItemStack(Block.GRAVEL, 10), new ItemStack(Item.EMERALD), new ItemStack(Item.FLINT.id, 4 + this.random.nextInt(2), 0)));
                }

                break;

            case 1:
                a(var2, Item.PAPER.id, this.random, this.j(0.8F));
                a(var2, Item.BOOK.id, this.random, this.j(0.8F));
                a(var2, Item.WRITTEN_BOOK.id, this.random, this.j(0.3F));
                b(var2, Block.BOOKSHELF.id, this.random, this.j(0.8F));
                b(var2, Block.GLASS.id, this.random, this.j(0.2F));
                b(var2, Item.COMPASS.id, this.random, this.j(0.2F));
                b(var2, Item.WATCH.id, this.random, this.j(0.2F));
                break;

            case 2:
                b(var2, Item.EYE_OF_ENDER.id, this.random, this.j(0.3F));
                b(var2, Item.EXP_BOTTLE.id, this.random, this.j(0.2F));
                b(var2, Item.REDSTONE.id, this.random, this.j(0.4F));
                b(var2, Block.GLOWSTONE.id, this.random, this.j(0.3F));
                int[] var3 = new int[] {Item.IRON_SWORD.id, Item.DIAMOND_SWORD.id, Item.IRON_CHESTPLATE.id, Item.DIAMOND_CHESTPLATE.id, Item.IRON_AXE.id, Item.DIAMOND_AXE.id, Item.IRON_PICKAXE.id, Item.DIAMOND_PICKAXE.id};
                int[] var4 = var3;
                int var5 = var3.length;
                int var6 = 0;

                while (true)
                {
                    if (var6 >= var5)
                    {
                        break label49;
                    }

                    int var7 = var4[var6];

                    if (this.random.nextFloat() < this.j(0.05F))
                    {
                        var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Item.EMERALD, 2 + this.random.nextInt(3), 0), EnchantmentManager.a(this.random, new ItemStack(var7, 1, 0), 5 + this.random.nextInt(15))));
                    }

                    ++var6;
                }

            case 3:
                a(var2, Item.COAL.id, this.random, this.j(0.7F));
                a(var2, Item.IRON_INGOT.id, this.random, this.j(0.5F));
                a(var2, Item.GOLD_INGOT.id, this.random, this.j(0.5F));
                a(var2, Item.DIAMOND.id, this.random, this.j(0.5F));
                b(var2, Item.IRON_SWORD.id, this.random, this.j(0.5F));
                b(var2, Item.DIAMOND_SWORD.id, this.random, this.j(0.5F));
                b(var2, Item.IRON_AXE.id, this.random, this.j(0.3F));
                b(var2, Item.DIAMOND_AXE.id, this.random, this.j(0.3F));
                b(var2, Item.IRON_PICKAXE.id, this.random, this.j(0.5F));
                b(var2, Item.DIAMOND_PICKAXE.id, this.random, this.j(0.5F));
                b(var2, Item.IRON_SPADE.id, this.random, this.j(0.2F));
                b(var2, Item.DIAMOND_SPADE.id, this.random, this.j(0.2F));
                b(var2, Item.IRON_HOE.id, this.random, this.j(0.2F));
                b(var2, Item.DIAMOND_HOE.id, this.random, this.j(0.2F));
                b(var2, Item.IRON_BOOTS.id, this.random, this.j(0.2F));
                b(var2, Item.DIAMOND_BOOTS.id, this.random, this.j(0.2F));
                b(var2, Item.IRON_HELMET.id, this.random, this.j(0.2F));
                b(var2, Item.DIAMOND_HELMET.id, this.random, this.j(0.2F));
                b(var2, Item.IRON_CHESTPLATE.id, this.random, this.j(0.2F));
                b(var2, Item.DIAMOND_CHESTPLATE.id, this.random, this.j(0.2F));
                b(var2, Item.IRON_LEGGINGS.id, this.random, this.j(0.2F));
                b(var2, Item.DIAMOND_LEGGINGS.id, this.random, this.j(0.2F));
                b(var2, Item.CHAINMAIL_BOOTS.id, this.random, this.j(0.1F));
                b(var2, Item.CHAINMAIL_HELMET.id, this.random, this.j(0.1F));
                b(var2, Item.CHAINMAIL_CHESTPLATE.id, this.random, this.j(0.1F));
                b(var2, Item.CHAINMAIL_LEGGINGS.id, this.random, this.j(0.1F));
                break;

            case 4:
                a(var2, Item.COAL.id, this.random, this.j(0.7F));
                a(var2, Item.PORK.id, this.random, this.j(0.5F));
                a(var2, Item.RAW_BEEF.id, this.random, this.j(0.5F));
                b(var2, Item.SADDLE.id, this.random, this.j(0.1F));
                b(var2, Item.LEATHER_CHESTPLATE.id, this.random, this.j(0.3F));
                b(var2, Item.LEATHER_BOOTS.id, this.random, this.j(0.3F));
                b(var2, Item.LEATHER_HELMET.id, this.random, this.j(0.3F));
                b(var2, Item.LEATHER_LEGGINGS.id, this.random, this.j(0.3F));
                b(var2, Item.GRILLED_PORK.id, this.random, this.j(0.3F));
                b(var2, Item.COOKED_BEEF.id, this.random, this.j(0.3F));
        }

        if (var2.isEmpty())
        {
            a(var2, Item.GOLD_INGOT.id, this.random, 1.0F);
        }

        Collections.shuffle(var2);

        if (this.i == null)
        {
            this.i = new MerchantRecipeList();
        }

        for (int var8 = 0; var8 < var1 && var8 < var2.size(); ++var8)
        {
            this.i.a((MerchantRecipe)var2.get(var8));
        }
    }

    /**
     * each recipie takes a random stack from villagerStockList and offers it for 1 emerald
     */
    public static void a(MerchantRecipeList var0, int var1, Random var2, float var3)
    {
        if (var2.nextFloat() < var3)
        {
            var0.add(new MerchantRecipe(a(var1, var2), Item.EMERALD));
        }
    }

    private static ItemStack a(int var0, Random var1)
    {
        return new ItemStack(var0, b(var0, var1), 0);
    }

    /**
     * default to 1, and villagerStockList contains a min/max amount for each index
     */
    private static int b(int var0, Random var1)
    {
        Tuple var2 = (Tuple)bN.get(Integer.valueOf(var0));
        return var2 == null ? 1 : (((Integer)var2.a()).intValue() >= ((Integer)var2.b()).intValue() ? ((Integer)var2.a()).intValue() : ((Integer)var2.a()).intValue() + var1.nextInt(((Integer)var2.b()).intValue() - ((Integer)var2.a()).intValue()));
    }

    public static void b(MerchantRecipeList var0, int var1, Random var2, float var3)
    {
        if (var2.nextFloat() < var3)
        {
            int var4 = c(var1, var2);
            ItemStack var5;
            ItemStack var6;

            if (var4 < 0)
            {
                var5 = new ItemStack(Item.EMERALD.id, 1, 0);
                var6 = new ItemStack(var1, -var4, 0);
            }
            else
            {
                var5 = new ItemStack(Item.EMERALD.id, var4, 0);
                var6 = new ItemStack(var1, 1, 0);
            }

            var0.add(new MerchantRecipe(var5, var6));
        }
    }

    private static int c(int var0, Random var1)
    {
        Tuple var2 = (Tuple)bO.get(Integer.valueOf(var0));
        return var2 == null ? 1 : (((Integer)var2.a()).intValue() >= ((Integer)var2.b()).intValue() ? ((Integer)var2.a()).intValue() : ((Integer)var2.a()).intValue() + var1.nextInt(((Integer)var2.b()).intValue() - ((Integer)var2.a()).intValue()));
    }

    /**
     * Initialize this creature.
     */
    public void bG()
    {
        this.setProfession(this.world.random.nextInt(5));
        VillagerRegistry.applyRandomTrade(this, this.world.random);
    }

    public void q()
    {
        this.bL = true;
    }

    static
    {
        bN.put(Integer.valueOf(Item.COAL.id), new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
        bN.put(Integer.valueOf(Item.IRON_INGOT.id), new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        bN.put(Integer.valueOf(Item.GOLD_INGOT.id), new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        bN.put(Integer.valueOf(Item.DIAMOND.id), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bN.put(Integer.valueOf(Item.PAPER.id), new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
        bN.put(Integer.valueOf(Item.BOOK.id), new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
        bN.put(Integer.valueOf(Item.WRITTEN_BOOK.id), new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
        bN.put(Integer.valueOf(Item.ENDER_PEARL.id), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bN.put(Integer.valueOf(Item.EYE_OF_ENDER.id), new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
        bN.put(Integer.valueOf(Item.PORK.id), new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        bN.put(Integer.valueOf(Item.RAW_BEEF.id), new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        bN.put(Integer.valueOf(Item.RAW_CHICKEN.id), new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        bN.put(Integer.valueOf(Item.COOKED_FISH.id), new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
        bN.put(Integer.valueOf(Item.SEEDS.id), new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
        bN.put(Integer.valueOf(Item.MELON_SEEDS.id), new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
        bN.put(Integer.valueOf(Item.PUMPKIN_SEEDS.id), new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
        bN.put(Integer.valueOf(Item.WHEAT.id), new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
        bN.put(Integer.valueOf(Block.WOOL.id), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
        bN.put(Integer.valueOf(Item.ROTTEN_FLESH.id), new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
        bO.put(Integer.valueOf(Item.FLINT_AND_STEEL.id), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bO.put(Integer.valueOf(Item.SHEARS.id), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bO.put(Integer.valueOf(Item.IRON_SWORD.id), new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        bO.put(Integer.valueOf(Item.DIAMOND_SWORD.id), new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
        bO.put(Integer.valueOf(Item.IRON_AXE.id), new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
        bO.put(Integer.valueOf(Item.DIAMOND_AXE.id), new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
        bO.put(Integer.valueOf(Item.IRON_PICKAXE.id), new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
        bO.put(Integer.valueOf(Item.DIAMOND_PICKAXE.id), new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        bO.put(Integer.valueOf(Item.IRON_SPADE.id), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bO.put(Integer.valueOf(Item.DIAMOND_SPADE.id), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bO.put(Integer.valueOf(Item.IRON_HOE.id), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bO.put(Integer.valueOf(Item.DIAMOND_HOE.id), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bO.put(Integer.valueOf(Item.IRON_BOOTS.id), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bO.put(Integer.valueOf(Item.DIAMOND_BOOTS.id), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bO.put(Integer.valueOf(Item.IRON_HELMET.id), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bO.put(Integer.valueOf(Item.DIAMOND_HELMET.id), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bO.put(Integer.valueOf(Item.IRON_CHESTPLATE.id), new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
        bO.put(Integer.valueOf(Item.DIAMOND_CHESTPLATE.id), new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
        bO.put(Integer.valueOf(Item.IRON_LEGGINGS.id), new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        bO.put(Integer.valueOf(Item.DIAMOND_LEGGINGS.id), new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
        bO.put(Integer.valueOf(Item.CHAINMAIL_BOOTS.id), new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
        bO.put(Integer.valueOf(Item.CHAINMAIL_HELMET.id), new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
        bO.put(Integer.valueOf(Item.CHAINMAIL_CHESTPLATE.id), new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
        bO.put(Integer.valueOf(Item.CHAINMAIL_LEGGINGS.id), new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
        bO.put(Integer.valueOf(Item.BREAD.id), new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
        bO.put(Integer.valueOf(Item.MELON.id), new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
        bO.put(Integer.valueOf(Item.APPLE.id), new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
        bO.put(Integer.valueOf(Item.COOKIE.id), new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
        bO.put(Integer.valueOf(Block.GLASS.id), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
        bO.put(Integer.valueOf(Block.BOOKSHELF.id), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bO.put(Integer.valueOf(Item.LEATHER_CHESTPLATE.id), new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
        bO.put(Integer.valueOf(Item.LEATHER_BOOTS.id), new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        bO.put(Integer.valueOf(Item.LEATHER_HELMET.id), new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        bO.put(Integer.valueOf(Item.LEATHER_LEGGINGS.id), new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        bO.put(Integer.valueOf(Item.SADDLE.id), new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
        bO.put(Integer.valueOf(Item.EXP_BOTTLE.id), new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
        bO.put(Integer.valueOf(Item.REDSTONE.id), new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
        bO.put(Integer.valueOf(Item.COMPASS.id), new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        bO.put(Integer.valueOf(Item.WATCH.id), new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        bO.put(Integer.valueOf(Block.GLOWSTONE.id), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
        bO.put(Integer.valueOf(Item.GRILLED_PORK.id), new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
        bO.put(Integer.valueOf(Item.COOKED_BEEF.id), new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
        bO.put(Integer.valueOf(Item.COOKED_CHICKEN.id), new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
        bO.put(Integer.valueOf(Item.EYE_OF_ENDER.id), new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        bO.put(Integer.valueOf(Item.ARROW.id), new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    }

	@Override
	public EntityAgeable createChild(EntityAgeable arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
}