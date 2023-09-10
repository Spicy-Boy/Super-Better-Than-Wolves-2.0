// FCMOD

package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import net.minecraft.client.Minecraft; // client only

public class FCAddOnHandler
{
	public static List m_ModList = new LinkedList();
	public static boolean m_bModsInitialized = false;

	public static final Logger m_Logger = Logger.getLogger("BetterThanWolves");

	// Client version
	private static final File m_LogFile = new File(Minecraft.getMinecraftDir(), "BTWLog.txt");    
	// Server version
	//private static final File m_LogFile = new File(new File("."), "BTWLog.txt");    

	private static FileHandler m_LogFileHandler = null;

	private static NetServerHandler netServerHandler;
	private static ArrayList<String> ackCheckFails = new ArrayList<String>();

	public static void AddMod(FCAddOn mod)
	{
		m_ModList.add(mod);
	}

	public static void InitializeMods()
	{
		if (!m_bModsInitialized)
		{
			InitializeLogger();

			LogMessage("...Add-On Handler Initializing...");

			PreInitializeMods();
			
			loadModConfigs();

			Iterator modIterator = m_ModList.iterator();

			while (modIterator.hasNext())
			{
				FCAddOn mod = (FCAddOn)modIterator.next();

				mod.Initialize();
			}

			PostInitializeMods();

			m_bModsInitialized = true;

			OnLanguageLoaded(StringTranslate.getInstance());

			LogMessage("...Add-On Handler Initialization Complete...");
		}
	}

	public static void InitializeLogger()
	{
		try
		{
			if ((m_LogFile.exists() || m_LogFile.createNewFile()) && m_LogFile.canWrite())
			{
				m_LogFileHandler = new FileHandler(m_LogFile.getPath());
				m_LogFileHandler.setFormatter(new SimpleFormatter());
				m_Logger.addHandler(m_LogFileHandler);

				m_Logger.setLevel(Level.FINE);
			}
		}
		catch (Throwable error)
		{
			throw new RuntimeException(error);
		}
	}

	public static void LogMessage(String string)
	{
		System.out.println(string);

		if (net.minecraft.server.MinecraftServer.getServer() != null)
		{
			// client
			net.minecraft.server.MinecraftServer.getServer().getLogAgent().logInfo(string);
			// server
			//net.minecraft.server.MinecraftServer.getServer().getLogAgent().func_98233_a(string);
		}

		m_Logger.fine(string);
	}

	public static void LogWarning(String string)
	{
		System.out.println(string);

		if (net.minecraft.server.MinecraftServer.getServer() != null)
		{
			// client
			net.minecraft.server.MinecraftServer.getServer().getLogAgent().logWarning(string);
			// server
			//net.minecraft.server.MinecraftServer.getServer().getLogAgent().func_98236_b(string);
		}

		m_Logger.fine(string);
	}

	public static void PreInitializeMods()
	{
		Iterator modIterator = m_ModList.iterator();

		while (modIterator.hasNext())
		{
			FCAddOn mod = (FCAddOn)modIterator.next();

			mod.PreInitialize();
		}
	}

	public static void PostInitializeMods()
	{
		Iterator modIterator = m_ModList.iterator();

		while (modIterator.hasNext())
		{
			FCAddOn mod = (FCAddOn)modIterator.next();

			mod.PostInitialize();
		}
	}

	public static void OnLanguageLoaded(StringTranslate translator)
	{
		// only call on language loaded after mods have been initialized to prevent funkiness due to static instance variable of 
		// StringTranslate creating ambiguous initialization order.

		if (m_bModsInitialized)
		{
			Iterator modIterator = m_ModList.iterator();

			while (modIterator.hasNext())
			{
				FCAddOn mod = (FCAddOn)modIterator.next();

				mod.OnLanguageLoaded(translator);

				String sPrefix = mod.GetLanguageFilePrefix();

				if (sPrefix != null)
				{
					LogMessage("...Add-On Handler Loading Custom Language File With Prefix: " + sPrefix + "..." );

					translator.LoadAddonLanguageExtension(sPrefix);
				}
			}	    	
		}		
	}

	public static void ServerCustomPacketReceived(NetServerHandler handler, Packet250CustomPayload packet)
	{
		Iterator modIterator = m_ModList.iterator();

		while (modIterator.hasNext())
		{
			FCAddOn mod = (FCAddOn)modIterator.next();

			if (mod.ServerCustomPacketReceived(handler, packet))
			{
				return;
			}    		
		}    	
	}


