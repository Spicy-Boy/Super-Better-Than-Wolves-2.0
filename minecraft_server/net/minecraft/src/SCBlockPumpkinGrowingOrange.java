package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingOrange extends SCBlockPumpkinGrowing {

	/**
	 * 
	 * @param iBlockID
	 * @param stemBlock
	 * @param vineBlock
	 * @param flowerBlock
	 * @param convertedBlockID
	 */
	protected SCBlockPumpkinGrowingOrange(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		setUnlocalizedName("SCBlockPumpkinGrowingOrange");
	}
	

	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel) {
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i;
			}
		}
		return 0;
	}
	
	protected int getMetaHarvested(int growthLevel) {
		if (growthLevel == 3 )
		{
			return 3;
		}
		else if (growthLevel == 2)
		{
			return 2;
		}
		else if (growthLevel == 1)
		{
			return 1;
		}
		else return 0;
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
			return GetGourdBounds(6, 6, 6);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(12, 12, 12);
		}
		else return GetGourdBounds(16, 16, 16);
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
	
	protected Icon[] orangeIcon;
	protected Icon[] orangeIconTop;
	protected Icon[] connectorIcon;
	
//	@Override
//  	public void registerIcons( IconRegister register )
//  	{
//		//Orange
//  		orangeIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
//		{
//  			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
//		}
//	
//		orangeIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
//		{
//		orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
//		}
//		
//        connectorIcon = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
//        {
//        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinConnector_" + iTempIndex );
//        }
//		
//		blockIcon = orangeIcon[3];
//	}
	
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
