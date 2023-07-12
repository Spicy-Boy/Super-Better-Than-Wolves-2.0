package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonVineFlowering extends SCBlockGourdVineFloweringBase {
	
	protected Block waterFruit;
	protected Block canaryFruit;
	protected Block honeydewFruit;
	protected Block cantaloupeFruit;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockMelonVineFlowering(int iBlockID, int vineBlock,int stemBlock, Block waterFruit, Block honeydewFruit, Block cantaloupeFruit, Block canaryFruit, int convertedBlockID) {
		super( iBlockID, vineBlock, stemBlock, convertedBlockID);
		
		this.waterFruit = waterFruit;
		this.canaryFruit = canaryFruit;
		this.honeydewFruit = honeydewFruit;
		this.cantaloupeFruit = cantaloupeFruit;
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
		
		if (CanGrowMelonAt( world, finalI, j, finalK ))
		{
			Block biomeFruit = getFruitAccordingToBiome(world, finalI, j, finalK, random);
			
			world.setBlockAndMetadataWithNotify( finalI, j, finalK, biomeFruit.blockID, targetDirection);
			
			//set this to mature vine to stop it growing a second pumpkin
		}
	}
	
	protected boolean CanGrowMelonAt( World world, int i, int j, int k )
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
		
		//Yellow
		BiomeGenBase canaryMelonBiomes[] = { 

				BiomeGenBase.desert,
				BiomeGenBase.desertHills,
				BiomeGenBase.extremeHills,
				BiomeGenBase.extremeHillsEdge
		};

		//Green
		BiomeGenBase honeydewMelonBiomes[] = {

				BiomeGenBase.swampland,
				BiomeGenBase.river,
		 		BiomeGenBase.beach,

		};
		
		//White
		BiomeGenBase cantaloupeMelonBiomes[] = {
			 	BiomeGenBase.taiga,
			 	BiomeGenBase.taigaHills,
				BiomeGenBase.icePlains,
				BiomeGenBase.iceMountains,
		 		BiomeGenBase.frozenOcean,
			 	BiomeGenBase.frozenRiver

		};
		
		//Mushroom
		BiomeGenBase mushroomBiome[] = {
				BiomeGenBase.mushroomIsland, 
				BiomeGenBase.mushroomIslandShore
		};
		
		//set the biomeFruit
		biomeFruit = this.waterFruit;
		
		for (int c = 0; c < canaryMelonBiomes.length; c++) {
    		if (biome == canaryMelonBiomes[c])
        	{
        		biomeFruit = this.canaryFruit;
        	}
		}
    	
    	for (int h = 0; h < honeydewMelonBiomes.length; h++) {
    		if (biome == honeydewMelonBiomes[h])
        	{
        		biomeFruit = this.honeydewFruit;
        	}
		}
    	
    	for (int c = 0; c < cantaloupeMelonBiomes.length; c++) {
    		if (biome == cantaloupeMelonBiomes[c])
        	{
        		biomeFruit = this.cantaloupeFruit;
        	}
		}
    	
    	for (int m = 0; m < mushroomBiome.length; m++) {
    		int blockBelow = world.getBlockId(i, j - 1, k);
    		
    		if (biome == mushroomBiome[m] || blockBelow == Block.mycelium.blockID)
        	{
    			for (int r = 0; r < 4 ; r++) {
    				
    				int rand = random.nextInt(4);
    				
    				if (rand == 0) {
    					biomeFruit = this.waterFruit;
    				}
    				else if (rand == 1) {
    					biomeFruit = this.canaryFruit;
    				}
    				else if (rand == 2) {
    					biomeFruit = this.honeydewFruit;
    				}
    				else if (rand == 3) {
    					biomeFruit = this.cantaloupeFruit;
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
	
	//RENDER 
	
	public Icon[] flowerIcons;
	

//    @Override
//    public void registerIcons( IconRegister register )
//    {
//        flowerIcons = new Icon[2];
//
//        for ( int iTempIndex = 0; iTempIndex < flowerIcons.length; iTempIndex++ )
//        {
//        	flowerIcons[iTempIndex] = register.registerIcon( "SCBlockMelonVineFlowering_" + iTempIndex );
//        }
//        
//        blockIcon = flowerIcons[1]; // for block hit effects and item render
//        
//        connectorIcons = new Icon[4];
//        for ( int iTempIndex = 0; iTempIndex < connectorIcons.length; iTempIndex++ )
//        {
//        	connectorIcons[iTempIndex] = register.registerIcon( "SCBlockMelonVineConnector_" + iTempIndex );
//        }
//   
//    }

//	@Override
//    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
//    {
//		if (!IsFullyGrown(blockAccess.getBlockMetadata(i, j, k)))
//		{
//			return flowerIcons[1];
//		}
//		else return flowerIcons[0];
//        
//    }

}
