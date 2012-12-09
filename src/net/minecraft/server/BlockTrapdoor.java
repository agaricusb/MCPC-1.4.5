package net.minecraft.server;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit
import net.minecraftforge.common.ForgeDirection;

public class BlockTrapdoor extends Block {
    public static boolean disableValidation = false;
    
    protected BlockTrapdoor(int i, Material material) {
        super(i, material);
        this.textureId = 84;
        if (material == Material.ORE) {
            ++this.textureId;
        }

        float f = 0.5F;
        float f1 = 1.0F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        this.a(CreativeModeTab.d);
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean c(IBlockAccess iblockaccess, int i, int j, int k) {
        return !g(iblockaccess.getData(i, j, k));
    }

    public int d() {
        return 0;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        this.updateShape(world, i, j, k);
        return super.e(world, i, j, k);
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        this.e(iblockaccess.getData(i, j, k));
    }

    public void f() {
        float f = 0.1875F;

        this.a(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
    }

    public void e(int i) {
        float f = 0.1875F;

        if ((i & 8) != 0) {
            this.a(0.0F, 1.0F - f, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
        }

        if (g(i)) {
            if ((i & 3) == 0) {
                this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }

            if ((i & 3) == 1) {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }

            if ((i & 3) == 2) {
                this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((i & 3) == 3) {
                this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
    }

    public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
        if (this.material == Material.ORE) {
            return true;
        } else {
            int i1 = world.getData(i, j, k);

            world.setData(i, j, k, i1 ^ 4);
            world.a(entityhuman, 1003, i, j, k, 0);
            return true;
        }
    }

    public void setOpen(World world, int i, int j, int k, boolean flag) {
        int l = world.getData(i, j, k);
        boolean flag1 = (l & 4) > 0;

        if (flag1 != flag) {
            world.setData(i, j, k, l ^ 4);
            world.a((EntityHuman) null, 1003, i, j, k, 0);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.isStatic) {
            int i1 = world.getData(i, j, k);
            int j1 = i;
            int k1 = k;

            if ((i1 & 3) == 0) {
                k1 = k + 1;
            }

            if ((i1 & 3) == 1) {
                --k1;
            }

            if ((i1 & 3) == 2) {
                j1 = i + 1;
            }

            if ((i1 & 3) == 3) {
                --j1;
            }

            if (!j(world.getTypeId(j1, j, k1)) && !world.isBlockSolidOnSide(j1, j, k1, ForgeDirection.getOrientation((i1 & 3) + 2))) {
                world.setTypeId(i, j, k, 0);
                this.c(world, i, j, k, i1, 0);
            }

            // CraftBukkit start
            if (l == 0 || l > 0 && Block.byId[l] != null && Block.byId[l].isPowerSource()) {
                org.bukkit.World bworld = world.getWorld();
                org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);

                int power = block.getBlockPower();
                int oldPower = (world.getData(i, j, k) & 4) > 0 ? 15 : 0;

                if (oldPower == 0 ^ power == 0 || (Block.byId[l] != null && Block.byId[l].isPowerSource())) {
                    BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    world.getServer().getPluginManager().callEvent(eventRedstone);

                    this.setOpen(world, i, j, k, eventRedstone.getNewCurrent() > 0);
                }
                // CraftBukkit end
            }
        }
    }

    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        this.updateShape(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
        int j1 = 0;

        if (l == 2) {
            j1 = 0;
        }

        if (l == 3) {
            j1 = 1;
        }

        if (l == 4) {
            j1 = 2;
        }

        if (l == 5) {
            j1 = 3;
        }

        if (l != 1 && l != 0 && f1 > 0.5F) {
            j1 |= 8;
        }

        return j1;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World var1, int var2, int var3, int var4, int var5)
    {
        if (disableValidation)
        {
            return true;
        }
        else if (var5 == 0)
        {
            return false;
        }
        else if (var5 == 1)
        {
            return false;
        }
        else
        {
            if (var5 == 2)
            {
                ++var4;
            }

            if (var5 == 3)
            {
                --var4;
            }

            if (var5 == 4)
            {
                ++var2;
            }

            if (var5 == 5)
            {
                --var2;
            }

            return j(var1.getTypeId(var2, var3, var4)) || var1.isBlockSolidOnSide(var2, var3, var4, ForgeDirection.UP);
        }
    }

    public static boolean g(int i) {
        return (i & 4) != 0;
    }

    /**
     * Checks if the block ID is a valid support block for the trap door to connect with. If it is not the trapdoor is
     * dropped into the world.
     */
    private static boolean j(int var0)
    {
        if (disableValidation)
        {
            return true;
        }
        else if (var0 <= 0)
        {
            return false;
        }
        else
        {
            Block var1 = Block.byId[var0];
            return var1 != null && var1.material.k() && var1.b() || var1 == Block.GLOWSTONE;
        }
    }
}
