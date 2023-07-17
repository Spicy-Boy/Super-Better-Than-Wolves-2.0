package net.minecraft.src;

public class SuperBTWItemFlintBlade extends SuperBTWItemBlade 
//should be extends SuperBTWItemBlade
{
	static int durability = 15;
	private final int m_iWeaponDamage;
	
	public SuperBTWItemFlintBlade(int iItemID) 
	{
		super(iItemID);
		// TODO Auto-generated constructor stub
		
		SetFilterableProperties( Item.m_iFilterable_Narrow );
	    	
	    setUnlocalizedName( "SuperBTWItemFlintBlade" );  
	    
	    this.setCreativeTab(CreativeTabs.tabTools);

	    m_iWeaponDamage = 3; //slightly weaker than stone axe
	    
        this.setMaxDamage(durability);
	   
	}
	
    public float getStrVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
    	float fStrength = super.getStrVsBlock( stack, world, block, i, j, k );
    	
    	if ( block.blockID == Block.web.blockID 
    			|| block.blockID == FCBetterThanWolves.fcBlockWeb.blockID)
    	{	
    		return fStrength *= 30;
    	}
    	
        Material material = block.blockMaterial;
        
        if ( material == Material.plants || material == Material.vine || material == Material.coral || material == Material.leaves || material == Material.pumpkin || material == FCBetterThanWolves.fcMaterialLog)
        {
        	return 3.0F;
        } 
        
//        if (block.blockID == Block.wood.blockID)
//        {
//        	return 3.0F;
//        }
        
    	return fStrength;
    }
    
	public int getMaterial()
	{
		return 0; //0 is flint, 1 is iron
	}
	
    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }


	
}

