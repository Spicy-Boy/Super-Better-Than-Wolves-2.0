package net.minecraft.src;

public class SuperBTWBlockMeatCube extends Block{
	
	protected SuperBTWBlockMeatCube(int par1) {
		super(par1, Material.ground);
		setStepSound( FCBetterThanWolves.fcStepSoundSquish );
		setUnlocalizedName("SuperBTWBlockMeatCube");
		
        setHardness( 0.3F );
        SetBuoyancy( 1F );
        
        SetShovelsEffectiveOn( true );
	
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
	
	@Override
    public boolean DoesBlockBreakSaw( World world, int i, int j, int k )
    {
		return false;
    }
	
	@Override
    public int GetItemIDDroppedOnSaw( World world, int i, int j, int k )
    {
		return FCBetterThanWolves.fcItemRawMysteryMeat.itemID;
    }
	
	@Override
    public int GetItemCountDroppedOnSaw( World world, int i, int j, int k )
    {
		return 9;
    }
}
