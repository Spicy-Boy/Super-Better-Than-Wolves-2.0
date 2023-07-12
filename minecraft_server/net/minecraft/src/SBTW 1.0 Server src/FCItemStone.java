//FCMOD

package net.minecraft.src;

public class FCItemStone extends Item
{
	
	private final int m_iWeaponDamage;
    public FCItemStone( int iItemID )
    {
        super( iItemID );
        
    	setUnlocalizedName( "fcItemStone" );
    	
    	SetFilterableProperties( Item.m_iFilterable_Small );
    	
    	setCreativeTab( CreativeTabs.tabMaterials );	
    	
    	m_iWeaponDamage = 1; //hardcore bible murder
    }
    
    @Override
    public boolean IsPistonPackable( ItemStack stack )
    {
    	return true;
    }
    
    @Override
    public int GetRequiredItemCountToPistonPack( ItemStack stack )
    {
    	return 8;
    }
    
    @Override
    public int GetResultingBlockIDOnPistonPack( ItemStack stack )
    {
    	return FCBetterThanWolves.fcBlockCobblestoneLoose.blockID;
    }
    
    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }

    
    //------------- Class Specific Methods ------------//
    
	//------------ Client Side Functionality ----------//
}
    
