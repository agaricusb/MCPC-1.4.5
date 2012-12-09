package net.minecraft.server;

import net.minecraftforge.common.ForgeDirection;
import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockLever extends Block {

    protected BlockLever(int i, int j) {
        super(i, j, Material.ORIENTABLE);
        this.a(CreativeModeTab.d);
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
        return 12;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World var1, int var2, int var3, int var4, int var5)
    {
        ForgeDirection var6 = ForgeDirection.getOrientation(var5);
        return var6 == ForgeDirection.DOWN && var1.isBlockSolidOnSide(var2, var3 + 1, var4, ForgeDirection.DOWN) || var6 == ForgeDirection.UP && var1.isBlockSolidOnSide(var2, var3 - 1, var4, ForgeDirection.UP) || var6 == ForgeDirection.NORTH && var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH) || var6 == ForgeDirection.SOUTH && var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH) || var6 == ForgeDirection.WEST && var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST) || var6 == ForgeDirection.EAST && var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2 - 1, var3, var4, ForgeDirection.EAST) || var1.isBlockSolidOnSide(var2 + 1, var3, var4, ForgeDirection.WEST) || var1.isBlockSolidOnSide(var2, var3, var4 - 1, ForgeDirection.SOUTH) || var1.isBlockSolidOnSide(var2, var3, var4 + 1, ForgeDirection.NORTH) || var1.isBlockSolidOnSide(var2, var3 - 1, var4, ForgeDirection.UP) || var1.isBlockSolidOnSide(var2, var3 + 1, var4, ForgeDirection.DOWN);
    }

    public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
        int j1 = i1 & 8;
        int k1 = i1 & 7;

        k1 = -1;
        if (l == 0 && world.t(i, j + 1, k)) {
            k1 = world.random.nextBoolean() ? 0 : 7;
        }

        if (l == 1 && world.v(i, j - 1, k)) {
            k1 = 5 + world.random.nextInt(2);
        }

        if (l == 2 && world.t(i, j, k + 1)) {
            k1 = 4;
        }

        if (l == 3 && world.t(i, j, k - 1)) {
            k1 = 3;
        }

        if (l == 4 && world.t(i + 1, j, k)) {
            k1 = 2;
        }

        if (l == 5 && world.t(i - 1, j, k)) {
            k1 = 1;
        }

        return k1 + j1;
    }

    public static int d(int i) {
        switch (i) {
        case 0:
            return 0;

        case 1:
            return 5;

        case 2:
            return 4;

        case 3:
            return 3;

        case 4:
            return 2;

        case 5:
            return 1;

        default:
            return -1;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (this.l(var1, var2, var3, var4))
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

            if (!var1.isBlockSolidOnSide(var2, var3 - 1, var4, ForgeDirection.UP) && var6 == 5)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3 - 1, var4, ForgeDirection.UP) && var6 == 6)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3 + 1, var4, ForgeDirection.DOWN) && var6 == 0)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3 + 1, var4, ForgeDirection.DOWN) && var6 == 7)
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
        int l = iblockaccess.getData(i, j, k) & 7;
        float f = 0.1875F;

        if (l == 1) {
            this.a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (l == 2) {
            this.a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (l == 3) {
            this.a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (l == 4) {
            this.a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else if (l != 5 && l != 6) {
            if (l == 0 || l == 7) {
                f = 0.25F;
                this.a(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
            }
        } else {
            f = 0.25F;
            this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        }
    }

    public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
        if (world.isStatic) {
            return true;
        } else {
            int i1 = world.getData(i, j, k);
            int j1 = i1 & 7;
            int k1 = 8 - (i1 & 8);

            // CraftBukkit start - Interact Lever
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
            world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, k1 > 0 ? 0.6F : 0.5F);
            world.applyPhysics(i, j, k, this.id);
            if (j1 == 1) {
                world.applyPhysics(i - 1, j, k, this.id);
            } else if (j1 == 2) {
                world.applyPhysics(i + 1, j, k, this.id);
            } else if (j1 == 3) {
                world.applyPhysics(i, j, k - 1, this.id);
            } else if (j1 == 4) {
                world.applyPhysics(i, j, k + 1, this.id);
            } else if (j1 != 5 && j1 != 6) {
                if (j1 == 0 || j1 == 7) {
                    world.applyPhysics(i, j + 1, k, this.id);
                }
            } else {
                world.applyPhysics(i, j - 1, k, this.id);
            }

            return true;
        }
    }

    public void remove(World world, int i, int j, int k, int l, int i1) {
        if ((i1 & 8) > 0) {
            world.applyPhysics(i, j, k, this.id);
            int j1 = i1 & 7;

            if (j1 == 1) {
                world.applyPhysics(i - 1, j, k, this.id);
            } else if (j1 == 2) {
                world.applyPhysics(i + 1, j, k, this.id);
            } else if (j1 == 3) {
                world.applyPhysics(i, j, k - 1, this.id);
            } else if (j1 == 4) {
                world.applyPhysics(i, j, k + 1, this.id);
            } else if (j1 != 5 && j1 != 6) {
                if (j1 == 0 || j1 == 7) {
                    world.applyPhysics(i, j + 1, k, this.id);
                }
            } else {
                world.applyPhysics(i, j - 1, k, this.id);
            }
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

            return j1 == 0 && l == 0 ? true : (j1 == 7 && l == 0 ? true : (j1 == 6 && l == 1 ? true : (j1 == 5 && l == 1 ? true : (j1 == 4 && l == 2 ? true : (j1 == 3 && l == 3 ? true : (j1 == 2 && l == 4 ? true : j1 == 1 && l == 5))))));
        }
    }

    public boolean isPowerSource() {
        return true;
    }
}
