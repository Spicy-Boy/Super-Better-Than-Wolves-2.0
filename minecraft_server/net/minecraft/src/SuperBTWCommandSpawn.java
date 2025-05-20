package net.minecraft.src;

public class SuperBTWCommandSpawn extends CommandBase
{
	
	public String getCommandName()
	{
		return "spawn";
	}

    /**
     * Return the required permission level for this command.
     */
	@Override
	public int getRequiredPermissionLevel()
    {
        return 2;
    }
	
	public void processCommand(ICommandSender sender, String[] arguments)
	{
    	EntityPlayerMP teleportingPlayer;
    	teleportingPlayer = getCommandSenderAsPlayer(sender);

    	// dont care actually vvv let them tp in the nether LOL 
//        if (!(teleportingPlayer.worldObj.getWorldInfo().getDimension() == 0))
//        {
//            throw new WrongUsageException("You can't do that here :)", new Object[0]);
//        }
    	
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
        
        teleportingPlayer.addChatMessage("Teleported you to spawn.");
	}
	
}
