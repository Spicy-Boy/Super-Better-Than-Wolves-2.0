package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockWetMudBrick extends FCBlockUnfiredBrick
{
    public SuperBTWBlockWetMudBrick( int iBlockID )
    {
        super( iBlockID );  
        
        setHardness( 0F );
        SetShovelsEffectiveOn( true );
        
        setStepSound( FCBetterThanWolves.fcStepSoundSquish );        
        
		SetCanBeCookedByKiln( true );
        
        setUnlocalizedName( "SuperBTWBlockWetMudBrick" );
    }
    
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
		return FCBetterThanWolves.fcItemPileDirt.itemID;
    }
}
