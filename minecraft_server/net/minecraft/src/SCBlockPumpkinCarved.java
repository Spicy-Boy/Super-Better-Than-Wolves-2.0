package net.minecraft.src;

import java.util.List;

public class SCBlockPumpkinCarved extends FCBlockPumpkinCarved {
	
    /** Boolean used to seperate different states of blocks */
    private boolean blockType;
    private Icon topIcon;
    private Icon faceIcon;
	
	protected SCBlockPumpkinCarved(int iBlockID) {
		super(iBlockID);
		
		setHardness( 1F );
    	SetAxesEffectiveOn( true );
    	
    	SetBuoyant();
    	
    	setStepSound( soundWoodFootstep );
    	
    	setUnlocalizedName( "SCBlockPumpkinCarved" );
    	setCreativeTab(CreativeTabs.tabBlock);
	}
	
	//from Sock's newer code
	@Override
    public boolean isValidForArmorSlot(int armorType, ItemStack itemStack) {
        
        int type = itemStack.getItemDamage();
        
        return armorType == 0 && type == 3;
    }
    
    @Override
    public String getBlurOverlay(ItemStack itemStack) {
        
        return "%blur%/misc/pumpkinblur.png";
    }
	
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {

        
        if ( world.getBlockId(i, j - 1, k) == Block.fence.blockID ) //|| world.getBlockId(i, j - 1, k) != Block.fence.blockID
		{
			return true;
		}
        
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true )) //|| world.getBlockId(i, j - 1, k) != Block.fence.blockID
		{
			return false;
		}
		
        return super.canPlaceBlockAt( world, i, j, k );
    }
    
    
    @Override
    public boolean canBlockStay(World par1World, int i, int j, int k) {
    	
        if ( par1World.getBlockId(i, j - 1, k) == Block.fence.blockID || FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( par1World, i, j - 1, k, 1, true )) //|| world.getBlockId(i, j - 1, k) != Block.fence.blockID
		{
			return true;
		}
        
        return false;
    }
    
    //from campfire
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {    	
        if ( !canBlockStay(world, i, j, k) ) //|| world.getBlockId(i, j - 1, k) != Block.fence.blockID
		{
        	world.setBlockToAir(i, j, k);
        	
        	Explode(world, i, j, k, world.getBlockMetadata(i, j, k));
        	
		}
    }
    
    //make pumpkins grow every day-- change base growth chance in pumpkin growing
    
    private void Explode( World world, double posX, double posY, double posZ, int entityMeta )
    {
    	
    	NotifyNearbyAnimalsFinishedFalling( world, MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ) );
        
        world.playAuxSFX( AuxFXIDOnExplode(world, (int)posX, (int)posY, (int)posZ, entityMeta), 
    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
    		0 );
    }
    
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int iSide) {
		// TODO Auto-generated method stub
		return true;
	}
	
    @Override
    public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        
        if (blockAccess.getBlockMetadata(i, j, k) <= 3) //mature pumpkin
        {
            return true;
        }
        else return false;
    }
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 7));
		par3List.add(new ItemStack(par1, 1, 11));
		par3List.add(new ItemStack(par1, 1, 15));

    }	
	
	public void onBlockPlacedBy(World world, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack stack)
    {
        int playerRotation = ((MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        
        int damage = stack.getItemDamage();
        
        if(damage >= 4 && damage < 8)
        {
        	playerRotation = playerRotation + 4;
        }
        else if(damage >= 8 && damage < 12)
        {
        	playerRotation = playerRotation + 8;
        }
        else if(damage >= 12)
        {
        	playerRotation = playerRotation + 12;
        } 
        
        world.setBlockMetadataWithNotify(par2, par3, par4, playerRotation);
    }

	private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
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
	
//	@Override
//	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
//		
//		IBlockAccess blockAccess = renderer.blockAccess;
//		int meta = blockAccess.getBlockMetadata(i, j, k);
//		
//		//Orange
//		if (meta == 0 || meta == 1 || meta == 2 || meta == 3){
//			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 16/16D)); 
//			renderer.renderStandardBlock( this, i, j, k );
//		}
//		//Green
//		if (meta == 4 || meta == 5 || meta == 6 || meta == 7){
//			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 8/16D)); 
//			renderer.renderStandardBlock( this, i, j, k );
//		}
//		//Yellow
//		if (meta == 8 || meta == 9 || meta == 10 || meta == 11){
//			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D));
//			renderer.renderStandardBlock( this, i, j, k );
//		}
//		//White
//		if (meta == 12 || meta == 13 || meta == 14 || meta == 15){
//			renderer.setRenderBounds(GetPumpkinBounds(5/16D, 6/16D));
//			renderer.renderStandardBlock( this, i, j, k );
//		}
//
//		return true;
//	}
	
//	@Override
//	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(iItemDamage) );
//		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
//	}
	
	private AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta) {
		
		//Orange
		if (meta == 0 || meta == 1 || meta == 2 || meta == 3){
			return GetPumpkinBounds(8/16D, 16/16D);
		}
		//Green
		else if (meta == 4 || meta == 5 || meta == 6 || meta == 7){
			return GetPumpkinBounds(8/16D, 8/16D);
		}
		//Yellow
		else if (meta == 8 || meta == 9 || meta == 10 || meta == 11){
			return GetPumpkinBounds(6/16D, 12/16D);
		}
		//White
		else return GetPumpkinBounds(5/16D, 6/16D);
	}
	
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	
	}

