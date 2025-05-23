package net.minecraft.src;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiIngame extends Gui
{
    private static final RenderItem itemRenderer = new RenderItem();
    private final Random rand = new Random();
    private final Minecraft mc;

    /** ChatGUI instance that retains all previous chat data */
    private final GuiNewChat persistantChatGUI;
    private int updateCounter = 0;

    /** The string specifying which record music is playing */
    private String recordPlaying = "";

    /** How many ticks the record playing message will be displayed */
    private int recordPlayingUpFor = 0;
    private boolean recordIsPlaying = false;

    /** Previous frame vignette brightness (slowly changes by 1% each frame) */
    public float prevVignetteBrightness = 1.0F;

    /** Remaining ticks the item highlight should be visible */
    private int remainingHighlightTicks;

    /** The ItemStack that is currently being highlighted */
    private ItemStack highlightingItemStack;

    public GuiIngame(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
        this.persistantChatGUI = new GuiNewChat(par1Minecraft);
    }

    /**
     * Render the ingame overlay with quick icon bar, ...
     */
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
    {
        ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int var6 = var5.getScaledWidth();
        int var7 = var5.getScaledHeight();
        FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(par1), var6, var7);
        }
        else
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID)
        {
            this.renderPumpkinBlur(var6, var7);
        }

        // FCMOD: Added (client only)
        RenderModSpecificPlayerSightEffects();
        // END FCMOD

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;

            if (var10 > 0.0F)
            {
                this.renderPortalOverlay(var10, var6, var7);
            }
        }

        boolean var11;
        int var12;
        int var13;
        int var17;
        int var16;
        int var18;
        int var20;
        int var23;
        int var22;
        int var24;
        byte var27;
        int var26;
        int var47;
        int var50;

        if (!this.mc.playerController.enableEverythingIsScrewedUpMode())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture("/gui/gui.png");
            InventoryPlayer var31 = this.mc.thePlayer.inventory;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var31.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
            this.mc.renderEngine.bindTexture("/gui/icons.png");
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
            this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(GL11.GL_BLEND);
            var11 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;

            if (this.mc.thePlayer.hurtResistantTime < 10)
            {
                var11 = false;
            }

            var12 = this.mc.thePlayer.getHealth();
            var13 = this.mc.thePlayer.prevHealth;
            this.rand.setSeed((long)(this.updateCounter * 312871));
            boolean var14 = false;
            FoodStats var15 = this.mc.thePlayer.getFoodStats();
            var16 = var15.getFoodLevel();
            var17 = var15.getPrevFoodLevel();
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
            int var19;

            if (this.mc.playerController.shouldDrawHUD())
            {
                var18 = var6 / 2 - 91;
                var19 = var6 / 2 + 91;
                this.mc.mcProfiler.startSection("expBar");
                var20 = this.mc.thePlayer.xpBarCap();

                if (var20 > 0)
                {
                    short var21 = 182;
                    var22 = (int)(this.mc.thePlayer.experience * (float)(var21 + 1));
                    var23 = var7 - 32 + 3;
                    this.drawTexturedModalRect(var18, var23, 0, 64, var21, 5);

                    if (var22 > 0)
                    {
                        this.drawTexturedModalRect(var18, var23, 0, 69, var22, 5);
                    }
                }

                var47 = var7 - 39;
                var22 = var47 - 10;
                var23 = this.mc.thePlayer.getTotalArmorValue();
                var24 = -1;

                if (this.mc.thePlayer.isPotionActive(Potion.regeneration))
                {
                    var24 = this.updateCounter % 25;
                }

                this.mc.mcProfiler.endStartSection("healthArmor");
                int var25;
                int var29;
                int var28;

                for (var25 = 0; var25 < 10; ++var25)
                {
                    if (var23 > 0)
                    {
                        var26 = var18 + var25 * 8;

                        if (var25 * 2 + 1 < var23)
                        {
                            this.drawTexturedModalRect(var26, var22, 34, 9, 9, 9);
                        }

                        if (var25 * 2 + 1 == var23)
                        {
                            this.drawTexturedModalRect(var26, var22, 25, 9, 9, 9);
                        }

                        if (var25 * 2 + 1 > var23)
                        {
                            this.drawTexturedModalRect(var26, var22, 16, 9, 9, 9);
                        }
                    }

                    var26 = 16;

                    if (this.mc.thePlayer.isPotionActive(Potion.poison))
                    {
                        var26 += 36;
                    }
                    else if (this.mc.thePlayer.isPotionActive(Potion.wither))
                    {
                        var26 += 72;
                    }

                    var27 = 0;

                    if (var11)
                    {
                        var27 = 1;
                    }

                    var28 = var18 + var25 * 8;
                    var29 = var47;

                    if (var12 <= 4)
                    {
                        var29 = var47 + this.rand.nextInt(2);
                    }

                    if (var25 == var24)
                    {
                        var29 -= 2;
                    }

                    byte var30 = 0;

                    if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                    {
                        var30 = 5;
                    }

                    this.drawTexturedModalRect(var28, var29, 16 + var27 * 9, 9 * var30, 9, 9);

                    if (var11)
                    {
                        if (var25 * 2 + 1 < var13)
                        {
                            this.drawTexturedModalRect(var28, var29, var26 + 54, 9 * var30, 9, 9);
                        }

                        if (var25 * 2 + 1 == var13)
                        {
                            this.drawTexturedModalRect(var28, var29, var26 + 63, 9 * var30, 9, 9);
                        }
                    }

                    if (var25 * 2 + 1 < var12)
                    {
                        this.drawTexturedModalRect(var28, var29, var26 + 36, 9 * var30, 9, 9);
                    }

                    if (var25 * 2 + 1 == var12)
                    {
                        this.drawTexturedModalRect(var28, var29, var26 + 45, 9 * var30, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("food");

                // FCMOD: Changed to add more detail to hunger display (client only)
                /*
                for (var25 = 0; var25 < 10; ++var25)
                {
                    var26 = var47;
                    var50 = 16;
                    byte var51 = 0;

                    if (this.mc.thePlayer.isPotionActive(Potion.hunger))
                    {
                        var50 += 36;
                        var51 = 13;
                    }

                    if (this.mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var16 * 3 + 1) == 0)
                    {
                        var26 = var47 + (this.rand.nextInt(3) - 1);
                    }

                    if (var14)
                    {
                        var51 = 1;
                    }

                    var29 = var19 - var25 * 8 - 9;
                    this.drawTexturedModalRect(var29, var26, 16 + var51 * 9, 27, 9, 9);

                    if (var14)
                    {
                        if (var25 * 2 + 1 < var17)
                        {
                            this.drawTexturedModalRect(var29, var26, var50 + 54, 27, 9, 9);
                        }

                        if (var25 * 2 + 1 == var17)
                        {
                            this.drawTexturedModalRect(var29, var26, var50 + 63, 27, 9, 9);
                        }
                    }

                    if (var25 * 2 + 1 < var16)
                    {
                        this.drawTexturedModalRect(var29, var26, var50 + 36, 27, 9, 9);
                    }

                    if (var25 * 2 + 1 == var16)
                    {
                        this.drawTexturedModalRect(var29, var26, var50 + 45, 27, 9, 9);
                    }
                }
                */
                DrawFoodOverlay( var19, var47 );
                
                int iSightlessTextOffset = -8;
                // END FCMOD

                this.mc.mcProfiler.endStartSection("air");

                // FCMOD: Change to display air gauge during recovery (client only)
                /*
                if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
                */
                if (this.mc.thePlayer.isInsideOfMaterial(Material.water) || mc.thePlayer.getAir() < 300 )
            	// END FCMOD 
                {
                    var25 = this.mc.thePlayer.getAir();
                    var26 = MathHelper.ceiling_double_int((double)(var25 - 2) * 10.0D / 300.0D);
                    var50 = MathHelper.ceiling_double_int((double)var25 * 10.0D / 300.0D) - var26;

                    for (var28 = 0; var28 < var26 + var50; ++var28)
                    {
                        if (var28 < var26)
                        {
                            this.drawTexturedModalRect(var19 - var28 * 8 - 9, var22, 16, 18, 9, 9);
                        }
                        else
                        {
                            this.drawTexturedModalRect(var19 - var28 * 8 - 9, var22, 25, 18, 9, 9);
                        }
                    }
                }
                // FCMOD: Added (client only)
                else if ( !DrawPenaltyText( var19, var22 ) )
                {                	
                	iSightlessTextOffset = 0;
                }
                
                DrawSightlessText( var19, var22 + iSightlessTextOffset );
                // END FCMOD

                this.mc.mcProfiler.endSection();
            }

            GL11.glDisable(GL11.GL_BLEND);
            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();

            for (var18 = 0; var18 < 9; ++var18)
            {
                var19 = var6 / 2 - 90 + var18 * 20 + 2;
                var20 = var7 - 16 - 3;
                this.renderInventorySlot(var18, var19, var20, par1);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            this.mc.mcProfiler.endSection();
        }

        float var33;

        if (this.mc.thePlayer.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            int var32 = this.mc.thePlayer.getSleepTimer();
            var33 = (float)var32 / 100.0F;

            if (var33 > 1.0F)
            {
                var33 = 1.0F - (float)(var32 - 100) / 10.0F;
            }

            var12 = (int)(220.0F * var33) << 24 | 1052704;
            drawRect(0, 0, var6, var7, var12);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            this.mc.mcProfiler.endSection();
        }

        int var38;
        int var37;

        if (this.mc.playerController.func_78763_f() && this.mc.thePlayer.experienceLevel > 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            var11 = false;
            var12 = var11 ? 16777215 : 8453920;
            String var34 = "" + this.mc.thePlayer.experienceLevel;
            var38 = (var6 - var8.getStringWidth(var34)) / 2;
            var37 = var7 - 31 - 4;
            var8.drawString(var34, var38 + 1, var37, 0);
            var8.drawString(var34, var38 - 1, var37, 0);
            var8.drawString(var34, var38, var37 + 1, 0);
            var8.drawString(var34, var38, var37 - 1, 0);
            var8.drawString(var34, var38, var37, var12);
            this.mc.mcProfiler.endSection();
        }

        String var35;

        if (this.mc.gameSettings.heldItemTooltips)
        {
            this.mc.mcProfiler.startSection("toolHighlight");

            if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null)
            {
                var35 = this.highlightingItemStack.getDisplayName();
                var12 = (var6 - var8.getStringWidth(var35)) / 2;
                var13 = var7 - 59;

                if (!this.mc.playerController.shouldDrawHUD())
                {
                    var13 += 14;
                }

                var38 = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);

                if (var38 > 255)
                {
                    var38 = 255;
                }

                if (var38 > 0)
                {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    var8.drawStringWithShadow(var35, var12, var13, 16777215 + (var38 << 24));
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }

            this.mc.mcProfiler.endSection();
        }

        if (this.mc.isDemo())
        {
            this.mc.mcProfiler.startSection("demo");
            var35 = "";

            if (this.mc.theWorld.getTotalWorldTime() >= 120500L)
            {
                var35 = StatCollector.translateToLocal("demo.demoExpired");
            }
            else
            {
                var35 = String.format(StatCollector.translateToLocal("demo.remainingTime"), new Object[] {StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()))});
            }

            var12 = var8.getStringWidth(var35);
            var8.drawStringWithShadow(var35, var6 - var12 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }

        if (this.mc.gameSettings.showDebugInfo)
        {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.5.2 (" + this.mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
            long var36 = Runtime.getRuntime().maxMemory();
            long var40 = Runtime.getRuntime().totalMemory();
            long var43 = Runtime.getRuntime().freeMemory();
            long var44 = var40 - var43;
            String var46 = "Used memory: " + var44 * 100L / var36 + "% (" + var44 / 1024L / 1024L + "MB) of " + var36 / 1024L / 1024L + "MB";
            this.drawString(var8, var46, var6 - var8.getStringWidth(var46) - 2, 2, 14737632);
            var46 = "Allocated memory: " + var40 * 100L / var36 + "% (" + var40 / 1024L / 1024L + "MB)";
            this.drawString(var8, var46, var6 - var8.getStringWidth(var46) - 2, 12, 14737632);
            // FCMOD: Removed (client only)
            /*
            var47 = MathHelper.floor_double(this.mc.thePlayer.posX);
            var22 = MathHelper.floor_double(this.mc.thePlayer.posY);
            var23 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var47), Integer.valueOf(var47 >> 4), Integer.valueOf(var47 & 15)}), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var23), Integer.valueOf(var23 >> 4), Integer.valueOf(var23 & 15)}), 2, 80, 14737632);
            var24 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            this.drawString(var8, "f: " + var24 + " (" + Direction.directions[var24] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);

            if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var47, var22, var23))
            {
                Chunk var52 = this.mc.theWorld.getChunkFromBlockCoords(var47, var23);
                this.drawString(var8, "lc: " + (var52.getTopFilledSegment() + 15) + " b: " + var52.getBiomeGenForWorldCoords(var47 & 15, var23 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var52.getSavedLightValue(EnumSkyBlock.Block, var47 & 15, var22, var23 & 15) + " sl: " + var52.getSavedLightValue(EnumSkyBlock.Sky, var47 & 15, var22, var23 & 15) + " rl: " + var52.getBlockLightValue(var47 & 15, var22, var23 & 15, 0), 2, 96, 14737632);
            }

            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(var47, var23))}), 2, 104, 14737632);
            */
            // END FCMOD
            // FCMOD: Added (client only)
            RenderModDebugOverlay();
            // END FCMOD
            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        }

        if (this.recordPlayingUpFor > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            var33 = (float)this.recordPlayingUpFor - par1;
            var12 = (int)(var33 * 256.0F / 20.0F);

            if (var12 > 255)
            {
                var12 = 255;
            }

            if (var12 > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                var13 = 16777215;

                if (this.recordIsPlaying)
                {
                    var13 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var13 + (var12 << 24));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        ScoreObjective var42 = this.mc.theWorld.getScoreboard().func_96539_a(1);

        if (var42 != null)
        {
            this.func_96136_a(var42, var7, var6, var8);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();
        var42 = this.mc.theWorld.getScoreboard().func_96539_a(0);

        if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1 || var42 != null))
        {
            this.mc.mcProfiler.startSection("playerList");
            NetClientHandler var39 = this.mc.thePlayer.sendQueue;
            List var41 = var39.playerInfoList;
            var38 = var39.currentServerMaxPlayers;
            var37 = var38;

            for (var16 = 1; var37 > 20; var37 = (var38 + var16 - 1) / var16)
            {
                ++var16;
            }

            var17 = 300 / var16;

            if (var17 > 150)
            {
                var17 = 150;
            }

            var18 = (var6 - var16 * var17) / 2;
            byte var45 = 10;
            drawRect(var18 - 1, var45 - 1, var18 + var17 * var16, var45 + 9 * var37, Integer.MIN_VALUE);

            for (var20 = 0; var20 < var38; ++var20)
            {
                var47 = var18 + var20 % var16 * var17;
                var22 = var45 + var20 / var16 * 9;
                drawRect(var47, var22, var47 + var17 - 1, var22 + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (var20 < var41.size())
                {
                    GuiPlayerInfo var49 = (GuiPlayerInfo)var41.get(var20);
                    ScorePlayerTeam var48 = this.mc.theWorld.getScoreboard().getPlayersTeam(var49.name);
                    String var53 = ScorePlayerTeam.func_96667_a(var48, var49.name);
                    var8.drawStringWithShadow(var53, var47, var22, 16777215);

                    if (var42 != null)
                    {
                        var26 = var47 + var8.getStringWidth(var53) + 5;
                        var50 = var47 + var17 - 12 - 5;

                        if (var50 - var26 > 5)
                        {
                            Score var56 = var42.getScoreboard().func_96529_a(var49.name, var42);
                            String var57 = EnumChatFormatting.YELLOW + "" + var56.func_96652_c();
                            var8.drawStringWithShadow(var57, var50 - var8.getStringWidth(var57), var22, 16777215);
                        }
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.renderEngine.bindTexture("/gui/icons.png");
                    byte var55 = 0;
                    boolean var54 = false;

                    if (var49.responseTime < 0)
                    {
                        var27 = 5;
                    }
                    else if (var49.responseTime < 150)
                    {
                        var27 = 0;
                    }
                    else if (var49.responseTime < 300)
                    {
                        var27 = 1;
                    }
                    else if (var49.responseTime < 600)
                    {
                        var27 = 2;
                    }
                    else if (var49.responseTime < 1000)
                    {
                        var27 = 3;
                    }
                    else
                    {
                        var27 = 4;
                    }

                    this.zLevel += 100.0F;
                    this.drawTexturedModalRect(var47 + var17 - 12, var22, 0 + var55 * 10, 176 + var27 * 8, 10, 8);
                    this.zLevel -= 100.0F;
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    private void func_96136_a(ScoreObjective par1ScoreObjective, int par2, int par3, FontRenderer par4FontRenderer)
    {
        Scoreboard var5 = par1ScoreObjective.getScoreboard();
        Collection var6 = var5.func_96534_i(par1ScoreObjective);

        if (var6.size() <= 15)
        {
            int var7 = par4FontRenderer.getStringWidth(par1ScoreObjective.getDisplayName());
            String var11;

            for (Iterator var8 = var6.iterator(); var8.hasNext(); var7 = Math.max(var7, par4FontRenderer.getStringWidth(var11)))
            {
                Score var9 = (Score)var8.next();
                ScorePlayerTeam var10 = var5.getPlayersTeam(var9.func_96653_e());
                var11 = ScorePlayerTeam.func_96667_a(var10, var9.func_96653_e()) + ": " + EnumChatFormatting.RED + var9.func_96652_c();
            }

            int var22 = var6.size() * par4FontRenderer.FONT_HEIGHT;
            int var23 = par2 / 2 + var22 / 3;
            byte var25 = 3;
            int var24 = par3 - var7 - var25;
            int var12 = 0;
            Iterator var13 = var6.iterator();

            while (var13.hasNext())
            {
                Score var14 = (Score)var13.next();
                ++var12;
                ScorePlayerTeam var15 = var5.getPlayersTeam(var14.func_96653_e());
                String var16 = ScorePlayerTeam.func_96667_a(var15, var14.func_96653_e());
                String var17 = EnumChatFormatting.RED + "" + var14.func_96652_c();
                int var19 = var23 - var12 * par4FontRenderer.FONT_HEIGHT;
                int var20 = par3 - var25 + 2;
                drawRect(var24 - 2, var19, var20, var19 + par4FontRenderer.FONT_HEIGHT, 1342177280);
                par4FontRenderer.drawString(var16, var24, var19, 553648127);
                par4FontRenderer.drawString(var17, var20 - par4FontRenderer.getStringWidth(var17), var19, 553648127);

                if (var12 == var6.size())
                {
                    String var21 = par1ScoreObjective.getDisplayName();
                    drawRect(var24 - 2, var19 - par4FontRenderer.FONT_HEIGHT - 1, var20, var19 - 1, 1610612736);
                    drawRect(var24 - 2, var19 - 1, var20, var19, 1342177280);
                    par4FontRenderer.drawString(var21, var24 + var7 / 2 - par4FontRenderer.getStringWidth(var21) / 2, var19 - par4FontRenderer.FONT_HEIGHT, 553648127);
                }
            }
        }
    }

    /**
     * Renders dragon's (boss) health on the HUD
     */
    private void renderBossHealth()
    {
        if (BossStatus.bossName != null && BossStatus.statusBarLength > 0)
        {
            --BossStatus.statusBarLength;
            FontRenderer var1 = this.mc.fontRenderer;
            ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var3 = var2.getScaledWidth();
            short var4 = 182;
            int var5 = var3 / 2 - var4 / 2;
            int var6 = (int)(BossStatus.healthScale * (float)(var4 + 1));
            byte var7 = 12;
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);

            if (var6 > 0)
            {
                this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
            }

            String var8 = BossStatus.bossName;
            var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture("/gui/icons.png");
        }
    }

    private void renderPumpkinBlur(int par1, int par2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        this.mc.renderEngine.bindTexture("%blur%/misc/pumpkinblur.png");
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the vignette. Args: vignetteBrightness, width, height
     */
    private void renderVignette(float par1, int par2, int par3)
    {
        par1 = 1.0F - par1;

        if (par1 < 0.0F)
        {
            par1 = 0.0F;
        }

        if (par1 > 1.0F)
        {
            par1 = 1.0F;
        }

        this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(par1 - this.prevVignetteBrightness) * 0.01D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
        this.mc.renderEngine.bindTexture("%blur%/misc/vignette.png");
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
        var4.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
        var4.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
        var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var4.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Renders the portal overlay. Args: portalStrength, width, height
     */
    private void renderPortalOverlay(float par1, int par2, int par3)
    {
        if (par1 < 1.0F)
        {
            par1 *= par1;
            par1 *= par1;
            par1 = par1 * 0.8F + 0.2F;
        }

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
        this.mc.renderEngine.bindTexture("/terrain.png");
        Icon var4 = Block.portal.getBlockTextureFromSide(1);
        float var5 = var4.getMinU();
        float var6 = var4.getMinV();
        float var7 = var4.getMaxU();
        float var8 = var4.getMaxV();
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)var5, (double)var8);
        var9.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)var7, (double)var8);
        var9.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)var7, (double)var6);
        var9.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var5, (double)var6);
        var9.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the specified item of the inventory slot at the specified location. Args: slot, x, y, partialTick
     */
    private void renderInventorySlot(int par1, int par2, int par3, float par4)
    {
        ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];

        if (var5 != null)
        {
            float var6 = (float)var5.animationsToGo - par4;

            if (var6 > 0.0F)
            {
                GL11.glPushMatrix();
                float var7 = 1.0F + var6 / 5.0F;
                GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
                GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
            }

            // FCMOD: Added (client only)
    		int iItemID = var5.getItem().itemID;
    		
	        if ( iItemID == Item.compass.itemID )
	        {
	        	TextureCompass.compassTexture.UpdateActive();
	        }
            // END FCMOD
	        
            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);

            // FCMOD: Added (client only)
	        if ( iItemID == Item.compass.itemID )
	        {
	        	TextureCompass.compassTexture.UpdateInert();
	        }
            // END FCMOD
	        
            if (var6 > 0.0F)
            {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
        }
    }

    /**
     * The update tick for the ingame UI
     */
    public void updateTick()
    {
        if (this.recordPlayingUpFor > 0)
        {
            --this.recordPlayingUpFor;
        }

        ++this.updateCounter;

        if (this.mc.thePlayer != null)
        {
            ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();

            if (var1 == null)
            {
                this.remainingHighlightTicks = 0;
            }
            else if (this.highlightingItemStack != null && var1.itemID == this.highlightingItemStack.itemID && ItemStack.areItemStackTagsEqual(var1, this.highlightingItemStack) && (var1.isItemStackDamageable() || var1.getItemDamage() == this.highlightingItemStack.getItemDamage()))
            {
                if (this.remainingHighlightTicks > 0)
                {
                    --this.remainingHighlightTicks;
                }
            }
            else
            {
                this.remainingHighlightTicks = 40;
            }

            this.highlightingItemStack = var1;
        }
    }

    public void setRecordPlayingMessage(String par1Str)
    {
        this.recordPlaying = "Now playing: " + par1Str;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = true;
    }

    /**
     * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
     */
    public GuiNewChat getChatGUI()
    {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }

    // FCMOD: Added New (client only)
	static final int m_iTrueSightRange = 10;
	
    private int m_iFoodLevelOnLastGUIUpdate = 0;
    private float m_fFatOnLastGUIUpdate = 0F;
    
    public void RenderModDebugOverlay()
    {
    	AddChunkBoundaryDisplay( 64 );
    	
    	//AddMovementSpeedDisplay( 74 );
    	
    	//AddCurrentBiomeDisplay( 74 );
    	
    	AddLoadedChunksOnServerDisplay( 84 );        
    }
    
    private void AddChunkBoundaryDisplay( int iYPos )
    {
        FontRenderer fontRenderer = mc.fontRenderer;
        
        int chunkX = MathHelper.floor_double(mc.thePlayer.posX) % 16;
        
        if ( chunkX < 0 )
        {
        	chunkX = 16 + chunkX;
        }
        
        int iDistToChunkBndryX = chunkX;
        
        if ( 15 - chunkX < chunkX )
        {
        	iDistToChunkBndryX = 15 - chunkX;
        }
        
        int chunkZ = MathHelper.floor_double(mc.thePlayer.posZ) % 16;
        
        if ( chunkZ < 0 )
        {
        	chunkZ = 16 + chunkZ;
        }
        
        int iDistToChunkBndryZ = chunkZ;
        
        if ( 15 - chunkZ < chunkZ )
        {
        	iDistToChunkBndryZ = 15 - chunkZ;
        }
        
        int iDistToChunkBndry = iDistToChunkBndryX;
        
        if ( iDistToChunkBndryZ < iDistToChunkBndryX )
        {
        	iDistToChunkBndry = iDistToChunkBndryZ;
        }
        
        drawString(fontRenderer, String.format("Dist To Chnk Bndry: %d", new Object[]
			{
			     Integer.valueOf( iDistToChunkBndry )
			}), 2, iYPos, 0xe0e0e0);
    }
    
    private void AddCurrentBiomeDisplay( int iYPos )
    {
        FontRenderer fontRenderer = mc.fontRenderer;        
        EntityPlayer player = mc.thePlayer;
        
        String sBiomeDescriptor;        
        
        BiomeGenBase biomeGen = player.worldObj.getBiomeGenForCoords( MathHelper.floor_double( player.posX ),
        	MathHelper.floor_double( player.posZ ) );        
        
        if ( biomeGen != null )
        {
        	sBiomeDescriptor = biomeGen.getClass().getName(); 
        }
        else
        {
        	sBiomeDescriptor = "unknown";
        }
        
        drawString( fontRenderer, "Biome: " + sBiomeDescriptor, 2, iYPos, 0xe0e0e0 );
        
    }
    
    private void AddMovementSpeedDisplay( int iYPos )
    {
        FontRenderer fontRenderer = mc.fontRenderer;
        
        double playerSpeed = Math.sqrt( ( mc.thePlayer.motionX * mc.thePlayer.motionX ) + ( mc.thePlayer.motionZ * mc.thePlayer.motionZ ) );
        
        
        String sPlayerSpeedString = String.format("Player Speed: %.5f", new Object[] {
    		Double.valueOf(playerSpeed) } ); 
        	
        drawString(fontRenderer, sPlayerSpeedString , 2, iYPos, 0xe0e0e0);
                                                                                
        double riddenSpeed = 0D;
        
        if ( mc.thePlayer.ridingEntity != null )
        {
        	riddenSpeed = Math.sqrt( ( mc.thePlayer.ridingEntity.motionX * mc.thePlayer.ridingEntity.motionX ) + ( mc.thePlayer.ridingEntity.motionZ * mc.thePlayer.ridingEntity.motionZ ) );
        }
        
        
        String sRiddenSpeedString = String.format("Ridden Speed: %.5f", new Object[] {
    		Double.valueOf(riddenSpeed) } );
        
        ScaledResolution resolution = new ScaledResolution( mc.gameSettings, 
        	mc.displayWidth, mc.displayHeight );
        
        int iXPos = 12 + fontRenderer.getStringWidth( sPlayerSpeedString );
        
        drawString(fontRenderer, String.format("Ridden Speed: %.5f", new Object[]
			{
        		Double.valueOf(riddenSpeed)
			}), iXPos, iYPos, 0xe0e0e0);
    }
    
    private void AddLoadedChunksOnServerDisplay( int iYPos )
    {
        if ( net.minecraft.server.MinecraftServer.getServer() != null )
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            
        	if ( net.minecraft.server.MinecraftServer.getServer().worldServers[0] != null )
        	{
	            IChunkProvider provider = 
	            	net.minecraft.server.MinecraftServer.getServer().worldServers[0].getChunkProvider();
	            
	            drawString( fontrenderer, "Overworld " + provider.makeString(), 2, 84, 0xe0e0e0 );
        	}
        	
        	if ( net.minecraft.server.MinecraftServer.getServer().worldServers[1] != null )
        	{
	            IChunkProvider provider = 
	            	net.minecraft.server.MinecraftServer.getServer().worldServers[1].getChunkProvider();
	            
	            drawString( fontrenderer, "Nether " + provider.makeString(), 2, 94, 0xe0e0e0 );
        	}
        	
        	if ( net.minecraft.server.MinecraftServer.getServer().worldServers[2] != null )
        	{
	            IChunkProvider provider = 
	            	net.minecraft.server.MinecraftServer.getServer().worldServers[2].getChunkProvider();
	            
	            drawString( fontrenderer, "End " + provider.makeString(), 2, 104, 0xe0e0e0 );
        	}
        }
    }
    
    private void renderEnderSpectaclesBlur()
    {
        ScaledResolution resolution = new ScaledResolution( mc.gameSettings, 
        	mc.displayWidth, mc.displayHeight);
        
        int iScreenWidth = resolution.getScaledWidth();
        int iScreenHeight = resolution.getScaledHeight();
        
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        mc.renderEngine.bindTexture("%blur%/btwmodtex/spectaclesblur.png");
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, iScreenHeight, -90D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(iScreenWidth, iScreenHeight, -90D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(iScreenWidth, 0.0D, -90D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);        
    }
    
    public void RenderGameOverlayWithGuiDisabled(float fSmoothCameraPartialTicks, boolean bScreenActive, int iMouseX, int iMouseY )
    {
    	// Renders anything that has a gameplay effect when the player has the GUI turned off
    	
        ScaledResolution resolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int iScreenWidth = resolution.getScaledWidth();
        int iScreenHeight = resolution.getScaledHeight();
        FontRenderer fontRenderer = this.mc.fontRenderer;
        
        mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(fSmoothCameraPartialTicks), iScreenWidth, iScreenHeight);
        }
        else
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID)
        {
            this.renderPumpkinBlur(iScreenWidth, iScreenHeight);
        }

        RenderModSpecificPlayerSightEffects();

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * fSmoothCameraPartialTicks;

            if (var10 > 0.0F)
            {
                this.renderPortalOverlay(var10, iScreenWidth, iScreenHeight);
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
    
    private void RenderModSpecificPlayerSightEffects()
    {
        if ( mc.gameSettings.thirdPersonView == 0 )
        {
		    if ( mc.thePlayer.IsWearingEnderSpectacles() )
		    {
		        renderEnderSpectaclesBlur();
		        
		        AddTrueSightParticles();
		        
		    	// Decided against this as it may trivialize base measurement too much.
//		        AddSpawnChunksParticles();
		    }
		    else if ( mc.thePlayer.isPotionActive( FCBetterThanWolves.potionTrueSight ) )
		    {
		        AddTrueSightParticles();
		        
		        int randy = rand.nextInt(10);
		        if (randy < 1)
		        {
			        AddSpawnChunksParticles();
		        }
		    }
        }
    }
    
    private void AddTrueSightParticles()
    {
        // create particles where mobs can spawn
        
        if ( !mc.isGamePaused )
        {
            EntityPlayer player = mc.thePlayer;
            World world = mc.theWorld;
            int iParticleSetting = mc.gameSettings.particleSetting;
            
			int iPlayerI = MathHelper.floor_double( player.posX );
			int iPlayerJ = MathHelper.floor_double( player.posY );
			int iPlayerK = MathHelper.floor_double( player.posZ );
			
			for ( int iTempI = iPlayerI - m_iTrueSightRange; iTempI <= iPlayerI + m_iTrueSightRange; iTempI++ )
			{
				for ( int iTempJ = iPlayerJ - m_iTrueSightRange; iTempJ <= iPlayerJ + m_iTrueSightRange; iTempJ++ )
				{
					for ( int iTempK = iPlayerK - m_iTrueSightRange; iTempK <= iPlayerK + m_iTrueSightRange; iTempK++ )
					{
						if ( FCUtilsWorld.CanMobsSpawnHere( world, iTempI, iTempJ, iTempK ) )
						{
							double dVerticalOffset = 0D;
							
							Block blockBelow = Block.blocksList[world.getBlockId( 
								iTempI, iTempJ - 1, iTempK )];
							
							if ( blockBelow != null )
							{
								dVerticalOffset = blockBelow.MobSpawnOnVerticalOffset( 
									world, iTempI, iTempJ - 1, iTempK );
							}
							
							if ( rand.nextInt( 12 ) <= ( 2 - iParticleSetting ) )
							{
								double particleX = (double)iTempI + rand.nextDouble();
								
								double particleY = (double)iTempJ + dVerticalOffset + 
									rand.nextDouble() * 0.25D;
								
								double particleZ = (double)iTempK + rand.nextDouble();
								
								SpawnTrueSightParticle( world, particleX, particleY, particleZ );								
							}
						}
					}
				}
			}
        }        
    }
    
    private void SpawnTrueSightParticle( World world, double dXPos, double dYPos, double dZPos )
    {
        EntityFX particleEntity = new EntitySpellParticleFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        
        particleEntity.setRBGColorF( 0F, 0F, 0F );
        
        mc.effectRenderer.addEffect( (EntityFX)particleEntity );
    }
    
    private void AddSpawnChunksParticles()
    {
        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;
        
        if ( !mc.isGamePaused && player.GetSpawnChunksVisualizationLocationJ() != 0 &&
        	world.provider.dimensionId == 0 ) // is overworld
        {   
        	// Note that world.GetClampedViewDistanceInChunks() is not accurate on the client
        	// and will always be 10, regardless of view-distance parameter on dedicated server
        	int iViewDistanceChunks = world.GetClampedViewDistanceInChunks();
        	
        	int iFirstPassRange = ( iViewDistanceChunks + 2 ) * 16; // player has to be within these bounds
        	
            int iPlayerX = MathHelper.floor_double( player.posX );            
            int iDeltaPosX = iPlayerX - player.GetSpawnChunksVisualizationLocationI();
            
            if ( iDeltaPosX >= -iFirstPassRange && iDeltaPosX <= iFirstPassRange )
            {
                int iPlayerZ = MathHelper.floor_double( player.posZ );            
                int iDeltaPosZ = iPlayerZ - player.GetSpawnChunksVisualizationLocationK();
                
                if ( iDeltaPosZ >= -iFirstPassRange && iDeltaPosZ <= iFirstPassRange )
                {
                    int iParticleSetting = mc.gameSettings.particleSetting;
                    
                	int iNumParticles = 200 - ( iParticleSetting * 100 );
                	
                	for ( int iTempCount = 0; iTempCount < iNumParticles; iTempCount++ )
                	{
						double particleY = player.posY -(double)m_iTrueSightRange + 
							( rand.nextDouble() * (double)m_iTrueSightRange * 2D );
						
						if ( particleY > 0D && particleY <= 256D )
						{
							double particleX = player.posX -(double)m_iTrueSightRange + 
							( rand.nextDouble() * (double)m_iTrueSightRange * 2D );
						
							double particleZ = player.posZ -(double)m_iTrueSightRange + 
							( rand.nextDouble() * (double)m_iTrueSightRange * 2D );
						
					    	int iSpawnChunkX = player.GetSpawnChunksVisualizationLocationI() >> 4;
	            			int iSpawnChunkZ = player.GetSpawnChunksVisualizationLocationK() >> 4;
	                	
							if ( IsPosInSpawnChunkZone( particleX, particleY, particleZ,
								iSpawnChunkX, iSpawnChunkZ, iViewDistanceChunks ) )
							{
								if ( IsPosInSpawnChunkZone( particleX, particleY, particleZ,
									iSpawnChunkX, iSpawnChunkZ, iViewDistanceChunks - 2 ) )
								{
									if ( IsPosInSpawnBlock( player, particleX, particleY, particleZ ) )
									{
										SpawnSpawnPointParticle( world, particleX, particleY, particleZ );
									}
									else
									{
										SpawnSpawnChunkInnerParticle( world, particleX, particleY, particleZ );
									}
								}
								else
								{
									SpawnSpawnChunkOuterParticle( world, particleX, particleY, particleZ );
								}
							}
                		}
                	}
                }
            }
        }
    }    
    
    private void SpawnSpawnChunkOuterParticle( World world, double dXPos, double dYPos, double dZPos )
    {
        EntityFX particleEntity = new EntityPortalFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        
        particleEntity.setRBGColorF( 0F, 0F, 0.5F );
        particleEntity.setAlphaF(0.5F);
        
        mc.effectRenderer.addEffect( (EntityFX)particleEntity );
    }
    
    private void SpawnSpawnChunkInnerParticle( World world, double dXPos, double dYPos, double dZPos )
    {
        EntityFX particleEntity = new EntityCritFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        
        particleEntity.setRBGColorF( 0.5F, 0F, 0.5F );
        particleEntity.setAlphaF(0.25F);
        
        mc.effectRenderer.addEffect( (EntityFX)particleEntity );
    }
    
    private void SpawnSpawnPointParticle( World world, double dXPos, double dYPos, double dZPos )
    {
        //EntityFX particleEntity = new EntityAuraFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        //EntityFX particleEntity = new EntityCritFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        //EntityFX particleEntity = new EntityPortalFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        EntityFX particleEntity = new EntityEnchantmentTableParticleFX( world, dXPos, dYPos, dZPos, 0.0D, 0.0D, 0.0D);
        
        particleEntity.setRBGColorF( 0.75F, 0F, 0F );
        particleEntity.setAlphaF(0.5F);
        
        mc.effectRenderer.addEffect( (EntityFX)particleEntity );
    }
    
    public boolean IsPosInSpawnChunkZone( double posX, double posY, double posZ, 
    	int iSpawnChunkX, int iSpawnChunkZ, int iChunkRange )
    {
    	int iPosChunkX = MathHelper.floor_double( posX / 16D );
    	int iDeltaX = iPosChunkX - iSpawnChunkX;  
    	
    	if ( iDeltaX >= -iChunkRange && iDeltaX <= iChunkRange )
    	{    	
    		int iPosChunkZ = MathHelper.floor_double( posZ / 16D );
        	int iDeltaZ = iPosChunkZ - iSpawnChunkZ;  
    		
        	if ( iDeltaZ >= -iChunkRange && iDeltaZ <= iChunkRange )
        	{
        		return true;
        	}
    	}
    	
    	return false;
    }
    
    public boolean IsPosInSpawnBlock( EntityPlayer player, double posX, double posY, double posZ )
    {
    	int iDeltaX = MathHelper.floor_double( posX ) - player.GetSpawnChunksVisualizationLocationI();
    	
    	if ( iDeltaX >= -1 && iDeltaX <= 1 )
    	{
    		int iDeltaZ = MathHelper.floor_double( posZ ) - player.GetSpawnChunksVisualizationLocationK();
    		
        	if ( iDeltaZ >= -1 && iDeltaZ <= 1 )
        	{
        		return true;
        	}
    	}
    	
    	return false;
    }
    
    private int m_iFoodOverlayShakeCounter = 0;
    
    private void DrawFoodOverlay( int iScreenX, int iScreenY )
    {
        FoodStats stats = mc.thePlayer.getFoodStats();
        
        int iHungerPenalty = mc.thePlayer.GetHungerPenaltyLevel();
        
        int iFoodLevel = stats.getFoodLevel();
        float fSaturationLevel = stats.getSaturationLevel();
        int iSaturationPips = (int)( ( stats.getSaturationLevel() + 0.124F ) * 4F );
        
        int iFullHungerPips = iFoodLevel / 6;
        
        if ( mc.thePlayer.m_bExhaustionAddedSinceLastGuiUpdate )
        {
        	m_iFoodOverlayShakeCounter = 20;
        	
        	mc.thePlayer.m_bExhaustionAddedSinceLastGuiUpdate = false;
        }
        else if ( m_iFoodOverlayShakeCounter > 0 )
        {
        	m_iFoodOverlayShakeCounter--;
        }
        
        for ( int iTempCount = 0; iTempCount < 10; ++iTempCount )
        {
            int iShankScreenY = iScreenY;
            int iShankTextureOffsetX = 16;
            byte iBackgroundTextureOffsetX = 0;

            if ( mc.thePlayer.isPotionActive( Potion.hunger ) )
            {
                iShankTextureOffsetX += 36;
                iBackgroundTextureOffsetX = 13;
            }
            else if ( iTempCount < iSaturationPips >> 3 )
            {
            	iBackgroundTextureOffsetX = 1;
            }

            if ( iHungerPenalty > 0 && updateCounter % ( iFoodLevel * 5 + 1 ) == 0 )
            {
                iShankScreenY = iScreenY + (this.rand.nextInt(3) - 1);
            }
            else if ( m_iFoodOverlayShakeCounter > 0 )
            {
            	int iShakeAmount = /*rand.nextInt( 2 ) +*/ 1;
            	
            	if ( rand.nextInt( 2 ) == 0 )
            	{
            		iShakeAmount = -iShakeAmount;
            	}
            	
                iShankScreenY = iScreenY + iShakeAmount;
            }

            int iShankScreenX = iScreenX - iTempCount * 8 - 9;
            
            drawTexturedModalRect( iShankScreenX, iShankScreenY, 16 + iBackgroundTextureOffsetX * 9, 27, 9, 9 );
            
            if ( iTempCount == iSaturationPips >> 3 )
            {
            	if ( !mc.thePlayer.isPotionActive( Potion.hunger ) )
            	{
	            	int iPartialPips = iSaturationPips % 8;
	            	
	            	if ( iPartialPips != 0 )
	            	{
		            	// draw partial pips
		            	
		                drawTexturedModalRect( iShankScreenX + 8 - iPartialPips, iShankScreenY, 25 + 8 - iPartialPips, 27, 1 + iPartialPips, 9 );
	            	}
            	}
            }

            if ( iTempCount < iFullHungerPips )
            {
                drawTexturedModalRect( iShankScreenX, iShankScreenY, iShankTextureOffsetX + 36, 27, 9, 9);
            }
            else if ( iTempCount == iFullHungerPips )
            {
            	int iPartialPips = iFoodLevel % 6;
            	
            	if ( iPartialPips != 0 )
            	{
            		drawTexturedModalRect( iShankScreenX + 7 - iPartialPips, iShankScreenY, iShankTextureOffsetX + 36 + 7 - iPartialPips, 27, 3 + iPartialPips, 9);
            	}
            }
        }        
    }
    
    public static final String[] sHealthPenaltyDescriptions = new String[] { "Invalid", "Hurt", "Injured", "Wounded", "Crippled", "Dying" };
    public static final String[] sHungerPenaltyDescriptions = new String[] { "Invalid", "Peckish", "Hungry", "Famished", "Starving", "Dying" };
    public static final String[] sFatPenaltyDescriptions = new String[] { "Invalid", "Plump", "Chubby", "Fat", "Obese", "Invalid" };
    
	private boolean DrawPenaltyText( int iScreenX, int iScreenY )
	{
		if ( mc.thePlayer.isDead )
		{
			return false;
		}
		
        FontRenderer rednerer = this.mc.fontRenderer;
        String sPenaltyString = null;
        
        int iHealthPenalty = mc.thePlayer.GetHealthPenaltyLevel();
        int iHungerPenalty = mc.thePlayer.GetHungerPenaltyLevel();
        int iFatPenalty = mc.thePlayer.GetFatPenaltyLevel();
        
        if ( iHealthPenalty > 0 && iHealthPenalty >= iHungerPenalty && iHealthPenalty >= iFatPenalty )
        {
        	sPenaltyString = sHealthPenaltyDescriptions[iHealthPenalty];
        }
        else if ( iHungerPenalty > 0 && iHungerPenalty >= iFatPenalty )
        {
        	sPenaltyString = sHungerPenaltyDescriptions[iHungerPenalty];
        }
        else if ( iFatPenalty > 0 )
        {
        	sPenaltyString = sFatPenaltyDescriptions[iFatPenalty];
        }        
        
        if ( sPenaltyString != null )
        {
            int iStringWidth = rednerer.getStringWidth( sPenaltyString );

            rednerer.drawStringWithShadow( sPenaltyString, iScreenX - iStringWidth , iScreenY, 0XFFFFFF );
            
            return true;
        }        
        
        return false;
	}
	
	private void DrawSightlessText( int iScreenX, int iScreenY )
	{
        int iGloomLevel = mc.thePlayer.GetGloomLevel();
        
        if ( mc.thePlayer.GetGloomLevel() > 0 && !mc.thePlayer.isDead )
        {
            FontRenderer rednerer = this.mc.fontRenderer;
            String sPenaltyString = null;
            
        	switch ( iGloomLevel )
        	{
        		case 2:
        			
	            	sPenaltyString = "Dread";
	            	
	            	break;
	            	
        		case 3:
        			
	            	sPenaltyString = "Terror";
	            	
	            	break;
	            	
	        	default:
	        		
	            	sPenaltyString = "Gloom";
	            	
	            	break;
        	}
        	
            int iStringWidth = rednerer.getStringWidth( sPenaltyString );

            rednerer.drawStringWithShadow( sPenaltyString, iScreenX - iStringWidth , iScreenY, 0XFFFFFF );
        }
	}
	
    static public boolean InstallationIntegrityTest()
    {
    	return true;
    }
    // END FCMOD
}
