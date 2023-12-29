package net.minecraft.src;

//import com.prupe.mcpatcher.cc.ColorizeBlock;
import java.util.List;
import java.util.Random;

//import org.lwjgl.opengl.GL11;

// FCMOD: Added
//import net.minecraft.client.Minecraft; //client only
// END FCMOD

public class Block
{
    /**
     * used as foreach item, if item.tab = current tab, display it on the screen
     */
    private CreativeTabs displayOnCreativeTab;
    public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
    public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
    public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
    public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
    public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
    public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
    public static final StepSound soundSandFootstep = new StepSound("sand", 1.0F, 1.0F);
    public static final StepSound soundSnowFootstep = new StepSound("snow", 1.0F, 1.0F);
    public static final StepSound soundLadderFootstep = new StepSoundSand("ladder", 1.0F, 1.0F);
    public static final StepSound soundAnvilFootstep = new StepSoundAnvil("anvil", 0.3F, 1.0F);

    /** List of ly/ff (BlockType) containing the already registered blocks. */
    public static final Block[] blocksList = new Block[4096];

    /**
     * An array of 4096 booleans corresponding to the result of the isOpaqueCube() method for each block ID
     */
    public static final boolean[] opaqueCubeLookup = new boolean[4096];

    /** How much light is subtracted for going through this block */
    public static final int[] lightOpacity = new int[4096];

    /** Array of booleans that tells if a block can grass
     * FCNOTE: Misleading name.  This is actually wether grass can grow under the block
     */
    public static final boolean[] canBlockGrass = new boolean[4096];

    /** Amount of light emitted */
    public static final int[] lightValue = new int[4096];

    /**
     * Flag if block ID should use the brightest neighbor light value as its own
     * FCNOTE: Misleading name in that this does not apply to the neighbor below the block,
     * only to sides and top
     */ 
    public static boolean[] useNeighborBrightness = new boolean[4096];
    
