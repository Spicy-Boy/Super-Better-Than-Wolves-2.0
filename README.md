# Super Better Than Wolves 2.0
The ultimate survival experience in Minecraft!

Version 2.0 adds...
>DOG TREATS!
>A GOURD OVERHAUL!
>SLEEPING!
>RIB SMASHING FUN!
>THATCH!
>NEW WAYS TO MAKE BRICKS!
>CONFIGS!
(and so much more!)
 
For updates and design musings, visit my dev diary thread:
http://www.sargunster.com/btwforum/viewtopic.php?t=10164
 
To play SBTW 1.0, the previous version, see the official 1.0 release thread:
http://www.sargunster.com/btwforum/viewtopic.php?t=10183

From the changelog:
SBTW VERSION 2.0
Started August 8, 2022
Switched to BETA 2.0 on July 12, 2023
Released Beta 2.4 on June 11, 2024

BETA 2.4 The Pro-Choice Update:

- Added a new ceramic block, terracotta, whose unfired form is crafted with sand+clay. Taken from Deco Addon!
    - Can be stained en-masse with dye in the cauldron, 16 terracotta per dye

- Added two new Deco brick types: white stone bricks and sandstone bricks. Crafted with 4 of respective block
    - Stairs and slabs available, cracked and mossy available as whole blocks
    
- Added piston packing to the meat cube... meat packing :D

- Added the ability to pulverize ore chunks into dust in the millstone (gold and iron)
    - the purpose of this new industrial process will be revealed in a later update

- Added a Latin translation for BTW (replaces Klingon.. sorry?) provided by Waluiji. TY!!! You can now RP as a Roman colonist.

- Changed the config system to use the official FCAddon property manager 
    - SBTW.properties now contains all configs. It can be found alongside the BTW properties file in the configs

- Added a config to disable the fire caused by lightning strikes
    - If you know you know...
    
- Added a config to set the HC respawn radius to whatever you like
    - Yes, this means you can disable Hardcore Spawn entirely by setting the respawn radius super low
    - This is a stop gap feature: ideally, this config will be set at world creation and be applied per-world

- Added a config to stop branches from generating/falling from trees (in case you hate wood for some reason)

- Added a config to re-enable hardcore bouncing (prevents block placement while in the air)

- Added a config to revert Steve's hunger bar properties to FC defaults (faster health drain while starving, can't jump while famished)

- Added the /smite [player] command to place divine retribution into the hands of admins

