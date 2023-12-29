# Super Better Than Wolves 2.0
The ultimate survival experience in Minecraft!

Version 2.0 adds...
>A GOURD OVERHAUL!
>SLEEPING!
>RIB SMASHING FUN!
>NEW WAYS TO MAKE BRICKS!
(and so much more!)
 
For updates and design musings, visit my dev diary thread:
http://www.sargunster.com/btwforum/viewtopic.php?t=10164
 
To play SBTW 1.0, the previous version, see the official 1.0 release thread:
http://www.sargunster.com/btwforum/viewtopic.php?t=10183

From the changelog:
SBTW VERSION 2.0
Started August 8, 2022
Switched to BETA 2.0 on July 12, 2023

2.2:

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


2.1 Hotfix:
-Added some files that were forgotten initially. This fixes a crash related to rendering baskets!

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
