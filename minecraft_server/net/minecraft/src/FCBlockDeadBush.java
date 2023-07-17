// FCMOD

package net.minecraft.src;

import java.util.Random;

public class FCBlockDeadBush extends BlockDeadBush
{
    protected static final double m_dWidth = 0.8D;
    protected static final double m_dHalfWidth = ( m_dWidth / 2D );
    
    protected FCBlockDeadBush( int iBlockID )
    {
    	super( iBlockID );
    	
    	//Aaron changed hardness
    	setHardness( 0.1F );
    	
    	SetBuoyant();
    	
        InitBlockBounds( 
        	0.5D - m_dHalfWidth, 0D, 0.5D - m_dHalfWidth, 
        	0.5D + m_dHalfWidth, 0.8D, 0.5D + m_dHalfWidth);
        
    	setStepSound( soundGrassFootstep );
    	
    	setUnlocalizedName("deadbush");    	
    }
    
    @Override
    public boolean CanSpitWebReplaceBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean IsReplaceableVegetation( World world, int i, int j, int k )
    {
    	return true;
    }
	
    @Override
    public boolean CanBeGrazedOn( IBlockAccess blockAccess, int i, int j, int k, 
    	EntityAnimal animal )
    {
		return animal.CanGrazeOnRoughVegetation();
    }
    
    @Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	return world.getBlockId( i, j, k ) == Block.sand.blockID;
    }
    
    //Aaron added a branch drop!
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return SuperBTWDefinitions.branch.itemID;
    }
    
    //AARON added chance to drop
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
        if ( !world.isRemote )
        {
        	if ( world.rand.nextDouble() > .20 )
            {
                int iIdDropped = idDropped( iMetadata, world.rand, iFortuneModifier );
                
                dropBlockAsItem_do( world, i, j, k, new ItemStack( iIdDropped, 1, 0) );
            }
        }
    }
    
    //------------- Class Specific Methods ------------//

	//----------- Client Side Functionality -----------//    
}
    
