// FCMOD

package net.minecraft.src;

// client only
//import net.minecraft.client.Minecraft;

import net.minecraft.server.MinecraftServer;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FCBetterThanWolves extends FCAddOn
{
	public static final String fcVersionString = "1.0.1";
	
	public static FCBetterThanWolves m_instance = new FCBetterThanWolves();
	
	private Map<String, String> propertyValues;
	
    // New Materials
    
    public static final Material fcMaterialCement = (new FCMaterialCement(MapColor.stoneColor));
    public static final Material fcMaterialSoulforgedSteel = (new FCMaterialSoulforgedSteel(MapColor.ironColor));
    public static final Material fcMaterialNetherGrowth = (new FCMaterialNetherGrowth(MapColor.foliageColor)).SetMobsCantSpawnOn();    
    public static final Material fcMaterialCandle = (new MaterialLogic(MapColor.airColor)).setNoPushMobility();
    public static final Material fcMaterialMiscellaneous = new Material(MapColor.dirtColor); // used for aesthetic blocks to avoid tool dependancies
    public static final Material fcMaterialLog = (new Material(MapColor.woodColor)).setBurning().setRequiresTool().SetMobsCantSpawnOn();
    public static final Material fcMaterialPlanks = (new Material(MapColor.woodColor)).setBurning().setRequiresTool().SetMobsCantSpawnOn();
    public static final Material fcMaterialAsh = (new MaterialLogic(MapColor.stoneColor)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
    public static final Material fcMaterialNaturalClay = (new Material(MapColor.clayColor)).setRequiresTool();
    public static final Material fcMaterialWicker = (new Material(MapColor.woodColor)).setBurning().setRequiresTool().SetAxesEfficientOn();
    public static final Material fcMaterialBasket = (new Material(MapColor.woodColor)).setNoPushMobility();
    public static final Material fcMaterialMilk = (new MaterialLogic(MapColor.snowColor)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
    public static final Material fcMaterialNetherRock = (new Material(MapColor.stoneColor)).setRequiresTool().SetNetherMobsCanSpawnOn();
    
	//New Blocks

    public static Block fcBlockSlabSandAndGravel;
    
    public static Block fcBlockArcaneVessel;
    
    public static Block fcBlockAxlePowerSource;
    
    public static Block fcBlockSidingAndCornerBlackStone;
    public static Block fcBlockMouldingAndDecorativeBlackStone;
    
    public static Block fcBlockAestheticOpaqueEarth;
    
    public static Block fcBlockCandle;
    
    public static Block fcBlockSandstoneSidingAndCorner;
    public static Block fcBlockSandstoneMouldingAndDecorative;
    
    public static Block fcBlockWoodOakSidingAndCorner;
    
    public static Block fcBlockSmoothStoneSidingAndCorner;
    
    public static Block fcBlockBrickSidingAndCorner;
    public static Block fcBlockBrickMouldingAndDecorative;
    
    public static Block fcBlockNetherBrickSidingAndCorner;
    public static Block fcBlockNetherBrickMouldingAndDecorative;
    
    public static Block fcBlockWhiteStoneStairs;    
    public static Block fcBlockWhiteStoneSidingAndCorner;
    public static Block fcBlockWhiteStoneMouldingAndDecorative;
    
    public static Block fcBlockStakeString;
    public static Block fcBlockStake;

    public static Block fcBlockScrewPump;

    public static Block fcBlockWoodSpruceSidingAndCorner;
    public static Block fcBlockWoodSpruceMouldingAndDecorative;
    
    public static Block fcBlockWoodBirchSidingAndCorner;
    public static Block fcBlockWoodBirchMouldingAndDecorative;
    
    public static Block fcBlockWoodJungleSidingAndCorner;
    public static Block fcBlockWoodJungleMouldingAndDecorative;
    
    public static Block fcBlockStoneBrickSidingAndCorner;
    public static Block fcBlockStoneBrickMouldingAndDecorative;
    
    public static Block fcBlockFarmlandLegacyFertilized;
    
    public static Block fcBlockWoolSlabTop;
    
    public static FCBlockDirtSlab fcBlockDirtSlab;
    
    public static Block fcBlockBloodMoss;
    
    public static Block fcInfernalEnchanter;
    
    public static Block fcSoulforgedSteelBlock;
    
	public static Block fcBlockDetectorGlowingLogic;
	
    public static Block fcBlockBloodLeaves;
    
    public static Block fcBloodWood;
    
    public static Block fcAestheticVegetation;
    
    public static Block fcBlockSmoothStoneMouldingAndDecorative;
    
    public static Block fcAestheticOpaque;
    public static Block fcAestheticNonOpaque;
    
    public static Block fcBlockMiningCharge;
    
    public static Block fcBuddyBlock;
	
	public static FCBlockKiln fcKiln;
	
	public static Block fcWoolSlab;
	
	public static Block fcAnvil;
	
	public static Block fcLightBulbOff;
	public static Block fcLightBulbOn;
	
	public static FCBlockBBQ fcBBQ;
	
	public static Block fcHopper;
	
	public static Block fcSaw;
	
	public static Block fcPlatform;
	
	public static Block fcCement;
	
	public static Block fcPulley;
	
	public static Block fcBlockPressurePlateSoulforgedSteel;
	
	public static Block fcBlockWoodOakMouldingAndDecorative;
	public static Block fcBlockLegacySmoothstoneAndOakCorner;
    
	public static FCBlockBlockDispenser fcBlockDispenser;
	
	public static Block fcCauldron;

	public static Block fcDetectorRailWood;
	public static Block fcBlockDetectorRailSoulforgedSteel;
	
	public static Block fcCompanionCube;
	
	public static Block fcBlockDetector;
	public static Block fcBlockDetectorLogic;
	
	public static Block fcLens;
	
	public static Block fcBlockHempCrop;
	
	public static Block fcHandCrank;
	
    public static Block fcMillStone;
    
    public static Block fcAnchor;
    
    public static Block fcRopeBlock;
    
    public static Block fcBlockLegacySmoothstoneAndOakSiding;
    
    public static Block fcBlockAxle;
    
    public static FCBlockRedstoneClutch fcBlockRedstoneClutch; 
    
    public static Block fcTurntable;
    
    public static Block fcBellows;
    
    public static Block fcBlockFireStoked;
    
    public static Block fcUnfiredPottery;

    public static Block fcCrucible;
    public static Block fcPlanter;
    public static Block fcVase;
    
    // extended blocks
    
    public static Block fcBlockRottenFlesh;
    public static Block fcBlockShaft;
	public static Block fcBlockSoulforgeDormant;	
    public static Block fcBlockSmoothstoneStairs;
    public static Block fcBlockRottenFleshSlab;
    public static Block fcBlockBoneSlab;
    public static Block fcBlockPumpkinFresh;
    
    public static Block fcBlockWoodBloodSidingAndCorner;
    public static Block fcBlockWoodBloodMouldingAndDecorative;
    public static Block fcBlockWoodBloodStairs;
    
    public static FCBlockLogDamaged fcBlockLogDamaged;
    
    public static Block fcBlockDirtLoose;
    public static Block fcBlockDirtLooseSlab;
    
    public static FCBlockCampfire fcBlockCampfireUnlit;
    public static FCBlockCampfire fcBlockCampfireSmall;
    public static FCBlockCampfire fcBlockCampfireMedium;
    public static FCBlockCampfire fcBlockCampfireLarge;
    
    public static FCBlockUnfiredBrick fcBlockUnfiredBrick;
    public static Block fcBlockCookedBrick;
    public static Block fcBlockBrickLoose;
    public static Block fcBlockBrickLooseSlab;
    
    public static Block fcBlockCobblestoneLoose;
    public static Block fcBlockCobblestoneLooseSlab;    
    
    public static Block fcBlockFurnaceBrickIdle;
    public static Block fcBlockFurnaceBrickBurning;
    
    public static Block fcBlockTorchFiniteUnlit;
    public static Block fcBlockTorchFiniteBurning;
    
    public static Block fcBlockStoneRough;
    public static Block fcBlockStoneRoughMidStrata;
    public static Block fcBlockStoneRoughDeepStrata;
    
    public static Block fcBlockWorkStump;
    public static FCBlockBasketWicker fcBlockBasketWicker;    
    public static FCBlockLogSpike fcBlockLogSpike;    
    public static Block fcBlockTorchNetherUnlit; 
    
    public static Block fcBlockWorkbench;
    public static Block fcBlockChest;
    public static Block fcBlockDoorWood;
    public static Block fcBlockWeb;
    
    public static Block fcBlockUnfiredClay;
    public static Block fcBlockMyceliumSlab;    
    public static FCBlockToolPlaced fcBlockToolPlaced;
    
    public static Block fcBlockBrickLooseStairs;    
    public static Block fcBlockCobblestoneLooseStairs;
    
    public static FCBlockLogSmouldering fcBlockLogSmouldering;
    public static FCBlockWoodCinders fcBlockWoodCinders;
    public static FCBlockStumpCharred fcBlockStumpCharred;
    public static Block fcBlockAshGroundCover;
    
    public static FCBlockSnowLoose fcBlockSnowLoose;
    public static FCBlockSnowLooseSlab fcBlockSnowLooseSlab;
    public static FCBlockSnowSolid fcBlockSnowSolid;
    public static FCBlockSnowSolidSlab fcBlockSnowSolidSlab;
    
    public static FCBlockLadder fcBlockLadder;
    public static FCBlockLadderOnFire fcBlockLadderOnFire;
    
    public static FCBlockShovel fcBlockShovel;
    public static FCBlockHamper fcBlockHamper;
    
    public static FCBlockCreeperOysters fcBlockCreeperOysters;
    public static FCBlockCreeperOystersSlab fcBlockCreeperOystersSlab;
    
    public static FCBlockTorchNetherBurning fcBlockTorchNetherBurning;
    
    public static FCBlockBucket fcBlockBucketEmpty;
    public static FCBlockBucketWater fcBlockBucketWater;
    public static FCBlockBucketCement fcBlockBucketCement;
    public static FCBlockBucketMilk fcBlockBucketMilk;
    public static FCBlockBucketMilkChocolate fcBlockBucketMilkChocolate;
    
    public static FCBlockMilk fcBlockMilk;
    public static FCBlockMilkChocolate fcBlockMilkChocolate;
    
    public static FCBlockGearBox fcBlockGearBox;
    
    public static FCBlockSpike fcBlockSpikeIron;
    public static FCBlockLightningRod fcBlockLightningRod;
    
    public static FCBlockChunkOreIron fcBlockChunkOreIron;
    public static FCBlockChunkOreGold fcBlockChunkOreGold;
    
    public static Block fcBlockStoneBrickLoose;
    public static Block fcBlockStoneBrickLooseSlab;
    public static Block fcBlockStoneBrickLooseStairs;
    
    public static Block fcBlockNetherBrickLoose;
    public static Block fcBlockNetherBrickLooseSlab;
    public static Block fcBlockNetherBrickLooseStairs;
    
    public static FCBlockNetherrackFalling fcBlockNetherrackFalling;
    
    public static FCBlockLavaPillow fcBlockLavaPillow;
    
    public static FCBlockMushroomCap fcBlockMushroomCapBrown;
    public static FCBlockMushroomCap fcBlockMushroomCapRed;
    
    public static FCBlockChunkOreStorageIron fcBlockChunkOreStorageIron;
    public static FCBlockChunkOreStorageGold fcBlockChunkOreStorageGold;
    
    public static FCBlockWicker fcBlockWicker;
    public static FCBlockWickerSlab fcBlockWickerSlab;    
    public static FCBlockWickerPane fcBlockWickerPane;
    
    public static FCBlockGrate fcBlockGrate;
    public static FCBlockSlats fcBlockSlats;
    
    public static FCBlockFarmland fcBlockFarmland;
    public static FCBlockFarmlandFertilized fcBlockFarmlandFertilized;
    public static FCBlockWheatCrop fcBlockWheatCrop;
    public static FCBlockWheatCropTop fcBlockWheatCropTop;
    public static FCBlockWeeds fcBlockWeeds;
    public static FCBlockPlanterSoil fcBlockPlanterSoil;
    
	// New Items
	
    public static Item fcItemBucketCement;    

    public static Item fcItemWolfRaw;
    public static Item fcItemWolfCooked;
    
    public static Item fcItemNethercoal;
    
    public static Item fcItemHempSeeds;
    
    public static Item fcItemHemp;
    
    public static Item fcItemGear;

    public static Item fcItemFlour;
    
    public static Item fcItemHempFibers;
    
    public static Item fcItemScouredLeather;
    
    public static Item fcItemDonut;
    
    public static Item fcItemRope;
    
    public static Item fcItemSlatsOld;
    
    public static Item fcItemDung;
    
    public static Item fcItemWaterWheel;
    
    public static Item fcItemWindMillBlade;
    public static Item fcItemWindMill;
    
    public static Item fcItemHempCloth;
    
    public static Item fcItemGrateOld;
    public static Item fcItemWickerPaneOld;
    
    public static Item fcItemTannedLeather;
    public static Item fcItemStrap;
    public static Item fcItemBelt;

    public static Item fcItemFoulFood;
    
    public static Item fcItemWoodBlade;
    
    public static Item fcItemGlue;
    public static Item fcItemTallow;
    
    public static Item fcItemHaft;
    
    public static Item fcItemSteel;
    
    public static Item fcItemRefinedPickAxe;    
    public static Item fcItemRefinedShovel;
    public static Item fcItemRefinedHoe;
    public static Item fcItemBattleAxe;
    public static Item fcItemRefinedSword;
    
    public static Item fcItemGroundNetherrack;
    public static Item fcItemHellfireDust;
    public static Item fcItemConcentratedHellfire;
    
    public static Item fcItemArmorPlate;
    public static Item fcItemPlateHelm;
    public static Item fcItemPlateBreastPlate;
    public static Item fcItemPlateLeggings;
    public static Item fcItemPlateBoots;
    
    public static Item fcItemCompositeBow;
    
    public static Item fcItemBroadheadArrowhead;
    public static Item fcItemBroadheadArrow;
    
    public static Item fcItemCoalDust;
    
    public static Item fcItemPadding;	
    
    public static Item fcItemFilament;
    
    public static Item fcItemPolishedLapis;
    
    public static Item fcItemUrn;
    public static Item fcItemSoulUrn;
    
    public static Item fcItemHardBoiledEgg;
    
    public static Item fcItemPotash;
    public static Item fcItemSoap;
    
    public static Item fcItemSawDust;
    
    public static Item fcItemArmorGimpHelm;
    public static Item fcItemArmorGimpChest;
    public static Item fcItemArmorGimpLeggings;
    public static Item fcItemArmorGimpBoots;
    
    public static Item fcItemDynamite;
    
    public static Item fcItemBreedingHarness;
    
    public static Item fcItemSoulDust;
    
    public static Item fcItemMattock;
    
    public static Item fcItemRefinedAxe;
    
    public static Item fcItemNetherSludge;
    public static Item fcItemNetherBrick;
    
    public static Item fcItemTuningFork;
    
    public static Item fcItemArcaneScroll;    
    
    public static Item fcItemCandle;
    
    public static Item fcItemBloodMossSpores;
    
    public static Item fcItemMould;
    
    public static Item fcItemCanvas;
    
    public static Item fcItemDogFood;
    public static Item fcItemRawEgg;
    public static Item fcItemFriedEgg;
    
    public static Item fcItemScrew;
        
    public static Item fcItemRottenArrow;
    
    public static Item fcItemOcularOfEnder;
    public static Item fcItemEnderSpectacles;
    
    public static Item fcItemStake;
    
    public static Item fcItemBrimstone;
    public static Item fcItemNitre;
    
    public static Item fcItemElement;
    public static Item fcItemFuse;
    
    public static Item fcItemBlastingOil;
    
    public static Item fcItemWindMillVertical;
    
    public static Item fcItemBoiledPotato;
    
    public static Item fcItemMuttonRaw;
    public static Item fcItemMuttonCooked;
    
    public static Item fcItemWitchWart;
    
    public static Item fcItemCookedCarrot;
    public static Item fcItemTastySandwich;
    public static Item fcItemSteakAndPotatoes;
    public static Item fcItemHamAndEggs;
    public static Item fcItemSteakDinner;
    public static Item fcItemPorkDinner;
    public static Item fcItemWolfDinner;
    public static Item fcItemRawKebab;
    public static Item fcItemCookedKebab;
    public static Item fcItemChickenSoup;
    public static Item fcItemFishSoup;
    public static Item fcItemHeartyStew;
    
    public static Item fcItemMushroomRed;
    public static Item fcItemMushroomBrown;
    
    public static Item fcItemNuggetIron;
    public static Item fcItemMail;
    
    public static Item fcItemRawMysteryMeat;
    public static Item fcItemCookedMysteryMeat;
    public static Item fcItemRawMushroomOmelet;
    public static Item fcItemCookedMushroomOmelet;
    public static Item fcItemRawScrambledEggs;
    public static Item fcItemCookedScrambledEggs;
    
    public static Item fcItemCreeperOysters;
    
    public static Item fcItemArmorWoolHelm;
    public static Item fcItemArmorWoolChest;
    public static Item fcItemArmorWoolLeggings;
    public static Item fcItemArmorWoolBoots;
    
    public static Item fcItemArmorPaddedHelm;
    public static Item fcItemArmorPaddedChest;
    public static Item fcItemArmorPaddedLeggings;
    public static Item fcItemArmorPaddedBoots;
    
    public static Item fcItemArmorTannedHelm;
    public static Item fcItemArmorTannedChest;
    public static Item fcItemArmorTannedLeggings;
    public static Item fcItemArmorTannedBoots;
    
    public static Item fcItemIngotDiamond;
    
    public static Item fcItemLeatherCut;
    public static Item fcItemTannedLeatherCut;    
    public static Item fcItemScouredLeatherCut;
    
    public static Item fcItemFishingRodBaited;
    
    public static Item fcItemPileDirt;
    public static Item fcItemPileSand;
    public static Item fcItemPileGravel;
    
    public static Item fcItemBatWing;
    public static Item fcItemGoldenDung;    
    public static Item fcItemBark;
    
    public static Item fcItemPileSoulSand;
    
    public static Item fcItemRedstoneLatch;
    
    public static Item fcItemNuggetSteel;
    
    public static Item fcItemWool;
    
    public static Item fcItemCocoaBeans;
    public static Item fcItemChocolate;
    public static Item fcItemBucketMilkChocolate;
    
    public static Item fcItemSoulFlux;
    public static Item fcItemEnderSlag;
    
    public static Item fcItemPastryUncookedCake;
    public static Item fcItemPastryUncookedCookies;
    public static Item fcItemPastryUncookedPumpkinPie;
    
    public static Item fcItemMysteriousGland;
    
    public static Item fcItemBeastLiverRaw;
    public static Item fcItemBeastLiverCooked;

    public static Item fcItemAncientProphecy;
    
    public static Item fcItemStumpRemover;
    
    public static Item fcItemChiselWood;
    public static Item fcItemStone;
    public static Item fcItemChiselStone;
    
    public static Item fcItemClubWood;    
    public static Item fcItemFireStarterSticks;
    public static Item fcItemFireStarterBow;
    
    public static Item fcItemChunkIronOre;
    public static Item fcItemPileIronOre;
    
    public static Item fcItemChiselIron;
    
    public static Item fcItemChunkGoldOre;
    public static Item fcItemPileGoldOre;
    
    public static Item fcItemWickerPiece;
    
    public static Item fcItemKnittingNeedles;
    public static Item fcItemKnitting;
    public static Item fcItemWoolKnit;
    
    public static Item fcItemClubBone;
    public static Item fcItemMeatCured;
    public static Item fcItemMetalFragment;
    public static Item fcItemPileClay;
    public static Item fcItemMeatBurned;
    public static Item fcItemChickenFeed;
    
    public static Item fcItemFishHookBone;    
    public static Item fcItemCarvingBone;    
    
    public static Item fcItemStoneBrick;
    
    public static Item fcItemWickerWeaving;
    
    public static Item fcItemWheat;
    public static Item fcItemWheatSeeds;
    
    public static Item fcItemBreadDough;
    
    public static Item fcItemStraw;
    
    public static Item fcItemBrickUnfired;
    public static Item fcItemNetherBrickUnfired;
    
    // legacy ID for MCPatcher grass slab support
    
    private static int fcBlockDirtSlabID = 206;
    
    // custom Container IDs
    // vanilla IDs can be found in NetClientHandler.handleOpenWindow()
    
    public static int fcMillStoneContainerID = 222;
    public static int fcCauldronContainerID = 223;
    public static int fcHopperContainerID = 224;
    public static int fcCrucibleContainerID = 225;
    public static int fcAnvilContainerID = 226;
    public static int fcBlockDispenserContainerID = 227;
    public static int fcPulleyContainerID = 228;
    public static int fcInfernalEnchanterContainerID = 229;
    public static int fcFurnaceBrickContainerID = 230;
    public static int fcHamperContainerID = 231;
    public static int fcVanillaAnvilContainerID = 232;
    
    // configuration settings
    
    private static final Map m_configurationMap = new HashMap();
    
    public static boolean fcDisableMinecartChanges = false;

    private static boolean fcLocalEnableHardcoreBuoy = true;
    private static boolean fcServerEnableHardcoreBuoy = true;
    
    private static int fcLocalHardcorePlayerNamesLevel = 1;
    private static int fcServerHardcorePlayerNamesLevel = 1;
    
    public static boolean fcDisableEndText = true;
    
    public static boolean fcDisableGearBoxPowerDrain = false;
    
    public static String fcPlayerSkinURL = "http://skins.minecraft.net/MinecraftSkins/";
    public static String fcPlayerCloakURL = "http://skins.minecraft.net/MinecraftCloaks/";
    
    // temporary state variables
    
    public static boolean bIsLensBeamBeingRemoved = false;
    
    // packet channels
    
    static public final String fcCustomPacketChannelSpawnCustomEntity = "FC|SE";
    static public final String fcCustomPacketChannelCustomEntityEvent = "FC|EV";
    static public final String fcCustomPacketChannelVersionCheck = "FC|VC";
    static public final String fcCustomPacketChannelBTWOptions = "FC|OP";
    static public final String fcCustomPacketChannelOpenCustomInterface = "FC|OI";
    
    static public final int fcCustomSpawnEntityPacketTypeCanvas = 0;
    static public final int fcCustomSpawnEntityPacketTypeWindMill = 1;
    static public final int fcCustomSpawnEntityPacketTypeWaterWheel = 2;
    static public final int fcCustomSpawnEntityPacketTypeMiningCharge = 3;
    static public final int fcCustomSpawnEntityPacketTypeItemBloodWoodSapling = 4;
    static public final int fcCustomSpawnEntityPacketTypeItemFloating = 5;
    static public final int fcCustomSpawnEntityPacketTypeDynamite = 6;
    static public final int fcCustomSpawnEntityPacketTypeUrn = 7;
    static public final int fcCustomSpawnEntityPacketTypeBlockLiftedByPlatform = 8;	
    static public final int fcCustomSpawnEntityPacketTypeWindMillVertical = 9;
    static public final int fcCustomSpawnEntityPacketTypeSpiderWeb = 10;
    static public final int fcCustomSpawnEntityPacketTypeSoulSand = 11;
    
    static public final int fcCustomEntityEventPacketTypeSetAttackTarget = 0;
    static public final int fcCustomEntityEventPacketTypeSquidTentacleAttack = 1;
    static public final int fcCustomEntityEventPacketTypeCowKickAttack = 2;
    
    // new step sounds
    
    public static StepSound fcStepSoundSquish = new FCStepSoundSquish("squish", 0.5F, 0.1F);

	// global static variables
	
	public static boolean m_bBlocksPotentialFluidSources[] = new boolean[4096];
	
	public static int fcBlockWoodSidingItemStubID;
	public static int fcBlockWoodMouldingItemStubID;
	public static int fcBlockWoodCornerItemStubID;
	public static int fcBlockWoodMouldingDecorativeItemStubID;
	public static int fcBlockWoodSidingDecorativeItemStubID;
	
	// Aux FX IDs
   
	public static final int m_iAnimalBirthingAuxFXID = 2222;
	public static final int m_iSawDamageEntityAuxFXID = 2223;	
	public static final int m_iNetherGrothSporesAuxFXID = 2224;	
	public static final int m_iGhastScreamSoundAuxFXID = 2225;	
	public static final int m_iBurpSoundAuxFXID = 2226;	
	public static final int m_iFireFizzSoundAuxFXID = 2227;	
	public static final int m_iGhastMoanSoundAuxFXID = 2228;	
	public static final int m_iMiningChargeExplosionAuxFXID = 2229;	
	public static final int m_iHopperXPEjectAuxFXID = 2230;	
	public static final int m_iItemCollectionPopSoundAuxFXID = 2231;	
	public static final int m_iXPEjectPopSoundAuxFXID = 2232;	
	public static final int m_iHopperCloseSoundAuxFXID = 2233;	
	public static final int m_iRedstonePowerClickSoundAuxFXID = 2234;	
	public static final int m_iMechanicalDeviceExplodeAuxFXID = 2235;	
	public static final int m_iBlockPlaceAuxFXID = 2236;	
	public static final int m_iDynamiteFuseSoundAuxFXID = 2237;	
	public static final int m_iClickLowPitchSoundAuxFXID = 2238;	
	public static final int m_iWolfHurtSoundAuxFXID = 2239;	
	public static final int m_iChickenHurtSoundAuxFXID = 2240;	
	public static final int m_iBlockDispenserSmokeEffectAuxFXID = 2241;	
	public static final int m_iCompanionCubeDeathAuxFXID = 2242;	
	public static final int m_iPossessedChickenExplosionAuxFXID = 2243;	
	public static final int m_iEnderBlockCollectAuxFXID = 2244;	
	public static final int m_iEnderBlockConvertAuxFXID = 2245;	
	public static final int m_iEnderBlockPlaceAuxFXID = 2246;	
	public static final int m_iEnderChangeDimensionAuxFXID = 2247;	
	public static final int m_iSoulUrnShatterAuxFXID = 2248;	
	public static final int m_iMelonExplodeAuxFXID = 2249;	
	public static final int m_iPumpkinExplodeAuxFXID = 2250;	
	public static final int m_iMelonImpactSoundAuxFXID = 2251;
	public static final int m_iBlockDestroyRespectParticleSettingsAuxFXID = 2252;
	public static final int m_iCowMilkFillAuxFXID = 2253;
	public static final int m_iCowMilkedAuxFXID = 2254;
	public static final int m_iCowConvertToMooshroomAuxFXID = 2255;
	public static final int m_iWolfHowlAuxFXID = 2256;
	public static final int m_iWolfConvertToDireAuxFXID = 2257;
	public static final int m_iCreeperNeuteredAuxFXID = 2258;
	public static final int m_iPossessedPigTransformToPigmanAuxFXID = 2259;	
	public static final int m_iPossessedVillagerTransformToWitchAuxFXID = 2260;
	public static final int m_iSheepWoolRegrowAuxFXID = 2261;
	public static final int m_iSquidTentacleFlingAuxFXID = 2262;
	public static final int m_iSnowGolemCreatedAuxFXID = 2263;
	public static final int m_iIronGolemCreatedAuxFXID = 2264;
	public static final int m_iTossTheMilkAuxFXID = 2265;
	public static final int m_iDungAppliedToWolfAuxFXID = 2266;
	public static final int m_iStumpRemovedAuxFXID = 2267;
	public static final int m_iShaftRippedOffLogAuxFXID = 2268;
	public static final int m_iStoneRippedOffAuxFXID = 2269;
	public static final int m_iGravelRippedOffStoneAuxFXID = 2270;
	public static final int m_iWoodBlockDestroyedAuxFXID = 2271;
	public static final int m_iBlockDestroyedWithImproperToolAuxFXID = 2272;
	public static final int m_iPossessedSquidTransformToGhastAuxFXID = 2273;
	public static final int m_iMortarAppliedAuxFXID = 2274;
	public static final int m_iLooseBlockOnMortarAuxFXID = 2275;
	public static final int m_iLogSmoulderingFallAuxFXID = 2276;
	public static final int m_iLogSmoulderingExplosionAuxFXID = 2277;
	public static final int m_iWaterEvaporateAuxFXID = 2278;
	public static final int m_iWitherCreatedAuxFXID = 2279;
	public static final int m_iLightningStrikeAuxFXID = 2280;
	public static final int m_iFlamingNetherrackFallAuxFXID = 2281;
	public static final int m_iCactusExplodeAuxFXID = 2282;
	public static final int m_iAnimalEatAuxFXID = 2283;
	public static final int m_iWolfEatAuxFXID = 2284;
	public static final int m_iEatFailAuxFXID = 2285;
	
	// new potion effects
	
    public static final Potion potionFortune = (new Potion(31, false, 14270531)).setPotionName("potion.fcFortune").setIconIndex(2, 0);	
    public static final Potion potionLooting = (new Potion(30, false, 9643043)).setPotionName("potion.fcLooting").setIconIndex(4, 0);	
    public static final Potion potionTrueSight = (new Potion(29, false, 14270531)).setPotionName("potion.fcTrueSight").setIconIndex(4, 1);
    
    public FCBetterThanWolves() {
    	super("Better Than Wolves CE", fcVersionString, "BTW");
    	this.shouldVersionCheck = false;
	}
    
    @Override
    public void PreInitialize() {
		registerConfigProperties();
    }
	
	@Override
    public void Initialize()
    {
    	FCAddOnHandler.LogMessage("Better Than Wolves Community Edition Version " + getVersion() + " Initializing...");        	
		ModInstallationIntegrityTest();
    	
//		// client only
//		PreInitClient();
		
		InstantiateModBlocks();
		InstantiateModItems();
		
		CreateModTileEntityMappings();
		CreateModEntityMappings();
		
		FCRecipes.AddAllModRecipes();
		
		InitBlocksPotentialFluidSources();
		InitDispenserBehaviors();
		
		FCTileEntityBeacon.InitializeEffectsByBlockID();
		
		InitCustomPackets();
		
//		// client only
//		PostInitClient();
		
		FCAddOnHandler.LogMessage("Better Than Wolves Initialization Complete.");
    }
	
    @Override
	public String GetLanguageFilePrefix()
	{
		return "BTW";
	}
	
    //------------- Class Specific Methods ------------//
	
    public String getVersion()
    {
    	return fcVersionString;
    }
    
	public void ModInstallationIntegrityTest()
	{
	    try
	    {
	    	World.InstallationIntegrityTest();
	    	Block.InstallationIntegrityTest();
	    	EntityLiving.InstallationIntegrityTest();
	    	EntityPlayer.InstallationIntegrityTestPlayer();
	    	EntityItem.InstallationIntegrityTestEntityItem();
//	    	// Client only
//	    	GuiIngame.InstallationIntegrityTest();
//	    	GuiContainer.InstallationIntegrityTest();
//	    	EntityRenderer.InstallationIntegrityTest();
	    }
	    catch (Exception e)
	    {
	    	String errorString = "***Better Than Wolves has not been properly installed.  Please consult the readme.txt file for installation instructions***";
	    	
	    	FCAddOnHandler.LogMessage(errorString);
			
	        throw new RuntimeException(e);
	    }
	}
    
	static public boolean IsHardcoreBuoyEnabled(World world)
	{
		if (!world.isRemote)
		{
    		return fcLocalEnableHardcoreBuoy;
		}
    	else
    	{
    		return fcServerEnableHardcoreBuoy;
    	}
	}
	
	static public boolean IsHardcorePlayerNamesEnabled(World world)
	{
		if (!world.isRemote)
		{
    		return fcLocalHardcorePlayerNamesLevel == 1;
		}
		else
		{
			return fcServerHardcorePlayerNamesLevel == 1;
		}
	}
	
	static public boolean AreHardcorePlayerNamesObstructed(World world)
	{
		if (!world.isRemote)
		{
    		return fcLocalHardcorePlayerNamesLevel == 2;
		}
		else
		{
			return fcServerHardcorePlayerNamesLevel == 2;
		}
	}
	
    private static int ValidateIDFromFile(int originalID, int fileID)
    {
    	if (fileID > 0)
    	{
    		return fileID;
    	}
    	else
    	{
    		return originalID;
    	}
    }
    
	private void InstantiateModBlocks()
	{
		fcBlockSlabSandAndGravel = new FCBlockSlabSandAndGravel(parseID("fcBlockSlabFallingID"));
		
	    fcBlockArcaneVessel = new FCBlockArcaneVessel(parseID("fcBlockArcaneVesselID"));
	    
	    fcBlockAxlePowerSource = new FCBlockAxlePowerSource(parseID("fcBlockAxlePowerSourceID"));
        
	    fcBlockSidingAndCornerBlackStone = (new FCBlockSidingAndCornerAndDecorative(parseID("fcBlockSidingAndCornerBlackStoneID"), 
	    	Material.rock, "fcBlockDecorativeBlackStone", 1.5F, 10F,Block.soundStoneFootstep, "fcBlockSidingBlackStone")).SetPicksEffectiveOn();
	    
	    fcBlockMouldingAndDecorativeBlackStone = (new FCBlockMouldingAndDecorative(parseID("fcBlockMouldingAndDecorativeBlackStoneID"), Material.rock, 
	    	"fcBlockDecorativeBlackStone", "fcBlockColumnBlackStone_side", 
	    	fcBlockSidingAndCornerBlackStone.blockID, 1.5F, 10F, Block.soundStoneFootstep, "fcBlockMouldingBlackStone")).SetPicksEffectiveOn();
	    
		fcBlockAestheticOpaqueEarth = new FCBlockAestheticOpaqueEarth(parseID("fcBlockAestheticOpaqueEarthID"));
        
		fcBlockCandle = new FCBlockCandle(parseID("fcBlockCandleID"));
		
		fcBlockSandstoneSidingAndCorner = (new FCBlockSandstoneSidingAndCornerAndDecorative(parseID("fcBlockSandstoneSidingAndCornerID"))).SetPicksEffectiveOn();		    
		fcBlockSandstoneMouldingAndDecorative = (new FCBlockSandstoneMouldingAndDecorative(parseID("fcBlockSandstoneMouldingAndDecorativeID"),
			fcBlockSandstoneSidingAndCorner.blockID)).SetPicksEffectiveOn();
        
        fcBlockWoodOakSidingAndCorner = new FCBlockWoodSidingAndCornerAndDecorative(parseID("fcBlockWoodOakSidingAndCornerID"), 
        	"FCBlockDecorativeWoodOak", "fcBlockWoodOakSiding");
        
		fcBlockSmoothStoneSidingAndCorner = (new FCBlockSidingAndCornerAndDecorative(parseID("fcBlockSmoothStoneSidingAndCornerID"), 
			Material.rock, "fcBlockDecorativeStone", 1.5F, 10F, Block.soundStoneFootstep, "fcStoneSiding")).SetPicksEffectiveOn();
		
		fcBlockBrickSidingAndCorner = (new FCBlockSidingAndCornerAndDecorative(parseID("fcBlockBrickSidingAndCornerID"), 
			Material.rock, "fcBlockDecorativeBrick", 2.0F, 10F, Block.soundStoneFootstep, "fcBrickSiding")).SetPicksEffectiveOn();
		    
		fcBlockBrickMouldingAndDecorative = (new FCBlockMouldingAndDecorative(parseID("fcBlockBrickMouldingAndDecorativeID"), 
			Material.rock, "fcBlockDecorativeBrick", "fcBlockColumnBrick_side", 
			fcBlockBrickSidingAndCorner.blockID, 2.0F, 10F, Block.soundStoneFootstep, "fcBrickMoulding")).SetPicksEffectiveOn();
        
		fcBlockNetherBrickSidingAndCorner = (new FCBlockNetherBrickSidingAndCornerAndDecorative(parseID("fcBlockNetherBrickSidingAndCornerID"))).SetPicksEffectiveOn();
		    
		fcBlockNetherBrickMouldingAndDecorative = (new FCBlockNetherBrickMouldingAndDecorative(parseID("fcBlockNetherBrickMouldingAndDecorativeID"), 
			fcBlockNetherBrickSidingAndCorner.blockID)).SetPicksEffectiveOn();
        
	    fcBlockWhiteStoneStairs = new FCBlockStairsWhiteStone(parseID("fcBlockWhiteStoneStairsID"));
	    
        fcBlockWhiteStoneSidingAndCorner = (new FCBlockSidingAndCornerAndDecorative(parseID("fcBlockWhiteStoneSidingAndCornerID"), 
        	Material.rock, "fcBlockDecorativeWhiteStone", 1.5F, 10F, Block.soundStoneFootstep, "fcWhiteStoneSiding")).SetPicksEffectiveOn();
		    
        fcBlockWhiteStoneMouldingAndDecorative = (new FCBlockMouldingAndDecorative(parseID("fcBlockWhiteStoneMouldingAndDecorativeID"), 
        	Material.rock, "fcBlockDecorativeWhiteStone", "fcBlockColumnWhiteStone_side", 
        	fcBlockWhiteStoneSidingAndCorner.blockID, 1.5F, 10F, Block.soundStoneFootstep, "fcWhiteStoneMoulding")).SetPicksEffectiveOn();
        
		fcBlockStakeString = new FCBlockStakeString(parseID("fcBlockStakeStringID"));
		fcBlockStake = new FCBlockStake(parseID("fcBlockStakeID"));
		
		fcBlockScrewPump = new FCBlockScrewPump(parseID("fcBlockScrewPumpID"));
		
        fcBlockWoodSpruceSidingAndCorner = new FCBlockWoodSidingAndCornerAndDecorative(parseID("fcBlockWoodSpruceSidingAndCornerID"), "fcBlockDecorativeWoodSpruce", "fcWoodSpruceSiding");
        
	    fcBlockWoodSpruceMouldingAndDecorative = new FCBlockWoodMouldingAndDecorative(parseID("fcBlockWoodSpruceMouldingID"), 
	    	"fcBlockDecorativeWoodSpruce", "fcBlockColumnWoodSpruce_side", 
	    	fcBlockWoodSpruceSidingAndCorner.blockID, "fcWoodSpruceMoulding");	    
		    
        fcBlockWoodBirchSidingAndCorner = new FCBlockWoodSidingAndCornerAndDecorative(parseID("fcBlockWoodBirchSidingAndCornerID"), 
        	"fcBlockDecorativeWoodBirch", "fcWoodBirchSiding");
        
	    fcBlockWoodBirchMouldingAndDecorative = new FCBlockWoodMouldingAndDecorative(parseID("fcBlockWoodBirchMouldingID"), 
	    	"fcBlockDecorativeWoodBirch", "fcBlockColumnWoodBirch_side", 
	    	fcBlockWoodBirchSidingAndCorner.blockID, "fcWoodBirchMoulding");
	    
        fcBlockWoodJungleSidingAndCorner = new FCBlockWoodSidingAndCornerAndDecorative(parseID("fcBlockWoodJungleSidingAndCornerID"), 
        	"fcBlockDecorativeWoodJungle", "fcWoodJungleSiding");
        
	    fcBlockWoodJungleMouldingAndDecorative = new FCBlockWoodMouldingAndDecorative(parseID("fcBlockWoodJungleMouldingID"), 
	    	"fcBlockDecorativeWoodJungle", "fcBlockColumnWoodJungle_side", 
	    	fcBlockWoodJungleSidingAndCorner.blockID, "fcWoodJungleMoulding");
		    
	    fcBlockStoneBrickSidingAndCorner = (new FCBlockSidingAndCornerAndDecorative(parseID("fcBlockStoneBrickSidingAndCornerID"), 
	    	Material.rock, "fcBlockDecorativeStoneBrick", 1.5F, 10F, 
    		Block.soundStoneFootstep, "fcStoneBrickSiding")).SetPicksEffectiveOn();
	    
	    fcBlockStoneBrickMouldingAndDecorative = (new FCBlockMouldingAndDecorative(parseID("fcBlockStoneBrickMouldingID"), 
	    	Material.rock, "fcBlockDecorativeStoneBrick", "fcBlockColumnStoneBrick_side", 
			fcBlockStoneBrickSidingAndCorner.blockID, 1.5F, 10F, Block.soundStoneFootstep, "fcStoneBrickMoulding")).SetPicksEffectiveOn();
	    
		fcBlockFarmlandLegacyFertilized = new FCBlockFarmlandLegacyFertilized(parseID("fcBlockFarmlandFertilizedID"));
		
		fcBlockWoolSlabTop = new FCBlockWoolSlab(parseID("fcBlockWoolSlabTopID"), true);

		fcBlockDirtSlab = new FCBlockDirtSlab(parseID("fcBlockDirtSlabID"));
		
		fcBlockBloodMoss = new FCBlockNetherGrowth(parseID("fcBlockNetherGrowthID"));
		
		fcInfernalEnchanter = new FCBlockInfernalEnchanter(parseID("fcInfernalEnchanterID"));
		
        fcSoulforgedSteelBlock = new FCBlockSoulforgedSteel(parseID("fcSoulforgedSteelBlockID"));

		fcBlockDetectorGlowingLogic = new FCBlockDetectorLogicGlowing(parseID("fcBlockDetectorGlowingLogicID"));
		
		fcBlockBloodLeaves = new FCBlockBloodWoodLeaves(parseID("fcLeavesID"));
		
		fcBloodWood = new FCBlockBloodWood(parseID("fcBloodWoodID"));
		
		fcAestheticVegetation = new FCBlockAestheticVegetation(parseID("fcAestheticVegetationID"));
		
	    fcBlockSmoothStoneMouldingAndDecorative = (new FCBlockMouldingAndDecorative(parseID("fcStoneMouldingID"), 
	    	Material.rock, "fcBlockDecorativeStone", "fcBlockColumnStone_side", "fcBlockColumnStone_top", "fcBlockPedestalStone_side", "fcBlockPedestalStone_top", 
			fcBlockSmoothStoneSidingAndCorner.blockID, 1.5F, 10F, Block.soundStoneFootstep, "fcStoneMoulding")).SetPicksEffectiveOn();
	    
		fcAestheticOpaque = new FCBlockAestheticOpaque(parseID("fcAestheticOpaqueID"));
		fcAestheticNonOpaque = new FCBlockAestheticNonOpaque(parseID("fcAestheticNonOpaqueID"));
		
		fcBlockMiningCharge = new FCBlockMiningCharge(parseID("fcMiningChargeID"));		
		
		fcBuddyBlock = new FCBlockBuddyBlock(parseID("fcBuddyBlockID"));

		fcKiln = new FCBlockKiln(parseID("fcKilnID"));
		
		fcWoolSlab = new FCBlockWoolSlab(parseID("fcWoolSlabID"), false);
		
        fcAnvil = new FCBlockSoulforge(parseID("fcAnvilID"));
        
        fcLightBulbOff = new FCBlockLightBulb(parseID("fcLightBulbOffID"), false);
        fcLightBulbOn = new FCBlockLightBulb(parseID("fcLightBulbOnID"), true);
        
        fcBBQ = new FCBlockBBQ(parseID("fcBBQID"));
        
        fcHopper = new FCBlockHopper(parseID("fcHopperID"));

        fcSaw = new FCBlockSaw(parseID("fcSawID"));
        
        fcPlatform = new FCBlockPlatform(parseID("fcPlatformID"));
        
        fcCement = new FCBlockCement(parseID("fcCementID"));        

        fcPulley = new FCBlockPulley(parseID("fcPulleyID"));
        
        fcBlockPressurePlateSoulforgedSteel = (new FCBlockPressurePlate(parseID("fcPressurePlateObsidianID"), 
        	"fcBlockPressurePlateSoulforgedSteel", fcMaterialSoulforgedSteel, EnumMobType.players)).setHardness(1.0F).setResistance(2000F).
        	setStepSound(Block.soundMetalFootstep).setUnlocalizedName("pressurePlate");

	    fcBlockWoodOakMouldingAndDecorative = new FCBlockWoodMouldingAndDecorative(parseID("fcMouldingID"), 
	    	"FCBlockDecorativeWoodOak", "fcBlockColumnWoodOak_side", fcBlockWoodOakSidingAndCorner.blockID, "fcBlockWoodOakMoulding");
	    
        fcBlockLegacySmoothstoneAndOakCorner = new FCBlockLegacyCorner(parseID("fcCornerID"));        
        
        fcBlockDispenser = new FCBlockBlockDispenser(parseID("fcBlockDispenserID"));
        
        fcCauldron = new FCBlockCauldron(parseID("fcCauldronID"));

        fcDetectorRailWood = (new FCBlockDetectorRail(parseID("fcDetectorRailWoodID"))).
        	setHardness(0.7F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("fcBlockDetectorRailWood");
        
        fcBlockDetectorRailSoulforgedSteel = (new FCBlockDetectorRail(parseID("fcDetectorRailObsidianID"))).
        	setHardness(0.7F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("fcBlockDetectorRailSoulforgedSteel");
        
        fcCompanionCube = new FCBlockCompanionCube(parseID("fcCompanionCubeID"));

        fcBlockDetector = new FCBlockDetectorBlock(parseID("fcBlockDetectorID"));
        fcBlockDetectorLogic = (new FCBlockDetectorLogic(parseID("fcBlockDetectorLogicID"))).setUnlocalizedName("fcBlockDetectorLogic");
        
        fcLens = new FCBlockLens(parseID("fcBlockLensID"));
        
        fcBlockHempCrop = new FCBlockHempCrop(parseID("fcHempCropID"));

        fcHandCrank = new FCBlockHandCrank(parseID("fcHandCrankID"));
        
        fcMillStone = new FCBlockMillStone(parseID("fcMillStoneID"));

        fcAnchor = new FCBlockAnchor(parseID("fcAnchorID"));

        fcRopeBlock = new FCBlockRope(parseID("fcRopeBlockID"));

        fcBlockLegacySmoothstoneAndOakSiding = new FCBlockLegacyOmniSlab(parseID("fcOmniSlabID"));

        fcBlockAxle = new FCBlockAxle(parseID("fcAxleBlockID"));

        fcBlockRedstoneClutch = new FCBlockRedstoneClutch(parseID("fcGearBoxID"));
        
        fcTurntable = new FCBlockTurntable(parseID("fcTurntableID"));
        
        fcBellows = new FCBlockBellows(parseID("fcBellowsID"));
        
        fcBlockFireStoked = new FCBlockFireStoked(parseID("fcStokedFireID"));
        
        fcUnfiredPottery = new FCBlockUnfiredPottery(parseID("fcUnfiredPotteryID"));
        
        fcCrucible = new FCBlockCrucible(parseID("fcCrucibleID"));
        fcPlanter = new FCBlockPlanter(parseID("fcPlanterID"));
        fcVase = new FCBlockVase(parseID("fcVaseID"));
        
        fcBlockRottenFlesh = new FCBlockRottenFlesh(parseID("fcBlockRottenFleshID"));
        
        fcBlockShaft = new FCBlockShaft(parseID("fcBlockShaftID"));
        
        fcBlockSoulforgeDormant = new FCBlockSoulforgeDormant(parseID("fcBlockSoulforgeDormantID"));
        
	    fcBlockSmoothstoneStairs = (new FCBlockStairs(parseID("fcBlockSmoothstoneStairsID"), Block.stone, 0)).
	    	SetPicksEffectiveOn().setUnlocalizedName("fcBlockSmoothstoneStairs");
	    
	    fcBlockRottenFleshSlab = new FCBlockRottenFleshSlab(parseID("fcBlockRottenFleshSlabID"));
	    
	    fcBlockBoneSlab = new FCBlockBoneSlab(parseID("fcBlockBoneSlabID"));
	    
	    fcBlockPumpkinFresh = (new FCBlockPumpkin(parseID("fcBlockPumpkinFreshID"), false));
	    ((FCBlockStem)Block.pumpkinStem).SetFruitBlock(fcBlockPumpkinFresh); // do this after pumpkin is instantiated
	    
        fcBlockWoodBloodSidingAndCorner = new FCBlockWoodSidingAndCornerAndDecorative(parseID("fcBlockWoodBloodSidingAndCornerID"), 
        	"fcBlockDecorativeWoodBlood", "fcWoodBloodSiding");
        
	    fcBlockWoodBloodMouldingAndDecorative = new FCBlockWoodMouldingAndDecorative(parseID("fcBlockWoodBloodMouldingAndDecorativeID"), 
	    	"fcBlockDecorativeWoodBlood", "fcBlockColumnWoodBlood_side", 
	    	fcBlockWoodBloodSidingAndCorner.blockID, "fcWoodBloodMoulding");
	    
	    fcBlockWoodBloodStairs = (new FCBlockStairsWood(parseID("fcBlockWoodBloodStairsID"), 
	    	Block.planks, 4)).setUnlocalizedName("fcBlockWoodBloodStairs");
	    
	    fcBlockLogDamaged = new FCBlockLogDamaged(parseID("fcBlockLogDamagedID"));
	    
	    fcBlockDirtLoose = new FCBlockDirtLoose(parseID("fcBlockDirtLooseID"));
	    fcBlockDirtLooseSlab = new FCBlockDirtLooseSlab(parseID("fcBlockDirtLooseSlabID"));
	    
	    fcBlockCampfireUnlit = (FCBlockCampfire)(new FCBlockCampfire(parseID("fcBlockCampfireUnlitID"), 0)).setCreativeTab(CreativeTabs.tabDecorations);
	    fcBlockCampfireSmall = (FCBlockCampfire)(new FCBlockCampfire(parseID("fcBlockCampfireSmallID"), 1).setLightValue(0.25F));
	    fcBlockCampfireMedium = (FCBlockCampfire)(new FCBlockCampfire(parseID("fcBlockCampfireMediumID"), 2).setLightValue(0.5F));
	    fcBlockCampfireLarge = (FCBlockCampfire)(new FCBlockCampfire(parseID("fcBlockCampfireLargeID"), 3).setLightValue(0.875F));
	    
	    fcBlockUnfiredBrick = new FCBlockUnfiredBrick(parseID("fcBlockUnfiredBrickID"));
	    fcBlockCookedBrick = new FCBlockCookedBrick(parseID("fcBlockCookedBrickID"));
	    
	    fcBlockBrickLoose = new FCBlockBrickLoose(parseID("fcBlockBrickLooseID"));
	    fcBlockBrickLooseSlab = new FCBlockBrickLooseSlab(parseID("fcBlockBrickLooseSlabID"));
	    
	    fcBlockCobblestoneLoose = new FCBlockCobblestoneLoose(parseID("fcBlockCobblestoneLooseID"));
	    fcBlockCobblestoneLooseSlab = new FCBlockCobblestoneLooseSlab(parseID("fcBlockCobblestoneLooseSlabID"));
	    
	    fcBlockFurnaceBrickIdle = new FCBlockFurnaceBrickIdle(parseID("fcBlockFurnaceBrickIdleID"));
	    fcBlockFurnaceBrickBurning = new FCBlockFurnaceBrickBurning(parseID("fcBlockFurnaceBrickBurningID"));
	    
	    fcBlockTorchFiniteUnlit = new FCBlockTorchFiniteUnlit(parseID("fcBlockTorchFiniteIdleID"));
	    fcBlockTorchFiniteBurning = new FCBlockTorchFiniteBurning(parseID("fcBlockTorchFiniteBurningID"));
	    
	    fcBlockStoneRough = (new FCBlockStoneRough(parseID("fcBlockStoneRoughID"), 0));
	    fcBlockStoneRoughMidStrata = (new FCBlockStoneRough(parseID("fcBlockStoneRoughMidStrataID"), 1));
	    fcBlockStoneRoughDeepStrata = (new FCBlockStoneRough(parseID("fcBlockStoneRoughDeepStrataID"), 2));
	    
	    fcBlockWorkStump = new FCBlockWorkStump(parseID("fcBlockWorkStumpID"));
	    
	    fcBlockBasketWicker = new FCBlockBasketWicker(parseID("fcBlockBasketWickerID"));
	    
	    fcBlockLogSpike = new FCBlockLogSpike(parseID("fcBlockLogSpikeID"));
	    
	    fcBlockTorchNetherUnlit = new FCBlockTorchNetherUnlit(parseID("fcBlockTorchIdleID"));

	    // flammable versions of vanilla blocks.  Keep flammability attributes out of class so as not to affect original blocks.
	    fcBlockWorkbench = (new FCBlockWorkbench(parseID("fcBlockWorkbenchID"))).SetFireProperties(5, 20);
	    fcBlockChest = (new FCBlockChest(parseID("fcBlockChestID"))).SetFireProperties(5, 20);
	    fcBlockDoorWood  = (new FCBlockDoorWood(parseID("fcBlockDoorWoodID"))).SetFireProperties(5, 20);
	    fcBlockWeb = (new FCBlockWeb(parseID("fcBlockWebID"))).SetFireProperties(60, 100).setCreativeTab(CreativeTabs.tabDecorations);
	    
	    fcBlockUnfiredClay = new FCBlockUnfiredClay(parseID("fcBlockUnfiredClayID"));
	    fcBlockMyceliumSlab = new FCBlockMyceliumSlab(parseID("fcBlockMyceliumSlabID"));
	    fcBlockToolPlaced = new FCBlockToolPlaced(parseID("fcBlockToolPlacedID"));
	    
	    fcBlockBrickLooseStairs = new FCBlockBrickLooseStairs(parseID("fcBlockBrickLooseStairsID"));
	    fcBlockCobblestoneLooseStairs = new FCBlockCobblestoneLooseStairs(parseID("fcBlockCobblestoneLooseStairsID"));
	    
	    fcBlockLogSmouldering = new FCBlockLogSmouldering(parseID("fcBlockLogSmoulderingID"));
	    fcBlockWoodCinders = new FCBlockWoodCinders(parseID("fcBlockWoodCindersID"));
	    fcBlockStumpCharred = new FCBlockStumpCharred(parseID("fcBlockStumpCharredID"));
	    fcBlockAshGroundCover = new FCBlockAshGroundCover(parseID("fcBlockAshGroundCoverID"));
	    
	    fcBlockSnowLoose = new FCBlockSnowLoose(parseID("fcBlockSnowLooseID"));
	    fcBlockSnowLooseSlab = new FCBlockSnowLooseSlab(parseID("fcBlockSnowLooseSlabID"));	    
	    fcBlockSnowSolid = new FCBlockSnowSolid(parseID("fcBlockSnowSolidID"));
	    fcBlockSnowSolidSlab = new FCBlockSnowSolidSlab(parseID("fcBlockSnowSolidSlabID"));
	    
	    fcBlockLadder = new FCBlockLadder(parseID("fcBlockLadderID"));
	    fcBlockLadderOnFire = new FCBlockLadderOnFire(parseID("fcBlockLadderOnFireID"));
	    
	    fcBlockShovel = new FCBlockShovel(parseID("fcBlockShovelID"));
	    fcBlockHamper = new FCBlockHamper(parseID("fcBlockHamperID"));
	    
	    fcBlockCreeperOysters = new FCBlockCreeperOysters(parseID("fcBlockCreeperOystersID"));
	    fcBlockCreeperOystersSlab = new FCBlockCreeperOystersSlab(parseID("fcBlockCreeperOystersSlabID"));
	    
	    fcBlockTorchNetherBurning = (FCBlockTorchNetherBurning)(new FCBlockTorchNetherBurning(parseID("fcBlockTorchNetherBurningID")))
	    		.setCreativeTab(CreativeTabs.tabDecorations);
	    
	    fcBlockBucketEmpty = new FCBlockBucket(parseID("fcBlockBucketEmptyID"));	    
	    fcBlockBucketWater = new FCBlockBucketWater(parseID("fcBlockBucketWaterID"));
	    fcBlockBucketCement = new FCBlockBucketCement(parseID("fcBlockBucketCementID"));
	    fcBlockBucketMilk = new FCBlockBucketMilk(parseID("fcBlockBucketMilkID"));
	    fcBlockBucketMilkChocolate = new FCBlockBucketMilkChocolate(parseID("fcBlockBucketMilkChocolateID"));
	    
	    fcBlockMilk = new FCBlockMilk(parseID("fcBlockMilkID"));
	    fcBlockMilkChocolate = new FCBlockMilkChocolate(parseID("fcBlockMilkChocolateID"));
	    
	    fcBlockGearBox = new FCBlockGearBox(parseID("fcBlockGearBoxID"));
	    
	    fcBlockSpikeIron = new FCBlockSpike(parseID("fcBlockSpikeIronID"));
	    fcBlockLightningRod = new FCBlockLightningRod(parseID("fcBlockLightningRodID"));
	    
	    fcBlockChunkOreIron = new FCBlockChunkOreIron(parseID("fcBlockChunkOreIronID"));
	    fcBlockChunkOreGold = new FCBlockChunkOreGold(parseID("fcBlockChunkOreGoldID"));
	    
	    fcBlockStoneBrickLoose = new FCBlockStoneBrickLoose(parseID("fcBlockStoneBrickLooseID"));
	    fcBlockStoneBrickLooseSlab = new FCBlockStoneBrickLooseSlab(parseID("fcBlockStoneBrickLooseSlabID"));
	    fcBlockStoneBrickLooseStairs = new FCBlockStoneBrickLooseStairs(parseID("fcBlockStoneBrickLooseStairsID"));
	    
	    fcBlockNetherBrickLoose = new FCBlockNetherBrickLoose(parseID("fcBlockNetherBrickLooseID"));
	    fcBlockNetherBrickLooseSlab = new FCBlockNetherBrickLooseSlab(parseID("fcBlockNetherBrickLooseSlabID"));
	    fcBlockNetherBrickLooseStairs = new FCBlockNetherBrickLooseStairs(parseID("fcBlockNetherBrickLooseStairsID"));
	    
	    fcBlockNetherrackFalling = new FCBlockNetherrackFalling(parseID("fcBlockNetherrackFallingID"));
	    
	    fcBlockLavaPillow = new FCBlockLavaPillow(parseID("fcBlockLavaPillowID"));
	    
	    fcBlockMushroomCapBrown = new FCBlockMushroomCap(parseID("fcBlockMushroomCapBrownID"), 0);
	    fcBlockMushroomCapRed = new FCBlockMushroomCap(parseID("fcBlockMushroomCapRedID"), 1);
	    
	    fcBlockChunkOreStorageIron = new FCBlockChunkOreStorageIron(parseID("fcBlockChunkOreStorageIronID"));
	    fcBlockChunkOreStorageGold = new FCBlockChunkOreStorageGold(parseID("fcBlockChunkOreStorageGoldID"));
	    
	    fcBlockWicker = new FCBlockWicker(parseID("fcBlockWickerID"));
	    fcBlockWickerSlab = new FCBlockWickerSlab(parseID("fcBlockWickerSlabID"));	    
	    fcBlockWickerPane = new FCBlockWickerPane(parseID("fcBlockWickerPaneID"));
	    
	    fcBlockGrate = new FCBlockGrate(parseID("fcBlockGrateID"));
	    fcBlockSlats = new FCBlockSlats(parseID("fcBlockSlatsID"));
	    
	    fcBlockFarmland = new FCBlockFarmland(parseID("fcBlockFarmlandNewID"));
	    fcBlockFarmlandFertilized = new FCBlockFarmlandFertilized(parseID("fcBlockFarmlandFertilizedNewID"));
	    
	    fcBlockWheatCrop = new FCBlockWheatCrop(parseID("fcBlockWheatCropID"));
	    fcBlockWheatCropTop = new FCBlockWheatCropTop(parseID("fcBlockWheatCropTopID"));
	    fcBlockWeeds = new FCBlockWeeds(parseID("fcBlockWeedsID"));
	    fcBlockPlanterSoil = new FCBlockPlanterSoil(parseID("fcBlockPlanterSoilID"));	    
    }
	    
	private void InstantiateModItems()
	{
    	fcItemBucketCement = new FCItemBucketCement(parseID("fcBucketCementID"));
    	
    	fcItemWolfRaw = new FCItemWolfChopRaw(parseID("fcWolfRawID"));
    	fcItemWolfCooked = new FCItemWolfChopCooked(parseID("fcWolfCookedID"));
    	
    	fcItemNethercoal = (new Item(parseID("fcNethercoalID"))).
    		SetFurnaceBurnTime(2 * FCEnumFurnaceBurnTime.COAL.m_iBurnTime).
    		SetFilterableProperties(Item.m_iFilterable_Small).
    		setUnlocalizedName("fcItemNethercoal").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemHempSeeds = (new FCItemSeeds(parseID("fcHempSeedsID"), 
    		fcBlockHempCrop.blockID)).SetAsBasicChickenFood().setUnlocalizedName(
			"fcItemSeedsHemp");
    	
    	fcItemHemp = (new Item(parseID("fcHempID"))).SetBuoyant().
    		SetBellowsBlowDistance(2).SetIncineratedInCrucible().
    		SetFilterableProperties(Item.m_iFilterable_Thin).
    		setUnlocalizedName("fcItemHemp").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemGear = (new Item(parseID("fcGearID"))).SetBuoyant().
    		SetIncineratedInCrucible().setUnlocalizedName("fcItemGear").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
		fcItemFlour = (new Item(parseID("fcFlourID"))).SetBuoyant().
			SetBellowsBlowDistance(3).SetIncineratedInCrucible().
			SetFilterableProperties(Item.m_iFilterable_Fine).
			setUnlocalizedName("fcItemFlour").setCreativeTab(CreativeTabs.tabMaterials);    	
        
        fcItemHempFibers = (new Item(parseID("fcHempFibersID"))).SetBuoyant().
        	SetBellowsBlowDistance(2).SetIncineratedInCrucible().
        	SetFilterableProperties(Item.m_iFilterable_Small | Item.m_iFilterable_Thin).
        	setUnlocalizedName("fcItemFibersHemp").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemScouredLeather = (new Item(parseID("fcScouredLeatherID"))).SetBuoyant().
        	SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Thin).
        	setUnlocalizedName("fcItemLeatherScoured").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemDonut = (new FCItemFood(parseID("fcDonutID"), 
        	FCItemFood.m_iDonutHungerHealed, FCItemFood.m_fDonutSaturationModifier, 
        	false, FCItemFood.m_sDonutItemName)).setAlwaysEdible().
        	SetFilterableProperties(Item.m_iFilterable_Small);
        
        fcItemRope = (new FCItemRope(parseID("fcRopeItemID"))).
        	SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Narrow).
        	setCreativeTab(CreativeTabs.tabTransport);
        
    	fcItemSlatsOld = new FCItemSlatsLegacy(parseID("fcRollersItemID"));        
        
        fcItemDung = new FCItemDung(parseID("fcDungID"));
        
        fcItemWaterWheel = (new FCItemWaterWheel(parseID("fcWaterWheelItemID"))).SetIncineratedInCrucible();
        
        fcItemWindMillBlade = (new Item(parseID("fcWindMillBladeItemID"))).
        	SetBuoyant().SetIncineratedInCrucible().setMaxStackSize(1).setFull3D().
        	setUnlocalizedName("fcItemBladeWindMill").setCreativeTab(CreativeTabs.tabMaterials);        
        
        fcItemWindMill = (new FCItemWindMill(parseID("fcWindMillItemID"))).SetIncineratedInCrucible();
        
        fcItemHempCloth = (new Item(parseID("fcHempClothID"))).SetBuoyant().
	    	SetBellowsBlowDistance(1).SetIncineratedInCrucible().
	    	SetFilterableProperties(Item.m_iFilterable_Thin).setUnlocalizedName("fcItempFabric").
	    	setCreativeTab(CreativeTabs.tabMaterials);
        
    	fcItemGrateOld = new FCItemGrateLegacy(parseID("fcGrateID"));
    	
    	fcItemWickerPaneOld = new FCItemWickerPaneLegacy(parseID("fcWickerID"));        
    	
    	fcItemTannedLeather = (new Item(parseID("fcTannedLeatherID"))).SetBuoyant().
    		SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Thin).
    		setUnlocalizedName("fcItemLeatherTanned").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemStrap = (new Item(parseID("fcStrapID"))).SetBuoyant().
    		SetBellowsBlowDistance(1).SetIncineratedInCrucible().
    		SetFilterableProperties(Item.m_iFilterable_Narrow | Item.m_iFilterable_Thin).
    		setUnlocalizedName("fcItemStrap").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemBelt = (new Item(parseID("fcBeltID"))).SetBuoyant().
    		SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Narrow).
    		setUnlocalizedName("fcItemBelt").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemFoulFood = new FCItemFoulFood(parseID("fcFoulFoodID"));
    	
    	fcItemWoodBlade = (new Item(parseID("fcWoodBladeID"))).SetBuoyant().
    		SetIncineratedInCrucible().setMaxStackSize(1).setUnlocalizedName("fcItemBladeWood").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemGlue = (new Item(parseID("fcGlueID"))).SetNeutralBuoyant().
    		SetIncineratedInCrucible().setUnlocalizedName("fcItemGlue").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemTallow = (new Item(parseID("fcTallowID"))).SetBuoyant().
    		SetIncineratedInCrucible().setUnlocalizedName("fcItemTallow").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemHaft = (new Item(parseID("fcHaftID"))).SetBuoyant().
    		SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Narrow).
    		setUnlocalizedName("fcItemHaft").setCreativeTab(CreativeTabs.tabMaterials);
    	
        fcItemSteel = (new Item(parseID("fcSteelID"))).
        	setUnlocalizedName("fcItemIngotSteel").setCreativeTab(CreativeTabs.tabMaterials);
        
    	fcItemRefinedPickAxe = new FCItemRefinedPickAxe(parseID("fcRefinedPickAxeID"));
    	fcItemRefinedShovel = new FCItemRefinedShovel(parseID("fcRefinedShovelID"));
    	fcItemRefinedHoe = new FCItemRefinedHoe(parseID("fcRefinedHoeID"));
    	fcItemBattleAxe = new FCItemBattleAxe(parseID("fcBattleAxeID"));
    	fcItemRefinedSword = new FCItemRefinedSword(parseID("fcRefinedSwordID"));
    	
    	fcItemGroundNetherrack = (new Item(parseID("fcGroundNetherrackID"))).
    		SetBellowsBlowDistance(2).setUnlocalizedName("fcItemNetherrackGround").
    		SetFilterableProperties(Item.m_iFilterable_Fine).
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemHellfireDust = (new Item(parseID("fcHellfireDustID"))).
    		SetBellowsBlowDistance(3).setUnlocalizedName("fcItemDustHellfire").
			SetFilterableProperties(Item.m_iFilterable_Fine).
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemConcentratedHellfire = (new Item(parseID("fcConcentratedHellfireID"))).
    		setUnlocalizedName("fcItemConcentratedHellfire").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemArmorPlate = (new Item(parseID("fcArmorPlateID"))).
    		setUnlocalizedName("fcItemArmorPlate").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemPlateHelm = (new FCItemArmorRefined(parseID("fcPlateHelmID"), 0, 10)).setUnlocalizedName("fcItemHelmetPlate");
    	fcItemPlateBreastPlate = (new FCItemArmorRefined(parseID("fcPlateBreastPlateID"), 1, 14)).setUnlocalizedName("fcItemChestplatePlate");
    	fcItemPlateLeggings = (new FCItemArmorRefined(parseID("fcPlateLeggingsID"), 2, 12)).setUnlocalizedName("fcItemLeggingsPlate");
    	fcItemPlateBoots = (new FCItemArmorRefined(parseID("fcPlateBootsID"), 3, 8)).setUnlocalizedName("fcItemBootsPlate");
    	
    	fcItemCompositeBow = new FCItemCompositeBow(parseID("fcCompositeBowID"));

    	fcItemBroadheadArrowhead = (new Item(parseID("fcBroadheadArrowheadID"))).
    		SetFilterableProperties(Item.m_iFilterable_Small).
    		setUnlocalizedName("fcItemArrowheadBroadhead").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemBroadheadArrow = new FCItemArrowBroadhead(parseID("fcBroadheadArrowID"));
    	
    	fcItemCoalDust = (new Item(parseID("fcCoalDustID"))).SetBellowsBlowDistance(3).
    		SetFurnaceBurnTime(FCEnumFurnaceBurnTime.COAL.m_iBurnTime / 2).
    		SetFilterableProperties(Item.m_iFilterable_Fine).
    		setUnlocalizedName("fcItemDustCoal").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemPadding = (new Item(parseID("fcPaddingID"))).SetBuoyant().
    		SetIncineratedInCrucible().setUnlocalizedName("fcItemPadding").
    		setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemFilament = (new Item(parseID("fcFilamentID"))).SetBellowsBlowDistance(1).
    		SetFilterableProperties(Item.m_iFilterable_Small).
    		setUnlocalizedName("fcItemFilament").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemPolishedLapis = (new Item(parseID("fcPolishedLapisID"))).
			SetFilterableProperties(Item.m_iFilterable_Small).
    		setUnlocalizedName("fcItemRedstoneEye").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemUrn = (new FCItemPlacesAsBlock(parseID("fcUrnID"), 
    		fcAestheticNonOpaque.blockID, FCBlockAestheticNonOpaque.m_iSubtypeUrn, 
    		"fcItemUrn")).SetBuoyant().setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemSoulUrn = new FCItemSoulUrn(parseID("fcSoulUrnID"));
    	
    	fcItemHardBoiledEgg = new FCItemHardBoiledEgg(parseID("fcHardBoiledEggID"));
    	
		fcItemPotash = (new Item(parseID("fcPotashID"))).SetBellowsBlowDistance(3).
			SetFilterableProperties(Item.m_iFilterable_Fine).
			setUnlocalizedName("fcItemPotash").setCreativeTab(CreativeTabs.tabMaterials);
		
		fcItemSoap = (new FCItemSoap(parseID("fcSoapID"))).SetIncineratedInCrucible().
			SetFilterableProperties(Item.m_iFilterable_Small).
			setCreativeTab(CreativeTabs.tabMaterials);
		
		fcItemSawDust = (new Item(parseID("fcSawDustID"))).SetBuoyant().
			SetBellowsBlowDistance(3).SetFurnaceBurnTime(FCEnumFurnaceBurnTime.KINDLING).
			SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Fine).
			setUnlocalizedName("fcItemDustSaw").setCreativeTab(CreativeTabs.tabMaterials);
		
		fcItemArmorGimpHelm = (new FCItemArmorGimp(parseID("fcTannedLeatherHelmID"), 0)).setUnlocalizedName("fcItemGimpHelm");
		fcItemArmorGimpChest = (new FCItemArmorGimp(parseID("fcTannedLeatherChestID"), 1)).setUnlocalizedName("fcItemGimpChest");
		fcItemArmorGimpLeggings = (new FCItemArmorGimp(parseID("fcTannedLeatherLeggingsID"), 2)).setUnlocalizedName("fcItemGimpLeggings");
		fcItemArmorGimpBoots = (new FCItemArmorGimp(parseID("fcTannedLeatherBootsID"), 3)).setUnlocalizedName("fcItemGimpBoots");
		
		fcItemDynamite = new FCItemDynamite(parseID("fcDynamiteID"));
		
		fcItemBreedingHarness = new FCItemBreedingHarness(parseID("fcBreedingHarnessID"));
		
		fcItemSoulDust = (new Item(parseID("fcSoulDustID"))).SetBuoyant().
			SetBellowsBlowDistance(3).SetFurnaceBurnTime(FCEnumFurnaceBurnTime.KINDLING).
			SetIncineratedInCrucible().SetFilterableProperties(Item.m_iFilterable_Fine).
			setUnlocalizedName("fcItemDustSoul").setCreativeTab(CreativeTabs.tabMaterials);
		
		fcItemMattock = new FCItemMattock(parseID("fcMattockID"));
		
    	fcItemRefinedAxe = new FCItemRefinedAxe(parseID("fcRefinedAxeID"));
    	
    	fcItemNetherSludge = new FCItemNetherSludge(parseID("fcNetherSludgeID"));     	
    	fcItemNetherBrick = new FCItemNetherBrick(parseID("fcNetherBrickID"));
    	
    	fcItemTuningFork = new FCItemTuningFork(parseID("fcItemTuningForkID"));
    	
    	fcItemArcaneScroll = new FCItemArcaneScroll(parseID("fcItemArcaneScrollID"));
    	
    	fcItemCandle = new FCItemCandle(parseID("fcItemCandleID"));
    	
    	fcItemBloodMossSpores = new FCItemBloodMossSpores(parseID("fcItemNetherGrowthSporesID"));
    	
    	fcItemMould = (new FCItemMould(parseID("fcItemMouldID"))).setUnlocalizedName("fcItemMould");
    	
    	fcItemCanvas = new FCItemCanvas(parseID("fcItemCanvasID"));
    	
    	fcItemDogFood = (new FCItemFood(parseID("fcItemDogFoodID"), 
    		FCItemFood.m_iDogFoodHungerHealed, FCItemFood.m_fDogFoodSaturationModifier, true, 
    		FCItemFood.m_sDogFoodItemName)).SetStandardFoodPoisoningEffect().
    		SetFilterableProperties(Item.m_iFilterable_Small);
	
    	fcItemRawEgg = (new FCItemFood(parseID("fcItemRawEggID"), 
    		FCItemFood.m_iRawEggHungerHealed, FCItemFood.m_fRawEggSaturationModifier, false, 
    		FCItemFood.m_sRawEggItemName)).SetStandardFoodPoisoningEffect().
    		SetFilterableProperties(Item.m_iFilterable_Small);
    	
    	fcItemFriedEgg = new FCItemFood(parseID("fcItemFriedEggID"), 
    		FCItemFood.m_iFriedEggHungerHealed, FCItemFood.m_fFriedEggSaturationModifier, false, 
    		FCItemFood.m_sFriedEggItemName).SetFilterableProperties(Item.m_iFilterable_Small);
    	
    	fcItemScrew = (new Item(parseID("fcItemScrewID"))).
    		setUnlocalizedName("fcItemScrew").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemRottenArrow = new FCItemArrowRotten(parseID("fcItemRottenArrowID"));
    	
    	fcItemOcularOfEnder = (new Item(parseID("fcItemOcularOfEnderID"))).
    		setUnlocalizedName("fcItemOcularOfEnder").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	fcItemEnderSpectacles = (new FCItemArmorSpecial(parseID("fcItemEnderSpectaclesID"), 0)).setUnlocalizedName("fcItemEnderSpectacles");
    	
    	fcItemStake = (new ItemReed(parseID("fcItemStakeID"), fcBlockStake)).
    		SetBuoyant().SetFilterableProperties(Item.m_iFilterable_SolidBlock).
    		setUnlocalizedName("fcItemStake").setCreativeTab(CreativeTabs.tabTools);
    	
        fcItemBrimstone = (new Item(parseID("fcItemBrimstoneID"))).
        	SetBellowsBlowDistance(3).SetFilterableProperties(Item.m_iFilterable_Fine).
        	setUnlocalizedName("fcItemBrimstone").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemNitre = (new Item(parseID("fcItemNitreID"))).
        	SetBellowsBlowDistance(3).SetFilterableProperties(Item.m_iFilterable_Fine).
        	setUnlocalizedName("fcItemNitre").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemElement = (new Item(parseID("fcItemElementID"))).
        	SetBellowsBlowDistance(1).setUnlocalizedName("fcItemElement").
    		SetFilterableProperties(Item.m_iFilterable_Small).
        	setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemFuse = (new Item(parseID("fcItemFuseID"))).
        	SetBuoyant().SetBellowsBlowDistance(2).
        	SetFilterableProperties(Item.m_iFilterable_Small | Item.m_iFilterable_Thin).
        	setUnlocalizedName("fcItemFuse").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemBlastingOil = (new Item(parseID("fcItemBlastingOilID"))).
        	setUnlocalizedName("fcItemBlastingOil").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemWindMillVertical = new FCItemWindMillVertical(parseID("fcItemWindMillVerticalID"));
        
        fcItemBoiledPotato = (new FCItemFood(parseID("fcItemBoiledPotatoID"), 
        	FCItemFood.m_iBoiledPotatoHungerHealed, FCItemFood.m_fBoiledPotatoSaturationModifier, 
        	false, FCItemFood.m_sBoiledPotatoItemName)).SetAsBasicPigFood().
    		SetFilterableProperties(Item.m_iFilterable_Small);
        
        fcItemMuttonRaw = (new FCItemFood(parseID("fcItemMuttonRawID"), FCItemFood.m_iMuttonRawHungerHealed, FCItemFood.m_fMuttonSaturationModifier, true, 
        	"fcItemMuttonRaw", true)).SetStandardFoodPoisoningEffect();
        
        fcItemMuttonCooked = new FCItemFood(parseID("fcItemMuttonCookedID"), FCItemFood.m_iMuttonCookedHungerHealed, FCItemFood.m_fMuttonSaturationModifier, true, 
        	"fcItemMuttonCooked");
        
        fcItemWitchWart = (new Item(parseID("fcItemWitchWartID"))).SetBuoyant().
        	SetBellowsBlowDistance(1).SetFilterableProperties(Item.m_iFilterable_Small).
        	setPotionEffect(PotionHelper.redstoneEffect).setUnlocalizedName("fcItemWitchWart");
        
        fcItemCookedCarrot = (new FCItemFood(parseID("fcItemCookedCarrotID"), 
        	FCItemFood.m_iCookedCarrotHungerHealed, FCItemFood.m_fCookedCarrotSaturationModifier, 
        	false, FCItemFood.m_sCookedCarrotItemName)).
        	SetAsBasicPigFood();
        
        fcItemTastySandwich = new FCItemFood(parseID("fcItemTastySandwichID"), FCItemFood.m_iTastySandwichHungerHealed, FCItemFood.m_fTastySandwichSaturationModifier, true, FCItemFood.m_sTastySandwichItemName);
        
        fcItemSteakAndPotatoes = new FCItemFood(parseID("fcItemSteakAndPotatoesID"), FCItemFood.m_iSteakAndPotatoesHungerHealed, FCItemFood.m_fSteakAndPotatoesSaturationModifier, true, FCItemFood.m_sSteakAndPotatoesItemName);
        
        fcItemHamAndEggs = new FCItemFood(parseID("fcItemHamAndEggsID"), FCItemFood.m_iHamAndEggsHungerHealed, FCItemFood.m_fHamAndEggsSaturationModifier, true, FCItemFood.m_sHamAndEggsItemName);
        
        fcItemSteakDinner = new FCItemFood(parseID("fcItemSteakDinnerID"), FCItemFood.m_iSteakDinnerHungerHealed, FCItemFood.m_fSteakDinnerSaturationModifier, true, FCItemFood.m_sSteakDinnerItemName);
        
        fcItemPorkDinner = new FCItemFood(parseID("fcItemPorkDinnerID"), FCItemFood.m_iPorkDinnerHungerHealed, FCItemFood.m_fPorkDinnerSaturationModifier, true, FCItemFood.m_sPorkDinnerItemName);
        
        fcItemWolfDinner = new FCItemFood(parseID("fcItemWolfDinnerID"), FCItemFood.m_iWolfDinnerHungerHealed, FCItemFood.m_fWolfDinnerSaturationModifier, true, FCItemFood.m_sWolfDinnerItemName);
        
        fcItemRawKebab = (new FCItemFood(parseID("fcItemRawKebabID"), FCItemFood.m_iRawKebabHungerHealed, FCItemFood.m_fRawKebabSaturationModifier, true, 
			FCItemFood.m_sRawKebabItemName)).SetStandardFoodPoisoningEffect();
        
        fcItemCookedKebab = new FCItemFood(parseID("fcItemCookedKebabID"), FCItemFood.m_iCookedKebabHungerHealed, FCItemFood.m_fCookedKebabSaturationModifier, true, FCItemFood.m_sCookedKebabItemName);
        
        fcItemChickenSoup = new FCItemSoup(parseID("fcItemChickenSoupID"), FCItemFood.m_iChickenSoupHungerHealed, FCItemFood.m_fChickenSoupSaturationModifier, true, FCItemFood.m_sChickenSoupItemName);
        
        fcItemFishSoup = new FCItemSoup(parseID("fcItemFishSoupID"), FCItemFood.m_iFishSoupHungerHealed, FCItemFood.m_fFishSoupSaturationModifier, false, FCItemFood.m_sFishSoupItemName);
        
        fcItemHeartyStew = new FCItemSoup(parseID("fcItemHeartyStewID"), FCItemFood.m_iHeartyStewHungerHealed, FCItemFood.m_fHeartyStewSaturationModifier, true, FCItemFood.m_sHeartyStewItemName);
        
        fcItemMushroomRed = (new FCItemMushroom(parseID("fcItemMushroomRedID"), 
        	FCItemMushroom.m_iRedMushroomHungerHealed, 
        	FCItemMushroom.m_fRedMushroomSaturationModifier, FCItemMushroom.m_sRedMushroomItemName, 
        	Block.mushroomRed.blockID)).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).
        	SetFilterableProperties(Item.m_iFilterable_Small).
        	setPotionEffect(PotionHelper.spiderEyeEffect); 
        
        fcItemMushroomBrown = new FCItemMushroom(parseID("fcItemMushroomBrownID"), 
        	FCItemMushroom.m_iBrownMushroomHungerHealed, 
        	FCItemMushroom.m_fBrownMushroomSaturationModifier, 
        	FCItemMushroom.m_sBrownMushroomItemName, Block.mushroomBrown.blockID).
        	SetFilterableProperties(Item.m_iFilterable_Small);
        
        fcItemNuggetIron = (new Item(parseID("fcItemNuggetIronID"))).
			SetFilterableProperties(Item.m_iFilterable_Small).
        	setUnlocalizedName("fcItemNuggetIron").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemMail = (new Item(parseID("fcItemMailID"))).setUnlocalizedName("fcItemMail");
        
        fcItemRawMysteryMeat = new FCItemMysteryMeatRaw(parseID("fcItemRawMysteryMeatID"));        
        fcItemCookedMysteryMeat = new FCItemMysteryMeatCooked(parseID("fcItemCookedMysteryMeatID"));
        
        fcItemRawMushroomOmelet = (new FCItemFood(parseID("fcItemRawMushroomOmeletID"), FCItemFood.m_iRawMushroomOmeletHungerHealed, FCItemFood.m_fRawMushroomOmeletSaturationModifier, false, 
			FCItemFood.m_sRawMushroomOmeletItemName)).SetStandardFoodPoisoningEffect();
        
        fcItemCookedMushroomOmelet = (new FCItemFood(parseID("fcItemCookedMushroomOmeletID"), FCItemFood.m_iCookedMushroomOmeletHungerHealed, FCItemFood.m_fCookedMushroomOmeletSaturationModifier, false, 
			FCItemFood.m_sCookedMushroomOmeletItemName));
        
        fcItemRawScrambledEggs = (new FCItemFood(parseID("fcItemRawScrambledEggsID"), FCItemFood.m_iRawScrambledEggsHungerHealed, FCItemFood.m_fRawScrambledEggsSaturationModifier, false, 
			FCItemFood.m_sRawScrambledEggsItemName)).SetStandardFoodPoisoningEffect();
        
        fcItemCookedScrambledEggs = (new FCItemFood(parseID("fcItemCookedScrambledEggsID"), FCItemFood.m_iCookedScrambledEggsHungerHealed, FCItemFood.m_fCookedScrambledEggsSaturationModifier, false, 
			FCItemFood.m_sCookedScrambledEggsItemName));
        
        fcItemCreeperOysters = new FCItemCreeperOysters(parseID("fcItemCreeperOystersID"));
        
        fcItemArmorWoolHelm = (new FCItemArmorWool(parseID("fcItemArmorWoolHelmID"), 0)).setUnlocalizedName("fcItemWoolHelm");
        fcItemArmorWoolChest = (new FCItemArmorWool(parseID("fcItemArmorWoolChestID"), 1)).setUnlocalizedName("fcItemWoolChest");
        fcItemArmorWoolLeggings = (new FCItemArmorWool(parseID("fcItemArmorWoolLeggingsID"), 2)).setUnlocalizedName("fcItemWoolLeggings");
        fcItemArmorWoolBoots = (new FCItemArmorWool(parseID("fcItemArmorWoolBootsID"), 3)).setUnlocalizedName("fcItemWoolBoots");
        
        fcItemArmorPaddedHelm = (new FCItemArmorPadded(parseID("fcItemArmorPaddedHelmID"), 0)).setUnlocalizedName("fcItemPaddedHelm");
        fcItemArmorPaddedChest = (new FCItemArmorPadded(parseID("fcItemArmorPaddedChestID"), 1)).setUnlocalizedName("fcItemPaddedChest");
        fcItemArmorPaddedLeggings = (new FCItemArmorPadded(parseID("fcItemArmorPaddedLeggingsID"), 2)).setUnlocalizedName("fcItemPaddedLeggings");
        fcItemArmorPaddedBoots = (new FCItemArmorPadded(parseID("fcItemArmorPaddedBootsID"), 3)).setUnlocalizedName("fcItemPaddedBoots");
        
        fcItemArmorTannedHelm = (new FCItemArmorTanned(parseID("fcItemArmorTannedHelmID"), 0)).setUnlocalizedName("fcItemTannedHelm");
        fcItemArmorTannedChest = (new FCItemArmorTanned(parseID("fcItemArmorTannedChestID"), 1)).setUnlocalizedName("fcItemTannedChest");
        fcItemArmorTannedLeggings = (new FCItemArmorTanned(parseID("fcItemArmorTannedLeggingsID"), 2)).setUnlocalizedName("fcItemTannedLeggings");
        fcItemArmorTannedBoots = (new FCItemArmorTanned(parseID("fcItemArmorTannedBootsID"), 3)).setUnlocalizedName("fcItemTannedBoots");        
        
        fcItemIngotDiamond = (new Item(parseID("fcItemIngotDiamondID"))).
        	setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("fcItemIngotDiamond");
        
        fcItemLeatherCut = (new Item(parseID("fcItemLeatherCutID"))).
        	SetBuoyant().SetFilterableProperties(Item.m_iFilterable_Thin).
        	setUnlocalizedName("fcItemLeatherCut");
        
        fcItemTannedLeatherCut = (new Item(parseID("fcItemTannedLeatherCutID"))).
        	SetBuoyant().SetFilterableProperties(Item.m_iFilterable_Thin).
        	setUnlocalizedName("fcItemLeatherTannedCut");
        
        fcItemScouredLeatherCut = (new Item(parseID("fcItemScouredLeatherCutID"))).
        	SetBuoyant().SetFilterableProperties(Item.m_iFilterable_Thin).
        	setUnlocalizedName("fcItemLeatherScouredCut");
        
        fcItemFishingRodBaited = new FCItemFishingRodBaited(parseID("fcItemFishingRodBaitedID"));
        
        fcItemPileDirt = new FCItemPileDirt(parseID("fcItemPileDirtID"));
        fcItemPileSand = new FCItemPileSand(parseID("fcItemPileSandID"));
        fcItemPileGravel = new FCItemPileGravel(parseID("fcItemPileGravelID"));
        
        fcItemBatWing = (new FCItemFood(parseID("fcItemBatWingID"), 
        	FCItemFood.m_iBatWingHungerHealed, FCItemFood.m_fBatWingSaturationModifier, false, 
			FCItemFood.m_sBatWingItemName)).SetStandardFoodPoisoningEffect().
			SetBellowsBlowDistance(3).
			SetFilterableProperties(Item.m_iFilterable_Small | Item.m_iFilterable_Thin);
        
        fcItemGoldenDung = (new Item(parseID("fcItemGoldenDungID"))).setUnlocalizedName("fcItemDungGolden");        
        fcItemBark = new FCItemBark(parseID("fcItemBarkID"));
        
        fcItemPileSoulSand = new FCItemPileSoulSand(parseID("fcItemPileSoulSandID"));        
        
        fcItemRedstoneLatch = (new Item(parseID("fcItemRedstoneLatchID"))).setUnlocalizedName("fcItemRedstoneLatch");

		fcItemNuggetSteel = (new Item(parseID("fcItemNuggetSteelID"))).setUnlocalizedName("fcItemNuggetSteel");
		
		fcItemWool = new FCItemWool(parseID("fcItemWoolID"));
		
		fcItemCocoaBeans = new FCItemCocoaBeans(parseID("fcItemCocoaBeansID"));
		
		fcItemChocolate = (new FCItemFood(parseID("fcItemChocolateID"), 
			FCItemFood.m_iChocolateHungerHealed, FCItemFood.m_fChocolateSaturationModifier, true, 
			FCItemFood.m_sChocolateItemName)).setAlwaysEdible().
    		SetFilterableProperties(Item.m_iFilterable_Small);
		
		fcItemBucketMilkChocolate = new FCItemBucketMilkChocolate(parseID("fcItemBucketChocolateMilkID"));    	
		fcItemSoulFlux = new FCItemSoulFlux(parseID("fcItemSoulFluxID"));
		
		fcItemEnderSlag = (new Item(parseID("fcItemEnderSlagID"))).
			SetBellowsBlowDistance(1).SetFilterableProperties(Item.m_iFilterable_Fine).
			setUnlocalizedName("fcItemEnderSlag");

		fcItemPastryUncookedCake = 
			(new FCItemPlacesAsBlock(parseID("fcItemPastryUncookedCakeID"), 
			fcUnfiredPottery.blockID, FCBlockUnfiredPottery.m_iSubtypeUncookedCake, 
			"fcItemPastryUncookedCake")).SetBuoyant().setCreativeTab(CreativeTabs.tabFood);    	
		
		fcItemPastryUncookedCookies = 
			(new FCItemPlacesAsBlock(parseID("fcItemPastryUncookedCookiesID"), 
			fcUnfiredPottery.blockID, FCBlockUnfiredPottery.m_iSubtypeUncookedCookies, 
			"fcItemPastryUncookedCookies")).SetBuoyant().setCreativeTab(CreativeTabs.tabFood);
		
		fcItemPastryUncookedPumpkinPie = 
			(new FCItemPlacesAsBlock(parseID("fcItemPastryUncookedPumpkinPieID"), 
			fcUnfiredPottery.blockID, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie, 
			"fcItemPastryUncookedPumpkinPie")).SetBuoyant().setCreativeTab(CreativeTabs.tabFood);
	
		fcItemMysteriousGland = new FCItemMysteriousGland(parseID("fcItemMysteriousGlandID"));
		
		fcItemBeastLiverRaw = (new FCItemFood(parseID("fcItemBeastLiverRawID"), FCItemFood.m_iBeastLiverRawHungerHealed, FCItemFood.m_fBeastLiverSaturationModifier, true, 
			"fcItemBeastLiverRaw")).SetStandardFoodPoisoningEffect();
        
		fcItemBeastLiverCooked = new FCItemFood(parseID("fcItemBeastLiverCookedID"), FCItemFood.m_iBeastLiverCookedHungerHealed, 
			FCItemFood.m_fBeastLiverSaturationModifier, true, "fcItemBeastLiverCooked");
        
		fcItemAncientProphecy = new FCItemAncientProphecy(parseID("fcItemAncientProphecyID"));
		
		fcItemStumpRemover = new FCItemStumpRemover(parseID("fcItemStumpRemoverID"));
		
	    fcItemChiselWood = new FCItemChiselWood(parseID("fcItemChiselWoodID"));
	    
	    fcItemStone = new FCItemStone(parseID("fcItemStoneID"));
	    
	    fcItemChiselStone = new FCItemChiselStone(parseID("fcItemChiselStoneID"));
	    
	    fcItemClubWood = new FCItemClubWood(parseID("fcItemClubID"));
	    
	    fcItemFireStarterSticks = new FCItemFireStarterPrimitive(
	    	parseID("fcItemFireStarterSticksID"), 250, 0.05F, -0.1F, 0.1F, 0.001F).
	    	SetFilterableProperties(Item.m_iFilterable_Narrow).	    	
	    	setUnlocalizedName("fcItemFireStarterSticks");
	    
	    fcItemFireStarterBow = new FCItemFireStarterPrimitive(parseID("fcItemFireStarterBowID"), 
	    	250, 0.025F, -0.1F, 0.1F, 0.004F).setUnlocalizedName("fcItemFireStarterBow");
	    
	    fcItemChunkIronOre = new FCItemChunkOreIron(parseID("fcItemChunkIronOreID"));
	    
	    fcItemPileIronOre = (new Item(parseID("fcItemPileIronOreID"))).
	    	SetFilterableProperties(Item.m_iFilterable_Fine).
	    	setUnlocalizedName("fcItemPileIronOre").setCreativeTab(CreativeTabs.tabMaterials);
	    
	    fcItemChiselIron = new FCItemChiselIron(parseID("fcItemChiselIronID"));
	    
	    fcItemChunkGoldOre = new FCItemChunkOreGold(parseID("fcItemChunkGoldOreID")); 
	    
	    fcItemPileGoldOre = (new Item(parseID("fcItemPileGoldOreID"))).
    		SetFilterableProperties(Item.m_iFilterable_Fine).
	    	setUnlocalizedName("fcItemPileGoldOre").setCreativeTab(CreativeTabs.tabMaterials);

	    fcItemWickerPiece = new FCItemWickerPiece(parseID("fcItemWickerPieceID"));
	    
	    fcItemKnittingNeedles = new FCItemKnittingNeedles(parseID("fcItemKnittingNeedlesID"));
	    fcItemKnitting = new FCItemKnitting(parseID("fcItemKnittingID"));
	    fcItemWoolKnit = new FCItemWoolKnit(parseID("fcItemWoolKnitID"));
	    
	    fcItemClubBone = new FCItemClub(parseID("fcItemClubBoneID"), FCItemClub.m_iDurabilityBone, 
	    	FCItemClub.m_iWeaponDamageBone, "fcItemClubBone");
	    
        fcItemMeatCured = new FCItemFoodCured(parseID("fcItemMeatCuredID"), 
        	FCItemFood.m_iMeatCuredHungerHealed, FCItemFood.m_fMeatCuredSaturationModifier, "fcItemMeatCured");
        
        fcItemMetalFragment = (new Item(parseID("fcItemMetalFragmentID"))).
        	SetFilterableProperties(Item.m_iFilterable_Small).
        	setUnlocalizedName("fcItemMetalFragment").setCreativeTab(CreativeTabs.tabMisc);
        
        fcItemPileClay = (new Item(parseID("fcItemPileClayID"))).
    		SetFilterableProperties(Item.m_iFilterable_Small).
        	setUnlocalizedName("fcItemPileClay").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemMeatBurned = new FCItemFood(parseID("fcItemMeatBurnedID"), 
        	FCItemFood.m_iMeatBurnedHungerHealed, FCItemFood.m_fMeatBurnedSaturationModifier, true, "fcItemMeatBurned");
        
        fcItemChickenFeed = (new Item(parseID("fcItemChickenFeedID"))).
        	SetAsBasicChickenFood().SetBellowsBlowDistance(2).
    		SetFilterableProperties(Item.m_iFilterable_Fine).
        	setUnlocalizedName("fcItemChickenFeed").setCreativeTab(CreativeTabs.tabFood);
        
        fcItemFishHookBone = (new Item(parseID("fcItemFishHookBoneID"))).
        	SetBellowsBlowDistance(2).SetFilterableProperties(Item.m_iFilterable_Small).
        	setUnlocalizedName("fcItemFishHookBone").setCreativeTab(CreativeTabs.tabMisc);
        
        fcItemCarvingBone = new FCItemCarvingBone(parseID("fcItemCarvingBoneID"));
        
        fcItemStoneBrick = (new Item(parseID("fcItemBrickStoneID"))).
    		setUnlocalizedName("fcItemBrickStone").setCreativeTab(CreativeTabs.tabMaterials);
        
        fcItemWickerWeaving = new FCItemWickerWeaving(parseID("fcItemWickerWeavingID"));

        fcItemWheat = new FCItemWheat(parseID("fcItemWheatID"));
        fcItemWheatSeeds = new FCItemWheatSeeds(parseID("fcItemWheatSeedsID"));
        
        fcItemBreadDough = (new FCItemPlacesAsBlock(parseID("fcItemBreadDoughID"), 
			fcUnfiredPottery.blockID, FCBlockUnfiredPottery.m_iSubtypeUncookedBread, 
			"fcItemBreadDough")).SetBuoyant().SetIncineratedInCrucible().
			setCreativeTab(CreativeTabs.tabFood);
		
        fcItemStraw = new FCItemStraw(parseID("fcItemStrawID"));
        
        fcItemBrickUnfired = new FCItemBrickUnfired(parseID("fcItemBrickUnfiredID"));
        fcItemNetherBrickUnfired = new FCItemNetherBrickUnfired(parseID("fcItemNetherBrickUnfiredID"));
        
	    // ***REMINDER***: Use IDs over 20K here
		
        CreateAssociatedItemsForModBlocks();
	}
	
	private void CreateAssociatedItemsForModBlocks()
	{
		RegisterCustomBlockItems();
		
        for (int iTempBlockID = 0; iTempBlockID < 4096; iTempBlockID++)
        {
            if (Block.blocksList[iTempBlockID] != null && Item.itemsList[iTempBlockID] == null)
            {
                Item.itemsList[iTempBlockID] = new ItemBlock(iTempBlockID - 256);
            }
        }        
	}
	
	private void RegisterCustomBlockItems()
	{
		Item.m_bSuppressConflictWarnings = true;

        Item.itemsList[fcBlockSlabSandAndGravel.blockID] = (new FCItemBlockSlabSandAndGravel(fcBlockSlabSandAndGravel.blockID - 256));
        
        Item.itemsList[fcBlockAestheticOpaqueEarth.blockID] = (new FCItemBlockAestheticOpaqueEarth(fcBlockAestheticOpaqueEarth.blockID - 256));
        Item.itemsList[fcBlockMiningCharge.blockID] = (new FCItemBlockMiningCharge(fcBlockMiningCharge.blockID - 256));
        Item.itemsList[fcWoolSlab.blockID] = (new FCItemBlockWoolSlab(fcWoolSlab.blockID - 256));        
        Item.itemsList[fcCompanionCube.blockID] = (new FCItemBlockCompanionCube(fcCompanionCube.blockID - 256));
        Item.itemsList[fcMillStone.blockID] = (new FCItemBlockMillStone(fcMillStone.blockID - 256));
        Item.itemsList[fcUnfiredPottery.blockID] = (new FCItemBlockUnfiredPottery(fcUnfiredPottery.blockID - 256));        
        Item.itemsList[fcVase.blockID] = (new FCItemBlockVase(fcVase.blockID - 256));        
        Item.itemsList[fcPlanter.blockID] = (new FCItemBlockPlanter(fcPlanter.blockID - 256));        
        Item.itemsList[fcAestheticNonOpaque.blockID] = (new FCItemBlockAestheticNonOpaque(fcAestheticNonOpaque.blockID - 256));        
        Item.itemsList[fcAestheticOpaque.blockID] = (new FCItemBlockAestheticOpaque(fcAestheticOpaque.blockID - 256));
        Item.itemsList[fcAestheticVegetation.blockID] = (new FCItemBlockAestheticVegetation(fcAestheticVegetation.blockID - 256));
        Item.itemsList[fcBloodWood.blockID] = (new FCItemBlockBloodWood(fcBloodWood.blockID - 256));
        Item.itemsList[fcBlockBloodLeaves.blockID] = (new FCItemBlockLeaves(fcBlockBloodLeaves.blockID - 256));
        Item.itemsList[fcBlockDirtSlab.blockID] = (new FCItemBlockDirtSlab(fcBlockDirtSlab.blockID - 256));
        Item.itemsList[fcBlockWhiteStoneStairs.blockID] = (new FCItemBlockStairsWhiteStone(fcBlockWhiteStoneStairs.blockID - 256));
        
        Item.itemsList[fcBlockLegacySmoothstoneAndOakSiding.blockID] = (new FCItemBlockLegacySiding(fcBlockLegacySmoothstoneAndOakSiding.blockID - 256));        
        Item.itemsList[fcBlockLegacySmoothstoneAndOakCorner.blockID] = (new FCItemBlockLegacyCorner(fcBlockLegacySmoothstoneAndOakCorner.blockID - 256));
        
        Item.itemsList[fcBlockStoneBrickSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockStoneBrickSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockStoneBrickMouldingAndDecorative.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockStoneBrickMouldingAndDecorative.blockID - 256));
        
        Item.itemsList[fcBlockWoodOakSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockWoodOakSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockWoodOakMouldingAndDecorative.blockID] = (new FCItemBlockMoulding(fcBlockWoodOakMouldingAndDecorative.blockID - 256));        
        Item.itemsList[fcBlockWoodSpruceSidingAndCorner.blockID] = (new FCItemBlockWoodSidingStub(fcBlockWoodSpruceSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockWoodSpruceMouldingAndDecorative.blockID] = (new FCItemBlockWoodMouldingStub(fcBlockWoodSpruceMouldingAndDecorative.blockID - 256));        
        Item.itemsList[fcBlockWoodBirchSidingAndCorner.blockID] = (new FCItemBlockWoodCornerStub(fcBlockWoodBirchSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockWoodBirchMouldingAndDecorative.blockID] = (new FCItemBlockWoodMouldingDecorativeStub(fcBlockWoodBirchMouldingAndDecorative.blockID - 256));        
        Item.itemsList[fcBlockWoodJungleSidingAndCorner.blockID] = (new FCItemBlockWoodSidingDecorativeStub(fcBlockWoodJungleSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockWoodJungleMouldingAndDecorative.blockID] = (new FCItemBlockMoulding(fcBlockWoodJungleMouldingAndDecorative.blockID - 256));
        Item.itemsList[fcBlockWoodBloodSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockWoodBloodSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockWoodBloodMouldingAndDecorative.blockID] = (new FCItemBlockMoulding(fcBlockWoodBloodMouldingAndDecorative.blockID - 256));
        
    	fcBlockWoodSidingItemStubID = fcBlockWoodSpruceSidingAndCorner.blockID;
    	fcBlockWoodMouldingItemStubID = fcBlockWoodSpruceMouldingAndDecorative.blockID;
    	fcBlockWoodCornerItemStubID = fcBlockWoodBirchSidingAndCorner.blockID;
    	fcBlockWoodMouldingDecorativeItemStubID = fcBlockWoodBirchMouldingAndDecorative.blockID;
    	fcBlockWoodSidingDecorativeItemStubID = fcBlockWoodJungleSidingAndCorner.blockID;
    	
        Item.itemsList[fcBlockWhiteStoneSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockWhiteStoneSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockWhiteStoneMouldingAndDecorative.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockWhiteStoneMouldingAndDecorative.blockID - 256));        
        
        Item.itemsList[fcBlockNetherBrickSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockNetherBrickSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockNetherBrickMouldingAndDecorative.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockNetherBrickMouldingAndDecorative.blockID - 256));
        
        Item.itemsList[fcBlockBrickSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockBrickSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockBrickMouldingAndDecorative.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockBrickMouldingAndDecorative.blockID - 256));
        
        Item.itemsList[fcBlockSmoothStoneSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockSmoothStoneSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockSmoothStoneMouldingAndDecorative.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockSmoothStoneMouldingAndDecorative.blockID - 256));
        
        Item.itemsList[fcBlockSandstoneSidingAndCorner.blockID] = (new FCItemBlockSidingAndCorner(fcBlockSandstoneSidingAndCorner.blockID - 256));
        Item.itemsList[fcBlockSandstoneMouldingAndDecorative.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockSandstoneMouldingAndDecorative.blockID - 256));
        
        Item.itemsList[fcBlockSidingAndCornerBlackStone.blockID] = (new FCItemBlockSidingAndCorner(fcBlockSidingAndCornerBlackStone.blockID - 256));
        Item.itemsList[fcBlockMouldingAndDecorativeBlackStone.blockID] = (new FCItemBlockMouldingAndDecorative(fcBlockMouldingAndDecorativeBlackStone.blockID - 256));
        
        Item.itemsList[fcBlockSoulforgeDormant.blockID] = (new FCItemBlockSoulforgeDormant(fcBlockSoulforgeDormant.blockID - 256));
        
        Item.itemsList[fcBlockRottenFleshSlab.blockID] = new FCItemBlockSlab(fcBlockRottenFleshSlab.blockID - 256);
        Item.itemsList[fcBlockBoneSlab.blockID] = new FCItemBlockSlab(fcBlockBoneSlab.blockID - 256);
        
        Item.itemsList[fcBlockPumpkinFresh.blockID] = new FCItemBlockPumpkinFresh(fcBlockPumpkinFresh.blockID - 256);
        
        Item.itemsList[fcBlockLogDamaged.blockID] = new FCItemBlockDamageToMetadata(fcBlockLogDamaged.blockID - 256);
        
        Item.itemsList[fcBlockDirtLooseSlab.blockID] = new FCItemBlockDirtLooseSlab(fcBlockDirtLooseSlab.blockID - 256);
        Item.itemsList[fcBlockBrickLooseSlab.blockID] = new FCItemBlockSlab(fcBlockBrickLooseSlab.blockID - 256);
        Item.itemsList[fcBlockCobblestoneLooseSlab.blockID] = new FCItemBlockSlab(fcBlockCobblestoneLooseSlab.blockID - 256);
        
        Item.itemsList[fcBlockTorchFiniteUnlit.blockID] = new FCItemBlockTorchFiniteIdle(fcBlockTorchFiniteUnlit.blockID - 256);
        Item.itemsList[fcBlockTorchFiniteBurning.blockID] = new FCItemBlockTorchFiniteBurning(fcBlockTorchFiniteBurning.blockID - 256);
        Item.itemsList[fcBlockTorchNetherUnlit.blockID] = new FCItemBlockTorchIdle(fcBlockTorchNetherUnlit.blockID - 256);        
        
        Item.itemsList[fcBlockMyceliumSlab.blockID] = new FCItemBlockSlab(fcBlockMyceliumSlab.blockID - 256);
        
        Item.itemsList[fcBlockStumpCharred.blockID] = new FCItemBlockDamageToMetadata(fcBlockStumpCharred.blockID - 256);
        
        Item.itemsList[fcBlockSnowLooseSlab.blockID] = new FCItemBlockSnowLooseSlab(fcBlockSnowLooseSlab.blockID - 256);
        Item.itemsList[fcBlockSnowSolidSlab.blockID] = new FCItemBlockSlab(fcBlockSnowSolidSlab.blockID - 256);
        
        Item.itemsList[fcBlockCreeperOystersSlab.blockID] = new FCItemBlockSlab(fcBlockCreeperOystersSlab.blockID - 256);
        
        Item.itemsList[fcBlockTorchNetherBurning.blockID] = (new FCItemBlockTorchBurning(fcBlockTorchNetherBurning.blockID - 256));
        
        Item.itemsList[fcBlockWickerSlab.blockID] = new FCItemBlockSlab(fcBlockWickerSlab.blockID - 256);
        
		// vanilla block overrides
		
        Item.itemsList[Block.wood.blockID] = (new FCItemBlockLog(Block.wood.blockID - 256, Block.wood, BlockLog.woodType)).setUnlocalizedName("log");
        Item.itemsList[Block.planks.blockID] = (new ItemMultiTextureTile(Block.planks.blockID - 256, Block.planks, FCBlockPlanks.m_sWoodTypes)).setUnlocalizedName("wood");

		Item.itemsList[Block.lever.blockID] = (new FCItemBlockLever(Block.lever.blockID - 256)).setUnlocalizedName("lever");
        
		Item.itemsList[Block.ice.blockID] = (new FCItemBlockIce(Block.ice.blockID - 256)).setUnlocalizedName("ice");
        Item.itemsList[Block.snow.blockID] = (new FCItemBlockSnow(Block.snow.blockID - 256, Block.snow)).setUnlocalizedName("snow");
        
		Item.itemsList[Block.stone.blockID] = new ItemBlockWithMetadata(Block.stone.blockID - 256, Block.stone).setUnlocalizedName("stone");
		Item.itemsList[Block.cobblestone.blockID] = new ItemBlockWithMetadata(Block.cobblestone.blockID - 256, Block.cobblestone).setUnlocalizedName("stonebrick");
		Item.itemsList[Block.oreCoal.blockID] = new ItemBlockWithMetadata(Block.oreCoal.blockID - 256, Block.oreCoal).setUnlocalizedName("oreCoal");
		Item.itemsList[Block.oreIron.blockID] = new ItemBlockWithMetadata(Block.oreIron.blockID - 256, Block.oreIron).setUnlocalizedName("oreIron");
		Item.itemsList[Block.oreGold.blockID] = new ItemBlockWithMetadata(Block.oreGold.blockID - 256, Block.oreGold).setUnlocalizedName("oreGold");
		Item.itemsList[Block.oreDiamond.blockID] = new ItemBlockWithMetadata(Block.oreDiamond.blockID - 256, Block.oreDiamond).setUnlocalizedName("oreDiamond");
		Item.itemsList[Block.oreEmerald.blockID] = new ItemBlockWithMetadata(Block.oreEmerald.blockID - 256, Block.oreEmerald).setUnlocalizedName("oreEmerald");
		Item.itemsList[Block.oreLapis.blockID] = new ItemBlockWithMetadata(Block.oreLapis.blockID - 256, Block.oreLapis).setUnlocalizedName("oreLapis");
		Item.itemsList[Block.oreRedstone.blockID] = new ItemBlockWithMetadata(Block.oreRedstone.blockID - 256, Block.oreRedstone).setUnlocalizedName("oreRedstone");
		
        Item.itemsList[Block.waterlily.blockID] = new FCItemBlockLilyPad(Block.waterlily.blockID - 256).setUnlocalizedName("waterlily");
        Item.itemsList[Block.anvil.blockID] = (new FCItemBlockDeadWeight(Block.anvil.blockID - 256)).setUnlocalizedName("anvil");
        
        Item.itemsList[Block.torchWood.blockID] = (new FCItemBlockTorchLegacy(Block.torchWood.blockID - 256)).setUnlocalizedName("torch");
        
		// vanilla block stack size changes 
		
		Item.itemsList[Block.melon.blockID].setMaxStackSize(16);
		
		// vanilla item block substitutions so old items will place different blocks

		Item.itemsList[Block.workbench.blockID] = new FCItemBlockLegacySubstitution(Block.workbench.blockID - 256, fcBlockWorkbench.blockID);		
		Item.itemsList[Block.chest.blockID] = new FCItemBlockLegacySubstitution(Block.chest.blockID - 256, fcBlockChest.blockID);		
		Item.itemsList[Block.doorWood.blockID] = new FCItemBlockLegacySubstitution(Block.doorWood.blockID - 256, fcBlockDoorWood.blockID);		
		Item.itemsList[Block.web.blockID] = new FCItemBlockLegacySubstitution(Block.web.blockID - 256, fcBlockWeb.blockID);		
		
		Item.itemsList[Block.blockClay.blockID] = new FCItemBlockLegacySubstitution(Block.blockClay.blockID - 256, fcBlockUnfiredClay.blockID);
		
		Item.itemsList[Block.ladder.blockID] = new FCItemBlockLegacySubstitution(Block.ladder.blockID - 256, fcBlockLadder.blockID);
		
		Item.itemsList[Block.blockSnow.blockID] = new FCItemBlockLegacySubstitution(Block.blockSnow.blockID - 256, fcBlockSnowSolid.blockID);
		
		Item.m_bSuppressConflictWarnings = false;
	}
	
	private void CreateModTileEntityMappings()
	{
		TileEntity.addMapping(FCTileEntityCement.class, "Cement");		
		TileEntity.addMapping(FCTileEntityCauldron.class, "fcCauldron");		
		TileEntity.addMapping(FCTileEntityMillStone.class, "MillStone");
		TileEntity.ReplaceVanillaMapping(TileEntityHopper.class, FCTileEntityHopper.class, "Hopper"); // needs to be a replace due to vanilla Hopper
		TileEntity.addMapping(FCTileEntityBlockDispenser.class, "BlockDispenser");
		TileEntity.addMapping(FCTileEntityPulley.class, "Pulley");
		TileEntity.addMapping(FCTileEntityTurntable.class, "Turntable");
		TileEntity.addMapping(FCTileEntityVase.class, "Vase");
		TileEntity.addMapping(FCTileEntityCrucible.class, "Crucible");
		
		TileEntity.addMapping(FCTileEntityInfernalEnchanter.class, "fcInfernalEnchanter");
		TileEntity.addMapping(FCTileEntityAnvil.class, "fcAnvil");
		
		TileEntity.addMapping(FCTileEntityArcaneVessel.class, "fcArcaneVessel");
		
		TileEntity.addMapping(FCTileEntityCampfire.class, "fcCampfire");		
		TileEntity.addMapping(FCTileEntityUnfiredBrick.class, "fcUnfiredBrick");		
		TileEntity.addMapping(FCTileEntityFurnaceBrick.class, "fcFurnaceBrick");
		TileEntity.addMapping(FCTileEntityTorchFinite.class, "fcTorchFinite");		
		TileEntity.addMapping(FCTileEntityBasketWicker.class, "fcBasket");		
		TileEntity.addMapping(FCTileEntityToolPlaced.class, "fcToolPlaced");		
		TileEntity.addMapping(FCTileEntityHamper.class, "fcHamper");		
		
		// vanilla tile entity substitutions
		
		TileEntity.ReplaceVanillaMapping(TileEntityChest.class, FCTileEntityChest.class, "Chest");
		TileEntity.ReplaceVanillaMapping(TileEntityMobSpawner.class, FCTileEntityMobSpawner.class, "MobSpawner");
		TileEntity.ReplaceVanillaMapping(TileEntityBeacon.class, FCTileEntityBeacon.class, "Beacon");
		TileEntity.ReplaceVanillaMapping(TileEntityEnderChest.class, FCTileEntityEnderChest.class, "EnderChest");		
	}
	
	private void CreateModEntityMappings()
	{
		EntityList.AddMapping(FCEntityWaterWheel.class, "WaterWheel", parseID("fcWaterWheelEntityID"));		
		EntityList.AddMapping(FCEntityWindMill.class, "WindMill", parseID("fcWindMillEntityID"));		
		EntityList.AddMapping(FCEntityMovingAnchor.class, "MovingAnchor", parseID("fcMovingAnchorEntityID"));
		EntityList.AddMapping(FCEntityMovingPlatform.class, "MovingPlatform", parseID("fcMovingPlatformEntityID"));
		EntityList.AddMapping(FCEntityBlockLiftedByPlatform.class, "BlockLiftedByPlatform", parseID("fcBlockLiftedByPlatformEntityID"));
		EntityList.AddMapping(FCEntityBroadheadArrow.class, "BroadheadArrow", parseID("fcBroadheadArrowEntityID"));
		EntityList.AddMapping(FCEntityUrn.class, "Urn", parseID("fcUrnEntityID"));
		EntityList.AddMapping(FCEntityDynamite.class, "Dynamite", parseID("fcDynamiteEntityID"));
		EntityList.AddMapping(FCEntityMiningCharge.class, "MiningCharge", parseID("fcMiningChargeEntityID"));
		EntityList.AddMapping(FCEntityInfiniteArrow.class, "fcInfiniteArrow", parseID("fcInfiniteArrowEntityID"));
		EntityList.AddMapping(FCEntityItemFloating.class, "fcItemFloating", parseID("fcItemFloatingEntityID"));
		EntityList.AddMapping(FCEntityItemBloodWoodSapling.class, "fcItemBloodWoodSapling", parseID("fcItemBloodWoodSaplingEntityID"));		
		EntityList.AddMapping(FCEntityCanvas.class, "fcCanvas", parseID("fcCanvasEntityID"));
		EntityList.AddMapping(FCEntityRottenArrow.class, "fcRottenArrow", parseID("fcRottenArrowEntityID"));
		EntityList.AddMapping(FCEntityWindMillVertical.class, "fcWindMillVertical", parseID("fcEntityWindMillVerticalID"));
		EntityList.AddMapping(FCEntitySpiderWeb.class, "fcSpiderWeb", parseID("fcEntitySpiderWebID"));
		EntityList.AddMapping(FCEntityWolfDire.class, "fcDireWolf", parseID("fcEntityDireWolfID"));
		EntityList.AddMapping(FCEntitySoulSand.class, "fcEntitySoulSand", parseID("fcEntitySoulSandID"));
		EntityList.AddMapping(FCEntityJungleSpider.class, "fcJungleSpider", parseID("fcEntityJungleSpiderID"));
		EntityList.AddMapping(FCEntityWitherPersistent.class, "fcWitherPersistent", parseID("fcWitherPersistentID"));
		
		//Uses legacy names from old API for compatibility
		EntityList.addMapping(FCEntityVillagerFarmer.class, "addonVillagerFarmer", 600, 5651507, 12422002);
		EntityList.addMapping(FCEntityVillagerLibrarian.class, "addonVillagerLibrarian", 601, 14342874, 16179719);
		EntityList.addMapping(FCEntityVillagerPriest.class, "addonVillagerPriest", 602, 8470879, 12422002);
		EntityList.addMapping(FCEntityVillagerBlacksmith.class, "addonVillagerBlacksmith", 603, 4802889, 12422002);
		EntityList.addMapping(FCEntityVillagerButcher.class, "addonVillagerButcher", 604, 11447982, 12422002);
		//Removes generic villager egg since specific villager types have their own eggs now
		EntityList.entityEggs.remove(120);
		
		EntityList.ReplaceExistingMapping(FCEntityWitherSkull.class, "WitherSkull");
		EntityList.ReplaceExistingMapping(FCEntityFallingBlock.class, "FallingSand");
        EntityList.ReplaceExistingMapping(FCEntityCreeper.class, "Creeper");
        EntityList.ReplaceExistingMapping(FCEntitySkeleton.class, "Skeleton");
        EntityList.ReplaceExistingMapping(FCEntitySpider.class, "Spider");
        EntityList.ReplaceExistingMapping(FCEntityZombie.class, "Zombie");		
        EntityList.ReplaceExistingMapping(FCEntitySlime.class, "Slime");
        EntityList.ReplaceExistingMapping(FCEntityGhast.class, "Ghast");
        EntityList.ReplaceExistingMapping(FCEntityPigZombie.class, "PigZombie");
        EntityList.ReplaceExistingMapping(FCEntityEnderman.class, "Enderman");
        EntityList.ReplaceExistingMapping(FCEntityCaveSpider.class, "CaveSpider");
        EntityList.ReplaceExistingMapping(FCEntityBlaze.class, "Blaze");
        EntityList.ReplaceExistingMapping(FCEntityMagmaCube.class, "LavaSlime");
        EntityList.ReplaceExistingMapping(FCEntityWither.class, "WitherBoss");
        EntityList.ReplaceExistingMapping(FCEntityBat.class, "Bat");
        EntityList.ReplaceExistingMapping(FCEntityWitch.class, "Witch");
        EntityList.ReplaceExistingMapping(FCEntityPig.class, "Pig");
        EntityList.ReplaceExistingMapping(FCEntitySheep.class, "Sheep");
        EntityList.ReplaceExistingMapping(FCEntityCow.class, "Cow");
        EntityList.ReplaceExistingMapping(FCEntityChicken.class, "Chicken");
        EntityList.ReplaceExistingMapping(FCEntitySquid.class, "Squid");
        EntityList.ReplaceExistingMapping(FCEntityWolf.class, "Wolf");
        EntityList.ReplaceExistingMapping(FCEntitySnowman.class, "SnowMan");
        EntityList.ReplaceExistingMapping(FCEntityOcelot.class, "Ozelot");
        EntityList.ReplaceExistingMapping(FCEntityVillager.class, "Villager");
	}
	
	private void InitBlocksPotentialFluidSources()
	{
		for (int iBlockID = 1; iBlockID < 4096; iBlockID++)
		{
			Block block = Block.blocksList[iBlockID];
			
			if (block != null && block instanceof FCIBlockFluidSource)
			{
				m_bBlocksPotentialFluidSources[iBlockID] = true;
			}
			else
			{
				m_bBlocksPotentialFluidSources[iBlockID] = false;
			}
		}
	}
	
	private void InitDispenserBehaviors()
	{		
		BlockDispenser.dispenseBehaviorRegistry.putObject(fcItemBroadheadArrow, new FCBehaviorBroadheadArrowDispense());
        BlockDispenser.dispenseBehaviorRegistry.putObject(fcItemRottenArrow, new FCBehaviorRottedArrowDispense());
        BlockDispenser.dispenseBehaviorRegistry.putObject(fcItemSoulUrn, new FCBehaviorSoulUrnDispense());
        BlockDispenser.dispenseBehaviorRegistry.putObject(fcItemDynamite, new FCBehaviorDispenseDynamite());
        
        // need this on the standalone server due to init order problems
        
        if (MinecraftServer.getServer() != null)
        {
        	BlockDispenser.dispenseBehaviorRegistry.putObject(Item.potion, new DispenserBehaviorPotion());
        	
        	DispenserBehaviorFilledBucket filledBucketBehavior = new DispenserBehaviorFilledBucket();
        	
            BlockDispenser.dispenseBehaviorRegistry.putObject(Item.bucketLava, filledBucketBehavior);
            BlockDispenser.dispenseBehaviorRegistry.putObject(Item.bucketWater, filledBucketBehavior);
            
            BlockDispenser.dispenseBehaviorRegistry.putObject(Item.bucketEmpty, new DispenserBehaviorEmptyBucket());
        }
	}
	
	private void registerConfigProperties() {
		//Gameplay config
		this.registerProperty("PlayerSkinURL", fcPlayerSkinURL, "Set the following to the addresses of the player skin servers you wish to use (the defaults are now defunct)");
		this.registerProperty("PlayerCloakURL", fcPlayerCloakURL);
		
		this.registerProperty("EnableHardcoreBuoy", "True", "Set the following to false to disable hardcore buoy, which causes some items to float in water");
		this.registerProperty("DisableEndText", "True", "Set the following to false to re-enable the end game text when leaving the end dimension. You'll regret it though.");
		this.registerProperty("DisableGearBoxPowerDrain", "False", "Set the following to true to stop gearboxes from shortening the length of mechanical power pulses through time.");
		
		//Client only config
		this.registerProperty("UseBetterGrass", "False", "Set to true to enable MCPatcher's Better Grass, which renders the grass top texture on the sides of grass blocks on 1 block tall slopes");
		
		//Server only config
		this.registerProperty("EnableHardcorePlayerNames", "2", "***Standalone Server Settings (do not apply to singleplayer or LAN***\n\n"
																+ "# Set to 0 to allow players to always see the names of other players\n"
																+ "# Set to 1 for hardcore, which displays no player names at all\n"
																+ "# Set to 2 for obstructed, which displays names, but not if they are behind other objects like blocks.");
		
		//Block ids
		this.registerProperty("fcBlockSlabFallingID", "175", "***Block IDs***\n\n");
		this.registerProperty("fcBlockArcaneVesselID", "176");
		this.registerProperty("fcBlockAxlePowerSourceID", "177");
		this.registerProperty("fcBlockSidingAndCornerBlackStoneID", "178");
		this.registerProperty("fcBlockMouldingAndDecorativeBlackStoneID", "179");
		this.registerProperty("fcBlockAestheticOpaqueEarthID", "180");
		this.registerProperty("fcBlockCandleID", "181");
		this.registerProperty("fcBlockSandstoneSidingAndCornerID", "182");
		this.registerProperty("fcBlockSandstoneMouldingAndDecorativeID", "183");
		this.registerProperty("fcBlockWoodOakSidingAndCornerID", "184");
		this.registerProperty("fcBlockSmoothStoneSidingAndCornerID", "185");
		this.registerProperty("fcBlockBrickSidingAndCornerID", "186");
		this.registerProperty("fcBlockBrickMouldingAndDecorativeID", "187");
		this.registerProperty("fcBlockNetherBrickSidingAndCornerID", "188");
		this.registerProperty("fcBlockNetherBrickMouldingAndDecorativeID", "189");
		this.registerProperty("fcBlockWhiteStoneStairsID", "190");
		this.registerProperty("fcBlockWhiteStoneSidingAndCornerID", "191");
		this.registerProperty("fcBlockWhiteStoneMouldingAndDecorativeID", "192");
		this.registerProperty("fcBlockStakeStringID", "193");
		this.registerProperty("fcBlockStakeID", "194");
		this.registerProperty("fcBlockScrewPumpID", "195");
		this.registerProperty("fcBlockWoodSpruceSidingAndCornerID", "196");
		this.registerProperty("fcBlockWoodSpruceMouldingID", "197");
		this.registerProperty("fcBlockWoodBirchSidingAndCornerID", "198");
		this.registerProperty("fcBlockWoodBirchMouldingID", "199");
		this.registerProperty("fcBlockWoodJungleSidingAndCornerID", "200");
		this.registerProperty("fcBlockWoodJungleMouldingID", "201");
		this.registerProperty("fcBlockStoneBrickSidingAndCornerID", "202");
		this.registerProperty("fcBlockStoneBrickMouldingID", "203");
		this.registerProperty("fcBlockFarmlandFertilizedID", "204");
		this.registerProperty("fcBlockWoolSlabTopID", "205");
		this.registerProperty("fcBlockDirtSlabID", "206");
		this.registerProperty("fcBlockNetherGrowthID", "207");
		this.registerProperty("fcInfernalEnchanterID", "208");
		this.registerProperty("fcSoulforgedSteelBlockID", "209");
		this.registerProperty("fcBlockDetectorGlowingLogicID", "210");
		this.registerProperty("fcLeavesID", "211");
		this.registerProperty("fcBloodWoodID", "212");
		this.registerProperty("fcAestheticVegetationID", "213");
		this.registerProperty("fcStoneMouldingID", "214");
		this.registerProperty("fcAestheticOpaqueID", "215");
		this.registerProperty("fcAestheticNonOpaqueID", "216");
		this.registerProperty("fcMiningChargeID", "217");
		this.registerProperty("fcBuddyBlockID", "218");
		this.registerProperty("fcKilnID", "219");
		this.registerProperty("fcWoolSlabID", "220");
		this.registerProperty("fcAnvilID", "221");
		this.registerProperty("fcLightBulbOffID", "222");
		this.registerProperty("fcLightBulbOnID", "223");
		this.registerProperty("fcBBQID", "224");
		this.registerProperty("fcHopperID", "225");
		this.registerProperty("fcSawID", "226");
		this.registerProperty("fcPlatformID", "227");
		this.registerProperty("fcCementID", "228");
		this.registerProperty("fcPulleyID", "229");
		this.registerProperty("fcPressurePlateObsidianID", "230");
		this.registerProperty("fcMouldingID", "231");
		this.registerProperty("fcCornerID", "232");
		this.registerProperty("fcBlockDispenserID", "233");
		this.registerProperty("fcCauldronID", "234");
		this.registerProperty("fcDetectorRailWoodID", "235");
		this.registerProperty("fcDetectorRailObsidianID", "236");
		this.registerProperty("fcCompanionCubeID", "237");
		this.registerProperty("fcBlockDetectorID", "238");
		this.registerProperty("fcBlockDetectorLogicID", "239");
		this.registerProperty("fcBlockLensID", "240");
		this.registerProperty("fcHempCropID", "241");
		this.registerProperty("fcHandCrankID", "242");
		this.registerProperty("fcMillStoneID", "243");
		this.registerProperty("fcAnchorID", "244");
		this.registerProperty("fcRopeBlockID", "245");
		this.registerProperty("fcOmniSlabID", "246");
		this.registerProperty("fcAxleBlockID", "247");
		this.registerProperty("fcGearBoxID", "248");
		this.registerProperty("fcTurntableID", "249");
		this.registerProperty("fcBellowsID", "250");
		this.registerProperty("fcStokedFireID", "251");
		this.registerProperty("fcUnfiredPotteryID", "252");
		this.registerProperty("fcCrucibleID", "253");
		this.registerProperty("fcPlanterID", "254");
		this.registerProperty("fcVaseID", "255");
		
		this.registerProperty("fcBlockRottenFleshID", "1000");
		this.registerProperty("fcBlockShaftID", "1001");
		this.registerProperty("fcBlockSoulforgeDormantID", "1002");
		this.registerProperty("fcBlockSmoothstoneStairsID", "1003");
		this.registerProperty("fcBlockRottenFleshSlabID", "1004");
		this.registerProperty("fcBlockBoneSlabID", "1005");
		this.registerProperty("fcBlockPumpkinFreshID", "1006");
		this.registerProperty("fcBlockWoodBloodSidingAndCornerID", "1007");
		this.registerProperty("fcBlockWoodBloodMouldingAndDecorativeID", "1008");
		this.registerProperty("fcBlockWoodBloodStairsID", "1009");
		this.registerProperty("fcBlockLogDamagedID", "1010");
		this.registerProperty("fcBlockDirtLooseID", "1011");
		this.registerProperty("fcBlockDirtLooseSlabID", "1012");
		this.registerProperty("fcBlockCampfireUnlitID", "1013");
		this.registerProperty("fcBlockCampfireSmallID", "1014");
		this.registerProperty("fcBlockCampfireMediumID", "1015");
		this.registerProperty("fcBlockCampfireLargeID", "1016");
		this.registerProperty("fcBlockUnfiredBrickID", "1017");
		this.registerProperty("fcBlockCookedBrickID", "1018");
		this.registerProperty("fcBlockBrickLooseID", "1019");
		this.registerProperty("fcBlockBrickLooseSlabID", "1020");
		this.registerProperty("fcBlockCobblestoneLooseID", "1021");
		this.registerProperty("fcBlockCobblestoneLooseSlabID", "1022");
		this.registerProperty("fcBlockFurnaceBrickIdleID", "1023");
		this.registerProperty("fcBlockFurnaceBrickBurningID", "1024");
		this.registerProperty("fcBlockTorchFiniteIdleID", "1025");
		this.registerProperty("fcBlockTorchFiniteBurningID", "1026");
		this.registerProperty("fcBlockStoneRoughID", "1027");
		this.registerProperty("fcBlockStoneRoughMidStrataID", "1028");
		this.registerProperty("fcBlockStoneRoughDeepStrataID", "1029");
		this.registerProperty("fcBlockWorkStumpID", "1030");
		this.registerProperty("fcBlockBasketWickerID", "1031");
		this.registerProperty("fcBlockLogSpikeID", "1032");
		this.registerProperty("fcBlockTorchIdleID", "1033");
		this.registerProperty("fcBlockWorkbenchID", "1034");
		this.registerProperty("fcBlockChestID", "1035");
		this.registerProperty("fcBlockDoorWoodID", "1036");
		this.registerProperty("fcBlockWebID", "1037");
		this.registerProperty("fcBlockUnfiredClayID", "1038");
		this.registerProperty("fcBlockMyceliumSlabID", "1039");
		this.registerProperty("fcBlockToolPlacedID", "1040");
		this.registerProperty("fcBlockBrickLooseStairsID", "1041");
		this.registerProperty("fcBlockCobblestoneLooseStairsID", "1042");
		this.registerProperty("fcBlockLogSmoulderingID", "1043");
		this.registerProperty("fcBlockWoodCindersID", "1044");
		this.registerProperty("fcBlockStumpCharredID", "1045");
		this.registerProperty("fcBlockAshGroundCoverID", "1046");
		this.registerProperty("fcBlockSnowLooseID", "1047");
		this.registerProperty("fcBlockSnowLooseSlabID", "1048");
		this.registerProperty("fcBlockSnowSolidID", "1049");
		this.registerProperty("fcBlockSnowSolidSlabID", "1050");
		this.registerProperty("fcBlockLadderID", "1051");
		this.registerProperty("fcBlockLadderOnFireID", "1052");
		this.registerProperty("fcBlockShovelID", "1053");
		this.registerProperty("fcBlockHamperID", "1054");
		this.registerProperty("fcBlockCreeperOystersID", "1055");
		this.registerProperty("fcBlockCreeperOystersSlabID", "1056");
		this.registerProperty("fcBlockTorchNetherBurningID", "1057");
		this.registerProperty("fcBlockBucketEmptyID", "1058");
		this.registerProperty("fcBlockBucketWaterID", "1059");
		this.registerProperty("fcBlockBucketCementID", "1060");
		this.registerProperty("fcBlockBucketMilkID", "1061");
		this.registerProperty("fcBlockBucketMilkChocolateID", "1062");
		this.registerProperty("fcBlockMilkID", "1063");
		this.registerProperty("fcBlockMilkChocolateID", "1064");
		this.registerProperty("fcBlockGearBoxID", "1065");
		this.registerProperty("fcBlockSpikeIronID", "1066");
		this.registerProperty("fcBlockLightningRodID", "1067");
		this.registerProperty("fcBlockChunkOreIronID", "1068");
		this.registerProperty("fcBlockChunkOreGoldID", "1069");
		this.registerProperty("fcBlockStoneBrickLooseID", "1070");
		this.registerProperty("fcBlockStoneBrickLooseSlabID", "1071");
		this.registerProperty("fcBlockStoneBrickLooseStairsID", "1072");
		this.registerProperty("fcBlockNetherBrickLooseID", "1073");
		this.registerProperty("fcBlockNetherBrickLooseSlabID", "1074");
		this.registerProperty("fcBlockNetherBrickLooseStairsID", "1075");
		this.registerProperty("fcBlockNetherrackFallingID", "1076");
		this.registerProperty("fcBlockLavaPillowID", "1077");
		this.registerProperty("fcBlockMushroomCapBrownID", "1078");
		this.registerProperty("fcBlockMushroomCapRedID", "1079");
		this.registerProperty("fcBlockChunkOreStorageIronID", "1080");
		this.registerProperty("fcBlockChunkOreStorageGoldID", "1081");
		this.registerProperty("fcBlockWickerID", "1082");
		this.registerProperty("fcBlockWickerSlabID", "1083");
		this.registerProperty("fcBlockWickerPaneID", "1084");
		this.registerProperty("fcBlockGrateID", "1085");
		this.registerProperty("fcBlockSlatsID", "1086");
		this.registerProperty("fcBlockFarmlandNewID", "1087");
		this.registerProperty("fcBlockFarmlandFertilizedNewID", "1088");
		this.registerProperty("fcBlockWheatCropID", "1089");
		this.registerProperty("fcBlockWheatCropTopID", "1090");
		this.registerProperty("fcBlockWeedsID", "1091");
		this.registerProperty("fcBlockPlanterSoilID", "1092");
		
		//Item ids
		this.registerProperty("fcBucketCementID", "222", "***Item IDs***\n\n");
		this.registerProperty("fcWolfRawID", "223");
		this.registerProperty("fcWolfCookedID", "224");
		this.registerProperty("fcNethercoalID", "225");
		this.registerProperty("fcHempSeedsID", "226");
		this.registerProperty("fcHempID", "227");
		this.registerProperty("fcGearID", "228");
		this.registerProperty("fcFlourID", "229");
		this.registerProperty("fcHempFibersID", "230");
		this.registerProperty("fcScouredLeatherID", "231");
		this.registerProperty("fcDonutID", "232");
		this.registerProperty("fcRopeItemID", "233");
		this.registerProperty("fcRollersItemID", "234");
		this.registerProperty("fcDungID", "235");
		this.registerProperty("fcWaterWheelItemID", "236");
		this.registerProperty("fcWindMillBladeItemID", "237");
		this.registerProperty("fcWindMillItemID", "238");
		this.registerProperty("fcHempClothID", "239");
		this.registerProperty("fcGrateID", "240");
		this.registerProperty("fcWickerID", "241");
		this.registerProperty("fcTannedLeatherID", "242");
		this.registerProperty("fcStrapID", "243");
		this.registerProperty("fcBeltID", "244");
		this.registerProperty("fcFoulFoodID", "245");
		this.registerProperty("fcWoodBladeID", "246");
		this.registerProperty("fcGlueID", "247");
		this.registerProperty("fcTallowID", "248");
		this.registerProperty("fcHaftID", "249");
		this.registerProperty("fcSteelID", "250");
		this.registerProperty("fcRefinedPickAxeID", "251");
		this.registerProperty("fcRefinedShovelID", "252");
		this.registerProperty("fcRefinedHoeID", "253");
		this.registerProperty("fcBattleAxeID", "254");
		this.registerProperty("fcRefinedSwordID", "255");
		this.registerProperty("fcGroundNetherrackID", "256");
		this.registerProperty("fcHellfireDustID", "257");
		this.registerProperty("fcConcentratedHellfireID", "258");
		this.registerProperty("fcArmorPlateID", "259");
		this.registerProperty("fcPlateHelmID", "260");
		this.registerProperty("fcPlateBreastPlateID", "261");
		this.registerProperty("fcPlateLeggingsID", "262");
		this.registerProperty("fcPlateBootsID", "263");
		this.registerProperty("fcCompositeBowID", "264");
		this.registerProperty("fcBroadheadArrowheadID", "265");
		this.registerProperty("fcBroadheadArrowID", "266");
		this.registerProperty("fcCoalDustID", "267");
		this.registerProperty("fcPaddingID", "268");
		this.registerProperty("fcFilamentID", "269");
		this.registerProperty("fcPolishedLapisID", "270");
		this.registerProperty("fcUrnID", "271");
		this.registerProperty("fcSoulUrnID", "272");
		this.registerProperty("fcHardBoiledEggID", "273");
		this.registerProperty("fcPotashID", "274");
		this.registerProperty("fcSoapID", "275");
		this.registerProperty("fcSawDustID", "276");
		this.registerProperty("fcTannedLeatherHelmID", "277");
		this.registerProperty("fcTannedLeatherChestID", "278");
		this.registerProperty("fcTannedLeatherLeggingsID", "279");
		this.registerProperty("fcTannedLeatherBootsID", "280");
		this.registerProperty("fcDynamiteID", "281");
		this.registerProperty("fcBreedingHarnessID", "282");
		this.registerProperty("fcSoulDustID", "283");
		this.registerProperty("fcMattockID", "284");
		this.registerProperty("fcRefinedAxeID", "285");
		this.registerProperty("fcNetherSludgeID", "286");
		this.registerProperty("fcNetherBrickID", "287");
		
		this.registerProperty("fcItemTuningForkID", "22222");
		this.registerProperty("fcItemArcaneScrollID", "22223");
		this.registerProperty("fcItemCandleID", "22224");
		this.registerProperty("fcItemNetherGrowthSporesID", "22225");
		this.registerProperty("fcItemMouldID", "22226");
		this.registerProperty("fcItemCanvasID", "22227");
		this.registerProperty("fcItemDogFoodID", "22228");
		this.registerProperty("fcItemRawEggID", "22229");
		this.registerProperty("fcItemFriedEggID", "22230");
		this.registerProperty("fcItemScrewID", "22231");
		this.registerProperty("fcItemRottenArrowID", "22232");
		this.registerProperty("fcItemOcularOfEnderID", "22233");
		this.registerProperty("fcItemEnderSpectaclesID", "22234");
		this.registerProperty("fcItemStakeID", "22235");
		this.registerProperty("fcItemBrimstoneID", "22236");
		this.registerProperty("fcItemNitreID", "22237");
		this.registerProperty("fcItemElementID", "22238");
		this.registerProperty("fcItemFuseID", "22239");
		this.registerProperty("fcItemBlastingOilID", "22240");
		this.registerProperty("fcItemWindMillVerticalID", "22241");
		this.registerProperty("fcItemBoiledPotatoID", "22242");
		this.registerProperty("fcItemMuttonRawID", "22243");
		this.registerProperty("fcItemMuttonCookedID", "22244");
		this.registerProperty("fcItemWitchWartID", "22245");
		this.registerProperty("fcItemCookedCarrotID", "22246");
		this.registerProperty("fcItemTastySandwichID", "22247");
		this.registerProperty("fcItemSteakAndPotatoesID", "22248");
		this.registerProperty("fcItemHamAndEggsID", "22249");
		this.registerProperty("fcItemSteakDinnerID", "22250");
		this.registerProperty("fcItemPorkDinnerID", "22251");
		this.registerProperty("fcItemWolfDinnerID", "22252");
		this.registerProperty("fcItemRawKebabID", "22253");
		this.registerProperty("fcItemCookedKebabID", "22254");
		this.registerProperty("fcItemChickenSoupID", "22255");
		this.registerProperty("fcItemFishSoupID", "22256");
		this.registerProperty("fcItemHeartyStewID", "22257");
		this.registerProperty("fcItemMushroomRedID", "22258");
		this.registerProperty("fcItemMushroomBrownID", "22259");
		this.registerProperty("fcItemNuggetIronID", "22260");
		this.registerProperty("fcItemMailID", "22261");
		this.registerProperty("fcItemRawMysteryMeatID", "22262");
		this.registerProperty("fcItemCookedMysteryMeatID", "22263");
		this.registerProperty("fcItemRawMushroomOmeletID", "22264");
		this.registerProperty("fcItemCookedMushroomOmeletID", "22265");
		this.registerProperty("fcItemRawScrambledEggsID", "22266");
		this.registerProperty("fcItemCookedScrambledEggsID", "22267");
		this.registerProperty("fcItemCreeperOystersID", "22268");
		this.registerProperty("fcItemArmorWoolHelmID", "22269");
		this.registerProperty("fcItemArmorWoolChestID", "22270");
		this.registerProperty("fcItemArmorWoolLeggingsID", "22271");
		this.registerProperty("fcItemArmorWoolBootsID", "22272");
		this.registerProperty("fcItemArmorPaddedHelmID", "22273");
		this.registerProperty("fcItemArmorPaddedChestID", "22274");
		this.registerProperty("fcItemArmorPaddedLeggingsID", "22275");
		this.registerProperty("fcItemArmorPaddedBootsID", "22276");
		this.registerProperty("fcItemArmorTannedHelmID", "22277");
		this.registerProperty("fcItemArmorTannedChestID", "22278");
		this.registerProperty("fcItemArmorTannedLeggingsID", "22279");
		this.registerProperty("fcItemArmorTannedBootsID", "22280");
		this.registerProperty("fcItemIngotDiamondID", "22281");
		this.registerProperty("fcItemLeatherCutID", "22282");
		this.registerProperty("fcItemTannedLeatherCutID", "22283");
		this.registerProperty("fcItemScouredLeatherCutID", "22284");
		this.registerProperty("fcItemFishingRodBaitedID", "22285");
		this.registerProperty("fcItemPileDirtID", "22286");
		this.registerProperty("fcItemPileSandID", "22287");
		this.registerProperty("fcItemPileGravelID", "22288");
		this.registerProperty("fcItemBatWingID", "22289");
		this.registerProperty("fcItemGoldenDungID", "22290");
		this.registerProperty("fcItemBarkID", "22291");
		this.registerProperty("fcItemPileSoulSandID", "22292");
		this.registerProperty("fcItemRedstoneLatchID", "22293");
		this.registerProperty("fcItemNuggetSteelID", "22294");
		
		this.registerProperty("fcItemWoolID", "2295");
		this.registerProperty("fcItemCocoaBeansID", "2296");
		this.registerProperty("fcItemChocolateID", "2297");
		this.registerProperty("fcItemBucketChocolateMilkID", "2298");
		this.registerProperty("fcItemSoulFluxID", "2299");
		this.registerProperty("fcItemEnderSlagID", "2300");
		this.registerProperty("fcItemPastryUncookedCakeID", "2301");
		this.registerProperty("fcItemPastryUncookedCookiesID", "2302");
		this.registerProperty("fcItemPastryUncookedPumpkinPieID", "2303");
		this.registerProperty("fcItemMysteriousGlandID", "2304");
		this.registerProperty("fcItemBeastLiverRawID", "2305");
		this.registerProperty("fcItemBeastLiverCookedID", "2306");
		this.registerProperty("fcItemAncientProphecyID", "2307");
		this.registerProperty("fcItemStumpRemoverID", "2308");
		
		this.registerProperty("fcItemChiselWoodID", "22309");
		this.registerProperty("fcItemStoneID", "22310");
		this.registerProperty("fcItemChiselStoneID", "22311");
		this.registerProperty("fcItemClubID", "22312");
		this.registerProperty("fcItemFireStarterSticksID", "22313");
		this.registerProperty("fcItemFireStarterBowID", "22314");
		this.registerProperty("fcItemChunkIronOreID", "22315");
		this.registerProperty("fcItemPileIronOreID", "22316");
		this.registerProperty("fcItemChiselIronID", "22317");
		this.registerProperty("fcItemChunkGoldOreID", "22318");
		this.registerProperty("fcItemPileGoldOreID", "22319");
		this.registerProperty("fcItemWickerPieceID", "22320");
		this.registerProperty("fcItemKnittingNeedlesID", "22321");
		this.registerProperty("fcItemKnittingID", "22322");
		this.registerProperty("fcItemWoolKnitID", "22323");
		this.registerProperty("fcItemClubBoneID", "22324");
		this.registerProperty("fcItemMeatCuredID", "22325");
		this.registerProperty("fcItemMetalFragmentID", "22326");
		this.registerProperty("fcItemPileClayID", "22327");
		this.registerProperty("fcItemMeatBurnedID", "22328");
		this.registerProperty("fcItemChickenFeedID", "22329");
		this.registerProperty("fcItemFishHookBoneID", "22330");
		this.registerProperty("fcItemCarvingBoneID", "22331");
		this.registerProperty("fcItemBrickStoneID", "22332");
		this.registerProperty("fcItemWickerWeavingID", "22333");
		this.registerProperty("fcItemWheatID", "22334");
		this.registerProperty("fcItemWheatSeedsID", "22335");
		this.registerProperty("fcItemBreadDoughID", "22336");
		this.registerProperty("fcItemStrawID", "22337");
		this.registerProperty("fcItemBrickUnfiredID", "22338");
		this.registerProperty("fcItemNetherBrickUnfiredID", "22339");
		
		//Entity ids
		this.registerProperty("fcWaterWheelEntityID", "222", "***Entity IDs***\n\n");
		this.registerProperty("fcWindMillEntityID", "223");
		this.registerProperty("fcMovingAnchorEntityID", "224");
		this.registerProperty("fcMovingPlatformEntityID", "225");
		this.registerProperty("fcBlockLiftedByPlatformEntityID", "226");
		this.registerProperty("fcBroadheadArrowEntityID", "227");
		this.registerProperty("fcUrnEntityID", "228");
		this.registerProperty("fcDynamiteEntityID", "229");
		this.registerProperty("fcMiningChargeEntityID", "230");
		this.registerProperty("fcInfiniteArrowEntityID", "231");
		this.registerProperty("fcItemFloatingEntityID", "232");
		this.registerProperty("fcItemBloodWoodSaplingEntityID", "233");
		this.registerProperty("fcCanvasEntityID", "234");
		this.registerProperty("fcRottenArrowEntityID", "235");
		this.registerProperty("fcEntityWindMillVerticalID", "236");
		this.registerProperty("fcEntitySpiderWebID", "237");
		this.registerProperty("fcEntityDireWolfID", "238");
		this.registerProperty("fcEntitySoulSandID", "239");
		this.registerProperty("fcEntityJungleSpiderID", "240");
		this.registerProperty("fcWitherPersistentID", "241");
		
		//Container ids
		this.registerProperty("fcMillStoneContainerID", "222", "***Container IDs***\n\n");
		this.registerProperty("fcCauldronContainerID", "223");
		this.registerProperty("fcHopperContainerID", "224");
		this.registerProperty("fcCrucibleContainerID", "225");
		this.registerProperty("fcAnvilContainerID", "226");
		this.registerProperty("fcBlockDispenserContainerID", "227");
		this.registerProperty("fcPulleyContainerID", "228");
		this.registerProperty("fcInfernalEnchanterContainerID", "229");
		this.registerProperty("fcFurnaceBrickContainerID", "230");
		this.registerProperty("fcHamperContainerID", "231");
		this.registerProperty("fcVanillaAnvilContainerID", "232");
		
		//Villager ids ported from extended addon API
		this.registerProperty("addonVillagerFarmer", "600");
		this.registerProperty("addonVillagerLibrarian", "601");
		this.registerProperty("addonVillagerPriest", "602");
		this.registerProperty("addonVillagerBlacksmith", "603");
		this.registerProperty("addonVillagerButcher", "604");
	}
	
	@Override
	public void handleConfigProperties(Map<String, String> propertyValues) {
		this.propertyValues = propertyValues;
		
		fcPlayerSkinURL = this.propertyValues.get("PlayerSkinURL");
		fcPlayerCloakURL = this.propertyValues.get("PlayerCloakURL");
		
		fcLocalEnableHardcoreBuoy = Boolean.parseBoolean(this.propertyValues.get("EnableHardcoreBuoy"));
		fcDisableEndText = Boolean.parseBoolean(this.propertyValues.get("DisableEndText"));
		fcDisableGearBoxPowerDrain = Boolean.parseBoolean(this.propertyValues.get("DisableGearBoxPowerDrain"));
		
		fcLocalHardcorePlayerNamesLevel = Integer.parseInt(this.propertyValues.get("EnableHardcorePlayerNames"));
		
//		com.prupe.mcpatcher.mal.block.RenderBlocksUtils.enableBetterGrass = Boolean.parseBoolean(this.propertyValues.get("UseBetterGrass"));
	}
    
    private int parseID(String name) {
    	try {
    		return Integer.parseInt(this.propertyValues.get(name));
    	}
    	catch (NumberFormatException e) {
    		if (this.propertyValues.get(name) == null) {
    			throw new IllegalArgumentException("Unable to find property " + name + " in addon " + this.addonName);
    		}
    		else {
    			throw new IllegalArgumentException("Invalid id value for property " + name + " in addon " + this.addonName + ". Check for stray whitespace");
    		}
    	}
    }
    
    @Override
    public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP player) {
    	if (!MinecraftServer.getServer().isSinglePlayer()) {
        	// send server connect message, which is displayed by all clients whether they have the mod installed or not
        	
    		FCUtilsWorld.SendPacketToPlayer(serverHandler, new Packet3Chat("\247e" + // yellow text
				player.username + " connected to Better Than Wolves CE server V" + FCBetterThanWolves.fcVersionString));
	    	
	    	// setup the version info packet
	    	
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        DataOutputStream dataStream = new DataOutputStream(byteStream);
	        
	        try
	        {
	        	dataStream.writeUTF(fcVersionString);
	        }
	        catch (Exception exception)
	        {
	            exception.printStackTrace();
	        }        

	        // send packet 
	        
	        Packet250CustomPayload versionPacket = new Packet250CustomPayload(
	        	FCBetterThanWolves.fcCustomPacketChannelVersionCheck, byteStream.toByteArray());
	        
	        FCUtilsWorld.SendPacketToPlayer(serverHandler, versionPacket);
    	}
    	else {
    		FCUtilsWorld.SendPacketToPlayer(serverHandler, new Packet3Chat("\247f" // white
	    		+ "BTW CE V" + FCBetterThanWolves.fcVersionString));
    	}
    	
    	// setup the server options packet
    	
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        
        byte bHardcoreBuoy = 0;
        
        if (fcLocalEnableHardcoreBuoy) {
        	bHardcoreBuoy = 1;
        }
        
        byte bHardcorePlayerNames = (byte)fcLocalHardcorePlayerNamesLevel;
        
        try {
            dataStream.writeByte(bHardcoreBuoy);
            dataStream.writeByte(bHardcorePlayerNames);            
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }        

        // send packet 
        
        Packet250CustomPayload optionsPacket = new Packet250CustomPayload(FCBetterThanWolves.fcCustomPacketChannelBTWOptions, byteStream.toByteArray());
        
        FCUtilsWorld.SendPacketToPlayer(serverHandler, optionsPacket);
        
    	if (!MinecraftServer.getServer().isSinglePlayer()) {
        	// send server options message
    		
    		String optionsString = "\247f" // white
    			+ "Hardcore Modes: Buoy ";
        	
    		if (fcLocalEnableHardcoreBuoy) {
        		optionsString += "(on)";
    		}
    		else {
        		optionsString += "(off)";
    		}
    		
    		optionsString += " Player Names ";
    		
    		if (fcLocalHardcorePlayerNamesLevel == 1) {
        		optionsString += "(Hardcore)";
    		}
    		else if (fcLocalHardcorePlayerNamesLevel == 2) {
        		optionsString += "(Obstructed)";
    		}
    		else {
        		optionsString += "(Displayed)";
    		}
    		
    		FCUtilsWorld.SendPacketToPlayer(serverHandler, new Packet3Chat(optionsString));
    	}		
    }
	
	public static void ServerOpenCustomInterface(EntityPlayerMP player, Container container, int iContainerID)
	{
        try
        {
        	int iWindowID = player.IncrementAndGetWindowID();
        	
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            DataOutputStream dataStream = new DataOutputStream(byteStream);
        	
            dataStream.writeInt(iWindowID);
            dataStream.writeInt(iContainerID);
            
            Packet250CustomPayload packet = new Packet250CustomPayload(fcCustomPacketChannelOpenCustomInterface, byteStream.toByteArray());
        	
            FCUtilsWorld.SendPacketToPlayer(player.playerNetServerHandler, packet);
            
            player.openContainer = container;
            player.openContainer.windowId = iWindowID;
            
        	// client
//            player.openContainer.addCraftingToCrafters(player);
            // server
            player.openContainer.onCraftGuiOpened(player);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
	}
	
	public static boolean IsSinglePlayerNonLan()
	{
		// client
//		return Minecraft.getMinecraft().isSingleplayer() && !Minecraft.getMinecraft().getIntegratedServer().getPublic();
		// server
		return false;
	}
	
	private void InitCustomPackets()
	{
        Packet.addIdClassMapping(166, false, true, FCPacket166StartBlockHarvest.class);

	}
	
	public static void DebugChatOutput(String string)
	{
		if (MinecraftServer.getServer() != null)
		{
	    	MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(
	    		new Packet3Chat(string));
	    	
	    	FCAddOnHandler.LogMessage(string);
		}
	}
	
	public static void DebugWarning(String string)
	{
		if (MinecraftServer.getServer() != null)
		{
	    	MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(
	    		new Packet3Chat("\247E" // yellow text
	    			+ "WARNING: " + string));
	    	
	    	FCAddOnHandler.LogWarning(string);
		}
	}
	
	public FCAddOnUtilsWorldData createWorldData() {
		return new FCUtilsWorldData();
	}
	
	//----------- Client Side Functionality -----------//
//
//	private void PreInitClient()
//	{
//	}
//	
//	private void PostInitClient()
//	{		
//		ClientAddEntityRenderers();
//	}
//
//    public void ClientAddEntityRenderers()
//    {
//		RenderManager.AddEntityRenderer(FCEntityWaterWheel.class, new FCClientRenderWaterWheel());
//		RenderManager.AddEntityRenderer(FCEntityWindMill.class, new FCClientRenderWindMill());        
//		RenderManager.AddEntityRenderer(FCEntityMovingAnchor.class, new FCClientRenderMovingAnchor());
//		RenderManager.AddEntityRenderer(FCEntityMovingPlatform.class, new FCClientRenderMovingPlatform());
//		RenderManager.AddEntityRenderer(FCEntityBlockLiftedByPlatform.class, new FCClientRenderBlockLiftedByPlatform());
//		RenderManager.AddEntityRenderer(FCEntityBroadheadArrow.class, new FCClientRenderBroadheadArrow());
//		RenderManager.AddEntityRenderer(FCEntityInfiniteArrow.class, new FCClientRenderInfiniteArrow());
//		RenderManager.AddEntityRenderer(FCEntityUrn.class, new FCClientRenderUrn());
//		RenderManager.AddEntityRenderer(FCEntityDynamite.class, new FCClientRenderDynamite());
//		RenderManager.AddEntityRenderer(FCEntityMiningCharge.class, new FCClientRenderMiningCharge());
//		RenderManager.AddEntityRenderer(FCEntityCanvas.class, new FCClientRenderCanvas());
//		RenderManager.AddEntityRenderer(FCEntityWindMillVertical.class, new FCClientRenderEntityWindMillVertical());
//		RenderManager.AddEntityRenderer(FCEntitySpiderWeb.class, new FCClientRenderEntitySpiderWeb());
//		RenderManager.AddEntityRenderer(FCEntityWolfDire.class, new FCClientRenderEntityWolfDire(new FCClientModelWolfDire(), null));
//		RenderManager.AddEntityRenderer(FCEntitySoulSand.class, new FCClientRenderEntitySoulSand());
//		RenderManager.AddEntityRenderer(FCEntityJungleSpider.class, new FCClientRenderSpider());
//		RenderManager.AddEntityRenderer(FCEntityWitherPersistent.class, new RenderWither());
//
//		// remapped vanilla entities
//		
//		RenderManager.AddEntityRenderer(FCEntitySpider.class, new FCClientRenderSpider());
//		RenderManager.AddEntityRenderer(FCEntityCaveSpider.class, new FCClientRenderSpider());
//		RenderManager.AddEntityRenderer(FCEntityPig.class, new RenderPig(new FCClientModelPig(), new FCClientModelPig(0.5F), 0.7F));
//		RenderManager.AddEntityRenderer(FCEntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
//		RenderManager.AddEntityRenderer(FCEntityCow.class, new RenderCow(new ModelCow(), 0.7F));
//		RenderManager.AddEntityRenderer(FCEntityWolf.class, new FCClientRenderWolf(new ModelWolf(), new ModelWolf(), 0.5F));
//		RenderManager.AddEntityRenderer(FCEntityChicken.class, new RenderChicken(new FCClientModelChicken(), 0.3F));
//		RenderManager.AddEntityRenderer(FCEntityOcelot.class, new RenderOcelot(new ModelOcelot(), 0.4F));
//		RenderManager.AddEntityRenderer(FCEntityCreeper.class, new RenderCreeper());
//		RenderManager.AddEntityRenderer(FCEntityEnderman.class, new RenderEnderman());
//		RenderManager.AddEntityRenderer(FCEntitySnowman.class, new RenderSnowMan());
//		RenderManager.AddEntityRenderer(FCEntitySkeleton.class, new RenderSkeleton());
//		RenderManager.AddEntityRenderer(FCEntityWitch.class, new RenderWitch());
//		RenderManager.AddEntityRenderer(FCEntityBlaze.class, new RenderBlaze());
//		RenderManager.AddEntityRenderer(FCEntityZombie.class, new RenderZombie());
//		RenderManager.AddEntityRenderer(FCEntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
//		RenderManager.AddEntityRenderer(FCEntityMagmaCube.class, new RenderMagmaCube());
//		RenderManager.AddEntityRenderer(FCEntityGhast.class, new RenderGhast());
//		RenderManager.AddEntityRenderer(FCEntitySquid.class, new FCClientRenderSquid());
//		RenderManager.AddEntityRenderer(FCEntityVillager.class, new RenderVillager());
//		RenderManager.AddEntityRenderer(FCEntityBat.class, new RenderBat());
//		RenderManager.AddEntityRenderer(FCEntityWither.class, new RenderWither());
//		RenderManager.AddEntityRenderer(FCEntityWitherSkull.class, new RenderWitherSkull());
//		RenderManager.AddEntityRenderer(FCEntityFallingBlock.class, new RenderFallingSand());
//		RenderManager.AddEntityRenderer(FCEntityLightningBolt.class, new FCClientRenderLightningBolt());
//
//		// additional vanilla remaps to cut down overhead of looking up super classes
//		
//		RenderManager.AddEntityRenderer(FCEntityPigZombie.class, new RenderZombie());
//    }
//
//	@Override
//    public boolean ClientCustomPacketReceived(Minecraft mcInstance, Packet250CustomPayload packet)
//    {		
//        try
//        {
//    		WorldClient world = mcInstance.theWorld;
//            DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
//    		
//	        if (packet.channel.equals(fcCustomPacketChannelSpawnCustomEntity))
//	        {
//        		Entity entityToSpawn = null;
//        		
//	            int iEntityType = dataStream.readInt();
//	            int iEntityId  = dataStream.readInt();
//	            
//	            if (iEntityType == fcCustomSpawnEntityPacketTypeCanvas)
//	            {
//		            int iXPos = dataStream.readInt();
//		            int iYPos = dataStream.readInt();
//		            int iZPos = dataStream.readInt();
//		            int iDirection = dataStream.readInt();
//		            int iArtOrdinal = dataStream.readInt();
//			        
//		            entityToSpawn = new FCEntityCanvas(world, iXPos, iYPos, iZPos, iDirection, iArtOrdinal);
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeWindMill)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;
//	    	        
//	    	        boolean bIAligned =  (dataStream.readByte()) > 0;
//	    	        
//	    	        FCEntityWindMill entityWindMill = new FCEntityWindMill(world, dXPos, dYPos, dZPos, bIAligned);
//	    	        
//	    	        entityToSpawn = entityWindMill;
//	    	        
//	    	        entityWindMill.setRotationSpeedScaled(dataStream.readInt());
//	    	        
//	    	        entityWindMill.setBladeColor(0, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(1, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(2, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(3, dataStream.readByte());		    	        
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeWaterWheel)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;
//	    	        
//	    	        boolean bIAligned =  (dataStream.readByte()) > 0;
//	    	        
//	    	        FCEntityWaterWheel entityWaterWheel = new FCEntityWaterWheel(world, dXPos, dYPos, dZPos, bIAligned);
//	    	        
//	    	        entityToSpawn = entityWaterWheel;
//	    	        
//	    	        entityWaterWheel.setRotationSpeedScaled(dataStream.readInt());		    	        
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeMiningCharge)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;
//	    	        
//	    	        int iFacing = dataStream.readByte();
//	    	        int iFuse = dataStream.readByte();
//	    	        
//	    	        boolean bAttachedToBlock = (dataStream.readByte()) > 0;
//	    	        
//		            entityToSpawn = new FCEntityMiningCharge(world, dXPos, dYPos, dZPos, iFacing, iFuse, bAttachedToBlock);
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeItemBloodWoodSapling ||
//            		iEntityType == fcCustomSpawnEntityPacketTypeItemFloating)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;		    	        
//	    	        
//	    	        int iItemID = dataStream.readInt();
//	    	        int iStackSize = dataStream.readInt();
//	    	        int iItemDamage = dataStream.readInt();
//	    	        
//	    	        double dMotionX = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionY = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionZ = ((double)(dataStream.readByte())) * 128D;
//
//	    	        if (iEntityType == fcCustomSpawnEntityPacketTypeItemBloodWoodSapling)
//	    	        {
//	    	        	entityToSpawn = new FCEntityItemBloodWoodSapling(world, dXPos, dYPos, dZPos, new ItemStack(iItemID, iStackSize, iItemDamage));
//	    	        }
//	    	        else
//	    	        {
//	    	        	entityToSpawn = new FCEntityItemFloating(world, dXPos, dYPos, dZPos, new ItemStack(iItemID, iStackSize, iItemDamage));
//	    	        }
//		            
//		            entityToSpawn.motionX = dMotionX;
//		            entityToSpawn.motionY = dMotionX;
//		            entityToSpawn.motionZ = dMotionX;
//		            
//		            entityToSpawn.serverPosX = (int)(dXPos * 32D);
//		            entityToSpawn.serverPosY = (int)(dYPos * 32D);
//		            entityToSpawn.serverPosZ = (int)(dZPos * 32D);
//		            
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeDynamite)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;		    	        
//	    	        
//	    	        int iItemID = dataStream.readInt();
//	    	        int iFuse = dataStream.readInt();
//	    	        
//	    	        double dMotionX = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionY = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionZ = ((double)(dataStream.readByte())) * 128D;
//
//	    	        FCEntityDynamite dynamite = new FCEntityDynamite(world, dXPos, dYPos, dZPos, iItemID);
//	    	        
//    	        	entityToSpawn = dynamite;
//
//		            dynamite.m_iFuse = iFuse;			            
//
//		            entityToSpawn.motionX = dMotionX;
//		            entityToSpawn.motionY = dMotionX;
//		            entityToSpawn.motionZ = dMotionX;
//		            
//		            entityToSpawn.serverPosX = (int)(dXPos * 32D);
//		            entityToSpawn.serverPosY = (int)(dYPos * 32D);
//		            entityToSpawn.serverPosZ = (int)(dZPos * 32D);			            
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeUrn)
//	            {	        
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;		    	        
//	    	        
//	    	        int iItemID = dataStream.readInt();
//	    	        
//	    	        double dMotionX = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionY = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionZ = ((double)(dataStream.readByte())) * 128D;
//
//    	        	entityToSpawn = new FCEntityUrn(world, dXPos, dYPos, dZPos, iItemID);
//
//		            entityToSpawn.motionX = dMotionX;
//		            entityToSpawn.motionY = dMotionX;
//		            entityToSpawn.motionZ = dMotionX;
//		            
//		            entityToSpawn.serverPosX = (int)(dXPos * 32D);
//		            entityToSpawn.serverPosY = (int)(dYPos * 32D);
//		            entityToSpawn.serverPosZ = (int)(dZPos * 32D);			            
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeBlockLiftedByPlatform)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;		    	        
//	    	        
//	    	        int iBlockID = dataStream.readInt();
//	    	        int iMetadata = dataStream.readInt();
//	    	        
//    	        	entityToSpawn = new FCEntityBlockLiftedByPlatform(world, dXPos, dYPos, dZPos, iBlockID, iMetadata);
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeWindMillVertical)
//	            {
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;
//	    	        
//	    	        FCEntityWindMillVertical entityWindMill = new FCEntityWindMillVertical(world, dXPos, dYPos, dZPos);
//	    	        
//	    	        entityToSpawn = entityWindMill;
//	    	        
//	    	        entityWindMill.setRotationSpeedScaled(dataStream.readInt());
//	    	        
//	    	        entityWindMill.setBladeColor(0, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(1, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(2, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(3, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(4, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(5, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(6, dataStream.readByte());
//	    	        entityWindMill.setBladeColor(7, dataStream.readByte());
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeSpiderWeb)
//	            {	        
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;		    	        
//	    	        
//	    	        double dMotionX = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionY = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionZ = ((double)(dataStream.readByte())) * 128D;
//
//    	        	entityToSpawn = new FCEntitySpiderWeb(world, dXPos, dYPos, dZPos);
//
//		            entityToSpawn.motionX = dMotionX;
//		            entityToSpawn.motionY = dMotionX;
//		            entityToSpawn.motionZ = dMotionX;
//		            
//		            entityToSpawn.serverPosX = (int)(dXPos * 32D);
//		            entityToSpawn.serverPosY = (int)(dYPos * 32D);
//		            entityToSpawn.serverPosZ = (int)(dZPos * 32D);
//		            
//	                world.playSound(dXPos, dYPos, dZPos, "mob.slime.attack", 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 0.6F);
//	            }
//	            else if (iEntityType == fcCustomSpawnEntityPacketTypeSoulSand)
//	            {	        
//	    	        double dXPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dYPos = ((double)(dataStream.readInt())) / 32D;
//	    	        double dZPos = ((double)(dataStream.readInt())) / 32D;		    	        
//	    	        
//	    	        double dMotionX = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionY = ((double)(dataStream.readByte())) * 128D;
//	    	        double dMotionZ = ((double)(dataStream.readByte())) * 128D;
//
//    	        	entityToSpawn = new FCEntitySoulSand(world, dXPos, dYPos, dZPos);
//
//		            entityToSpawn.motionX = dMotionX;
//		            entityToSpawn.motionY = dMotionX;
//		            entityToSpawn.motionZ = dMotionX;
//		            
//		            entityToSpawn.serverPosX = (int)(dXPos * 32D);
//		            entityToSpawn.serverPosY = (int)(dYPos * 32D);
//		            entityToSpawn.serverPosZ = (int)(dZPos * 32D);
//		            
//	    	    	world.playSound(dXPos, dYPos, dZPos, "dig.sand", 
//		    			1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F);
//	            }
//	            
//	            if (entityToSpawn != null)
//	            {
//	            	world.addEntityToWorld(iEntityId, entityToSpawn);
//	            	
//		        	return true;
//	            }
//	        }
//	        else if (packet.channel.equals(fcCustomPacketChannelCustomEntityEvent))
//	        {
//	            int iEntityID  = dataStream.readInt();
//	            
//	            Entity entity = world.getEntityByID(iEntityID);
//	            
//	            if (entity != null)
//	            {
//	            	int iEventType = dataStream.readByte();
//	            	
//	            	if (iEventType == fcCustomEntityEventPacketTypeSetAttackTarget)
//	            	{
//	            		if (entity instanceof EntityCreature)
//	            		{
//	            			EntityCreature attackingCreature = (EntityCreature)entity;
//	            			
//		            		int iTargetEntityID = dataStream.readInt();
//		            		
//		            		if (iTargetEntityID >= 0)
//		            		{
//		        	            Entity targetEntity = world.getEntityByID(iTargetEntityID);
//		        	            
//		        	            if (targetEntity != null)
//		        	            {
//		        	            	attackingCreature.setTarget(targetEntity);
//		        	            }
//		        	            else
//		        	            {
//		        	            	attackingCreature.setTarget(null);
//		        	            }
//		            		}
//		            		else
//		            		{
//	        	            	attackingCreature.setTarget(null);
//		            		}
//	            		}
//	            	}
//	            	else if (iEventType == fcCustomEntityEventPacketTypeSquidTentacleAttack)
//	            	{
//	            		if (entity instanceof FCEntitySquid)
//	            		{
//	        	        	FCEntitySquid attackingSquid = (FCEntitySquid)entity;
//	        	        	
//		        	        double dTargetXPos = ((double)(dataStream.readInt())) / 32D;
//		        	        double dTargetYPos = ((double)(dataStream.readInt())) / 32D;
//		        	        double dTargetZPos = ((double)(dataStream.readInt())) / 32D;
//		        	        
//	        	        	attackingSquid.OnClientNotifiedOfTentacleAttack(dTargetXPos, dTargetYPos, dTargetZPos);
//	            		}	        	        
//	            	}
//	            	else if (iEventType == fcCustomEntityEventPacketTypeCowKickAttack)
//	            	{
//	            		if (entity instanceof FCEntityCow)
//	            		{
//	        	        	FCEntityCow attackingCow = (FCEntityCow)entity;
//	        	        	
//	        	        	attackingCow.OnClientNotifiedOfKickAttack();
//	            		}	        	        
//	            	}
//	            }	            
//	        }
//	        else if (packet.channel.equals(fcCustomPacketChannelVersionCheck))
//	        {
//	        	String version = dataStream.readUTF();
//
//	        	if (version.equals(getVersion()))
//	        	{
//	        		mcInstance.thePlayer.addChatMessage("\247f" // white
//			    		+ "BTW version check successful.");
//	        	}
//	        	else
//	        	{
//	        		mcInstance.thePlayer.addChatMessage("\2474" // red text
//	    	    		+ "WARNING: BTW version mismatch detected! Local Version: " + getVersion() + " Server Version: " + version);
//	        	}
//	        	
//	        	return true;
//	        }
//	        else if (packet.channel.equals(fcCustomPacketChannelBTWOptions))
//	        {
//	        	Byte bHardcoreBuoy = dataStream.readByte();
//	        	
//	        	if (bHardcoreBuoy == 0)
//	        	{
//	        		fcServerEnableHardcoreBuoy = false;
//	        	}
//	        	else
//	        	{
//	        		fcServerEnableHardcoreBuoy = true;
//	        	}
//	        	
//	        	Byte bHardcorePlayerNames = dataStream.readByte();
//	        	
//	        	fcServerHardcorePlayerNamesLevel = bHardcorePlayerNames;
//	        	
//	        	if (fcServerHardcorePlayerNamesLevel < 0 || fcServerHardcorePlayerNamesLevel > 2)
//	        	{
//	        		fcServerHardcorePlayerNamesLevel = 0;
//	        	}
//	        	
//	        	return true;
//	        }
//	        else if (packet.channel.equals(fcCustomPacketChannelOpenCustomInterface))
//	        {
//	            int iWindowID = dataStream.readInt();
//	            int iContainerID  = dataStream.readInt();
//	            
//                EntityClientPlayerMP player = mcInstance.thePlayer;
//                
//            	GuiContainer gui = ClientGetAssociatedGUI(player, iContainerID);
//            	
//            	if (gui != null)
//            	{
//                    mcInstance.displayGuiScreen(gui);
//                    
//                    player.openContainer.windowId = iWindowID;
//                    
//                    return true;
//            	}                
//	        }	        
//        }
//        catch (IOException ioexception)
//        {
//            ioexception.printStackTrace();
//        }
//        
//        return false;
//    }
//	
//    public GuiContainer ClientGetAssociatedGUI(EntityClientPlayerMP entityclientplayermp, int iContainerID)
//    {
//		if (iContainerID == fcMillStoneContainerID)
//		{
//			// the millstone no longer has a gui
//		}
//		else if (iContainerID == fcCauldronContainerID)
//		{
//			FCTileEntityCauldron cauldronEntity = new FCTileEntityCauldron();
//			
//			return new FCClientGuiCookingVessel(entityclientplayermp.inventory, cauldronEntity, iContainerID);
//		}		
//		else if (iContainerID == fcHopperContainerID)
//		{
//			FCTileEntityHopper hopperEntity = new FCTileEntityHopper();
//			
//			return new FCClientGuiHopper(entityclientplayermp.inventory, hopperEntity);
//		}
//		else if (iContainerID == fcCrucibleContainerID)
//		{
//			FCTileEntityCrucible crucibleEntity = new FCTileEntityCrucible();
//			
//			return new FCClientGuiCookingVessel(entityclientplayermp.inventory, crucibleEntity, iContainerID);
//		}
//		else if (iContainerID == fcAnvilContainerID)
//		{
//			return new FCClientGuiCraftingSoulforge(entityclientplayermp.inventory, entityclientplayermp.worldObj, 0, 0, 0);
//		}
//		else if (iContainerID == fcBlockDispenserContainerID)
//		{
//			FCTileEntityBlockDispenser dispenserEntity = new FCTileEntityBlockDispenser();
//			
//			return new FCClientGuiBlockDispenser(entityclientplayermp.inventory, dispenserEntity);
//		}
//		else if (iContainerID == fcPulleyContainerID)
//		{
//			FCTileEntityPulley pulleyEntity = new FCTileEntityPulley();
//			
//			return new FCClientGuiPulley(entityclientplayermp.inventory, pulleyEntity);
//		}
//		else if (iContainerID == fcInfernalEnchanterContainerID)
//		{
//			return new FCClientGuiInfernalEnchanter(entityclientplayermp.inventory, entityclientplayermp.worldObj, 0, 0, 0);
//		}		
//		else if (iContainerID == fcFurnaceBrickContainerID)
//		{
//			FCTileEntityFurnaceBrick brickFurnaceEntity = new FCTileEntityFurnaceBrick();
//			
//			return new GuiFurnace(entityclientplayermp.inventory, brickFurnaceEntity);
//		}
//		else if (iContainerID == fcHamperContainerID)
//		{
//			// Hamper uses a dummy inventory for the client to avoid calls on openChest() and closetChest()
//			// which would crash if not initialized.  This is the same way the vanilla chest handles it.
//			
//			InventoryBasic hamperInventory = new InventoryBasic (FCTileEntityHamper.m_sUnlocalizedName, false, 
//				FCTileEntityHamper.m_iInventorySize);
//			
//			return new FCClientGuiHamper(entityclientplayermp.inventory, hamperInventory);
//		}
//		else if (iContainerID == fcVanillaAnvilContainerID)
//		{
//			return new FCClientGuiCraftingAnvil(entityclientplayermp.inventory, entityclientplayermp.worldObj, 0, 0, 0);
//		}
//		
//        return null;
//    }
//
//	@Override
//    public boolean ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData)
//    {
//    	Random rand = world.rand;
//    	
//        double posX = (double)i + 0.5F;
//        double posY = (double)j + 0.5F;
//        double posZ = (double)k + 0.5F;
//        
//        int iParticleSetting = mcInstance.gameSettings.particleSetting;
//        
//    	switch (iFXID)
//    	{
//    		case m_iAnimalBirthingAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//                
//                for (int counter = 0; counter < 10; counter++)
//                {
//                    double bloodX = posX + rand.nextDouble();
//                    double bloodY = posY + 1.0D + rand.nextDouble();
//                    double bloodZ = posZ + rand.nextDouble();
//                    
//                    world.spawnParticle("reddust", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
//                }
//                
//                for (int counter = 0; counter < 10; counter++)
//                {
//    	            double bloodX = posX -0.5D + rand.nextDouble();
//    	            double bloodY = posY + rand.nextDouble();
//    	            double bloodZ = posZ - 0.5D + rand.nextDouble();
//                
//                    world.spawnParticle("dripLava", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
//                }
//                
//    			break;
//    			
//    		case m_iSawDamageEntityAuxFXID:
//    			
//		        world.playSound((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
//		    		"minecart.base", 
//		    		1.00F + (rand.nextFloat() * 0.1F),		// volume 
//		    		2.0F + (rand.nextFloat() * 0.1F));	// pitch		        
//    	        
//    	        int iFacing = iFXSpecificData;
//
//    	        // emit blood particles
//    	        
//	        	FCUtilsBlockPos iTargetPos = new FCUtilsBlockPos(i, j, k);
//	        	
//	        	iTargetPos.AddFacingAsOffset(iFacing);
//	        		
//	            for (int counter = 0; counter < 10; counter++)
//	            {
//	                float smokeX = (float)iTargetPos.i + rand.nextFloat();
//	                float smokeY = (float)iTargetPos.j + rand.nextFloat();
//	                float smokeZ = (float)iTargetPos.k + rand.nextFloat();
//	                
//	                world.spawnParticle("reddust", smokeX, smokeY, smokeZ, 0.0D, 0.0D, 0.0D);
//	            }
//    	        
//    			break;
//    			
//    		case m_iNetherGrothSporesAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "random.fuse", 2.0F, rand.nextFloat() * 0.4F + 1.5F);
//    	            
//	            for (int iTempCount = 0; iTempCount < 10; iTempCount++)
//	            {
//	            	world.spawnParticle("hugeexplosion", 
//	        			posX + rand.nextDouble() * 10.0D - 5D, 
//	        			posY + rand.nextDouble() * 10.0D - 5D, 
//	        			posZ + rand.nextDouble() * 10.0D - 5D, 
//	        			0.0D, 0.0D, 0.0D);
//	            }
//	            
//    			break;
//    			
//    		case m_iGhastScreamSoundAuxFXID:
//    			
//    			float fScreamPitch = rand.nextFloat() * 0.4F + 0.8F;
//    			
//    			if (iFXSpecificData == 1)
//    			{
//    				// low pitch used by Soulforged Steel
//    				
//    				fScreamPitch = rand.nextFloat() * 0.4F + 0.25F;
//    			}
//    			
//	            world.playSound(posX, posY, posZ, "mob.ghast.scream", 1.0F, fScreamPitch);
//	            
//    			break;
//    			
//    		case m_iBurpSoundAuxFXID:
//    			
//	            world.playSound(posX, posY, posZ, "random.burp", 
//            		1.0F, rand.nextFloat() * 0.4F + 0.7F);
//	            
//    			break;
//    			
//    		case m_iFireFizzSoundAuxFXID:
//    			
//    			float fFizzVolume = 0.5F;
//    			float fFizzPitch = 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F;
//    	        
//    			if (iFXSpecificData == 1)
//    			{
//    				fFizzVolume = 0.1F;
//    				fFizzPitch = 1F +  + (rand.nextFloat() - rand.nextFloat()) * 0.2F;
//    			}
//    			
//    	        world.playSound(posX, posY, posZ, "random.fizz", 
//    	        	fFizzVolume, fFizzPitch);
//    	        
//    			break;
//    			
//    		case m_iGhastMoanSoundAuxFXID:
//    			
//    	    	world.playSound(posX, posY, posZ, "mob.ghast.moan", 
//	    			0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
//    	    	
//    			break;
//    			
//    		case m_iMiningChargeExplosionAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "random.explode", 
//	        		4F, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
//    	        
//    	        world.spawnParticle("hugeexplosion", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//    			break;    			
//    			
//    		case m_iHopperXPEjectAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "liquid.lavapop", 
//	        		0.5F + rand.nextFloat() * 0.25F, 0.5F + rand.nextFloat() * 0.25F );
//    	        
//    	        posY -= 0.6;
//    	        
//    	        for (int iTempCount = 0; iTempCount < 4; iTempCount++)
//    	        {
//    		        world.spawnParticle("slime", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        }
//    	        
//    			break;
//    			
//    		case m_iItemCollectionPopSoundAuxFXID:	
//    			
//	            world.playSound(posX, posY, posZ, "random.pop", 
//            		0.25F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
//	            
//    			break;
//    			
//    		case m_iXPEjectPopSoundAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "liquid.lavapop", 
//	        		0.5F + rand.nextFloat() * 0.25F, 0.5F + rand.nextFloat() * 0.25F );
//		        
//    			break;
//    			
//    		case m_iHopperCloseSoundAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.irongolem.walk", 1.0F, 1.25F);
//    	        
//    			break;
//    			
//    		case m_iRedstonePowerClickSoundAuxFXID:
//    			
//	            world.playSound(posX, posY, posZ, "random.click", 0.75F, 2.0F);
//	            
//    			break;
//    			
//    		case m_iMechanicalDeviceExplodeAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.zombie.woodbreak", 0.5F, 0.60F + (rand.nextFloat() * 0.25F));
//    	        
//    	        world.spawnParticle("explode", posX, posY, posZ, 0D, 0D, 0D);
//    	        
//    	        for (int iTempCount = 0; iTempCount < 20; iTempCount ++)
//    	        {
//    	        	double dSmokeX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dSmokeY = posY + world.rand.nextDouble() - 0.5D;
//    	        	double dSmokeZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dSmokeVelX = (dSmokeX - posX) * 0.33D;
//    	        	double dSmokeVelY = (dSmokeY - posY) * 0.33D;
//    	        	double dSmokeVelZ = (dSmokeZ - posZ) * 0.33D;
//    	        	
//    	            world.spawnParticle("smoke", dSmokeX, dSmokeY, dSmokeZ, 
//	            		dSmokeVelX, dSmokeVelY, dSmokeVelZ);
//    	        }
//    	        
//    			break;
//    			
//    		case m_iBlockPlaceAuxFXID:
//	    		{
//	    			int iBlockID = iFXSpecificData;
//	    			Block block = Block.blocksList[iBlockID];
//	    			
//	    			if (block != null)
//	    			{    			
//		                world.playSound(posX, posY, posZ, 
//	                		block.stepSound.getStepSound(), 
//	                		(block.stepSound.getVolume() + 1.0F) / 2.0F, 
//	                		block.stepSound.getPitch() * 0.8F);
//	    			}
//	    		}
//    			
//    			break;
//    			
//    		case m_iDynamiteFuseSoundAuxFXID:	
//    			
//            	world.playSound(posX, posY, posZ, "random.fuse", 1.0F, 1.0F);
//            	
//    			break;
//    			
//    		case m_iClickLowPitchSoundAuxFXID:	
//    			
//            	world.playSound(posX, posY, posZ, "random.click", 0.10F, 0.5F);
//            	
//    			break;
//    			
//    		case m_iWolfHurtSoundAuxFXID:	
//    			
//                world.playSound(posX, posY, posZ, "mob.wolf.hurt", 
//            		0.4F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//                
//    			break;
//    			
//    		case m_iChickenHurtSoundAuxFXID:	
//    			
//                world.playSound(posX, posY, posZ, "mob.chicken.hurt", 
//                	1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//                
//    			break;
//    			
//    		case m_iBlockDispenserSmokeEffectAuxFXID:
//    			
//	            // spawn smoke particles
//	        	
//    	        int iBDFacing = iFXSpecificData;
//    	        
//    	        FCUtilsBlockPos targetDeltaPos = new FCUtilsBlockPos(0, 0, 0);
//    	        
//    	        targetDeltaPos.AddFacingAsOffset(iBDFacing);
//    	        
//    	        double dEjectXPos = (double)i + (double)targetDeltaPos.i * 0.6D + 0.5D;
//    	        double dEjectYPos = (double)j + (double)targetDeltaPos.j * 0.6D + 0.5D;
//    	        double dEjectZPos = (double)k + (double)targetDeltaPos.k * 0.6D + 0.5D;
//    	        
//	            for (int iTempCount = 0; iTempCount < 10; iTempCount++)
//	            {
//	                double d4 = rand.nextDouble() * 0.20000000000000001D + 0.01D;
//	                
//	                double dSmokeXPos = dEjectXPos + (double)targetDeltaPos.i * 0.01D + (rand.nextDouble() - 0.5D) * (double)targetDeltaPos.i * 0.5D;
//	                double dSmokeYPos = dEjectYPos + (double)targetDeltaPos.j * 0.01D + (rand.nextDouble() - 0.5D) * 0.5D;
//	                double dSmokeZPos = dEjectZPos + (double)targetDeltaPos.k * 0.01D + (rand.nextDouble() - 0.5D) * (double)targetDeltaPos.k * 0.5D;
//	                
//	                double dSmokeXVel = (double)targetDeltaPos.i * d4 + rand.nextGaussian() * 0.01D;
//	                double dSmokeYVel = (double)targetDeltaPos.j * d4 + -0.029999999999999999D + world.rand.nextGaussian() * 0.01D;
//	                double dSmokeZVel = (double)targetDeltaPos.k * d4 + rand.nextGaussian() * 0.01D;
//	                
//	                world.spawnParticle("smoke", dSmokeXPos, dSmokeYPos, dSmokeZPos, dSmokeXVel, dSmokeYVel, dSmokeZVel);
//	            }
//	            
//    			break;
//    			
//    		case m_iCompanionCubeDeathAuxFXID:
//    			
//    			FCBlockCompanionCube.SpawnHearts(world, i, j, k);
//    			
//                world.playSound((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 
//            		"mob.wolf.whine", 
//            		0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
//                
//    			break;
//    			
//    		case m_iPossessedChickenExplosionAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "random.explode", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//                
//	            world.playSound(posX, posY, posZ, "mob.chicken.hurt", 2.0F, rand.nextFloat() * 0.4F + 1.2F);
//	            
//                for (int counter = 0; counter < 10; counter++)
//                {
//                    double bloodX = posX + rand.nextDouble();
//                    double bloodY = posY + 1.0D + rand.nextDouble();
//                    double bloodZ = posZ + rand.nextDouble();
//                    
//                    world.spawnParticle("reddust", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
//                }
//                
//                for (int counter = 0; counter < 10; counter++)
//                {
//    	            double bloodX = posX -0.5D + rand.nextDouble();
//    	            double bloodY = posY + rand.nextDouble() * 0.5F;
//    	            double bloodZ = posZ - 0.5D + rand.nextDouble();
//                
//                    world.spawnParticle("dripLava", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
//                }
//                
//    	        for (int iTempCount = 0; iTempCount < 300; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 1.0D;// + world.rand.nextDouble() * 0.25D;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = 0.2D + world.rand.nextDouble() * 0.6D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	        	// 331 = redstone dust based particles
//    	        	
//    	            world.spawnParticle("iconcrack_331", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }    	        
//                
//    	        for (int iTempCount = 0; iTempCount < 25; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 1.0D;// + world.rand.nextDouble() * 0.25D;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = 0.2D + world.rand.nextDouble() * 0.6D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	        	// 352 = bone based particles
//    	        	
//    	            world.spawnParticle("iconcrack_352", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }    	        
//                
//    			break;
//    			
//    		case m_iEnderBlockCollectAuxFXID:                
//    			{
//	                int iBlockID = iFXSpecificData & 0xfff;
//	                int iMetadata = iFXSpecificData >> 12 & 0xff;
//	
//	                if (iBlockID > 0)
//	                {
//	                    Block block = Block.blocksList[iBlockID];
//	                    
//	                    world.playSound(posX, posY, posZ, block.stepSound.getBreakSound(), 
//	                    	(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
//	                    
//		                mcInstance.effectRenderer.addBlockDestroyEffects(i, j, k, iBlockID, iMetadata);
//	                }	
//    			}
//                
//    			break;
//    			
//    		case m_iEnderBlockConvertAuxFXID:
//				{
//	                int iBlockID = iFXSpecificData & 0xfff;
//	                int iMetadata = iFXSpecificData >> 12 & 0xff;
//	
//	                if (iBlockID > 0)
//	                {
//	                    Block block = Block.blocksList[iBlockID];
//	                    
//	                    world.playSound(posX, posY, posZ, block.stepSound.getBreakSound(), 
//	                    	(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
//	                    
//		                mcInstance.effectRenderer.addBlockDestroyEffects(i, j, k, iBlockID, iMetadata);
//	                }
//	                
//	    	        for (int iTempCount = 0; iTempCount < 25; iTempCount ++)
//	    	        {
//						double particleX = posX + (rand.nextDouble() - 0.5D) * 1.5D;
//						double particleY = posY + (rand.nextDouble() - 0.5D);
//						double particleZ = posZ + (rand.nextDouble() - 0.5D) * 1.5D;
//						
//			            world.spawnParticle("mobSpell", particleX, particleY, particleZ, 0D, 0D, 0D);
//	    	        }
//				}
//				
//                world.playSound(posX, posY, posZ, "mob.endermen.portal", 1.0F, 1.0F);
//                
//    			break;
//    			
//    		case m_iEnderBlockPlaceAuxFXID:                
//	    		{
//	                int iBlockID = iFXSpecificData & 0xfff;
//	                
//	    			Block block = Block.blocksList[iBlockID];
//	    			
//	    			if (block != null)
//	    			{    			
//		                world.playSound(posX, posY, posZ, 
//	                		block.stepSound.getStepSound(), 
//	                		(block.stepSound.getVolume() + 1.0F) / 2.0F, 
//	                		block.stepSound.getPitch() * 0.8F);
//	    			}
//	    			
//	                world.playSound(posX, posY, posZ, "mob.endermen.hit", 1.0F, 1.0F);	    			
//	    		}
//	    		
//    			break;
//    			
//    		case m_iEnderChangeDimensionAuxFXID:	
//    			
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//                world.playSound(posX, posY, posZ, "ambient.weather.thunder", 3.0F, rand.nextFloat() * 0.4F + 0.8F);
//                
//    			break;    			
//    			
//    		case m_iSoulUrnShatterAuxFXID:
//    			
//    	        for(int iTempCount = 0; iTempCount < 8; iTempCount++)
//    	        {
//    	            world.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        }
//
//                world.playSound(posX, posY, posZ, "random.glass", 
//            		1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
//                
//	            world.playSound(posX, posY, posZ, "mob.ghast.scream", 0.2F, rand.nextFloat() * 0.2F + 0.5F);
//	            
//                for (int iTempCount = 0; iTempCount < 100; iTempCount++)
//                {
//					double particleX = (double)posX + rand.nextDouble() * 3D - 1.5D;
//					double particleY = (double)posY + rand.nextDouble() * 3D - 1.5D;
//					double particleZ = (double)posZ + rand.nextDouble() * 3D - 1.5D;
//					
//		            world.spawnParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
//                }
//
//    			break;
//    			
//    		case m_iMelonExplodeAuxFXID:
//    		case m_iPumpkinExplodeAuxFXID:
//    			
//	        	// 360 = melon slice based particles
//	        	
//    			String particle = "iconcrack_360";
//    			
//    			if (iFXID == m_iPumpkinExplodeAuxFXID)
//    			{
//    				particle = "iconcrack_" + 31016; //HARD CODED from SCDefs pumpkin slice id!!
//    			}
//    			
//    	        for (int iTempCount = 0; iTempCount < 150; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 0.45D;;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = world.rand.nextDouble() * 0.7D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	            world.spawnParticle(particle, dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }    	        
//                
//    	        world.playSound(posX, posY, posZ, "mob.zombie.wood", 0.2F, 0.60F + (rand.nextFloat() * 0.25F));
//    	        
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//                
//    			break;
//    			
//    		case m_iMelonImpactSoundAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.zombie.wood", 0.1F, 0.40F + (rand.nextFloat() * 0.25F));
//    	        
//    			break;
//    			
//    		case m_iBlockDestroyRespectParticleSettingsAuxFXID:
//    		{
//    			
//    			// regular block destroy does not respect particle setting options.  This effect produces the same results but won't
//    			// overload on particles in automated systems if particles are turned down
//    			
//                int iBlockID = iFXSpecificData & 0xfff;
//                int iMetadata = iFXSpecificData >> 12 & 0xff;
//
//                if (iBlockID > 0)
//                {
//                    Block block = Block.blocksList[iBlockID];
//                    
//        	        world.playSound(posX, posY, posZ, block.stepSound.getBreakSound(), 
//        	        	(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
//        	        
//                    if (iParticleSetting <= 1)
//                    {                	
//	                    for (int iIOffset = 0; iIOffset < 4; ++iIOffset)
//	                    {
//	                        for (int iJOffset = 0; iJOffset < 4; ++iJOffset)
//	                        {
//	                            for (int iKOffset = 0; iKOffset < 4; ++iKOffset)
//	                            {
//	                            	if (iParticleSetting == 0 || rand.nextInt(3) == 0)
//	                            	{
//		                                double var11 = (double)i + ((double)iIOffset + 0.5D) / 4D;
//		                                double var13 = (double)j + ((double)iJOffset + 0.5D) / 4D;
//		                                double var15 = (double)k + ((double)iKOffset + 0.5D) / 4D;
//		                                
//		                                EntityDiggingFX digEffect = new EntityDiggingFX(world, var11, var13, var15, 
//		                                	var11 - (double)i - 0.5D, var13 - (double)j - 0.5D, var15 - (double)k - 0.5D, 
//		                                	block, 0, iMetadata, mcInstance.renderEngine);
//		                                
//		                                digEffect.applyRenderColor(iMetadata);
//		                                
//		                                mcInstance.effectRenderer.addEffect(digEffect);
//	                            	}
//	                            }
//	                        }
//	                    }
//	                }
//                }
//
//    			break;
//    		}
//    			
//    		case m_iCowMilkFillAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//                
//    			break;
//    			
//    		case m_iCowMilkedAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//                
//    			String milkParticle = "iconcrack_332"; // snowball
//    			
//    	        for (int iTempCount = 0; iTempCount < 50; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 0.45D;;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = world.rand.nextDouble() * 0.25D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	            world.spawnParticle(milkParticle, dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }
//    	        
//    			break;    			
//    			
//    		case m_iCowConvertToMooshroomAuxFXID:
//    			
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//    	        world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//    	        
//    	        float fHurtPitch = (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F;
//    	        
//    	        if (iFXSpecificData > 0)
//    	        {
//    	        	// child pitch
//    	        	
//    	        	fHurtPitch += 0.5F;    	        	
//    	        }    	        
//    	        
//    	        world.playSound(posX, posY, posZ, "mob.cow.hurt", 1.0F, fHurtPitch);
//    	        
//    			break;
//    			
//    		case m_iWolfHowlAuxFXID:
//    		{    	        
//    			float fSoundVolume = 0F;
//    			float fSoundPitch = 0F;
//    			
//    	        if (iFXSpecificData > 0)
//    	        {
//    	        	// dire howl
//    	        	
//    	        	fSoundVolume = 10F;
//    	        	fSoundPitch = (rand.nextFloat() - rand.nextFloat()) * 0.05F + 0.55F;    	        	
//    	        }
//    	        else
//    	        {
//    	        	// regular wolf howl
//    	        	
//    	        	fSoundVolume = 8.5F;
//    	        	fSoundPitch = (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1F;    	        	
//    	        }
//    	        
//    	        EntityPlayer localPlayer = mcInstance.thePlayer;
//    	        
//    	        if (localPlayer != null)
//    	        {
//    	        	if (localPlayer.posY < 64)
//    	        	{
//    	        		float fVolumeMultiplier = (float)(localPlayer.posY / 64D);
//    	        		
//    	        		fSoundVolume *= fVolumeMultiplier;
//    	        		
//    	        		if (fSoundVolume < 1F)
//    	        		{
//    	        			fSoundVolume = 1F;
//    	        		}
//    	        	}
//    	        }
//    	        
//    	        world.playSound(posX, posY, posZ, "mob.wolf.howl", fSoundVolume, fSoundPitch);
//    	        
//    	        break;
//    		}
//    	        
//    		case m_iWolfConvertToDireAuxFXID:
//    			
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//    	        world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//    	        
//    	        world.playSound(posX, posY, posZ, "mob.wolf.growl", 8.5F, (rand.nextFloat() - rand.nextFloat()) * 0.05F + 0.55F);
//    	        
//    			break;
//    			
//    		case m_iCreeperNeuteredAuxFXID:
//    			
//    			world.playSound(posX, posY, posZ, "mob.sheep.shear", 1.0F, 1.0F);
//                
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.1F + 0.7F);
//                
//    			String creeperJizzParticle = "iconcrack_332"; // snowball
//    			
//    	        for (int iTempCount = 0; iTempCount < 50; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 0.45D;;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = world.rand.nextDouble() * 0.25D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	        	// 360 = melon slice based particles
//    	        	
//    	            world.spawnParticle(creeperJizzParticle, dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }
//    	        
//    			break;    			
//    			
//    		case m_iPossessedPigTransformToPigmanAuxFXID:
//    			
//	            world.playSound(posX, posY, posZ, "mob.pig.death", 2.0F, rand.nextFloat() * 0.4F + 1.2F);
//	            
//	            world.playSound(posX, posY, posZ, "mob.zombiepig.zpigangry", 2.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
//	            
//    	        world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//    	        
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//    	        for (int iTempCount = 0; iTempCount < 50; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 1.0D;// + world.rand.nextDouble() * 0.25D;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = 0.2D + world.rand.nextDouble() * 0.6D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	        	// 319 = raw pork based particles
//    	        	
//    	            world.spawnParticle("iconcrack_319", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }    	        
//                
//    			break;
//    			
//    		case m_iPossessedVillagerTransformToWitchAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "ambient.weather.thunder", 3.0F, rand.nextFloat() * 0.4F + 0.8F);
//                
//    			world.playSound(posX, posY, posZ, "mob.ghast.affectionate scream", 2.0F, 0.5F + rand.nextFloat() * 0.25F);
//
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//    	        // basically a duplicate of the witch particle effect
//    	        
//                for (int iTempCount = 0; iTempCount < rand.nextInt(35) + 10; ++iTempCount)
//                {
//                    world.spawnParticle("witchMagic", posX + rand.nextGaussian() * 0.12999999523162842D, 
//                    	posY + 2.0D + rand.nextGaussian() * 0.12999999523162842D, 
//                    	posZ + rand.nextGaussian() * 0.12999999523162842D, 
//                    	0.0D, 0.0D, 0.0D);
//                }
//                
//    			break;
//    			
//    		case m_iSheepWoolRegrowAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//                
//    			break;
//    			
//    		case m_iSquidTentacleFlingAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//                
//                if (iParticleSetting <= 1)
//                {                	
//	                int iBlockID = Block.waterStill.blockID;
//	                int iMetadata = 0;
//	
//                    Block block = Block.blocksList[iBlockID];
//                    
//                    for (int iIOffset = 0; iIOffset < 2; ++iIOffset)
//                    {
//                        for (int iJOffset = 0; iJOffset < 2; ++iJOffset)
//                        {
//                            for (int iKOffset = 0; iKOffset < 2; ++iKOffset)
//                            {
//                            	if (iParticleSetting == 0 || rand.nextInt(3) == 0)
//                            	{
//	                                double var11 = (double)i + ((double)iIOffset + 0.5D) / 2D;
//	                                double var13 = (double)j + ((double)iJOffset + 0.5D) / 2D;
//	                                double var15 = (double)k + ((double)iKOffset + 0.5D) / 2D;
//	                                
//	                                EntityDiggingFX digEffect = new EntityDiggingFX(world, var11, var13, var15, 
//	                                	var11 - (double)i - 0.5D, var13 - (double)j - 0.5D, var15 - (double)k - 0.5D, 
//	                                	block, 0, iMetadata, mcInstance.renderEngine);
//	                                
//	                                digEffect.applyRenderColor(iMetadata);
//	                                
//	                                mcInstance.effectRenderer.addEffect(digEffect);
//                            	}
//                            }
//                        }
//                    }
//                }
//                    
//    			break;    			
//    			
//    		case m_iSnowGolemCreatedAuxFXID:
//                
//                for (int iTempCount = 0; iTempCount < 120; ++iTempCount)
//                {
//                    world.spawnParticle("snowshovel", (double)i + world.rand.nextDouble(), (double)(j - 2) + world.rand.nextDouble() * 2.5D, (double)k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
//                }
//                
//    	        for(int iTempCount = 0; iTempCount < 8; iTempCount++)
//    	        {
//    	            world.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        }
//
//                world.playSound(posX, posY, posZ, "random.glass", 
//            		1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
//                
//	            world.playSound(posX, posY, posZ, "mob.enderdragon.growl", 0.25F, rand.nextFloat() * 0.2F + 1.8F);
//	            
//                for (int iTempCount = 0; iTempCount < 100; iTempCount++)
//                {
//					double particleX = (double)posX + rand.nextDouble() * 3D - 1.5D;
//					double particleY = (double)posY + rand.nextDouble() * 3D - 1.5D;
//					double particleZ = (double)posZ + rand.nextDouble() * 3D - 1.5D;
//					
//		            world.spawnParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
//                }
//                
//    			break;
//    			
//    		case m_iIronGolemCreatedAuxFXID:
//                
//                for (int iTempCount = 0; iTempCount < 120; ++iTempCount)
//                {
//                    world.spawnParticle("snowballpoof", (double)i + world.rand.nextDouble(), (double)(j - 2) + world.rand.nextDouble() * 3.9D, (double)k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
//                }
//                
//    	        for(int iTempCount = 0; iTempCount < 8; iTempCount++)
//    	        {
//    	            world.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        }
//
//                world.playSound(posX, posY, posZ, "random.glass", 
//            		1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
//                
//	            world.playSound(posX, posY, posZ, "mob.irongolem.death", 1.0F, rand.nextFloat() * 0.2F + 0.5F);
//	            
//	            world.playSound(posX, posY, posZ, "mob.enderdragon.growl", 0.5F, rand.nextFloat() * 0.2F + 1.5F);
//	            
//                for (int iTempCount = 0; iTempCount < 100; iTempCount++)
//                {
//					double particleX = (double)posX + rand.nextDouble() * 3D - 1.5D;
//					double particleY = (double)posY + rand.nextDouble() * 3D - 1.5D;
//					double particleZ = (double)posZ + rand.nextDouble() * 3D - 1.5D;
//					
//		            world.spawnParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
//                }
//                
//    			break;
//    			
//    		case m_iTossTheMilkAuxFXID:
//                
//                for (int iTempCount = 0; iTempCount < 30; ++iTempCount)
//                {
//                    world.spawnParticle("snowballpoof", (double)i + world.rand.nextDouble(), (double)(j - 2) + world.rand.nextDouble() * 3.9D, (double)k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
//                }
//                
//                float fSoundPitch = 2.0F;
//                
//                if (iFXSpecificData > 0)
//                {
//                	fSoundPitch = 1.2F;
//                }
//                world.playSound(posX, posY, posZ,
//                	"mob.slime.attack", 0.5F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.1F + 0.6F);
//                
//                world.playSound(posX, posY, posZ, 
//                	"random.classic_hurt", 0.25F, fSoundPitch);
//                
//    			break;
//    			
//    		case m_iDungAppliedToWolfAuxFXID:
//    			
//                world.playSound((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 
//            		"mob.wolf.whine", 
//            		0.5F, 1.5F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
//                
//    	        for (int iTempCount = 0; iTempCount < 15; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 0.5D + world.rand.nextDouble() * 0.25D;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.25D;
//    	        	double dChunkVelY = 0.1D + world.rand.nextDouble() * 0.1D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.25D;
//    	        	
//    	        	// 319 = raw pork based particles
//    	        	
//    	            world.spawnParticle("iconcrack_491", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }    	        
//                
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 0.5F, (rand.nextFloat() - rand.nextFloat()) * 0.1F + 0.8F);
//                
//                break;
//                
//    		case m_iStumpRemovedAuxFXID:
//    			
//                world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//                
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        
//    	        for (int iTempCount = 0; iTempCount < 20; iTempCount ++)
//    	        {
//    	        	double dSmokeX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dSmokeY = posY + world.rand.nextDouble() - 0.5D;
//    	        	double dSmokeZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dSmokeVelX = (dSmokeX - posX) * 0.33D;
//    	        	double dSmokeVelY = (dSmokeY - posY) * 0.33D;
//    	        	double dSmokeVelZ = (dSmokeZ - posZ) * 0.33D;
//    	        	
//    	            world.spawnParticle("smoke", dSmokeX, dSmokeY, dSmokeZ, 
//	            		dSmokeVelX, dSmokeVelY, dSmokeVelZ);
//    	        }
//    	        
//    			break;
//                
//    		case m_iShaftRippedOffLogAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.zombie.woodbreak", 0.25F, 1.0F + (rand.nextFloat() * 0.25F));
//    	        
//    			break;
//    			
//    		case m_iStoneRippedOffAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "random.anvil_land", 0.5F, world.rand.nextFloat() * 0.25F + 1.75F);
//    	        
//    			break;
//    			
//    		case m_iGravelRippedOffStoneAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "random.anvil_land", 0.25F, world.rand.nextFloat() * 0.25F + 1.5F);
//    	        world.playSound(posX, posY, posZ, "step.gravel", 1F, world.rand.nextFloat() * 0.25F + 1F);
//    	        
//    			break;
//    			
//    		case m_iWoodBlockDestroyedAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.zombie.woodbreak", 0.25F, 1.0F + (rand.nextFloat() * 0.25F));
//    	        
//    			break;
//    			
//    		case m_iBlockDestroyedWithImproperToolAuxFXID:
//    			
//                int iBlockID = iFXSpecificData & 0xfff;
//    	        Block block = Block.blocksList[iBlockID];
//    			
//    			if (block != null)
//    			{
//                    int iMetadata = iFXSpecificData >> 12 & 0xff;
//
//    	        	// FCTODO: Would be cool to put this stuff in the material, or stepsound, or the block itself 
//    	        	if (block.blockMaterial == FCBetterThanWolves.fcMaterialPlanks ||
//    	        		block.blockMaterial == FCBetterThanWolves.fcMaterialLog)
//    	        	{
//    	    	        world.playSound(posX, posY, posZ, "mob.zombie.woodbreak", 0.25F, 1.0F + (rand.nextFloat() * 0.25F));
//    	        	}
//    	        	else if (block.blockMaterial == Material.anvil)
//    	        	{
//    	    	        world.playSound(posX, posY, posZ, "random.anvil_land", 1F, world.rand.nextFloat() * 0.25F + 0.75F);
//    	        	}
//    			}
//    			
//    			break;
//    			
//    		case m_iPossessedSquidTransformToGhastAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.slime.attack", 1F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
//    	        
//	            world.playSound(posX, posY, posZ, "mob.ghast.scream", 10F, (rand.nextFloat() - rand.nextFloat()) * 0.2F  + 1F);
//    	        
//    	        for (int iTempCount = 0; iTempCount < 10; iTempCount ++)
//    	        {
//    	        	double dExplodeX = posX + (world.rand.nextDouble() - 0.5D) * 4D;
//    	        	double dExplodeY = posY + (world.rand.nextDouble() - 0.5D) * 4D;
//    	        	double dExplodeZ = posZ + (world.rand.nextDouble() - 0.5D) * 4D;
//    	        	
//        	        world.spawnParticle("largeexplode", dExplodeX, dExplodeY, dExplodeZ, 0.0D, 0.0D, 0.0D);        	        
//    	        }    	        
//                
//    			break;
//    			
//    		case m_iMortarAppliedAuxFXID:
//    			
//    			world.playSound(posX, posY, posZ, "mob.slime.attack", 0.70F + rand.nextFloat() * 0.1F, 0.85F + rand.nextFloat() * 0.1F);
//    	        
//    			break;    			
//    			
//    		case m_iLooseBlockOnMortarAuxFXID:
//    			
//    			world.playSound(posX, posY, posZ, "mob.slime.attack", 0.15F + rand.nextFloat() * 0.1F, 0.6F + rand.nextFloat() * 0.1F);
//    	        
//    			break;
//    			
//    		case m_iLogSmoulderingFallAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "mob.zombie.woodbreak", 1.25F, 0.5F + rand.nextFloat() * 0.1F);
//    	        
//	            world.playSound(posX, posY, posZ, "mob.ghast.fireball", 1F, 0.5F + rand.nextFloat() * 0.1F);
//    	        
//    			break;
//    			
//    		case m_iLogSmoulderingExplosionAuxFXID:
//
//    			/*
//    	        world.playSound(posX, posY, posZ, "random.explode", 
//	        		1.25F, 0.7F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
//	        		*/
//    	        world.playSound(posX, posY, posZ, "mob.zombie.wood", 1.25F, 0.5F + rand.nextFloat() * 0.1F);
//    	        
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0D, 0D, 0D);
//    	        
//    	        for (int iTempCount = 0; iTempCount < 10; iTempCount ++)
//    	        {
//    	        	world.spawnParticle("fccinders", posX, posY, posZ, 0D, 0D, 0D);
//    	        }    	        
//    	        
//    			break;    			
//    			
//    		case m_iWaterEvaporateAuxFXID:
//    			
//    	        world.playSound(posX, posY, posZ, "random.fizz", 0.5F, 
//    	    		2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
//    	        
//	            for(int iTempCount = 0; iTempCount < 8; iTempCount++)
//	            {
//	                world.spawnParticle("largesmoke", posX + rand.nextDouble() - 0.5D, 
//	                	posY + rand.nextDouble() - 0.5D, posZ + rand.nextDouble() - 0.5D, 
//	                	0D, 0D, 0D);
//	            }
//    	    	
//    			break;
//    			
//    		case m_iWitherCreatedAuxFXID:
//                
//                for (int iTempCount = 0; iTempCount < 120; ++iTempCount)
//                {
//                    world.spawnParticle("snowballpoof", (double)i + world.rand.nextDouble(), 
//                    	(double)(j - 2) + world.rand.nextDouble() * 3.9D, 
//                    	(double)k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
//                }
//                
//    	        for(int iTempCount = 0; iTempCount < 8; iTempCount++)
//    	        {
//    	            world.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
//    	        }
//
//                world.playSound(posX, posY, posZ, "random.glass", 
//            		1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
//                
//	            world.playSound(posX, posY, posZ, "mob.wither.death", 1.0F, 
//	            	rand.nextFloat() * 0.2F + 0.5F);
//	            
//	            world.playSound(posX, posY, posZ, "mob.enderdragon.growl", 0.5F, 
//	            	rand.nextFloat() * 0.2F + 1.5F);
//	            
//                for (int iTempCount = 0; iTempCount < 100; iTempCount++)
//                {
//					double particleX = (double)posX + rand.nextDouble() * 3D - 1.5D;
//					double particleY = (double)posY + rand.nextDouble() * 3D - 1.5D;
//					double particleZ = (double)posZ + rand.nextDouble() * 3D - 1.5D;
//					
//		            world.spawnParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
//                }
//                
//    			break;
//    			
//    		case m_iLightningStrikeAuxFXID:
// 
//    	        world.spawnParticle("largeexplode", posX, posY, posZ, 0D, 0D, 0D);
//    			
//	            world.playSound(posX, posY, posZ, "random.explode", 
//	            	4F, 0.5F + rand.nextFloat() * 0.2F);
//	            
//	            world.playSound(posX, posY, posZ, "ambient.weather.thunder", 
//	            	10000F, 0.8F + rand.nextFloat() * 0.2F);	            
//	            
//    			break;
//    			
//    		case m_iFlamingNetherrackFallAuxFXID:
//    			
//	            world.playSound(posX, posY, posZ, "mob.ghast.fireball", 0.1F, 
//	            	0.75F + rand.nextFloat() * 0.1F);
//	            
//	            break;
//	            
//    		case m_iCactusExplodeAuxFXID:
//    			
//    	        for (int iTempCount = 0; iTempCount < 150; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY - 0.45D;;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	double dChunkVelY = world.rand.nextDouble() * 0.7D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;
//    	        	
//    	        	// 338 = reed based particles
//    	        	
//    	            world.spawnParticle("iconcrack_338", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }
//    	        
//    			break;
//    			
//    		case m_iAnimalEatAuxFXID:
//    			
//    			world.playSound(posX, posY, posZ, "random.eat", 0.75F, 
//    		    	(rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
//    		    
//    	        for (int iTempCount = 0; iTempCount < 25; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.25D;
//    	        	double dChunkVelY = world.rand.nextDouble() * 0.35D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.25D;
//    	        	
//    	        	// 361 = pumpkin seeds
//    	        	
//    	            world.spawnParticle("iconcrack_361", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }
//    	        
//    		    break;
//    		    
//    		case m_iWolfEatAuxFXID:
//    			
//	            world.playSound(posX, posY, posZ, "random.burp", 
//            		1.0F, rand.nextFloat() * 0.4F + 0.7F);
//    		    
//    	        for (int iTempCount = 0; iTempCount < 25; iTempCount ++)
//    	        {
//    	        	double dChunkX = posX + world.rand.nextDouble() - 0.5D;
//    	        	double dChunkY = posY;
//    	        	double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;
//    	        	
//    	        	double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.25D;
//    	        	double dChunkVelY = world.rand.nextDouble() * 0.35D;
//    	        	double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.25D;
//    	        	
//    	        	// 281 = bowl based particles
//    	        	
//    	            world.spawnParticle("iconcrack_281", dChunkX, dChunkY, dChunkZ, 
//    	            	dChunkVelX, dChunkVelY, dChunkVelZ);
//    	        }
//    	        
//    		    break;
//    		    
//    		case m_iEatFailAuxFXID:
//    			
//	            world.playSound(posX, posY, posZ, "random.burp", 
//            		0.25F, rand.nextFloat() * 0.3F + 1F);
//	            
//    			break;
//    			
//			default:
//				
//				return false;
//    	}
//    	
//    	return true;
//    }
}