package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	//to initialize and read a settings file
	private PropertyManager settings;
	private boolean isTpaEnabled;
	private boolean isRandomHCStartEnabled;
	private boolean isTeamStartEnabled;
	
	private boolean areDynamicTorchesEnabled;
	private boolean is2x2LeatherEnabled;
	private boolean areHungerTweaksEnabled;
	private boolean areBranchesEnabled;
	private boolean isHardcoreBouncingEnabled;
	
	private boolean isLightningFireEnabled;
	
	private boolean isCustomRespawnRadiusEnabled;
	private int customRespawnRadius;
	private int customRespawnExclusionRadius;
	
	//Dawn's color order from Deco Addon!!
	public final static String[] colorOrder = {"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	
    private SuperBTW() 
    {
    	super("SUPER BETTER THAN WOLVES", "BETA 2.4 (w/ nilla gourds ~_~)", "SBTW");
    }

	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Preparing...");
		
		//deprecated vvv as of Jan 25, 2024, we use the preinitialize config method set by FCAddon
//		this.initializeServerProperties();
		
    	SuperBTWDefinitions.addDefinitions();
    	SuperBTWRecipes.addRecipes();
    	
    	if (SuperBTW.instance.getTeamStartEnabled())
    	{
	    	//server teams initialization, only if team start is enabled for now
			filePath = "tpteams.txt";
			teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
			teamsAndCoords = createTeamsAndCoordsMap(filePath);
    	}
		
    	FCAddOnHandler.LogMessage(this.getName() + " Super Better Than Wolves initialized!");
	}

	public String GetLanguageFilePrefix()
	{
		return "SuperBTW";
	}
	
	@Override
	public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP playerMP) 
	{
//		super.serverPlayerConnectionInitialized(serverHandler, playerMP);
		
		if (SuperBTW.instance.getTeamStartEnabled() && playerMP.deathCounter == 0)
		{
					
			if (playerMP != null) //if the player exists at time of execution
			{
				//TESTER VVV
//				System.out.println("attempting a tpteams start teleportation baby");
				
				teleportPlayerToTeamLocationOrDefault(playerMP);

//				playerMP.playerNetServerHandler.setPlayerLocation(1000, 60, 100, playerMP.rotationYaw, playerMP.rotationPitch);
			}
		}
		
		
	}

