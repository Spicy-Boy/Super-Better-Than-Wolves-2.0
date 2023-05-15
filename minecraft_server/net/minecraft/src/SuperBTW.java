package net.minecraft.src;

public class SuperBTW extends FCAddOn
{
	

/**
 * @author EpicAaron29 (aaron on the discord)
 *
 */

	public static SuperBTW instance = new SuperBTW();
	
    private SuperBTW() 
    {
        super("SUPER BETTER THAN WOLVES", "1.0", "");
    }

	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		
    	SuperBTWDefinitions.addDefinitions();
    	SuperBTWRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Initialized Super Better Than Wolves");

	}
	
	
	public String GetLanguageFilePrefix()
	{
		return "SuperBTW";
	}
	
}


