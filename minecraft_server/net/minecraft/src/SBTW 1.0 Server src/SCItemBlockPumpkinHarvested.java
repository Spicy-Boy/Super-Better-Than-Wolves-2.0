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
    public void OnUsedInCrafting( EntityPlayer player, ItemStack outputStack )
    {
		if ( outputStack.itemID == SCDefs.pumpkinCarved.blockID )
		{
	    	if ( !player.worldObj.isRemote )
	    	{
    			FCUtilsItem.EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, 
    				new ItemStack( Item.pumpkinSeeds, 4, 0 ) );
	    	}
	    	
	    	if ( player.m_iTimesCraftedThisTick == 0 )
			{
				// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
		    	
				player.playSound( "mob.slime.attack", 0.5F, ( player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.1F + 0.7F );
			}
		}
		else if (outputStack.itemID == SCDefs.pumpkinSlice.itemID )
		{
	    	if ( !player.worldObj.isRemote )
	    	{
    			FCUtilsItem.EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, 
    				new ItemStack( Item.pumpkinSeeds, 4, 0 ) );
	    	}
	    	
	    	if ( player.m_iTimesCraftedThisTick == 0 )
			{
				// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
		    	
				player.playSound( "mob.slime.attack", 0.5F, ( player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.1F + 0.7F );
			}
			
		}
    }
}
