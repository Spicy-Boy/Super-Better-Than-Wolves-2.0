package net.minecraft.src;

public class SuperBTWItemRibCarving extends SuperBTWItemBladeProgressive 
{
	
	static int internalBladeDamage;
	
	public SuperBTWItemRibCarving(int iItemID) {
		super(iItemID);
		// TODO Auto-generated constructor stub
	    	
	    setUnlocalizedName( "SuperBTWItemRibCarving" );  
	    
	    setMaxDamage( GetProgressiveCraftingMaxDamage() );
	    
	 }
	
	 protected int GetProgressiveCraftingMaxDamage() //how long it takes... don't forget to match value from RecipesRibCarving!
	 {
	    return 200;
	 }
	 
	 protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
	 {
	    	//worldObj.playAuxSFX( FCBetterThanWolves.m_iCreeperNeuteredAuxFXID, i, j, k, 0 );
	        player.playSound( "mob.slime.small", 
	        	0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
	        	( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
	        
	        player.playSound( "random.eat", 0.5F + 0.5F * (float)player.rand.nextInt(2), ( player.rand.nextFloat() * 0.25F ) + 1.75F );
	        
	        if ( player.worldObj.isRemote )
	        {
		        for (int var3 = 0; var3 < 5; ++var3)
		        {
			        Vec3 var4 = player.worldObj.getWorldVec3Pool().getVecFromPool( ( (double)player.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D );
			        
			        var4.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180.0F );
			        var4.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180.0F );
			        
			        Vec3 var5 = player.worldObj.getWorldVec3Pool().getVecFromPool( ( (double)player.rand.nextFloat() - 0.5D) * 0.3D, (double)(-player.rand.nextFloat()) * 0.6D - 0.3D, 0.6D );
			        
			        var5.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180.0F);
			        var5.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180.0F);
			        
			        var5 = var5.addVector( player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
			        
			        player.worldObj.spawnParticle( "iconcrack_" + this.itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord);
		        }
	        }
	 }
	 
	 public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
	 {
	    	ItemStack ribCarving = new ItemStack( SuperBTWDefinitions.ribCarving, 1);
	    	
	        world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
	        
			//FCUtilsItem.GivePlayerStackOrEject( player, leatherWorking );
	        
	        int internalBladeDamage;
	        
	        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;
	        
	        if (internalBladeDamage >= 16)
	        {
	        	
	        }
	        else
	        {
	        	FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack(SuperBTWDefinitions.flintBlade, 1, internalBladeDamage));
	        	FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack(Item.beefRaw, 1));
	        }
	        
	        
			
	        return new ItemStack( SuperBTWDefinitions.rib, 1);
	 }
	 
	    
	 public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
	 {
		 super.onCreated( stack, world, player );
		 
	 }
	 


}
