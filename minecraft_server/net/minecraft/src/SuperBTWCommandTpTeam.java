package net.minecraft.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.server.MinecraftServer;

//tasty hashmap tutorial: https://www.youtube.com/watch?v=H62Jfv1DJlU

public class SuperBTWCommandTpTeam //extends CommandBase
{
//	//initializes a map with team names as the keys and lists of usernames as the values
//	Map<String, List<String>> teamsAndUsernames;
//	
//	//initializes a map with team names as the keys and an array of 3 coordinates a each value(integers)
//	//the coords will represent x y z values used for teleportation (in that order: x, y, z)
//	Map<String, int[]> teamsAndCoords;
//	
//	String filePath; //name or location of the file containing the above information
//	
//	/* EXAMPLE OF TEXT FILE FORMAT
//
//$Team1
//x:200
//y:68
//z:2
//topguy
//EpicAaron29
//dingitaaaa
//dawnraider
//GatesOfLogic
//
//	 */
//	
//	public SuperBTWCommandTpTeam() //initialize the command object and prepare the team names
//	{
//		filePath = "tpteams.txt";
//		
//		teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
//		
//		
//		teamsAndCoords = createTeamsAndCoordsMap(filePath);
//		
//		//TESTER for teams&users map VVV
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
//        //END TESTER for teams&coords map
//		
//	}
//	
////	public void saveNewTextFile() 
////	{
////		
////	}
//	
//	public String getCommandName()
//	{
//		return "tpteam";
//	}
//	
//    /**
//     * Return the required permission level for this command.
//     */
//    public int getRequiredPermissionLevel()
//    {
//        return 2;
//    }
//    
//    public void refreshMapsFromTextFile()
//    //recreates the teams&players map and the teams&coords map from the text file
//    {
//		teamsAndUsernames = createTeamsAndUsernamesMap(filePath);
//		
//		teamsAndCoords = createTeamsAndCoordsMap(filePath);
//    }
//    
//    public void processCommand(ICommandSender par1ICommandSender, String[] commandStringArray)
//    {
//    	if (commandStringArray.length < 1)
//    	{
//    		throw new WrongUsageException("Try /tpteam list, /tpteam list [teamname], /tpteam [teamname] (to teleport players), or /tpteam refresh", new Object[0]);
//    	}
//    	else if (commandStringArray.length >= 1)
//        {
//
//            if (commandStringArray[0].equals("list")) //used to see team names and members in-game
//            {
//                if (commandStringArray.length < 2) 
//                //if command = "list" and nothing else, print list of teams
//                {
//                	Set<String> keySet = teamsAndCoords.keySet();
//                	String teams = "Teams:";
//                	for (String key : keySet)
//                	{
//                		teams += " "+key;
//                	}
//                	par1ICommandSender.sendChatToPlayer(teams);
//                	return;
//                }
//                else if (commandStringArray.length == 2) //ex: /tpteam list Team3 would print all the players under Team3
//                //if second parameter is a valid team name, print the usernames listed inside that team
//                {
//                	if (!isTeamName(commandStringArray[1])) //checks to see if the second parameter is a team name
//                	{
//                		throw new WrongUsageException("Team "+commandStringArray[1]+" couldn't be found.", new Object[0]);
//                	}
//                	
//                	String players = "Players:";
//                	
//                	for (int x = 0; x < teamsAndUsernames.get(commandStringArray[1]).size(); x++)
//                	{
//                		players += " "+teamsAndUsernames.get(commandStringArray[1]).get(x);
//                	}
//                	par1ICommandSender.sendChatToPlayer(players);
//                	return;
//                }
//
//            }
//            else if (commandStringArray[0].equals("refresh"))
//            {
//            	notifyAdmins(par1ICommandSender, "Refreshing tpteam text file!", new Object[0]);
//            	refreshMapsFromTextFile();
//            	return;
//            }
//        }
//    }
//    
//    public boolean isTeamName(String userInput) 
//    //modularly checks to see if a team name has been typed, returns true if so 
//    //(case sensitive!)
//    {
//    	Set<String> keySet = teamsAndCoords.keySet();
//
//    	for (String key : keySet) //checks if the team name exists in the keys for teamsAndCoords map
//    	{
//    		if (key.equals(userInput))
//    		{
//    			return true;
//    		}
//    	}
//    	
//    	return false;
//    }
//	
//	//creates an hash map connecting team names (Strings) to array lists of usernames (<Strings>)
//	public Map<String, List<String>> createTeamsAndUsernamesMap(String filePath)
//	{
//		teamsAndUsernames = new HashMap<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
//        {
//        	String line;
//        	String currentTeam = null;
//        	
//        	while ((line = br.readLine()) != null)
//        	{
//        		if (line.isEmpty())
//        		{
//        			continue;
//        		}
//        		else if (line.startsWith("$")) //$ is the marker for a team name
//        		{
//        			currentTeam = line.substring(1); //excludes the $ marker
//        			teamsAndUsernames.put(currentTeam, new ArrayList<>());
//        		}
//        		else if (currentTeam != null 
//        				&& !(line.startsWith("x:"))
//        				&& !(line.startsWith("y:"))
//        				&& !(line.startsWith("z:"))
//        				)
//        		{
//            		teamsAndUsernames.get(currentTeam).add(line); //adds the username to the arrayList at the current team key
//            	}
//        	}
//        }
//        catch (IOException e) 
//        {
//            e.printStackTrace();
//        }
//		
//		return teamsAndUsernames;
//	}
//	
//	//creates an hash map connecting team names (Strings) to an array of coordinates (<integers>)
//	public Map<String, int[]> createTeamsAndCoordsMap(String filePath)
//	{
//		teamsAndCoords = new HashMap<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
//        {
//        	String line;
//        	String currentTeam = null;
//        	
//        	while ((line = br.readLine()) != null)
//        	{
//
//            	
//        		if (line.startsWith("$")) //$ is the marker for a team name
//        		{
//        			currentTeam = line.substring(1); //excludes the $ marker
//        			teamsAndCoords.put(currentTeam, new int[3]); //maps the team name to an empty array of three ints
//        		}
//        		else if (line.startsWith("x:")) 
//        		{
//        			teamsAndCoords.get(currentTeam)[0] = Integer.parseInt(line.substring(2).trim()); //adds the integer only (excludes the 'x:') to the arrayList at the specified team key
//            	}
//        		else if (line.startsWith("y:")) 
//        		{
//        			teamsAndCoords.get(currentTeam)[1] = Integer.parseInt(line.substring(2).trim()); //adds the integer only (excludes the 'y:') to the arrayList at the specified team key
//            	}
//        		else if (line.startsWith("z:")) 
//        		{
//        			teamsAndCoords.get(currentTeam)[2] = Integer.parseInt(line.substring(2).trim()); //adds the integer only (excludes the 'z:') to the arrayList at the specified team key
//            	}
//        	}
//        }
//        catch (IOException e) 
//        {
//            e.printStackTrace();
//        }
//		
//		return teamsAndCoords;
//	}
 
}
