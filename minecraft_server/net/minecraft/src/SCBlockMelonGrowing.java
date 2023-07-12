package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockMelonGrowing extends SCBlockGourdGrowing {

	protected SCBlockMelonGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}

	@Override
	protected float GetBaseGrowthChance()
	{
		//changed to match the rate of wheat
		return 0.4F;
	}
	
	@Override
	protected boolean canBePossessed() {
		return false;
	}
	
	@Override
	public int idDropped( int iMetadata, Random random, int iFortuneModifier )
	{		
		return SCDefs.melonHarvested.blockID;
	}

    @Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
    {
    	return FCBetterThanWolves.m_iMelonExplodeAuxFXID;
    }
    
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourceMelon;
	}

}
