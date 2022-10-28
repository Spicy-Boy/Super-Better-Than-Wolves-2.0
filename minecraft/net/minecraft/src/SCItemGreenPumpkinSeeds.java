package net.minecraft.src;

public class SCItemGreenPumpkinSeeds extends FCItemSeedFood 
{
	//AKA slab pumpkins

	public SCItemGreenPumpkinSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, 1, 0F, iCropBlockID);
		setUnlocalizedName( "SCItemGreenPumpkinSeeds" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
		
	}

}
