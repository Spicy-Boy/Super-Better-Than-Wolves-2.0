package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.server.MinecraftServer;

public class WorldServer extends World
{
    private final MinecraftServer mcServer;
    private final EntityTracker theEntityTracker;
    // FCMOD: Changed
    //private final PlayerManager thePlayerManager;
    private final FCChunkTracker m_chunkTracker;
    // END FCMOD
    
    // FCNOTE: Contains NextTickListEntry objects with updates scheduled for blocks
    private Set field_73064_N;

    /** All work to do in future ticks. */
    private TreeSet pendingTickListEntries;
    public ChunkProviderServer theChunkProviderServer;

    /** Whether or not level saving is enabled */
    public boolean levelSaving;

    /** is false if there are no players */
    private boolean allPlayersSleeping;
    private int updateEntityTick = 0;
    private final Teleporter field_85177_Q;

    /**
     * Double buffer of ServerBlockEventList[] for holding pending BlockEventData's
     */
    private ServerBlockEventList[] blockEventCache = new ServerBlockEventList[] {new ServerBlockEventList((ServerBlockEvent)null), new ServerBlockEventList((ServerBlockEvent)null)};

    /**
     * The index into the blockEventCache; either 0, or 1, toggled in sendBlockEventPackets  where all BlockEvent are
     * applied locally and send to clients.
     */
    private int blockEventCacheIndex = 0;
    private static final WeightedRandomChestContent[] bonusChestContent = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Item.stick.itemID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.planks.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.wood.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Item.axeStone.itemID, 0, 1, 1, 3), new WeightedRandomChestContent(Item.axeWood.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.pickaxeStone.itemID, 0, 1, 1, 3), new WeightedRandomChestContent(Item.pickaxeWood.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 2, 3, 5), new WeightedRandomChestContent(Item.bread.itemID, 0, 2, 3, 3)};
    private ArrayList field_94579_S = new ArrayList();

    /** An IntHashMap of entity IDs (integers) to their Entity objects. */
    private IntHashMap entityIdMap;

    public WorldServer(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, Profiler par6Profiler, ILogAgent par7ILogAgent)
    {
        super(par2ISaveHandler, par3Str, par5WorldSettings, WorldProvider.getProviderForDimension(par4), par6Profiler, par7ILogAgent);
        this.mcServer = par1MinecraftServer;
        
        // FCMOD: Added
        saveHandler.LoadModSpecificData( this );
        // END FCMOD
        
        this.theEntityTracker = new EntityTracker(this);
        
        // FCMOD: Changed
        //this.thePlayerManager = new PlayerManager(this, par1MinecraftServer.getConfigurationManager().getViewDistance());
        m_chunkTracker = new FCChunkTracker( this, 
        	par1MinecraftServer.getConfigurationManager().getViewDistance());
        // END FCMOD

        if (this.entityIdMap == null)
        {
            this.entityIdMap = new IntHashMap();
        }

        if (this.field_73064_N == null)
        {
            this.field_73064_N = new HashSet();
        }

        if (this.pendingTickListEntries == null)
        {
            this.pendingTickListEntries = new TreeSet();
        }

        this.field_85177_Q = new Teleporter(this);
        this.worldScoreboard = new ServerScoreboard(par1MinecraftServer);
        ScoreboardSaveData var8 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");

        if (var8 == null)
        {
            var8 = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", var8);
        }

        var8.func_96499_a(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(var8);
    }

    /**
     * Runs a single tick for the world
     */
    public void tick()
    {
        super.tick();

        if (this.getWorldInfo().isHardcoreModeEnabled() && this.difficultySetting < 3)
        {
            this.difficultySetting = 3;
        }
        // FCMOD: Added to eliminate peaceful and easy difficulties
        else if ( difficultySetting < 2 )
        {
        	difficultySetting = 2;
        }
        // END FCMOD

        this.provider.worldChunkMgr.cleanupCache();

        //AARON commented this out in order to stop the time skip for sleeping
//      if (this.areAllPlayersAsleep())
//      {
//          boolean var1 = false;
//
//          if (this.spawnHostileMobs && this.difficultySetting >= 1)
//          {
//              ;
//          }
//
//          if (!var1)
//          {
//              long var2 = this.worldInfo.getWorldTime() + 24000L;
//              this.worldInfo.setWorldTime(var2 - var2 % 24000L);
//              this.wakeAllPlayers();
//          }
//      }

        this.theProfiler.startSection("mobSpawner");

        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning"))
        {
        	// FCMOD: Code change to prevent animal spawning after chunk generation
        	/*
            SpawnerAnimals.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
            */
            SpawnerAnimals.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, false);
            // END FCMOD
        }

        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        int var4 = this.calculateSkylightSubtracted(1.0F);

        if (var4 != this.skylightSubtracted)
        {
            this.skylightSubtracted = var4;
        }

        this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
        this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickTiles");
        this.tickBlocksAndAmbiance();
        this.theProfiler.endStartSection("chunkMap");
        // FCMOD: Changed
        //this.thePlayerManager.updatePlayerInstances();
        m_chunkTracker.Update();
        // END FCMOD
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        // FCMOD: Removed
        //this.villageSiegeObj.tick();
        // END FCMOD
        this.theProfiler.endStartSection("portalForcer");
        this.field_85177_Q.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.sendAndApplyBlockEvents();
    }

    /**
     * only spawns creatures allowed by the chunkProvider
     */
    public SpawnListEntry spawnRandomCreature(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        List var5 = this.getChunkProvider().getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
        return var5 != null && !var5.isEmpty() ? (SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var5) : null;
    }

    /**
     * Updates the flag that indicates whether or not all players in the world are sleeping.
     */
    public void updateAllPlayersSleepingFlag()
    {
        this.allPlayersSleeping = !this.playerEntities.isEmpty();
        Iterator var1 = this.playerEntities.iterator();

        while (var1.hasNext())
        {
            EntityPlayer var2 = (EntityPlayer)var1.next();

            if (!var2.isPlayerSleeping())
            {
                this.allPlayersSleeping = false;
                break;
            }
        }
    }

    protected void wakeAllPlayers()
    {
        this.allPlayersSleeping = false;
        Iterator var1 = this.playerEntities.iterator();

        while (var1.hasNext())
        {
            EntityPlayer var2 = (EntityPlayer)var1.next();

            if (var2.isPlayerSleeping())
            {
                var2.wakeUpPlayer(false, false, true);
            }
        }

        this.resetRainAndThunder();
    }

    private void resetRainAndThunder()
    {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }

    public boolean areAllPlayersAsleep()
    {
        if (this.allPlayersSleeping && !this.isRemote)
        {
            Iterator var1 = this.playerEntities.iterator();
            EntityPlayer var2;

            do
            {
                if (!var1.hasNext())
                {
                    return true;
                }

                var2 = (EntityPlayer)var1.next();
            }
            while (var2.isPlayerFullyAsleep());

            return false;
        }
        else
        {
            return false;
        }
    }

    /**
     * plays random cave ambient sounds and runs updateTick on random blocks within each chunk in the vacinity of a
     * player
     */
    protected void tickBlocksAndAmbiance()
    {
        super.tickBlocksAndAmbiance();
        int var1 = 0;
        int var2 = 0;
        // FCMOD: Changed
        //Iterator var3 = this.activeChunkSet.iterator();
        Iterator<ChunkCoordIntPair> var3 = m_activeChunksCoordsList.iterator();
        // END FCMOD

        while (var3.hasNext())
        {
            ChunkCoordIntPair var4 = (ChunkCoordIntPair)var3.next();
            int var5 = var4.chunkXPos * 16;
            int var6 = var4.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var7 = this.getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
            this.moodSoundAndLightCheck(var5, var6, var7);
            this.theProfiler.endStartSection("tickChunk");
            var7.updateSkylight();
            this.theProfiler.endStartSection("thunder");
            int var8;
            int var9;
            int var10;
            int var11;

            // FCMOD: Changed
            //if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering())
            if ( rand.nextInt( 50000 ) == 0 && isRaining() && isThundering() )
        	// END FCMOD 
            {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                var8 = this.updateLCG >> 2;
                var9 = var5 + (var8 & 15);
                var10 = var6 + (var8 >> 8 & 15);
                var11 = this.getPrecipitationHeight(var9, var10);

                // FCMOD: Changed
                //if (this.canLightningStrikeAt(var9, var11, var10))
                if ( CanLightningStrikeAtPos( var9, var11, var10 ) )
            	// END FCMOD
                {
                	// FCMOD: Changed
                    //this.addWeatherEffect(new EntityLightningBolt(this, (double)var9, (double)var11, (double)var10));
                	FCUtilsBlockPos strikePos = new FCUtilsBlockPos( var9, var11, var10 );
            		
            		AdjustLightningPosForSurroundings( strikePos );
            		
                    if ( IsBlockPosActive( strikePos.i, strikePos.j, strikePos.k ) )
                    {
                        addWeatherEffect( new FCEntityLightningBolt( this, (double)strikePos.i + 0.5D, 
                        	(double)strikePos.j, (double)strikePos.k + 0.5D ) );
                    }
                	// END FCMOD
                }
            }

            this.theProfiler.endStartSection("iceandsnow");
            int var13;

            if (this.rand.nextInt(16) == 0)
            {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                var8 = this.updateLCG >> 2;
                var9 = var8 & 15;
                var10 = var8 >> 8 & 15;
                var11 = this.getPrecipitationHeight(var9 + var5, var10 + var6);

                if (this.isBlockFreezableNaturally(var9 + var5, var11 - 1, var10 + var6))
                {
                    this.setBlock(var9 + var5, var11 - 1, var10 + var6, Block.ice.blockID);
                }

                if (this.isRaining() && this.canSnowAt(var9 + var5, var11, var10 + var6))
                {
                    this.setBlock(var9 + var5, var11, var10 + var6, Block.snow.blockID);
                }
                // FCMOD: Added
                else if (this.isRaining() && this.canSnowAt(var9 + var5, var11 + 1, var10 + var6))
                {
                    setBlock(var9 + var5, var11 + 1, var10 + var6, Block.snow.blockID);
                }
                // END FCMOD

                if (this.isRaining())
                {
                    BiomeGenBase var12 = this.getBiomeGenForCoords(var9 + var5, var10 + var6);

                    // FCMOD: Changed for clarity
                    //if (var12.canSpawnLightningBolt())
                    if ( var12.CanRainInBiome() )
                	// END FCMOD
                    {
                        var13 = this.getBlockId(var9 + var5, var11 - 1, var10 + var6);

                        if (var13 != 0)
                        {
                            Block.blocksList[var13].fillWithRain(this, var9 + var5, var11 - 1, var10 + var6);
                        }
                    }
                }
            }

            this.theProfiler.endStartSection("tickTiles");
            ExtendedBlockStorage[] var19 = var7.getBlockStorageArray();
            var9 = var19.length;

            for (var10 = 0; var10 < var9; ++var10)
            {
                ExtendedBlockStorage var21 = var19[var10];

                if (var21 != null && var21.getNeedsRandomTick())
                {
                    for (int var20 = 0; var20 < 3; ++var20)
                    {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        var13 = this.updateLCG >> 2;
                        int var14 = var13 & 15;
                        int var15 = var13 >> 8 & 15;
                        int var16 = var13 >> 16 & 15;
                        int var17 = var21.getExtBlockID(var14, var16, var15);
                        ++var2;
                        Block var18 = Block.blocksList[var17];

                        if (var18 != null && var18.getTickRandomly())
                        {
                            ++var1;
                            // FCMOD: Code changed
                            /*
                            var18.updateTick(this, var14 + var5, var16 + var21.getYLocation(), var15 + var6, this.rand);
                            */
                            var18.RandomUpdateTick(this, var14 + var5, var16 + var21.getYLocation(), var15 + var6, rand);
                            // END FCMOD
                        }
                    }
                }
            }

            this.theProfiler.endSection();
        }

        // FCMOD: Added
        ModUpdateTick();
        // END FCMOD
    }

    /**
     * Returns true if the given block will receive a scheduled tick in the future. Args: X, Y, Z, blockID
     */
    public boolean isBlockTickScheduled(int par1, int par2, int par3, int par4)
    {
        NextTickListEntry var5 = new NextTickListEntry(par1, par2, par3, par4);
        return this.field_94579_S.contains(var5);
    }

    /**
     * Used to schedule a call to the updateTick method on the specified block.
     */
    public void scheduleBlockUpdate(int par1, int par2, int par3, int par4, int par5)
    {
        this.func_82740_a(par1, par2, par3, par4, par5, 0);
    }

    public void func_82740_a(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
        // FCMOD: Removed
        //byte var8 = 0;
        // END FCMOD

        if (this.scheduledUpdatesAreImmediate && par4 > 0)
        {
            if (Block.blocksList[par4].func_82506_l())
            {
            	// FCCHUNK: Decide on updates around original spawn
            	// FCMOD: Changed
            	/*
                if (this.checkChunksExist(var7.xCoord - var8, var7.yCoord - var8, var7.zCoord - var8, var7.xCoord + var8, var7.yCoord + var8, var7.zCoord + var8))
                */
                if ( IsBlockPosActive( var7.xCoord, var7.yCoord, var7.zCoord ) )
            	// END FCMOD
                {
                    int var9 = this.getBlockId(var7.xCoord, var7.yCoord, var7.zCoord);

                    if (var9 == var7.blockID && var9 > 0)
                    {
                        Block.blocksList[var9].updateTick(this, var7.xCoord, var7.yCoord, var7.zCoord, this.rand);
                    }
                }

                return;
            }

            par5 = 1;
        }

    	// FCCHUNK: Decide on updates around original spawn
    	// FCMOD: Changed
    	/*
        if (this.checkChunksExist(par1 - var8, par2 - var8, par3 - var8, par1 + var8, par2 + var8, par3 + var8))
        */
        if ( IsBlockPosActive( par1, par2, par3 ) )
        // END FCMOD
        {
            if (par4 > 0)
            {
                var7.setScheduledTime((long)par5 + this.worldInfo.getWorldTotalTime());
                var7.func_82753_a(par6);
            }

            if (!this.field_73064_N.contains(var7))
            {
                this.field_73064_N.add(var7);
                this.pendingTickListEntries.add(var7);
            }
        }
    }

    /**
     * Schedules a block update from the saved information in a chunk. Called when the chunk is loaded.
     */
    public void scheduleBlockUpdateFromLoad(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
        var7.func_82753_a(par6);

        if (par4 > 0)
        {
            var7.setScheduledTime((long)par5 + this.worldInfo.getWorldTotalTime());
        }

        if (!this.field_73064_N.contains(var7))
        {
            this.field_73064_N.add(var7);
            this.pendingTickListEntries.add(var7);
        }
    }

    /**
     * Updates (and cleans up) entities and tile entities
     */
    public void updateEntities()
    {
    	// FCMOD: Removed pausing of updates when no players in world.  Replaced elsewhere
    	/*
        if (this.playerEntities.isEmpty())
        {
            if (this.updateEntityTick++ >= 1200)
            {
                return;
            }
        }
        else
        {
            this.resetUpdateEntityTick();
        }
        */
    	// END FCMOD

        super.updateEntities();
    }

    /**
     * Resets the updateEntityTick field to 0
     */
    public void resetUpdateEntityTick()
    {
        this.updateEntityTick = 0;
    }

    /**
     * Runs through the list of updates to run and ticks them
     */
    public boolean tickUpdates(boolean par1)
    {
        int var2 = this.pendingTickListEntries.size();

        if (var2 != this.field_73064_N.size())
        {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        else
        {
            if (var2 > 1000)
            {
                var2 = 1000;
            }

            this.theProfiler.startSection("cleaning");
            NextTickListEntry var4;

            for (int var3 = 0; var3 < var2; ++var3)
            {
                var4 = (NextTickListEntry)this.pendingTickListEntries.first();

                if (!par1 && var4.scheduledTime > this.worldInfo.getWorldTotalTime())
                {
                    break;
                }

                this.pendingTickListEntries.remove(var4);
                this.field_73064_N.remove(var4);
                this.field_94579_S.add(var4);
            }

            this.theProfiler.endSection();
            this.theProfiler.startSection("ticking");
            Iterator var14 = this.field_94579_S.iterator();

            while (var14.hasNext())
            {
                var4 = (NextTickListEntry)var14.next();
                var14.remove();
            	// FCCHUNK: Decide on updates around original spawn
                // FCMOD: Changed to prevent neighboring chunks inadvertantly being loaded during
                // updates.  This was causing stuff like fire loading a ton of chunks in the nether.
                /*
                byte var5 = 0;
                
                if (this.checkChunksExist(var4.xCoord - var5, var4.yCoord - var5, var4.zCoord - var5, var4.xCoord + var5, var4.yCoord + var5, var4.zCoord + var5))
                */
                if ( IsBlockPosActive( var4.xCoord, var4.yCoord, var4.zCoord ) )
            	// END FCMOD
                {
                    int var6 = this.getBlockId(var4.xCoord, var4.yCoord, var4.zCoord);

                    if (var6 > 0 && Block.isAssociatedBlockID(var6, var4.blockID))
                    {
                        try
                        {
                            Block.blocksList[var6].updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
                        }
                        catch (Throwable var13)
                        {
                            CrashReport var8 = CrashReport.makeCrashReport(var13, "Exception while ticking a block");
                            CrashReportCategory var9 = var8.makeCategory("Block being ticked");
                            int var10;

                            try
                            {
                                var10 = this.getBlockMetadata(var4.xCoord, var4.yCoord, var4.zCoord);
                            }
                            catch (Throwable var12)
                            {
                                var10 = -1;
                            }

                            CrashReportCategory.func_85068_a(var9, var4.xCoord, var4.yCoord, var4.zCoord, var6, var10);
                            throw new ReportedException(var8);
                        }
                    }
                }
            	// FCCHUNK: Decide on updates around original spawn
                // FCMOD: Removed to reduce unnecessary overhead of recheduling ticks in inactive chunks
                /*
                else
                {
                	// FCMOD: Changed to not immediately retick on next update to ease on performance
                    //this.scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.blockID, 0);
                    scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.blockID, 
                    	Block.blocksList[var4.blockID].tickRate( this ) );
                    // END FCMOD
                }
                */
                // END FCMOD
            }

            this.theProfiler.endSection();
            this.field_94579_S.clear();
            return !this.pendingTickListEntries.isEmpty();
        }
    }

    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
    {
        ArrayList var3 = null;
        ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
        int var5 = (var4.chunkXPos << 4) - 2;
        int var6 = var5 + 16 + 2;
        int var7 = (var4.chunkZPos << 4) - 2;
        int var8 = var7 + 16 + 2;

        for (int var9 = 0; var9 < 2; ++var9)
        {
            Iterator var10;

            if (var9 == 0)
            {
                var10 = this.pendingTickListEntries.iterator();
            }
            else
            {
                var10 = this.field_94579_S.iterator();

                if (!this.field_94579_S.isEmpty())
                {
                    System.out.println(this.field_94579_S.size());
                }
            }

            while (var10.hasNext())
            {
                NextTickListEntry var11 = (NextTickListEntry)var10.next();

                if (var11.xCoord >= var5 && var11.xCoord < var6 && var11.zCoord >= var7 && var11.zCoord < var8)
                {
                    if (par2)
                    {
                        this.field_73064_N.remove(var11);
                        var10.remove();
                    }

                    if (var3 == null)
                    {
                        var3 = new ArrayList();
                    }

                    var3.add(var11);
                }
            }
        }

        return var3;
    }

    /**
     * Will update the entity in the world if the chunk the entity is in is currently loaded or its forced to update.
     * Args: entity, forceUpdate
     */
    public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
    {
        if (!this.mcServer.getCanSpawnAnimals() && (par1Entity instanceof EntityAnimal || par1Entity instanceof EntityWaterMob))
        {
            par1Entity.setDead();
        }

        if (!this.mcServer.getCanSpawnNPCs() && par1Entity instanceof INpc)
        {
            par1Entity.setDead();
        }

        if (!(par1Entity.riddenByEntity instanceof EntityPlayer))
        {
            // Added so that creatures in periphery chunks can still despawn
            int iEntityI = MathHelper.floor_double( par1Entity.posX );
            int iEntityK = MathHelper.floor_double( par1Entity.posZ );
            
            if ( par2 && !IsBlockPosActive( iEntityI, 0, iEntityK ) && par1Entity.addedToChunk  )
            {
                if ( par1Entity.ridingEntity == null )
                {
                	par1Entity.OutOfUpdateRangeUpdate();
                }
                
                return; // intentionally skip super call
            }
            // END FCMOD
            
            super.updateEntityWithOptionalForce(par1Entity, par2);
        }
    }

    /**
     * direct call to super.updateEntityWithOptionalForce
     */
    public void uncheckedUpdateEntity(Entity par1Entity, boolean par2)
    {
        super.updateEntityWithOptionalForce(par1Entity, par2);
    }

    /**
     * Creates the chunk provider for this world. Called in the constructor. Retrieves provider from worldProvider?
     */
    protected IChunkProvider createChunkProvider()
    {
        IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
        this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
        return this.theChunkProviderServer;
    }

    // FCMOD: Added (server only) alias to match client
    public List getAllTileEntityInBox( int par1, int par2, int par3, int par4, int par5, int par6 )
    {
    	return getTileEntityList( par1, par2, par3, par4, par5, par6 );
    }
    // END FCMOD
    /**
     * get a list of tileEntity's
     */
    public List getTileEntityList(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        ArrayList var7 = new ArrayList();

        for (int var8 = 0; var8 < this.loadedTileEntityList.size(); ++var8)
        {
            TileEntity var9 = (TileEntity)this.loadedTileEntityList.get(var8);

            if (var9.xCoord >= par1 && var9.yCoord >= par2 && var9.zCoord >= par3 && var9.xCoord < par4 && var9.yCoord < par5 && var9.zCoord < par6)
            {
                var7.add(var9);
            }
        }

        return var7;
    }

    /**
     * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
     */
    public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
    {
        return !this.mcServer.func_96290_a(this, par2, par3, par4, par1EntityPlayer);
    }

    protected void initialize(WorldSettings par1WorldSettings)
    {
        if (this.entityIdMap == null)
        {
            this.entityIdMap = new IntHashMap();
        }

        if (this.field_73064_N == null)
        {
            this.field_73064_N = new HashSet();
        }

        if (this.pendingTickListEntries == null)
        {
            this.pendingTickListEntries = new TreeSet();
        }

        this.createSpawnPosition(par1WorldSettings);
        super.initialize(par1WorldSettings);
    }

    /**
     * creates a spawn position at random within 256 blocks of 0,0
     */
    protected void createSpawnPosition(WorldSettings par1WorldSettings)
    {
        if (!this.provider.canRespawnHere())
        {
            this.worldInfo.setSpawnPosition(0, this.provider.getAverageGroundLevel(), 0);
        }
        else
        {
            this.findingSpawnPoint = true;
            WorldChunkManager var2 = this.provider.worldChunkMgr;
            List var3 = var2.getBiomesToSpawnIn();
            Random var4 = new Random(this.getSeed());
            ChunkPosition var5 = var2.findBiomePosition(0, 0, 256, var3, var4);
            int var6 = 0;
            int var7 = this.provider.getAverageGroundLevel();
            int var8 = 0;

            if (var5 != null)
            {
                var6 = var5.x;
                var8 = var5.z;
            }
            else
            {
                this.getWorldLogAgent().func_98236_b("Unable to find spawn biome");
            }

            int var9 = 0;

            while (!this.provider.canCoordinateBeSpawn(var6, var8))
            {
                var6 += var4.nextInt(64) - var4.nextInt(64);
                var8 += var4.nextInt(64) - var4.nextInt(64);
                ++var9;

                if (var9 == 1000)
                {
                    break;
                }
            }

            this.worldInfo.setSpawnPosition(var6, var7, var8);
            this.findingSpawnPoint = false;

            if (par1WorldSettings.isBonusChestEnabled())
            {
                this.createBonusChest();
            }
        }
    }

    /**
     * Creates the bonus chest in the world.
     */
    protected void createBonusChest()
    {
    	// FCMOD: Changed
        //WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(bonusChestContent, 10);
        FCWorldGeneratorBonusBasket var1 = new FCWorldGeneratorBonusBasket();
    	// END FCMOD

        for (int var2 = 0; var2 < 10; ++var2)
        {
            int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
            int var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
            int var5 = this.getTopSolidOrLiquidBlock(var3, var4) + 1;

            if (var1.generate(this, this.rand, var3, var5, var4))
            {
                break;
            }
        }
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates getEntrancePortalLocation()
    {
        return this.provider.getEntrancePortalLocation();
    }

    /**
     * Saves all chunks to disk while updating progress bar.
     */
    public void saveAllChunks(boolean par1, IProgressUpdate par2IProgressUpdate) throws MinecraftException
    {
        if (this.chunkProvider.canSave())
        {
            if (par2IProgressUpdate != null)
            {
                par2IProgressUpdate.displaySavingString("Saving level");
            }

            this.saveLevel();

            if (par2IProgressUpdate != null)
            {
                par2IProgressUpdate.displayLoadingString("Saving chunks");
            }

            this.chunkProvider.saveChunks(par1, par2IProgressUpdate);

            // FCMOD: Added
            saveHandler.SaveModSpecificData( this );
            // END FCMOD
        }
    }

    public void func_104140_m()
    {
        if (this.chunkProvider.canSave())
        {
            this.chunkProvider.func_104112_b();
        }
    }

    /**
     * Saves the chunks to disk.
     */
    protected void saveLevel() throws MinecraftException
    {
        this.checkSessionLock();
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    protected void obtainEntitySkin(Entity par1Entity)
    {
        super.obtainEntitySkin(par1Entity);
        this.entityIdMap.addKey(par1Entity.entityId, par1Entity);
        Entity[] var2 = par1Entity.getParts();

        if (var2 != null)
        {
            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                this.entityIdMap.addKey(var2[var3].entityId, var2[var3]);
            }
        }
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    protected void releaseEntitySkin(Entity par1Entity)
    {
        super.releaseEntitySkin(par1Entity);
        this.entityIdMap.removeObject(par1Entity.entityId);
        Entity[] var2 = par1Entity.getParts();

        if (var2 != null)
        {
            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                this.entityIdMap.removeObject(var2[var3].entityId);
            }
        }
    }

    /**
     * Returns the Entity with the given ID, or null if it doesn't exist in this World.
     */
    public Entity getEntityByID(int par1)
    {
        return (Entity)this.entityIdMap.lookup(par1);
    }

    /**
     * adds a lightning bolt to the list of lightning bolts in this world.
     */
    public boolean addWeatherEffect(Entity par1Entity)
    {
        if (super.addWeatherEffect(par1Entity))
        {
        	// FCMOD: Changed to avoid modding Packet71Weather        	
            //this.mcServer.getConfigurationManager().sendPacketToPlayersAroundPoint(par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512.0D, this.provider.dimensionId, new Packet71Weather(par1Entity));
        	Packet71Weather packet = new Packet71Weather( par1Entity );
        	
        	if ( par1Entity instanceof FCEntityLightningBolt )
        	{
        		packet.isLightningBolt = 1;
        	}
        	
            this.mcServer.getConfigurationManager().sendPacketToPlayersAroundPoint(
            	par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512.0D, provider.dimensionId, 
            	packet );
            // END FCMOD
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * sends a Packet 38 (Entity Status) to all tracked players of that entity
     */
    public void setEntityState(Entity par1Entity, byte par2)
    {
        Packet38EntityStatus var3 = new Packet38EntityStatus(par1Entity.entityId, par2);
        this.getEntityTracker().sendPacketToTrackedPlayersAndTrackedEntity(par1Entity, var3);
    }

    /**
     * returns a new explosion. Does initiation (at time of writing Explosion is not finished)
     */
    public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
    {
        Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.isSmoking = par10;
        var11.doExplosionA();
        var11.doExplosionB(false);

        if (!par10)
        {
            var11.affectedBlockPositions.clear();
        }

        Iterator var12 = this.playerEntities.iterator();

        while (var12.hasNext())
        {
            EntityPlayer var13 = (EntityPlayer)var12.next();

            if (var13.getDistanceSq(par2, par4, par6) < 4096.0D)
            {
                ((EntityPlayerMP)var13).playerNetServerHandler.sendPacket(new Packet60Explosion(par2, par4, par6, par8, var11.affectedBlockPositions, (Vec3)var11.func_77277_b().get(var13)));
            }
        }

        return var11;
    }

    /**
     * Adds a block event with the given Args to the blockEventCache. During the next tick(), the block specified will
     * have its onBlockEvent handler called with the given parameters. Args: X,Y,Z, BlockID, EventID, EventParameter
     */
    public void addBlockEvent(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        BlockEventData var7 = new BlockEventData(par1, par2, par3, par4, par5, par6);
        Iterator var8 = this.blockEventCache[this.blockEventCacheIndex].iterator();
        BlockEventData var9;

        do
        {
            if (!var8.hasNext())
            {
                this.blockEventCache[this.blockEventCacheIndex].add(var7);
                return;
            }

            var9 = (BlockEventData)var8.next();
        }
        while (!var9.equals(var7));
    }

    /**
     * Send and apply locally all pending BlockEvents to each player with 64m radius of the event.
     */
    private void sendAndApplyBlockEvents()
    {
        while (!this.blockEventCache[this.blockEventCacheIndex].isEmpty())
        {
            int var1 = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= 1;
            Iterator var2 = this.blockEventCache[var1].iterator();

            while (var2.hasNext())
            {
                BlockEventData var3 = (BlockEventData)var2.next();

                if (this.onBlockEventReceived(var3))
                {
                    this.mcServer.getConfigurationManager().sendPacketToPlayersAroundPoint((double)var3.getX(), (double)var3.getY(), (double)var3.getZ(), 64.0D, this.provider.dimensionId, new Packet54PlayNoteBlock(var3.getX(), var3.getY(), var3.getZ(), var3.getBlockID(), var3.getEventID(), var3.getEventParameter()));
                }
            }

            this.blockEventCache[var1].clear();
        }
    }

    /**
     * Called to apply a pending BlockEvent to apply to the current world.
     */
    private boolean onBlockEventReceived(BlockEventData par1BlockEventData)
    {
        int var2 = this.getBlockId(par1BlockEventData.getX(), par1BlockEventData.getY(), par1BlockEventData.getZ());
        return var2 == par1BlockEventData.getBlockID() ? Block.blocksList[var2].onBlockEventReceived(this, par1BlockEventData.getX(), par1BlockEventData.getY(), par1BlockEventData.getZ(), par1BlockEventData.getEventID(), par1BlockEventData.getEventParameter()) : false;
    }

    /**
     * Syncs all changes to disk and wait for completion.
     */
    public void flush()
    {
        this.saveHandler.flush();
    }

    /**
     * Updates all weather states.
     */
    // FCMOD: Removed and replaced later
    /*
    protected void updateWeather()
    {
        boolean var1 = this.isRaining();
        super.updateWeather();

        if (var1 != this.isRaining())
        {
            if (var1)
            {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(2, 0));
            }
            else
            {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(1, 0));
            }
        }
    }
    */
    // END FCMOD

    /**
     * Gets the MinecraftServer.
     */
    public MinecraftServer getMinecraftServer()
    {
        return this.mcServer;
    }

    /**
     * Gets the EntityTracker
     */
    public EntityTracker getEntityTracker()
    {
        return this.theEntityTracker;
    }

    // FCMOD: Removed
    /*
    public PlayerManager getPlayerManager()
    {
        return this.thePlayerManager;
    }
    */
    // END FCMOD

    public Teleporter getDefaultTeleporter()
    {
        return this.field_85177_Q;
    }

    // FCMOD: Added
    private boolean m_bHasTicked = false;
    
    protected LinkedList<ChunkCoordIntPair> m_chunksToCheckForUnloadList = 
    	new LinkedList<ChunkCoordIntPair>();
    
    private long m_lNoPlayersOnServerTickCount = 0;
    
    // MinecraftServer loads out to 192 blocks, or 12 chunks. One more for wiggle.
    
    private final int m_iChunksAroundSpawnToCheckForUnload = 13;  
    
    @Override
    public void ModSpecificTick()
    {
    	if ( !m_bHasTicked )
    	{
    		m_bHasTicked = true;

    		MarkChunksAroundSpawnToCheckForUnload();
    	}    	    	
    	
		CheckChunksToUnloadList();
    }    
    
    public void AddChunkToCheckForUnloadList( int iChunkX, int iChunkZ )
    {
    	m_chunksToCheckForUnloadList.add( new ChunkCoordIntPair( iChunkX, iChunkZ ) );
    }
    
    public void AddChunkRangeToCheckForUnloadList( int iMinChunkX, int iMinChunkZ, 
    	int iMaxChunkX, int iMaxChunkZ )
    {
    	for ( int iTempChunkX = iMinChunkX; iTempChunkX <= iMaxChunkX; iTempChunkX++ )
    	{
        	for ( int iTempChunkZ = iMinChunkZ; iTempChunkZ <= iMaxChunkZ; iTempChunkZ++ )
        	{
        		AddChunkToCheckForUnloadList( iTempChunkX, iTempChunkZ );
        	}
    	}
    }
    
    private void CheckChunksToUnloadList()
    {
    	if ( !m_chunksToCheckForUnloadList.isEmpty() )
    	{
        	Iterator<ChunkCoordIntPair> tempIterator = m_chunksToCheckForUnloadList.iterator();
        	
        	while ( tempIterator.hasNext() )
        	{
        		ChunkCoordIntPair tempCoord = tempIterator.next();
        		
        		if ( CheckChunkShouldBeUnloaded( tempCoord.chunkXPos, tempCoord.chunkZPos ) )
        		{
        			theChunkProviderServer.ForceAddToChunksToUnload( 
        				tempCoord.chunkXPos, tempCoord.chunkZPos );
        		}
        	}
    		
    		m_chunksToCheckForUnloadList.clear();
    	}
    }
    
    private boolean CheckChunkShouldBeUnloaded( int iChunkX, int iChunkZ )
    {
    	return chunkExists( iChunkX, iChunkZ ) && 
    		!m_chunkTracker.IsChunkBeingWatched( iChunkX, iChunkZ  )&&
    		!theChunkProviderServer.IsSpawnChunk( iChunkX, iChunkZ );
    }
    
    private void MarkChunksAroundSpawnToCheckForUnload()
    {
    	// this function cleans up extra useless overworld chunks loaded by 
    	// MinecraftServer.initialWorldChunkLoad()
    	
		if ( provider.canRespawnHere() )
		{
			int iSpawnChunkX = worldInfo.getSpawnX() >> 4;
			int iSpawnChunkZ = worldInfo.getSpawnZ() >> 4;
            
            AddChunkRangeToCheckForUnloadList( 
            	iSpawnChunkX - m_iChunksAroundSpawnToCheckForUnload, 
            	iSpawnChunkZ - m_iChunksAroundSpawnToCheckForUnload,
            	iSpawnChunkX + m_iChunksAroundSpawnToCheckForUnload, 
            	iSpawnChunkZ + m_iChunksAroundSpawnToCheckForUnload );        
		}
    }

    @Override
    public boolean IsUpdateScheduledForBlock( int i, int j, int k, int iBlockID )
    {
        NextTickListEntry tempEntry = new NextTickListEntry( i, j, k, iBlockID );
        
        return field_73064_N.contains( tempEntry );
    }

    @Override
    protected void updateWeather()
    {
    	// Vanilla code replaced to fix storms not relaying state to clients and a couple of other little oddities like the state
    	// changes for weather not being communicated immeditately
    	
        super.updateWeather();

        if ( worldInfo.m_bPreviouslyRaining != worldInfo.isRaining() )
        {
            if ( worldInfo.isRaining() )
            {
                mcServer.getConfigurationManager().sendPacketToAllPlayers( new Packet70GameEvent( 1, 0 ) );
            }
            else
            {
                mcServer.getConfigurationManager().sendPacketToAllPlayers( new Packet70GameEvent( 2, 0 ) );
            }
            
            worldInfo.m_bPreviouslyRaining = worldInfo.isRaining();
        }
        
        if ( worldInfo.m_bPreviouslyThundering != worldInfo.isThundering() )
        {
            if ( worldInfo.isThundering() )
            {
                mcServer.getConfigurationManager().sendPacketToAllPlayers( new Packet70GameEvent( 7, 0 ) );
            }
            else
            {
                mcServer.getConfigurationManager().sendPacketToAllPlayers( new Packet70GameEvent( 8, 0 ) );
            }
            
            worldInfo.m_bPreviouslyThundering = worldInfo.isThundering();
        }
    }
    
    private void ModUpdateTick()
    {
    	ValidateMagneticPointList();
    	
    	ValidateLootingBeaconList();
    	
    	ValidateSpawnLocationList();
    	
    	//AARON adds a check here for world border timing!
    	//Comment this out if it causes issues... it shouldn't :)
    	worldBorderCheck();
    }
    
    private long lTimeOfLastShrink = -1;
    String shrinkingMessage;
    boolean toggle = true;
    //AARON added this method to handle world border shrinking shenanigans
    private void worldBorderCheck()
    {
    	//if shrinking is disabled, skip worldBorderCheck() entirely
    	//only run this method 
    	if (!SuperBTW.instance.getWorldBorderEnabled() || !SuperBTW.instance.getCanWorldBorderShrink())
    	{
    		return;
    	}
    	int iTimeFactor = (int)getWorldTime();
    	//Checks for new moon rise (~~time=108,700)
    	//if new moon rising, the world border is toggled to start shrinking!
    	if (iTimeFactor == 108700 && !SuperBTW.instance.getIsWorldBorderShrinkingToggled())
    	{
    		SuperBTW.instance.setIsWorldBorderShrinkingToggled(true);
    		
    		//lets every player know that the blood moon is rising! 
    		//vvv permadeath is being toggled and shrinking world border!
            Iterator playerIterator = this.playerEntities.iterator();

            while (playerIterator.hasNext())
            {
                EntityPlayer selectedPlayer = (EntityPlayer)playerIterator.next();

                selectedPlayer.addChatMessage("THE BLOOD MOON RISES: PERMADEATH STARTS NOW.");
            }
    	}
    	//this pulses once to set off the shrinking timer
    	if (lTimeOfLastShrink == -1 && SuperBTW.instance.getIsWorldBorderShrinkingToggled())
    	{
    		lTimeOfLastShrink = FCUtilsWorld.GetOverworldTimeServerOnly();
    	}
    	
		//TESTERS VVV 
		System.out.println("World Time: "+FCUtilsWorld.GetOverworldTimeServerOnly() );
		System.out.println("lTimeOfLastShrink + getShrinkingTimerIncrement(): "+ (lTimeOfLastShrink + SuperBTW.instance.getShrinkingTimerIncrement()) );
	
    	if (SuperBTW.instance.getIsWorldBorderShrinkingToggled() && toggle)
    	{
    		toggle = false;
    		//this prevents multiple pings at the same time, which WorldServer is prone to for some reason...
    		if ( !( ( this.getTotalWorldTime()) % 100 != 0 ))
    		{
    			
    	    	if (SuperBTW.instance.getIsWorldBorderShrinkingToggled())
    	    	{
    	    		//if enough time is passed (time between shrinks set by shrinkingTimerIncrement in SuperBTW.java)
    	    		if ( FCUtilsWorld.GetOverworldTimeServerOnly() > lTimeOfLastShrink + SuperBTW.instance.getShrinkingTimerIncrement())
    	    		{
    	    			lTimeOfLastShrink = FCUtilsWorld.GetOverworldTimeServerOnly();
    	    			shrinkingMessage = "The World Border will shrink by "+SuperBTW.instance.getShrinkingAmount()
    	    				+" blocks in about "+getMinutesFromTicks(SuperBTW.instance.getShrinkingTimerIncrement())+" minutes!";
    	    			
    	    			//SEND MESSAGE TO ALL PLAYER vvv
    	    	    	Iterator playerIterator = this.playerEntities.iterator();
    	    	    	
    	    	        while (playerIterator.hasNext())
    	    	        {
    	    	            EntityPlayer selectedPlayer = (EntityPlayer)playerIterator.next();
    	    	
    	    	        	selectedPlayer.addChatMessage(shrinkingMessage);
    	    	        }
    	    	        //Player messages ^^^
    	    			
    	    			int newX = SuperBTW.instance.getRectangularWorldBorderX() - SuperBTW.instance.getShrinkingAmount();
    	    			SuperBTW.instance.setRectangularWorldBorderX(newX);
    	    			
    	    			int newZ = SuperBTW.instance.getRectangularWorldBorderZ() - SuperBTW.instance.getShrinkingAmount();
    	    			SuperBTW.instance.setRectangularWorldBorderZ(newZ);
    	    			
    	    			//TESTER vvv
    		    		System.out.println("WORLD BORDER UPDATED!!!!");

    	    		}
    	    		
    	    	}
    		}
    	}
    	

    }
    
    //AAROn created this method in order to dynamically convert from ticks to minutes
    //24000 ticks is equal to 20 minutes
    private int getMinutesFromTicks(int ticks)
    {
//    	int ticksPerTwentyMinutes = 24000;
//    	int twentyMinutes = 20;
    	
    	double minutes = ticks / (20 * 60);
    	
    	return (int)minutes;	
    }
    
    private void ValidateMagneticPointList()
    {
    	// periodically check the magnetic point list for dead points
    	
    	//AARON TESTER
//    	System.out.println("Inside Magnetic Points List!");
    	
    	int iTimeFactor = (int)getWorldTime();
    	
    	if ( ( iTimeFactor & 15 ) == 0 )
    	{
    		//AARON TESTER
//        	System.out.println("UwU past the weird magnetic points list line :D");
    		
    		int iListLength = m_MagneticPointList.m_MagneticPoints.size();
    		
    		if ( iListLength > 0 )
    		{
        		iTimeFactor = iTimeFactor >> 4;
                
    			int iTempIndex = (int)( iTimeFactor % iListLength );
    			
    			FCMagneticPoint tempPoint = (FCMagneticPoint)m_MagneticPointList.m_MagneticPoints.get( iTempIndex );
    			
    	        if ( checkChunksExist( tempPoint.m_iIPos, 0, tempPoint.m_iKPos, tempPoint.m_iIPos, 0, tempPoint.m_iKPos ) )
    	        {
    	        	// just check if the point has an associated beacon.  If it does, the beacon itself can validate its precise state
    	        	
    	        	if ( getBlockId( tempPoint.m_iIPos, tempPoint.m_iJPos, tempPoint.m_iKPos ) != Block.beacon.blockID )
    	        	{
    	        		m_MagneticPointList.m_MagneticPoints.remove( iTempIndex );
    	        		
    	        	}
    	        }    			
    		}
    	}
    }
    
    private void ValidateLootingBeaconList()
    {
    	// periodically check the looting beacon list for dead points
    	
    	int iTimeFactor = (int)getWorldTime();
    	
    	if ( ( iTimeFactor & 15 ) == 0 )
    	{
    		int iListLength = m_LootingBeaconLocationList.m_EffectLocations.size();
    		
    		if ( iListLength > 0 )
    		{
        		iTimeFactor = iTimeFactor >> 4;
                
    			int iTempIndex = (int)( iTimeFactor % iListLength );
    			
    			FCBeaconEffectLocation tempPoint = (FCBeaconEffectLocation)m_LootingBeaconLocationList.m_EffectLocations.get( iTempIndex );
    			
    	        if ( checkChunksExist( tempPoint.m_iIPos, 0, tempPoint.m_iKPos, tempPoint.m_iIPos, 0, tempPoint.m_iKPos ) )
    	        {
    	        	// just check if the point has an associated beacon.  If it does, the beacon itself can validate its precise state
    	        	
    	        	if ( getBlockId( tempPoint.m_iIPos, tempPoint.m_iJPos, tempPoint.m_iKPos ) != Block.beacon.blockID )
    	        	{
    	        		m_LootingBeaconLocationList.m_EffectLocations.remove( iTempIndex );
    	        		
    	        	}
    	        }    			
    		}
    	}
    }
    
    private void ValidateSpawnLocationList()
    {
    	// periodically check the list for dead points
    	
    	long lWorldTime = getWorldTime();
    	
    	if ( ( lWorldTime & 15 ) == 0 )
    	{
        	Iterator tempIterator = m_SpawnLocationList.m_SpawnLocations.iterator();
        	
        	while ( tempIterator.hasNext() )
        	{
        		FCSpawnLocation tempPoint = (FCSpawnLocation)tempIterator.next();
        		
        		if ( lWorldTime < tempPoint.m_lSpawnTime || lWorldTime - tempPoint.m_lSpawnTime > FCUtilsHardcoreSpawn.m_iHardcoreSpawnTimeBetweenReassignments )
        		{        			
        			tempIterator.remove();
        		}        		
        	}
    	}
    }
    
    protected void AdjustLightningPosForSurroundings( FCUtilsBlockPos strikePos )
    {
    	int iHighJ = strikePos.j;
    	
    	// store bounds variables since strikePos changes during search
    	
    	int iMinI = strikePos.i - 16;
    	int iMinK = strikePos.k - 16;
    	
    	int iMaxI = strikePos.i + 16;
    	int iMaxK = strikePos.k + 16;
    	
    	for ( int iTempI = iMinI; iTempI <= iMaxI; iTempI++ )
    	{
        	for ( int iTempK = iMinK; iTempK <= iMaxK; iTempK++ )
        	{
                int iTempJ = getPrecipitationHeight( iTempI, iTempK );
                
                if ( iTempJ > iHighJ )
                {
                    if ( CanLightningStrikeAtPos( iTempI, iTempJ, iTempK ) )
                    {
	                	strikePos.i = iTempI;
	                	iHighJ = strikePos.j = iTempJ;
	                	strikePos.k = iTempK;
                    }
                }
        	}
    	}    	
    	
    	// check entities
    	
        List<Entity> entityList = getEntitiesWithinAABB( Entity.class, AxisAlignedBB.getAABBPool().getAABB( 
        	iMinI, iHighJ, iMinK, iMaxI + 1D, 256D, iMaxK + 1D ) );
    	
        Iterator<Entity> entityIterator = entityList.iterator();

        while ( entityIterator.hasNext() )
        {
            Entity tempEntity = entityIterator.next();
            
            if ( tempEntity.isEntityAlive() && tempEntity.AttractsLightning() )
            {
            	int iEntityMaxJ = (int)tempEntity.boundingBox.maxY + 1;
            	
            	if ( iEntityMaxJ > iHighJ )
            	{            	
	            	int iEntityI = MathHelper.floor_double( tempEntity.posX );
	            	int iEntityK = MathHelper.floor_double( tempEntity.posZ );
	            	
	                int iPrecipitationJ = getPrecipitationHeight( iEntityI, iEntityK );
	                
	                if ( iPrecipitationJ <= iEntityMaxJ &&
	                	CanLightningStrikeAtPos( iEntityI, iPrecipitationJ, iEntityK ) )
	                {
	                	strikePos.i = iEntityI;
	                	iHighJ = strikePos.j = iPrecipitationJ;
	                	strikePos.k = iEntityK;
	                }	                
            	}                
            }
        }

        // secondary search for lightning rods in a larger area around new strike pos
        
        if ( IsBlockPosActive( strikePos.i, strikePos.j, strikePos.k ) )
        {
        	iMinI = strikePos.i - 16;
        	iMinK = strikePos.k - 16;
        	
        	iMaxI = strikePos.i + 16;
        	iMaxK = strikePos.k + 16;
        	
        	for ( int iTempI = iMinI; iTempI <= iMaxI; iTempI++ )
        	{
            	for ( int iTempK = iMinK; iTempK <= iMaxK; iTempK++ )
            	{
                    int iTempJ = getPrecipitationHeight( iTempI, iTempK );
                    
                    if ( iTempJ > iHighJ && getBlockId( iTempI, iTempJ - 1, iTempK ) == 
                    	FCBetterThanWolves.fcBlockLightningRod.blockID )
                    {
                    	// intentionally don't test CanLightningStrikeAtPos() so that
                    	// rods in non-lightning biomes can still attract lightning to them
                    	
	                	strikePos.i = iTempI;
	                	iHighJ = strikePos.j = iTempJ;
	                	strikePos.k = iTempK;
                    }
            	}
        	}        	
        }
    }
    
    @Override
    public int GetClampedViewDistanceInChunks()
    {
    	int iRange = getMinecraftServer().getConfigurationManager().getViewDistance();
    	
    	return MathHelper.clamp_int( iRange, 3, 15 );
    }
    
    @Override
    protected void UpdateActiveChunkMap()
    {
    	super.UpdateActiveChunkMap();
    	
    	UpdateServerIdleState();    	
    	
    	// FCCHUNK: Decide on updates around original spawn
    	
    	if ( provider.dimensionId == 0 && !IsServerIdle() )
    	{
	        ChunkCoordinates originalSpawn = getSpawnPoint();
	        
	        AddAreaAroundChunkToActiveChunkMap( originalSpawn.posX >> 4, 
	        	originalSpawn.posZ >> 4 );
    	}
    }
    
    public FCChunkTracker GetChunkTracker()
    {
        return m_chunkTracker;
    }    

    protected void UpdateServerIdleState()
    {
        if ( !AreAnyPlayersOnServer() )
        {
        	m_lNoPlayersOnServerTickCount++;
        }
        else
        {
        	m_lNoPlayersOnServerTickCount = 0;
        }
    }
    
    protected boolean IsServerIdle()
    {
        return m_lNoPlayersOnServerTickCount >= 1200L;
    }
    
    protected boolean AreAnyPlayersOnServer()
    {
    	return mcServer.getCurrentPlayerCount() > 0;
    }

	//------------ Addon Data Handling -----------//

	private Map<Class<? extends FCAddOn>, FCAddOnUtilsWorldData> addonWorldDataMap = new HashMap();

	public void saveWorldDataToNBT(File dimensionDirectory) {
		for (Map.Entry<Class<? extends FCAddOn>, FCAddOnUtilsWorldData> entry : addonWorldDataMap.entrySet()) {
			NBTTagCompound modData = new NBTTagCompound();
	        
			entry.getValue().saveWorldDataToNBT(this, modData);
	        
	        NBTTagCompound fileData = new NBTTagCompound();
	        
	        fileData.setTag( "Data", modData );

	        try {
	            File modSaveFile = new File(dimensionDirectory, entry.getValue().getFilename() + ".dat");
	            
	            if (modSaveFile.exists()) {
	            	modSaveFile.delete();
	            }
	            
	            CompressedStreamTools.writeCompressed( fileData, new FileOutputStream(modSaveFile) );

	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}

	public void loadWorldDataFromNBT(File dimensionDirectory) {
		addonWorldDataMap = FCAddOnHandler.initWorldDataForAddon();
		
		for (Map.Entry<Class<? extends FCAddOn>, FCAddOnUtilsWorldData> entry : addonWorldDataMap.entrySet()) {
			File modSaveFile = null;
			
			try {
				modSaveFile = new File(dimensionDirectory, entry.getValue().getFilename() + ".dat");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (modSaveFile != null && modSaveFile.exists()) {
				try {
					NBTTagCompound fileTag = CompressedStreamTools.readCompressed( new FileInputStream(modSaveFile));
					NBTTagCompound dataTag = fileTag.getCompoundTag("Data");

					entry.getValue().loadWorldDataFromNBT(this, dataTag);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public FCAddOnUtilsWorldData getWorldDataForMod(Class<? extends FCAddOn> mod) {
		if (addonWorldDataMap.containsKey(mod))
			return addonWorldDataMap.get(mod);
		else
			return null;
	}

	public void setWorldDataForMod(Class<? extends FCAddOn> mod, FCAddOnUtilsWorldData data) {
		addonWorldDataMap.put(mod, data);
	}
    // END FCMOD
}