    public static Block stone = new FCBlockStone( 1 );
    public static BlockGrass grass = new FCBlockGrass( 2 );
    public static Block dirt = new FCBlockDirt( 3 );
    public static Block cobblestone = (new FCBlockCobblestone(4)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
    public static Block planks = new FCBlockPlanks( 5 );
    public static Block sapling = (new FCBlockSapling(6)).setHardness(0.0F).SetBuoyant().setStepSound(soundGrassFootstep).setUnlocalizedName("sapling");
    public static Block bedrock = new FCBlockBedrock( 7 );
    public static BlockFluid waterMoving = (BlockFluid)(new FCBlockWaterFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats();
    public static Block waterStill = (new FCBlockWaterStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats();
    public static BlockFluid lavaMoving = (BlockFluid)(new FCBlockLavaFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setUnlocalizedName("lava").disableStats();
    public static Block lavaStill = (new FCBlockLavaStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setUnlocalizedName("lava").disableStats();
    public static Block sand = new FCBlockSand( 12 );
    public static Block gravel = new FCBlockGravel( 13 );
    public static Block oreGold = (new FCBlockOreGold(14)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreGold");
    public static Block oreIron = (new FCBlockOreIron(15)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreIron");
    public static Block oreCoal = (new FCBlockOreCoal(16)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreCoal");
    public static Block wood = new FCBlockLog( 17 );
    public static BlockLeaves leaves = new FCBlockLeaves( 18 );
    public static Block sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep).setUnlocalizedName("sponge");
    public static Block glass = (new FCBlockGlass(20, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("glass");
    public static Block oreLapis = (new FCBlockOreLapis(21)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreLapis");
    public static Block blockLapis = (new Block(22, Material.rock)).SetPicksEffectiveOn().setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock);
    public static Block dispenser = new FCBlockDispenserVanilla( 23 );
    public static Block sandStone = new FCBlockSandStone( 24 );
    public static Block music = new FCBlockNote( 25 );
    public static Block bed = (new FCBlockBed(26)).setHardness(0.2F).SetBuoyant().setUnlocalizedName("bed").disableStats();
    public static Block railPowered = (new BlockRailPowered(27)).SetPicksEffectiveOn().setHardness(0.7F).setStepSound(soundMetalFootstep).setUnlocalizedName("goldenRail");
    public static Block railDetector = (new FCBlockDetectorRail(28)).setHardness(0.7F).setStepSound(soundMetalFootstep).setUnlocalizedName("detectorRail");
    public static BlockPistonBase pistonStickyBase = (BlockPistonBase)(new FCBlockPistonBase(29, true)).setUnlocalizedName("pistonStickyBase");
    public static Block web = new FCBlockWeb( 30 );
    public static BlockTallGrass tallGrass = new FCBlockTallGrass( 31 );
    public static BlockDeadBush deadBush = new FCBlockDeadBush( 32 );
    public static BlockPistonBase pistonBase = (BlockPistonBase)(new FCBlockPistonBase(33, false)).setUnlocalizedName("pistonBase");
    public static BlockPistonExtension pistonExtension = new FCBlockPistonExtension(34);
    public static Block cloth = new FCBlockCloth(); // sets own blockID of 35
    public static BlockPistonMoving pistonMoving = new FCBlockPistonMoving( 36 );
    public static BlockFlower plantYellow = (BlockFlower)(new FCBlockFlowerBlossom( 37 )).setUnlocalizedName("flower");
    public static BlockFlower plantRed = (BlockFlower)(new FCBlockFlowerBlossom( 38 )).setUnlocalizedName("rose");
    public static BlockFlower mushroomBrown = (BlockFlower)(new FCBlockMushroomBrown(39, "mushroom_brown")).setHardness(0.0F).SetBuoyant().setStepSound(soundGrassFootstep).setUnlocalizedName("mushroom");
    public static BlockFlower mushroomRed = (BlockFlower)(new FCBlockMushroom(40, "mushroom_red")).setHardness(0.0F).SetBuoyant().setStepSound(soundGrassFootstep).setUnlocalizedName("mushroom");
    public static Block blockGold = (new FCBlockOreStorage(41)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockGold");
    public static Block blockIron = (new FCBlockOreStorage(42)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockIron");
    public static BlockHalfSlab stoneDoubleSlab = (BlockHalfSlab)(new FCBlockStep(43, true)).SetPicksEffectiveOn().setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stoneSlab");
    public static BlockHalfSlab stoneSingleSlab = (BlockHalfSlab)(new FCBlockStep(44, false)).SetPicksEffectiveOn().setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("stoneSlab");
    public static Block brick = (new FCBlockBrick(45)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
    public static Block tnt = new FCBlockPowderKeg( 46 );
    public static Block bookShelf = new FCBlockBookshelf( 47 );
    public static Block cobblestoneMossy = new FCBlockCobblestoneMossy( 48 ); 
    public static Block obsidian = new FCBlockObsidian( 49 );
    public static Block torchWood = new FCBlockTorchLegacy( 50 );
    public static BlockFire fire = (BlockFire)(new FCBlockFire(51)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("fire").disableStats();
    public static Block mobSpawner = new FCBlockMobSpawner( 52 );
    public static Block stairsWoodOak = (new FCBlockStairsWood( 53, planks, 0 ) ).setUnlocalizedName( "stairsWood" );
    public static BlockChest chest = (BlockChest)( new FCBlockChest( 54 ) ).setCreativeTab( null );
    public static BlockRedstoneWire redstoneWire = (BlockRedstoneWire)(new FCBlockRedstoneWire(55)).setHardness(0.0F).setStepSound(soundPowderFootstep).setUnlocalizedName("redstoneDust").disableStats();
    public static Block oreDiamond = (new FCBlockOreDiamond(56)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreDiamond");
    public static Block blockDiamond = (new FCBlockOreStorage(57)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockDiamond");
    public static Block workbench = new FCBlockWorkbench( 58 );
    public static Block crops = (new FCBlockWheatLegacy(59)).SetBuoyant().setUnlocalizedName("crops");
    public static Block tilledField = new FCBlockFarmlandLegacyUnfertilized( 60 );
    public static Block furnaceIdle = new FCBlockFurnace( 61, false );
    public static Block furnaceBurning = new FCBlockFurnace( 62, true );
    public static Block signPost = new FCBlockSign( 63, true );
    public static Block doorWood = new FCBlockDoorWood( 64 );
    public static Block ladder = new FCBlockLegacyLadder( 65 );
    public static Block rail = new FCBlockRailRegular( 66 );
    public static Block stairsCobblestone = new FCBlockStairsCobblestone( 67 );
    public static Block signWall = new FCBlockSignWall( 68 );
    public static Block lever = (new FCBlockLever(69)).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("lever");
    public static Block pressurePlateStone = new FCBlockPressurePlateStone( 70 );
    public static Block doorIron = (new FCBlockDoor(71, Material.iron)).setHardness(5.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("doorIron").disableStats();
    public static Block pressurePlatePlanks = new FCBlockPressurePlatePlanks( 72 );
    public static Block oreRedstone = (new FCBlockRedstoneOre(73, false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock);
    public static Block oreRedstoneGlowing = (new FCBlockRedstoneOre(74, true)).setLightValue(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreRedstone").setCreativeTab(null);
    public static Block torchRedstoneIdle = (new BlockRedstoneTorch( 75, false )).setUnlocalizedName("notGate");
    public static Block torchRedstoneActive = (new BlockRedstoneTorch( 76, true )).setLightValue(0.5F).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone);
    public static Block stoneButton = (new FCBlockButtonStone(77)).setHardness(0.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("button");
    public static Block snow = new FCBlockSnowCover( 78 );
    public static Block ice = (new FCBlockIce(79)).setHardness(0.5F).SetBuoyant().setLightOpacity(3).setStepSound(soundGlassFootstep).setUnlocalizedName("ice");
    public static Block blockSnow = new FCBlockSnowLegacy( 80 );
    public static Block cactus = new FCBlockCactus( 81 );
    public static Block blockClay = (new FCBlockClay(82)).setHardness(0.6F).setUnlocalizedName("clay");
    public static Block reed = (new FCBlockReed(83)).setHardness(0.0F).SetBuoyant().setStepSound(soundGrassFootstep).setUnlocalizedName("reeds").disableStats();
    public static Block jukebox = new FCBlockJukebox( 84 );
    public static Block fence = new FCBlockFenceWood( 85 );
    public static Block pumpkin = new FCBlockPumpkinCarved( 86 );
    public static Block netherrack = new FCBlockNetherrack( 87 );
    public static Block slowSand = (new FCBlockSoulSand(88)).setHardness(0.5F).setStepSound(soundSandFootstep).setUnlocalizedName("hellsand");
    public static Block glowStone = new FCBlockGlowStone( 89 );
    public static BlockPortal portal = new FCBlockPortal( 90 );
    public static Block pumpkinLantern = new FCBlockJackOLantern( 91 );
    public static Block cake = (new FCBlockCake(92)).setHardness(0.5F).setStepSound(soundClothFootstep).setUnlocalizedName("cake").disableStats();
    public static BlockRedstoneRepeater redstoneRepeaterIdle = new FCBlockRedstoneRepeater( 93, false );
    public static BlockRedstoneRepeater redstoneRepeaterActive = new FCBlockRedstoneRepeater( 94, true );

    /**
     * April fools secret locked chest, only spawns on new chunks on 1st April.
     */
    public static Block lockedChest = (new BlockLockedChest(95)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("lockedchest").setTickRandomly(true);
    public static Block trapdoor = new FCBlockTrapDoor( 96 );
    public static Block silverfish = new FCBlockSilverfish( 97 );
    public static Block stoneBrick = new FCBlockStoneBrick( 98 );
    public static Block mushroomCapBrown = new FCBlockMushroomCapLegacy( 99, 0 );
    public static Block mushroomCapRed = new FCBlockMushroomCapLegacy( 100, 1 );
    public static Block fenceIron = new FCBlockIronBars( 101 );
    public static Block thinGlass = (new FCBlockPane(102, "glass", "thinglass_top", Material.glass, false)).setHardness(0.3F).SetPicksEffectiveOn().setStepSound(soundGlassFootstep).setUnlocalizedName("thinGlass");
    public static Block melon = (new FCBlockMelon(103)).setHardness(1.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("melon");
    public static Block pumpkinStem = new FCBlockStem( 104, pumpkin );
    public static Block melonStem = new FCBlockStem( 105, melon );
    public static Block vine = new FCBlockVine( 106 );
    public static Block fenceGate = new FCBlockFenceGate( 107 );
    public static Block stairsBrick = (new FCBlockStairsBrick(108)).setUnlocalizedName("stairsBrick");
    public static Block stairsStoneBrick = new FCBlockStairsStoneBrick( 109 );
    public static BlockMycelium mycelium = new FCBlockMycelium( 110 );
    public static Block waterlily = (new FCBlockLilyPad(111)).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("waterlily");
    public static Block netherBrick = new FCBlockNetherBrick( 112 );
    public static Block netherFence = (new FCBlockFence( 113, "netherBrick", FCBetterThanWolves.fcMaterialNetherRock )).setHardness(2F).setResistance(10F).setStepSound(soundStoneFootstep).setUnlocalizedName("netherFence");
    public static Block stairsNetherBrick = new FCBlockStairsNetherBrick( 114 );
    public static Block netherStalk = (new FCBlockNetherStalk(115)).setUnlocalizedName("netherStalk");
    public static Block enchantmentTable = (new FCBlockEnchantmentTable(116)).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable");
    public static Block brewingStand = (new FCBlockBrewingStand(117)).setHardness(0.5F).setLightValue(0.125F).setUnlocalizedName("brewingStand");
    public static BlockCauldron cauldron = (BlockCauldron)(new FCBlockVanillaCauldron(118)).setHardness(2.0F).setUnlocalizedName("cauldron");
    public static Block endPortal = (new FCBlockEndPortal(119, Material.portal)).setHardness(-1.0F).setResistance(6000000.0F);
    public static Block endPortalFrame = (new FCBlockEndPortalFrame(120)).setStepSound(soundGlassFootstep).setLightValue(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations);
    public static Block whiteStone = (new FCBlockEndStone(121, Material.rock)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock);
    public static Block dragonEgg = (new FCBlockDragonEgg(122)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundStoneFootstep).setLightValue(0.125F).setUnlocalizedName("dragonEgg");
    public static Block redstoneLampIdle = (new FCBlockRedstoneLight(123, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone);
    public static Block redstoneLampActive = (new FCBlockRedstoneLight(124, true)).setHardness(0.3F).setStepSound(soundGlassFootstep).setUnlocalizedName("redstoneLight");
    public static BlockHalfSlab woodDoubleSlab = new FCBlockWoodSlab( 125, true );
    public static BlockHalfSlab woodSingleSlab = new FCBlockWoodSlab( 126, false );
    public static Block cocoaPlant = (new FCBlockCocoa(127)).setHardness(0.2F).setResistance(5.0F).SetBuoyant().setStepSound(soundWoodFootstep).setUnlocalizedName("cocoa");
    public static Block stairsSandStone = (new FCBlockStairsSandStone(128)).setUnlocalizedName("stairsSandStone");
    public static Block oreEmerald = (new FCBlockOreEmerald(129)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreEmerald");
    public static Block enderChest = (new FCBlockEnderChest(130)).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("enderChest").setLightValue(0.5F);
    public static BlockTripWireSource tripWireSource = new FCBlockTripWireSource( 131 );
    public static Block tripWire = (new FCBlockTripWire(132)).setUnlocalizedName("tripWire");
    public static Block blockEmerald = (new FCBlockOreStorage(133)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockEmerald");
    public static Block stairsWoodSpruce = ( new FCBlockStairsWood( 134, planks, 1 ) ).setUnlocalizedName( "stairsWoodSpruce" );
    public static Block stairsWoodBirch = ( new FCBlockStairsWood( 135, planks, 2 ) ).setUnlocalizedName( "stairsWoodBirch" );
    public static Block stairsWoodJungle = ( new FCBlockStairsWood( 136, planks, 3 ) ).setUnlocalizedName( "stairsWoodJungle" );
    public static Block commandBlock = (new BlockCommandBlock(137)).setUnlocalizedName("commandBlock");
    public static BlockBeacon beacon = (BlockBeacon)(new FCBlockBeacon(138)).setUnlocalizedName("beacon").setLightValue(1.0F);
    public static Block cobblestoneWall = (new FCBlockWall(139, cobblestone)).setUnlocalizedName("cobbleWall");
    public static Block flowerPot = (new FCBlockFlowerPot(140)).setHardness(0.0F).setStepSound(soundPowderFootstep).setUnlocalizedName("flowerPot");
    public static Block carrot = (new FCBlockCarrot(141)).setUnlocalizedName("carrots");
    public static Block potato = (new FCBlockPotato(142)).setUnlocalizedName("potatoes");
    public static Block woodenButton = (new FCBlockButtonWood(143)).setHardness(0.5F).setStepSound(soundWoodFootstep).setUnlocalizedName("button");
    public static Block skull = new FCBlockSkull( 144 );
    public static Block anvil = new FCBlockAnvil( 145 );
    public static Block chestTrapped = new FCBlockStub( 146 ).setUnlocalizedName( "chestTrap" );
    public static Block pressurePlateGold = new FCBlockStub( 147 ).setUnlocalizedName( "weightedPlate_light" );
    public static Block pressurePlateIron = new FCBlockStub( 148 ).setUnlocalizedName( "weightedPlate_heavy" );
    public static BlockComparator redstoneComparatorIdle = (BlockComparator)(new FCBlockComparator(149, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("comparator").disableStats();
    public static BlockComparator redstoneComparatorActive = (BlockComparator)(new FCBlockComparator(150, true)).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setUnlocalizedName("comparator").disableStats();
    public static BlockDaylightDetector daylightSensor = (BlockDaylightDetector)(new FCBlockDaylightDetector(151)).setHardness(0.2F).setStepSound(soundWoodFootstep).setUnlocalizedName("daylightDetector");
    public static Block blockRedstone = (new BlockPoweredOre(152)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockRedstone").setLightValue(0.75F);
    public static Block oreNetherQuartz = new FCBlockNetherQuartzOre( 153 );
    public static BlockHopper hopperBlock = new FCBlockVanillaHopper( 154 );
    public static Block blockNetherQuartz = new FCBlockBlackStone( 155 );
    public static Block stairsNetherQuartz = new FCBlockBlackStoneStairs( 156 );
    public static Block railActivator = new FCBlockStub( 157 ).setUnlocalizedName( "activatorRail" );
    public static Block dropper = new FCBlockStub( 158 ).setUnlocalizedName( "dropper" );

    /** ID of the block. */
    public final int blockID;

    /** Indicates how many hits it takes to break a block. */
    protected float blockHardness;

    /** Indicates the blocks resistance to explosions. */
    protected float blockResistance;

    /**
     * set to true when Block's constructor is called through the chain of super()'s. Note: Never used
     */
    protected boolean blockConstructorCalled = true;

    /**
     * If this field is true, the block is counted for statistics (mined or placed)
     */
    protected boolean enableStats = true;

    /**
     * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
     * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    protected boolean needsRandomTick;

    /** true if the Block contains a Tile Entity */
    protected boolean isBlockContainer;

    /** FCNOTE: DEPRECATED */
    protected double minX = 0D;
    /** FCNOTE: DEPRECATED */
    protected double minY = 0D;
    /** FCNOTE: DEPRECATED */
    protected double minZ = 0D;
    /** FCNOTE: DEPRECATED */
    protected double maxX = 1D;
    /** FCNOTE: DEPRECATED */
    protected double maxY = 1D;
    /** FCNOTE: DEPRECATED */
    protected double maxZ = 1D;

    /** Sound of stepping on the block */
    public StepSound stepSound;
    public float blockParticleGravity;

    /** Block material definition. */
    public Material blockMaterial;
    
    /**
     * Determines how much velocity is maintained while moving on top of this block
     */
    public float slipperiness;

    /** The unlocalized name of this block. */
    private String unlocalizedName;
    protected Icon blockIcon;
    
    public static final boolean[] blockReplaced = new boolean[4096];
    
    private int idDroppedOnStonecut = -1;
    private int countDroppedOnStonecut = 0;
    private int metaDroppedOnStonecut = 0;
    private MapColor[] mapColorsForMetadata;

    protected Block(int par1, Material par2Material)
    {
        this.stepSound = soundPowderFootstep;
        this.blockParticleGravity = 1.0F;
        this.slipperiness = 0.6F;

        if (blocksList[par1] != null)
        {
            throw new IllegalArgumentException("Slot " + par1 + " is already occupied by " + blocksList[par1] + " when adding " + this);
        }
        else
        {
            this.blockMaterial = par2Material;
            blocksList[par1] = this;
            this.blockID = par1;
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            opaqueCubeLookup[par1] = this.isOpaqueCube();
            lightOpacity[par1] = this.isOpaqueCube() ? 255 : 0;
            canBlockGrass[par1] = !par2Material.getCanBlockGrass();
            // FCMOD: Added
            useNeighborBrightness[par1] = false;
            SetFilterableProperties( Item.m_iFilterable_SolidBlock );
            // END FCMOD
        }
    }

    /**
     * This method is called on a block after all other blocks gets already created. You can use it to reference and
     * configure something on the block that needs the others ones.
     */
    protected void initializeBlock() {}

    /**
     * Sets the footstep sound for the block. Returns the object for convenience in constructing.
     */
    protected Block setStepSound(StepSound par1StepSound)
    {
        this.stepSound = par1StepSound;
        return this;
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    protected Block setLightOpacity(int par1)
    {
        lightOpacity[this.blockID] = par1;
        return this;
    }

    /**
     * Sets the amount of light emitted by a block from 0.0f to 1.0f (converts internally to 0-15). Returns the object
     * for convenience in constructing.
     */
    protected Block setLightValue(float par1)
    {
        lightValue[this.blockID] = (int)(15.0F * par1);
        return this;
    }

    /**
     * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
     */
    protected Block setResistance(float par1)
    {
        this.blockResistance = par1 * 3.0F;
        return this;
    }

    public static boolean isNormalCube(int par0)
    {
        Block var1 = blocksList[par0];
        // FCMOD: Changed this to not care about providing power (this is the way it used to work)
        /*
        return var1 == null ? false : var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock() && !var1.canProvidePower();
        */
        return var1 == null ? false : var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock();
        // END FCMOD
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return true;
    }

	/**
	 * FCNOTE: This is misnamed and returns true if the block *doesn't* block movement
	 */	
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return !this.blockMaterial.blocksMovement();
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 0;
    }

    /**
     * Sets how many hits it takes to break a block.
     */
    protected Block setHardness(float par1)
    {
        this.blockHardness = par1;

        if (this.blockResistance < par1 * 5.0F)
        {
            this.blockResistance = par1 * 5.0F;
        }

        return this;
    }

    /**
     * This method will make the hardness of the block equals to -1, and the block is indestructible.
     */
    protected Block setBlockUnbreakable()
    {
        this.setHardness(-1.0F);
        return this;
    }

    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    public float getBlockHardness(World par1World, int par2, int par3, int par4)
    {
        return this.blockHardness;
    }

    /**
     * Sets whether this block type will receive random update ticks
     */
    protected Block setTickRandomly(boolean par1)
    {
        this.needsRandomTick = par1;
        return this;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean getTickRandomly()
    {
        return this.needsRandomTick;
    }

    public boolean hasTileEntity()
    {
        return this.isBlockContainer;
    }

    /**
     * FCNOTE: DEPRECATED 
     */
    protected final void setBlockBounds(float par1, float par2, float par3, float par4, float par5, float par6)
    {
    	// FCMOD: Removed due to deprecating these variables to eliminate client/server race conditions
    	/*
        this.minX = (double)par1;
        this.minY = (double)par2;
        this.minZ = (double)par3;
        this.maxX = (double)par4;
        this.maxY = (double)par5;
        this.maxZ = (double)par6;
        */
    	// END FCMOD
    }

    /**
//     * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
//     */
//    public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        return par1IBlockAccess.getBrightness(par2, par3, par4, lightValue[par1IBlockAccess.getBlockId(par2, par3, par4)]);
//    }
//
//    /**
//     * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
//     */
//    public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        return par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, lightValue[par1IBlockAccess.getBlockId(par2, par3, par4)]);
//    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    // FCMOD: Code removed and replaced later.  Client only.
    /*
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par5 == 0 && this.minY > 0.0D ? true : (par5 == 1 && this.maxY < 1.0D ? true : (par5 == 2 && this.minZ > 0.0D ? true : (par5 == 3 && this.maxZ < 1.0D ? true : (par5 == 4 && this.minX > 0.0D ? true : (par5 == 5 && this.maxX < 1.0D ? true : !par1IBlockAccess.isBlockOpaqueCube(par2, par3, par4))))));
    }
    */
    // END FCMOD

    /**
     * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getBlockMaterial(par2, par3, par4).isSolid();
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
//    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
//    {
//        return this.getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
//    }
//
//    /**
//     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
//     */
//    public Icon getIcon(int par1, int par2)
//    {
//        return this.blockIcon;
//    }
//
//    /**
//     * Returns the block texture based on the side being looked at.  Args: side
//     */
//    public final Icon getBlockTextureFromSide(int par1)
//    {
//        return this.getIcon(par1, 0);
//    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    // FCMOD: Removed and replaced
    /*
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
    }
    */
    // END FCMOD

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     * FCNOTE: The "mask" referred to above is just the bounding box to check for intersection with
     */
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        AxisAlignedBB var8 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (var8 != null && par5AxisAlignedBB.intersectsWith(var8))
        {
            par6List.add(var8);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    // FCMOD: Removed and replaced
    /*
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
    }
    */
    // END FCMOD

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return true;
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int par1, boolean par2)
    {
        return this.isCollidable();
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable()
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     * FCNOTE: Called on server only
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {}

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {}

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     * FCNOTE: Called AFTER block is set to air, not before
     */
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     * FCNOTE: Only called on server
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World par1World)
    {
        return 10;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     * FCNOTE: Called on server only
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {}

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     * FCNOTE: Called on server only.  Called AFTER block is set, so it is no longer valid at pos.
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {}

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }

    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    // FCMOD: Removed and replaced later
    /*
    public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
    {
        float var6 = this.getBlockHardness(par2World, par3, par4, par5);
        return var6 < 0.0F ? 0.0F : (!par1EntityPlayer.canHarvestBlock(this) ? par1EntityPlayer.getCurrentPlayerStrVsBlock(this, false) / var6 / 100.0F : par1EntityPlayer.getCurrentPlayerStrVsBlock(this, true) / var6 / 30.0F);
    }
    */
    // END FCMOD

    /**
     * Drops the specified block items
     */
    public final void dropBlockAsItem(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, 1.0F, par6);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isRemote)
        {
            int var8 = this.quantityDroppedWithBonus(par7, par1World.rand);

            for (int var9 = 0; var9 < var8; ++var9)
            {
                if (par1World.rand.nextFloat() <= par6)
                {
                    int var10 = this.idDropped(par5, par1World.rand, par7);

                    if (var10 > 0)
                    {
                        this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var10, 1, this.damageDropped(par5)));
                    }
                }
            }
        }
    }

    /**
     * Spawns EntityItem in the world for the given ItemStack if the world is not remote.
     */
    protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float var6 = 0.7F;
            double var7 = (double)(par1World.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var9 = (double)(par1World.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var11 = (double)(par1World.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            EntityItem var13 = new EntityItem(par1World, (double)par2 + var7, (double)par3 + var9, (double)par4 + var11, par5ItemStack);
            var13.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(var13);
        }
    }

    /**
     * called by spawner, ore, redstoneOre blocks
     */
    protected void dropXpOnBlockBreak(World par1World, int par2, int par3, int par4, int par5)
    {
    	// FCMOD: Code removed
    	/*
        if (!par1World.isRemote)
        {
            while (par5 > 0)
            {
                int var6 = EntityXPOrb.getXPSplit(par5);
                par5 -= var6;
                par1World.spawnEntityInWorld(new EntityXPOrb(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, var6));
            }
        }
        */
    	// END FCMOD
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return 0;
    }

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float getExplosionResistance(Entity par1Entity)
    {
        return this.blockResistance / 5.0F;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    // FCMOD: Removed and replaced
    /*
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        par5Vec3 = par5Vec3.addVector((double)(-par2), (double)(-par3), (double)(-par4));
        par6Vec3 = par6Vec3.addVector((double)(-par2), (double)(-par3), (double)(-par4));
        Vec3 var7 = par5Vec3.getIntermediateWithXValue(par6Vec3, this.minX);
        Vec3 var8 = par5Vec3.getIntermediateWithXValue(par6Vec3, this.maxX);
        Vec3 var9 = par5Vec3.getIntermediateWithYValue(par6Vec3, this.minY);
        Vec3 var10 = par5Vec3.getIntermediateWithYValue(par6Vec3, this.maxY);
        Vec3 var11 = par5Vec3.getIntermediateWithZValue(par6Vec3, this.minZ);
        Vec3 var12 = par5Vec3.getIntermediateWithZValue(par6Vec3, this.maxZ);

        if (!this.isVecInsideYZBounds(var7))
        {
            var7 = null;
        }

        if (!this.isVecInsideYZBounds(var8))
        {
            var8 = null;
        }

        if (!this.isVecInsideXZBounds(var9))
        {
            var9 = null;
        }

        if (!this.isVecInsideXZBounds(var10))
        {
            var10 = null;
        }

        if (!this.isVecInsideXYBounds(var11))
        {
            var11 = null;
        }

        if (!this.isVecInsideXYBounds(var12))
        {
            var12 = null;
        }

        Vec3 var13 = null;

        if (var7 != null && (var13 == null || par5Vec3.squareDistanceTo(var7) < par5Vec3.squareDistanceTo(var13)))
        {
            var13 = var7;
        }

        if (var8 != null && (var13 == null || par5Vec3.squareDistanceTo(var8) < par5Vec3.squareDistanceTo(var13)))
        {
            var13 = var8;
        }

        if (var9 != null && (var13 == null || par5Vec3.squareDistanceTo(var9) < par5Vec3.squareDistanceTo(var13)))
        {
            var13 = var9;
        }

        if (var10 != null && (var13 == null || par5Vec3.squareDistanceTo(var10) < par5Vec3.squareDistanceTo(var13)))
        {
            var13 = var10;
        }

        if (var11 != null && (var13 == null || par5Vec3.squareDistanceTo(var11) < par5Vec3.squareDistanceTo(var13)))
        {
            var13 = var11;
        }

        if (var12 != null && (var13 == null || par5Vec3.squareDistanceTo(var12) < par5Vec3.squareDistanceTo(var13)))
        {
            var13 = var12;
        }

        if (var13 == null)
        {
            return null;
        }
        else
        {
            byte var14 = -1;

            if (var13 == var7)
            {
                var14 = 4;
            }

            if (var13 == var8)
            {
                var14 = 5;
            }

            if (var13 == var9)
            {
                var14 = 0;
            }

            if (var13 == var10)
            {
                var14 = 1;
            }

            if (var13 == var11)
            {
                var14 = 2;
            }

            if (var13 == var12)
            {
                var14 = 3;
            }

            return new MovingObjectPosition(par2, par3, par4, var14, var13.addVector((double)par2, (double)par3, (double)par4));
        }
    }

    private boolean isVecInsideYZBounds(Vec3 par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ;
    }

    private boolean isVecInsideXZBounds(Vec3 par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ;
    }

    private boolean isVecInsideXYBounds(Vec3 par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY;
    }
    */
    // END FCMOD

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {}

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
//    public int getRenderBlockPass()
//    {
//        return 0;
//    }

    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5, ItemStack par6ItemStack)
    {
        return this.canPlaceBlockOnSide(par1World, par2, par3, par4, par5);
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockId(par2, par3, par4);
        return var5 == 0 || blocksList[var5].blockMaterial.isReplaceable();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return false;
    }

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {}

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        return par9;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {}

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {}

    /** FCNOTE: DEPRECATED */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {}

    /**
     * returns the block bounderies minX value
     */
    //was previously getBlockBoundsMinX
    public final double getMinX()
    {
        return this.minX;
    }

    /**
     * returns the block bounderies maxX value
     */
    public final double getBlockBoundsMaxX()
    {
        return this.maxX;
    }

    /**
     * returns the block bounderies minY value
     */
    public final double getBlockBoundsMinY()
    {
        return this.minY;
    }

    /**
     * returns the block bounderies maxY value
     */
    public final double getBlockBoundsMaxY()
    {
        return this.maxY;
    }

    /**
     * returns the block bounderies minZ value
     */
    public final double getBlockBoundsMinZ()
    {
        return this.minZ;
    }

    /**
     * returns the block bounderies maxZ value
     */
    public final double getBlockBoundsMaxZ()
    {
        return this.maxZ;
    }

//    public int getBlockColor()
//    {
//        return ColorizeBlock.colorizeBlock(this) ? ColorizeBlock.blockColor : 16777215;
//    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
//    public int getRenderColor(int par1)
//    {
//        return ColorizeBlock.colorizeBlock(this, par1) ? ColorizeBlock.blockColor : 16777215;
//    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
//    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        return ColorizeBlock.colorizeBlock(this, par1IBlockAccess, par2, par3, par4) ? ColorizeBlock.blockColor : 16777215;
//    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return 0;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return false;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {}

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return 0;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender() {}

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     * FCNOTE: Only called on server
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);

        // FCMOD: Changed
        /*
        par2EntityPlayer.addExhaustion(0.025F);
        
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
        */        
        par2EntityPlayer.AddHarvestBlockExhaustion( blockID, par3, par4, par5, par6 );        
        
        if ( this.canSilkHarvest( par6 ) && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
    	// END FCMOD
        {
            ItemStack var8 = this.createStackedBlock(par6);

            if (var8 != null)
            {
                this.dropBlockAsItem_do(par1World, par3, par4, par5, var8);
            }
        }
        else
        {
            int var7 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
            this.dropBlockAsItem(par1World, par3, par4, par5, par6, var7);
        }
    }

    /**
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean canSilkHarvest()
    {
        return this.renderAsNormalBlock() && !this.isBlockContainer;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     * FCNOTE: This is the function used to create the silk-touch drop
     */
    protected ItemStack createStackedBlock(int par1)
    {
        int var2 = 0;

        if (this.blockID >= 0 && this.blockID < Item.itemsList.length && Item.itemsList[this.blockID].getHasSubtypes())
        {
            var2 = par1;
        }

        return new ItemStack(this.blockID, 1, var2);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        return this.quantityDropped(par2Random);
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * Called when the block is placed in the world.
     * FCNOTE: Called AFTER the block is placed, unlike onBlockPlaced()
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack) {}

    /**
     * Called after a block is placed
     */
    public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {}

    public Block setUnlocalizedName(String par1Str)
    {
        this.unlocalizedName = par1Str;
        return this;
    }

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    /**
     * Returns the unlocalized name of this block.
     */
    public String getUnlocalizedName()
    {
        return "tile." + this.unlocalizedName;
    }

//    /**
//     * Returns the unlocalized name without the tile. prefix. Caution: client-only.
//     */
//    public String getUnlocalizedName2()
//    {
//        return this.unlocalizedName;
//    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        return false;
    }

    /**
     * Return the state of blocks statistics flags - if the block is counted for mined and placed.
     */
    public boolean getEnableStats()
    {
        return this.enableStats;
    }

    /**
     * Disable statistics for the block, the block will no count for mined or placed.
     */
    protected Block disableStats()
    {
        this.enableStats = false;
        return this;
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     * FCMOD: 3 = can be piston shoveled, but free otherwise 
     */
    public int getMobilityFlag()
    {
        return this.blockMaterial.getMaterialMobility();
    }

    /**
     * Returns the default ambient occlusion value based on block opacity
     */
//    public float getAmbientOcclusionLightValue(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        return par1IBlockAccess.isBlockNormalCube(par2, par3, par4) ? 0.2F : 1.0F;
//    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6) {}

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     * FCNOTE: Client only
     */
//    public int idPicked(World par1World, int par2, int par3, int par4)
//    {
//        return this.blockID;
//    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    /**
     * Returns the CreativeTab to display the given block on.
     */
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return this.displayOnCreativeTab;
    }

    /**
     * Sets the CreativeTab to display this block on.
     */
    public Block setCreativeTab(CreativeTabs par1CreativeTabs)
    {
        this.displayOnCreativeTab = par1CreativeTabs;
        return this;
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {}

    /**
     * Called when this block is set (with meta data).
     */
    public void onSetBlockIDWithMetaData(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * currently only used by BlockCauldron to incrament meta-data during rain
     */
    public void fillWithRain(World par1World, int par2, int par3, int par4) {}

    /**
     * Returns true only if block is flowerPot
     */
    public boolean isFlowerPot()
    {
        return false;
    }

    public boolean func_82506_l()
    {
        return true;
    }

    /**
     * Return whether this block can drop from an explosion.
     */
    public boolean canDropFromExplosion(Explosion par1Explosion)
    {
        return true;
    }

    /**
     * Returns true if the given block ID is equivalent to this one. Example: redstoneTorchOn matches itself and
     * redstoneTorchOff, and vice versa. Most blocks only match themselves.
     */
    public boolean isAssociatedBlockID(int par1)
    {
        return this.blockID == par1;
    }

    /**
     * Static version of isAssociatedBlockID.
     */
    public static boolean isAssociatedBlockID(int par0, int par1)
    {
        return par0 == par1 ? true : (par0 != 0 && par1 != 0 && blocksList[par0] != null && blocksList[par1] != null ? blocksList[par0].isAssociatedBlockID(par1) : false);
    }

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    public boolean hasComparatorInputOverride()
    {
        return false;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return 0;
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
//    public void registerIcons(IconRegister par1IconRegister)
//    {
//        this.blockIcon = par1IconRegister.registerIcon(this.unlocalizedName);
//    }

    /**
     * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
     */
//    public String getItemIconName()
//    {
//        return null;
//    }

    static
    {
        Item.itemsList[cloth.blockID] = (new ItemCloth(cloth.blockID - 256)).setUnlocalizedName("cloth");
        Item.itemsList[wood.blockID] = (new ItemMultiTextureTile(wood.blockID - 256, wood, BlockLog.woodType)).setUnlocalizedName("log");
        Item.itemsList[planks.blockID] = (new ItemMultiTextureTile(planks.blockID - 256, planks, BlockWood.woodType)).setUnlocalizedName("wood");
        Item.itemsList[silverfish.blockID] = (new ItemMultiTextureTile(silverfish.blockID - 256, silverfish, BlockSilverfish.silverfishStoneTypes)).setUnlocalizedName("monsterStoneEgg");
        Item.itemsList[stoneBrick.blockID] = (new ItemMultiTextureTile(stoneBrick.blockID - 256, stoneBrick, BlockStoneBrick.STONE_BRICK_TYPES)).setUnlocalizedName("stonebricksmooth");
        Item.itemsList[sandStone.blockID] = (new ItemMultiTextureTile(sandStone.blockID - 256, sandStone, BlockSandStone.SAND_STONE_TYPES)).setUnlocalizedName("sandStone");
        Item.itemsList[blockNetherQuartz.blockID] = (new ItemMultiTextureTile(blockNetherQuartz.blockID - 256, blockNetherQuartz, BlockQuartz.quartzBlockTypes)).setUnlocalizedName("quartzBlock");
        Item.itemsList[stoneSingleSlab.blockID] = (new ItemSlab(stoneSingleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, false)).setUnlocalizedName("stoneSlab");
        Item.itemsList[stoneDoubleSlab.blockID] = (new ItemSlab(stoneDoubleSlab.blockID - 256, stoneSingleSlab, stoneDoubleSlab, true)).setUnlocalizedName("stoneSlab");
        Item.itemsList[woodSingleSlab.blockID] = (new ItemSlab(woodSingleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, false)).setUnlocalizedName("woodSlab");
        Item.itemsList[woodDoubleSlab.blockID] = (new ItemSlab(woodDoubleSlab.blockID - 256, woodSingleSlab, woodDoubleSlab, true)).setUnlocalizedName("woodSlab");
        Item.itemsList[sapling.blockID] = (new ItemMultiTextureTile(sapling.blockID - 256, sapling, BlockSapling.WOOD_TYPES)).setUnlocalizedName("sapling");
        Item.itemsList[leaves.blockID] = (new ItemLeaves(leaves.blockID - 256)).setUnlocalizedName("leaves");
        Item.itemsList[vine.blockID] = new ItemColored(vine.blockID - 256, false);
        Item.itemsList[tallGrass.blockID] = (new ItemColored(tallGrass.blockID - 256, true)).setBlockNames(new String[] {"shrub", "grass", "fern"});
        Item.itemsList[snow.blockID] = new ItemSnow(snow.blockID - 256, snow);
        Item.itemsList[waterlily.blockID] = new ItemLilyPad(waterlily.blockID - 256);
        Item.itemsList[pistonBase.blockID] = new ItemPiston(pistonBase.blockID - 256);
        Item.itemsList[pistonStickyBase.blockID] = new ItemPiston(pistonStickyBase.blockID - 256);
        Item.itemsList[cobblestoneWall.blockID] = (new ItemMultiTextureTile(cobblestoneWall.blockID - 256, cobblestoneWall, BlockWall.types)).setUnlocalizedName("cobbleWall");
        Item.itemsList[anvil.blockID] = (new ItemAnvilBlock(anvil)).setUnlocalizedName("anvil");

        for (int var0 = 0; var0 < 256; ++var0)
        {
            if (blocksList[var0] != null)
            {
                if (Item.itemsList[var0] == null)
                {
                    Item.itemsList[var0] = new ItemBlock(var0 - 256);
                    blocksList[var0].initializeBlock();
                }

                // FCMOD: Changed to not overwrite settings in block classes and to instead set
                // useNeighborBrightness[] in block classes for most cases instead.
                /*
                boolean var1 = false;

                // FCNOTE: renderType 10 is used by vanilla stairs
                if (var0 > 0 && blocksList[var0].getRenderType() == 10)
                {
                    var1 = true;
                }

                if (var0 > 0 && blocksList[var0] instanceof BlockHalfSlab)
                {
                    var1 = true;
                }

                if (var0 == tilledField.blockID)
                {
                    var1 = true;
                }

                if (canBlockGrass[var0])
                {
                    var1 = true;
                }

                if (lightOpacity[var0] == 0)
                {
                    var1 = true;
                }

                useNeighborBrightness[var0] = var1;
                */
                if ( canBlockGrass[var0] || lightOpacity[var0] == 0 )
                {
                	useNeighborBrightness[var0] = true;
                }
                // END FCMOD
            }
        }

        canBlockGrass[0] = true;
        StatList.initBreakableStats();
    }
    
    // FCMOD: Added New
	private static final int[] m_iRotatedFacingsAroundJClockwise = 
		new int[] { 0, 1, 4, 5, 3, 2 };
	
	private static final int[] m_iRotatedFacingsAroundJCounterclockwise = 
		new int[] { 0, 1, 5, 4, 2, 3 };
	
	private static final int[] m_iCycledFacings = 
		new int[] { 4, 0, 1, 5, 3, 2 };
	
	private static final int[] m_iCycledFacingsReversed = 
		new int[] { 1, 2, 5, 4, 0, 3 };
	
    public boolean IsNormalCube( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return blockMaterial.isOpaque() && renderAsNormalBlock();
    }
    
    /**
     * Similar to onBlockPlacedBy() but called before the block is placed rather than after
     */
    public int PreBlockPlacedBy( World world, int i, int j, int k, int iMetadata, EntityLiving entityBy ) 
    {
    	return iMetadata;    	
    }

    public void SetBlockMaterial( Material material )
    {
    	blockMaterial = material;
    	
        canBlockGrass[blockID] = !material.getCanBlockGrass();    	
    }
    
    /**
     * Called on server only
     */
    public void RandomUpdateTick( World world, int i, int j, int k, Random rand )
    {
    	updateTick( world, i, j, k, rand );
    }
    
	public void ClientNotificationOfMetadataChange( World world, int i, int j, int k, int iOldMetadata, int iNewMetadata )
	{
	}
	
    public void OnArrowImpact( World world, int i, int j, int k, EntityArrow arrow )
    {
    }
    
    public void OnArrowCollide( World world, int i, int j, int k, EntityArrow arrow )
    {
    }
    
    public float GetMovementModifier( World world, int i, int j, int k )
    {
    	float fModifier = 1.0F;
    	
		if ( blockMaterial != Material.ground && blockMaterial != Material.grass )
		{
			fModifier *= 1.2F;
		}
    	
    	return fModifier;
    }
    
    public void OnPlayerWalksOnBlock( World world, int i, int j, int k, EntityPlayer player )
    {    
    	{  
        	//AARON re-enabled some sinkhole behavior specifically related to weak blocks
        	//in theory, you can create a pitfall trap
        	if ( !SuperBTWDefinitions.doesBlockTriggerWeaklingFall(world.getBlockId(i,j,k)) &&
        			 SuperBTWDefinitions.isWeakBlock(world.getBlockId(i, j-1, k)))
        	{
//        		System.out.println("Standing on non-trigger block, weak block beneath!");
        		//this code works by converting the falling block to metadata 10, which triggers a fall in updateTick()
        		world.setBlockMetadataWithNotify(i, j-1, k, 10);

        		//these if statements check all adjacent blocks for traps, including 
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i+1, j-1, k)))
        		{
        			world.setBlockMetadataWithNotify(i+1, j-1, k, 10);
        		}
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i-1, j-1, k)))
        		{
        			world.setBlockMetadataWithNotify(i-1, j-1, k, 10);
        		}
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i, j-1, k+1)))
        		{
        			world.setBlockMetadataWithNotify(i, j-1, k+1, 10);
        		}
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i, j-1, k-1)))
        		{
        			world.setBlockMetadataWithNotify(i, j-1, k-1, 10);
        		}

        		//diagnilly, blocks drop in a 3x3 centered on the stepped-on block
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i-1, j-1, k-1)))
        		{
        			world.setBlockMetadataWithNotify(i-1, j-1, k-1, 10);
        		}
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i+1, j-1, k-1)))
        		{
        			world.setBlockMetadataWithNotify(i+1, j-1, k-1, 10);
        		}
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i+1, j-1, k+1)))
        		{
        			world.setBlockMetadataWithNotify(i+1, j-1, k+1, 10);
        		}
        		if (SuperBTWDefinitions.isWeakBlock(world.getBlockId(i-1, j-1, k+1)))
        		{
        			world.setBlockMetadataWithNotify(i-1, j-1, k+1, 10);
        		}
        	}
    	}

    	
		// Disabled Hardcore sinkholes
    	/*
    	if ( IsFallingBlock( world, i, j, k ) )
    	{
    		CheckForUnstableGround( world, i, j, k );
    	}
    	*/
    }
    	
    //AARON added this method to signify whether a block can be busted down by a zombie
    public boolean isWeakBlock(World worldObj, int x, int y, int z) 
    {
    	return false;
    }    	
    
    /**
     * Applies to Hopper ejecting items into the world, not inserting into the block itself
     */
    public boolean DoesBlockHopperEject( World world, int i, int j, int k )
    {
    	return blockMaterial.isSolid();
    }

    /**
     * Applies to Hopper inserting items directly into the block's inventory.  This
     * does not ensure the block has a valid inventory, it's just a first-pass chance
     * to block such behavior.
     */
    public boolean DoesBlockHopperInsert( World world, int i, int j, int k )
    {
    	return false;
    }
    
    /**
     * Returns true if the block is warm enough to melt nearby snow or ice
     */
    public boolean GetIsBlockWarm( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }
    
    public StepSound GetStepSound( World world, int i, int j, int k )
    {
    	return stepSound;
    }
    
    public void ClientBreakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
    }
    
    public void ClientBlockAdded( World world, int i, int j, int k )
    {
    }
    
    public boolean HasStrata()
    {
    	return false;
    }
    
    public int GetMetadataConversionForStrataLevel( int iLevel, int iMetadata )
    {
    	return iMetadata;
    }
    
    public float getExplosionResistance( Entity entity, World world, int i, int j, int k )
    {
        return getExplosionResistance( entity );
    }
    
    public boolean CanBlockStayDuringGenerate( World world, int i, int j, int k )
    {
    	// breaking this off into a separate function so that we can prevent certain blocks (like mushrooms) spawning under certain conditions without messing up worldgen or
    	// its usual growth conditions
    	
    	// NOTE: This function isn't called for all types of WorldGen to avoid excessive base class changes where not needed.  If you want to override it, make sure it is called
    	// appropriately for the block in question first.
    	
    	return canBlockStay( world, i, j, k );
    }
    
    /**
     * Used to determine if this is a stair block for purposes of connecting visually to others
     */
    public boolean IsStairBlock()
    {
    	return false;
    }
    
    public boolean ShouldDeleteTileEntityOnBlockChange( int iNewBlockID )
    {
    	return true;
    }

    /** 
     * Determines whether other stone will "connect" to this block for purposes of determing whether
     * a stone block can be individually harvested without breaking apart
     */
    public boolean IsNaturalStone( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }

    static public AxisAlignedBB GetFulBlockBoundingBoxFromPool( World world, int i, int j, int k )
    {
    	return AxisAlignedBB.getAABBPool().getAABB(
    		(float)i, (float)j, (float)k, 
    		(float)i + 1.0F, (float)j + 1.0F, (float)k + 1.0F );	    	
    }
    
    public boolean CanSpitWebReplaceBlock( World world, int i, int j, int k )
    {
    	return IsGroundCover( ) || IsAirBlock();
    }
    
    public boolean IsAirBlock()
    {
    	return false;
    }
    
    public boolean IsReplaceableVegetation( World world, int i, int j, int k )
    {
    	return false;
    }
    
	public boolean HasWaterToSidesOrTop( World world, int i, int j, int k )
	{
		for ( int iFacing = 1; iFacing <= 5; iFacing++ )
		{		
			FCUtilsBlockPos tempPos = new FCUtilsBlockPos( i, j, k, iFacing );
		
			int iTempBlockID = world.getBlockId( tempPos.i, tempPos.j, tempPos.k );
			Block tempBlock = Block.blocksList[iTempBlockID];
			
			if ( tempBlock != null && tempBlock.blockMaterial == Material.water )
			{
				return true;
			}			
		}
		
		return false;
	}
	
	public boolean GetPreventsFluidFlow( World world, int i, int j, int k, Block fluidBlock )
	{
        return blockMaterial == Material.portal ? true : blockMaterial.blocksMovement();
	}

	public void OnFluidFlowIntoBlock( World world, int i, int j, int k, BlockFluid fluidBlock )
	{
        dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
	}

	public boolean IsBlockClimbable( World world, int i, int j, int k )
	{
		return false;
	}

	/** 
	 * Whether or not the block sets off Buddy Blocks.  Set to false for stuff like redstone blocks
	 * that can cause feedback loops.
	 */
	public boolean TriggersBuddy()
	{
		return true;
	}
	
    //------------ Addon interfacing related functionality ----------//
	
    /**
     * Replaces a reference to an existing block (vanilla or btw)
     * @param id The block id to be replaced
     * @param newClass The class of the new block
     * @param parameters Optional additional parameters to pass to the block, not including the id.
     */
    public static Block replaceBlock(int id, Class newClass, Object ... parameters) {
    	if (blockReplaced[id]) {
    		throw new RuntimeException("Multiple addons attempting to replace block " + blocksList[id]);
    	}
    	else {
    		Block newBlock = null;
    		
    		Class[] parameterTypes = new Class[parameters.length + 1];
    		Object[] parameterValues = new Object[parameters.length + 1];
    		
    		parameterTypes[0] = Integer.TYPE;
    		parameterValues[0] = id;

    		Block original = blocksList[id];
    		blocksList[id] = null;
    		
    		for (int i = 0; i < parameters.length; i++) {
    			Class<?> type = parameters[i].getClass();
    			
    			if (type == Integer.class) {
    				type = Integer.TYPE;
    			}
    			else if (type == Boolean.class) {
    				type = Boolean.TYPE;
    			}
    			else if (type == Float.class) {
    				type = Float.TYPE;
    			}
    			else if (type == Double.class) {
    				type = Double.TYPE;
    			}
    			else if (Block.class.isAssignableFrom(type)) {
    				type = Block.class;
    			}
    			else if (Item.class.isAssignableFrom(type)) {
    				type = Item.class;
    			}
    			else if (Material.class.isAssignableFrom(type)) {
    				type = Material.class;
    			}
    			
    			parameterTypes[i + 1] = type;
    			parameterValues[i + 1] = parameters[i];
    		}
    		
    		try {
    			newBlock = (Block) newClass.getConstructor(parameterTypes).newInstance(parameterValues);
    		} catch (InstantiationException e) {
    			throw new RuntimeException("A problem has occured attempting to instantiate replacement for " + blocksList[id]);
    		} catch (IllegalArgumentException e) {
    			throw new RuntimeException("Incompatible types passed to specified constructor for " + blocksList[id]);
    		} catch (NoSuchMethodException e) {
    			throw new RuntimeException("No appropriate constructor found for " + blocksList[id] + ". Constructors must be public to be used in replacement.");
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		blockReplaced[id] = true;
    		
    		newBlock.setHardness(original.blockHardness).setResistance(original.blockResistance).setStepSound(original.stepSound).setUnlocalizedName(original.unlocalizedName).setCreativeTab(original.getCreativeTabToDisplayOn());
    		if (!original.enableStats)
    			newBlock.disableStats();
    		
    		blocksList[id] = newBlock;
    		
    		return newBlock;
    	}
    }
	
    //------------ Harvesting related functionality ----------//
    
    protected boolean canSilkHarvest( int iMetadata )
    {
    	return canSilkHarvest();
    }
    
    /**
     * Called on server only, after the block is removed from the world
     */
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
        world.playAuxSFX( FCBetterThanWolves.m_iBlockDestroyedWithImproperToolAuxFXID, i, j, k, blockID + ( iMetadata << 12 ) );
    	
    	DropComponentItemsOnBadBreak( world, i, j, k, iMetadata, 1F );
    }
    
    protected void DropItemsIndividualy( World world, int i, int j, int k, int iIDDropped, int iPileCount, int iDamageDropped, float fChanceOfPileDrop )
	{
    	for ( int iTempCount = 0; iTempCount < iPileCount; iTempCount++ )
    	{
    		if ( world.rand.nextFloat() <= fChanceOfPileDrop )
    		{
	    		ItemStack stack = new ItemStack( iIDDropped, 1, iDamageDropped );
	    		
	            dropBlockAsItem_do( world, i, j, k, stack );
    		}
    	}
	}
    
    /**
     * Called by explosions and improper tool use.  
     * Should return true if the block processes its own drops through this method, false otherwise
     * Note that the block may no longer be at the specified position when this is called
     */
    public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
    {
    	return false;
    }
    
    /**
     * Explosion may be null if this is called by a mining charge
     */
    public void DropItemsOnDestroyedByExplosion( World world, int i, int j, int k, Explosion explosion )
    {
        if ( !world.isRemote && canDropFromExplosion( explosion ) )
        {
        	float fChance = 1F;
        	
        	if ( explosion != null )
        	{
        		fChance /= explosion.explosionSize;
        	}
        	
        	int iMetadata = world.getBlockMetadata( i, j, k );

        	if ( !DropComponentItemsOnBadBreak( world, i, j, k, iMetadata, fChance ) )
        	{
        		dropBlockAsItemWithChance( world, i, j, k, iMetadata, fChance, 0 );
        	}
        }
    }

    /**
     * Notifies neighbors of dirt blocks that they should be loosened
     */
    protected void OnDirtDugWithImproperTool( World world, int i, int j, int k )
    {
    	for ( int iTempFacing = 0; iTempFacing < 6; iTempFacing++ )
    	{
    		NotifyNeighborDirtDugWithImproperTool( world, i, j, k, iTempFacing );    		    		
    	}
    }
    
    protected void OnDirtSlabDugWithImproperTool( World world, int i, int j, int k, 
    	boolean bUpsideDown )
    {
    	for ( int iTempFacing = 0; iTempFacing < 6; iTempFacing++ )
    	{
	        if ( !( bUpsideDown && iTempFacing == 0 ) && !( !bUpsideDown && iTempFacing == 1 ) )
	        {
	        	NotifyNeighborDirtDugWithImproperTool( world, i, j, k, iTempFacing );
	        }
    	}
    }
    
    protected void NotifyNeighborDirtDugWithImproperTool( World world, int i, int j, int k, 
    	int iToFacing )
    {
		FCUtilsBlockPos neighborPos = new FCUtilsBlockPos( i, j, k, iToFacing );
		
		int iTargetBlockID = world.getBlockId( neighborPos.i, neighborPos.j, neighborPos.k );
		
		Block targetBlock = blocksList[iTargetBlockID];
		
		if ( targetBlock != null )
		{    	
			targetBlock.OnNeighborDirtDugWithImproperTool( world, 
				neighborPos.i, neighborPos.j, neighborPos.k, GetOppositeFacing( iToFacing ) );
		}    		
    }
    
    protected void OnNeighborDirtDugWithImproperTool( World world, int i, int j, int k, 
    	int iToFacing )
    {
    }
    
    //------------ Hard Point related functionality ----------//
    
	/**
	 * small attachment surfaces, like those required for the bottom of a torch (approx 1/8 block width)
	 */
	public boolean HasSmallCenterHardPointToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency )
	{
		return HasCenterHardPointToFacing( blockAccess, i, j, k, iFacing, bIgnoreTransparency );
	}
	
	public boolean HasSmallCenterHardPointToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return HasSmallCenterHardPointToFacing( blockAccess, i, j, k, iFacing, false );
	}

	/**
	 * medium sized attachment points like the top of fence posts (approx 1/4 block width)
	 */
	public boolean HasCenterHardPointToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency )
	{
		return HasLargeCenterHardPointToFacing( blockAccess, i, j, k, iFacing, bIgnoreTransparency );
	}

