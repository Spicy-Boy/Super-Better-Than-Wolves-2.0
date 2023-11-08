package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockReedThatch extends FCBlockFalling
{
    public SuperBTWBlockReedThatch( int iBlockID )
    {
    	super( iBlockID, FCBetterThanWolves.fcMaterialWicker );
    	
    	SetBuoyant();
        
    	setHardness( 0.3F ); 
    	
        SetAxesEffectiveOn();
    	
    	SetFireProperties( FCEnumFlammability.HIGH );
    	
    	setStepSound( soundGrassFootstep );
    	
    	setUnlocalizedName( "SuperBTWBlockReedThatch" );
        
        setCreativeTab( CreativeTabs.tabBlock );
        
        SetFurnaceBurnTime( FCEnumFurnaceBurnTime.PLANKS_OAK );
    }
    
	@Override
    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
    {
        return SuperBTWDefinitions.reedThatch.blockID;
    }

    public int quantityDropped( Random rand )
    {
        return 1;
    }
    
//    @Override
//    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
//    {
//    	this.dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.reed, 4));
//    	this.dropBlockAsItem_do(world, i, j, k, new ItemStack(FCBetterThanWolves.fcItemSawDust, 4));
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
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
    
    //GRAVITY related methods
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand ) 
    {
    	if  ( HasFallingBlockRestingOn( world, i, j, k ) )
    	{
    		CheckForFall( world, i, j, k );
    	}
   
    }
    
	@Override
    public void OnPlayerWalksOnBlock( World world, int i, int j, int k, EntityPlayer player )
    {
		CheckForFall( world, i, j, k );
    }

}


