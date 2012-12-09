package net.minecraft.server;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

// CraftBukkit start
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
// CraftBukkit end

public class ItemInWorldManager {

	private double blockReachDistance = 5.0D;
	
    public World world;
    public EntityPlayer player;
    private EnumGamemode gamemode;
    private boolean d;
    private int lastDigTick;
    private int f;
    private int g;
    private int h;
    private int currentTick;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;

    public ItemInWorldManager(World world) {
        this.gamemode = EnumGamemode.NONE;
        this.o = -1;
        this.world = world;
    }

    // CraftBukkit start - keep this for backwards compatibility
    public ItemInWorldManager(WorldServer world) {
        this((World) world);
    }
    // CraftBukkit end

    public void setGameMode(EnumGamemode enumgamemode) {
        this.gamemode = enumgamemode;
        enumgamemode.a(this.player.abilities);
        this.player.updateAbilities();
    }

    public EnumGamemode getGameMode() {
        return this.gamemode;
    }

    public boolean isCreative() {
        return this.gamemode.d();
    }

    public void b(EnumGamemode enumgamemode) {
        if (this.gamemode == EnumGamemode.NONE) {
            this.gamemode = enumgamemode;
        }

        this.setGameMode(this.gamemode);
    }

    public void a() {
        this.currentTick = (int) (System.currentTimeMillis() / 50); // CraftBukkit
        int i;
        float f;
        int j;

        if (this.j) {
            i = this.currentTick - this.n;
            int k = this.world.getTypeId(this.k, this.l, this.m);

            if (k == 0) {
                this.j = false;
            } else {
                Block block = Block.byId[k];

                f = block.getDamage(this.player, this.player.world, this.k, this.l, this.m) * (float) (i + 1);
                j = (int) (f * 10.0F);
                if (j != this.o) {
                    this.world.g(this.player.id, this.k, this.l, this.m, j);
                    this.o = j;
                }

                if (f >= 1.0F) {
                    this.j = false;
                    this.breakBlock(this.k, this.l, this.m);
                }
            }
        } else if (this.d) {
            i = this.world.getTypeId(this.f, this.g, this.h);
            Block block1 = Block.byId[i];

            if (block1 == null) {
                this.world.g(this.player.id, this.f, this.g, this.h, -1);
                this.o = -1;
                this.d = false;
            } else {
                int l = this.currentTick - this.lastDigTick;

                f = block1.getDamage(this.player, this.player.world, this.f, this.g, this.h) * (float) (l + 1);
                j = (int) (f * 10.0F);
                if (j != this.o) {
                    this.world.g(this.player.id, this.f, this.g, this.h, j);
                    this.o = j;
                }
            }
        }
    }

    public void dig(int i, int j, int k, int l) {
        // this.world.douseFire((EntityHuman) null, i, j, k, l); // CraftBukkit - moved down
        // CraftBukkit
        PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, l, this.player.inventory.getItemInHand());

