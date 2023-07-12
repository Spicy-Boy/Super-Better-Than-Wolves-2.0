package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockMelonFresh extends SCBlockMelonBase {

	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	
	protected SCBlockMelonFresh(int iBlockID, int stemBlock, int vineBlock, int flowerBlock) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock);
		setTickRandomly( true ); 
		setUnlocalizedName("SCBlockMelonFresh");
		setCreativeTab(CreativeTabs.tabDecorations);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        
	}
	
    private AxisAlignedBB GetMelonBounds(double width, double height, double length)
	{
    	AxisAlignedBB melonWater = null;
    	
    	melonWater = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - width/2, 0.0D, 8/16D - length/2, 
    			8/16D + width/2, height, 8/16D + length/2);
    	
    		return melonWater;
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{		
		IBlockAccess blockAccess = renderer.blockAccess;
		
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int growthLevel = GetGrowthLevel( meta );
		
		this.renderMelon( renderer, i, j, k, growthLevel );
		
		this.renderVineConnector( renderer, i, j, k, connectorIcon[growthLevel]);

		return true;
	}

	private void renderMelon(RenderBlocks renderer, int i, int j, int k, int growthLevel)
	{		
		AxisAlignedBB melonBounds = null;
				
		if ( growthLevel == 0 )
		{
			melonBounds = GetMelonBounds( 6/16D, 6/16D, 6/16D );
		}
		else if ( growthLevel == 1 )
		{
			melonBounds = GetMelonBounds( 8/16D, 8/16D, 8/16D );
		}
		else if ( growthLevel == 2 )
		{
			melonBounds = GetMelonBounds( 12/16D, 12/16D, 12/16D );
		}
		else if ( growthLevel == 3 )
		{
			melonBounds = GetMelonBounds( 16/16D, 16/16D, 16/16D );
		}
		
		renderer.setRenderBounds(melonBounds);
		renderer.renderStandardBlock( this, i, j, k );
	}

//----------- Client Side Functionality -----------//
    
    protected Icon m_IconTop;
    private Icon[] m_iconArray;
    private Icon[] m_iconTopArray;
    private Icon[] connectorIcon;

    @Override
    public void registerIcons( IconRegister register )
    {
		m_iconArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockMelonSide_" + iTempIndex );
        }
        
        m_iconTopArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconTopArray[iTempIndex] = register.registerIcon( "SCBlockMelonTop_" + iTempIndex );
        }
        
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinConnector_" + iTempIndex );
        }
    }
    
	@Override
	public Icon getIcon( int iSide, int iMetadata )
	{
		int growthLevel = GetGrowthLevel(iMetadata);
		
		if ( iSide == 1 || iSide == 0 )
		{
			return m_iconTopArray[growthLevel];
		}
		
		return m_iconArray[growthLevel];
	}
    
}
