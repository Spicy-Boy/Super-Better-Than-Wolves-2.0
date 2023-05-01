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
		AddRecipe(new ItemStack(FCBetterThanWolves.fcBlockWorkbench, 1), new Object[] // ITS BACK BABY
		{ "###", "#X#", "###", '#', Block.planks, 'X', Item.ingotIron });
		
		
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
	}

	public static void addToolRecipes() {
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
		
		AddStokedCrucibleRecipe(new ItemStack(SuperBTWDefinitions.rib, 1),
				new ItemStack[] { new ItemStack(SuperBTWDefinitions.cowRib) });
		// this.addRecipe(new ItemStack(Block.railPowered, 6), new Object[] {"X X",
		// "X#X", "XRX", 'X', Item.ingotGold, 'R', Item.redstone, '#', Item.stick});

		AddRecipe(new ItemStack(Block.railPowered, 8),
				new Object[] { "IRI", "XYX", "IRI", Character.valueOf('X'), Item.ingotGold, Character.valueOf('I'),
						Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('Y'), Item.stick, });

		
		// debug
		// FCRecipes.AddShapelessRecipe(new ItemStack(SuperBTWDefinitions.branchBlock,
		// 1), new ItemStack [] {new ItemStack(Item.diamond)} );

		// FCRecipes.AddShapelessRecipe(new
		// ItemStack(FCBetterThanWolves.fcItemArmorWoolBoots, 1), new ItemStack [] {new
		// ItemStack(Item.diamond)} );

	}

}