	public static void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP playerMP) {
		netServerHandler = serverHandler;

		for (Object mod : FCAddOnHandler.m_ModList) {
			((FCAddOn) mod).serverPlayerConnectionInitialized(serverHandler, playerMP);
			
			if (((FCAddOn) mod).shouldVersionCheck)
				((FCAddOn) mod).sendVersionCheckToClient(serverHandler, playerMP);
		}
	}

	public static boolean getAwaitingLoginAck() {
		for (Object mod : FCAddOnHandler.m_ModList) {
			if (((FCAddOn) mod).getAwaitingLoginAck()) {
				return true;
			}
		}

		return false;
	}

	public static void incrementTicksSinceAckRequested() {
		for (Object mod : FCAddOnHandler.m_ModList) {
			((FCAddOn) mod).incrementTicksSinceAckRequested();
		}
	}

	public static void handleAckCheck() {
		for (Object mod : FCAddOnHandler.m_ModList) {
			if (!((FCAddOn) mod).handleAckCheck()) {
				ackCheckFails.add(((FCAddOn) mod).getName());
			}
		}

		if (!ackCheckFails.isEmpty()) {
			String message = "WARNING: Client missing the following addons, or very high latency connection: ";

			for (int i = 0; i < ackCheckFails.size(); i++) {
				if (i > 0)
					message += ", ";
				message += ackCheckFails.get(i);
			}

			FCUtilsWorld.SendPacketToPlayer(netServerHandler, new Packet3Chat(message));
		}
	}
	
	public static Map<Class<? extends FCAddOn>, FCAddOnUtilsWorldData> initWorldDataForAddon() {
		Map<Class<? extends FCAddOn>, FCAddOnUtilsWorldData> worldDataMap = new HashMap();
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOnUtilsWorldData worldData = ((FCAddOn) mod).createWorldData();
			
			if (worldData != null) {
				worldDataMap.put(((FCAddOn) mod).getClass(), worldData);
			}
		}
		
		return worldDataMap;
	}
	
	public static void loadModConfigs() {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;
			
			Map<String, String> addonConfigProperties = addon.loadConfigProperties();
			addon.handleConfigProperties(addonConfigProperties);
		}
	}

	//----------- Client Side Functionality -----------//

	public static void ClientCustomPacketReceived(Minecraft mcInstance, Packet250CustomPayload packet)
	{
		Iterator modIterator = m_ModList.iterator();

		while (modIterator.hasNext())
		{
			FCAddOn mod = (FCAddOn)modIterator.next();

			if (packet.channel == mod.addonCustomPacketChannelVersionCheck && mod.shouldVersionCheck && mod.versionCheckPacketReceived(mcInstance, packet)) {
				return;
			}
			else if (mod.ClientCustomPacketReceived(mcInstance, packet)){
				return;
			}    		
		}    	
	}

	public static void ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData)
	{
		Iterator modIterator = m_ModList.iterator();

		while (modIterator.hasNext())
		{
			FCAddOn mod = (FCAddOn)modIterator.next();

			if (mod.ClientPlayCustomAuxFX(mcInstance, world, player, iFXID, i, j, k, iFXSpecificData)) {
				return;
			}    		
		}    	
	}

	public static boolean interceptCustomClientPacket(Minecraft mc, Packet250CustomPayload packet) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			if (((FCAddOn) mod).interceptCustomClientPacket(mc, packet)) {
				return true;
			}
		}

		return false;
	}

	public static EntityFX spawnCustomParticle(World world, String particleType, double x, double y, double z, double velX, double velY, double velZ) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			EntityFX particle = ((FCAddOn) mod).spawnCustomParticle(world, particleType, x, y, z, velX, velY, velZ);
			if (particle != null)
				return particle;
		}

		return null;
	}
	
	// --- SAU: Loot Tables --- //

	public static void addBonusBasketLoot(ArrayList<FCUtilsRandomItemStack> bonusBasket) {
//		for (Object mod : FCAddOnHandler.m_ModList.values()) {
		
		for (Object mod : FCAddOnHandler.m_ModList) 
		{
			((FCAddOn) mod).addBonusBasketLoot(bonusBasket);
		}

//		}
	}

	public static void addWitchHutLoot(ArrayList<FCUtilsRandomItemStack> basketLoot) {
		
		for (Object mod : FCAddOnHandler.m_ModList) 
		{
			((FCAddOn) mod).addWitchHutLoot(basketLoot);
		}
////		for (Object mod : FCAddOnHandler.m_ModList.values()) {
//			FCAddOn addon = (FCAddOn) mod;
//
//			addon.addWitchHutLoot(basketLoot);
////		}
	}

	public static void addDesertWellLoot(ArrayList<FCUtilsRandomItemStack> arrayList) {
		
		for (Object mod : FCAddOnHandler.m_ModList) 
		{
			((FCAddOn) mod).addDesertWellLoot(arrayList);
		}
		
//		for (Object mod : FCAddOnHandler.m_ModList.values()) {
//			FCAddOn addon = (FCAddOn) mod;
//
//			addon.addDesertWellLoot(arrayList);
//		}
	}

	public static void addDesertPyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
		
		for (Object mod : FCAddOnHandler.m_ModList) 
		{
			((FCAddOn) mod).addDesertPyramidLoot(chestLoot, lootedChestLoot);
		}
		
