package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonGrowingCanaryAsleep extends SCBlockMelonGrowingAsleep 
{

	protected SCBlockMelonGrowingCanaryAsleep(int iBlockID, int stemBlock, int vineBlock, int flowerBlock,
			int convertedBlockID) 
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.melonCanary.blockID ,meta);
	}
	
	@Override
	protected void convertBlock(World world, int i, int j, int k)
	{	
		int meta = world.getBlockMetadata(i, j, k);
		
		world.setBlockAndMetadata(i, j, k, convertedBlockID , meta);
	}
	
	@Override
	public int damageDropped(int meta) {
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 12;
		}
		else if (this.GetGrowthLevel(meta) == 2)
		{
			return 8;
		}
		else if (this.GetGrowthLevel(meta) == 1)
		{
			return 4;
		}
		else return 0;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) 
	{
		
		return SCDefs.melonCanaryHarvested.blockID;
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
		int dir = this.getDirection(meta);
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//young
		if (growthLevel == 0)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(6, 4, 4);
			}
			else return GetGourdBounds(4, 4, 6);
		}
		//teen
		else if (growthLevel == 1)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(10, 6, 6);
			}
			else return GetGourdBounds(6, 6, 10);
		}
		//adult
		else if (growthLevel == 2)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(12, 8, 8);
			}
			else return GetGourdBounds(8, 8, 12);
		}
		//mature
		else if (dir == 1 || dir == 3)
		{
			return GetGourdBounds(16, 10, 10);
		}
		else return GetGourdBounds(10, 10, 16);
	}	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		this.setTextureRotations(renderer, i, j, k, meta);
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
		renderer.renderStandardBlock(this, i, j, k);
		
		renderer.ClearUvRotation();
		
		this.renderVineConnector(renderer, i, j, k, connectorIcon[growthLevel]);
		
		return true;
	}
	
	private void setTextureRotations(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		int dir = this.getDirection(meta);
		
		if (dir == 0 || dir == 2)
		{
			renderer.SetUvRotateTop(0);
			renderer.SetUvRotateBottom(0);
			renderer.SetUvRotateNorth(1);
			renderer.SetUvRotateSouth(1);
		}
		else if (dir == 1 || dir == 3)
		{
			renderer.SetUvRotateTop(1);
			renderer.SetUvRotateBottom(1);
			renderer.SetUvRotateEast(1);
			renderer.SetUvRotateWest(1);
		}
			
		
		
	}

	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int dir = this.getDirection(blockAccess.getBlockMetadata(i, j, k));
		
		this.setTextureRotations(renderer, i, j, k, meta);
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
		
		renderer.ClearUvRotation();
	}
	
	protected Icon[] waterCanaryIcon;
	protected Icon[] waterCanaryIconTop;
	protected Icon[] connectorIcon;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		//Orange
		waterCanaryIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < waterCanaryIcon.length; iTempIndex++ )
		{
  			waterCanaryIcon[iTempIndex] = register.registerIcon( "SCBlockMelonYellowSide_" + iTempIndex );
		}
	
  		waterCanaryIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < waterCanaryIconTop.length; iTempIndex++ )
		{
			waterCanaryIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonYellowTop_" + iTempIndex );
		}
		
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockMelonCanaryConnector_" + iTempIndex );
        }
		
		blockIcon = waterCanaryIcon[3];
	}
	
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	int growthLevel = GetGrowthLevel(iMetadata);
    	int dir = getDirection(iMetadata);

    	
    	if (iSide == 2 || iSide == 3 )
    	{
    		if (dir == 0 || dir == 2)
    		{
    			return waterCanaryIconTop[growthLevel];
    		}
    		else return waterCanaryIcon[growthLevel];
    	}
    	else if (iSide == 4 || iSide == 5 )
    	{
    		if (dir == 1 || dir == 3)
    		{
    			return waterCanaryIconTop[growthLevel];
    		}
    		else return waterCanaryIcon[growthLevel];
    	}
    	else return waterCanaryIcon[growthLevel];
    }

}
