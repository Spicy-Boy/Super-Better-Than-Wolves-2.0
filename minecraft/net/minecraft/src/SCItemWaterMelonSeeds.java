package net.minecraft.src;

public class SCItemWaterMelonSeeds extends FCItemSeeds
{
	
	public SCItemWaterMelonSeeds(int iItemID, int iCropBlockID) 
	{
		
		super(iItemID, iCropBlockID);
		setUnlocalizedName( "SCItemSeedsWaterMelon" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
		
	}

}