//		for (Object mod : FCAddOnHandler.m_ModList.values()) {
//			FCAddOn addon = (FCAddOn) mod;
//
//			addon.addDesertPyramidLoot(chestLoot, lootedChestLoot);
//		}
	}

	public static int getNumberOfItemsInDesertPyramidChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) 
		{	
			amount = ((FCAddOn) mod).getNumberOfItemsInDesertPyramidChests(rand);
		}
		
//		for (Object mod : FCAddOnHandler.m_ModList.values()) {
//			FCAddOn addon = (FCAddOn) mod;
//
//			amount = addon.getNumberOfItemsInDesertPyramidChests(rand);
//		}
		
		return amount;
	}
	
	public static void addJunglePyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addJunglePyramidLoot(chestLoot, lootedChestLoot);
		}		
	}
	
	public static int getNumberOfItemsInJunglePyramidChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInJunglePyramidChests(rand);
		}
		
		return amount;
	}

	public static void addMinecartLoot(ArrayList<WeightedRandomChestContent> minecartLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addMinecartLoot(minecartLoot);
		}		
	}
	
	public static int getNumberOfItemsInMinecartChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInMinecartChests(rand);
		}
		
		return amount;
	}

	public static void filterMinecartContents(EntityMinecartChest minecart) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.filterMinecartContents(minecart);
		}
	}

	public static void addDungeonChestLoot(ArrayList<FCUtilsRandomItemStack> chestLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addDungeonChestLoot(chestLoot);
		}	
	}
	
	public static int getNumberOfItemsInDungeonChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInDungeonChests(rand);
		}
		
		return amount;
	}
	
	public static void filterDungeonChestContents(World world, int chestPosX, int chestPosY, int chestPosZ) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.filterDungeonChestContents(world, chestPosX, chestPosY, chestPosZ);
		}
	}

	public static void addStrongholdChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addStrongholdChestLoot(chestLoot);
		}	
	}

	public static int getNumberOfItemsInStrongholdChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInStrongholdChests(rand);
		}
		
		return amount;
	}
	
	public static void addBlacksmithChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addBlacksmithChestLoot(chestLoot);
		}	
	}
	
	public static int getNumberOfItemsInBlacksmithChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInBlacksmithChests(rand);
		}
		
		return amount;
	}

	public static void addStrongholdLibraryChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addStrongholdLibraryChestLoot(chestLoot);
		}	
	}
	
	public static int getNumberOfItemsInStrongholdLibraryChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInStrongholdLibraryChests(rand);
		}
		
		return amount;
	}
	
	public static void addStrongholdCrossingChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			addon.addStrongholdCrossingChestLoot(chestLoot);
		}	
	}
	
	public static int getNumberOfItemsInStrongholdCrossingChests(Random rand) {
		int amount = 0;
		
		for (Object mod : FCAddOnHandler.m_ModList) {
			FCAddOn addon = (FCAddOn) mod;

			amount = addon.getNumberOfItemsInStrongholdCrossingChests(rand);
		}
		
		return amount;
	}
}
