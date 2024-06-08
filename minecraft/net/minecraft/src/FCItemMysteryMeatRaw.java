// FCMOD

package net.minecraft.src;

public class FCItemMysteryMeatRaw extends FCItemFood
{
	public FCItemMysteryMeatRaw( int iItemID )
	{
		super( iItemID, 4, 0.25F, true, "fcItemMysteryMeatRaw", true );
		
		SetStandardFoodPoisoningEffect();
	}
	
	//AARON added packing :D
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
    	return SuperBTWDefinitions.meatCube.blockID;
    }

	//----------- Client Side Functionality -----------//
	
	@Override
    public void registerIcons( IconRegister register )
    {
        itemIcon = register.registerIcon("beefRaw");
    }
}
