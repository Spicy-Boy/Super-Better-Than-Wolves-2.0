// FCMOD

package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class SCBlockGourdFalling extends FCBlockFalling
{
	private static final double m_dArrowSpeedSquaredToExplode = 1.10D;
	
    protected SCBlockGourdFalling( int iBlockID )
    {
        super( iBlockID, Material.pumpkin );
        
        SetAxesEffectiveOn( true );
        SetBuoyant();
        
        setTickRandomly( true );   
        
        setStepSound(soundWoodFootstep);
        
    }
    
	/**
     * Returns the orentation value from the specified metadata
     */
    public static int getDirection(int meta)
    {
        return meta & 3;
    }
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	super.updateTick( world, i, j, k, rand );
    	
    }
    
    @Override
    public int getMobilityFlag()
    {
    	// allow gourds to be pushed by pistons
    	return 0;
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
    
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int iSide) {

		return true;
	}
	
    @Override
    public void OnArrowImpact( World world, int i, int j, int k, EntityArrow arrow )
    {
    	if ( !world.isRemote )
    	{
    		double dArrowSpeedSq = arrow.motionX * arrow.motionX + arrow.motionY * arrow.motionY + arrow.motionZ * arrow.motionZ;
    		
    		if ( dArrowSpeedSq >= m_dArrowSpeedSquaredToExplode )
    		{
	    		world.setBlockWithNotify( i, j, k, 0 );
	    		
	    		Explode( world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D , world.getBlockMetadata(i, j, k));
    		}
    		else
    		{    		
    			world.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
    		}
    	}
    }
    
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	// there's no improper tool to harvest gourds, but this also happens if the block is deleted after falling due to sitting on an
    	// improper block
    	
        world.playAuxSFX( AuxFXIDOnExplode(world,i,j,k, iMetadata), i, j, k, 0);
    }
    
    @Override
    public boolean OnFinishedFalling( EntityFallingSand entity, float fFallDistance )
    {
    	//entity.metadata = 0; // reset stem connection
    	
    	if ( !entity.worldObj.isRemote )
    	{
	        int i = MathHelper.floor_double( entity.posX );
	        int j = MathHelper.floor_double( entity.posY );
	        int k = MathHelper.floor_double( entity.posZ );
	        
	        int iFallDistance = MathHelper.ceiling_float_int( entity.fallDistance - 5.0F );
	        
	    	if ( iFallDistance >= 0 )
	    	{	    		
	    		DamageCollidingEntitiesOnFall( entity, fFallDistance );
	    		
	    		if ( !Material.water.equals( entity.worldObj.getBlockMaterial( i, j, k ) ) )
	    		{	    			
		    		if ( entity.rand.nextInt( 10 ) < iFallDistance )
		    		{
		    			Explode( entity.worldObj, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, entity.metadata);
		    			
		    			return false;
		    		}
	    		}
	    	}
	    	
			entity.worldObj.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
			
			//this.setBlockOnFinishedFalling(entity, i, j, k);
    	}
        
    	return true;
    }    
    
	//protected abstract void setBlockOnFinishedFalling(EntityFallingSand entity, int i, int j, int k);
    

	private static final int m_iLoadedRangeToCheckFalling = 32;
	
    protected boolean CheckForFall( World world, int i, int j, int k)
	{
		if ( CanFallIntoBlockAtPos( world, i, j - 1, k ) && j >= 0 )
		{
			if ( !BlockSand.fallInstantly && world.checkChunksExist( 
					i - m_iLoadedRangeToCheckFalling, j - m_iLoadedRangeToCheckFalling, k - m_iLoadedRangeToCheckFalling, 
					i + m_iLoadedRangeToCheckFalling, j + m_iLoadedRangeToCheckFalling, k + m_iLoadedRangeToCheckFalling ) )
			{
				if ( !world.isRemote )
				{
					FCEntityFallingBlock fallingEntity = new FCEntityFallingBlock( world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
	                    	blockID, world.getBlockMetadata( i, j, k ) );
					

					onStartFalling( fallingEntity );

					world.spawnEntityInWorld( fallingEntity );
				}

				return true;
			}
			else
			{
				world.setBlockToAir( i, j, k );

				while ( CanFallIntoBlockAtPos( world, i, j - 1, k ) && j > 0 )
				{
					j--;
				}

				if ( j > 0 )
				{
					world.setBlock( i, j, k, blockID );
				}

				return true;
			}
		}

		return false;
	}
    
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
		super.onNeighborBlockChange( world, i, j, k, iBlockID );
		
		//ValidateConnectionState( world, i, j, k );
    }
	
    @Override
    public boolean CanBeGrazedOn( IBlockAccess access, int i, int j, int k, EntityAnimal animal )
    {
		return animal.CanGrazeOnRoughVegetation();
    }

    //------------- Class Specific Methods ------------//
    
    
	abstract protected Item ItemToDropOnExplode();
	
	abstract protected int ItemCountToDropOnExplode(World world, int i, int k, int j, int meta);
	
	abstract protected int AuxFXIDOnExplode(World world, int i, int k, int j, int meta);
	
	abstract protected DamageSource GetFallDamageSource();	
	
    private void Explode( World world, double posX, double posY, double posZ, int entityMeta )
    {
    	Item itemToDrop = ItemToDropOnExplode();
    	
    	if ( itemToDrop != null )
    	{
	        for (int iTempCount = 0; iTempCount < ItemCountToDropOnExplode(world, (int)posX, (int)posY, (int)posZ, entityMeta); iTempCount++)
	        {
	    		ItemStack itemStack = new ItemStack( itemToDrop, 1, 0 );
	
	        	double dItemX = posX + ( world.rand.nextDouble() - 0.5D ) * 2D;
	        	double dItemY = posY + 0.5D;
	        	double dItemZ = posZ + ( world.rand.nextDouble() - 0.5D ) * 2D;
	    		
	            EntityItem entityItem = new EntityItem( world, dItemX, dItemY, dItemZ, itemStack );
	            
	            entityItem.motionX = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            entityItem.motionY = 0.2D + world.rand.nextDouble() * 0.3D;
	            entityItem.motionZ = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            
	            entityItem.delayBeforeCanPickup = 10;
	            
	            world.spawnEntityInWorld( entityItem );
	        }
    	}
    	
    	NotifyNearbyAnimalsFinishedFalling( world, MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ) );
        
        world.playAuxSFX( AuxFXIDOnExplode(world, (int)posX, (int)posY, (int)posZ, entityMeta), 
    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
    		0 );
    }
    
    private void DamageCollidingEntitiesOnFall( EntityFallingSand entity, float fFallDistance )
    {
        int var2 = MathHelper.ceiling_float_int( fFallDistance - 1.0F );

        if (var2 > 0)
        {
            ArrayList collisionList = new ArrayList( entity.worldObj.getEntitiesWithinAABBExcludingEntity( entity, entity.boundingBox ) );
            
            DamageSource source = GetFallDamageSource();
            
            Iterator iterator = collisionList.iterator();

            while ( iterator.hasNext() )
            {
                Entity tempEntity = (Entity)iterator.next();
                
                tempEntity.attackEntityFrom( source, 1 );
            }

        }
    }
    

