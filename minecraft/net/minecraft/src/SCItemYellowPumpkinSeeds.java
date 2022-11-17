package net.minecraft.src;

public class SCItemYellowPumpkinSeeds extends FCItemSeedFood 
{
	//AKA normal sized pumpkin but it is yellow
	
	public SCItemYellowPumpkinSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, 1, 0F, iCropBlockID);
		setUnlocalizedName( "SCItemYellowPumpkinSeeds" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
		
	}

}
