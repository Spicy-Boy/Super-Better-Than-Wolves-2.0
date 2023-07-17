package net.minecraft.src;

public class FoodStats
{
    /** The player's food level. */
	// FCMOD: Code change to increase food meter resolution
	/*
    private int foodLevel = 20;
    */
    private int foodLevel = 60;
    // END FCMOD

    /** The player's food saturation. */
    // FCMOD: Code change so that player spawns with zero fat
    /*
    private float foodSaturationLevel = 5.0F;
    */
    private float foodSaturationLevel = 0F;
    // END FCMOD

    /** The player's food exhaustion. */
    private float foodExhaustionLevel;

    /** The player's food timer value. */
    private int foodTimer = 0;
    
	// FCMOD: Code change to increase food meter resolution
	/*
    private int prevFoodLevel = 20;
    */
    private int prevFoodLevel = 60;
    // END FCMOD

    /**
     * Args: int foodLevel, float foodSaturationModifier
     */
    // FCMOD: Code removed and replaced later by custom function
    /*
    public void addStats(int par1, float par2)
    {
        this.foodLevel = Math.min(par1 + this.foodLevel, 20);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)par1 * par2 * 2.0F, (float)this.foodLevel);
    }
	*/
	// END FCMOD

    /**
     * Eat some food.
     */
    public void addStats(ItemFood par1ItemFood)
    {
    	// FCMOD: Code change
    	/*
        this.addStats(par1ItemFood.getHealAmount(), par1ItemFood.getSaturationModifier());
        */
        this.addStats(par1ItemFood.GetHungerRestored(), par1ItemFood.getSaturationModifier());
        // END FCMOD
    }

    /**
     * Handles the food game logic.
     */
    // FCMOD: Code removed and replaced later by custom function
    /*
    public void onUpdate(EntityPlayer par1EntityPlayer)
    {
        int var2 = par1EntityPlayer.worldObj.difficultySetting;
        this.prevFoodLevel = this.foodLevel;

        if (this.foodExhaustionLevel > 4.0F)
        {
            this.foodExhaustionLevel -= 4.0F;

            if (this.foodSaturationLevel > 0.0F)
            {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
            }
            else if (var2 > 0)
            {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        if (this.foodLevel >= 18 && par1EntityPlayer.shouldHeal())
        {
            ++this.foodTimer;

            if (this.foodTimer >= 80)
            {
                par1EntityPlayer.heal(1);
                this.foodTimer = 0;
            }
        }
        else if (this.foodLevel <= 0)
        {
            ++this.foodTimer;

            if (this.foodTimer >= 80)
            {
                if (par1EntityPlayer.getHealth() > 10 || var2 >= 3 || par1EntityPlayer.getHealth() > 1 && var2 >= 2)
                {
                    par1EntityPlayer.attackEntityFrom(DamageSource.starve, 1);
                }

                this.foodTimer = 0;
            }
        }
        else
        {
            this.foodTimer = 0;
        }
    }
	*/
	// END FCMOD

