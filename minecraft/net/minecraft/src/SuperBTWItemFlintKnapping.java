package net.minecraft.src;
import java.util.List;

public class SuperBTWItemFlintKnapping extends Item
{
	//NOTE: this icon array is worthless and does nothing!!!
//    public static final String[] bowPullIconNameArray = new String[] {"bow_pull_0", "bow_pull_1", "bow_pull_2"};
//    public Icon[] iconArray;
    
	public SuperBTWItemFlintKnapping(int iItemID) {
		super(iItemID);
		// TODO Auto-generated constructor stub
	    	
	    setUnlocalizedName( "SuperBTWItemFlintKnapping" );  

	    maxStackSize = 1;
        
        //setMaxDamage( GetProgressiveCraftingMaxDamage() );
	    this.setMaxDamage(10);
	}
	
//	 protected int GetProgressiveCraftingMaxDamage()
//	 {
//	    return 10;
//	 }

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
    
    //when bad hits == 3, break item and return stone to player
    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {	
    	//a little animation :F
    	player.swingItem();
    	
    	if (itemStack.getTagCompound() == null)
    	{
    		NBTTagCompound newTag = new NBTTagCompound();
    		itemStack.setTagCompound(newTag);
    		itemStack.getTagCompound().setInteger("badHits", 0);
    	}
    	
    	int badHits = itemStack.getTagCompound().getInteger("badHits");

    	int var6 = this.getMaxItemUseDuration(itemStack) - iTicksInUseRemaining;
    	//TESTER VVV
//    	System.out.println("Var 6 = " + var6);
//        float var7 = (float)var6 / 20.0F;
//        var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
        
    	if (var6 < 5)
    	{
    		return;
    	}
    	
        if (var6 < 20 || var6 > 35)
        {
        	badHits++;
//        	System.out.println("Uh oh! bad hits: " + badHits);
        	
        	player.playSound( "random.bow", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
        	
        	itemStack.getTagCompound().setInteger("badHits", badHits);
            
            if (badHits > 3)
            {
        		itemStack.damageItem( 11, player );
//        		FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack(FCBetterThanWolves.fcItemStone, 1));
        		//player.inventory.mainInventory[player.inventory.currentItem] = null;
        		player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( FCBetterThanWolves.fcItemStone, 1);
            }
        }
        else if (var6 >= 21)
        {
        	itemStack.damageItem( -1, player);
        	//System.out.println("Item damage = " + itemStack.getItemDamage());
        	player.playSound( "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
            //var7 = 1.0F;
        	
        	if (itemStack.getItemDamage() <= 0)
        	{
        		
        		FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack(FCBetterThanWolves.fcItemStone, 1));
        		//itemStack.damageItem( 11, player );
        		//to remove item from inv VVV
        		player.inventory.mainInventory[player.inventory.currentItem] = null;
        		player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( SuperBTWDefinitions.flintBlade, 1);
        	}
        }
    	
    	
    }

//    public void registerIcons(IconRegister par1IconRegister)
//    {
//        super.registerIcons(par1IconRegister);
//        this.iconArray = new Icon[bowPullIconNameArray.length];
//
//        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
//        {
//            this.iconArray[var2] = par1IconRegister.registerIcon(bowPullIconNameArray[var2]);
//        }
//    }
    
//    public Icon getItemIconForUseDuration(int par1)
//    {
//        return this.iconArray[par1];
//    }
}


//Knapping iteration 3, animation work
//**********************************
//    public SuperBTWItemFlintKnapping(int par1)
//    {
//        super(par1);
//        this.maxStackSize = 1;
//        //setMaxDamage( GetProgressiveCraftingMaxDamage() );
//        this.setMaxDamage(384);
//        setUnlocalizedName("SuperBTWItemFlintKnapping");
//
//    }
//    
//	 protected int GetProgressiveCraftingMaxDamage()
//	 {
//	    return 10;
//	 }
//	
//
//    /**
//     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
//     */
//    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
//    {
//    	System.out.println(par1ItemStack.getItemDamage() + "Item damage!");
//        boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
//
//        if (var5 || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
//        {
//            int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
//            float var7 = (float)var6 / 20.0F;
//            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
//
//            if ((double)var7 < 0.1D)
//            {
//                return;
//            }
//
//            if (var7 > 1.0F)
//            {
//                var7 = 1.0F;
//            }
//            
//        }
//    }
//
//    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
//    {
//        return par1ItemStack;
//    }
//
//    /**
//     * How long it takes to use or consume an item
//     */
//    public int getMaxItemUseDuration(ItemStack par1ItemStack)
//    {
//        return 72000;
//    }
//
//    /**
//     * returns the action that specifies what animation to play when the items is being used
//     */
//    public EnumAction getItemUseAction(ItemStack par1ItemStack)
//    {
//        return EnumAction.bow;
//    }
//
//    /**
//     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
//     */
//    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
//    {
//
//        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
//
//
//        return par1ItemStack;
//    }
//
//    public void registerIcons(IconRegister par1IconRegister)
//    {
//        super.registerIcons(par1IconRegister);
//        this.iconArray = new Icon[bowPullIconNameArray.length];
//
//        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
//        {
//            this.iconArray[var2] = par1IconRegister.registerIcon(bowPullIconNameArray[var2]);
//        }
//    }
//
//    /**
//     * used to cycle through icons based on their used duration, i.e. for the bow
//     */
//    public Icon getItemIconForUseDuration(int par1)
//    {
//        return this.iconArray[par1];
//    }



//**********************************************************************
//PRE BOW TEST CODE!!!! Completely functional!!!!!!!!
//Delete these comment brackets to invoke this shit... otherwise.........
/*
public class SuperBTWItemFlintKnapping extends Item 
{
    public static final String[] knappingIconNameArray = new String[] {"knapping_pull_0", "knapping_pull_1", "knapping_pull_2"};
    private Icon[] iconArray;
	
	public SuperBTWItemFlintKnapping(int iItemID) {
		super(iItemID);
		// TODO Auto-generated constructor stub
	    	
	    setUnlocalizedName( "SuperBTWItemFlintKnapping" );  

	    maxStackSize = 1;
        
        setMaxDamage( GetProgressiveCraftingMaxDamage() );
	}
	
	 protected int GetProgressiveCraftingMaxDamage() //how long it takes... don't forget to match value from RecipesRibCarving!
	 {
	    return 10;
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

    //when bad hits == 3, break item and return stone to player
    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {
    	itemStack.damageItem( -1, player );
    	player.playSound( "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
    	
    	if (itemStack.getItemDamage() <= 0)
    	{
    		
    		FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack(FCBetterThanWolves.fcItemStone, 1));
    		itemStack.damageItem( 11, player );
    		//to remove item from inv VVV
    		//player.inventory.mainInventory[player.inventory.currentItem] = null;
    		player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( SuperBTWDefinitions.flintBlade, 1);;
    	}
    	
    	
    }
	
	
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.iconArray = new Icon[knappingIconNameArray.length];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(knappingIconNameArray[var2]);
        }
    }
    
    public Icon getItemIconForUseDuration(int par1)
    {
        return this.iconArray[par1];
    }
}
*/


//**********************************************************************
//*****************OLD STUFF VVV

//	private Icon m_iconFlintKnapping;
//
//	@Override    
//	public void registerIcons( IconRegister register )
//	{
//		super.registerIcons( register );
//   	
//		m_iconFlintKnapping = register.registerIcon( "SuperBTWItemFlintKnapping" );
//	}
	
///*STANDARD CODE (HOLD RIGHT CLICK)*/
//	public ItemStack onItemRightClick( ItemStack itemStack, World world, EntityPlayer entityPlayer )
//	{
//		
//		PerformUseEffects(entityPlayer);
//		
//		itemStack.damageItem( -1, entityPlayer );
//		
//		if (itemStack.getItemDamage() == 0) 
//		{
//			FCUtilsItem.GivePlayerStackOrEject( entityPlayer, new ItemStack(FCBetterThanWolves.fcItemStone, 1));
//			
//			return new ItemStack( SuperBTWDefinitions.flintBlade, 1);
//		}
//		else 
//		{
//			return itemStack;
//		}
//		
//	}
//	
//	protected void PerformUseEffects( EntityPlayer player )
//	{
//		player.playSound( "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
//		//System.out.println("USED KNAPPING USED KNAPPING USED KNAPPING");
//	}

/*ATTEMPT TO MAKE IT TAKE DAMAGE WHEN RIGHT CLICK IS RELEASED*/
//	public ItemStack onItemRightClick( ItemStack itemStack, World world, EntityPlayer entityPlayer )
//	{
//		
//		PerformUseEffects(entityPlayer);
//		
//		entityPlayer.setItemInUse( itemStack, getMaxItemUseDuration( itemStack ) );
//		if (holding == false) 
//		{
//			itemStack.damageItem( -1, entityPlayer );
//			holding = true;
//		}
//		
//		if (itemStack.getItemDamage() == 0) 
//		{
//			FCUtilsItem.GivePlayerStackOrEject( entityPlayer, new ItemStack(FCBetterThanWolves.fcItemStone, 1));
//			
//			return new ItemStack( SuperBTWDefinitions.flintBlade, 1);
//		}
//		else 
//		{
//			return itemStack;
//		}
//		
//	}
//	
//	public void onPlayerStoppedUsing(ItemStack itemStack, World par2World, EntityPlayer entityPlayer, int par4) 
//	{
//		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH!!!!");
//		holding = false;
//		
//	}

	
//	//from FCItemCraftingProgressive
//    protected int GetProgressiveCraftingMaxDamage()
//    {
//    	return m_iDefaultMaxDamage;
//    }
    
//    public int getMaxItemUseDuration(ItemStack par1ItemStack)
//    {
//        return 5000;
//    }

