package net.minecraft.server;

import net.minecraftforge.common.ForgeDirection;
import java.util.Random;

// CraftBukkit start
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
// CraftBukkit end

public class BlockFire extends Block {

    private int[] a = new int[256];
    private int[] b = new int[256];

    protected BlockFire(int i, int j) {
        super(i, j, Material.FIRE);
        this.b(true);
    }

    public void t_() {
        this.b = Block.blockFlammability;
        this.a = Block.blockFireSpreadSpeed;
        
        this.a(Block.WOOD.id, 5, 20);
        this.a(Block.WOOD_DOUBLE_STEP.id, 5, 20);
        this.a(Block.WOOD_STEP.id, 5, 20);
        this.a(Block.FENCE.id, 5, 20);
        this.a(Block.WOOD_STAIRS.id, 5, 20);
        this.a(Block.BIRCH_WOOD_STAIRS.id, 5, 20);
        this.a(Block.SPRUCE_WOOD_STAIRS.id, 5, 20);
        this.a(Block.JUNGLE_WOOD_STAIRS.id, 5, 20);
        this.a(Block.LOG.id, 5, 5);
        this.a(Block.LEAVES.id, 30, 60);
        this.a(Block.BOOKSHELF.id, 30, 20);
        this.a(Block.TNT.id, 15, 100);
        this.a(Block.LONG_GRASS.id, 60, 100);
        this.a(Block.WOOL.id, 30, 60);
        this.a(Block.VINE.id, 15, 100);
    }

    /**
     * Sets the burn rate for a block. The larger abilityToCatchFire the more easily it will catch. The larger
     * chanceToEncourageFire the faster it will burn and spread to other blocks. Args: blockID, chanceToEncourageFire,
     * abilityToCatchFire
     */
    private void a(int var1, int var2, int var3)
    {
        Block.setBurnProperties(var1, var2, var3);
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
        return 3;
    }

    public int a(Random random) {
        return 0;
    }

    public int r_() {
        return 30;
    }

