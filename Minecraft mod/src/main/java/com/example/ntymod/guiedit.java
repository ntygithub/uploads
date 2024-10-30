package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class guiedit extends GuiIngame {
    NtyCommand commando = new NtyCommand();
    ScaledResolution reso;
    public int widthscreen;
    public int heightscreen;
    int xofset;
    int yofset;
    private boolean rendermyhotbar;

    public guiedit(Minecraft mc) {
        super(mc);
        this.reso = new ScaledResolution(this.mc);
        this.widthscreen = this.reso.getScaledWidth();
        this.heightscreen = this.reso.getScaledHeight();
        this.xofset = 402 + this.widthscreen;
        this.yofset = 180 + this.heightscreen / 4;
        this.rendermyhotbar = false;

    }


    @SubscribeEvent
    public void onRenderGameOverlay(Pre event) {
        if (event.type == ElementType.HOTBAR) {
            event.setCanceled(true);
            this.rendermyhotbar = true;
        }
    }


    @SubscribeEvent
    public void onRenderGameOverlay(Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (this.rendermyhotbar) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();
            for(int i = 0; i < 9; ++i) {
                this.renderInventorySlot(i, 100 + this.xofset, 100 + i * 20 + this.yofset, event.partialTicks);
                InventoryPlayer inv = mc.thePlayer.inventory;
                mc.renderEngine.bindTexture(new ResourceLocation("ntymod:widgets5.png"));
                this.drawTexturedModalRect(97 + this.xofset, 97 + this.yofset, 0, 74, 22, 182);
                this.drawTexturedModalRect(96 + this.xofset, 96 + inv.currentItem * 20 + this.yofset, 22, 232, 24, 24);
            }
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }

        this.rendermyhotbar = false;
    }

    private void renderInventorySlot(int slot, int x, int y, float partialTicks) {

        ItemStack stack = this.mc.thePlayer.inventory.mainInventory[slot];

        if (stack != null) {
            float animationProgress = (float)stack.animationsToGo - partialTicks;
            if (animationProgress > 0.0F) {
                GlStateManager.pushMatrix();
                float scale = 1.0F + animationProgress / 5.0F;
                GlStateManager.translate((float)(x + 8), (float)(y + 12), 0.0F);
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
                GlStateManager.popMatrix();
            }

            this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRendererObj, stack, x, y, (String)null);

        }

    }
}
