package net.minecraft.src;

public class SuperBTWDefinitions {
	
	private static final int

		id_leatherWorking = 20000,
		id_flintKnapping = 20001,
		
		id_flintBlade = 20002,
		id_ironBlade = 20003,
		
		id_cowRib = 20004,
		id_rib = 20005,
		id_cookedCowRib = 20006,
		id_bonePickaxe = 20007,
		
		id_leatherWorkingIron = 20008,
		
		id_wetMudBrick = 20009,
		id_wetAdobe = 20010,
		id_adobe = 20011,
		
		id_branch = 20012,
		
		id_ribCarving = 20013,
		id_ribCarvingIron = 20014,
	
		id_cookedCowRibPartial = 20015,
		id_cookedCowRibSpent = 20016,
	
		id_hoeStoneNew = 20017,
		
		id_bowStringing = 20018,
	
		id_deathClub = 20019,
		
		id_bedroll = 20020,
		
		id_mortarBucket = 20021,
		
		id_trowel = 20022;
		
	private static final int
		id_branchBlock = 2000,
		id_sunflower = 2001,
		id_gravestone = 2002,
		id_meatCube = 2003,
		id_blockBedroll = 2004,
		id_timeCube = 2005;
		
	
	public static Item leatherWorking;
	public static Item flintKnapping;
	public static Item flintBlade;
	public static Item ironBlade;
	public static Item branch;
	public static Item cowRib;
	public static Item rib;
	public static Item cookedCowRib;
	public static Item bonePickaxe;
	public static Item leatherWorkingIron;
	public static Item ribCarving;
	public static Item ribCarvingIron;
	public static Item cookedCowRibPartial;
	public static Item cookedCowRibSpent;
	public static Item hoeStoneNew;
	public static Item bowStringing;
	public static Item deathClub;
	public static Item fcItemBedroll;
	public static Item mortarBucket;
	public static Item trowel;
	
	public static Block branchBlock;
	public static Block sunflower;
	public static Block gravestone;
	public static Block meatCube;
	public static Block fcBlockBedroll;
	public static Block timeCube;
	
	public static void addDefinitions() 
	{

		leatherWorking = new SuperBTWItemLeatherWorking(id_leatherWorking - 256);
		flintKnapping = new SuperBTWItemFlintKnapping(id_flintKnapping - 256);
		flintBlade = new SuperBTWItemFlintBlade(id_flintBlade - 256);
		ironBlade = new SuperBTWItemIronBlade(id_ironBlade - 256);
		branch = new SuperBTWItemBranch(id_branch - 256);
		cowRib = new SuperBTWItemCowRib(id_cowRib - 256);
		rib = new SuperBTWItemRib(id_rib - 256);
		cookedCowRib = new SuperBTWItemCookedCowRib(id_cookedCowRib - 256);
		bonePickaxe = new SuperBTWItemBonePickaxe(id_bonePickaxe - 256);
		leatherWorkingIron = new SuperBTWItemLeatherWorkingIron(id_leatherWorkingIron - 256);
		ribCarving = new SuperBTWItemRibCarving(id_ribCarving - 256);
		ribCarvingIron = new SuperBTWItemRibCarvingIron(id_ribCarvingIron - 256);
		cookedCowRibPartial = new SuperBTWItemCookedCowRibPartial(id_cookedCowRibPartial - 256);
		cookedCowRibSpent = new SuperBTWItemCookedCowRibSpent(id_cookedCowRibSpent - 256);
		hoeStoneNew = ( new FCItemHoe( id_hoeStoneNew, EnumToolMaterial.BONE ) ).setUnlocalizedName( "hoeStone" );;
		bowStringing = new SuperBTWItemBowStringing(id_bowStringing - 256);
		deathClub = new SuperBTWItemDeathClub(id_deathClub - 256);
		mortarBucket = new SuperBTWItemMortarBucket(id_mortarBucket - 256);
		trowel = new SuperBTWItemTrowel(id_trowel - 256);
		
		branchBlock = new SuperBTWBlockBranch(id_branchBlock);
		Item.itemsList[branchBlock.blockID] = new ItemBlock(branchBlock.blockID - 256); 
	
		sunflower = new SuperBTWBlockSunflower(id_sunflower);
		Item.itemsList[sunflower.blockID] = new ItemBlock(sunflower.blockID - 256);
		
		gravestone = new SuperBTWBlockGravestone(id_gravestone);
		Item.itemsList[gravestone.blockID] = new ItemBlock(gravestone.blockID - 256);
		
		meatCube = new SuperBTWBlockMeatCube(id_meatCube);
		Item.itemsList[meatCube.blockID] = new ItemBlock(meatCube.blockID - 256);
		
		fcBlockBedroll = new FCBlockBedroll(id_blockBedroll).setHardness(0.1F).SetBuoyant().setUnlocalizedName("fcBlockBedroll").disableStats();
		fcItemBedroll = new FCItemBed(id_bedroll - 256, id_blockBedroll).SetBuoyant().SetIncineratedInCrucible().setMaxStackSize(1).setUnlocalizedName("fcItemBedroll");
		
		timeCube = new SuperBTWBlockTimeCube(id_timeCube, null);
		Item.itemsList[timeCube.blockID] = new ItemBlock(timeCube.blockID - 256)
				.setMaxStackSize( 1 );
		TileEntity.addMapping(SuperBTWBlockTimeCube.class, "timeCube");
	}
	
}
