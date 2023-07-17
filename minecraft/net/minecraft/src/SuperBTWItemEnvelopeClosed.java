package net.minecraft.src;

public class SuperBTWItemEnvelopeClosed extends Item
{
    /** Number of ticks to run while 'EnumAction'ing until result. */
    public final int itemUseDuration;
	
	public SuperBTWItemEnvelopeClosed(int iItemID)
	{
	super( iItemID );
	
    maxStackSize = 1;        
    
    this.itemUseDuration = 16;
    
    SetBellowsBlowDistance( 1 );
    
    setUnlocalizedName( "SuperBTWItemEnvelopeClosed" );
	}
	
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 16;
    }
	
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        
        ItemStack theGift = null;
        
        theGift = new ItemStack(SuperBTWDefinitions.ironBlade, 1);
        
//        theGift = stack.getTagCompound().get

        
        player.playSound( "step.cloth", 
	        	0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
	        	( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
        
        
        
        return theGift;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.miscUse;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        
        return par1ItemStack;
    }

}
