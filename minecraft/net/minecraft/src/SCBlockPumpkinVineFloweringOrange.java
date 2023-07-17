package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinVineFloweringOrange extends SCBlockGourdVineFloweringBase 
{
	protected SCBlockPumpkinVineFloweringOrange(int iBlockID, int vineBlock,int stemBlock, Block fruitBlock, Block greenFruit, Block yellowFruit, Block whiteFruit, int convertedBlockID) 
	{
		super( iBlockID, vineBlock, stemBlock, convertedBlockID);
		
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
			Block classSpecificFruit = SCDefs.pumpkinOrange;
			//Block classSpecificFruit = SuperBTWDefinitions.meatCube;
			
			world.setBlockAndMetadataWithNotify( finalI, j, finalK, classSpecificFruit.blockID, targetDirection);
			
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
