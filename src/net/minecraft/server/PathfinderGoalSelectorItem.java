package net.minecraft.server;

public class PathfinderGoalSelectorItem
{
  public PathfinderGoal a;
  public int b;

  public PathfinderGoalSelectorItem(PathfinderGoalSelector paramPathfinderGoalSelector, int paramInt, PathfinderGoal paramPathfinderGoal)
  {
    this.b = paramInt;
    this.a = paramPathfinderGoal;
  }
}