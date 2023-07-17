// FCMOD

package net.minecraft.src;

public class FCItemRottenFlesh extends FCItemFood
{
    public FCItemRottenFlesh( int iItemID )
    {
        super( iItemID, 3, 0F, true, "rottenFlesh" );        

        SetIncreasedFoodPoisoningEffect();
        //AARON added so that pigs can survive on 10 pieces of rotten flesh a day
        SetPigFoodValue( EntityAnimal.m_iBaseGrazeFoodValue * 4);
    }
    
    
    
    @Override
    public boolean IsPistonPackable( ItemStack stack )
    {
    	return true;
    }
    
    @Override
    public int GetRequiredItemCountToPistonPack( ItemStack stack )
    {
    	return 9;
    }
    
    @Override
    public int GetResultingBlockIDOnPistonPack( ItemStack stack )
    {
    	return FCBetterThanWolves.fcBlockRottenFlesh.blockID;
    }    
}
