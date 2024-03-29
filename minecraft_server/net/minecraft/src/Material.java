package net.minecraft.src;

public class Material
{
    public static final Material air = new MaterialTransparent(MapColor.airColor);

    /** The material used by BlockGrass. */
    // FCMOD: Changed
    //public static final Material grass = new Material(MapColor.grassColor);
    //public static final Material ground = new Material(MapColor.dirtColor);
    //public static final Material wood = (new Material(MapColor.woodColor)).setBurning();
    public static final Material grass = (new Material(MapColor.grassColor)).setRequiresTool();
    public static final Material ground = (new Material(MapColor.dirtColor)).setRequiresTool();
    public static final Material wood = (new Material(MapColor.woodColor)).setBurning().SetMobsCantSpawnOn().SetAxesEfficientOn();
    // END FCMOD
    public static final Material rock = (new Material(MapColor.stoneColor)).setRequiresTool();
    // FCMOD: Changed
    //public static final Material iron = (new Material(MapColor.ironColor)).setRequiresTool();
    //public static final Material anvil = (new Material(MapColor.ironColor)).setRequiresTool().setImmovableMobility();
    public static final Material iron = (new Material(MapColor.ironColor)).setRequiresTool();
    public static final Material anvil = (new Material(MapColor.ironColor)).setRequiresTool().setImmovableMobility();
    // END FCMOD
    public static final Material water = (new MaterialLiquid(MapColor.waterColor)).setNoPushMobility();
    public static final Material lava = (new MaterialLiquid(MapColor.tntColor)).setNoPushMobility();
    // FCMOD: Changed
    //public static final Material leaves = (new Material(MapColor.foliageColor)).setBurning().setTranslucent().setNoPushMobility();
    //public static final Material plants = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility();
    //public static final Material vine = (new MaterialLogic(MapColor.foliageColor)).setBurning().setNoPushMobility().setReplaceable();
    public static final Material leaves = (new Material(MapColor.foliageColor)).setBurning().setTranslucent().setNoPushMobility().SetAxesEfficientOn().SetAxesTreatAsVegetation();
    public static final Material plants = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility().SetAxesEfficientOn().SetAxesTreatAsVegetation();
    public static final Material vine = (new MaterialLogic(MapColor.foliageColor)).setBurning().setNoPushMobility().setReplaceable().SetAxesEfficientOn().SetAxesTreatAsVegetation();
    // END FCMOD
    public static final Material sponge = new Material(MapColor.clothColor);
    // FCMOD: Changed
    //public static final Material cloth = (new Material(MapColor.clothColor)).setBurning();
    public static final Material cloth = (new Material(MapColor.clothColor)).setBurning().SetAxesEfficientOn();
    // END FCMOD
    public static final Material fire = (new MaterialTransparent(MapColor.airColor)).setNoPushMobility();
    // FCMOD: Changed
    //public static final Material sand = new Material(MapColor.sandColor);
    public static final Material sand = (new Material(MapColor.sandColor)).setRequiresTool();
    // END FCMOD
    public static final Material circuits = (new MaterialLogic(MapColor.airColor)).setNoPushMobility();
    // FCMOD: Changed
    //public static final Material glass = (new Material(MapColor.airColor)).setTranslucent().setAlwaysHarvested();
    //AARON changed to make glass a non-spawning material again
    public static final Material glass = (new Material(MapColor.airColor)).setTranslucent().SetMobsCantSpawnOn().setAlwaysHarvested();
    // END FCMOD
    public static final Material redstoneLight = (new Material(MapColor.airColor)).setAlwaysHarvested();
    // FCMOD: Changed
    //public static final Material tnt = (new Material(MapColor.tntColor)).setBurning().setTranslucent();
    public static final Material tnt = (new Material(MapColor.tntColor)).setBurning().setTranslucent();
    // END FCMOD
    public static final Material coral = (new Material(MapColor.foliageColor)).setNoPushMobility();
    public static final Material ice = (new Material(MapColor.iceColor)).setTranslucent().setAlwaysHarvested();
    public static final Material snow = (new MaterialLogic(MapColor.snowColor)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();

    /** The material for crafted snow. */
    public static final Material craftedSnow = (new Material(MapColor.snowColor)).setRequiresTool();
    // FCMOD: Changed
    //public static final Material cactus = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility();
    public static final Material cactus = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility().SetMobsCantSpawnOn();    
    // END FCMOD
    public static final Material clay = new Material(MapColor.clayColor);

    /** pumpkin */
    // FCMOD: Changed
    //public static final Material pumpkin = (new Material(MapColor.foliageColor)).setNoPushMobility();
    public static final Material pumpkin = (new Material(MapColor.foliageColor)).setNoPushMobility().SetAxesEfficientOn();
    // END FCMOD
    public static final Material dragonEgg = (new Material(MapColor.foliageColor)).setNoPushMobility();

    /** Material used for portals */
    public static final Material portal = (new MaterialPortal(MapColor.airColor)).setImmovableMobility();

    /** Cake's material, see BlockCake */
    public static final Material cake = (new Material(MapColor.airColor)).setNoPushMobility();

    /** Web's material. */
    public static final Material web = (new MaterialWeb(MapColor.clothColor)).setRequiresTool().setNoPushMobility();

    /** Pistons' material. */
    public static final Material piston = (new Material(MapColor.stoneColor)).setImmovableMobility();

    /** Bool defining if the block can burn or not. */
    private boolean canBurn;

    /**
     * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
     * and tall grass.
     */
    private boolean replaceable;

    /** Indicates if the material is translucent */
    private boolean isTranslucent;

    /** The color index used to draw the blocks of this material on maps. */
    public final MapColor materialMapColor;

    /**
     * Determines if the material can be harvested without a tool (or with the wrong tool)
     */
    private boolean requiresNoTool = true;

    /**
     * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
     * indicates that it can't be pushed.
     */
    private int mobilityFlag;
    private boolean field_85159_M;

    public Material(MapColor par1MapColor)
    {
        this.materialMapColor = par1MapColor;
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return false;
    }

    public boolean isSolid()
    {
        return true;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean getCanBlockGrass()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return true;
    }

    /**
     * Marks the material as translucent
     */
    // FCMOD: Changed
    //private Material setTranslucent()
    protected Material setTranslucent()
    // END FCMOD
    {
        this.isTranslucent = true;
        return this;
    }

    /**
     * Makes blocks with this material require the correct tool to be harvested.
     */
    protected Material setRequiresTool()
    {
        this.requiresNoTool = false;
        return this;
    }

    /**
     * Set the canBurn bool to True and return the current object.
     */
    protected Material setBurning()
    {
        this.canBurn = true;
        return this;
    }

    /**
     * Returns if the block can burn or not.
     */
    public boolean getCanBurn()
    {
        return this.canBurn;
    }

    /**
     * Sets {@link #replaceable} to true.
     */
    public Material setReplaceable()
    {
        this.replaceable = true;
        return this;
    }

    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    public boolean isReplaceable()
    {
        return this.replaceable;
    }

    /**
     * Indicate if the material is opaque
     */
    public boolean isOpaque()
    {
        return this.isTranslucent ? false : this.blocksMovement();
    }

    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    public boolean isToolNotRequired()
    {
        return this.requiresNoTool;
    }

    /**
     * Returns the mobility information of the material, 0 = free, 1 = can't push but can move over, 2 = total
     * immobility and stop pistons.
     */
    public int getMaterialMobility()
    {
        return this.mobilityFlag;
    }

    /**
     * This type of material can't be pushed, but pistons can move over it.
     */
    protected Material setNoPushMobility()
    {
        this.mobilityFlag = 1;
        return this;
    }

    /**
     * This type of material can't be pushed, and pistons are blocked to move.
     */
    protected Material setImmovableMobility()
    {
        this.mobilityFlag = 2;
        return this;
    }

    /**
     * Set as harvestable in any case.
     */
    protected Material setAlwaysHarvested()
    {
        this.field_85159_M = true;
        return this;
    }

    /**
     * Check to see if we can harvest it in any case.
     */
    public boolean isAlwaysHarvested()
    {
        return this.field_85159_M;
    }
    
    // FCMOD: Added New
    private boolean m_bMobsCanSpawnOn = true;
    private boolean m_bNetherMobsCanSpawnOn = false;
    private boolean m_bAxesEfficientOn = false;
    private boolean m_bAxesTreatAsVegetation = false;
    
    public boolean GetMobsCanSpawnOn( int iDimension )
    {
    	if ( iDimension == -1 )
    	{
    		return m_bNetherMobsCanSpawnOn;
    	}
    	
    	return m_bMobsCanSpawnOn;
    }
    
    public Material SetMobsCantSpawnOn()
    {
    	m_bMobsCanSpawnOn = false;
    	
    	return this;
    }
    
    public Material SetNetherMobsCanSpawnOn()
    {
    	m_bNetherMobsCanSpawnOn = true;
    	
    	return this;
    }
    
    public boolean GetAxesEfficientOn()
    {
    	return m_bAxesEfficientOn;
    }
    
    public Material SetAxesEfficientOn()
    {
    	m_bAxesEfficientOn = true;
    	
    	return this;
    }
    
    /**
     * If true, this material doesn't damage axes or consume hunger if the 
     * harvesting axe is of sufficient quality 
     */
    public boolean GetAxesTreatAsVegetation()
    {
    	return m_bAxesTreatAsVegetation;    	
    }
    
    public Material SetAxesTreatAsVegetation()
    {
    	m_bAxesTreatAsVegetation = true;
    	
    	return this;
    }
    // END FCMOD
}
