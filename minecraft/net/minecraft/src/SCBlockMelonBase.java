package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class SCBlockMelonBase extends BlockDirectional {

	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	
	protected SCBlockMelonBase(int iBlockID, int stemBlock, int vineBlock, int flowerBlock) {
		super(iBlockID, Material.pumpkin);
		setTickRandomly( true ); 
		
		setCreativeTab(CreativeTabs.tabDecorations);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{	
		super.updateTick(world, i, j, k, random);
		
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
        {
        	AttemptToGrow( world, i, j, k, random );
        }
	}

	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.99F;
    }
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	protected int GetGrowthLevel( int meta) {
		
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	protected void AttemptToGrow( World world, int i, int j, int k, Random random ) 
    {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		int lightLevel = 9;
		int meta = world.getBlockMetadata(i, j, k);
		
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 &&  
    		world.GetBlockNaturalLightValue( i, j + 1, k ) >= lightLevel )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && 
	        	(blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j, k ) ||
	        	blockBelow.CanWildVegetationGrowOnBlock(world, i, j, k) || blockBelow.CanReedsGrowOnBlock(world, i, j, k) || blockBelow.blockID == Block.mycelium.blockID ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );

	    		
	            if ( random.nextFloat() <= fGrowthChance && GrowthLevel < 3) 
				{
	                world.setBlockMetadataWithNotify( i, j, k, meta + 4 );
	                System.out.println("pumpkin grow stage: "+ GrowthLevel);	            	
				}
	        }
	    }
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
	
	public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4, Icon icon)
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
        
        if (this.hasStemFacing(r, par2, par3, par4))
        {
        	this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F, icon);
        }
        
		return true;

    }
    
	private boolean hasStemFacing( RenderBlocks r, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	    int iTargetFacing = 0;
	    
	    int dir = this.getDirection(r.blockAccess.getBlockMetadata(i, j, k));
	    	
	    if (dir == 0) {
	    	iTargetFacing = 2;
	    }
	    else if (dir == 1) {
	    	iTargetFacing = 5;
	    }
	    else if (dir == 2) {
	    	iTargetFacing = 3;
	    }
	    else if (dir == 3) {
	    	iTargetFacing = 4;
	    }
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = r.blockAccess.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    
	    if ( targetBlockID == this.stemBlock || targetBlockID == this.vineBlock || targetBlockID == this.flowerBlock)
	    {	
	    	return true;
	    }
	    else return false;
	}

	/**
     * Utility function to draw crossed swuares
     */
    public void drawConnector(Block block, int meta, double x, double y, double z, float scale, Icon icon)
    {
        Tessellator tess = Tessellator.instance;
        int growthLevel = this.GetGrowthLevel(meta);
        
        int dir = meta & 3;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double var20 = (double)scale;
        
        //dir 0: North
        double minX = x + 0.5D;// - var20;
        double maxX = x + 0.5D;// + var20;
        double minZ = z - 0.5D;// - var20;
        double maxZ = z + 0.5D + var20;
        
        
        if (dir == 3) { //west
            minX = x - 0.5D;// - var20;
            maxX = x + 0.5D + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
            
        } else if (dir == 2) {  //South 	
            minX = x + 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0.5D - var20;
            maxZ = z + 1.5D;// + var20;
            
        } else if (dir == 1) { //east  	
            minX = x + 0.5D - var20;
            maxX = x + 1.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
        }
        
        
        if (dir == 3 || dir == 0) {
            tess.addVertexWithUV(minX, y + (2 * (double)scale) , minZ, minU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, minU, minV);
        } else {
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
        }

    }
	
	public boolean GetExtendsAlongFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return GetExtendsAlongAxis( blockAccess, i, j, k, ConvertFacingToAxis( iFacing ) );
	}
	
	public boolean GetExtendsAlongAxis( IBlockAccess blockAccess, int i, int j, int k, int iAxis )
	{
		return GetExtendsAlongAxisFromMetadata( blockAccess.getBlockMetadata( i, j, k ), iAxis );		
	}
	
	public boolean GetExtendsAlongAxisFromMetadata( int iMetadata, int iAxis )
	{
		return ( iMetadata & ( 1 << iAxis ) ) > 0;

	}
	
	static public int ConvertFacingToAxis( int iFacing )
	{
		if ( iFacing == 4 || iFacing == 5 )
		{
			return 0;
		}
		else if ( iFacing == 0 || iFacing == 1 )
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	
	public AxisAlignedBB GetBoundsFromPoolForStringToFacing( int iFacing )
	{
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB( 
			0.4D, 0.5D, 0.4D, 
    		0.6D, 1.5D, 0.6D);
		
		box.TiltToFacingAlongJ( iFacing );
		
		return box;
	}
	
	
//----------- Client Side Functionality -----------//
    
//    protected Icon m_IconTop;
//    protected Icon[] m_iconArray;
//    protected Icon[] m_iconTopArray;
//    protected Icon[] connectorIcon;
//
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
//            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
//        }
//        
//        m_iconTopArray = new Icon[4];
//
//        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
//        {
//            m_iconTopArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
//        }
//        
//        connectorIcon = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
//        {
//        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinConnector_" + iTempIndex );
//        }
//    }
//
//    
//    @Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	int growthLevel = GetGrowthLevel(iMetadata);
//    	
//    	if ( iSide == 1 || iSide == 0 )
//    	{
//    		return m_iconTopArray[growthLevel];
//    	}
//    	
//    	return m_iconArray[growthLevel];
//    }
    
}
