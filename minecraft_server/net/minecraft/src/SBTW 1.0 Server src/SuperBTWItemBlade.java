package net.minecraft.src;

public class SuperBTWItemBlade extends Item
{
	private final int m_iWeaponDamage = 0;
	
	protected SuperBTWItemBlade( int iItemID) 
	{
		super(iItemID);
		setFull3D();
		this.setMaxStackSize(1);
	}

	public int getMaterial()
	{
		return 1; //0 is flint, 1 is iron
	}
	
    public float getStrVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
    	return super.getStrVsBlock( stack, world, block, i, j, k );
    }
    
    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }
    
    public boolean onBlockDestroyed( ItemStack stack, World world, int iBlockID, int i, int j, int k, EntityLiving usingEntity )
    {
    	stack.damageItem( 1, usingEntity );
        return true;
    }
    
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }
    
    private boolean isDamaged;
    
    @Override
    public boolean IsConsumedInCrafting()
    {
    	return false;
    }
    
//    @Override
//    public void OnUsedInCrafting(int iItemDamage, EntityPlayer player, ItemStack outputStack)
//    {
////    	if (outputStack.itemID == SuperBTWDefinitions.orangePumpkinSeeds.itemID ||
////    			outputStack.itemID == SuperBTWDefinitions.orangePumpkinSeeds.itemID ||
////    			outputStack.itemID == SuperBTWDefinitions.orangePumpkinSeeds.itemID ||
////    			outputStack.itemID == SuperBTWDefinitions.orangePumpkinSeeds.itemID
//    	   )
//    	{
//    		isDamaged = true;
//    	}
//    	else isDamaged = false;
//    }
    
    @Override
    public boolean IsDamagedInCrafting() {
    	
    	return isDamaged;
    }
    
    @Override
    public void OnBrokenInCrafting( EntityPlayer player )
    {
    	PlayBreakSoundOnPlayer( player );
    }
    
    static public void PlayBreakSoundOnPlayer( EntityPlayer player )
    {
		// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
    	
		player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );
    }
    
    
//    @Override
//    public boolean onBlockDestroyed( ItemStack stack, World world, int iBlockID, int i, int j, int k, EntityLiving usingEntity )
//    {
//        if ( iBlockID != Block.leaves.blockID && 
//        	iBlockID != FCBetterThanWolves.fcBlockBloodLeaves.blockID &&
//        	iBlockID != Block.vine.blockID && 
//        	iBlockID != FCBetterThanWolves.fcBlockHempCrop.blockID )
//        {
//            return super.onBlockDestroyed(stack, world, iBlockID, i, j, k, usingEntity);
//        }
//        else
//        {
//            stack.damageItem( 1, usingEntity );
//            return true;
//        }
//    }
	

}
