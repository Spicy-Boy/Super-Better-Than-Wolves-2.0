
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;

public class DLMain extends FCAddOn{

	private static DLMain instance;
	public static int id_lightsourceinvis = 2042;
	public static Block lightsourceinvis;
	/* put this in a loaded class
    //hiracho DL instance
    static
    {
    	DLmain.getInstance();
    }

	 */

	public DLMain() {
		super("Hiracho's Dynamic Lighting", "1.1.0", "DL");
	}
	
	public String GetLanguageFilePrefix()
    {
        return "DL";
    }
	
	public static DLMain getInstance() 
	{
		if (instance == null) {
			instance = new DLMain();
		}
		return instance;
	}
	


	@Override
	public void Initialize() {
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		addNewItems();
		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
	}
	
	public static void addNewItems()
	{
		lightsourceinvis = (new DLLightSource(id_lightsourceinvis-256)); 
	}
}
