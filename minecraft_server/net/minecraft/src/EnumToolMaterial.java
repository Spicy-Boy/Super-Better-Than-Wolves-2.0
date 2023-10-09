package net.minecraft.src;

public enum EnumToolMaterial
{
	// FCMOD: Change to rebalance value of early game tools and value of iron
	/*
    WOOD(0, 59, 2.0F, 0, 15),
    STONE(1, 131, 4.0F, 1, 5),
    IRON(2, 250, 6.0F, 2, 14),
    EMERALD(3, 1561, 8.0F, 3, 10),
    GOLD(0, 32, 12.0F, 0, 22);
    */
	// FCNOTE: Min efficiency is slightly greater than 1 due to > 1 test elsewhere in the code, 
	// There's one such test in EntityPlayer.getCurrentPlayerStrVsBlock() but I'm not sure 
	// it's the only one I was referring to in this comment when first written    
	
	/*Aaron changes: 
	 * make SFS enchantable
	 * add BONE material
	 * diamond can accept 1 extra infernal enchantment
	 * diamond enchantability increased
	*/
	WOOD( 0, 10, 1.01F, 0, 0, 20, 2 ),  // no vanilla enchant of wood
    STONE( 1, 50, 1.01F, 1, 5, 10, 1 ), 
    IRON( 2, 500, 6F, 2, 14, 25, 2 ),
    EMERALD( 3, 1561, 8F, 3, 18, 30, 3 ),
    GOLD( 0, 32, 12F, 0, 22, 30, 3 ),
    SOULFORGED_STEEL( 4, 2250, 12F, 4, 30, 30, 5 ),
    BONE(1, 20, 1.01F, 1, 5, 10, 1);
    // END FCMOD

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
     */
    private final int harvestLevel;

    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    private final int maxUses;

    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    private final float efficiencyOnProperMaterial;

    /** Damage versus entities. */
    private final int damageVsEntity;

    /** Defines the natural enchantability factor of the material. */
    private final int enchantability;

    // FCMOD: Changed
    /*
    private EnumToolMaterial(int par3, int par4, float par5, int par6, int par7)
    {
        this.harvestLevel = par3;
        this.maxUses = par4;
        this.efficiencyOnProperMaterial = par5;
        this.damageVsEntity = par6;
        this.enchantability = par7;
    }
    */
    private EnumToolMaterial( int iHarvestLevel, int iMaxUses, float fEffeciency, int iWeaponDamage, 
    	int iEnchantability, int iInfernalMaxEnchantmentCost, int iInfernalMaxNumEnchants )
    {
        harvestLevel = iHarvestLevel;
        maxUses = iMaxUses;
        efficiencyOnProperMaterial = fEffeciency;
        damageVsEntity = iWeaponDamage;
        enchantability = iEnchantability;

        m_iInfernalMaxEnchantmentCost = iInfernalMaxEnchantmentCost;
        m_iInfernalMaxNumEnchants = iInfernalMaxNumEnchants;
    }    
    // END FCMOD

    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    public int getMaxUses()
    {
        return this.maxUses;
    }

    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    public float getEfficiencyOnProperMaterial()
    {
        return this.efficiencyOnProperMaterial;
    }

    /**
     * Damage versus entities.
     */
    public int getDamageVsEntity()
    {
        return this.damageVsEntity;
    }

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
     */
    public int getHarvestLevel()
    {
        return this.harvestLevel;
    }

    /**
     * Return the natural enchantability factor of the material.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    /**
     * Return the crafting material for this tool material, used to determine the item that can be used to repair a tool
     * with an anvil
     */
    public int getToolCraftingMaterial()
    {
        return this == WOOD ? Block.planks.blockID : (this == STONE ? Block.cobblestone.blockID : (this == GOLD ? Item.ingotGold.itemID : (this == IRON ? Item.ingotIron.itemID : (this == EMERALD ? Item.diamond.itemID : 0))));
    }
    
    // FCMOD: Added New
    private final int m_iInfernalMaxEnchantmentCost;
    private final int m_iInfernalMaxNumEnchants;
    
    public int GetInfernalMaxEnchantmentCost()
    {
    	return m_iInfernalMaxEnchantmentCost;
}
    
    public int GetInfernalMaxNumEnchants()
    {
    	return m_iInfernalMaxNumEnchants;
    }
    // END FCMOD
}
