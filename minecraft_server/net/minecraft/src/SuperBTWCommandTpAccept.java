package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class SuperBTWCommandTpAccept extends CommandBase
{
	public String getCommandName()
	{
		return "tpaccept";
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
	    if (arguments.length < 1) 
	    {
	        throw new WrongUsageException("Try /tpaccept [playername]", new Object[0]);
	    } 
		else if (!SuperBTW.instance.getTpaEnabled())
		{
			throw new WrongUsageException("This command is disabled.", new Object[0]);
		}
	    else
	    {
	    	EntityPlayerMP acceptingPlayer;
	    	//gets the person accepting the request (the person sending the message) as a EntityPlayerMP object
	    	acceptingPlayer = getCommandSenderAsPlayer(sender);
	    	
            if (acceptingPlayer == null) 
            {
                throw new PlayerNotFoundException();
            }
            
            EntityPlayerMP teleportingPlayer = findPlayer(sender, arguments[arguments.length - 1]);
            
            if (teleportingPlayer == null)
            {
            	throw new PlayerNotFoundException();
            }
            
            if (teleportingPlayer.worldObj != teleportingPlayer.worldObj) 
            {
            	notifyAdmins(sender, "commands.tp.notSameDimension", new Object[0]);
            	return;
            }
            
            String teleportingPlayerName = teleportingPlayer.getEntityName();
            String acceptingPlayerName = acceptingPlayer.getEntityName();
            
            if (teleportingPlayer.getTpaRequestName().equals(acceptingPlayerName))
            {
            	acceptingPlayer.addChatMessage("Teleported "+teleportingPlayerName+" to you.");
            	teleportingPlayer.addChatMessage("Teleported you to "+acceptingPlayerName+".");
            	
            	teleportingPlayer.mountEntity((Entity)null);
            	teleportingPlayer.playerNetServerHandler.setPlayerLocation(acceptingPlayer.posX, acceptingPlayer.posY, acceptingPlayer.posZ, acceptingPlayer.rotationYaw, acceptingPlayer.rotationPitch);
            	teleportingPlayer.setTpaRequestName("");
            }
            else
            {
            	acceptingPlayer.addChatMessage("No active teleport requests found from "+teleportingPlayerName+".");
            }
            
	    }
	    
	}
}
