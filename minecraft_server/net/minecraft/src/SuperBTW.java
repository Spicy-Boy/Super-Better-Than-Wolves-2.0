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
	private boolean isWorldBorderEnabled;
	
	private int rectangularWorldBorderX;
	private int rectangularWorldBorderZ;
	private boolean isWorldBorderAroundSpawn;
	
	private boolean isCustomRespawnRadiusEnabled;
	private int customRespawnRadius;
	private int customRespawnExclusionRadius;
	
	private boolean isLightningFireEnabled;

	
	
    private SuperBTW() 
    {
        super("SUPER BETTER THAN WOLVES", "BETA 2.4 (w/ nilla gourds ~_~)", "SBTW");
    }

	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Preparing...");
		
		//Deprecated VVV As of January 25, 2024, we use the preinitialize config property manager set by FCAddon
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
	
//	private PropertyManager settings;
//	private boolean isTpaEnabled;
//	private boolean isRandomHCStartEnabled;
//	private boolean isTeamStartEnabled;
//	private boolean isWorldBorderEnabled;
//	
//	private int rectangularWorldBorderX;
//	private int rectangularWorldBorderZ;
//	private boolean isWorldBorderAroundSpawn;
//	
//	private boolean isCustomRespawnRadiusEnabled;
//	private int customRespawnRadius;
//	private int customRespawnExclusionRadius;
//
//	private boolean isLightningFireEnabled;

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
//				playerMP.playerNetServerHandler.setPlayerLocation(1000, 60, 100, playerMP.rotationYaw, playerMP.rotationPitch);
				
				teleportPlayerToTeamLocationOrDefault(playerMP);

			}
		}
		
		
	}

//Methods (and variables) related to teams and coordinates (same functionality as SuperBTWCommandTpTeam methods) ~ ~ ~ ~ ~ ~ ~ ~ ~
	/*
	 * NOTE FOR THE FUTURE LOSER- Create a SuperBTWTpTeamManager object to keep track of tpteam information and file reading
	 * 
	 * 
	 */
	
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
    
    //updates the mappings for both coords and usernames maps
    public void updateMaps()
    {
    	teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
    	teamsAndCoords = createTeamsAndCoordsMap(filePath);
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
	
	//Methods related to the SBTWserver.properties file V V V ~~~~~~~~~~~~~~~~~~ V V V >_< ^_^
		@Override
		public void PreInitialize() {
			
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
			
			String propertyName5 = "Enable-World-Border";
			registerProperty(propertyName5, "false", "The World Border gives players a zap for leaving the boundaries (a rectangle centered on 0,0)");
			this.setWorldBorderEnabled(this.loadConfigProperties().get(propertyName5).equals("true"));
			
			String propertyName7 = "Set-World-Border-X-Axis";
			registerProperty(propertyName7, "5000", "");
			this.setRectangularWorldBorderX( Integer.parseInt(this.loadConfigProperties().get(propertyName7)) );
			//TESTER VVV
			System.out.println(propertyName7 +"="+getRectangularWorldBorderX());
			
			String propertyName8 = "Set-World-Border-Z-Axis";
			registerProperty(propertyName8, "5000", "");
	        this.setRectangularWorldBorderZ( Integer.parseInt(this.loadConfigProperties().get(propertyName8)) );
			//TESTER VVV
			System.out.println(propertyName8 +"="+getRectangularWorldBorderZ());
	        
			String propertyName6 = "Center-World-Border-around-SPAWN?";
			registerProperty(propertyName6, "false", "");
			this.setIsWorldBorderAroundSpawn(this.loadConfigProperties().get(propertyName6).equals("true"));
			
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
			
//			String propertyName = "";
//			registerProperty(propertyName, "false", "");
//			this.set( Integer.parseInt(this.loadConfigProperties().get(propertyName10)) );
			
//			String propertyName = "";
//			registerProperty(propertyName, "false", "");
//			this.set( Integer.parseInt(this.loadConfigProperties().get(propertyName10)) );
		}
	
//	public void initializeServerProperties()
//	{
//        //AARON attempts to initialize a custom server config :D
//        FCAddOnHandler.LogMessage("Loading SBTW Server properties");
//        this.settings = new PropertyManager(new File("SBTWserver.properties"), net.minecraft.server.MinecraftServer.getServer().getLogAgent());
//        this.setTpaEnabled(this.settings.getBooleanProperty("Enable-TPA-commands", false));
//        
//        this.setRandomHCStartEnabled(this.settings.getBooleanProperty("Start-with-random-HC-Spawn", false));
//        this.setTeamStartEnabled(this.settings.getBooleanProperty("Start-at-tpteam-location", false));
//        
//        this.setWorldBorderEnabled(this.settings.getBooleanProperty("Enable-World-Border", false));
//        this.setIsWorldBorderAroundSpawn(this.settings.getBooleanProperty("Enable-world-border-centered-around-spawn", false));
//        this.setRectangularWorldBorderX(this.settings.getIntProperty("World-border-X-distance", 5000));
//        this.setRectangularWorldBorderZ(this.settings.getIntProperty("World-border-Z-distance", 5000));
//        
//        this.setCustomRespawnRadiusEnabled(this.settings.getBooleanProperty("Enable-custom-HC-respawn-radius", false));
//        this.setCustonRespawnRadius(this.settings.getIntProperty("Custom-HC-respawn-radius", 2000));
//        this.setCustomRespawnExclusionRadius(this.settings.getIntProperty("Custom-HC-respawn-exclusion-radius", 1000));
//	
//        this.setLightningFireEnabled(this.settings.getBooleanProperty("Enabled-lightning-fire", true));
//	}
	
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
	
	public void setWorldBorderEnabled(boolean b) 
	{
		this.isWorldBorderEnabled = b;
	}
	public boolean getWorldBorderEnabled() 
	{
		return isWorldBorderEnabled;
	}
	
	public void setIsWorldBorderAroundSpawn(boolean b) 
	{
		this.isWorldBorderAroundSpawn = b;
	}
	public boolean getIsWorldBorderAroundSpawn() 
	{
		return isWorldBorderAroundSpawn;
	}
	
	public void setRectangularWorldBorderX(int i)
	{
		this.rectangularWorldBorderX = i;
	}
	public int getRectangularWorldBorderX()
	{
		return rectangularWorldBorderX;
	}
	
	public void setRectangularWorldBorderZ(int i)
	{
		this.rectangularWorldBorderZ = i;
	}
	public int getRectangularWorldBorderZ()
	{
		return rectangularWorldBorderZ;
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
	
	public void setLightningFireEnabled(boolean b)
	{
		this.isLightningFireEnabled = b;
	}
	public boolean getLightningFireEnabled()
	{
		return isLightningFireEnabled;
	}

}