//Methods (and variables) related to teams and coordinates (same functionality as SuperBTWCommandTpTeam methods) ~ ~ ~ ~ ~ ~ ~ ~ ~
	
	//accessible tpteams maps
	Map<String, List<String>> teamsAndUsernames;
	Map<String, double[]> teamsAndCoords;
	String filePath; //name or location of the file containing the above information
	
	
	private void teleportPlayerToTeamLocationOrDefault(EntityPlayerMP teleportingPlayer) 
	{
		boolean playerFound = false;
		
    	for (String key : teamsAndCoords.keySet()) 
    	{
    		String teleportingPlayerName = teleportingPlayer.getEntityName();

    		if (doesUsernameExistInTeam(teleportingPlayerName, key) || doesUsernameExistInTeam(teleportingPlayerName.toLowerCase(), key))
			{
				double x = teamsAndCoords.get(key)[0]; //reminder: the x,y,z values are stored in an array
//        		System.out.println(x);
				double y = teamsAndCoords.get(key)[1];
//        		System.out.println(y);
				double z = teamsAndCoords.get(key)[2];
//        		System.out.println(z);
				
				teleportingPlayer.playerNetServerHandler.setPlayerLocation(x, y, z, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
				teleportingPlayer.deathCounter += 1; //the player's death counter will begin on 1 rather than 0... ;p
				playerFound = true;
			}
    	}
    	
    	//if not found in any teams, send player to default spawn location :p
		if (!playerFound && !SuperBTW.instance.getRandomHCStartEnabled())
		{
			//TESTER VVV
    		System.out.println("Sending player to default start!");
    		
			String defaultTeamName = "default";
			
			double x = teamsAndCoords.get(defaultTeamName)[0]; //reminder: the x,y,z values are stored in an array
    		System.out.println(x);
			double y = teamsAndCoords.get(defaultTeamName)[1];
    		System.out.println(y);
			double z = teamsAndCoords.get(defaultTeamName)[2];
    		System.out.println(z);
			
			teleportingPlayer.playerNetServerHandler.setPlayerLocation(x, y, z, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
			teleportingPlayer.deathCounter += 1; //the player's death counter will begin on 1 rather than 0... ;p
		}
	}
	
    public boolean doesUsernameExistInTeam(String username, String team)
    {
    	List<String> listUsernames = teamsAndUsernames.get(team);
    	
    	for (int p = 0; p < listUsernames.size(); p++) 
    	{
    		//TESTER VVV
//    		System.out.println("Does "+username+" equal "+listUsernames.get(p));
    		
    		if (username.equals(listUsernames.get(p)))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
	
	//creates an hash map connecting team names (Strings) to array lists of usernames (<Strings>)
	public Map<String, List<String>> createTeamsAndUsernamesMap(String filePath)
	{
		teamsAndUsernames = new HashMap<String, List<String>>();

        try 
        {
        	BufferedReader br = new BufferedReader(new FileReader(filePath));
        	String line;
        	String currentTeam = null;
        	
        	while ((line = br.readLine()) != null)
        	{
        		if (line.isEmpty())
        		{
        			continue;
        		}
        		else if (line.startsWith("$")) //$ is the marker for a team name
        		{
        			currentTeam = line.substring(1); //excludes the $ marker
        			teamsAndUsernames.put(currentTeam, new ArrayList<String>());
        		}
        		else if (currentTeam != null 
        				&& !(line.startsWith("x:"))
        				&& !(line.startsWith("y:"))
        				&& !(line.startsWith("z:"))
        				)
        		{
            		teamsAndUsernames.get(currentTeam).add(line); //adds the username to the arrayList at the current team key
            	}
        	}
            br.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
		
		return teamsAndUsernames;
	}
		
	//creates an hash map connecting team names (Strings) to an array of coordinates (<integers>)
	public Map<String, double[]> createTeamsAndCoordsMap(String filePath)
	{
		teamsAndCoords = new HashMap<String, double[]>();

        try 
        {
        	BufferedReader br = new BufferedReader(new FileReader(filePath));
        			
        	String line;
        	String currentTeam = null;
        	
        	while ((line = br.readLine()) != null)
        	{
        		if (line.startsWith("$")) //$ is the marker for a team name
        		{
        			currentTeam = line.substring(1); //excludes the $ marker
        			teamsAndCoords.put(currentTeam, new double[3]); //maps the team name to an empty array of three ints
        		}
        		else if (line.startsWith("x:")) 
        		{
        			teamsAndCoords.get(currentTeam)[0] = Double.parseDouble(line.substring(2).trim()); //adds the integer only (excludes the 'x:') to the arrayList at the specified team key
            	}
        		else if (line.startsWith("y:")) 
        		{
        			teamsAndCoords.get(currentTeam)[1] = Double.parseDouble(line.substring(2).trim()); //adds the integer only (excludes the 'y:') to the arrayList at the specified team key
            	}
        		else if (line.startsWith("z:")) 
        		{
        			teamsAndCoords.get(currentTeam)[2] = Double.parseDouble(line.substring(2).trim()); //adds the integer only (excludes the 'z:') to the arrayList at the specified team key
            	}
        	}
        	br.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
		
		return teamsAndCoords;
	}
	
	//Methods related to the SBTW.properties file V V V ~~~~~~~~~~~~~~~~~~ V V V >_< ^_^
	//NOTE the temp variables propertyName[X] are a holdover from rough draft of code... 
	//I kept them, but just know they don't mean anything
	@Override
	public void PreInitialize()
	{
		String propertyName1 = "Enable-TPA-Commands";
		registerProperty(propertyName1, "false", "Allows players to use the TPA commands--/tpa [playername], /tpaccept [playername], and /tpcancel..");
		this.setTpaEnabled(this.loadConfigProperties().get(propertyName1).equals("true"));
		//TESTER VVV
		System.out.println(propertyName1 +"="+getTpaEnabled());
		
		String propertyName2 = "Enable-Lightning-Fire";
		registerProperty(propertyName2, "true", "Toggles whether or not bolts of lightning can start fires..the horror....the horror..");
		this.setLightningFireEnabled(this.loadConfigProperties().get(propertyName2).equals("true"));
		
		String propertyName3 = "Enable-Random-HC-Start";
		registerProperty(propertyName3, "false", "Players will begin at a random hardcore spawn instead of worldspawn.");
		this.setRandomHCStartEnabled(this.loadConfigProperties().get(propertyName3).equals("true"));
		
		String propertyName4 = "Enable-TpTeam-Start";
		registerProperty(propertyName4, "false", "Players begin at their set tpteam location, or the default location if no team is specified. See tpteams.txt (/tpteam set default) (/tpteam add [playername] [tpteam name])..");
		this.setTeamStartEnabled(this.loadConfigProperties().get(propertyName4).equals("true"));
		
		String propertyName9 = "Enable-Custom-HC-Respawn-Radius";
		registerProperty(propertyName9, "false", "If enabled, players will respawn within this set range");
		this.setCustomRespawnRadiusEnabled(this.loadConfigProperties().get(propertyName9).equals("true"));

		String propertyName10 = "Set-Custom-Respawn-Radius";
		registerProperty(propertyName10, "2000", "An unchanging HC respawn radius..");
		this.setCustomRespawnRadius( Integer.parseInt(this.loadConfigProperties().get(propertyName10)) );
		//TESTER VVV
		System.out.println(propertyName10 +"="+getCustomRespawnRadius());
		
		String propertyName11 = "Set-Custom-Respawn-Exclusion-Radius";
		registerProperty(propertyName11, "100", "The inner disc of the HC Spawn radius where nobody can spawn..");
		this.setCustomRespawnExclusionRadius( Integer.parseInt(this.loadConfigProperties().get(propertyName11)) );
		//TESTER VVV
		System.out.println(propertyName10 +"="+getCustomRespawnExclusionRadius());
		
		String propertyName18 = "Enable-Dynamic-Torches";
		registerProperty(propertyName18, "true", "If enabled, torches emit light when held in the hand!");
		this.setDynamicTorchesEnabled(this.loadConfigProperties().get(propertyName18).equals("true"));
		
		String propertyName19 = "Enable-2x2-Leather-Armor-Crafting";
		registerProperty(propertyName19, "true", "If enabled, leather armor can be crafted from cut leather in the 2x2 grid!");
		this.set2x2LeatherEnabled(this.loadConfigProperties().get(propertyName19).equals("true"));
		
		String propertyName20 = "Enable-Branches";
		registerProperty(propertyName20, "true", "If enabled, branches fall from trees and generate under trees!");
		this.setBranchesEnabled(this.loadConfigProperties().get(propertyName20).equals("true"));
		
		String propertyName21 = "Enable-Hunger-Tweaks";
		registerProperty(propertyName21, "true", "Turn this off to experience the original BTW hunger bar properties.. (ex: can't jump on famished)");
		this.setHungerTweaksEnabled(this.loadConfigProperties().get(propertyName21).equals("true"));
		
		String propertyName22 = "Enable-Hardcore-Bouncing";
		registerProperty(propertyName22, "false", "Hardcore bouncing prevents block placement while airborn. Disabled by default in SBTW!");
		this.setIsHardcoreBouncingEnabled(this.loadConfigProperties().get(propertyName22).equals("true"));

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
	
	public void setLightningFireEnabled(boolean b)
	{
		this.isLightningFireEnabled = b;
	}
	public boolean getLightningFireEnabled()
	{
		return this.isLightningFireEnabled;
	}
	
	public void setDynamicTorchesEnabled(boolean b)
	{
		this.areDynamicTorchesEnabled = b;
	}
	public boolean getDynamicTorchesEnabled()
	{
		return this.areDynamicTorchesEnabled;
	}
	
	public void set2x2LeatherEnabled(boolean b)
	{
		this.is2x2LeatherEnabled = b;
	}
	public boolean get2x2LeatherEnabled()
	{
		return this.is2x2LeatherEnabled;
	}
	
	public void setBranchesEnabled(boolean b)
	{
		this.areBranchesEnabled = b;
	}
	public boolean getBranchesEnabled()
	{
		return this.areBranchesEnabled;
	}
	
	public void setHungerTweaksEnabled(boolean b)
	{
		this.areHungerTweaksEnabled = b;
	}
	public boolean areHungerTweaksEnabled()
	{
		return this.areHungerTweaksEnabled;
	}
	
	public void setCustomRespawnRadiusEnabled(boolean b)
	{
		this.isCustomRespawnRadiusEnabled = b;
	}
	public boolean getCustomRespawnRadiusEnabled()
	{
		return isCustomRespawnRadiusEnabled;
	}
	
	public void setCustomRespawnRadius(int i)
	{
		this.customRespawnRadius = i;
	}
	public int getCustomRespawnRadius()
	{
		return customRespawnRadius;
	}
	
	public void setCustomRespawnExclusionRadius(int i)
	{
		this.customRespawnExclusionRadius = i;
	}
	public int getCustomRespawnExclusionRadius()
	{
		return customRespawnExclusionRadius;
	}
	
	public void setIsHardcoreBouncingEnabled(boolean b)
	{
		this.isHardcoreBouncingEnabled = b;
	}
	public boolean isHardcoreBouncingEnabled()
	{
		return isHardcoreBouncingEnabled;
	}
	
//	public void initializeServerProperties()
//	{
//        //AARON attempts to initialize a custom server config :D
//        FCAddOnHandler.LogMessage("Loading SBTW Server properties");
//        this.settings = new PropertyManager(new File("SBTWserver.properties"), net.minecraft.server.MinecraftServer.getServer().getLogAgent());
//        this.setTpaEnabled(this.settings.getBooleanProperty("Enable-TPA-commands", false));
//        this.setRandomHCStartEnabled(this.settings.getBooleanProperty("Start-with-random-HC-Spawn", false));
//        this.setTeamStartEnabled(this.settings.getBooleanProperty("Start-at-tpteam-location", false));
//	}

}