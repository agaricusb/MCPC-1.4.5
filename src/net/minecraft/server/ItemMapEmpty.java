package net.minecraft.server;

public class ItemMapEmpty extends ItemWorldMapBase
{
	protected ItemMapEmpty(int paramInt)
	{
		super(paramInt);
		a(CreativeModeTab.f);
	}

	public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
	{
		ItemStack localItemStack = new ItemStack(Item.MAP, 1, paramWorld.b("map"));

		String str = "map_" + localItemStack.getData();
		WorldMap localWorldMap = new WorldMap(str);
		paramWorld.a(str, localWorldMap);

		localWorldMap.scale = 0;
		int i = 128 * (1 << localWorldMap.scale);
		localWorldMap.centerX = (int)(Math.round(paramEntityHuman.locX / i) * i);
		localWorldMap.centerZ = (int)(Math.round(paramEntityHuman.locZ / i) * i);
		localWorldMap.map = paramWorld.worldProvider.dimension;

		localWorldMap.c();

		paramItemStack.count -= 1;
		if (paramItemStack.count <= 0) {
			return localItemStack;
		}
		if (!paramEntityHuman.inventory.pickup(localItemStack.cloneItemStack())) {
			paramEntityHuman.drop(localItemStack);
		}

		return paramItemStack;
	}
}