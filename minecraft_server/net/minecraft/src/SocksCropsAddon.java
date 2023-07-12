package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

/**
 * @author Sockthing (@socklessthing)
 *
 */

public class SocksCropsAddon extends FCAddOn
{
    public static SocksCropsAddon instance = new SocksCropsAddon();
        
    public static final String ADDON_NAME = "Sock's Crops";
    public static final String ADDON_VERSION = "1.GOURDS";

	public static final int m_iMelonExplodeAuxFXID = 2300;	
	
	public static final int m_iMelonCantaloupeExplodeAuxFXID = 2301;

	public static final int m_iMelonHoneydewExplodeAuxFXID = 2302;

	public static final int m_iMelonCanaryExplodeAuxFXID = 2303;
    
	public static final int m_iPumpkinExplodeAuxFXID = 2305;
	
    private SocksCropsAddon() {
        super(ADDON_NAME, ADDON_VERSION, "SC");
    }
    
    @Override
    public void Initialize() {
    	FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		
    	SCDefs.addDefinitions();
    	SCRecipes.addRecipes();
		
    	FCAddOnHandler.LogMessage(this.getName() + " Initialized");
    }
    
    @Override
    public String GetLanguageFilePrefix() {
    	return "SC";
    }
    
    @Override
    public boolean ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i,
    		int j, int k, int iFXSpecificData)
    {
		double posX = (double)i + 0.5F;
		double posY = (double)j + 0.5F;
		double posZ = (double)k + 0.5F;

		int iParticleSetting = mcInstance.gameSettings.particleSetting;

		switch (iFXID)
		{
		case m_iMelonExplodeAuxFXID:
		case m_iMelonCanaryExplodeAuxFXID:
		case m_iMelonCantaloupeExplodeAuxFXID:
		case m_iMelonHoneydewExplodeAuxFXID:
		case m_iPumpkinExplodeAuxFXID:

			// 360 = melon slice based particles

			String particle = "iconcrack_" + Item.melon.itemID;

			if (iFXID == m_iPumpkinExplodeAuxFXID)
			{
				particle = "iconcrack_" + Item.pumpkinSeeds.itemID;
			}
			else if (iFXID == m_iMelonCanaryExplodeAuxFXID)
			{
				particle = "iconcrack_" + SCDefs.melonCanarySlice.itemID;
			}
			else if (iFXID == m_iMelonHoneydewExplodeAuxFXID)
			{
				particle = "iconcrack_" + SCDefs.melonHoneydewSlice.itemID;
			}
			else if (iFXID == m_iMelonCantaloupeExplodeAuxFXID)
			{
				particle = "iconcrack_" + SCDefs.melonCantaloupeSlice.itemID;
			}

			for (int iTempCount = 0; iTempCount < 150; iTempCount ++)
			{
				double dChunkX = posX + world.rand.nextDouble() - 0.5D;
				double dChunkY = posY - 0.45D;;
				double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;

				double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
				double dChunkVelY = world.rand.nextDouble() * 0.7D;
				double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;

				world.spawnParticle(particle, dChunkX, dChunkY, dChunkZ, 
						dChunkVelX, dChunkVelY, dChunkVelZ);
			}    	        

			world.playSound(posX, posY, posZ, "mob.zombie.wood", 0.2F, 0.60F + (world.rand.nextFloat() * 0.25F));

			world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 0.6F);

			break;
			
		default:

			return false;
		}

		return true;
	}
}