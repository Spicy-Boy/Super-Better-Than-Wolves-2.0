
package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdVineDead extends SCBlockGourdVine {

	private static String texVine;
	private static String texConnector;

	protected SCBlockGourdVineDead(int iBlockID, int floweringBlock, int stemBlock) {
		super(iBlockID, floweringBlock, stemBlock, 0, texVine, texConnector);
		
		setHardness( 0.0F );
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!this.canBlockStay(world, i, j, k))
		{
			world.setBlock(i, j, k, 0);
		}
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k );
    }
	
	@Override
    public void registerIcons( IconRegister register )
    {
    	vineIcons = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < vineIcons.length; iTempIndex++ )
        {
        	vineIcons[iTempIndex] = register.registerIcon( "SCBlockPumpkinVineDead_" + iTempIndex );
        }
        
        blockIcon = vineIcons[3]; // for block hit effects and item render
        
        connectorIcons = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcons.length; iTempIndex++ )
        {
        	connectorIcons[iTempIndex] = register.registerIcon( "SCBlockPumpkinVineConnectorDead_" + iTempIndex );
        }
   
    }
	
	@Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
        return vineIcons[growthLevel];
    }

}
