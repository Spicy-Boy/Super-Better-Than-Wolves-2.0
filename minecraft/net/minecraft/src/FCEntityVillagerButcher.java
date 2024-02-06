package net.minecraft.src;

public class FCEntityVillagerButcher extends FCEntityVillager {
	public FCEntityVillagerButcher(World world) {
		super(world, FCEntityVillager.professionIDButcher);
	}
	
	@Override
	public int getCurrentMaxNumTrades() {
		int tradeLevel = this.GetCurrentTradeLevel();
		
		if (tradeLevel >= 4) {
			return tradeLevel + 1;
		}
		else {
			return tradeLevel;
		}
	}
	
	//CLIENT ONLY
	public String getTexture() {
		if (this.GetCurrentTradeLevel() >= 4)
            return "/btwmodtex/fcButcherLvl.png";
        return "/mob/villager/butcher.png";
	}
}