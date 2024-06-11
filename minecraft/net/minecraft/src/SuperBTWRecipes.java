package net.minecraft.src;

public class SuperBTWRecipes extends FCRecipes {
	public static final SuperBTWRecipes instance = new SuperBTWRecipes();

	private SuperBTWRecipes() {
	}

	private static final int m_iIgnoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;

	public static void addRecipes() {
		addCustomRecipeClasses();
		addBlockRecipes();
		addFoodRecipes();
		addToolRecipes();
		addProgressRecipes();
		addBladeRecipes();
		addMiscRecipes();
	}

	public static void addCustomRecipeClasses() {
		CraftingManager.getInstance().getRecipeList().add( new SuperBTWRecipesLeatherCutting() );

		CraftingManager.getInstance().getRecipeList().add(new SuperBTWRecipesRibCarving());

		CraftingManager.getInstance().getRecipeList().add(new SuperBTWRecipesBladeReturn());

		CraftingManager.getInstance().getRecipeList().add( new SuperBTWRecipesPumpkinCutting() );
		
		CraftingManager.getInstance().getRecipeList().add(new SuperBTWRecipesRibCracking());
	}

	public static void addBlockRecipes() {
		
		//alternative platform recipe high efficiency
		AddRecipe( new ItemStack( FCBetterThanWolves.fcPlatform ), new Object[] {
	    		"#X#", 
	    		" # ", 
	    		"#X#", 
	    		Character.valueOf( '#' ), new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, m_iIgnoreMetadata ), 
	    		Character.valueOf( 'X' ), SuperBTWDefinitions.reedThatch 
			} );
		//alternative platform recipe
		AddRecipe( new ItemStack( FCBetterThanWolves.fcPlatform ), new Object[] {
	    		"#X#", 
	    		" # ", 
	    		"#X#", 
	    		Character.valueOf( '#' ), Block.planks, 
	    		Character.valueOf( 'X' ), SuperBTWDefinitions.reedThatch 
			} );
		
