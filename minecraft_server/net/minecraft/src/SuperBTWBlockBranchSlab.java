package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SuperBTWBlockBranchSlab extends FCBlockFalling {

	public static String[] types = { "branches", "reeds" }; //also used in the lang file eg: tile.SuperBTWBlockBranchSlab.branches.name=Bunch of Shafts

	//Metadata
	public static final int BRANCHES = 0;
	public static final int BRANCHES_I_AXIS = 1;
	
	public static final int REEDS = 2;
	public static final int REEDS_I_AXIS = 3;
	
	public static final int FULL_BRANCHES = 8;
	public static final int FULL_BRANCHES_I_AXIS = 9;
	
	public static final int FULL_REEDS = 10;
	public static final int FULL_REEDS_I_AXIS = 11;
	
	public SuperBTWBlockBranchSlab(int iBlockID) {
		super(iBlockID, Material.wood);
		
		setCreativeTab(CreativeTabs.tabBlock);
		
		setUnlocalizedName("SuperBTWBlockBranchSlab");
	}
	
	//----------- Creative Menu -----------// 
	
	@Override
	public void getSubBlocks(int id, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(id, 1, BRANCHES));
		list.add(new ItemStack(id, 1, FULL_BRANCHES));
		
		list.add(new ItemStack(id, 1, REEDS));
		list.add(new ItemStack(id, 1, FULL_REEDS));
	}
	
	//----------- Right Clicking -----------// 
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

		int meta = world.getBlockMetadata(x, y, z);
		ItemStack heldStack = player.getCurrentEquippedItem();
		
		if (heldStack != null)
		{
			if (meta == BRANCHES || meta == BRANCHES_I_AXIS)
			{
				if (heldStack.itemID == Item.stick.itemID && heldStack.stackSize >= 32)
				{				
					setFullBlock(world, x, y, z, meta);
					heldStack.stackSize -= 32;
					
					return true;
				}
			}
			else if (meta == REEDS || meta == REEDS_I_AXIS)
			{
				if (heldStack.itemID == Item.reed.itemID && heldStack.stackSize >= 32)
				{				
					setFullBlock(world, x, y, z, meta);
					heldStack.stackSize -= 32;
					
					return true;
				}
			}

		}

		return false;
		
	}
	
	protected void setFullBlock(World world, int x, int y, int z, int meta)
	{
		world.setBlockMetadataWithNotify(x, y, z, meta + 8);
	}
	
	//----------- Drops -----------//
	
	@Override
	public int idDropped(int meta, Random rand, int fortuneModifier)
	{		
		return this.blockID;
	}
	
    @Override
    public int damageDropped(int meta) {

        if ( meta == BRANCHES_I_AXIS )
        {
            meta = BRANCHES;
        }
        else if ( meta == REEDS_I_AXIS )
        {
            meta = REEDS;
        }
        
        //FullBlocks
        if ( meta == FULL_BRANCHES_I_AXIS)
        {
            meta = FULL_BRANCHES;
        }
        else if ( meta == FULL_REEDS_I_AXIS)
        {
            meta = FULL_REEDS;
        }
        return meta;
    }
	
	//----------- Block Placing -----------//
	
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
		if ( iFacing == 4 || iFacing == 5 )
		{
			if ( iMetadata == BRANCHES )
			{
				iMetadata = BRANCHES_I_AXIS;
			}
			else if ( iMetadata == REEDS )
			{
				iMetadata = REEDS_I_AXIS;
			}
			
			//FullBlocks
			if ( iMetadata == FULL_BRANCHES)
			{
				iMetadata = FULL_BRANCHES_I_AXIS;
			}
			else if ( iMetadata == FULL_REEDS)
			{
				iMetadata = FULL_REEDS_I_AXIS;
			}
		}
		
		return iMetadata;
    }
	
	@Override
    public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving placingEntity, ItemStack stack )
    {
		int iFacing = FCUtilsMisc.ConvertOrientationToFlatBlockFacingReversed( placingEntity );

		if ( iFacing == 4 || iFacing == 5 )
		{
			int iMetadata = world.getBlockMetadata( i, j, k );
			
			if ( iMetadata == BRANCHES )
			{
				world.setBlockMetadataWithNotify( i, j, k, BRANCHES_I_AXIS );
			}
			else if ( iMetadata == REEDS )
			{
				world.setBlockMetadataWithNotify( i, j, k, REEDS_I_AXIS );
			}
			
			//Full Blocks
			if ( iMetadata == FULL_BRANCHES)
			{
				world.setBlockMetadataWithNotify( i, j, k, FULL_BRANCHES_I_AXIS);
			}
			else if ( iMetadata == FULL_REEDS)
			{
				world.setBlockMetadataWithNotify( i, j, k, FULL_REEDS_I_AXIS);
			}
		}			
    }
	
	//----------- Falling Block Methods -----------//
	
	private int ItemCountToDropOnExplode(int meta) {
		
		if (isFullBlock(meta))
		{
			return 64;
		}
		
		else return 32;
	}

	private Item ItemToDropOnExplode(int meta) {
				
		if (getType(meta) == 0)
		{
			return Item.stick;
		}
		else if (getType(meta) == 1)
		{
			return Item.reed;
		}
		
		else return null;
	}
	@Override
    public boolean OnFinishedFalling( EntityFallingSand entity, float fFallDistance )
    {
    	if ( !entity.worldObj.isRemote )
    	{
	        int i = MathHelper.floor_double( entity.posX );
	        int j = MathHelper.floor_double( entity.posY );
	        int k = MathHelper.floor_double( entity.posZ );
	        
	        int iFallDistance = MathHelper.ceiling_float_int( entity.fallDistance );
	        
	    	if ( iFallDistance >= 0 )
	    	{	    		
//	    		DamageCollidingEntitiesOnFall( entity, fFallDistance );
	    		
	    		if ( !Material.water.equals( entity.worldObj.getBlockMaterial( i, j, k ) ) )
	    		{	    			
	    			Explode( entity.worldObj, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D , entity);
	    			
	    			return false;
	    		}
	    	}
	    	
			entity.worldObj.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
    	}
        
    	return true;
    }
	
    private void Explode( World world, double posX, double posY, double posZ, EntityFallingSand entity )
    {
    	Item itemToDrop = ItemToDropOnExplode(entity.metadata);
    	
    	if ( itemToDrop != null )
    	{
	        for (int iTempCount = 0; iTempCount < ItemCountToDropOnExplode(entity.metadata); iTempCount++)
	        {
	    		ItemStack itemStack = new ItemStack( itemToDrop, 1, 0 );
	
	            EntityItem entityItem = new EntityItem( world, posX, posY, posZ, itemStack );
	            
	            entityItem.motionX = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            entityItem.motionY = 0.2D + world.rand.nextDouble() * 0.3D;
	            entityItem.motionZ = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            
	            entityItem.delayBeforeCanPickup = 10;
	            
	            world.spawnEntityInWorld( entityItem );
	        }
    	}
    	
    	NotifyNearbyAnimalsFinishedFalling( world, MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ) );
        
