package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonGrowingCantaloupeAsleep extends SCBlockMelonGrowingAsleep
{
	
	protected SCBlockMelonGrowingCantaloupeAsleep(int iBlockID, int stemBlock, int vineBlock, int flowerBlock,
			int convertedBlockID) 
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}
	
	public void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.melonCantaloupe.blockID, meta);
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
	public int idDropped(int par1, Random par2Random, int par3) {
		
		return SCDefs.melonHarvested.blockID;
	}

	@Override
	public int damageDropped(int meta) {
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 11;
		}
		else if (this.GetGrowthLevel(meta) == 2)
		{
			return 10;
		}
		else if (this.GetGrowthLevel(meta) == 1)
		{
			return 9;
		}
		else return 8;
	}
	
	//--- Render ---//
	
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
			return GetGourdBounds(8, 8, 8);
		}
		else return GetGourdBounds(10, 10, 10);
	}	
	
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
//	protected Icon[] waterMelonIcon;
//	protected Icon[] waterMelonIconTop;
//	protected Icon[] connectorIcon;
//	
//	@Override
//  	public void registerIcons( IconRegister register )
//  	{
//		//Orange
//		waterMelonIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < waterMelonIcon.length; iTempIndex++ )
//		{
//  			waterMelonIcon[iTempIndex] = register.registerIcon( "SCBlockMelonWhiteSide_" + iTempIndex );
//		}
//	
//  		waterMelonIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < waterMelonIconTop.length; iTempIndex++ )
//		{
//			waterMelonIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonWhiteTop_" + iTempIndex );
//		}
//		
//        connectorIcon = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
//        {
//        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockMelonSmallConnector_" + iTempIndex );
//        }
//		
//		blockIcon = waterMelonIcon[3];
//	}
//	
//	@Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	int growthLevel = GetGrowthLevel(iMetadata);
//    	
//    	if ( iSide == 1 || iSide == 0 )
//    	{
//    		return waterMelonIconTop[growthLevel];
//    	}
//    	
//    	return waterMelonIcon[growthLevel];
//    }
	
}
