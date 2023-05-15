package net.minecraft.src;

public class SuperBTWItemBonePickaxe extends FCItemPickaxe
{
	public SuperBTWItemBonePickaxe (int iItemID) 
	{
		super (iItemID, EnumToolMaterial.BONE);
		
    	SetIncineratedInCrucible();
    	setUnlocalizedName( "SuperBTWItemBonePickaxe" );
    	
    	this.setCreativeTab(CreativeTabs.tabTools);
		
	}
}