//        world.playAuxSFX( AuxFXIDOnExplode(), 
//    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
//    		0 );
    }
	
	//----------- Helper Methods -----------//

	private int getDirection(int meta)
	{
		return meta & 1;
	}
	
	private boolean isFullBlock(int meta)
	{
		return meta > 7;
	}

	private int getType(int meta)
	{
		if ( meta == BRANCHES || meta == BRANCHES_I_AXIS )
		{
			return 0;
		}
		else if ( meta == REEDS || meta == REEDS_I_AXIS )
		{
			return 1;
		}
		
		//FullBlocks
		if ( meta == FULL_BRANCHES || meta == FULL_BRANCHES_I_AXIS )
		{
			return 0;
		}
		else if ( meta == FULL_REEDS || meta == FULL_REEDS_I_AXIS )
		{
			return 1;
		}
		
		return 0;
	}
	
	//----------- Textures -----------//
	
	private String[] topTextures = { "SCBlockPackedShaft_top", "SCBlockPackedSugarCane_top" };
	private String[] sideTextures = { "SCBlockPackedShaft_side", "SCBlockPackedSugarCane_side" };
	
	public Icon[] topIcons;
	public Icon[] sideIcons;

//	@Override
//	public void registerIcons(IconRegister register) {
//		topIcons = new Icon[topTextures.length];
//		sideIcons = new Icon[sideTextures.length];
//		
//		for (int i = 0; i < types.length; i++) {
//			topIcons[i] = register.registerIcon(topTextures[i]);
//			sideIcons[i] = register.registerIcon(sideTextures[i]);
//		}
//	}
	
	public Icon getIcon(int side, int meta) {
        int dir = getDirection(meta);
        int type = getType(meta);
        
        if (dir == 0 && (side == 2 || side == 3) ||
            dir == 1 && (side == 4 || side == 5)) {
        	return topIcons[type];
        }
        else {
        	return sideIcons[type];
        }
    }
	
