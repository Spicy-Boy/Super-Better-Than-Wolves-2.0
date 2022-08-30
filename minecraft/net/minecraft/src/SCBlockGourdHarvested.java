package net.minecraft.src;

public abstract class SCBlockGourdHarvested extends SCBlockGourdFalling {

	protected SCBlockGourdHarvested(int iBlockID) {
		super(iBlockID);
		setCreativeTab( CreativeTabs.tabBlock );
		
	}

    public static int GetGrowthLevel(int meta)
    {
        return meta & 3;
    }
    
	
	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	/**
	 * 
	 * @param meta
	 * @return 0 = orange, 1 = green, 2 = yellow, 3 = white
	 */
	protected int getType( int meta) {
		
		if (meta < 4)
		{
			return 0;
		}
		else if (meta >= 4 && meta < 8) 
		{
			return 1;
		}
		else if (meta >= 8 && meta < 12) 
		{
			return 2;
		} 
		else return 3;
	}
	
	protected int GetGrowthLevel( IBlockAccess blockAccess, int x, int y, int z)
	{		
		return GetGrowthLevel(blockAccess.getBlockMetadata(x, y, z));		
	}
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {

		return GetGrowthLevel(world.getBlockMetadata(x, y, z));		
	}
	
	protected int getType( IBlockAccess blockAccess, int x, int y, int z)
	{		
		return getType(blockAccess.getBlockMetadata(x, y, z));		
	}
	
	protected int getType( World world, int x, int y, int z) {

		return getType(world.getBlockMetadata(x, y, z));		
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) 
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{	
				
		//Orange
		if (meta == 0)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (meta == 1)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (meta == 2)
		{
			return GetGourdBounds(12, 12, 12);
		}
		else if (meta == 3)
		{
			return GetGourdBounds(16, 16, 16);
		}
		
		//Green
		else if (meta == 4)
		{
			return GetGourdBounds(6, 4, 6);
		}
		else if (meta == 5)
		{
			return GetGourdBounds(8, 5, 8);
		}
		else if (meta == 6)
		{
			return GetGourdBounds(12, 6, 12);
		}
		else if (meta == 7)
		{
			return GetGourdBounds(16, 8, 16);
		}
		
		//Yellow
		else if (meta == 8)
		{
			return GetGourdBounds(4, 4, 4);
		}
		else if (meta == 9)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (meta == 10)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else if (meta == 11)
		{
			return GetGourdBounds(12, 12, 12);
		}
		
		//White
		else if (meta == 12)
		{
			return GetGourdBounds(4, 3, 4);
		}
		else if (meta == 13)
		{
			return GetGourdBounds(6, 4, 6);
		}
		else if (meta == 14)
		{
			return GetGourdBounds(8, 5, 8);
		}
		else return GetGourdBounds(10, 6, 10);
	}
	

	
}
