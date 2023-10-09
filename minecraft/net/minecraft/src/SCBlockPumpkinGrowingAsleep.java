package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockPumpkinGrowingAsleep extends SCBlockGourdGrowingAsleep 
{

	protected SCBlockPumpkinGrowingAsleep(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) 
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}
	
	protected float GetBaseGrowthChance()
    {
		//matches wheat!
    	return 0.4F;
    }

	
	@Override
	public int idPicked(World world, int par2, int par3, int par4)
	{
		return SCDefs.pumpkinHarvested.blockID;
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

