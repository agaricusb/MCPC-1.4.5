package net.minecraftforge.common;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

import net.minecraft.server.*;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent.*;

public class ForgeHooks
{
    static class GrassEntry extends WeightedRandomChoice
    {
        public final Block block;
        public final int metadata;
        public GrassEntry(Block block, int meta, int weight)
        {
            super(weight);
            this.block = block;
            this.metadata = meta;
        }
    }

    static class SeedEntry extends WeightedRandomChoice
    {
        public final ItemStack seed;
        public SeedEntry(ItemStack seed, int weight)
        {
            super(weight);
            this.seed = seed;
        }
    }
    static final List<GrassEntry> grassList = new ArrayList<GrassEntry>();
    static final List<SeedEntry> seedList = new ArrayList<SeedEntry>();

    public static void plantGrass(World world, int x, int y, int z)
    {
        GrassEntry grass = (GrassEntry)WeightedRandom.a(world.random, grassList);
        if (grass == null || grass.block == null || !grass.block.d(world, x, y, z))
        {
            return;
        }
        world.setTypeIdAndData(x, y, z, grass.block.id, grass.metadata);
    }

    public static ItemStack getGrassSeed(World world)
    {
        SeedEntry entry = (SeedEntry)WeightedRandom.a(world.random, seedList);
        if (entry == null || entry.seed == null)
        {
            return null;
        }
        return entry.seed.cloneItemStack();
    }

    private static boolean toolInit = false;
    static HashMap<Item, List> toolClasses = new HashMap<Item, List>();
    static HashMap<List, Integer> toolHarvestLevels = new HashMap<List, Integer>();
    static HashSet<List> toolEffectiveness = new HashSet<List>();

    public static boolean canHarvestBlock(Block block, EntityHuman player, int metadata)
    {
        if (block.material.isAlwaysDestroyable())
        {
            return true;
        }

        ItemStack stack = player.inventory.getItemInHand();
        if (stack == null)
        {
            return player.b(block);
        }

        List info = (List)toolClasses.get(stack.getItem());
        if (info == null)
        {
            return player.b(block);
        }

        Object[] tmp = info.toArray();
        String toolClass = (String)tmp[0];
        int harvestLevel = (Integer)tmp[1];

        Integer blockHarvestLevel = (Integer)toolHarvestLevels.get(Arrays.asList(block, metadata, toolClass));
        if (blockHarvestLevel == null)
        {
            return player.b(block);
        }

        if (blockHarvestLevel > harvestLevel)
        {
            return false;
        }
        return true;
    }

    public static float blockStrength(Block block, EntityHuman player, World world, int x, int y, int z)
    {
        int metadata = world.getData(x, y, z);
        float hardness = block.m(world, x, y, z);
        if (hardness < 0.0F)
        {
            return 0.0F;
        }

        if (!canHarvestBlock(block, player, metadata))
        {
            float speed = ForgeEventFactory.getBreakSpeed(player, block, metadata, 1.0f);
            return (speed < 0 ? 0 : speed) / hardness / 100F;
        }
        else
        {
             return player.getCurrentPlayerStrVsBlock(block, metadata) / hardness / 30F;
        }
    }

    public static boolean isToolEffective(ItemStack stack, Block block, int metadata)
    {
        List toolClass = (List)toolClasses.get(stack.getItem());
        if (toolClass == null)
        {
            return false;
        }
        return toolEffectiveness.contains(Arrays.asList(block, metadata, (String)toolClass.get(0)));
    }

