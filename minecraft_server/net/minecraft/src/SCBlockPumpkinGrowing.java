package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockPumpkinGrowing extends SCBlockGourdGrowing {

	protected SCBlockPumpkinGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}
	
	protected float GetBaseGrowthChance()
    {
		//Changed this to match the rate of wheat
		return 0.4F;
    }

	@Override
	protected boolean canBePossessed() {
		return false;
	}
	
	@Override
	public int idDropped( int iMetadata, Random random, int iFortuneModifier )
	{		
		return SCDefs.pumpkinHarvested.blockID;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return getMetaHarvested(meta);
	}

	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta) {
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
	}

	@Override
	protected DamageSource GetFallDamageSource() {
		return FCDamageSourceCustom.m_DamageSourcePumpkin;
	}

}
