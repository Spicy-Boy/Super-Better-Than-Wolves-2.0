// FCMOD

package net.minecraft.src;

import net.minecraft.src.IBlockAccess;

public class FCBlockFurnaceBrickIdle extends FCBlockFurnaceBrick
{
    protected FCBlockFurnaceBrickIdle( int iBlockID )
    {
        super( iBlockID, false );
    }
    
    //AARON added this in order for spicy meat and such (JK!!!! Just lighting bug)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        if (m_bIsRenderingInterior) {
            // hex 999999
            return 10066329;
        }
        return super.colorMultiplier(blockAccess, x, y, z);
    }
}
