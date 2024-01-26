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
        super("SUPER BTW", "BETA 2.4 (w/ nilla gourds ~_~)", "SBTW");
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

}