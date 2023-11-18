package net.minecraft.src;

import java.util.Random;

public class SuperBTWItemBlockReedThatchSlab extends FCItemBlockSlab
{

	public SuperBTWItemBlockReedThatchSlab(int iItemID) 
	{
		super(iItemID);
	}
	
    @Override
    public boolean canCombineWithBlock( World world, int i, int j, int k, int iItemDamage )
    {
    	int iBlockID = world.getBlockId( i, j, k );
    	
        if ( iBlockID == SuperBTWDefinitions.reedThatchSlab.blockID )
        {
    		return true;
        }
        
       	return super.canCombineWithBlock( world, i, j, k, iItemDamage );
    }
    
    @Override
    public boolean convertToFullBlock( World world, int i, int j, int k )
    {
    	
    	int iNewBlockID = ((FCBlockSlab)Block.blocksList[getBlockID()]).GetCombinedBlockID( 0 );
    	
    	return world.setBlockWithNotify( i, j, k, iNewBlockID );
    }

}
