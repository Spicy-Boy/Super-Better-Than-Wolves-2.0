package net.minecraft.src;

public class SuperBTWDefinitions {
	
	//lifted straight out of Block.java
	public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
	
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
		
		id_wetMudBrickItem = 20009,
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
		
		id_trowel = 20022,
		
		id_envelopeOpen = 20023,
		id_envelopeClosed = 20024,
		id_tombstonePlacer = 20025,
		
		id_pipeEmpty = 20026,
		id_pipePacked = 20027,
		id_pipeLit = 20028,
		
		id_treat = 20029,
		id_badTreat = 20030;
	
		//GOURD MANIA!
	private static final int
		id_waterMelonSeeds = 31020,
		id_canaryMelonSeeds = 31021,
		id_honeydewMelonSeeds = 31022,
		id_cantaloupeMelonSeeds = 31023,
				
		id_foulSeeds = 31024,
	
	
		id_orangePumpkinSeeds = 31029,
		id_greenPumpkinSeeds = 31030,
		id_yellowPumpkinSeeds = 31031,
		id_whitePumpkinSeeds = 31032;
		
	private static final int
		id_branchBlock = 2000,
		id_sunflower = 2001,
		id_gravestone = 2002,
		id_meatCube = 2003,
		id_blockBedroll = 2004,
		id_timeCube = 2005,
		id_wetMudBrick = 2006,
		id_gloryHole = 2007,
		id_reedThatchSlab = 2008,
		id_reedThatch = 2009,
		id_strawThatchSlab = 2010,
		id_strawThatch = 2011,
		id_stickBundleLooseSlab = 2012,
		id_stickBundleLoose = 2013,
		id_superGlass = 2014,
		id_superBlock = 2015,
		// 2016-2020 saved
		id_terracotta = 2021,
		id_stainedTerracotta = 2022,
		id_unfiredTerracotta = 2023,
		id_terracottaSlab = 2024,
		id_terracottaSlab2 = 2025,
		id_terracottaSlabDefault = 2026,
		id_whiteStoneBrick = 2027,
		id_whiteStoneBrickStairs = 2028,
		id_whiteStoneBrickSlab = 2029,
		id_sandstoneBrickLarge = 2030,
		id_sandstoneBrickLargeStairs = 2031,
		id_sandstoneBrickLargeSlab = 2032;
	
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
	public static Item envelopeOpen;
	public static Item envelopeClosed;
	public static Item tombstonePlacer;
	public static Item pipeEmpty;
	public static Item pipePacked;
	public static Item pipeLit;
	public static Item treat;
	public static Item badTreat;
	
	public static Block branchBlock;
	public static Block sunflower;
	public static Block gravestone;
	public static Block meatCube;
	public static Block fcBlockBedroll;
	public static Block timeCube;
	
	public static Block superGlass;
	public static Block superBlock;

	public static Block ghBlockGloryhole;
	
	public static Block reedThatchSlab;
	public static Block reedThatch;
	public static Block strawThatchSlab;
	public static Block strawThatch;
	
	public static Block stickBundleLooseSlab; //untied version
	public static Block stickBundleLoose;

	
	public static SuperBTWBlockWetMudBrick wetMudBrick;
	public static Item wetMudBrickItem;
	
	public static Block terracotta, stainedTerracotta, unfiredTerracotta;
	public static Block terracottaSlab, terracottaSlab2, terracottaSlabDefault;
	public static Block whiteStoneBrick, whiteStoneBrickStairs, whiteStoneBrickSlab;
	public static Block sandstoneBrickLarge, sandstoneBrickLargeStairs, sandstoneBrickLargeSlab;
	
	//GOURD MANIA!
	
	public static Item orangePumpkinSeeds;
	public static Item greenPumpkinSeeds;
	public static Item yellowPumpkinSeeds;
	public static Item whitePumpkinSeeds;
	
