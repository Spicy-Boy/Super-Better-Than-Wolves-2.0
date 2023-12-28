// FCMOD

package net.minecraft.src;

import java.util.List;

public class FCEntityChicken extends EntityChicken
{
    protected long m_lTimeToLayEgg = 0;
    
    public FCEntityChicken( World world )
    {
        super( world );
        
        getNavigator().setAvoidsWater( true );
        
        tasks.RemoveAllTasks();
        
        tasks.addTask( 0, new EntityAISwimming( this ) );
        tasks.addTask( 1, new EntityAIPanic( this, 0.38F ) );
        tasks.addTask( 2, new EntityAIMate( this, 0.25F ) );
        tasks.addTask( 3, new FCEntityAIMultiTempt( this, 0.25F ) );
        tasks.addTask( 4, new FCEntityAIGraze( this ) );
        tasks.addTask( 5, new FCEntityAIMoveToLooseFood( this, 0.25F ) );
        tasks.addTask( 6, new FCEntityAIMoveToGraze( this, 0.25F ) );
        tasks.addTask( 7, new EntityAIFollowParent( this, 0.28F ) );
        tasks.addTask( 8, new FCEntityAIWanderSimple( this, 0.25F ) );
        tasks.addTask( 9, new EntityAIWatchClosest( this, EntityPlayer.class, 6F ) );
        tasks.addTask( 10, new EntityAILookIdle( this ) );
        
        renderDistanceWeight = 2D; // render further away than their size would normally allow
    }
    
    @Override
    public void onLivingUpdate()
    {
    	timeUntilNextEgg = 6000; // reset the vanilla egg laying counter so it never completes
    	
        super.onLivingUpdate();
    }

    @Override
    public void writeEntityToNBT( NBTTagCompound tag )
    {
        super.writeEntityToNBT( tag );
        
	 	tag.setLong( "fcTimeToLayEgg", m_lTimeToLayEgg );	    
    }
    
    @Override
    public void readEntityFromNBT( NBTTagCompound tag )
    {
        super.readEntityFromNBT( tag );

	    if ( tag.hasKey( "fcTimeToLayEgg" ) )
	    {
	    	m_lTimeToLayEgg = tag.getLong( "fcTimeToLayEgg" );
	    }
	    else
	    {
	    	m_lTimeToLayEgg = 0;
	    }
    }
    
    @Override
    protected void playStepSound( int iBlockI, int iBlockJ, int iBlockK, int iBlockID )
    {
    	// Override to get rid of annoying clicking vanilla sound
    }
    
    @Override
    protected void dropFewItems( boolean bKilledByPlayer, int iLootingModifier )
    {
    	if ( !IsStarving() )
    	{
	        int iNumDrops = rand.nextInt( 2 ) + 2 + rand.nextInt( 1 + iLootingModifier );
	
	        if ( IsFamished() )
	        {
	        	iNumDrops = iNumDrops / 2;
	        }
	
	        for ( int iTempCount = 0; iTempCount < iNumDrops; ++iTempCount )
	        {
	            dropItem( Item.feather.itemID, 1 );
	        }
	
	        if ( IsFullyFed() && !HasHeadCrabbedSquid() )
	        {
	            if ( isBurning() )
	            {
	                dropItem( FCBetterThanWolves.fcItemMeatBurned.itemID, 1 );
	            }
	            else
	            {
	                dropItem( Item.chickenRaw.itemID, 1 );
	            }
	        }
    	}
    }
    
    //AARON anxietyCounter
    int chickenAnxietyCounter = 0;
    
