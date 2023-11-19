package net.minecraft.src;

public class SuperBTWItemPipePacked extends Item
{
	
	public SuperBTWItemPipePacked(int itemID) 
	{
		super(itemID);
		setUnlocalizedName( "SuperBTWItemPipePacked" );
		SetBuoyant();
		setCreativeTab( CreativeTabs.tabTools);
		maxStackSize = 16;
	}

}
