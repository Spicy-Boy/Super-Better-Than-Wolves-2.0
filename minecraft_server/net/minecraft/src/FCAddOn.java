// FCMOD

package net.minecraft.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public abstract class FCAddOn {
	protected String addonName;
	protected String versionString;
	protected String prefix;

	protected boolean isRequiredClientAndServer = false;
	protected boolean shouldVersionCheck = false;

	private boolean awaitingLoginAck = false;
	private int ticksSinceAckRequested = 0;
	private static final int maxTicksForAckWait = 50;

	public String addonCustomPacketChannelVersionCheck;
	public String addonCustomPacketChannelVersionCheckAck;

	private ArrayList<String> configProperties = new ArrayList();
	private Map<String, String> configPropertyDefaults = new HashMap();
	private Map<String, String> configPropertyComments = new HashMap();

	protected FCAddOn() {
		FCAddOnHandler.AddMod(this);
	}

	/**
	 * @param addonName Used for display in version checking
	 * @param versionString Used for version checking
	 * @param prefix Used for translations and packet channels
	 */
	public FCAddOn(String addonName, String versionString, String prefix) {
		this();
		this.addonName = addonName;
		this.versionString = versionString;
		this.prefix = prefix;
		this.addonCustomPacketChannelVersionCheck = prefix + "|VC";
		this.addonCustomPacketChannelVersionCheckAck = prefix + "|VC_Ack";
		this.shouldVersionCheck = true;
		this.isRequiredClientAndServer = true;
	}

	public void PreInitialize() {}

	abstract public void Initialize();

	public void PostInitialize() {}

	public void OnLanguageLoaded( StringTranslate translator ) {}

	/**
	 * Prefix for custom addon-specific .lang files
	 * Returns null if addon doesn't support such files
	 */
	public String GetLanguageFilePrefix() {
		return null;
	}

	/**
	 * Registers a property to load via the config
	 * This should be done either PreInitialize() as properties are read between pre-initialization and initialization
	 * @param propertyName The name for the property
	 * @param defaultValue The default value. If the config file cannot be found, one will be generated and populated with the provided default
	 * @param comment Comment to show for the property (optional through overload)
	 */
	protected void registerProperty(String propertyName, String defaultValue, String comment) {
		if (!configPropertyDefaults.containsKey(propertyName)) {
			configProperties.add(propertyName);
			configPropertyDefaults.put(propertyName, defaultValue);
			configPropertyComments.put(propertyName, comment);
		}
		else {
			FCAddOnHandler.LogWarning("Cannot add config property \"" + propertyName + "\" for " + addonName + " because a property with that name has already been registered for this addon");
		}
	}
	
	protected void registerProperty(String propertyName, String defaultValue) {
		this.registerProperty(propertyName, defaultValue, "");
	}

	/**
	 * Where the addon should handle the values read from config files
	 * This is called after Initialize() is called
	 * @param propertyValues Key-value pair for each property which has been registered
	 */
	public void handleConfigProperties(Map<String, String> propertyValues) {}

	/**
	 * Loads the registered properties
	 * Addons should not need to override this but may if they would like to implement custom property handling
	 * @return A map of the property names paired with the value read from them. Returns null if no properties were registered or if there was a problem loading the file
	 */
	public Map<String, String> loadConfigProperties() {
		if (this.configPropertyDefaults.size() == 0) {
			return null;
		}
		
		String filename = this.prefix + ".properties";
		BufferedReader fileIn = null;

		try {
			fileIn = new BufferedReader(new FileReader("config/" + filename));
		} catch (FileNotFoundException e) {
			//Generate file if it does not exist
			try {
				Files.createDirectories(Paths.get("config"));

				File config = new File("config/" + filename);
				config.createNewFile();
				fileIn = new BufferedReader(new FileReader(config));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (fileIn != null) {
			ArrayList<String> unpopulatedProperties = new ArrayList();
			Map<String, String> propertyValues = new HashMap();

			String line;
			
			//Reads properties from the config file
			try {
				while ((line = fileIn.readLine()) != null) {
					if (line.startsWith("#"))
						continue;
					
					String[] lineSplit = line.split("=");
					
					if (configPropertyDefaults.containsKey(lineSplit[0])) {
						propertyValues.put(lineSplit[0], lineSplit[1]);
					}
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			for (String propertyName : configProperties) {
				if (!propertyValues.containsKey(propertyName)) {
					unpopulatedProperties.add(propertyName);
				}
			}
			
			//Populates the default values into the config file for any properties not found, then loads those defaults
			if (unpopulatedProperties.size() > 0) {
				File config = new File("config/" + filename);
				
				for (String propertyName : unpopulatedProperties) {
					propertyValues.put(propertyName, configPropertyDefaults.get(propertyName));
					
					try {
						String comment = configPropertyComments.get(propertyName);
						
						if (!comment.equals("")) {
							Files.write(config.toPath(), ("\n# " + comment + "\n").getBytes(), StandardOpenOption.APPEND);
						}
						
						Files.write(config.toPath(), (propertyName + "=" + configPropertyDefaults.get(propertyName) + "\n").getBytes(), StandardOpenOption.APPEND);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			try {
				fileIn.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
			return propertyValues;
		}
		else {
			return null;
		}
	}

	/**
	 * Called when a player joins the world
	 */
	public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP playerMP) {}

	/**
	 * Handles version checking
	 */
	public void sendVersionCheckToClient(NetServerHandler serverHandler, EntityPlayerMP playerMP) {
		if (!MinecraftServer.getServer().isSinglePlayer()) {
			//AARON changed the version string to not have a V ;)
			FCUtilsWorld.SendPacketToPlayer(serverHandler, new Packet3Chat("\u00a7f" + addonName + " " + versionString));

			if (shouldVersionCheck) {
				ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
				DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

				try {
					dataOutput.writeUTF(versionString);
				}
				catch (Exception var9) {
					var9.printStackTrace();
				}

				Packet250CustomPayload var4 = new Packet250CustomPayload(addonCustomPacketChannelVersionCheck, byteArrayOutput.toByteArray());
				FCUtilsWorld.SendPacketToPlayer(serverHandler, var4);
				awaitingLoginAck = true;
			}
		}
		else {
			//AARON changed this version string too! No more V ;)
			FCUtilsWorld.SendPacketToPlayer(serverHandler, new Packet3Chat("\u00a7f" + addonName + " " + versionString));
		}
	}

	/**
	 * Returns true if the packet has been processed, false otherwise
	 */    
	public boolean ServerCustomPacketReceived(NetServerHandler handler, Packet250CustomPayload packet) {
		return false;
	}

	/**
	 * Called when client ack packet is received by the NetServerHandler
	 * If overriding, make sure to make a call to the super method if you want to maintain version checking
	 * @return true if packet was handled, false otherwise
	 */
	public boolean serverAckPacketReceived(NetServerHandler serverHandler, Packet250CustomPayload packet) {
		if (addonCustomPacketChannelVersionCheckAck.equals(packet.channel)) {
			FCUtilsWorld.SendPacketToPlayer(serverHandler, new Packet3Chat("\u00a7f" + addonName + " version check successful."));
			awaitingLoginAck = false;
			ticksSinceAckRequested = 0;
		}

		return false;
	}

	/**
	 * @return whether the server is awaiting the client's response to the version check
	 */
	public boolean getAwaitingLoginAck() {
		return awaitingLoginAck;
	}

	public void incrementTicksSinceAckRequested() {
		ticksSinceAckRequested++;
	}

	public boolean handleAckCheck() {
		if (ticksSinceAckRequested > maxTicksForAckWait) {
			awaitingLoginAck = false;
			ticksSinceAckRequested = 0;
			return false;
		}

		return true;
	}

	public String getName() {
		return this.addonName;
	}

	public String getVersionString() {
		return this.versionString;
	}

	/**
	 * Used for storing custom information in the save file
	 * Information is saved per dimension
	 * @return
	 */
	public FCAddOnUtilsWorldData createWorldData() {
		return null;
	}

	//----------- Client Side Functionality -----------//

	/**
	 * Called when the version check packet is received by the NetClientHandler
	 * If overriding, make sure to make a call to the super method
	 * @return true if packet was handled, false otherwise
	 */
//	public boolean versionCheckPacketReceived(Minecraft mc, Packet250CustomPayload packet) {
//		try {
//			WorldClient world = mc.theWorld;
//			DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
//
//			String var33 = dataStream.readUTF();
//
//			if (!var33.equals(versionString)) {
//				mc.thePlayer.addChatMessage("\u00a74" + "WARNING: " + this.getName() + " version mismatch detected! Local Version: " + this.versionString + " Server Version: " + var33);
//			}
//
//			ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
//			DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);
//
//			try {
//				dataOutput.writeUTF(versionString);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			// TODO: Re-enable ack packet checking
//			//Packet250CustomPayload ackPacket = new Packet250CustomPayload(addonCustomPacketChannelVersionCheckAck, byteArrayOutput.toByteArray());
//			//mc.getNetHandler().addToSendQueue(ackPacket);
//
//			mc.thePlayer.addChatMessage("\u00a7f" + addonName + " version check successful.");
//
//			return true;
//		}
//		catch (IOException var23) {
//			var23.printStackTrace();
//		}
//
//		return false;
//	}
//
//	/**
//	 * @return true if the packet has been processed, false otherwise
//	 */
//	public boolean ClientCustomPacketReceived(Minecraft mcInstance, Packet250CustomPayload packet) {
//		return false;
//	}
//	/**
//	 * Used to modify existing client side packet250 behavior (For modifying BTW behavior)
//	 * @return true if packet was handled, false otherwise
//	 */
//	public boolean interceptCustomClientPacket(Minecraft mc, Packet250CustomPayload packet) {
//		return false;
//	}
//
//	/**
//	 * @return true if the packet has been processed, false otherwise
//	 */
//	public boolean ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData) {
//		return false;
//	}
//
//	/**
//	 * Spawns a custom particle based on a string specifying the type
//	 * @return the spawned particle, or null if type is not handled
//	 */
//	public EntityFX spawnCustomParticle(World world, String particleType, double x, double y, double z, double velX, double velY, double velZ) {
//		return null;
//	}
}
