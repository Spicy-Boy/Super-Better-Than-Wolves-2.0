package net.minecraft.src;

public class SuperBTWItemChunkIronSintered extends FCItemPlacesAsBlock
{
    public SuperBTWItemChunkIronSintered( int iItemID )
    {
        super( iItemID, SuperBTWDefinitions.blockIronSintered.blockID );
        
        SetFilterableProperties( Item.m_iFilterable_Small );
        
        setUnlocalizedName( "SuperBTWItemChunkIronSintered" );
        
        setCreativeTab( CreativeTabs.tabMaterials );
    }
    
    @Override
    public boolean IsPistonPackable( ItemStack stack )
    {
    	return true;
    }
    
    @Override
    public int GetRequiredItemCountToPistonPack( ItemStack stack )
    {
    	return 9;
    }
    
    @Override
    public int GetResultingBlockIDOnPistonPack( ItemStack stack )
    {
    	return SuperBTWDefinitions.blockIronSinteredStorage.blockID;
    }
}