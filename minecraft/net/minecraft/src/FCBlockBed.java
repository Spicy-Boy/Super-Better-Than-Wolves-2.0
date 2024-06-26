// FCMOD

package net.minecraft.src;

import java.util.Iterator;

public class FCBlockBed extends FCBlockBedBase
{
    public FCBlockBed(int blockID) {
		super(blockID);
    	
		this.setStepSound(Block.soundClothFootstep);
		this.SetBlockMaterial(FCBetterThanWolves.fcMaterialPlanks);
		
    	InitBlockBounds(0D, 0D, 0D, 1D, 0.5625D, 1D);
	}
	
	@Override
	public boolean DropComponentItemsOnBadBreak(World world, int x, int y, int z, int iMetadata, float chanceOfDrop) {
		DropItemsIndividualy(world, x, y, z, FCBetterThanWolves.fcItemSawDust.itemID, 3, 0, chanceOfDrop);
		//AARON updatedt he bed to drop 3 sticks instead of 1
		DropItemsIndividualy(world, x, y, z, Item.stick.itemID, 3, 0, chanceOfDrop);
		//Aaron updated the bed to drop 3 padding instead of 2
		DropItemsIndividualy(world, x, y, z, FCBetterThanWolves.fcItemPadding.itemID, 3, 0, chanceOfDrop);
		
		return true;
	}
	
    //------------- Class Specific Methods ------------//
	
	//----------- Client Side Functionality -----------//
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
        renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
        	renderer.blockAccess, i, j, k ) );
        
    	return renderer.RenderBlockBed( this, i, j, k );
    }
    
    @Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, 
    	int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
		return m_currentBlockRenderer.ShouldSideBeRenderedBasedOnCurrentBounds( 
			iNeighborI, iNeighborJ, iNeighborK, iSide );
    }
	
	@Override
    public int idPicked(World world, int x, int y, int z) {
        return Item.bed.itemID;
    }
}