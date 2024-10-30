package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class speedruntimer {
    public boolean stopclearsplit = false;
    public boolean stopmaxorsplit = false;
    public boolean stopstormsplit = false;
    public boolean stopgoldorsplit = false;
    public boolean stopnecronsplit = false;
    public boolean shouldstarttiming = false;
    long startTime = 0;
    long splitnow = 0;
    long runningTime = 0;

    long[] splitslist = new long[6];
    Boolean shouldpineapple0 = false;
    Boolean shouldpineapple1 = false;
    Boolean shouldpineapple2 = false;
    Boolean shouldpineapple3 = false;
    Boolean shouldpineapple4 = false;
    Boolean shouldpineapple5 = false;
    int thesplitnumber = 0;
    long runstartTime = 0;

    String[] splitstitle = {"Clear", "Maxor", "Storm", "Goldor", "Necron","Total"};
    public String sendaway = "";
    String banana = "";
    String banana1 = (EnumChatFormatting.GRAY+"\n"+EnumChatFormatting.GRAY+"                  "+
            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
            EnumChatFormatting.GRAY + "                  "+
            EnumChatFormatting.GRAY + "             " +
            EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Splits timer" +
            EnumChatFormatting.GRAY +  "    \n" +
            EnumChatFormatting.GRAY +"                  "+
            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n");
    String base = (EnumChatFormatting.GRAY+"\n"+EnumChatFormatting.GRAY+"                  "+
            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
            EnumChatFormatting.GRAY + "                  "+
            EnumChatFormatting.GRAY + "             " +
            EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Splits timer" +
            EnumChatFormatting.GRAY +  "    \n" +
            EnumChatFormatting.GRAY +"                  "+
            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n");

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if(shouldstarttiming){
            thesplitnumber = 0;
            shouldpineapple0 = false;
            shouldpineapple1 = false;
            shouldpineapple2 = false;
            shouldpineapple3 = false;
            shouldpineapple4 = false;
            runstartTime = System.currentTimeMillis();
            shouldpineapple5 =true;
            runningTime=0;
            startTime = System.currentTimeMillis();
            shouldstarttiming = false;
            banana1 = base;
            shouldpineapple0 = true;



        }
        if(shouldpineapple0){
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[0] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
        }
        if(stopclearsplit){ // after clear
            thesplitnumber++;
            shouldpineapple0 = false;
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + splitstitle[0] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
            splitslist[0] = dothething();
            banana1 = banana;
            shouldpineapple1 = true;
            stopclearsplit= false;

        }
        if(shouldpineapple1){
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[1] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
        }
        if(stopmaxorsplit){
            thesplitnumber++;
            shouldpineapple1 = false;
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + splitstitle[1] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
            splitslist[1] = dothething();
            banana1 = banana;
            shouldpineapple2 = true;
            stopmaxorsplit = false;

        }
        if(shouldpineapple2){
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[2] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
        }
        if(stopstormsplit){
            thesplitnumber++;
            shouldpineapple2 = false;
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + splitstitle[2] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
            splitslist[2] = dothething();
            banana1 = banana;
            shouldpineapple3 = true;
            stopstormsplit = false;

        }
        if(shouldpineapple3){
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[3] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
        }
        if(stopgoldorsplit){
            thesplitnumber++;
            shouldpineapple3 = false;
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + splitstitle[3] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
            splitslist[3] = dothething();
            banana1 = banana;
            shouldpineapple4 = true;
            stopgoldorsplit = false;

        }
        if(shouldpineapple4){
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[4] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
        }
        if(stopnecronsplit){
            thesplitnumber++;
            shouldpineapple4 = false;
            banana= banana1 + ""+EnumChatFormatting.GRAY+"                             "+ EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + splitstitle[4] +": "+ convertohumanreadable((float) ((int) (System.currentTimeMillis()-startTime)/10)/100)+"\n";
            splitslist[4] = dothething();
            banana1 = banana;
            splitslist[5] = runningTime;
            shouldpineapple5 = false;
            banana=banana+("\n" + EnumChatFormatting.BLUE + "                             " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[5] + ": " + convertohumanreadable((float) ((int) (runningTime) / 10) / 100) + "\n");
            sendaway = base;
            for(int i=0;i<5;i++){
                sendaway = sendaway + (EnumChatFormatting.BLUE + "                             " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[i] + ": " + convertohumanreadable((float) ((int) (splitslist[i]) / 10) / 100) + "\n");
            }
            sendaway = sendaway + ("\n" + EnumChatFormatting.BLUE + "                             " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[5] + ": " + convertohumanreadable((float) ((int) (splitslist[5]) / 10) / 100) + "\n");
            ntymod.printthis(sendaway);
            stopnecronsplit = false;

        }
        if(shouldpineapple5) {
            lastrendersecretsAlert("" + EnumChatFormatting.BLUE + "                             " + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + splitstitle[5] + ": " + convertohumanreadable((float) ((int) (System.currentTimeMillis()-runstartTime) / 10) / 100) + "\n");
        }
        rendersecretsAlert(banana);
    }
    public long dothething(){
        splitnow = System.currentTimeMillis()-startTime;
        runningTime+=splitnow;
        startTime = System.currentTimeMillis();
        return splitnow;
    }
    @SubscribeEvent
    public void onAlertTwo(ClientChatReceivedEvent event) {

        String message2 = StringUtils.stripControlCodes(event.message.getUnformattedText());

        // if (message2.startsWith("printme")) {
        //     Minecraft.getMinecraft().thePlayer.sendChatMessage("[NPC] Mort: Good luck.");
        //     Minecraft.getMinecraft().thePlayer.sendChatMessage("[BOSS] Maxor: WELL WELL WELL LOOK WHO'S HERE!");
        //     Minecraft.getMinecraft().thePlayer.sendChatMessage("[BOSS] Storm: Pathetic Maxor, just like expected.");
        //     Minecraft.getMinecraft().thePlayer.sendChatMessage("[BOSS] Goldor: Don't think you can hide from me");
        //     Minecraft.getMinecraft().thePlayer.sendChatMessage("[BOSS] Necron: You went further than any human before, congratulations");
        //     Minecraft.getMinecraft().thePlayer.sendChatMessage("[BOSS] Necron: All this, for nothing");
        // }

        if (message2.startsWith("[NPC] Mort: Here, I found this map when I first entered the dungeon.")) {
            shouldstarttiming = true;
            wantstodisplay = true;
        }

        else if (message2.startsWith("[BOSS] Maxor: WELL! WELL! WELL! LOOK WHO'S HERE!")) {
            stopclearsplit=true;
        }

        else if (message2.startsWith("[BOSS] Storm: Pathetic Maxor, just like expected.")) {
            stopmaxorsplit = true;
        }

        else if (message2.startsWith("[BOSS] Goldor: Who dares trespass into my domain?")) {
            stopstormsplit = true;
        }

        else if (message2.startsWith("[BOSS] Necron: You went further than any human before, congratulations")) {
            stopgoldorsplit = true;
        }

        else if (message2.startsWith("[BOSS] Necron: All this, for nothing")) {
            stopnecronsplit = true;
        }


    }
    private boolean wantstodisplay = false;
    private void rendersecretsAlert(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        if (wantstodisplay) {
            GL11.glPushMatrix();
            GL11.glScalef(1.0f, 1.0f, 1.0f);

            int x = (int) screenWidth/2+190;
            int y = (int) ((3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f)-15;
            for(String line: message.split("\n")) {
                mc.ingameGUI.drawString(fontRenderer, line, x, y+=fontRenderer.FONT_HEIGHT, 0xd52a1b);
            }

            GL11.glPopMatrix();
        }
    }

    private void lastrendersecretsAlert(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        if (wantstodisplay) {
            GL11.glPushMatrix();
            GL11.glScalef(1.0f, 1.0f, 1.0f);

            int x = (int) screenWidth/2+190;
            int y = (int) ((3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f)-15;
            for(String line: message.split("\n")) {
                mc.ingameGUI.drawString(fontRenderer, line, x, y+fontRenderer.FONT_HEIGHT*(thesplitnumber+7), 0xd52a1b);
            }

            GL11.glPopMatrix();
        }
    }

    private boolean worldJustLoaded = false;
    private boolean worldJustUnloaded = false;

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        this.worldJustLoaded = true;
    }


    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (this.worldJustLoaded) {
            this.worldJustLoaded = false;
            wantstodisplay = false;
        }
    }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (event.phase == TickEvent.Phase.END) {
            if (mc.theWorld == null && !worldJustUnloaded) {
                worldJustUnloaded = true;
                wantstodisplay=false;
            }
            else if (mc.theWorld != null && worldJustUnloaded) {
                worldJustUnloaded = false;
            }
        }
    }

    private String convertohumanreadable(float something){
        int minutes = (int) something/60;
        int seconds = (int) something%60;
        float milis = something - ((int) something);
        return ""+ minutes+":" +String.format("%05.2f",(float) seconds + milis);
    }



}
