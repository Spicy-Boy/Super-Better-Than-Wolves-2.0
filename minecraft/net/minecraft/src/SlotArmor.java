package net.minecraft.src;

class SlotArmor extends Slot
{
    /**
     * The armor type that can be placed on that slot, it uses the same values of armorType field on ItemArmor.
     */
    final int armorType;

    /**
     * The parent class of this clot, ContainerPlayer, SlotArmor is a Anon inner class.
     */
    final ContainerPlayer parent;

    SlotArmor(ContainerPlayer par1ContainerPlayer, IInventory par2IInventory, int par3, int par4, int par5, int par6)
    {
        super(par2IInventory, par3, par4, par5);
        this.parent = par1ContainerPlayer;
        this.armorType = par6;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 1;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    //AARON CHANGED to allow pumpkin hats
//    public boolean isItemValid(ItemStack par1ItemStack)
//    {
//    	//make compatible with Socks Crops
////        return itemStack == null ? false : (itemStack.getItem() instanceof ItemArmor ? ((ItemArmor)itemStack.getItem()).armorType == this.armorType : (itemStack.getItem().itemID != Block.pumpkin.blockID 
////        		&& (getPumpkin(itemStack))/*par1ItemStack.getItem().itemID != SCDefs.pumpkinCarved.blockID */ && itemStack.getItem().itemID != Item.skull.itemID ? false : this.armorType == 0));
//    	return par1ItemStack == null ? false : (par1ItemStack.getItem() instanceof ItemArmor ? ((ItemArmor)par1ItemStack.getItem()).armorType == this.armorType : ( getPumpkin(par1ItemStack) && par1ItemStack.getItem().itemID != Item.skull.itemID ? false : this.armorType == 0));
//
//    }
    
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack == null ? false : (par1ItemStack.getItem() instanceof ItemArmor ? ((ItemArmor)par1ItemStack.getItem()).armorType == this.armorType : (par1ItemStack.getItem().itemID != Block.pumpkin.blockID && getPumpkin(par1ItemStack) && par1ItemStack.getItem().itemID != Item.skull.itemID ? false : this.armorType == 0));
    }


    //AARON ADDED
    private boolean getPumpkin(ItemStack itemStack) {
        boolean pumpkin = false;
        
        if (itemStack.getItem().itemID != SCDefs.pumpkinCarved.blockID) pumpkin = true;
        
        else if (itemStack.getItemDamage() != 3 ) pumpkin = true;
        
        return pumpkin;
    }

	/**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public Icon getBackgroundIconIndex()
    {
        return ItemArmor.func_94602_b(this.armorType);
    }
}
