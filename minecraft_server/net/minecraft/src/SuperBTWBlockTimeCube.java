package net.minecraft.src;

public class SuperBTWBlockTimeCube extends BlockContainer 
{

	protected SuperBTWBlockTimeCube(int par1, Material par2Material) 
	{
		super(par1, Material.redstoneLight);
		setUnlocalizedName("tnt_bottom");
		setCreativeTab(CreativeTabs.tabTransport);
		setHardness(10);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		SuperBTWTileEntityTimeCube var2 = new SuperBTWTileEntityTimeCube();
		return var2;
	}

}