	public boolean HasCenterHardPointToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return HasCenterHardPointToFacing( blockAccess, i, j, k, iFacing, false );
	}

	/**
	 * large attachment points that can support a full block width
	 */	
	public boolean HasLargeCenterHardPointToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency )
	{
		return blockAccess.isBlockNormalCube( i, j, k );
	}

	public boolean HasLargeCenterHardPointToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return HasLargeCenterHardPointToFacing( blockAccess, i, j, k, iFacing, false );
	}
	
	/**
	 * returns true if the block is sitting on the one below, like a torch resting on the ground
	 */
	public boolean IsBlockRestingOnThatBelow( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}

	/**
	 * returns true if block is attached to a block in a particular direction.  Example: pumpkins attached to stems
	 */
	public boolean IsBlockAttachedToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return false;
	}
	
	public void AttachToFacing( World world, int i, int j, int k, int iFacing )
	{
	}
	
	public boolean HasContactPointToFullFace( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return blockAccess.isBlockNormalCube( i, j, k );
	}
	
	public boolean HasContactPointToSlabSideFace( IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIsSlabUpsideDown )
	{
		return HasContactPointToFullFace( blockAccess, i, j, k, iFacing );
	}
	
	/**
	 * This method refers to the 'L' shaped sides of stair blocks.  Other stair facings will refernce either the full face, stair top,
	 * or slab methods, depending on their shape
	 */
	public boolean HasContactPointToStairShapedFace( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return HasContactPointToFullFace( blockAccess, i, j, k, iFacing );
	}
	
	/**
	 * This method refers to the half-block shaped top or bottom of stair blocks.
	 */
	public boolean HasContactPointToStairNarrowVerticalFace( IBlockAccess blockAccess, int i, int j, int k, int iFacing, int iStairFacing )
	{
		return HasContactPointToFullFace( blockAccess, i, j, k, iFacing );
	}
	
	/**
	 * Should return true if mortar has been successfully applied to block.
	 */
	public boolean OnMortarApplied( World world, int i, int j, int k )
	{
		return false;
	}
	
	public boolean HasMortar( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
	
    public boolean HasNeighborWithMortarInContact( World world, int i, int j, int k )
    {
    	for ( int iTempFacing = 0; iTempFacing < 6; iTempFacing++ )
    	{
    		if ( FCUtilsWorld.HasNeighborWithMortarInFullFaceContactToFacing( world, i, j, k, iTempFacing ) )
			{
				return true;
			}
    	}
    	
    	return false;
    }
    
	public boolean IsStickyToSnow( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
	
    public boolean HasStickySnowNeighborInContact( World world, int i, int j, int k )
    {
    	for ( int iTempFacing = 0; iTempFacing < 6; iTempFacing++ )
    	{
    		if ( FCUtilsWorld.HasStickySnowNeighborInFullFaceContactToFacing( world, i, j, k, iTempFacing ) )
			{
				return true;
			}
    	}
    	
    	return false;
    }
    
    //--------------- Fire related functionality -------------//
    
    private int m_iDefaultFurnaceBurnTime = 0;    
    
    public int GetFurnaceBurnTime( int iItemDamage )
    {
    	return m_iDefaultFurnaceBurnTime;
    }
    
    public void SetFurnaceBurnTime( int iBurnTime )
    {
    	m_iDefaultFurnaceBurnTime = iBurnTime;
    }
    
    public void SetFurnaceBurnTime( FCEnumFurnaceBurnTime burnTime )
    {
    	SetFurnaceBurnTime( burnTime.m_iBurnTime );
    }
    
    public boolean DoesInfiniteBurnToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
    {
    	return false;
    }
    
    public boolean DoesExtinguishFireAbove( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public void OnDestroyedByFire( World world, int i, int j, int k, int iFireAge, boolean bForcedFireSpread )
    {
        if ( bForcedFireSpread || ( world.rand.nextInt( iFireAge + 10 ) < 5 && 
        	!world.IsRainingAtPos( i, j, k ) ) )
        {
            int iNewFireMetadata = iFireAge + world.rand.nextInt( 5 ) / 4;

            if ( iNewFireMetadata > 15 )
            {
                iNewFireMetadata = 15;
            }

            world.setBlockAndMetadataWithNotify( i, j, k, Block.fire.blockID, iNewFireMetadata );
        }
        else
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
    }    
    
    public Block SetFireProperties( int iChanceToEncourageFire, int iAbilityToCatchFire )
    {
    	BlockFire.chanceToEncourageFire[blockID] = iChanceToEncourageFire;
    	BlockFire.abilityToCatchFire[blockID] = iAbilityToCatchFire;
    	
    	return this;
    }
    
    public Block SetFireProperties( FCEnumFlammability flammability )
    {
    	return SetFireProperties( flammability.m_iChanceToEncourageFire, 
    		flammability.m_iAbilityToCatchFire );
    }
    
    /**
     * Whether the block itself can be set on fire, rather than a neighboring block being set to a fire block
     */
    public boolean GetCanBeSetOnFireDirectly( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean GetCanBeSetOnFireDirectlyByItem( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetCanBeSetOnFireDirectly( blockAccess, i, j, k );
    }
    
    public boolean SetOnFireDirectly( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public int GetChanceOfFireSpreadingDirectlyTo( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 0;
    }
    
    public boolean GetCanBlockLightItemOnFire( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean GetDoesFireDamageToEntities( World world, int i, int j, int k, Entity entity )
    {
    	return GetDoesFireDamageToEntities( world, i, j, k );
    }
    
    public boolean GetDoesFireDamageToEntities( World world, int i, int j, int k )
    {
    	return false;
    }
    
    /**
     * Used by Hibachi to determine if it can remove the block above it when lit
     */
    public boolean GetCanBlockBeIncinerated( World world, int i, int j, int k )
    {
		return Block.fire.canBlockCatchFire( world, i, j, k ) || !blockMaterial.blocksMovement();
    }
    
    /** 
     * Whether a fire block can be directly placed over this one, without first burning or catching fire, as if it were air.
     */
    public boolean GetCanBlockBeReplacedByFire( World world, int i, int j, int k )
    {
    	return IsAirBlock();
    }
    
    public boolean IsIncineratedInCrucible()
    {
    	return FCBlockFire.CanBlockBeDestroyedByFire( blockID );
    }
    
    //------------- Pathing related functionality ------------//
    
    public boolean CanPathThroughBlock( IBlockAccess blockAccess, int i, int j, int k, 
    	Entity entity, PathFinder pathFinder )
    {
		// note: getBlocksMovement() is misnamed and returns if the block *doesn't* block movement
    	
    	return getBlocksMovement( blockAccess, i, j, k );
    }

    /**
     * Used to determine if entities who start their pathing from within this block
     * should instead start pathing from a neighbor block instead, to prevent getting stuck
     * in this one.  Mostly applies to stuff like chickens getting stuck in fences.
     */
    public boolean ShouldOffsetPositionIfPathingOutOf( IBlockAccess blockAccess, 
    	int i, int j, int k, Entity entity, PathFinder pathFinder )
    {
    	return !CanPathThroughBlock( blockAccess, i, j, k, entity, pathFinder );
    }
    
    public int GetWeightOnPathBlocked( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 0;
    }    
    
    public int AdjustPathWeightOnNotBlocked( int iPreviousWeight )
    {
    	return iPreviousWeight;
    }
    
    public boolean IsBreakableBarricade( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }

    public boolean IsBreakableBarricadeOpen( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }
    
    //------------- Kiln related functionality ------------//
    
    private boolean m_bCanBeCookedByKiln = false;
    private int m_iItemIndexDroppedWhenCookedByKiln = -1;
    private int m_iItemDamageDroppedWhenCookedByKiln = 0;
    
    public Block SetCanBeCookedByKiln( boolean bCanBeCooked )
    {
    	m_bCanBeCookedByKiln = bCanBeCooked;
    	
    	return this;
    }
    
    public boolean GetCanBeCookedByKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return m_bCanBeCookedByKiln;
    }    
    
    public int GetCookTimeMultiplierInKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 1;
    }    
    
    public Block SetItemIndexDroppedWhenCookedByKiln( int iItemIndex )
    {
    	m_iItemIndexDroppedWhenCookedByKiln = iItemIndex;
    	
    	return this;
    }
    
    public int GetItemIndexDroppedWhenCookedByKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return m_iItemIndexDroppedWhenCookedByKiln;
    }
    
    public Block SetItemDamageDroppedWhenCookedByKiln( int iItemDamage )
    {
    	m_iItemDamageDroppedWhenCookedByKiln = iItemDamage;
    	
    	return this;
    }
    
    public int GetItemDamageDroppedWhenCookedByKiln( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return m_iItemDamageDroppedWhenCookedByKiln;
    }
    
    public void OnCookedByKiln( World world, int i, int j, int k )
    {
    	int iItemDropped = GetItemIndexDroppedWhenCookedByKiln( world, i, j, k );
    	
    	if ( iItemDropped >= 0 )
    	{
    		int iDamageDropped = GetItemDamageDroppedWhenCookedByKiln( world, i, j, k );
    		
        	world.setBlockToAir( i, j, k );

        	if ( iItemDropped > 0 )
        	{
        		FCUtilsItem.EjectSingleItemWithRandomOffset( world, i, j, k, iItemDropped, iDamageDropped );
        	}
    	}
    }
    
    //------------- Saw related functionality ------------//
    
    public boolean DoesBlockBreakSaw( World world, int i, int j, int k )
    {
    	if ( blockMaterial.isSolid() )
    	{
	    	if ( blockMaterial != Material.wood && 
	    		blockMaterial != Material.cactus && 
	    		blockMaterial != Material.pumpkin &&
	    		blockMaterial != Material.leaves &&
	    		blockMaterial != Material.plants &&
	    		blockMaterial != Material.vine &&
	    		blockMaterial != Material.snow &&
	    		blockMaterial != Material.craftedSnow &&
	    		blockMaterial != FCBetterThanWolves.fcMaterialLog &&
	    		blockMaterial != FCBetterThanWolves.fcMaterialPlanks &&
	    		blockMaterial != FCBetterThanWolves.fcMaterialAsh
			)
	    	{
				return true;
	    	}
    	}
    	
    	return false;
    }

    /*
     * returns true if the block has been sawed, false otherwise
     */
    public boolean OnBlockSawed( World world, int i, int j, int k, int iSawPosI, int iSawPosJ, int iSawPosK )
    {
    	return OnBlockSawed( world, i, j, k );
    }
    
    /*
     * returns true if the block has been sawed, false otherwise
     */
    public boolean OnBlockSawed( World world, int i, int j, int k )
    {		
    	int iItemIDDropped = GetItemIDDroppedOnSaw( world, i, j, k ); 
    	
    	if (  iItemIDDropped >= 0 )
    	{
    		int iItemCountDropped = GetItemCountDroppedOnSaw( world, i, j, k );
    		int iItemDamageDropped = GetItemDamageDroppedOnSaw( world, i, j, k );
    		
    		for ( int iTempCount = 0; iTempCount < iItemCountDropped; iTempCount++ )
    		{
				FCUtilsItem.EjectSingleItemWithRandomOffset( world, i, j, k, 
					iItemIDDropped, iItemDamageDropped );
    		}
    	}
    	else
    	{
    		if ( !DoesBlockDropAsItemOnSaw( world, i, j, k ) )
        	{
    			return false;
        	}
        	
			dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
    	}
    	
    	world.setBlockToAir( i, j, k );
    	
    	return true;
    }
    
    public int GetItemIDDroppedOnSaw( World world, int i, int j, int k )
    {
    	return -1;
    }
    
    public int GetItemCountDroppedOnSaw( World world, int i, int j, int k )
    {
    	return 0;
    }
    
    public int GetItemDamageDroppedOnSaw( World world, int i, int j, int k )
    {
    	return 0;
    }
    
    public boolean DoesBlockDropAsItemOnSaw( World world, int i, int j, int k )
    {
    	return blockMaterial.isSolid();
    }
    
    //------------- Stonecutter related functionality ------------//
    // Only called by Automation+ addon
    // Can be used by other addons to interface
    
    public void setItemIDDroppedOnStonecutter(int id) {
    	this.idDroppedOnStonecut = id;
    }
    
    public void setItemCountDroppedOnStonecutter(int count) {
    	this.countDroppedOnStonecut = count;
    }

    public void setItemDamageDroppedOnStonecutter(int meta) {
    	this.metaDroppedOnStonecut = meta;
    }

    public int getItemIDDroppedOnStonecutter(World world, int x, int y, int z) {
        return this.idDroppedOnStonecut;
    }

    public int getItemCountDroppedOnStonecutter(World world, int x, int y, int z) {
        return this.countDroppedOnStonecut;
    }

    public int getItemDamageDroppedOnStonecutter(World world, int x, int y, int z)  {
        return this.metaDroppedOnStonecut;
    }

    public boolean doesBlockDropAsItemOnStonecutter(World world, int x, int y, int z)  {
        return this.blockMaterial.isSolid();
    }

    public boolean doesBlockBreakStonecutter(World world, int x, int y, int z) {
        return this.blockMaterial.isSolid() && this.blockMaterial != Material.rock && this.blockMaterial != Material.snow && this.blockMaterial != Material.craftedSnow && this.blockMaterial != FCBetterThanWolves.fcMaterialAsh && this.blockMaterial != FCBetterThanWolves.fcMaterialNetherRock;
    }

    public boolean onBlockStonecut(World world, int x, int y, int z, int stonecutterX, int stonecutterY, int stonecutterZ) {
        return this.onBlockStonecut(world, x, y, z);
    }

    /**
     * Override this method to provide finer control over stonecutter behavior.
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean onBlockStonecut(World world, int x, int y, int z){
        int id = this.getItemIDDroppedOnStonecutter(world, x, y, z);

        if (id >= 0) {
            int count = this.getItemCountDroppedOnStonecutter(world, x, y, z);
            int meta = this.getItemDamageDroppedOnStonecutter(world, x, y, z);

            for (int i = 0; i < count; i++) {
                FCUtilsItem.EjectSingleItemWithRandomOffset(world, x, y, z, id, meta);
            }
        }
        else {
            if (!this.doesBlockDropAsItemOnStonecutter(world, x, y, z)) {
                return false;
            }

            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        }

        world.setBlockToAir(x, y, z);
        return true;
    }
    
    //------------- Mechanical power related functionality ------------//
    
    public int GetMechanicalPowerLevelProvidedToAxleAtFacing( World world, int i, int j, int k, int iFacing )
    {
    	return 0;
    }
    
    //------------- Tool effectiveness functionality ------------//
    
    private boolean m_bShovelsEffectiveOn = false;
    private boolean m_bPicksEffectiveOn = false;
    private boolean m_bAxesEffectiveOn = false;
    private boolean m_bHoesEffectiveOn = false;
    
    private boolean m_bChiselsEffectiveOn = false;
    private boolean m_bChiselsCanHarvest = false;
    
    public boolean AreShovelsEffectiveOn()
    {
    	return m_bShovelsEffectiveOn;
    }
    
    public boolean ArePicksEffectiveOn()
    {
    	return m_bPicksEffectiveOn;
    }
    
    public boolean AreAxesEffectiveOn()
    {
    	return m_bAxesEffectiveOn;
    }
    
    public boolean AreHoesEffectiveOn()
    {
    	return m_bHoesEffectiveOn;
    }
    
    public boolean AreChiselsEffectiveOn()
    {
    	return m_bChiselsEffectiveOn;
    }
    
    public boolean AreChiselsEffectiveOn( World world, int i, int j, int k )
    {
    	return AreChiselsEffectiveOn();
    }
    
    public boolean CanChiselsHarvest()
    {
    	return m_bChiselsCanHarvest;
    }
    
    public Block SetShovelsEffectiveOn() { return SetShovelsEffectiveOn( true ); }
    public Block SetShovelsEffectiveOn( boolean bEffective )
    {
    	m_bShovelsEffectiveOn = bEffective;
    	
    	return this;
    }
    
    public Block SetPicksEffectiveOn() { return SetPicksEffectiveOn( true ); }
    public Block SetPicksEffectiveOn( boolean bEffective )
    {
    	m_bPicksEffectiveOn = bEffective;
    	
    	return this;
    }
    
    public Block SetAxesEffectiveOn() { return SetAxesEffectiveOn( true ); }
    public Block SetAxesEffectiveOn( boolean bEffective )
    {
    	m_bAxesEffectiveOn = bEffective;
    	
    	return this;
    }
    
    public Block SetHoesEffectiveOn() { return SetHoesEffectiveOn( true ); }
    public Block SetHoesEffectiveOn( boolean bEffective )
    {
    	m_bHoesEffectiveOn = bEffective;
    	
    	return this;
    }
    
    public Block SetChiselsEffectiveOn() { return SetChiselsEffectiveOn( true ); }
    public Block SetChiselsEffectiveOn( boolean bEffective )
    {
    	m_bChiselsEffectiveOn = bEffective;
    	
    	return this;
    }
    
    public Block SetChiselsCanHarvest() { return SetChiselsCanHarvest( true ); }
    public Block SetChiselsCanHarvest( boolean bCanHarvest )
    {
    	m_bChiselsCanHarvest = bCanHarvest;
    	
    	return this;
    }
    
    public float getPlayerRelativeBlockHardness( EntityPlayer player, World world, int i, int j, int k )
    {
        float fBlockHardness = getBlockHardness( world, i, j, k );
        
        if ( fBlockHardness >= 0F )
        {
            float fRelativeHardness = player.getCurrentPlayerStrVsBlock( this, i, j, k ) / fBlockHardness;
            
        	if ( player.IsCurrentToolEffectiveOnBlock( this, i, j, k ) )
        	{
        		return fRelativeHardness / 30F;
        	}
        	else
        	{
        		return fRelativeHardness / 200F;
        	}
        }
        else
        {
        	return 0F; 
        }
    }
    
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return false;
    }
    
    /**
     * Returns false if the block has not been replaced with another, and should be removed
     */
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	return false;
    }
    
    public int GetEfficientToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 0;
    }
    
    public int GetHarvestToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetEfficientToolLevel( blockAccess, i, j, k );
    }
    
    /**
     * The following is for stumps and such, which are a pain to remove regardless of whether their overall block has
     * relevant tool effeciencies
     */
    public boolean GetIsProblemToRemove( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean GetDoesStumpRemoverWorkOnBlock( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean CanToolsStickInBlock( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return true;
    }
    
    //------------- Buoyancy related functionality ------------//
    
	private float m_fBuoyancy = -1.0F;
	
    public Block SetBuoyancy( float fBuoyancy )
    {
    	m_fBuoyancy = fBuoyancy;
    	
    	return this;
    }
    
    public Block SetBuoyant() { return SetBuoyancy( 1F ); }
    public Block SetNonBuoyant() { return SetBuoyancy( -1F ); }
    public Block SetNeutralBuoyant() { return SetBuoyancy( 0F ); }
    
    public float GetBuoyancy( int iMetadata )
    {
    	return m_fBuoyancy;
    }
    
    //------------- Ground cover related functionality ------------//
    
    public boolean CanGroundCoverRestOnBlock( World world, int i, int j, int k )
    {
    	if ( world.doesBlockHaveSolidTopSurface( i, j, k ) )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public float GroundCoverRestingOnVisualOffset( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 0F;
    }
    
    public boolean IsGroundCover()
    {
    	return false;
    }
    
    public boolean AttempToSpreadGrassToBlock( World world, int i, int j, int k )
    {
    	if ( GetCanGrassSpreadToBlock( world, i, j, k ) &&
        	world.GetBlockNaturalLightValueMaximum( i, j + 1, k ) >= 
    		FCBlockGrass.m_iGrassSpreadToLightLevel && 
        	Block.lightOpacity[world.getBlockId( i, j + 1, k )] <= 2 &&
    		!FCBlockGroundCover.IsGroundCoverRestingOnBlock( world, i, j, k ) )    		
    	{
    		return SpreadGrassToBlock( world, i, j, k );
    	}
    	
    	return false;
    }
    
    public boolean GetCanGrassSpreadToBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean SpreadGrassToBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean GetCanGrassGrowUnderBlock( World world, int i, int j, int k, boolean bGrassOnHalfSlab )
    {
    	if ( !bGrassOnHalfSlab )
    	{
    		return !HasLargeCenterHardPointToFacing( world, i, j, k, 0 );
    	}
    	
    	return true;
    }
    
    public boolean AttempToSpreadMyceliumToBlock( World world, int i, int j, int k )
    {
    	if ( GetCanMyceliumSpreadToBlock( world, i, j, k ) &&
    		world.getBlockLightValue( i, j + 1, k ) >= 
    		FCBlockMycelium.m_iMyceliumSpreadToMinimumLightLevel &&
    		Block.lightOpacity[world.getBlockId( i, j + 1, k )] <= 2 &&
    		!FCBlockGroundCover.IsGroundCoverRestingOnBlock( world, i, j, k ) )    		
    	{
			return SpreadMyceliumToBlock( world, i, j, k );
    	}
    	
    	return false;
    }
    
    public boolean GetCanMyceliumSpreadToBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean SpreadMyceliumToBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean GetCanBlightSpreadToBlock( World world, int i, int j, int k, int iBlightLevel )
    {
    	return false;
    }

    /**
     * Used by blocks like grass and mycellium to determine if they should use a snow side
     * texture.  Note that this refers to the top visible surface, not just the top facing,
     * which means that stuff like half-slabs should only return true if they have ground cover
     * actually on the top surface halfway up the block vertically.
     */ 
    public boolean IsSnowCoveringTopSurface( IBlockAccess blockAccess, int i, int j, int k )
    {
    	int iBlockAboveID = blockAccess.getBlockId( i, j + 1, k );
    	
    	if ( iBlockAboveID != 0 )
    	{
    		Block blockAbove = blocksList[iBlockAboveID];
    		
    		Material aboveMaterial = blockAbove.blockMaterial;
    		
	        if ( aboveMaterial == Material.snow || aboveMaterial == Material.craftedSnow && 
	        	blockAbove.HasCenterHardPointToFacing( blockAccess, i, j + 1, k, 0 ) )
	        {
	        	return true;
	        }
	        else if ( 
	        	blockAbove.GroundCoverRestingOnVisualOffset( blockAccess, i, j + 1, k ) < -0.99F && 
	        	blockAccess.getBlockId( i, j + 2, k ) == snow.blockID )
	        {
	        	// consider snow resting on tall grass and such
	        	
	        	return true;
	        }
    	}
    	
    	return false;
    }
    
    //---------- Piston Related Functionality ---------//
    
    /**
     * Returns the metadata that will be placed
     */
    public int OnPreBlockPlacedByPiston( World world, int i, int j, int k, int iMetadata, int iDirectionMoved )
    {
    	return iMetadata;
    }
    
    public boolean CanBlockBePulledByPiston( World world, int i, int j, int k, int iToFacing )
    {
    	if ( getMobilityFlag() != 1 ) // blocks destroyed on push can not be pulled
    	{
    		return CanBlockBePushedByPiston( world, i, j, k, iToFacing );
    	}
    	
    	return false;    	                         
    }
    
    public boolean CanBlockBePushedByPiston( World world, int i, int j, int k, int iToFacing )
    {
        int iMobility = getMobilityFlag();
        
        return iMobility == 1 || ( iMobility != 2 && !( this instanceof ITileEntityProvider ) );
    }
    
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return AreShovelsEffectiveOn();
    }
    
    /**
     * returns the direction the shoveled block will go in if this block is moving towards iToFacing.  
     * return -1 if it's no shoveling is taking place.
     */
    public int GetPistonShovelEjectDirection( World world, int i, int j, int k, int iToFacing )
    {
    	return -1;
    }
    
    public AxisAlignedBB GetAsPistonMovingBoundingBox( World world, int i, int j, int k )
    {
    	return getCollisionBoundingBoxFromPool( world, i, j, k );
    }
    
    public int AdjustMetadataForPistonMove( int iMetadata )
    {
    	return iMetadata;
    }
    
    public boolean CanContainPistonPackingToFacing( World world, int i, int j, int k, int iFacing )
    {
    	return HasLargeCenterHardPointToFacing( world, i, j, k, iFacing, true );
    }    
    
    public boolean IsPistonPackable( ItemStack stack )
    {
    	return false;
    }
    
    public int GetRequiredItemCountToPistonPack( ItemStack stack )
    {
    	return 0;
    }
    
    public int GetResultingBlockIDOnPistonPack( ItemStack stack )
    {
    	return 0;
    }
    
    public int GetResultingBlockMetadataOnPistonPack( ItemStack stack )
    {
    	return 0;
    }
    
    public void OnBrokenByPistonPush( World world, int i, int j, int k, int iMetadata )
    {
    	dropBlockAsItem( world, i, j, k, iMetadata, 0 );
    }
    
    //------------- Hopper Filtering Functionality -----------//
    
    protected int m_iFilterablePropertiesBitfield = 0;
    
    public boolean CanItemPassIfFilter( ItemStack filteredItem )
    {
    	return true;
    }
    
    public int GetFilterableProperties( ItemStack stack )
    {
    	return m_iFilterablePropertiesBitfield;
    }
    
    public void SetFilterableProperties( int iProperties )
    {
    	m_iFilterablePropertiesBitfield = iProperties;
    }
    
    public boolean CanTransformItemIfFilter( ItemStack filteredItem )
    {
    	return false;
    }
    
    //---------- Falling Block Functionality ----------//
    
    private static final int m_iLoadedRangeToCheckFalling = 32;
    
    public boolean IsFallingBlock()
    {
    	return false;
    }
    
    protected boolean CheckForFall( World world, int i, int j, int k)
    {
        if ( CanFallIntoBlockAtPos( world, i, j - 1, k ) && j >= 0 )
        {
            if ( !BlockSand.fallInstantly && world.checkChunksExist( 
            	i - m_iLoadedRangeToCheckFalling, j - m_iLoadedRangeToCheckFalling, k - m_iLoadedRangeToCheckFalling, 
            	i + m_iLoadedRangeToCheckFalling, j + m_iLoadedRangeToCheckFalling, k + m_iLoadedRangeToCheckFalling ) )
            {
                if ( !world.isRemote )
                {
                	FCEntityFallingBlock fallingEntity = new FCEntityFallingBlock( world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
                    	blockID, world.getBlockMetadata( i, j, k ) );
                    
                    onStartFalling( fallingEntity );
                    
                    world.spawnEntityInWorld( fallingEntity );
                }
                
                return true;
            }
            else
            {
                world.setBlockToAir( i, j, k );

                while ( CanFallIntoBlockAtPos( world, i, j - 1, k ) && j > 0 )
                {
                    j--;
                }

                if ( j > 0 )
                {
                    world.setBlock( i, j, k, blockID );
                }
                
                return true;
            }
        }
        
        return false;
    }

    /**
     * Only called on server
     */
    protected void onStartFalling( EntityFallingSand entity ) {}
    
    /**
     * This is actually called when a block lands safely.  Do not rename as BlockSand has a child method off of this
     */
    public void onFinishFalling( World world, int i, int j, int k, int iMetadata )
    {
    	NotifyNearbyAnimalsFinishedFalling( world, i, j, k );
    }

    protected void OnFallingUpdate( FCEntityFallingBlock entity ) {}
    
    public void NotifyNearbyAnimalsFinishedFalling( World world, int i, int j, int k )
    {
    	if ( !world.isRemote )
    	{
            EntityPlayer entityPlayer = world.getClosestPlayer((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 64D );
            
            if ( entityPlayer != null )
            {
            	world.NotifyNearbyAnimalsOfPlayerBlockAddOrRemove( entityPlayer, this, i, j, k );
            }
    	}
    }
    
	/** 
	 * returns true if the block still exists
	 */
    public boolean OnFinishedFalling( EntityFallingSand entity, float fFallDistance )
    {
    	return true;
    }
    
    /**
     * returns true if the block has combined with the entity
     */
    public boolean AttemptToCombineWithFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	return false;
    }
    
    public boolean CanBeCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	return false;
    }
    
    public void OnCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    }
    
    protected boolean CanFallIntoBlockAtPos( World world, int i, int j, int k )
    {
    	Block targetBlock = Block.blocksList[world.getBlockId( i, j, k )];

    	return targetBlock == null || !targetBlock.CanSupportFallingBlocks( world, i, j, k );
    }
    
    public boolean CanSupportFallingBlocks( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return HasCenterHardPointToFacing( blockAccess, i, j, k, 1, true );    	
    }
    
    protected void CheckForUnstableGround( World world, int i, int j, int k )
    {
    	for ( int iJOffset = 1; iJOffset <= 16; iJOffset++ )
    	{
    		int iTempJ = j - iJOffset;
    		
    		if ( iTempJ <= 0 )
    		{
    			break;
    		}
    		else
    		{
    	        if( world.isAirBlock( i, iTempJ, k ) )
    	        {
    	        	world.notifyBlockOfNeighborChange( i, iTempJ + 1, k, 0 );
    	        	
    	        	break;
    	        }
    	        else
    	        {    	        	
    	        	int iTempBlockID = world.getBlockId( i, iTempJ, k );
    	        	
    	        	if ( iTempBlockID == Block.fire.blockID )
    	        	{
        	        	world.notifyBlockOfNeighborChange( i, iTempJ + 1, k, 0 );
        	        	
        	        	break;
    	        	}
    	        	else
    	        	{
    	        		Block tempBlock = Block.blocksList[iTempBlockID];
    	        		
    	        		if ( tempBlock.blockMaterial == Material.water || tempBlock.blockMaterial == Material.lava )
    	        		{
            	        	world.notifyBlockOfNeighborChange( i, iTempJ + 1, k, 0 );
            	        	
            	        	break;
    	        		}
    	        		else if ( !tempBlock.IsFallingBlock() )
    	        		{
    	        			break;
    	        		}
    	        	}
    	        }    			
    		}
    	}
    }
    
    protected void ScheduleCheckForFall( World world, int i, int j, int k )
    {
        world.scheduleBlockUpdate( i, j, k, blockID, tickRate( world ) );
    }

    /**
     * Called on server only
     */
    public void OnBlockDestroyedLandingFromFall( World world, int i, int j, int k, int iMetadata )
    {
    	OnBlockDestroyedWithImproperTool( world, null, i, j, k, iMetadata );
    }
    
    public boolean HasFallingBlockRestingOn( IBlockAccess blockAccess, int i, int j, int k )
    {
    	Block blockAbove = Block.blocksList[blockAccess.getBlockId( i, j + 1, k )];
    	                                    
        return blockAbove != null && blockAbove.IsFallingBlock() && 
        	blockAbove.HasCenterHardPointToFacing( blockAccess, i, j + 1, k, 0 );
    }
    
	//----------- Block Facing Functionality ----------//
	
	public int GetFacing( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetFacing( blockAccess.getBlockMetadata( i, j, k ) );
	}
	
	public int GetFacing( int iMetadata )
	{
		return 0;
	}
	
	public void SetFacing( World world, int i, int j, int k, int iFacing )
	{
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		int iNewMetadata = SetFacing( iMetadata, iFacing );
		
		if ( iNewMetadata != iMetadata )
		{
			world.setBlockMetadataWithNotify( i, j, k, iNewMetadata );
		}
	}
	
	public int SetFacing( int iMetadata, int iFacing )
	{
		return iMetadata;
	}
	
	/**
	 * Cycle through all the possible facings for a block 
	 * returns true if the facing has actually changed as a result of this call
	 */
	public boolean ToggleFacing( World world, int i, int j, int k, boolean bReverse )
	{
		return RotateAroundJAxis( world, i, j, k, bReverse );
	}
	
    public int ConvertFacingToTopTextureRotation( int iFacing )
    {
    	if ( iFacing >= 2 )
    	{
    		if ( iFacing <= 3 )
    		{
    			if ( iFacing == 3 )
    			{
    				return 3;
    			}
    		}
    		else
    		{
    			if ( iFacing == 4 )
    			{
    				return 2;
    			}
    			else // iFacing == 5
    			{
    				return 1;
    			}
    		}
    	}
    	
    	return 0;
    }
    
    public int ConvertFacingToBottomTextureRotation( int iFacing )
    {
    	if ( iFacing >= 2 )
    	{
    		if ( iFacing <= 3 )
    		{
    			if ( iFacing == 3 )
    			{
    				return 3;
    			}
    		}
    		else
    		{
    			if ( iFacing == 4 )
    			{
    				return 1;
    			}
    			else // iFacing == 5
    			{
    				return 2;
    			}
    		}
    	}
    	
    	return 0;
    }
    
	static public int GetOppositeFacing( int iFacing )
	{
		return iFacing ^ 1;
	}
	
	static public int RotateFacingAroundJ( int iFacing, boolean bReverse )
	{
		if ( bReverse )
		{
			return m_iRotatedFacingsAroundJCounterclockwise[iFacing];
		}
		
		return m_iRotatedFacingsAroundJClockwise[iFacing];
	}

	static public int CycleFacing( int iFacing, boolean bReverse )
	{
		if ( bReverse )
		{
			return m_iCycledFacingsReversed[iFacing];
		}
		
		return m_iCycledFacings[iFacing];
	}

    //-------- Turntable Related Functionality --------//
    
	public boolean CanRotateOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return blockAccess.isBlockNormalCube( i, j, k );
	}	
	
	public boolean CanTransmitRotationHorizontallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return blockAccess.isBlockNormalCube( i, j, k );
	}
	
	public boolean CanTransmitRotationVerticallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return blockAccess.isBlockNormalCube( i, j, k );
	}
	
	/**
	 * Returns the new crafting counter after rotation.  It is unmodified if no crafting has taken place, 
	 * incremented or reset on completion if it has.
	 */
	public int RotateOnTurntable( World world, int i, int j, int k, boolean bReverse, int iCraftingCounter )
	{
		OnRotatedOnTurntable( world, i, j, k );
		
		if ( !RotateAroundJAxis( world, i, j, k, bReverse ) )
		{
			// notify the surrounding blocks of a change if no facing change actually takes place, so that buddy can pick up on it
			// this is because solid blocks still "rotate" even if their facing doesn't change
			
	    	world.notifyBlocksOfNeighborChange( i, j, k, blockID );	    	
		}
		
		return iCraftingCounter;
	}
	
    //AARON added a method that can be overridden to cause animal scare
    //primary use is campfires
    public boolean alwaysStartlesAnimals()
    {
    	return false;
    }

	/*
	 * Intended to play block specific FX and such
	 */
	protected void OnRotatedOnTurntable( World world, int i, int j, int k )
	{
	}

	protected int TurntableCraftingRotation( World world, int i, int j, int k, boolean bReverse, int iCraftingCounter )
	{
		iCraftingCounter++;
		
		if ( iCraftingCounter >= GetRotationsToCraftOnTurntable( world, i, j, k ) )
		{
			OnCraftedOnTurntable( world, i, j, k );
			
			int iNewBlockID = GetNewBlockIDCraftedOnTurntable( world, i, j, k );
			int iNewBlockMetadata = GetNewMetadataCraftedOnTurntable( world, i, j, k );
			int iItemIDDropped = GetItemIDCraftedOnTurntable( world, i, j, k );
			int iItemCountDropped = GetItemCountCraftedOnTurntable( world, i, j, k );
			int iItemDamageDropped = GetItemDamageCraftedOnTurntable( world, i, j, k );
			
			for ( int tempCount = 0; tempCount < iItemCountDropped; tempCount++ )
			{
				FCUtilsItem.EjectSingleItemWithRandomOffset( world, i, j + 1, k, iItemIDDropped, iItemDamageDropped );			
			}

			world.setBlockAndMetadataWithNotify( i, j, k, iNewBlockID, iNewBlockMetadata );			
			
			iCraftingCounter = 0;
		}
		
		return iCraftingCounter;
	}
	
	public int GetRotationsToCraftOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return 1;
	}
	
	public void OnCraftedOnTurntable( World world, int i, int j, int k )
	{
        world.playAuxSFX( FCBetterThanWolves.m_iBlockDestroyRespectParticleSettingsAuxFXID, 
        	i, j, k, world.getBlockId( i, j, k ) + ( world.getBlockMetadata( i, j, k ) << 12 ) );        
	}
	
	public int GetNewBlockIDCraftedOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return 0;
	}
	
	public int GetNewMetadataCraftedOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return 0;
	}
	
	public int GetItemIDCraftedOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return Item.clay.itemID;
	}
	
	public int GetItemCountCraftedOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return 1;
	}
	
	public int GetItemDamageCraftedOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return 0;
	}
	
	/**
	 * Returns true if the facing has actually changed as a result of this call
	 */
	public boolean RotateAroundJAxis( World world, int i, int j, int k, boolean bReverse )
	{
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		int iNewMetadata = RotateMetadataAroundJAxis( iMetadata, bReverse );
		
		if ( iNewMetadata != iMetadata )
		{
			world.setBlockMetadataWithNotify( i, j, k, iNewMetadata );
			
			return true;
		}
		
		return false;
	}

	public int RotateMetadataAroundJAxis( int iMetadata, boolean bReverse )
	{
		int iFacing = GetFacing( iMetadata );
		
		int iNewFacing = RotateFacingAroundJ( iFacing, bReverse );
		
		return SetFacing( iMetadata, iNewFacing );
	}

    public boolean CanRotateAroundBlockOnTurntableToFacing( World world, int i, int j, int k, int iFacing  )
    {
    	return false;
    }
    
    /**
     * Returns false if the block was destroyed and should not be rotated
     */
    public boolean OnRotatedAroundBlockOnTurntableToFacing( World world, int i, int j, int k, int iFacing  )
    {
    	return true;
    }
    
    public int GetNewMetadataRotatedAroundBlockOnTurntableToFacing( World world, int i, int j, int k, int iInitialFacing, int iRotatedFacing )
    {
    	return 0;
    }
    
    //----- Block Dispenser Related Functionality -----//
    
    /**
     * If the stack returned is null, the block will not be retrieved
     */
    public ItemStack GetStackRetrievedByBlockDispenser( World world, int i, int j, int k )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k );
    	
    	if ( canSilkHarvest( iMetadata ) )
    	{
    		return createStackedBlock( iMetadata );
    	}
    	
    	int iIdDropped = idDropped( iMetadata, world.rand, 0 );
    	
    	if ( iIdDropped > 0 )
    	{
    		return new ItemStack( iIdDropped, 1, damageDropped( iMetadata ) );
    	}
    	
    	return null;
    }
    
    /**
     * Whether a block is destroyed by the dispenser, even if no item is collected
     */
    public boolean IsBlockDestroyedByBlockDispenser( int iMetadata )
    {
    	return false;
    }
    
    public void OnRemovedByBlockDispenser( World world, int i, int j, int k )
    {
        world.playAuxSFX( FCBetterThanWolves.m_iBlockDestroyRespectParticleSettingsAuxFXID, 
        	i, j, k, blockID + ( world.getBlockMetadata( i, j, k ) << 12 ) );
        
    	world.setBlockWithNotify( i, j, k, 0 );
    }
    
	//---------- Weather Related Functionality --------//
    
    /**
     * Called on server only
     */
    public void OnStruckByLightning( World world, int i, int j, int k )
    {
    }
    
	//------- Mob Spawning Related Functionality ------//
    
    /**
     * This is only a first-pass indicator as to whether ANY mobs can spawn on top of the block,
     * so stuff like leaves where only Jungle Spiders can spawn on them, should still return true.
     */
    public boolean CanMobsSpawnOn( World world, int i, int j, int k )
    {
        return blockMaterial.GetMobsCanSpawnOn( world.provider.dimensionId ) &&
    		getCollisionBoundingBoxFromPool( world, i, j, k ) != null;
    }

    public float MobSpawnOnVerticalOffset( World world, int i, int j, int k )
    {
    	return 0F;
    }
    
	//-------- Collision Handling Functionality -------//
    
    /**
     * This should never be modified after a block is initialized to avoid multithreading issues
     * that occurred with the old min/max bounds variables.
     */
    private AxisAlignedBB m_fixedBlockBounds = new AxisAlignedBB( 0D, 0D, 0D, 1D, 1D, 1D );
    private boolean m_bFixedBlockBoundsSet = false;
    
    /**
     * Should only ever be called once for a block.  Repeated calls will silently fail without
     * changing the bounds.
     */
    protected void InitBlockBounds( double dMinX, double dMinY, double dMinZ, 
    	double dMaxX, double dMaxY, double dMaxZ )
    {
    	if ( !m_bFixedBlockBoundsSet )
    	{
    		// only allow the bounds to be set before they're ever accessed to 
    		// discourage the kind of errors that necessitated it in the first 
    		// place: client and server threads modifying the min/max values 
    		// resulting in race conditions.
    		
    		m_fixedBlockBounds.setBounds( dMinX, dMinY, dMinZ, dMaxX, dMaxY, dMaxZ );    		
    	}
    }

    protected void InitBlockBounds( AxisAlignedBB bounds )
    {
    	if ( !m_bFixedBlockBoundsSet )
    	{
    		m_fixedBlockBounds.setBB( bounds );
    	}
    }
    
    protected AxisAlignedBB GetFixedBlockBoundsFromPool()
    {
		m_bFixedBlockBoundsSet = true;
		
    	return m_fixedBlockBounds.MakeTemporaryCopy();
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
    {
    	return GetBlockBoundsFromPoolBasedOnState( world, i, j, k ).offset( i, j, k );
    }
    
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( 
    	IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetFixedBlockBoundsFromPool();
    }
    
    public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, 
    	Vec3 startRay, Vec3 endRay )
    {
    	return CollisionRayTraceVsBlockBounds( world, i, j, k, startRay, endRay );
    }

    public MovingObjectPosition MouseOverRayTrace( World world, int i, int j, int k, 
    	Vec3 startRay, Vec3 endRay )
    {
    	return collisionRayTrace( world, i, j, k, startRay, endRay );
    }

    public MovingObjectPosition CollisionRayTraceVsBlockBounds( World world, int i, int j, int k, 
    	Vec3 startRay, Vec3 endRay )
    {
    	AxisAlignedBB collisionBox = GetBlockBoundsFromPoolBasedOnState( 
    		world, i, j, k ).offset( i, j, k );
    	
    	MovingObjectPosition collisionPoint = collisionBox.calculateIntercept( startRay, endRay );
    	
    	if ( collisionPoint != null )
    	{
    		collisionPoint.blockX = i;
    		collisionPoint.blockY = j;
    		collisionPoint.blockZ = k;
    	}
    	
    	return collisionPoint;
    }
    
	//------------- Grazing Functionality -------------//
    
    private int m_iHerbivoreItemFoodValue = 0;
    private int m_iBirdItemFoodValue = 0;
    private int m_iPigItemFoodValue = 0;
    
    public boolean CanBeGrazedOn( IBlockAccess blockAccess, int i, int j, int k, 
    	EntityAnimal byAnimal )
    {
    	return false;
    }
    
    public void OnGrazed( World world, int i, int j, int k, EntityAnimal animal )
    {
        world.setBlockToAir( i, j, k );

        Block blockBelow = blocksList[world.getBlockId( i, j - 1, k )];
        
        if ( blockBelow != null )
        {
        	blockBelow.OnVegetationAboveGrazed( world, i, j - 1, k, animal );
        }
    }
    
	public void OnVegetationAboveGrazed( World world, int i, int j, int k, EntityAnimal animal )
	{
	}
	
	/** 
	 * Used when pigs dig up dirt to let any attached neighbors know that they should break loose
	 */
	public void NotifyNeighborsBlockDisrupted( World world, int i, int j, int k )
	{
		FCUtilsBlockPos pos = new FCUtilsBlockPos( i, j, k );
		FCUtilsBlockPos tempPos = new FCUtilsBlockPos();
		
		for ( int iTempFacing = 0; iTempFacing <= 5; iTempFacing++ )
		{
			tempPos.Set( pos );			
			tempPos.AddFacingAsOffset( iTempFacing );
			
			Block tempBlock = Block.blocksList[world.getBlockId( tempPos.i, tempPos.j, tempPos.k )];
			
			if ( tempBlock != null )
			{
				tempBlock.OnNeighborDisrupted( world, tempPos.i, tempPos.j, tempPos.k, 
					GetOppositeFacing( iTempFacing ) );
			}
		}
	}
	
	public void OnNeighborDisrupted( World world, int i, int j, int k, int iToFacing )
	{
	}
	
    public int GetHerbivoreItemFoodValue( int iItemDamage )
    {
    	return m_iHerbivoreItemFoodValue;
    }
    
    public void SetHerbivoreItemFoodValue( int iFoodValue )
    {
    	m_iHerbivoreItemFoodValue = iFoodValue;
    }
    
    public int GetChickenItemFoodValue( int iItemDamage )
    {
    	return m_iBirdItemFoodValue;
    }
    
    public void SetChickenItemFoodValue( int iFoodValue )
    {
    	m_iBirdItemFoodValue = iFoodValue;
    }
    
    public int GetPigItemFoodValue( int iItemDamage )
    {
    	return m_iPigItemFoodValue;
    }
    
    public void SetPigItemFoodValue( int iFoodValue )
    {
    	m_iPigItemFoodValue = iFoodValue;
    }
    
	//----------- Plant Growth Functionality ----------//
    
    public boolean CanDomesticatedCropsGrowOnBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean CanReedsGrowOnBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean CanSaplingsGrowOnBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    /**
     * Covers stuff like flowers and tall grass
     */
    public boolean CanWildVegetationGrowOnBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean CanNetherWartGrowOnBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
    public boolean CanCactusGrowOnBlock( World world, int i, int j, int k )
    {
    	return false;
    }
    
	public boolean IsBlockHydratedForPlantGrowthOn( World world, int i, int j, int k )
	{
		return false;
	}

	public boolean IsConsideredNeighbouringWaterForReedGrowthOn( World world, int i, int j, int k )
	{
		for ( int iTempI = i - 1; iTempI <= i + 1; iTempI++ )
		{		
			for ( int iTempK = k - 1; iTempK <= k + 1; iTempK++ )
			{				
				if ( world.getBlockMaterial( iTempI, j, iTempK ) == Material.water )
				{
					return true;
				}
			}
		}

		return false;
	}
	
	/** 
	 * This is used by old style non-daily plant growth
	 */
	public float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock )
	{
		return 1F;
	}
	
	public boolean GetIsFertilizedForPlantGrowth( World world, int i, int j, int k )
	{
		return false;
	}
	
	/** 
	 * Called when a plant hits a full growth stage, like wheat fully grown, 
	 * or each full block of Hemp.  Used to clear fertilizer.
	 */
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
	}
	
	/**
	 * Called server only.  Called AFTER the plant is removed, so it's no longer valid.  
	 */
	public void NotifyOfPlantAboveRemoved( World world, int i, int j, int k, Block plantBlock )
	{
	}
	
	/**
	 * This determines whether weeds can share space with crop blocks, or grow
	 * within their own independent weed blocks
	 */
	public boolean CanWeedsGrowInBlock( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}

	/**
	 * The growth level of weeds growing out of this block.  Range of 0 to 7 
	 */
	public int GetWeedsGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
	{
		return 0;
	}
	
	public void RemoveWeeds( World world, int i, int j, int k )
	{
	}
	
	public boolean AttemptToApplyFertilizerTo( World world, int i, int j, int k )
	{
		return false;
	}
	
	public boolean GetConvertsLegacySoil( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;		
	}
	
	//----------------- Map related functionality ----------------//
    
    /**
     * Gets the color used in map rendering for this block with the specified metadata
     * @param meta
     * @return
     */
    public MapColor getMapColor(int meta) {
    	if (mapColorsForMetadata == null) {
    		return this.blockMaterial.materialMapColor;
    	}
    	else {
    		try {
    			return mapColorsForMetadata[meta];
    		}
    		catch (Exception e) {
    			FCAddOnHandler.LogMessage("Map color not found for metadata " + meta + " of block " + this);
        		return this.blockMaterial.materialMapColor;
    		}
    	}
    }
    
    /**
     * Set the array of map colors to use per metadata for this block. Make sure you include ALL possible metadata when using this method!
     * @param mapColors Array of mapcolor objects which is referenced when rendering maps
     * @return
     */
    public Block setMapColorsForMetadata(MapColor[] mapColors) {
    	this.mapColorsForMetadata = mapColors;
    	return this;
    }
	
	//----------------- Integrity Test ----------------//
    
    static public boolean InstallationIntegrityTest()
    {
    	return true;
    }
    
	//----------- Client Side Functionality -----------//
    
    /**
     * Mainly used by shouldSideBeRendered() so that it can access the current render bounds.
     * NOTE: Does not apply to item rendering unless specifically set within RenderBlockAsItem()
     */