//----------- Client Side Functionality -----------//
	
    /**
     * 
     * @param x width along xAxis in px
     * @param y height along yAxis in px
     * @param z width along zAxis in px
     * @return AxisAlignedBB of the gourd
     */
	protected AxisAlignedBB GetGourdBounds(int x, int y, int z)
	{
		double xSize = x/16D;
		double ySize = y/16D;
		double zSize = z/16D;	
		
    	AxisAlignedBB pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - xSize/2, 0.0D, 8/16D - zSize/2, 
    			8/16D + xSize/2, ySize, 8/16D + zSize/2);
    	
    	return pumpkinBox;
	}
	
//    @Override
//    public void registerIcons( IconRegister register )
//    {
////		blockIcon = register.registerIcon( "pumpkin_side" );
////		
////		m_IconTop = register.registerIcon( "pumpkin_top" );
//		
//		//Orange
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
//    
//    @Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//    	if (iMetadata == 1)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return orangeIconTop[1];
//        	}
//        	
//    		else return orangeIcon[1];
//    	}
//    	else if (iMetadata == 2)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return orangeIconTop[2];
//        	}
//        	
//    		else return orangeIcon[2];
//    	}
//    	else if (iMetadata == 3)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return orangeIconTop[3];
//        	}
//        	
//    		else return orangeIcon[3];
//    	}
//    	else if (iMetadata == 4)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return greenIconTop[0];
//        	}
//        	
//    		else return greenIcon[0];
//    	}
//    	else if (iMetadata == 5)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return greenIconTop[1];
//        	}
//        	
//    		else return greenIcon[1];
//    	}
//    	else if (iMetadata == 6)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return greenIconTop[2];
//        	}
//        	
//    		else return greenIcon[2];
//    	}
//    	else if (iMetadata == 7)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return greenIconTop[3];
//        	}
//        	
//    		else return greenIcon[3];
//    	}
//    	else if (iMetadata == 8)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return yellowIconTop[0];
//        	}
//        	
//    		else return yellowIcon[0];
//    	}
//    	else if (iMetadata == 9)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return yellowIconTop[1];
//        	}
//        	
//    		else return yellowIcon[1];
//    	}
//    	else if (iMetadata == 10)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return yellowIconTop[2];
//        	}
//        	
//    		else return yellowIcon[2];
//    	}
//    	else if (iMetadata == 11)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return yellowIconTop[3];
//        	}
//        	
//    		else return yellowIcon[3];
//    	}
//    	else if (iMetadata == 12)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return whiteIconTop[0];
//        	}
//        	
//    		else return whiteIcon[0];
//    	}
//    	else if (iMetadata == 13)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return whiteIconTop[1];
//        	}
//        	
//    		else return whiteIcon[1];
//    	}
//    	else if (iMetadata == 14)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return whiteIconTop[2];
//        	}
//        	
//    		else return whiteIcon[2];
//    	}
//    	else if (iMetadata == 15)
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return whiteIconTop[3];
//        	}
//        	
//    		else return whiteIcon[3];
//    	}
//    	else //meta 0
//    	{
//			if ( iSide == 1 || iSide == 0 )
//        	{
//        		return orangeIconTop[0];
//        	}
//        	
//    		else return orangeIcon[0];
//    	}
//    	
//    	
//    	
//    }
	
}