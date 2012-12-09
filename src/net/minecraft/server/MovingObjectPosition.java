package net.minecraft.server;

public class MovingObjectPosition
{
    /** What type of ray trace hit was this? 0 = block, 1 = entity */
    public EnumMovingObjectType type;

    /** x coordinate of the block ray traced against */
    public int b;

    /** y coordinate of the block ray traced against */
    public int c;

    /** z coordinate of the block ray traced against */
    public int d;

    /**
     * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, East = 2, West
     * = 3, North = 4, South = 5.
     */
    public int face;

    /** The vector position of the hit */
    public Vec3D pos;

    /** The hit entity */
    public Entity entity;
    public int subHit = -1;

    public MovingObjectPosition(int var1, int var2, int var3, int var4, Vec3D var5)
    {
        this.type = EnumMovingObjectType.TILE;
        this.b = var1;
        this.c = var2;
        this.d = var3;
        this.face = var4;
        this.pos = var5.b.create(var5.c, var5.d, var5.e);
    }

    public MovingObjectPosition(Entity var1)
    {
        this.type = EnumMovingObjectType.ENTITY;
        this.entity = var1;
        this.pos = var1.world.getVec3DPool().create(var1.locX, var1.locY, var1.locZ);
    }
}
