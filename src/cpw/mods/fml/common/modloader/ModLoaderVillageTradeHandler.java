package cpw.mods.fml.common.modloader;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.server.EntityVillager;
import net.minecraft.server.Item;
import net.minecraft.server.MerchantRecipeList;
import net.minecraft.server.TradeEntry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class ModLoaderVillageTradeHandler implements IVillageTradeHandler
{
    private List<TradeEntry> trades = Lists.newArrayList();

    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        for (TradeEntry ent : trades)
        {
            if (ent.buying)
            {
                VillagerRegistry.addEmeraldBuyRecipe(villager, recipeList, random, Item.byId[ent.id], ent.chance, ent.min, ent.max);
            }
            else
            {
                VillagerRegistry.addEmeraldSellRecipe(villager, recipeList, random, Item.byId[ent.id], ent.chance, ent.min, ent.max);
            }
        }
    }

    public void addTrade(TradeEntry entry)
    {
        trades.add(entry);
    }
}
