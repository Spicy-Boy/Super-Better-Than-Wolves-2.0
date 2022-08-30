package net.minecraft.src;

public class SuperBTWItemRib extends Item
{
	public SuperBTWItemRib (int iItemID) 
	{
		super (iItemID);
		
		setFull3D();
		
    	SetIncineratedInCrucible();
    	setUnlocalizedName( "SuperBTWItemRib" );
    	
    	this.setCreativeTab(CreativeTabs.tabMaterials);
		
	}
}
