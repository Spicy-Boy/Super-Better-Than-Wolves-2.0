package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class SuperBTWCommandSmite extends CommandBase 
{

	public String getCommandName()
	{
		return "smite";
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
	    if (arguments.length < 1) 
	    {
	        throw new WrongUsageException("Try /smite [playername]", new Object[0]);
	    } 
	    else
	    {
	    	EntityPlayerMP smotePlayer = findPlayer(sender, arguments[arguments.length - 1]);

            if (smotePlayer == null) 
            {
                throw new PlayerNotFoundException();
            }

			smotePlayer.worldObj.addWeatherEffect( new FCEntityLightningBolt( smotePlayer.worldObj, smotePlayer.posX + 0.5D, 
					smotePlayer.posY, smotePlayer.posZ + 0.5D ) );
	    }
	}

    //auto completes usernames
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length >= 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayers()) : null;
    }

    protected String[] getPlayers()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}