package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockChunkIronSintered extends FCBlockChunkOre
{
    protected SuperBTWBlockChunkIronSintered( int iBlockID )
    {
        super( iBlockID );
        
        setUnlocalizedName( "SuperBTWBlockChunkIronSintered" );
    }
    
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
		return SuperBTWDefinitions.chunkIronSintered.itemID;
    }
	
	@Override
    public int GetItemIndexDroppedWhenCookedByKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
		return FCBetterThanWolves.fcItemNuggetIron.itemID;
    }
}