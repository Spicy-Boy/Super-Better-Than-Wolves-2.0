package net.minecraft.src;

import java.util.ArrayList;

public class SuperBTWRecipesRibCracking implements IRecipe
{
	
	public static ItemStack blade;

	 public boolean matches( InventoryCrafting craftingInventory, World world )
	    {
	        ItemStack bladeStack = null;
	        ItemStack leatherStack = null;

	        for ( int iTempSlot = 0; iTempSlot < craftingInventory.getSizeInventory(); ++iTempSlot )
	        {
	            ItemStack tempStack = craftingInventory.getStackInSlot( iTempSlot );

	            if ( tempStack != null )
	            {
	                if ( isBlade( tempStack ) && !isLowQualityAxe(tempStack) )
	                {
	                	if ( bladeStack == null )
	                	{
	                		bladeStack = tempStack;
	                	}
	                	else
	                	{
	                		return false;
	                	}
	                }
	                else if ( isLeather( tempStack ) )
	                {
	                    if ( leatherStack == null )
	                    {
	                        leatherStack = tempStack;
	                    }
	                    else
	                    {
	                    	return false;
	                    }
	                }
	                else
	                {
	                	return false;
	                }
	            }
	        }

	        return bladeStack != null && leatherStack != null;
	   }
	 
	 private boolean isBlade( ItemStack stack)
	 {
	    	int iItemID = stack.itemID;
  		
	    	if ( stack.getItem() instanceof FCItemAxe)
	    			//&& ((FCItemAxe) stack.getItem()).toolMaterial.getHarvestLevel() <= 1) //||
	    	{
	    		
	    		return true;
	    	}
	    	
	    	return false;
	 }
	 
		private boolean isLowQualityAxe(ItemStack stack)
		{
			return isBlade(stack) && ((FCItemAxe) stack.getItem()).toolMaterial.getHarvestLevel() <= 1;
		}
	 
	 private boolean isLeather( ItemStack stack )
	 {
	    	int iItemID = stack.itemID;
	    		    		
	    	if ( iItemID == SuperBTWDefinitions.cowRib.itemID )
	    	{
	    		return true;	
	    	}
	    	
	    	return false;
	 }

		public ItemStack getCraftingResult(InventoryCrafting craftingInventory) {
		    {
		        ItemStack bladeStack = null;
		        ItemStack leatherStack = null;
		        
		        for ( int iTempSlot = 0; iTempSlot < craftingInventory.getSizeInventory(); ++iTempSlot )
		        {
		            ItemStack tempStack = craftingInventory.getStackInSlot( iTempSlot );

		            if ( tempStack != null )
		            {
		                if ( isBlade( tempStack ) && !isLowQualityAxe(tempStack))
		                {
		                	if ( bladeStack == null )
		                	{
		                		bladeStack = tempStack;
		                	}
		                	else
		                	{
		                		return null;
		                	}
		                }
		                else if ( isLeather( tempStack ) )
		                {
		                    if ( leatherStack == null )
		                    {
		                        leatherStack = tempStack;                        
		                    }
		                    else
		                    {
		                    	return null;
		                    }
		                }
		                else
		                {
		                	return null;
		                }
		            }
		        }
		        
		        if ( leatherStack != null && bladeStack != null )
		        {
		        	ItemStack resultStack = null;


		            resultStack = new ItemStack(Item.beefRaw, 1); //this value must match!

		            return resultStack;

		        }
		        
		    	return null;
		    }
		}


		public int getRecipeSize() {
			// TODO Auto-generated method stub
			return 2;
		}


		public ItemStack getRecipeOutput() {
			// TODO Auto-generated method stub
			return null;
		}

		//a bonemeal
		public boolean HasSecondaryOutput() {
			// TODO Auto-generated method stub
			return true;
		}


		public boolean matches(IRecipe recipe) {
			// TODO Auto-generated method stub
			return false;
		}

}
