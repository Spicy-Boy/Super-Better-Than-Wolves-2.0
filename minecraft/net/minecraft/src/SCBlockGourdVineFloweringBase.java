package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockGourdVineFloweringBase extends SCBlockGourdVine {
	
	private static String texConnector;
	private static String texVine;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockGourdVineFloweringBase(int iBlockID, int vineBlock, int stemBlock, int convertedBlockID) {
		super(iBlockID, vineBlock, stemBlock, convertedBlockID, texVine, texConnector);

		this.vineBlock = vineBlock;
		this.convertedBlockID = convertedBlockID;
		
		setHardness( 0.5F );
	}
	
	protected float GetBaseGrowthChance()
    {
    	return 0.2F;
    }
	
	protected float GetFruitGrowthChance() {
		return 0.85F;
	}

	//OLD, not very functional
//	@Override
//    public void updateTick( World world, int i, int j, int k, Random rand )
//    {
//		if (!this.canBlockStay(world, i, j, k))
//		{
//			world.setBlockAndMetadata(i, j, k, convertedBlockID, world.getBlockMetadata(i, j, k));
//		}
//		else
//		{
//			if (!IsFullyGrown( world, i, j, k) && checkTimeOfDay(world) ) //daytime
//			{
//				if (rand.nextFloat() <= this.GetBaseGrowthChance())
//				{
//					this.attemptToGrow(world, i, j, k, rand);
//				}
//			}
//			else
//			{
//				int dir = this.getDirection(world.getBlockMetadata(i, j, k));
//				world.setBlockAndMetadata(i, j, k, this.blockID, dir + 12);
//			}
//		}
//    }
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if (!this.canBlockStay(world, i, j, k))
        {
            world.setBlockAndMetadata(i, j, k, convertedBlockID, world.getBlockMetadata(i, j, k));
        }
        else
        {
            if ( checkTimeOfDay(world) ) //daytime
            {
                if (!IsFullyGrown( world, i, j, k)) 
                {
                    if (rand.nextFloat() <= this.GetBaseGrowthChance())
                    {
                        this.attemptToGrow(world, i, j, k, rand);
                    }
                }
                else
                {
                    //set mature
                    int dir = this.getDirection(world.getBlockMetadata(i, j, k));
                    world.setBlockAndMetadata(i, j, k, vineBlock, dir + 12);
                }
                
            }

        }
    }
	
	protected void attemptToGrow(World world, int i, int j, int k, Random rand) {
		
		int meta = world.getBlockMetadata(i, j, k);        
		world.setBlockAndMetadataWithNotify(i, j, k, this.blockID ,meta + 4);
		
		if (rand.nextFloat() <= this.GetFruitGrowthChance())
		{
			this.attemptToGrowFruit(world, i, j, k, rand);
			
			//set mature
			int dir = this.getDirection(world.getBlockMetadata(i, j, k));
			world.setBlockAndMetadata(i, j, k, this.blockID, dir + 12);
		}

	}

	
	protected abstract void attemptToGrowFruit(World world, int i, int j, int k, Random random);
	
	
	protected boolean CanGrowFruitAt( World world, int i, int j, int k )
    {
		int blockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[blockID];
		
		//if the block at the targetPos is null or isReplaceable()
        if ( (block == null || FCUtilsWorld.IsReplaceableBlock( world, i, j, k) )
        		//but not a stem, vine or flowering vine
        		&& ( blockID != this.blockID || blockID != this.vineBlock || blockID != this.stemBlock ) )
        {
			// CanGrowOnBlock() to allow vine to grow on tilled earth and such
			if ( CanGrowOnBlock( world, i, j - 1, k ) )
            {				
				return true;
            }
        }
        
        return false;
    }   
	
	//RENDER 
	
	public Icon[] flowerIcons;
	

    @Override
    public void registerIcons( IconRegister register )
    {
        flowerIcons = new Icon[2];

        for ( int iTempIndex = 0; iTempIndex < flowerIcons.length; iTempIndex++ )
        {
        	flowerIcons[iTempIndex] = register.registerIcon( "SCBlockPumpkinVineFlowering_" + iTempIndex );
        }
        
        blockIcon = flowerIcons[1]; // for block hit effects and item render
        
        connectorIcons = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcons.length; iTempIndex++ )
        {
        	connectorIcons[iTempIndex] = register.registerIcon( "SCBlockPumpkinVineConnector_" + iTempIndex );
        }
   
    }

	@Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
		if (!IsFullyGrown(blockAccess.getBlockMetadata(i, j, k)))
		{
			return flowerIcons[1];
		}
		else return flowerIcons[0];
        
    }
	
	public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;
        
        int growthLevel = this.GetGrowthLevel(blockAccess.getBlockMetadata(par2, par3, par4));
        this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), par2, par3, par4, 1.0F, this.connectorIcons[3]);
        
		return true;
    }
}
