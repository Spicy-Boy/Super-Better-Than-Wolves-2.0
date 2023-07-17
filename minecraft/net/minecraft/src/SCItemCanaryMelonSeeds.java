package net.minecraft.src;

public class SCItemCanaryMelonSeeds extends FCItemSeeds 
{
	
	public SCItemCanaryMelonSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, iCropBlockID);
		setUnlocalizedName( "SCItemSeedsCanaryMelon" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
	}

}
