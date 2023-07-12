package net.minecraft.src;

public class SuperBTWItemIronBlade extends SuperBTWItemBlade 
//should be extends SuperBTWItemBlade
{
	
	static int durability = 75;
	private final int m_iWeaponDamage;
	
	public SuperBTWItemIronBlade(int iItemID) 
	{
		//ID, material, number of uses DEPCATED!!!
		super(iItemID);
		// TODO Auto-generated constructor stub
	    	
		SetFilterableProperties( Item.m_iFilterable_Narrow );
		
	    setUnlocalizedName( "SuperBTWItemIronBlade" ); 
	    
	    this.setCreativeTab(CreativeTabs.tabTools);
	    
	    SetIncineratedInCrucible();
	    
	    m_iWeaponDamage = 4; //same as stone axe
	    
        this.setMaxDamage(durability);
	}
	
	
    public float getStrVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
    	float fStrength = super.getStrVsBlock( stack, world, block, i, j, k );
    	
    	if ( block.blockID == Block.web.blockID || block.blockID == FCBetterThanWolves.fcBlockWeb.blockID )
    	{	
    		return fStrength *= 150;
    	}
    	
        Material material = block.blockMaterial;
        
        if ( material == Material.plants || material == Material.vine || material == Material.coral || material == Material.leaves || material == Material.pumpkin || material == FCBetterThanWolves.fcMaterialLog)
        {
        	return 4.5F;
        } 
        
//        if (block.blockID == Block.wood.blockID)
//        {
//        	return 4.0F;
//        }
    	
    	return fStrength;
    }
    
	public int getMaterial()
	{
		return 1; //0 is flint, 1 is iron
	}
	
    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }
}