//	@Override
//	public Icon getIcon( int iSide, int iMetadata )
//	{
//	 	if (iMetadata == 1)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return orangeIconTop[3];
//	     	}else if ( iSide == 4 )
//	     	{
//	     		return orangeFace;
//	     	}
//	     	
//	 		else return orangeIcon[3];
//	 	}
//	 	else if (iMetadata == 2)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return orangeIconTop[3];
//	     	}else if ( iSide == 2 )
//	     	{
//	     		return orangeFace;
//	     	}
//	     	
//	 		else return orangeIcon[3];
//	 	}
//	 	else if (iMetadata == 3)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return orangeIconTop[3];
//	     	}else if ( iSide == 5 )
//	     	{
//	     		return orangeFace;
//	     	}
//	     	
//	 		else return orangeIcon[3];
//	 	}
//	 	else if (iMetadata == 4)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return greenIconTop[3];
//	     	}else if ( iSide == 3 )
//	     	{
//	     		return greenFace;
//	     	}
//	     	
//	 		else return greenIcon[3];
//	 	}
//	 	else if (iMetadata == 5)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return greenIconTop[3];
//	     	}else if ( iSide == 4 )
//	     	{
//	     		return greenFace;
//	     	}
//	     	
//	 		else return greenIcon[3];
//	 	}
//	 	else if (iMetadata == 6)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return greenIconTop[3];
//	     	}else if ( iSide == 2 )
//	     	{
//	     		return greenFace;
//	     	}
//	     	
//	 		else return greenIcon[3];
//	 	}
//	 	else if (iMetadata == 7)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return greenIconTop[3];
//	     	}else if ( iSide == 5 )
//	     	{
//	     		return greenFace;
//	     	}
//	     	
//	 		else return greenIcon[3];
//	 	}
//	 	else if (iMetadata == 8)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return yellowIconTop[3];
//	     	}else if ( iSide == 3 )
//	     	{
//	     		return yellowFace;
//	     	}
//	     	
//	 		else return yellowIcon[3];
//	 	}
//	 	else if (iMetadata == 9)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return yellowIconTop[3];
//	     	}else if ( iSide == 4 )
//	     	{
//	     		return yellowFace;
//	     	}
//	     	
//	 		else return yellowIcon[3];
//	 	}
//	 	else if (iMetadata == 10)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return yellowIconTop[3];
//	     	}else if ( iSide == 2 )
//	     	{
//	     		return yellowFace;
//	     	}
//	     	
//	 		else return yellowIcon[3];
//	 	}
//	 	else if (iMetadata == 11)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return yellowIconTop[3];
//	     	}else if ( iSide == 5 )
//	     	{
//	     		return yellowFace;
//	     	}
//	     	
//	 		else return yellowIcon[3];
//	 	}
//	 	else if (iMetadata == 12)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return whiteIconTop[3];
//	     	}else if ( iSide == 3 )
//	     	{
//	     		return whiteFace;
//	     	}
//	     	
//	 		else return whiteIcon[3];
//	 	}
//	 	else if (iMetadata == 13)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return whiteIconTop[3];
//	     	}else if ( iSide == 4 )
//	     	{
//	     		return whiteFace;
//	     	}
//	     	
//	 		else return whiteIcon[3];
//	 	}
//	 	else if (iMetadata == 14)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return whiteIconTop[3];
//	     	}else if ( iSide == 2 )
//	     	{
//	     		return whiteFace;
//	     	}
//	     	
//	 		else return whiteIcon[3];
//	 	}
//	 	else if (iMetadata == 15)
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return whiteIconTop[3];
//	     	}else if ( iSide == 5 )
//	     	{
//	     		return whiteFace;
//	     	}
//	     	
//	 		else return whiteIcon[3];
//	 	}
//	 	else //meta 0
//	 	{
//			if ( iSide == 1 || iSide == 0 )
//	     	{
//	     		return orangeIconTop[3];
//	     	}else if ( iSide == 3 )
//	     	{
//	     		return orangeFace;
//	     	}
//	     	
//	 		else return orangeIcon[3];
//	 	}
//	}
    
