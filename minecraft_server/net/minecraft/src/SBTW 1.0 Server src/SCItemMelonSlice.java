package net.minecraft.src;

public class SCItemMelonSlice extends FCItemFoodHighRes {

	protected SCItemMelonSlice(int par1, String sItemName) {
		super(par1, 2, 0F, false, sItemName );
		setAlwaysEdible();
		
		setUnlocalizedName( sItemName ); 
	}
	
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
    	//drop melon seed
        if (!world.isRemote && world.rand.nextFloat() < 0.33F)
        {
        	ItemStack itemStack = new ItemStack( Item.melonSeeds, 1, 0 );
        	           
        	double dItemX = player.posX;
        	double dItemY = player.posY + 1.2D;
        	double dItemZ = player.posZ;
        	
            EntityItem entityItem = new EntityItem( world, dItemX, dItemY, dItemZ, itemStack );
            
            //copied & modified from EntityThrowable
            entityItem.motionX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4F);
            entityItem.motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4F);
            entityItem.motionY = (double)(-MathHelper.sin((player.rotationPitch + 0.0F) / 180.0F * (float)Math.PI) * 0.6F);
            
            entityItem.delayBeforeCanPickup = 20;
            
            world.spawnEntityInWorld( entityItem );
            
            
            
        }
    }

}
