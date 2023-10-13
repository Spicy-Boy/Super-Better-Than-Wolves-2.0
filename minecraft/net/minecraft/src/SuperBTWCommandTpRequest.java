package net.minecraft.src;

//a player sends a teleport request by typing /tpa [playername]

public class SuperBTWCommandTpRequest extends CommandBase 
{
	
	public String getCommandName()
	{
		return "tpa";
	}
	
	public int getRequiredPermissionlevel()
	{
		return 2;
	}
	
	public void processCommand(ICommandSender sender, String[] arguments)
	{
		
	}
	
	
	
}
