package net.minecraft.src;

public class SuperBTWItemCowRib extends FCItemFood
{
	public SuperBTWItemCowRib (int iItemID) 
	{
		super (iItemID, 2, 0.25F, false, "SuperBTWItemCowRib", true);
		
    	SetIncineratedInCrucible();
    	setUnlocalizedName( "SuperBTWItemCowRib" );
    	
    	this.setCreativeTab(CreativeTabs.tabFood);
    	
    	SetIncreasedFoodPoisoningEffect();
    	
    	maxStackSize = 1;
		
	}
	
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 128;
    }
}
