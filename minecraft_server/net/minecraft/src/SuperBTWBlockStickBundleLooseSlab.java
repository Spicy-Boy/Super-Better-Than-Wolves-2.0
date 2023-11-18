package net.minecraft.src;

public class SuperBTWBlockStickBundleLooseSlab extends FCBlockSlabFalling
{
	/*
	 * METADATA BREAKDOWN:
	 * 
	 * 1/4 block contains 4 sticks
	 * 1/2 block contains 8 sticks
	 * 3/4 block contains 12 sticks
	 * Full stick block contains 16 sticks
	 * 
	 * 0 = 1/4 block placed horizontally north/south, basically a quarter slab! 
	 * 1 = 1/2 block placed horizontally north/south, a half slab
	 * 
	 * 2 = 1/4 block placed horizontally east/west,
	 * 3 = 1/2 block placed horizontally east/west
	 * 
	 * horizontal slabs are placed when placing against the side of a block
	 * vertical slabs are created when placing against the top or bottom of a block
	 * 
	 * shift+right click with empty hand to reduce block size by 1/4 and pull out 4 sticks into hand
	 * 
	 * right click 1/4 block with torch, or ignite to transform into a lit campfire
	 * 
	 */
	
    public SuperBTWBlockStickBundleLooseSlab( int iBlockID )
    {
    	super( iBlockID, Material.wood );
    	
    	SetBuoyant();
        
    	setHardness( 0.2F ); 
    	
    	SetAxesEffectiveOn(true);
    	
    	SetFireProperties( FCEnumFlammability.HIGH );
    	
    	setStepSound( soundWoodFootstep );
    	
    	setUnlocalizedName( "SuperBTWBlockStickBundleLooseSlab" );
    	
        setLightOpacity( 2 );
        Block.useNeighborBrightness[iBlockID] = true;
        
        setCreativeTab( CreativeTabs.tabBlock );
        
//        SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING );
    }
    
	//indicates that a zombie can tear through it!
	public boolean isWeakBlock(World worldObj, int x, int y, int z) 
	{
		return true;
	}
	
	@Override
	public int GetCombinedBlockID(int iMetadata) {
		// TODO Auto-generated method stub
		return SuperBTWDefinitions.stickBundleLoose.blockID;
	}
	
    @Override
    protected ItemStack createStackedBlock( int iMetadata )
    {
        return new ItemStack( blockID, 1, 0 );
    }
    
    @Override
    public boolean CanBePlacedUpsideDownAtLocation( World world, int i, int j, int k )
    {
    	return false;
    }
    
    @Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
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
        	//ADD METADATA SPECIFIC DROPS
    	}

    }
	
	

}
