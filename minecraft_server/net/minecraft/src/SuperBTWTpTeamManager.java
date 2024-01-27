package net.minecraft.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperBTWTpTeamManager 
{	
	Map<String, List<String>> teamsAndUsernames;
	Map<String, double[]> teamsAndCoords;
	String filePath; //name or location of the file containing the above information
	
	public SuperBTWTpTeamManager(String fileName)
	{
		filePath = fileName; //usually, the file is called tpteams.txt
		
		teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
		teamsAndCoords = createTeamsAndCoordsMap(filePath);
	}
	
	public void teleportPlayerToTeamLocationOrDefault(EntityPlayerMP teleportingPlayer) 
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

}
