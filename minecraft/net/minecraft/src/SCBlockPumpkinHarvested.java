package net.minecraft.src;

import java.util.List;

public class SCBlockPumpkinHarvested extends SCBlockGourdHarvested {

	protected SCBlockPumpkinHarvested(int iBlockID) {
		super(iBlockID);
		
		setHardness(1.0F);
        
        setStepSound(soundWoodFootstep);
        
        setUnlocalizedName("SCBlockPumpkinHarvested");
		
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
		
		if (blockAccess.getBlockMetadata(i, j, k) == 3) //mature pumpkin
		{
			return true;
		}
		else return super.IsNormalCube(blockAccess, i, j, k);
	}
	
	//CHANGED to be per type seeds
	@Override
	protected Item ItemToDropOnExplode(int meta)
	{
		if (meta == 3)
		{
			return SuperBTWDefinitions.orangePumpkinSeeds;
		}
		else if (meta == 7)
		{
			return SuperBTWDefinitions.greenPumpkinSeeds;
		}
		else if (meta == 11)
		{
			return SuperBTWDefinitions.yellowPumpkinSeeds;
		}
		else if (meta == 15)
		{
			return SuperBTWDefinitions.whitePumpkinSeeds;
		}
		
		return Item.pumpkinSeeds;
	}
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		if (meta == 3 || meta == 7)
		{
			return 3;
		}
		else if (this.GetGrowthLevel(meta) == 3)
		{
			return 2;
		}
		else return 0;
	}
	
	
	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourcePumpkin;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (int i = 0; i < 16; i++) {
			par3List.add(new ItemStack(par1, 1, i));
			
		}
    }
		
	//----------- Render/Icon Functionality -----------//
	
	private Icon[] orangeIcon;
	private Icon[] orangeIconTop;
	private Icon[] greenIcon;
	private Icon[] greenIconTop;
	private Icon[] yellowIcon;
	private Icon[] yellowIconTop;
	private Icon[] whiteIcon;
	private Icon[] whiteIconTop;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		//Orange
  		orangeIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
		{
  			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
		}
	
		orangeIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
		{
		orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
		}
		
		//Green
		greenIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < greenIcon.length; iTempIndex++ )
		{
  			greenIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenSide_" + iTempIndex );
		}
	
  		greenIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < greenIconTop.length; iTempIndex++ )
		{
			greenIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenTop_" + iTempIndex );
		}
		
		//Yellow
		yellowIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < yellowIcon.length; iTempIndex++ )
		{
  			yellowIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowSide_" + iTempIndex );
		}
	
  		yellowIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < yellowIconTop.length; iTempIndex++ )
		{
			yellowIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowTop_" + iTempIndex );
		}
		
		//White
		whiteIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < whiteIcon.length; iTempIndex++ )
		{
  			whiteIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteSide_" + iTempIndex );
		}
	
  		whiteIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < whiteIconTop.length; iTempIndex++ )
		{
			whiteIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteTop_" + iTempIndex );
		}
	}
	
	@Override
	public Icon getIcon( int side, int meta )
	{
		int growthLevel = this.GetGrowthLevel(meta);
		int type = this.getType(meta);
		
		if (type == 0) 
		{
			if ( side == 1 || side == 0 )
	    	{
	    		return blockIcon = orangeIconTop[growthLevel];
	    	}
	    	
			else return blockIcon = orangeIcon[growthLevel];
		}
		else if (type == 1) 
		{
			if ( side == 1 || side == 0 )
	    	{
	    		return blockIcon = greenIconTop[growthLevel];
	    	}
	    	
			else return blockIcon = greenIcon[growthLevel];
		}
		else if (type == 2) 
		{
			if ( side == 1 || side == 0 )
	    	{
	    		return blockIcon = yellowIconTop[growthLevel];
	    	}
	    	
			else return blockIcon = yellowIcon[growthLevel];
		}
		else if (type == 3) 
		{
			if ( side == 1 || side == 0 )
	    	{
	    		return blockIcon = whiteIconTop[growthLevel];
	    	}
	    	
			else return blockIcon = whiteIcon[growthLevel];
		}
		
		else return blockIcon;

	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	}
	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
		renderer.renderStandardBlock( this, i, j, k );

		return true;
	}

	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(iItemDamage) );
		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
	}

}
