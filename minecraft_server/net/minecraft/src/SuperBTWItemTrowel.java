package net.minecraft.src;

public class SuperBTWItemTrowel extends Item
{
	
	private final int m_iWeaponDamage;
	public SuperBTWItemTrowel(int par1)
	{
        super(par1);
        setFull3D();
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName( "SuperBTWItemTrowel" );
        
        m_iWeaponDamage = 1;
	}
	
    @Override
    public boolean onItemUse( ItemStack stack, EntityPlayer player, World world, 
    	int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
    	ItemStack mortarStack = GetFirstMortarStack( player );
    	
		if (mortarStack != null)
		{
			
	        if ( player != null && player.canPlayerEdit( i, j, k, iFacing, stack ) )
	        {
	        	Block targetBlock = Block.blocksList[world.getBlockId( i, j, k )];
	        	
	        	if ( targetBlock != null && targetBlock.OnMortarApplied( world, i, j, k ) )
	        	{            	
	    			if ( !world.isRemote )
	    			{
	    		        world.playAuxSFX( FCBetterThanWolves.m_iMortarAppliedAuxFXID, i, j, k, 0 ); 
	    			}
	    			
	    			if (isMortarBucket(mortarStack.itemID))
	    			{
	    				mortarStack.useItemRightClick(world, player);
	    				
	        			if ((mortarStack.getItemDamage()) + 1 >= 16)
	        			{
//	        				System.out.println("BUCKET SHOULD BE DELETED");
	        				
	        				player.inventory.consumeInventoryItem(mortarStack.itemID);
	        				FCUtilsItem.GivePlayerStackOrEject(player, new ItemStack(Item.bucketEmpty));
//	        				player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty));
	        			}
	        			else
	        			{
	        				mortarStack.damageItem( 1, player);
	        			}
	        			
	    			}
	    			else 
	    			{
	    				player.inventory.consumeInventoryItem( mortarStack.itemID );
	    			}
	    			
	            	return true;
	        	}
	        }
	        
		}
        
        return false;
    }
	
    public ItemStack GetFirstMortarStack( EntityPlayer player )
    {
    	for ( int iTempSlot = 0; iTempSlot < player.inventory.mainInventory.length; iTempSlot++ )
    	{
    		ItemStack tempStack = player.inventory.getStackInSlot( iTempSlot );
    		
    		if ( tempStack != null && IsItemMortar( tempStack.itemID ) )
    		{
    			return tempStack;
    		}
    	}
    	
    	return null;
    }
    
    public boolean IsItemMortar( int iItemID )
    {    	
//    	augsburger puppenkiste urmel
    	return iItemID == Item.clay.itemID 
    			|| iItemID == FCBetterThanWolves.fcItemNetherSludge.itemID
    			|| iItemID == Item.slimeBall.itemID
    			|| iItemID == SuperBTWDefinitions.mortarBucket.itemID;
    }
    
    public boolean isMortarBucket (int iItemID)
    {
    	return iItemID == SuperBTWDefinitions.mortarBucket.itemID;
    }
    
    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }

}
