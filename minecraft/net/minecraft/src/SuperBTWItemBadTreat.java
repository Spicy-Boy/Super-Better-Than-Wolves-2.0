package net.minecraft.src;

public class SuperBTWItemBadTreat extends FCItemFood 
{
	public SuperBTWItemBadTreat(int iItemID) 
	{
		super (iItemID, -2, 0.25F, false, "SuperBTWItemTreat", true);
		
	    this.setCreativeTab(CreativeTabs.tabFood);
        //AARON added so that pigs can survive on 5 pieces of treat flesh a day
//        SetPigFoodValue( EntityAnimal.m_iBaseGrazeFoodValue * 8);
//        isWolfsFavoriteMeat();
        
//        SetBuoyant();        
//        SetIncineratedInCrucible();
        
	}
	
    public boolean IsWolfFood()
    {
    	return true;
    }
}