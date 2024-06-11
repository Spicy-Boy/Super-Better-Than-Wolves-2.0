package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockSandstoneBrickLarge extends Block
{
	public static final int typeDefault = 0;
	public static final int typeMossy = 1;
	public static final int typeCracked = 2;
	public static final int typeChiseled = 3;

    public SuperBTWBlockSandstoneBrickLarge( int iBlockID )
    {
    	super(iBlockID, Material.rock);
        
        SetPicksEffectiveOn();
        
        setHardness( 1.7F ); //slightly harder than sandstone
        
        setStepSound( soundStoneFootstep );
        
        this.setCreativeTab(CreativeTabs.tabBlock);
        
        setTickRandomly( true );
    }
    
    // random tick / convert to cracked, mossy
    //taken from FCBlockStoneBrick
	@Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		if ( iMetadata == 0 )
		{
			// check for drip conditions
			
			if ( !world.getBlockMaterial( i, j - 1, k ).blocksMovement() )
			{
				int iBlockAboveID = world.getBlockId( i, j + 1, k );
				
				if ( iBlockAboveID == Block.waterMoving.blockID || iBlockAboveID == Block.waterStill.blockID )
				{
					if ( random.nextInt( 15 ) == 0 )
					{
						world.setBlockMetadataWithNotify( i, j, k, 1 );
						
						world.markBlockRangeForRenderUpdate( i, j, k, i, j, k );
					}
				}
				else if ( iBlockAboveID == Block.lavaMoving.blockID || iBlockAboveID == Block.lavaStill.blockID )
				{
					if ( random.nextInt( 15 ) == 0 )
					{
						world.setBlockMetadataWithNotify( i, j, k, 2 );
						
						world.markBlockRangeForRenderUpdate( i, j, k, i, j, k );
					}
				}
			}			
		}
    }
    
    @Override
    public int GetHarvestToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 3; // diamond or better (sandstone is diamond or better... does that matter?)
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, Block.sand.blockID, 2, 0, fChanceOfDrop );
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 4, 0, fChanceOfDrop );
		
		return true;
	}

}