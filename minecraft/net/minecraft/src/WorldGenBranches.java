package net.minecraft.src;

import java.util.Random;

public class WorldGenBranches extends WorldGenerator
{
	
	private int branchBlockID;
	
	
	
	public WorldGenBranches(int ID)
	{
		
		this.branchBlockID = ID;
		
	}
	
	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		
    	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( par3, par5 );
    	
        boolean bIsValidBiome = currentBiome == BiomeGenBase.forest || 
        	currentBiome == BiomeGenBase.taiga || currentBiome == BiomeGenBase.forestHills || currentBiome == BiomeGenBase.taigaHills
        	|| currentBiome == BiomeGenBase.swampland || currentBiome == BiomeGenBase.jungle;
        
		for (int var6 = 0; var6 < 9/*This is how big clusters are!!!! I think*/; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
            
            if ( !bIsValidBiome )
            {
            	// must occur after all random number generation to avoid messing up world gen
            	
            	continue;
            }
            
            // FCMOD: Change
            /*
            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.provider.hasNoSky || var8 < 127) && Block.blocksList[this.plantBlockId].canBlockStay(par1World, var7, var8, var9))
            */
            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.canBlockSeeTheSky(var7, var8, var9) && var8 < 127 && var8 > 50) && 
            	Block.blocksList[this.branchBlockID].CanBlockStayDuringGenerate(par1World, var7, var8, var9))
            	//Block.blocksList[this.branchBlockID].CanReedsGrowOnBlock(par1World, var7, var8, var9));
            	// END FCMOD
            {
                par1World.setBlock(var7, var8, var9, this.branchBlockID, 0, 2);
                //System.out.println("BRANNCH!! at: x: " + var7 + " y: " + var8 + " z: " + var9 );
                
            }
        }

        return true;
    }

}
