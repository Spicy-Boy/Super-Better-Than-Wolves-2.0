package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingYellow extends SCBlockPumpkinGrowing {

	protected SCBlockPumpkinGrowingYellow(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		setUnlocalizedName("SCBlockPumpkinGrowingYellow");
	}
	
	@Override
	public void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinYellowAsleep.blockID ,meta + 4);
	}

	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel) {
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i + 8; 
			}
		}
		return 0;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		
		//return getMetaHarvested(meta);
		
		if (growthLevel == 3 )
		{
			return 11; 
		}
		else if (growthLevel == 2)
		{
			return 10;
		}
		else if (growthLevel == 1)
		{
			return 9;
		}
		else return 8;
	}
	
	
	protected int getMetaHarvested(int growthLevel) {
		if (growthLevel == 3 )
		{
			return 11; 
		}
		else if (growthLevel == 2)
		{
			return 10;
		}
		else if (growthLevel == 1)
		{
			return 9;
		}
		else return 8;
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
			return GetGourdBounds(4, 4, 4);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else return GetGourdBounds(12, 12, 12);
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
//	
//	@Override
//	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
//		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
//	}
//	
//
//	protected Icon[] orangeIcon;
//	protected Icon[] orangeIconTop;
//	protected Icon[] connectorIcon;
//	
//	@Override
//  	public void registerIcons( IconRegister register )
//  	{
//		//Orange
//  		orangeIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
//		{
//  			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowSide_" + iTempIndex );
//		}
//	
//		orangeIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
//		{
//		orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowTop_" + iTempIndex );
//		}
//		
//        connectorIcon = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
//        {
//        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowConnector_" + iTempIndex );
//        }
//		
//		blockIcon = orangeIcon[3];
//	}
//	
//	@Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	int growthLevel = GetGrowthLevel(iMetadata);
//    	
//    	if ( iSide == 1 || iSide == 0 )
//    	{
//    		return orangeIconTop[growthLevel];
//    	}
//    	
//    	return orangeIcon[growthLevel];
//    }
}
