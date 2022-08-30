package net.minecraft.src;

import java.util.Random;

public class SuperBTWBlockSunflower extends BlockFlower
{
	

	protected SuperBTWBlockSunflower(int par1) {
		super(par1);
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SuperBTWBlockSunflower");
	
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entity,
			ItemStack par6ItemStack) {
		if (world.getBlockId(i, j + 1, k) == 0)
		{
			world.setBlockAndMetadataWithNotify(i, j + 1, k, this.blockID, 1);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		if (world.getBlockId(i, j + 1, k) == 0)
		{
			return true;
		}
		else return false;
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		if (world.getBlockId(i, j - 1, k) == this.blockID)
		{
			return true;
		}
		else return super.canBlockStay(world, i, j, k);
	}
	
	private boolean isTopBlock(RenderBlocks renderer, int i, int j, int k) {
		if (renderer.blockAccess.getBlockMetadata(i, j, k) == 1) {
			return true;
		}
		else return false;
	}
	
	@Override
	public int idDropped(int meta, Random par2Random, int par3) {
		if (meta == 0) {
			return this.blockID;
		}
		else return 0;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int meta) {
		if (world.getBlockId(i, j - 1, k) == this.blockID)
		{
			world.setBlockWithNotify(i, j - 1, k, 0);
			DropItemsIndividualy(world, i, j, k, this.blockID, 1, 0, 1);
		}
	}
	
	Icon sunflowerBottom;
	Icon sunflowerTop;
	Icon sunflowerFront;
	Icon sunflowerBack;
	
	@Override
	public void registerIcons(IconRegister register) {
		sunflowerBottom = register.registerIcon("sunflower_bottom");
		sunflowerTop = register.registerIcon("sunflower_top");
		sunflowerFront = register.registerIcon("sunflower_front");
		sunflowerBack = register.registerIcon("sunflower_back");
		
		blockIcon = sunflowerFront;
	}
	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		if (isTopBlock(renderer, i, j, k))
		{
			this.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, sunflowerTop);
			this.renderSunflowerPlaneWithTextures(renderer, this, i, j, k, sunflowerFront);
			this.renderBackSunflowerPlaneWithTextures(renderer, this, i, j, k, sunflowerBack);
		}
		else this.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, sunflowerBottom);
	
		
		return true;
	}
	
	public static void RenderCrossedSquaresWithTexture( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture )
    {
        boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
        
        if ( !bHasOverride )
        {
        	renderBlocks.setOverrideBlockTexture( texture );
        }
        
        renderBlocks.renderCrossedSquares( block, i, j, k );
        
        if ( !bHasOverride )
        {
        	renderBlocks.clearOverrideBlockTexture();
        }
    }
    
    public static void renderSunflowerPlaneWithTextures( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture )
    {
    	 boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
         
         if ( !bHasOverride )
         {
         	renderBlocks.setOverrideBlockTexture( texture );
         }
         
         renderFrontSideFlower(renderBlocks, i, j, k, texture);
         
         if ( !bHasOverride )
         {
         	renderBlocks.clearOverrideBlockTexture();
         }
	}
    
    public static void renderBackSunflowerPlaneWithTextures( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture )
    {
    	 boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
         
         if ( !bHasOverride )
         {
         	renderBlocks.setOverrideBlockTexture( texture );
         }
         
         renderBackSideFlower(renderBlocks, i, j, k, texture);
         
         if ( !bHasOverride )
         {
         	renderBlocks.clearOverrideBlockTexture();
         }
	}
    
    public static void renderFrontSideFlower(RenderBlocks renderer, int x, int y, int z, Icon texture)
    {
    	Tessellator tess = Tessellator.instance;
        
        Icon icon = texture;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double minX = x + 0.5D + (0.125/2);
        double maxX = x + 0.5D + (0.125/2);
        double minZ = z + 0D;
        double maxZ = z + 1D;
        
        double yUp = 0.125D;

        tess.addVertexWithUV(maxX  - 0.25, y + 1D + yUp, maxZ, maxU, minV);
        
        tess.addVertexWithUV(maxX + 0.25, y + 0D + yUp, maxZ, maxU, maxV);
        tess.addVertexWithUV(minX + 0.25, y + 0D + yUp, minZ, minU, maxV);
        
        tess.addVertexWithUV(minX - 0.25 , y + 1D + yUp, minZ, minU, minV);
		
    }

    public static void renderBackSideFlower(RenderBlocks renderer, int x, int y, int z, Icon texture)
    {
        Tessellator tess = Tessellator.instance;
        
        Icon icon = texture;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double minX = x + 0.5D + (0.125/2);
        double maxX = x + 0.5D + (0.125/2);
        double minZ = z + 0D;
        double maxZ = z + 1D;
        
        double yUp = 0.125D;

        tess.addVertexWithUV(minX  - 0.25, y + 1D + yUp, minZ, minU, minV);
        
        tess.addVertexWithUV(minX + 0.25, y + 0D + yUp, minZ , minU, maxV);
        tess.addVertexWithUV(maxX + 0.25, y + 0D + yUp, maxZ , maxU, maxV);
        
        tess.addVertexWithUV(maxX - 0.25, y + 1D + yUp, maxZ , maxU, minV);
		
    }

	
}
