package net.minecraft.server;

import java.util.Random;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import org.bukkit.event.entity.EntityDamageByBlockEvent; // CraftBukkit

public class BlockCactus extends Block implements IPlantable {

    protected BlockCactus(int i, int j) {
        super(i, j, Material.CACTUS);
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    public void b(World world, int i, int j, int k, Random random) {
        if (world.isEmpty(i, j + 1, k)) {
            int l;

            for (l = 1; world.getTypeId(i, j - l, k) == this.id; ++l) {
                ;
            }

            if (l < 3) {
                int i1 = world.getData(i, j, k);

                if (i1 == 15) {
                    org.bukkit.craftbukkit.event.CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this.id, 0); // CraftBukkit
                    world.setData(i, j, k, 0);
                } else {
                    world.setData(i, j, k, i1 + 1);
                }
            }
        }
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        float f = 0.0625F;

        return AxisAlignedBB.a().a((double) ((float) i + f), (double) j, (double) ((float) k + f), (double) ((float) (i + 1) - f), (double) ((float) (j + 1) - f), (double) ((float) (k + 1) - f));
    }

    public int a(int i) {
        return i == 1 ? this.textureId - 1 : (i == 0 ? this.textureId + 1 : this.textureId);
    }

    public boolean b() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public int d() {
        return 13;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return !super.canPlace(world, i, j, k) ? false : this.d(world, i, j, k);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!this.d(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setTypeId(i, j, k, 0);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World var1, int var2, int var3, int var4)
    {
        if (var1.getMaterial(var2 - 1, var3, var4).isBuildable())
        {
            return false;
        }
        else if (var1.getMaterial(var2 + 1, var3, var4).isBuildable())
        {
            return false;
        }
        else if (var1.getMaterial(var2, var3, var4 - 1).isBuildable())
        {
            return false;
        }
        else if (var1.getMaterial(var2, var3, var4 + 1).isBuildable())
        {
            return false;
        }
        else
        {
            int var5 = var1.getTypeId(var2, var3 - 1, var4);
            return byId[var5] != null && byId[var5].canSustainPlant(var1, var2, var3 - 1, var4, ForgeDirection.UP, this);
        }
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        // CraftBukkit start - EntityDamageByBlock event
        if (entity instanceof EntityLiving) {
            org.bukkit.block.Block damager = world.getWorld().getBlockAt(i, j, k);
            org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();

            EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, org.bukkit.event.entity.EntityDamageEvent.DamageCause.CONTACT, 1);
            world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                damagee.setLastDamageCause(event);
                entity.damageEntity(DamageSource.CACTUS, event.getDamage());
            }
            return;
        }
        // CraftBukkit end

        entity.damageEntity(DamageSource.CACTUS, 1);
    }
    

    public EnumPlantType getPlantType(World var1, int var2, int var3, int var4)
    {
        return EnumPlantType.Desert;
    }

    public int getPlantID(World var1, int var2, int var3, int var4)
    {
        return this.id;
    }

    public int getPlantMetadata(World var1, int var2, int var3, int var4)
    {
        return -1;
    }
}
