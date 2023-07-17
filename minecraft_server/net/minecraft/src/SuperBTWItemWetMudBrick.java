package net.minecraft.src;

public class SuperBTWItemWetMudBrick extends FCItemPlacesAsBlock 
{

	public SuperBTWItemWetMudBrick(int iItemID) 
	{
    	super( iItemID, SuperBTWDefinitions.wetMudBrick.blockID );
    	
    	m_bRequireNoEntitiesInTargetBlock = true; // so that it isn't immediately kicked away
    	
    	setUnlocalizedName( "SuperBTWItemWetMudBrick" );
    	
    	setCreativeTab( CreativeTabs.tabMaterials );
	}
	
    @Override
    public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
    {
		if ( player.m_iTimesCraftedThisTick == 0 && world.isRemote )
		{
			player.playSound( "mob.slime.attack", 0.25F, 
				(  world.rand.nextFloat() - world.rand.nextFloat() ) * 0.1F + 0.7F );
		}
		
		super.onCreated( stack, world, player );
    }

}
