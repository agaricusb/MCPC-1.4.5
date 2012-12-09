package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.Location;
// CraftBukkit end

public class Chunk {

    public static boolean a;
    private ChunkSection[] sections;
    private byte[] s;
    public int[] b;
    public boolean[] c;
    public boolean d;
    public World world;
    public int[] heightMap;
    public final int x;
    public final int z;
    private boolean t;
    public Map tileEntities;
    public List[] entitySlices;
    public boolean done;
    public boolean l;
    public boolean m;
    public long n;
    public boolean seenByPlayer;
    public int p;
    private int u;
    boolean q;

    public Chunk(World world, int i, int j) {
        this.sections = new ChunkSection[16];
        this.s = new byte[256];
        this.b = new int[256];
        this.c = new boolean[256];
        this.t = false;
        this.tileEntities = new HashMap();
        this.done = false;
        this.l = false;
        this.m = false;
        this.n = 0L;
        this.seenByPlayer = false;
        this.p = 0;
        this.u = 4096;
        this.q = false;
        this.entitySlices = new List[16];
        this.world = world;
        this.x = i;
        this.z = j;
        this.heightMap = new int[256];

        for (int k = 0; k < this.entitySlices.length; ++k) {
            this.entitySlices[k] = new org.bukkit.craftbukkit.util.UnsafeList(); // CraftBukkit - ArrayList -> UnsafeList
        }

        Arrays.fill(this.b, -999);
        Arrays.fill(this.s, (byte) -1);

        // CraftBukkit start
        if (!(this instanceof EmptyChunk)) {
            this.bukkitChunk = new org.bukkit.craftbukkit.CraftChunk(this);
        }
    }

    public org.bukkit.Chunk bukkitChunk;
    public boolean mustSave;
    // CraftBukkit end

    public Chunk(World world, byte[] abyte, int i, int j) {
        this(world, i, j);
        int k = abyte.length / 256;

        for (int l = 0; l < 16; ++l) {
            for (int i1 = 0; i1 < 16; ++i1) {
                for (int j1 = 0; j1 < k; ++j1) {
                    int b0 = abyte[l << 11 | i1 << 7 | j1] & 0xff;

                    if (b0 != 0) {
                        int k1 = j1 >> 4;

                        if (this.sections[k1] == null) {
                            this.sections[k1] = new ChunkSection(k1 << 4);
                        }

                        this.sections[k1].a(l, j1 & 15, i1, b0);
                    }
                }
            }
        }
    }
    

    public Chunk(World var1, byte[] var2, byte[] var3, int var4, int var5)
    {
        this(var1, var4, var5);
        int var6 = var2.length / 256;

        for (int var7 = 0; var7 < 16; ++var7)
        {
            for (int var8 = 0; var8 < 16; ++var8)
            {
                for (int var9 = 0; var9 < var6; ++var9)
                {
                    int var10 = var7 << 11 | var8 << 7 | var9;
                    int var11 = var2[var10] & 255;
                    byte var12 = var3[var10];

                    if (var11 != 0)
                    {
                        int var13 = var9 >> 4;

                        if (this.sections[var13] == null)
                        {
                            this.sections[var13] = new ChunkSection(var13 << 4);
                        }

                        this.sections[var13].a(var7, var9 & 15, var8, var11);
                        this.sections[var13].b(var7, var9 & 15, var8, var12);
                    }
                }
            }
        }
    }

    public boolean a(int i, int j) {
        return i == this.x && j == this.z;
    }

    public int b(int i, int j) {
        return this.heightMap[j << 4 | i];
    }

    public int h() {
        for (int i = this.sections.length - 1; i >= 0; --i) {
            if (this.sections[i] != null) {
                return this.sections[i].d();
            }
        }

        return 0;
    }

    public ChunkSection[] i() {
        return this.sections;
    }

