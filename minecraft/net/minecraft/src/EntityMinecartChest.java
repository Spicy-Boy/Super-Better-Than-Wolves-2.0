package net.minecraft.src;

public class EntityMinecartChest extends EntityMinecartContainer
{
    public EntityMinecartChest(World par1World)
    {
        super(par1World);
    }

    public EntityMinecartChest(World par1, double par2, double par4, double par6)
    {
        super(par1, par2, par4, par6);
    }

    public void killMinecart(DamageSource par1DamageSource)
    {
        super.killMinecart(par1DamageSource);
        
        // FCMOD: Changed
        this.dropItemWithOffset(Block.chest.blockID, 1, 0.0F);
    	//dropItemWithOffset( FCBetterThanWolves.fcItemSawDust.itemID, 6, 0.0F );
    	//dropItemWithOffset( Item.stick.itemID, 2, 0.0F );
    	
        if ( !worldObj.isRemote )
        {        
			playSound( "mob.zombie.woodbreak", 0.25F, 0.75F + ( worldObj.rand.nextFloat() * 0.25F ) );
    }
        // END FCMOD        
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 27;
    }

    public int getMinecartType()
    {
        return 1;
    }

    public Block getDefaultDisplayTile()
    {
        return Block.chest;
    }

    public int getDefaultDisplayTileOffset()
    {
        return 8;
    }
}
