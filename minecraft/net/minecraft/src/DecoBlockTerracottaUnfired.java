package net.minecraft.src;

public class DecoBlockTerracottaUnfired extends FCBlockFallingFullBlock
{
	public DecoBlockTerracottaUnfired(int id) {
		super(id, Material.sand);
        this.setHardness(0.5F);
        this.SetShovelsEffectiveOn();
        this.SetFilterableProperties(8);
        this.setStepSound(soundSandFootstep);
        this.setCreativeTab(CreativeTabs.tabBlock);
        
        SetCanBeCookedByKiln( true );
	}
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 6, 0, fChanceOfDrop );
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileClay.itemID, 1, 0, fChanceOfDrop );
		
		return true;
	}


    public float GetMovementModifier(World var1, int var2, int var3, int var4) {
        return 0.8F;
    }
    
    //AARON's addition
	@Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
	
	// KILN properties

    public void OnCookedByKiln(World var1, int var2, int var3, int var4) {
        var1.setBlockWithNotify(var2, var3, var4, 0);
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, SuperBTWDefinitions.terracotta.blockID, 0);
    }
	
    @Override
    public int GetCookTimeMultiplierInKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 4; //same as bricks
    }
	
	//TODO: Packing! How do I add complex packing that considers 2 different ingredients, though?
    
    //CLIENT ONLY
    public void RenderBlockSecondPass(RenderBlocks var1, int var2, int var3, int var4, boolean var5)
    {
        this.RenderCookingByKilnOverlay(var1, var2, var3, var4, var5);
    }
}