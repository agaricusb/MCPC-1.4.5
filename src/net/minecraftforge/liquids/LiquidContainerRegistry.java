
package net.minecraftforge.liquids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public class LiquidContainerRegistry {

    public static final int BUCKET_VOLUME = 1000;
    public static final ItemStack EMPTY_BUCKET = new ItemStack(Item.BUCKET);

    private static Map<List, LiquidContainerData> mapFilledItemFromLiquid = new HashMap();
    private static Map<List, LiquidContainerData> mapLiquidFromFilledItem = new HashMap();
    private static Set<List> setContainerValidation = new HashSet();
    private static Set<List> setLiquidValidation = new HashSet();
    private static ArrayList<LiquidContainerData> liquids = new ArrayList();

    /**
     * Default registrations
     */
    static {
        registerLiquid(new LiquidContainerData(new LiquidStack(Block.STATIONARY_WATER, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(Item.WATER_BUCKET), new ItemStack(Item.BUCKET)));
        registerLiquid(new LiquidContainerData(new LiquidStack(Block.STATIONARY_LAVA, LiquidContainerRegistry.BUCKET_VOLUME),  new ItemStack(Item.LAVA_BUCKET), new ItemStack(Item.BUCKET)));
        registerLiquid(new LiquidContainerData(new LiquidStack(Block.STATIONARY_WATER, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(Item.POTION), new ItemStack(Item.GLASS_BOTTLE)));
        // registerLiquid(new LiquidContainerData(new LiquidStack(Item.bucketMilk, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));
    }

    /**
     * To register a container with a non-bucket size, the LiquidContainerData entry simply needs to use a size other than LiquidManager.BUCKET_VOLUME
     */
    public static void registerLiquid(LiquidContainerData data) {

        mapFilledItemFromLiquid.put(Arrays.asList(data.container.id, data.container.getData(), data.stillLiquid.itemID, data.stillLiquid.itemMeta), data);
        mapLiquidFromFilledItem.put(Arrays.asList(data.filled.id, data.filled.getData()), data);
        setContainerValidation.add(Arrays.asList(data.container.id, data.container.getData()));
        setLiquidValidation.add(Arrays.asList(data.stillLiquid.itemID, data.stillLiquid.itemMeta));

        liquids.add(data);
    }

    public static LiquidStack getLiquidForFilledItem(ItemStack filledContainer) {

        if (filledContainer == null) {
            return null;
        }
        LiquidContainerData ret = mapLiquidFromFilledItem.get(Arrays.asList(filledContainer.id, filledContainer.getData()));
        if (ret != null) {
            return ret.stillLiquid.copy();
        }
        return null;
    }

    public static ItemStack fillLiquidContainer(LiquidStack liquid, ItemStack emptyContainer) {

        if (emptyContainer == null || liquid == null) {
            return null;
        }
        LiquidContainerData ret = mapFilledItemFromLiquid.get(Arrays.asList(emptyContainer.id, emptyContainer.getData(), liquid.itemID, liquid.itemMeta));
        if (ret != null) {
            if (liquid.amount >= ret.stillLiquid.amount) {
                return ret.filled.cloneItemStack();
            }
        }
        return null;
    }

    public static boolean containsLiquid(ItemStack filledContainer, LiquidStack liquid) {

        if (filledContainer == null || liquid == null) {
            return false;
        }
        LiquidContainerData ret = mapLiquidFromFilledItem.get(Arrays.asList(filledContainer.id, filledContainer.getData()));
        if (ret != null) {
            return ret.stillLiquid.isLiquidEqual(liquid);
        }
        return false;
    }

    public static boolean isBucket(ItemStack container) {

        if (container == null) {
            return false;
        }

        if (container.doMaterialsMatch(EMPTY_BUCKET)) {
            return true;
        }

        LiquidContainerData ret = mapLiquidFromFilledItem.get(Arrays.asList(container.id, container.getData()));
        if (ret != null) {
            return ret.container.doMaterialsMatch(EMPTY_BUCKET);
        }
        return false;
    }

    public static boolean isContainer(ItemStack container) {

        return isEmptyContainer(container) || isFilledContainer(container);
    }

    public static boolean isEmptyContainer(ItemStack emptyContainer) {

        if (emptyContainer == null) {
            return false;
        }
        return setContainerValidation.contains(Arrays.asList(emptyContainer.id, emptyContainer.getData()));
    }

    public static boolean isFilledContainer(ItemStack filledContainer) {

        if (filledContainer == null) {
            return false;
        }
        return getLiquidForFilledItem(filledContainer) != null;
    }

    public static boolean isLiquid(ItemStack item) {

        if (item == null) {
            return false;
        }
        return setLiquidValidation.contains(Arrays.asList(item.id, item.getData()));
    }

    public static LiquidContainerData[] getRegisteredLiquidContainerData() {

        return liquids.toArray(new LiquidContainerData[0]);
    }
}
