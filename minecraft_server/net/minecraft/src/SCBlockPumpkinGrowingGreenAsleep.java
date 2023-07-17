package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingGreenAsleep extends SCBlockPumpkinGrowingAsleep 
{
	/**
	 * 
	 * @param iBlockID
	 * @param stemBlock
	 * @param vineBlock
	 * @param flowerBlock
	 * @param convertedBlockID
	 */
	protected SCBlockPumpkinGrowingGreenAsleep(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) 
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		//setUnlocalizedName("SCBlockPumpkinGrowingOrange");
	}
	
	@Override
	public void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinGreen.blockID ,meta);
	}
	
	@Override
	public int damageDropped(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		
		//return getMetaHarvested(meta);
		
		if (growthLevel == 3 )
		{
			return 7; 
		}
		else if (growthLevel == 2)
		{
			return 6;
		}
		else if (growthLevel == 1)
		{
			return 5;
		}
		else return 4;
	}
	
	protected int getMetaHarvested(int growthLevel) {
		if (growthLevel == 3 )
		{
			return 7; 
		}
		else if (growthLevel == 2)
		{
			return 6;
		}
		else if (growthLevel == 1)
		{
			return 5;
		}
		else return 4;
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		return this.GetBlockBoundsFromPoolBasedOnState(blockAccess.getBlockMetadata(i, j, k));
	}
	
	private AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//Orange
		if (growthLevel == 0)
		{
			return GetGourdBounds(6, 4, 6);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(8, 5, 8);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(12, 6, 12);
		}
		else return GetGourdBounds(16, 8, 16);
	}	
	
	//--- Render ---//
	
//	@Override
//	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
//		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
//		renderer.renderStandardBlock(this, i, j, k);
//		
//		this.renderVineConnector(renderer, i, j, k, connectorIcon[growthLevel]);
//		
//		return true;
//	}
	
//	@Override
//	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
//		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
//	}
//	
//	protected Icon[] greenIcon;
//	protected Icon[] greenIconTop;
//	protected Icon[] connectorIcon;
	
//	@Override
//  	public void registerIcons( IconRegister register )
//  	{
//		//Orange
//		greenIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < greenIcon.length; iTempIndex++ )
//		{
//  			greenIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenSide_" + iTempIndex );
//		}
//	
//  		greenIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < greenIconTop.length; iTempIndex++ )
//		{
//			greenIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenTop_" + iTempIndex );
//		}
//		
//        connectorIcon = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
//        {
//        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenConnector_" + iTempIndex );
//        }
//    	
//    	blockIcon = greenIcon[3];
//    	
//		
//	}
//	
//	@Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	int growthLevel = GetGrowthLevel(iMetadata);
//    	
//    	if ( iSide == 1 || iSide == 0 )
//    	{
//    		return greenIconTop[growthLevel];
//    	}
//
//    	return greenIcon[growthLevel];
//    }
}
