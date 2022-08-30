package net.minecraft.src;

public abstract class SCBlockMelonGrowing extends SCBlockGourdGrowing {

	protected SCBlockMelonGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}

	@Override
	protected float GetBaseGrowthChance()
	{
		return 0.08F;
	}
	
	@Override
	protected boolean canBePossessed() {
		return false;
	}

	
	@Override
	protected Item ItemToDropOnExplode()
	{
		return Item.melonSeeds;
	}

	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		int growthLevel = this.GetGrowthLevel(world, i, j, k);
		if (growthLevel == 3)
		{
			return 1;
		}
		else return 0;
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
