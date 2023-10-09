// FCMOD

package net.minecraft.src;

public class FCItemChiselStone extends FCItemChisel
{	
	private final int m_iWeaponDamage;
	
    protected FCItemChiselStone( int iItemID )
    {
        super( iItemID, EnumToolMaterial.STONE, 8 );
        
        SetFilterableProperties( Item.m_iFilterable_Small );
        
        efficiencyOnProperMaterial /= 2;
        
        setUnlocalizedName( "fcItemChiselStone" );   
        
        m_iWeaponDamage = 1;
    }
    
    @Override
    public float getStrVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
    	float fStrength = super.getStrVsBlock( stack, world, block, i, j, k );
    	
    	if ( block.blockID == Block.web.blockID || block.blockID == FCBetterThanWolves.fcBlockWeb.blockID )
    	{
    		// boost stone chisels against webs so that it reads a little better
    		
    		fStrength *= 2;
    	}
    	
    	return fStrength;
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
}