//    public RenderBlocks m_currentBlockRenderer = null;
    
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, 
    	int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
        Block neighborBlock = Block.blocksList[blockAccess.getBlockId( iNeighborI, iNeighborJ, iNeighborK )];
        
        if ( neighborBlock != null )
        {
        	return neighborBlock.ShouldRenderNeighborFullFaceSide( blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide ); 
        }
        
        return true;
    }
    
    public boolean ShouldRenderNeighborHalfSlabSide( IBlockAccess blockAccess, int i, int j, int k, int iNeighborSlabSide, boolean bNeighborUpsideDown )
    {
		return !isOpaqueCube();
    }
    
    public boolean ShouldRenderNeighborFullFaceSide( IBlockAccess blockAccess, int i, int j, int k, int iNeighborSide )
    {
		return !isOpaqueCube();
    }
    
//    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
//    {
//        renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
//        	renderer.blockAccess, i, j, k ) );
//        
//    	return renderer.renderStandardBlock( this, i, j, k );
//    }

    /** 
     * If the block has a second pass, like a kiln cooking overlay texture, it should Override this method.  This method does not call the overlay
     * by default to cut down on rendering time, since this function is called by every single loaded block.
     * Note that this function is necessary to prevent potential recursion within RenderBlock, if it were to call its own overlays
     * directly, and then potentially get called with a texture overlay itself through RenderBlockWithTexture.
     */
