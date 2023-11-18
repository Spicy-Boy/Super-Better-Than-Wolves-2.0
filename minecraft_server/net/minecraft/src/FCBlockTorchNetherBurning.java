// FCMOD

package net.minecraft.src;

import java.util.Random;

public class FCBlockTorchNetherBurning extends FCBlockTorchBaseBurning
{
    protected FCBlockTorchNetherBurning( int iBlockID )
    {
    	super( iBlockID );
    	
    	setLightValue( 0.9375F );
    	
    	setUnlocalizedName( "fcBlockTorchNetherBurning" );
    	
    	setTickRandomly( true );
    }
    
	//AARON added this to allow right click pick up torch
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {  
//		System.out.println("HI RIGHT CLICK TORCH!!");
    
        if (!world.isRemote && player.getCurrentEquippedItem() == null )
        {
//			System.out.println("EMPTY HAND DETECT!");

        		player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( FCBetterThanWolves.fcBlockTorchNetherBurning, 1);
        		

                player.worldObj.playSoundAtEntity( player, "random.pop", 0.2F, 
                		((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        		world.setBlock(i, j, k, 0);
        		
        		return true;
        }
		
		return false;
    }
	//:>
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        super.updateTick( world, i, j, k, rand );
        
        // last param provides increased chance of fire spread, over default of 100
        
		FCBlockFire.CheckForFireSpreadAndDestructionToOneBlockLocation( world, i, j + 1, k, rand, 0, 25 );
    }
    
	@Override
    public boolean CanBeCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	return true;
    }
    
    //------------- Class Specific Methods ------------//

	//----------- Client Side Functionality -----------//
}
