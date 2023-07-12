package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonGrowingAsleep extends SCBlockGourdGrowingAsleep
{
	
	protected SCBlockMelonGrowingAsleep(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) 
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}
	
	@Override
	protected float GetBaseGrowthChance()
	{
		//changed to match the rate of wheat
		return 0.4F;
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

	@Override
	protected int getMetaHarvested(int growthLevel) {
		// TODO Auto-generated method stub
		return 0;
	}

}
