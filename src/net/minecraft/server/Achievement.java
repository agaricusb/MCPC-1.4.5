package net.minecraft.server;

import net.minecraft.server.AchievementList;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Statistic;

public class Achievement extends Statistic {

	public final int a;
	public final int b;
	public final Achievement c;
	private final String k;
	public final ItemStack d;
	private boolean m;

	public Achievement(int paramInt1, String paramString, int paramInt2, int paramInt3, Item paramItem, Achievement paramAchievement)
	{
		this(paramInt1, paramString, paramInt2, paramInt3, new ItemStack(paramItem), paramAchievement);
	}

	public Achievement(int paramInt1, String paramString, int paramInt2, int paramInt3, Block paramBlock, Achievement paramAchievement) {
		this(paramInt1, paramString, paramInt2, paramInt3, new ItemStack(paramBlock), paramAchievement);
	}

	public Achievement(int paramInt1, String paramString, int paramInt2, int paramInt3, ItemStack paramItemStack, Achievement paramAchievement) {
		super(5242880 + paramInt1, "achievement." + paramString);
		this.d = paramItemStack;

		this.k = ("achievement." + paramString + ".desc");
		this.a = paramInt2;
		this.b = paramInt3;

		if (paramInt2 < AchievementList.a) AchievementList.a = paramInt2;
		if (paramInt3 < AchievementList.b) AchievementList.b = paramInt3;
		if (paramInt2 > AchievementList.c) AchievementList.c = paramInt2;
		if (paramInt3 > AchievementList.d) AchievementList.d = paramInt3;
		this.c = paramAchievement;
	}

	public Achievement a()
	{
		this.f = true;
		return this;
	}

	public Achievement b() {
		this.m = true;
		return this;
	}

	public Achievement c()
	{
		super.g();

		AchievementList.e.add(this);

		return this;
	}
}