    public void initLighting() {
        int i = this.h();

        this.p = Integer.MAX_VALUE;

        int j;
        int k;

        for (j = 0; j < 16; ++j) {
            k = 0;

            while (k < 16) {
                this.b[j + (k << 4)] = -999;
                int l = i + 16 - 1;

                while (true) {
                    if (l > 0) {
                        if (this.b(j, l - 1, k) == 0) {
                            --l;
                            continue;
                        }

                        this.heightMap[k << 4 | j] = l;
                        if (l < this.p) {
                            this.p = l;
                        }
                    }

                    if (!this.world.worldProvider.f) {
                        l = 15;
                        int i1 = i + 16 - 1;

                        do {
                            l -= this.b(j, i1, k);
                            if (l > 0) {
                                ChunkSection chunksection = this.sections[i1 >> 4];

                                if (chunksection != null) {
                                    chunksection.c(j, i1 & 15, k, l);
                                    this.world.o((this.x << 4) + j, i1, (this.z << 4) + k);
                                }
                            }

                            --i1;
                        } while (i1 > 0 && l > 0);
                    }

                    ++k;
                    break;
                }
            }
        }

        this.l = true;

        for (j = 0; j < 16; ++j) {
            for (k = 0; k < 16; ++k) {
                this.e(j, k);
            }
        }
    }

    private void e(int i, int j) {
        this.c[i + j * 16] = true;
        this.t = true;
    }

    private void q() {
        this.world.methodProfiler.a("recheckGaps");
        if (this.world.areChunksLoaded(this.x * 16 + 8, 0, this.z * 16 + 8, 16)) {
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    if (this.c[i + j * 16]) {
                        this.c[i + j * 16] = false;
                        int k = this.b(i, j);
                        int l = this.x * 16 + i;
                        int i1 = this.z * 16 + j;
                        int j1 = this.world.g(l - 1, i1);
                        int k1 = this.world.g(l + 1, i1);
                        int l1 = this.world.g(l, i1 - 1);
                        int i2 = this.world.g(l, i1 + 1);

                        if (k1 < j1) {
                            j1 = k1;
                        }

                        if (l1 < j1) {
                            j1 = l1;
                        }

                        if (i2 < j1) {
                            j1 = i2;
                        }

                        this.g(l, i1, j1);
                        this.g(l - 1, i1, k);
                        this.g(l + 1, i1, k);
                        this.g(l, i1 - 1, k);
                        this.g(l, i1 + 1, k);
                    }
                }
            }

