package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;
import net.minecraftforge.common.IShearable;
import org.bukkit.event.block.LeavesDecayEvent; // CraftBukkit

public class BlockLeaves extends BlockTransparant implements IShearable {

    private int cD;
    public static final String[] a = new String[] { "oak", "spruce", "birch", "jungle"};
    int[] b;

    protected BlockLeaves(int i, int j) {
        super(i, j, Material.LEAVES, false);
        this.cD = j;
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    public void remove(World world, int i, int j, int k, int l, int i1) {
        byte b0 = 1;
        int j1 = b0 + 1;

        if (world.d(i - j1, j - j1, k - j1, i + j1, j + j1, k + j1)) {
            for (int k1 = -b0; k1 <= b0; ++k1) {
                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        int j2 = world.getTypeId(i + k1, j + l1, k + i2);

                        if (Block.byId[j2] != null)
                        {
                            Block.byId[j2].beginLeavesDecay(world, i + k1, j + l1, k + i2);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!var1.isStatic)
        {
            int var6 = var1.getData(var2, var3, var4);

            if ((var6 & 8) != 0 && (var6 & 4) == 0)
            {
                byte var7 = 4;
                int var8 = var7 + 1;
                byte var9 = 32;
                int var10 = var9 * var9;
                int var11 = var9 / 2;

                if (this.b == null)
                {
                    this.b = new int[var9 * var9 * var9];
                }

                int var12;

                if (var1.d(var2 - var8, var3 - var8, var4 - var8, var2 + var8, var3 + var8, var4 + var8))
                {
                    int var13;
                    int var14;
                    int var15;

                    for (var12 = -var7; var12 <= var7; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                var15 = var1.getTypeId(var2 + var12, var3 + var13, var4 + var14);
                                Block var16 = Block.byId[var15];

                                if (var16 != null && var16.canSustainLeaves(var1, var2 + var12, var3 + var13, var4 + var14))
                                {
                                    this.b[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (var16 != null && var16.isLeaves(var1, var2 + var12, var3 + var13, var4 + var14))
                                {
                                    this.b[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                }
                                else
                                {
                                    this.b[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }
                    }

                    for (var12 = 1; var12 <= 4; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                for (var15 = -var7; var15 <= var7; ++var15)
                                {
                                    if (this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
                                    {
                                        if (this.b[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var12 = this.b[var11 * var10 + var11 * var9 + var11];

                if (var12 >= 0)
                {
                    var1.setRawData(var2, var3, var4, var6 & -9);
                }
                else
                {
                    this.l(var1, var2, var3, var4);
                }
            }
        }
    }

    private void l(World world, int i, int j, int k) {
        // CraftBukkit start
        LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
        world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }
        // CraftBukkit end

        this.c(world, i, j, k, world.getData(i, j, k), 0);
        world.setTypeId(i, j, k, 0);
    }

    public int a(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public int getDropType(int i, Random random, int j) {
        return Block.SAPLING.id;
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
        if (!world.isStatic) {
            byte b0 = 20;

            if ((l & 3) == 3) {
                b0 = 40;
            }

            if (world.random.nextInt(b0) == 0) {
                int j1 = this.getDropType(l, world.random, i1);

                this.b(world, i, j, k, new ItemStack(j1, 1, this.getDropData(l)));
            }

            if ((l & 3) == 0 && world.random.nextInt(200) == 0) {
                this.b(world, i, j, k, new ItemStack(Item.APPLE, 1, 0));
            }
        }
    }

    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        if (!world.isStatic && entityhuman.bT() != null && entityhuman.bT().id == Item.SHEARS.id) {
            entityhuman.a(StatisticList.C[this.id], 1);
            this.b(world, i, j, k, new ItemStack(Block.LEAVES.id, 1, l & 3));
        } else {
            super.a(world, entityhuman, i, j, k, l);
        }
    }

    public int getDropData(int i) {
        return i & 3;
    }

    public boolean c() {
        return !this.c;
    }

    public int a(int i, int j) {
        return (j & 3) == 1 ? this.textureId + 80 : ((j & 3) == 3 ? this.textureId + 144 : this.textureId);
    }
    

    public boolean isShearable(ItemStack var1, World var2, int var3, int var4, int var5)
    {
        return true;
    }

    public ArrayList onSheared(ItemStack var1, World var2, int var3, int var4, int var5, int var6)
    {
        ArrayList<ItemStack> var7 = new ArrayList<ItemStack>();
        var7.add(new ItemStack(this, 1, var2.getData(var3, var4, var5) & 3));
        return var7;
    }

    public void beginLeavesDecay(World var1, int var2, int var3, int var4)
    {
        var1.setRawData(var2, var3, var4, var1.getData(var2, var3, var4) | 8);
    }

    public boolean isLeaves(World var1, int var2, int var3, int var4)
    {
        return true;
    }
}