    public void b(World world, int i, int j, int k, Random random) {
        if (world.getGameRules().getBoolean("doFireTick")) {
        	
            Block var6 = Block.byId[world.getTypeId(i, j - 1, k)];
            boolean flag = var6 != null && var6.isFireSource(world, i, j - 1, k, world.getData(i, j - 1, k), ForgeDirection.UP);

            if (world.worldProvider instanceof WorldProviderTheEnd && world.getTypeId(i, j - 1, k) == Block.BEDROCK.id) {
                flag = true;
            }

            if (!this.canPlace(world, i, j, k)) {
                fireExtinguished(world, i, j, k); // CraftBukkit - invalid place location
            }

            if (!flag && world.N() && (world.D(i, j, k) || world.D(i - 1, j, k) || world.D(i + 1, j, k) || world.D(i, j, k - 1) || world.D(i, j, k + 1))) {
                fireExtinguished(world, i, j, k); // CraftBukkit - extinguished by rain
            } else {
                int l = world.getData(i, j, k);

                if (l < 15) {
                    world.setRawData(i, j, k, l + random.nextInt(3) / 2);
                }

                world.a(i, j, k, this.id, this.r_() + random.nextInt(10));
                if (!flag && !this.l(world, i, j, k)) {
                    if (!world.v(i, j - 1, k) || l > 3) {
                        world.setTypeId(i, j, k, 0);
                    }
                } else if (!flag && !this.canBlockCatchFire((IBlockAccess) world, i, j - 1, k, ForgeDirection.UP) && l == 15 && random.nextInt(4) == 0) {
                    fireExtinguished(world, i, j, k); // CraftBukkit - burn out
                } else {
                    boolean flag1 = world.E(i, j, k);
                    byte b0 = 0;

                    if (flag1) {
                        b0 = -50;
                    }

                    this.tryToCatchBlockOnFire(world, i + 1, j, k, 300 + b0, random, l, ForgeDirection.WEST);
                    this.tryToCatchBlockOnFire(world, i - 1, j, k, 300 + b0, random, l, ForgeDirection.EAST);
                    this.tryToCatchBlockOnFire(world, i, j - 1, k, 250 + b0, random, l, ForgeDirection.UP);
                    this.tryToCatchBlockOnFire(world, i, j + 1, k, 250 + b0, random, l, ForgeDirection.DOWN);
                    this.tryToCatchBlockOnFire(world, i, j, k - 1, 300 + b0, random, l, ForgeDirection.SOUTH);
                    this.tryToCatchBlockOnFire(world, i, j, k + 1, 300 + b0, random, l, ForgeDirection.NORTH);

                    // CraftBukkit start - call to stop spread of fire
                    org.bukkit.Server server = world.getServer();
                    org.bukkit.World bworld = world.getWorld();

                    BlockIgniteEvent.IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.SPREAD;
                    org.bukkit.block.Block fromBlock = bworld.getBlockAt(i, j, k);
                    // CraftBukkit end

                    for (int i1 = i - 1; i1 <= i + 1; ++i1) {
                        for (int j1 = k - 1; j1 <= k + 1; ++j1) {
                            for (int k1 = j - 1; k1 <= j + 4; ++k1) {
                                if (i1 != i || k1 != j || j1 != k) {
                                    int l1 = 100;

                                    if (k1 > j + 1) {
                                        l1 += (k1 - (j + 1)) * 100;
                                    }

                                    int i2 = this.n(world, i1, k1, j1);

                                    if (i2 > 0) {
                                        int j2 = (i2 + 40 + world.difficulty * 7) / (l + 30);

                                        if (flag1) {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.N() || !world.D(i1, k1, j1)) && !world.D(i1 - 1, k1, k) && !world.D(i1 + 1, k1, j1) && !world.D(i1, k1, j1 - 1) && !world.D(i1, k1, j1 + 1)) {
                                            int k2 = l + random.nextInt(5) / 4;

                                            if (k2 > 15) {
                                                k2 = 15;
                                            }

                                            // CraftBukkit start - call to stop spread of fire
                                            org.bukkit.block.Block block = bworld.getBlockAt(i1, k1, j1);

                                            if (block.getTypeId() != Block.FIRE.id) {
                                                BlockIgniteEvent event = new BlockIgniteEvent(block, igniteCause, null);
                                                server.getPluginManager().callEvent(event);

                                                if (event.isCancelled()) {
                                                    continue;
                                                }

                                                org.bukkit.block.BlockState blockState = bworld.getBlockAt(i1, k1, j1).getState();
                                                blockState.setTypeId(this.id);
                                                blockState.setData(new org.bukkit.material.MaterialData(this.id, (byte) k2));

                                                BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), fromBlock, blockState);
                                                server.getPluginManager().callEvent(spreadEvent);

                                                if (!spreadEvent.isCancelled()) {
                                                    blockState.update(true);
                                                }
                                            }
                                            // CraftBukkit end
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean l() {
        return false;
    }
    

    @Deprecated
    private void a(World var1, int var2, int var3, int var4, int var5, Random var6, int var7)
    {
        this.tryToCatchBlockOnFire(var1, var2, var3, var4, var5, var6, var7, ForgeDirection.UP);
    }

    private void tryToCatchBlockOnFire(World world, int var2, int var3, int var4, int var5, Random random, int var7, ForgeDirection var8)
    {
        int var9 = 0;
        Block var10 = Block.byId[world.getTypeId(var2, var3, var4)];

        if (var10 != null)
        {
            var9 = var10.getFlammability(world, var2, var3, var4, world.getData(var2, var3, var4), var8);
        }

        if (random.nextInt(var5) < var9)
        {
            boolean var11 = world.getTypeId(var2, var3, var4) == Block.TNT.id;
            
            // CraftBukkit start
            org.bukkit.block.Block theBlock = world.getWorld().getBlockAt(var2, var3, var4);

            BlockBurnEvent event = new BlockBurnEvent(theBlock);
            world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }
            // CraftBukkit end

            if (random.nextInt(var7 + 10) < 5 && !world.D(var2, var3, var4))
            {
                int var12 = var7 + random.nextInt(5) / 4;

                if (var12 > 15)
                {
                    var12 = 15;
                }

                world.setTypeIdAndData(var2, var3, var4, this.id, var12);
            }
            else
            {
                world.setTypeId(var2, var3, var4, 0);
            }

            if (var11)
            {
                Block.TNT.postBreak(world, var2, var3, var4, 1);
            }
        }
    }

    /**
     * Returns true if at least one block next to this one can burn.
     */
    private boolean l(World var1, int var2, int var3, int var4)
    {
        return this.canBlockCatchFire(var1, var2 + 1, var3, var4, ForgeDirection.WEST) || this.canBlockCatchFire(var1, var2 - 1, var3, var4, ForgeDirection.EAST) || this.canBlockCatchFire(var1, var2, var3 - 1, var4, ForgeDirection.UP) || this.canBlockCatchFire(var1, var2, var3 + 1, var4, ForgeDirection.DOWN) || this.canBlockCatchFire(var1, var2, var3, var4 - 1, ForgeDirection.SOUTH) || this.canBlockCatchFire(var1, var2, var3, var4 + 1, ForgeDirection.NORTH);
    }


    /**
     * Gets the highest chance of a neighbor block encouraging this block to catch fire
     */
    private int n(World var1, int var2, int var3, int var4)
    {
        byte var5 = 0;

        if (!var1.isEmpty(var2, var3, var4))
        {
            return 0;
        }
        else
        {
            int var6 = this.getChanceToEncourageFire(var1, var2 + 1, var3, var4, var5, ForgeDirection.WEST);
            var6 = this.getChanceToEncourageFire(var1, var2 - 1, var3, var4, var6, ForgeDirection.EAST);
            var6 = this.getChanceToEncourageFire(var1, var2, var3 - 1, var4, var6, ForgeDirection.UP);
            var6 = this.getChanceToEncourageFire(var1, var2, var3 + 1, var4, var6, ForgeDirection.DOWN);
            var6 = this.getChanceToEncourageFire(var1, var2, var3, var4 - 1, var6, ForgeDirection.SOUTH);
            var6 = this.getChanceToEncourageFire(var1, var2, var3, var4 + 1, var6, ForgeDirection.NORTH);
            return var6;
        }
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean m()
    {
        return false;
    }

    @Deprecated

    /**
     * Checks the specified block coordinate to see if it can catch fire.  Args: blockAccess, x, y, z
     */
    public boolean d(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.canBlockCatchFire(var1, var2, var3, var4, ForgeDirection.UP);
    }

    @Deprecated

    /**
     * Retrieves a specified block's chance to encourage their neighbors to burn and if the number is greater than the
     * current number passed in it will return its number instead of the passed in one.  Args: world, x, y, z,
     * curChanceToEncourageFire
     */
    public int d(World var1, int var2, int var3, int var4, int var5)
    {
        return this.getChanceToEncourageFire(var1, var2, var3, var4, var5, ForgeDirection.UP);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return world.v(i, j - 1, k) || this.l(world, i, j, k);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.v(i, j - 1, k) && !this.l(world, i, j, k)) {
            fireExtinguished(world, i, j, k); // CraftBukkit - fuel block gone
        }
    }

    public void onPlace(World world, int i, int j, int k) {
        if (world.worldProvider.dimension > 0 || world.getTypeId(i, j - 1, k) != Block.OBSIDIAN.id || !Block.PORTAL.i_(world, i, j, k)) {
            if (!world.v(i, j - 1, k) && !this.l(world, i, j, k)) {
                fireExtinguished(world, i, j, k); // CraftBukkit - fuel block broke
            } else {
                world.a(i, j, k, this.id, this.r_() + world.random.nextInt(10));
            }
        }
    }

    // CraftBukkit start
    private void fireExtinguished(World world, int x, int y, int z) {
        if (org.bukkit.craftbukkit.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(x, y, z), 0).isCancelled() == false) {
            world.setTypeId(x, y, z, 0);
        }
    }
    // CraftBukkit end
    

    public boolean canBlockCatchFire(IBlockAccess var1, int var2, int var3, int var4, ForgeDirection var5)
    {
        Block var6 = Block.byId[var1.getTypeId(var2, var3, var4)];
        return var6 != null ? var6.isFlammable(var1, var2, var3, var4, var1.getData(var2, var3, var4), var5) : false;
    }

    public int getChanceToEncourageFire(World var1, int var2, int var3, int var4, int var5, ForgeDirection var6)
    {
        int var7 = 0;
        Block var8 = Block.byId[var1.getTypeId(var2, var3, var4)];

        if (var8 != null)
        {
            var7 = var8.getFireSpreadSpeed(var1, var2, var3, var4, var1.getData(var2, var3, var4), var6);
        }

        return var7 > var5 ? var7 : var5;
    }
}
