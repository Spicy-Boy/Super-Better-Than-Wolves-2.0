package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.server.MinecraftServer;

public class EntityItem extends Entity
{
    /**
     * The age of this EntityItem (used to animate it up and down as well as expire it)
     */
    public int age;
    public int delayBeforeCanPickup;

    /** The health of this EntityItem. (For example, damage for tools) */
    private int health;

    /** The EntityItem's random initial float height. */
    public float hoverStart;

    public EntityItem(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.age = 0;
        this.health = 5;
        this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    }

    public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
    {
        this(par1World, par2, par4, par6);
        this.setEntityItemStack(par8ItemStack);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public EntityItem(World par1World)
    {
        super(par1World);
        this.age = 0;
        this.health = 5;
        this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
    }

    protected void entityInit()
    {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.delayBeforeCanPickup > 0)
        {
            --this.delayBeforeCanPickup;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        // FCMOD: Added
        UpdateHardcoreBuoy();
        // END FCMOD
        // FCMOD: Changed this to reverse Mojang's "fix" to items getting stuck in blocks
        // and to reduce discrepancies between client and server by pushing only on server
        /*
        this.noClip = this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
        */
        if ( !worldObj.isRemote )
        {
        	pushOutOfBlocks( posX, ( boundingBox.minY + boundingBox.maxY) / 2D, posZ );
        }
        // END FCMOD
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        boolean var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;

        if (var1 || this.ticksExisted % 25 == 0)
        {
            if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava)
            {
                this.motionY = 0.20000000298023224D;
                this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
            }

            if (!this.worldObj.isRemote)
            {
                this.searchForOtherItemsNearby();
            }
        }

        float var2 = 0.98F;

        if (this.onGround)
        {
            var2 = 0.58800006F;
            int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

            if (var3 > 0)
            {
                var2 = Block.blocksList[var3].slipperiness * 0.98F;
            }
        }

        this.motionX *= (double)var2;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)var2;

        if (this.onGround)
        {
            this.motionY *= -0.5D;
        }

        ++this.age;

