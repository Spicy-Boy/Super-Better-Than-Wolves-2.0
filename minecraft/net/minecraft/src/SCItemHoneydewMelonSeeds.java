package net.minecraft.src;

public class SCItemHoneydewMelonSeeds extends FCItemSeeds 
{

	public SCItemHoneydewMelonSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, iCropBlockID);
		setUnlocalizedName( "SCItemSeedsHoneydewMelon" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
	}
	
}
