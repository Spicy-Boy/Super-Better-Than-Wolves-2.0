package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinStem extends FCBlockCrops {
	
	
	
	private static final double m_dWidth = 0.25D;
	private static final double m_dHalfWidth = ( m_dWidth / 2D );
	
	protected SCBlockPumpkinStem(int iBlockID) {
		super( iBlockID);
		
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
        InitBlockBounds( 0.5F - m_dHalfWidth, 0F, 0.5F - m_dHalfWidth, 
        	0.5F + m_dHalfWidth, 0.25F, 0.5F + m_dHalfWidth );
        
        setStepSound( soundGrassFootstep );
        
        setUnlocalizedName( "pumpkinStem" ); 
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k );
    }
	

	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return Item.pumpkinSeeds.itemID;
	}
	
	@Override
	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F; // 0.5F = 50% 
    }
	
	public float GetVineGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) && blockOn.CanWildVegetationGrowOnBlock(world, i, j, k);
    }
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	System.out.println("stem: update tick");
        	System.out.println("my meta is:" + world.getBlockMetadata(i, j, k));
        	
	        if ( world.provider.dimensionId != 1 && !IsFullyGrown( world, i, j, k ) )
	        {
	        	this.AttemptToGrow( world, i, j, k, rand );
	        }
	        
	        int GrowthLevel = GetGrowthLevel(world, i, j, k);
	        
	        if (GrowthLevel > 3 && GrowthLevel < 8  ) {
	        	
	        	this.AttemptToGrowVine(world, i, j, k, rand);
	        }
	        
	        
        }
    }
	
	private void GrowVineAdjacent( World world, int i, int j, int k, Random rand ) {
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	    int iTargetFacing = 0;
	    
	    if ( HasSpaceToGrow( world, i, j, k ) )
	    {
	    	// if the plant doesn't have space around it to grow, 
	    	// the fruit will crush its own stem
	    	
	        iTargetFacing = rand.nextInt( 4 ) +2;
	    	
	        targetPos.AddFacingAsOffset( iTargetFacing );
	    }
	    
	    if ( CanGrowVineAt( world, targetPos.i, targetPos.j, targetPos.k ) )
	    {	
	    	if (iTargetFacing == 2)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 2 ); //TODO change back to Sleeping
	    		
	    	} else if (iTargetFacing == 3)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 0 );//TODO change back to Sleeping
	    		
	    	} else if (iTargetFacing == 4)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 1 );//TODO change back to Sleeping
	    	} else if (iTargetFacing == 5)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 3 );//TODO change back to Sleeping
	    	}
	    }

	}
	
	protected void AttemptToGrowVine( World world, int i, int j, int k, Random rand )
	{
		float vineGrowthChance = GetVineGrowthChance(world, i, j, k);
		
		if ( rand.nextFloat() <= vineGrowthChance ) 
		{
    		GrowVineAdjacent(world, i, j, k, rand);
        	
		}
	}
	
	@Override
	protected void AttemptToGrow( World world, int i, int j, int k, Random rand ) 
    {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 &&  
    		world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && 
	        	blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	    		
	    		
	    		System.out.println("stem: growth chance: " + fGrowthChance);

	            if ( rand.nextFloat() <= fGrowthChance ) 
				{
	            	//Grow stem
	            	System.out.println("stem: growing!");
	            	
	            	IncrementGrowthLevel( world, i, j, k );
	  
		            System.out.println("stem growth is:" + GrowthLevel);
		            
	            	
				}
	        }
	    }
    }
	
	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 7;
    }
	
	protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 7;
    }
	
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
//----------- Client Side Functionality -----------//
    
    private Icon[] stemArray;

    @Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "SCBlockPumpkinStem_7" );
		
		stemArray = new Icon[8];

        for ( int iTempIndex = 0; iTempIndex < stemArray.length; iTempIndex++ )
        {
        	stemArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinStem_" + iTempIndex );
        }
 
    }

    @Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
    	int iGrowthLevel = GetGrowthLevel( blockAccess, i, j, k );
    	
    	return stemArray[iGrowthLevel];
    }

