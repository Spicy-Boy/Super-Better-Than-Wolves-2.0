// FCMOD

package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class FCEntityWolf extends EntityWolf
{
	private static final int m_iIsEngagedInPossessionAttemptDataWatcherID = 26;

	private static final float m_fMoveSpeedAggressive = 0.45F;
	private static final float m_fMoveSpeedPassive = 0.3F;    

	public int m_iHowlingCountdown = 0;

	public int m_iHeardHowlCountdown = 0;

	public int m_iInfectionCountdown = -1;

	private static final int m_iMinimumInfectionTime = 12000;
	private static final int m_iInfectionTimeVariance = 12000;

	private float m_iPossessionHeadRotation = 0F;
	private boolean m_bIsDoingHeadSpin = false;
	private boolean m_bHasHeadSpunOnThisPossessionAttempt = false;
	private int m_iPossessionAttemptCountdown = 0;

	private static final int m_iChanceOfPossessionAttempt = ( FCUtilsMisc.m_iTicksPerMinute * 10 );
	private static final int m_iPossessionAttemptTime = ( FCUtilsMisc.m_iTicksPerSecond * 10 );

	protected static final int m_iHungerCountVariance = ( FCUtilsMisc.m_iTicksPerMinute );

	public FCEntityWolf( World world )
	{
		super( world );

		moveSpeed = m_fMoveSpeedAggressive;

		tasks.RemoveAllTasks();

		tasks.addTask( 1, new EntityAISwimming( this ) );
		tasks.addTask( 2, new FCEntityAIPanicOnHeadCrab( this, m_fMoveSpeedAggressive ) );
		tasks.addTask( 3, aiSit );
		tasks.addTask( 4, new EntityAILeapAtTarget( this, 0.4F ) );
		// FCTODO: Should the following be passive move speed or agressive?
		tasks.addTask( 5, new EntityAIAttackOnCollide( this, m_fMoveSpeedPassive, true ) );
		tasks.addTask( 6, new EntityAIFollowOwner( this, m_fMoveSpeedPassive, 10F, 2F ) );
		tasks.addTask( 7, new EntityAIMate( this, m_fMoveSpeedPassive ) );
		//		Hiracho: added multitempt
		tasks.addTask( 7, new FCEntityAIMultiTempt( this, m_fMoveSpeedPassive ) );
		tasks.addTask( 8, new FCEntityAIWolfHowl( this ) );
		tasks.addTask( 8, new FCEntityAIWolfHowlSitting( this ) );
		//		Hiracho: wolves dont slow down when walking to loose food
		tasks.addTask( 9, new FCEntityAIMoveToLooseFood( this, m_fMoveSpeedPassive ) );
		tasks.addTask( 10, new FCEntityAIWanderSimple( this, m_fMoveSpeedPassive ) );
		tasks.addTask( 11, new EntityAIBeg( this, 8F ) );
		tasks.addTask( 12, new EntityAIWatchClosest( this, EntityPlayer.class, 8F ) );
		tasks.addTask( 13, new EntityAILookIdle( this ) );        

		targetTasks.RemoveAllTasks();

		targetTasks.addTask( 1, new EntityAIOwnerHurtByTarget( this ) );
		targetTasks.addTask( 2, new EntityAIOwnerHurtTarget( this ) );
		targetTasks.addTask( 3, new EntityAIHurtByTarget( this, true ) );

		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfStarvingOrHostile( this, 
				FCEntityVillager.class, 16F, 0, false ) );

		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfStarvingOrHostile( this, 
				EntityPlayer.class, 16F, 0, false ) );

		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfHungry( this, 
				FCEntityChicken.class, 16F, 0, false ) );

		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfHungry( this, 
				FCEntitySheep.class, 16F, 0, false ) );

		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfHungry( this, 
				FCEntityPig.class, 16F, 0, false ) );

		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfStarving( this, 
				FCEntityCow.class, 16F, 0, false ) );        
	}

	@Override
	public void setAttackTarget( EntityLiving target )
	{
		EntityLivingSetAttackTarget( target ); // bypass parent
	}

	//AARON temporarily disabled this shit because he can't get entityplayer to register as a kill... why??!!
