package net.minecraft.server;

import cpw.mods.fml.common.registry.VillagerRegistry;

public class PathfinderGoalMakeLove extends PathfinderGoal
{
    private EntityVillager b;
    private EntityVillager c;
    private World d;
    private int e = 0;
    Village a;

    public PathfinderGoalMakeLove(EntityVillager var1)
    {
        this.b = var1;
        this.d = var1.world;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.b.getAge() != 0)
        {
            return false;
        }
        else if (this.b.aB().nextInt(500) != 0)
        {
            return false;
        }
        else
        {
            this.a = this.d.villages.getClosestVillage(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.locY), MathHelper.floor(this.b.locZ), 0);

            if (this.a == null)
            {
                return false;
            }
            else if (!this.f())
            {
                return false;
            }
            else
            {
                Entity var1 = this.d.a(EntityVillager.class, this.b.boundingBox.grow(8.0D, 3.0D, 8.0D), this.b);

                if (var1 == null)
                {
                    return false;
                }
                else
                {
                    this.c = (EntityVillager)var1;
                    return this.c.getAge() == 0;
                }
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.e = 300;
        this.b.f(true);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a = null;
        this.c = null;
        this.b.f(false);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.e >= 0 && this.f() && this.b.getAge() == 0;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        --this.e;
        this.b.getControllerLook().a(this.c, 10.0F, 30.0F);

        if (this.b.e(this.c) > 2.25D)
        {
            this.b.getNavigation().a(this.c, 0.25F);
        }
        else if (this.e == 0 && this.c.n())
        {
            this.g();
        }

        if (this.b.aB().nextInt(35) == 0)
        {
            this.d.broadcastEntityEffect(this.b, (byte)12);
        }
    }

    private boolean f()
    {
        if (!this.a.i())
        {
            return false;
        }
        else
        {
            int var1 = (int)((double)((float)this.a.getDoorCount()) * 0.35D);
            return this.a.getPopulationCount() < var1;
        }
    }

    private void g()
    {
        EntityVillager var1 = new EntityVillager(this.d);
        this.c.setAge(6000);
        this.b.setAge(6000);
        var1.setAge(-24000);
        VillagerRegistry.applyRandomTrade(var1, this.b.aB());
        var1.setPositionRotation(this.b.locX, this.b.locY, this.b.locZ, 0.0F, 0.0F);
        this.d.addEntity(var1);
        this.d.broadcastEntityEffect(var1, (byte)12);
    }
}
