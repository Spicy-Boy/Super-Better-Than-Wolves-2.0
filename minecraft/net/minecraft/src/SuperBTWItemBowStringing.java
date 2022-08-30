package net.minecraft.src;
//NOTE!
/* Define the images for the animation in itemBow.java
 * Define the animation times in entityPlayer
 */
public class SuperBTWItemBowStringing extends Item {

	public SuperBTWItemBowStringing(int iItemID) {
		super(iItemID);
		// TODO Auto-generated constructor stub
	    	
	    setUnlocalizedName( "SuperBTWItemBowStringing" );  

	    maxStackSize = 1;
        
	    this.setMaxDamage(10);
	}

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
    	//same as bow, not sure what this does...
    	
        return 72000; 
    }
	
	public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {

    	player.setItemInUse( stack, getMaxItemUseDuration( stack ) );
    	
    	return stack;
    }
	
	//may need to look into this to make animation work
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
    
    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {	

    	int var6 = this.getMaxItemUseDuration(itemStack) - iTicksInUseRemaining;
    	System.out.println("Var 6 = " + var6);
        
    	if (var6 < 15) //nothing happens
    	{
    		return;
    	}
    	
        if (var6 > 23 && var6 < 35) //success!
        {
        	//player.inventory.mainInventory[player.inventory.currentItem] = null;
        	player.playSound( "random.bow", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
        	player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.bow, 1, 0);

        }
        else if (var6 > 29) //bow breaks!!!
        {
        	itemStack.damageItem( 11, player );
        	player.playSound( "mob.zombie.woodbreak", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
        	player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.silk, 2);
            //var7 = 1.0F;
        }
    	
    	
    }
    
    public void OnUsedInCrafting( EntityPlayer player, ItemStack outputStack )
    {
		if ( outputStack.itemID == Item.silk.itemID )
		{
	    	if ( !player.worldObj.isRemote )
	    	{
    			FCUtilsItem.EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, new ItemStack( Item.stick ) ); 
	    	}
	    	
	    	if ( player.m_iTimesCraftedThisTick == 0 )
			{
				player.playSound( "random.bow", 0.25F, player.worldObj.rand.nextFloat() * 0.25F + 1.5F );
			}
		}
    }
	
}