- Added the /tpabove [player] [# of blocks] command to allow admins to spy on players.. works with negative numbers, defaults 20 blocks

- Added a new recipe for the bedroll that just uses padding. There are now 4 bedroll recipes to fit every possible sleeping arrangement.

- Changed the first layer of the ender beacon to be the trans-dimensional chest instead of a dimensional chest (swapped lvl 1->2,2->3,3->1) 

- Changed withers to be made with soulsand again. Yes, you can still use bone blocks if you like

- Changed/tweaked Hardcore Hoosies 2.0 just a little bit more, as some evil players still wanted to abuse the poor animals

- Fixed an issue where trades weren't refreshing properly (again... ;I) and added a measure to prevent chunk reversion in rare cases

- Fixed an issue where the death counter was being reset to 0 when going through the end portal
     NOTE: respawning at a Steel Beacon still resets the death counter! Will probably get fixed someday? NBD perhaps

- Fixed an issue that caused a crash when applying arrows (and maybe other things too) to your armor slots. 

- Fixed an issue where baby wolves/puppies would not eat food off the ground and could only be fed when dying of starvation.

- Fixed an issue where horny wolves would eat an endless amount of food off the ground... hehe I'm sorry for this one! A single misplaced line of code.

- Fixed an issue where the bed would break into components as well as an entire intact bed at the same time

- Fixed a server issue where players sleeping in the overworld would speed up time even if there were non-sleepers in other dimensions. Instant-death in most cases.

NOTE for Aaron: temporarily disabled neo-gourd trades (re-enable when gourds are back!)

BETA 2.3 Punish All Cow Abusers Update:

- Added the ctrl key as an alternative sprint button. This cannot be rebound at this time.
    -If rebinding your ctrl key is something important to you, then please reach out to me and I can make it configurable. 
    -I just didn't feel like coding that right now, and I won't bother if it isn't something anyone will use

- Added the treat, a special item for your dog. Show some love to your canine companion and he might give some right back!
    -Another Sockthing classic texture

- Added a server config to SBTWserver.properties that toggles team spawns on initial login (functions like /set spawn)
    -basically, instead of starting at spawn, new players will begin at the location set by their respective team
    -if a player is not a member of a team, they will spawn at the "default" team location (use /tpteam set default) 

- Added a server config to SBTWserver.properties that toggles HC Spawn on initial login
    -basically, instead of starting at spawn, new players in a server start at a random HC spawn
    -players will spawn near each other due (I think) to hardcore soulmating, which helps performance

NOTE: If both team start and HC start are enabled at the same time, then players will random HC spawn rather than go to team default

- Added the /deaths command that informs the player how many times they have died in the current world.

- Changed/replaced the librarian trades for bookshelves and redstone with trades to purchase block dispensers and detector blocks

- Changed the level 3 Librarian level up trade to a buddy block since you can now buy BDs from him

- Changed/swapped around the blaze powder and bat wings librarian trade because, IMO, bat wings are a lot harder to farm than blaze rods!

- Changed the level 2 priest trades to be more desirable. Before, the trades consisted of random trash enchantments.
    -The priest knows how to use the enchanting table a lot better than you do tbh

- Added a super secret trade for epic loot    

- Changed spawners to spread mossy stone to loose cobble as well as mortared 

- Changed/tweaked Hardcore Hoofsies to demand a little bit more respect than the community has been giving lately... >:)
    -Holding a tasty snack will make hoofsies much more forgiving of hugsies

- Changed campfires to startle animals when placed
    
- Changed trap doors to be climbable when placed above ladders

- Changed mycelium to grow in complete darkness. It didn't really make sense that it needed sunlight.

- Fixed an issue where loose dirt was breakable by zombies... sorry :)    
    
- Fixed a long standing issue where villager trades would not refresh properly (thanks CE team!)

BETA 2.2:

- Added thatch, a new roofing material made of sticks and reeds
    -Caution, this material is weaker than wood and may not stop a hungry zombie!
    
- Added pitfall traps! Try setting them with thatch and another unstable block
    
- Added straw thatch, a thatch variant that swaps reeds for straw
    
- Added more reasonable recipes for acquiring wool blocks and platforms that are not dependent on wicker weaving    

- Added two creative-only blocks: super glass and super block
    - Super glass is bedrock but see-through
    - Super blocks are also just retextured bedrock

- Added three new player-to-player teleportation commands: /tpa [player name], /tpaccept [player name], and /tpcancel

- Added /tpteam commands for setting up teams of players and teleporting them as a group
    -/tpteam list, /tpteam list [teamname], tpteam refresh, /tpteam [teamname], /tpteam player [playername], /tpteam new [teamname], /tpteam set [teamname], tpteam remove [playername], tpteam add [playername]
    -TODO: configuration to make players spawn at their team location on first enter server

- Added a new configuration file, SBTWserver.properties, to servers (auto-generated on start up) that allows server owners to disable or enable SBTW's new tpa commands

- Added the tombstone, a block that can be crafted by chiseling a slab of stone   

- Added Hardcore Nightmares... remember, it is just a dream!
        
- Added part of Tsughoggr's Gloryhole and Blow mod to SBTW
        - Adds the gloryhole, an oven-like block that can be used to create blocks of glass when filled with sand from the top and heated from below
        - Changed the gloryhole recipe to require a block of obsidian rather than nether quarts
        
- Added a new breeding method for wolves-- mystery meat! Credit to the CE team for this one. 
    >Kibble is out, which will make dog armies a little tougher to make. I intend to make a non-mystery meat breeding item too for this purpose, but kibble isn't it folks
    - Wild wolves can breed now

