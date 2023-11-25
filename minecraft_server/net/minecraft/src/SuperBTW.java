package net.minecraft.src;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperBTW extends FCAddOn
{
	

/**
 * @author EpicAaron29 (aaron on the discord)
 *
 */

	public static SuperBTW instance = new SuperBTW();
	
	//accessible tpteams maps
	Map<String, List<String>> teamsAndUsernames;
	Map<String, double[]> teamsAndCoords;
	
	//to initialize and read a settings file
	private PropertyManager settings;
	private boolean isTpaEnabled;
	private boolean isRandomHCStartEnabled;
	private boolean isTeamStartEnabled;
	
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
	
	//These methods make the tpteams lists accessible to players!
	public Map<String, List<String>> getTeamsAndUsernamesMap ()
	{
		return teamsAndUsernames;
	}
	public void setTeamsAndUsernamesMap(Map<String, List<String>> map)
	{
		teamsAndUsernames = new HashMap<String, List<String>>(map);
	}
	
	public Map<String, double[]> getTeamsAndCoordsMap ()
	{
		return teamsAndCoords;
	}
	
	public void setTeamsAndCoordsMap(Map<String, double[]> map)
	{
		teamsAndCoords = new HashMap<String, double[]>(map);
	}
	
	
	//Methods related to the SBTWserver.properties file
	public void initializeServerProperties()
	{
        //AARON attempts to initialize a custom server config :D
        FCAddOnHandler.LogMessage("Loading SBTW Server properties");
        this.settings = new PropertyManager(new File("SBTWserver.properties"), net.minecraft.server.MinecraftServer.getServer().getLogAgent());
        this.setTpaEnabled(this.settings.getBooleanProperty("Enable-TPA-commands", false));
        this.setRandomHCStartEnabled(this.settings.getBooleanProperty("Start-with-random-HC-Spawn", false));
        this.setTeamStartEnabled(this.settings.getBooleanProperty("Start-at-tpteam-location", false));
	}
	
	public void setTpaEnabled(boolean b) 
	{
		this.isTpaEnabled = b;
	}
	public boolean getTpaEnabled()
	{
		return isTpaEnabled;
	}
	
	public void setRandomHCStartEnabled(boolean b) 
	{
		this.isRandomHCStartEnabled = b;
	}
	public boolean getRandomHCStartEnabled() 
	{
		return isRandomHCStartEnabled;
	}
	
	public void setTeamStartEnabled(boolean b) 
	{
		this.isTeamStartEnabled = b;
	}
	
	public boolean getTeamStartEnabled() 
	{
		return isTeamStartEnabled;
	}
	
	

}