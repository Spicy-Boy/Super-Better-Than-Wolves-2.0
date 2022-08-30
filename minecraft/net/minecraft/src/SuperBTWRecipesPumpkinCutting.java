// FCMOD

package net.minecraft.src;

import java.util.ArrayList;

public class SuperBTWRecipesPumpkinCutting implements IRecipe
{
    public boolean matches( InventoryCrafting craftingInventory, World world )
    {
        ItemStack bladeStack = null;
        ItemStack pumpkinStack = null;

        for ( int iTempSlot = 0; iTempSlot < craftingInventory.getSizeInventory(); ++iTempSlot )
        {
            ItemStack tempStack = craftingInventory.getStackInSlot( iTempSlot );

            if ( tempStack != null )
            {
                if ( isKnife( tempStack ) )
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
                else if ( isPumpkin( tempStack ) )
                {
                    if ( pumpkinStack == null )
                    {
                        pumpkinStack = tempStack;
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

        return bladeStack != null && pumpkinStack != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult( InventoryCrafting craftingInventory )
    {
        ItemStack bladeStack = null;
        ItemStack pumpkinStack = null;
        
        for ( int iTempSlot = 0; iTempSlot < craftingInventory.getSizeInventory(); ++iTempSlot )
        {
            ItemStack tempStack = craftingInventory.getStackInSlot( iTempSlot );

            if ( tempStack != null )
            {
                if ( isKnife( tempStack ) )
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
                else if ( isPumpkin( tempStack ) )
                {
                    if ( pumpkinStack == null )
                    {
                        pumpkinStack = tempStack;                        
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

        if ( pumpkinStack != null && bladeStack != null )
        {
            ItemStack resultStack = null;
            //be careful! The code below will cause a crash if you don't find a way to make axes work too (see method: isKnife)
            //SuperBTWItemBlade knifeItem = (SuperBTWItemBlade)bladeStack.getItem();

            //SOCK: commented out superiour iron knife
            
//	        if ( knifeItem.itemID == SuperBTWDefinitions.flintBlade.itemID ) // flint
//	        {
//	        	resultStack = new ItemStack( SCDefs.pumpkinSlice, 2 );
//	        }
//	        else
//	        {
//	        	resultStack = new ItemStack( SCDefs.pumpkinSlice, 4 );
//	        }
            
            //SOCK: comment the below if the above is uncommented
            
            resultStack = new ItemStack( SCDefs.pumpkinSlice, 2 );
                        
        	return resultStack;
        }
        
    	return null;
    }

    @Override
    public int getRecipeSize()
    {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }

    @Override
    public boolean matches( IRecipe recipe )
    {
    	return false;
    }
    
    @Override
    public boolean HasSecondaryOutput()
    {
    	return true;
    }
    
	//------------- Class Specific Methods ------------//
    
    private boolean isKnife( ItemStack stack )
    {
    	int iItemID = stack.itemID;
    		
    	if ( stack.getItem() instanceof SuperBTWItemBlade || 
    			stack.getItem().itemID == Item.axeIron.itemID ||
    			stack.getItem().itemID == Item.axeDiamond.itemID)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    private boolean isPumpkin( ItemStack stack )
    {
    	int iItemID = stack.itemID;
    		
    	if ( iItemID == SCDefs.pumpkinHarvested.blockID )
    	{
    		return true;
    	}
    	
    	return false;
    }    
}
