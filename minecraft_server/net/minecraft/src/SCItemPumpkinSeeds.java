package net.minecraft.src;

public class SCItemPumpkinSeeds extends FCItemSeeds 
{

	public SCItemPumpkinSeeds(int iItemID) 
	{
		super(iItemID, SCDefs.SCpumpkinStem.blockID);
		setUnlocalizedName("seeds_pumpkin");
		setCreativeTab(CreativeTabs.tabDecorations);
	}


}
