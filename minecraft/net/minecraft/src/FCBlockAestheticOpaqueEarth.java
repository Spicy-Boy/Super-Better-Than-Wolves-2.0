// FCMOD

package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class FCBlockAestheticOpaqueEarth extends Block
{
    public final static int m_iSubtypeBlightLevel0 = 0;
    public final static int m_iSubtypeBlightLevel1 = 1;
    public final static int m_iSubtypeBlightLevel2 = 2;
    public final static int m_iSubtypeBlightRootsLevel2 = 3;
    public final static int m_iSubtypeBlightLevel3 = 4;
    public final static int m_iSubtypeBlightRootsLevel3 = 5;
    public final static int m_iSubtypePackedEarth = 6;
    public final static int m_iSubtypeDung = 7;
    
    public static final int[] m_iSubtypeToBlightLevel = new int[] { 0, 1, 2, 2, 3, 3, -1, -1 };
    
    public static final int[] m_iBlightLevelToSubtype = new int[] { 
    	m_iSubtypeBlightLevel0, m_iSubtypeBlightLevel1, 
    	m_iSubtypeBlightLevel2, m_iSubtypeBlightLevel3 };
    
    public final static int m_iNumSubtypes = 8;
    
    public FCBlockAestheticOpaqueEarth( int iBlockID )
    {
        super( iBlockID, Material.ground );
        
        setHardness( 0.6F );
        SetShovelsEffectiveOn( true );
    	SetHoesEffectiveOn();
        
        setTickRandomly( true );

        setStepSound( soundGravelFootstep );
        
//		setCreativeTab( CreativeTabs.tabBlock );
		
        setUnlocalizedName( "fcBlockAestheticOpaqueEarth" );        
    }
    
	@Override
	public int damageDropped( int iMetadata )
    {
		if ( IsBlightFromMetadata( iMetadata ) )
		{
			return 0;
		}
		
        return iMetadata; 
    }
    
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
		if ( IsBlightFromMetadata( iMetadata ) )
		{
			return FCBetterThanWolves.fcBlockDirtLoose.blockID;
		}
		
		return blockID;
    }

	@Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
		if ( IsBlightFromMetadata( iMetadata ) )
		{
	        if ( !world.isRemote )
	        {	        
		        int iNumDropped = 8;
		        
		        for( int iTempCount = 0; iTempCount < iNumDropped; iTempCount++ )
		        {
	                dropBlockAsItem_do( world, i, j, k, new ItemStack( FCBetterThanWolves.fcItemPileDirt ) );
		        }
	        }
		}
		else
		{
			super.dropBlockAsItemWithChance( world, i, j, k, iMetadata, fChance, iFortuneModifier );
		}
    }

	@Override
    public void RandomUpdateTick( World world, int i, int j, int k, Random rand )
    {
		int iSubtype = world.getBlockMetadata( i, j, k );
		
		if ( IsBlightFromMetadata( iSubtype ) )
		{
			BlightRandomUpdateTick( world, i, j, k, rand );
		}
    }

	@Override
    protected ItemStack createStackedBlock( int iMetadata )
    {
		// overriden to provide custom silk-touch drops
		
        int iItemDamage = iMetadata;

        if (  iMetadata == m_iSubtypeBlightLevel1 ||
        	iMetadata == m_iSubtypeBlightLevel2 ||
        	iMetadata == m_iSubtypeBlightRootsLevel2 )
        {
        	iItemDamage = m_iSubtypeBlightLevel0;
        }
        else if (  iMetadata == m_iSubtypeBlightRootsLevel3 )
        {
        	iItemDamage = m_iSubtypeBlightLevel3;
        }

        return new ItemStack( blockID, 1, iItemDamage );
    }

    @Override
    public float GetMovementModifier( World world, int i, int j, int k )
    {
    	float fModifier = 1F;
        	
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
		if ( iSubtype == m_iSubtypePackedEarth )
		{
			fModifier = 1.2F;
		}
		else if ( iSubtype == m_iSubtypeDung )
		{
			fModifier = 0.8F;
		}
    	
    	return fModifier;
    }
    
    @Override
    public StepSound GetStepSound( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
    	if ( iSubtype == m_iSubtypeDung )
    	{
    		return FCBetterThanWolves.fcStepSoundSquish;
    	}
    	
    	return stepSound;
    }
    
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	DropAsPiles( world, i, j, k, iMetadata, 1F );
    	
    	if ( iMetadata != m_iSubtypeDung && iMetadata != m_iSubtypePackedEarth  )
    	{
    		OnDirtDugWithImproperTool( world, i, j, k );
    	}
    }
    
	private void DropAsPiles( World world, int i, int j, int k, int iMetadata, float fChanceOfPileDrop )
	{
    	Item itemToDrop = FCBetterThanWolves.fcItemPileDirt;
    	int iCountToDrop = 6;
    	int iSubtype = iMetadata;
    	
    	if ( iSubtype == m_iSubtypePackedEarth )
    	{
    		iCountToDrop = 12;
    	}
    	else if ( iSubtype == m_iSubtypeDung )
    	{
    		itemToDrop = FCBetterThanWolves.fcItemDung;
    		iCountToDrop = 8;
    	}
    	
    	for ( int iTempCount = 0; iTempCount < iCountToDrop; iTempCount++ )
    	{
    		if ( world.rand.nextFloat() <= fChanceOfPileDrop )
    		{
	    		ItemStack tempStack = new ItemStack( itemToDrop );
	    		
	            dropBlockAsItem_do( world, i, j, k, tempStack );
    		}
    	}
	}
	
	@Override
    public boolean canDropFromExplosion(Explosion par1Explosion)
    {
        return false;
    }
    
	@Override
    public void onBlockDestroyedByExplosion( World world, int i, int j, int k, Explosion explosion )
    {
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		float fChanceOfPileDrop = 1F;
		
		if ( explosion != null )
		{
			fChanceOfPileDrop = 1F / explosion.explosionSize;
		}
		
        DropAsPiles( world, i, j, k, iMetadata, fChanceOfPileDrop );
    	
    	if ( iMetadata != m_iSubtypeDung && iMetadata != m_iSubtypePackedEarth  )
    	{
    		OnDirtDugWithImproperTool( world, i, j, k );
    	}
    }
	
	@Override
    public boolean CanSaplingsGrowOnBlock( World world, int i, int j, int k )
    {
		return IsBlightFromMetadata( world.getBlockMetadata( i, j, k ) );
    }
    
	@Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
	
	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return stack != null && stack.getItem() instanceof FCItemHoe;
    }
	
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	if ( IsBlightFromMetadata( world.getBlockMetadata( i, j, k ) ) )
    	{
	    	world.setBlockWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID );
	
	    	if ( !world.isRemote )
			{
	            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
			}
	    	
	    	return true;
    	}
    	
    	return false;
    }
    
	//------------- Class Specific Methods ------------//
	
	public boolean IsSurfaceBlightFromMetadata( int iMetadata )
	{
		return iMetadata >= m_iSubtypeBlightLevel0 && iMetadata <= m_iSubtypeBlightLevel3
			&& iMetadata != m_iSubtypeBlightRootsLevel2;
	}
	
	public boolean IsBlightFromMetadata( int iMetadata )
	{
		return iMetadata >= m_iSubtypeBlightLevel0 && iMetadata <= m_iSubtypeBlightRootsLevel3;
	}
	
	private int GetRootsSubtypeForLevel( int iLevel )
	{
		if ( iLevel >= 3 )
		{
			return m_iSubtypeBlightRootsLevel3;
		}
		else
		{
			return m_iSubtypeBlightRootsLevel2;
		}
	}
	
	private void BlightRandomUpdateTick( World world, int i, int j, int k, Random rand )
	{
		if ( !CheckForBlightSurfaceConversions( world, i, j, k ) )
		{
			int iBlockSubtype = world.getBlockMetadata( i, j, k );
			
			if ( iBlockSubtype == m_iSubtypeBlightLevel1 )
			{
				BlightKillLeaves( world, i, j, k, rand );
			}
			else if ( iBlockSubtype >= m_iSubtypeBlightLevel2 )
			{	
				BlightKillVinesAndLeaves( world, i, j, k, rand );
			}
			
			if ( world.provider.dimensionId != 1 )
			{
				CheckForBlightSpread( world, i, j, k, rand );
				
				CheckForBlightEvolution( world, i, j, k, rand );
			}
		}
	}
	
	private void CheckForSpreadToLocation( World world, int i, int j, int k, int iBlightSubtype )
	{
		int iTargetBlockID = world.getBlockId( i, j, k );
		
		if ( iTargetBlockID > 0 )
		{
			int iBlightLevel = m_iSubtypeToBlightLevel[iBlightSubtype];
			
			if ( iTargetBlockID == blockID )
			{
				// evolve lower level blight
				
				int iTargetMetadata = world.getBlockMetadata( i, j, k );
				int iTargetBlightLevel = m_iSubtypeToBlightLevel[iTargetMetadata];				
				
				if ( iTargetBlightLevel < iBlightLevel && iTargetBlightLevel >= 0 )
				{
					if ( IsSurfaceBlightFromMetadata( iTargetMetadata ) )
					{
						if ( iBlightLevel == 3 )
						{
							iTargetBlightLevel = 3;
						}
						else
						{
							iTargetBlightLevel++;
						}
						
                		world.setBlockMetadataWithNotify( i, j, k, 
                			m_iBlightLevelToSubtype[iTargetBlightLevel] ); 
					}
					else
					{
	            		world.setBlockMetadataWithNotify( i, j, k, m_iSubtypeBlightRootsLevel3 ); 
					}
				}				
			}
			else
			{
				Block targetBlock = Block.blocksList[iTargetBlockID];
				
				if ( targetBlock.GetCanBlightSpreadToBlock( world, i, j, k, iBlightLevel ) )
				{
					if ( iBlightLevel < 3 )
					{
						if ( Block.lightOpacity[world.getBlockId( i, j + 1, k )] <= 2 )
						{
	            			world.setBlockAndMetadataWithNotify( i, j, k, blockID, 
	            				m_iSubtypeBlightLevel0 );
						}
					}
					else
					{
						if ( Block.lightOpacity[world.getBlockId( i, j + 1, k )] <= 2 )
						{
	            			world.setBlockAndMetadataWithNotify( i, j, k, blockID, 
	            				m_iSubtypeBlightLevel3 );
						}
						else
						{
	            			world.setBlockAndMetadataWithNotify( i, j, k, blockID, 
	            				m_iSubtypeBlightRootsLevel3 );
						}
					}
				}
			}
		}			
	}
	
	private void CheckForBlightSpread( World world, int i, int j, int k, Random rand )
	{
		int iBlockSubtype = world.getBlockMetadata( i, j, k );
		
		if ( iBlockSubtype == m_iSubtypeBlightLevel0 )
		{
            int iRandI = i + rand.nextInt( 3 ) - 1;
            int iRandJ = j + rand.nextInt( 3 ) - 1;
            int iRandK = k + rand.nextInt( 3 ) - 1;
            
            CheckForSpreadToLocation( world, iRandI, iRandJ, iRandK, iBlockSubtype );
		}
		else if ( iBlockSubtype == m_iSubtypeBlightLevel1 )
		{	
			// check for spread
			
            for ( int iTempCount = 0; iTempCount < 2; iTempCount++ )
            {
                int iRandI = i + rand.nextInt( 3 ) - 1;
                int iRandJ = j + rand.nextInt( 4 ) - 1;
                int iRandK = k + rand.nextInt( 3 ) - 1;
                
                CheckForSpreadToLocation( world, iRandI, iRandJ, iRandK, iBlockSubtype );
            }
		}
		else // levels 2 & 3
		{
			// check for spread
			
            for ( int iTempCount = 0; iTempCount < 4; iTempCount++ )
            {
                int iRandI = i + rand.nextInt( 3 ) - 1;
                int iRandJ = j + rand.nextInt( 5 ) - 2;
                int iRandK = k + rand.nextInt( 3 ) - 1;
                
                CheckForSpreadToLocation( world, iRandI, iRandJ, iRandK, iBlockSubtype );
            }
            
            // grow roots
            
			int iRootsSubtype = GetRootsSubtypeForLevel( 
				m_iSubtypeToBlightLevel[iBlockSubtype] );
			
            if ( world.getBlockId( i, j - 1, k ) == Block.dirt.blockID )
            {
        		world.setBlockAndMetadataWithNotify( i, j - 1, k, blockID, iRootsSubtype );
            }
            
            if ( world.getBlockId( i, j + 1, k ) == Block.dirt.blockID )
            {
        		world.setBlockAndMetadataWithNotify( i, j + 1, k, blockID, iRootsSubtype );
            }
		}
	}
	
	private void CheckForBlightEvolution( World world, int i, int j, int k, Random rand )
	{
		int iBlockSubtype = world.getBlockMetadata( i, j, k );
		
		if ( iBlockSubtype == m_iSubtypeBlightLevel0 )
		{
            // check for evolution
            
            int iRandomFacing = rand.nextInt( 6 );
            
            FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k, iRandomFacing );
            
            if ( world.getBlockMaterial( targetPos.i, targetPos.j, targetPos.k ) == Material.water )
            {
        		world.setBlockMetadataWithNotify( i, j, k, m_iSubtypeBlightLevel1 );
            }
		}
		else if ( iBlockSubtype == m_iSubtypeBlightLevel1 )
		{	
            // check for evolution
            
            int iRandomFacing = rand.nextInt( 6 );
            
            FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k, iRandomFacing );
            
            if ( world.getBlockMaterial( targetPos.i, targetPos.j, targetPos.k ) == Material.lava )
            {
        		world.setBlockMetadataWithNotify( i, j, k, m_iSubtypeBlightLevel2 );
            }
		}
		else if ( iBlockSubtype == m_iSubtypeBlightLevel2 || 
			iBlockSubtype == m_iSubtypeBlightRootsLevel2 )
		{
            // check for evolution
            
            int iRandI = i + rand.nextInt( 7 ) - 3;
            int iRandJ = j + rand.nextInt( 7 ) - 3;
            int iRandK = k + rand.nextInt( 7 ) - 3;
            
            int iTargetBlockID = world.getBlockId( iRandI, iRandJ, iRandK );                

            if ( iTargetBlockID == Block.portal.blockID )
            {
	            if ( iBlockSubtype == m_iSubtypeBlightLevel2 )
	            {
	            	world.setBlockMetadataWithNotify( i, j, k, m_iSubtypeBlightLevel3 );
	            }
	            else
	            {
	            	world.setBlockMetadataWithNotify( i, j, k, m_iSubtypeBlightRootsLevel3 );
	            }
            }
		}			
	}
	
	/**
	 * Returns true if the blight has changed forms
	 */
	private boolean CheckForBlightSurfaceConversions( World world, int i, int j, int k )
	{
		int iBlightSubtype = world.getBlockMetadata( i, j, k );
		int iBlockAboveID = world.getBlockId(i, j + 1, k);
		
		if ( Block.lightOpacity[iBlockAboveID] > 2 )
		{
			// below surface
			
			if ( iBlightSubtype == m_iSubtypeBlightLevel0 )
			{
	            world.setBlockWithNotify( i, j, k, Block.dirt.blockID );
	            
	            return true;
			}
            else if ( iBlightSubtype == m_iSubtypeBlightLevel2 )
            {
        		world.setBlockAndMetadataWithNotify( i, j, k, blockID, 
        			m_iSubtypeBlightRootsLevel2 );
        		
        		return true;
            }
            else if ( iBlightSubtype == m_iSubtypeBlightLevel3 )
            {
        		world.setBlockAndMetadataWithNotify( i, j, k, blockID, 
        			m_iSubtypeBlightRootsLevel3 );
        		
        		return true;
            }
		}
		else
		{
			// on surface			
        	
            if ( iBlightSubtype == m_iSubtypeBlightRootsLevel2 )
            {
        		world.setBlockAndMetadataWithNotify( i, j, k, blockID, m_iSubtypeBlightLevel2 );
        		
        		return true;
            }
            if ( iBlightSubtype == m_iSubtypeBlightRootsLevel3 )
            {
        		world.setBlockAndMetadataWithNotify( i, j, k, blockID, m_iSubtypeBlightLevel3 );
        		
        		return true;
            }
		}
		
		return false;
	}
	
	private void BlightKillLeaves( World world, int i, int j, int k, Random rand )
	{
        for ( int iTempCount = 0; iTempCount < 4; ++iTempCount )
        {
            int iRandI = i + rand.nextInt( 3 ) - 1;
            int iRandJ = j + rand.nextInt( 9 );
            int iRandK = k + rand.nextInt( 3 ) - 1;
            
            int iTargetBlockID = world.getBlockId( iRandI, iRandJ, iRandK );                

            if ( iTargetBlockID == Block.leaves.blockID )
            {
        		world.setBlockWithNotify( iRandI, iRandJ, iRandK, 0 );
            }
        }
	}
	
	private void BlightKillVinesAndLeaves( World world, int i, int j, int k, Random rand )
	{
        for ( int iTempCount = 0; iTempCount < 4; ++iTempCount )
        {
            int iRandI = i + rand.nextInt( 3 ) - 1;
            int iRandJ = j + rand.nextInt( 9 );
            int iRandK = k + rand.nextInt( 3 ) - 1;
            
            int iTargetBlockID = world.getBlockId( iRandI, iRandJ, iRandK );                

            if ( iTargetBlockID == Block.leaves.blockID || iTargetBlockID == Block.vine.blockID )
            {
        		world.setBlockWithNotify( iRandI, iRandJ, iRandK, 0 );
            }
        }
	}
	
	//----------- Client Side Functionality -----------//
	
    private Icon m_IconDung;
    private Icon m_IconPackedEarth;
    
    private Icon[] m_IconBlightLevel0SideArray = new Icon[6];
    private Icon[] m_IconBlightLevel1SideArray = new Icon[6];
    private Icon[] m_IconBlightLevel2SideArray = new Icon[6];
    private Icon[] m_IconBlightLevel3SideArray = new Icon[6];
    
    private Icon[] m_IconBlightRootsLevel2SideArray = new Icon[6];
    
    private Icon m_IconBlightRootsLevel3;    
    
	@Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "dirt" ); // for hit effects
		
		m_IconDung = register.registerIcon( "fcBlockDung" );
    	m_IconPackedEarth = register.registerIcon( "FCBlockPackedEarth" );
    	
    	m_IconBlightLevel0SideArray[0] = register.registerIcon( "FCBlockBlightL0_bottom" );
    	m_IconBlightLevel0SideArray[1] = register.registerIcon( "FCBlockBlightL0_top" );
    	
    	Icon sideIcon = register.registerIcon( "FCBlockBlightL0_side" );
    	
    	m_IconBlightLevel0SideArray[2] = sideIcon;
    	m_IconBlightLevel0SideArray[3] = sideIcon;
    	m_IconBlightLevel0SideArray[4] = sideIcon;
    	m_IconBlightLevel0SideArray[5] = sideIcon;
    	
    	m_IconBlightLevel1SideArray[0] = register.registerIcon( "FCBlockBlightL1_bottom" );
    	m_IconBlightLevel1SideArray[1] = register.registerIcon( "FCBlockBlightL1_top" );
    	
    	sideIcon = register.registerIcon( "FCBlockBlightL1_side" );
    	
    	m_IconBlightLevel1SideArray[2] = sideIcon;
    	m_IconBlightLevel1SideArray[3] = sideIcon;
    	m_IconBlightLevel1SideArray[4] = sideIcon;
    	m_IconBlightLevel1SideArray[5] = sideIcon;
    	
    	m_IconBlightLevel2SideArray[0] = register.registerIcon( "FCBlockBlightL2_bottom" );
    	m_IconBlightLevel2SideArray[1] = register.registerIcon( "FCBlockBlightL2_top" );
    	
    	sideIcon = register.registerIcon( "FCBlockBlightL2_side" );
    	
    	m_IconBlightLevel2SideArray[2] = sideIcon;
    	m_IconBlightLevel2SideArray[3] = sideIcon;
    	m_IconBlightLevel2SideArray[4] = sideIcon;
    	m_IconBlightLevel2SideArray[5] = sideIcon;
    	
    	m_IconBlightLevel3SideArray[0] = register.registerIcon( "FCBlockBlightL3_roots" );
    	m_IconBlightLevel3SideArray[1] = register.registerIcon( "FCBlockBlightL3_top" );
    	
    	sideIcon = register.registerIcon( "FCBlockBlightL3_side" );
    	
    	m_IconBlightLevel3SideArray[2] = sideIcon;
    	m_IconBlightLevel3SideArray[3] = sideIcon;
    	m_IconBlightLevel3SideArray[4] = sideIcon;
    	m_IconBlightLevel3SideArray[5] = sideIcon;
    	
    	m_IconBlightRootsLevel2SideArray[0] = register.registerIcon( "FCBlockBlightL2_bottom" );
    	
    	sideIcon = register.registerIcon( "FCBlockBlightL2_roots" );
    	
    	m_IconBlightRootsLevel2SideArray[1] = sideIcon;    	
    	m_IconBlightRootsLevel2SideArray[2] = sideIcon;
    	m_IconBlightRootsLevel2SideArray[3] = sideIcon;
    	m_IconBlightRootsLevel2SideArray[4] = sideIcon;
    	m_IconBlightRootsLevel2SideArray[5] = sideIcon;
    	
    	m_IconBlightRootsLevel3 = register.registerIcon( "FCBlockBlightL3_roots" );
    }
	
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	if ( iMetadata == m_iSubtypeBlightLevel0 )
    	{
    		return m_IconBlightLevel0SideArray[iSide];
    	}
    	else if ( iMetadata == m_iSubtypeBlightLevel1 )
    	{
    		return m_IconBlightLevel1SideArray[iSide];
    	}
    	else if ( iMetadata == m_iSubtypeBlightLevel2 )
    	{
    		return m_IconBlightLevel2SideArray[iSide];
    	}
    	else if ( iMetadata == m_iSubtypeBlightRootsLevel2 )
    	{
    		return m_IconBlightRootsLevel2SideArray[iSide];
    	}
    	else if ( iMetadata == m_iSubtypeBlightLevel3 )
    	{
    		return m_IconBlightLevel3SideArray[iSide];
    	}
    	else if ( iMetadata == m_iSubtypeBlightRootsLevel3 )
    	{
    		return m_IconBlightRootsLevel3;
    	}
    	else if ( iMetadata == m_iSubtypePackedEarth )
    	{
    		return m_IconPackedEarth;
    	}
    	else if ( iMetadata == m_iSubtypeDung )
    	{
    		return m_IconDung;
    	}
    	
    	return blockIcon;
    }    
    
	@Override
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, m_iSubtypeBlightLevel0));
        list.add(new ItemStack(blockID, 1, m_iSubtypeBlightLevel1));
        list.add(new ItemStack(blockID, 1, m_iSubtypeBlightLevel2));
        list.add(new ItemStack(blockID, 1, m_iSubtypeBlightLevel3));
        list.add(new ItemStack(blockID, 1, m_iSubtypeBlightRootsLevel2));
        list.add(new ItemStack(blockID, 1, m_iSubtypeBlightRootsLevel3));
        list.add(new ItemStack(blockID, 1, m_iSubtypePackedEarth));
        list.add(new ItemStack(blockID, 1, m_iSubtypeDung));
    }

	@Override
    public int getDamageValue(World world, int x, int y, int z) {
		// used only by pick block
		int metadata = world.getBlockMetadata(x, y, z);
		
		if (metadata == m_iSubtypeBlightLevel0 || metadata == m_iSubtypeBlightLevel1 || metadata == m_iSubtypeBlightLevel2 || metadata == m_iSubtypeBlightLevel3 || metadata == m_iSubtypeBlightRootsLevel2 || metadata == m_iSubtypeBlightRootsLevel3) {
			return metadata;
		}
		
		return super.getDamageValue(world, x, y, z);		
    }
}
