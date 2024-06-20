// FCMOD

package net.minecraft.src;

public class SuperBTWBlockChunkIronSinteredStorage extends FCBlockChunkOreStorage
{
    protected SuperBTWBlockChunkIronSinteredStorage( int iBlockID )
    {
        super( iBlockID );
        
        setUnlocalizedName( "SuperBTWBlockChunkIronSintered" );
        
        setCreativeTab( CreativeTabs.tabBlock );
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, SuperBTWDefinitions.chunkIronSintered.itemID, 
			9, 0, fChanceOfDrop );
		
		return true;
	}	
	
	@Override
    public int GetItemIndexDroppedWhenCookedByKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
		return Item.ingotIron.itemID;
    }
    
	//------------- Class Specific Methods ------------//
	
	//----------- Client Side Functionality -----------//
}