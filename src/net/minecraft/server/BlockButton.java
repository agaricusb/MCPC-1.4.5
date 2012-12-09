package net.minecraft.server;

import java.util.List;
import java.util.Random;
import net.minecraftforge.common.ForgeDirection;
//CraftBukkit start
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;
//CraftBukkit end

public class BlockButton extends Block {

    private final boolean a;

    protected BlockButton(int i, int j, boolean flag) {
        super(i, j, Material.ORIENTABLE);
        this.b(true);
        this.a(CreativeModeTab.d);
        this.a = flag;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public int r_() {
        return this.a ? 30 : 20;
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World var1, int var2, int var3, int var4, int var5)
    {
        ForgeDirection var6 = ForgeDirection.getOrientation(var5);
        return var6 == ForgeDirection.NORTH && var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH) || var6 == ForgeDirection.SOUTH && var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH) || var6 == ForgeDirection.WEST && var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST) || var6 == ForgeDirection.EAST && var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return world.t(i - 1, j, k) ? true : (world.t(i + 1, j, k) ? true : (world.t(i, j, k - 1) ? true : world.t(i, j, k + 1)));
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public int getPlacedData(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var12)
    {
        int var9 = var1.getData(var2, var3, var4);
        int var10 = var9 & 8;
        var9 &= 7;
        ForgeDirection var11 = ForgeDirection.getOrientation(var5);

        if (var11 == ForgeDirection.NORTH && var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH))
        {
            var9 = 4;
        }
        else if (var11 == ForgeDirection.SOUTH && var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH))
        {
            var9 = 3;
        }
        else if (var11 == ForgeDirection.WEST && var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST))
        {
            var9 = 2;
        }
        else if (var11 == ForgeDirection.EAST && var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST))
        {
            var9 = 1;
        }
        else
        {
            var9 = this.l(var1, var2, var3, var4);
        }

        return (var9 + var10);
    }

    /**
     * Get side which this button is facing.
     */
    private int l(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST) ? 1 : (var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST) ? 2 : (var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH) ? 3 : (var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH) ? 4 : 1)));
    }


    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (this.n(var1, var2, var3, var4))
        {
            int var6 = var1.getData(var2, var3, var4) & 7;
            boolean var7 = false;

            if (!var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST) && var6 == 1)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST) && var6 == 2)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH) && var6 == 3)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH) && var6 == 4)
            {
                var7 = true;
            }

            if (var7)
            {
                this.c(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
                var1.setTypeId(var2, var3, var4, 0);
            }
        }
    }

    private boolean n(World world, int i, int j, int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setTypeId(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getData(i, j, k);

        this.e(l);
    }

    private void e(int i) {
        int j = i & 7;
        boolean flag = (i & 8) > 0;
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.1875F;
        float f3 = 0.125F;

        if (flag) {
            f3 = 0.0625F;
        }

        if (j == 1) {
            this.a(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
        } else if (j == 2) {
            this.a(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
        } else if (j == 3) {
            this.a(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
        } else if (j == 4) {
            this.a(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
        }
    }

    public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
        int i1 = world.getData(i, j, k);
        int j1 = i1 & 7;
        int k1 = 8 - (i1 & 8);

        if (k1 == 0) {
            return true;
        } else {
            // CraftBukkit start
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int old = (k1 != 8) ? 1 : 0;
            int current = (k1 == 8) ? 1 : 0;

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
            world.getServer().getPluginManager().callEvent(eventRedstone);

            if ((eventRedstone.getNewCurrent() > 0) != (k1 == 8)) {
                return true;
            }
            // CraftBukkit end

            world.setData(i, j, k, j1 + k1);
            world.e(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, 0.6F);
            this.d(world, i, j, k, j1);
            world.a(i, j, k, this.id, this.r_());
            return true;
        }
    }

    public void remove(World world, int i, int j, int k, int l, int i1) {
        if ((i1 & 8) > 0) {
            int j1 = i1 & 7;

            this.d(world, i, j, k, j1);
        }

        super.remove(world, i, j, k, l, i1);
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return (iblockaccess.getData(i, j, k) & 8) > 0;
    }

    public boolean c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        int i1 = iblockaccess.getData(i, j, k);

        if ((i1 & 8) == 0) {
            return false;
        } else {
            int j1 = i1 & 7;

            return j1 == 5 && l == 1 ? true : (j1 == 4 && l == 2 ? true : (j1 == 3 && l == 3 ? true : (j1 == 2 && l == 4 ? true : j1 == 1 && l == 5)));
        }
    }

    public boolean isPowerSource() {
        return true;
    }

    public void b(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            int l = world.getData(i, j, k);

            if ((l & 8) != 0) {
                // CraftBukkit start
                org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);

                BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 1, 0);
                world.getServer().getPluginManager().callEvent(eventRedstone);

                if (eventRedstone.getNewCurrent() > 0) {
                    return;
                }
                // CraftBukkit end

                if (this.a) {
                    this.o(world, i, j, k);
                } else {
                    world.setData(i, j, k, l & 7);
                    int i1 = l & 7;

                    this.d(world, i, j, k, i1);
                    world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, 0.5F);
                    world.e(i, j, k, i, j, k);
                }
            }
        }
    }

    public void f() {
        float f = 0.1875F;
        float f1 = 0.125F;
        float f2 = 0.125F;

        this.a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World world, int var2, int var3, int var4, Entity entity)
    {
        if (!world.isStatic && this.a && (world.getData(var2, var3, var4) & 8) == 0)
        {
            // CraftBukkit start - Call interaction when entities (currently arrows) hit wooden buttons
            EntityInteractEvent event = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(var2, var3, var4));
            world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }
            // CraftBukkit end
            this.o(world, var2, var3, var4);
        }
    }

    private void o(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getData(var2, var3, var4);
        int var6 = var5 & 7;
        boolean var7 = (var5 & 8) != 0;
        this.e(var5);
        List var8 = var1.a(EntityArrow.class, AxisAlignedBB.a().a((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)var3 + this.maxY, (double)var4 + this.maxZ));
        boolean var9 = !var8.isEmpty();

        if (var9 && !var7)
        {
            var1.setData(var2, var3, var4, var6 | 8);
            this.d(var1, var2, var3, var4, var6);
            var1.e(var2, var3, var4, var2, var3, var4);
            var1.makeSound((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!var9 && var7)
        {
            var1.setData(var2, var3, var4, var6);
            this.d(var1, var2, var3, var4, var6);
            var1.e(var2, var3, var4, var2, var3, var4);
            var1.makeSound((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (var9)
        {
            var1.a(var2, var3, var4, this.id, this.r_());
        }
    }

    private void d(World world, int i, int j, int k, int l) {
        world.applyPhysics(i, j, k, this.id);
        if (l == 1) {
            world.applyPhysics(i - 1, j, k, this.id);
        } else if (l == 2) {
            world.applyPhysics(i + 1, j, k, this.id);
        } else if (l == 3) {
            world.applyPhysics(i, j, k - 1, this.id);
        } else if (l == 4) {
            world.applyPhysics(i, j, k + 1, this.id);
        } else {
            world.applyPhysics(i, j - 1, k, this.id);
        }
    }
}
