package net.minecraft.src;

public class SuperBTWTileEntityTimeCube extends TileEntity
{
	  @Override
//  AARON ADDED THE TIME CUBE CODE!!!! DO NOT LET THIS EXIST IN THE FINAL MOD!!!!
    public void updateEntity() {
        int sizeXZ=10;
        int sizeY=10;

        for ( int iTempI = xCoord - sizeXZ; iTempI <= xCoord + sizeXZ; iTempI++ )
        {
            for ( int iTempJ = yCoord - sizeY; iTempJ <= yCoord + sizeY; iTempJ++ )
            {
                for ( int iTempK = zCoord - sizeXZ; iTempK <= zCoord + sizeXZ; iTempK++ )
                {
                    int blockID =worldObj.getBlockId(iTempI, iTempJ, iTempK);
                    if (blockID==0)
                    {
                        if(worldObj.rand.nextInt(200)==0)
                        {
                            worldObj.spawnParticle("reddust", iTempI+0.5D, iTempJ+0.5D, iTempK+0.5D, 0, 0, 0);
                        }
                    }

                    else if( !worldObj.IsUpdateScheduledForBlock(iTempI, iTempJ, iTempK, blockID))
                    {
                        worldObj.scheduleBlockUpdate(iTempI, iTempJ, iTempK, blockID, 10);
                    }
                }
            }
        }
    }
}