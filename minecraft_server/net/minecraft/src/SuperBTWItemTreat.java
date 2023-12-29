package net.minecraft.src;

public class SuperBTWItemTreat extends FCItemFood 
{
	public SuperBTWItemTreat(int iItemID) 
	{
		super (iItemID, 2, 0.25F, false, "SuperBTWItemTreat", true);

        //AARON added so that pigs can survive on 5 pieces of treat flesh a day
        SetPigFoodValue( EntityAnimal.m_iBaseGrazeFoodValue * 8);
        isWolfsFavoriteMeat();

        SetBuoyant();        
        SetIncineratedInCrucible();

	}

    public boolean IsWolfFood()
    {
    	return true;
    }
}