	public static Item waterMelonSeeds;
	public static Item canaryMelonSeeds;
	public static Item honeydewMelonSeeds;
	public static Item cantaloupeMelonSeeds;
	
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
		hoeStoneNew = ( new FCItemHoe( id_hoeStoneNew, EnumToolMaterial.BONE ) ).setUnlocalizedName( "hoeStone" );
		bowStringing = new SuperBTWItemBowStringing(id_bowStringing - 256);
		deathClub = new SuperBTWItemDeathClub(id_deathClub - 256);
		mortarBucket = new SuperBTWItemMortarBucket(id_mortarBucket - 256);
		trowel = new SuperBTWItemTrowel(id_trowel - 256);
		envelopeOpen = new SuperBTWItemEnvelopeOpen(id_envelopeOpen - 256);
		envelopeClosed = new SuperBTWItemEnvelopeClosed(id_envelopeClosed - 256);
		tombstonePlacer = new SuperBTWItemTombstonePlacer(id_tombstonePlacer - 256);
		pipeEmpty = new SuperBTWItemPipeEmpty(id_pipeEmpty - 256);
		pipePacked = new SuperBTWItemPipePacked(id_pipePacked - 256);
		pipeLit = new SuperBTWItemPipeLit(id_pipeEmpty - 256);
		treat = new SuperBTWItemTreat(id_treat - 256);
		badTreat = new SuperBTWItemBadTreat(id_badTreat - 256);
		
		branchBlock = new SuperBTWBlockBranch(id_branchBlock);
		Item.itemsList[branchBlock.blockID] = new ItemBlock(branchBlock.blockID - 256); 
	
		sunflower = new SuperBTWBlockSunflower(id_sunflower);
		Item.itemsList[sunflower.blockID] = new ItemBlock(sunflower.blockID - 256);
		
		gravestone = new SuperBTWBlockGravestone(id_gravestone);
		Item.itemsList[gravestone.blockID] = new ItemBlock(gravestone.blockID - 256);
		
		meatCube = new SuperBTWBlockMeatCube(id_meatCube);
		Item.itemsList[meatCube.blockID] = new ItemBlock(meatCube.blockID - 256);
		
		superGlass = new SuperBTWBlockSuperGlass(id_superGlass, Material.glass).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockUnbreakable()
				.setResistance( 6000000F ).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("SuperBTWBlockSuperGlass");
		Item.itemsList[superGlass.blockID] = new ItemBlock(superGlass.blockID - 256);
		
		superBlock = new FCBlockBedrock(id_superBlock).setBlockUnbreakable().setResistance( 6000000F ).setCreativeTab(CreativeTabs.tabBlock)
				.setUnlocalizedName("SuperBTWBlockSuperBlock");
		Item.itemsList[superBlock.blockID] = new ItemBlock(superBlock.blockID - 256);
		
		fcBlockBedroll = new FCBlockBedroll(id_blockBedroll).setHardness(0.1F).SetBuoyant().setUnlocalizedName("fcBlockBedroll").disableStats();
		fcItemBedroll = new FCItemBed(id_bedroll - 256, id_blockBedroll).SetBuoyant().SetIncineratedInCrucible().setMaxStackSize(1).setUnlocalizedName("fcItemBedroll");
		
		timeCube = new SuperBTWBlockTimeCube(id_timeCube, null);
		Item.itemsList[timeCube.blockID] = new ItemBlock(timeCube.blockID - 256).setMaxStackSize( 16 );
		TileEntity.addMapping(SuperBTWTileEntityTimeCube.class, "timeCube");
		
		wetMudBrick = new SuperBTWBlockWetMudBrick(id_wetMudBrick);
		TileEntity.addMapping(SuperBTWTileEntityWetMudBrick.class, "wetMudbrick");
		wetMudBrickItem = new SuperBTWItemWetMudBrick(id_wetMudBrickItem - 256);
		
