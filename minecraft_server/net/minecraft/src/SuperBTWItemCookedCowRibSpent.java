package net.minecraft.src;

public class SuperBTWItemCookedCowRibSpent extends ItemFood {

	public SuperBTWItemCookedCowRibSpent (int iItemID) 
	{
		super (iItemID, 2, 0.25F, false, false);
		
		
    	SetIncineratedInCrucible();
    	setUnlocalizedName( "SuperBTWItemCookedCowRibSpent" );
    	
    	maxStackSize = 1;
		
	}
	
}
