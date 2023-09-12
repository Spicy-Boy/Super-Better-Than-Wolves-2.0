package net.minecraft.src;

import java.util.ArrayList;

public class SuperBTW extends FCAddOn
{
	

/**
 * @author EpicAaron29 (aaron on the discord)
 *
 */

	public static SuperBTW instance = new SuperBTW();
	
    private SuperBTW() 
    {
        super("SUPER BETTER THAN WOLVES", "BETA 2.2 (w/ trusty melons uwu)", "");
    }

	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Preparing...");
		
    	SuperBTWDefinitions.addDefinitions();
    	SuperBTWRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Super Better Than Wolves initialized!");

	}

	public String GetLanguageFilePrefix()
	{
		return "SuperBTW";
	}
	
	@Override
	//Sockthing's fancy compatibility addon!
		public void addMinecartLoot(ArrayList<WeightedRandomChestContent> minecartLoot) 
		{
			// TODO Auto-generated method stub
//			minecartLoot.clear();
		//takes out the old melons, allows the new water melons to be placed
			minecartLoot.remove(new WeightedRandomChestContent(Item.melonSeeds.itemID, 0, 2, 4, 10) ); 
			minecartLoot.add(new WeightedRandomChestContent(SuperBTWDefinitions.waterMelonSeeds.itemID, 0, 2, 4, 10) ); 
			//key for parameters: item id, damage, min chance to generate, max chance to generate, how many times it tries
			
		}
	
	@Override
		public void addDesertPyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot,
				ArrayList<WeightedRandomChestContent> lootedChestLoot) 
		{
			// TODO Auto-generated method stub
			lootedChestLoot.add( new WeightedRandomChestContent( SuperBTWDefinitions.canaryMelonSeeds.itemID, 0, 1, 1, 7 ) );
			
		}

}