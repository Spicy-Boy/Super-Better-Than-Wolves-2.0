package net.minecraft.src;

public class SCRecipes {
	public static final SCRecipes instance = new SCRecipes();
	
	private static final int m_iIgnoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;
		
	public static void addRecipes() {

		addGourdRecipes();
	}

	private static void addGourdRecipes()
	{
		int farmer = FCEntityVillager.professionIDFarmer;
		
		//TEMPORARILY DISABLED the new gourd trades from farmers :p
		
//		FCEntityVillager.removeTradeToBuy(farmer, Block.melon.blockID, 0);
//		FCEntityVillager.removeTradeToBuy(farmer, FCBetterThanWolves.fcBlockPumpkinFresh.blockID, 0);
		
		//AARON updated the counts to require fewer gourds... since growing large volumes is a lot harder! //prev, 8-10 or 10-16
//		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.melonHarvested.blockID, 3, 3, 8, 1F, 3);
//		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.pumpkinHarvested.blockID, 3, 3, 8, 1F, 3);
		
		//pumpkin pie
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
	    		new ItemStack( Item.sugar ),
	    		new ItemStack( SCDefs.pumpkinHarvested, 1, 3 ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
	    		new ItemStack( Item.sugar ),
	    		new ItemStack( SCDefs.pumpkinHarvested, 1, 7 ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
	    		new ItemStack( Item.sugar ),
	    		new ItemStack( SCDefs.pumpkinHarvested, 1, 11 ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
	    		new ItemStack( Item.sugar ),
	    		new ItemStack( SCDefs.pumpkinHarvested, 1, 15 ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 3 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 3 )
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 7 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 7 )
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 11 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 11 )
			} );
		
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, 15 ), new Object[] {
				new ItemStack( SCDefs.pumpkinHarvested, 1, 15 )
			} );
		
		//Jack'o'Lantern
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pumpkinJack, 1, 3 ), new Object[] {
				new ItemStack( SCDefs.pumpkinCarved, 1, 3 ), 
				new ItemStack( FCBetterThanWolves.fcItemCandle, 1, m_iIgnoreMetadata ) 
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pumpkinJack, 1, 7 ), new Object[] {
				new ItemStack( SCDefs.pumpkinCarved, 1, 7 ), 
				new ItemStack( FCBetterThanWolves.fcItemCandle, 1, m_iIgnoreMetadata ) 
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pumpkinJack, 1, 11 ), new Object[] {
				new ItemStack( SCDefs.pumpkinCarved, 1, 11 ), 
				new ItemStack( FCBetterThanWolves.fcItemCandle, 1, m_iIgnoreMetadata ) 
			} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pumpkinJack, 1, 15 ), new Object[] {
				new ItemStack( SCDefs.pumpkinCarved, 1, 15 ), 
				new ItemStack( FCBetterThanWolves.fcItemCandle, 1, m_iIgnoreMetadata ) 
			} );
		
		//OLD Melon to Slice

		
//		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonHoneydewSlice, 5 ), new Object[] { 
//	            new ItemStack( SCDefs.melonHarvested, 1, 7)
//	        } );
//		//I DONT KNOW WHY THIS DOESNT WORK....
////		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonWaterSlice, 5 ), new Object[] { 
////	            new ItemStack( SCDefs.melonHarvested, 1, 3)
////	        } );
//		
//		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonWaterSlice, 5 ), new Object[] { 
//				new ItemStack( SCDefs.melonHarvested, 1, 3)
//			} );
//		
//		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonCantaloupeSlice, 5 ), new Object[] { 
//	            new ItemStack( SCDefs.melonHarvested, 1, 11 )
//	        } );
//		
//		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.melonCanarySlice, 5 ), new Object[] { 
//	            new ItemStack( SCDefs.melonCanaryHarvested, 1, 12 )
//	        } );
//		
//		//Slice to seeds
//		FCRecipes.AddShapelessRecipe( new ItemStack( Item.melonSeeds, 1 ), new Object[] { 
//	            new ItemStack( SCDefs.melonHoneydewSlice, 1 )
//	        } );
//		
//		FCRecipes.AddShapelessRecipe( new ItemStack( Item.melonSeeds, 1 ), new Object[] { 
//	            new ItemStack( SCDefs.melonCantaloupeSlice, 1 )
//	        } );
//		
//		FCRecipes.AddShapelessRecipe( new ItemStack( Item.melonSeeds, 1 ), new Object[] { 
//	            new ItemStack( SCDefs.melonCanarySlice, 1 )
//	        } );
//		
//		FCRecipes.AddShapelessRecipe( new ItemStack( Item.melonSeeds, 1 ), new Object[] { 
//	            new ItemStack( SCDefs.melonWaterSlice, 1 )
//	        } );
		
		//Moved from SuperBTWRecipes
		FurnaceRecipes.smelting().addSmelting( SCDefs.pumpkinSlice.itemID, new ItemStack( SCDefs.pumpkinSliceRoasted ), 0 );
		//Moved from SuperBTWRecipes
		FCRecipes.AddCauldronRecipe( 
				new ItemStack( SCDefs.pumpkinSliceBoiled, 1 ), 
				new ItemStack[] {
					new ItemStack( SCDefs.pumpkinSlice, 1 ),
			} );
	}

}
