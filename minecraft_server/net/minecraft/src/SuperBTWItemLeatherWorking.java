package net.minecraft.src;

public class SuperBTWItemLeatherWorking extends SuperBTWItemBladeProgressive 
{
	
	static int internalBladeDamage;
	
	public SuperBTWItemLeatherWorking(int iItemID) {
		super(iItemID);
		// TODO Auto-generated constructor stub
	    	
	    setUnlocalizedName( "SuperBTWItemLeatherWorking" );  
	    
	    setMaxDamage( GetProgressiveCraftingMaxDamage() );
	    
	 }
	
	 protected int GetProgressiveCraftingMaxDamage() //how long it takes... don't forget to match value from RecipesLeatherCutting!
	 {
	    return 150;
	 }
	 
	 protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
	 {
	    	//worldObj.playAuxSFX( FCBetterThanWolves.m_iCreeperNeuteredAuxFXID, i, j, k, 0 );
	        player.playSound( "step.cloth", 
	        	0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
	        	( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
	 }
	 
	 public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
	 {
	    	ItemStack leatherWorking = new ItemStack( SuperBTWDefinitions.leatherWorking, 1);
	    	
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
	        }
	        
	        
			
	        return new ItemStack( FCBetterThanWolves.fcItemLeatherCut, 2);
	 }
	 
	 private Icon m_iconLeatherWorking;
	 
//	 @Override    
//	 public void registerIcons( IconRegister register )
//	 {
//		 super.registerIcons( register );
//	    	
//	     m_iconLeatherWorking = register.registerIcon( "SuperBTWItemLeatherWorking" );	     
//	 }
	    
	 public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
	 {
		 super.onCreated( stack, world, player );
		 
	 }
	 


}