//	@Override
//	public void onKillEntity( EntityLiving entityKilled )
//	{
//		System.out.println("ON KILLED ENTITY");
//		System.out.println("ON KILLED ENTITY");
//		System.out.println("ON KILLED ENTITY");
//		System.out.println("ON KILLED ENTITY");
//		System.out.println("ON KILLED ENTITY");
//		System.out.println("ON KILLED ENTITY");
//		
//		System.out.println("ON KILLED ENTITY");
//		System.out.println("ON KILLED ENTITY");
//
//		//    	On kill player
//		if (entityKilled instanceof EntityPlayer)
//		{
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			System.out.println("KILLED A PLAYER :DDDD");
//			
//		}
//
//
//	}

	@Override
	public int getMaxHealth()
	{
		return 20;
	}    

	@Override
	protected void entityInit()
	{
		super.entityInit();

		dataWatcher.addObject( m_iIsEngagedInPossessionAttemptDataWatcherID, new Byte( (byte)0 ) );

		ResetHungerCountdown();
	}

	@Override
	public void writeEntityToNBT( NBTTagCompound tag )
	{
		super.writeEntityToNBT(tag);

		tag.setInteger( "fcInfection", m_iInfectionCountdown );        
	}

	@Override
	public void readEntityFromNBT( NBTTagCompound tag )
	{
		super.readEntityFromNBT( tag );

		if ( tag.hasKey( "bIsFed" ) )
		{
			boolean bIsFed = tag.getBoolean( "bIsFed" );

			if ( bIsFed )
			{
				SetHungerLevel( 0 );
			}
			else
			{
				SetHungerLevel( 1 );
			}
		}

		if ( tag.hasKey( "fcInfection" ) )
		{
			m_iInfectionCountdown = tag.getInteger( "fcInfection" );
		}
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	protected String getLivingSound()
	{
		if ( IsWildAndHostile() )
		{
			return "mob.wolf.growl";
		}
		else if ( this.rand.nextInt(3) == 0 )
		{
			if ( isTamed() && ( dataWatcher.getWatchableObjectInt(18) < 10 || !IsFullyFed() ) )
			{
				if ( IsStarving() )
				{
					return "mob.wolf.growl";
				}
				else
				{
					return "mob.wolf.whine";
				}
			}
			else
			{
				return "mob.wolf.panting";
			}
		}
		else
		{
			return "mob.wolf.bark";
		}        
	}

	@Override
	protected int getDropItemId()
	{
		if ( !worldObj.isRemote )
		{
			// yummy yummy wolfchops

			if ( isBurning() )
			{
				return FCBetterThanWolves.fcItemMeatBurned.itemID;
			} 
			else
			{            
				return FCBetterThanWolves.fcItemWolfRaw.itemID;
			}
		}

		return -1;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if ( worldObj.isRemote )
		{
			m_iHowlingCountdown = Math.max( 0, m_iHowlingCountdown - 1 );
		}
		else
		{
			m_iHeardHowlCountdown = Math.max( 0, m_iHeardHowlCountdown - 1 );

			if ( m_iInfectionCountdown > 0 )
			{
				m_iInfectionCountdown--;

				if ( m_iInfectionCountdown <= 0 )
				{
					TransformToDire();

					return; // entity is no longer valid after transformation
				}
			}
		}
	}

	@Override
	public boolean attackEntityFrom( DamageSource source, int iDamageAmount )
	{
		if ( !isEntityInvulnerable() && !worldObj.isRemote && !isTamed() )
		{
			if ( source.getEntity() instanceof EntityPlayer )
			{
				// When wild wolves are attacked by a player they turn permanently hostile

				setAngry( true );
			}           
		}

		return super.attackEntityFrom(source, iDamageAmount);
	}

	@Override
	public int GetMeleeAttackStrength( Entity target )
	{
		return 4;
	}

	@Override
	public boolean attackEntityAsMob( Entity target )
	{
		// override parent, so wild and tame wolves do the same damage

		return MeleeAttack( target );
	}

	@Override
	public boolean interact( EntityPlayer player )
	{
		ItemStack playerStack = player.inventory.getCurrentItem();

		if ( playerStack != null )
		{
			if (playerStack.itemID == FCBetterThanWolves.fcItemWolfRaw.itemID || playerStack.itemID == FCBetterThanWolves.fcItemWolfCooked.itemID) {
				// turn wolves hostile when you try to feed them wolf meat

				worldObj.playSoundAtEntity(this, "mob.wolf.growl", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

				setAngry(true);
				setTamed(false);
				setOwner("");

				setAttackTarget( player );

				return true;
			}

			if ( AttemptUseStackOn( player, playerStack ) )
			{
				if ( !player.capabilities.isCreativeMode )
				{
					playerStack.stackSize--;

					if ( playerStack.stackSize <= 0 )
					{
						player.inventory.setInventorySlotContents(
								player.inventory.currentItem, null );
					}
				}

				return true;
			}
		}
		if ( EntityAnimalInteract( player ) ) 
			return true;

		if (!worldObj.isRemote && isTamed() && player.username.equalsIgnoreCase(getOwnerName()) && (playerStack == null || !IsEdibleItem(playerStack)  || !isBreedingItem(playerStack) ))
		{
			aiSit.setSitting( !isSitting() );
			isJumping = false;
			setPathToEntity( null );
		}

		return false;
	}

	@Override
	public boolean GetCanBeHeadCrabbed( boolean bSquidInWater )
	{
		// can only head crabbed when they randomly collide with a squid out of water, 
		// but are never actually targetted

		return !bSquidInWater && riddenByEntity == null && isEntityAlive() && !isChild();
	}

	@Override
	public double getMountedYOffset()
	{
		return (double)height * 1.2D;
	}
	//	Hiracho: changed to use mysterymeat
	//AARON added treat
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return (stack !=null && (stack.itemID == FCBetterThanWolves.fcItemRawMysteryMeat.itemID || stack.itemID == FCBetterThanWolves.fcItemCookedMysteryMeat.itemID || stack.itemID == SuperBTWDefinitions.treat.itemID));
	}

	@Override
	public void setAngry( boolean bAngry )
	{
		super.setAngry( bAngry );

		if ( bAngry )
		{
			setSitting(false);
		}
	}

	@Override
	public boolean IsReadyToEatLooseFood()
	{
		// only eat loose food if the wolf is not fed.  Don't do it if they are just 
		// wounded like by hand, nor do it for breeding like with other animals.
		// Hiracho: changed vvv, do check breeding items on ground
//		return (!IsFullyFed() || IsReadyToEatBreedingItem());
		return (!IsFullyFed());
	}

	@Override
	public boolean IsReadyToEatLooseItem( ItemStack stack )
	{
		Item tempItem = stack.getItem();

		if ( IsReadyToEatLooseFood() && tempItem.IsWolfFood() && ( tempItem.itemID != Item.rottenFlesh.itemID || IsStarving() ) )
		{
			return true;
		}

		return false;
	}

	@Override
	protected boolean AttemptToEatLooseItem( ItemStack stack )
	{
		if ( IsReadyToEatLooseItem( stack ) )
		{
			OnEat( stack.getItem() );

			return true;
		}

		return false;
	}

	@Override
	public boolean IsEdibleItem( ItemStack stack )
	{
		return stack.getItem().IsWolfFood();
	}
	//	Hiracho removed wolves needing ownder, added breedingreadiness check, always use oneat
	public boolean AttemptToBeHandFedItem(ItemStack stack) {
		if (dataWatcher.getWatchableObjectInt(18) < 20 || !IsFullyFed() || (isBreedingItem(stack) &&IsReadyToEatBreedingItem())) {
			this.OnEat(stack.getItem());
			return true;
		}

		return false;
	}

	@Override
	public void OnNearbyPlayerBlockAddOrRemove( EntityPlayer player )
	{
		// do nothing so that the player isn't attacked by wolves every time they place or remove a block
	}

	@Override
	protected void OnNearbyPlayerStartles( EntityPlayer player )
	{
		if ( !isTamed() )
		{
			if ( getAttackTarget() == null )
			{
				setAttackTarget( player );
			}
		}
	}

	@Override
	protected boolean GetCanCreatureTypeBePossessed()
	{
		return true;
	}

	@Override
	protected void HandlePossession()
	{
		super.HandlePossession();

		if ( IsFullyPossessed() )
		{
			if ( IsEngangedInPossessionAttempt() )
			{
				if ( !m_bHasHeadSpunOnThisPossessionAttempt )
				{
					m_bHasHeadSpunOnThisPossessionAttempt = true;

					m_bIsDoingHeadSpin = true;

					if ( !worldObj.isRemote )
					{
						playSound( "portal.portal", 3.0F, rand.nextFloat() * 0.1F + 0.75F );
					}
				}

				if ( !worldObj.isRemote )
				{    				
					m_iPossessionAttemptCountdown--;

					if ( m_iPossessionAttemptCountdown <= 0 )
					{
						SetEngangedInPossessionAttempt( false );

						AttemptToPossessNearbyCreature( 16D, false );
					}
				}
			}
			else
			{
				m_bHasHeadSpunOnThisPossessionAttempt = false;

				if ( !worldObj.isRemote )
				{
					if ( rand.nextInt( m_iChanceOfPossessionAttempt ) == 0 )
					{
						SetEngangedInPossessionAttempt( true );

						m_iPossessionAttemptCountdown = m_iPossessionAttemptTime;
					}
				}
			}

			UpdateHeadSpin();
		}
	}

	@Override
	public void OnNearbyAnimalAttacked( EntityAnimal attackedAnimal, EntityLiving attackSource )
	{
		// wolves and ocelots don't give a shit if a nearby animal is attacked
	}

	@Override
	public void NotifyOfWolfHowl( Entity sourceEntity )
	{
		if ( !isLivingDead )
		{
			double dDeltaX = posX - sourceEntity.posX;
			double dDeltaZ = posZ - sourceEntity.posZ;

			double dDistSq = dDeltaX * dDeltaX + dDeltaZ * dDeltaZ;

			if ( dDistSq < FCEntityAIWolfHowl.m_dHearHowlDistanceSQ )
			{
				if ( this != sourceEntity )
				{
					m_iHeardHowlCountdown = FCEntityAIWolfHowl.m_iHeardHowlDuration;
				}
			}
		}
	}    
	//	Hiracho: changed so pups are always wild, so wild wolves can breed too.
	//	TODO think about adding check for owner, wild babies from tamed wolves has its uses/kinda fun(puppytraining before it listens), maybe annoying?
	@Override
	public FCEntityWolf spawnBabyAnimal( EntityAgeable parent )
	{
		return new FCEntityWolf(worldObj);
//		FCEntityWolf baby = new FCEntityWolf( worldObj );
//
//		String sOwner = getOwnerName();
//
//		if ( sOwner != null && sOwner.trim().length() > 0 )
//		{
//			baby.setOwner( sOwner );
//			baby.setTamed( true );
//		}
//
//		return baby;
	}


	@Override
	public boolean IsSubjectToHunger()
	{
		return true;
	}

	//AARON edits the hunger state method to make baby wolves more reasonable to care for
	@Override
	protected void UpdateHungerState()
	{
		//AAROn commented out the super call (from EntityAnimal)
//		super.UpdateHungerState();
		
		//re-implement the method without exception for children 
		if ( IsSubjectToHunger() )
		{
			if ( !isChild() )
			{
				m_iHungerCountdown--;
			}
			else    			
			{
				// puppies burn more energy

				m_iHungerCountdown -= 2;
			}

			if ( m_iHungerCountdown <= 0 )
			{
				if ( IsFullyFed() )
				{
					OnBecomeFamished();
				}
				else if ( IsFamished() )
				{
					OnBecomeStarving();
				}
				else // starving
				{
					if (!isChild()) //if adult wolf, normal starve
					{	
						OnStarvingCountExpired();
					}
					else
					{
						//children/puppies starve extremely quickly
						attackEntityFrom( DamageSource.starve, 1 );
					}
				}

				ResetHungerCountdown();
			}
		}
		
		UpdateShitState();
	}

	@Override
	protected void OnStarvingCountExpired()
	{
		super.OnStarvingCountExpired();

		if ( isEntityAlive() && !IsFullyPossessed() )
		{
			// Turn tamed wolves wild and hostile when they've been starving for too long
			// fully possessed wolves don't enter the angry state so as to not risk appearing 
			// hostile but otherwise behave the same so as to not attract undue attention

			setAngry( true );

			setTamed( false );		            
			setOwner( "" );
		}		
	}

	@Override
	protected void ResetHungerCountdown()
	{
		m_iHungerCountdown = m_iFullHungerCount + ( rand.nextInt( 
				m_iHungerCountVariance * 2 ) - m_iHungerCountVariance ); 
	}

	@Override
	protected float GetHungerSpeedModifier()
	{
		// wolves don't slow down when they get hungry... they just get mad

		return 1F;
	}

	@Override
	protected boolean IsTooHungryToGrow()
	{
		return !IsFullyFed();    	
	}

	@Override
	protected boolean IsTooHungryToHeal()
	{
		// handles own healing independent of hunger

		return true; 
	}

	@Override
	protected int GetItemFoodValue( ItemStack stack )
	{
		// class processes its own food values, so this prevents EntityAnimal from treating
		// it like a herbivore.

		return 0;
	}
	//	Hiracho TODO decide if to remove this entirely(including the EntityWolf override )
	@Override
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}

		else if (!(par1EntityAnimal instanceof EntityWolf))
		{
			return false;
		}

		else
		{
			EntityWolf var2 = (EntityWolf)par1EntityAnimal;
			return  var2.isSitting() ? false : this.isInLove() && par1EntityAnimal.isInLove();
		}
	}
	//------------- Class Specific Methods ------------//

	public boolean IsEngangedInPossessionAttempt()    
	{
		return dataWatcher.getWatchableObjectByte( m_iIsEngagedInPossessionAttemptDataWatcherID ) > 0;
	}

	public void SetEngangedInPossessionAttempt( boolean bIsEngaged )
	{
		Byte tempByte = 0;

		if ( bIsEngaged )
		{
			tempByte = 1;
		}

		dataWatcher.updateObject( m_iIsEngagedInPossessionAttemptDataWatcherID, Byte.valueOf( tempByte ) );
	}

	private void UpdateShitState()
	{
		if ( IsFullyFed() )
		{
			int chanceOfShitting = 1;

			if ( IsDarkEnoughToAffectShitting() )
			{
				chanceOfShitting *= 2;
			}

			// a wolf shits on average every 20 minutes if in the light
			if ( worldObj.rand.nextInt( 24000 ) < chanceOfShitting )
			{
				AttemptToShit();
			}
		}        
	}

	private boolean AttemptUseStackOn( EntityPlayer player, ItemStack playerStack )
	{
		if ( isTamed() )
		{
			if ( playerStack.itemID == Item.dyePowder.itemID )
			{
				int iNewColor = BlockCloth.getBlockFromDye( playerStack.getItemDamage() );

				if ( iNewColor != getCollarColor() )
				{
					setCollarColor( iNewColor );

					return true;
				}
			}
			else if ( playerStack.itemID == FCBetterThanWolves.fcItemDung.itemID )
			{
				int iNewColor = 12;

				if ( iNewColor != getCollarColor() )
				{
					setCollarColor( iNewColor );

					if ( !worldObj.isRemote )
					{
						worldObj.playAuxSFX( FCBetterThanWolves.m_iDungAppliedToWolfAuxFXID, 
								MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), 
								MathHelper.floor_double( posZ ), 0 );
					}

					return true;
				}
			}
		}
		else // !isTamed()
		{
			//AARON added the rib as an option
			if (  (playerStack.itemID == Item.bone.itemID || playerStack.itemID == SuperBTWDefinitions.rib.itemID) && !IsWildAndHostile() )
			{
				if ( !worldObj.isRemote )
				{
					if ( rand.nextInt( 3 ) == 0 )
					{
						setTamed( true );

						setPathToEntity( null );
						setAttackTarget( null );
						aiSit.setSitting( true );
						setEntityHealth( 20 );
						setOwner( player.username );
						playTameEffect( true );

						worldObj.setEntityState( this, (byte)7 );
					}
					else
					{
						playTameEffect( false );

						worldObj.setEntityState( this, (byte)6 );
					}
				}

				return true;
			}
		}

		return false;
	}

	private void OnEat( Item food )
	{
		heal( food.GetWolfHealAmount() );

		int iHungerLevel = GetHungerLevel();

		if ( iHungerLevel > 0 )
		{
			SetHungerLevel( iHungerLevel - 1 );
		}

		ResetHungerCountdown();

		if ( !worldObj.isRemote )
		{
			worldObj.playAuxSFX( FCBetterThanWolves.m_iWolfEatAuxFXID,	                
					MathHelper.floor_double( posX ), (int)( posY + height ),
					MathHelper.floor_double( posZ ), 0 );        	
		}
		//		Hiracho: Added
		//AARON added treat
		if ( food.itemID == FCBetterThanWolves.fcItemRawMysteryMeat.itemID ||food.itemID == FCBetterThanWolves.fcItemCookedMysteryMeat.itemID ||food.itemID == SuperBTWDefinitions.treat.itemID )
		{
			OnEatBreedingItem();
		}
		if ( food.itemID == Item.rottenFlesh.itemID )
		{
			OnRottenFleshEaten();
		}
		else if ( food.itemID == FCBetterThanWolves.fcItemChocolate.itemID )
		{
			OnChocolateEaten();
		}
	}

	private void OnChocolateEaten()
	{
		if ( !worldObj.isRemote )
		{
			addPotionEffect( new PotionEffect( Potion.wither.id, 40 * 20, 0 ) );

			worldObj.setEntityState(this, (byte)11);
		}
	}

	private void OnRottenFleshEaten()
	{
		if (m_iInfectionCountdown < 0 )
		{
			m_iInfectionCountdown = m_iMinimumInfectionTime + rand.nextInt( m_iInfectionTimeVariance );
		}
	}

	public boolean IsDarkEnoughToAffectShitting()
	{
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		int lightValue = worldObj.getBlockLightValue( i, j, k );

		if ( lightValue <= 5 )
		{
			return true;
		}

		return false;
	}

	public boolean AttemptToShit()
	{
		float poopVectorX = MathHelper.sin((rotationYawHead / 180F) * (float)Math.PI); 

		float poopVectorZ = -MathHelper.cos((rotationYawHead / 180F) * (float)Math.PI); 

		double shitPosX = posX + poopVectorX;
		double shitPosY = posY + 0.25D;
		double shitPosZ = posZ + poopVectorZ;

		int shitPosI = MathHelper.floor_double( shitPosX );
		int shitPosJ = MathHelper.floor_double( shitPosY );
		int shitPosK = MathHelper.floor_double( shitPosZ );

		if ( !IsPathToBlockOpenToShitting( shitPosI, shitPosJ, shitPosK ) )
		{
			return false;
		}

		EntityItem entityitem = new EntityItem( worldObj, 
				shitPosX, shitPosY, shitPosZ, 
				new ItemStack( FCBetterThanWolves.fcItemDung ) );

		float velocityFactor = 0.05F;

		entityitem.motionX = poopVectorX * 10.0f * velocityFactor;

		entityitem.motionZ = poopVectorZ * 10.0f * velocityFactor;

		entityitem.motionY = (float)worldObj.rand.nextGaussian() * velocityFactor + 0.2F;

		entityitem.delayBeforeCanPickup = 10;

		worldObj.spawnEntityInWorld( entityitem );

		worldObj.playSoundAtEntity( this, "random.explode", 0.2F, 1.25F );

		worldObj.playSoundAtEntity(this, "mob.wolf.growl", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

		// emit smoke

		for ( int counter = 0; counter < 5; counter++ )
		{
			double smokeX = posX + ( poopVectorX * 0.5f ) + ( worldObj.rand.nextDouble() * 0.25F );
			double smokeY = posY + worldObj.rand.nextDouble() * 0.5F + 0.25F;
			double smokeZ = posZ + ( poopVectorZ * 0.5f ) + ( worldObj.rand.nextDouble() * 0.25F );

			worldObj.spawnParticle( "smoke", smokeX, smokeY, smokeZ, 0.0D, 0.0D, 0.0D );
		}

		return true;
	}

	private boolean IsPathToBlockOpenToShitting( int i, int j, int k )
	{
		if ( !IsBlockOpenToShitting( i, j, k ) )
		{
			return false;
		}

		int wolfI = MathHelper.floor_double( posX );
		int wolfK = MathHelper.floor_double( posZ );

		int deltaI = i - wolfI;
		int deltaK = k - wolfK;

		if ( deltaI != 0 && deltaK != 0 )
		{
			// we're pooping on a diagnal.  Test to make sure that we're not warping poop through blocked off corners

			if ( !IsBlockOpenToShitting( wolfI, j, k ) && !IsBlockOpenToShitting( i, j, wolfK ) )
			{
				return false;
			}
		}

		return true;
	}

	private boolean IsBlockOpenToShitting( int i, int j, int k )
	{
		Block block = Block.blocksList[worldObj.getBlockId( i, j, k )];

		if ( block != null && ( block == Block.waterMoving || block == Block.waterStill || block == Block.lavaMoving || block == Block.lavaStill || block == Block.fire || block.blockMaterial.isReplaceable() ||
				block == FCBetterThanWolves.fcBlockDetectorLogic || block == FCBetterThanWolves.fcBlockDetectorGlowingLogic|| 
				block == FCBetterThanWolves.fcBlockFireStoked ) )
		{
			block = null;
		}

		if ( block != null )
		{
			return false;
		}        

		return true;
	}

	private void TransformToDire()
	{
		int iFXI = MathHelper.floor_double( posX );
		int iFXJ = MathHelper.floor_double( posY ) + 1;
		int iFXK = MathHelper.floor_double( posZ );

		worldObj.func_82739_e( FCBetterThanWolves.m_iWolfConvertToDireAuxFXID, iFXI, iFXJ, iFXK, 0 );

		setDead();

		FCEntityWolfDire direWolf = new FCEntityWolfDire( worldObj );

		direWolf.setLocationAndAngles( posX, posY, posZ, rotationYaw, rotationPitch );
		direWolf.renderYawOffset = renderYawOffset;

		worldObj.spawnEntityInWorld( direWolf );
	}

	public boolean IsWildAndHostile()
	{
		if ( !isTamed() )
		{
			if ( IsStarving() || isAngry() )
			{
				// wild wolves always hostile when starving

				return true;
			}
			else
			{
				// check if night and full moon

				int iTimeOfDay = (int)( worldObj.worldInfo.getWorldTime() % 24000L );

				if ( iTimeOfDay > 13500 && iTimeOfDay < 22500 )
				{
					int iMoonPhase = worldObj.getMoonPhase();

					if ( iMoonPhase == 0 && worldObj.worldInfo.getWorldTime() > 24000L ) // full moon, and not the first one of the game
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean IsWildAndHungry()
	{
		if ( !isTamed() )
		{
			if ( !IsFullyFed() )
			{
				return true;
			}
		}

		return false;
	}

	public boolean IsWildAndStarving()
	{
		if ( !isTamed() )
		{
			if ( IsStarving() )
			{
				return true;
			}
		}

		return false;
	}

	public float GetGrazeHeadVerticalOffset(float par1)
	{
		if ( m_iHowlingCountdown > 0 )
		{
			float fTiltFraction = 1F;

			if ( m_iHowlingCountdown < 5 )
			{
				fTiltFraction = (float)m_iHowlingCountdown / 5F;
			}
			else if (  m_iHowlingCountdown > FCEntityAIWolfHowl.m_iHowlDuration - 10 )
			{
				fTiltFraction = (float)( FCEntityAIWolfHowl.m_iHowlDuration + 1 - m_iHowlingCountdown ) / 10F;
			}

			if ( !isSitting() )
			{
				return fTiltFraction * -0.5F;
			}
			else
			{
				return fTiltFraction * -0.25F;
			}
		}

		return 0F;    	
	}

	public float GetGrazeHeadRotation(float par1)
	{
		if ( m_iHowlingCountdown > 0 )
		{
			float fTiltFraction = 1F;

			if ( m_iHowlingCountdown < 5 )
			{
				fTiltFraction = (float)m_iHowlingCountdown / 5F;
			}
			else if (  m_iHowlingCountdown > FCEntityAIWolfHowl.m_iHowlDuration - 10 )
			{
				fTiltFraction = (float)( FCEntityAIWolfHowl.m_iHowlDuration + 1 - m_iHowlingCountdown ) / 10F;
			}    			

			return fTiltFraction * -((float)Math.PI / 5F);
		}
		else
		{
			return rotationPitch / (180F / (float)Math.PI);
		}    	
	}

	public boolean AreEyesGlowing()
	{
		return m_bIsDoingHeadSpin;
	}

	public float GetPossessionHeadRotation()
	{
		return m_iPossessionHeadRotation * 2F * (float)Math.PI;
	}

	private void UpdateHeadSpin()
	{
		if ( m_bIsDoingHeadSpin )
		{
			m_iPossessionHeadRotation += 0.008F;

			if ( m_iPossessionHeadRotation >= 1.0F )
			{
				m_iPossessionHeadRotation = 0F;

				m_bIsDoingHeadSpin = false;
			}
		}
	}

	//----------- Client Side Functionality -----------//

	@Override
	public String getTexture()
	{
		if ( isTamed() )
		{
			if ( IsStarving() )
			{
				return "/btwmodtex/fcWolf_tame_starving.png";
			}

			return "/mob/wolf_tame.png";
		}
		else if ( isAngry() )
		{
			return "/mob/wolf_angry.png";
		}
		else if ( IsStarving() )
		{
			return "/btwmodtex/fcWolf_wild_starving.png"; 
		}

		return texture; // intentionally bypass super method
	}

	@Override
	public void handleHealthUpdate( byte bUpdateType )
	{
		if ( bUpdateType == 10 )
		{
			m_iHowlingCountdown = FCEntityAIWolfHowl.m_iHowlDuration;
		}
		else if ( bUpdateType == 11 )
		{
			addPotionEffect( new PotionEffect( Potion.wither.id, 40 * 20, 0 ) );
		}
		else
		{    	
			super.handleHealthUpdate(bUpdateType);
		}
	}

	@Override
	public float getTailRotation()
	{
		if ( IsWildAndHostile() )
		{
			return 1.5393804F;
		}
		else if ( isTamed() )
		{
			return ( 0.55F - (float)( 20 - this.dataWatcher.getWatchableObjectInt( 18 ) ) * 
					0.02F ) * (float)Math.PI;
		}

		return (float)Math.PI / 5F;
	}
}


//// FCMOD
//
//package net.minecraft.src;
//
//import java.util.Iterator;
//import java.util.List;
//
//public class FCEntityWolf extends EntityWolf
//{
//	private static final int m_iIsEngagedInPossessionAttemptDataWatcherID = 26;
//
//	private static final float m_fMoveSpeedAggressive = 0.45F;
//	private static final float m_fMoveSpeedPassive = 0.3F;    
//
//	public int m_iHowlingCountdown = 0;
//
//	public int m_iHeardHowlCountdown = 0;
//
//	public int m_iInfectionCountdown = -1;
//
//	private static final int m_iMinimumInfectionTime = 12000;
//	private static final int m_iInfectionTimeVariance = 12000;
//
//	private float m_iPossessionHeadRotation = 0F;
//	private boolean m_bIsDoingHeadSpin = false;
//	private boolean m_bHasHeadSpunOnThisPossessionAttempt = false;
//	private int m_iPossessionAttemptCountdown = 0;
//
//	private static final int m_iChanceOfPossessionAttempt = ( FCUtilsMisc.m_iTicksPerMinute * 10 );
//	private static final int m_iPossessionAttemptTime = ( FCUtilsMisc.m_iTicksPerSecond * 10 );
//
//	protected static final int m_iHungerCountVariance = ( FCUtilsMisc.m_iTicksPerMinute );
//
//	public FCEntityWolf( World world )
//	{
//		super( world );
//
//		moveSpeed = m_fMoveSpeedAggressive;
//
//		tasks.RemoveAllTasks();
//
//		tasks.addTask( 1, new EntityAISwimming( this ) );
//		tasks.addTask( 2, new FCEntityAIPanicOnHeadCrab( this, m_fMoveSpeedAggressive ) );
//		tasks.addTask( 3, aiSit );
//		tasks.addTask( 4, new EntityAILeapAtTarget( this, 0.4F ) );
//		// FCTODO: Should the following be passive move speed or agressive?
//		tasks.addTask( 5, new EntityAIAttackOnCollide( this, m_fMoveSpeedPassive, true ) );
//		tasks.addTask( 6, new EntityAIFollowOwner( this, m_fMoveSpeedPassive, 10F, 2F ) );
//		tasks.addTask( 7, new EntityAIMate( this, m_fMoveSpeedPassive ) );
////		Hiracho: added multitempt //AARON added for Hirachosan
//		tasks.addTask( 7, new FCEntityAIMultiTempt( this, m_fMoveSpeedPassive ) ); //this makes the animals follow when holding breeding item
//		
//		tasks.addTask( 8, new FCEntityAIWolfHowl( this ) );
//		tasks.addTask( 8, new FCEntityAIWolfHowlSitting( this ) );
//		tasks.addTask( 9, new FCEntityAIMoveToLooseFood( this, 0.23F ) );
//		tasks.addTask( 10, new FCEntityAIWanderSimple( this, m_fMoveSpeedPassive ) );
//		tasks.addTask( 11, new EntityAIBeg( this, 8F ) );
//		tasks.addTask( 12, new EntityAIWatchClosest( this, EntityPlayer.class, 8F ) );
//		tasks.addTask( 13, new EntityAILookIdle( this ) );        
//
//		targetTasks.RemoveAllTasks();
//
//		targetTasks.addTask( 1, new EntityAIOwnerHurtByTarget( this ) );
//		targetTasks.addTask( 2, new EntityAIOwnerHurtTarget( this ) );
//		targetTasks.addTask( 3, new EntityAIHurtByTarget( this, true ) );
//
//		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfStarvingOrHostile( this, 
//				FCEntityVillager.class, 16F, 0, false ) );
//
//		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfStarvingOrHostile( this, 
//				EntityPlayer.class, 16F, 0, false ) );
//
//		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfHungry( this, 
//				FCEntityChicken.class, 16F, 0, false ) );
//
//		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfHungry( this, 
//				FCEntitySheep.class, 16F, 0, false ) );
//
//		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfHungry( this, 
//				FCEntityPig.class, 16F, 0, false ) );
//
//		targetTasks.addTask( 4, new FCEntityAIWolfWildTargetIfStarving( this, 
//				FCEntityCow.class, 16F, 0, false ) );        
//	}
//	
//	@Override
//	//AARON stole from Dawn and Hirachosan for happy breeding! Excitement
//	public void onKillEntity( EntityLiving entityKilled )
//	{
//		//    	On kill player
//		if (entityKilled instanceof EntityPlayer)
//		{
//			int breedableWolf = 0;
//			int maxWolvesFed = 4;
//			//    		set breeding self
//			if (this.IsReadyToEatBreedingItem())
//			{
//				this.OnEatBreedingItem();
//				breedableWolf++;
//			}
//			//        	Get entities in certain range
//			List nearbyEntityList = worldObj.getEntitiesWithinAABBExcludingEntity( this, 
//					boundingBox.expand( 16D, 8D, 16D ) );
//			Iterator nearbyEntityIterator = nearbyEntityList.iterator();
//			//              set a max to number of wolves being fed breedingitem
//			while ( nearbyEntityIterator.hasNext() && breedableWolf <=maxWolvesFed)
//			{
//				Entity nearbyEntity = (Entity)nearbyEntityIterator.next();
//				//                	check entities for being wolf
//				if ( nearbyEntity instanceof FCEntityWolf )
//				{
//					FCEntityWolf nearbyWolf = (FCEntityWolf)nearbyEntity;
//					//                        only go into breeding mode when able to
//					if (nearbyWolf.IsReadyToEatBreedingItem())
//					{
//						nearbyWolf.OnEatBreedingItem();
//						breedableWolf++;
//					}
//				}
//			}
//		}
//	}
//
//	@Override
//	public void setAttackTarget( EntityLiving target )
//	{
//		EntityLivingSetAttackTarget( target ); // bypass parent
//	}
//
//	@Override
//	public int getMaxHealth()
//	{
//		return 20;
//	}    
//
//	@Override
//	protected void entityInit()
//	{
//		super.entityInit();
//
//		dataWatcher.addObject( m_iIsEngagedInPossessionAttemptDataWatcherID, new Byte( (byte)0 ) );
//
//		ResetHungerCountdown();
//	}
//
//	@Override
//	public void writeEntityToNBT( NBTTagCompound tag )
//	{
//		super.writeEntityToNBT(tag);
//
//		tag.setInteger( "fcInfection", m_iInfectionCountdown );        
//	}
//
//	@Override
//	public void readEntityFromNBT( NBTTagCompound tag )
//	{
//		super.readEntityFromNBT( tag );
//
//		if ( tag.hasKey( "bIsFed" ) )
//		{
//			boolean bIsFed = tag.getBoolean( "bIsFed" );
//
//			if ( bIsFed )
//			{
//				SetHungerLevel( 0 );
//			}
//			else
//			{
//				SetHungerLevel( 1 );
//			}
//		}
//
//		if ( tag.hasKey( "fcInfection" ) )
//		{
//			m_iInfectionCountdown = tag.getInteger( "fcInfection" );
//		}
//	}
//
//	@Override
//	protected boolean canDespawn()
//	{
//		return false;
//	}
//
//	@Override
//	protected String getLivingSound()
//	{
//		if ( IsWildAndHostile() )
//		{
//			return "mob.wolf.growl";
//		}
//		else if ( this.rand.nextInt(3) == 0 )
//		{
//			if ( isTamed() && ( dataWatcher.getWatchableObjectInt(18) < 10 || !IsFullyFed() ) )
//			{
//				if ( IsStarving() )
//				{
//					return "mob.wolf.growl";
//				}
//				else
//				{
//					return "mob.wolf.whine";
//				}
//			}
//			else
//			{
//				return "mob.wolf.panting";
//			}
//		}
//		else
//		{
//			return "mob.wolf.bark";
//		}        
//	}
//
//	@Override
//	protected int getDropItemId()
//	{
//		if ( !worldObj.isRemote )
//		{
//			// yummy yummy wolfchops
//
//			if ( isBurning() )
//			{
//				return FCBetterThanWolves.fcItemMeatBurned.itemID;
//			} 
//			else
//			{            
//				return FCBetterThanWolves.fcItemWolfRaw.itemID;
//			}
//		}
//
//		return -1;
//	}
//
//	@Override
//	public void onLivingUpdate()
//	{
//		super.onLivingUpdate();
//
//		if ( worldObj.isRemote )
//		{
//			m_iHowlingCountdown = Math.max( 0, m_iHowlingCountdown - 1 );
//		}
//		else
//		{
//			m_iHeardHowlCountdown = Math.max( 0, m_iHeardHowlCountdown - 1 );
//
//			if ( m_iInfectionCountdown > 0 )
//			{
//				m_iInfectionCountdown--;
//
//				if ( m_iInfectionCountdown <= 0 )
//				{
//					TransformToDire();
//
//					return; // entity is no longer valid after transformation
//				}
//			}
//		}
//	}
//
//	@Override
//	public boolean attackEntityFrom( DamageSource source, int iDamageAmount )
//	{
//		if ( !isEntityInvulnerable() && !worldObj.isRemote && !isTamed() )
//		{
//			if ( source.getEntity() instanceof EntityPlayer )
//			{
//				// When wild wolves are attacked by a player they turn permanently hostile
//
//				setAngry( true );
//			}           
//		}
//
//		return super.attackEntityFrom(source, iDamageAmount);
//	}
//
//	@Override
//	public int GetMeleeAttackStrength( Entity target )
//	{
//		return 4;
//	}
//
//	@Override
//	public boolean attackEntityAsMob( Entity target )
//	{
//		// override parent, so wild and tame wolves do the same damage
//
//		return MeleeAttack( target );
//	}
//
//	@Override
//	public boolean interact( EntityPlayer player )
//	{
//		ItemStack playerStack = player.inventory.getCurrentItem();
//
//		if ( playerStack != null )
//		{
//			if (playerStack.itemID == FCBetterThanWolves.fcItemWolfRaw.itemID || playerStack.itemID == FCBetterThanWolves.fcItemWolfCooked.itemID) {
//				// turn wolves hostile when you try to feed them wolf meat
//
//				worldObj.playSoundAtEntity(this, "mob.wolf.growl", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//
//				setAngry(true);
//				setTamed(false);
//				setOwner("");
//
//				setAttackTarget( player );
//
//				return true;
//			}
//
//			if ( AttemptUseStackOn( player, playerStack ) )
//			{
//				if ( !player.capabilities.isCreativeMode )
//				{
//					playerStack.stackSize--;
//
//					if ( playerStack.stackSize <= 0 )
//					{
//						player.inventory.setInventorySlotContents(
//								player.inventory.currentItem, null );
//					}
//				}
//
//				return true;
//			}
//		}
//		
//		//AARON changed this as per Hirachosan in order to allow wolf breeding even if untamed
////		if ( !EntityAnimalInteract( player ) ) // intentionally bypass parent
////		{
////			if (!worldObj.isRemote && isTamed() && player.username.equalsIgnoreCase(getOwnerName()) && (playerStack == null || !IsEdibleItem(playerStack) || !isBreedingItem(playerStack)))
////			{
////				aiSit.setSitting( !isSitting() );
////				isJumping = false;
////				setPathToEntity( null );
////			}
////
////			return false;
////		}
////
////		return true;
//		
//		if ( EntityAnimalInteract( player ) ) 
//			return true;
//		
//		if (!worldObj.isRemote && isTamed() && player.username.equalsIgnoreCase(getOwnerName()) && (playerStack == null || !IsEdibleItem(playerStack)  || !isBreedingItem(playerStack) ))
//		{
//			aiSit.setSitting( !isSitting() );
//			isJumping = false;
//			setPathToEntity( null );
//		}
//		
//		return false;	
//	}
//
//	@Override
//	public boolean GetCanBeHeadCrabbed( boolean bSquidInWater )
//	{
//		// can only head crabbed when they randomly collide with a squid out of water, 
//		// but are never actually targetted
//
//		return !bSquidInWater && riddenByEntity == null && isEntityAlive() && !isChild();
//	}
//
//	@Override
//	public double getMountedYOffset()
//	{
//		return (double)height * 1.2D;
//	}
//
//	@Override
//	public boolean isBreedingItem(ItemStack stack) {
//		if (stack != null && isTamed()) {
//			Item item = stack.getItem();
//			
//			//AARON followed Hirachosan and made mystery meat the breeding item for wolves
//			//TODO: Make a more automatable breeding item (besides mystery meat) for an eventual puppy mill update
////			return stack.itemID == FCBetterThanWolves.fcItemDogFood.itemID;
//			return stack.itemID == FCBetterThanWolves.fcItemRawMysteryMeat.itemID || stack.itemID == FCBetterThanWolves.fcItemCookedMysteryMeat.itemID; 
//
//		}
//
//		return false;
//	}
//
//	@Override
//	public void setAngry( boolean bAngry )
//	{
//		super.setAngry( bAngry );
//
//		if ( bAngry )
//		{
//			setSitting(false);
//		}
//	}
//	
//	//AARON added a parameter to this class
//	public boolean IsReadyToEatLooseFood(ItemStack item)
//	{
//		// only eat loose food if the wolf is not fed.  Don't do it if they are just 
//		// wounded like by hand, nor do it for breeding like with other animals.
//
//		//AARON added the breeding items to this, so they will breed regardless of hunger
//		return !IsFullyFed() || (IsReadyToEatBreedingItem() && isBreedingItem(item)); 
//		//the breeding item check prevents over eating in general
//	}
//
//	@Override
//	public boolean IsReadyToEatLooseItem( ItemStack stack )
//	{
//		Item tempItem = stack.getItem();
//		
//		//AARON's new method at work
//		if ( IsReadyToEatLooseFood(stack) && tempItem.IsWolfFood() )
//		{
//			// only eat rotten flesh off the ground when starving
//
//			if ( tempItem.itemID != Item.rottenFlesh.itemID || IsStarving() )
//			{
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	@Override
//	protected boolean AttemptToEatLooseItem( ItemStack stack )
//	{
//		if ( IsReadyToEatLooseItem( stack ) )
//		{
//			OnEat( stack.getItem() );
//
//			return true;
//		}
//
//		return false;
//	}
//
//	@Override
//	public boolean IsEdibleItem( ItemStack stack )
//	{
//		return stack.getItem().IsWolfFood();
//	}
//
//	public boolean AttemptToBeHandFedItem(ItemStack stack) {
////		if (isTamed()) { //AARON removed the owner check. Ownerless creatures can be fed!
//			if (dataWatcher.getWatchableObjectInt(18) < 20 || !IsFullyFed() || /*AARON stole this extra bit*/ (isBreedingItem(stack) &&IsReadyToEatBreedingItem())) {
//				this.OnEat(stack.getItem());
//				return true;
//			}
//			//AAROn commented this out
////			else if (isBreedingItem(stack) &&IsReadyToEatBreedingItem() ) {
////				this.OnEatBreedingItem();
////				return true;
////			}
////		}
//
//		return false;
//	}
//
//	@Override
//	public void OnNearbyPlayerBlockAddOrRemove( EntityPlayer player )
//	{
//		// do nothing so that the player isn't attacked by wolves every time they place or remove a block
//	}
//
//	@Override
//	protected void OnNearbyPlayerStartles( EntityPlayer player )
//	{
//		if ( !isTamed() )
//		{
//			if ( getAttackTarget() == null )
//			{
//				setAttackTarget( player );
//			}
//		}
//	}
//
//	@Override
//	protected boolean GetCanCreatureTypeBePossessed()
//	{
//		return true;
//	}
//
//	@Override
//	protected void HandlePossession()
//	{
//		super.HandlePossession();
//
//		if ( IsFullyPossessed() )
//		{
//			if ( IsEngangedInPossessionAttempt() )
//			{
//				if ( !m_bHasHeadSpunOnThisPossessionAttempt )
//				{
//					m_bHasHeadSpunOnThisPossessionAttempt = true;
//
//					m_bIsDoingHeadSpin = true;
//
//					if ( !worldObj.isRemote )
//					{
//						playSound( "portal.portal", 3.0F, rand.nextFloat() * 0.1F + 0.75F );
//					}
//				}
//
//				if ( !worldObj.isRemote )
//				{    				
//					m_iPossessionAttemptCountdown--;
//
//					if ( m_iPossessionAttemptCountdown <= 0 )
//					{
//						SetEngangedInPossessionAttempt( false );
//
//						AttemptToPossessNearbyCreature( 16D, false );
//					}
//				}
//			}
//			else
//			{
//				m_bHasHeadSpunOnThisPossessionAttempt = false;
//
//				if ( !worldObj.isRemote )
//				{
//					if ( rand.nextInt( m_iChanceOfPossessionAttempt ) == 0 )
//					{
//						SetEngangedInPossessionAttempt( true );
//
//						m_iPossessionAttemptCountdown = m_iPossessionAttemptTime;
//					}
//				}
//			}
//
//			UpdateHeadSpin();
//		}
//	}
//
//	@Override
//	public void OnNearbyAnimalAttacked( EntityAnimal attackedAnimal, EntityLiving attackSource )
//	{
//		// wolves and ocelots don't give a shit if a nearby animal is attacked
//	}
//
//	@Override
//	public void NotifyOfWolfHowl( Entity sourceEntity )
//	{
//		if ( !isLivingDead )
//		{
//			double dDeltaX = posX - sourceEntity.posX;
//			double dDeltaZ = posZ - sourceEntity.posZ;
//
//			double dDistSq = dDeltaX * dDeltaX + dDeltaZ * dDeltaZ;
//
//			if ( dDistSq < FCEntityAIWolfHowl.m_dHearHowlDistanceSQ )
//			{
//				if ( this != sourceEntity )
//				{
//					m_iHeardHowlCountdown = FCEntityAIWolfHowl.m_iHeardHowlDuration;
//				}
//			}
//		}
//	}    
//
//	@Override
//	public FCEntityWolf spawnBabyAnimal( EntityAgeable parent )
//	{
//		FCEntityWolf baby = new FCEntityWolf( worldObj );
//		
//		//AARON commented this out so that baby wolfs spawn untamed
////		String sOwner = getOwnerName();
////
////		if ( sOwner != null && sOwner.trim().length() > 0 )
////		{
////			baby.setOwner( sOwner );
////			baby.setTamed( true );
////		}
//		
//		baby.setTamed( false );
//		
//		return baby;
//	}
//
//	@Override
//	public boolean IsSubjectToHunger()
//	{
//		return true;
//	}
//
//	@Override
//	protected void UpdateHungerState()
//	{
//		super.UpdateHungerState();
//
//		UpdateShitState();
//	}
//
//	@Override
//	protected void OnStarvingCountExpired()
//	{
//		super.OnStarvingCountExpired();
//
//		if ( isEntityAlive() && !IsFullyPossessed() )
//		{
//			// Turn tamed wolves wild and hostile when they've been starving for too long
//			// fully possessed wolves don't enter the angry state so as to not risk appearing 
//			// hostile but otherwise behave the same so as to not attract undue attention
//
//			setAngry( true );
//
//			setTamed( false );		            
//			setOwner( "" );
//		}		
//	}
//
//	@Override
//	protected void ResetHungerCountdown()
//	{
//		m_iHungerCountdown = m_iFullHungerCount + ( rand.nextInt( 
//				m_iHungerCountVariance * 2 ) - m_iHungerCountVariance ); 
//	}
//
//	@Override
//	protected float GetHungerSpeedModifier()
//	{
//		// wolves don't slow down when they get hungry... they just get mad
//
//		return 1F;
//	}
//
//	@Override
//	protected boolean IsTooHungryToGrow()
//	{
//		return !IsFullyFed();    	
//	}
//
//	@Override
//	protected boolean IsTooHungryToHeal()
//	{
//		// handles own healing independent of hunger
//
//		return true; 
//	}
//
//	@Override
//	protected int GetItemFoodValue( ItemStack stack )
//	{
//		// class processes its own food values, so this prevents EntityAnimal from treating
//		// it like a herbivore.
//
//		return 0;
//	}
//	
////	Hiracho TODO decide if to remove this entirely(including the EntityWolf override )
//	//AARON stole from Hirachosan <3
//	@Override
//    public boolean canMateWith(EntityAnimal par1EntityAnimal)
//    {
//        if (par1EntityAnimal == this)
//        {
//            return false;
//        }
//
//        else if (!(par1EntityAnimal instanceof EntityWolf))
//        {
//            return false;
//        }
//
//        else
//        {
//            EntityWolf var2 = (EntityWolf)par1EntityAnimal;
//            return  var2.isSitting() ? false : this.isInLove() && par1EntityAnimal.isInLove();
//        }
//    }
//
//	//------------- Class Specific Methods ------------//
//
//	public boolean IsEngangedInPossessionAttempt()    
//	{
//		return dataWatcher.getWatchableObjectByte( m_iIsEngagedInPossessionAttemptDataWatcherID ) > 0;
//	}
//
//	public void SetEngangedInPossessionAttempt( boolean bIsEngaged )
//	{
//		Byte tempByte = 0;
//
//		if ( bIsEngaged )
//		{
//			tempByte = 1;
//		}
//
//		dataWatcher.updateObject( m_iIsEngagedInPossessionAttemptDataWatcherID, Byte.valueOf( tempByte ) );
//	}
//
//	private void UpdateShitState()
//	{
//		if ( IsFullyFed() )
//		{
//			int chanceOfShitting = 1;
//
//			if ( IsDarkEnoughToAffectShitting() )
//			{
//				chanceOfShitting *= 2;
//			}
//
//			// a wolf shits on average every 20 minutes if in the light
//			if ( worldObj.rand.nextInt( 24000 ) < chanceOfShitting )
//			{
//				AttemptToShit();
//			}
//		}        
//	}
//
//	private boolean AttemptUseStackOn( EntityPlayer player, ItemStack playerStack )
//	{
//		if ( isTamed() )
//		{
//			if ( playerStack.itemID == Item.dyePowder.itemID )
//			{
//				int iNewColor = BlockCloth.getBlockFromDye( playerStack.getItemDamage() );
//
//				if ( iNewColor != getCollarColor() )
//				{
//					setCollarColor( iNewColor );
//
//					return true;
//				}
//			}
//			else if ( playerStack.itemID == FCBetterThanWolves.fcItemDung.itemID )
//			{
//				int iNewColor = 12;
//
//				if ( iNewColor != getCollarColor() )
//				{
//					setCollarColor( iNewColor );
//
//					if ( !worldObj.isRemote )
//					{
//						worldObj.playAuxSFX( FCBetterThanWolves.m_iDungAppliedToWolfAuxFXID, 
//								MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), 
//								MathHelper.floor_double( posZ ), 0 );
//					}
//
//					return true;
//				}
//			}
//		}
//		else // !isTamed()
//		{
//			if (  playerStack.itemID == Item.bone.itemID && !IsWildAndHostile() )
//			{
//				if ( !worldObj.isRemote )
//				{
//					if ( rand.nextInt( 3 ) == 0 )
//					{
//						setTamed( true );
//
//						setPathToEntity( null );
//						setAttackTarget( null );
//						aiSit.setSitting( true );
//						setEntityHealth( 20 );
//						setOwner( player.username );
//						playTameEffect( true );
//
//						worldObj.setEntityState( this, (byte)7 );
//					}
//					else
//					{
//						playTameEffect( false );
//
//						worldObj.setEntityState( this, (byte)6 );
//					}
//				}
//
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	private void OnEat( Item food )
//	{
//		heal( food.GetWolfHealAmount() );
//
//		int iHungerLevel = GetHungerLevel();
//
//		if ( iHungerLevel > 0 )
//		{
//			SetHungerLevel( iHungerLevel - 1 );
//		}
//
//		ResetHungerCountdown();
//
//		if ( !worldObj.isRemote )
//		{
//			worldObj.playAuxSFX( FCBetterThanWolves.m_iWolfEatAuxFXID,	                
//					MathHelper.floor_double( posX ), (int)( posY + height ),
//					MathHelper.floor_double( posZ ), 0 );        	
//		}
//		
//		//AARON stole from hirachosan, eating now handles breeding
//		//BREEDING ITEMS listed below
//		if ( food.itemID == FCBetterThanWolves.fcItemRawMysteryMeat.itemID ||food.itemID == FCBetterThanWolves.fcItemCookedMysteryMeat.itemID )
//		{
//			OnEatBreedingItem();
//		}
//
//		if ( food.itemID == Item.rottenFlesh.itemID )
//		{
//			OnRottenFleshEaten();
//		}
//		else if ( food.itemID == FCBetterThanWolves.fcItemChocolate.itemID )
//		{
//			OnChocolateEaten();
//		}
//	}
//
//	private void OnChocolateEaten()
//	{
//		if ( !worldObj.isRemote )
//		{
//			addPotionEffect( new PotionEffect( Potion.wither.id, 40 * 20, 0 ) );
//
//			worldObj.setEntityState(this, (byte)11);
//		}
//	}
//
//	private void OnRottenFleshEaten()
//	{
//		if (m_iInfectionCountdown < 0 )
//		{
//			m_iInfectionCountdown = m_iMinimumInfectionTime + rand.nextInt( m_iInfectionTimeVariance );
//		}
//	}
//
//	public boolean IsDarkEnoughToAffectShitting()
//	{
//		int i = MathHelper.floor_double(posX);
//		int j = MathHelper.floor_double(posY);
//		int k = MathHelper.floor_double(posZ);
//
//		int lightValue = worldObj.getBlockLightValue( i, j, k );
//
//		if ( lightValue <= 5 )
//		{
//			return true;
//		}
//
//		return false;
//	}
//
//	public boolean AttemptToShit()
//	{
//		float poopVectorX = MathHelper.sin((rotationYawHead / 180F) * (float)Math.PI); 
//
//		float poopVectorZ = -MathHelper.cos((rotationYawHead / 180F) * (float)Math.PI); 
//
//		double shitPosX = posX + poopVectorX;
//		double shitPosY = posY + 0.25D;
//		double shitPosZ = posZ + poopVectorZ;
//
//		int shitPosI = MathHelper.floor_double( shitPosX );
//		int shitPosJ = MathHelper.floor_double( shitPosY );
//		int shitPosK = MathHelper.floor_double( shitPosZ );
//
//		if ( !IsPathToBlockOpenToShitting( shitPosI, shitPosJ, shitPosK ) )
//		{
//			return false;
//		}
//
//		EntityItem entityitem = new EntityItem( worldObj, 
//				shitPosX, shitPosY, shitPosZ, 
//				new ItemStack( FCBetterThanWolves.fcItemDung ) );
//
//		float velocityFactor = 0.05F;
//
//		entityitem.motionX = poopVectorX * 10.0f * velocityFactor;
//
//		entityitem.motionZ = poopVectorZ * 10.0f * velocityFactor;
//
//		entityitem.motionY = (float)worldObj.rand.nextGaussian() * velocityFactor + 0.2F;
//
//		entityitem.delayBeforeCanPickup = 10;
//
//		worldObj.spawnEntityInWorld( entityitem );
//
//		worldObj.playSoundAtEntity( this, "random.explode", 0.2F, 1.25F );
//
//		worldObj.playSoundAtEntity(this, "mob.wolf.growl", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//
//		// emit smoke
//
//		for ( int counter = 0; counter < 5; counter++ )
//		{
//			double smokeX = posX + ( poopVectorX * 0.5f ) + ( worldObj.rand.nextDouble() * 0.25F );
//			double smokeY = posY + worldObj.rand.nextDouble() * 0.5F + 0.25F;
//			double smokeZ = posZ + ( poopVectorZ * 0.5f ) + ( worldObj.rand.nextDouble() * 0.25F );
//
//			worldObj.spawnParticle( "smoke", smokeX, smokeY, smokeZ, 0.0D, 0.0D, 0.0D );
//		}
//
//		return true;
//	}
//
//	private boolean IsPathToBlockOpenToShitting( int i, int j, int k )
//	{
//		if ( !IsBlockOpenToShitting( i, j, k ) )
//		{
//			return false;
//		}
//
//		int wolfI = MathHelper.floor_double( posX );
//		int wolfK = MathHelper.floor_double( posZ );
//
//		int deltaI = i - wolfI;
//		int deltaK = k - wolfK;
//
//		if ( deltaI != 0 && deltaK != 0 )
//		{
//			// we're pooping on a diagnal.  Test to make sure that we're not warping poop through blocked off corners
//
//			if ( !IsBlockOpenToShitting( wolfI, j, k ) && !IsBlockOpenToShitting( i, j, wolfK ) )
//			{
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	private boolean IsBlockOpenToShitting( int i, int j, int k )
//	{
//		Block block = Block.blocksList[worldObj.getBlockId( i, j, k )];
//
//		if ( block != null && ( block == Block.waterMoving || block == Block.waterStill || block == Block.lavaMoving || block == Block.lavaStill || block == Block.fire || block.blockMaterial.isReplaceable() ||
//				block == FCBetterThanWolves.fcBlockDetectorLogic || block == FCBetterThanWolves.fcBlockDetectorGlowingLogic|| 
//				block == FCBetterThanWolves.fcBlockFireStoked ) )
//		{
//			block = null;
//		}
//
//		if ( block != null )
//		{
//			return false;
//		}        
//
//		return true;
//	}
//
//	private void TransformToDire()
//	{
//		int iFXI = MathHelper.floor_double( posX );
//		int iFXJ = MathHelper.floor_double( posY ) + 1;
//		int iFXK = MathHelper.floor_double( posZ );
//
//		worldObj.func_82739_e( FCBetterThanWolves.m_iWolfConvertToDireAuxFXID, iFXI, iFXJ, iFXK, 0 );
//
//		setDead();
//
//		FCEntityWolfDire direWolf = new FCEntityWolfDire( worldObj );
//
//		direWolf.setLocationAndAngles( posX, posY, posZ, rotationYaw, rotationPitch );
//		direWolf.renderYawOffset = renderYawOffset;
//
//		worldObj.spawnEntityInWorld( direWolf );
//	}
//
//	public boolean IsWildAndHostile()
//	{
//		if ( !isTamed() )
//		{
//			if ( IsStarving() || isAngry() )
//			{
//				// wild wolves always hostile when starving
//
//				return true;
//			}
//			else
//			{
//				// check if night and full moon
//
//				int iTimeOfDay = (int)( worldObj.worldInfo.getWorldTime() % 24000L );
//
//				if ( iTimeOfDay > 13500 && iTimeOfDay < 22500 )
//				{
//					int iMoonPhase = worldObj.getMoonPhase();
//
//					if ( iMoonPhase == 0 && worldObj.worldInfo.getWorldTime() > 24000L ) // full moon, and not the first one of the game
//					{
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}
//
//	public boolean IsWildAndHungry()
//	{
//		if ( !isTamed() )
//		{
//			if ( !IsFullyFed() )
//			{
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	public boolean IsWildAndStarving()
//	{
//		if ( !isTamed() )
//		{
//			if ( IsStarving() )
//			{
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	public float GetGrazeHeadVerticalOffset(float par1)
//	{
//		if ( m_iHowlingCountdown > 0 )
//		{
//			float fTiltFraction = 1F;
//
//			if ( m_iHowlingCountdown < 5 )
//			{
//				fTiltFraction = (float)m_iHowlingCountdown / 5F;
//			}
//			else if (  m_iHowlingCountdown > FCEntityAIWolfHowl.m_iHowlDuration - 10 )
//			{
//				fTiltFraction = (float)( FCEntityAIWolfHowl.m_iHowlDuration + 1 - m_iHowlingCountdown ) / 10F;
//			}
//
//			if ( !isSitting() )
//			{
//				return fTiltFraction * -0.5F;
//			}
//			else
//			{
//				return fTiltFraction * -0.25F;
//			}
//		}
//
//		return 0F;    	
//	}
//
//	public float GetGrazeHeadRotation(float par1)
//	{
//		if ( m_iHowlingCountdown > 0 )
//		{
//			float fTiltFraction = 1F;
//
//			if ( m_iHowlingCountdown < 5 )
//			{
//				fTiltFraction = (float)m_iHowlingCountdown / 5F;
//			}
//			else if (  m_iHowlingCountdown > FCEntityAIWolfHowl.m_iHowlDuration - 10 )
//			{
//				fTiltFraction = (float)( FCEntityAIWolfHowl.m_iHowlDuration + 1 - m_iHowlingCountdown ) / 10F;
//			}    			
//
//			return fTiltFraction * -((float)Math.PI / 5F);
//		}
//		else
//		{
//			return rotationPitch / (180F / (float)Math.PI);
//		}    	
//	}
//
//	public boolean AreEyesGlowing()
//	{
//		return m_bIsDoingHeadSpin;
//	}
//
//	public float GetPossessionHeadRotation()
//	{
//		return m_iPossessionHeadRotation * 2F * (float)Math.PI;
//	}
//
//	private void UpdateHeadSpin()
//	{
//		if ( m_bIsDoingHeadSpin )
//		{
//			m_iPossessionHeadRotation += 0.008F;
//
//			if ( m_iPossessionHeadRotation >= 1.0F )
//			{
//				m_iPossessionHeadRotation = 0F;
//
//				m_bIsDoingHeadSpin = false;
//			}
//		}
//	}
//
//	//----------- Client Side Functionality -----------//
//
//	@Override
//	public String getTexture()
//	{
//		if ( isTamed() )
//		{
//			if ( IsStarving() )
//			{
//				return "/btwmodtex/fcWolf_tame_starving.png";
//			}
//
//			return "/mob/wolf_tame.png";
//		}
//		else if ( isAngry() )
//		{
//			return "/mob/wolf_angry.png";
//		}
//		else if ( IsStarving() )
//		{
//			return "/btwmodtex/fcWolf_wild_starving.png"; 
//		}
//
//		return texture; // intentionally bypass super method
//	}
//
//	@Override
//	public void handleHealthUpdate( byte bUpdateType )
//	{
//		if ( bUpdateType == 10 )
//		{
//			m_iHowlingCountdown = FCEntityAIWolfHowl.m_iHowlDuration;
//		}
//		else if ( bUpdateType == 11 )
//		{
//			addPotionEffect( new PotionEffect( Potion.wither.id, 40 * 20, 0 ) );
//		}
//		else
//		{    	
//			super.handleHealthUpdate(bUpdateType);
//		}
//	}
//
//	@Override
//	public float getTailRotation()
//	{
//		if ( IsWildAndHostile() )
//		{
//			return 1.5393804F;
//		}
//		else if ( isTamed() )
//		{
//			return ( 0.55F - (float)( 20 - this.dataWatcher.getWatchableObjectInt( 18 ) ) * 
//					0.02F ) * (float)Math.PI;
//		}
//
//		return (float)Math.PI / 5F;
//	}
//}