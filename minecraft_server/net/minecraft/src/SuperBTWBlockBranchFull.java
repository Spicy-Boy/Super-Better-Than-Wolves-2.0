package net.minecraft.src;

public class SuperBTWBlockBranchFull extends FCBlockFallingFullBlock 
{
    public SuperBTWBlockBranchFull( int iBlockID )
    {
        super( iBlockID, Material.wood );
        
        setHardness( 0.1F );        
        SetShovelsEffectiveOn( true );
        SetAxesEffectiveOn( true );  
        
        setStepSound( soundGravelFootstep );
        
        setUnlocalizedName("SuperBTWBlockBranchFull");        
        
		setCreativeTab( CreativeTabs.tabBlock );
		
        SetBuoyant();
        
        SetFireProperties( 5, 5 );
    }
    
	@Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }

}
