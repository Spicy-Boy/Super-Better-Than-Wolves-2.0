package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinWhiteFresh extends SCBlockPumpkinBase {

	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	
	protected SCBlockPumpkinWhiteFresh(int iBlockID, int stemBlock, int vineBlock, int flowerBlock) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock);
		setTickRandomly( true ); 
		setUnlocalizedName("fcBlockPumpkinFresh");
		setCreativeTab(CreativeTabs.tabDecorations);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        
	}
	
	private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
//	@Override
//	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
//		//super.RenderBlock(renderer, i, j, k);
//		
//		IBlockAccess blockAccess = renderer.blockAccess;
//		int meta = blockAccess.getBlockMetadata(i, j, k);
//		int growthLevel = GetGrowthLevel(meta);
//		
//		if (growthLevel == 0) {
//			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 3/16D)); // stage 0 = 6x6x6
//			renderer.renderStandardBlock( this, i, j, k );
//		}else if (growthLevel == 1){
//			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 1 = 8x8x8
//			renderer.renderStandardBlock( this, i, j, k );
//		}else if (growthLevel == 2){
//			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 12x12x12
//			renderer.renderStandardBlock( this, i, j, k );
//		}else if (growthLevel == 3){
//			renderer.setRenderBounds(GetPumpkinBounds(5/16D, 6/16D)); // stage 2 = 14x14x14
//			renderer.renderStandardBlock( this, i, j, k );
//		}
//		
//		
//		this.renderVineConnector( renderer, i, j, k);
//
//		return true;
//	}

//----------- Client Side Functionality -----------//
    
//    protected Icon m_IconTop;
//    private Icon[] m_iconArray;
//    private Icon[] m_iconTopArray;
//    private Icon[] connectorIcon;

//    @Override
//    public void registerIcons( IconRegister register )
//    {
//		blockIcon = register.registerIcon( "pumpkin_side" );
//		
//		m_IconTop = register.registerIcon( "pumpkin_top" );
//		
//		m_iconArray = new Icon[4];
//
//        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
//        {
//            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteSide_" + iTempIndex );
//        }
//        
//        m_iconTopArray = new Icon[4];
//
//        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
//        {
//            m_iconTopArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinWhiteTop_" + iTempIndex );
//        }
//        
//        connectorIcon = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
//        {
//        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinConnector_" + iTempIndex );
//        }
//    }
    
	@Override
	protected Item ItemToDropOnExplode() {
		return Item.pumpkinSeeds;
	}

	@Override
	protected int ItemCountToDropOnExplode() {
		return 1;
	}

	@Override
	protected int AuxFXIDOnExplode() {
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
	}

	@Override
	protected DamageSource GetFallDamageSource() {
		return FCDamageSourceCustom.m_DamageSourcePumpkin;
	}

	@Override
	protected void setBlockOnFinishedFalling(EntityFallingSand entity, int i, int j, int k)
	{
		//shift metaLanding by 12 for pumpkinType
		entity.worldObj.setBlockAndMetadata(i, j, k, SCDefs.pumpkinHarvested.blockID, 0 + 12);
	}
    
}
