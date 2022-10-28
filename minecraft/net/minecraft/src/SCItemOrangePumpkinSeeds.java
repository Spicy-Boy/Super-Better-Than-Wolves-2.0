package net.minecraft.src;

public class SCItemOrangePumpkinSeeds extends FCItemSeedFood 
{
	//AKA Giant Pumpkin Seeds

	public SCItemOrangePumpkinSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, 1, 0F, iCropBlockID);
		setUnlocalizedName( "SCItemOrangePumpkinSeeds" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
		
	}

}
