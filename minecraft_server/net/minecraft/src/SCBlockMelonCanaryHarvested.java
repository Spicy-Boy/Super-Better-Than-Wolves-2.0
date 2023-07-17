package net.minecraft.src;

import java.util.List;

public class SCBlockMelonCanaryHarvested extends SCBlockGourdHarvested {

	protected SCBlockMelonCanaryHarvested(int iBlockID) {
		super(iBlockID);
		
		setHardness(1.0F);
        
        setStepSound(soundWoodFootstep);
        
        setUnlocalizedName("SCBlockMelonCanaryHarvested");
		
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
	protected Item ItemToDropOnExplode(int meta)
	{
		return SuperBTWDefinitions.canaryMelonSeeds;
	}
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 4;
		}
		else return 0;
	}
	
	
	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		return SocksCropsAddon.m_iMelonCanaryExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourceMelon;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 8));
		par3List.add(new ItemStack(par1, 1, 12));
    }
	
	
	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		
		if (growthLevel == 0)
		{
			return 0;
		}
		else if (growthLevel == 1)
		{
			return 4;
		}
		else if (growthLevel == 2)
		{
			return 8;
		}
		return 12;
	}
	
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack itemStack)
    {
        int dir = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        int shiftGrowthLevel = itemStack.getItemDamage();
        
//        par1World.setBlockMetadataWithNotify(par2, par3, par4, dir + shiftGrowthLevel, 2);
        par1World.setBlockMetadata(par2, par3, par4, dir + shiftGrowthLevel, 2);
    }

	public static int GetGrowthLevel(int meta)
    {
		if (meta < 4)
		{
			return 0;
		}
		else if (meta >= 4 && meta < 8) 
		{
			return 1;
		}
		else if (meta >= 8 && meta < 12) 
		{
			return 2;
		} 
		else return 3;
        
    }
	
	public static int GetDirection( int meta)
	{
		return meta & 3;
	}

		
	//----------- Render/Icon Functionality -----------//
	
	private Icon[] yellowIcon;
	private Icon[] yellowIconTop;
	
//	@Override
//  	public void registerIcons( IconRegister register )
//  	{
//
//		//Yellow
//		yellowIcon = new Icon[4];
//		
//  		for ( int iTempIndex = 0; iTempIndex < yellowIcon.length; iTempIndex++ )
//		{
//  			yellowIcon[iTempIndex] = register.registerIcon( "SCBlockMelonYellowSide_" + iTempIndex );
//		}
//	
//  		yellowIconTop = new Icon[4];
//	
//		for ( int iTempIndex = 0; iTempIndex < yellowIconTop.length; iTempIndex++ )
//		{
//			yellowIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonYellowTop_" + iTempIndex );
//		}
//		
//	}
	
//	@Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	int growthLevel = GetGrowthLevel(iMetadata);
//    	int dir = getDirection(iMetadata);
//
//    	
//    	if (iSide == 2 || iSide == 3 )
//    	{
//    		if (dir == 0 || dir == 2)
//    		{
//    			return yellowIconTop[growthLevel];
//    		}
//    		else return yellowIcon[growthLevel];
//    	}
//    	else if (iSide == 4 || iSide == 5 )
//    	{
//    		if (dir == 1 || dir == 3)
//    		{
//    			return yellowIconTop[growthLevel];
//    		}
//    		else return yellowIcon[growthLevel];
//    	}
//    	else return yellowIcon[growthLevel];
//    }
//	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( int meta )
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
//	
//	private void setTextureRotations(RenderBlocks renderer, int meta)
//	{
//		int dir = this.getDirection(meta);
//		
//		if (dir == 0 || dir == 2)
//		{
//			renderer.SetUvRotateTop(0);
//			renderer.SetUvRotateBottom(0);
//			renderer.SetUvRotateNorth(1);
//			renderer.SetUvRotateSouth(1);
//		}
//		else if (dir == 1 || dir == 3)
//		{
//			renderer.SetUvRotateTop(1);
//			renderer.SetUvRotateBottom(1);
//			renderer.SetUvRotateEast(1);
//			renderer.SetUvRotateWest(1);
//		}
//			
//		
//		
//	}
//	
//	@Override
//	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
//		
//		this.setTextureRotations(renderer, meta);
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
//		renderer.renderStandardBlock( this, i, j, k );
//		renderer.ClearUvRotation();
//
//		return true;
//	}
//
//	@Override
//	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		this.setTextureRotations(renderer, meta);		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
//		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
//		renderer.ClearUvRotation();
//	}
//	
//	@Override
//	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		this.setTextureRotations(renderer, iItemDamage);
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(iItemDamage) );
//		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
//		renderer.ClearUvRotation();
//	}
	
	
	

}
