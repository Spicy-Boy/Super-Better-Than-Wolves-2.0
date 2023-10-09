package net.minecraft.src;

public class SCItemCantaloupeMelonSeeds extends FCItemSeeds 
{
	
	public SCItemCantaloupeMelonSeeds(int iItemID, int iCropBlockID) 
	{
		super(iItemID, iCropBlockID);
		setUnlocalizedName( "SCItemSeedsCantaloupeMelon" );
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(64);
		SetFilterableProperties( m_iFilterable_Fine );
		SetBellowsBlowDistance( 2 );
		SetAsBasicChickenFood();
	}

}
