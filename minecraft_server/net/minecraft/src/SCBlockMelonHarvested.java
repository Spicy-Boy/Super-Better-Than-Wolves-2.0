package net.minecraft.src;

import java.util.List;

public class SCBlockMelonHarvested extends SCBlockGourdHarvested {

	protected SCBlockMelonHarvested(int iBlockID) {
		super(iBlockID);
		
		setHardness(1.0F);
        
        setStepSound(soundWoodFootstep);
        
        setUnlocalizedName("SCBlockMelonHarvested");
		
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
		
		if (blockAccess.getBlockMetadata(i, j, k) == 3) //mature watermelon
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
			return SuperBTWDefinitions.waterMelonSeeds;
		}
		else if (meta == 7)
		{
			return SuperBTWDefinitions.honeydewMelonSeeds;
		}
		else if (meta == 11)
		{
			return SuperBTWDefinitions.cantaloupeMelonSeeds;
		}
		
		return Item.melonSeeds;
	}
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		if (meta == 3)
		{
			return 5;
		}
		else if (meta == 7)
		{
			return 4;
		}
		else if (meta == 11)
		{
			return 3;
		}

		else return 0;
	}
	
	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		if (this.getType(meta) == 1)
		{
			 return SocksCropsAddon.m_iMelonHoneydewExplodeAuxFXID;
		}
		else if (this.getType(meta) == 2)
		{
			 return SocksCropsAddon.m_iMelonCantaloupeExplodeAuxFXID;
		}
		else return SocksCropsAddon.m_iMelonExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourceMelon;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		//Water
		par3List.add(new ItemStack(par1, 1, 0) );
		par3List.add(new ItemStack(par1, 1, 1) );
		par3List.add(new ItemStack(par1, 1, 2) );
		par3List.add(new ItemStack(par1, 1, 3) );
		
		//Honedew
		par3List.add(new ItemStack(par1, 1, 4) );
		par3List.add(new ItemStack(par1, 1, 5) );
		par3List.add(new ItemStack(par1, 1, 6) );
		par3List.add(new ItemStack(par1, 1, 7) );
		
		//Cantaloupe
		par3List.add(new ItemStack(par1, 1, 8) );
		par3List.add(new ItemStack(par1, 1, 9) );
		par3List.add(new ItemStack(par1, 1, 10) );
		par3List.add(new ItemStack(par1, 1, 11) );
		
		//Canary - check CanaryHarvested
//		par3List.add(new ItemStack(par1, 1, 12) );		
//		par3List.add(new ItemStack(par1, 1, 13) );
//		par3List.add(new ItemStack(par1, 1, 14) );
//		par3List.add(new ItemStack(par1, 1, 15) );
    }
		
	//----------- Render/Icon Functionality -----------//
	
	private Icon[] waterIcon;
	private Icon[] waterIconTop;
	private Icon[] honeydewIcon;
	private Icon[] honeydewIconTop;
	private Icon[] cantaloupeIcon;
	private Icon[] cantaloupeIconTop;
	
//	@Override
//  	public void registerIcons( IconRegister register )
//  	{
//		//Water
//  		waterIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < waterIcon.length; iTempIndex++ )
//		{
//  			waterIcon[iTempIndex] = register.registerIcon( "SCBlockMelonSide_" + iTempIndex );
//		}
//	
//		waterIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < waterIconTop.length; iTempIndex++ )
//		{
//		waterIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonTop_" + iTempIndex );
//		}
//		
//		//Honeydew
//		honeydewIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < honeydewIcon.length; iTempIndex++ )
//		{
//  			honeydewIcon[iTempIndex] = register.registerIcon( "SCBlockMelonGreenSide_" + iTempIndex );
//		}
//	
//  		honeydewIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < honeydewIconTop.length; iTempIndex++ )
//		{
//			honeydewIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonGreenTop_" + iTempIndex );
//		}
//		
//		//Cantaloupe
//		cantaloupeIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < cantaloupeIcon.length; iTempIndex++ )
//		{
//  			cantaloupeIcon[iTempIndex] = register.registerIcon( "SCBlockMelonWhiteSide_" + iTempIndex );
//		}
//	
//  		cantaloupeIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < cantaloupeIconTop.length; iTempIndex++ )
//		{
//			cantaloupeIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonWhiteTop_" + iTempIndex );
//		}
//	}
	
//	@Override
//	public Icon getIcon( int side, int meta )
//	{
//		int growthLevel = this.GetGrowthLevel(meta);
//		int type = this.getType(meta);
//		
//		if (type == 0) 
//		{
//			if ( side == 1 || side == 0 )
//	    	{
//	    		return blockIcon = waterIconTop[growthLevel];
//	    	}
//	    	
//			else return blockIcon = waterIcon[growthLevel];
//		}
//		else if (type == 1) 
//		{
//			if ( side == 1 || side == 0 )
//	    	{
//	    		return blockIcon = honeydewIconTop[growthLevel];
//	    	}
//	    	
//			else return blockIcon = honeydewIcon[growthLevel];
//		}
//		else if (type == 2) 
//		{
//			if ( side == 1 || side == 0 )
//	    	{
//	    		return blockIcon = cantaloupeIconTop[growthLevel];
//	    	}
//	    	
//			else return blockIcon = cantaloupeIcon[growthLevel];
//		}
//		
//		else return blockIcon;
//
//	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{	
				
		//init BB
		AxisAlignedBB pumpkinBounds =  GetGourdBounds(16, 16, 16);
		int type = this.getType(meta);
		int growthLevel = this.GetGrowthLevel(meta);
		
		if (meta == 0)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (meta == 1)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (meta == 2)
		{
			return GetGourdBounds(12, 12, 12);
		}
		else if (meta == 3)
		{
			return GetGourdBounds(16, 16, 16);
		}
		
		else if (meta == 4 || meta == 8 )
		{
			return GetGourdBounds(4, 4, 4);
		}
		else if (meta == 5 || meta == 9)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (meta == 6 || meta == 10)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (meta == 7 || meta == 11)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else return GetGourdBounds(16, 16, 16);
	
	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	}
	
	
//	@Override
//	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
//		renderer.renderStandardBlock( this, i, j, k );
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
//	@Override
//	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(iItemDamage) );
//		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
//	}

}
