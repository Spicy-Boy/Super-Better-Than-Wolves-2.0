package net.minecraft.src;

public class SuperBTWCommandSetHome extends CommandBase 
{

	public String getCommandName()
	{
		return "sethome";
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
	        throw new WrongUsageException("Try /sethome", new Object[0]);
	    } 
		else if (!SuperBTW.instance.getSetHomeEnabled())
		{
			throw new WrongUsageException("This command is disabled.", new Object[0]);
		}
	    else
	    {
	    	EntityPlayerMP settingPlayer = getCommandSenderAsPlayer(sender);
	    	
	        if (settingPlayer == null) 
	        {
	            throw new PlayerNotFoundException();
	        }
	        
	        settingPlayer.setXCoordHome(settingPlayer.posX);
	        settingPlayer.setYCoordHome(settingPlayer.posY);
	        settingPlayer.setZCoordHome(settingPlayer.posZ);
	        
	        settingPlayer.addChatMessage("Your home has been set.");
        }
	        
	        
	}

}