//    public void RenderBlockSecondPass( RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult )
//    {    
//    }
    
//    public boolean RenderBlockWithTexture( RenderBlocks renderBlocks, int i, int j, int k, Icon texture )
//    {
//    	boolean bReturnValue;
//    	
//    	renderBlocks.setOverrideBlockTexture( texture );
//    	
//		// this test is necessary due to optimizations in RenderStandardFullBlock() that 
//    	// assumes the texture isn't overriden 
//    	if ( !renderAsNormalBlock() )
//    	{
//	        bReturnValue = RenderBlock( renderBlocks, i, j, k );
//	        
//    	}
//    	else
//    	{
//        	renderBlocks.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
//        		renderBlocks.blockAccess, i, j, k ) );
//            
//            bReturnValue = renderBlocks.renderStandardBlock( this, i, j, k );            
//    	}
//    	
//        renderBlocks.clearOverrideBlockTexture();
//        
//        return bReturnValue;
//    }

    public AxisAlignedBB GetBlockBoundsFromPoolForItemRender( int iItemDamage )
    {
    	return GetFixedBlockBoundsFromPool();
    }
    
//    public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
//    {
//    	renderBlocks.renderBlockAsItemVanilla( this, iItemDamage, fBrightness );
//    }
    
//    public boolean DoesItemRenderAsBlock( int iItemDamage )
//    {
//    	return RenderBlocks.DoesRenderIDRenderItemIn3d( getRenderType() );
//    }    
    
