package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;

import java.io.*;

public class withercloak {

    NtyCommand ntyCommand = new NtyCommand();
    actualguinow mygui = new actualguinow();
    @SubscribeEvent
    public void whencreeperveil(ClientChatReceivedEvent event) {
        String message = event.message.getFormattedText();
        if (message.contains("withercloak")) {
            creeperveilAlert();
        }
    }

    private long SpiritStartTime = -1;
    public boolean showmessages = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(mygui.returnwitherguy()) {
            creeperveilAlert();
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    @SubscribeEvent
    public void oncreeperRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            FontRenderer fontRenderer = mc.fontRendererObj;

            if (SpiritStartTime != -1 && System.currentTimeMillis() - SpiritStartTime <= 10000) {
                renderAlert(" Creeper Veil ON!! ", SpiritStartTime, 10000, 10000, 40);
            }
        }
    }

    private void renderAlert(String message, long startTime, long displayDuration, long cooldownDuration, int yOffset) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        int unscaledWidth = Display.getWidth();
        int unscaledHeight = Display.getHeight();

        int mango[] = eatmydog();
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+mango[0]));

        float xscale = screenWidth/unscaledWidth;
        float yscale = screenHeight/unscaledHeight;
//        int x = (int) ((screenWidth / 2 - 2.4*fontRenderer.getStringWidth(message) ) / 3.0f);
//        int y = (int) ((3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f);
        int y = (int) (screenHeight*mango[3]/400f);
        int x = (int) (screenWidth*mango[0]/400f);

        int countdownY = (int) ((1.5*3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f);
//        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Width = "+unscaledWidth+"\n"+"Height = "+unscaledHeight+"\n"));
//        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+ntyCommand.doopengui()));//works fine

        if (System.currentTimeMillis() - startTime <= displayDuration&&showmessages) {
            GL11.glPushMatrix();
            GL11.glScalef(3.0f, 3.0f, 3.0f);

            if(mygui.shouldBigNotif()) {
                mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.OBFUSCATED + "aaa" + EnumChatFormatting.RESET + EnumChatFormatting.BOLD + message + EnumChatFormatting.RESET + EnumChatFormatting.OBFUSCATED + "aaa", x, y, 0x6f6f6f);
            }

            String countdown = String.valueOf(10 - (System.currentTimeMillis() - startTime) / 1000);
            int countdownX = (int) ((screenWidth / 2 - fontRenderer.getStringWidth(countdown) / 2) / 3.0f);
            if(mygui.shouldBigCountdown()) {
                mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.BOLD + countdown, countdownX - 2, countdownY, 0xFFFFFF);
            }
            GL11.glPopMatrix();
        }

        String cooldownMessage = "W.Cloak cooldown: " + (cooldownDuration / 1000 - (System.currentTimeMillis() - startTime) / 1000) + "s";
        if(mygui.shouldSmallCountdown()) {
            mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.BOLD + cooldownMessage, (int) screenWidth / 80, 5 * countdownY, 0x6f6f6f);
        }
    }

    public void creeperveilAlert() {
        showmessages = true;
        SpiritStartTime = System.currentTimeMillis();
    }

    dog Dog = new dog();
    private void cookmydog() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Dog.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(Dog);
            objectOutputStream.close();
        }
        catch (IOException e) {
            ;
        }
    }

    public int[] eatmydog(){
        int banana[] = new int[6];
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Dog.ser"));
            dog restoredDog = (dog) objectInputStream.readObject();
            banana[0]=restoredDog.dogBigNotifx;
            banana[1]=restoredDog.dogBigCountdownx;
            banana[2]=restoredDog.dogSmallCountdownx;
            banana[3]=restoredDog.dogBigNotify ;
            banana[4]=restoredDog.dogBigCountdowny;
            banana[5]=restoredDog.dogSmallCountdowny;
        }catch (Exception e){
            e.printStackTrace();
        }
        return banana;
    }



}