		//thatch recipes (shapeless with branch/stick variants)
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatchSlab  ), new Object[] {
	    		new ItemStack( SuperBTWDefinitions.branch ),
	    		new ItemStack( SuperBTWDefinitions.branch ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatchSlab ), new Object[] {
	    		new ItemStack( Item.stick ),
	    		new ItemStack( SuperBTWDefinitions.branch ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatchSlab ), new Object[] {
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatch  ), new Object[] {
	    		new ItemStack( SuperBTWDefinitions.reedThatchSlab ),
	    		new ItemStack( SuperBTWDefinitions.reedThatchSlab )
			} );
		
		AddRecipe( new ItemStack( SuperBTWDefinitions.reedThatchSlab, 4 ), new Object[] {
	    		"##", 
	    		Character.valueOf( '#' ), new ItemStack( SuperBTWDefinitions.reedThatch )
			} );
		AddRecipe( new ItemStack( SuperBTWDefinitions.strawThatchSlab, 4 ), new Object[] {
	    		"##", 
	    		Character.valueOf( '#' ), new ItemStack( SuperBTWDefinitions.strawThatch )
			} );
		
		//break down break down
		AddShapelessRecipe( new ItemStack( Item.reed, 2 ), new Object[] {
	    		new ItemStack( SuperBTWDefinitions.reedThatchSlab  ),
			} );
		AddShapelessRecipe( new ItemStack( Item.reed, 4 ), new Object[] {
	    		new ItemStack( SuperBTWDefinitions.reedThatch  ),
			} );
		
		//straw thatch recipes 
		//thatch recipes (shapeless with branch/stick variants)
				AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatchSlab  ), new Object[] {
			    		new ItemStack( SuperBTWDefinitions.branch ),
			    		new ItemStack( SuperBTWDefinitions.branch ),
			    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
			    		new ItemStack( FCBetterThanWolves.fcItemStraw )
					} );
				AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatchSlab ), new Object[] {
			    		new ItemStack( Item.stick ),
			    		new ItemStack( SuperBTWDefinitions.branch ),
			    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
			    		new ItemStack( FCBetterThanWolves.fcItemStraw )
					} );
				AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatchSlab ), new Object[] {
						new ItemStack( Item.stick ),
						new ItemStack( Item.stick ),
			    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
			    		new ItemStack( FCBetterThanWolves.fcItemStraw )
					} );
				AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatch  ), new Object[] {
			    		new ItemStack( SuperBTWDefinitions.strawThatchSlab ),
			    		new ItemStack( SuperBTWDefinitions.strawThatchSlab )
					} );
				//break down break down straw
				AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemStraw, 2 ), new Object[] {
			    		new ItemStack( SuperBTWDefinitions.strawThatchSlab  ),
					} );
				AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemStraw, 4 ), new Object[] {
			    		new ItemStack( SuperBTWDefinitions.strawThatch  ),
					} );
		
		
		AddRecipe(
				new ItemStack(SuperBTWDefinitions.ghBlockGloryhole),
				new Object[] {
					"IGI", "BPB", "BVB",
					'I', new ItemStack(Item.ingotIron),
					'G', new ItemStack(FCBetterThanWolves.fcItemGear),
					'B', new ItemStack(FCBetterThanWolves.fcBlockBrickLoose),
					'P', new ItemStack(Block.obsidian),
					'V', new ItemStack(Block.fenceIron)
				}
			);
		
		//tombstone recipe from chiseling a stone slab
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.tombstonePlacer, 1 ), new Object[] 
		{	    		
	    		new ItemStack( FCBetterThanWolves.fcItemChiselIron, 1, m_iIgnoreMetadata ),
	    		new ItemStack( Item.itemsList[FCBetterThanWolves.fcBlockSmoothStoneSidingAndCorner.blockID], 1, 0 )
		} );
		
		AddRecipe(new ItemStack(FCBetterThanWolves.fcBlockWorkbench, 1), new Object[] // ITS BACK BABY
		{ "###", "#X#", "###", '#', Block.planks, 'X', Item.ingotIron });
		
		AddRecipe(new ItemStack(FCBetterThanWolves.fcBlockWorkbench, 1), new Object[]
		{ "###", "#X#", "###",
				Character.valueOf('#'), new ItemStack (FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, m_iIgnoreMetadata),
				Character.valueOf('X'), Item.ingotIron });
		
		
		AddRecipe(new ItemStack( SuperBTWDefinitions.meatCube, 1 ),
				new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), FCBetterThanWolves.fcItemRawMysteryMeat});

		
		AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemRawMysteryMeat, 9 ), new Object[] {	    		
	    		new ItemStack( SuperBTWDefinitions.meatCube),
			} );
		
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.wetMudBrickItem, 4 ), new Object[] {
	    		new ItemStack( Item.clay ),
	    		new ItemStack( FCBetterThanWolves.fcBlockDirtLoose ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( Item.bucketWater ),
			} );
		
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.mortarBucket, 1 ), new Object[] {
	    		new ItemStack( Item.clay ),
	    		new ItemStack( Block.sand ),
	    		new ItemStack( Item.bucketWater ),
			} );
		//empty the bucket
		AddShapelessRecipe( new ItemStack( Item.bucketEmpty, 1), new Object[] {
	    		new ItemStack( SuperBTWDefinitions.mortarBucket, 1,  m_iIgnoreMetadata  ),
			} );
		
		
		//DECO!
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.unfiredTerracotta, 1), new Object[] {
	    		new ItemStack( Item.clay ),
	    		new ItemStack( Block.sand )
			} );
		//staining terracotta
		for (int i = 0; i < 32; i++)
		{
			FCRecipes.AddCauldronRecipe(new ItemStack(SuperBTWDefinitions.stainedTerracotta, 16, i % 16), new ItemStack[] { new ItemStack(SuperBTWDefinitions.terracotta, 16), new ItemStack(Item.dyePowder, 1, i)});
		}
		// terracotta slabs
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.terracottaSlabDefault, 6), new Object[] {"###", '#', new ItemStack(SuperBTWDefinitions.terracotta, 1)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.terracotta, 1), new Object[] {"#", "#", '#', new ItemStack(SuperBTWDefinitions.terracottaSlabDefault, 1)});
		for(int i = 0; i < 16; i++)
		{
			FCRecipes.AddRecipe(new ItemStack(i < 8 ? SuperBTWDefinitions.terracottaSlab : SuperBTWDefinitions.terracottaSlab2, 6, i % 8), new Object[] {"###", '#', new ItemStack(SuperBTWDefinitions.stainedTerracotta, 1, i)});
			FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.stainedTerracotta, 1, i), new Object[] {"#", "#", '#', new ItemStack(i < 8 ? SuperBTWDefinitions.terracottaSlab : SuperBTWDefinitions.terracottaSlab2, 1, i % 8)});
		}
		//white stone bricks
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.whiteStoneBrick,4), new Object[]{"XX","XX",'X',new ItemStack(FCBetterThanWolves.fcAestheticOpaque,1,9)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.whiteStoneBrickStairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(SuperBTWDefinitions.whiteStoneBrick)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.whiteStoneBrickSlab, 6), new Object[] {"###", '#', new ItemStack(SuperBTWDefinitions.whiteStoneBrick)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.whiteStoneBrick, 1), new Object[] {"X", "X", 'X', new ItemStack(SuperBTWDefinitions.whiteStoneBrickSlab, 1)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.whiteStoneBrick,4,3), new Object[]{"XX","XX",'X',new ItemStack(SuperBTWDefinitions.whiteStoneBrick)}); //chiseled

		
		//sandstone bricks
