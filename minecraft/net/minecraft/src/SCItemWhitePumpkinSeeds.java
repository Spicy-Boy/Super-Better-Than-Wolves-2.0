package net.minecraft.src;

public class SCItemWhitePumpkinSeeds extends FCItemSeedFood 
{
	//AKA smol pumpkin
	
	public SCItemWhitePumpkinSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, 1, 0F, iCropBlockID);
		setUnlocalizedName( "SCItemWhitePumpkinSeeds" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
		
	}

}
