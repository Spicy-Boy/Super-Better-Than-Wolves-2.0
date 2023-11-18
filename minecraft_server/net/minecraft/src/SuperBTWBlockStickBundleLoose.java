package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockStickBundleLoose extends FCBlockFalling 
{
    public SuperBTWBlockStickBundleLoose( int iBlockID )
    {
    	super( iBlockID, Material.wood );
    	
    	SetBuoyant();
        
    	setHardness( 0.2F ); 
    	
        SetAxesEffectiveOn(true);
    	
    	SetFireProperties( FCEnumFlammability.HIGH );
    	
    	setStepSound( soundWoodFootstep );
    	
    	setUnlocalizedName( "SuperBTWBlockStickBundleLoose" );
        
        setCreativeTab( CreativeTabs.tabBlock );
        
//        SetFurnaceBurnTime( FCEnumFurnaceBurnTime.PLANKS_OAK );
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

}