//	@Override
//	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
//        int meta = blockAccess.getBlockMetadata(x, y, z);
//		int dir = getDirection(meta);
//        int type = getType(meta);
//        
//        if (dir == 0 && (side == 2 || side == 3) ||
//            dir == 1 && (side == 4 || side == 5)) {
//        	return topIcons[type];
//        }
//        else {
//        	return sideIcons[type];
//        }
//	}

	//----------- Bounds -----------//
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetBlockBoundsFromPoolBasedOnState(blockAccess.getBlockMetadata(i, j, k));
	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta )
	{
		if (isFullBlock(meta)) 
		{
			return getBounds(0F, 1F);
		}
		else return getBounds(0F, 0.5F);
	}
	
	private AxisAlignedBB getBounds(float minY, float maxY) {
		
		float centerX = 8/16F;
		float centerZ = 8/16F;
		
		float halfWidth = 8/16F;
		
		return new AxisAlignedBB(
				centerX - halfWidth, minY, centerZ - halfWidth,
				centerX + halfWidth, maxY, centerZ + halfWidth);
	}

	//----------- Render -----------//
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {

		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k)
	{	
		return isFullBlock(blockAccess.getBlockMetadata(i, j, k));
	}
		
//	public boolean RenderBlock(RenderBlocks render, int x, int y, int z)
//	{
//        int meta = render.blockAccess.getBlockMetadata(x, y, z);
//        int dir = getDirection(meta);
//
//        if (dir == 1) {
//			render.SetUvRotateTop(1);
//			render.SetUvRotateBottom(1);
//			render.SetUvRotateWest(1);
//			render.SetUvRotateEast(1);
//        }
//        else if (dir == 0) {
//			render.SetUvRotateNorth(1);
//			render.SetUvRotateSouth(1);
//        }
//        
//		render.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(meta));
//		render.renderStandardBlock(this, x, y, z);
//		render.ClearUvRotation();
//		return true;
//	}
	
	//----------- Render Falling Block -----------//
	
//	@Override
//	public void RenderFallingBlock(RenderBlocks render, int x, int y, int z, int meta) {
//        int dir = getDirection(meta);
//
//        if (dir == 1) {
//			render.SetUvRotateTop(1);
//			render.SetUvRotateBottom(1);
//			render.SetUvRotateWest(1);
//			render.SetUvRotateEast(1);
//        }
//        else if (dir == 0) {
//			render.SetUvRotateNorth(1);
//			render.SetUvRotateSouth(1);
//        }
//        
//		render.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(meta));
//		render.RenderStandardFallingBlock(this, x, y, z, meta);
//		render.ClearUvRotation();
//	}
	
	//----------- Render Item Block -----------//
	
//	@Override
//	public void RenderBlockAsItem(RenderBlocks render, int damage, float fBrightness) {
//        int dir = getDirection(damage);
//
//        if (dir == 1) {
//			render.SetUvRotateTop(1);
//			render.SetUvRotateBottom(1);
//			render.SetUvRotateWest(1);
//			render.SetUvRotateEast(1);
//        }
//        else if (dir == 0) {
//			render.SetUvRotateNorth(1);
//			render.SetUvRotateSouth(1);
//        }
//        
//		render.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(damage));
//		
//		FCClientUtilsRender.RenderInvBlockWithMetadata(render, this, -0.5F, -0.5F, -0.5F, damage);
//		
//		render.ClearUvRotation();
//	}
}


