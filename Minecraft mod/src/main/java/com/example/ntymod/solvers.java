package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.*;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.ScaledResolution;

import net.minecraft.util.ResourceLocation;


import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.opengl.GL11;

public class solvers {
    private static final Pattern titlePattern = Pattern.compile("^What starts with: ['\"](.+)['\"]\\?$");
    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fontRenderer = mc.fontRendererObj;
    ScaledResolution scaledResolution = new ScaledResolution(mc);
    int screenWidth = scaledResolution.getScaledWidth();
    int screenHeight = scaledResolution.getScaledHeight();

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (mc.currentScreen instanceof GuiChest && Minecraft.getMinecraft().thePlayer != null) {
            ContainerChest chest = (ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer;
            IInventory chestInventory = chest.getLowerChestInventory();
            if (chestInventory.hasCustomName()) {
                if (chestInventory.getName().startsWith("What starts with")) {
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(chestInventory.getName()));
                    Matcher matcher = titlePattern.matcher(chestInventory.getName());
                    if (matcher.find()) {
                        String sequence = matcher.group(1);
//                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("FOUND: "+ sequence));
                        for (Slot slot : chest.inventorySlots) {
                            if (slot.getStack() == null) {
                                continue;
                            }
                            else if (slot.slotNumber<9||slot.slotNumber%9==8||slot.slotNumber%9==0||((45<slot.slotNumber)&& (slot.slotNumber<54))||slot.slotNumber>54){continue;}
                            else if (slot.getStack().isItemEnchanted()){continue;}
                            else if (StringUtils.stripControlCodes(slot.getStack().getDisplayName().toLowerCase()).startsWith(sequence.toLowerCase())) {
//                                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Starts"+sequence+"||" + slot.getStack().getDisplayName() + slot.slotNumber));
//                                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+ slot.xDisplayPosition +"||"+ slot.yDisplayPosition));
                                mc.renderEngine.bindTexture(new ResourceLocation("ntymod:widgets5.png"));
                                GL11.glDisable(GL11.GL_DEPTH_TEST);
                                GL11.glDepthFunc(GL11.GL_ALWAYS);
                                mc.ingameGUI.drawTexturedModalRect(slot.xDisplayPosition + screenWidth/2+ 175, slot.yDisplayPosition + screenHeight/2+ 34, 22, 232, 24, 24);
                                GL11.glDepthFunc(GL11.GL_LEQUAL);
                                GL11.glEnable(GL11.GL_DEPTH_TEST);
                            }
                        }
                    }
                }
            }
        }
    }



    @SubscribeEvent
    public void onGuiA(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiChest && Minecraft.getMinecraft().thePlayer != null) {
            ContainerChest chest = ((ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer);
            IInventory chestInventory = chest.getLowerChestInventory();
            if (chestInventory.hasCustomName()) {
                if (chestInventory.getName().startsWith("Click the button on time")) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("melody is a felony");
                }
                else if (chestInventory.getName().startsWith("What starts with")) {
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("starts with"));
                }
            }
        }
    }
}

