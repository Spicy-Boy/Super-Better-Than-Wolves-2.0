package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SuperBTWBlockBranch extends Block
{
	protected SuperBTWBlockBranch(int par1)
	{
        super(par1, Material.circuits);
        //same as lily pad
        float var2 = 0.5F;
        float var3 = 0.015625F;
        this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
        
        InitBlockBounds( 0D, 0D, 0D, 1D, 0.015625D, 1D );
        
		setUnlocalizedName("SuperBTWBlockBranchBlock");
	}
	
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		
		if (par1World.getBlockId(par2, par3 - 1, par4) == Block.grass.blockID || par1World.getBlockId(par2, par3 - 1, par4) == Block.dirt.blockID)
		{
			return true;
		}
		else return false;
		
		//return super.canBlockStay(par1World, par2, par3, par4);
	}
	
	@Override
	public boolean CanBeCrushedByFallingEntity(World world, int i, int j, int k, EntityFallingSand entity) {
		// TODO Auto-generated method stub
		return true;
	}
	

    
    public int idDropped( int iMetaData, Random random, int iFortuneModifier )
    {
        return SuperBTWDefinitions.branch.itemID;
    }

    
    //rendering code! to mimic lilypadssssss VVV
//    
//    public int getRenderType()
//    {
//        return 23;
//    }
//    
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        if (par7Entity == null || !(par7Entity instanceof EntityBoat))
        {
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }
    
//    @Override
//    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
//    		int iSide) {
//    	// TODO Auto-generated method stub
//    	return true;
//    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
    {
    	return null;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    private Icon woodIcon;
    private Icon branchIcon;
    
//    public void registerIcons( IconRegister register )
//    {
//        woodIcon = register.registerIcon( "tree_spruce" );
//        branchIcon = register.registerIcon("SuperBTWBlockBranch");
//       // branchIcon = register.registerIcon("redstoneDust_cross");
//    }
    
    public Icon getIcon( int iSide, int iMetadata )
    {
//    	return branchIcon;
        if ( iSide == 1 || iSide == 0 )
        {
            return branchIcon;
        }
        
        return woodIcon;
    }
    
  
/*SOCK'S RENDER CODE: has some z fighting and transparency issues...*/    
//    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k)
//    {
//        
//        renderBlocks.setRenderBounds(12/16D, 0, 3/16D, 14/16D, 1/16D, 5/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(11/16D, 0, 4/16D, 13/16D, 1/16D, 6/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(10/16D, 0, 5/16D, 12/16D, 1/16D, 7/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(9/16D, 0, 6/16D, 11/16D, 1/16D, 8/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(7/16D, 0, 6/16D, 10/16D, 1/16D, 9/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(8/16D, 0, 5/16D, 9/16D, 1/16D, 10/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(6/16D, 0, 9/16D, 8/16D, 1/16D, 11/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(5/16D, 0, 10/16D, 7/16D, 1/16D, 12/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(4/16D, 0, 11/16D, 6/16D, 1/16D, 13/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(3/16D, 0, 12/16D, 5/16D, 1/16D, 14/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//        
//        renderBlocks.setRenderBounds(2/16D, 0, 13/16D, 4/16D, 1/16D, 15/16D);
//        renderBlocks.renderStandardBlock(this, i, j, k);
//
//        return true;
//    }
    

}
