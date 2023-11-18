package net.minecraft.src;

public class SuperBTWItemBlockStickBundleLooseSlab extends FCItemBlockSlab
{

	public SuperBTWItemBlockStickBundleLooseSlab(int iItemID) 
	{
		super(iItemID);
	}
	
    @Override
    public boolean canCombineWithBlock( World world, int i, int j, int k, int iItemDamage )
    {
    	int iBlockID = world.getBlockId( i, j, k );
    	
        if ( iBlockID == SuperBTWDefinitions.stickBundleLooseSlab.blockID )
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
