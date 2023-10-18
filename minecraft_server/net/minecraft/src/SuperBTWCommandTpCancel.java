package net.minecraft.src;

public class SuperBTWCommandTpCancel extends CommandBase
{
	public String getCommandName()
	{
		return "tpcancel";
	}
	
	public int getRequiredPermissionlevel()
	{
		return 0;
	}
	
	public void processCommand(ICommandSender sender, String[] arguments)
	{
		
	    if (arguments.length > 1) 
	    {
	        throw new WrongUsageException("Try /tpcancel", new Object[0]);
	    } 
	    else
	    {
		    
			EntityPlayerMP cancelingPlayer;
	    	//gets the person canceling the request (the person sending the message) as a EntityPlayerMP object
	    	cancelingPlayer = getCommandSenderAsPlayer(sender);
	    	
	        if (cancelingPlayer == null) 
	        {
	            throw new PlayerNotFoundException();
	        }
	        
	        cancelingPlayer.addChatMessage("Canceled active teleport request.");
	        
	        cancelingPlayer.setTpaRequestName("");
	    }
        
        
    	
    	
	    
	}
	
}
