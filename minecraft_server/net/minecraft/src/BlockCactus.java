package net.minecraft.src;

import java.util.Random;

public class BlockCactus extends Block
{
    //AARON
    static 
    {
    	Gloryholes.instance.getVersionString();
    }
    //AARON
    static 
    {
    	SuperBTW.instance.getVersionString();
    }
    
    private Icon cactusTopIcon;
    private Icon cactusBottomIcon;

    protected BlockCactus(int par1)
    {
        super(par1, Material.cactus);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isAirBlock(par2, par3 + 1, par4))
        {
            int var6;

            for (var6 = 1; par1World.getBlockId(par2, par3 - var6, par4) == this.blockID; ++var6)
            {
                ;
            }

            if (var6 < 3)
            {
                int var7 = par1World.getBlockMetadata(par2, par3, par4);

                if (var7 == 15)
                {
                    par1World.setBlock(par2, par3 + 1, par4, this.blockID);
                    par1World.setBlockMetadata(par2, par3, par4, 0, 4);
                    this.onNeighborBlockChange(par1World, par2, par3 + 1, par4, this.blockID);
                }
                else
                {
                    par1World.setBlockMetadata(par2, par3, par4, var7 + 1, 4);
                }
            }
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        return AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1) - var5));
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        return AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)(par3 + 1), (double)((float)(par4 + 1) - var5));
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.cactusTopIcon : (par1 == 0 ? this.cactusBottomIcon : this.blockIcon);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 13;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return !super.canPlaceBlockAt(par1World, par2, par3, par4) ? false : this.canBlockStay(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            par1World.destroyBlock(par2, par3, par4, true);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid())
        {
            return false;
        }
        else if (par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid())
        {
            return false;
        }
        else if (par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid())
        {
            return false;
        }
        else if (par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid())
        {
            return false;
        }
        else
        {
            int var5 = par1World.getBlockId(par2, par3 - 1, par4);
            return var5 == Block.cactus.blockID || var5 == Block.sand.blockID;
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.attackEntityFrom(DamageSource.cactus, 1);
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
//    public void registerIcons(IconRegister par1IconRegister)
//    {
//        this.blockIcon = par1IconRegister.registerIcon("cactus_side");
//        this.cactusTopIcon = par1IconRegister.registerIcon("cactus_top");
//        this.cactusBottomIcon = par1IconRegister.registerIcon("cactus_bottom");
//    }
}
