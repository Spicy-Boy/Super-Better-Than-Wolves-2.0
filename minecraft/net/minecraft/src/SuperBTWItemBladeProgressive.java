package net.minecraft.src;

//the class that all knife-based progressive recipes pull from
/*I'm not sure if this is really ever going to be used, 
 * but I wanted to future proof these recipes in case I need to make more!
 * Who knows, maybe cutting and carving with knives will be a hit?
*/
public class SuperBTWItemBladeProgressive extends FCItemCraftingProgressive
{
	
	static int internalBladeDamage;
	
	public SuperBTWItemBladeProgressive (int iItemID)
	{
		super(iItemID);
	}
	
}
