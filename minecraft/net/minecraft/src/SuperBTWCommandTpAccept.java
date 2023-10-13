package net.minecraft.src;

public class SuperBTWCommandTpAccept extends CommandBase
{
	public String getCommandName()
	{
		return "tpaccept";
	}
	
	public int getRequiredPermissionlevel()
	{
		return 2;
	}
	
	public void processCommand(ICommandSender sender, String[] arguments)
	{
	    if (arguments.length < 1) 
	    {
	        throw new WrongUsageException("Try /tpaccept [playername]", new Object[0]);
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
            }
            else
            {
            	acceptingPlayer.addChatMessage("No active teleport requests found from "+teleportingPlayerName+".");
            }
            
	    }
	    
	}
}
