package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.util.Vector;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
// CraftBukkit end

import net.minecraftforge.common.IMinecartCollisionHandler;
import net.minecraftforge.common.MinecartRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;

public class EntityMinecart extends Entity implements IInventory
{
    /** Array of item stacks stored in minecart (for storage minecarts). */
    protected ItemStack[] items;
    protected int e;
    protected boolean f;
    private int maxStack = MAX_STACK;
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();

    /** The type of minecart, 2 for powered, 1 for storage. */
    public int type;
    public double b;
    public double c;
    protected final IUpdatePlayerListBox g;
    protected boolean h;
    protected static final int[][][] matrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};

    /** appears to be the progress of the turn */
    protected int j;
    protected double as;
    protected double at;
    protected double au;
    protected double av;
    protected double aw;

    public static float defaultMaxSpeedRail = 0.4F;
    public static float defaultMaxSpeedGround = 0.4F;
    public static float defaultMaxSpeedAirLateral = 0.4F;
    public static float defaultMaxSpeedAirVertical = -1.0F;
    public static double defaultDragRidden = 0.996999979019165D;
    public static double defaultDragEmpty = 0.9599999785423279D;
    public static double defaultDragAir = 0.949999988079071D;
    protected boolean canUseRail;
    protected boolean canBePushed;
    private static IMinecartCollisionHandler collisionHandler = null;
    protected float maxSpeedRail;
    protected float maxSpeedGround;
    protected float maxSpeedAirLateral;
    protected float maxSpeedAirVertical;
    protected double dragAir;

    public EntityMinecart(World var1)
    {
        super(var1);
        this.items = new ItemStack[27]; // CraftBukkit
        this.canUseRail = true;
        this.canBePushed = true;
        this.items = new ItemStack[36];
        this.e = 0;
        this.f = false;
        this.h = true;
        this.m = true;
        this.a(0.98F, 0.7F);
        this.height = this.length / 2.0F;
        this.g = var1 != null ? var1.a(this) : null;
        this.maxSpeedRail = defaultMaxSpeedRail;
        this.maxSpeedGround = defaultMaxSpeedGround;
        this.maxSpeedAirLateral = defaultMaxSpeedAirLateral;
        this.maxSpeedAirVertical = defaultMaxSpeedAirVertical;
        this.dragAir = defaultDragAir;
        
        this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle) this.getBukkitEntity())); // CraftBukkit
    }

    public EntityMinecart(World var1, int var2)
    {
        this(var1);
        this.type = var2;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    protected void a()
    {
        this.datawatcher.a(16, new Byte((byte)0));
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Integer(0));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB g(Entity var1)
    {
        return getCollisionHandler() != null ? getCollisionHandler().getCollisionBox(this, var1) : var1.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB E()
    {
        return getCollisionHandler() != null ? getCollisionHandler().getBoundingBox(this) : null;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean M()
    {
        return this.canBePushed;
    }

    public EntityMinecart(World var1, double var2, double var4, double var6, int var8)
    {
        this(var1);
        this.setPosition(var2, var4 + (double)this.height, var6);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = var2;
        this.lastY = var4;
        this.lastZ = var6;
        this.type = var8;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double X()
    {
        return (double)this.length * 0.0D - 0.30000001192092896D;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource var1, int var2)
    {
        if (this.isInvulnerable()) {
            return false;
        } else {
        if (!this.world.isStatic && !this.dead)
        {
            // CraftBukkit start
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();
            org.bukkit.entity.Entity passenger = (var1.getEntity() == null) ? null : var1.getEntity().getBukkitEntity();

            VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, var2);
            this.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return true;
            }

            var2 = event.getDamage();
            // CraftBukkit end
            
            this.func_70494_i(-this.func_70493_k());
            this.func_70497_h(10);
            this.K();
            this.setDamage(this.getDamage() + var2 * 10);

            if (var1.getEntity() instanceof EntityHuman && ((EntityHuman)var1.getEntity()).abilities.canInstantlyBuild)
            {
                this.setDamage(100);
            }

            if (this.getDamage() > 40)
            {
                if (this.passenger != null)
                {
                    this.passenger.mount(this);
                }
                
                // CraftBukkit start
                VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
                this.world.getServer().getPluginManager().callEvent(destroyEvent);

                if (destroyEvent.isCancelled()) {
                    this.setDamage(40); // Maximize damage so this doesn't get triggered again right away
                    return true;
                }
                // CraftBukkit end

                this.die();
                this.dropCartAsItem();
            }

            return true;
        }
        else
        {
            return true;
        }
    }
}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Will get destroyed next tick.
     */
    public void die()
    {
        if (this.h)
        {
            for (int var1 = 0; var1 < this.getSize(); ++var1)
            {
                ItemStack var2 = this.getItem(var1);

                if (var2 != null)
                {
                    float var3 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var4 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var5 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (var2.count > 0)
                    {
                        int var6 = this.random.nextInt(21) + 10;

                        if (var6 > var2.count)
                        {
                            var6 = var2.count;
                        }

                        var2.count -= var6;
                        EntityItem var7 = new EntityItem(this.world, this.locX + (double)var3, this.locY + (double)var4, this.locZ + (double)var5, new ItemStack(var2.id, var6, var2.getData()));

                        if (var2.hasTag())
                        {
                            var7.itemStack.setTag((NBTTagCompound)var2.getTag().clone());
                        }

                        float var8 = 0.05F;
                        var7.motX = (double)((float)this.random.nextGaussian() * var8);
                        var7.motY = (double)((float)this.random.nextGaussian() * var8 + 0.2F);
                        var7.motZ = (double)((float)this.random.nextGaussian() * var8);
                        this.world.addEntity(var7);
                    }
                }
            }
        }

        super.die();

        if (this.g != null)
        {
            this.g.a();
        }
    }

    public void b(int var1)
    {
        this.h = false;
        super.b(var1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        // CraftBukkit start
        double prevX = this.locX;
        double prevY = this.locY;
        double prevZ = this.locZ;
        float prevYaw = this.yaw;
        float prevPitch = this.pitch;
        // CraftBukkit end
        
        if (this.g != null)
        {
            this.g.a();
        }

        if (this.func_70496_j() > 0)
        {
            this.func_70497_h(this.func_70496_j() - 1);
        }

        if (this.getDamage() > 0)
        {
            this.setDamage(this.getDamage() - 1);
        }

        if (this.locY < -64.0D)
        {
            this.C();
        }

        if (this.h() && this.random.nextInt(4) == 0 && this.type == 2 && this.getClass() == EntityMinecart.class)
        {
            this.world.addParticle("largesmoke", this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D);
        }

        if (this.world.isStatic)
        {
            if (this.j > 0)
            {
                double var1 = this.locX + (this.as - this.locX) / (double)this.j;
                double var3 = this.locY + (this.at - this.locY) / (double)this.j;
                double var5 = this.locZ + (this.au - this.locZ) / (double)this.j;
                double var7 = MathHelper.g(this.av - (double)this.yaw);
                this.yaw = (float)((double)this.yaw + var7 / (double)this.j);
                this.pitch = (float)((double)this.pitch + (this.aw - (double)this.pitch) / (double)this.j);
                --this.j;
                this.setPosition(var1, var3, var5);
                this.b(this.yaw, this.pitch);
            }
            else
            {
                this.setPosition(this.locX, this.locY, this.locZ);
                this.b(this.yaw, this.pitch);
            }
        }
        else
        {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            this.motY -= 0.03999999910593033D;
            int var45 = MathHelper.floor(this.locX);
            int var2 = MathHelper.floor(this.locY);
            int var46 = MathHelper.floor(this.locZ);

            if (BlockMinecartTrack.e_(this.world, var45, var2 - 1, var46))
            {
                --var2;
            }

            double var4 = 0.4D;
            double var6 = 0.0078125D;
            int var8 = this.world.getTypeId(var45, var2, var46);

            if (this.canUseRail() && BlockMinecartTrack.d(var8))
            {
                this.fallDistance = 0.0F;
                Vec3D var9 = this.func_70489_a(this.locX, this.locY, this.locZ);
                int var10 = ((BlockMinecartTrack)Block.byId[var8]).getBasicRailMetadata(this.world, this, var45, var2, var46);
                this.locY = (double)var2;
                boolean var11 = false;
                boolean var12 = false;

                if (var8 == Block.GOLDEN_RAIL.id)
                {
                    var11 = (this.world.getData(var45, var2, var46) & 8) != 0;
                    var12 = !var11;
                }

                if (((BlockMinecartTrack)Block.byId[var8]).p())
                {
                    var10 &= 7;
                }

                if (var10 >= 2 && var10 <= 5)
                {
                    this.locY = (double)(var2 + 1);
                }

                this.adjustSlopeVelocities(var10);
                int[][] var13 = matrix[var10];
                double var14 = (double)(var13[1][0] - var13[0][0]);
                double var16 = (double)(var13[1][2] - var13[0][2]);
                double var18 = Math.sqrt(var14 * var14 + var16 * var16);
                double var20 = this.motX * var14 + this.motZ * var16;

                if (var20 < 0.0D)
                {
                    var14 = -var14;
                    var16 = -var16;
                }

                double var22 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                this.motX = var22 * var14 / var18;
                this.motZ = var22 * var16 / var18;
                double var24;
                double var26;

                if (this.passenger != null)
                {
                    var24 = this.passenger.motX * this.passenger.motX + this.passenger.motZ * this.passenger.motZ;
                    var26 = this.motX * this.motX + this.motZ * this.motZ;

                    if (var24 > 1.0E-4D && var26 < 0.01D)
                    {
                        this.motX += this.passenger.motX * 0.1D;
                        this.motZ += this.passenger.motZ * 0.1D;
                        var12 = false;
                    }
                }

                if (var12 && this.shouldDoRailFunctions())
                {
                    var24 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                    if (var24 < 0.03D)
                    {
                        this.motX *= 0.0D;
                        this.motY *= 0.0D;
                        this.motZ *= 0.0D;
                    }
                    else
                    {
                        this.motX *= 0.5D;
                        this.motY *= 0.0D;
                        this.motZ *= 0.5D;
                    }
                }

                var24 = 0.0D;
                var26 = (double)var45 + 0.5D + (double)var13[0][0] * 0.5D;
                double var28 = (double)var46 + 0.5D + (double)var13[0][2] * 0.5D;
                double var30 = (double)var45 + 0.5D + (double)var13[1][0] * 0.5D;
                double var32 = (double)var46 + 0.5D + (double)var13[1][2] * 0.5D;
                var14 = var30 - var26;
                var16 = var32 - var28;

                if (var14 == 0.0D)
                {
                    this.locX = (double)var45 + 0.5D;
                    var24 = this.locZ - (double)var46;
                }
                else if (var16 == 0.0D)
                {
                    this.locZ = (double)var46 + 0.5D;
                    var24 = this.locX - (double)var45;
                }
                else
                {
                    double var34 = this.locX - var26;
                    double var36 = this.locZ - var28;
                    var24 = (var34 * var14 + var36 * var16) * 2.0D;
                }

                this.locX = var26 + var14 * var24;
                this.locZ = var28 + var16 * var24;
                this.setPosition(this.locX, this.locY + (double)this.height, this.locZ);
                this.moveMinecartOnRail(var45, var2, var46);

                if (var13[0][1] != 0 && MathHelper.floor(this.locX) - var45 == var13[0][0] && MathHelper.floor(this.locZ) - var46 == var13[0][2])
                {
                    this.setPosition(this.locX, this.locY + (double)var13[0][1], this.locZ);
                }
                else if (var13[1][1] != 0 && MathHelper.floor(this.locX) - var45 == var13[1][0] && MathHelper.floor(this.locZ) - var46 == var13[1][2])
                {
                    this.setPosition(this.locX, this.locY + (double)var13[1][1], this.locZ);
                }

                this.applyDragAndPushForces();
                Vec3D var38 = this.func_70489_a(this.locX, this.locY, this.locZ);

                if (var38 != null && var9 != null)
                {
                    double var39 = (var9.d - var38.d) * 0.05D;
                    var22 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                    if (var22 > 0.0D)
                    {
                        this.motX = this.motX / var22 * (var22 + var39);
                        this.motZ = this.motZ / var22 * (var22 + var39);
                    }

                    this.setPosition(this.locX, var38.d, this.locZ);
                }

                int var52 = MathHelper.floor(this.locX);
                int var40 = MathHelper.floor(this.locZ);

                if (var52 != var45 || var40 != var46)
                {
                    var22 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    this.motX = var22 * (double)(var52 - var45);
                    this.motZ = var22 * (double)(var40 - var46);
                }

                this.updatePushForces();

                if (this.shouldDoRailFunctions())
                {
                    ((BlockMinecartTrack)Block.byId[var8]).onMinecartPass(this.world, this, var45, var2, var46);
                }

                if (var11 && this.shouldDoRailFunctions())
                {
                    double var41 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                    if (var41 > 0.01D)
                    {
                        double var43 = 0.06D;
                        this.motX += this.motX / var41 * var43;
                        this.motZ += this.motZ / var41 * var43;
                    }
                    else if (var10 == 1)
                    {
                        if (this.world.t(var45 - 1, var2, var46))
                        {
                            this.motX = 0.02D;
                        }
                        else if (this.world.t(var45 + 1, var2, var46))
                        {
                            this.motX = -0.02D;
                        }
                    }
                    else if (var10 == 0)
                    {
                        if (this.world.t(var45, var2, var46 - 1))
                        {
                            this.motZ = 0.02D;
                        }
                        else if (this.world.t(var45, var2, var46 + 1))
                        {
                            this.motZ = -0.02D;
                        }
                    }
                }
            }
            else
            {
                this.moveMinecartOffRail(var45, var2, var46);
            }

            this.D();
            this.pitch = 0.0F;
            double var47 = this.lastX - this.locX;
            double var48 = this.lastZ - this.locZ;

            if (var47 * var47 + var48 * var48 > 0.001D)
            {
                this.yaw = (float)(Math.atan2(var48, var47) * 180.0D / Math.PI);

                if (this.f)
                {
                    this.yaw += 180.0F;
                }
            }

            double var49 = (double)MathHelper.g(this.yaw - this.lastYaw);

            if (var49 < -170.0D || var49 >= 170.0D)
            {
                this.yaw += 180.0F;
                this.f = !this.f;
            }

            this.b(this.yaw, this.pitch);
            
            // CraftBukkit start
            org.bukkit.World bworld = this.world.getWorld();
            Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
            Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();

            this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));

            if (!from.equals(to)) {
                this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleMoveEvent(vehicle, from, to));
            }
            // CraftBukkit end
            
            AxisAlignedBB var15 = null;
            if (getCollisionHandler() != null)
            {
                var15 = getCollisionHandler().getMinecartCollisionBox(this);
            }
            else
            {
                var15 = this.boundingBox.grow(0.2D, 0.0D, 0.2D);
            }

            List var50 = this.world.getEntities(this, var15);

            if (var50 != null && !var50.isEmpty())
            {
                for (int var17 = 0; var17 < var50.size(); ++var17)
                {
                    Entity var51 = (Entity)var50.get(var17);

                    if (var51 != this.passenger && var51.M() && var51 instanceof EntityMinecart)
                    {
                        var51.collide(this);
                    }
                }
            }

            if (this.passenger != null && this.passenger.dead)
            {
                if (this.passenger.vehicle == this)
                {
                    this.passenger.vehicle = null;
                }

                this.passenger = null;
            }

            this.updateFuel();
            MinecraftForge.EVENT_BUS.post(new MinecartUpdateEvent(this, (float)var45, (float)var2, (float)var46));
        }
    }

    public Vec3D func_70489_a(double var1, double var3, double var5)
    {
        int var7 = MathHelper.floor(var1);
        int var8 = MathHelper.floor(var3);
        int var9 = MathHelper.floor(var5);

        if (BlockMinecartTrack.e_(this.world, var7, var8 - 1, var9))
        {
            --var8;
        }

        int var10 = this.world.getTypeId(var7, var8, var9);

        if (BlockMinecartTrack.d(var10))
        {
            int var11 = ((BlockMinecartTrack)Block.byId[var10]).getBasicRailMetadata(this.world, this, var7, var8, var9);
            var3 = (double)var8;

            if (var11 >= 2 && var11 <= 5)
            {
                var3 = (double)(var8 + 1);
            }

            int[][] var12 = matrix[var11];
            double var13 = 0.0D;
            double var15 = (double)var7 + 0.5D + (double)var12[0][0] * 0.5D;
            double var17 = (double)var8 + 0.5D + (double)var12[0][1] * 0.5D;
            double var19 = (double)var9 + 0.5D + (double)var12[0][2] * 0.5D;
            double var21 = (double)var7 + 0.5D + (double)var12[1][0] * 0.5D;
            double var23 = (double)var8 + 0.5D + (double)var12[1][1] * 0.5D;
            double var25 = (double)var9 + 0.5D + (double)var12[1][2] * 0.5D;
            double var27 = var21 - var15;
            double var29 = (var23 - var17) * 2.0D;
            double var31 = var25 - var19;

            if (var27 == 0.0D)
            {
                var1 = (double)var7 + 0.5D;
                var13 = var5 - (double)var9;
            }
            else if (var31 == 0.0D)
            {
                var5 = (double)var9 + 0.5D;
                var13 = var1 - (double)var7;
            }
            else
            {
                double var33 = var1 - var15;
                double var35 = var5 - var19;
                var13 = (var33 * var27 + var35 * var31) * 2.0D;
            }

            var1 = var15 + var27 * var13;
            var3 = var17 + var29 * var13;
            var5 = var19 + var31 * var13;

            if (var29 < 0.0D)
            {
                ++var3;
            }

            if (var29 > 0.0D)
            {
                var3 += 0.5D;
            }

            return this.world.getVec3DPool().create(var1, var3, var5);
        }
        else
        {
            return null;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound var1)
    {
        var1.setInt("Type", this.type);

        if (this.isPoweredCart())
        {
            var1.setDouble("PushX", this.b);
            var1.setDouble("PushZ", this.c);
            var1.setInt("Fuel", this.e);
        }

        if (this.getSize() > 0)
        {
            NBTTagList var2 = new NBTTagList();

            for (int var3 = 0; var3 < this.items.length; ++var3)
            {
                if (this.items[var3] != null)
                {
                    NBTTagCompound var4 = new NBTTagCompound();
                    var4.setByte("Slot", (byte)var3);
                    this.items[var3].save(var4);
                    var2.add(var4);
                }
            }

            var1.set("Items", var2);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound var1)
    {
        this.type = var1.getInt("Type");

        if (this.isPoweredCart())
        {
            this.b = var1.getDouble("PushX");
            this.c = var1.getDouble("PushZ");

            try
            {
                this.e = var1.getInt("Fuel");
            }
            catch (ClassCastException var6)
            {
                this.e = var1.getShort("Fuel");
            }
        }

        if (this.getSize() > 0)
        {
            NBTTagList var2 = var1.getList("Items");
            this.items = new ItemStack[this.getSize()];

            for (int var3 = 0; var3 < var2.size(); ++var3)
            {
                NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
                int var5 = var4.getByte("Slot") & 255;

                if (var5 >= 0 && var5 < this.items.length)
                {
                    this.items[var5] = ItemStack.a(var4);
                }
            }
        }
    }

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void collide(Entity var1)
    {
        MinecraftForge.EVENT_BUS.post(new MinecartCollisionEvent(this, var1));
        
        if (!this.world.isStatic && var1 != this.passenger)
        {
	        // CraftBukkit start
	        Vehicle vehicle = (Vehicle) this.getBukkitEntity();
	        org.bukkit.entity.Entity hitEntity = (var1 == null) ? null : var1.getBukkitEntity();
	
	        VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
	        this.world.getServer().getPluginManager().callEvent(collisionEvent);
	
	        if (collisionEvent.isCancelled()) {
	            return;
	        }
	        // CraftBukkit end
        }

        if (getCollisionHandler() != null)
        {
            getCollisionHandler().onEntityCollision(this, var1);
        }
        else
        {
            if (!this.world.isStatic && var1 != this.passenger)
            {
                if (var1 instanceof EntityLiving && !(var1 instanceof EntityHuman) && !(var1 instanceof EntityIronGolem) && this.canBeRidden() && this.motX * this.motX + this.motZ * this.motZ > 0.01D && this.passenger == null && var1.vehicle == null)
                {
                    var1.mount(this);
                }

                double var2 = var1.locX - this.locX;
                double var4 = var1.locZ - this.locZ;
                double var6 = var2 * var2 + var4 * var4;

                if (var6 >= 9.999999747378752E-5D)
                {
                    var6 = (double)MathHelper.sqrt(var6);
                    var2 /= var6;
                    var4 /= var6;
                    double var8 = 1.0D / var6;

                    if (var8 > 1.0D)
                    {
                        var8 = 1.0D;
                    }

                    var2 *= var8;
                    var4 *= var8;
                    var2 *= 0.10000000149011612D;
                    var4 *= 0.10000000149011612D;
                    var2 *= (double)(1.0F - this.Z);
                    var4 *= (double)(1.0F - this.Z);
                    var2 *= 0.5D;
                    var4 *= 0.5D;

                    if (var1 instanceof EntityMinecart)
                    {
                        double var10 = var1.locX - this.locX;
                        double var12 = var1.locZ - this.locZ;
                        Vec3D var14 = this.world.getVec3DPool().create(var10, 0.0D, var12).a();
                        Vec3D var15 = this.world.getVec3DPool().create((double)MathHelper.cos(this.yaw * (float)Math.PI / 180.0F), 0.0D, (double)MathHelper.sin(this.yaw * (float)Math.PI / 180.0F)).a();
                        double var16 = Math.abs(var14.b(var15));

                        if (var16 < 0.800000011920929D)
                        {
                            return;
                        }

                        double var18 = var1.motX + this.motX;
                        double var20 = var1.motZ + this.motZ;

                        if (((EntityMinecart)var1).isPoweredCart() && !this.isPoweredCart())
                        {
                            this.motX *= 0.20000000298023224D;
                            this.motZ *= 0.20000000298023224D;
                            this.g(var1.motX - var2, 0.0D, var1.motZ - var4);
                            var1.motX *= 0.949999988079071D;
                            var1.motZ *= 0.949999988079071D;
                        }
                        else if (!((EntityMinecart)var1).isPoweredCart() && this.isPoweredCart())
                        {
                            var1.motX *= 0.20000000298023224D;
                            var1.motZ *= 0.20000000298023224D;
                            var1.g(this.motX + var2, 0.0D, this.motZ + var4);
                            this.motX *= 0.949999988079071D;
                            this.motZ *= 0.949999988079071D;
                        }
                        else
                        {
                            var18 /= 2.0D;
                            var20 /= 2.0D;
                            this.motX *= 0.20000000298023224D;
                            this.motZ *= 0.20000000298023224D;
                            this.g(var18 - var2, 0.0D, var20 - var4);
                            var1.motX *= 0.20000000298023224D;
                            var1.motZ *= 0.20000000298023224D;
                            var1.g(var18 + var2, 0.0D, var20 + var4);
                        }
                    }
                    else
                    {
                        this.g(-var2, 0.0D, -var4);
                        var1.g(var2 / 4.0D, 0.0D, var4 / 4.0D);
                    }
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.type == 1 && this.getClass() == EntityMinecart.class ? 27 : 0;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int var1)
    {
        return this.items[var1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int var1, int var2)
    {
        if (this.items[var1] != null)
        {
            ItemStack var3;

            if (this.items[var1].count <= var2)
            {
                var3 = this.items[var1];
                this.items[var1] = null;
                return var3;
            }
            else
            {
                var3 = this.items[var1].a(var2);

                if (this.items[var1].count == 0)
                {
                    this.items[var1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int var1)
    {
        if (this.items[var1] != null)
        {
            ItemStack var2 = this.items[var1];
            this.items[var1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        this.items[var1] = var2;

        if (var2 != null && var2.count > this.getMaxStackSize())
        {
            var2.count = this.getMaxStackSize();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.minecart";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update() {}

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman var1)
    {
        if (MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, var1)))
        {
            return true;
        }
        else
        {
            if (this.canBeRidden())
            {
                if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != var1)
                {
                    return true;
                }

                if (!this.world.isStatic)
                {
                    var1.mount(this);
                }
            }
            else if (this.getSize() > 0)
            {
                if (!this.world.isStatic)
                {
                    var1.openContainer(this);
                }
            }
            else if (this.type == 2 && this.getClass() == EntityMinecart.class)
            {
                ItemStack var2 = var1.inventory.getItemInHand();

                if (var2 != null && var2.id == Item.COAL.id)
                {
                    if (--var2.count == 0)
                    {
                        var1.inventory.setItem(var1.inventory.itemInHandIndex, (ItemStack)null);
                    }

                    this.e += 3600;
                }

                this.b = this.locX - var1.locX;
                this.c = this.locZ - var1.locZ;
            }

            return true;
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman var1)
    {
        return this.dead ? false : var1.e(this) <= 64.0D;
    }

    /**
     * Is this minecart powered (Fuel > 0)
     */
    public boolean h()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    /**
     * Set if this minecart is powered (Fuel > 0)
     */
    protected void e(boolean var1)
    {
        if (var1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) & -2)));
        }
    }

    public void startOpen() {}

    public void f() {}

    /**
     * Sets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public void setDamage(int var1)
    {
        this.datawatcher.watch(19, Integer.valueOf(var1));
    }

    /**
     * Gets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public int getDamage()
    {
        return this.datawatcher.getInt(19);
    }

    public void func_70497_h(int var1)
    {
        this.datawatcher.watch(17, Integer.valueOf(var1));
    }

    public int func_70496_j()
    {
        return this.datawatcher.getInt(17);
    }

    public void func_70494_i(int var1)
    {
        this.datawatcher.watch(18, Integer.valueOf(var1));
    }

    public int func_70493_k()
    {
        return this.datawatcher.getInt(18);
    }

    public void dropCartAsItem()
    {
        Iterator var1 = this.getItemsDropped().iterator();

        while (var1.hasNext())
        {
            ItemStack var2 = (ItemStack)var1.next();
            this.a(var2, 0.0F);
        }
    }

    public List getItemsDropped()
    {
        ArrayList var1 = new ArrayList();
        var1.add(new ItemStack(Item.MINECART));

        switch (this.type)
        {
            case 1:
                var1.add(new ItemStack(Block.CHEST));
                break;

            case 2:
                var1.add(new ItemStack(Block.FURNACE));
        }

        return var1;
    }

    public ItemStack getCartItem()
    {
        return MinecartRegistry.getItemForCart(this);
    }

    public boolean isPoweredCart()
    {
        return this.type == 2 && this.getClass() == EntityMinecart.class;
    }

    public boolean isStorageCart()
    {
        return this.type == 1 && this.getClass() == EntityMinecart.class;
    }

    public boolean canBeRidden()
    {
        return this.type == 0 && this.getClass() == EntityMinecart.class;
    }

    public boolean canUseRail()
    {
        return this.canUseRail;
    }

    public void setCanUseRail(boolean var1)
    {
        this.canUseRail = var1;
    }

    public boolean shouldDoRailFunctions()
    {
        return true;
    }

    public int getMinecartType()
    {
        return this.type;
    }

    public static IMinecartCollisionHandler getCollisionHandler()
    {
        return collisionHandler;
    }

    public static void setCollisionHandler(IMinecartCollisionHandler var0)
    {
        collisionHandler = var0;
    }

    protected double getDrag()
    {
        return this.passenger != null ? defaultDragRidden : defaultDragEmpty;
    }

    protected void applyDragAndPushForces()
    {
        if (this.isPoweredCart())
        {
            double var1 = (double)MathHelper.sqrt(this.b * this.b + this.c * this.c);

            if (var1 > 0.01D)
            {
                this.b /= var1;
                this.c /= var1;
                double var3 = 0.04D;
                this.motX *= 0.8D;
                this.motY *= 0.0D;
                this.motZ *= 0.8D;
                this.motX += this.b * var3;
                this.motZ += this.c * var3;
            }
            else
            {
                this.motX *= 0.9D;
                this.motY *= 0.0D;
                this.motZ *= 0.9D;
            }
        }

        this.motX *= this.getDrag();
        this.motY *= 0.0D;
        this.motZ *= this.getDrag();
    }

    protected void updatePushForces()
    {
        if (this.isPoweredCart())
        {
            double var1 = (double)MathHelper.sqrt(this.b * this.b + this.c * this.c);

            if (var1 > 0.01D && this.motX * this.motX + this.motZ * this.motZ > 0.001D)
            {
                this.b /= var1;
                this.c /= var1;

                if (this.b * this.motX + this.c * this.motZ < 0.0D)
                {
                    this.b = 0.0D;
                    this.c = 0.0D;
                }
                else
                {
                    this.b = this.motX;
                    this.c = this.motZ;
                }
            }
        }
    }

    protected void moveMinecartOnRail(int var1, int var2, int var3)
    {
        int var4 = this.world.getTypeId(var1, var2, var3);

        if (BlockMinecartTrack.d(var4))
        {
            float var5 = ((BlockMinecartTrack)Block.byId[var4]).getRailMaxSpeed(this.world, this, var1, var2, var3);
            double var6 = (double)Math.min(var5, this.getMaxSpeedRail());
            double var8 = this.motX;
            double var10 = this.motZ;

            if (this.passenger != null)
            {
                var8 *= 0.75D;
                var10 *= 0.75D;
            }

            if (var8 < -var6)
            {
                var8 = -var6;
            }

            if (var8 > var6)
            {
                var8 = var6;
            }

            if (var10 < -var6)
            {
                var10 = -var6;
            }

            if (var10 > var6)
            {
                var10 = var6;
            }

            this.move(var8, 0.0D, var10);
        }
    }

    protected void moveMinecartOffRail(int var1, int var2, int var3)
    {
        double var4 = (double)this.getMaxSpeedGround();

        if (!this.onGround)
        {
            var4 = (double)this.getMaxSpeedAirLateral();
        }

        if (this.motX < -var4)
        {
            this.motX = -var4;
        }

        if (this.motX > var4)
        {
            this.motX = var4;
        }

        if (this.motZ < -var4)
        {
            this.motZ = -var4;
        }

        if (this.motZ > var4)
        {
            this.motZ = var4;
        }

        double var6 = this.motY;

        if (this.getMaxSpeedAirVertical() > 0.0F && this.motY > (double)this.getMaxSpeedAirVertical())
        {
            var6 = (double)this.getMaxSpeedAirVertical();

            if (Math.abs(this.motX) < 0.30000001192092896D && Math.abs(this.motZ) < 0.30000001192092896D)
            {
                var6 = 0.15000000596046448D;
                this.motY = var6;
            }
        }

        if (this.onGround)
        {
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
        }

        this.move(this.motX, var6, this.motZ);

        if (!this.onGround)
        {
            this.motX *= this.getDragAir();
            this.motY *= this.getDragAir();
            this.motZ *= this.getDragAir();
        }
    }

    protected void updateFuel()
    {
        if (this.e > 0)
        {
            --this.e;
        }

        if (this.e <= 0)
        {
            this.b = this.c = 0.0D;
        }

        this.e(this.e > 0);
    }

    protected void adjustSlopeVelocities(int var1)
    {
        double var2 = 0.0078125D;

        if (var1 == 2)
        {
            this.motX -= var2;
        }
        else if (var1 == 3)
        {
            this.motX += var2;
        }
        else if (var1 == 4)
        {
            this.motZ += var2;
        }
        else if (var1 == 5)
        {
            this.motZ -= var2;
        }
    }

    public float getMaxSpeedRail()
    {
        return this.maxSpeedRail;
    }

    public void setMaxSpeedRail(float var1)
    {
        this.maxSpeedRail = var1;
    }

    public float getMaxSpeedGround()
    {
        return this.maxSpeedGround;
    }

    public void setMaxSpeedGround(float var1)
    {
        this.maxSpeedGround = var1;
    }

    public float getMaxSpeedAirLateral()
    {
        return this.maxSpeedAirLateral;
    }

    public void setMaxSpeedAirLateral(float var1)
    {
        this.maxSpeedAirLateral = var1;
    }

    public float getMaxSpeedAirVertical()
    {
        return this.maxSpeedAirVertical;
    }

    public void setMaxSpeedAirVertical(float var1)
    {
        this.maxSpeedAirVertical = var1;
    }

    public double getDragAir()
    {
        return this.dragAir;
    }

    public void setDragAir(double var1)
    {
        this.dragAir = var1;
    }


    public ItemStack[] getContents() {
        return this.items;
    }

    public void onOpen(CraftHumanEntity who) {
        transaction.add(who);
    }

    public void onClose(CraftHumanEntity who) {
        transaction.remove(who);
    }

    public List<HumanEntity> getViewers() {
        return transaction;
    }

    public InventoryHolder getOwner() {
        org.bukkit.entity.Entity cart = getBukkitEntity();
        if(cart instanceof InventoryHolder) return (InventoryHolder) cart;
        return null;
    }

    public void setMaxStackSize(int size) {
        maxStack = size;
    }
}