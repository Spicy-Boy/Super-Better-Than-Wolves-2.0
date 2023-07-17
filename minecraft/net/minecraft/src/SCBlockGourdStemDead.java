package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdStemDead extends FCBlockPlants {

	private static final double m_dWidth = 0.25D;
	private static final double m_dHalfWidth = ( m_dWidth / 2D );
	
	protected SCBlockGourdStemDead(int iBlockID) {
		super(iBlockID, Material.plants);
		
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
        InitBlockBounds( 0.5F - m_dHalfWidth, 0F, 0.5F - m_dHalfWidth, 
        	0.5F + m_dHalfWidth, 0.25F, 0.5F + m_dHalfWidth );
        
        setStepSound( soundGrassFootstep );
        
        setUnlocalizedName("SCBlockStemDead");
	}

	
//----------- Client Side Functionality -----------//
    
    private Icon[] stemArray;

    @Override
    public void registerIcons( IconRegister register )
    {
		
		stemArray = new Icon[8];

        for ( int iTempIndex = 0; iTempIndex < stemArray.length; iTempIndex++ )
        {
        	stemArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinStemDead_" + iTempIndex );
        }
        
        blockIcon = stemArray[7];
 
    }

    @Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
    	int iGrowthLevel = GetGrowthLevel( blockAccess, i, j, k );
    	
    	return stemArray[iGrowthLevel];
    }

    protected int GetGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetGrowthLevel( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 7;
    }
	
	// --- Render --- //
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		IBlockAccess blockAccess = renderer.blockAccess;
		
		Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, i, j, k));
		//renderer.renderCrossedSquares(this, i, j, k);

		this.renderLargeCrossedSquares(renderer, i, j, k);
		
		FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );
		return true;
	}
	
	public boolean renderLargeCrossedSquares(RenderBlocks r, int par2, int par3, int par4)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;


        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;
        r.drawCrossedSquares(this, blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 2.0F);
        
		return true;

    }	
	

}