//    public void RenderCookingByKilnOverlay( RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult )
//    {
//    	if ( bFirstPassResult )
//    	{
//	    	IBlockAccess blockAccess = renderBlocks.blockAccess;
//	    	
//	    	// check texture override to prevent recursion
//			if ( !renderBlocks.hasOverrideBlockTexture() && GetCanBeCookedByKiln( blockAccess, i, j, k ) )
//			{
//	    		int iBlockBelowID = blockAccess.getBlockId( i, j - 1, k );
//	    		
//	    		if ( iBlockBelowID == FCBetterThanWolves.fcKiln.blockID )
//	    		{
//	    			Icon overlayTexture = FCBetterThanWolves.fcKiln.GetCookTextureForCurrentState( blockAccess, i, j - 1, k );
//	    			
//	            	if ( overlayTexture != null )
//	            	{
//	            		RenderBlockWithTexture( renderBlocks, i, j, k, overlayTexture );
//	            	}
//	    		} 
//			}
//    	}
//    }
    
    public boolean ShouldRenderWhileFalling( World world, EntityFallingSand entity )
    {
        int iCurrentBlockI = MathHelper.floor_double( entity.posX );
        int iCurrentBlockJ = MathHelper.floor_double( entity.posY );
        int iCurrentBlockK = MathHelper.floor_double( entity.posZ );
        
        int iBlockIDAtLocation = world.getBlockId( iCurrentBlockI, iCurrentBlockJ, iCurrentBlockK );
        
    	return iBlockIDAtLocation != entity.blockID;
    }

    /**
     * Applies both to falling blocks, and those pushed by pistons
     */     
