// FCMOD

package net.minecraft.src;

public class SCItemBlockPumpkinHarvested extends ItemMultiTextureTile
{
    public SCItemBlockPumpkinHarvested( int iBlockID, Block block, String[] string )
    {
        super(iBlockID, block, string);
        
		setMaxStackSize( 64 );
    }
    
    @Override
    public void OnUsedInCrafting(int metadata, EntityPlayer player, ItemStack outputStack )
    {
    	
    	Item itemToEject = SuperBTWDefinitions.whitePumpkinSeeds;
    	int quantity = 0;
    	
		if ( outputStack.itemID == SCDefs.pumpkinCarved.blockID )
		{
			if (metadata == 3)
			{
				itemToEject = SuperBTWDefinitions.orangePumpkinSeeds;
				quantity = 4;
			}
			else if (metadata == 7)
			{
				itemToEject = SuperBTWDefinitions.greenPumpkinSeeds;
				quantity = 5;
			}
			else if (metadata == 11)
			{
				itemToEject = SuperBTWDefinitions.yellowPumpkinSeeds;
				quantity = 3;
			}
			else if (metadata == 15)
			{
				quantity = 3;
			}
			
			
	    	if ( !player.worldObj.isRemote && quantity != 0 )
	    	{
    			FCUtilsItem.EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, 
    				new ItemStack( itemToEject, quantity, 0 ) );
	    	}
	    	
	    	if ( player.m_iTimesCraftedThisTick == 0 )
			{
				// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
		    	
				player.playSound( "mob.slime.attack", 0.5F, ( player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.1F + 0.7F );
			}
		}
		else if (outputStack.itemID == SuperBTWDefinitions.orangePumpkinSeeds.itemID ||
				outputStack.itemID == SuperBTWDefinitions.greenPumpkinSeeds.itemID ||
				outputStack.itemID == SuperBTWDefinitions.yellowPumpkinSeeds.itemID ||
				outputStack.itemID == SuperBTWDefinitions.whitePumpkinSeeds.itemID
				)
		{
			itemToEject = SCDefs.pumpkinSlice;
			
			if (metadata == 3)
			{
				quantity = 4;
			}
			if (metadata == 2)
			{
				quantity = 3;
			}
			
			if (metadata == 7)
			{
				quantity = 3;
			}
			if (metadata == 6)
			{
				quantity = 2;
			}
			
			if (metadata == 11)
			{
				quantity = 3;
			}
			
			if (metadata == 15)
			{
				quantity = 2;
			}
			
			
			
	    	if ( !player.worldObj.isRemote && quantity != 0)
	    	{
    			FCUtilsItem.EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, 
    				new ItemStack( itemToEject, quantity, 0 ) );
	    	}
	    	
	    	if ( player.m_iTimesCraftedThisTick == 0 )
			{
				// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
		    	
				player.playSound( "mob.slime.attack", 0.5F, ( player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.1F + 0.7F );
			}
			
		}
    }
}
