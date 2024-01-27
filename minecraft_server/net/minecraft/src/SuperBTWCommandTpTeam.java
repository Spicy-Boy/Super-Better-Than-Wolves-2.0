package net.minecraft.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.server.MinecraftServer;

//tasty hashmap tutorial: https://www.youtube.com/watch?v=H62Jfv1DJlU

public class SuperBTWCommandTpTeam extends CommandBase
{
	//initializes a map with team names as the keys and lists of usernames as the values
	Map<String, List<String>> teamsAndUsernames;
	
	//initializes a map with team names as the keys and an array of 3 coordinates a each value(integers)
	//the coords will represent x y z values used for teleportation (in that order: x, y, z)
	Map<String, double[]> teamsAndCoords;
	
	String filePath; //name or location of the file containing the above information
	
	/* EXAMPLE OF TEXT FILE FORMAT tpteams.txt

	$Team1
	x:200
	y:68
	z:2
	topguy
	EpicAaron29
	dingitaaaa
	dawnraider
	GatesOfLogic

	 */
	
	public SuperBTWCommandTpTeam() //initialize the command object and prepare the team names
	{
		filePath = "tpteams.txt";
		
		teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
		
		teamsAndCoords = createTeamsAndCoordsMap(filePath);
		
		//TESTER for teams&users map VVV
//		System.out.println(teamsAndUsernames);
//		
//		//TESTER for teams&coords map VVV
//        // Get the set of keys from the HashMap
//        Set<String> keySet = teamsAndCoords.keySet();
//
//        // Iterate through the key set and print the keys and their corresponding values
//        for (String key : keySet) 
//        {
//            System.out.println("Key: "+key);
//            System.out.println("x: "+teamsAndCoords.get(key)[0]);
//            System.out.println("y: "+teamsAndCoords.get(key)[1]);
//            System.out.println("z: "+teamsAndCoords.get(key)[2]);
//        }
        //END TESTER for teams&coords map
		
	}
	
//	public void saveNewTextFile() 
//	{
//		
//	}
	
	public String getCommandName()
	{
		return "tpteam";
	}
	
    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    public void refreshMapsFromTextFile()
    //recreates the teams&players map and the teams&coords map from the text file
    {
		teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
		
		teamsAndCoords = createTeamsAndCoordsMap(filePath);
    }
    
