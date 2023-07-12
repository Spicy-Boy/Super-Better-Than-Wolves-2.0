package net.minecraft.src;

public class SuperBTWItemCookedCowRibPartial extends ItemFood {
	
	public SuperBTWItemCookedCowRibPartial (int iItemID) 
	{
		super (iItemID, 2, 0.25F, false, false);
		
		
    	SetIncineratedInCrucible();
    	setUnlocalizedName( "SuperBTWItemCookedCowRibPartial" );
    	
    	maxStackSize = 1;
		
	}
	
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 64;
    }
	
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        
        return new ItemStack( SuperBTWDefinitions.cookedCowRibSpent, 1);
    }
}
