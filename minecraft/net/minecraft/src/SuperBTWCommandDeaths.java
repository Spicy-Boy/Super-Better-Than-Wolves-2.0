package net.minecraft.src;

public class SuperBTWCommandDeaths extends CommandBase 
{
	
	public String getCommandName()
	{
		return "deaths";
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
		EntityPlayerMP commandSender = getCommandSenderAsPlayer(sender);
		commandSender.addChatMessage(""+commandSender.deathCounter);
	}

}
