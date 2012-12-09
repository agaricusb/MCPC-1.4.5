package net.minecraft.server;

import java.util.Random;

public class WorldGenGrass extends WorldGenerator
{
    /** Stores ID for WorldGenTallGrass */
    private int a;
    private int b;

    public WorldGenGrass(int var1, int var2)
    {
        this.a = var1;
        this.b = var2;
    }

    public boolean a(World var1, Random var2, int var3, int var4, int var5)
    {
        Block var7 = null;

        do
        {
            var7 = Block.byId[var1.getTypeId(var3, var4, var5)];

            if (var7 != null && !var7.isLeaves(var1, var3, var4, var5))
            {
                break;
            }

            --var4;
        }
        while (var4 > 0);

        for (int var8 = 0; var8 < 128; ++var8)
        {
            int var9 = var3 + var2.nextInt(8) - var2.nextInt(8);
            int var10 = var4 + var2.nextInt(4) - var2.nextInt(4);
            int var11 = var5 + var2.nextInt(8) - var2.nextInt(8);

            if (var1.isEmpty(var9, var10, var11) && Block.byId[this.a].d(var1, var9, var10, var11))
            {
                var1.setRawTypeIdAndData(var9, var10, var11, this.a, this.b);
            }
        }

        return true;
    }
}