    //AARON is messing with chicken collision code for devious ends!
    @Override
    protected void func_85033_bc()
    {
    	
        List var1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        
        if (chickenAnxietyCounter > 0)
        {
        	//TESTER VVV
//        	System.out.println("REDUCED chicken ANXIETY");
        	//the de-increment of anxiety is 1 at a time, so it raises much faster than it drops afterwards
        	chickenAnxietyCounter--;
        }
        
        if (var1 != null && !var1.isEmpty())
        {
            for (int var2 = 0; var2 < var1.size(); ++var2)
            {
                Entity var3 = (Entity)var1.get(var2);
                
                //if colliding entity is an instance of EntityPlayer && player isn't holding a tempting item... 
                //check chicken anxiety timer, increment, then startle chicken if anxiety is maxed!
                if (var3 instanceof EntityPlayer)
                {
                	//TESTER VV
//                    System.out.println("Just collided with a chicken!!!!");
//                    System.out.println("Just collided with a chicken!!!!");
                    
                	//there has to be a != null check at the very beginning paired with the currentEquip check or the game will crash if empty handed
                	//the getCurrentEquipment check is dangerous... if empty handed during check, the game just crashes!
                	if ( ( ((EntityPlayer)var3).getCurrentEquippedItem() != null && !( this.IsTemptingItem( ((EntityPlayer)var3).getCurrentEquippedItem() ) )
                			|| ((EntityPlayer)var3).getCurrentEquippedItem() == null
                			))
                	{
	                	//400 at +5 is about 9 seconds of solid pushing! this number is different
	                    if (chickenAnxietyCounter > 700)
	                    {
	                    	//initiates outburst
	                    	OnNearbyPlayerStartles( (EntityPlayer)var3 );
	                    	//reset anxiety after outburst
	                    	chickenAnxietyCounter = 0;
	                    }
	                    else
	                    {
	                    	//TESTER VVV
//	                    	System.out.println("Added anxiety on collision, now: "+chickenAnxietyCounter);
	                    	
	                    	//SPOILER ALERT: the chicken anxiety increments rapidly, but it falls 15x slower. This makes chickens "remember" that they have been pushed and will remain agitated for a long time after contact
	                    	chickenAnxietyCounter += 15;
	                    }
                	}
                }
                


                if (var3.canBePushed())
                {
                    this.collideWithEntity(var3);
                }
            }
        }
    }
    
    @Override
    public FCEntityChicken spawnBabyAnimal( EntityAgeable parent )
    {
        return new FCEntityChicken( worldObj );
    }
    
    @Override
    protected boolean IsReadyToEatBreedingItem()
    {
    	return IsFullyFed() && getGrowingAge() == 0 && m_lTimeToLayEgg == 0;
    }
    
	@Override    
    protected void OnEatBreedingItem()
    {
    	long lCurrentTime = FCUtilsWorld.GetOverworldTimeServerOnly();
    	
    	// following morning, at least half day from now
    	
    	m_lTimeToLayEgg = ( ( ( lCurrentTime + 12000L ) / 24000L ) + 1 ) * 24000L; 
    	
    	// crack of dawn (22550) + 30 seconds random variance
    	
    	m_lTimeToLayEgg +=  (long)( -1450 + rand.nextInt( 600 ) ); 
    	
		worldObj.playSoundAtEntity( this, 
        		getDeathSound(), getSoundVolume(), 
        		rand.nextFloat() * 0.2F + 1.5F );		
    }
    
	@Override
    public boolean IsAffectedByMovementModifiers()
    {
    	return false;
    }

    @Override
    protected boolean GetCanCreatureTypeBePossessed()
    {
    	return true;
    }
    
	@Override
    protected void OnFullPossession()
    {
        worldObj.playAuxSFX( FCBetterThanWolves.m_iPossessedChickenExplosionAuxFXID, 
    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
    		0 );
        
        // explode feathers
        
        int iFeatherCount = rand.nextInt(3) + 2;

        for (int iTempCount = 0; iTempCount < iFeatherCount; iTempCount++)
        {
    		ItemStack itemStack = new ItemStack( Item.feather.itemID, 1, 0 );

        	double dFeatherX = posX + ( worldObj.rand.nextDouble() - 0.5D ) * 2D;
        	double dFeatherY = posY + 0.5D;
        	double dFeatherZ = posZ + ( worldObj.rand.nextDouble() - 0.5D ) * 2D;
        	
            EntityItem entityitem = new EntityItem( worldObj, dFeatherX, dFeatherY, dFeatherZ, itemStack );
            
            entityitem.motionX = ( worldObj.rand.nextDouble() - 0.5D ) * 0.5D;
            entityitem.motionY = 0.2D + worldObj.rand.nextDouble() * 0.3D;
            entityitem.motionZ = ( worldObj.rand.nextDouble() - 0.5D ) * 0.5D;
            
            entityitem.delayBeforeCanPickup = 10;
            
            worldObj.spawnEntityInWorld( entityitem );
        }
        
        AttemptToPossessNearbyCreatureOnDeath();
        
		setDead();
    }
        
    @Override
    public double getMountedYOffset()
    {
		return (double)height * 1.3F;
    }
    
    @Override
    public boolean isBreedingItem( ItemStack stack )
    {
        return stack.itemID == FCBetterThanWolves.fcItemChickenFeed.itemID;
    }
    
    @Override
    protected String getLivingSound()
    {
    	if ( !IsStarving() )
    	{
    		return "mob.chicken.say";
    	}
    	else
    	{
    		return "mob.chicken.hurt";
    	}
    }