//		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.sandstoneBrickLarge, 4), new Object[]{"XX","XX",'X',new ItemStack(Block.sandStone, 2)});
		//sandstone bricks acquired with chisel splitting
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.sandstoneBrickLarge, 1 ), new Object[] {	    		
	    		new ItemStack( FCBetterThanWolves.fcItemChiselIron, 1, m_iIgnoreMetadata ),
	    		new ItemStack( Block.sandStone, 1 )
			} );
		
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.sandstoneBrickLargeStairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(SuperBTWDefinitions.sandstoneBrickLarge)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.sandstoneBrickLargeSlab, 6), new Object[] {"###", '#', new ItemStack(SuperBTWDefinitions.sandstoneBrickLarge)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.sandstoneBrickLarge, 1), new Object[] {"X", "X", 'X', new ItemStack(SuperBTWDefinitions.sandstoneBrickLargeSlab, 1)});
		FCRecipes.AddRecipe(new ItemStack(SuperBTWDefinitions.sandstoneBrickLarge,4,3), new Object[]{"XX","XX",'X',new ItemStack(SuperBTWDefinitions.sandstoneBrickLarge)});
		

		//newer ce functionality vv
//		FCRecipes.addKilnRecipe(new ItemStack(SuperBTWDefinitions.terracotta),
//				SuperBTWDefinitions.unfiredTerracotta,
//				FCRecipes.cookTimeMultiplierClay);
	}

	public static void addToolRecipes() {
//		FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.trowel, 1), new ItemStack[] {
//				new ItemStack(FCBetterThanWolves.fcItemNuggetIron), new ItemStack(FCBetterThanWolves.fcItemNuggetIron), new ItemStack(FCBetterThanWolves.fcItemNuggetIron)});
		AddRecipe(new ItemStack(SuperBTWDefinitions.trowel, 1), new Object[]
		{ "##", "#X", '#', FCBetterThanWolves.fcItemNuggetIron, 'X', Item.stick });
		
		
		FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.bonePickaxe, 1), new ItemStack[] {
				new ItemStack(SuperBTWDefinitions.rib), new ItemStack(Item.silk), new ItemStack(Item.stick) });
		
		FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.bonePickaxe, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.rib),
						new ItemStack(FCBetterThanWolves.fcItemHempFibers), new ItemStack(Item.stick) });

		// hoes hoes hoes
		AddRecipe(new ItemStack(SuperBTWDefinitions.hoeStoneNew, 1),
				new Object[] { "XI", "SI", " I", Character.valueOf('X'), FCBetterThanWolves.fcItemStone,
						Character.valueOf('I'), Item.stick, Character.valueOf('S'), Item.silk, });
		AddRecipe(new ItemStack(SuperBTWDefinitions.hoeStoneNew, 1),
				new Object[] { "IX", "IS", "I ", Character.valueOf('X'), FCBetterThanWolves.fcItemStone,
						Character.valueOf('I'), Item.stick, Character.valueOf('S'), Item.silk, });
		AddRecipe(new ItemStack(SuperBTWDefinitions.hoeStoneNew, 1),
				new Object[] { "XI ", " IS", " I ", Character.valueOf('X'), FCBetterThanWolves.fcItemStone,
						Character.valueOf('I'), Item.stick, Character.valueOf('S'), Item.silk, });
		AddRecipe(new ItemStack(SuperBTWDefinitions.hoeStoneNew, 1),
				new Object[] { " IX", "SI ", " I ", Character.valueOf('X'), FCBetterThanWolves.fcItemStone,
						Character.valueOf('I'), Item.stick, Character.valueOf('S'), Item.silk, });
		AddRecipe(new ItemStack(SuperBTWDefinitions.hoeStoneNew, 1),
				new Object[] { "XIS", " I ", " I ", Character.valueOf('X'), FCBetterThanWolves.fcItemStone,
						Character.valueOf('I'), Item.stick, Character.valueOf('S'), Item.silk, });

		FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.deathClub, 1), new ItemStack[] {
				new ItemStack(SuperBTWDefinitions.rib), new ItemStack(SuperBTWDefinitions.rib), new ItemStack(Item.stick), new ItemStack(Item.stick) });
	
		AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.fcItemBedroll), new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemPadding, 1),
				new ItemStack(FCBetterThanWolves.fcItemPadding, 1)});
		
		AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.fcItemBedroll), new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemWoolKnit, 1, m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemWoolKnit, 1, m_iIgnoreMetadata),
				new ItemStack(Item.silk)});
		
		AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.fcItemBedroll), new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemWoolKnit, 1, m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemWoolKnit, 1, m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemWool, 1,  m_iIgnoreMetadata)});
		
		AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.fcItemBedroll), new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemWoolKnit, 1, m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemWoolKnit, 1, m_iIgnoreMetadata),
				new ItemStack(Item.feather, 1),
				new ItemStack(Item.feather, 1)});
	}

	public static void addFoodRecipes() {
		//treat w/ mutton
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" X ", 
	    		"O#O", 
	    		" X ", 
	    		Character.valueOf( '#' ), FCBetterThanWolves.fcItemMuttonCooked, 
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" O ", 
	    		"X#X", 
	    		" O ", 
	    		Character.valueOf( '#' ), FCBetterThanWolves.fcItemMuttonCooked, 
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		//treat w/ pork
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" X ", 
	    		"O#O", 
	    		" X ", 
	    		Character.valueOf( '#' ), Item.porkCooked, 
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" O ", 
	    		"X#X", 
	    		" O ", 
	    		Character.valueOf( '#' ), Item.porkCooked,  
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		//treat w/ beef
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" X ", 
	    		"O#O", 
	    		" X ", 
	    		Character.valueOf( '#' ), Item.beefCooked, 
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" O ", 
	    		"X#X", 
	    		" O ", 
	    		Character.valueOf( '#' ), Item.beefCooked,  
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour,
	    		Character.valueOf( 'X' ), Item.sugar 
			} );
		//treat w/ chicken
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" X ", 
	    		"O#O", 
	    		" X ", 
	    		Character.valueOf( '#' ), Item.chickenCooked, 
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" O ", 
	    		"X#X", 
	    		" O ", 
	    		Character.valueOf( '#' ), Item.chickenCooked,  
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		//treat w/ mystery meat
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" X ", 
	    		"O#O", 
	    		" X ", 
	    		Character.valueOf( '#' ), FCBetterThanWolves.fcItemCookedMysteryMeat, 
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		AddRecipe( new ItemStack( SuperBTWDefinitions.treat ), new Object[] {
	    		" O ", 
	    		"X#X", 
	    		" O ", 
	    		Character.valueOf( '#' ), FCBetterThanWolves.fcItemCookedMysteryMeat,   
	    		Character.valueOf( 'O' ), FCBetterThanWolves.fcItemFlour, Character.valueOf( 'X' ), Item.sugar 
			} );
		
		FurnaceRecipes.smelting().addSmelting(SuperBTWDefinitions.cowRib.itemID,
				new ItemStack(SuperBTWDefinitions.cookedCowRib), 0);

		// Moved to SCRecipes
