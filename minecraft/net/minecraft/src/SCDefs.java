package net.minecraft.src;

public class SCDefs {
	
    public static final int m_iFilterable_NoProperties = 0;
    public static final int m_iFilterable_SolidBlock = 1;
    public static final int m_iFilterable_Small = 2;
    public static final int m_iFilterable_Narrow = 4;
    public static final int m_iFilterable_Fine = 8;
    public static final int m_iFilterable_Thin = 16;
	
	// --- BLOCK ID's --- //
	// SC FROM 2500 - 2999

    //Pumpkins
    public static int    
        id_pumpkinStem = 2640,
        id_pumpkinVine = 2641,
        id_pumpkinVineDead = 2642,        
        id_pumpkinVineFlowering = 2643,
        id_pumpkinOrange = 2645,
        id_pumpkinGreen = 2646,
        id_pumpkinYellow = 2647,
        id_pumpkinWhite = 2648,
        id_pumpkinHarvested = 2649,
        id_pumpkinCarved = 2650,
        id_pumpkinJack = 2651,
        id_pumpkinPossessed = 2652,
        id_pumpkinStemDead = 2653,
        
    	id_pumpkinOrangeAsleep = 2654;
    
    //Melons
    public static int    
        id_melonStem = 2660,
        id_melonVine = 2661,
        id_melonVineDead = 2662,
        id_melonVineFlowering = 2663,    
        id_melonWater = 2664,
        id_melonCanary = 2665,
        id_melonHoneydew = 2666,
        id_melonCantaloupe = 2667,
        id_melonHarvested = 2668,
        id_melonCanaryHarvested = 2669;
    
    //AARON'S CUSTOM stems and flowers etc.
    public static int 
    	id_pumpkinStemOrange = 2670,
    	id_pumpkinVineFloweringOrange = 2671,
    	id_pumpkinVineOrange = 2672;
	
	
	// SC ENDS AT 2999

	// --- ITEM ID's --- //
	// SC STARTS AT 31000
	
	//Pumpkin & Melon
	private static int		
		id_pumpkinSeeds = 31010, //unused
		id_melonSeeds = 31011, //unused
		id_melonWaterSlice = 31012,
		id_melonCanarySlice = 31013,
		id_melonHoneydewSlice = 31014,
		id_melonCantaloupeSlice = 31015;
		//AARON ADDED
//		id_orangePumpkinSeeds = 31016,
//		id_greenPumpkinSeeds = 31017,
//		id_yellowPumpkinSeeds = 31018,
//		id_whitePumpkinSeeds = 31019,
//		
//		id_waterMelonSeeds = 31020,
//		id_canaryMelonSeeds = 31021,
//		id_HoneydewMelonSeeds = 31022,
//		id_cantaloupeMelonSeeds = 31023,
//		
//		id_foulSeeds = 31024;
		
		

	private static int
		id_pumpkinSlice = 31016,
		id_pumpkinSliceRoasted = 31017,
		id_pumpkinSliceBoiled = 31018;
		

	// ENDS 32000
	
	// --- Block & Item --- //

	//Pumpkin & Melon
	public static Block pumpkinOrange, pumpkinGreen, pumpkinYellow, pumpkinWhite,
						pumpkinPossessed,
						pumpkinHarvested,
						pumpkinCarved,
						pumpkinJack,
						SCpumpkinStem, pumpkinStemOrange, pumpkinVineFloweringOrange, pumpkinVineOrange,
						pumpkinVineDead, pumpkinVine, 
						pumpkinVineFlowering, pumpkinStemDead,
						
						pumpkinOrangeAsleep;
	
	public static Block melonWater, melonCanary, melonHoneydew, melonCantaloupe,
						melonHarvested, melonCanaryHarvested,
						SCmelonStem,
						melonVine, melonVineSleeping,
						melonVineFlowering, melonVineFloweringSleeping;
	
	public static Item melonWaterSlice, melonCanarySlice, melonHoneydewSlice, melonCantaloupeSlice, pumpkinSlice,
	pumpkinSliceRoasted, pumpkinSliceBoiled, 
	