    @Override
    public boolean IsSubjectToHunger()
    {
    	return true;
    }
    
    @Override
    protected int GetFoodValueMultiplier()
    {
    	return 1;
    }    

    @Override
    public boolean ShouldNotifyBlockOnGraze()
    {
    	// only clear a fraction of the blocks that we graze
    	
    	return rand.nextInt( 8 ) == 0;
    }

    @Override
    public void PlayGrazeFX( int i, int j, int k, int iBlockID )
    {
    	// chickens don't play block destroy effect since they are just pecking until
    	// the vegetation dies, rather than eating it
    }
    
    @Override
    public FCUtilsBlockPos GetGrazeBlockForPos()
    {
    	// chickens can't graze under snow and ash
    	
    	FCUtilsBlockPos pos = super.GetGrazeBlockForPos();
    	
    	if ( pos != null && FCBlockGroundCover.IsGroundCoverRestingOnBlock( worldObj, 
    		pos.i, pos.j, pos.k ) )
    	{
			return null;
    	}
    	
    	return pos;
    }
    
    @Override
    public int GetGrazeDuration()
    {
    	return 20;
    }
    
    @Override
    public boolean ShouldStayInPlaceToGraze()
    {
    	// wander off occasionally so they don't OCD the same spot
    	
    	if ( rand.nextInt( 10 ) != 0 )
    	{
    		return super.ShouldStayInPlaceToGraze();
    	}
    	
    	return false;
    }
    
    @Override
    public boolean IsHungryEnoughToForceMoveToGraze()
    {
    	// chickens are more frantic in their movements
    	
		return isChild() || !IsFullyFed() || m_iHungerCountdown + 
			( GetGrazeHungerGain() * 3 / 4 ) <= m_iFullHungerCount;
    }
    
    @Override
    protected int GetItemFoodValue( ItemStack stack )
    {
    	return stack.getItem().GetChickenFoodValue( stack.getItemDamage() ) * 
    		GetFoodValueMultiplier();
    }
    
    @Override
    public void OnBecomeFamished()
    {
    	super.OnBecomeFamished();
    	
		// if the chicken is ever not in the fully fed state, it cancels egg production
		
		m_lTimeToLayEgg = 0;
    }
    
    @Override
    protected void UpdateHungerState()
    {
        if ( !isChild() && IsFullyFed() && m_lTimeToLayEgg > 0 && ValidateTimeToLayEgg() )
        {
			// producing eggs consumes extra food. Hunger will be validated in super method
			
        	// Decided against extra hunger on chickens as I didn't think it would read well
			//m_iHungerCountdown--;
			
    		if ( FCUtilsWorld.GetOverworldTimeServerOnly() > m_lTimeToLayEgg )
    		{
	            playSound( "mob.slime.attack", 1.0F, getSoundPitch() );
	            
	            playSound( getDeathSound(), getSoundVolume(), ( getSoundPitch() + 0.25F ) * ( getSoundPitch() + 0.25F ) );
	            dropItem(Item.egg.itemID, 1);
	            
        		m_lTimeToLayEgg = 0;
    		}
        }
        
    	// must call super method after extra hunger consumed above to validate
    	
    	super.UpdateHungerState();
    }
    
	//------------- Class Specific Methods ------------//
    
    private boolean ValidateTimeToLayEgg()
    {
    	long lCurrentTime = FCUtilsWorld.GetOverworldTimeServerOnly();
    	long lDeltaTime = m_lTimeToLayEgg - lCurrentTime;
    	
    	if ( lDeltaTime > 48000L ) 
    	{
    		// we're more than 2 days before the time, something is wrong (like a time change command), so don't lay
    		
    		m_lTimeToLayEgg = 0;    		
    		
    		return false;
    	}
    	
    	return true;
    }

    @Override
    protected float GetGrazeHeadRotationMagnitudeDivisor()
    {
    	return 3F;
    }
    
    @Override
    protected float GetGrazeHeadRotationRateMultiplier()
    {
    	return 28.7F / 2F;
    }
    
	//----------- Client Side Functionality -----------//    
	
	@Override
    public String getTexture()
    {
    	int iHungerLevel = GetHungerLevel();
    	
    	if ( iHungerLevel == 1 )
    	{
			return "/btwmodtex/fcChickenFamished.png";
    	}
    	else if ( iHungerLevel == 2 )
    	{
			return "/btwmodtex/fcChickenStarving.png";
    	}
    	
        return super.getTexture();
    }
}