        // FCMOD: Code change
        /*
        if (!this.worldObj.isRemote && this.age >= 6000)
        {
            this.setDead();
        }
        */
        CheckForItemDespawn();
        // END FCMOD
    }

    /**
     * Looks for other itemstacks nearby and tries to stack them together
     */
    private void searchForOtherItemsNearby()
    {
        Iterator var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();

        while (var1.hasNext())
        {
            EntityItem var2 = (EntityItem)var1.next();
            this.combineItems(var2);
        }
    }

    /**
     * Tries to merge this item with the item passed as the parameter. Returns true if successful. Either this item or
     * the other item will  be removed from the world.
     */
    public boolean combineItems(EntityItem par1EntityItem)
    {
        if (par1EntityItem == this)
        {
            return false;
        }
        else if (par1EntityItem.isEntityAlive() && this.isEntityAlive())
        {
            ItemStack var2 = this.getEntityItem();
            ItemStack var3 = par1EntityItem.getEntityItem();

            if (var3.getItem() != var2.getItem())
            {
                return false;
            }
            else if (var3.hasTagCompound() ^ var2.hasTagCompound())
            {
                return false;
            }
            else if (var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound()))
            {
                return false;
            }
            else if (var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage())
            {
                return false;
            }
            else if (var3.stackSize < var2.stackSize)
            {
                return par1EntityItem.combineItems(this);
            }
            else if (var3.stackSize + var2.stackSize > var3.getMaxStackSize())
            {
                return false;
            }
            else
            {
                var3.stackSize += var2.stackSize;
                par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
                par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
                par1EntityItem.setEntityItemStack(var3);
                this.setDead();
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * sets the age of the item so that it'll despawn one minute after it has been dropped (instead of five). Used when
     * items are dropped from players in creative mode
     */
    public void setAgeToCreativeDespawnTime()
    {
        this.age = 4800;
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    /**
     * Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    protected void dealFireDamage(int par1)
    {
        this.attackEntityFrom(DamageSource.inFire, par1);
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
        else if (this.getEntityItem() != null && this.getEntityItem().itemID == Item.netherStar.itemID && par1DamageSource.isExplosion())
        {
            return false;
        }
        else
        {
            this.setBeenAttacked();
	        // FCMOD: Code added
	        if ( !worldObj.isRemote && !isDead && getEntityItem().getItem().itemID == FCBetterThanWolves.fcItemBlastingOil.itemID )
	        {
	    		DetonateBlastingOil();
	    		
	    		return false;
		    }
	        // END FCMOD
            this.health -= par2;

            if (this.health <= 0)
            {
                this.setDead();
            }

            return false;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("Health", (short)((byte)this.health));
        par1NBTTagCompound.setShort("Age", (short)this.age);

        if (this.getEntityItem() != null)
        {
            par1NBTTagCompound.setCompoundTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
        
        // FCMOD: Code added
        par1NBTTagCompound.setLong( "fcDespawnTime", m_lAbsoluteItemDespawnTime );
        
    	par1NBTTagCompound.setString("yyDroppedByPlayerDeath", droppedByPlayerDeath);
    	par1NBTTagCompound.setInteger("yyNDeath", nDeath);
	    // END FCMOD    
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.health = par1NBTTagCompound.getShort("Health") & 255;
        this.age = par1NBTTagCompound.getShort("Age");
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));

        // FCMOD: Code added
	    if ( par1NBTTagCompound.hasKey( "fcDespawnTime" ) )
	    {
	    	m_lAbsoluteItemDespawnTime = par1NBTTagCompound.getLong( "fcDespawnTime" );
	    }
	    // END FCMOD
	    if(par1NBTTagCompound.hasKey("yyDroppedByPlayerDeath"))
    	{
    		droppedByPlayerDeath = par1NBTTagCompound.getString("yyDroppedByPlayerDeath");
    	}
    	if(par1NBTTagCompound.hasKey("yyNDeath"))
    	{
    		nDeath = par1NBTTagCompound.getInteger("yyNDeath");
    	}
	    
        if (this.getEntityItem() == null)
        {
            this.setDead();
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote)
        {
            ItemStack var2 = this.getEntityItem();
            int var3 = var2.stackSize;

            if (this.delayBeforeCanPickup == 0 && par1EntityPlayer.inventory.addItemStackToInventory(var2))
            {
                if (var2.itemID == Block.wood.blockID)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }

                if (var2.itemID == Item.leather.itemID)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.killCow);
                }

                if (var2.itemID == Item.diamond.itemID)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
                }

                if (var2.itemID == Item.blazeRod.itemID)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
                }

                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, var3);

                if (var2.stackSize <= 0)
                {
                    this.setDead();
                }
            }
        }
    }

    /**
     * Gets the username of the entity.
     */
    public String getEntityName()
    {
        return StatCollector.translateToLocal("item." + this.getEntityItem().getItemName());
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    public void travelToTheEnd(int par1)
    {
        super.travelToTheEnd(par1);

        if (!this.worldObj.isRemote)
        {
            this.searchForOtherItemsNearby();
        }
    }

    /**
     * Returns the ItemStack corresponding to the Entity (Note: if no item exists, will log an error but still return an
     * ItemStack containing Block.stone)
     */
    public ItemStack getEntityItem()
    {
        ItemStack var1 = this.getDataWatcher().getWatchableObjectItemStack(10);

        if (var1 == null)
        {
            if (this.worldObj != null)
            {
                this.worldObj.getWorldLogAgent().logSevere("Item entity " + this.entityId + " has no item?!");
            }

            return new ItemStack(Block.stone);
        }
        else
        {
            return var1;
        }
    }

    /**
     * Sets the ItemStack for this entity
     */
    public void setEntityItemStack(ItemStack par1ItemStack)
    {
        this.getDataWatcher().updateObject(10, par1ItemStack);
        this.getDataWatcher().setObjectWatched(10);
    }

    // FCMOD: Code added
    private long m_lAbsoluteItemDespawnTime = 0;
    
    //AARON ADDED from YANY!!
    public String droppedByPlayerDeath = "";
    public int nDeath = -1;
    
    private void UpdateHardcoreBuoy()
    {
        if ( FCBetterThanWolves.IsHardcoreBuoyEnabled( worldObj ) )
        {
	        int numDepthChecks = 10;
	        double d = 0.0D;
	        double dBoundingYOffset = 0.10D;
	
	        for ( int j = 0; j < numDepthChecks; j++ )
	        {
	            double d2 = ( boundingBox.minY + ( ( boundingBox.maxY - boundingBox.minY) * (double)(j + 0) ) * ( 0.375D ) ) + dBoundingYOffset;
	            double d8 = ( boundingBox.minY + ( ( boundingBox.maxY - boundingBox.minY) * (double)(j + 1) ) * ( 0.375D ) ) + dBoundingYOffset;
	            
	            AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(boundingBox.minX, d2, boundingBox.minZ, boundingBox.maxX, d8, boundingBox.maxZ);
	
	            if ( worldObj.isAABBInMaterial( axisalignedbb, Material.water ) )
	            {
	                d += 1.0D / (double)numDepthChecks;
	            }
	            else 
	            {
	            	break;
	            }
	        }
	        
	        if ( d > 0.001D )
	        {
	    		if ( !IsInUndertow() )
	    		{
		        	float fBuoyancyShifted = getEntityItem().getItem().GetBuoyancy( getEntityItem().getItemDamage() ) + 1.0F;
		        	
		        	// positive velocity due to boyancy
		        	
		        	motionY += 0.04D * fBuoyancyShifted * d;
	    		}
		  
		        // drag due to being in water
		        
	        	motionX *= 0.90F;
	        	motionY *= 0.90F;
	        	motionZ *= 0.90F;	        	
	        }	        
        }
    }

    @Override
    protected void doBlockCollisions()
    {
		//Inherited function added so that blocks like Hoppers get collision events with items on top.  
    	// Copy of code from Entity.java, changes marked with FCMOD.

        int i = MathHelper.floor_double(boundingBox.minX + 0.001D);
        // FCMOD: code change
        /*
        int j = MathHelper.floor_double(boundingBox.minY + 0.001D);
        */
        int j = MathHelper.floor_double(boundingBox.minY - 0.01D);
        // END FCMOD
        int k = MathHelper.floor_double(boundingBox.minZ + 0.001D);
        int l = MathHelper.floor_double(boundingBox.maxX - 0.001D);
        int i1 = MathHelper.floor_double(boundingBox.maxY - 0.001D);
        int j1 = MathHelper.floor_double(boundingBox.maxZ - 0.001D);

        if (worldObj.checkChunksExist(i, j, k, l, i1, j1))
        {
            for (int k1 = i; k1 <= l; k1++)
            {
                for (int l1 = j; l1 <= i1; l1++)
                {
                    for (int i2 = k; i2 <= j1; i2++)
                    {
                        int j2 = worldObj.getBlockId(k1, l1, i2);

                        if (j2 > 0)
                        {
                            Block.blocksList[j2].onEntityCollidedWithBlock(worldObj, k1, l1, i2, this);
                        }
                    }
                }
            }
        }
    }

    private boolean IsInUndertow()
    {
        int minI = MathHelper.floor_double( boundingBox.minX);
        int maxI = MathHelper.floor_double( boundingBox.maxX + 1.0D);
        
        int minJ = MathHelper.floor_double( boundingBox.minY);
        int maxJ = MathHelper.floor_double( boundingBox.maxY + 1.0D);
        
        int minK = MathHelper.floor_double( boundingBox.minZ);
        int maxK = MathHelper.floor_double( boundingBox.maxZ + 1.0D );

        for ( int i = minI; i < maxI; i++ )
        {
            for ( int j = minJ; j < maxJ; j++ )
            {
                for ( int k = minK; k < maxK; k++ )
                {
                	if ( DoesBlockHaveUndertow( i, j, k ) )
            		{
                		return true;
            		}                	
                }
            }
        }

        return false;
    }
    
    private boolean DoesBlockHaveUndertow( int i, int j, int k )
    {
		int iBlockID = worldObj.getBlockId( i, j, k );
		
		if (  iBlockID == Block.waterMoving.blockID || iBlockID == Block.waterStill.blockID )
		{
			int iFluidHeight = worldObj.getBlockMetadata( i, j, k );
			
			if ( iFluidHeight >= 8 )
			{
				return true;
			}
	    		
			iBlockID = worldObj.getBlockId( i, j - 1, k );
	    		
    		if (  iBlockID == Block.waterMoving.blockID || iBlockID == Block.waterStill.blockID )
    		{
    			iFluidHeight = worldObj.getBlockMetadata( i, j - 1, k );
    			
    			if ( iFluidHeight >= 8 )
    			{
    				return true;
    			}
    		}	    	 
	    		
    		iBlockID = worldObj.getBlockId( i, j + 1, k );
    		
    		if (  iBlockID == Block.waterMoving.blockID || iBlockID == Block.waterStill.blockID )
    		{
    			iFluidHeight = worldObj.getBlockMetadata( i, j + 1, k );
    			
    			if ( iFluidHeight >= 8 )
    			{
    				return true;
    			}
    		}	    	 
		}
		
		return false;
    }    
    
    @Override
    protected void fall( float fFallDistance )
    {
        super.fall( fFallDistance );

        if ( !worldObj.isRemote )
        {
	        if ( getEntityItem().getItem().itemID == FCBetterThanWolves.fcItemBlastingOil.itemID )
	        {
	        	if ( fFallDistance > 3F )
	        	{
	        		DetonateBlastingOil();
	        	}
		    }
        }
    }
    
    private void DetonateBlastingOil()
    {
		int iStackSize = getEntityItem().stackSize;
		
    	health = 0;
		setDead();
		
		if ( iStackSize > 0 )
		{
			// cap the explosion size at that of TNT to avoid overly weaponzing Blasting Oil
			
	        float fExplosionSize = 1.5F + ( ( iStackSize - 1 ) * 2.5F / 63.0F );

	        worldObj.createExplosion( null, posX, posY, posZ, fExplosionSize, true );
		}
    }

    @Override
    protected boolean pushOutOfBlocks(double par1, double par3, double par5)
    {
    	// Inherited function added to revert Mojang's changes 

        int var7 = MathHelper.floor_double(par1);
        int var8 = MathHelper.floor_double(par3);
        int var9 = MathHelper.floor_double(par5);
        double var10 = par1 - (double)var7;
        double var12 = par3 - (double)var8;
        double var14 = par5 - (double)var9;

        if (this.worldObj.isBlockNormalCube(var7, var8, var9))
        {
            boolean var16 = !this.worldObj.isBlockNormalCube(var7 - 1, var8, var9);
            boolean var17 = !this.worldObj.isBlockNormalCube(var7 + 1, var8, var9);
            boolean var18 = !this.worldObj.isBlockNormalCube(var7, var8 - 1, var9);
            boolean var19 = !this.worldObj.isBlockNormalCube(var7, var8 + 1, var9);
            boolean var20 = !this.worldObj.isBlockNormalCube(var7, var8, var9 - 1);
            boolean var21 = !this.worldObj.isBlockNormalCube(var7, var8, var9 + 1);
            byte var22 = -1;
            double var23 = 9999.0D;

            if (var16 && var10 < var23)
            {
                var23 = var10;
                var22 = 0;
            }

            if (var17 && 1.0D - var10 < var23)
            {
                var23 = 1.0D - var10;
                var22 = 1;
            }

            if (var18 && var12 < var23)
            {
                var23 = var12;
                var22 = 2;
            }

            if (var19 && 1.0D - var12 < var23)
            {
                var23 = 1.0D - var12;
                var22 = 3;
            }

            if (var20 && var14 < var23)
            {
                var23 = var14;
                var22 = 4;
            }

            if (var21 && 1.0D - var14 < var23)
            {
                var23 = 1.0D - var14;
                var22 = 5;
            }

            float var25 = this.rand.nextFloat() * 0.2F + 0.1F;

            if (var22 == 0)
            {
                this.motionX = (double)(-var25);
            }

            if (var22 == 1)
            {
                this.motionX = (double)var25;
            }

            if (var22 == 2)
            {
                this.motionY = (double)(-var25);
            }

            if (var22 == 3)
            {
                this.motionY = (double)var25;
            }

            if (var22 == 4)
            {
                this.motionZ = (double)(-var25);
            }

            if (var22 == 5)
            {
                this.motionZ = (double)var25;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean IsItemEntity()
    {
    	return true;
    }
    
    @Override
    public boolean CanEntityTriggerTripwire()
    {
    	return false;
    }
    
    //AARON CHANGED with the help of YANY!
    private void CheckForItemDespawn()
    {
        if ( !worldObj.isRemote )
        {
            if(nDeath > -1)
            {
                EntityPlayer player = MinecraftServer.getServer().worldServers[0].getPlayerEntityByName(droppedByPlayerDeath);
                if(player == null)
                {
                    NBTTagCompound nbt = ((SaveHandler)MinecraftServer.getServer().worldServers[0].getSaveHandler()).getPlayerData(droppedByPlayerDeath);
                    if(nbt.hasKey("yyNumberOfDeaths") && nDeath < nbt.getInteger("yyNumberOfDeaths") - 10)
                    {
                        setDead();
                    }
                }
                //number of deaths required to kill items
                else if(nDeath < player.deathCounter - 5)
                {
                    setDead();
                }
            }
            else if ( m_lAbsoluteItemDespawnTime > 0 )
            {
                // using getTotalWorldTime() here so that /time commands and time advancement due to HC Spawn don't affect it
                
                long lOverworldTime = MinecraftServer.getServer().worldServers[0].getTotalWorldTime();
                
                if ( lOverworldTime >= m_lAbsoluteItemDespawnTime  )
                {
                    setDead();
                }                
            }
            else
            {
                if ( age >= 6000 )
                {
                    setDead();
                }
            }
        }
    }
    
    //AARON CHANGED to match Yany's example ;p
    public void SetEntityItemAsDroppedOnPlayerDeath(EntityPlayer player) {
        // set items dropped on player death to despawn 1 Minecraft day (20 minutes) later
        // using getTotalWorldTime() here so that /time commands and time advancement due to HC Spawn don't affect it
        
        if (!worldObj.isRemote) {
            droppedByPlayerDeath = player.username;
            nDeath = player.deathCounter;
        }
    }
    
    static public boolean InstallationIntegrityTestEntityItem()
    {
    	return true;
    }
    // END FCMOD    
}
