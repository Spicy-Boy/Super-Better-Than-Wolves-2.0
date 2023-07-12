// FCMOD

package net.minecraft.src;

public class FCItemChiselWood extends FCItemChisel
{	
	private final int m_iWeaponDamage;
    protected FCItemChiselWood( int iItemID )
    {
        super( iItemID, EnumToolMaterial.WOOD, 2 );
        
        SetBuoyant();
    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.SHAFT.m_iBurnTime / 2 );    	
		SetFilterableProperties( m_iFilterable_Narrow );
        
        efficiencyOnProperMaterial /= 4;
        
        setUnlocalizedName( "fcItemChiselWood" );
        
        m_iWeaponDamage = 2;
    }
    
    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }
    
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }
    
    //------------- Class Specific Methods ------------//
    
	//------------ Client Side Functionality ----------//
}