    /**
     * Reads food stats from an NBT object.
     */
    public void readNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("foodLevel"))
        {
            this.foodLevel = par1NBTTagCompound.getInteger("foodLevel");
            this.foodTimer = par1NBTTagCompound.getInteger("foodTickTimer");
            this.foodSaturationLevel = par1NBTTagCompound.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel = par1NBTTagCompound.getFloat("foodExhaustionLevel");
            
            // FCMOD: Code added
            if ( !par1NBTTagCompound.hasKey("fcFoodLevelAdjusted"))
            {
            	foodLevel = foodLevel * 3;
            	foodSaturationLevel = 0F;
            }
            
            // sanity check the values as apparently they can get fucked up when importing from vanilla
            
            if ( foodLevel > 60 || foodLevel < 0 )
            {
            	foodLevel = 60;
            }
            
            if ( foodSaturationLevel > 20F || foodSaturationLevel < 0F )
            {
            	foodSaturationLevel = 20F;
            }
            // END FCMOD
        }
    }

    /**
     * Writes food stats to an NBT object.
     */
    public void writeNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInteger("foodLevel", this.foodLevel);
        par1NBTTagCompound.setInteger("foodTickTimer", this.foodTimer);
        par1NBTTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
        par1NBTTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
        
        // FCMOD: Code added
        par1NBTTagCompound.setBoolean( "fcFoodLevelAdjusted", true );
        // END FCMOD
    }

    /**
     * Get the player's food level.
     */
    public int getFoodLevel()
    {
        return this.foodLevel;
    }

    public int getPrevFoodLevel()
    {
        return this.prevFoodLevel;
    }

    /**
     * If foodLevel is not max.
     */
    public boolean needFood()
    {
    	// FCMOD: Code changed
    	/*
        return this.foodLevel < 20;
        */
        return this.foodLevel < 60;    	
        // END FCMOD
    }

    /**
     * adds input to foodExhaustionLevel to a max of 40
     */
    public void addExhaustion(float par1)
    {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + par1, 40.0F);        
    }

    /**
     * Get the player's food saturation level.
     */
    public float getSaturationLevel()
    {
        return this.foodSaturationLevel;
    }

    public void setFoodLevel(int par1)
    {
        this.foodLevel = par1;
    }

    public void setFoodSaturationLevel(float par1)
    {
        this.foodSaturationLevel = par1;
    }
    
    // FCMOD: Added New
    /**
     * Note that iFoodGain is one third regular hunger gained, with 6 units being a full pip
     */
    public void addStats( int iFoodGain, float fFatMultiplier )
    {
    	int iPreviousFoodLevel = foodLevel;
    	
        foodLevel = Math.min( iFoodGain + foodLevel, 60);
        
        int iExcessFood = iFoodGain - ( foodLevel - iPreviousFoodLevel );
        
        if ( iExcessFood > 0 )
        {
        	// divide by 3 due to increased resolution
        	
            foodSaturationLevel = Math.min( foodSaturationLevel + (float)iExcessFood * fFatMultiplier / 3F, 20F );
        }
    }
    
    public void onUpdate( EntityPlayer player )
    {
    	// only called on server
    	
        int iDifficulty = player.worldObj.difficultySetting;
        
        prevFoodLevel = foodLevel;
        
        if ( iDifficulty > 0 )
        {
	        // burn hunger
	        
	        while ( foodLevel > 0 && foodExhaustionLevel >= 1.33F && !ShouldBurnFatBeforeHunger() )
	        {
	            foodExhaustionLevel -= 1.33F;
	            
	            foodLevel = Math.max( foodLevel - 1, 0 );
	        }
	        
	    	// burn fat
	    	
	        while ( foodExhaustionLevel >= 0.5F && ShouldBurnFatBeforeHunger() )
	        {
	    		foodExhaustionLevel -= 0.5F;
	    		
	            foodSaturationLevel = Math.max( foodSaturationLevel - 0.125F, 0F );
	        }
        }
        else
        {
        	foodExhaustionLevel = 0F;
        }

        if ( foodLevel > 24 && player.shouldHeal() )
        {
            ++foodTimer;

            if ( foodTimer >= 600 ) // once every 30 seconds
            {
                player.heal( 1 );
                foodTimer = 0;
            }
        }
        else if ( foodLevel <= 0 && foodSaturationLevel <= 0.01F )
        {
            ++foodTimer;
            
            //AARON CHANGED to make starvation take slightly longer (original value: 80)
            //God bless Yany for discovering this
            if ( foodTimer >= 200 )
            {
            	if ( iDifficulty > 0 )
            	{
            		player.attackEntityFrom( DamageSource.starve, 1 );
            	}

                foodTimer = 0;
            }

            // reset the exhaustion level so that it doesn't stack up while the player is starving
            
            foodExhaustionLevel = 0F;
        }
        else
        {
            foodTimer = 0;
        }
    }
    
    private boolean ShouldBurnFatBeforeHunger()
    {
    	// only burn fat when the corresponding hunger pip is completely depleted
    	
    	return foodSaturationLevel > (float)( ( foodLevel + 5 ) / 6 ) * 2F;    	
    }    
    // END FCMOD    
}