////VVVVVVVVVVVV AARON'S CODE ... ;)
//package net.minecraft.src;
//
//import java.util.List;
//
//public class SuperBTWBlockBranchSlab extends Block
//{
//	
//	public static String[] type = {"branches"};
//	public String[] topTextures = {"SuperBTWBlockBranchSlab_butt"};
//	public String[] sideTextures = {"SuperBTWBlockBranchFull_side"};
//	
//	
//	
//    public SuperBTWBlockBranchSlab( int iBlockID )
//    {
//        super( iBlockID, Material.wood);
//        
//        setHardness( 0.1F );
//        SetShovelsEffectiveOn( true );
//        SetAxesEffectiveOn( true );  
//        
//        setStepSound( soundGravelFootstep );
//        
//        setUnlocalizedName( "SuperBTWBlockBranchSlab" );
//        
//    	setCreativeTab( CreativeTabs.tabBlock );
//    	
//        SetBuoyant();
//        
//        SetFireProperties( 5, 5 );
//    }
//    
//    @Override
//    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
//    	
//    	par3List.add(new ItemStack(par1, 1, 8)); //full block
//    	par3List.add(new ItemStack(par1, 1, 0)); //half slab for creative menu
//    }
//    
//	@Override
//    public boolean CanBePistonShoveled( World world, int i, int j, int k )
//    {
//    	return true;
//    }
//
////    @Override
////	public int GetCombinedBlockID( int iMetadata )
////	{
////		return SuperBTWDefinitions.branchFull.blockID;
////	}
//    
//    //Rendering Code:
//
///* metadata guide ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// * 0 = half slab north south
// * 1 = halfg slab east west
// * ADD 8 TO GO TO FULL BLOCK
// * 8 = full block north south
// * 9 = full block east west
// * to add a new type, increase each value by 2
// */
//	
//	public Icon[] topIcons;
//	public Icon[] sideIcons;
//	
//	//SOCK'S RENDER CODE
//	public static boolean isFullBlock(int meta)
//    {
//        if (meta < 8) return false;
//        else return true;
//    }
//    
//    protected AxisAlignedBB getBounds(float minY, float maxY)
//    {
//        float centerX = 8/16F;
//        float centerZ = 8/16F;
//        
//        return new AxisAlignedBB(
//            centerX - 0.5F, minY, centerZ - 0.5F,
//            centerX + 0.5F, maxY, centerZ + 0.5F);
//    }
//    
//    @Override
//    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
//    {
//        int meta = blockAccess.getBlockMetadata(x, y, z);
//        
//        if (isFullBlock(meta))
//        {
//            return getBounds(0F, 1F).offset(y, z, meta);
//        }
//        
//        return getBounds(0F, 0.5F).offset(y, z, meta);
//    }
//    
//    @Override
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
//
//        int meta = world.getBlockMetadata(i, j, k);
//        
//        if (isFullBlock(meta))
//        {
//            return getBounds(0F, 1F);
//        }
//        
//        return getBounds(0F, 0.5F);
//
//    }
//    
//    @Override
//    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
//    		int iSide) 
//    {
//    	return true;
//    }
//    
//    @Override
//    public boolean isOpaqueCube() 
//    {
//
//    	return false;
//    }
//    
//    @Override
//    public boolean renderAsNormalBlock() 
//    {
//    	// TODO Auto-generated method stub
//    	return false;
//    }
//    
//    @Override
//    public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) 
//    {
//    	// TODO Auto-generated method stub
//    	return isFullBlock(blockAccess.getBlockMetadata(i, j, k));
//    }
//    
//    
//	
//    /**
//     * Returns the orentation value from the specified metadata
//     */
//    public static int getDirection(int par0)
//    {
//        return par0 & 1;
//    }
//    
//    
//    
//	public void registerIcons(IconRegister register) {
//		topIcons = new Icon[topTextures.length];
//		sideIcons = new Icon[topTextures.length];
//		
//		for (int i = 0; i < type.length; i++) {
//			topIcons[i] = register.registerIcon(topTextures[i]);
//			sideIcons[i] = register.registerIcon(sideTextures[i]);
//		}
//	}
//	
//	public Icon getIcon(int side, int meta) {
//        int directionMeta = getDirection(meta);
//        int type = getType(meta);
//        
//        if (directionMeta == 0 && (side == 2 || side == 3) ||
//        		directionMeta == 1 && (side == 4 || side == 5)
//        		){
//        	return topIcons[type];
//        }
//        else {
//        	return sideIcons[type];
//        }
//    }
//	
//	private int getType (int meta)
//	{
//		int type = 0;
//		
//		if ((meta < 2 || meta > 7) && meta < 10)
//		{
//			type = 0;
//		}
//		else if (meta < 4) //type is 1 which is equal to shafts/sugar cane/whatever
//		{
//			type = 1;
//		}
//		
//		return type;
//	}
//	
//	public boolean RenderBlock(RenderBlocks render, int x, int y, int z) {
//        int meta = render.blockAccess.getBlockMetadata(x, y, z);
//        int directionMeta = getDirection(meta);
//
//        if (directionMeta == 1) 
//        {
//        	//rotates the top texture by one, and bottom by one
//			render.SetUvRotateTop(1);
//			render.SetUvRotateBottom(1);
//			render.SetUvRotateWest(1);
//			render.SetUvRotateEast(1);
//        }
//        
//		render.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(render.blockAccess, x, y, z));
//		render.renderStandardBlock(this, x, y, z);
//		render.ClearUvRotation();
//		return true;
//	}
//	
////	@Override
////	//par5EntityLiving = player
////	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving,
////			ItemStack par6ItemStack) 
////	{
////		int meta = par1World.getBlockMetadata(par2, par3, par4);
////				
////		// TODO Auto-generated method stub
////		int playerRotation = ((MathHelper.floor_double((double)
////				(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
////		par1World.setBlockMetadataWithNotify(par2, par3, par4, meta + playerRotation & 1);
////	}
//	
//    public AxisAlignedBB getBoundsForItem(int meta) {
//        
//        if (isFullBlock(meta))
//        {
//            return getBounds(0F, 1F);
//        }
//        
//        return getBounds(0F, 0.5F);
//
//    }
//
//	@Override
//	public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
//		// TODO Auto-generated method stub
//
//		renderBlocks.setRenderBounds(getBoundsForItem(iItemDamage));
//		
//		FCClientUtilsRender.RenderInvBlockWithMetadata(renderBlocks, this, -0.5F, -0.5F, -0.5F, iItemDamage);
//
//	}
//	
//	
////			0 = down
////			1 = up
////			2 = north;
////			3 = south
////			4 = west
////			5 = east
//	
////BIG DEAL CLASS: Direction.java
//	
//	
//}
