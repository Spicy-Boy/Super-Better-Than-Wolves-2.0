package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class SuperBTWCommandTpAbove extends CommandBase
{
    public String getCommandName()
    {
        return "tpabove";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public void processCommand(ICommandSender sender, String[] arguments) {
        if (arguments.length < 1 || arguments.length > 3) {

            throw new WrongUsageException("Try /tpabove [playername] or /tpabove [playername] [number]", new Object[0]);

        }
        else if (arguments.length == 1)
        {
            // this is /tpabove [playername] (defaults to 20 blocks above the player!)

            EntityPlayerMP teleportingPlayer = getCommandSenderAsPlayer(sender);

            EntityPlayerMP targetPlayer = findPlayer(sender, arguments[arguments.length - 1]);

            if (targetPlayer == null)
            {
                throw new PlayerNotFoundException();
            }

            if (targetPlayer.worldObj != teleportingPlayer.worldObj)
            {
                notifyAdmins(sender, "commands.tp.notSameDimension", new Object[0]);
                return;
            }

//            String teleportingPlayerName = teleportingPlayer.getEntityName();
            String targetPlayerName = targetPlayer.getEntityName();

            teleportingPlayer.addChatMessage("Teleported you 20 blocks above "+targetPlayerName+".");

            teleportingPlayer.mountEntity((Entity)null);
            teleportingPlayer.playerNetServerHandler.setPlayerLocation(targetPlayer.posX, targetPlayer.posY+20/*the hard coded default*/, targetPlayer.posZ, targetPlayer.rotationYaw, targetPlayer.rotationPitch);

        }
        else if (arguments.length == 2)
        {
            // this is /tpabove [playername] [number of blocks above]

            EntityPlayerMP teleportingPlayer = getCommandSenderAsPlayer(sender);

            EntityPlayerMP targetPlayer = findPlayer(sender, arguments[arguments.length - 2]);

            if (targetPlayer == null)
            {
                throw new PlayerNotFoundException();
            }

            if (targetPlayer.worldObj != teleportingPlayer.worldObj)
            {
                notifyAdmins(sender, "commands.tp.notSameDimension", new Object[0]);
                return;
            }

            int blocksAbove = 1;

            if (isValidInteger(arguments[arguments.length - 1]))
            {
                blocksAbove = Integer.parseInt(arguments[arguments.length - 1]);
            }
            else
            {
                notifyAdmins(sender, "Try /tpabove [playername] [number]", new Object[0]);
                return;
            }

            String targetPlayerName = targetPlayer.getEntityName();

            teleportingPlayer.addChatMessage("Teleported you "+blocksAbove+" blocks above "+targetPlayerName+".");

            teleportingPlayer.mountEntity((Entity)null);
            teleportingPlayer.playerNetServerHandler.setPlayerLocation(targetPlayer.posX, targetPlayer.posY+blocksAbove, targetPlayer.posZ, targetPlayer.rotationYaw, targetPlayer.rotationPitch);

        }

    }

    //this is some chatGPT method shenanigans
    public static boolean isValidInteger(String str)
    {
        try
        {
            // Attempt to parse the string to an integer
            Integer.parseInt(str);
            return true; // If successful, it's a valid integer
        }
        catch (NumberFormatException e)
        {
            return false; // If an exception is caught, it's not a valid integer
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
