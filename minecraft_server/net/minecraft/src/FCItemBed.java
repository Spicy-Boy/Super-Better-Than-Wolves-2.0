package net.minecraft.src;

public class FCItemBed extends FCItemPlacesAsBlock {
    public FCItemBed(int itemID, int blockID) {
		super(itemID, blockID);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        else if (side != 1) {
            return false;
        }
        else {
            Block bed = Block.blocksList[this.m_iBlockID];

            int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

            y++;

            byte offsetX = 0;
            byte offsetZ = 0;

            if (facing == 0) {
                offsetZ = 1;
            }

            if (facing == 1) {
                offsetX = -1;
            }

            if (facing == 2) {
                offsetZ = -1;
            }

            if (facing == 3) {
                offsetX = 1;
            }

            if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x + offsetX, y, z + offsetZ, side, stack)) {
                if (world.isAirBlock(x, y, z) && world.isAirBlock(x + offsetX, y, z + offsetZ) && world.doesBlockHaveSolidTopSurface(x, y - 1, z) && world.doesBlockHaveSolidTopSurface(x + offsetX, y - 1, z + offsetZ)) {
                    world.setBlock(x, y, z, bed.blockID, facing, 3);

                    if (world.getBlockId(x, y, z) == bed.blockID) {
                        world.setBlock(x + offsetX, y, z + offsetZ, bed.blockID, facing + 8, 3);
                    }

                    PlayPlaceSound(world, x, y, z, bed);
                    world.NotifyNearbyAnimalsOfPlayerBlockAddOrRemove(player, bed, x, y, z);

                    stack.stackSize--;
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
    }
}