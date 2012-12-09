package net.minecraft.server;

import java.util.List;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

public class FakeInventory implements IInventory, InventoryHolder 
{
	public ItemStack[] items;
	public int maxStack = 64;
	
	public FakeInventory(int nr)
	{
		items = new ItemStack[nr];
	}
	
	public FakeInventory()
	{
		items = new ItemStack[1];
	}
	@Override
	public boolean a_(EntityHuman arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void f() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemStack[] getContents() {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public ItemStack getItem(int arg0) {
		// TODO Auto-generated method stub
		return items[arg0];
	}

	@Override
	public int getMaxStackSize() {
		// TODO Auto-generated method stub
		return maxStack;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "FakeInv";
	}

	@Override
	public InventoryHolder getOwner() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return items.length;
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
	public void setItem(int arg0, ItemStack arg1) {
		items[arg0] = arg1;
	}

	@Override
	public void setMaxStackSize(int arg0) {
		maxStack = arg0;
	}

	@Override
	public ItemStack splitStack(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack splitWithoutUpdate(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return new CraftInventory(this);
	}

}
