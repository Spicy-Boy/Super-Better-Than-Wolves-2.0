package net.minecraft.src;

public class SuperBTWItemCutGrass extends Item 
{

	public SuperBTWItemCutGrass(int iItemID) {
		super(iItemID);
		
//		System.out.println("XD\\nXD\\nXD\\nXD\\nXD\\nXD\\nXD\\nXD\\nXD\\nXD\\nXD\\nXD\\n");
		
		setUnlocalizedName("SCItemCuttings");
		
    	//AARON set this value so a cow can survive on ~10 straw a day 
    	//(obviously, should be supplemented with something else)
		SetHerbivoreFoodValue( EntityAnimal.m_iBaseGrazeFoodValue * 6);
		setCreativeTab( CreativeTabs.tabMaterials );   
		
    	SetBuoyant();
    	SetIncineratedInCrucible();
    	SetFilterableProperties( m_iFilterable_Narrow );
		
		//TODO reduces the flame on a campfire (dampens it) and creates a plume of black smoke
		//if placed in full flame campfire, then it burns normally
	}

}
