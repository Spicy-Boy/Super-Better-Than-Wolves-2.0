package net.minecraft.src;

import java.io.File;

public class SuperBTW extends FCAddOn
{
	

/**
 * @author EpicAaron29 (aaron on the discord)
 *
 */

	public static SuperBTW instance = new SuperBTW();
	
	//to initialize and read a settings file
	private PropertyManager settings;
	private boolean isTpaEnabled;
	private boolean isRandomHCStartEnabled;
	
    private SuperBTW() 
    {
        super("SUPER BETTER THAN WOLVES", "BETA 2.2 (w/ vanilla melons owo)", "");
    }

	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Preparing...");
		
		this.initializeServerProperties();
    	SuperBTWDefinitions.addDefinitions();
    	SuperBTWRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Super Better Than Wolves initialized!");
	}

	public String GetLanguageFilePrefix()
	{
		return "SuperBTW";
	}
	
	public void initializeServerProperties()
	{
        //AARON attempts to initialize a custom server config :D
        FCAddOnHandler.LogMessage("Loading SBTW Server properties");
        this.settings = new PropertyManager(new File("SBTWserver.properties"), net.minecraft.server.MinecraftServer.getServer().getLogAgent());
        this.setTpaEnabled(this.settings.getBooleanProperty("Enable-TPA-commands", false));
        this.setRandomHCStartEnabled(this.settings.getBooleanProperty("Start-with-random-HC-Spawn", false));
        
	}
	
	public void setTpaEnabled(boolean b) 
	{
		this.isTpaEnabled = b;
	}
	
	public boolean getTpaEnabled()
	{
		return isTpaEnabled;
	}
	
	public void setRandomHCStartEnabled(boolean b) {
		// TODO Auto-generated method stub
		this.isRandomHCStartEnabled = b;
	}
	
	public boolean getRandomHCStartEnabled() {
		return isRandomHCStartEnabled;
	}

}