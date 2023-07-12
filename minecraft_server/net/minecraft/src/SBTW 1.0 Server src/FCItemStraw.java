// FCMOD

package net.minecraft.src;

public class FCItemStraw extends Item
{
    public FCItemStraw( int iItemID )
    {
    	super( iItemID );
    	
    	SetBuoyant();
        SetIncineratedInCrucible();
		SetBellowsBlowDistance( 2 );
    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING );
    	SetFilterableProperties( m_iFilterable_Narrow );
        
    	//AARON CHANGED this value so a cow can survive on ~10 straw a day 
    	//(obviously, should be supplemented with something else)
    	SetHerbivoreFoodValue( EntityAnimal.m_iBaseGrazeFoodValue * 6);
    	
    	setUnlocalizedName( "fcItemStraw" );
    	
    	setCreativeTab( CreativeTabs.tabMaterials );    	
    }
    
    //------------- Class Specific Methods ------------//
    
	//------------ Client Side Functionality ----------//
}
