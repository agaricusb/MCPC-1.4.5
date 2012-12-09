package ee.lutsu.alpha.minecraft;

import net.minecraft.server.World;

public interface BlockChangeHandler 
{
	/**
	 * All block changes in the world go thru here.
	 * 
	 * Called just before the actual change. So you can request from the world the current block.
	 * Data will always be 0 if setTypeId() is being called
	 */
	public void blockChanged(World w, int x, int y, int z, int type, int data);
}
