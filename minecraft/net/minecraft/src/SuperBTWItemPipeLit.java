package net.minecraft.src;

//a torch with a bow-drawing type animation on it
public class SuperBTWItemPipeLit extends Item 
{
    static public final int m_iMaxDamage = 32; // Setting this low helps reduce SMP updates
	
	public SuperBTWItemPipeLit(int itemID) 
	{
		super(itemID);
		setUnlocalizedName( "SuperBTWItemPipeLit" );
		SetBuoyant();
		
        maxStackSize = 1;
        
        setMaxDamage( m_iMaxDamage ); //the duration of the bar
		
	}
	
	//puts out the pipe if the player becomes submerged in water
    @Override
    public void onUpdate( ItemStack stack, World world, EntityPlayer entity, int iInventorySlot, boolean bIsHandHeldItem )
    {
    	if ( !world.isRemote && stack.stackSize > 0 )
    	{
        	if ( entity.isInWater() && entity.isInsideOfMaterial(Material.water) ) 
        	{
                int iFXI = MathHelper.floor_double( entity.posX );
                int iFXJ = MathHelper.floor_double( entity.posY ) + 1;
                int iFXK = MathHelper.floor_double( entity.posZ );
                
                //No fizz sound, pipe is too small for that
//		        world.playAuxSFX( 1004, iFXI, iFXJ, iFXK, 0 ); // fizz sound fx
		        
        		stack.itemID = SuperBTWDefinitions.pipeEmpty.itemID;       		        		
        	}
    	}
    	
    	
//    	//MYSTERIOUS INHERITANCE
//        public int getMaxItemUseDuration(ItemStack par1ItemStack)
//        {
//        	//same as bow, not sure what this does...
//        	
//            return 72000; 
//        }
//        
//        //when player releases right click, create puff of smoke
//        public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
//        {	
//        	
//        }
        
    }
    
    
    
    
    
    
	
	
	
}
