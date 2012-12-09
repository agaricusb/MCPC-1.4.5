package net.minecraft.server;

import java.util.Random;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit
import net.minecraftforge.common.ForgeDirection;

public class BlockTripwireHook extends Block {

    public BlockTripwireHook(int i) {
        super(i, 172, Material.ORIENTABLE);
        this.a(CreativeModeTab.d);
        this.b(true);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public int d() {
        return 29;
    }

    public int r_() {
        return 10;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World var1, int var2, int var3, int var4, int var5)
    {
        ForgeDirection var6 = ForgeDirection.getOrientation(var5);
        return var6 == ForgeDirection.NORTH && var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH) || var6 == ForgeDirection.SOUTH && var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH) || var6 == ForgeDirection.WEST && var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST) || var6 == ForgeDirection.EAST && var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.SOUTH) || var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.NORTH) || var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.EAST) || var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.WEST);
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void postPlace(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        byte var9 = 0;

        if (var5 == 2 && var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.WEST, true))
        {
            var9 = 2;
        }

        if (var5 == 3 && var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.EAST, true))
        {
            var9 = 0;
        }

        if (var5 == 4 && var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.NORTH, true))
        {
            var9 = 1;
        }

        if (var5 == 5 && var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.SOUTH, true))
        {
            var9 = 3;
        }

        this.a(var1, var2, var3, var4, this.id, var9, false, -1, 0);
    }
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (var5 != this.id && this.l(var1, var2, var3, var4))
        {
            int var6 = var1.getData(var2, var3, var4);
            int var7 = var6 & 3;
            boolean var8 = false;

            if (!var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.SOUTH) && var7 == 3)
            {
                var8 = true;
            }

            if (!var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.NORTH) && var7 == 1)
            {
                var8 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.EAST) && var7 == 0)
            {
                var8 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.WEST) && var7 == 2)
            {
                var8 = true;
            }

            if (var8)
            {
                this.c(var1, var2, var3, var4, var6, 0);
                var1.setTypeId(var2, var3, var4, 0);
            }
        }
    }

    public void a(World world, int i, int j, int k, int l, int i1, boolean flag, int j1, int k1) {
        int l1 = i1 & 3;
        boolean flag1 = (i1 & 4) == 4;
        boolean flag2 = (i1 & 8) == 8;
        boolean flag3 = l == Block.TRIPWIRE_SOURCE.id;
        boolean flag4 = false;
        boolean flag5 = !world.isBlockSolidOnSide(i, j - 1, k, ForgeDirection.UP);
        int i2 = Direction.a[l1];
        int j2 = Direction.b[l1];
        int k2 = 0;
        int[] aint = new int[42];

        int l2;
        int i3;
        int j3;
        int k3;
        int l3;

        for (i3 = 1; i3 < 42; ++i3) {
            l2 = i + i2 * i3;
            k3 = k + j2 * i3;
            j3 = world.getTypeId(l2, j, k3);
            if (j3 == Block.TRIPWIRE_SOURCE.id) {
                l3 = world.getData(l2, j, k3);
                if ((l3 & 3) == Direction.f[l1]) {
                    k2 = i3;
                }
                break;
            }

            if (j3 != Block.TRIPWIRE.id && i3 != j1) {
                aint[i3] = -1;
                flag3 = false;
            } else {
                l3 = i3 == j1 ? k1 : world.getData(l2, j, k3);
                boolean flag6 = (l3 & 8) != 8;
                boolean flag7 = (l3 & 1) == 1;
                boolean flag8 = (l3 & 2) == 2;

                flag3 &= flag8 == flag5;
                flag4 |= flag6 && flag7;
                aint[i3] = l3;
                if (i3 == j1) {
                    world.a(i, j, k, l, this.r_());
                    flag3 &= flag6;
                }
            }
        }

        flag3 &= k2 > 1;
        flag4 &= flag3;
        i3 = (flag3 ? 4 : 0) | (flag4 ? 8 : 0);
        i1 = l1 | i3;
        if (k2 > 0) {
            l2 = i + i2 * k2;
            k3 = k + j2 * k2;
            j3 = Direction.f[l1];
            world.setData(l2, j, k3, j3 | i3);
            this.d(world, l2, j, k3, j3);
            this.a(world, l2, j, k3, flag3, flag4, flag1, flag2);
        }

        // CraftBukkit start
        org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);

        BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 1, 0);
        world.getServer().getPluginManager().callEvent(eventRedstone);

        if (eventRedstone.getNewCurrent() > 0) {
            return;
        }
        // CraftBukkit end

        this.a(world, i, j, k, flag3, flag4, flag1, flag2);
        if (l > 0) {
            world.setData(i, j, k, i1);
            if (flag) {
                this.d(world, i, j, k, l1);
            }
        }

        if (flag1 != flag3) {
            for (l2 = 1; l2 < k2; ++l2) {
                k3 = i + i2 * l2;
                j3 = k + j2 * l2;
                l3 = aint[l2];
                if (l3 >= 0) {
                    if (flag3) {
                        l3 |= 4;
                    } else {
                        l3 &= -5;
                    }

                    world.setData(k3, j, j3, l3);
                }
            }
        }
    }

    public void b(World world, int i, int j, int k, Random random) {
        this.a(world, i, j, k, this.id, world.getData(i, j, k), true, -1, 0);
    }

    private void a(World world, int i, int j, int k, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        if (flag1 && !flag3) {
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.4F, 0.6F);
        } else if (!flag1 && flag3) {
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.4F, 0.5F);
        } else if (flag && !flag2) {
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.4F, 0.7F);
        } else if (!flag && flag2) {
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.bowhit", 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
        }
    }

    private void d(World world, int i, int j, int k, int l) {
        world.applyPhysics(i, j, k, this.id);
        if (l == 3) {
            world.applyPhysics(i - 1, j, k, this.id);
        } else if (l == 1) {
            world.applyPhysics(i + 1, j, k, this.id);
        } else if (l == 0) {
            world.applyPhysics(i, j, k - 1, this.id);
        } else if (l == 2) {
            world.applyPhysics(i, j, k + 1, this.id);
        }
    }

    private boolean l(World world, int i, int j, int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setTypeId(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getData(i, j, k) & 3;
        float f = 0.1875F;

        if (l == 3) {
            this.a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (l == 1) {
            this.a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (l == 0) {
            this.a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (l == 2) {
            this.a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
    }

    public void remove(World world, int i, int j, int k, int l, int i1) {
        boolean flag = (i1 & 4) == 4;
        boolean flag1 = (i1 & 8) == 8;

        if (flag || flag1) {
            this.a(world, i, j, k, 0, i1, false, -1, 0);
        }

        if (flag1) {
            world.applyPhysics(i, j, k, this.id);
            int j1 = i1 & 3;

            if (j1 == 3) {
                world.applyPhysics(i - 1, j, k, this.id);
            } else if (j1 == 1) {
                world.applyPhysics(i + 1, j, k, this.id);
            } else if (j1 == 0) {
                world.applyPhysics(i, j, k - 1, this.id);
            } else if (j1 == 2) {
                world.applyPhysics(i, j, k + 1, this.id);
            }
        }

        super.remove(world, i, j, k, l, i1);
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return (iblockaccess.getData(i, j, k) & 8) == 8;
    }

    public boolean c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        int i1 = iblockaccess.getData(i, j, k);

        if ((i1 & 8) != 8) {
            return false;
        } else {
            int j1 = i1 & 3;

            return j1 == 2 && l == 2 ? true : (j1 == 0 && l == 3 ? true : (j1 == 1 && l == 4 ? true : j1 == 3 && l == 5));
        }
    }

    public boolean isPowerSource() {
        return true;
    }
}
