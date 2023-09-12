package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class ComponentScatteredFeatureDesertPyramid extends ComponentScatteredFeature
{
	// FCNOTE: Tracks the loot chests which have already been generated.
    private boolean[] field_74940_h = new boolean[4];
    
    //SAU: ADDED
    private static ArrayList<WeightedRandomChestContent> chestLoot = new ArrayList();
    private static ArrayList<WeightedRandomChestContent> lootedChestLoot = new ArrayList();

    /** List of items to generate in chests of Temples. */
    // FCMOD: Removed as deprecated
    //private static final WeightedRandomChestContent[] itemsToGenerateInTemple = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3), new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 2, 7, 15), new WeightedRandomChestContent(Item.emerald.itemID, 0, 1, 3, 2), new WeightedRandomChestContent(Item.bone.itemID, 0, 4, 6, 20), new WeightedRandomChestContent(Item.rottenFlesh.itemID, 0, 3, 7, 16)};
    // END FCMOD

    public ComponentScatteredFeatureDesertPyramid(Random par1Random, int par2, int par3)
    {
        super(par1Random, par2, 64, par3, 21, 15, 21);
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    // FCMOD: Removed and replaced
    /*
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, Block.sandStone.blockID, Block.sandStone.blockID, false);
        int var4;

        for (var4 = 1; var4 <= 9; ++var4)
        {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, var4, var4, var4, this.scatteredFeatureSizeX - 1 - var4, var4, this.scatteredFeatureSizeZ - 1 - var4, Block.sandStone.blockID, Block.sandStone.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, var4 + 1, var4, var4 + 1, this.scatteredFeatureSizeX - 2 - var4, var4, this.scatteredFeatureSizeZ - 2 - var4, 0, 0, false);
        }

        int var5;

        for (var4 = 0; var4 < this.scatteredFeatureSizeX; ++var4)
        {
            for (var5 = 0; var5 < this.scatteredFeatureSizeZ; ++var5)
            {
                this.fillCurrentPositionBlocksDownwards(par1World, Block.sandStone.blockID, 0, var4, -5, var5, par3StructureBoundingBox);
            }
        }

        var4 = this.getMetadataWithOffset(Block.stairsSandStone.blockID, 3);
        var5 = this.getMetadataWithOffset(Block.stairsSandStone.blockID, 2);
        int var6 = this.getMetadataWithOffset(Block.stairsSandStone.blockID, 0);
        int var7 = this.getMetadataWithOffset(Block.stairsSandStone.blockID, 1);
        byte var8 = 1;
        byte var9 = 11;
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 9, 4, Block.sandStone.blockID, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 10, 1, 3, 10, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var4, 2, 10, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var5, 2, 10, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var6, 0, 10, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var7, 4, 10, 2, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 5, 0, 0, this.scatteredFeatureSizeX - 1, 9, 4, Block.sandStone.blockID, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 4, 10, 1, this.scatteredFeatureSizeX - 2, 10, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var4, this.scatteredFeatureSizeX - 3, 10, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var5, this.scatteredFeatureSizeX - 3, 10, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var6, this.scatteredFeatureSizeX - 5, 10, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var7, this.scatteredFeatureSizeX - 1, 10, 2, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 0, 0, 12, 4, 4, Block.sandStone.blockID, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 9, 1, 0, 11, 3, 4, 0, 0, false);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 9, 1, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 9, 2, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 9, 3, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 10, 3, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 11, 3, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 11, 2, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 11, 1, 1, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 8, 3, 3, Block.sandStone.blockID, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 2, 8, 2, 2, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 12, 1, 1, 16, 3, 3, Block.sandStone.blockID, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 12, 1, 2, 16, 2, 2, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 5, 4, 5, this.scatteredFeatureSizeX - 6, 4, this.scatteredFeatureSizeZ - 6, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 9, 4, 9, 11, 4, 11, 0, 0, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 8, 1, 8, 8, 3, 8, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 12, 1, 8, 12, 3, 8, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 8, 1, 12, 8, 3, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 12, 1, 12, 12, 3, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 5, 4, 4, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 5, 1, 5, this.scatteredFeatureSizeX - 2, 4, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 6, 7, 9, 6, 7, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 7, 7, 9, this.scatteredFeatureSizeX - 7, 7, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 5, 5, 9, 5, 7, 11, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 6, 5, 9, this.scatteredFeatureSizeX - 6, 7, 11, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 5, 5, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 5, 6, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 6, 6, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, this.scatteredFeatureSizeX - 6, 5, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, this.scatteredFeatureSizeX - 6, 6, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, this.scatteredFeatureSizeX - 7, 6, 10, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 2, 4, 4, 2, 6, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 3, 4, 4, this.scatteredFeatureSizeX - 3, 6, 4, 0, 0, false);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var4, 2, 4, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var4, 2, 3, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var4, this.scatteredFeatureSizeX - 3, 4, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var4, this.scatteredFeatureSizeX - 3, 3, 4, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 3, 2, 2, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 3, 1, 3, this.scatteredFeatureSizeX - 2, 2, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, 0, 1, 1, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, 0, this.scatteredFeatureSizeX - 2, 1, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 1, 1, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 1, this.scatteredFeatureSizeX - 2, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var7, 2, 1, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsSandStone.blockID, var6, this.scatteredFeatureSizeX - 3, 1, 2, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 5, 4, 3, 18, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 5, 3, 5, this.scatteredFeatureSizeX - 5, 3, 17, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 5, 4, 2, 16, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.scatteredFeatureSizeX - 6, 1, 5, this.scatteredFeatureSizeX - 5, 2, 16, 0, 0, false);
        int var10;

        for (var10 = 5; var10 <= 17; var10 += 2)
        {
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 4, 1, var10, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, 4, 2, var10, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, this.scatteredFeatureSizeX - 5, 1, var10, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, this.scatteredFeatureSizeX - 5, 2, var10, par3StructureBoundingBox);
        }

        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 10, 0, 7, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 10, 0, 8, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 9, 0, 9, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 11, 0, 9, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 8, 0, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 12, 0, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 7, 0, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 13, 0, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 9, 0, 11, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 11, 0, 11, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 10, 0, 12, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 10, 0, 13, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var9, 10, 0, 10, par3StructureBoundingBox);

        for (var10 = 0; var10 <= this.scatteredFeatureSizeX - 1; var10 += this.scatteredFeatureSizeX - 1)
        {
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 2, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 2, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 2, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 3, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 3, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 3, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 4, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, var10, 4, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 4, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 5, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 5, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 5, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 6, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, var10, 6, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 6, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 7, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 7, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 7, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 8, 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 8, 2, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 8, 3, par3StructureBoundingBox);
        }

        for (var10 = 2; var10 <= this.scatteredFeatureSizeX - 3; var10 += this.scatteredFeatureSizeX - 3 - 2)
        {
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 - 1, 2, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 2, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 + 1, 2, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 - 1, 3, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 3, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 + 1, 3, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10 - 1, 4, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, var10, 4, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10 + 1, 4, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 - 1, 5, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 5, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 + 1, 5, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10 - 1, 6, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, var10, 6, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10 + 1, 6, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10 - 1, 7, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10, 7, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, var10 + 1, 7, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 - 1, 8, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10, 8, 0, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, var10 + 1, 8, 0, par3StructureBoundingBox);
        }

        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 8, 4, 0, 12, 6, 0, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 8, 6, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 12, 6, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 9, 5, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, 10, 5, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, var8, 11, 5, 0, par3StructureBoundingBox);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 8, -14, 8, 12, -11, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 8, -10, 8, 12, -10, 12, Block.sandStone.blockID, 1, Block.sandStone.blockID, 1, false);
        this.fillWithMetadataBlocks(par1World, par3StructureBoundingBox, 8, -9, 8, 12, -9, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, -8, 8, 12, -1, 12, Block.sandStone.blockID, Block.sandStone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 9, -11, 9, 11, -1, 11, 0, 0, false);
        this.placeBlockAtCurrentPosition(par1World, Block.pressurePlateStone.blockID, 0, 10, -11, 10, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 9, -13, 9, 11, -13, 11, Block.tnt.blockID, 0, false);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 8, -11, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 8, -10, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, 7, -10, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 7, -11, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 12, -11, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 12, -10, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, 13, -10, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 13, -11, 10, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 10, -11, 8, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 10, -10, 8, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, 10, -10, 7, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 10, -11, 7, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 10, -11, 12, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 10, -10, 12, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 1, 10, -10, 13, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.sandStone.blockID, 2, 10, -11, 13, par3StructureBoundingBox);

        for (var10 = 0; var10 < 4; ++var10)
        {
            if (!this.field_74940_h[var10])
            {
                int var11 = Direction.offsetX[var10] * 2;
                int var12 = Direction.offsetZ[var10] * 2;
                this.field_74940_h[var10] = this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 10 + var11, -11, 10 + var12, WeightedRandomChestContent.func_92080_a(itemsToGenerateInTemple, new WeightedRandomChestContent[] {Item.enchantedBook.func_92114_b(par2Random)}), 2 + par2Random.nextInt(5));
            }
        }

        return true;
    }
    */
    // END FCMOD
    
    // FCMOD: Added New
    private static final WeightedRandomChestContent[] m_LootListArray = 
    	new WeightedRandomChestContent[] {
    		// item ID, item damage, min stack size, max stack size, weight
	    	new WeightedRandomChestContent( Item.helmetGold.itemID, 0, 1, 1, 5 ), 
	    	new WeightedRandomChestContent( Item.plateGold.itemID, 0, 1, 1, 2 ), 
	    	new WeightedRandomChestContent( Item.legsGold.itemID, 0, 1, 1, 5 ), 
	    	new WeightedRandomChestContent( Item.bootsGold.itemID, 0, 1, 1, 2 ), 
	    	new WeightedRandomChestContent( Item.swordGold.itemID, 0, 1, 1, 5 ), 
	    	new WeightedRandomChestContent( Item.emerald.itemID, 0, 1, 5, 15 ), 
	    	new WeightedRandomChestContent( Item.bone.itemID, 0, 4, 6, 20 ), 
	    	new WeightedRandomChestContent( Item.rottenFlesh.itemID, 0, 3, 7, 11 ),
	    	new WeightedRandomChestContent( Item.skull.itemID, 0, 1, 1, 5 )
		};
    
    private static final WeightedRandomChestContent[] m_LootedLootListArray = 
    	new WeightedRandomChestContent[] {
	    	new WeightedRandomChestContent( Item.bone.itemID, 0, 4, 6, 20 ), 
	    	new WeightedRandomChestContent( Item.rottenFlesh.itemID, 0, 3, 7, 11 ),
	    	new WeightedRandomChestContent( Item.skull.itemID, 0, 1, 1, 5 )
		};
    
    //SAU: ADDED
    static {
    	chestLoot.add( new WeightedRandomChestContent( Item.helmetGold.itemID, 0, 1, 1, 5 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.plateGold.itemID, 0, 1, 1, 2 ) ); 
    	chestLoot.add( new WeightedRandomChestContent( Item.legsGold.itemID, 0, 1, 1, 5 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.bootsGold.itemID, 0, 1, 1, 2 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.swordGold.itemID, 0, 1, 1, 5 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.emerald.itemID, 0, 1, 5, 15 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.bone.itemID, 0, 4, 6, 20 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.rottenFlesh.itemID, 0, 3, 7, 11 ) );
    	chestLoot.add( new WeightedRandomChestContent( Item.skull.itemID, 0, 1, 1, 5 ) );
    	
    	lootedChestLoot.add( new WeightedRandomChestContent( Item.bone.itemID, 0, 4, 6, 20 ) );
    	lootedChestLoot.add( new WeightedRandomChestContent( Item.rottenFlesh.itemID, 0, 3, 7, 11 ) );
    	lootedChestLoot.add( new WeightedRandomChestContent( Item.skull.itemID, 0, 1, 1, 5 ) );
    	
    	FCAddOnHandler.addDesertPyramidLoot( chestLoot, lootedChestLoot );
    }
    
    public boolean addComponentParts( World world, Random generatorRand, 
    	StructureBoundingBox boundingBox )
    {
    	boolean bIsLooted = FCUtilsHardcoreSpawn.IsInLootedTempleRadius( world, 
    		boundingBox.getCenterX(), boundingBox.getCenterZ() );
    	
    	// All cloth blocks from original have been replaced by obsidian, 
    	// and allowances made for looted temples
    	
        fillWithBlocks(world, boundingBox, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, Block.sandStone.blockID, Block.sandStone.blockID, false);
        
        int var4;

        for (var4 = 1; var4 <= 9; ++var4)
        {
            fillWithBlocks(world, boundingBox, var4, var4, var4, this.scatteredFeatureSizeX - 1 - var4, var4, this.scatteredFeatureSizeZ - 1 - var4, Block.sandStone.blockID, Block.sandStone.blockID, false);
            fillWithBlocks(world, boundingBox, var4 + 1, var4, var4 + 1, this.scatteredFeatureSizeX - 2 - var4, var4, this.scatteredFeatureSizeZ - 2 - var4, 0, 0, false);
}

        int var5;

        for (var4 = 0; var4 < this.scatteredFeatureSizeX; ++var4)
        {
            for (var5 = 0; var5 < this.scatteredFeatureSizeZ; ++var5)
            {
                fillCurrentPositionBlocksDownwards(world, Block.sandStone.blockID, 0, var4, -5, var5, boundingBox);
            }
        }

        var4 = getMetadataWithOffset(Block.stairsSandStone.blockID, 3);
        var5 = getMetadataWithOffset(Block.stairsSandStone.blockID, 2);
        
        int var6 = getMetadataWithOffset(Block.stairsSandStone.blockID, 0);
        int var7 = getMetadataWithOffset(Block.stairsSandStone.blockID, 1);
        
        fillWithBlocks(world, boundingBox, 0, 0, 0, 4, 9, 4, Block.sandStone.blockID, 0, false);
        fillWithBlocks(world, boundingBox, 1, 10, 1, 3, 10, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var4, 2, 10, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var5, 2, 10, 4, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var6, 0, 10, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var7, 4, 10, 2, boundingBox);
        
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 5, 0, 0, this.scatteredFeatureSizeX - 1, 9, 4, Block.sandStone.blockID, 0, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 4, 10, 1, this.scatteredFeatureSizeX - 2, 10, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var4, this.scatteredFeatureSizeX - 3, 10, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var5, this.scatteredFeatureSizeX - 3, 10, 4, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var6, this.scatteredFeatureSizeX - 5, 10, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var7, this.scatteredFeatureSizeX - 1, 10, 2, boundingBox);
        
        fillWithBlocks(world, boundingBox, 8, 0, 0, 12, 4, 4, Block.sandStone.blockID, 0, false);
        fillWithBlocks(world, boundingBox, 9, 1, 0, 11, 3, 4, 0, 0, false);
        
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 9, 1, 1, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 9, 2, 1, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 9, 3, 1, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 10, 3, 1, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 11, 3, 1, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 11, 2, 1, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 11, 1, 1, boundingBox);
        
        fillWithBlocks(world, boundingBox, 4, 1, 1, 8, 3, 3, Block.sandStone.blockID, 0, false);
        fillWithBlocks(world, boundingBox, 4, 1, 2, 8, 2, 2, 0, 0, false);
        fillWithBlocks(world, boundingBox, 12, 1, 1, 16, 3, 3, Block.sandStone.blockID, 0, false);
        fillWithBlocks(world, boundingBox, 12, 1, 2, 16, 2, 2, 0, 0, false);
        fillWithBlocks(world, boundingBox, 5, 4, 5, this.scatteredFeatureSizeX - 6, 4, this.scatteredFeatureSizeZ - 6, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, 9, 4, 9, 11, 4, 11, 0, 0, false);
        
        fillWithMetadataBlocks(world, boundingBox, 8, 1, 8, 8, 3, 8, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        fillWithMetadataBlocks(world, boundingBox, 12, 1, 8, 12, 3, 8, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        fillWithMetadataBlocks(world, boundingBox, 8, 1, 12, 8, 3, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        fillWithMetadataBlocks(world, boundingBox, 12, 1, 12, 12, 3, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        
        fillWithBlocks(world, boundingBox, 1, 1, 5, 4, 4, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 5, 1, 5, this.scatteredFeatureSizeX - 2, 4, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, 6, 7, 9, 6, 7, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 7, 7, 9, this.scatteredFeatureSizeX - 7, 7, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
        
        fillWithMetadataBlocks(world, boundingBox, 5, 5, 9, 5, 7, 11, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        fillWithMetadataBlocks(world, boundingBox, this.scatteredFeatureSizeX - 6, 5, 9, this.scatteredFeatureSizeX - 6, 7, 11, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        
        placeBlockAtCurrentPosition(world, 0, 0, 5, 5, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 5, 6, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 6, 6, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, this.scatteredFeatureSizeX - 6, 5, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, this.scatteredFeatureSizeX - 6, 6, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, this.scatteredFeatureSizeX - 7, 6, 10, boundingBox);
        
        fillWithBlocks(world, boundingBox, 2, 4, 4, 2, 6, 4, 0, 0, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 3, 4, 4, this.scatteredFeatureSizeX - 3, 6, 4, 0, 0, false);
        
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var4, 2, 4, 5, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var4, 2, 3, 4, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var4, this.scatteredFeatureSizeX - 3, 4, 5, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var4, this.scatteredFeatureSizeX - 3, 3, 4, boundingBox);
        
        fillWithBlocks(world, boundingBox, 1, 1, 3, 2, 2, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 3, 1, 3, this.scatteredFeatureSizeX - 2, 2, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
        
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, 0, 1, 1, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, 0, this.scatteredFeatureSizeX - 2, 1, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stoneSingleSlab.blockID, 1, 1, 2, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stoneSingleSlab.blockID, 1, this.scatteredFeatureSizeX - 2, 2, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var7, 2, 1, 2, boundingBox);
        placeBlockAtCurrentPosition(world, Block.stairsSandStone.blockID, var6, this.scatteredFeatureSizeX - 3, 1, 2, boundingBox);
        
        fillWithBlocks(world, boundingBox, 4, 3, 5, 4, 3, 18, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 5, 3, 5, this.scatteredFeatureSizeX - 5, 3, 17, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, 3, 1, 5, 4, 2, 16, 0, 0, false);
        fillWithBlocks(world, boundingBox, this.scatteredFeatureSizeX - 6, 1, 5, this.scatteredFeatureSizeX - 5, 2, 16, 0, 0, false);
        
        int var10;

        for (var10 = 5; var10 <= 17; var10 += 2)
        {
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 4, 1, var10, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, 4, 2, var10, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, this.scatteredFeatureSizeX - 5, 1, var10, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, this.scatteredFeatureSizeX - 5, 2, var10, boundingBox);
        }

        // the following is the center tiled portion of the floor, centered on 10, 0, 10
        
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 10, 0, 7, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 10, 0, 8, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 9, 0, 9, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 11, 0, 9, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 8, 0, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 12, 0, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 7, 0, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 13, 0, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 9, 0, 11, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 11, 0, 11, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 10, 0, 12, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 10, 0, 13, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 10, 0, 10, boundingBox);

        for (var10 = 0; var10 <= this.scatteredFeatureSizeX - 1; var10 += this.scatteredFeatureSizeX - 1)
        {
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 2, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 2, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 2, 3, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 3, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 3, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 3, 3, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 4, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, var10, 4, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 4, 3, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 5, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 5, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 5, 3, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 6, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, var10, 6, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 6, 3, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 7, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 7, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 7, 3, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 8, 1, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 8, 2, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 8, 3, boundingBox);
        }

        for (var10 = 2; var10 <= this.scatteredFeatureSizeX - 3; var10 += this.scatteredFeatureSizeX - 3 - 2)
        {
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 - 1, 2, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 2, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 + 1, 2, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 - 1, 3, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 3, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 + 1, 3, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10 - 1, 4, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, var10, 4, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10 + 1, 4, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 - 1, 5, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 5, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 + 1, 5, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10 - 1, 6, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, var10, 6, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10 + 1, 6, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10 - 1, 7, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10, 7, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, var10 + 1, 7, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 - 1, 8, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10, 8, 0, boundingBox);
            placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, var10 + 1, 8, 0, boundingBox);
        }

        fillWithMetadataBlocks(world, boundingBox, 8, 4, 0, 12, 6, 0, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        
        placeBlockAtCurrentPosition(world, 0, 0, 8, 6, 0, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 12, 6, 0, boundingBox);
        
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 9, 5, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, 10, 5, 0, boundingBox);
        placeBlockAtCurrentPosition(world, Block.obsidian.blockID, 0, 11, 5, 0, boundingBox);
        
        fillWithMetadataBlocks(world, boundingBox, 8, -14, 8, 12, -11, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        fillWithMetadataBlocks(world, boundingBox, 8, -10, 8, 12, -10, 12, Block.sandStone.blockID, 1, Block.sandStone.blockID, 1, false);
        fillWithMetadataBlocks(world, boundingBox, 8, -9, 8, 12, -9, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
        
        fillWithBlocks(world, boundingBox, 8, -8, 8, 12, -1, 12, Block.sandStone.blockID, Block.sandStone.blockID, false);
        fillWithBlocks(world, boundingBox, 9, -11, 9, 11, -1, 11, 0, 0, false);
        
        placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, 10, -11, 10, boundingBox);
        
        fillWithBlocks(world, boundingBox, 9, -13, 9, 11, -13, 11, Block.tnt.blockID, 0, false);
        
        placeBlockAtCurrentPosition(world, 0, 0, 8, -11, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 8, -10, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, 7, -10, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 7, -11, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 12, -11, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 12, -10, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, 13, -10, 10, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 13, -11, 10, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 10, -11, 8, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 10, -10, 8, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, 10, -10, 7, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 10, -11, 7, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 10, -11, 12, boundingBox);
        placeBlockAtCurrentPosition(world, 0, 0, 10, -10, 12, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 1, 10, -10, 13, boundingBox);
        placeBlockAtCurrentPosition(world, Block.sandStone.blockID, 2, 10, -11, 13, boundingBox);

        for ( var10 = 0; var10 < 4; ++var10 )
        {
            if ( !field_74940_h[var10] )
            {
                int iXOffset = Direction.offsetX[var10] * 2;
                int iZOffset = Direction.offsetZ[var10] * 2;
                
                //SAU: REPLACED
//                WeightedRandomChestContent[] lootList = m_LootListArray;
                WeightedRandomChestContent[] lootList;
                
                //SAU: REPLACED
//                int iNumItems = 2 + generatorRand.nextInt( 5 );

                int iNumItems = FCAddOnHandler.getNumberOfItemsInDesertPyramidChests(generatorRand);
                
                if (iNumItems >= 27)
                {
                	iNumItems = 27;
                }
                                
                if (iNumItems == 0)
                {
                	iNumItems = 2 + generatorRand.nextInt( 5 );
                }
                
                //SAU: ADDED
                WeightedRandomChestContent[] arrChestLoot = new WeightedRandomChestContent[chestLoot.size()];
                WeightedRandomChestContent[] arrLootedChestLoot = new WeightedRandomChestContent[lootedChestLoot.size()];
                
                //SAU: REPLACED
//            	if ( bIsLooted )
//            	{
//                    lootList = m_LootedLootListArray;
//                    iNumItems /= 2;
//            	}
                
                lootList = chestLoot.toArray(arrChestLoot);
                
            	if ( bIsLooted )
            	{
                    iNumItems /= 2;
                    
                    lootList = lootedChestLoot.toArray(arrLootedChestLoot);
            	}
            	
                // creates a modified loot list that includes instantiated items with complex data
                // such as the following enchanted book with a random enchantment
                
            	WeightedRandomChestContent[] moddedLootList =
                	WeightedRandomChestContent.func_92080_a( lootList, 
                		new WeightedRandomChestContent[] { 
            			Item.enchantedBook.func_92114_b( generatorRand ) } );
            	
                field_74940_h[var10] = generateStructureChestContents( world, boundingBox, 
                	generatorRand, 10 + iXOffset, -11, 10 + iZOffset, 
                	moddedLootList, iNumItems );
            }
        }

        if ( bIsLooted )
        {
        	// clear the center of the floor for access to the shaft
        	
            fillWithBlocks(world, boundingBox, 9, 0, 9, 10, 0, 10, 0, 0, false);
            
            // clear out trapped area
            
            this.fillWithBlocks(world, boundingBox, 9, -13, 9, 11, -11, 11, 0, 0, false);            
            
            // drop a ladder
            
            int iLadderFacing = getMetadataWithOffset( Block.ladder.blockID, 5 ); // legacy ladder metadata is straight facing
            
            int iLadderMetadata = FCBetterThanWolves.fcBlockLadder.SetFacing( 0, iLadderFacing ); 
            
            for ( int iTempY = -13; iTempY <= 0; iTempY++ )
            {
            	placeBlockAtCurrentPosition(world, FCBetterThanWolves.fcBlockLadder.blockID, iLadderMetadata, 
            		9, iTempY, 9, boundingBox );
            }            
        }
        else
        {
        	// place enchanting table at center
        	
        	placeBlockAtCurrentPosition(world, Block.enchantmentTable.blockID, 0, 10, 1, 10, boundingBox );
        }
        
        return true;
    }
    // END FCMOD
}