    public void processCommand(ICommandSender par1ICommandSender, String[] commandStringArray)
    {
    	if (commandStringArray.length < 1) //if no parameters are entered besides /tpteam
    	{
    		
    		notifyAdmins(par1ICommandSender, "Information: /tpteam list, /tpteam list [teamname], tpteam refresh", new Object[0]);
    		notifyAdmins(par1ICommandSender, "Teleportation: /tpteam [teamname] (team tp), /tpteam player [playername] (individual tp)", new Object[0]);
    		notifyAdmins(par1ICommandSender, "Modification: /tpteam new [teamname], /tpteam set [teamname], tpteam remove [playername], tpteam add [playername]", new Object[0]);

    		return;
    	}
    	
   
    	if (commandStringArray.length >= 1)
		{
    		
    		//tp entire team sub command
			if (isTeamName(commandStringArray[0]))
			//^^^checks to see if the parameter equals an existing team name
			//if true, teleport all players in the team to the specified coordinates!
			{
				double x = teamsAndCoords.get(commandStringArray[0])[0]; //reminder: the x,y,z values are stored in an array
//				System.out.println(x);
				double y = teamsAndCoords.get(commandStringArray[0])[1];
//				System.out.println(y);
				double z = teamsAndCoords.get(commandStringArray[0])[2];
//				System.out.println(z);
				
				List<String> usernamesInTeam = teamsAndUsernames.get(commandStringArray[0]);
//				System.out.println(usernamesInTeam);
				
				for (int p = 0; p < usernamesInTeam.size(); p++) //iterates all players in the team list
				{
//					EntityPlayerMP teleportingPlayer = findPlayer(par1ICommandSender, usernamesInTeam.get(p));
					EntityPlayerMP teleportingPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(usernamesInTeam.get(p));
					if (teleportingPlayer != null) //if the player exists at time of execution
					{
						//teleport players one at a time
						teleportingPlayer.playerNetServerHandler.setPlayerLocation(x, y, z, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
					}
      
				}
				
			}
			
			//refresh sub command
            else if (commandStringArray[0].equals("refresh")) //recreate the two hash maps while the server is running
            {
            	notifyAdmins(par1ICommandSender, "Refreshing tpteams.txt!", new Object[0]);
            	refreshMapsFromTextFile();
            	return;
            }
			
			//list sub command
            else if (commandStringArray[0].equals("list")) //used to print team names and lists of players in teams
            {
                if (commandStringArray.length < 2) 
                //if command = "list" and nothing else, print list of teams
                {
                	Set<String> keySet = teamsAndCoords.keySet();
                	
                	String teams = "Teams:";
                	for (String key : keySet)
                	{
                		teams += " "+key;
                	}
                	par1ICommandSender.sendChatToPlayer(teams);
                	return;
                }
                else if (commandStringArray.length == 2) //ex: /tpteam list Team3 would print all the players under Team3
                //if second parameter is a valid team name, print the usernames listed inside that team
                {
                	if (!isTeamName(commandStringArray[1])) //checks to see if the second parameter is/not a team name
                	{
                		throw new WrongUsageException("Team "+commandStringArray[1]+" couldn't be found.", new Object[0]);
                	}
                	
                	String players = "Players:";
                	
                	for (int x = 0; x < teamsAndUsernames.get(commandStringArray[1]).size(); x++)
                	{
                		players += " "+teamsAndUsernames.get(commandStringArray[1]).get(x);
                	}
                	par1ICommandSender.sendChatToPlayer(players);
                	return;
                }

            }
            else if (commandStringArray[0].equals("player"))
            	//^^ player specific teleportation. Player can be sent to their team or another team local
            {
            	
                if (commandStringArray.length < 2) 
                    //^^ if command is only a single parameter, correct the player!
                    {
                   		throw new WrongUsageException("Try /tpteam player [player name] or add [team name] to the end.", new Object[0]);
                    }
                
                String userToTeleport = commandStringArray[1];
            	
            	EntityPlayerMP teleportingPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(userToTeleport);
            	
            	if (commandStringArray.length == 2)
            	{
                //^^ /tpteam player [playername] to tp a player to their respective team coords (if multiple teams, random)
            		
            		//TESTER VVVV
            		notifyAdmins(par1ICommandSender, "Initiated /tpteam player [playername]", new Object[0]);
                    
            		//TESTER VVV shows all available keys
//	            	for (String key : teamsAndCoords.keySet()) 
//	            	{
//	            		notifyAdmins(par1ICommandSender, ""+key, new Object[0]);
//	            	}
            		
	            	for (String key : teamsAndCoords.keySet()) 
	            	{
	            		notifyAdmins(par1ICommandSender, "Scanning team "+key, new Object[0]);
	            		
	            		if (doesUsernameExistInTeam(userToTeleport, key) || doesUsernameExistInTeam(userToTeleport.toLowerCase(), key))
	            		{
	        				double x = teamsAndCoords.get(key)[0]; //reminder: the x,y,z values are stored in an array
	//        				System.out.println(x);
	        				double y = teamsAndCoords.get(key)[1];
	//        				System.out.println(y);
	        				double z = teamsAndCoords.get(key)[2];
	//        				System.out.println(z);
	        				
	    					if (teleportingPlayer != null) //if the player exists at time of execution
	    					{
	    						//teleport players one at a time
	    						teleportingPlayer.playerNetServerHandler.setPlayerLocation(x, y, z, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
	    						notifyAdmins(par1ICommandSender, "Teleported "+commandStringArray[1]+" to location of "+key+".", new Object[0]);
	    						return;
	    					}
	            		}
	            	}
	            	
            		notifyAdmins(par1ICommandSender, "Player "+userToTeleport+" could not be found in any teams.", new Object[0]);
            		return;
            	}
            	else if (commandStringArray.length == 3)
            	{
              	//^^ /tpteam player [playername] [teamname] teleports selected player to selected team location
	            	
             		//TESTER VVVV
//            		notifyAdmins(par1ICommandSender, "Initiated /tpteam player [playername] [teamname]", new Object[0]);
            		
            		String teamKey = commandStringArray[2];
            		
                    if (!(isTeamName(teamKey)))
                    {
                     	throw new WrongUsageException("Specified team name doesn't exist. Case sensitive!", new Object[0]);
                    }
            		
            		
    				double x = teamsAndCoords.get(teamKey)[0]; //reminder: the x,y,z values are stored in an array
//        				System.out.println(x);
    				double y = teamsAndCoords.get(teamKey)[1];
//        				System.out.println(y);
    				double z = teamsAndCoords.get(teamKey)[2];
//        				System.out.println(z);
    				
					if (teleportingPlayer != null) //if the player exists at time of execution
					{
						//teleport players one at a time
						teleportingPlayer.playerNetServerHandler.setPlayerLocation(x, y, z, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
						notifyAdmins(par1ICommandSender, "Teleported "+userToTeleport+" to location of "+teamKey+".", new Object[0]);
						return;
					}
        		
            		notifyAdmins(par1ICommandSender, "Team or player not found!", new Object[0]);
            		return;
            	
            	}
            
            }
//            else if (commandStringArray[0].equals("self"))
//            //^^ teleports the sender to their team location (if in multiple teams, random)
////            In EntityPlayerMP, set an int to limit how many times a non-op can tp... probably this must be its own command?
//            {
//            	wadwadw
//            }
            else if (commandStringArray[0].equals("add")) 
            //^^ /tpteam add [playername] [teamname] (team names are CASE SENSITIVE!!!)
            //StringArray[1] = player name, [2] = team name
            {
                if (commandStringArray.length < 3) 
                //^^ if command is only a single parameter, correct the player!
                {
               		throw new WrongUsageException("Try /tpteam add [player name] [team name].", new Object[0]);
                }
                
                String teamKey = commandStringArray[2];
                String newUser = commandStringArray[1];
                
                List<String> usernames;
                
                if (!(isTeamName(teamKey)))
                {
                 	throw new WrongUsageException("Specified team name doesn't exist. Case sensitive!", new Object[0]);
                }

                usernames = teamsAndUsernames.get(teamKey);
                usernames.add(newUser);
                
                teamsAndUsernames.put(teamKey, usernames);
                
                try {
					writeTextFile(); //save the changes to the text file
					notifyAdmins(par1ICommandSender, "Added "+newUser+" to "+teamKey+".", new Object[0]);
				} 
                catch (IOException e) 
                {
					e.printStackTrace();
				}
            }
            else if (commandStringArray[0].equals("remove"))
            //^^ /tpteam remove [playername] [teamname] (team names are CASE SENSITIVE!!!)
            //Removed a player from a specified team list
            //No, you can't remove a team entirely using commands. That would suck to mess up!
            {
                if (commandStringArray.length < 3) 
                //^^ if command is only a single parameter, correct the player!
                {
               		throw new WrongUsageException("Try /tpteam add [player name] [team name].", new Object[0]);
                }
                
                String teamKey = commandStringArray[2];
                String userToDelete = commandStringArray[1];
                
                List<String> usernames;
                
                if (!(isTeamName(teamKey)))
                {
                 	throw new WrongUsageException("Specified team name doesn't exist. Case sensitive!", new Object[0]);
                }
                
                if (!(doesUsernameExistInTeam(userToDelete, teamKey)))
                {
                	throw new WrongUsageException("User isn't in specified team.", new Object[0]);
                }

                usernames = teamsAndUsernames.get(teamKey);
                usernames.remove(userToDelete);
                
                teamsAndUsernames.put(teamKey, usernames);
                
                try {
					writeTextFile(); //save the changes to the text file
					notifyAdmins(par1ICommandSender, "Removed "+userToDelete+" from "+teamKey+".", new Object[0]);
				} 
                catch (IOException e) 
                {
					e.printStackTrace();
				}
            }
            else if (commandStringArray[0].equals("set"))
            //^^ /tpteam set [teamname] to set the coordinates of the team to your current standing position
            //IF team doesn't exist, creates a new team with the set name! commandStringArray[1] = [teamname]
            {
            	EntityPlayerMP playerCommandSender = getCommandSenderAsPlayer(par1ICommandSender);
            	
            	if (isTeamName(commandStringArray[1])) //set new coords for team
            	{
            		teamsAndCoords.get(commandStringArray[1])[0] = playerCommandSender.posX;
            		teamsAndCoords.get(commandStringArray[1])[1] = playerCommandSender.posY;
            		teamsAndCoords.get(commandStringArray[1])[2] = playerCommandSender.posZ;
            		
    				notifyAdmins(par1ICommandSender, "Changed the coords for "+commandStringArray[1]+".", new Object[0]);

            	}
            	else //create brand new team
            	{
            		createNewTeam(commandStringArray[1], par1ICommandSender);
            	}
            	
            	try {
					writeTextFile();
				} 
            	catch (IOException e) 
            	{
					e.printStackTrace();
				}
            }
            else if (commandStringArray[0].equals("new"))
            //^^ /tpteam new [teamname] to create a new team with coordinates at your current standing position
            //commandStringArray[1] = [teamname]
            {
            	
            	createNewTeam(commandStringArray[1], par1ICommandSender);
            	
            	try {
					writeTextFile();
				} 
            	catch (IOException e) 
            	{
					e.printStackTrace();
				}
            }
    	}

    }

	public void writeTextFile() throws IOException   
    //Recreates the tpteams.txt file, usually with added/removed usernames!
    {
    	BufferedWriter writer = new BufferedWriter(new FileWriter("tpteams.txt"));
    	
    	Set<String> keySet = teamsAndCoords.keySet(); //gets a set of key names (each key is a team name)
    	
    	for (String key : keySet) //iterates all key names, populating the space beneath it with coords + names
    	{
    		writer.write("$"+ key);
    		writer.newLine();
    		
    		writer.write("x:"+teamsAndCoords.get(key)[0]);
    		writer.newLine();
    		
    		writer.write("y:"+teamsAndCoords.get(key)[1]);
    		writer.newLine();
    		
    		writer.write("z:"+teamsAndCoords.get(key)[2]);
    		writer.newLine();
    		
        	for (int i = 0; i < teamsAndUsernames.get(key).size(); i++)
        	{
        		writer.write(teamsAndUsernames.get(key).get(i));
        		writer.newLine();
        	}
        	writer.newLine();
    	}
    	
    	writer.close();
    	
    	//THIS PART UPDATES THE MAPPING USED IN SuperBTW.java TO SET DEFAULT SPAWN!
    	SuperBTW.instance.tpteamManager.updateMaps();
    	//Janky as hell, I know... I need to figure out a better way to share the maps between command and addon instance!
    	
    }
    
    public boolean isTeamName(String userInput) 
    //^^^ modularly checks to see if a team name has been typed, returns true if so 
    //(case sensitive!)
    {
    	Set<String> keySet = teamsAndCoords.keySet();

    	for (String key : keySet) //checks if the team name exists in the keys for teamsAndCoords map
    	{
    		if (key.equals(userInput))
    		{
    			return true;
    		}
    	}
    	
    	return false;
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
    
    private void createNewTeam(String newTeamName, ICommandSender sender)
    {
    	if (!(teamsAndUsernames.containsKey(newTeamName))) //if the specified team name doesn't already exist
    	{
        	double[] arrayCoords = new double[3];
        	
        	List<String> listUsernames = new ArrayList<String>();
        	
        	teamsAndUsernames.put(newTeamName,listUsernames);
        	
        	EntityPlayerMP playerCommandSender = getCommandSenderAsPlayer(sender);
        	
        	arrayCoords[0] = playerCommandSender.posX;
        	arrayCoords[1] = playerCommandSender.posY;
        	arrayCoords[2] = playerCommandSender.posZ;
        	
        	teamsAndCoords.put(newTeamName,arrayCoords);
        	
			notifyAdmins(sender, "Created a new team called "+newTeamName+".", new Object[0]);
			
    	}
    	else
    	{
        	throw new WrongUsageException("That team already exists!", new Object[0]);
    	}

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
 
}
