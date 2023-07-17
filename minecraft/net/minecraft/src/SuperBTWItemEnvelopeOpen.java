package net.minecraft.src;

public class SuperBTWItemEnvelopeOpen extends Item 
{
	
	public SuperBTWItemEnvelopeOpen(int iItemID)
	{
		
	super( iItemID );  
    setUnlocalizedName( "SuperBTWItemEnvelopeOpen" );
    SetBuoyant();
    SetBellowsBlowDistance( 3 );
	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING );
	SetFilterableProperties( m_iFilterable_Small | m_iFilterable_Thin );
    setCreativeTab( CreativeTabs.tabMaterials );
    SetIncineratedInCrucible();
	
	}
}