//    public void RenderFallingBlock( RenderBlocks renderBlocks, int i, int j, int k, int iMetadata ) 
//    {
//        renderBlocks.setRenderBounds( GetFixedBlockBoundsFromPool() );
//        
//        renderBlocks.RenderStandardFallingBlock( this, i, j, k, iMetadata );
//    }
    
    public boolean ShouldSideBeRenderedOnFallingBlock( int iSide, int iMetadata )
    {
    	// called within renderBlocks.renderStandardMovingBlock() instead of the usual
    	// shouldSideBeRendered() since neighboring blocks aren't relevant while moving
    	
    	return true;
    }
    
//    public void RenderBlockMovedByPiston( RenderBlocks renderBlocks, int i, int j, int k )
//    {
//        renderBlocks.renderBlockAllFaces( this, i, j, k );
//    }
    
    public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, int i, int j, int k )
    {
    	return GetBlockBoundsFromPoolBasedOnState( world, i, j, k ).offset( i, j, k );
    }
    
    /** 
     * Replaces vanilla call in RenderGlobal to provide ray trace info so specific portions of the block can be highlighted as selected
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, MovingObjectPosition rayTraceHit )
    {
        return getSelectedBoundingBoxFromPool( world, rayTraceHit.blockX, rayTraceHit.blockY, rayTraceHit.blockZ );
    }

    /**
     * Called by geometric primitives that FCModelBlock uses, to reference textures not associated with a specific block
     * side.
     */ 
    public Icon GetIconByIndex( int iIndex )
    {
    	return blockIcon;
    }
    
    public Icon GetHopperFilterIcon()
    {
    	return null;
    }
    
