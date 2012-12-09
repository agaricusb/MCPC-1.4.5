package net.minecraft.server;

import org.bukkit.craftbukkit.block.CraftBlockState; // CraftBukkit
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class ItemSeeds extends Item implements IPlantable {

    private int id;
    private int b;

    public ItemSeeds(int i, int j, int k) {
        super(i);
        this.id = j;
        this.b = k;
        this.a(CreativeModeTab.l);
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
        if (l != 1) {
            return false;
        } else if (entityhuman.e(i, j, k) && entityhuman.e(i, j + 1, k)) {
            int i1 = world.getTypeId(i, j, k);
            Block bl = Block.byId[i1];

            if (bl != null && bl.canSustainPlant(world, i, j, k, ForgeDirection.UP, this) && world.isEmpty(i, j + 1, k))
            {
                CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j + 1, k); // CraftBukkit

                world.setTypeId(i, j + 1, k, this.id);

                // CraftBukkit start - seeds
                org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, i, j, k);

                if (event.isCancelled() || !event.canBuild()) {
                    event.getBlockPlaced().setTypeId(0);
                    return false;
                }
                // CraftBukkit end

                --itemstack.count;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    

    public EnumPlantType getPlantType(World var1, int var2, int var3, int var4)
    {
        return this.id == Block.NETHER_WART.id ? EnumPlantType.Nether : EnumPlantType.Crop;
    }

    public int getPlantID(World var1, int var2, int var3, int var4)
    {
        return this.id;
    }

    public int getPlantMetadata(World var1, int var2, int var3, int var4)
    {
        return 0;
    }
}