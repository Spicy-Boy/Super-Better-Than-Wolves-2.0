package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinVineFlowering extends SCBlockGourdVineFloweringBase {
	
	protected Block fruitBlock;
	protected Block greenFruit;
	protected Block yellowFruit;
	protected Block whiteFruit;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockPumpkinVineFlowering(int iBlockID, int vineBlock,int stemBlock, Block fruitBlock, Block greenFruit, Block yellowFruit, Block whiteFruit, int convertedBlockID) {
		super( iBlockID, vineBlock, stemBlock, convertedBlockID);
		
		this.fruitBlock = fruitBlock;
		this.greenFruit = greenFruit;
		this.yellowFruit = yellowFruit;
		this.whiteFruit = whiteFruit;
		this.vineBlock = vineBlock;
		this.convertedBlockID = convertedBlockID;
		
		setUnlocalizedName("SCBlockPumpkinVineFlowering");
	}
	
	@Override
	protected void convert(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int dir = this.getDirection(meta);
		
		for (int d = dir; d < 4; d++) {
			world.setBlockAndMetadataWithNotify(i, j, k, convertedBlockID, d + 12);
			
		}
	}
	
	@Override
	protected void attemptToGrowFruit(World world, int i, int j, int k, Random random) {
		
		int targetDirection = random.nextInt(4);
		
		int directionI = Direction.offsetX[targetDirection];
		int directionK = Direction.offsetZ[targetDirection];
		
		int finalI = i + directionI;
		int finalK = k + directionK;
		
		if (CanGrowPumpkinAt( world, finalI, j, finalK ))
		{
			Block biomeFruit = getFruitAccordingToBiome(world, finalI, j, finalK, random);
			
			world.setBlockAndMetadataWithNotify( finalI, j, finalK, biomeFruit.blockID, targetDirection);
			
			//set this to mature vine to stop it growing a second pumpkin
		}
	}
	
	protected boolean CanGrowPumpkinAt( World world, int i, int j, int k )
    {
		int blockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[blockID];
		
		//if the block at the targetPos is null or isReplaceable()
        if ( (block == null || FCUtilsWorld.IsReplaceableBlock( world, i, j, k) )
        		
        	//but not a stem, vine or flowering vine
        	&& ( blockID != this.vineBlock || blockID != this.stemBlock || blockID != this.floweringBlock ) ) {
                
        	// CanGrowOnBlock() to allow vine to grow on tilled earth and such
        	if ( CanGrowOnBlock( world, i, j - 1, k ) )
	        {				
        		return true;
	        }
        }
        
        return false;
    }
	

	private Block getFruitAccordingToBiome(World world, int i, int j, int k, Random random) {
		Block biomeFruit;
		
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		
		//Honeydew
		BiomeGenBase greenPumpkin[] = { 
				BiomeGenBase.swampland,
				BiomeGenBase.river,
				BiomeGenBase.extremeHills,
				BiomeGenBase.extremeHillsEdge };
		
		//Canary
		BiomeGenBase yellowPumpkin[] = {
				BiomeGenBase.desert,
				BiomeGenBase.desertHills,
		 		BiomeGenBase.jungle,
		 		BiomeGenBase.jungleHills,
		 		BiomeGenBase.beach };
		
		//Cantaloupe
		BiomeGenBase whitePumpkin[] = {
				BiomeGenBase.icePlains,
				BiomeGenBase.iceMountains,
			 	BiomeGenBase.taiga,
			 	BiomeGenBase.taigaHills,
			 	BiomeGenBase.frozenOcean,
			 	BiomeGenBase.frozenRiver };
		 
		BiomeGenBase allPumpkins[] = {
				BiomeGenBase.mushroomIsland, 
				BiomeGenBase.mushroomIslandShore };
		
		//set the biomeFruit
		biomeFruit = this.fruitBlock;
		
		for (int g = 0; g < greenPumpkin.length; g++) {
    		if (biome == greenPumpkin[g])
        	{
        		biomeFruit = this.greenFruit;
        	}
		}
    	
    	for (int y = 0; y < yellowPumpkin.length; y++) {
    		if (biome == yellowPumpkin[y])
        	{
        		biomeFruit = this.yellowFruit;
        	}
		}
    	
    	for (int w = 0; w < whitePumpkin.length; w++) {
    		if (biome == whitePumpkin[w])
        	{
        		biomeFruit = this.whiteFruit;
        	}
		}
    	
    	for (int a = 0; a < allPumpkins.length; a++) {
    		int blockBelow = world.getBlockId(i, j - 1, k);
    		
    		if (biome == allPumpkins[a] || blockBelow == Block.mycelium.blockID)
        	{
    			for (int r = 0; r < 4 ; r++) {
    				
    				int rand = random.nextInt(4);
    				
    				if (rand == 0) {
    					biomeFruit = this.fruitBlock;
    				}
    				else if (rand == 1) {
    					biomeFruit = this.greenFruit;
    				}
    				else if (rand == 2) {
    					biomeFruit = this.yellowFruit;
    				}
    				else if (rand == 3) {
    					biomeFruit = this.whiteFruit;
    				}
    		    }
        	}
		}
    	
		return biomeFruit;
	}
	
	@Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
    	
    	if (growthLevel == 0)
		{
			return GetVineBounds(4, 8, 8);
		}
		else if (growthLevel == 1)
		{
			return GetVineBounds(6, 8, 8);
		}
		else if (growthLevel == 2)
		{
			return GetVineBounds(12, 12, 12);
		}
		else return GetVineBounds(16, 16, 16);
    		
    }

}
