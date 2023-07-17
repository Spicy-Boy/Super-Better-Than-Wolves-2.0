package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinJack extends FCBlockJackOLantern {

	protected SCBlockPumpkinJack(int iBlockID) {
		super(iBlockID);
		setTickRandomly(true);
        
        setHardness(1.0F);
        SetBuoyant();
        
        setStepSound(soundWoodFootstep);
        setLightValue(1.0F);
        setUnlocalizedName("SCBlockPumpkinJack");
        
        setCreativeTab(CreativeTabs.tabBlock);
	}
	
    public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        
        if (blockAccess.getBlockMetadata(i, j, k) <= 3) //mature pumpkin
        {
            return true;
        }
        else return false;
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int iSide) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		CheckForExtinguish( world, i, j, k );
    }
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
		CheckForExtinguish( world, i, j, k );
    }	
	
    @Override
    public boolean GetCanBlockLightItemOnFire( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean CanBeGrazedOn( IBlockAccess blockAccess, int i, int j, int k, 
    	EntityAnimal animal )
    {
		return animal.CanGrazeOnRoughVegetation();
    }
    
    private void CheckForExtinguish( World world, int i, int j, int k )
	{
		int iMetadata = world.getBlockMetadata( i, j, k );
		
//		if ( ( iMetadata & 8 ) != 0 )
//		{
			if ( HasWaterToSidesOrTop( world, i, j, k ) )
			{
				ExtinguishLantern( world, i, j, k );
			}
//		}
	}
    
	private void ExtinguishLantern( World world, int i, int j, int k )
	{
		int meta = world.getBlockMetadata( i, j, k );
		
		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.pumpkinCarved.blockID, meta);
		
        world.playAuxSFX( FCBetterThanWolves.m_iFireFizzSoundAuxFXID, i, j, k, 0 );							        
	}
	
	//SC
	
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
//	
//	@Override
//	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
//	{
//		IBlockAccess blockAccess = renderer.blockAccess;
//		
//		//Orange
//		if (iItemDamage < 4){
//			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 16/16D)); 
//		}
//		//Green
//		else if (iItemDamage >= 4 && iItemDamage < 8){
//			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 8/16D)); 
//
//		}
//		//Yellow
//		else if (iItemDamage >= 8 && iItemDamage < 12){
//			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D));
//		}
//		//White
//		else if (iItemDamage >= 12){
//			renderer.setRenderBounds(GetPumpkinBounds(5/16D, 6/16D));
//		}
//		
//		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
//	}
//	
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
    
    private Icon[] orangeIcon;
    private Icon[] orangeIconTop;
    private Icon[] greenIcon;
    private Icon[] greenIconTop;
    private Icon[] yellowIcon;
    private Icon[] yellowIconTop;
    private Icon[] whiteIcon;
    private Icon[] whiteIconTop;
    
    private Icon orangeFace;
    private Icon greenFace;
    private Icon yellowFace;
    private Icon whiteFace;
    
    private Icon faceIcon;
    private Icon topIcon;
	
//	public void registerIcons(IconRegister register)
//    {
//        this.faceIcon = register.registerIcon("pumpkin_face");
//        this.topIcon = register.registerIcon("pumpkin_top");
//        this.blockIcon = register.registerIcon("pumpkin_side");
//        
//        orangeFace = register.registerIcon("pumpkin_jack");
//        greenFace = register.registerIcon("SCBlockPumpkinGreenJackFront");
//        yellowFace = register.registerIcon("SCBlockPumpkinYellowJackFront");
//        whiteFace = register.registerIcon("SCBlockPumpkinWhiteJackFront");
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
