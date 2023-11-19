package net.minecraft.src;

//Purpose of this class is to mess around with how commands work in MC
//Test 1: send a chat to a player

public class SuperBTWCommandTEST extends CommandBase 
{
	public String getCommandName()
	{
		return "gubgub";
	}
	
	public int getRequiredPermissionlevel()
	{
		return 2;
	}

	public void processCommand(ICommandSender sender, String[] arguments)
	{
		EntityPlayerMP teleportingPlayer = findPlayer(sender, arguments[0]);
		
//		EntityPlayerMP targetPlayer = findPlayer(sender, arguments[1]);
		
//		int x, y, z;
		
		teleportingPlayer.setPositionAndUpdate(200, 200, 200);
		
		System.out.println("bingus bingus");
	}
}

