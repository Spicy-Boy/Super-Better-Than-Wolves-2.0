package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockReedThatchSlab extends FCBlockSlabFalling
{
    public SuperBTWBlockReedThatchSlab( int iBlockID )
    {
    	super( iBlockID, Material.leaves );
    	
    	SetBuoyant();
        
    	setHardness( 0.3F ); 
    	
        SetAxesEffectiveOn();
    	
    	SetFireProperties( FCEnumFlammability.HIGH );
    	
    	setStepSound( soundGrassFootstep );
    	
    	setUnlocalizedName( "SuperBTWBlockReedThatchSlab" );
    	
        setLightOpacity( 2 );
        Block.useNeighborBrightness[iBlockID] = true;
        
        setCreativeTab( CreativeTabs.tabBlock );
        
        SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING );
    }
    
	@Override
    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
    {
        return SuperBTWDefinitions.reedThatchSlab.blockID;
    }

    public int quantityDropped( Random rand )
    {
        return 1;
    }
    
//    @Override
//    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
//    {
//    	this.dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.reed, 2));
//    	this.dropBlockAsItem_do(world, i, j, k, new ItemStack(FCBetterThanWolves.fcItemSawDust, 2));
//    }
    
    @Override
    public void onBlockAdded( World world, int i, int j, int k )
    {
        	ScheduleCheckForFall( world, i, j, k );
    }
    
	//indicates that a zombie can tear through it!
	public boolean isWeakBlock(World worldObj, int x, int y, int z) 
	{
		return true;
	}
    
	@Override
	public int GetCombinedBlockID(int iMetadata) {
		// TODO Auto-generated method stub
		return SuperBTWDefinitions.reedThatch.blockID;
	}
	
    @Override
    protected ItemStack createStackedBlock( int iMetadata )
    {
        return new ItemStack( blockID, 1, 0 );
    }
    
    @Override
    public boolean CanBePlacedUpsideDownAtLocation( World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
    
    //GRAVITY related methods
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand ) 
    {
    	if  ( GetIsUpsideDown( world, i, j, k ) && HasFallingBlockRestingOn( world, i, j, k ) )
    	{
    		if ( !CheckForFall( world, i, j, k ) )
    		{
            	if ( GetIsUpsideDown( world, i, j, k ) )
            	{
            		SetIsUpsideDown( world, i, j, k, false );
            	}
    		}
    	}
    }
    
	@Override
    public void OnPlayerWalksOnBlock( World world, int i, int j, int k, EntityPlayer player )
    {
		if ( !CheckForFall( world, i, j, k ) )
		{
        	if ( GetIsUpsideDown( world, i, j, k ) )
        	{
        		SetIsUpsideDown( world, i, j, k, false );
        	}
		}
    }
	
    public boolean CanBeCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	return true;
    }
    
    public void OnCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	if (entity.blockID == SuperBTWDefinitions.reedThatchSlab.blockID || 
    		entity.blockID == SuperBTWDefinitions.reedThatch.blockID )
    	{
    		
    	}
    	else 
    	{
    		entity.worldObj.playAuxSFX( FCBetterThanWolves.m_iMechanicalDeviceExplodeAuxFXID,            
				MathHelper.floor_double( entity.posX ), 
				MathHelper.floor_double( entity.posY ), 
				MathHelper.floor_double( entity.posZ ), 0 );
        	dropBlockAsItem_do( world, i, j, k, new ItemStack( Item.reed, 1) );     
        	dropBlockAsItem_do( world, i, j, k, new ItemStack( FCBetterThanWolves.fcItemSawDust, 1) ); 
    	}

    }
    
    
}

