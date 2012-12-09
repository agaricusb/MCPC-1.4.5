package net.minecraft.server;

import java.util.List;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventorySubcontainer
implements IInventory, InventoryHolder
{
	private String a;
	private int b;
	protected ItemStack[] items;
	private List d;

	public InventorySubcontainer(String s, int i)
	{
		this.a = s;
		this.b = i;
		this.items = new ItemStack[i];
	}

	public ItemStack getItem(int i) {
		return this.items[i];
	}

	public ItemStack splitStack(int i, int j) {
		if (this.items[i] != null)
		{
			if (this.items[i].count <= j) {
				ItemStack itemstack = this.items[i];
				this.items[i] = null;
				update();
				return itemstack;
			}
			ItemStack itemstack = this.items[i].a(j);
			if (this.items[i].count == 0) {
				this.items[i] = null;
			}

			update();
			return itemstack;
		}

		return null;
	}

	public ItemStack splitWithoutUpdate(int i)
	{
		if (this.items[i] != null) {
			ItemStack itemstack = this.items[i];

			this.items[i] = null;
			return itemstack;
		}
		return null;
	}

	public void setItem(int i, ItemStack itemstack)
	{
		this.items[i] = itemstack;
		if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
			itemstack.count = getMaxStackSize();
		}

		update();
	}

	public int getSize() {
		return this.b;
	}

	public String getName() {
		return this.a;
	}

	public int getMaxStackSize() {
		return 64;
	}

	public void update() {
		if (this.d != null) {
            for (int i = 0; i < this.d.size(); ++i) {
                ((IInventoryListener) this.d.get(i)).a(this);
			}
		}
	}

    public boolean a_(EntityHuman entityhuman) {
		return true;
	}

	public void startOpen()
	{
	}

	public void f()
	{
	}

	@Override
	public ItemStack[] getContents() {
		return items;
	}

	@Override
	public InventoryHolder getOwner() {
		return this;
	}

	@Override
	public List<HumanEntity> getViewers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClose(CraftHumanEntity arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(CraftHumanEntity arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxStackSize(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Inventory getInventory() {
		return new CraftInventory(this);
	}
}