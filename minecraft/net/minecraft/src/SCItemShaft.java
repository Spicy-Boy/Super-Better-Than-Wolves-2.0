// FCMOD: modified

package net.minecraft.src;

public class SCItemShaft extends FCItemPlacesAsBlock
{
    public SCItemShaft( int iItemID )
    {
    	// the shaft supplies its own blockID through method override to avoid initialization order
    	// problems
    	
    	super( iItemID);
    	
    	setFull3D();
    	
    	SetBuoyant();
    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.SHAFT );
    	SetIncineratedInCrucible();
    	SetFilterableProperties( m_iFilterable_Narrow );
    	
    	setUnlocalizedName( "stick" );
    	
    	setCreativeTab( CreativeTabs.tabMaterials );    	
    }
    
    @Override
    public int getBlockID()
    {
        return FCBetterThanWolves.fcBlockShaft.blockID;
    }
    
    @Override
    public int getMetadata(int iItemDamage) {
    	
    	return 0;
    }
    
    @Override
    public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )    
    {
    	boolean shouldPlaceSlab = false;
    	
    	//AARON changes to allow placing blocks of shafts
//    	if (itemStack.stackSize >= 32)
//		{
//			shouldPlaceSlab = true;
//		}
    	
    	
        int iNewBlockID = GetBlockIDToPlace( itemStack.getItemDamage(), iFacing, fClickX, fClickY, fClickZ );
        
       	if (shouldPlaceSlab)
       	{
    		iNewBlockID = SuperBTWDefinitions.branchBlock.blockID;
       	}
        
        if ( itemStack.stackSize == 0 ||
        	( player != null && !player.canPlayerEdit( i, j, k, iFacing, itemStack ) ) ||        	
    		( j == 255 && Block.blocksList[iNewBlockID].blockMaterial.isSolid() ) )
        {
            return false;
        }
        
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
		
		int iOldBlockID = world.getBlockId( i, j, k );
        Block oldBlock = Block.blocksList[iOldBlockID];
		
        if ( oldBlock != null )
        {
        	if ( oldBlock.IsGroundCover( ) )
        	{
        		iFacing = 1;
        	}
        	else if ( !oldBlock.blockMaterial.isReplaceable() )
        	{
        		targetPos.AddFacingAsOffset( iFacing );
        	}
        }

        if ( ( !m_bRequireNoEntitiesInTargetBlock || IsTargetFreeOfObstructingEntities( world, targetPos.i, targetPos.j, targetPos.k ) ) && 
        	world.canPlaceEntityOnSide( iNewBlockID, targetPos.i, targetPos.j, targetPos.k, false, iFacing, player, itemStack ) )
        {
        	
        	Block newBlock = Block.blocksList[iNewBlockID];
                        
        	int iNewMetadata = getMetadata( itemStack.getItemDamage() );
        	
        	iNewMetadata = newBlock.onBlockPlaced( world, targetPos.i, targetPos.j, targetPos.k, iFacing, fClickX, fClickY, fClickZ, iNewMetadata );

        	iNewMetadata = newBlock.PreBlockPlacedBy( world, targetPos.i, targetPos.j, targetPos.k, iNewMetadata, player );            
            
            if ( world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, 
        		targetPos.k, iNewBlockID, iNewMetadata ) )
            {
                if ( world.getBlockId( targetPos.i, targetPos.j, targetPos.k ) == iNewBlockID )
                {
                    newBlock.onBlockPlacedBy( world, targetPos.i, targetPos.j, 
                		targetPos.k, player, itemStack );
                    
                    newBlock.onPostBlockPlaced( world, targetPos.i, targetPos.j, targetPos.k, iNewMetadata );
                    
                    // Panick animals when blocks are placed near them
                    world.NotifyNearbyAnimalsOfPlayerBlockAddOrRemove( player, newBlock, targetPos.i, targetPos.j, targetPos.k );            
                }
                
                PlayPlaceSound( world, targetPos.i, targetPos.j, targetPos.k, newBlock );
                
            	if (shouldPlaceSlab)
               	{
            		itemStack.stackSize -= 32;
               	}
            	else itemStack.stackSize--;
            }
            
        	return true;    	
        }
        
    	return false;    	
    }

    //------------- Class Specific Methods ------------//
	
	//----------- Client Side Functionality -----------//
}
