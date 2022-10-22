package net.minecraft.src;

import java.util.Random;

//will not convert back into the waking pumpkin until daylight!
//meanwhile, the waking pumpkin does not grow a stage until night, so all growth must take at least one day to happen
public abstract class SCBlockGourdGrowingAsleep extends SCBlockGourdFalling

{
	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	protected int convertedBlockID;
	
	protected boolean sameTime; //true == day
	
	protected SCBlockGourdGrowingAsleep(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        this.convertedBlockID = convertedBlockID;
        
        setHardness(1F);
	}
	
	protected abstract float GetBaseGrowthChance();
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{	
		if (!canBlockStay(world, i, j, k))
		{
			this.convertBlock(world, i, j, k); //converts the block to the non growing/harvested version
			//super.updateTick(world, i, j, k, random); //check falling, we don't as the converted block handles the falling
		}
		else
		{
//			if ( this.canBePossessed() && random.nextFloat() <= getPossesionChance() && hasPortalInRange(world, i, j, k) )
//		    {
//				this.becomePossessed(world, i, j, k, random);
//		    }
			//The main difference between asleep and waking pumpkins is whether it can trigger growth at day or night
			if ( checkTimeOfDay(world) && random.nextFloat() <= this.GetBaseGrowthChance() ) //daytime
			{
				this.grow(world, i, j, k, random);
				//this.attemptToGrow(world, i, j, k, random);
				
			}
		}				
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k ) && hasVineFacing(world, i, j, k);
    }
	
    protected int GetLightLevelForGrowth()
    {
    	return 11;
    }
    
	protected void convertBlock(World world, int i, int j, int k)
	{	
		int growthLevel = this.GetGrowthLevel(world, i, j, k);
		int harvestedMeta = getMetaHarvested(growthLevel);
		
		world.setBlockAndMetadata(i, j, k, convertedBlockID , harvestedMeta);
	}
	
	protected abstract int getMetaHarvested(int growthLevel);
	
	//NEEDS TO CONVERT BACK TO WAKING FORM! Switch the block ID but maintain the same metadata
//	public abstract void grow(World world, int i, int j, int k, Random random);
	protected void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockAndMetadataWithNotify(i, j, k, this.blockID ,meta);
	}
	
	protected int GetGrowthLevel( int meta) {
		
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
	}
	
	protected boolean checkTimeOfDay(World world) {
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		return (iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 );
	}
	
	protected int GetGrowthLevel( IBlockAccess blockAccess, int x, int y, int z)
	{		
		return GetGrowthLevel(blockAccess.getBlockMetadata(x, y, z));		
	}
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {

		return GetGrowthLevel(world.getBlockMetadata(x, y, z));		
	}
	
	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) == 3;
    }	
	
	protected boolean IsFullyGrown(World world, int i, int j, int k) {
		return IsFullyGrown(world.getBlockMetadata(i, j, k));
	}
	
	protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && (world.doesBlockHaveSolidTopSurface( i, j, k ) || blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ));
    }
	
	protected boolean hasVineFacing( World world, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );

	    int dir = getDirection(world.getBlockMetadata(i, j, k));
	    
	    
	    int oppositeFacing = Direction.rotateOpposite[dir];
	    int iTargetFacing = Direction.directionToFacing[oppositeFacing];
	    
	    
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = world.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    
	    if ( targetBlockID == this.vineBlock || targetBlockID == this.flowerBlock)
	    {	
	    	return true;
	    	
	    }
	    else return false;
	}
	
	private boolean hasStemFacing( RenderBlocks r, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	    int iTargetFacing = 0;
	    
	    int dir = this.getDirection(r.blockAccess.getBlockMetadata(i, j, k));
	    	
	    if (dir == 0) {
	    	iTargetFacing = 2;
	    }
	    else if (dir == 1) {
	    	iTargetFacing = 5;
	    }
	    else if (dir == 2) {
	    	iTargetFacing = 3;
	    }
	    else if (dir == 3) {
	    	iTargetFacing = 4;
	    }
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = r.blockAccess.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    
	    if ( targetBlockID == this.vineBlock || targetBlockID == this.flowerBlock)
	    {	
	    	return true;
	    }
	    else return false;
	}
	
	public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4, Icon icon)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;


        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;
        
        if (this.hasStemFacing(r, par2, par3, par4))
        {
        	this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F, icon);
        }
        
		return true;

    }
	
	/**
     * Utility function to draw crossed swuares
     */
    public void drawConnector(Block block, int meta, double x, double y, double z, float scale, Icon icon)
    {
        Tessellator tess = Tessellator.instance;
        
        int dir = meta & 3;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double var20 = (double)scale;
        
        //dir 0: North
        double minX = x + 0.5D;// - var20;
        double maxX = x + 0.5D;// + var20;
        double minZ = z - 0.5D;// - var20;
        double maxZ = z + 0.5D + var20;
        
        
        if (dir == 3) { //west
            minX = x - 0.5D;// - var20;
            maxX = x + 0.5D + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
            
        } else if (dir == 2) {  //South 	
            minX = x + 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0.5D - var20;
            maxZ = z + 1.5D;// + var20;
            
        } else if (dir == 1) { //east  	
            minX = x + 0.5D - var20;
            maxX = x + 1.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
        }
        
        
        if (dir == 3 || dir == 0) {
            tess.addVertexWithUV(minX, y + (2 * (double)scale) , minZ, minU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, minU, minV);
        } else {
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
        }

    }
	
}


