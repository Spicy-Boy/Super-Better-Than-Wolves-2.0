package net.minecraft.src;

/**
 * @author ) Tsughoggr on the forum
 * https://www.sargunster.com/btwforum/viewtopic.php?t=10163
 */

public class Gloryholes extends FCAddOn
{
	
	public static Gloryholes instance = new Gloryholes();
	
    private Gloryholes() 
    {
        super("Gloryholes and Blow", "V0.Jar Remix", "");
    }
	
	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Preparing...");

    	FCAddOnHandler.LogMessage(this.getName() + " Tsughoggr's Gloryholes and Blow initialized!");

	}

}
