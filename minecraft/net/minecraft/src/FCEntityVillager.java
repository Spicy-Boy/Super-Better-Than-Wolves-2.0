// FCMOD

package net.minecraft.src;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.ToDoubleFunction;

public abstract class FCEntityVillager extends EntityVillager
{
	public static int m_iNumProfessionTypes = 5;

	protected static final int m_iInLoveDataWatcherID = 22;	
	protected static final int m_iTradeLevelDataWatcherID = 23;

	// data watcher 24 used by EntityCreature parent to indicate possession

	protected static final int m_iTradeExperienceDataWatcherID = 25; 
	protected static final int m_iDirtyPeasantDataWatcherID = 26;

	protected int m_iAIFullTickCountdown;
	protected int m_iUpdateTradesCountdown;

	public static final int professionIDFarmer = 0;
	public static final int professionIDLibrarian = 1;
	public static final int professionIDPriest = 2;
	public static final int professionIDBlacksmith = 3;
	public static final int professionIDButcher = 4;

	public static Map<Integer, Class> professionMap = new HashMap<Integer, Class>();

	public static Map<Integer, Set<WeightedMerchantEntry>> tradeByProfessionList = new HashMap();
	public static Map<Integer, Map<Integer, WeightedMerchantEntry>> levelUpTradeByProfessionList = new HashMap();
	public static Map<Integer, WeightedMerchantEntry> defaultTradeByProfessionList = new HashMap();
	
	public static Map<Integer, Map<WeightedMerchantEntry, TradeEffect>> tradeEffectRegistry = new HashMap();
	public static Map<Integer, Map<Integer, TradeEffect>> levelUpEffectRegistry = new HashMap();
	
	public static TradeEffect defaultLevelUpEffect;
	
	public static int[] xpPerLevel = {0, 5, 7, 10, 15, 20};

	static {
		professionMap.put(professionIDFarmer, FCEntityVillagerFarmer.class);
		professionMap.put(professionIDLibrarian, FCEntityVillagerLibrarian.class);
		professionMap.put(professionIDPriest, FCEntityVillagerPriest.class);
		professionMap.put(professionIDBlacksmith, FCEntityVillagerBlacksmith.class);
		professionMap.put(professionIDButcher, FCEntityVillagerButcher.class);
		
		defaultLevelUpEffect = new TradeEffect() {
			@Override
			public void playEffect(FCEntityVillager villager) {
				villager.worldObj.playSoundAtEntity(villager, "random.levelup", 0.5F + (villager.rand.nextFloat() * 0.25F), 1.5F);
			}
		};
	}

	public FCEntityVillager(World world)
	{
		this(world, 0);
	}

	public FCEntityVillager(World world, int iProfession)
	{
		super(world, iProfession);

		tasks.RemoveAllTasksOfClass(EntityAIAvoidEntity.class);

		tasks.addTask(1, new EntityAIAvoidEntity(this, FCEntityZombie.class, 8.0F, 0.3F, 0.35F));
		tasks.addTask(1, new EntityAIAvoidEntity(this, FCEntityWolf.class, 8.0F, 0.3F, 0.35F));

		tasks.RemoveAllTasksOfClass(EntityAIVillagerMate.class);

		tasks.addTask(1, new FCEntityAIVillagerMate(this));
		tasks.addTask(2, new EntityAITempt(this, 0.3F, Item.diamond.itemID, false));

		experienceValue = 50; // set experience when slain

		m_iUpdateTradesCountdown = 0;    
		m_iAIFullTickCountdown = 0;
	}

	@Override
	protected void updateAITick() {
		m_iAIFullTickCountdown--;

		if (m_iAIFullTickCountdown <= 0) {
			m_iAIFullTickCountdown = 70 + rand.nextInt(50); // schedule the next village position update

			worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));

			villageObj = worldObj.villageCollectionObj.findNearestVillage(
					MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 32);