//    protected void RenderCrossHatch( RenderBlocks renderer, int i, int j, int k, Icon icon, 
//    	double dBorderWidth, double dVerticalOffset )
//    {
//    	Tessellator tessellator = Tessellator.instance;
//    	
//    	double dX = i; 
//    	double dY = j + dVerticalOffset;
//    	double dZ = k;
//    	
//        double dMinU = icon.getMinU();
//        double dMinV = icon.getMinV();
//        double dMaxU = icon.getMaxU();
//        double dMaxV = icon.getMaxV();
//        
//        double dX1 = dX + 1D - dBorderWidth;
//        double dX2 = dX + dBorderWidth;
//        
//        double dZ1 = dZ;
//        double dZ2 = dZ + 1D;
//        
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ1, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ1, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ2, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ2, dMaxU, dMinV );
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ2, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ2, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ1, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ1, dMaxU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ2, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ2, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ1, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ1, dMaxU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ1, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ1, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ2, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ2, dMaxU, dMinV );
//        
//        dX1 = dX;
//        dX2 = dX + 1D;
//        
//        dZ1 = dZ + dBorderWidth;
//        dZ2 = dZ + 1D - dBorderWidth;
//        
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ1, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ1, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ1, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ1, dMaxU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ1, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ1, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ1, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ1, dMaxU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ2, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ2, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ2, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ2, dMaxU, dMinV );
//        tessellator.addVertexWithUV( dX1, dY + 1D, dZ2, dMinU, dMinV );
//        tessellator.addVertexWithUV( dX1, dY + 0D, dZ2, dMinU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 0D, dZ2, dMaxU, dMaxV );
//        tessellator.addVertexWithUV( dX2, dY + 1D, dZ2, dMaxU, dMinV );
//    }
    // END FCMOD
    
    //AARON imported from well it says so right below VVV
	//------- SocksAddonsUtils Functionality -------//
	
    /**
     * Used to allow Blocks to be placed in a specific armorSlot 
     * @param armorType 0: Helmet, 1: Chest, 2: Legs, 3: boots
     * @param itemStack 
     */
	public boolean isValidForArmorSlot(int armorType, ItemStack itemStack) {
		return false;
	}
	
	/**
	 * Example Pumpkin: "%blur%/misc/pumpkinblur.png"
	 * @return Returns the directory string of the blur overlay texture that should be used when this is worn in the helmet slot
	 */
	public String getBlurOverlay(ItemStack itemStack) {
		return null;
	}
	
	/**
	 * Returns true or false depending if the blur overlay should be shown when the player disabled the GUI 
	 */
	public boolean showBlurOverlayWithGuiDisabled(ItemStack itemStack) {
		return false;
	}
	
	/**
	 * If leaves can replace this Block. Used when generating leaves for trees on world gen and tree growth
	 */
	public boolean canBeReplacedByLeaves()
	{
		return false;
	}
	
	// END SAU
}