//	@Override
//	public void updateTick( World world, int i, int j, int k, Random random )
//	{
//	    
//	    if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
//	    {
//	        if ( world.getBlockLightValue( i, j + 1, k ) >= 9)
//	        {
//	            this.CheckForGrowth( world, i, j, k, random );
//	            System.out.println("checking growth for vine");  
//	        }
//	    }
//	}
//	 
//	private void CheckForGrowth( World world, int i, int j, int k, Random rand )
//	{
//        if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 &&
//        	world.getBlockLightValue( i, j + 1, k ) >= 9 )
//        {
//	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
//	        
//	        if ( blockBelow != null && 
//	        	blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
//	        {
//	    		float fGrowthChance = 0.8F * //was 0.2F
//	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
//	    		//System.out.println("growth chance: " + fGrowthChance);
//	    		
//	            if ( rand.nextFloat() <= fGrowthChance )
//	            {
//	                int iMetadata = world.getBlockMetadata( i, j, k );
//	
//	                if ( iMetadata < 2 ) //was 14
//	                {
//	                    ++iMetadata;
//	                    
//	                    world.setBlockMetadataWithNotify( i, j, k, iMetadata );    
//	                    //System.out.println("metadata: " + iMetadata);
//	                }
//	                else if ( iMetadata == 2 ) //was 14
//	                {
//	                    FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
//	                    int iTargetFacing = 0;
//	                    
//	                    if ( HasSpaceToGrow( world, i, j, k ) )
//	                    {
//	                    	// if the plant doesn't have space around it to grow, 
//	                    	// the fruit will crush its own stem
//	                    	
//		                    iTargetFacing = rand.nextInt( 4 ) + 2;
//		                	
//		                    targetPos.AddFacingAsOffset( iTargetFacing );
//	                    }
//	                    
//	                    if ( CanGrowVineAt( world, targetPos.i, targetPos.j, targetPos.k ) )
//	                    {	
//	                    	blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
//	                    		
//	                    	world.setBlockWithNotify( targetPos.i, targetPos.j, targetPos.k, 
//	                    		SCDefs.pumpkinVine.blockID );
//	                    	
//	                    	//System.out.println("growing vine");
//	                    	
//	                    	if ( iTargetFacing != 0 )
//	                    	{
//	                    		SCDefs.pumpkinVine.AttachToFacing( world, 
//	                    			targetPos.i, targetPos.j, targetPos.k, 
//	                    			Block.GetOppositeFacing( iTargetFacing ) );
//	                    		
//	                            world.setBlockMetadataWithNotify( i, j, k, 15 );
//	                    	}   
//	                    }
//	                }
//	            }
//	        }
//        }
//	}
//	
	protected boolean HasSpaceToGrow( World world, int i, int j, int k )
    {
        for ( int iTargetFacing = 2; iTargetFacing <= 5; iTargetFacing++ )
        {
        	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
        	
            targetPos.AddFacingAsOffset( iTargetFacing );
            
            if ( CanGrowVineAt( world, targetPos.i, targetPos.j, targetPos.k ) )
            {
            	System.out.println("CanGrowVineAt");
            	return true;
            }
        }
        
        System.out.println("Can NOT GrowVineAt");
        return false;
    }
	
	protected boolean CanGrowVineAt( World world, int i, int j, int k )
    {
		int iBlockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[iBlockID];
		
        if ( FCUtilsWorld.IsReplaceableBlock( world, i, j, k ) ||
        	block == null &&
    		iBlockID != Block.cocoaPlant.blockID && 
    		iBlockID != SCDefs.pumpkinStem.blockID && 
    		iBlockID != SCDefs.pumpkinVine.blockID && 
    		iBlockID != SCDefs.pumpkinVineFlowering.blockID )
        {
			// CanGrowOnBlock() to allow fruit to grow on tilled earth and such
			if ( world.doesBlockHaveSolidTopSurface( i, j - 1, k ) ||
				CanGrowOnBlock( world, i, j - 1, k ) ) 
            {				
				return true;
            }
        }
        
        return false;
    }

	
	
	

}
