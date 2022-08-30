package net.minecraft.src;

import com.prupe.mcpatcher.cit.CITUtils;
import java.util.List;
import java.util.Random;

public class Item
{
    private CreativeTabs tabToDisplayOn = null;

    /** The RNG used by the Item subclasses. */
    protected static Random itemRand = new Random();

    /** A 32000 elements Item array. */
    public static Item[] itemsList = new Item[32000];
    
    public static final int m_iFilterable_NoProperties = 0;
    public static final int m_iFilterable_SolidBlock = 1;
    public static final int m_iFilterable_Small = 2;
    public static final int m_iFilterable_Narrow = 4;
    public static final int m_iFilterable_Fine = 8;
    public static final int m_iFilterable_Thin = 16;
    
    public static Item shovelIron = ( new FCItemShovel( 0, EnumToolMaterial.IRON ) ).setUnlocalizedName( "shovelIron" );
    public static Item pickaxeIron = ( new FCItemPickaxe( 1, EnumToolMaterial.IRON ) ).setUnlocalizedName( "pickaxeIron" );
    public static Item axeIron = ( new FCItemAxe( 2, EnumToolMaterial.IRON ) ).setUnlocalizedName( "hatchetIron" );
    public static Item flintAndSteel = ( new FCItemFlintAndSteel( 3 ) ).setUnlocalizedName( "flintAndSteel" );
    public static Item appleRed = ( new ItemFood( 4, 1, 0F, false ) ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "apple" );
    public static ItemBow bow = new FCItemBow( 5 );
    public static Item arrow = new FCItemArrow( 6 );
    public static Item coal = ( new ItemCoal( 7 ) ).SetIncineratedInCrucible().SetFurnaceBurnTime( FCEnumFurnaceBurnTime.COAL ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "coal" );
    public static Item diamond = ( new Item( 8 ) ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "diamond" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item ingotIron = (new Item(9)).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials);
    public static Item ingotGold = (new Item(10)).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials);
    public static Item swordIron = ( new FCItemSword( 11, EnumToolMaterial.IRON ) ).setUnlocalizedName( "swordIron" );
    public static Item swordWood = ( new FCItemSword( 12, EnumToolMaterial.WOOD ) ).setUnlocalizedName( "swordWood" );
    public static Item shovelWood = ( new FCItemShovel( 13, EnumToolMaterial.WOOD ) ).SetDamageVsEntity( 2 ).setUnlocalizedName( "shovelWood" );
    public static Item pickaxeWood = ( new FCItemPickaxe( 14, EnumToolMaterial.WOOD, 1 ) ).setUnlocalizedName( "pickaxeWood" );
    public static Item axeWood = ( new FCItemAxe( 15, EnumToolMaterial.WOOD ) ).setUnlocalizedName( "hatchetWood" );
    public static Item swordStone = ( new FCItemSword( 16, EnumToolMaterial.STONE ) ).setUnlocalizedName( "swordStone" );	    
    public static Item shovelStone = new FCItemShovelStone( 17 );
    public static Item pickaxeStone = ( new FCItemPickaxe( 18, EnumToolMaterial.STONE ) ).setUnlocalizedName( "pickaxeStone" );
    public static Item axeStone = ( new FCItemAxe( 19, EnumToolMaterial.STONE ) ).setUnlocalizedName( "hatchetStone" );
    public static Item swordDiamond = ( new FCItemSword( 20, EnumToolMaterial.EMERALD ) ).setUnlocalizedName( "swordDiamond" );	    
    public static Item shovelDiamond = ( new FCItemShovel( 21, EnumToolMaterial.EMERALD ) ).setUnlocalizedName( "shovelDiamond" );
    public static Item pickaxeDiamond = ( new FCItemPickaxe( 22, EnumToolMaterial.EMERALD ) ).setUnlocalizedName( "pickaxeDiamond" );	    
    public static Item axeDiamond = ( new FCItemAxe( 23, EnumToolMaterial.EMERALD ) ).setUnlocalizedName( "hatchetDiamond" );
    public static Item stick = new FCItemShaft( 24 );    
    public static Item bowlEmpty = ( new Item( 25 ) ).SetBuoyant().SetIncineratedInCrucible().setUnlocalizedName( "bowl" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item bowlSoup = ( new FCItemMushroomSoup( 26, 3 ) ).setUnlocalizedName( "mushroomStew" );
    public static Item swordGold = ( new FCItemSword( 27, EnumToolMaterial.GOLD ) ).setUnlocalizedName( "swordGold" );
    public static Item shovelGold = ( new FCItemShovel( 28, EnumToolMaterial.GOLD ) ).setUnlocalizedName( "shovelGold" );
    public static Item pickaxeGold = ( new FCItemPickaxe( 29, EnumToolMaterial.GOLD ) ).setUnlocalizedName( "pickaxeGold" );	    
    public static Item axeGold = ( new FCItemAxe( 30, EnumToolMaterial.GOLD ) ).setUnlocalizedName( "hatchetGold" );
    public static Item silk = ( new Item( 31 ) ).SetBuoyant().SetBellowsBlowDistance( 2 ).SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Small | m_iFilterable_Thin ).setUnlocalizedName( "string" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item feather = ( new Item( 32 ) ).SetBuoyant().SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING ).SetIncineratedInCrucible().SetBellowsBlowDistance( 3 ).SetFilterableProperties( m_iFilterable_Small | m_iFilterable_Thin ).setUnlocalizedName( "feather" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item gunpowder = ( new Item( 33 ) ).SetBellowsBlowDistance( 3 ).SetFilterableProperties( m_iFilterable_Fine ).setUnlocalizedName( "sulphur" ).setPotionEffect( PotionHelper.gunpowderEffect ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item hoeWood = ( new FCItemHoe( 34, EnumToolMaterial.WOOD ) ).setUnlocalizedName( "hoeWood" );
    public static Item hoeStone = ( new FCItemHoe( 35, EnumToolMaterial.STONE ) ).setUnlocalizedName( "hoeStone" );
    public static Item hoeIron = ( new FCItemHoe( 36, EnumToolMaterial.IRON ) ).setUnlocalizedName( "hoeIron" );
    public static Item hoeDiamond = ( new FCItemHoe( 37, EnumToolMaterial.EMERALD ) ).setUnlocalizedName( "hoeDiamond" );
    public static Item hoeGold = ( new FCItemHoe( 38, EnumToolMaterial.GOLD ) ).setUnlocalizedName( "hoeGold" );
    public static Item seeds = ( new FCItemSeeds( 39, Block.crops.blockID ) ).SetAsBasicChickenFood().setUnlocalizedName( "seeds" ).setCreativeTab( null );
    public static Item wheat = new FCItemWheatLegacy( 40 );
    public static Item bread = ( new ItemFood( 41, 3, 0.25F, false ) ).setUnlocalizedName( "bread" );
    public static ItemArmor helmetLeather = (ItemArmor)( new FCItemArmorLeather( 42, 0 ) ).setUnlocalizedName( "helmetCloth" );
    public static ItemArmor plateLeather = (ItemArmor)( new FCItemArmorLeather( 43, 1 ) ).setUnlocalizedName( "chestplateCloth" );
    public static ItemArmor legsLeather = (ItemArmor)( new FCItemArmorLeather( 44, 2 ) ).setUnlocalizedName( "leggingsCloth" );
    public static ItemArmor bootsLeather = (ItemArmor)( new FCItemArmorLeather( 45, 3 ) ).setUnlocalizedName( "bootsCloth" );    
    public static ItemArmor helmetChain = (ItemArmor)( new FCItemArmorChain( 46, 0, 3 ) ).setUnlocalizedName( "helmetChain" );
    public static ItemArmor plateChain = (ItemArmor)( new FCItemArmorChain( 47, 1, 4 ) ).setUnlocalizedName( "chestplateChain" );
    public static ItemArmor legsChain = (ItemArmor)( new FCItemArmorChain( 48, 2, 4 ) ).setUnlocalizedName( "leggingsChain" );
    public static ItemArmor bootsChain = (ItemArmor)( new FCItemArmorChain( 49, 3, 2 ) ).setUnlocalizedName( "bootsChain" );
    public static ItemArmor helmetIron = (ItemArmor)( new FCItemArmorIron( 50, 0, 5 ) ).setUnlocalizedName( "helmetIron" );
    public static ItemArmor plateIron = (ItemArmor)( new FCItemArmorIron( 51, 1, 8 ) ).setUnlocalizedName( "chestplateIron" );
    public static ItemArmor legsIron = (ItemArmor)( new FCItemArmorIron( 52, 2, 7 ) ).setUnlocalizedName( "leggingsIron" );
    public static ItemArmor bootsIron = (ItemArmor)( new FCItemArmorIron( 53, 3, 4 ) ).setUnlocalizedName( "bootsIron" );
    public static ItemArmor helmetDiamond = (ItemArmor)( new FCItemArmorDiamond( 54, 0, 5 ) ).setUnlocalizedName( "helmetDiamond" );
    public static ItemArmor plateDiamond = (ItemArmor)( new FCItemArmorDiamond( 55, 1, 8 ) ).setUnlocalizedName( "chestplateDiamond" );
    public static ItemArmor legsDiamond = (ItemArmor)( new FCItemArmorDiamond( 56, 2, 7 ) ).setUnlocalizedName( "leggingsDiamond" );
    public static ItemArmor bootsDiamond = (ItemArmor)( new FCItemArmorDiamond( 57, 3, 4 ) ).setUnlocalizedName( "bootsDiamond" );
    public static ItemArmor helmetGold = (ItemArmor)( new FCItemArmorGold( 58, 0, 5 ) ).setUnlocalizedName( "helmetGold" );
    public static ItemArmor plateGold = (ItemArmor)( new FCItemArmorGold( 59, 1, 8 ) ).setUnlocalizedName( "chestplateGold" );
    public static ItemArmor legsGold = (ItemArmor)( new FCItemArmorGold( 60, 2, 7 ) ).setUnlocalizedName( "leggingsGold" );
    public static ItemArmor bootsGold = (ItemArmor)( new FCItemArmorGold( 61, 3, 4 ) ).setUnlocalizedName( "bootsGold" );
    public static Item flint = new FCItemFlint( 62 );
    public static Item porkRaw = ( new FCItemFood( 63, FCItemFood.m_iPorkChopRawHungerHealed, FCItemFood.m_fPorkChopSaturationModifier, true, "porkchopRaw", true ) ).SetStandardFoodPoisoningEffect();    
    public static Item porkCooked = ( new ItemFood( 64, FCItemFood.m_iPorkChopCookedHungerHealed, FCItemFood.m_fPorkChopSaturationModifier, true ) ).setUnlocalizedName( "porkchopCooked" );    
    public static Item painting = ( new ItemHangingEntity( 65, EntityPainting.class ) ).SetBuoyant().SetIncineratedInCrucible().setUnlocalizedName( "painting" );
    public static Item appleGold = ( new ItemAppleGold( 66, 1, 0F, false ) ).setAlwaysEdible().setPotionEffect( Potion.regeneration.id, 5, 0, 1F ).SetNonBuoyant().SetNotIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "appleGold" );
    public static Item sign = new FCItemSign( 67 );
    public static Item doorWood = new FCItemDoorWood( 68 );	    
    public static Item bucketEmpty = new FCItemBucketEmpty( 69 );
    public static Item bucketWater = new FCItemBucketWater( 70 );
    public static Item bucketLava = new FCItemBucketLava( 71 );
    public static Item minecartEmpty = ( new FCItemMinecart( 72, 0 ) ).setUnlocalizedName( "minecart" );
    public static Item saddle = ( new ItemSaddle( 73 ) ).SetBuoyant().SetIncineratedInCrucible().setUnlocalizedName( "saddle" );
    public static Item doorIron = (new ItemDoor(74, Material.iron)).setUnlocalizedName("doorIron");
    public static Item redstone = new FCItemRedstone( 75 );
    public static Item snowball = new FCItemSnowball( 76 );
    public static Item boat = new FCItemBoat( 77 );
    public static Item leather = ( new Item( 78 ) ).SetBuoyant().SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Thin ).setUnlocalizedName( "leather" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item bucketMilk = new FCItemBucketMilk( 79 );    	
    public static Item brick = new FCItemBrick( 80 );
    public static Item clay = new FCItemClay( 81 );
    public static Item reed = ( new ItemReed( 82, Block.reed ) ).SetBuoyant().SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING ).SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Narrow ).setUnlocalizedName( "reeds" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item paper = ( new Item( 83 ) ).SetBuoyant().SetBellowsBlowDistance( 3 ).SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING ).SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Small | m_iFilterable_Thin ).setUnlocalizedName( "paper" ).setCreativeTab( CreativeTabs.tabMisc );
    public static Item book = new FCItemBook( 84 );
    public static Item slimeBall = new FCItemSlimeball( 85 );
    public static Item minecartCrate = ( new FCItemMinecart( 86, 1 ) ).setUnlocalizedName( "minecartChest" );
    public static Item minecartPowered = ( new FCItemMinecart( 87, 2 ) ).setUnlocalizedName( "minecartFurnace" );
    public static Item egg = new FCItemEgg( 88 );
    public static Item compass = (new Item(89)).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools);
    public static ItemFishingRod fishingRod = new FCItemFishingRod( 90 );
    public static Item pocketSundial = (new Item(91)).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools);
    public static Item lightStoneDust = ( new Item( 92 ) ).SetBellowsBlowDistance( 3 ).SetFilterableProperties( m_iFilterable_Fine ).setUnlocalizedName( "yellowDust" ).setCreativeTab( CreativeTabs.tabMaterials );    
    public static Item fishRaw = ( new FCItemFood( 93, FCItemFood.m_iFishRawHungerHealed, FCItemFood.m_fFishSaturationModifier, false, "fishRaw" ) ).SetStandardFoodPoisoningEffect();
    public static Item fishCooked = ( new ItemFood( 94, FCItemFood.m_iFishCookedHungerHealed, FCItemFood.m_fFishSaturationModifier, false ) ).setUnlocalizedName( "fishCooked" );
    public static Item dyePowder = new FCItemDye( 95 );
    public static Item bone = new FCItemBone( 96 );
    public static Item sugar = ( new Item( 97 ) ).SetBuoyant().SetBellowsBlowDistance( 3 ).SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Fine ).setUnlocalizedName( "sugar" ).setCreativeTab( CreativeTabs.tabMaterials );	    
    public static Item cake = ( new ItemReed( 98, Block.cake ) ).SetBuoyant().SetIncineratedInCrucible().setMaxStackSize( 1 ).setUnlocalizedName( "cake" ).setCreativeTab( CreativeTabs.tabFood );
    public static Item bed = ( new ItemBed( 99 ) ).SetBuoyant().SetIncineratedInCrucible().setMaxStackSize( 1 ).setUnlocalizedName( "bed" );
    public static Item redstoneRepeater = new FCItemRedstoneRepeater( 100 );
    public static Item cookie = ( new ItemFood( 101, 1, 1F, false ) ).setAlwaysEdible().SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "cookie" );
    public static ItemMap map = new FCItemMap( 102 );
    public static ItemShears shears = (ItemShears)( new FCItemShears( 103 ) ).setUnlocalizedName( "shears" );
    public static Item melon = new FCItemFoodHighRes( 104, 2, 0F, false, "melon" );
    public static Item pumpkinSeeds = ( new FCItemSeedFood( 105, 1, 0F, Block.pumpkinStem.blockID ) ).SetAsBasicChickenFood().SetBellowsBlowDistance( 2 ).SetFilterableProperties( m_iFilterable_Fine ).setUnlocalizedName( "seeds_pumpkin" );
    public static Item melonSeeds = ( new FCItemSeeds( 106, Block.melonStem.blockID ) ).SetAsBasicChickenFood().setUnlocalizedName( "seeds_melon" );
    public static Item beefRaw = ( new FCItemFood( 107, FCItemFood.m_iBeefRawHungerHealed, FCItemFood.m_fBeefSaturationModifier, true, "beefRaw", true ) ).SetStandardFoodPoisoningEffect();    
    public static Item beefCooked = ( new ItemFood( 108, FCItemFood.m_iBeefCookedHungerHealed, FCItemFood.m_fBeefSaturationModifier, true ) ).setUnlocalizedName("beefCooked");
    public static Item chickenRaw = ( new FCItemFood( 109, FCItemFood.m_iChickenRawHungerHealed, FCItemFood.m_fChickenSaturationModifier, true, "chickenRaw" ) ).SetStandardFoodPoisoningEffect();    
    public static Item chickenCooked = (new ItemFood( 110, FCItemFood.m_iChickenCookedHungerHealed, FCItemFood.m_fChickenSaturationModifier, true ) ).setUnlocalizedName( "chickenCooked" );    
    public static Item rottenFlesh = new FCItemRottenFlesh( 111 );
    public static Item enderPearl = ( new ItemEnderPearl( 112 ) ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "enderPearl" );
    public static Item blazeRod = ( new Item( 113 ) ).SetFurnaceBurnTime( FCEnumFurnaceBurnTime.BLAZE_ROD ).SetFilterableProperties( m_iFilterable_Narrow ).setUnlocalizedName( "blazeRod" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item ghastTear = ( new Item( 114 ) ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "ghastTear" ).setPotionEffect( PotionHelper.ghastTearEffect ).setCreativeTab( CreativeTabs.tabBrewing );
    public static Item goldNugget = ( new Item( 115 ) ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "goldNugget" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item netherStalkSeeds = ( new FCItemSeeds( 116, Block.netherStalk.blockID ) ).SetBellowsBlowDistance( 1 ).setUnlocalizedName( "netherStalkSeeds" ).setPotionEffect( "+4" );
    public static ItemPotion potion = new FCItemPotion( 117 );
    public static Item glassBottle = ( new FCItemGlassBottle( 118 ) ).SetBuoyant().setUnlocalizedName( "glassBottle" );
    public static Item spiderEye = ( (new ItemFood( 119, 2, 0.8F, false ) ).setPotionEffect( Potion.poison.id, 5, 0, 1F ) ).SetNeutralBuoyant().SetFilterableProperties( m_iFilterable_Small ).setPotionEffect( PotionHelper.goldenCarrotEffect ).setUnlocalizedName( "spiderEye" );
    public static Item fermentedSpiderEye = ( new Item( 120 ) ).SetNeutralBuoyant().SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "fermentedSpiderEye" ).setPotionEffect( PotionHelper.fermentedSpiderEyeEffect ).setCreativeTab( CreativeTabs.tabBrewing );
    public static Item blazePowder = ( new Item( 121 ) ).SetBellowsBlowDistance( 3 ).SetFilterableProperties( m_iFilterable_Fine ).setUnlocalizedName( "blazePowder" ).setPotionEffect( PotionHelper.blazePowderEffect ).setCreativeTab( CreativeTabs.tabBrewing );
    public static Item magmaCream = ( new Item( 122 ) ).SetNeutralBuoyant().setUnlocalizedName( "magmaCream" ).setPotionEffect( PotionHelper.magmaCreamEffect ).setCreativeTab( CreativeTabs.tabBrewing );
    public static Item brewingStand = (new ItemReed(123, Block.brewingStand)).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing);
    public static Item cauldron = (new ItemReed(124, Block.cauldron)).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing);
    public static Item eyeOfEnder = ( new ItemEnderEye( 125 ) ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "eyeOfEnder" );
    public static Item speckledMelon = ( new Item( 126 ) ).setUnlocalizedName( "speckledMelon" ).setCreativeTab( CreativeTabs.tabFood );
    public static Item monsterPlacer = (new ItemMonsterPlacer(127)).setUnlocalizedName("monsterPlacer");

    /**
     * Bottle o' Enchanting. Drops between 1 and 3 experience orbs when thrown.
     */
    public static Item expBottle = (new ItemExpBottle(128)).setUnlocalizedName("expBottle");
    public static Item fireballCharge = new FCItemFireCharge( 129 );
    public static Item writableBook = ( new ItemWritableBook( 130 ) ).SetBuoyant().SetIncineratedInCrucible().setUnlocalizedName( "writingBook" ).setCreativeTab( CreativeTabs.tabMisc );
    public static Item writtenBook = ( new ItemEditableBook( 131 ) ).SetBuoyant().SetIncineratedInCrucible().setUnlocalizedName( "writtenBook" );
    public static Item emerald = (new Item(132)).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials);
    public static Item itemFrame = ( new ItemHangingEntity( 133, EntityItemFrame.class ) ).SetBuoyant().SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_SolidBlock ).setUnlocalizedName( "frame" );
    public static Item flowerPot = ( new ItemReed( 134, Block.flowerPot ) ).SetBuoyant().SetFilterableProperties( m_iFilterable_SolidBlock ).setUnlocalizedName( "flowerPot" ).setCreativeTab( CreativeTabs.tabDecorations );
    public static Item carrot = ( new FCItemSeedFood( 135, 3, 0F, Block.carrot.blockID ) ).SetFilterableProperties( m_iFilterable_Small ).SetAsBasicPigFood().setUnlocalizedName( "carrots" );
    public static Item potato = ( new FCItemSeedFood( 136, 3, 0F, Block.potato.blockID ) ).SetFilterableProperties( m_iFilterable_Small ).SetAsBasicPigFood().setUnlocalizedName( "potato" );
    public static Item bakedPotato = ( new ItemFood( 137, 2, 0F, false ) ).SetFilterableProperties( m_iFilterable_Small ).SetAsBasicPigFood().setUnlocalizedName( "potatoBaked" );	    
    public static Item poisonousPotato = ( new ItemFood( 138, 1, 0F, false ) ).setPotionEffect( Potion.poison.id, 5, 0, 0.6F ).SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "potatoPoisonous" );
    public static ItemEmptyMap emptyMap = new FCItemEmptyMap( 139 );
    public static Item goldenCarrot = ( new ItemFood( 140, 1, 0F, false ) ).SetNonBuoyant().SetFilterableProperties( m_iFilterable_Small ).setUnlocalizedName( "carrotGolden" );
    public static Item skull = ( new ItemSkull( 141 ) ).SetBuoyant().SetIncineratedInCrucible().SetFilterableProperties( m_iFilterable_SolidBlock ).setUnlocalizedName( "skull" );
    public static Item carrotOnAStick = new FCItemCarrotOnAStick( 142 );
    public static Item netherStar = new FCItemNetherStar( 143 );
    public static Item pumpkinPie = ( new ItemFood( 144, 2, 2.5F, false ) ).setAlwaysEdible().setUnlocalizedName( "pumpkinPie" ).setCreativeTab( CreativeTabs.tabFood );
    public static Item firework = (new ItemFirework(145)).setUnlocalizedName("fireworks");
    public static Item fireworkCharge = (new ItemFireworkCharge(146)).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc);
    public static ItemEnchantedBook enchantedBook = (ItemEnchantedBook)( new FCItemEnchantedBook( 147 ) ).setMaxStackSize( 1 ).setUnlocalizedName( "enchantedBook" );
    public static Item comparator = (new FCItemPlacesAsBlock(148, Block.redstoneComparatorIdle.blockID)).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone);
    public static Item netherrackBrick = ( new Item(149 ) ).setUnlocalizedName( "netherbrick" );
    public static Item netherQuartz = ( new FCItemNetherQuartz( 150 ) ).setUnlocalizedName( "netherquartz" ).setCreativeTab( CreativeTabs.tabMaterials );
    public static Item minecartTnt = ( new FCItemStub( 151 ) ).setUnlocalizedName( "minecartTnt" );
    public static Item minecartHopper = ( new FCItemStub( 152 ) ).setUnlocalizedName( "minecartHopper" );
    // Added aliases to avoid annoying naming differences between client and server
    public static Item tntMinecart = minecartTnt;
    public static Item hopperMinecart = minecartHopper;
    
    public static Item record13 = (new ItemRecord(2000, "13")).setUnlocalizedName("record");
    public static Item recordCat = (new ItemRecord(2001, "cat")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordBlocks = (new ItemRecord(2002, "blocks")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordChirp = (new ItemRecord(2003, "chirp")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordFar = (new ItemRecord(2004, "far")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordMall = (new ItemRecord(2005, "mall")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordMellohi = (new ItemRecord(2006, "mellohi")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordStal = (new ItemRecord(2007, "stal")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordStrad = (new ItemRecord(2008, "strad")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordWard = (new ItemRecord(2009, "ward")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item record11 = (new ItemRecord(2010, "11")).setUnlocalizedName("record").setCreativeTab( null );
    public static Item recordWait = (new ItemRecord(2011, "wait")).setUnlocalizedName("record").setCreativeTab((CreativeTabs)null);

    /** The ID of this item. */
    public final int itemID;

    /** Maximum size of the stack. */
    protected int maxStackSize = 64;

    /** Maximum damage an item can handle. */
    private int maxDamage = 0;

    /** If true, render the object in full 3D, like weapons and tools. */
    protected boolean bFull3D = false;

    /**
     * Some items (like dyes) have multiple subtypes on same item, this is field define this behavior
     */
    protected boolean hasSubtypes = false;
    private Item containerItem = null;
    private String potionEffect = null;

    /** The unlocalized name of this item. */
    private String unlocalizedName;

    /** Icon index in the icons table. */
    protected Icon itemIcon;
    
    public static final boolean[] itemReplaced = new boolean[32000];
    private Class entityClass = EntityItem.class;

    protected Item(int par1)
    {
        this.itemID = 256 + par1;

        // FCMOD: Code added
        if ( !m_bSuppressConflictWarnings )
    	// END FCMOD
        if (itemsList[256 + par1] != null)
        {
            System.out.println("CONFLICT @ " + par1);
        }

        itemsList[256 + par1] = this;
    }

    public Item setMaxStackSize(int par1)
    {
        this.maxStackSize = par1;
        return this;
    }

    /**
     * Returns 0 for /terrain.png, 1 for /gui/items.png
     */
    public int getSpriteNumber()
    {
        return 1;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {
        return this.itemIcon;
    }

    /**
     * Returns the icon index of the stack given as argument.
     */
    public final Icon getIconIndex(ItemStack par1ItemStack)
    {
    	return CITUtils.getIcon(this.getIconFromDamage(par1ItemStack.getItemDamage()), par1ItemStack, 0);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    // FCMOD: Removed and replaced later
    /*
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 1.0F;
    }
    */
    // END FCMOD

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * Returns the maximum size of the stack for a specific item. *Isn't this more a Set than a Get?*
     */
    public int getItemStackLimit()
    {
        return this.maxStackSize;
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return 0;
    }

    public boolean getHasSubtypes()
    {
        return this.hasSubtypes;
    }

    protected Item setHasSubtypes(boolean par1)
    {
        this.hasSubtypes = par1;
        return this;
    }

    /**
     * Returns the maximum damage an item can take.
     */
    public int getMaxDamage()
    {
        return this.maxDamage;
    }

    /**
     * set max damage of an Item
     */
    protected Item setMaxDamage(int par1)
    {
        this.maxDamage = par1;
        return this;
    }

    public boolean isDamageable()
    {
        return this.maxDamage > 0 && !this.hasSubtypes;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        return false;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        return 1;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    // FCMOD: Removed and replaced
    /*
    public boolean canHarvestBlock(Block par1Block)
    {
        return false;
    }
    */
    // END FCMOD    

    /**
     * Called when a player right clicks an entity with an item.
     */
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
        return false;
    }

    /**
     * Sets bFull3D to True and return the object.
     */
    public Item setFull3D()
    {
        this.bFull3D = true;
        return this;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return this.bFull3D;
    }

    /**
     * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
     * hands.
     */
    public boolean shouldRotateAroundWhenRendering()
    {
        return false;
    }

    /**
     * Sets the unlocalized name of this item to the string passed as the parameter, prefixed by "item."
     */
    public Item setUnlocalizedName(String par1Str)
    {
        this.unlocalizedName = par1Str;
        return this;
    }

    /**
     * Gets the localized name of the given item stack.
     */
    public String getLocalizedName(ItemStack par1ItemStack)
    {
        String var2 = this.getUnlocalizedName(par1ItemStack);
        return var2 == null ? "" : StatCollector.translateToLocal(var2);
    }

    /**
     * Returns the unlocalized name of this item.
     */
    public String getUnlocalizedName()
    {
        return "item." + this.unlocalizedName;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "item." + this.unlocalizedName;
    }

    public Item setContainerItem(Item par1Item)
    {
        this.containerItem = par1Item;
        return this;
    }

    /**
     * If this returns true, after a recipe involving this item is crafted the container item will be added to the
     * player's inventory instead of remaining in the crafting grid.
     */
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    public boolean getShareTag()
    {
        return true;
    }

    public Item getContainerItem()
    {
        return this.containerItem;
    }

    /**
     * True if this Item has a container item (a.k.a. crafting result)
     */
    public boolean hasContainerItem()
    {
        return this.containerItem != null;
    }

    public String getStatName()
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    public String func_77653_i(ItemStack par1ItemStack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName(par1ItemStack) + ".name");
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return 16777215;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    // FCMOD: Changed
    //public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {}
    public void onUpdate( ItemStack stack, World world, EntityPlayer entity, int iInventorySlot, boolean bIsHandHeldItem ) {}
    // END FCMOD

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {}

    /**
     * false for all Items except sub-classes of ItemMapBase
     */
    public boolean isMap()
    {
        return false;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.none;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 0;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {}

    /**
     * Sets the string representing this item's effect on a potion when used as an ingredient.
     */
    protected Item setPotionEffect(String par1Str)
    {
        this.potionEffect = par1Str;
        return this;
    }

    /**
     * Returns a string representing what this item does to a potion.
     */
    public String getPotionEffect()
    {
        return this.potionEffect;
    }

    /**
     * Returns true if this item serves as a potion ingredient (its ingredient information is not null).
     */
    public boolean isPotionIngredient()
    {
        return this.potionEffect != null;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {}

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        return ("" + StringTranslate.getInstance().translateNamedKey(this.getLocalizedName(par1ItemStack))).trim();
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted() ? EnumRarity.rare : EnumRarity.common;
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isItemTool(ItemStack par1ItemStack)
    {
        return this.getItemStackLimit() == 1 && this.isDamageable();
    }

    protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
        float var4 = 1.0F;
        float var5 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * var4;
        float var6 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * var4;
        double var7 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)var4;
        double var9 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par2EntityPlayer.yOffset;
        double var11 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)var4;
        Vec3 var13 = par1World.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
        return par1World.rayTraceBlocks_do_do(var13, var23, par3, !par3);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }

    public boolean requiresMultipleRenderPasses()
    {
        return false;
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return this.getIconFromDamage(par1);
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    /**
     * gets the CreativeTab this item is displayed on
     */
    public CreativeTabs getCreativeTab()
    {
        return this.tabToDisplayOn;
    }

    /**
     * returns this;
     */
    public Item setCreativeTab(CreativeTabs par1CreativeTabs)
    {
        this.tabToDisplayOn = par1CreativeTabs;
        return this;
    }

    public boolean func_82788_x()
    {
        return true;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }

    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.unlocalizedName);
    }

    static
    {
        StatList.initStats();
    }

	// FCMOD: Added New
    public static boolean m_bSuppressConflictWarnings = false;
    
    /**
     * Method which replaces canPlaceItemBlockOnSide() in ItemBlock. Allows the client to prevent item usage before it is relayed
     * to the server by returning false.  Only called on client.
     */    
    public boolean CanItemBeUsedByPlayer( World world, int i, int j, int k, int iFacing, EntityPlayer player, ItemStack stack )
    {
    	return true;    	
    }
    
    public boolean DoZombiesConsume()
    {
    	return false;
    }
    
    public boolean IsEfficientVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
        return false;
    }
    
    public boolean canHarvestBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
        return false;
    }
    
    public float getStrVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
        return 1F;
    }
    
    public boolean IsMultiUsePerClick()
    {
    	return true;
    }
    
    public float GetExhaustionOnUsedToHarvestBlock( int iBlockID, World world, int i, int j, int k, int iBlockMetadata )
    {
    	return 0.025F; // standard default exhaustion amount
    }
    
    public void InitializeStackOnGiveCommand( Random rand, ItemStack stack )
    {
    }
    
    public void UpdateUsingItem( ItemStack stack, World world, EntityPlayer player )
    {
    }
    
    public int GetItemUseWarmupDuration()
    {
    	return 7;
    }
    
    public boolean IgnoreDamageWhenComparingDuringUse()
    {
    	return false;
    }
	
    //------------ Addon interfacing related functionality ----------//

    /**
     * Replaces a reference to an existing block (vanilla or btw)
     * @param id The block id to be replaced
     * @param newClass The class of the new item
     * @param parameters Optional additional parameters to pass to the item, not including the id.
     */
    public static Item replaceItem(int id, Class newClass, Object ... parameters) {
    	if (itemReplaced[id]) {
    		throw new RuntimeException("Multiple addons attempting to replace item " + itemsList[id]);
    	}
    	else {
    		m_bSuppressConflictWarnings = true;
    		
    		Item newItem = null;
    		
    		Class[] parameterTypes = new Class[parameters.length + 1];
    		Object[] parameterValues = new Object[parameters.length + 1];
    		
    		parameterTypes[0] = Integer.TYPE;
    		parameterValues[0] = id - 256;
    		
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
    			
    			parameterTypes[i + 1] = type;
    			parameterValues[i + 1] = parameters[i];
    		}
    		
    			try {
					newItem = (Item) newClass.getConstructor(parameterTypes).newInstance(parameterValues);
				} catch (InstantiationException e) {
					throw new RuntimeException("A problem has occured attempting to instantiate replacement for " + itemsList[id]);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("Incompatible types passed to specified constructor for " + itemsList[id]);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException("No appropriate constructor found for " + itemsList[id] + ". Constructors must be public to be used in replacement.");
				} catch (Exception e) {
					e.printStackTrace();
				}
    		
    		itemReplaced[id] = true;
    		Item original = itemsList[id];
    		itemsList[id] = null;
    		
    		newItem.SetFilterableProperties(original.m_iFilterablePropertiesBitfield).SetBuoyancy(original.GetBuoyancy(0)).setCreativeTab(original.getCreativeTab());
    		
    		if (original.IsIncineratedInCrucible())
    			newItem.SetIncineratedInCrucible();
    		else
    			newItem.SetNotIncineratedInCrucible();
    		
    		itemsList[id] = newItem;
    		
    		m_bSuppressConflictWarnings = false;
    		
    		return newItem;
    	}
    }

    //----------- Animal Food related functionality -----------//
    //AARON CHANGED to make animal foods worth way more!
//    public static final int m_iBaseHerbivoreItemFoodValue = ( EntityAnimal.m_iBaseGrazeFoodValue * 4 );
//    public static final int m_iBasePigItemFoodValue = ( EntityAnimal.m_iBaseGrazeFoodValue * 4 );
//    public static final int m_iBaseChickenItemFoodValue = ( EntityAnimal.m_iBaseGrazeFoodValue * 8 );
    
    //about 4 wheat per day of whatever a cow eats per day to keep it alive
    public static final int m_iBaseHerbivoreItemFoodValue = ( EntityAnimal.m_iBaseGrazeFoodValue * 15 );
    
    //about 4 of whatever a pig eats per day to keep it alive
    public static final int m_iBasePigItemFoodValue = ( EntityAnimal.m_iBaseGrazeFoodValue * 10 );
    
    //NO CLUE!!! I should probably test?
    public static final int m_iBaseChickenItemFoodValue = ( EntityAnimal.m_iBaseGrazeFoodValue * 16 );

    private int m_iHerbivoreFoodValue = 0;
    private int m_iBirdFoodValue = 0;
    private int m_iPigFoodValue = 0;
    
    public int GetHerbivoreFoodValue( int iItemDamage )
    {
    	return m_iHerbivoreFoodValue;
    }
    
    public Item SetHerbivoreFoodValue( int iFoodValue )
    {
    	m_iHerbivoreFoodValue = iFoodValue;
    	
    	return this;
    }
    
    public Item SetAsBasicHerbivoreFood()
    {
    	return SetHerbivoreFoodValue( m_iBaseHerbivoreItemFoodValue );
    }
    
    public int GetChickenFoodValue( int iItemDamage )
    {
    	return m_iBirdFoodValue;
    }
    
    public Item SetChickenFoodValue( int iFoodValue )
    {
    	m_iBirdFoodValue = iFoodValue;
    	
    	return this;
    }
    
    public Item SetAsBasicChickenFood()
    {
    	return SetChickenFoodValue( m_iBaseChickenItemFoodValue );
    }
    
    public int GetPigFoodValue( int iItemDamage )
    {
    	return m_iPigFoodValue;
    }
    
    public Item SetPigFoodValue( int iFoodValue )
    {
    	m_iPigFoodValue = iFoodValue;
    	
    	return this;
    }
    
    public Item SetAsBasicPigFood()
    {
    	return SetPigFoodValue( m_iBasePigItemFoodValue );
    }
    
    public boolean IsWolfFood()
    {
    	return false;
    }
    
    public int GetWolfHealAmount()
    {
    	return 0;
    }
    
    //------------- Buoyancy related functionality ------------//
    
	private float m_fBuoyancy = -1.0F;
	
    public Item SetBuoyancy( float fBuoyancy )
    {
    	m_fBuoyancy = fBuoyancy;
    	
    	return this;
    }
    
    public Item SetBuoyant() { return SetBuoyancy( 1F ); }
    public Item SetNonBuoyant() { return SetBuoyancy( -1F ); }
    public Item SetNeutralBuoyant() { return SetBuoyancy( 0F ); }
    
    public float GetBuoyancy( int iItemDamage )
    {
    	return m_fBuoyancy;
    }
    
    public int GetWeightWhenWorn()
    {
    	return 0;
    }
    
    //------------- Bellows related functionality ------------//
    
	private int m_iBellowsBlowDistance = 0;
	
	/**
	 * 3 = light powders or light large surface objects like paper or bat wings
	 * 2 = seeds, dyes, fibers, chunkier powders like ground netherrack or sand, 
	 * 	   heavier sheets like bark or wicker
	 * 1 = fabric or wool, small leather like straps, arrows, heavier small mobsdrops like creeper 
	 * 	   oysters, and witch wart, dirt and gravel, heavy seeds like cocoa beans and netherwart
	 * 0 = everything else
	 */
    public Item SetBellowsBlowDistance( int iDistance )
    {
    	m_iBellowsBlowDistance = iDistance;
    	
    	return this;
    }
    
    public int GetBellowsBlowDistance( int iItemDamage )
    {
    	return m_iBellowsBlowDistance;
    }
    
    //------------- Enchanting related functionality ------------//
    
	private int m_iInfernalMaxNumEnchants = 0;
	private int m_iInfernalMaxEnchantmentCost = 0;
	
    public Item SetInfernalMaxNumEnchants( int iMaxNumEnchants )
    {
    	m_iInfernalMaxNumEnchants = iMaxNumEnchants;
    	
    	return this;
    }
    
    public int GetInfernalMaxNumEnchants()
    {
    	return m_iInfernalMaxNumEnchants;
    }    
    
    public Item SetInfernalMaxEnchantmentCost( int iMaxEnchantmentCost )
    {
    	m_iInfernalMaxEnchantmentCost = iMaxEnchantmentCost;
    	
    	return this;
    }
    
    public int GetInfernalMaxEnchantmentCost()
    {
    	return m_iInfernalMaxEnchantmentCost;
    }
    
    public boolean IsEnchantmentApplicable( Enchantment enchantment )
    {
    	return enchantment.type == EnumEnchantmentType.all;
    }
    
    //------------- Crafting related functionality ------------//    
    
    protected int m_iDefaultFurnaceBurnTime = 0;
    protected boolean m_bIsInceratedInCrucible = false;
    
    public boolean IsConsumedInCrafting()
    {
    	return true;
    }
    
    public boolean IsDamagedInCrafting()
    {
    	return false;
    }    
    
    public void OnUsedInCrafting( int iItemDamage, EntityPlayer player, ItemStack outputStack )
    {
    	OnUsedInCrafting( player, outputStack );
    }
    
    public void OnUsedInCrafting( EntityPlayer player, ItemStack outputStack )
    {
    }
    
    public void OnDamagedInCrafting( EntityPlayer player )
    {
    }
    
    public void OnBrokenInCrafting( EntityPlayer player )
    {
    }
    
    public int GetFurnaceBurnTime( int iItemDamage )
    {
    	return m_iDefaultFurnaceBurnTime;
    }
    
    public Item SetFurnaceBurnTime( int iBurnTime )
    {
    	m_iDefaultFurnaceBurnTime = iBurnTime;
    	
    	return this;
    }
    
    public Item SetFurnaceBurnTime( FCEnumFurnaceBurnTime burnTime )
    {
    	SetFurnaceBurnTime( burnTime.m_iBurnTime );
    	
    	return this;
    }
    
    public int GetCampfireBurnTime( int iItemDamage )
    {
    	return GetFurnaceBurnTime( iItemDamage );
    }
    
    /**
     * Used to override default activation behavior on certain blocks like accessing inventory on furnace and campfires 
     */
    public boolean GetCanItemStartFireOnUse( int iItemDamage )
    {
    	return false;
    }    
    
    /**
     * Used to override default activation behavior on certain blocks like accessing inventory on furnace and campfires 
     */
    public boolean GetCanItemBeSetOnFireOnUse( int iItemDamage )
    {
    	return false;
    }
    
    public boolean GetCanBeFedDirectlyIntoCampfire( int iItemDamage )
    {
		return !GetCanItemBeSetOnFireOnUse( iItemDamage ) && !GetCanItemStartFireOnUse( iItemDamage ) &&
			GetCampfireBurnTime( iItemDamage ) > 0;
    }
    
    public boolean GetCanBeFedDirectlyIntoBrickOven( int iItemDamage )
    {
		return !GetCanItemBeSetOnFireOnUse( iItemDamage ) && !GetCanItemStartFireOnUse( iItemDamage ) &&
			GetFurnaceBurnTime( iItemDamage ) > 0;
    }
    
    public boolean IsIncineratedInCrucible()
    {
    	return m_bIsInceratedInCrucible;
    }
    
    public Item SetIncineratedInCrucible()
    {
    	m_bIsInceratedInCrucible = true;
    	
    	return this;
    }
    
    public Item SetNotIncineratedInCrucible()
    {
    	m_bIsInceratedInCrucible = false;
    	
    	return this;
    }
    
    public boolean DoesConsumeContainerItemWhenCrafted( Item containerItem )
    {
    	return false;
    }
    
    //------------- Piston Packing related functionality ------------//
    
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
    
    public Item SetFilterableProperties( int iProperties )
    {
    	m_iFilterablePropertiesBitfield = iProperties;
    	
    	return this;
    }
    
    public boolean CanTransformItemIfFilter( ItemStack filteredItem )
    {
    	return false;
    }
    
    //------------- Deprecated tool functionality ------------//
    
    public static void SetAllPicksToBeEffectiveVsBlock( Block block )
    {
    	block.SetPicksEffectiveOn( true );
    }
    
    public static void SetAllAxesToBeEffectiveVsBlock( Block block )
    {
    	block.SetAxesEffectiveOn( true );
    }
    
    public static void SetAllShovelsToBeEffectiveVsBlock( Block block )
    {
    	block.SetShovelsEffectiveOn( true );
    }
    
    //----- Block Dispenser Related Functionality -----//
    
	/**
	 * This method should return true if the item is successfully placed (in which case the BD will 
	 * consume the corresponding item in its inventory), false otherwise.  Co-ordinates specify
	 * the BD position.
	 */
	public boolean OnItemUsedByBlockDispenser( ItemStack stack, World world, 
		int i, int j, int k, int iFacing )
	{
		FCBetterThanWolves.fcBlockDispenser.SpitOutItem( world, i, j, k, stack );
		
        world.playAuxSFX( 1000, i, j, k, 0 ); // normal pitch click							        
        
		return true;
	}
    
    //------- Item Entity Related Functionality -------//
    
    public boolean hasCustomItemEntity() {
    	return this.entityClass != EntityItem.class;
    }
    
    public Class getCustomItemEntity() {
    	return this.entityClass;
    }
    
    public void setCustomItemEntity(Class entityClass) {
    	this.entityClass = entityClass;
    }
    
    public EntityItem createItemAsEntityInWorld(World world, double x, double y, double z, ItemStack stack) {
    	return null;
    }
	
	//----------- Client Side Functionality -----------//
    
    public Icon GetHopperFilterIcon()
    {
    	return null;
    }
    // END FCMOD    
}
