package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinFresh extends SCBlockPumpkinBase {

	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	
	protected SCBlockPumpkinFresh(int iBlockID, int stemBlock, int vineBlock, int flowerBlock) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock);
		setTickRandomly( true ); 
		setUnlocalizedName("SCBlockPumpkinFresh");
		setCreativeTab(CreativeTabs.tabDecorations);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        
	}

    private AxisAlignedBB GetPumpkinBounds(double size)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, size*2, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		//super.RenderBlock(renderer, i, j, k);
		
		IBlockAccess blockAccess = renderer.blockAccess;
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int growthLevel = GetGrowthLevel(meta);
		
		if (growthLevel == 0) {
			renderer.setRenderBounds(GetPumpkinBounds(3/16D)); // stage 0 = 6x6x6
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (growthLevel == 1){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D)); // stage 1 = 8x8x8
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (growthLevel == 2){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D)); // stage 2 = 12x12x12
			//renderer.renderStandardBlock( this, i, j, k );
		}else if (growthLevel == 3){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D)); // stage 2 = 14x14x14
			
		}
		renderer.renderStandardBlock( this, i, j, k );
		
		this.renderVineConnector( renderer, i, j, k);

		return true;
	}
	
	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta) {
		
		IBlockAccess blockAccess = renderer.blockAccess;
		//int meta = blockAccess.getBlockMetadata(i, j, k);
		int growthLevel = GetGrowthLevel(meta);
		
		if ( meta == 0 || meta == 1 || meta == 2 || meta == 3 ) {
			renderer.setRenderBounds(GetPumpkinBounds(3/16D)); // stage 0 = 6x6x6
			//renderer.renderStandardBlock( this, i, j, k );
		} else if ( meta == 4 || meta == 5 || meta == 6 || meta == 7 ) {
			renderer.setRenderBounds(GetPumpkinBounds(4/16D)); // stage 1 = 8x8x8
			//renderer.renderStandardBlock( this, i, j, k );
		} else if ( meta == 8 || meta == 9 || meta == 10 || meta == 11 ) {
			renderer.setRenderBounds(GetPumpkinBounds(6/16D)); // stage 2 = 12x12x12
			//renderer.renderStandardBlock( this, i, j, k );
		} else if ( meta == 12 || meta == 13 || meta == 14 || meta == 15 ) {
			renderer.setRenderBounds(GetPumpkinBounds(8/16D)); // stage 2 = 14x14x14
			//renderer.renderStandardBlock( this, i, j, k );
		}
		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta );
	}

//----------- Client Side Functionality -----------//
    
//    protected Icon m_IconTop;
//    private Icon[] m_iconArray;
//    private Icon[] m_iconTopArray;
//    private Icon[] connectorIcon;

    @Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "pumpkin_side" );
		
		m_IconTop = register.registerIcon( "pumpkin_top" );
		
		m_iconArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
        }
        
        m_iconTopArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconTopArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
        }
        
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinConnector_" + iTempIndex );
        }
    }

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
		int meta = entity.worldObj.getBlockMetadata(i, j, k);
		
		if (meta == 12 || meta == 13 || meta == 14 || meta == 15)
    	{
    		entity.worldObj.setBlockAndMetadata(i, j, k, SCDefs.pumpkinHarvested.blockID, 3);
    	}
    	else if (meta == 8 || meta == 9 || meta == 10 || meta == 11)
    	{
    		entity.worldObj.setBlockAndMetadata(i, j, k, SCDefs.pumpkinHarvested.blockID, 2);
    	}
    	else if (meta == 4 || meta == 5 || meta == 6 || meta == 7)
    	{
    		entity.worldObj.setBlockAndMetadata(i, j, k, SCDefs.pumpkinHarvested.blockID, 1);
    	}
    	else if (meta == 0 || meta == 1 || meta == 2 || meta == 3) {
    		entity.worldObj.setBlockAndMetadata(i, j, k, SCDefs.pumpkinHarvested.blockID, 0);
    	} 
		
		
		
	}
    
}