		reedThatchSlab = new SuperBTWBlockReedThatchSlab(id_reedThatchSlab);
		Item.itemsList[reedThatchSlab.blockID] = new SuperBTWItemBlockReedThatchSlab(reedThatchSlab.blockID - 256);
		reedThatch = new SuperBTWBlockReedThatch(id_reedThatch);
		Item.itemsList[reedThatch.blockID] = new ItemBlock(reedThatch.blockID - 256);
		
		strawThatchSlab = new SuperBTWBlockStrawThatchSlab(id_strawThatchSlab);
		Item.itemsList[strawThatchSlab.blockID] = new SuperBTWItemBlockStrawThatchSlab(strawThatchSlab.blockID - 256);
		strawThatch = new SuperBTWBlockStrawThatch(id_strawThatch);
		Item.itemsList[strawThatch.blockID] = new ItemBlock(strawThatch.blockID - 256);
		
		//Sockthing's beta code
		stickBundleLooseSlab = new SuperBTWBlockStickBundleLooseSlab(id_stickBundleLooseSlab);
		Item.itemsList[stickBundleLooseSlab.blockID] = new SuperBTWItemBlockStickBundleLooseSlab(stickBundleLooseSlab.blockID - 256);
		stickBundleLoose = new SuperBTWBlockStickBundleLoose(id_stickBundleLoose);
		Item.itemsList[stickBundleLoose.blockID] = new ItemBlock(stickBundleLoose.blockID - 256);
		

//        branchBlock = new SuperBTWBlockBranchSlab(id_branchBlock);
//        Item.itemsList[branchBlock.blockID] = new ItemMultiTextureTile(id_branchBlock - 256, branchBlock, SuperBTWBlockBranchSlab.types);
        
        //Item.stick = new SCItemShaft(Item.stick.itemID);
		
		//GOURD MANIA!
		
		orangePumpkinSeeds = new SCItemOrangePumpkinSeeds (id_orangePumpkinSeeds - 256, SCDefs.id_pumpkinStemOrange);
		greenPumpkinSeeds = new SCItemGreenPumpkinSeeds (id_greenPumpkinSeeds - 256, SCDefs.id_pumpkinStemGreen);
		yellowPumpkinSeeds = new SCItemYellowPumpkinSeeds (id_yellowPumpkinSeeds - 256, SCDefs.id_pumpkinStemYellow);
		whitePumpkinSeeds = new SCItemWhitePumpkinSeeds (id_whitePumpkinSeeds - 256, SCDefs.id_pumpkinStemWhite);
		
		waterMelonSeeds = new SCItemWaterMelonSeeds (id_waterMelonSeeds - 256, SCDefs.id_melonStemWater);
		canaryMelonSeeds = new SCItemCanaryMelonSeeds (id_canaryMelonSeeds - 256, SCDefs.id_melonStemCanary);
		honeydewMelonSeeds = new SCItemHoneydewMelonSeeds (id_honeydewMelonSeeds - 256, SCDefs.id_melonStemHoneydew);
		cantaloupeMelonSeeds = new SCItemCantaloupeMelonSeeds (id_cantaloupeMelonSeeds - 256, SCDefs.id_melonStemCantaloupe);
	
		//Ts
		ghBlockGloryhole = new TSGBlockGloryhole(id_gloryHole).setUnlocalizedName("ghBlockGloryhole").setCreativeTab(CreativeTabs.tabDecorations);
		Item.itemsList[ghBlockGloryhole.blockID] = new ItemBlock(ghBlockGloryhole.blockID - 256);
		
