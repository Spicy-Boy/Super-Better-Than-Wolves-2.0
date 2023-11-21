package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.server.MinecraftServer;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
	private static ArrayList<ICommand> commandsClient = new ArrayList<ICommand>();
	private static ArrayList<ICommand> commandsServer = new ArrayList<ICommand>();
	
    public ServerCommandManager()
    {
        this.registerCommand(new CommandTime());
        this.registerCommand(new CommandGameMode());
        this.registerCommand(new CommandDifficulty());
        this.registerCommand(new CommandDefaultGameMode());
        this.registerCommand(new CommandKill());
        this.registerCommand(new CommandToggleDownfall());
        this.registerCommand(new CommandWeather());
        this.registerCommand(new CommandXP());
        this.registerCommand(new CommandServerTp());
        this.registerCommand(new CommandGive());
        this.registerCommand(new CommandEffect());
        this.registerCommand(new CommandEnchant());
        this.registerCommand(new CommandServerEmote());
        this.registerCommand(new CommandShowSeed());
        this.registerCommand(new CommandHelp());
        this.registerCommand(new CommandDebug());
        this.registerCommand(new CommandServerMessage());
        this.registerCommand(new CommandServerSay());
        this.registerCommand(new CommandSetSpawnpoint());
        this.registerCommand(new CommandGameRule());
        this.registerCommand(new CommandClearInventory());
        this.registerCommand(new ServerCommandTestFor());
        this.registerCommand(new ServerCommandScoreboard());
        this.registerCommand(new FCCommandServerLoc());
        
        //AARON added some commands :D
        this.registerCommand(new SuperBTWCommandTpRequest());
        this.registerCommand(new SuperBTWCommandTpAccept());
        this.registerCommand(new SuperBTWCommandTpCancel());
        this.registerCommand(new SuperBTWCommandTpTeam());
        this.registerCommand(new SuperBTWCommandDeaths());

        if (MinecraftServer.getServer().isDedicatedServer())
        {
            this.registerCommand(new CommandServerOp());
            this.registerCommand(new CommandServerDeop());
            this.registerCommand(new CommandServerStop());
            this.registerCommand(new CommandServerSaveAll());
            this.registerCommand(new CommandServerSaveOff());
            this.registerCommand(new CommandServerSaveOn());
            this.registerCommand(new CommandServerBanIp());
            this.registerCommand(new CommandServerPardonIp());
            this.registerCommand(new CommandServerBan());
            this.registerCommand(new CommandServerBanlist());
            this.registerCommand(new CommandServerPardon());
            this.registerCommand(new CommandServerKick());
            this.registerCommand(new CommandServerList());
            this.registerCommand(new CommandServerWhitelist());
            
            for (ICommand command : commandsServer) {
    			this.registerCommand(command);
    		}
        }
        else
        {
            this.registerCommand(new CommandServerPublishLocal());
            
            for (ICommand command : commandsClient) {
    			this.registerCommand(command);
    		}
        }

        CommandBase.setAdminCommander(this);
    }

    /**
     * Sends a message to the admins of the server from a given CommandSender with the given resource string and given
     * extra srings. If the int par2 is even or zero, the original sender is also notified.
     */
    public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object ... par4ArrayOfObj)
    {
        boolean var5 = true;

        if (par1ICommandSender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"))
        {
            var5 = false;
        }

        if (var5)
        {
            Iterator var6 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

            while (var6.hasNext())
            {
                EntityPlayerMP var7 = (EntityPlayerMP)var6.next();

                if (var7 != par1ICommandSender && MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var7.username))
                {
                    var7.sendChatToPlayer("" + EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "[" + par1ICommandSender.getCommandSenderName() + ": " + var7.translateString(par3Str, par4ArrayOfObj) + "]");
                }
            }
        }

        if (par1ICommandSender != MinecraftServer.getServer())
        {
            MinecraftServer.getServer().getLogAgent().func_98236_b("[" + par1ICommandSender.getCommandSenderName() + ": " + MinecraftServer.getServer().translateString(par3Str, par4ArrayOfObj) + "]");
        }

        if ((par2 & 1) != 1)
        {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString(par3Str, par4ArrayOfObj));
        }
    }
	
    /**
     * Register a command for both client and server
     * @param command
     */
	public static void registerAddonCommand(ICommand command) {
		commandsClient.add(command);
		commandsServer.add(command);
	}
	
	/**
	 * Register a client only command
	 * @param command
	 */
	public static void registerAddonCommandClient(ICommand command) {
		commandsClient.add(command);
	}
	
	/**
	 * Register a server only command
	 * @param command
	 */
	public static void registerAddonCommandServer(ICommand command) {
		commandsClient.add(command);
	}
}
