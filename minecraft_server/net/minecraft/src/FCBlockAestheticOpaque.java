// FCMOD

package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class FCBlockAestheticOpaque extends Block
{
    public final static int m_iSubtypeWicker = 0; // deprecated
    public final static int m_iSubtypeDung = 1; // deprecated
    public final static int m_iSubtypeSteel = 2;
    public final static int m_iSubtypeHellfire = 3;
    public final static int m_iSubtypePadding = 4;
    public final static int m_iSubtypeSoap = 5;
    public final static int m_iSubtypeRope = 6;
    public final static int m_iSubtypeFlint = 7;
    public final static int m_iSubtypeNetherrackWithGrowth = 8;
    public final static int m_iSubtypeWhiteStone = 9;
    public final static int m_iSubtypeWhiteCobble = 10;
    public final static int m_iSubtypeBarrel = 11;
    public final static int m_iSubtypeChoppingBlockDirty = 12;
    public final static int m_iSubtypeChoppingBlockClean = 13;
    public final static int m_iSubtypeEnderBlock = 14;
    public final static int m_iSubtypeBone = 15;
    
    public final static int m_iNumSubtypes = 16;    
    
    private final static float m_fDefaultHardness = 2F;
    
    public FCBlockAestheticOpaque( int iBlockID )
    {
        super( iBlockID, FCBetterThanWolves.fcMaterialMiscellaneous );
        
        setHardness( m_fDefaultHardness ); 
        SetAxesEffectiveOn( true );        
        SetPicksEffectiveOn( true );       
        
        setStepSound( soundStoneFootstep );
        
//		setCreativeTab( CreativeTabs.tabBlock );
		
        setUnlocalizedName( "fcBlockAestheticOpaque" );        
    }

	@Override
	public int damageDropped( int iMetadata )
    {
		if ( iMetadata == m_iSubtypeWicker )
		{
			return 0;
		}
		else if ( iMetadata == m_iSubtypeSteel )
		{
			// the new block type should not have a metadata value
			
			return 0;
		}
		else if ( iMetadata == m_iSubtypeFlint )
		{
			return 0;
		}
		else if ( iMetadata == m_iSubtypeNetherrackWithGrowth )
		{
			return 0;
		}
		else if ( iMetadata == m_iSubtypeWhiteStone )
		{
			return m_iSubtypeWhiteCobble;
		}
		
        return iMetadata; 
    }
    
	@Override
    public int idDropped(int metadata, Random random, int fortuneModifier)
    {
		if (metadata == m_iSubtypeWicker) {
			return FCBetterThanWolves.fcBlockWicker.blockID;
		}
		else if (metadata == m_iSubtypeSteel) {
			// convert to the new steel block
			
			return FCBetterThanWolves.fcSoulforgedSteelBlock.blockID;
		}
		else if (metadata == m_iSubtypeFlint) {
			return Item.flint.itemID;
		}
		else if (metadata == m_iSubtypeNetherrackWithGrowth) {
			return Block.netherrack.blockID;
		}
		
		return blockID;
    }
    
	@Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
		if ( iMetadata == m_iSubtypeFlint )
		{
	        if ( world.isRemote )
	        {
	            return;
	        }
	        
	        int iNumDropped = 9;
	        
	        for(int k1 = 0; k1 < iNumDropped; k1++)
	        {
	            int iItemID = idDropped( iMetadata, world.rand, iFortuneModifier );
	            
	            if ( iItemID > 0 )
	            {
	                dropBlockAsItem_do( world, i, j, k, new ItemStack( iItemID, 1, damageDropped( iMetadata ) ) );
	            }
	        }
		}
		else
		{
			super.dropBlockAsItemWithChance( world, i, j, k, iMetadata, fChance, iFortuneModifier );
		}
    }

	@Override
    protected boolean canSilkHarvest( int iMetadata )
    {
    	int iSubType = iMetadata;

    	if ( iSubType == m_iSubtypeNetherrackWithGrowth || iSubType == m_iSubtypeSteel  )
    	{
    		return false;
    	}
    	
        return true;
    }    
    
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iChangedBlockID )
    {
    	int iSubType = world.getBlockMetadata( i, j, k );

    	if ( iSubType == m_iSubtypeNetherrackWithGrowth )
    	{
    		int iBlockAboveID = world.getBlockId( i, j + 1, k );
    		
    		if ( iBlockAboveID != FCBetterThanWolves.fcBlockBloodMoss.blockID )
    		{
    			// convert back to regular netherrack if we don't have a growth above us
    			
    			world.setBlock( i, j, k, Block.netherrack.blockID );
    		}
    	}
    }
	
	@Override
    public boolean DoesInfiniteBurnToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
    {
    	int iSubType = blockAccess.getBlockMetadata( i, j, k );

    	return iSubType == m_iSubtypeHellfire;
    }
	
	@Override
    public boolean DoesBlockBreakSaw( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );

		if ( iSubtype == m_iSubtypeWicker ||
			iSubtype == m_iSubtypeDung ||
			iSubtype == m_iSubtypePadding ||
			iSubtype == m_iSubtypeSoap ||
			iSubtype == m_iSubtypeRope || 
			iSubtype == m_iSubtypeBarrel ||
			iSubtype == m_iSubtypeChoppingBlockDirty ||
			iSubtype == m_iSubtypeChoppingBlockClean ||
			iSubtype == m_iSubtypeBone )
		{
			return false;
		}
		
		return true;
    }
	
	@Override
    public boolean OnBlockSawed( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );

		if (  iSubtype == m_iSubtypeChoppingBlockDirty ||
			iSubtype == m_iSubtypeChoppingBlockClean )
		{
			return false;
		}
		
		return super.OnBlockSawed( world, i, j, k );
    }
    
	@Override
    public int GetItemIDDroppedOnSaw( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
    	if ( iSubtype == m_iSubtypeWicker )
    	{
    		return FCBetterThanWolves.fcBlockWickerSlab.blockID;
    	}
    	else if ( iSubtype == m_iSubtypeBone )
    	{
    		return Item.bone.itemID;
    	}

    	return super.GetItemIDDroppedOnSaw( world, i, j, k );
    }
	
	@Override
    public int GetItemCountDroppedOnSaw( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
    	if ( iSubtype == m_iSubtypeWicker )
    	{
    		return 2;
    	}
    	else if ( iSubtype == m_iSubtypeBone )
    	{
    		return 5; // 9 in full block
    	}

    	return super.GetItemCountDroppedOnSaw( world, i, j, k );
    }
    
	@Override
    public float GetMovementModifier( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
    	if ( iSubtype == m_iSubtypeDung )
    	{
    		return 1F;
    	}
        
        return 1.2F;
    }
	
    @Override
    public StepSound GetStepSound( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
    	if ( iSubtype == m_iSubtypeDung )
    	{
    		return FCBetterThanWolves.fcStepSoundSquish;
    	}
    	else if ( iSubtype == m_iSubtypeBone )
    	{
    		return soundGravelFootstep;
    	}
    	
    	return stepSound;
    }
    
    @Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	int iSubtype = world.getBlockMetadata( i, j, k );
    	
    	return iSubtype == m_iSubtypeDung || iSubtype == m_iSubtypeBone || iSubtype == m_iSubtypeSoap;
    }
    
    @Override
    public boolean CanToolsStickInBlock( IBlockAccess blockAccess, int i, int j, int k )
    {
    	int iSubtype = blockAccess.getBlockMetadata( i, j, k );
    	
    	return iSubtype != m_iSubtypeWicker;
    }
    
	//------------- Class Specific Methods ------------//
}