        if (!this.gamemode.isAdventure() || this.player.f(i, j, k)) {
            // CraftBukkit start
            if (event.isCancelled()) {
                // Let the client know the block still exists
                ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                // Update any tile entity data for this block
                TileEntity tileentity = this.world.getTileEntity(i, j, k);
                if (tileentity != null) {
                    this.player.netServerHandler.sendPacket(tileentity.getUpdatePacket());
                }
                return;
            }
            // CraftBukkit end
            
            net.minecraftforge.event.entity.player.PlayerInteractEvent var5 = ForgeEventFactory.onPlayerInteract(this.player, net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, i, j, k, l);

            if (var5.isCanceled())
            {
                this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                return;
            }
            
            if (this.isCreative()) {
                if (!this.world.douseFire((EntityHuman) null, i, j, k, l)) {
                    this.breakBlock(i, j, k);
                }
            } else {
                this.world.douseFire(this.player, i, j, k, l);
                this.lastDigTick = this.currentTick;
                float f = 1.0F;
                int i1 = this.world.getTypeId(i, j, k);
                // CraftBukkit start - Swings at air do *NOT* exist.
                Block var8 = Block.byId[i1];
                if (var8 == null)
                	return;
                
                if (var5.useItem == Result.DENY && f >= 1.0F)
                {
                	this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    return;
                }

                if (event.useInteractedBlock() == Event.Result.DENY) {
                    // If we denied a door from opening, we need to send a correcting update to the client, as it already opened the door.
                    if (i1 == Block.WOODEN_DOOR.id) {
                        // For some reason *BOTH* the bottom/top part have to be marked updated.
                        boolean bottom = (this.world.getData(i, j, k) & 8) == 0;
                        ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                        ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j + (bottom ? 1 : -1), k, this.world));
                    } else if (i1 == Block.TRAP_DOOR.id) {
                        ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    }
                } else if ((var8 != null && var5.useBlock != Result.DENY) || i1 > 0) {
                	var8.attack(this.world, i, j, k, this.player);
                    // Allow fire punching to be blocked
                    this.world.douseFire((EntityHuman) null, i, j, k, l);
                }
                else
                	this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));

                // Handle hitting a block
                if (i1 > 0) {
                    f = Block.byId[i1].getDamage(this.player, this.world, i, j, k);
                }

                if (event.useItemInHand() == Event.Result.DENY) {
                    // If we 'insta destroyed' then the client needs to be informed.
                    if (f > 1.0f) {
                        ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    }
                    return;
                }
                org.bukkit.event.block.BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.player, i, j, k, this.player.inventory.getItemInHand(), f >= 1.0f);

                if (blockEvent.isCancelled()) {
                    // Let the client know the block still exists
                    ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                    return;
                }

                if (blockEvent.getInstaBreak()) {
                    f = 2.0f;
                }
                    // CraftBukkit end

                if (i1 > 0 && f >= 1.0F) {
                    this.breakBlock(i, j, k);
                } else {
                    this.d = true;
                    this.f = i;
                    this.g = j;
                    this.h = k;
                    int j1 = (int) (f * 10.0F);

                    this.world.g(this.player.id, i, j, k, j1);
                    this.o = j1;
                }
            }
        }
    }

    public void a(int i, int j, int k) {
        if (i == this.f && j == this.g && k == this.h) {
            this.currentTick = (int) (System.currentTimeMillis() / 50); // CraftBukkit
            int l = this.currentTick - this.lastDigTick;
            int i1 = this.world.getTypeId(i, j, k);

            if (i1 != 0) {
                Block block = Block.byId[i1];
                float f = block.getDamage(this.player, this.player.world, i, j, k) * (float) (l + 1);

                if (f >= 0.7F) {
                    this.d = false;
                    this.world.g(this.player.id, i, j, k, -1);
                    this.breakBlock(i, j, k);
                } else if (!this.j) {
                    this.d = false;
                    this.j = true;
                    this.k = i;
                    this.l = j;
                    this.m = k;
                    this.n = this.lastDigTick;
                }
            }
        // CraftBukkit start - force blockreset to client
        } else {
            ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
            // CraftBukkit end
        }
    }

    public void c(int i, int j, int k) {
        this.d = false;
        this.world.g(this.player.id, this.f, this.g, this.h, -1);
    }

    /**
     * Removes a block and triggers the appropriate events
     */
    private boolean d(int var1, int var2, int var3)
    {
        Block var4 = Block.byId[this.world.getTypeId(var1, var2, var3)];
        int var5 = this.world.getData(var1, var2, var3);

        if (var4 != null)
        {
            var4.a(this.world, var1, var2, var3, var5, this.player);
        }

        boolean var6 = var4 != null && var4.removeBlockByPlayer(this.world, this.player, var1, var2, var3);

        if (var4 != null && var6)
        {
            var4.postBreak(this.world, var1, var2, var3, var5);
        }

        return var6;
    }

    public boolean breakBlock(int i, int j, int k) {
        // CraftBukkit start
        BlockBreakEvent event = null;

        if (this.player instanceof EntityPlayer) {
            org.bukkit.block.Block block = this.world.getWorld().getBlockAt(i, j, k);

            // Tell client the block is gone immediately then process events
            if (world.getTileEntity(i, j, k) == null) {
                Packet53BlockChange packet = new Packet53BlockChange(i, j, k, this.world);

                packet.material = 0;
                packet.data = 0;
                ((EntityPlayer) this.player).netServerHandler.sendPacket(packet);
            }

            event = new BlockBreakEvent(block, this.player.getBukkitEntity());

            // Adventure mode pre-cancel
            event.setCancelled(this.gamemode.isAdventure() && !this.player.f(i, j, k));

            // Calculate default block experience
            Block nmsBlock = Block.byId[block.getTypeId()];

            if (nmsBlock != null && !event.isCancelled() && !this.isCreative() && this.player.b(nmsBlock)) {
                // Copied from Block.a(world, entityhuman, int, int, int, int)
                if (!(nmsBlock.s_() && EnchantmentManager.hasSilkTouchEnchantment(this.player))) {
                    int data = block.getData();
                    int bonusLevel = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);

                    event.setExpToDrop(nmsBlock.getExpDrop(this.world, data, bonusLevel));
                }
            }

            this.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                // Let the client know the block still exists
                ((EntityPlayer) this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
                // Update any tile entity data for this block
                TileEntity tileentity = this.world.getTileEntity(i, j, k);
                if (tileentity != null) {
                    this.player.netServerHandler.sendPacket(tileentity.getUpdatePacket());
                }
                return false;
            }
        }
        // CraftBukkit end

        ItemStack itemstack = this.player.bT();
        if (itemstack != null && itemstack.getItem().onBlockStartBreak(itemstack, i, j, k, this.player))
        {
            return false;
        } else {
            int l = this.world.getTypeId(i, j, k);
            if (Block.byId[l] == null) return false; // CraftBukkit - a plugin set block to air without cancelling
            int i1 = this.world.getData(i, j, k);
            // CraftBukkit start - special case skulls, their item data comes from a tile entity
            if (l == Block.SKULL.id) {
                i1 = Block.SKULL.getDropData(world, i, j, k);
            }
            // CraftBukkit end

            this.world.a(this.player, 2001, i, j, k, l + (this.world.getData(i, j, k) << 12));
            boolean flag = false;
            boolean flag1 = false;
            if (this.isCreative()) {
            	flag = this.d(i, j, k);
                this.player.netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
            } else {
                flag1 = this.player.b(Block.byId[l]);
                Block var10 = Block.byId[l];
                
                if (var10 != null)
                	flag1 = var10.canHarvestBlock(this.player, i1);
                
                if (itemstack != null) {
                    itemstack.a(this.world, l, i, j, k, this.player);
                    if (itemstack.count == 0) {
                        this.player.bU();
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(this.player, itemstack));
                    }
                }
            }
            
            flag = this.d(i, j, k);
            
            if (flag && flag1) {
                Block.byId[l].a(this.world, this.player, i, j, k, i1);
            }

            // CraftBukkit start - drop event experience
            if (flag && event != null) {
                Block.byId[l].f(this.world, i, j, k, event.getExpToDrop());
            }
            // CraftBukkit end

            return flag;
        }
    }

    public boolean useItem(EntityHuman entityhuman, World world, ItemStack itemstack) {
        int i = itemstack.count;
        int j = itemstack.getData();
        ItemStack itemstack1 = itemstack.a(world, entityhuman);

        if (itemstack1 == itemstack && (itemstack1 == null || itemstack1.count == i && itemstack1.m() <= 0 && itemstack1.getData() == j)) {
            return false;
        } else {
            entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
            if (this.isCreative()) {
                itemstack1.count = i;
                if (itemstack1.f()) {
                    itemstack1.setData(j);
                }
            }

            if (itemstack1.count == 0) {
                entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
                MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(this.player, itemstack1));
            }

            if (!entityhuman.bM()) {
                ((EntityPlayer) entityhuman).updateInventory(entityhuman.defaultContainer);
            }

            return true;
        }
    }
    

    /**
     * Activate the clicked on block, otherwise use the held item. Args: player, world, itemStack, x, y, z, side,
     * xOffset, yOffset, zOffset
     */
    public boolean interact(EntityHuman var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
    	net.minecraftforge.event.entity.player.PlayerInteractEvent var11 = ForgeEventFactory.onPlayerInteract(var1, net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, var4, var5, var6, var7);

        if (var11.isCanceled())
        {
            this.player.netServerHandler.sendPacket(new Packet53BlockChange(var4, var5, var6, this.world));
            return false;
        }
        else
        {
            Item var12 = var3 != null ? var3.getItem() : null;

            if (var12 != null && var12.onItemUseFirst(var3, var1, var2, var4, var5, var6, var7, var8, var9, var10))
            {
                if (var3.count <= 0)
                {
                    ForgeEventFactory.onPlayerDestroyItem(this.player, var3);
                }

                return true;
            }
            else
            {
                int i1 = var2.getTypeId(var4, var5, var6);
                Block var14 = Block.byId[i1];
                boolean result = false;

                if (var14 != null)
                {
                    if (var11.useBlock != Result.DENY)
                    {
                    	// called below by bukkit
                    	//result = var14.interact(var2, var4, var5, var6, var1, var7, var8, var9, var10);
                    }
                    else
                    {
                        this.player.netServerHandler.sendPacket(new Packet53BlockChange(var4, var5, var6, this.world));
                        result = var11.useItem != Result.ALLOW;
                    }
                }

                if (!result)
                {
                    // CraftBukkit start - Interact
                    
                    if (i1 > 0) {
                        PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(var1, Action.RIGHT_CLICK_BLOCK, var4, var5, var6, var7, var3);
                        if (event.useInteractedBlock() == Event.Result.DENY) {
                            // If we denied a door from opening, we need to send a correcting update to the client, as it already opened the door.
                            if (i1 == Block.WOODEN_DOOR.id) {
                                boolean bottom = (world.getData(var4, var5, var6) & 8) == 0;
                                ((EntityPlayer) var1).netServerHandler.sendPacket(new Packet53BlockChange(var4, var5 + (bottom ? 1 : -1), var6, world));
                            }
                            result = (event.useItemInHand() != Event.Result.ALLOW);
                        } else {
                            result = Block.byId[i1].interact(world, var4, var5, var6, var1, var7, var8, var9, var10);
                        }

                        if (var3 != null && !result) {
                            int j1 = var3.getData();
                            int k1 = var3.count;

                            result = var3.placeItem(var1, world,var4, var5, var6, var7, var8, var9, var10);

                            // The item count should not decrement in Creative mode.
                            if (this.isCreative()) {
                                var3.setData(j1);
                                var3.count = k1;
                            }
                        }

                        // If we have 'true' and no explicit deny *or* an explicit allow -- run the item part of the hook
                        if (var3 != null && ((!result && event.useItemInHand() != Event.Result.DENY) || event.useItemInHand() == Event.Result.ALLOW)) {
                            this.useItem(var1, world, var3);
                        }
                    }
                 // CraftBukkit end
                    if (var3 != null && var3.count <= 0)
                        ForgeEventFactory.onPlayerDestroyItem(this.player, var3);
                }

                return result;
            }
        }
    }

    public void a(WorldServer worldserver) {
        this.world = worldserver;
    }
    

    public double getBlockReachDistance()
    {
        return this.blockReachDistance;
    }

    public void setBlockReachDistance(double var1)
    {
        this.blockReachDistance = var1;
    }
}
