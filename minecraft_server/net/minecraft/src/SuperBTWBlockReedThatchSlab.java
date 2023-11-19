package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockReedThatchSlab extends FCBlockSlabFalling
{
    public SuperBTWBlockReedThatchSlab( int iBlockID )
    {
    	super( iBlockID, Material.wood );
    	
    	SetBuoyant();
        
    	setHardness( 0.3F ); 
    	
    	SetAxesEffectiveOn(true);
    	
    	SetFireProperties( FCEnumFlammability.HIGH );
    	
    	setStepSound( soundGrassFootstep );
    	
    	setUnlocalizedName( "SuperBTWBlockReedThatchSlab" );
    	
        setLightOpacity( 2 );
        Block.useNeighborBrightness[iBlockID] = true;
        
        setCreativeTab( CreativeTabs.tabBlock );
        
        SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING );
    }
    
//	@Override
//    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
//    {
//        return SuperBTWDefinitions.reedThatchSlab.blockID;
//    }
//
//    public int quantityDropped( Random rand )
//    {
//        return 1;
//    }
    
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	DropItemsIndividualy( world, i, j, k, Item.reed.itemID, 2, 0, 1 );
    }
    
//	@Override
//	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
//	{
//		DropItemsIndividualy( world, i, j, k, Item.reed.itemID, 2, 0, fChanceOfDrop );
//		
//		return true;
//	}
    
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
    	if  ( GetIsUpsideDown( world, i, j, k ) && 
    			HasFallingBlockRestingOn( world, i, j, k ) &&
    			SuperBTWDefinitions.doesBlockTriggerWeaklingFall(world.getBlockId(i, j+1, k))
    			|| world.getBlockMetadata(i, j, k) >= 9
    					
    		)
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
		
		super.OnPlayerWalksOnBlock(world, i, j, k, player);
    }
	
    public boolean CanBeCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	return true;
    }
    
    public void OnCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	if (SuperBTWDefinitions.doesBlockCrushWeaklings(entity.blockID))
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

