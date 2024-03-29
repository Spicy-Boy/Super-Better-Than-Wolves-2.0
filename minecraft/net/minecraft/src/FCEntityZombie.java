// FCMOD

package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class FCEntityZombie extends EntityZombie
{
	public int m_iVillagerClass = -1;

    private IEntitySelector m_targetEntitySelector;
	
    public FCEntityZombie( World world )
    {
        super( world );
        
        getNavigator().setBreakDoors( false );
        
        tasks.RemoveAllTasksOfClass( EntityAIBreakDoor.class );
        tasks.RemoveAllTasksOfClass( EntityAIAttackOnCollide.class );
        tasks.RemoveAllTasksOfClass( EntityAIWander.class );
        
        tasks.addTask( 1, new FCEntityAIZombieBreakBarricades( this ) );
        tasks.addTask( 2, new EntityAIAttackOnCollide( this, EntityPlayer.class, moveSpeed, false ) );
        tasks.addTask( 3, new FCEntityAIZombieSecondaryAttack( this ) );        
        tasks.addTask( 6, new FCEntityAIWanderSimple( this, moveSpeed ) );
        
        targetTasks.RemoveAllTasksOfClass( EntityAINearestAttackableTarget.class );
        
        //AARON added this task to the very end... will it work?
        tasks.addTask(1, new SuperBTWEntityAIBreakBlock(this));
        
        targetTasks.addTask( 2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 
        	16F, 0, true ) ); // last param is line of sight
//        tasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 24.0F, 0,
//                ((EntityMobAccess)this).getCanXray() == (byte)0));

        m_targetEntitySelector = new FCEntityZombieSecondaryTargetFilter( this );
        
        targetTasks.addTask( 2, new EntityAINearestAttackableTarget( this, EntityCreature.class, 16F, 0, 
        	false, false, m_targetEntitySelector ) );
        
    }
    
    @Override
    protected int func_96121_ay()
    {
    	// this function determines PathNavigate.pathSearchRange
    	// the parent setting it to 40 (instead of the default 16 for all other entities
    	// was causing zombies to look outside of the loaded chunks for a path, causing them
    	// to be loaded when they shouldn't have been 
    	
        return 16;
    }
    
    @Override
    public float getSpeedModifier()
    {
    	if ( isVillager() )
    	{
    		return 1.5F;
    	}
    	
        return super.getSpeedModifier();
    }
    
    @Override
    public void setVillager( boolean bIsVillager )
    {
    	super.setVillager( bIsVillager );
    	
        getNavigator().setBreakDoors( bIsVillager );
    }
    
    @Override
    public void onLivingUpdate()
    {
		if ( !isVillager() )
		{
			CheckForCatchFireInSun();
		}
		
    	EntityMobOnLivingUpdate(); // skip EntityZombie method as we have our own sunlight handling		
    }
    
    @Override
    public boolean attackEntityAsMob( Entity attackedEntity )
    {
    	// setting fire to other mobs has been moved up in the hierarchy
    	
        return MeleeAttack( attackedEntity ); // super.super() call
    }
    
    @Override
    protected void dropRareDrop( int iBonusDrop )
    {
    	// override to prevent super method from dropping stuff
    }
    
    protected void addRandomArmor()
    {
        EntityLivingAddRandomArmor();  // super.super() call

        if ( rand.nextFloat() < 0.05F )
        {
            int iHeldType = rand.nextInt( 3 );

            if ( iHeldType == 0 )
            {
                setCurrentItemOrArmor( 0, new ItemStack( Item.swordIron ) );
            }
            else
            {
                setCurrentItemOrArmor( 0, new ItemStack( Item.shovelIron ) );
            }

            // increase held equipment drop rate
            
            equipmentDropChances[0] = 0.99F;
        }
    }
    
    @Override
    public void writeEntityToNBT( NBTTagCompound tag )
    {
        super.writeEntityToNBT( tag );
        
        tag.setInteger( "fcVillagerClass", m_iVillagerClass );
    }

    @Override
    public void readEntityFromNBT( NBTTagCompound tag )
    {
        super.readEntityFromNBT( tag );
        
        if ( tag.hasKey( "fcVillagerClass" ) )
        {
        	m_iVillagerClass = tag.getInteger( "fcVillagerClass" );
        }
    }
    
    @Override
    public void onKillEntity( EntityLiving entityKilled )
    {
    	// super method intentionally not called here
    	
        if ( rand.nextInt( 4 ) != 0 && entityKilled instanceof FCEntityVillager )
        {
            FCEntityZombie newZombie = new FCEntityZombie( this.worldObj );
            
            newZombie.func_82149_j( entityKilled ); // set same position and ables as killed entity
            
            worldObj.removeEntity( entityKilled );
            
            newZombie.SetPersistent( true );
            newZombie.setVillager( true );
            
            newZombie.m_iVillagerClass = ((FCEntityVillager)entityKilled).getProfession();

            if ( entityKilled.isChild() )
            {
                newZombie.setChild( true );
            }

            worldObj.spawnEntityInWorld( newZombie );
            
            worldObj.playAuxSFXAtEntity( null, 1016, (int)posX, (int)posY, (int)posZ, 0 );
        }
    }
    
    @Override
    public void initCreature()
    {
    	// super method intentionally not called here
    	
        setCanPickUpLoot( rand.nextFloat() < 0.15F );

        addRandomArmor();
        
        func_82162_bC(); // check for enchanting equipped items
    }
    
    @Override
    public boolean interact( EntityPlayer player )
    {
    	// Overriden to get rid of vanilla method of curing zombies with golden apples
    	
    	return false;
    }
    
    @Override
    protected void convertToVillager()
    {
        FCEntityVillager newVillager = FCEntityVillager.createVillagerFromProfession(this.worldObj, this.m_iVillagerClass);
        
        newVillager.func_82149_j(this);  // set same position and ables as replaced entity
        
        newVillager.initCreature();
        
        if (m_iVillagerClass == 0)
        {
        	newVillager.SetDirtyPeasant(1);
        }
        
        newVillager.func_82187_q();

        if (this.isChild())
        {
            newVillager.setGrowingAge( -newVillager.GetTicksForChildToGrow() );
        }

        worldObj.removeEntity(this);
        worldObj.spawnEntityInWorld(newVillager);
        
        newVillager.addPotionEffect(new PotionEffect( Potion.confusion.id, 200, 0 ) );
        
        worldObj.playAuxSFXAtEntity( (EntityPlayer)null, 1017, (int)posX, (int)posY, (int)posZ, 0 );
    }
    
    @Override
    protected int getConversionTimeBoost()
    {
    	// override of super to remove random element and make conversion times consisten
    	
    	return 1;
    }
    
    @Override
    protected void ModSpecificOnLivingUpdate()
    {
    	super.ModSpecificOnLivingUpdate();
    	
    	CheckForLooseFood();
    	
    	if ( !worldObj.isRemote && isVillager() && !isDead && m_iVillagerClass < 0 )
    	{
    		// this is a villager zombie that hasn't been properly initialized with a class, 
    		// probably spawned before zombie apocalypse changes
    		
    		setVillager( false );
    	}
    }
    
    @Override
    public void CheckForScrollDrop()
    {    	
    	if ( rand.nextInt( 1000 ) == 0 )
    	{
            ItemStack itemstack = new ItemStack( FCBetterThanWolves.fcItemArcaneScroll, 1, Enchantment.smite.effectId );
            
            entityDropItem( itemstack, 0F );
    	}
    }
    
    @Override
    protected void attackEntity( Entity attackedEntity, float fDistanceToTarget )
    {
    	if ( attackedEntity instanceof EntityAnimal )
    	{
        	// extend reach on zombies slightly to avoid problems attacking animals due to their elongated bodies
        	
            if ( attackTime <= 0 && fDistanceToTarget < 4.0F )
            {
                attackTime = 20;
                attackEntityAsMob( attackedEntity );
            }
    	}
    	else
    	{
    		super.attackEntity( attackedEntity, fDistanceToTarget );
    	}    	
    }
    
    @Override
    protected void dropHead()
    {
    	entityDropItem( new ItemStack( Item.skull.itemID, 1, 2 ), 0F );
    }
    
    @Override
    public void SpawnerInitCreature()
    {
    	setCanPickUpLoot( rand.nextFloat() < 0.15F );
    	
    	func_82162_bC();
    }

    @Override
    protected float getSoundPitch()
    {
    	if ( !isChild() && isVillager() )
    	{
    		return ( rand.nextFloat() - rand.nextFloat() ) * 0.2F + 0.7F;
    	}
    	else
    	{
    		return super.getSoundPitch();
    	}
    }
    
    @Override
    public boolean GetCanBeHeadCrabbed( boolean bSquidInWater )
    {
    	// can only head crabbed when they randomly collide with a squid out of water, 
    	// but are never actually targetted
    	
    	return !bSquidInWater && riddenByEntity == null && isEntityAlive() && !isChild();
    }
    
    @Override
    public void OnHeadCrabbedBySquid( FCEntitySquid squid )
    {
        playSound( getDeathSound(), getSoundVolume(), getSoundPitch());
    }
    
    @Override
    public double getMountedYOffset()
    {
		return (double)height;
    }
    
    @Override
    protected boolean IsWeightedByHeadCrab()
    {
    	return false;
    }
    
    @Override
    public Entity GetHeadCrabSharedAttackTarget()
    {
    	return getAttackTarget();
    }
    
    @Override
	public boolean IsImmuneToHeadCrabDamage()
	{
		return true;
	}
	
	//------------- Class Specific Methods ------------//
    
	private void CheckForLooseFood()
	{
	    if ( !worldObj.isRemote && !isLivingDead )
	    {
	    	boolean bAte = false;
	    	
	        List itemList = worldObj.getEntitiesWithinAABB( EntityItem.class, boundingBox.expand( 2.5D, 1D, 2.5D ) );
	        
	        Iterator itemIterator = itemList.iterator();
	
	        while ( itemIterator.hasNext())
	        {
	    		EntityItem itemEntity = (EntityItem)itemIterator.next();
	    		
		        if ( itemEntity.delayBeforeCanPickup == 0 && !(itemEntity.isDead) )
		        {
		        	ItemStack itemStack = itemEntity.getEntityItem();
		        	
		        	Item item = itemStack.getItem();
		        	
		        	if ( item.DoZombiesConsume() )
		        	{
			            itemEntity.setDead();
			            
			            bAte = true;				            
		        	}
		        }		        
	        }
	        
	        if ( bAte )
	        {
	        	worldObj.playAuxSFX( FCBetterThanWolves.m_iBurpSoundAuxFXID, 
	        		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 0 );
	        }
	    }
	}
	
    public boolean AttemptToStartCure()
    {
    	if ( !isLivingDead && isVillager() && !isConverting() )
    	{
    		startConversion( rand.nextInt( 2401 ) + 3600 );
    		
    		return true;
    	}
    	
    	return false;
    }
    
	//----------- Client Side Functionality -----------//
    
    public void handleHealthUpdate(byte var1)
    {
        super.handleHealthUpdate(var1);

        if (var1 == 16)
        {
            this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.say", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.2F + 0.5F, false);
        }
    }
}
