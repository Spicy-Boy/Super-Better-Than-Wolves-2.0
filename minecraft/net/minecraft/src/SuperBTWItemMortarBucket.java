package net.minecraft.src;

public class SuperBTWItemMortarBucket extends FCItemMortar
{

    public SuperBTWItemMortarBucket( int iItemID )
    {
    	super( iItemID );
    	
    	setUnlocalizedName( "SuperBTWItemMortarBucket" );
    	
    	setCreativeTab( CreativeTabs.tabMaterials );
    	
    	maxStackSize = 1;
    	
    	this.setMaxDamage(16); //number of uses
    }
    
    @Override
    public boolean onItemUse( ItemStack stack, EntityPlayer player, World world, 
    	int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
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
    			
    	        
    			if ((stack.getItemDamage()) + 1 >= 16)
    			{
    				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.bucketEmpty, 1);
    			}
    			else
    			{
        			stack.damageItem( 1, player);
    			}
        		
            	return true;
        	}
        }
        
        return false;
    }
    
    public void consumeUse(ItemStack stack, EntityPlayer player)
    {
		stack.damageItem( 1, player);
    }
    
    @Override
    public boolean DoesConsumeContainerItemWhenCrafted( Item containerItem )
    {
    	if ( containerItem.itemID == Item.bucketEmpty.itemID )
    	{
    		return true;
    	}
    	
    	return false;
    }
	
}