    static void initTools()
    {
        if (toolInit)
        {
            return;
        }
        toolInit = true;

            MinecraftForge.setToolClass(Item.WOOD_PICKAXE, "pickaxe", 0);
            MinecraftForge.setToolClass(Item.STONE_PICKAXE, "pickaxe", 1);
            MinecraftForge.setToolClass(Item.IRON_PICKAXE, "pickaxe", 2);
            MinecraftForge.setToolClass(Item.GOLD_PICKAXE, "pickaxe", 0);
            MinecraftForge.setToolClass(Item.DIAMOND_PICKAXE, "pickaxe", 3);
            MinecraftForge.setToolClass(Item.WOOD_AXE, "axe", 0);
            MinecraftForge.setToolClass(Item.STONE_AXE, "axe", 1);
            MinecraftForge.setToolClass(Item.IRON_AXE, "axe", 2);
            MinecraftForge.setToolClass(Item.GOLD_AXE, "axe", 0);
            MinecraftForge.setToolClass(Item.DIAMOND_AXE, "axe", 3);
            MinecraftForge.setToolClass(Item.WOOD_SPADE, "shovel", 0);
            MinecraftForge.setToolClass(Item.STONE_SPADE, "shovel", 1);
            MinecraftForge.setToolClass(Item.IRON_SPADE, "shovel", 2);
            MinecraftForge.setToolClass(Item.GOLD_SPADE, "shovel", 0);
            MinecraftForge.setToolClass(Item.DIAMOND_SPADE, "shovel", 3);

        for (Block block : ItemPickaxe.c)
        {
            MinecraftForge.setBlockHarvestLevel(block, "pickaxe", 0);
        }

        for (Block block : ItemSpade.c)
        {
            MinecraftForge.setBlockHarvestLevel(block, "shovel", 0);
        }

        for (Block block : ItemAxe.c)
        {
            MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
        }

            MinecraftForge.setBlockHarvestLevel(Block.OBSIDIAN, "pickaxe", 3);
            MinecraftForge.setBlockHarvestLevel(Block.EMERALD_ORE, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(Block.DIAMOND_ORE, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(Block.DIAMOND_BLOCK, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(Block.GOLD_ORE, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(Block.GOLD_BLOCK, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(Block.IRON_ORE, "pickaxe", 1);
            MinecraftForge.setBlockHarvestLevel(Block.IRON_BLOCK, "pickaxe", 1);
            MinecraftForge.setBlockHarvestLevel(Block.LAPIS_ORE, "pickaxe", 1);
            MinecraftForge.setBlockHarvestLevel(Block.LAPIS_BLOCK, "pickaxe", 1);
            MinecraftForge.setBlockHarvestLevel(Block.REDSTONE_ORE, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(Block.GLOWING_REDSTONE_ORE, "pickaxe", 2);
            MinecraftForge.removeBlockEffectiveness(Block.REDSTONE_ORE, "pickaxe");
            MinecraftForge.removeBlockEffectiveness(Block.OBSIDIAN, "pickaxe");
            MinecraftForge.removeBlockEffectiveness(Block.GLOWING_REDSTONE_ORE, "pickaxe");
    }

    public static String getTexture(String _default, Object obj)
    {
        if (obj instanceof Item)
        {
            return ((Item)obj).getTextureFile();
        }
        else if (obj instanceof Block)
        {
            return ((Block)obj).getTextureFile();
        }
        else
        {
            return _default;
        }
    }

    public static int getTotalArmorValue(EntityHuman player)
    {
        int ret = 0;
        for (int x = 0; x < player.inventory.armor.length; x++)
        {
            ItemStack stack = player.inventory.armor[x];
            if (stack != null && stack.getItem() instanceof ISpecialArmor)
            {
                ret += ((ISpecialArmor)stack.getItem()).getArmorDisplay(player, stack, x);
            }
            else if (stack != null && stack.getItem() instanceof ItemArmor)
            {
                ret += ((ItemArmor)stack.getItem()).b;
            }
        }
        return ret;
    }

    static
    {
        grassList.add(new GrassEntry(Block.YELLOW_FLOWER, 0, 20));
        grassList.add(new GrassEntry(Block.RED_ROSE,    0, 10));
        seedList.add(new SeedEntry(new ItemStack(Item.SEEDS), 10));
        initTools();
    }

    /**
     * Called when a player uses 'pick block', calls new Entity and Block hooks.
     */
    public static boolean onPickBlock(MovingObjectPosition target, EntityHuman player, World world)
    {
        ItemStack result = null;
        boolean isCreative = player.abilities.canInstantlyBuild;

        if (target.type == EnumMovingObjectType.TILE)
        {
            int x = target.b;
            int y = target.c;
            int z = target.d;
            Block var8 = Block.byId[world.getTypeId(x, y, z)];

            if (var8 == null)
            {
                return false;
            }

            result = var8.getPickBlock(target, world, x, y, z);
        }
        else
        {
            if (target.type != EnumMovingObjectType.ENTITY || target.entity == null || !isCreative)
            {
                return false;
            }

            result = target.entity.getPickedResult(target);
        }

        if (result == null)
        {
            return false;
        }

        for (int x = 0; x < 9; x++)
        {
            ItemStack stack = player.inventory.getItem(x);
            if (stack != null && stack.doMaterialsMatch(result) && ItemStack.equals(stack, result))
            {
                player.inventory.itemInHandIndex = x;
                return true;
            }
        }

        if (!isCreative)
        {
            return false;
        }

        int slot = player.inventory.i();
        if (slot < 0 || slot >= 9)
        {
            slot = player.inventory.itemInHandIndex;
        }

        player.inventory.setItem(slot, result);
        player.inventory.itemInHandIndex = slot;
        return true;
    }

    //Optifine Helper Functions u.u, these are here specifically for Optifine
    //Note: When using Optfine, these methods are invoked using reflection, which
    //incurs a major performance penalty.
    public static void onLivingSetAttackTarget(EntityLiving entity, EntityLiving target)
    {
        MinecraftForge.EVENT_BUS.post(new LivingSetAttackTargetEvent(entity, target));
    }

    public static boolean onLivingUpdate(EntityLiving entity)
    {
        return MinecraftForge.EVENT_BUS.post(new LivingUpdateEvent(entity));
    }

    public static boolean onLivingAttack(EntityLiving entity, DamageSource src, int amount)
    {
        return MinecraftForge.EVENT_BUS.post(new LivingAttackEvent(entity, src, amount));
    }

    public static int onLivingHurt(EntityLiving entity, DamageSource src, int amount)
    {
        LivingHurtEvent event = new LivingHurtEvent(entity, src, amount);
        return (MinecraftForge.EVENT_BUS.post(event) ? 0 : event.ammount);
    }

    public static boolean onLivingDeath(EntityLiving entity, DamageSource src)
    {
        return MinecraftForge.EVENT_BUS.post(new LivingDeathEvent(entity, src));
    }

    public static boolean onLivingDrops(EntityLiving entity, DamageSource source, ArrayList<EntityItem> drops, int lootingLevel, boolean recentlyHit, int specialDropValue)
    {
        return MinecraftForge.EVENT_BUS.post(new LivingDropsEvent(entity, source, drops, lootingLevel, recentlyHit, specialDropValue));
    }

    public static float onLivingFall(EntityLiving entity, float distance)
    {
        LivingFallEvent event = new LivingFallEvent(entity, distance);
        return (MinecraftForge.EVENT_BUS.post(event) ? 0.0f : event.distance);
    }

    public static boolean isLivingOnLadder(Block block, World world, int x, int y, int z)
    {
        return block != null && block.isLadder(world, x, y, z);
    }

    public static void onLivingJump(EntityLiving entity)
    {
        MinecraftForge.EVENT_BUS.post(new LivingJumpEvent(entity));
    }

    public static EntityItem onPlayerTossEvent(EntityHuman player, ItemStack item)
    {
        player.captureDrops = true;
        EntityItem ret = player.a(item, false);
        player.capturedDrops.clear();
        player.captureDrops = false;

        ItemTossEvent event = new ItemTossEvent(ret, player);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return null;
        }

        player.a(event.entityItem);
        return event.entityItem;
    }
}