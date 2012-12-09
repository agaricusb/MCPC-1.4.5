package net.minecraft.server;

import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.CreativeModeTab;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockLadder extends Block {

   protected BlockLadder(int var1, int var2) {
      super(var1, var2, Material.ORIENTABLE);
      this.a(CreativeModeTab.c);
   }

   public AxisAlignedBB e(World var1, int var2, int var3, int var4) {
      this.updateShape(var1, var2, var3, var4);
      return super.e(var1, var2, var3, var4);
   }

   public void updateShape(IBlockAccess var1, int var2, int var3, int var4) {
      this.d(var1.getData(var2, var3, var4));
   }

   public void d(int var1) {
      float var3 = 0.125F;
      if(var1 == 2) {
         this.a(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
      }

      if(var1 == 3) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
      }

      if(var1 == 4) {
         this.a(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

      if(var1 == 5) {
         this.a(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
      }

   }

   public boolean c() {
      return false;
   }

   public boolean b() {
      return false;
   }

   public int d() {
      return 8;
   }

   public boolean canPlace(World var1, int var2, int var3, int var4) {
      return var1.t(var2 - 1, var3, var4)?true:(var1.t(var2 + 1, var3, var4)?true:(var1.t(var2, var3, var4 - 1)?true:var1.t(var2, var3, var4 + 1)));
   }

   public int getPlacedData(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var9) {
      int var10 = var9;
      if((var9 == 0 || var5 == 2) && var1.t(var2, var3, var4 + 1)) {
         var10 = 2;
      }

      if((var10 == 0 || var5 == 3) && var1.t(var2, var3, var4 - 1)) {
         var10 = 3;
      }

      if((var10 == 0 || var5 == 4) && var1.t(var2 + 1, var3, var4)) {
         var10 = 4;
      }

      if((var10 == 0 || var5 == 5) && var1.t(var2 - 1, var3, var4)) {
         var10 = 5;
      }

      return var10;
   }

   public void doPhysics(World var1, int var2, int var3, int var4, int var5) {
      int var6 = var1.getData(var2, var3, var4);
      boolean var7 = false;
      if(var6 == 2 && var1.t(var2, var3, var4 + 1)) {
         var7 = true;
      }

      if(var6 == 3 && var1.t(var2, var3, var4 - 1)) {
         var7 = true;
      }

      if(var6 == 4 && var1.t(var2 + 1, var3, var4)) {
         var7 = true;
      }

      if(var6 == 5 && var1.t(var2 - 1, var3, var4)) {
         var7 = true;
      }

      if(!var7) {
         this.c(var1, var2, var3, var4, var6, 0);
         var1.setTypeId(var2, var3, var4, 0);
      }

      super.doPhysics(var1, var2, var3, var4, var5);
   }

   public int a(Random var1) {
      return 1;
   }
   
   // Forge Hook
   @Override
   public boolean isLadder(World world, int x, int y, int z)
   {
	   return true;
   }
}
