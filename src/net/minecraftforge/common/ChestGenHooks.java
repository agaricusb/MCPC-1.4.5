package net.minecraftforge.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.server.ItemStack;
import net.minecraft.server.StructurePieceTreasure;
import net.minecraft.server.WorldGenJungleTemple;
import net.minecraft.server.WorldGenMineshaftPieces;
import net.minecraft.server.WorldGenPyramidPiece;
import net.minecraft.server.WorldGenStrongholdChestCorridor;
import net.minecraft.server.WorldGenStrongholdLibrary;
import net.minecraft.server.WorldGenStrongholdRoomCrossing;
import net.minecraft.server.WorldGenVillageBlacksmith;
import net.minecraft.server.WorldServer;

public class ChestGenHooks
{
    //Currently implemented categories for chests/dispensers, Dungeon loot is still in DungeonHooks
    public static final String MINESHAFT_CORRIDOR = "mineshaftCorridor";
    public static final String PYRAMID_DESERT_CHEST = "pyramidDesertyChest";
    public static final String PYRAMID_JUNGLE_CHEST = "pyramidJungleChest";
    public static final String PYRAMID_JUNGLE_DISPENSER = "pyramidJungleDispenser";
    public static final String STRONGHOLD_CORRIDOR = "strongholdCorridor";
    public static final String STRONGHOLD_LIBRARY = "strongholdLibrary";
    public static final String STRONGHOLD_CROSSING = "strongholdCrossing";
    public static final String VILLAGE_BLACKSMITH = "villageBlacksmith";
    public static final String BONUS_CHEST = "bonusChest";

    private static final HashMap<String, ChestGenHooks> chestInfo = new HashMap<String, ChestGenHooks>();
    private static boolean hasInit = false;
    static 
    {
        init();
    }

    private static void init()
    {
        if (!hasInit)
        {
            addInfo("mineshaftCorridor", WorldGenMineshaftPieces.a, 3, 7);
            addInfo("pyramidDesertyChest", WorldGenPyramidPiece.i, 2, 7);
            addInfo("pyramidJungleChest", WorldGenJungleTemple.l, 2, 7);
            addInfo("pyramidJungleDispenser", WorldGenJungleTemple.m, 2, 2);
            addInfo("strongholdCorridor", WorldGenStrongholdChestCorridor.a, 2, 4);
            addInfo("strongholdLibrary", WorldGenStrongholdLibrary.b, 1, 5);
            addInfo("strongholdCrossing", WorldGenStrongholdRoomCrossing.c, 1, 5);
            addInfo("villageBlacksmith", WorldGenVillageBlacksmith.a, 3, 9);
            addInfo("bonusChest", WorldServer.T, 10, 10);
        }
    }

    private static void addInfo(String category, StructurePieceTreasure[] items, int min, int max)
    {
        chestInfo.put(category, new ChestGenHooks(category, items, min, max));
    }

    /**
     * Retrieves, or creates the info class for the specified category.
     * 
     * @param category The category name
     * @return A instance of ChestGenHooks for the specified category.
     */
    public static ChestGenHooks getInfo(String category)
    {
        if (!chestInfo.containsKey(category))
        {
            chestInfo.put(category, new ChestGenHooks(category));
        }
        return chestInfo.get(category);
    }

    /**
     * Generates an array of items based on the input min/max count.
     * If the stack can not hold the total amount, it will be split into 
     * stacks of size 1.
     * 
     * @param rand A random number generator
     * @param source Source item stack
     * @param min Minimum number of items
     * @param max Maximum number of items
     * @return An array containing the generated item stacks 
     */
    public static ItemStack[] generateStacks(Random rand, ItemStack source, int min, int max)
    {
        int count = min + (rand.nextInt(max - min + 1));

        ItemStack[] ret;
        if (source.getItem() == null)
        {
            ret = new ItemStack[0];
        }
        else if (count > source.getItem().getMaxStackSize())
        {
            ret = new ItemStack[count];
            for (int x = 0; x < count; x++)
            {
                ret[x] = source.cloneItemStack();
                ret[x].count = 1;
            }
        }
        else
        {
            ret = new ItemStack[1];
            ret[0] = source.cloneItemStack();
            ret[0].count = count;
        }
        return ret;
    }

    //shortcut functions, See the non-static versions below
    public static StructurePieceTreasure[] getItems(String category){ return getInfo(category).getItems(); }
    public static int getCount(String category, Random rand){ return getInfo(category).getCount(rand); }
    public static void addItem(String category, StructurePieceTreasure item){ getInfo(category).addItem(item); }
    public static void removeItem(String category, ItemStack item){ getInfo(category).removeItem(item); }

    private String category;
    private int countMin = 0;
    private int countMax = 0;
    private ArrayList<StructurePieceTreasure> contents = new ArrayList<StructurePieceTreasure>();

    public ChestGenHooks(String category)
    {
        this.category = category;
    }

    public ChestGenHooks(String category, StructurePieceTreasure[] items, int min, int max)
    {
        this(category);
        for (StructurePieceTreasure item : items)
        {
            contents.add(item);
        }
        countMin = min;
        countMax = max;
    }

    /**
     * Adds a new entry into the possible items to generate.
     * 
     * @param item The item to add.
     */
    public void addItem(StructurePieceTreasure item)
    {
        contents.add(item);
    }

    /**
     * Removes all items that match the input item stack, Only metadata and item ID are checked.
     * If the input item has a metadata of -1, all metadatas will match.
     * 
     * @param item The item to check
     */
    public void removeItem(ItemStack item)
    {
        Iterator<StructurePieceTreasure> itr = contents.iterator();
        while(itr.hasNext())
        {
            StructurePieceTreasure cont = itr.next();
            if (item.doMaterialsMatch(cont.itemStack) || (item.getData() == -1 && item.id == cont.itemStack.id))
            {
                itr.remove();
            }
        }
    }

    /**
     * Gets an array of all random objects that are associated with this category.
     * 
     * @return The random objects
     */
    public StructurePieceTreasure[] getItems()
    {
        return contents.toArray(new StructurePieceTreasure[contents.size()]);
    }

    /**
     * Gets a random number between countMin and countMax.
     * 
     * @param rand A RNG
     * @return A random number where countMin <= num <= countMax
     */
    public int getCount(Random rand)
    {
        return countMin < countMax ? countMin + rand.nextInt(countMax - countMin) : countMin;
    }

    //Accessors
    public int getMin(){ return countMin; }
    public int getMax(){ return countMax; }
    public void setMin(int value){ countMin = value; }
    public void setMax(int value){ countMax = value; }
}