//		FurnaceRecipes.smelting().addSmelting( SCDefs.pumpkinSlice.itemID, new ItemStack( SCDefs.pumpkinSliceRoasted ), 0 );

		// Moved to SCRecipes
//		AddCauldronRecipe( 
//			new ItemStack( SCDefs.pumpkinSliceBoiled, 1 ), 
//			new ItemStack[] {
//				new ItemStack( SCDefs.pumpkinSlice, 1 ),
//		} );

		AddCauldronRecipe(new ItemStack(SuperBTWDefinitions.cookedCowRib, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.cowRib, 1), });
		
		//gourd recipes
		
		//Pumpkins
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 3 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 3 ),
//				new ItemStack( SuperBTWDefinitions.orangePumpkinSeeds, 4),
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 7 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 7),
//				new ItemStack( SuperBTWDefinitions.greenPumpkinSeeds, 5),
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 11 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 11 ),
//				new ItemStack( SuperBTWDefinitions.yellowPumpkinSeeds, 3),
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 15 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 15 ),
//				new ItemStack( SuperBTWDefinitions.whitePumpkinSeeds, 2),
			} );
		
		
		//Melons
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonHoneydewSlice, 3 ), new Object[] { 
	            new ItemStack( SCDefs.melonHarvested, 1, 7)
	        } );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonWaterSlice, 5 ), new Object[] { 
				new ItemStack( SCDefs.melonHarvested, 1, 3)
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonCantaloupeSlice, 3 ), new Object[] { 
	            new ItemStack( SCDefs.melonHarvested, 1, 11 )
	        } );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonCanarySlice, 4 ), new Object[] { 
	            new ItemStack( SCDefs.melonCanaryHarvested, 1, 12 )
	        } );
		
		//Slice to seeds
		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.honeydewMelonSeeds, 1 ), new Object[] { 
	            new ItemStack( SCDefs.melonHoneydewSlice, 1 )
	        } );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.cantaloupeMelonSeeds, 1 ), new Object[] { 
	            new ItemStack( SCDefs.melonCantaloupeSlice, 1 )
	        } );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.canaryMelonSeeds, 1 ), new Object[] { 
	            new ItemStack( SCDefs.melonCanarySlice, 1 )
	        } );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.waterMelonSeeds, 1 ), new Object[] { 
	            new ItemStack( SCDefs.melonWaterSlice, 1 )
	        } );
		
		//Melon slices to seeds
		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.honeydewMelonSeeds, 1 ), new Object[] { 
        new ItemStack( SCDefs.melonHoneydewSlice, 1 )
		} );

		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.cantaloupeMelonSeeds, 1 ), new Object[] { 
        new ItemStack( SCDefs.melonCantaloupeSlice, 1 )
		} );

		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.canaryMelonSeeds, 1 ), new Object[] { 
        new ItemStack( SCDefs.melonCanarySlice, 1 )
		} );

		FCRecipes.AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.waterMelonSeeds, 1 ), new Object[] { 
        new ItemStack( SCDefs.melonWaterSlice, 1 )
		} );
		
		//curing beef rib
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemMeatCured, 2 ), new Object[] { 
	            new ItemStack( SuperBTWDefinitions.cowRib), new ItemStack( FCBetterThanWolves.fcItemNitre), new ItemStack( FCBetterThanWolves.fcItemNitre),
	            new ItemStack( FCBetterThanWolves.fcItemNitre)
	        } );
		
	}

	private static void addProgressRecipes() {

//		AddShapelessRecipe( new ItemStack( Item.silk, 3 ), new Object[] {
//				new ItemStack( SuperBTWDefinitions.bowStringing, 1, m_iIgnoreMetadata ) 
//			} );
		AddShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(Item.silk, 3),
				new Object[] { new ItemStack(SuperBTWDefinitions.bowStringing, 1, m_iIgnoreMetadata) });

		FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.bowStringing, 1, 0),
				new ItemStack[] { new ItemStack(Item.stick), new ItemStack(Item.silk), new ItemStack(Item.silk),
						new ItemStack(Item.silk) });

		FCRecipes.AddShapelessRecipe(
				new ItemStack(SuperBTWDefinitions.flintKnapping, 1,
						/* this number must match the value within GetProgressiveCraftingMaxDamage */10),
				new ItemStack[] { new ItemStack(Item.flint), new ItemStack(FCBetterThanWolves.fcItemStone) });
	}

	private static void addBladeRecipes() {
		AddRecipe(new ItemStack(SuperBTWDefinitions.ironBlade),
				new Object[] { "X", "X", Character.valueOf('X'), FCBetterThanWolves.fcItemNuggetIron });
		AddRecipe(new ItemStack(SuperBTWDefinitions.ironBlade),
				new Object[] { "X", "X", Character.valueOf('X'), FCBetterThanWolves.fcItemNuggetIron });

		AddRecipe(
				new ItemStack(FCBetterThanWolves.fcAestheticVegetation, 1,
						FCBlockAestheticVegetation.m_iSubtypeVineTrap),
				new Object[] { "##", Character.valueOf('#'), Block.vine });

	}

	private static void addMiscRecipes() 
	{
		//AARON added a config for these! If disabled, players cannot craft the super efficient leather armor
		if (SuperBTW.instance.get2x2LeatherEnabled())
		{
			FCRecipes.AddShapelessRecipe(new ItemStack(Item.plateLeather, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemLeatherCut) });
			FCRecipes.AddShapelessRecipe(new ItemStack(Item.legsLeather, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemLeatherCut) });
			FCRecipes.AddShapelessRecipe(new ItemStack(Item.bootsLeather, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemLeatherCut) });
			FCRecipes.AddShapelessRecipe(new ItemStack(Item.helmetLeather, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemLeatherCut) });
			
			FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemArmorTannedChest, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut) });	
			FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemArmorTannedLeggings, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut) });
			FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemArmorTannedBoots, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut),
							new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut) });
			FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemArmorTannedHelm, 1),
					new ItemStack[] { new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut) });
		}

		


		// branch recipes!
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit, 1),
				new ItemStack[] { new ItemStack(Item.coal), new ItemStack(SuperBTWDefinitions.branch) });

		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemFireStarterSticks, 1), new ItemStack[] {
				new ItemStack(SuperBTWDefinitions.branch), new ItemStack(SuperBTWDefinitions.branch) });
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemFireStarterSticks, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch), new ItemStack(Item.stick) });
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemChiselWood, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch) });

		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemFireStarterBow, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch), new ItemStack(SuperBTWDefinitions.branch),
						new ItemStack(Item.silk) });
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemFireStarterBow, 1), new ItemStack[] {
				new ItemStack(SuperBTWDefinitions.branch), new ItemStack(Item.stick), new ItemStack(Item.silk) });

		// all them recipes be like (LMAO!!! CAMPFIRE!!!!)
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcBlockCampfireUnlit, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch), new ItemStack(Item.stick),
						new ItemStack(Item.stick), new ItemStack(Item.stick) });
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcBlockCampfireUnlit, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch), new ItemStack(SuperBTWDefinitions.branch),
						new ItemStack(Item.stick), new ItemStack(Item.stick) });
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcBlockCampfireUnlit, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch), new ItemStack(SuperBTWDefinitions.branch),
						new ItemStack(SuperBTWDefinitions.branch), new ItemStack(Item.stick) });
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcBlockCampfireUnlit, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.branch), new ItemStack(SuperBTWDefinitions.branch),
						new ItemStack(SuperBTWDefinitions.branch), new ItemStack(SuperBTWDefinitions.branch) });
		
		//rib recipes
		AddMillStoneRecipe( new ItemStack( Item.dyePowder, 6, 15 ), new ItemStack( SuperBTWDefinitions.rib, 1) );
		AddMillStoneRecipe( new ItemStack( Item.dyePowder, 2, 15 ), new ItemStack( FCBetterThanWolves.fcItemClubBone, 1) );
		
		//reduce ore to dust recipes
		AddMillStoneRecipe( new ItemStack( FCBetterThanWolves.fcItemPileIronOre, 2), new ItemStack( FCBetterThanWolves.fcItemChunkIronOre, 1) );
		AddMillStoneRecipe( new ItemStack( FCBetterThanWolves.fcItemPileGoldOre, 2), new ItemStack( FCBetterThanWolves.fcItemChunkGoldOre, 1) );
		
		AddStokedCrucibleRecipe(new ItemStack(SuperBTWDefinitions.rib, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.cowRib) });
		// this.addRecipe(new ItemStack(Block.railPowered, 6), new Object[] {"X X",
		// "X#X", "XRX", 'X', Item.ingotGold, 'R', Item.redstone, '#', Item.stick});

		AddRecipe(new ItemStack(Block.railPowered, 8),
				new Object[] { "IRI", "XYX", "IRI", Character.valueOf('X'), Item.ingotGold, Character.valueOf('I'),
						Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('Y'), Item.stick, });

		//full block thatch recipes (sort of cumbersome)
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatch ), new Object[] {
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		//full block thatch recipes with mixed branches
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( Item.stick ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.reedThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed ),
	    		new ItemStack( Item.reed )
			} );
		
		//full block straw thatch recipes (sort of cumbersome)
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatch ), new Object[] {
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw )
			} );
		//full block thatch recipes with mixed branches
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( Item.stick ),
				new ItemStack( Item.stick ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( Item.stick ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw )
			} );
		AddShapelessRecipe( new ItemStack( SuperBTWDefinitions.strawThatch ), new Object[] {
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
				new ItemStack( SuperBTWDefinitions.branch ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw ),
	    		new ItemStack( FCBetterThanWolves.fcItemStraw )
			} );
		
		// debug
		// FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.branchBlock,
		// 1), new ItemStack [] {new ItemStack(Item.diamond)} );

		// FCRecipes.AddShapelessRecipe(new
		// ItemStack(FCBetterThanWolves.fcItemArmorWoolBoots, 1), new ItemStack [] {new
		// ItemStack(Item.diamond)} );

	}

}
