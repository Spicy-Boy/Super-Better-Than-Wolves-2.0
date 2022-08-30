package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdStem extends FCBlockCrops {
	
	protected int vineBlock;
	protected int flowerBlock;
	public Block convertedBlock;
	private boolean canBePossessed;
	
	private static final double m_dWidth = 0.25D;
	private static final double m_dHalfWidth = ( m_dWidth / 2D );
	
	protected SCBlockGourdStem(int iBlockID, int vineBlock , int flowerBlock, Block convertedBlock) {
		super( iBlockID );
        
        this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        this.convertedBlock = convertedBlock;
        
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
        InitBlockBounds( 0.5F - m_dHalfWidth, 0F, 0.5F - m_dHalfWidth, 
        	0.5F + m_dHalfWidth, 0.25F, 0.5F + m_dHalfWidth );
        
        setStepSound( soundGrassFootstep );
	}
	
	@Override
	public float GetBaseGrowthChance(World world, int i, int j, int k) {
		return 0.1F;
	}

	public float GetVineGrowthChance( )
    {
    	return 0.25F;
    }
	
	public int GetLightLevelForGrowth()
	{
		return 9;
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		
		
        if ( UpdateIfBlockStays( world, i, j, k ) ) //checks if canBlockStay() and sets to air if not
        {	
        	if ( canBePossessed() && hasPortalInRange(world, i, j, k) && rand.nextFloat() <= 0.1F )
    	    {
    			this.becomePossessed(world, i, j, k, rand);
    			
    	    }        	
        	else if (!IsFullyGrown( world, i, j, k ) && checkTimeOfDay(world) ) //daytime
			{
				this.AttemptToGrow(world, i, j, k, rand); //growth chance is handles within this method
				
				if ( GetGrowthLevel(world, i, j, k) > 2  & rand.nextFloat() <= this.GetVineGrowthChance())
		    	{
		    		this.growVineAdjacent(world, i, j, k, rand);
		    	}
			}
        }
    }
	
	public boolean canBePossessed()
	{
		return this.canBePossessed;
	}
	
	public Block setCanBePossessed(boolean b)
	{
        this.canBePossessed = b;
        return this;
	}

	private void becomePossessed(World world, int i, int j, int k, Random rand)
    {
    	int meta = world.getBlockMetadata(i, j, k);
    	//System.out.println(meta);    	
    	//System.out.println(this.convertedBlock.blockID);
		world.setBlockAndMetadata(i, j, k, this.convertedBlock.blockID, meta);
		
	}

	protected boolean hasPortalInRange( World world, int i, int j, int k )
    {
    	int portalRange = 16;
    	
        for ( int iTempI = i - portalRange; iTempI <= i + portalRange; iTempI++ )
        {
            for ( int iTempJ = j - portalRange; iTempJ <= j + portalRange; iTempJ++ )
            {
                for ( int iTempK = k - portalRange; iTempK <= k + portalRange; iTempK++ )
                {
                    if ( world.getBlockId( iTempI, iTempJ, iTempK ) == Block.portal.blockID )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

	protected boolean checkTimeOfDay(World world) {
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		return (iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 );
	}
	
	@Override
	protected void AttemptToGrow( World world, int i, int j, int k, Random rand ) 
    {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 && world.GetBlockNaturalLightValue( i, j, k ) >= GetLightLevelForGrowth() )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );

	            if ( rand.nextFloat() <= fGrowthChance ) 
				{
	            	IncrementGrowthLevel( world, i, j, k );	            	
				}
	        }
	    }
    }
	
	private void growVineAdjacent( World world, int i, int j, int k, Random random ) {
		int targetDirection = random.nextInt(4);
		
		int directionI = Direction.offsetX[targetDirection];
		int directionK = Direction.offsetZ[targetDirection];

		int finalI = i + directionI;
		int finalK = k + directionK;
		
		if (CanGrowVineAt( world, finalI, j, finalK ))
		{
			world.setBlockAndMetadataWithNotify( finalI, j, finalK, this.vineBlock, targetDirection);
		}
	}

	protected boolean CanGrowVineAt( World world, int i, int j, int k )
    {
		int blockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[blockID];
		
		//if the block at the targetPos is null or isReplaceable()
        if ( (block == null || FCUtilsWorld.IsReplaceableBlock( world, i, j, k) )
        		//but not a stem, vine or flowering vine
        		&& ( blockID != this.blockID || blockID != this.vineBlock || blockID != this.flowerBlock ) )
        {
			// CanGrowOnBlock() to allow vine to grow on tilled earth and such
			if ( world.doesBlockHaveSolidTopSurface( i, j - 1, k ) || CanGrowOnBlock( world, i, j - 1, k ) )
            {				
				return true;
            }
        }
        
        return false;
    }
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k );
    }
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) && blockOn.CanWildVegetationGrowOnBlock(world, i, j, k);
    }	

	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}	
	
	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 7;
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
	

}