			if (villageObj == null) {
				detachHome();
			}
			else {
				ChunkCoordinates var1 = villageObj.getCenter();

				setHomeArea(var1.posX, var1.posY, var1.posZ, (int)((float)villageObj.getVillageRadius() * 0.6F));
			}
		}

		if (!isTrading()) {
			if (GetCurrentTradeLevel() == 0) {
				// this indicates a newly created villager or an old one that was created before I revamped the trading system

				SetTradeLevel(1);

				buyingList = null;
				m_iUpdateTradesCountdown = 0;

				checkForNewTrades(1);
			}
			else if (m_iUpdateTradesCountdown > 0) {
				m_iUpdateTradesCountdown--;

				if (m_iUpdateTradesCountdown <= 0) {
					// remove all trades which have exceeded their maximum uses

					Iterator tradeListIterator = this.buyingList.iterator();

					while (tradeListIterator.hasNext()) {
						MerchantRecipe tempRecipe = (MerchantRecipe)tradeListIterator.next();

						if (tempRecipe.func_82784_g()) { // check for toolUses >= this.maxTradeUses;
							tradeListIterator.remove();
						}
					}

					int desiredNumTrades = getCurrentMaxNumTrades();

					if (buyingList.size() < desiredNumTrades) {
						checkForNewTrades(desiredNumTrades - buyingList.size());

						worldObj.setEntityState(this, (byte)14); // triggers "happy villager" particles

						addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
					}
				}
			}
			else {
				// schedule periodic checks of the trade list so it'll never jam up

				m_iUpdateTradesCountdown = 600 + rand.nextInt(600); // 30 seconds to a minute
			}
		}
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if (customInteract(player))
		{
			return true;
		}

		if (GetInLove() > 0)
		{
			return EntityAgeableInteract(player);
		}

		return super.interact(player);  		
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		dataWatcher.addObject(m_iInLoveDataWatcherID, new Integer(0));
		dataWatcher.addObject(m_iTradeLevelDataWatcherID, new Integer(0));
		dataWatcher.addObject(m_iTradeExperienceDataWatcherID, new Integer(0));
		dataWatcher.addObject(m_iDirtyPeasantDataWatcherID, new Integer(0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setInteger("FCInLove", GetInLove());

		tag.setInteger("FCTradeLevel", GetCurrentTradeLevel());
		tag.setInteger("FCTradeXP", GetCurrentTradeXP());

		tag.setInteger("FCDirty", GetDirtyPeasant());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);

		if (tag.hasKey("FCInLove")) {
			this.SetInLove(tag.getInteger("FCInLove"));
		}

		if (tag.hasKey("FCTradeLevel")) {
			this.SetTradeLevel(tag.getInteger("FCTradeLevel"));
		}

		if (tag.hasKey("FCTradeXP")) {
			this.SetTradeExperience(tag.getInteger("FCTradeXP"));
		}

		if (tag.hasKey("FCDirty")) {
			this.SetDirtyPeasant(tag.getInteger("FCDirty"));
		}

		this.checkForInvalidTrades();
	}

	@Override
	public void setRevengeTarget(EntityLiving attackingEntity)
	{
		entityLivingToAttack = attackingEntity;

		if (attackingEntity != null)
		{
			revengeTimer = 100;

			if (villageObj != null)
			{
				villageObj.addOrRenewAgressor(attackingEntity);
			}

			if (isEntityAlive())
			{
				worldObj.setEntityState(this, (byte)13);
			}
		}
		else
		{
			revengeTimer = 0;
		}
	}

	@Override
	public void useRecipe(MerchantRecipe recipe) {
		//if (!recipe.isMandatory())
			recipe.incrementToolUses();

		m_iUpdateTradesCountdown = 10;

		// special trade reactions
		this.playEffectsForTrade(recipe);

		if (recipe.m_iTradeLevel < 0) { // negative trade levels represent a level up trade
			int tradeLevel = GetCurrentTradeLevel();

			if (tradeLevel < 5 && GetCurrentTradeXP() == GetCurrentTradeMaxXP() && GetCurrentTradeLevel() == -(recipe.m_iTradeLevel)) {
				tradeLevel++;

				SetTradeLevel(tradeLevel);
				SetTradeExperience(0);

				this.playEffectsForLevelUp(recipe);
			}
		}
		else if (recipe.m_iTradeLevel >= GetCurrentTradeLevel() && !recipe.isMandatory()) {  		
			int currentXP = GetCurrentTradeXP() + 1;
			int maxXP = GetCurrentTradeMaxXP();

			if (currentXP > maxXP) {
				currentXP = maxXP;
			}

			SetTradeExperience(currentXP);
		}
	}

	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player) {
		if (buyingList == null) {
			checkForNewTrades(1);
		}

		return buyingList;
	}

	@Override
	public void initCreature() {
		setProfession(this.getProfessionFromClass());
	}


	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (!worldObj.isRemote)
		{
			if (isEntityAlive())
			{
				CheckForLooseMilk();
			}
		}
		else
		{
			updateStatusParticles();
		}
	}

	@Override
	protected void dropFewItems(boolean bKilledByPlayer, int iLootingModifier)
	{
		if (!HasHeadCrabbedSquid())
		{
			int iDropItemID = FCBetterThanWolves.fcItemRawMysteryMeat.itemID;

			if (isBurning())
			{
				iDropItemID = FCBetterThanWolves.fcItemCookedMysteryMeat.itemID;
			}

			int iNumDropped = rand.nextInt(2) + 1 + rand.nextInt(1 + iLootingModifier);

			for (int iTempCount = 0; iTempCount < iNumDropped; ++iTempCount)
			{    	
				dropItem(iDropItemID, 1);
			}
		}
	}

	@Override
	protected float getSoundPitch()
	{
		float fPitch = super.getSoundPitch();

		if (IsPossessed() || (getProfession() == 2 && GetCurrentTradeLevel() == 5))
		{
			fPitch *= 0.60F;
		}

		return fPitch;
	}

	@Override
	protected boolean GetCanCreatureTypeBePossessed()
	{
		return true;
	}

	@Override
	protected void OnFullPossession()
	{
		worldObj.playAuxSFX(FCBetterThanWolves.m_iPossessedVillagerTransformToWitchAuxFXID, 
				MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 
				0);

		setDead();

		FCEntityWitch entityWitch = new FCEntityWitch(worldObj);

		entityWitch.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		entityWitch.renderYawOffset = renderYawOffset;

		entityWitch.SetPersistent(true);

		worldObj.spawnEntityInWorld(entityWitch);
	}

	@Override
	public boolean IsValidZombieSecondaryTarget(EntityZombie zombie)
	{
		return true;
	}

	@Override
	public boolean IsSecondaryTargetForSquid()
	{
		return true;
	}

	@Override
	public double getMountedYOffset()
	{
		return (double)height;
	}

	@Override
	public FCEntityVillager func_90012_b(EntityAgeable otherParent)
	{
		// creates new villager when breeding

		FCEntityVillager child = createVillager(this.worldObj);

		child.initCreature();

		return child;
	}

	//------------- Class Specific Methods ------------//

	public static FCEntityVillager createVillager(World world) {
		return createVillagerFromProfession(world, 0);
	}

	public static FCEntityVillager createVillagerFromProfession(World world, int profession) {
		Class villagerClass = professionMap.get(profession);

		try {
			FCEntityVillager villager = (FCEntityVillager) villagerClass.getConstructor(World.class).newInstance(world);
			villager.setProfession(profession);

			return villager;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	public FCEntityVillager spawnBabyVillagerWithProfession(EntityAgeable otherParent, int profession) {
		FCEntityVillager child = createVillagerFromProfession(this.worldObj, profession);
		child.initCreature();

		return child;
	}

	public int getProfessionFromClass() {
		for (int i = 0; i < professionMap.size(); i++) {
			if (this.getClass().isAssignableFrom(professionMap.get(i))) {
				return i;
			}
		}

		return 0;
	}

	// ------ Trade list functionality ------ //

	protected void checkForNewTrades(int availableTrades) {
		if (availableTrades > 0) {
			if (this.GetCurrentTradeMaxXP() == this.GetCurrentTradeXP() && this.checkForLevelUpTrade()) {
				--availableTrades;

				if (availableTrades <= 0) {
					return;
				}
			}

			MerchantRecipeList recipeList = new MerchantRecipeList();

			availableTrades = this.checkForProfessionMandatoryTrades(recipeList, availableTrades, this.GetCurrentTradeLevel());

			if (availableTrades > 0) {
				this.checkForProfessionTrades(recipeList, availableTrades);
			}

			if (recipeList.isEmpty()) {
				recipeList.add(this.getProfessionDefaultTrade());
			}
			else {
				Collections.shuffle(recipeList);
			}

			if (this.buyingList == null) {
				this.buyingList = new MerchantRecipeList();
			}

			for (int i = 0; i < availableTrades && i < recipeList.size(); ++i) {
				this.buyingList.addToListWithCheck((MerchantRecipe)recipeList.get(i));
			}
		}
	}

	protected void checkForProfessionTrades(MerchantRecipeList recipeList, int availableTrades) {
		//Copies trade list to allow for special case trades
		Set<WeightedMerchantEntry> tradeList = new HashSet();

		for (WeightedMerchantEntry entry : tradeByProfessionList.get(this.getProfessionFromClass())) {
			if (entry.level <= this.GetCurrentTradeLevel() && !entry.isMandatory() && entry.canBeAdded(this)) {
				tradeList.add(entry);
			}
		}
		
		//AARON replaces this with the CE sanity check
//		for (; availableTrades > 0; availableTrades--) {
//			recipeList.add(this.getRandomTradeFromAdjustedWeight(tradeList));
		
		// Sanity check to prevent infinite loops
		int currentAttempts = 0;
		int maxAttempts = 50;

		while (availableTrades > 0 && currentAttempts < maxAttempts) {
			MerchantRecipe recipe = this.getRandomTradeFromAdjustedWeight(tradeList);

			if (!this.doesRecipeListAlreadyContainRecipe(recipe)) {
				recipeList.add(recipe);
				availableTrades--;
			}

			currentAttempts++;
			//AARON ^^^
		}
	}

	// TODO: For some reason mandatory trades occasionally do not get populated - requires further investigation
	protected int checkForProfessionMandatoryTrades(MerchantRecipeList recipeList, int availableTrades, int level) {
		Set<WeightedMerchantEntry> entries = tradeByProfessionList.get(this.getProfessionFromClass());

		if (entries != null) {
			for (WeightedMerchantEntry entry : entries) {
				if (entry.level <= level && availableTrades > 0 && entry.isMandatory()) {
					//AARON replaces with code from CE fix
//					recipeList.add(entry.generateRecipe(this.rand));
					MerchantRecipe recipe = entry.generateRecipe(this.rand);

					if (!this.doesRecipeListAlreadyContainRecipe(recipe)) {
						recipeList.add(recipe);
						availableTrades--;
					}
					//AARON ^^^
				}
			}
		}

		return availableTrades;
	}

	private boolean checkForLevelUpTrade() {
		MerchantRecipe recipe = this.getProfessionLevelUpTrade(this.GetCurrentTradeLevel());

		//AARON changed "Does" to lowercase
		if (recipe != null && !this.doesRecipeListAlreadyContainRecipe(recipe)) {
			this.buyingList.add(recipe);
			return true;
		}
		else {
			return false;
		}
	}

	protected MerchantRecipe getProfessionLevelUpTrade(int level) {
		WeightedMerchantEntry entry = levelUpTradeByProfessionList.get(this.getProfessionFromClass()).get(level);

		return entry.generateRecipe(this.rand);
	}

	//AARON changes this method to reflect CE trading repopulation fix
	//Also first letter is now lowercase
//	protected boolean DoesRecipeListAlreadyContainRecipe(MerchantRecipe recipe)
//	{
//		for (int iTempRecipeIndex = 0; iTempRecipeIndex < buyingList.size(); ++iTempRecipeIndex)
//		{
//			MerchantRecipe tempRecipe = (MerchantRecipe)buyingList.get(iTempRecipeIndex);
//
//			if (recipe.hasSameIDsAs(tempRecipe))
//			{
	protected boolean doesRecipeListAlreadyContainRecipe(MerchantRecipe recipe) {
		for (int i = 0; i < buyingList.size(); ++i) {
			MerchantRecipe recipeForCompare = (MerchantRecipe) buyingList.get(i);

			if (recipe.hasSameIDsAs(recipeForCompare)) {
			//AARON ^^^
				return true;
			}
		}

		return false;
	}

	/**
	 * Return the default trade to use if no other trade is found
	 * @return The default trade
	 */
	protected MerchantRecipe getProfessionDefaultTrade() {
		return defaultTradeByProfessionList.get(this.getProfessionFromClass()).generateRecipe(this.rand);
	}

	protected MerchantRecipe getRandomTradeFromAdjustedWeight(Set<WeightedMerchantEntry> tradeList) {
		final int villagerTradeLevel = GetCurrentTradeLevel();

		ToDoubleFunction<WeightedMerchantEntry> weighter = new ToDoubleFunction<WeightedMerchantEntry>() {
			@Override
			public double applyAsDouble(WeightedMerchantEntry entry) {
				return entry.weight * entry.level / villagerTradeLevel;
			}
		};

		FCUtilsRandomSelector<WeightedMerchantEntry> selector = FCUtilsRandomSelector.weighted(tradeList, weighter);

		return selector.next(this.rand).generateRecipe(this.rand);
	}

	public int getCurrentMaxNumTrades() {
		return GetCurrentTradeLevel();
	}

	private void checkForInvalidTrades() {
		MerchantRecipe trade;

		if (this.buyingList != null) {
			Iterator iterator = this.buyingList.iterator();

			while (iterator.hasNext()) {
				trade = (MerchantRecipe)iterator.next();

				if (this.isInvalidProfessionTrade(trade)) {
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Used to clear any invalid trades e.g. that may be left over from previous versions
	 * @param trade The trade to check
	 * @return Whether the trade was invalid and should be removed
	 */
	protected boolean isInvalidProfessionTrade(MerchantRecipe trade) {
		//Checks if the trade is in any of the trade lists
		//If it is, check for invalid stack sizes

		//Standard trades
		for (WeightedMerchantEntry entry : tradeByProfessionList.get(this.getProfessionFromClass())) {
			if (entry.matchesMerchantRecipe(trade)) {
				if (trade.getItemToBuy().stackSize > trade.getItemToBuy().getMaxStackSize() ||
						trade.hasSecondItemToBuy() && trade.getSecondItemToBuy().stackSize > trade.getSecondItemToBuy().getMaxStackSize() ||
						trade.getItemToSell().stackSize > trade.getItemToSell().getMaxStackSize()) {
					return true;
				}

				return false;
			}
		}

		//Level up trades
		for (int i = 1; i <= 5; i++) {
			WeightedMerchantEntry entry = levelUpTradeByProfessionList.get(this.getProfessionFromClass()).get(i);

			if (entry.matchesMerchantRecipe(trade)) {
				if (trade.getItemToBuy().stackSize > trade.getItemToBuy().getMaxStackSize() ||
						trade.hasSecondItemToBuy() && trade.getSecondItemToBuy().stackSize > trade.getSecondItemToBuy().getMaxStackSize() ||
						trade.getItemToSell().stackSize > trade.getItemToSell().getMaxStackSize()) {
					return true;
				}

				return false;
			}
		}

		//Trade not in any lists
		return true;
	}

	// ------ Simple buying trades ------ //

	public static WeightedMerchantEntry addTradeToBuySingleItem(int profession, int itemID, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		return addTradeToBuySingleItem(profession, itemID, 0, minEmeraldCount, maxEmeraldCount, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToBuySingleItem(int profession, int itemID, int itemMetadata, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		return addTradeToBuy(profession, itemID, itemMetadata, 1, 1, minEmeraldCount, maxEmeraldCount, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToBuyMultipleItems(int profession, int itemID, int minItemCount, int maxItemCount, float weight, int tradeLevel) {
		return addTradeToBuyMultipleItems(profession, itemID, 0, minItemCount, maxItemCount, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToBuyMultipleItems(int profession, int itemID, int itemMetadata, int minItemCount, int maxItemCount, float weight, int tradeLevel) {
		return addTradeToBuy(profession, itemID, itemMetadata, minItemCount, maxItemCount, 1, 1, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToBuy(int profession, int itemID, int itemMetadata, int minItemCount, int maxItemCount, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		WeightedMerchantRecipeEntry entry = new WeightedMerchantRecipeEntry(
				itemID, itemMetadata, minItemCount, maxItemCount,
				Item.emerald.itemID, 0, minEmeraldCount, maxEmeraldCount,
				weight, tradeLevel
			);

		return addCustomTrade(profession, entry);
	}

	// ------ Simple selling trades ------ //

	public static WeightedMerchantEntry addTradeToSellSingleItem(int profession, int itemID, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		return addTradeToSellSingleItem(profession, itemID, 0, minEmeraldCount, maxEmeraldCount, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToSellSingleItem(int profession, int itemID, int itemMetadata, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		return addTradeToSell(profession, itemID, itemMetadata, 1, 1, minEmeraldCount, maxEmeraldCount, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToSellMultipleItems(int profession, int itemID, int minItemCount, int maxItemCount, float weight, int tradeLevel) {
		return addTradeToSellMultipleItems(profession, itemID, 0, minItemCount, maxItemCount, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToSellMultipleItems(int profession, int itemID, int itemMetadata, int minItemCount, int maxItemCount, float weight, int tradeLevel) {
		return addTradeToSell(profession, itemID, itemMetadata, minItemCount, maxItemCount, 1, 1, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addTradeToSell(int profession, int itemID, int itemMetadata, int minItemCount, int maxItemCount, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		WeightedMerchantRecipeEntry entry = new WeightedMerchantRecipeEntry(
				Item.emerald.itemID, 0, minEmeraldCount, maxEmeraldCount,
				itemID, itemMetadata, minItemCount, maxItemCount,
				weight, tradeLevel
			);

		return addCustomTrade(profession, entry);
	}

	// ------ Other trades ------//

	public static WeightedMerchantEntry addArcaneScrollTrade(int profession, int enchantmentID, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		return addItemConversionTrade(profession, Item.paper.itemID, 0, minEmeraldCount, maxEmeraldCount, FCBetterThanWolves.fcItemArcaneScroll.itemID, enchantmentID, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addSkullconversionTrade(int profession, int inputSkullType, int minEmeralds, int maxEmeralds, int resultSkullType, float weight, int tradeLevel) {
		return addItemConversionTrade(profession, Item.skull.itemID, inputSkullType, minEmeralds, maxEmeralds, Item.skull.itemID, resultSkullType, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addItemConversionTrade(int profession, int itemID, int minEmeralds, int maxEmeralds, int resultID, float weight, int tradeLevel) {
		return addItemConversionTrade(profession, itemID, 0, minEmeralds, maxEmeralds, resultID, 0, weight, tradeLevel);
	}

	public static WeightedMerchantEntry addItemConversionTrade(int profession, int itemID, int itemMetadata, int minEmeraldCount, int maxEmeraldCount, int resultID, int resultMetadata, float weight, int tradeLevel) {
		WeightedMerchantRecipeEntry entry = new WeightedMerchantRecipeEntry(
				itemID, itemMetadata, 1, 1,
				Item.emerald.itemID, 0, minEmeraldCount, maxEmeraldCount,
				resultID, resultMetadata, 1, 1,
				weight, tradeLevel
			);

		return addCustomTrade(profession, entry);
	}

	public static WeightedMerchantEntry addEnchantmentTrade(int profession, int itemID, int minEmeraldCount, int maxEmeraldCount, float weight, int tradeLevel) {
		WeightMerchantEnchantmentEntry entry = new WeightMerchantEnchantmentEntry(itemID, minEmeraldCount, maxEmeraldCount, weight, tradeLevel);

		return addCustomTrade(profession, entry);
	}

	public static WeightedMerchantEntry addComplexTrade(
			int profession,
			int input1ID, int input1Metadata, int input1MinCount, int input1MaxCount,
			int input2ID, int input2Metadata, int input2MinCount, int input2MaxCount,
			int resultID, int resultMetadata, int resultMinCount, int resultMaxCount,
			float weight, int tradeLevel
			) {
		WeightedMerchantRecipeEntry entry = new WeightedMerchantRecipeEntry(
				input1ID, input1Metadata, input1MinCount, input1MaxCount,
				input2ID, input2Metadata, input2MinCount, input2MaxCount,
				resultID, resultMetadata, resultMinCount, resultMaxCount,
				weight, tradeLevel
			);

		return addCustomTrade(profession, entry);
	}

	/**
	 * Add a trade with a custom-defined WeightedMerchantEntry class
	 */
	public static WeightedMerchantEntry addCustomTrade(int profession, WeightedMerchantEntry entry) {
		Map<Integer, Set<WeightedMerchantEntry>> tradeList = tradeByProfessionList;

		Set<WeightedMerchantEntry> tradeEntryList = tradeList.get(profession);

		if (tradeEntryList == null) {
			tradeEntryList = new HashSet();
			tradeList.put(profession, tradeEntryList);
		}

		tradeEntryList.add(entry);

		return entry;
	}

	// ------ Level up trades ------ //

	public static void addLevelUpTradeToBuySingleItem(int profession, int itemID, int minEmeraldCount, int maxEmeraldCount, int tradeLevel) {
		addLevelUpTradeToBuySingleItem(profession, itemID, 0, minEmeraldCount, maxEmeraldCount, tradeLevel);
	}

	public static void addLevelUpTradeToBuySingleItem(int profession, int itemID, int itemMetadata, int minEmeraldCount, int maxEmeraldCount, int tradeLevel) {
		addLevelUpTradeToBuy(profession, itemID, itemMetadata, 1, 1, minEmeraldCount, maxEmeraldCount, tradeLevel);
	}

	public static void addLevelUpTradeToBuyMultipleItems(int profession, int itemID, int minItemCount, int maxItemCount, int tradeLevel) {
		addLevelUpTradeToBuyMultipleItems(profession, itemID, 0, minItemCount, maxItemCount, tradeLevel);
	}

	public static void addLevelUpTradeToBuyMultipleItems(int profession, int itemID, int itemMetadata, int minItemCount, int maxItemCount, int tradeLevel) {
		addLevelUpTradeToBuy(profession, itemID, itemMetadata, minItemCount, maxItemCount, 1, 1, tradeLevel);
	}

	public static void addLevelUpTradeToBuy(int profession, int itemID, int itemMetadata, int minItemCount, int maxItemCount, int minEmeraldCount, int maxEmeraldCount, int tradeLevel) {
		WeightedMerchantRecipeEntry entry = new WeightedMerchantRecipeEntry(
				itemID, itemMetadata, minItemCount, maxItemCount,
				Item.emerald.itemID, 0, minEmeraldCount, maxEmeraldCount,
				1, tradeLevel
			);

		addCustomLevelUpTrade(profession, entry);
	}

	public static void addCustomLevelUpTrade(int profession, WeightedMerchantEntry entry) {
		Map<Integer, WeightedMerchantEntry> tradeEntryList = levelUpTradeByProfessionList.get(profession);

		if (tradeEntryList == null) {
			tradeEntryList = new HashMap();
			levelUpTradeByProfessionList.put(profession, tradeEntryList);
		}
		
		int tradeLevel = entry.level;
		entry.level *= -1; //Negative level indicates level up

		if (tradeEntryList.containsKey(tradeLevel)) {
			throw new RuntimeException("Profession id " + profession + "already has a level up trade assigned for level " + tradeLevel);
		}
		else {
			tradeEntryList.put(tradeLevel, entry);
		}
	}

	public static boolean removeLevelUpTrade(int profession, int level) {
		Map<Integer, WeightedMerchantEntry> tradeEntryList = levelUpTradeByProfessionList.get(profession);
		
		if (tradeEntryList != null) {
			WeightedMerchantEntry entry = tradeEntryList.get(level);
			
			if (entry != null) {
				tradeEntryList.remove(level);
			}
		}
		
		return false;
	}
	
	// ------ Trade removal ------ //
	public static boolean removeTradeToBuy(int profession, int itemID, int itemMetadata) {
		return removeComplexTrade(profession, itemID, itemMetadata, 0, 0, Item.emerald.itemID, 0);
	}

	public static boolean removeTradeToSell(int profession, int itemID, int itemMetadata) {
		return removeComplexTrade(profession, Item.emerald.itemID, 0, 0, 0, itemID, itemMetadata);
	}
	
	public static boolean removeComplexTrade(int profession, int item1ID, int item1Metadata, int item2ID, int item2Metadata, int resultID, int resultMetadata) {
		WeightedMerchantRecipeEntry entryToRemove = new WeightedMerchantRecipeEntry(item1ID, item1Metadata, 1, 1, item2ID, item2Metadata, 1, 1, resultID, resultMetadata, 1, 1, 1, 1);

		return removeCustomTrade(profession, entryToRemove);
	}

	public static boolean removeEnchantmentTrade(int profession, int itemID) {
		WeightMerchantEnchantmentEntry entryToRemove = new WeightMerchantEnchantmentEntry(itemID, 1, 1, 1, 1);

		return removeCustomTrade(profession, entryToRemove);
	}
	
	public static boolean removeCustomTrade(int profession, WeightedMerchantEntry entryToRemove) {
		//Standard trades
		for (WeightedMerchantEntry entry : tradeByProfessionList.get(profession)) {
			if (entry.equals(entryToRemove)) {
				tradeByProfessionList.get(profession).remove(entry);
				return true;
			}
		}

		//Level up trades
		for (int i = 1; i <= 5; i++) {
			WeightedMerchantEntry entry = levelUpTradeByProfessionList.get(profession).get(i);

			if (entry.equals(entryToRemove)) {
				levelUpTradeByProfessionList.get(profession).put(i, null);
				return true;
			}
		}

		//Trade not in any lists
		return false;
	}
	
	// ------ Trade Effects ------ //

	public void playEffectsForTrade(MerchantRecipe trade) {
		Map<WeightedMerchantEntry, TradeEffect> effectRegistry = tradeEffectRegistry.get(this.getProfessionFromClass());
		
		if (effectRegistry != null) {
			for (WeightedMerchantEntry entry : effectRegistry.keySet()) {
				if (entry.matchesMerchantRecipe(trade)) {
					effectRegistry.get(entry).playEffect(this);
				}
			}
		}
	}
	
	public static void registerEffectForTrade(int profession, WeightedMerchantEntry entry, TradeEffect effect) {
		Map<WeightedMerchantEntry, TradeEffect> effectRegistry = tradeEffectRegistry.get(profession);
		
		if (effectRegistry == null) {
			effectRegistry = new HashMap();
			tradeEffectRegistry.put(profession, effectRegistry);
		}
		
		effectRegistry.put(entry, effect);
	}
	
	public static boolean removeEffectForTrade(int profession, WeightedMerchantEntry entry, TradeEffect effect) {
		Map<WeightedMerchantEntry, TradeEffect> effectRegistry = tradeEffectRegistry.get(profession);
		
		if (effectRegistry != null && effectRegistry.containsKey(entry)) {
			effectRegistry.remove(entry);
			return true;
		}
		else {
			return false;
		}
	}

	public void playEffectsForLevelUp(MerchantRecipe trade) {
		Map<Integer, TradeEffect> effectRegistry = levelUpEffectRegistry.get(this.getProfessionFromClass());
		
		if (effectRegistry != null) {
			TradeEffect effect = effectRegistry.get(-trade.m_iTradeLevel);
			
			if (effect != null) {
				effect.playEffect(this);
				return;
			}
		}
		
		defaultLevelUpEffect.playEffect(this);
	}
	
	public static void registerEffectForLevelUp(int profession, int level, TradeEffect effect) {
		Map<Integer, TradeEffect> effectRegistry = levelUpEffectRegistry.get(profession);
		
		if (effectRegistry == null) {
			effectRegistry = new HashMap();
			levelUpEffectRegistry.put(profession, effectRegistry);
		}
		
		effectRegistry.put(level, effect);
	}

	public static boolean removeEffectForLevelUp(int profession, int level) {
		Map<Integer, TradeEffect> effectRegistry = levelUpEffectRegistry.get(profession);
		
		if (effectRegistry != null && effectRegistry.containsKey(level)) {
			effectRegistry.remove(level);
			return true;
		}
		else {
			return false;
		}
	}
	
	// ------ Misc entity functionality ------ //

	protected boolean customInteract(EntityPlayer player) {
		ItemStack heldStack = player.inventory.getCurrentItem();

		if (heldStack != null && heldStack.getItem().itemID == Item.diamond.itemID && getGrowingAge() == 0 && GetInLove() == 0 && !IsPossessed()) {
			if (!player.capabilities.isCreativeMode) {
				heldStack.stackSize--;

				if (heldStack.stackSize <= 0) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
				}
			}

			worldObj.playSoundAtEntity(this, "random.classic_hurt", 1.0F, getSoundPitch() * 2.0F);

			SetInLove(1);

			entityToAttack = null;

			return true;
		}

		return false;
	}
	
	protected void updateStatusParticles() {
		spawnCustomParticles();

		if (GetInLove() > 0) {
			GenerateRandomParticles("heart");
		}
	}
	
	protected void spawnCustomParticles() {}

	protected void GenerateRandomParticles(String sParticle) {
		for (int iTempCount = 0; iTempCount < 5; ++iTempCount) {
			double dVelX = rand.nextGaussian() * 0.02D;
			double dVelY = rand.nextGaussian() * 0.02D;
			double dVelZ = rand.nextGaussian() * 0.02D;

			worldObj.spawnParticle(sParticle, 
					posX + (rand.nextFloat() * width * 2F) - width, 
					posY + 1D + (rand.nextFloat() * height), 
					posZ + (rand.nextFloat() * width * 2F) - width, 
					dVelX, dVelY, dVelZ);
		}
	}

	public void CheckForLooseMilk() {
		List collisionList = worldObj.getEntitiesWithinAABB(EntityItem.class, 
				AxisAlignedBB.getAABBPool().getAABB(
						posX - 1.0f, posY - 1.0f, posZ - 1.0f, 
						posX + 1.0f, posY + 1.0f, posZ + 1.0f));

		if (!collisionList.isEmpty()) {
			for(int listIndex = 0; listIndex < collisionList.size(); listIndex++) {
				EntityItem entityItem = (EntityItem)collisionList.get(listIndex);

				if (entityItem.delayBeforeCanPickup <= 0 && !(entityItem.isDead)) {
					int iTempItemID = entityItem.getEntityItem().itemID;

					Item tempItem = Item.itemsList[iTempItemID];

					if (tempItem.itemID == Item.bucketMilk.itemID) {
						// toss the milk

						entityItem.setDead();

						entityItem = new EntityItem(worldObj, posX, posY - 0.30000001192092896D + (double)getEyeHeight(), posZ, 
								new ItemStack(Item.bucketMilk, 1, 0));

						float f1 = 0.2F;

						entityItem.motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
						entityItem.motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;

						entityItem.motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f1 + 0.2F;

						f1 = 0.02F;
						float f3 = rand.nextFloat() * 3.141593F * 2.0F;
						f1 *= rand.nextFloat();
						entityItem.motionX += Math.cos(f3) * (double)f1;
						entityItem.motionY += 0.25F;
						entityItem.motionZ += Math.sin(f3) * (double)f1;

						entityItem.delayBeforeCanPickup = 10;

						worldObj.spawnEntityInWorld(entityItem);

						int iFXI = MathHelper.floor_double(entityItem.posX);
						int iFXJ = MathHelper.floor_double(entityItem.posY);
						int iFXK = MathHelper.floor_double(entityItem.posZ);

						int iExtraData = 0;

						if (IsPossessed() || (getProfession() == 2 && GetCurrentTradeLevel() == 5)) {
							iExtraData = 1;
						}

						worldObj.playAuxSFX(FCBetterThanWolves.m_iTossTheMilkAuxFXID, iFXI, iFXJ, iFXK, iExtraData); 	    


					}
				}
			}
		}
	}  

	public int GetInLove() {
		return dataWatcher.getWatchableObjectInt(m_iInLoveDataWatcherID);
	}

	public void SetInLove(int iInLove) {
		dataWatcher.updateObject(m_iInLoveDataWatcherID, iInLove);
	}

	public int GetCurrentTradeLevel() {
		return dataWatcher.getWatchableObjectInt(m_iTradeLevelDataWatcherID);
	}

	public void SetTradeLevel(int iTradeLevel) {
		dataWatcher.updateObject(m_iTradeLevelDataWatcherID, iTradeLevel);
	}

	public int GetCurrentTradeXP() {
		return dataWatcher.getWatchableObjectInt(m_iTradeExperienceDataWatcherID);
	}

	public void SetTradeExperience(int iTradeExperience) {
		dataWatcher.updateObject(m_iTradeExperienceDataWatcherID, iTradeExperience);
	}

	public int GetDirtyPeasant() {
		return 0;
	}

	public int GetCurrentTradeMaxXP() {
		return this.xpPerLevel[GetCurrentTradeLevel()];
	}

	public void SetDirtyPeasant(int iDirtyPeasant) {}

	protected void ScheduleImmediateTradelistRefresh() {
		m_iUpdateTradesCountdown = 1;
	}
	
	// ------ Helper Classes ------ //

	public static abstract class WeightedMerchantEntry {
		public final float weight;
		public int level; //Not final to allow inversion for level up trade
		
		private boolean isMandatory;
		
		private TradeConditional conditional;

		public WeightedMerchantEntry(float weight, int level) {
			this.weight = weight;
			this.level = level;
			this.isMandatory = false;
		}

		public abstract boolean equals(WeightedMerchantEntry entry);

		public abstract MerchantRecipe generateRecipe(Random rand);

		public abstract boolean matchesMerchantRecipe(MerchantRecipe trade);

		public void setDefault(int profession) {
			FCEntityVillager.defaultTradeByProfessionList.put(profession, this);
		}
		
		public WeightedMerchantEntry registerEffectForTrade(int profession, TradeEffect effect) {
			FCEntityVillager.registerEffectForTrade(profession, this, effect);
			return this;
		}
		
		public boolean isMandatory() {
			return this.isMandatory;
		}
		
		public WeightedMerchantEntry setMandatory() {
			this.isMandatory = true;
			return this;
		}
		
		public WeightedMerchantEntry setConditional(TradeConditional conditional) {
			this.conditional = conditional;
			return this;
		}
		
		public boolean canBeAdded(FCEntityVillager villager) {
			return this.conditional == null || this.conditional.shouldAddTrade(villager);
		}
	}

	public static class WeightMerchantEnchantmentEntry extends WeightedMerchantEntry {
		public final int itemID;
		public final int minEmeraldCount;
		public final int maxEmeraldCount;

		public WeightMerchantEnchantmentEntry(int itemID, int minEmeraldCount, int maxEmeraldCount, float weight, int level) {
			super(weight, level);

			this.itemID = itemID;
			this.minEmeraldCount = minEmeraldCount;
			this.maxEmeraldCount = maxEmeraldCount;
		}

		public boolean equals(WeightedMerchantEntry entry) {
			return entry instanceof WeightMerchantEnchantmentEntry && 
					this.itemID == ((WeightMerchantEnchantmentEntry) entry).itemID;
		}

		@Override
		public MerchantRecipe generateRecipe(Random rand) {
			int cost = MathHelper.getRandomIntegerInRange(rand, minEmeraldCount, maxEmeraldCount);

			ItemStack input = new ItemStack(Item.itemsList[this.itemID]);
			ItemStack emeralds = new ItemStack(Item.emerald, cost);
			ItemStack result = EnchantmentHelper.addRandomEnchantment(rand, input.copy(), 5 + rand.nextInt(15));

			MerchantRecipe trade = new MerchantRecipe(input, emeralds, result, level);
			
			if (this.isMandatory())
				trade.setMandatory();
			
			return trade;
		}

		@Override
		public boolean matchesMerchantRecipe(MerchantRecipe trade) {
			return trade.getItemToBuy().itemID == this.itemID && 
					(!trade.hasSecondItemToBuy() || trade.getSecondItemToBuy().itemID == Item.emerald.itemID) && 
					trade.getItemToSell().itemID == this.itemID; 
		}
	}

	public static class WeightedMerchantRecipeEntry extends WeightedMerchantEntry {
		public final int input1ID;
		public final int input1Metadata;
		public final int input1MinCount;
		public final int input1MaxCount;

		public final int input2ID;
		public final int input2Metadata;
		public final int input2MinCount;
		public final int input2MaxCount;

		public final int resultID;
		public final int resultMetadata;
		public final int resultMinCount;
		public final int resultMaxCount;

		private boolean randomizeMeta1 = false;
		private int[] randomMetas1;
		private boolean randomizeMeta2 = false;
		private int[] randomMetas2;
		private boolean randomizeMetaResult = false;
		private int[] randomMetasResult;

		public WeightedMerchantRecipeEntry(int input1ID, int input1Metadata, int input1MinCount, int input1MaxCount,
				int input2ID, int input2Metadata, int input2MinCount, int input2MaxCount,
				int resultID, int resultMetadata, int resultMinCount, int resultMaxCount,
				float weight, int level) {
			super(weight, level);

			this.input1ID = input1ID;
			this.input1Metadata = input1Metadata;
			this.input1MinCount = input1MinCount;
			this.input1MaxCount = input1MaxCount;

			this.input2ID = input2ID;
			this.input2Metadata = input2Metadata;
			this.input2MinCount = input2MinCount;
			this.input2MaxCount = input2MaxCount;

			this.resultID = resultID;
			this.resultMetadata = resultMetadata;
			this.resultMinCount = resultMinCount;
			this.resultMaxCount = resultMaxCount;
		}

		public WeightedMerchantRecipeEntry(int inputID, int inputMetadata, int inputMinCount, int inputMaxCount,
				int resultID, int resultMetadata, int resultMinCount, int resultMaxCount,
				float weight, int level) {
			super(weight, level);

			this.input1ID = inputID;
			this.input1Metadata = inputMetadata;
			this.input1MinCount = inputMinCount;
			this.input1MaxCount = inputMaxCount;

			this.input2ID = 0;
			this.input2Metadata = 0;
			this.input2MinCount = 0;
			this.input2MaxCount = 0;

			this.resultID = resultID;
			this.resultMetadata = resultMetadata;
			this.resultMinCount = resultMinCount;
			this.resultMaxCount = resultMaxCount;
		}

		@Override
		public boolean equals(WeightedMerchantEntry entry) {
			if (entry instanceof WeightedMerchantRecipeEntry) {
				WeightedMerchantRecipeEntry recipeEntry = (WeightedMerchantRecipeEntry) entry;
				
				return this.input1ID == recipeEntry.input1ID &&
						this.input1Metadata == recipeEntry.input1Metadata &&
						this.input2ID == recipeEntry.input2ID &&
						this.input2Metadata == recipeEntry.input2Metadata &&
						this.resultID == recipeEntry.resultID &&
						this.resultMetadata == recipeEntry.resultMetadata;
			}
			else {
				return false;
			}
		}

		@Override
		public MerchantRecipe generateRecipe(Random rand) {
			int count1 = MathHelper.getRandomIntegerInRange(rand, input1MinCount, input1MaxCount);
			int count2 = MathHelper.getRandomIntegerInRange(rand, input2MinCount, input2MaxCount);
			int countResult = MathHelper.getRandomIntegerInRange(rand, resultMinCount, resultMaxCount);

			int meta1 = input1Metadata;
			int meta2 = input2Metadata;
			int metaResult = resultMetadata;

			if (randomizeMeta1) {
				meta1 = randomMetas1[rand.nextInt(randomMetas1.length)];
			}
			if (randomizeMeta2) {
				meta2 = randomMetas2[rand.nextInt(randomMetas2.length)];
			}
			if (randomizeMetaResult) {
				metaResult = randomMetasResult[rand.nextInt(randomMetasResult.length)];
			}

			ItemStack input1 = new ItemStack(Item.itemsList[input1ID], count1, meta1);

			ItemStack input2 = null;
			if (input2ID != 0)
				input2 = new ItemStack(Item.itemsList[input2ID], count2, meta2);

			ItemStack result = new ItemStack(Item.itemsList[resultID], countResult, metaResult);

			MerchantRecipe trade = new MerchantRecipe(input1, input2, result, level);
			
			if (this.isMandatory())
				trade.setMandatory();
			
			return trade;
		}

		public WeightedMerchantEntry setRandomMetas(int[] randomMetas, int slot) {
			switch (slot) {
			case 0:
				this.randomizeMeta1 = true;
				this.randomMetas1 = randomMetas;
				break;
			case 1:
				this.randomizeMeta2 = true;
				this.randomMetas2 = randomMetas;
				break;
			case 2:
				this.randomizeMetaResult = true;
				this.randomMetasResult = randomMetas;
				break;
			}

			return this;
		}

		@Override
		public boolean matchesMerchantRecipe(MerchantRecipe trade) {
			return trade.getItemToBuy().itemID == this.input1ID && 
					(trade.getItemToBuy().getItemDamage() == this.input1Metadata || this.randomizeMeta1) &&
					(!trade.hasSecondItemToBuy() || 
							(trade.getSecondItemToBuy().itemID == this.input2ID && 
							(trade.getSecondItemToBuy().getItemDamage() == this.input2Metadata || this.randomizeMeta2))) && 
					trade.getItemToSell().itemID == this.resultID && 
					(trade.getItemToSell().getItemDamage() == this.resultMetadata || this.randomizeMetaResult);
		}
	}
	
	public static abstract class TradeEffect {
		public abstract void playEffect(FCEntityVillager villager);
	}
	
	public static abstract class TradeConditional {
		public abstract boolean shouldAddTrade(FCEntityVillager villager);
	}

	//----------- Client Side Functionality -----------//

	@Override
	public void handleHealthUpdate(byte bUpdateType) {
		super.handleHealthUpdate(bUpdateType);

		if (bUpdateType == 14) {
			// item collect sound on villager restock
			worldObj.playSound(posX, posY, posZ, "random.pop", 
					0.25F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1F) * 2F);
		}
	}
}