package net.minecraft.src;

public class SuperBTWItemTombstonePlacer extends Item 
{
	public SuperBTWItemTombstonePlacer( int itemID )
	{
		super(itemID);
		
		maxStackSize = 1;
		
		setCreativeTab( CreativeTabs.tabDecorations);
		
		setUnlocalizedName( "SuperBTWItemTombstonePlacer" );

	}
	
    @Override
    public boolean onItemUse( ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
        if ( iFacing == 1 )
        {
            ++j;

            if ( player.canPlayerEdit( i, j, k, iFacing, stack ))
            {
                if ( getTombstoneBlock().canPlaceBlockAt( world, i, j, k ) )
                {
                    int side = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
                    
                    world.setBlock(i, j, k, getTombstoneBlock().blockID, side, 2);
                    
                    --stack.stackSize;
                    
                    return true;
                }
            }
        }
        
        return false;
    }
 

    
    public Block getTombstoneBlock()
    {
    	return SuperBTWDefinitions.gravestone;
    }
}
