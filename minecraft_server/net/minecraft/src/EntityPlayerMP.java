package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private StringTranslate translator = new StringTranslate("en_US");

    /**
     * The NetServerHandler assigned to this player by the ServerConfigurationManager.
     */
    public NetServerHandler playerNetServerHandler;

    /** Reference to the MinecraftServer object. */
    public MinecraftServer mcServer;

    /** The ItemInWorldManager belonging to this player */
    public ItemInWorldManager theItemInWorldManager;

    /** player X position as seen by PlayerManager */
    public double managedPosX;

    /** player Z position as seen by PlayerManager */
    public double managedPosZ;

    /** LinkedList that holds the loaded chunks. */
    /**
     * FCNOTE: Deprecated list used by vanilla PlayerInstance and PlayerManager to track watched
     * chunks
     */
    public final List loadedChunks = new LinkedList();

    /** entities added to this list will  be packet29'd to the player */
    public final List destroyedItemsNetCache = new LinkedList();

    /** set to getHealth */
    private int lastHealth = -99999999;

    /** set to foodStats.GetFoodLevel */
    private int lastFoodLevel = -99999999;

    /** set to foodStats.getSaturationLevel() == 0.0F each tick */
    private boolean wasHungry = true;

    /** Amount of experience the client was last set to */
    private int lastExperience = -99999999;

    /** de-increments onUpdate, attackEntityFrom is ignored if this >0 */
    private int initialInvulnerability = 60;

    /** must be between 3>x>15 (strictly between) */
    private int renderDistance = 0;
    private int chatVisibility = 0;
    private boolean chatColours = true;

    /**
     * The currently in use window ID. Incremented every time a window is opened.
     */
    private int currentWindowId = 0;

    /**
     * poor mans concurency flag, lets hope the jvm doesn't re-order the setting of this flag wrt the inventory change
     * on the next line
     */
    public boolean playerInventoryBeingManipulated;
    public int ping;

    /**
     * Set when a player beats the ender dragon, used to respawn the player at the spawn point while retaining inventory
     * and XP
     */
    public boolean playerConqueredTheEnd = false;

    public EntityPlayerMP(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager)
    {
        super(par2World);
        par4ItemInWorldManager.thisPlayerMP = this;
        this.theItemInWorldManager = par4ItemInWorldManager;
        this.renderDistance = par1MinecraftServer.getConfigurationManager().getViewDistance();
        ChunkCoordinates var5 = par2World.getSpawnPoint();
        int var6 = var5.posX;
        int var7 = var5.posZ;
        int var8 = var5.posY;

        if (!par2World.provider.hasNoSky && par2World.getWorldInfo().getGameType() != EnumGameType.ADVENTURE)
        {
            int var9 = Math.max(5, par1MinecraftServer.getSpawnProtectionSize() - 6);
            var6 += this.rand.nextInt(var9 * 2) - var9;
            var7 += this.rand.nextInt(var9 * 2) - var9;
            var8 = par2World.getTopSolidOrLiquidBlock(var6, var7);
        }

        this.mcServer = par1MinecraftServer;
        this.stepHeight = 0.0F;
        this.username = par3Str;
        this.yOffset = 0.0F;
        this.setLocationAndAngles((double)var6 + 0.5D, (double)var8, (double)var7 + 0.5D, 0.0F, 0.0F);

        while (!par2World.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty())
        {
            this.setPosition(this.posX, this.posY + 1.0D, this.posZ);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("playerGameType"))
        {
            if (MinecraftServer.getServer().func_104056_am())
            {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
            }
            else
            {
                this.theItemInWorldManager.setGameType(EnumGameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }

    /**
     * Add experience levels to this player.
     */
    public void addExperienceLevel(int par1)
    {
        super.addExperienceLevel(par1);
        this.lastExperience = -1;
    }

    public void addSelfToInternalCraftingInventory()
    {
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void resetHeight()
    {
        this.yOffset = 0.0F;
    }

    public float getEyeHeight()
    {
        return 1.62F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.theItemInWorldManager.updateBlockRemoving();
        --this.initialInvulnerability;
        this.openContainer.detectAndSendChanges();

        while (!this.destroyedItemsNetCache.isEmpty())
        {
            int var1 = Math.min(this.destroyedItemsNetCache.size(), 127);
            int[] var2 = new int[var1];
            Iterator var3 = this.destroyedItemsNetCache.iterator();
            int var4 = 0;

            while (var3.hasNext() && var4 < var1)
            {
                var2[var4++] = ((Integer)var3.next()).intValue();
                var3.remove();
            }

            this.playerNetServerHandler.sendPacketToPlayer(new Packet29DestroyEntity(var2));
        }

        // FCMOD: Changed
        /*
        if (!this.loadedChunks.isEmpty())
        {
            ArrayList var6 = new ArrayList();
            Iterator var7 = this.loadedChunks.iterator();
            ArrayList var8 = new ArrayList();

            while (var7.hasNext() && var6.size() < 5)
            {
                ChunkCoordIntPair var9 = (ChunkCoordIntPair)var7.next();
                var7.remove();

                if (var9 != null && this.worldObj.blockExists(var9.chunkXPos << 4, 0, var9.chunkZPos << 4))
                {
                    var6.add(this.worldObj.getChunkFromChunkCoords(var9.chunkXPos, var9.chunkZPos));
                    var8.addAll(((WorldServer)this.worldObj).getAllTileEntityInBox(var9.chunkXPos * 16, 0, var9.chunkZPos * 16, var9.chunkXPos * 16 + 16, 256, var9.chunkZPos * 16 + 16));
                }
            }

            if (!var6.isEmpty())
            {
                this.playerNetServerHandler.sendPacketToPlayer(new Packet56MapChunks(var6));
                Iterator var11 = var8.iterator();

                while (var11.hasNext())
                {
                    TileEntity var5 = (TileEntity)var11.next();
                    this.sendTileEntityToPlayer(var5);
                }

                var11 = var6.iterator();

                while (var11.hasNext())
                {
                    Chunk var10 = (Chunk)var11.next();
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, var10);
                }
            }
        }
        */
        SendChunksToClient();
        // END FCMOD

        // FCMOD: Code Added
        ModSpecificOnUpdate();
        // END FCMOD
    }

    public void setEntityHealth(int par1)
    {
        super.setEntityHealth(par1);
        Collection var2 = this.getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96638_f);
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            ScoreObjective var4 = (ScoreObjective)var3.next();
            this.getWorldScoreboard().func_96529_a(this.getEntityName(), var4).func_96651_a(Arrays.asList(new EntityPlayer[] {this}));
        }
    }

    public void onUpdateEntity()
    {
        try
        {
            super.onUpdate();

            for (int var1 = 0; var1 < this.inventory.getSizeInventory(); ++var1)
            {
                ItemStack var5 = this.inventory.getStackInSlot(var1);

                if (var5 != null && Item.itemsList[var5.itemID].isMap() && this.playerNetServerHandler.packetSize() <= 5)
                {
                    Packet var6 = ((ItemMapBase)Item.itemsList[var5.itemID]).createMapDataPacket(var5, this.worldObj, this);

                    if (var6 != null)
                    {
                        this.playerNetServerHandler.sendPacketToPlayer(var6);
                    }
                }
            }

        	// FCMOD: Code changed to relay changes in food saturation
        	/*
            if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0F != this.wasHungry)
        	*/
        	if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0F != this.wasHungry ||
        		m_iLastFoodSaturation != (int)( foodStats.getSaturationLevel() * 8F ) )
    		// END FCMOD
            {
                this.playerNetServerHandler.sendPacketToPlayer(new Packet8UpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                this.wasHungry = this.foodStats.getSaturationLevel() == 0.0F;
            	// FCMOD: Code added
            	m_iLastFoodSaturation = (int)( foodStats.getSaturationLevel() * 8F );
            	// END FCMOD
            }

            if (this.experienceTotal != this.lastExperience)
            {
                this.lastExperience = this.experienceTotal;
                this.playerNetServerHandler.sendPacketToPlayer(new Packet43Experience(this.experience, this.experienceTotal, this.experienceLevel));
            }
        }
        catch (Throwable var4)
        {
            CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
            CrashReportCategory var3 = var2.makeCategory("Player being ticked");
            this.func_85029_a(var3);
            throw new ReportedException(var2);
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        this.mcServer.getConfigurationManager().sendChatMsg(this.field_94063_bt.func_94546_b());

        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
        {
            this.inventory.dropAllItems();
        }

        // FCMOD: Code added
        CustomModDrops( par1DamageSource );
        // END FCMOD

        Collection var2 = this.worldObj.getScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96642_c);
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            ScoreObjective var4 = (ScoreObjective)var3.next();
            Score var5 = this.getWorldScoreboard().func_96529_a(this.getEntityName(), var4);
            var5.func_96648_a();
        }

        EntityLiving var6 = this.func_94060_bK();

        if (var6 != null)
        {
            var6.addToPlayerScore(this, this.scoreValue);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            boolean var3 = this.mcServer.isDedicatedServer() && this.mcServer.isPVPEnabled() && "fall".equals(par1DamageSource.damageType);

            if (!var3 && this.initialInvulnerability > 0 && par1DamageSource != DamageSource.outOfWorld)
            {
                return false;
            }
            else
            {
                if (par1DamageSource instanceof EntityDamageSource)
                {
                    Entity var4 = par1DamageSource.getEntity();

                    if (var4 instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)var4))
                    {
                        return false;
                    }

                    if (var4 instanceof EntityArrow)
                    {
                        EntityArrow var5 = (EntityArrow)var4;

                        if (var5.shootingEntity instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)var5.shootingEntity))
                        {
                            return false;
                        }
                    }
                }

                return super.attackEntityFrom(par1DamageSource, par2);
            }
        }
    }

    public boolean func_96122_a(EntityPlayer par1EntityPlayer)
    {
        return !this.mcServer.isPVPEnabled() ? false : super.func_96122_a(par1EntityPlayer);
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public void travelToDimension(int par1)
    {
        if (this.dimension == 1 && par1 == 1)
        {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(4, 0));
        }
        else
        {
            if (this.dimension == 1 && par1 == 0)
            {
                this.triggerAchievement(AchievementList.theEnd);
                ChunkCoordinates var2 = this.mcServer.worldServerForDimension(par1).getEntrancePortalLocation();

                if (var2 != null)
                {
                    this.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY, (double)var2.posZ, 0.0F, 0.0F);
                }

                par1 = 1;
            }
            else
            {
                this.triggerAchievement(AchievementList.portal);
            }

            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, par1);
            this.lastExperience = -1;
            this.lastHealth = -1;
            this.lastFoodLevel = -1;
        }
    }

    /**
     * called from onUpdate for all tileEntity in specific chunks
     */
    // FCMOD: Changed (server only) to match name on client
    //private void getTileEntityInfo(TileEntity par1TileEntity)
    private void sendTileEntityToPlayer(TileEntity par1TileEntity)
    // END FCMOD
    {
        if (par1TileEntity != null)
        {
            Packet var2 = par1TileEntity.getDescriptionPacket();

            if (var2 != null)
            {
                this.playerNetServerHandler.sendPacketToPlayer(var2);
            }
        }
    }

    /**
     * Called whenever an item is picked up from walking over it. Args: pickedUpEntity, stackSize
     */
    public void onItemPickup(Entity par1Entity, int par2)
    {
        super.onItemPickup(par1Entity, par2);
        this.openContainer.detectAndSendChanges();
    }

    /**
     * Attempts to have the player sleep in a bed at the specified location.
     */
    public EnumStatus sleepInBedAt(int par1, int par2, int par3)
    {
        EnumStatus var4 = super.sleepInBedAt(par1, par2, par3);

        if (var4 == EnumStatus.OK)
        {
            Packet17Sleep var5 = new Packet17Sleep(this, 0, par1, par2, par3);
            this.getServerForPlayer().getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, var5);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacketToPlayer(var5);
        }

        return var4;
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
    {
        if (this.isPlayerSleeping())
        {
            this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 3));
        }

        super.wakeUpPlayer(par1, par2, par3);

        if (this.playerNetServerHandler != null)
        {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity par1Entity)
    {
        super.mountEntity(par1Entity);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(this, this.ridingEntity));
        this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double par1, boolean par3) {}

    /**
     * likeUpdateFallState, but called from updateFlyingState, rather than moveEntity
     */
    public void updateFlyingState(double par1, boolean par3)
    {
        super.updateFallState(par1, par3);
    }

    private void incrementWindowID()
    {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }
    
    /**
     * Displays the crafting GUI for a workbench.
     */
    public void displayGUIWorkbench(int par1, int par2, int par3)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 1, "Crafting", 9, true));
        // FCMOD: Changed
        //this.openContainer = new ContainerWorkbench(this.inventory, this.worldObj, par1, par2, par3);
        openContainer = new FCContainerWorkbench( inventory, worldObj, par1, par2, par3 );
        // END FCMOD
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 4, par4Str == null ? "" : par4Str, 9, par4Str != null));
        this.openContainer = new ContainerEnchantment(this.inventory, this.worldObj, par1, par2, par3);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * Displays the GUI for interacting with an anvil.
     */
    public void displayGUIAnvil(int par1, int par2, int par3)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 8, "Repairing", 9, true));
        this.openContainer = new ContainerRepair(this.inventory, this.worldObj, par1, par2, par3, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory par1IInventory)
    {
        if (this.openContainer != this.inventoryContainer)
        {
            this.closeScreen();
        }

        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory(), par1IInventory.isInvNameLocalized()));
        this.openContainer = new ContainerChest(this.inventory, par1IInventory);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    public void displayGUIHopper(TileEntityHopper par1TileEntityHopper)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 9, par1TileEntityHopper.getInvName(), par1TileEntityHopper.getSizeInventory(), par1TileEntityHopper.isInvNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, par1TileEntityHopper);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 9, par1EntityMinecartHopper.getInvName(), par1EntityMinecartHopper.getSizeInventory(), par1EntityMinecartHopper.isInvNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, par1EntityMinecartHopper);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory(), par1TileEntityFurnace.isInvNameLocalized()));
        this.openContainer = new ContainerFurnace(this.inventory, par1TileEntityFurnace);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, par1TileEntityDispenser instanceof TileEntityDropper ? 10 : 3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory(), par1TileEntityDispenser.isInvNameLocalized()));
        this.openContainer = new ContainerDispenser(this.inventory, par1TileEntityDispenser);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory(), par1TileEntityBrewingStand.isInvNameLocalized()));
        this.openContainer = new ContainerBrewingStand(this.inventory, par1TileEntityBrewingStand);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    /**
     * Displays the GUI for interacting with a beacon.
     */
    public void displayGUIBeacon(TileEntityBeacon par1TileEntityBeacon)
    {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 7, par1TileEntityBeacon.getInvName(), par1TileEntityBeacon.getSizeInventory(), par1TileEntityBeacon.isInvNameLocalized()));
        this.openContainer = new ContainerBeacon(this.inventory, par1TileEntityBeacon);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str)
    {
        this.incrementWindowID();
        this.openContainer = new ContainerMerchant(this.inventory, par1IMerchant, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        // FCMOD: Code moved slightly later to avoid init order problems
        /*
        this.openContainer.addCraftingToCrafters(this);
        */
        // END FCMOD
        InventoryMerchant var3 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 6, par2Str == null ? "" : par2Str, var3.getSizeInventory(), par2Str != null));
        // FCMOD: Code moved from above to avoid init order problems
        this.openContainer.addCraftingToCrafters(this);
        // END FCMOD
        MerchantRecipeList var4 = par1IMerchant.getRecipes(this);

        if (var4 != null)
        {
            try
            {
                ByteArrayOutputStream var5 = new ByteArrayOutputStream();
                DataOutputStream var6 = new DataOutputStream(var5);
                var6.writeInt(this.currentWindowId);
                var4.writeRecipiesToStream(var6);
                this.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TrList", var5.toByteArray()));
            }
            catch (IOException var7)
            {
                var7.printStackTrace();
            }
        }
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot. Args: Container, slot number, slot contents
     */
    public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
    {
        if (!(par1Container.getSlot(par2) instanceof SlotCrafting))
        {
            if (!this.playerInventoryBeingManipulated)
            {
                this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
            }
        }
    }

    public void sendContainerToPlayer(Container par1Container)
    {
        this.sendContainerAndContentsToPlayer(par1Container, par1Container.getInventory());
    }

    public void sendContainerAndContentsToPlayer(Container par1Container, List par2List)
    {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet104WindowItems(par1Container.windowId, par2List));
        this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, this.inventory.getItemStack()));
    }

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    public void sendProgressBarUpdate(Container par1Container, int par2, int par3)
    {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet101CloseWindow(this.openContainer.windowId));
        this.closeInventory();
    }

    /**
     * updates item held by mouse
     */
    public void updateHeldItem()
    {
        if (!this.playerInventoryBeingManipulated)
        {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }

    public void closeInventory()
    {
        this.openContainer.onCraftGuiClosed(this);
        this.openContainer = this.inventoryContainer;
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase != null)
        {
            if (!par1StatBase.isIndependent)
            {
                while (par2 > 100)
                {
                    this.playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, 100));
                    par2 -= 100;
                }

                this.playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, par2));
            }
        }
    }

    public void mountEntityAndWakeUp()
    {
        if (this.riddenByEntity != null)
        {
            this.riddenByEntity.mountEntity(this);
        }

        if (this.sleeping)
        {
            this.wakeUpPlayer(true, false, false);
        }
    }

    /**
     * this function is called when a players inventory is sent to him, lastHealth is updated on any dimension
     * transitions, then reset.
     */
    public void setPlayerHealthUpdated()
    {
        this.lastHealth = -99999999;
    }

    /**
     * Add a chat message to the player
     */
    public void addChatMessage(String par1Str)
    {
        StringTranslate var2 = StringTranslate.getInstance();
        String var3 = var2.translateKey(par1Str);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(var3));
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void onItemUseFinish()
    {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet38EntityStatus(this.entityId, (byte)9));
        super.onItemUseFinish();
    }

    /**
     * sets the itemInUse when the use item button is clicked. Args: itemstack, int maxItemUseDuration
     */
    public void setItemInUse(ItemStack par1ItemStack, int par2)
    {
        super.setItemInUse(par1ItemStack, par2);

        if (par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat)
        {
            this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 5));
        }
    }

    /**
     * Copies the values from the given player into this player if boolean par2 is true. Always clones Ender Chest
     * Inventory.
     */
    public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2)
    {
        super.clonePlayer(par1EntityPlayer, par2);
        this.lastExperience = -1;
        this.lastHealth = -1;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)par1EntityPlayer).destroyedItemsNetCache);
    }

    protected void onNewPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onNewPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.entityId, par1PotionEffect));
    }

    protected void onChangedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onChangedPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.entityId, par1PotionEffect));
    }

    protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
    {
        super.onFinishedPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet42RemoveEntityEffect(this.entityId, par1PotionEffect));
    }

    /**
     * Move the entity to the coordinates informed, but keep yaw/pitch values.
     */
    public void setPositionAndUpdate(double par1, double par3, double par5)
    {
        this.playerNetServerHandler.setPlayerLocation(par1, par3, par5, this.rotationYaw, this.rotationPitch);
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity par1Entity)
    {
        this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 6));
    }

    public void onEnchantmentCritical(Entity par1Entity)
    {
        this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 7));
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        if (this.playerNetServerHandler != null)
        {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet202PlayerAbilities(this.capabilities));
        }
    }

    public WorldServer getServerForPlayer()
    {
        return (WorldServer)this.worldObj;
    }

    /**
     * Sets the player's game mode and sends it to them.
     */
    public void setGameType(EnumGameType par1EnumGameType)
    {
        this.theItemInWorldManager.setGameType(par1EnumGameType);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(3, par1EnumGameType.getID()));
    }

    public void sendChatToPlayer(String par1Str)
    {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(par1Str));
    }

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    public boolean canCommandSenderUseCommand(int par1, String par2Str)
    {
        return "seed".equals(par2Str) && !this.mcServer.isDedicatedServer() ? true : (!"tell".equals(par2Str) && !"help".equals(par2Str) && !"me".equals(par2Str) ? this.mcServer.getConfigurationManager().areCommandsAllowed(this.username) : true);
    }

    /**
     * Gets the player's IP address. Used in /banip.
     */
    public String getPlayerIP()
    {
        String var1 = this.playerNetServerHandler.netManager.getSocketAddress().toString();
        var1 = var1.substring(var1.indexOf("/") + 1);
        var1 = var1.substring(0, var1.indexOf(":"));
        return var1;
    }

    public void updateClientInfo(Packet204ClientInfo par1Packet204ClientInfo)
    {
        if (this.translator.getLanguageList().containsKey(par1Packet204ClientInfo.getLanguage()))
        {
            this.translator.setLanguage(par1Packet204ClientInfo.getLanguage(), false);
        }

        int var2 = 256 >> par1Packet204ClientInfo.getRenderDistance();

        if (var2 > 3 && var2 < 15)
        {
            this.renderDistance = var2;
        }

        this.chatVisibility = par1Packet204ClientInfo.getChatVisibility();
        this.chatColours = par1Packet204ClientInfo.getChatColours();

        if (this.mcServer.isSinglePlayer() && this.mcServer.getServerOwner().equals(this.username))
        {
            this.mcServer.setDifficultyForAllWorlds(par1Packet204ClientInfo.getDifficulty());
        }

        this.setHideCape(1, !par1Packet204ClientInfo.getShowCape());
    }

    public StringTranslate getTranslator()
    {
        return this.translator;
    }

    public int getChatVisibility()
    {
        return this.chatVisibility;
    }

    /**
     * on recieving this message the client (if permission is given) will download the requested textures
     */
    public void requestTexturePackLoad(String par1Str, int par2)
    {
        String var3 = par1Str + "\u0000" + par2;
        this.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TPack", var3.getBytes()));
    }

    /**
     * Return the position for this command sender.
     */
    public ChunkCoordinates getPlayerCoordinates()
    {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5D), MathHelper.floor_double(this.posZ));
    }

    // FCMOD: Added
    public LinkedList<ChunkCoordIntPair> m_chunksToBeSentToClient = 
    	new LinkedList<ChunkCoordIntPair>();
    
    private int m_iLastFoodSaturation = -99999999;
	private int m_iExhaustionWithTimeCounter = 0;
	
	private static final int m_iExhaustionWithTimePeriod = 600; // once per 30 seconds
	private static final float m_fExhaustionWithTimeAmount = 0.5F; // set with above to begin starving to death after 160 minutes if you're fully obese, losing 1 hunger every 4 minutes	
    
    private static final float m_fMinimumGloomBiteChance = 0.01F; 
    private static final float m_fMaximumGloomBiteChance = 0.05F; // 1/second
    
	private static final int m_iDelayBetweenZeroDamageAttackSounds = 20; 
	private long m_lTimeOfLastZeroDamageAttackSound = 0;
	
    @Override
    protected void CheckForHeadDrop( DamageSource source, int iLootingModifier )
    {
	    Entity sourceEntity = source.getEntity();        
	    int iHeadChance = this.rand.nextInt(200);        
	    
        iHeadChance -= iLootingModifier;
    	
	    if ( sourceEntity instanceof EntityPlayer)
	    {
	    	if ( ((EntityPlayer)sourceEntity).getHeldItem() != null && 
	    		((EntityPlayer)sourceEntity).getHeldItem().getItem().itemID == FCBetterThanWolves.fcItemBattleAxe.itemID )
	    	{
	    		// 100% chance of decapitation with Battle Axe in PvP
	    		
	    		iHeadChance = 0;
	    	}
	    }
	    else if ( source == FCDamageSourceCustom.m_DamageSourceChoppingBlock )
	    {
			iHeadChance = 0;
	    }
	    
	    if ( iHeadChance < 5 )
	    {
	        dropHead();
	    }
    }
    
    private void CustomModDrops( DamageSource source )
    {
	    Entity sourceEntity = source.getEntity();        
        int iLootingModifier = GetAmbientLootingModifier();

        if ( sourceEntity instanceof EntityPlayer )
        {
            int iPlayerLootingModifier = EnchantmentHelper.getLootingModifier((EntityLiving)sourceEntity);
            
            if ( iPlayerLootingModifier > iLootingModifier )
            {
            	iLootingModifier = iPlayerLootingModifier;
            }
        }
        
        DropMysteryMeat( iLootingModifier );
        
        CheckForHeadDrop( source, iLootingModifier );
    }
    
    private void DropMysteryMeat( int iLootingModifier )
    {
        if ( !HasHeadCrabbedSquid() )
        {
	    	// only drop mystery meat periodically to prevent people killing themselves repeatedly for food.
	    	// With HC Spawn enabled, it only happens when the player will be moved to a new spawn location.
	    	
			long lOverworldTime = MinecraftServer.getServer().worldServers[0].getWorldTime();
			
			if ( m_lTimeOfLastSpawnAssignment == 0 || m_lTimeOfLastSpawnAssignment > lOverworldTime || 
				lOverworldTime - m_lTimeOfLastSpawnAssignment >= FCUtilsHardcoreSpawn.m_iHardcoreSpawnTimeBetweenReassignments )
			{
				int iDropItemID = FCBetterThanWolves.fcItemRawMysteryMeat.itemID;
				
				if ( isBurning() )
				{
					iDropItemID = FCBetterThanWolves.fcItemMeatBurned.itemID;
				}
		    	
		        int iFat = ( (int)foodStats.getSaturationLevel() ) / 2;
		        
		        int iNumDropped = 1 + iFat;
		
		        for ( int iTempCount = 0; iTempCount < iNumDropped; ++iTempCount )
		        {        	
		            dropItem( iDropItemID, 1 );
		        }
			}
        }
    }
    
    private void ModSpecificOnUpdate()
    {
        UpdateExhaustionWithTime();
        
        UpdateHealthAndHungerEffects();
        
        UpdateMagneticInfluences();
        
        UpdateSpawnChunksVisualization();
        
    	NotifyBlockWalkedOn();
    }
    
	private void UpdateMagneticInfluences()
	{
		if ( ( worldObj.getTotalWorldTime() + entityId ) % 40 != 0 )
		{
			// stagger these updates as they can be performance intensive
			 
			return;
		}
		 
		FCMagneticPoint strongestPoint = null;
		double dStrongestFieldStrength = 0.0D;
		 
	    if ( worldObj.provider.isSurfaceWorld() )
	    {
	    	ChunkCoordinates spawnPos = worldObj.getSpawnPoint();
	    	 
	    	strongestPoint = new FCMagneticPoint( spawnPos.posX, 0, spawnPos.posZ, 2 );
	    	 
	    	dStrongestFieldStrength = strongestPoint.GetFieldStrengthRelativeToPosition( posX, posZ );
	    	 
	        Iterator pointIterator = worldObj.GetMagneticPointList().m_MagneticPoints.iterator();
	         
	        while ( pointIterator.hasNext() )
	        {
	        	FCMagneticPoint tempPoint = (FCMagneticPoint)pointIterator.next();
	        	 
	        	double dTempFieldStrength = tempPoint.GetFieldStrengthRelativeToPosition( posX, posZ );
	        	 
	        	if ( dTempFieldStrength > dStrongestFieldStrength )
	        	{
	        		strongestPoint = tempPoint;
	        		dStrongestFieldStrength = dTempFieldStrength;
	        	}
	        }
	    }
	    else
	    {
	        Iterator pointIterator = worldObj.GetMagneticPointList().m_MagneticPoints.iterator();
	         
	        while ( pointIterator.hasNext() )
	        {
	        	FCMagneticPoint tempPoint = (FCMagneticPoint)pointIterator.next();
	        	 
	        	double dTempFieldStrength = tempPoint.GetFieldStrengthRelativeToPositionWithBackgroundNoise( posX, posZ );
	        	 
	        	if ( dTempFieldStrength > dStrongestFieldStrength )
	        	{
	        		strongestPoint = tempPoint;
	        		dStrongestFieldStrength = dTempFieldStrength;
	        	}
	        }
	    }         
	     
	    if ( strongestPoint != null )
	    {
	    	SetHasValidMagneticPointForLocation( true );
	    	SetStongestMagneticPointForLocationI( strongestPoint.m_iIPos );
	    	SetStongestMagneticPointForLocationK( strongestPoint.m_iKPos );
	    }
	    else
	    {
	    	SetHasValidMagneticPointForLocation( false );
	    }
	}
	
	private void UpdateSpawnChunksVisualization()
	{
		if ( worldObj.provider.dimensionId == 0 && ( IsWearingEnderSpectacles() || 
			isPotionActive( FCBetterThanWolves.potionTrueSight ) ) )
		{
			SetSpawnChunksVisualization( worldObj.worldInfo.getSpawnX(),
				worldObj.worldInfo.getSpawnY(), worldObj.worldInfo.getSpawnZ() );
		}
		else
		{
			SetSpawnChunksVisualization( 0, 0, 0 );
		}
	}
	
    private void UpdateExhaustionWithTime()
    {
    	m_iExhaustionWithTimeCounter++;
    	
    	if ( m_iExhaustionWithTimeCounter >= m_iExhaustionWithTimePeriod )
    	{
            if ( !capabilities.disableDamage ) // disable hunger drain in creative
            {
            	foodStats.addExhaustion( m_fExhaustionWithTimeAmount );
            }
            
    		m_iExhaustionWithTimeCounter = 0;
    	}
    }
    
    private void UpdateHealthAndHungerEffects()
    {
    	if ( !isDead && ( worldObj.getTotalWorldTime() + (long)entityId ) % 80L == 0L )
    	{
    		//AARON CHANGED to make starvation nausea less immediate
    		if ( foodStats.getFoodLevel() <= 0 && foodStats.getSaturationLevel() <= 0F && health <= 6 )
    		{
                addPotionEffect( new PotionEffect( Potion.confusion.getId(), 180, 0, true ) );                            
    		}
    		
    		if ( health <= 2 )
    		{
                addPotionEffect( new PotionEffect( Potion.blindness.getId(), 180, 0, true ) );                            
    		}
    	}
    }
    
    @Override
    protected void UpdateGloomState()
    {
    	if ( !isDead )
    	{
    		if ( IsInGloom() )
    		{
    			m_iInGloomCounter++;
    			
    			if ( GetGloomLevel() == 0 || ( m_iInGloomCounter > m_iGloomCounterBetweenStateChanges && GetGloomLevel() < 3 ) )
    			{
    				SetGloomLevel( GetGloomLevel() + 1 );
    				
    				m_iInGloomCounter = 0;
    			}
    			
    			if ( GetGloomLevel() >= 3 )
    			{
    		    	if ( ( worldObj.getTotalWorldTime() + (long)entityId ) % 80L == 0L )
    		    	{
		                addPotionEffect( new PotionEffect( Potion.confusion.getId(), 180, 0, true ) );                            
    		    	}
    		    	
    		    	// gloom bites
    		    	
    	    		float fCounterProgress = (float)m_iInGloomCounter / (float)m_iGloomCounterBetweenStateChanges;
    	    		
    	    		if ( fCounterProgress > 1.0F )
    	    		{
    	    			fCounterProgress = 1.0F;
    	    		}

            		float fGloomBiteChance = m_fMinimumGloomBiteChance + ( m_fMaximumGloomBiteChance - m_fMinimumGloomBiteChance ) * fCounterProgress;        		
    	    		
            		if ( rand.nextFloat() < fGloomBiteChance )
            		{
            			if ( attackEntityFrom( FCDamageSourceCustom.m_DamageSourceGloom, 1 ) )
            			{            			
	            			if ( health <= 0 )
	            			{
	            	        	worldObj.playAuxSFX( FCBetterThanWolves.m_iBurpSoundAuxFXID, 
	            	        		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 0 );
	            			}
            			}
            		}
		    	}
    		}
    		else
    		{
        		SetGloomLevel( 0 );
        		
        		m_iInGloomCounter = 0;
    		}   		    		
    	}
    }

    @Override
    protected void UpdateFatPenaltyLevel()
    {
        int iFat = (int)foodStats.getSaturationLevel();
    	int iFatLevel = 4;
        
        if ( iFat < 12 )
        {
        	iFatLevel = 0;
        }
        else if ( iFat < 14 )
        {
        	iFatLevel = 1;
        }
        else if ( iFat < 16 )
        {
        	iFatLevel = 2;
        }
        else if ( iFat < 18 )
        {
        	iFatLevel = 3;
        }
    	
    	SetFatPenaltyLevel( iFatLevel );
    }
	
    @Override
	protected void UpdateHungerPenaltyLevel()
	{
        int iHunger = foodStats.getFoodLevel();
        int iPenaltyLevel = 5;
        
        if ( iHunger > 24 )
        {
        	iPenaltyLevel = 0;
        }
        else if ( iHunger > 18 )
        {
        	iPenaltyLevel = 1;
        }
        else if ( iHunger > 12 )
        {
        	iPenaltyLevel = 2;
        }
        else if ( iHunger > 6 )
        {
        	iPenaltyLevel = 3;
        }
        else if ( iHunger > 0 || foodStats.getSaturationLevel() > 0F )
        {
        	iPenaltyLevel = 4;
        }
        
        SetHungerPenaltyLevel( iPenaltyLevel );
	}
	
    @Override
	protected void UpdateHealthPenaltyLevel()
	{
		int iHealth = getHealth();
        int iPenaltyLevel = 5;
        
        if ( iHealth > 10 )
        {
        	iPenaltyLevel = 0;
        }
        else if ( iHealth > 8 )
        {
        	iPenaltyLevel = 1;
        }
        else if ( iHealth > 6 )
        {
        	iPenaltyLevel = 2;
        }
        else if ( iHealth > 4 )
        {
        	iPenaltyLevel = 3;
        }
        else if ( iHealth > 2 )
        {
        	iPenaltyLevel = 4;
        }
        
        SetHealthPenaltyLevel( iPenaltyLevel );
	}
	
    private boolean IsInGloom()
    {
        if ( !capabilities.disableDamage ) // disable darkness effects in creative
        {
	        if ( !isPotionActive( Potion.nightVision ) && worldObj.provider.dimensionId == 0 )
	        {
		        int i = MathHelper.floor_double( posX );
		        int j = MathHelper.floor_double( posY - yOffset );
		        int k = MathHelper.floor_double( posZ );
		        
		        int iOldSkylightSubtracted = worldObj.skylightSubtracted;
		        
		        float fSunBrightness = worldObj.ComputeOverworldSunBrightnessWithMoonPhases();
		        
		        if ( fSunBrightness < 0.02D )
		        {
		        	// world is in gloom, no skylight at all
		        	worldObj.skylightSubtracted = 15;
		        }
		        else
		        {
		        	worldObj.skylightSubtracted = (int)( ( 1F - fSunBrightness ) * 11.9F );
		        }

		        float fBlockInLightValue = worldObj.getLightBrightness( i, j, k );
		        
		        float fBlockAboveLightValue = worldObj.getLightBrightness( i, j + 1, k );
		        
		        if ( fBlockAboveLightValue > fBlockInLightValue )
		        {
		        	fBlockInLightValue = fBlockAboveLightValue;
		        }
		        
		        worldObj.skylightSubtracted = iOldSkylightSubtracted;
		        
		    	return fBlockInLightValue < 0.001F;
	        }
        }
        
        return false;
    }
    
	@Override    
	public void AddRawChatMessage( String message )
	{
		playerNetServerHandler.sendPacket( new Packet3Chat( message ) );
	}

	@Override
    protected void OnZeroDamageAttack()
    {
		long lCurrentTime = worldObj.getWorldTime();
		
		if ( lCurrentTime > m_lTimeOfLastZeroDamageAttackSound + m_iDelayBetweenZeroDamageAttackSounds )
		{
			worldObj.playSoundAtEntity( this, 
	    		"random.classic_hurt", 1.0F +  rand.nextFloat() * 0.25F, 
	    		getSoundPitch() * 1.20F + rand.nextFloat() * 0.1F);
			
			m_lTimeOfLastZeroDamageAttackSound = lCurrentTime;
		}
    }
	
	@Override
    public void OnStruckByLightning( FCEntityLightningBolt boltEntity )
    {
        if ( !capabilities.disableDamage )
        {
            dealFireDamage( 12 );
            
            setFire( 8 );

        	FlingAwayFromEntity( boltEntity, 2D );
    		
    		worldObj.playSoundAtEntity( this, 
        		"random.classic_hurt", 1.0F +  rand.nextFloat() * 0.25F, 
        		getSoundPitch() * 1.20F + rand.nextFloat() * 0.1F);
    		
            addPotionEffect( new PotionEffect( Potion.blindness.getId(), 90, 0, true ) );
            
            addPotionEffect( new PotionEffect( Potion.confusion.getId(), 180, 0, true ) );                            
        }
    }
	
    public int IncrementAndGetWindowID()
    {
        this.currentWindowId = this.currentWindowId % 100 + 1;
        
        return currentWindowId;
    }
    
    private void NotifyBlockWalkedOn()
    {
        if ( onGround )
        {
        	int iGroundI = MathHelper.floor_double( posX );
        	int iGroundJ = MathHelper.floor_double( posY - 0.03D - (double)yOffset ); // same calc used for step sound
        	int iGroundK = MathHelper.floor_double( posZ );
        	
        	Block blockOn = Block.blocksList[worldObj.getBlockId( iGroundI, iGroundJ, iGroundK )];

        	if ( blockOn == null || blockOn.getCollisionBoundingBoxFromPool( worldObj, iGroundI, iGroundJ, iGroundK ) == null )
        	{
        		float fHalfWidth = width / 2F;
        		
        		// block we are standing on directly is air or has no collision box.  Check the horizontal extents of our box for a movement modifier

        		int iCenterGroundI = iGroundI;
        		
        		iGroundI = MathHelper.floor_double( posX + fHalfWidth );	        		
	        	blockOn = Block.blocksList[worldObj.getBlockId( iGroundI, iGroundJ, iGroundK )];
	        	
	        	if ( blockOn == null || blockOn.getCollisionBoundingBoxFromPool( worldObj, iGroundI, iGroundJ, iGroundK ) == null )
	        	{
	        		iGroundI = MathHelper.floor_double( posX - fHalfWidth );
		        	blockOn = Block.blocksList[worldObj.getBlockId( iGroundI, iGroundJ, iGroundK )];
		        	
		        	if ( blockOn == null || blockOn.getCollisionBoundingBoxFromPool( worldObj, iGroundI, iGroundJ, iGroundK ) == null )
		        	{
		        		iGroundI = iCenterGroundI;
		        		
		        		iGroundK = MathHelper.floor_double( posZ + fHalfWidth );
			        	blockOn = Block.blocksList[worldObj.getBlockId( iGroundI, iGroundJ, iGroundK )];
			        	
			        	if ( blockOn == null || blockOn.getCollisionBoundingBoxFromPool( worldObj, iGroundI, iGroundJ, iGroundK ) == null )
			        	{
			        		iGroundK = MathHelper.floor_double( posZ - fHalfWidth );
				        	blockOn = Block.blocksList[worldObj.getBlockId( iGroundI, iGroundJ, iGroundK )];					        	
			        	}
		        	}
	        	}	        	
        	}
        	
        	if ( blockOn != null )
        	{
        		blockOn.OnPlayerWalksOnBlock( worldObj, iGroundI, iGroundJ, iGroundK, this );        		
        	}
        }        
    }
    
    public void SendChunksToClient()
    {
        if ( !m_chunksToBeSentToClient.isEmpty() )
        {
            Iterator<ChunkCoordIntPair> coordIterator = m_chunksToBeSentToClient.iterator();
            
            ArrayList<Chunk> chunksToSend = new ArrayList<Chunk>();
            ArrayList<TileEntity> tileEntitiesToSend = new ArrayList<TileEntity>();

            while ( coordIterator.hasNext() && chunksToSend.size() < 5 )
            {
                ChunkCoordIntPair tempCoord = coordIterator.next();
                coordIterator.remove();

                if ( tempCoord != null && worldObj.chunkExists( 
                	tempCoord.chunkXPos, tempCoord.chunkZPos ) )
                {
                    chunksToSend.add( worldObj.getChunkFromChunkCoords(
                    	tempCoord.chunkXPos, tempCoord.chunkZPos ) );
                    
                    tileEntitiesToSend.addAll( getServerForPlayer().getAllTileEntityInBox(
                    	tempCoord.chunkXPos * 16, 0, tempCoord.chunkZPos * 16, 
                    	tempCoord.chunkXPos * 16 + 16, 256, tempCoord.chunkZPos * 16 + 16));
                }
            }

            if ( !chunksToSend.isEmpty() )
            {
	        	FCUtilsWorld.SendPacketToPlayer( playerNetServerHandler, 
	        		new Packet56MapChunks( chunksToSend ) );
	        	
                Iterator<TileEntity> tileIterator = tileEntitiesToSend.iterator();

                while ( tileIterator.hasNext() )
                {
                    TileEntity tempTile = (TileEntity)tileIterator.next();
                    
                    sendTileEntityToPlayer( tempTile );
                }

                Iterator<Chunk> chunkIterator = chunksToSend.iterator();

                while ( chunkIterator.hasNext() )
                {
                    Chunk var10 = chunkIterator.next();
                    
                    // the following call checks for entities in the chunk, and starts the player
                    // watching them for updates
                    getServerForPlayer().getEntityTracker().func_85172_a( this, var10 );
                }
            }
        }
    }
	// END FCMOD
}