- Changed bread dough and mud brick crafting to maintain the full bucket of water between crafts. No more refilling
    - This is a stopgap. Ideally, this behavior will be based on nearby water sources. Working on it!

- Changed the anchor recipe to require an iron nugget rather than an ingot (steel nugget too because why not?)
      
- Changed/reverted glass to the state of not allowing monsters to spawn on top of it. No more need to light up large glass structures!
        
- Changed the chat window during sleep to be toggled off by default. Press "t" while sleeping to chat. 

- Changed hearty stew to mainly be produced with beef. This makes stew less of a braindead endgame food option and more of a project to obtain automatically
        >Notably, you can't make stew with pork, mutton only produces two servings of stew, and wolf chops only produce 3 servings. Fry up some eggs mate.        
    
- Fixed an issue that caused the player to wake up from rain even when it wasn't really raining on the bed due to rounding problems
    
- Fixed crude torch pickup. There was a bug where right clicking a torch would sometimes cause the torch to be placed in the inventory invisibly


BETA 2.1 Hotfix:
-Added some files that were forgotten initially. This fixes a crash related to rendering baskets!

BETA 2.0: 

Additions:

-TEMPORARY OMISSION for BETA 2.0: Biome decorator places standard gourds with vanilla seeds/plants

    @ the request of Wario-Luigi Hybrid

-Added SLEEPING!

   Like in Arminias mod/CE 1.4, the world is simulated during sleep. Time is not skipped! Monsters still lurk outside...

-Added Hardcore Soaked!

-Added the bedroll from CE 1.4. You can sleep on this! Great for camping trips.

   Three recipes: wool knit + wool knit + string OR unknit wool OR two feathers

-Added the ability to pick up torches and bricks by right clicking them. No more fumbling!

-Added 9 new seeds for each type of pumpkin and melon WOW!

-Added new item: Bucket of Mortar

   right click to apply mortar to a block, depletes contents of bucket (16 uses)

-Added new tool: the trowel

   right click to apply mortar from your inventory

-Added new block: Wet Mud Bricks

   Can be dried in the sun or baked in a kiln to produce bricks more efficiently than with clay balls alone

-Added more efficient recipes for tanned leather armor

   crafted using cut tanned leather in the 2x2 crafting grid, like untanned leather

-Added rib cracking: use a sharp axe to quickly get meat off a rib

   the rib itself is reduced to bonemeal in the process

-Added the ability to cure ribs with nitre

-Added the Time Cube to the creative menu (WARNING: I am not responsible for any corruption that temporal shenanigans unleash upon your world)

-Added lava and powered axle to creative menu

Changes/Fixes:

-Changed wolves to breed to produce untamed puppies. This opens up some options :)

-Changed the ender goggle overlay to be transparent in places so you can see somewhat while wearing them

-Changed pumpkin seeds to stack to 64 rather than 16 for storage and automation purposes OH NO MY INVENTORY BALANCE!!!!!!!!

-Changed pumpkin and melon varieties to grow by seed rather than biome.
EX: a yellow pumpkin requires a yellow pumpkin seed to grow.

-Changed gourds on the vine to take a few days to mature (like wheat) (rather than being random)

-Changed the ender spectacle overlay to be easier to see through

-Changed wicker baskets to recycle into 3 wicker rather than just 1

-Changed rib bones to be grindable in millstone to produce 6 bonemeal

-Changed the bone club to be grindable in a millstone to produce 2 bonemeal. Hardcore Recycling!

-Changed dead bushes to be a little tougher and made some more bountiful than others

-Changed FCAddOn to no longer display a V next to version number when loading into a world.. for pretty much no reason

-Fixed the lighting bug that caused hampers and such to look completely pitch black (thanks Dawn, Hiracho, and the CE team!!!)

-Fixed a game bricking bug that created a ticking tile entity or something when using a piston shovel to pick up ash.

-Fixed an issue in which the vase was also a Time Cube

-Fixed a quirk where ribs looked kind of buggy to eat. That was my attempt at making them take longer to chew, but it looked sort of dumb
