package net.minecraft.server;

import java.util.Random;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.BiomeMeta;
import net.minecraft.server.Block;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityOcelot;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenGrass;
import net.minecraft.server.WorldGenGroundBush;
import net.minecraft.server.WorldGenMegaTree;
import net.minecraft.server.WorldGenTrees;
import net.minecraft.server.WorldGenVines;
import net.minecraft.server.WorldGenerator;

public class BiomeJungle extends BiomeBase {

   public BiomeJungle(int var1) {
      super(var1);
      this.I.z = 50;
      this.I.B = 25;
      this.I.A = 4;
      this.K.add(new BiomeMeta(EntityOcelot.class, 2, 1, 1));
      this.K.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
   }

   public WorldGenerator a(Random var1) {
      return (WorldGenerator)(var1.nextInt(10) == 0?this.P:(var1.nextInt(2) == 0?new WorldGenGroundBush(3, 0):(var1.nextInt(3) == 0?new WorldGenMegaTree(false, 10 + var1.nextInt(20), 3, 3):new WorldGenTrees(false, 4 + var1.nextInt(7), 3, 3, true))));
   }

   public WorldGenerator b(Random var1) {
      return var1.nextInt(4) == 0?new WorldGenGrass(Block.LONG_GRASS.id, 2):new WorldGenGrass(Block.LONG_GRASS.id, 1);
   }

   public void a(World var1, Random var2, int var3, int var4) {
      super.a(var1, var2, var3, var4);
      WorldGenVines var5 = new WorldGenVines();

      for(int var6 = 0; var6 < 50; ++var6) {
         int var7 = var3 + var2.nextInt(16) + 8;
         byte var8 = 64;
         int var9 = var4 + var2.nextInt(16) + 8;
         var5.a(var1, var2, var7, var8, var9);
      }

   }
}
