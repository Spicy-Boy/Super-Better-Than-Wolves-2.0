package net.minecraft.src;

public class SuperBTWBlockSandstoneBrickLargeStairs extends FCBlockStairs
{
	
    public SuperBTWBlockSandstoneBrickLargeStairs( int iBlockID, Block referenceBlock, int iReferenceBlockMetadata )
    {
        super( iBlockID, referenceBlock, iReferenceBlockMetadata);
        setHardness( 0.5F );
////    	setResistance( 5F );
    }
    
    @Override
    public int GetHarvestToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 3; // diamond or better (sandstone is diamond or better... does that matter?)
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, Block.sand.blockID, 1, 0, fChanceOfDrop );
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 1, 0, fChanceOfDrop );
		
		return true;
	}

}