//    private Icon[] orangeIcon;
//    private Icon[] orangeIconTop;
//    private Icon[] greenIcon;
//    private Icon[] greenIconTop;
//    private Icon[] yellowIcon;
//    private Icon[] yellowIconTop;
//    private Icon[] whiteIcon;
//    private Icon[] whiteIconTop;
//    
//    private Icon orangeFace;
//    private Icon greenFace;
//    private Icon yellowFace;
//    private Icon whiteFace;
//	
//	public void registerIcons(IconRegister register)
//    {
//        this.faceIcon = register.registerIcon("pumpkin_face");
//        this.topIcon = register.registerIcon("pumpkin_top");
//        this.blockIcon = register.registerIcon("pumpkin_side");
//        
//        orangeFace = register.registerIcon("pumpkin_face");
//        greenFace = register.registerIcon("SCBlockPumpkinGreenCarvedFront");
//        yellowFace = register.registerIcon("SCBlockPumpkinYellowCarvedFront");
//        whiteFace = register.registerIcon("SCBlockPumpkinWhiteCarvedFront");
//        
//      //Orange
//    	orangeIcon = new Icon[4];
//		
//		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
//		{
//			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
//		}
//		
//		orangeIconTop = new Icon[4];
//		
//		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
//		{
//			orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
//		}
//		
//
//		//Green
//		greenIcon = new Icon[4];
//		
//        for ( int iTempIndex = 0; iTempIndex < greenIcon.length; iTempIndex++ )
//        {
//        	greenIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenSide_" + iTempIndex );
//        }
//        
//        greenIconTop = new Icon[4];
//
//        for ( int iTempIndex = 0; iTempIndex < greenIconTop.length; iTempIndex++ )
//        {
//        	greenIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinGreenTop_" + iTempIndex );
//        }
//        
//        //Yellow
//        yellowIcon = new Icon[4];
//		
//		for ( int iTempIndex = 0; iTempIndex < yellowIcon.length; iTempIndex++ )
//		{
//			yellowIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowSide_" + iTempIndex );
//		}
//		
//		yellowIconTop = new Icon[4];
//		
//		for ( int iTempIndex = 0; iTempIndex < yellowIconTop.length; iTempIndex++ )
//		{
//			yellowIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowTop_" + iTempIndex );
//		}
//		
//        //White
//		whiteIcon = new Icon[4];
//		
//		for ( int iTempIndex = 0; iTempIndex < whiteIcon.length; iTempIndex++ )
//		{
//			whiteIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteSide_" + iTempIndex );
//		}
//		
//		whiteIconTop = new Icon[4];
//		
//		for ( int iTempIndex = 0; iTempIndex < whiteIconTop.length; iTempIndex++ )
//		{
//			whiteIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteTop_" + iTempIndex );
//		}
//    }

	
}
