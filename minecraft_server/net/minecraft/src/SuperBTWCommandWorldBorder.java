package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class SuperBTWCommandWorldBorder extends CommandBase 
{

	//COMMAND TRACKER
	// /worldborder enable /worldborder disable
	// /worldborder [playername] (toggles if player is immune to world border
	// /worldborder shrink (toggles if world border is shrinking)
	// /worldborder shrink [# to shrink by]
	// ^^^ this is basically a toggle switch for world shrinking 
	// /worldborder info (gives debug info, reads variables to admins)


    public String getCommandName()
    {
        return "worldborder";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    public void processCommand(ICommandSender sender, String[] arguments) {
        if (arguments.length < 1 || arguments.length > 4) {

            throw new WrongUsageException("Try /worldborder [playername] or /worldborder enable/disable", new Object[0]);

        }
        else if (arguments.length == 1)
        {
        	if (arguments[0].equals("enable") || arguments[0].equals("true"))
        	{
        		SuperBTW.instance.setWorldBorderEnabled(true);
        		notifyAdmins(sender, "Enabled the world border.", new Object[0]);
        	}
        	else if (arguments[0].equals("disable") || arguments[0].equals("false"))
        	{
        		SuperBTW.instance.setWorldBorderEnabled(false);
        		notifyAdmins(sender, "Disabled the world border.", new Object[0]);
        	}
        	else if (arguments[0].equals("shrink"))
        	{
        		
        		if (SuperBTW.instance.getIsWorldBorderShrinkingToggled())
        		{
        			SuperBTW.instance.setIsWorldBorderShrinkingToggled(false);
        			notifyAdmins(sender, "World Border shrinking toggled OFF", new Object[0]);
        		}
        		else 
        		{
        			SuperBTW.instance.setIsWorldBorderShrinkingToggled(true);
        			notifyAdmins(sender, "World Border shrinking toggled ON! Border is now shrinking!", new Object[0]);
        		}
        	}
        	else if (arguments[0].equals("info"))
        	{
     			notifyAdmins(sender, "World Border allowed? "+SuperBTW.instance.getWorldBorderEnabled(), new Object[0]);
     			notifyAdmins(sender, "WB shrinking toggled? "+SuperBTW.instance.getIsWorldBorderShrinkingToggled(), new Object[0]);
     			notifyAdmins(sender, "Current WB x and y: "+SuperBTW.instance.getRectangularWorldBorderX()+" "+SuperBTW.instance.getRectangularWorldBorderZ(), new Object[0]);
        	}
        	else
        	{
        		EntityPlayerMP targetPlayer = findPlayer(sender, arguments[0]);
                
        		if (targetPlayer == null)
                {
                    throw new PlayerNotFoundException();
                }
        		
        		String targetPlayerName = targetPlayer.getEntityName();
        		if (targetPlayer.getIsImmuneToWorldBorder())
        		{
        			targetPlayer.setIsImmuneToWorldBorder(false);
        			notifyAdmins(sender, targetPlayerName+" is no longer immune to the border.", new Object[0]);
        		}
        		else
        		{
        			targetPlayer.setIsImmuneToWorldBorder(true);
        			notifyAdmins(sender, targetPlayerName+" is now immune to the border.", new Object[0]);
    
        		}
        	}
        }
        else if (arguments.length == 2)
        {
    		if (arguments[0].equals("shrink"))
    		{
    			SuperBTW.instance.setRectangularWorldBorderX(SuperBTW.instance.getRectangularWorldBorderX() - Integer.parseInt(arguments[1]));
    			SuperBTW.instance.setRectangularWorldBorderZ(SuperBTW.instance.getRectangularWorldBorderZ() - Integer.parseInt(arguments[1]));
    			
    		}
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
