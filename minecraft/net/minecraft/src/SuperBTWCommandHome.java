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
	        
	        if (teleportingPlayer.zHome == -15)
	        {
	        	//this is a simplistic approach to not having home set-- basically, use the /spawn code and send them to spawn instead
	            boolean searchingForEmptySpace = true;
	            int yIncrementer = 0;
	            
	            while (searchingForEmptySpace)
	            {
	                Material aboveTargetMaterial = teleportingPlayer.worldObj.getBlockMaterial(teleportingPlayer.worldObj.getWorldInfo().getSpawnX(), teleportingPlayer.worldObj.getWorldInfo().getSpawnY()+yIncrementer, teleportingPlayer.worldObj.getWorldInfo().getSpawnZ());
	                if (!aboveTargetMaterial.isSolid() && !aboveTargetMaterial.isLiquid())
	                {
	                    searchingForEmptySpace = false;
	                }
	                else
	                {
	                    yIncrementer++;
	                    if (yIncrementer > 32)
	                    {
	                        break;
	                    }
	                }
	            }

	            teleportingPlayer.mountEntity((Entity)null);
	            teleportingPlayer.playerNetServerHandler.setPlayerLocation(teleportingPlayer.worldObj.getWorldInfo().getSpawnX(), teleportingPlayer.worldObj.getWorldInfo().getSpawnY()+yIncrementer, teleportingPlayer.worldObj.getWorldInfo().getSpawnZ(), teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
	            
	        }
	        else
	        {
	        	teleportingPlayer.mountEntity((Entity)null);
	        	teleportingPlayer.playerNetServerHandler.setPlayerLocation(teleportingPlayer.xHome, teleportingPlayer.yHome, teleportingPlayer.zHome, teleportingPlayer.rotationYaw, teleportingPlayer.rotationPitch);
		        
	        }
	        

	        teleportingPlayer.addChatMessage("Welcome home :D");
        }
	        
	        
	}

}
