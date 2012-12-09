package net.minecraftforge.oredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import org.bukkit.inventory.Recipe;

import net.minecraft.server.Block;
import net.minecraft.server.IRecipe;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ShapelessRecipes;
import net.minecraft.server.World;

public class ShapelessOreRecipe implements IRecipe 
{
    private ItemStack output = null;
    private ArrayList input = new ArrayList();    

    public ShapelessOreRecipe(Block result, Object... recipe){ this(new ItemStack(result), recipe); }
    public ShapelessOreRecipe(Item  result, Object... recipe){ this(new ItemStack(result), recipe); }
    
    public ShapelessOreRecipe(ItemStack result, Object... recipe)
    {
        output = result.cloneItemStack();
        for (Object in : recipe)
        {
            if (in instanceof ItemStack)
            {
                input.add(((ItemStack)in).cloneItemStack());
            }
            else if (in instanceof Item)
            {
                input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                input.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
                input.add(OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    ShapelessOreRecipe(ShapelessRecipes recipe, Map<ItemStack, String> replacements)
    {
        output = recipe.b();

        for(ItemStack ingred : ((List<ItemStack>)recipe.ingredients))
        {
            Object finalObj = ingred;
            for(Entry<ItemStack, String> replace : replacements.entrySet())
            {
                if(OreDictionary.itemMatches(replace.getKey(), ingred, false))
                {
                    finalObj = OreDictionary.getOres(replace.getValue());
                    break;
                }
            }
            input.add(finalObj);
        }
    }

    @Override
    public int a(){ return input.size(); }

    @Override
    public ItemStack b(){ return output; }
    
    @Override
    public ItemStack a(InventoryCrafting var1){ return output.cloneItemStack(); }
    
    @Override
    public boolean a(InventoryCrafting var1, World world) 
    {
        ArrayList required = new ArrayList(input);

        for (int x = 0; x < var1.getSize(); x++)
        {
            ItemStack slot = var1.getItem(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;
                    
                    Object next = req.next();
                    
                    if (next instanceof ItemStack)
                    {
                        match = checkItemEquals((ItemStack)next, slot);
                    }
                    else if (next instanceof ArrayList)
                    {
                        for (ItemStack item : (ArrayList<ItemStack>)next)
                        {
                            match = match || checkItemEquals(item, slot);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }
    
    private boolean checkItemEquals(ItemStack target, ItemStack input)
    {
        return (target.id == input.id && (target.getData() == -1 || target.getData() == input.getData()));
    }
	@Override
	public Recipe toBukkitRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}