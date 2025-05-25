package net.minecraft.src;

public class SuperBTWCommandHome extends CommandBase 
{

	public String getCommandName()
	{
		return "home";
	}
	
    /**
     * Return the required permission level for this command.
     */
	@Override
	public int getRequiredPermissionLevel()
    {
        return 0;
    }
	
    /**
     * Returns true if the given command sender is allowed to use this command.
     */
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }
	
	public void processCommand(ICommandSender sender, String[] arguments)
	{
	    if (arguments.length > 1) 
	    {
	        throw new WrongUsageException("Try /home", new Object[0]);
	    } 
		else if (!SuperBTW.instance.getSetHomeEnabled())
		{
			throw new WrongUsageException("This command is disabled.", new Object[0]);
		}
	    else
	    {
	    	EntityPlayerMP teleportingPlayer = getCommandSenderAsPlayer(sender);
	    	
	        if (teleportingPlayer == null) 
	        {
	            throw new PlayerNotFoundException();
	        }
	        
        	teleportingPlayer.mountEntity((Entity)null);
        	teleportingPlayer.playerNetServerHandler.setPlayerLocation(teleportingPlayer.xHome, teleportingPlayer.yHome, teleportingPlayer.zHome, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
	        
	        teleportingPlayer.addChatMessage("Welcome home :D");
        }
	        
	        
	}

}
