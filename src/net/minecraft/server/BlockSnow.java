package net.minecraft.server;

import java.util.Random;

public class BlockSnow extends Block {

    protected BlockSnow(int i, int j) {
        super(i, j, Material.SNOW_LAYER);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        int l = world.getData(i, j, k) & 7;

        return l >= 3 ? AxisAlignedBB.a().a((double) i + this.minX, (double) j + this.minY, (double) k + this.minZ, (double) i + this.maxX, (double) ((float) j + 0.5F), (double) k + this.maxZ) : null;
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getData(i, j, k) & 7;
        float f = (float) (2 * (1 + l)) / 16.0F;

        this.a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getTypeId(var2, var3 - 1, var4);
        Block var6 = Block.byId[var5];
        return var6 != null && (var6.isLeaves(var1, var2, var3 - 1, var4) || Block.byId[var5].c()) ? var1.getMaterial(var2, var3 - 1, var4).isSolid() : false;
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        this.n(world, i, j, k);
    }

    private boolean n(World world, int i, int j, int k) {
        if (!this.canPlace(world, i, j, k)) {
            //this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setRawTypeId(i, j, k, 0); // CraftBukkit
            world.notify(i, j, k); // CraftBukkit - Notify clients of the reversion
            return false;
        } else {
            return true;
        }
    }

    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        int i1 = Item.SNOW_BALL.id;

        this.b(world, i, j, k, new ItemStack(i1, 1, 0));
        world.setTypeId(i, j, k, 0);
        entityhuman.a(StatisticList.C[this.id], 1);
    }

    public int getDropType(int i, Random random, int j) {
        return Item.SNOW_BALL.id;
    }

    public int a(Random random) {
        return 0;
    }

    public void b(World world, int i, int j, int k, Random random) {
        if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11) {
            // CraftBukkit start
            if (org.bukkit.craftbukkit.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), 0).isCancelled()) {
                return;
            }
            // CraftBukkit end
            
            world.setTypeId(i, j, k, 0);
        }
    }
}
