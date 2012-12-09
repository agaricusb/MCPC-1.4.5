package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;
import net.minecraftforge.common.ForgeDirection;
import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class BlockStem extends BlockFlower {

    private Block blockFruit;

    protected BlockStem(int i, Block block) {
        super(i, 111);
        this.blockFruit = block;
        this.b(true);
        float f = 0.125F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.a((CreativeModeTab) null);
    }

    protected boolean d_(int i) {
        return i == Block.SOIL.id;
    }

    public void b(World world, int i, int j, int k, Random random) {
        super.b(world, i, j, k, random);
        if (world.getLightLevel(i, j + 1, k) >= 9) {
            float f = this.n(world, i, j, k);

            if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                int l = world.getData(i, j, k);

                if (l < 7) {
                    ++l;
                    CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this.id, l); // CraftBukkit
                } else {
                    if (world.getTypeId(i - 1, j, k) == this.blockFruit.id) {
                        return;
                    }

                    if (world.getTypeId(i + 1, j, k) == this.blockFruit.id) {
                        return;
                    }

                    if (world.getTypeId(i, j, k - 1) == this.blockFruit.id) {
                        return;
                    }

                    if (world.getTypeId(i, j, k + 1) == this.blockFruit.id) {
                        return;
                    }

                    int i1 = random.nextInt(4);
                    int j1 = i;
                    int k1 = k;

                    if (i1 == 0) {
                        j1 = i - 1;
                    }

                    if (i1 == 1) {
                        ++j1;
                    }

                    if (i1 == 2) {
                        k1 = k - 1;
                    }

                    if (i1 == 3) {
                        ++k1;
                    }

                    int l1 = world.getTypeId(j1, j - 1, k1);
                    boolean var12 = byId[l1] != null && byId[l1].canSustainPlant(world, j1, j - 1, k1, ForgeDirection.UP, this);

                    if (world.getTypeId(j1, j, k1) == 0 && (var12 || l1 == Block.DIRT.id || l1 == Block.GRASS.id)) {
                        CraftEventFactory.handleBlockGrowEvent(world, j1, j, k1, this.blockFruit.id, 0); // CraftBukkit
                    }
                }
            }
        }
    }

    public void l(World world, int i, int j, int k) {
        world.setData(i, j, k, 7);
    }


    private float n(World var1, int var2, int var3, int var4)
    {
        float var5 = 1.0F;
        int var6 = var1.getTypeId(var2, var3, var4 - 1);
        int var7 = var1.getTypeId(var2, var3, var4 + 1);
        int var8 = var1.getTypeId(var2 - 1, var3, var4);
        int var9 = var1.getTypeId(var2 + 1, var3, var4);
        int var10 = var1.getTypeId(var2 - 1, var3, var4 - 1);
        int var11 = var1.getTypeId(var2 + 1, var3, var4 - 1);
        int var12 = var1.getTypeId(var2 + 1, var3, var4 + 1);
        int var13 = var1.getTypeId(var2 - 1, var3, var4 + 1);
        boolean var14 = var8 == this.id || var9 == this.id;
        boolean var15 = var6 == this.id || var7 == this.id;
        boolean var16 = var10 == this.id || var11 == this.id || var12 == this.id || var13 == this.id;

        for (int var17 = var2 - 1; var17 <= var2 + 1; ++var17)
        {
            for (int var18 = var4 - 1; var18 <= var4 + 1; ++var18)
            {
                int var19 = var1.getTypeId(var17, var3 - 1, var18);
                float var20 = 0.0F;

                if (byId[var19] != null && byId[var19].canSustainPlant(var1, var17, var3 - 1, var18, ForgeDirection.UP, this))
                {
                    var20 = 1.0F;

                    if (byId[var19].isFertile(var1, var17, var3 - 1, var18))
                    {
                        var20 = 3.0F;
                    }
                }

                if (var17 != var2 || var18 != var4)
                {
                    var20 /= 4.0F;
                }

                var5 += var20;
            }
        }

        if (var16 || var14 && var15)
        {
            var5 /= 2.0F;
        }

        return var5;
    }

    public int a(int i, int j) {
        return this.textureId;
    }

    public void f() {
        float f = 0.125F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        this.maxY = (double) ((float) (iblockaccess.getData(i, j, k) * 2 + 2) / 16.0F);
        float f = 0.125F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float) this.maxY, 0.5F + f);
    }

    public int d() {
        return 19;
    }


    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
    {
        super.dropNaturally(var1, var2, var3, var4, var5, var6, var7);
    }

    public ArrayList getBlockDropped(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ArrayList var7 = new ArrayList();

        for (int var8 = 0; var8 < 3; ++var8)
        {
            if (var1.random.nextInt(15) <= var5)
            {
                var7.add(new ItemStack(this.blockFruit == PUMPKIN ? Item.PUMPKIN_SEEDS : Item.MELON_SEEDS));
            }
        }

        return var7;
    }

    public int getDropType(int i, Random random, int j) {
        return -1;
    }

    public int a(Random random) {
        return 1;
    }
}