	orangePumpkinSeeds, greenPumpkinSeeds;
	
	//Gourd
	public static Block gourdStem,
						gourdVine,
						gourdVineFlowering;
		

	public static void addDefinitions() {
		
		addPumpkinDefs();
	}
	

	private static void addPumpkinDefs() {
		
		
		
		//Pumpkins
		//Growing
		pumpkinOrange = new SCBlockPumpkinGrowingOrange(id_pumpkinOrange, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinOrange.blockID] = new ItemBlock(pumpkinOrange.blockID - 256);
		
		pumpkinOrangeAsleep = new SCBlockPumpkinGrowingOrangeAsleep(id_pumpkinOrangeAsleep, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinOrangeAsleep.blockID] = new ItemBlock(pumpkinOrangeAsleep.blockID - 256);
	
		
		pumpkinGreen = new SCBlockPumpkinGrowingGreen(id_pumpkinGreen, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinGreen.blockID] = new ItemBlock(pumpkinGreen.blockID - 256);
		
		pumpkinYellow = new SCBlockPumpkinGrowingYellow(id_pumpkinYellow, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinYellow.blockID] = new ItemBlock(pumpkinYellow.blockID - 256);
		
		pumpkinWhite = new SCBlockPumpkinGrowingWhite(id_pumpkinWhite, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinWhite.blockID] = new ItemBlock(pumpkinWhite.blockID - 256);
		
		//Harvested
		pumpkinHarvested = new SCBlockPumpkinHarvested(id_pumpkinHarvested);
		Item.itemsList[pumpkinHarvested.blockID] = new SCItemBlockPumpkinHarvested(pumpkinHarvested.blockID - 256, pumpkinHarvested, new String[] {"orange_0", "orange_1", "orange_2", "orange_3", 
																																			"green_0", "green_1", "green_2", "green_3",
																																			"yellow_0", "yellow_1", "yellow_2", "yellow_3",
																																			"white_0", "white_1", "white_2", "white_3"
																																			}).setMaxStackSize(16);
		
		
		//Carved
		pumpkinCarved = new SCBlockPumpkinCarved(id_pumpkinCarved);
		Item.itemsList[pumpkinCarved.blockID] = new ItemMultiTextureTile(pumpkinCarved.blockID - 256, pumpkinCarved, new String[] {"orange", "green", "yellow", "white"});
		
		pumpkinJack = new SCBlockPumpkinJack(id_pumpkinJack);
		Item.itemsList[pumpkinJack.blockID] = new ItemMultiTextureTile(pumpkinJack.blockID - 256, pumpkinJack, new String[] {"orange", "green", "yellow", "white"});
		
		//Vine
		pumpkinVine = new SCBlockGourdVine(id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinStem, id_pumpkinVineDead, "SCBlockPumpkinVine_", "SCBlockPumpkinVineConnector_");
		Item.itemsList[pumpkinVine.blockID] = new ItemBlock(pumpkinVine.blockID - 256);
		
		pumpkinVineDead = new SCBlockGourdVineDead(id_pumpkinVineDead, id_pumpkinVineFlowering, id_pumpkinStem);
		Item.itemsList[pumpkinVineDead.blockID] = new ItemBlock(pumpkinVineDead.blockID - 256);
		
		//Flower
		pumpkinVineFlowering = new SCBlockPumpkinVineFlowering(id_pumpkinVineFlowering, id_pumpkinVine, id_pumpkinStem, pumpkinOrange, pumpkinGreen, pumpkinYellow, pumpkinWhite, id_pumpkinVineDead);
		Item.itemsList[pumpkinVineFlowering.blockID] = new ItemBlock(pumpkinVineFlowering.blockID - 256);
		
		//Stem
		pumpkinStemDead = new SCBlockGourdStemDead(id_pumpkinStemDead);
		Item.itemsList[pumpkinStemDead.blockID] = new ItemBlock(pumpkinStemDead.blockID - 256);
		
		SCpumpkinStem = new SCBlockGourdStem(id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, pumpkinStemDead).setCanBePossessed(false);
		Item.itemsList[SCpumpkinStem.blockID] = new ItemBlock(SCpumpkinStem.blockID - 256);
		
		//AARON'S ATTEMPT at creating pumpkin specific vines!
		
		pumpkinVineFloweringOrange = new SCBlockPumpkinVineFloweringOrange(id_pumpkinVineFloweringOrange, id_pumpkinVineOrange, id_pumpkinStemOrange, pumpkinOrange, pumpkinGreen, pumpkinYellow, pumpkinWhite, id_pumpkinVineDead);
		Item.itemsList[pumpkinVineFloweringOrange.blockID] = new ItemBlock(pumpkinVineFloweringOrange.blockID - 256);
		
		pumpkinVineOrange = new SCBlockGourdVine(id_pumpkinVineOrange, id_pumpkinVineFloweringOrange, id_pumpkinStemOrange, id_pumpkinVineDead, "SCBlockPumpkinVine_", "SCBlockPumpkinVineConnector_");
		Item.itemsList[pumpkinVineOrange.blockID] = new ItemBlock(pumpkinVineOrange.blockID - 256);
		
		pumpkinStemOrange = new SCBlockGourdStem(id_pumpkinStemOrange, id_pumpkinVineOrange, id_pumpkinVineFloweringOrange, pumpkinStemDead).setCanBePossessed(false);
		Item.itemsList[pumpkinStemOrange.blockID] = new ItemBlock(pumpkinStemOrange.blockID - 256);
		
		
		
		//Items
		//pumpkinSeeds = new FCItemSeedFood( id_pumpkinSeeds - 256, 1, 0F, pumpkinStem.blockID).setCreativeTab(CreativeTabs.tabDecorations).setUnlocalizedName( "SCSeedsPumpkin" );
		//The code below makes the vanilla pumpkin seed place a SC gourd.
		//Item.itemsList[Item.pumpkinSeeds.itemID] = new FCItemSeedFood( Item.pumpkinSeeds.itemID - 256, 1, 0F, SCpumpkinStem.blockID).setUnlocalizedName( "seeds_pumpkin" ).SetAsBasicChickenFood();
		//orangePumpkinSeeds = new FCItemSeedFood( id_orangePumpkinSeeds - 256, 1, 0F, SCpumpkinStem.blockID).setCreativeTab(CreativeTabs.tabFood).SetAsBasicChickenFood().SetBellowsBlowDistance( 2 ).SetFilterableProperties( m_iFilterable_Fine ).setMaxStackSize(14).setUnlocalizedName( "SCItemSeedsOrangePumpkin" );
		//orangePumpkinSeeds = new SCItemOrangePumpkinSeeds( id_orangePumpkinSeeds - 256, SCpumpkinStem.blockID);
		
		//Melon
		
		//Growing (Types: Watermelon (Green/Red),  Canary melon (Yellow/White), Honeydew (Light Green), Cantaloupe (Green/Orange)
		melonWater = new SCBlockMelonWaterGrowing(id_melonWater, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonHarvested);
		Item.itemsList[melonWater.blockID] = new ItemBlock(melonWater.blockID - 256);
		
		melonCanary = new SCBlockMelonCanaryGrowing(id_melonCanary, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonCanaryHarvested);
		Item.itemsList[melonCanary.blockID] = new ItemBlock(melonCanary.blockID - 256);
		
		melonHoneydew = new SCBlockMelonHoneydewGrowing(id_melonHoneydew, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonHarvested);
		Item.itemsList[melonHoneydew.blockID] = new ItemBlock(melonHoneydew.blockID - 256);
		
		melonCantaloupe = new SCBlockMelonCantaloupeGrowing(id_melonCantaloupe, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonHarvested);
		Item.itemsList[melonCantaloupe.blockID] = new ItemBlock(melonCantaloupe.blockID - 256);
		
		//Harvested
		melonHarvested = new SCBlockMelonHarvested(id_melonHarvested);
		Item.itemsList[melonHarvested.blockID] = new ItemMultiTextureTile(melonHarvested.blockID - 256, melonHarvested, new String[] {"water_0", "water_1", "water_2", "water_3", //Water
																																	  "honeydew_0", "honeydew_1", "honeydew_2", "honeydew_3", //Honeydew
																																	  "cantaloupe_0", "cantaloupe_1", "cantaloupe_2", "cantaloupe_3" //Cantaloupe
																																	  }).setMaxStackSize(16);
		
		melonCanaryHarvested = new SCBlockMelonCanaryHarvested(id_melonCanaryHarvested);
		Item.itemsList[melonCanaryHarvested.blockID] = new ItemMultiTextureTile(melonCanaryHarvested.blockID - 256, melonCanaryHarvested, new String[] {"canary_0", "", "", "",
																																						"canary_1", "", "", "",
																																						"canary_2", "", "", "",
																																						"canary_3", "", "", ""
																																						}).setMaxStackSize(16);
		
		//Vine
		melonVine = new SCBlockGourdVine(id_melonVine, id_melonVineFlowering, id_melonStem, id_pumpkinVineDead, "SCBlockPumpkinVine_", "SCBlockPumpkinVineConnector_");
		Item.itemsList[melonVine.blockID] = new ItemBlock(melonVine.blockID - 256);
		
		//Flower
		melonVineFlowering = new SCBlockMelonVineFlowering(id_melonVineFlowering, id_melonVine, id_melonStem, melonWater, melonHoneydew, melonCantaloupe, melonCanary, id_pumpkinVineDead);
		Item.itemsList[melonVineFlowering.blockID] = new ItemBlock(melonVineFlowering.blockID - 256);
		
		//Stem
		SCmelonStem = new SCBlockGourdStem(id_melonStem, id_melonVine, id_melonVineFlowering, pumpkinStemDead).setCanBePossessed(false);
		Item.itemsList[SCmelonStem.blockID] = new ItemBlock(SCmelonStem.blockID - 256);
		
		//Items
		//melonSeeds = ( new FCItemSeeds(id_melonSeeds , melonStem.blockID).setCreativeTab(CreativeTabs.tabDecorations).SetAsBasicChickenFood().setUnlocalizedName( "SCSeedsMelon" ) );
		//Below makes vanilla seeds place SC gourd. I am changing that to allow unique seeds for each variety!
		//Item.itemsList[Item.melonSeeds.itemID] =  new FCItemSeeds(Item.melonSeeds.itemID - 256 , SCmelonStem.blockID).SetAsBasicChickenFood().setUnlocalizedName( "seeds_melon" );
		
		
		melonWaterSlice = new SCItemMelonSlice( id_melonWaterSlice - 256, "SCItemMelonSlice");
	    melonCanarySlice = new SCItemMelonSlice( id_melonCanarySlice - 256, "SCItemMelonYellowSlice");
	    melonHoneydewSlice = new SCItemMelonSlice( id_melonHoneydewSlice - 256, "SCItemMelonWhiteSlice");
	    melonCantaloupeSlice = new SCItemMelonSlice( id_melonCantaloupeSlice - 256, "SCItemMelonGreenSlice");
	    
	    pumpkinSlice = new Item(id_pumpkinSlice - 256).setMaxStackSize(16).setCreativeTab(CreativeTabs.tabFood).setUnlocalizedName("SCItemPumpkinSlice");  
	    pumpkinSliceRoasted = new FCItemFood(id_pumpkinSliceRoasted - 256, 1, 0, false, "SCItemPumpkinSlice_cooked").setCreativeTab(CreativeTabs.tabFood);
	    pumpkinSliceBoiled = new FCItemFood(id_pumpkinSliceBoiled - 256, 1, 0, false, "SCItemPumpkinSlice_boiled").setCreativeTab(CreativeTabs.tabFood);

	}
	
}
