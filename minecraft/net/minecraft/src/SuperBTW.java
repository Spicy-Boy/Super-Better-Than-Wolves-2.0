package net.minecraft.src;

public class SuperBTW extends FCAddOn
{

/**
 * @author EpicAaron29 (aaron on the discord)
 *
 */

	public static SuperBTW instance = new SuperBTW();
	
    private SuperBTW() 
    {
    	super("SUPER BTW", "BETA 2.5 (w/ old melons O_O)", "SBTW");
    }
    
    //settings variables
	private PropertyManager settings;
	
	private boolean areDynamicTorchesEnabled;
	private boolean is2x2LeatherEnabled;
	private boolean areHungerTweaksEnabled;
	private boolean areBranchesEnabled;
	private boolean isHardcoreBouncingEnabled;
	
	private boolean isLightningFireEnabled;
	
	private boolean isCustomRespawnRadiusEnabled;
	private int customRespawnRadius;
	private int customRespawnExclusionRadius;
	
	private boolean isEndermanGriefingEnabled;
	
	//Dawn's color order from Deco Addon!!
	public final static String[] colorOrder = {"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	
	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Preparing...");
		
    	SuperBTWDefinitions.addDefinitions();
    	SuperBTWRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Super Better Than Wolves initialized!");

	}

	public String GetLanguageFilePrefix()
	{
		return "SuperBTW";
	}
	
	//Methods related to the SBTW.properties file V V V ~~~~~~~~~~~~~~~~~~ V V V >_< ^_^
	//NOTE the temp variables propertyName[X] are a holdover from rough draft of code... 
	//I kept them, but just know they don't mean anything
	@Override
	public void PreInitialize()
	{		
		String propertyName2 = "Enable-Lightning-Fire";
		registerProperty(propertyName2, "true", "Toggles whether or not bolts of lightning can start fires..the horror....the horror..");
		this.setLightningFireEnabled(this.loadConfigProperties().get(propertyName2).equals("true"));
		
		String propertyName18 = "Enable-Dynamic-Torches";
		registerProperty(propertyName18, "true", "Turn this off to stop torches from emitting light when held in the hand!");
		this.setDynamicTorchesEnabled(this.loadConfigProperties().get(propertyName18).equals("true"));
		
		String propertyName9 = "Enable-Custom-HC-Respawn-Radius";
		registerProperty(propertyName9, "false", "If enabled, you will respawn within a set range, unchanging!");
		this.setCustomRespawnRadiusEnabled(this.loadConfigProperties().get(propertyName9).equals("true"));

		String propertyName10 = "Set-Custom-Respawn-Radius";
		registerProperty(propertyName10, "8000", "vvv The HC respawn radius decided by you..");
		this.setCustomRespawnRadius( Integer.parseInt(this.loadConfigProperties().get(propertyName10)) );
		//TESTER VVV
//		System.out.println(propertyName10 +"="+getCustomRespawnRadius());
		
		String propertyName11 = "Set-Custom-Respawn-Exclusion-Radius";
		registerProperty(propertyName11, "100", "vvv The inner disc of the HC Spawn radius within which nobody can spawn.. (make smaller than main radius!)");
		this.setCustomRespawnExclusionRadius( Integer.parseInt(this.loadConfigProperties().get(propertyName11)) );
		//TESTER VVV
//		System.out.println(propertyName10 +"="+getCustomRespawnExclusionRadius());
		
		String propertyName19 = "Enable-2x2-Leather-Armor-Crafting";
		registerProperty(propertyName19, "true", "Turn this off to restrict leather armor crafting to the crafting table..");
		this.set2x2LeatherEnabled(this.loadConfigProperties().get(propertyName19).equals("true"));
		
		String propertyName20 = "Enable-Branches";
		registerProperty(propertyName20, "true", "Turn this off to stop branches from falling from trees or generating!");
		this.setBranchesEnabled(this.loadConfigProperties().get(propertyName20).equals("true"));
		
		String propertyName21 = "Enable-Hunger-Tweaks";
		registerProperty(propertyName21, "true", "Turn this off to experience the original BTW hunger bar properties.. (ex: can't jump on famished)");
		this.setHungerTweaksEnabled(this.loadConfigProperties().get(propertyName21).equals("true"));
		
		String propertyName22 = "Enable-Hardcore-Bouncing";
		registerProperty(propertyName22, "false", "Hardcore bouncing prevents block placement while airborn. Disabled by default in SBTW!");
		this.setIsHardcoreBouncingEnabled(this.loadConfigProperties().get(propertyName22).equals("true"));
		
		String propertyName23 = "Enable-Enderman-Griefing";
		registerProperty(propertyName23, "true", "If disabled, enderman will no longer pick up anything but flowers..");
		this.setIsEndermanGriefingEnabled(this.loadConfigProperties().get(propertyName23).equals("true"));
		
	}
	
	public void setLightningFireEnabled(boolean b)
	{
		this.isLightningFireEnabled = b;
	}
	public boolean getLightningFireEnabled()
	{
		return this.isLightningFireEnabled;
	}
	
	public void setDynamicTorchesEnabled(boolean b)
	{
		this.areDynamicTorchesEnabled = b;
	}
	public boolean getDynamicTorchesEnabled()
	{
		return this.areDynamicTorchesEnabled;
	}
	
	public void set2x2LeatherEnabled(boolean b)
	{
		this.is2x2LeatherEnabled = b;
	}
	public boolean get2x2LeatherEnabled()
	{
		return this.is2x2LeatherEnabled;
	}
	
	public void setBranchesEnabled(boolean b)
	{
		this.areBranchesEnabled = b;
	}
	public boolean getBranchesEnabled()
	{
		return this.areBranchesEnabled;
	}
	
	public void setHungerTweaksEnabled(boolean b)
	{
		this.areHungerTweaksEnabled = b;
	}
	public boolean areHungerTweaksEnabled()
	{
		return this.areHungerTweaksEnabled;
	}
	
	public void setCustomRespawnRadiusEnabled(boolean b)
	{
		this.isCustomRespawnRadiusEnabled = b;
	}
	public boolean getCustomRespawnRadiusEnabled()
	{
		return isCustomRespawnRadiusEnabled;
	}
	
	public void setCustomRespawnRadius(int i)
	{
		this.customRespawnRadius = i;
	}
	public int getCustomRespawnRadius()
	{
		return customRespawnRadius;
	}
	
	public void setCustomRespawnExclusionRadius(int i)
	{
		this.customRespawnExclusionRadius = i;
	}
	public int getCustomRespawnExclusionRadius()
	{
		return customRespawnExclusionRadius;
	}
	
	public void setIsHardcoreBouncingEnabled(boolean b)
	{
		this.isHardcoreBouncingEnabled = b;
	}
	public boolean isHardcoreBouncingEnabled()
	{
		return isHardcoreBouncingEnabled;
	}
	
	public void setIsEndermanGriefingEnabled(boolean b)
	{
		this.isEndermanGriefingEnabled = b;
	}
	public boolean getIsEndermanGriefingEnabled()
	{
		return isEndermanGriefingEnabled;
	}
}