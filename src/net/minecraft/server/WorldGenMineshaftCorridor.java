package net.minecraft.server;

import java.util.List;
import java.util.Random;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenMineshaftCorridor extends StructurePiece
{
    private final boolean a;
    private final boolean b;
    private boolean c;

    /**
     * A count of the different sections of this mine. The space between ceiling supports.
     */
    private int d;

    public WorldGenMineshaftCorridor(int var1, Random var2, StructureBoundingBox var3, int var4)
    {
        super(var1);
        this.f = var4;
        this.e = var3;
        this.a = var2.nextInt(3) == 0;
        this.b = !this.a && var2.nextInt(23) == 0;

        if (this.f != 2 && this.f != 0)
        {
            this.d = var3.b() / 5;
        }
        else
        {
            this.d = var3.d() / 5;
        }
    }

    public static StructureBoundingBox a(List var0, Random var1, int var2, int var3, int var4, int var5)
    {
        StructureBoundingBox var6 = new StructureBoundingBox(var2, var3, var4, var2, var3 + 2, var4);
        int var7;

        for (var7 = var1.nextInt(3) + 2; var7 > 0; --var7)
        {
            int var8 = var7 * 5;

            switch (var5)
            {
                case 0:
                    var6.d = var2 + 2;
                    var6.f = var4 + (var8 - 1);
                    break;

                case 1:
                    var6.a = var2 - (var8 - 1);
                    var6.f = var4 + 2;
                    break;

                case 2:
                    var6.d = var2 + 2;
                    var6.c = var4 - (var8 - 1);
                    break;

                case 3:
                    var6.d = var2 + (var8 - 1);
                    var6.f = var4 + 2;
            }

            if (StructurePiece.a(var0, var6) == null)
            {
                break;
            }
        }

        return var7 > 0 ? var6 : null;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece var1, List var2, Random var3)
    {
        int var4 = this.c();
        int var5 = var3.nextInt(4);

        switch (this.f)
        {
            case 0:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a, this.e.b - 1 + var3.nextInt(3), this.e.f + 1, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a - 1, this.e.b - 1 + var3.nextInt(3), this.e.f - 3, 1, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.d + 1, this.e.b - 1 + var3.nextInt(3), this.e.f - 3, 3, var4);
                }

                break;

            case 1:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a - 1, this.e.b - 1 + var3.nextInt(3), this.e.c, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a, this.e.b - 1 + var3.nextInt(3), this.e.c - 1, 2, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a, this.e.b - 1 + var3.nextInt(3), this.e.f + 1, 0, var4);
                }

                break;

            case 2:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a, this.e.b - 1 + var3.nextInt(3), this.e.c - 1, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a - 1, this.e.b - 1 + var3.nextInt(3), this.e.c, 1, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.d + 1, this.e.b - 1 + var3.nextInt(3), this.e.c, 3, var4);
                }

                break;

            case 3:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.d + 1, this.e.b - 1 + var3.nextInt(3), this.e.c, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.d - 3, this.e.b - 1 + var3.nextInt(3), this.e.c - 1, 2, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(var1, var2, var3, this.e.d - 3, this.e.b - 1 + var3.nextInt(3), this.e.f + 1, 0, var4);
                }
        }

        if (var4 < 8)
        {
            int var6;
            int var7;

            if (this.f != 2 && this.f != 0)
            {
                for (var6 = this.e.a + 3; var6 + 3 <= this.e.d; var6 += 5)
                {
                    var7 = var3.nextInt(5);

                    if (var7 == 0)
                    {
                        WorldGenMineshaftPieces.a(var1, var2, var3, var6, this.e.b, this.e.c - 1, 2, var4 + 1);
                    }
                    else if (var7 == 1)
                    {
                        WorldGenMineshaftPieces.a(var1, var2, var3, var6, this.e.b, this.e.f + 1, 0, var4 + 1);
                    }
                }
            }
            else
            {
                for (var6 = this.e.c + 3; var6 + 3 <= this.e.f; var6 += 5)
                {
                    var7 = var3.nextInt(5);

                    if (var7 == 0)
                    {
                        WorldGenMineshaftPieces.a(var1, var2, var3, this.e.a - 1, this.e.b, var6, 1, var4 + 1);
                    }
                    else if (var7 == 1)
                    {
                        WorldGenMineshaftPieces.a(var1, var2, var3, this.e.d + 1, this.e.b, var6, 3, var4 + 1);
                    }
                }
            }
        }
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World var1, Random var2, StructureBoundingBox var3)
    {
        if (this.a(var1, var3))
        {
            return false;
        }
        else
        {
            int var4 = this.d * 5 - 1;
            this.a(var1, var3, 0, 0, 0, 2, 1, var4, 0, 0, false);
            this.a(var1, var3, var2, 0.8F, 0, 2, 0, 2, 2, var4, 0, 0, false);

            if (this.b)
            {
                this.a(var1, var3, var2, 0.6F, 0, 0, 0, 2, 1, var4, Block.WEB.id, 0, false);
            }

            int var5;
            int var6;
            int var7;

            for (var5 = 0; var5 < this.d; ++var5)
            {
                var6 = 2 + var5 * 5;
                this.a(var1, var3, 0, 0, var6, 0, 1, var6, Block.FENCE.id, 0, false);
                this.a(var1, var3, 2, 0, var6, 2, 1, var6, Block.FENCE.id, 0, false);

                if (var2.nextInt(4) == 0)
                {
                    this.a(var1, var3, 0, 2, var6, 0, 2, var6, Block.WOOD.id, 0, false);
                    this.a(var1, var3, 2, 2, var6, 2, 2, var6, Block.WOOD.id, 0, false);
                }
                else
                {
                    this.a(var1, var3, 0, 2, var6, 2, 2, var6, Block.WOOD.id, 0, false);
                }

                this.a(var1, var3, var2, 0.1F, 0, 2, var6 - 1, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.1F, 2, 2, var6 - 1, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.1F, 0, 2, var6 + 1, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.1F, 2, 2, var6 + 1, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.05F, 0, 2, var6 - 2, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.05F, 2, 2, var6 - 2, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.05F, 0, 2, var6 + 2, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.05F, 2, 2, var6 + 2, Block.WEB.id, 0);
                this.a(var1, var3, var2, 0.05F, 1, 2, var6 - 1, Block.TORCH.id, 0);
                this.a(var1, var3, var2, 0.05F, 1, 2, var6 + 1, Block.TORCH.id, 0);
                ChestGenHooks var8 = ChestGenHooks.getInfo("mineshaftCorridor");

                if (var2.nextInt(100) == 0)
                {
                    this.a(var1, var3, var2, 2, 0, var6 - 1, var8.getItems(), var8.getCount(var2));
                }

                if (var2.nextInt(100) == 0)
                {
                    this.a(var1, var3, var2, 0, 0, var6 + 1, var8.getItems(), var8.getCount(var2));
                }

                if (this.b && !this.c)
                {
                    var7 = this.a(0);
                    int var9 = var6 - 1 + var2.nextInt(3);
                    int var10 = this.a(1, var9);
                    var9 = this.b(1, var9);

                    if (var3.b(var10, var7, var9))
                    {
                        this.c = true;
                        var1.setTypeId(var10, var7, var9, Block.MOB_SPAWNER.id);
                        TileEntityMobSpawner var11 = (TileEntityMobSpawner)var1.getTileEntity(var10, var7, var9);

                        if (var11 != null)
                        {
                            var11.a("CaveSpider");
                        }
                    }
                }
            }

            for (var5 = 0; var5 <= 2; ++var5)
            {
                for (var6 = 0; var6 <= var4; ++var6)
                {
                    var7 = this.a(var1, var5, -1, var6, var3);

                    if (var7 == 0)
                    {
                        this.a(var1, Block.WOOD.id, 0, var5, -1, var6, var3);
                    }
                }
            }

            if (this.a)
            {
                for (var5 = 0; var5 <= var4; ++var5)
                {
                    var6 = this.a(var1, 1, -1, var5, var3);

                    if (var6 > 0 && Block.q[var6])
                    {
                        this.a(var1, var3, var2, 0.7F, 1, 0, var5, Block.RAILS.id, this.c(Block.RAILS.id, 0));
                    }
                }
            }

            return true;
        }
    }
}