		//Deco additions!
		terracotta = new DecoBlockTerracotta(id_terracotta).setHardness(1.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("decoBlockTerracotta").setCreativeTab(CreativeTabs.tabBlock);
		Item.itemsList[terracotta.blockID] = new ItemBlock(terracotta.blockID - 256);
		stainedTerracotta = (new DecoBlockTerracottaStained(id_stainedTerracotta)).setHardness(1.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("decoBlockTerracottaStained");
		Item.itemsList[stainedTerracotta.blockID] = new DecoItemBlockColored(stainedTerracotta.blockID - 256, stainedTerracotta);
		
		unfiredTerracotta = new DecoBlockTerracottaUnfired(id_unfiredTerracotta).setUnlocalizedName("decoBlockTerracottaUnfired");
		Item.itemsList[unfiredTerracotta.blockID] = new ItemBlock(unfiredTerracotta.blockID - 256);
		
		terracottaSlabDefault = new DecoBlockSlabStone(id_terracottaSlabDefault, new Block[] {SuperBTWDefinitions.terracotta}, new int[] {0}).setUnlocalizedName("decoBlockTerracottaSlabDefault");
		Item.itemsList[SuperBTWDefinitions.terracottaSlabDefault.blockID] = new DecoItemBlockSlab(SuperBTWDefinitions.terracottaSlabDefault.blockID - 256);
		terracottaSlab = new DecoBlockSlabStone(id_terracottaSlab, new Block[] {SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta}, new int[] {0, 1, 2, 3, 4, 5, 6, 7}).setUnlocalizedName("decoBlockTerracottaSlab");
		Item.itemsList[SuperBTWDefinitions.terracottaSlab.blockID] = new DecoItemBlockSlab(SuperBTWDefinitions.terracottaSlab.blockID - 256);
		terracottaSlab2 = new DecoBlockSlabStone(id_terracottaSlab2, new Block[] {SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta, SuperBTWDefinitions.stainedTerracotta}, new int[] {8, 9, 10, 11, 12, 13, 14, 15}).setUnlocalizedName("decoBlockTerracottaSlab2");
		Item.itemsList[SuperBTWDefinitions.terracottaSlab2.blockID] = new DecoItemBlockSlab(SuperBTWDefinitions.terracottaSlab2.blockID - 256);
		
//		whiteStoneBrick = new DecoBlockWhiteStoneBrick(id_whiteStoneBrick).setUnlocalizedName("decoBlockWhiteBricks");
//		Item.itemsList[whiteStoneBrick.blockID] = new DecoItemBlockBrick(whiteStoneBrick.blockID - 256, whiteStoneBrick);
//		whiteStoneBrickSlab = new DecoBlockSlabStone(id_whiteStoneBrickSlab, new Block[] {SuperBTWDefinitions.whiteStoneBrick}, new int[] {0}).setUnlocalizedName("decoBlockWhiteStoneBrickSlab");
//		Item.itemsList[SuperBTWDefinitions.terracottaSlabDefault.blockID] = new DecoItemBlockSlab(SuperBTWDefinitions.whiteStoneBrickSlab.blockID - 256);
//		whiteStoneBrickStairs = new FCBlockStairs(id_whiteStoneBrickStairs, whiteStoneBrick, 0).setUnlocalizedName("decoBlockWhiteBricksStairs");
//		Item.itemsList[whiteStoneBrickStairs.blockID] = new ItemBlock(whiteStoneBrickStairs.blockID - 256);
//		
//		sandstoneBrickLarge = new DecoBlockWhiteStoneBrick(id_sandstoneBrickLarge).setUnlocalizedName("decoBlockSandstoneBrickLarge");
//		Item.itemsList[whiteStoneBrick.blockID] = new DecoItemBlockBrick(whiteStoneBrick.blockID - 256, whiteStoneBrick);
//		
//		sandstoneBrickLargeSlab = new DecoBlockSlabStone(id_sandstoneBrickLargeSlab, new Block[] {SuperBTWDefinitions.sandstoneBrickLarge}, new int[] {0}).setUnlocalizedName("decoBlockSandstoneBrickLargeSlab");
//		Item.itemsList[SuperBTWDefinitions.sandstoneBrickLargeSlab.blockID] = new DecoItemBlockSlab(SuperBTWDefinitions.sandstoneBrickLargeSlab.blockID - 256);
//
//		sandstoneBrickLargeStairs = new FCBlockStairs(id_sandstoneBrickLargeStairs, sandstoneBrickLarge, 0).setUnlocalizedName("decoBlockSandstoneBrickLargeStairs");
//		Item.itemsList[sandstoneBrickLargeStairs.blockID] = new ItemBlock(sandstoneBrickLargeStairs.blockID - 256);
//		
//		sandStoneBrickLargeStairs = new DecoBlockStairsSandStone(id_sandstoneBrickLargeStairs, Block.sandStone, 6).setUnlocalizedName("decoBlockStairsSandStoneBrickLarge");
	}
	
	//BLOCK ID CHECKERS, for when you need to check if a block has a certain quality to it!
	
	public static boolean isPlantBlock(int blockID)
	{
    	if (blockID == Block.tallGrass.blockID ||
    			blockID == Block.plantRed.blockID ||
    			blockID == Block.plantYellow.blockID ||
    			blockID == Block.mushroomBrown.blockID ||
    			blockID == Block.mushroomRed.blockID ||
    			blockID == SuperBTWDefinitions.branchBlock.blockID
        	)
    	{
    		return true;
    	}
    	
    	return false;
	}
	
	//a list of my weak blocks. If a block is weak, it will trigger certain new behaviors outlined in methods below!
	//this mostly relates to gravity interactions
	public static boolean isWeakBlock(int blockID)
	{
    	if (blockID == SuperBTWDefinitions.strawThatchSlab.blockID || 
        		blockID == SuperBTWDefinitions.strawThatch.blockID ||
        		blockID == SuperBTWDefinitions.reedThatchSlab.blockID ||
        		blockID == SuperBTWDefinitions.reedThatch.blockID ||
        		blockID == SuperBTWDefinitions.stickBundleLooseSlab.blockID ||
        		blockID == SuperBTWDefinitions.stickBundleLoose.blockID
        	)
    	{
    		return true;
    	}
    	
    	return false;
	}
	
    //checks if the block ID matches one that will cause weak blocks to break beneath them
	//for example, if a block of obsidian is placed above thatch, blockIDAbove will equal obsidian
    public static boolean doesBlockCrushWeaklings(int blockIDAbove)
    {
    	if (isWeakBlock(blockIDAbove))
        {
        	return false;
        }
    	
    	return true;
    }
    
    //checks if the block ID matches one that will cause weak blocks to fall
    public static boolean doesBlockTriggerWeaklingFall(int blockIDAbove)
    {

    	if (isWeakBlock(blockIDAbove) || blockIDAbove == Block.sand.blockID
    			|| blockIDAbove == FCBetterThanWolves.fcBlockSlabSandAndGravel.blockID
    			|| blockIDAbove == Block.gravel.blockID
    			|| blockIDAbove == FCBetterThanWolves.fcBlockDirtLoose.blockID
    			|| blockIDAbove == FCBetterThanWolves.fcBlockDirtLooseSlab.blockID
    			)
        {
        	return false;
        }
    	
    	return true;
    }
    
//    //AARON's compiled list of falling blocks to be used as pitfall bait
//    public static boolean isFallingBlock(int blockID)
//    {
//    	if (blockID == SuperBTWDefinitions.strawThatchSlab.blockID || 
//        		blockID == SuperBTWDefinitions.strawThatch.blockID ||
//        		blockID == SuperBTWDefinitions.reedThatchSlab.blockID ||
//        		blockID == SuperBTWDefinitions.reedThatch.blockID ||
//        		blockID == SuperBTWDefinitions.stickBundleLooseSlab.blockID ||
//        		blockID == SuperBTWDefinitions.stickBundleLoose.blockID
//        	)
//    	{
//    		return true;
//    	}
//    	
//    	return false;
//    }

   //NOT USED right now. Will be used for water bucket crafting w/ water source detection
	public static boolean isWaterSourceBlock(int blockID)
	{

		if (blockID == Block.waterStill.blockID
				|| blockID == Block.waterMoving.blockID
				|| blockID == Block.cauldron.blockID)
		{
		    //TESTER VVV
		    System.out.println("Found water source block nearby!");
			return true;
		}
		
		return false;
	}

	
}