            this.t = false;
        }

        this.world.methodProfiler.b();
    }

    private void g(int i, int j, int k) {
        int l = this.world.getHighestBlockYAt(i, j);

        if (l > k) {
            this.d(i, j, k, l + 1);
        } else if (l < k) {
            this.d(i, j, l, k + 1);
        }
    }

    private void d(int i, int j, int k, int l) {
        if (l > k && this.world.areChunksLoaded(i, 0, j, 16)) {
            for (int i1 = k; i1 < l; ++i1) {
                this.world.c(EnumSkyBlock.SKY, i, i1, j);
            }

            this.l = true;
        }
    }

    private void h(int i, int j, int k) {
        int l = this.heightMap[k << 4 | i] & 255;
        int i1 = l;

        if (j > l) {
            i1 = j;
        }

        while (i1 > 0 && this.b(i, i1 - 1, k) == 0) {
            --i1;
        }

        if (i1 != l) {
            this.world.g(i + this.x * 16, k + this.z * 16, i1, l);
            this.heightMap[k << 4 | i] = i1;
            int j1 = this.x * 16 + i;
            int k1 = this.z * 16 + k;
            int l1;
            int i2;

            if (!this.world.worldProvider.f) {
                ChunkSection chunksection;

                if (i1 < l) {
                    for (l1 = i1; l1 < l; ++l1) {
                        chunksection = this.sections[l1 >> 4];
                        if (chunksection != null) {
                            chunksection.c(i, l1 & 15, k, 15);
                            this.world.o((this.x << 4) + i, l1, (this.z << 4) + k);
                        }
                    }
                } else {
                    for (l1 = l; l1 < i1; ++l1) {
                        chunksection = this.sections[l1 >> 4];
                        if (chunksection != null) {
                            chunksection.c(i, l1 & 15, k, 0);
                            this.world.o((this.x << 4) + i, l1, (this.z << 4) + k);
                        }
                    }
                }

                l1 = 15;

                while (i1 > 0 && l1 > 0) {
                    --i1;
                    i2 = this.b(i, i1, k);
                    if (i2 == 0) {
                        i2 = 1;
                    }

                    l1 -= i2;
                    if (l1 < 0) {
                        l1 = 0;
                    }

                    ChunkSection chunksection1 = this.sections[i1 >> 4];

                    if (chunksection1 != null) {
                        chunksection1.c(i, i1 & 15, k, l1);
                    }
                }
            }

            l1 = this.heightMap[k << 4 | i];
            i2 = l;
            int j2 = l1;

            if (l1 < l) {
                i2 = l1;
                j2 = l;
            }

            if (l1 < this.p) {
                this.p = l1;
            }

            if (!this.world.worldProvider.f) {
                this.d(j1 - 1, k1, i2, j2);
                this.d(j1 + 1, k1, i2, j2);
                this.d(j1, k1 - 1, i2, j2);
                this.d(j1, k1 + 1, i2, j2);
                this.d(j1, k1, i2, j2);
            }

            this.l = true;
        }
    }

    public int b(int var1, int var2, int var3)
    {
        int var4 = (this.x << 4) + var1;
        int var5 = (this.z << 4) + var3;
        Block var6 = Block.byId[this.getTypeId(var1, var2, var3)];
        return var6 == null ? 0 : var6.getLightOpacity(this.world, var4, var2, var5);
    }

    /**
     * Return the ID of a block in the chunk.
     */
    public int getTypeId(int var1, int var2, int var3)
    {
        if (var2 >> 4 < this.sections.length && var2 >> 4 >= 0)
        {
            ChunkSection var4 = this.sections[var2 >> 4];
            return var4 != null ? var4.a(var1, var2 & 15, var3) : 0;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Return the metadata corresponding to the given coordinates inside a chunk.
     */
    public int getData(int var1, int var2, int var3)
    {
        if (var2 >> 4 < this.sections.length && var2 >> 4 >= 0)
        {
            ChunkSection var4 = this.sections[var2 >> 4];
            return var4 != null ? var4.b(var1, var2 & 15, var3) : 0;
        }
        else
        {
            return 0;
        }
    }

    public boolean a(int i, int j, int k, int l) {
        return this.a(i, j, k, l, 0);
    }

    public boolean a(int i, int j, int k, int l, int i1) {
        int j1 = k << 4 | i;

        if (j >= this.b[j1] - 1) {
            this.b[j1] = -999;
        }

        int k1 = this.heightMap[j1];
        int l1 = this.getTypeId(i, j, k);
        int i2 = this.getData(i, j, k);

        if (l1 == l && i2 == i1) {
            return false;
        } else if (j >> 4 < this.sections.length && j >> 4 >= 0)
            {
            ChunkSection chunksection = this.sections[j >> 4];
            boolean flag = false;

            if (chunksection == null) {
                if (l == 0) {
                    return false;
                }

                chunksection = this.sections[j >> 4] = new ChunkSection(j >> 4 << 4);
                flag = j >= k1;
            }

            int j2 = this.x * 16 + i;
            int k2 = this.z * 16 + k;

            if (l1 != 0 && !this.world.isStatic) {
                Block.byId[l1].h(this.world, j2, j, k2, i2);
            }

            chunksection.a(i, j & 15, k, l);
            if (l1 != 0) {
                if (!this.world.isStatic) {
                    Block.byId[l1].remove(this.world, j2, j, k2, l1, i2);
                } else if (Block.byId[l1] instanceof BlockContainer && l1 != l) {
                    this.world.r(j2, j, k2);
                }
            }

            if (chunksection.a(i, j & 15, k) != l) {
                return false;
            } else {
                chunksection.b(i, j & 15, k, i1);
                if (flag) {
                    this.initLighting();
                } else {
                    if (this.b(i, j, k) > 0) {
                        if (j >= k1) {
                            this.h(i, j + 1, k);
                        }
                    } else if (j == k1 - 1) {
                        this.h(i, j, k);
                    }

                    this.e(i, k);
                }

                if (l != 0) {
                    if (!this.world.isStatic) {
                        // CraftBukkit start - Don't extend piston until data is set
                        if (!(Block.byId[l] instanceof BlockPiston) || i2 != 0) {
                            Block.byId[l].onPlace(this.world, j2, j, k2);
                        }
                        // CraftBukkit end
                    }

                    if (Block.byId[l] != null && Block.byId[l].hasTileEntity(i1))
                    {
                        // CraftBukkit start - don't create tile entity if placement failed
                        if (this.getTypeId(i, j, k) != l) {
                            return false;
                        }
                        // CraftBukkit end

                    	TileEntity tileentity = this.e(i, j, k);
                        if (tileentity == null) {
                            tileentity = Block.byId[l].createTileEntity(this.world, i1);
                            this.world.setTileEntity(j2, j, k2, tileentity);
                        }

                        if (tileentity != null) {
                            tileentity.h();
                        }
                    }
                }
                    else if (l1 > 0 && Block.byId[l1] instanceof BlockContainer) {
                    	TileEntity tileentity = this.e(i, j, k);
                    	if (tileentity != null) {
                    		tileentity.h();
                    	}
                }
                this.l = true;
                return true;
            }
        }
        else
        	return false;
    }

    /**
     * Set the metadata of a block in the chunk
     */
    public boolean b(int var1, int var2, int var3, int var4)
    {
        ChunkSection var5 = var2 >> 4 < this.sections.length && var2 >> 4 >= 0 ? this.sections[var2 >> 4] : null;

        if (var5 == null)
        {
            return false;
        }
        else
        {
            int var6 = var5.b(var1, var2 & 15, var3);

            if (var6 == var4)
            {
                return false;
            }
            else
            {
                this.l = true;
                var5.b(var1, var2 & 15, var3, var4);
                int var7 = var5.a(var1, var2 & 15, var3);

                if (var7 > 0 && Block.byId[var7] != null && Block.byId[var7].hasTileEntity(var4))
                {
                    TileEntity var8 = this.e(var1, var2, var3);

                    if (var8 != null)
                    {
                        var8.h();
                        var8.p = var4;
                    }
                }

                return true;
            }
        }
    }


    /**
     * Gets the amount of light saved in this block (doesn't adjust for daylight)
     */
    public int getBrightness(EnumSkyBlock var1, int var2, int var3, int var4)
    {
        ChunkSection var5 = var3 >> 4 < this.sections.length && var3 >> 4 >= 0 ? this.sections[var3 >> 4] : null;
        return var5 == null ? (this.d(var2, var3, var4) ? var1.c : 0) : (var1 == EnumSkyBlock.SKY ? var5.c(var2, var3 & 15, var4) : (var1 == EnumSkyBlock.BLOCK ? var5.d(var2, var3 & 15, var4) : var1.c));
    }

    /**
     * Sets the light value at the coordinate. If enumskyblock is set to sky it sets it in the skylightmap and if its a
     * block then into the blocklightmap. Args enumSkyBlock, x, y, z, lightValue
     */
    public void a(EnumSkyBlock var1, int var2, int var3, int var4, int var5)
    {
        if (var3 >> 4 < this.sections.length && var3 >> 4 >= 0)
        {
            ChunkSection var6 = this.sections[var3 >> 4];

            if (var6 == null)
            {
                var6 = this.sections[var3 >> 4] = new ChunkSection(var3 >> 4 << 4);
                this.initLighting();
            }

            this.l = true;

            if (var1 == EnumSkyBlock.SKY)
            {
                if (!this.world.worldProvider.f)
                {
                    var6.c(var2, var3 & 15, var4, var5);
                }
            }
            else if (var1 == EnumSkyBlock.BLOCK)
            {
                var6.d(var2, var3 & 15, var4, var5);
            }
        }
    }



    /**
     * Gets the amount of light on a block taking into account sunlight
     */
    public int c(int var1, int var2, int var3, int var4)
    {
        ChunkSection var5 = var2 >> 4 < this.sections.length && var2 >> 4 >= 0 ? this.sections[var2 >> 4] : null;

        if (var5 != null)
        {
            int var6 = this.world.worldProvider.f ? 0 : var5.c(var1, var2 & 15, var3);

            if (var6 > 0)
            {
                a = true;
            }

            var6 -= var4;
            int var7 = var5.d(var1, var2 & 15, var3);

            if (var7 > var6)
            {
                var6 = var7;
            }

            return var6;
        }
        else
        {
            return !this.world.worldProvider.f && var4 < EnumSkyBlock.SKY.c ? EnumSkyBlock.SKY.c - var4 : 0;
        }
    }

    /**
     * Adds an entity to the chunk. Args: entity
     */
    public void a(Entity entity) {
        this.m = true;
        int i = MathHelper.floor(entity.locX / 16.0D);
        int j = MathHelper.floor(entity.locZ / 16.0D);

        if (i != this.x || j != this.z) {
            // CraftBukkit start
            Bukkit.getLogger().warning("Wrong location for " + entity + " in world '" + world.getWorld().getName() + "'!");
            // Thread.dumpStack();
            Bukkit.getLogger().warning("Entity is at " + entity.locX + "," + entity.locZ + " (chunk " + i + "," + j + ") but was stored in chunk " + this.x + "," + this.z);
            // CraftBukkit end
        }

        int k = MathHelper.floor(entity.locY / 16.0D);

        if (k < 0) {
            k = 0;
        }

        if (k >= this.entitySlices.length) {
            k = this.entitySlices.length - 1;
        }

        MinecraftForge.EVENT_BUS.post(new EnteringChunk(entity, this.x, this.z, entity.ai, entity.ak));
        entity.ah = true;
        entity.ai = this.x;
        entity.aj = k;
        entity.ak = this.z;
        this.entitySlices[k].add(entity);
    }

    public void b(Entity entity) {
        this.a(entity, entity.aj);
    }

    public void a(Entity entity, int i) {
        if (i < 0) {
            i = 0;
        }

        if (i >= this.entitySlices.length) {
            i = this.entitySlices.length - 1;
        }

        this.entitySlices[i].remove(entity);
    }

    public boolean d(int i, int j, int k) {
        return j >= this.heightMap[k << 4 | i];
    }


    /**
     * Gets the TileEntity for a given block in this chunk
     */
    public TileEntity e(int var1, int var2, int var3)
    {
        ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
        TileEntity var5 = (TileEntity)this.tileEntities.get(var4);

        if (var5 != null && var5.r())
        {
            this.tileEntities.remove(var4);
            var5 = null;
        }

        if (var5 == null)
        {
            int var6 = this.getTypeId(var1, var2, var3);
            int var7 = this.getData(var1, var2, var3);

            if (var6 <= 0 || !Block.byId[var6].hasTileEntity(var7))
            {
                return null;
            }

            if (var5 == null)
            {
                var5 = Block.byId[var6].createTileEntity(this.world, var7);
                this.world.setTileEntity(this.x * 16 + var1, var2, this.z * 16 + var3, var5);
            }

            var5 = (TileEntity)this.tileEntities.get(var4);
        }

        //return var5;
      if(var5 != null && var5.r()) {
         this.tileEntities.remove(var4);
         return null;
      } else {
         return var5;
      }
    }

    public void a(TileEntity tileentity) {
        int i = tileentity.x - this.x * 16;
        int j = tileentity.y;
        int k = tileentity.z - this.z * 16;

        this.a(i, j, k, tileentity);
        if (this.d) {
            this.world.addTileEntity(tileentity);
        }
    }

    /**
     * Sets the TileEntity for a given block in this chunk
     */
   public void a(int i, int j, int k, TileEntity tileentity) {
      ChunkPosition chunkposition = new ChunkPosition(i, j, k);
      tileentity.b(this.world);
      tileentity.x = this.x * 16 + i;
      tileentity.y = j;
      tileentity.z = this.z * 16 + k;
      if(this.getTypeId(i, j, k) != 0 && Block.byId[this.getTypeId(i, j, k)] instanceof BlockContainer) {
         tileentity.s();
         this.tileEntities.put(chunkposition, tileentity);
      } else {
         System.out.println("Attempted to place a tile entity (" + tileentity + ") at " + tileentity.x + "," + tileentity.y + "," + tileentity.z + " (" + org.bukkit.Material.getMaterial(this.getTypeId(i, j, k)) + ") where there was no entity tile!");
         System.out.println("Chunk coordinates: " + this.x * 16 + "," + this.z * 16);
         (new Exception()).printStackTrace();
      }

   }

    public void f(int i, int j, int k) {
        ChunkPosition chunkposition = new ChunkPosition(i, j, k);

        if (this.d) {
            TileEntity tileentity = (TileEntity) this.tileEntities.remove(chunkposition);

            if (tileentity != null) {
                tileentity.w_();
            }
        }
    }

    /**
     * Called when this Chunk is loaded by the ChunkProvider
     */
    public void addEntities()
    {
      this.d = true;
      this.world.a(this.tileEntities.values());

      for(int i = 0; i < this.entitySlices.length; ++i) {
         this.world.a(this.entitySlices[i]);
      }

        MinecraftForge.EVENT_BUS.post(new Load(this));
    }

    public void removeEntities() {
        this.d = false;
        Iterator iterator = this.tileEntities.values().iterator();

        while (iterator.hasNext()) {
            TileEntity tileentity = (TileEntity) iterator.next();

            this.world.a(tileentity);
        }

        for (int i = 0; i < this.entitySlices.length; ++i) {
            // CraftBukkit start
            java.util.Iterator<Object> iter = this.entitySlices[i].iterator();
            while (iter.hasNext()) {
                Entity entity = (Entity) iter.next();
                int cx = Location.locToBlock(entity.locX) >> 4;
                int cz = Location.locToBlock(entity.locZ) >> 4;

                // Do not pass along players, as doing so can get them stuck outside of time.
                // (which for example disables inventory icon updates and prevents block breaking)
                if (entity instanceof EntityPlayer && (cx != this.x || cz != this.z)) {
                    iter.remove();
                }
            }
            // CraftBukkit end

            this.world.b(this.entitySlices[i]);
        }
        
        MinecraftForge.EVENT_BUS.post(new Unload(this));
    }

    public void e() {
        this.l = true;
    }

    public void a(Entity entity, AxisAlignedBB axisalignedbb, List list) {
        int i = MathHelper.floor((axisalignedbb.b - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor((axisalignedbb.e + World.MAX_ENTITY_RADIUS) / 16.0D);

        if (i < 0) {
            i = 0;
        }

        if (j >= this.entitySlices.length) {
            j = this.entitySlices.length - 1;
        }

        for (int k = i; k <= j; ++k) {
            List list1 = this.entitySlices[k];

            for (int l = 0; l < list1.size(); ++l) {
                Entity entity1 = (Entity) list1.get(l);

                if (entity1 != entity && entity1.boundingBox.a(axisalignedbb)) {
                    list.add(entity1);
                    Entity[] aentity = entity1.ao();

                    if (aentity != null) {
                        for (int i1 = 0; i1 < aentity.length; ++i1) {
                            entity1 = aentity[i1];
                            if (entity1 != entity && entity1.boundingBox.a(axisalignedbb)) {
                                list.add(entity1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(Class oclass, AxisAlignedBB axisalignedbb, List list, IEntitySelector ientityselector) {
        int i = MathHelper.floor((axisalignedbb.b - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor((axisalignedbb.e + World.MAX_ENTITY_RADIUS) / 16.0D);

        if (i < 0) {
            i = 0;
        } else if (i >= this.entitySlices.length) {
            i = this.entitySlices.length - 1;
        }

        if (j >= this.entitySlices.length) {
            j = this.entitySlices.length - 1;
        } else if (j < 0) {
            j = 0;
        }

        for (int k = i; k <= j; ++k) {
            List list1 = this.entitySlices[k];

            for (int l = 0; l < list1.size(); ++l) {
                Entity entity = (Entity) list1.get(l);

                if (oclass.isAssignableFrom(entity.getClass()) && entity.boundingBox.a(axisalignedbb) && (ientityselector == null || ientityselector.a(entity))) {
                    list.add(entity);
                }
            }
        }
    }

    public boolean a(boolean flag) {
        if (flag) {
            if (this.m && this.world.getTime() != this.n) {
                return true;
            }
        } else if (this.m && this.world.getTime() >= this.n + 600L) {
            return true;
        }

        return this.l;
    }

    public Random a(long i) {
        return new Random(this.world.getSeed() + (long) (this.x * this.x * 4987142) + (long) (this.x * 5947611) + (long) (this.z * this.z) * 4392871L + (long) (this.z * 389711) ^ i);
    }

    public boolean isEmpty() {
        return false;
    }

    public void a(IChunkProvider ichunkprovider, IChunkProvider ichunkprovider1, int i, int j) {
        if (!this.done && ichunkprovider.isChunkLoaded(i + 1, j + 1) && ichunkprovider.isChunkLoaded(i, j + 1) && ichunkprovider.isChunkLoaded(i + 1, j)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i, j);
        }

        if (ichunkprovider.isChunkLoaded(i - 1, j) && !ichunkprovider.getOrCreateChunk(i - 1, j).done && ichunkprovider.isChunkLoaded(i - 1, j + 1) && ichunkprovider.isChunkLoaded(i, j + 1) && ichunkprovider.isChunkLoaded(i - 1, j + 1)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j);
        }

        if (ichunkprovider.isChunkLoaded(i, j - 1) && !ichunkprovider.getOrCreateChunk(i, j - 1).done && ichunkprovider.isChunkLoaded(i + 1, j - 1) && ichunkprovider.isChunkLoaded(i + 1, j - 1) && ichunkprovider.isChunkLoaded(i + 1, j)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i, j - 1);
        }

        if (ichunkprovider.isChunkLoaded(i - 1, j - 1) && !ichunkprovider.getOrCreateChunk(i - 1, j - 1).done && ichunkprovider.isChunkLoaded(i, j - 1) && ichunkprovider.isChunkLoaded(i - 1, j)) {
            ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j - 1);
        }
    }

    public int d(int i, int j) {
        int k = i | j << 4;
        int l = this.b[k];

        if (l == -999) {
            int i1 = this.h() + 15;

            l = -1;

            while (i1 > 0 && l == -1) {
                int j1 = this.getTypeId(i, i1, j);
                Material material = j1 == 0 ? Material.AIR : Block.byId[j1].material;

                if (!material.isSolid() && !material.isLiquid()) {
                    --i1;
                } else {
                    l = i1 + 1;
                }
            }

            this.b[k] = l;
        }

        return l;
    }

    public void k() {
        if (this.t && !this.world.worldProvider.f) {
            this.q();
        }
    }

    public ChunkCoordIntPair l() {
        return new ChunkCoordIntPair(this.x, this.z);
    }

    public boolean c(int i, int j) {
        if (i < 0) {
            i = 0;
        }

        if (j >= 256) {
            j = 255;
        }

        for (int k = i; k <= j; k += 16) {
            ChunkSection chunksection = this.sections[k >> 4];

            if (chunksection != null && !chunksection.a()) {
                return false;
            }
        }

        return true;
    }

    public void a(ChunkSection[] achunksection) {
        this.sections = achunksection;
    }

    public BiomeBase a(int i, int j, WorldChunkManager worldchunkmanager) {
        int k = this.s[j << 4 | i] & 255;

        if (k == 255) {
            BiomeBase biomebase = worldchunkmanager.getBiome((this.x << 4) + i, (this.z << 4) + j);

            k = biomebase.id;
            this.s[j << 4 | i] = (byte) (k & 255);
        }

        return BiomeBase.biomes[k] == null ? BiomeBase.PLAINS : BiomeBase.biomes[k];
    }

    public byte[] m() {
        return this.s;
    }

    public void a(byte[] abyte) {
        this.s = abyte;
    }

    public void n() {
        this.u = 0;
    }

    public void o() {
        for (int i = 0; i < 8; ++i) {
            if (this.u >= 4096) {
                return;
            }

            int j = this.u % 16;
            int k = this.u / 16 % 16;
            int l = this.u / 256;

            ++this.u;
            int i1 = (this.x << 4) + k;
            int j1 = (this.z << 4) + l;

            for (int k1 = 0; k1 < 16; ++k1) {
                int l1 = (j << 4) + k1;

                if (this.sections[j] == null && (k1 == 0 || k1 == 15 || k == 0 || k == 15 || l == 0 || l == 15) || this.sections[j] != null && this.sections[j].a(k, k1, l) == 0) {
                    if (Block.lightEmission[this.world.getTypeId(i1, l1 - 1, j1)] > 0) {
                        this.world.z(i1, l1 - 1, j1);
                    }

                    if (Block.lightEmission[this.world.getTypeId(i1, l1 + 1, j1)] > 0) {
                        this.world.z(i1, l1 + 1, j1);
                    }

                    if (Block.lightEmission[this.world.getTypeId(i1 - 1, l1, j1)] > 0) {
                        this.world.z(i1 - 1, l1, j1);
                    }

                    if (Block.lightEmission[this.world.getTypeId(i1 + 1, l1, j1)] > 0) {
                        this.world.z(i1 + 1, l1, j1);
                    }

                    if (Block.lightEmission[this.world.getTypeId(i1, l1, j1 - 1)] > 0) {
                        this.world.z(i1, l1, j1 - 1);
                    }

                    if (Block.lightEmission[this.world.getTypeId(i1, l1, j1 + 1)] > 0) {
                        this.world.z(i1, l1, j1 + 1);
                    }

                    this.world.z(i1, l1, j1);
                }
            }
        }
    }
    

    public void cleanChunkBlockTileEntity(int var1, int var2, int var3)
    {
        ChunkPosition var4 = new ChunkPosition(var1, var2, var3);

        if (this.d)
        {
            TileEntity var5 = (TileEntity)this.tileEntities.get(var4);

            if (var5 != null && var5.r())
            {
                this.tileEntities.remove(var4);
            }
        }
    }
}
