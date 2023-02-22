// FCMOD

package net.minecraft.src;

import java.util.Random;

public class FCBlockTorchNetherUnlit extends FCBlockTorchBaseUnlit
{
    protected FCBlockTorchNetherUnlit( int iBlockID )
    {
    	super( iBlockID );    	
    	
    	setUnlocalizedName( "fcBlockTorchIdle" );
    }
    
	//AARON added this to allow right click pick up torch
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {  
//		System.out.println("HI RIGHT CLICK TORCH!!");
    
        if (!world.isRemote && player.getCurrentEquippedItem() == null )
        {
//			System.out.println("EMPTY HAND DETECT!");

        		player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( FCBetterThanWolves.fcBlockTorchNetherUnlit, 1);
        		
        		world.setBlock(i, j, k, 0);
        		
        		return true;
        }
		
		return false;
    }
	//:>
    
	@Override
	protected int GetLitBlockID()
	{
		return FCBetterThanWolves.fcBlockTorchNetherBurning.blockID;
	}
	
    //------------- Class Specific Methods ------------//

	//----------- Client Side Functionality -----------//
}
    
