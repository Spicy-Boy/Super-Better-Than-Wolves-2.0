// FCMOD

package net.minecraft.src;

import java.util.Random;

public class FCBlockCookedBrick extends Block
{
	public static final double m_dBrickHeight = ( 4D / 16D );
	public static final double m_dBrickWidth = ( 6D / 16D );
	public static final double m_dBrickHalfWidth = ( m_dBrickWidth / 2D );
	public static final double m_dBrickLength = ( 12D / 16D );
	public static final double m_dBrickHalfLength = ( m_dBrickLength / 2D );
	
    public FCBlockCookedBrick( int iBlockID )
    {
        super( iBlockID, Material.circuits );  
        
        setHardness( 0F );
        SetPicksEffectiveOn( true );
        
        setStepSound( soundStoneFootstep );        
        
        setUnlocalizedName( "fcBlockCookedBrick" );
    }
    
    //Sock's code!
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {        
        ItemStack brick = new ItemStack(Item.brick);
        
        if (FCUtilsInventory.AddItemStackToInventoryInSlotRange( player.inventory, brick, 0, player.inventory.getSizeInventory() - 5 ))
        {
//            world.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0);
           
            player.worldObj.playSoundAtEntity( player, "random.pop", 0.2F, 
        		((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        	world.setBlockToAir(i, j, k);

            return true;
        }
        else
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
//            world.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0);
            world.setBlockToAir(i, j, k);
            
            
            return true;
        }
    }
    
//	//AARON added this to allow right click pick up torch I MEAN brick
//	@Override
//    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
//    {  
////		System.out.println("HI RIGHT CLICK BRICK!!");
//		if (!world.isRemote)
//		{
//	        if (player.getCurrentEquippedItem() == null)
//	        {
//	        	player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.brick, 1);
//	        	world.setBlock(i, j, k, 0);
//	        	return true;
//	        }
//	        else if (player.getCurrentEquippedItem().itemID == Item.brick.itemID)
//	        {
//	        	int currentStackSize = player.inventory.mainInventory[player.inventory.currentItem].stackSize;
//	        	if (currentStackSize < 64)
//	        	{
//	            	player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.brick, currentStackSize+1);
//	        	}
//	        	world.setBlock(i, j, k, 0);
//	        	return true;
//	        }
//		}
//
////			System.out.println("EMPTY HAND DETECT!");
//
////        	player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.brick, 1);
////        	FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack( Item.brick, 1));
//        	
//		return false;
//    }
//	//:>
    
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
        return SetIAligned( iMetadata, IsFacingIAligned( iFacing ) );
    }
    
	@Override
	public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving entityLiving, ItemStack stack )
	{
		int iFacing = FCUtilsMisc.ConvertOrientationToFlatBlockFacingReversed( entityLiving );
		
		SetIAligned( world, i, j, k, IsFacingIAligned( iFacing ) );
	}	
    
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
		return Item.brick.itemID;
    }
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
	{
		return null;
	}
	
    @Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( 
    	IBlockAccess blockAccess, int i, int j, int k )
    {
		if ( GetIsIAligned( blockAccess, i, j, k ) )
		{
        	return AxisAlignedBB.getAABBPool().getAABB(         	
        		0.5D - m_dBrickHalfLength, 0D, 0.5D - m_dBrickHalfWidth, 
        		0.5D + m_dBrickHalfLength, m_dBrickHeight, 0.5D + m_dBrickHalfWidth );
		}
		
    	return AxisAlignedBB.getAABBPool().getAABB(         	
    		0.5D - m_dBrickHalfWidth, 0D, 0.5D - m_dBrickHalfLength, 
    		0.5D + m_dBrickHalfWidth, m_dBrickHeight, 0.5D + m_dBrickHalfLength );
    }
    
	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
		return FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true );
    }
    
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            world.setBlockWithNotify(i, j, k, 0);
    	}
    }
    
    @Override
    public boolean CanGroundCoverRestOnBlock( World world, int i, int j, int k )
    {
    	return world.doesBlockHaveSolidTopSurface( i, j - 1, k );
    }
    
    @Override
    public float GroundCoverRestingOnVisualOffset( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return -1F;        
    }
    
	@Override
	public int GetFacing( int iMetadata )
	{
		if ( GetIsIAligned( iMetadata ) )
		{
			return 4;
		}
		
		return 2;
	}
	
	@Override
	public int SetFacing( int iMetadata, int iFacing )
	{
		return SetIAligned( iMetadata, IsFacingIAligned( iFacing ) );
	}
	
	@Override
	public boolean CanRotateOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return true;
	}
	
	@Override
	public int RotateMetadataAroundJAxis( int iMetadata, boolean bReverse )
	{
		return SetIAligned( iMetadata, !GetIsIAligned( iMetadata ) );
	}

	//------------- Class Specific Methods ------------//
	
	public void SetIAligned( World world, int i, int j, int k, boolean bIAligned )
	{
		int iMetadata = SetIAligned( world.getBlockMetadata( i, j, k ), bIAligned );
		
		world.setBlockMetadataWithNotify( i, j, k, iMetadata );
	}
	
	public int SetIAligned( int iMetadata, boolean bIAligned )
	{
		if ( bIAligned )
		{
			iMetadata |= 1;
		}
		else
		{
			iMetadata &= (~1);
		}
		
		return iMetadata;
	}
	
	public boolean GetIsIAligned( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetIsIAligned( blockAccess.getBlockMetadata( i, j, k ) );
	}
	
	public boolean GetIsIAligned( int iMetadata )
	{
		return ( iMetadata & 1 ) != 0;
	}
	
	public boolean IsFacingIAligned( int iFacing )
	{
		return iFacing >= 4;
	}
	
	//----------- Client Side Functionality -----------//
	
//	@Override
//    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
//    {
//		if ( iSide == 0 )
//		{
//			return FCClientUtilsRender.ShouldRenderNeighborFullFaceSide( blockAccess,
//				iNeighborI, iNeighborJ, iNeighborK, iSide );
//		}
//		
//		return true;
//    }
}