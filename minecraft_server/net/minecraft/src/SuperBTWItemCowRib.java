package net.minecraft.src;

public class SuperBTWItemCowRib extends FCItemFood
{
	public SuperBTWItemCowRib (int iItemID) 
	{
		super (iItemID, 2, 0.25F, true, "SuperBTWItemCowRib", true);
		
    	SetIncineratedInCrucible();
    	setUnlocalizedName( "SuperBTWItemCowRib" );
    	
    	this.setCreativeTab(CreativeTabs.tabFood);
    	
    	SetIncreasedFoodPoisoningEffect();
    	
    	maxStackSize = 1;
		
	}
	
    @Override
    public void OnUsedInCrafting( int iItemDamage, EntityPlayer player, ItemStack outputStack )
    {
    	if ( !player.worldObj.isRemote )
    	{
    		if ( outputStack.itemID == Item.beefRaw.itemID )
    		{
    			FCUtilsItem.EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, 
    				new ItemStack(  Item.dyePowder, 1, 15) );
    		}
    	}
    }
}
