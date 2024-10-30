package com.example.ntymod;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.binding.BooleanExpression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Mod(modid = ntymod.MODID, version = ntymod.VERSION)
public class ntymod {
    public static final String MODID = "ntymod";
    public static final String VERSION = "1.11.3";
    private static String banana;
    private static String termshaha;
    NtyCommand ntyCommand = new NtyCommand();
    public static KeyBinding wardrobeKey;
    public static KeyBinding petsKey;

    public static void main (String[] args){
        ;
    }


    @Mod.EventHandler
    public void preInit(final FMLInitializationEvent event) throws IOException {
        // Register the event handler
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new NtyCommand());
        MinecraftForge.EVENT_BUS.register(new guiedit(Minecraft.getMinecraft()));//works in normal mc but not the compile test for some reaosn
        MinecraftForge.EVENT_BUS.register(new compactchat());
        MinecraftForge.EVENT_BUS.register(new withercloak());
        MinecraftForge.EVENT_BUS.register(new actualguinow());
        MinecraftForge.EVENT_BUS.register(new solvers());
        wardrobeKey = new KeyBinding("key.ntymod.wardrobe", Keyboard.KEY_Y, "key.categories.ntymod");
        petsKey = new KeyBinding("key.ntymod.pets", Keyboard.KEY_U, "key.categories.ntymod");
        ClientRegistry.registerKeyBinding(wardrobeKey);
        ClientRegistry.registerKeyBinding(petsKey);
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new spiritleap());
        MinecraftForge.EVENT_BUS.register(new speedruntimer());
        MinecraftForge.EVENT_BUS.register(new trytoalign());
        MinecraftForge.EVENT_BUS.register(new inventoryviewer());
        MinecraftForge.EVENT_BUS.register(new waypointrender());


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
            showmessages=false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\n"+"                  "+
                            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                            EnumChatFormatting.RESET + "                   "+
                            EnumChatFormatting.GRAY + EnumChatFormatting.OBFUSCATED + "------" +
                            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + "----" +
                            EnumChatFormatting.BLUE + EnumChatFormatting.UNDERLINE + EnumChatFormatting.BOLD + "[ntymod]" +
                            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + "----" +
                            EnumChatFormatting.GRAY + EnumChatFormatting.OBFUSCATED + "------\n" +
                            "                  "+
                            EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"));

        }
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.thirdPersonView == 2) {
            mc.gameSettings.thirdPersonView = 0;// add a option maybe
        }

        if (event.phase == TickEvent.Phase.END) {
            if (mc.theWorld == null && !worldJustUnloaded) {
                worldJustUnloaded = true;
                showmessages=false;
            }
            else if (mc.theWorld != null && worldJustUnloaded) {
                worldJustUnloaded = false;
            }
        }
    }
    final Pattern readyPattern = Pattern.compile("^(.+) is now ready!$");
    final Pattern terminalPattern = Pattern.compile("^(.+) activated a terminal!.*$");//these three are starts with becuase has the (n/8),, fixed the matcher
    final Pattern devicePattern = Pattern.compile("^(.+) completed a device!.*$");
    final Pattern leverPattern = Pattern.compile("^(.+) activated a lever!.*$");

    final Pattern pfinderjoin = Pattern.compile("^Party Finder > (.+) joined the.*$");


    public ArrayList<String> catateam=  new ArrayList<String>();
    public int[] terminalarray = {0,0,0,0,0};
    public int[] leverarray = {0,0,0,0,0};
    public int[] devicearray = {0,0,0,0,0};
    public Boolean stilladdingnames = false;
    public Boolean addmorenames = false;
    public Boolean wearedone = false;

    public Boolean recordterminals = false;
    public Boolean telleveryone = false;

    public void toggletell(Boolean tellornot){
        telleveryone = tellornot;
    }

    @SubscribeEvent
    public void onAlert(ClientChatReceivedEvent event) {
        String message = event.message.getFormattedText();
        if (message.contains(":")) {
            return;
        }
        if(message.contains("telleveryoneNOW")){
            telleveryone = !telleveryone;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+ telleveryone));
        }
        if (message.contains("saved your life!")&&!message.contains("Spirit")) {
            bonzoAlert();
            if(telleveryone){
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Bozo mask popped - 3s");
            }
        }
        else if (message.contains("Spirit Mask saved your life!")) {
            spiritAlert();
            if(telleveryone){
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Spirit mask popped - 3s");
            }
        }
        else if (message.contains("saved you from certain death!")&&message.contains("Phoenix Pet")) {
            phoenixAlert();
            if(telleveryone){
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Phoenix popped - 3.5s");
            }
        }
        else if (message.contains("wantstodothething")) {
            wantstodothething=true;
        }
        else if (message.contains("bazinga")) {
            wantstodothething2=true;
        }
        else if (message.contains("showmenames")) {
            showmenames=true;
        }
        else if (message.contains("You despawned your")) {
            despawnAlert();
        }

        else if (message.contains("kachow")) {
            addmorenames = true;
        }
        else if (message.contains("blip")) {
            manualtestme = true;
        }

        else if (message.contains("holyhell")) {
            manualtestprint = true;
        }

        String message2 = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message2.contains("entered The Catacombs")){// or master mode
            addmorenames = true;
            clearterminalarrays();
//            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("catabombs"));
        }
        if(addmorenames) {
            if (message2.contains("is now ready") && !stilladdingnames) {
                Matcher matcher = readyPattern.matcher(message2);
                if (matcher.find()) {
                    catateam.clear();
                    String name = matcher.group(1);
                    catateam.add(name);
                }
                stilladdingnames = true;
            }
            if (message2.contains("is now ready") && stilladdingnames) {
                Matcher matcher = readyPattern.matcher(message2);
                if (matcher.find()) {
                    String name = matcher.group(1);
                    if(!catateam.contains(name)) {
                        catateam.add(name);
                    }
//                    if(catateam.size()==5){
//                        addmorenames=false;
//                        wearedone = true;
//                    }// this probably messed things up
                }
            }
        }
        String message3 = StringUtils.stripControlCodes(event.message.getFormattedText());
        if(recordterminals){
            if(message3.contains("activated a terminal")){//only this works for some rzn
//                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_BLUE + "TERM"));
                Matcher matcher1 = terminalPattern.matcher(message3);
                if (matcher1.find()) {
                    String name1 = matcher1.group(1);
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + name1));
                    terminalarray[catateam.indexOf(name1)]+=1;
                }
            }
            if(message3.contains("activated a lever")){
//                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_BLUE + "LEV"));
                Matcher matcher2 = leverPattern.matcher(message3);
                if (matcher2.find()) {
                    String name2 = matcher2.group(1);
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + name2 + "LEV"));
                    leverarray[catateam.indexOf(name2)]+=1;
                }
            }
            if(message3.contains("completed a device")){
//                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_BLUE + "DEV"));
                Matcher matcher3 = devicePattern.matcher(message3);
                if (matcher3.find()) {
                    String name3 = matcher3.group(1);
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + name3 + "DEV"));
                    devicearray[catateam.indexOf(name3)]+=1;
                }
            }
        }
        if(message.contains("Party Finder")){
            Matcher hello = pfinderjoin.matcher(message3);
            if(hello.find()){
                testoneofasync.doarmourofaname(hello.group(1));
            }
        }

    }
    private void clearterminalarrays(){
        terminalarray = new int[]{0, 0, 0, 0, 0};
        devicearray = new int[]{0, 0, 0, 0, 0};
        leverarray = new int[]{0, 0, 0, 0, 0};
    }

    public Boolean shouldprintnow = false;

    @SubscribeEvent
    public void onAlertTwo(ClientChatReceivedEvent event) {

        String message2 = StringUtils.stripControlCodes(event.message.getUnformattedText());

//        if(message2.contains("hahaha")){
//            Matcher hello = pfinderjoin.matcher("Party Finder > 2cs joined the dungeon hahah doka.");
//            if(hello.find()){
//                testoneofasync.doarmourofaname(hello.group(1));
//            }
////            testoneofasync.doarmourofaname("2cs");
//        }


        if (message2.startsWith("[NPC] Mort: Good luck.")){
            addmorenames = false;
            wearedone = true;
            stilladdingnames = false;
            banana = (EnumChatFormatting.GRAY+"\n"+EnumChatFormatting.GRAY+"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                    EnumChatFormatting.GRAY + "                  "+
                    EnumChatFormatting.GRAY + "           " +
                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Secrets count" +
                    EnumChatFormatting.GRAY +  "    \n" +
                    EnumChatFormatting.GRAY +"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n");
            termshaha = (EnumChatFormatting.GRAY+"\n"+EnumChatFormatting.GRAY+"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                    EnumChatFormatting.GRAY + "                  "+
                    EnumChatFormatting.GRAY + "           " +
                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Terminals counts" +
                    EnumChatFormatting.GRAY +  "    \n" +
                    EnumChatFormatting.GRAY +"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n");
        }

        if (message2.startsWith("[BOSS] Necron: All this, for nothing")){
            shouldprintnow=true;
            if(banana!=null) {
                secretsAlert();
            }
        }

        if (message2.startsWith("[BOSS] Necron: You went further than any human before, congratulations")){
            thebossjustdied = true;
            addmorenames = true;
            recordterminals =false;
        }

        if (message2.startsWith("[BOSS] Goldor: Who dares trespass into my domain")) {
            recordterminals = true;// dunno if work
        }


//        else if (message2.contains("SecretsNOW")) {
//            if(banana!=null) {
//                secretsAlert();
//            }
//        }





//        if (message2.contains("entered The Catacombs")){// or master mode
//            addmorenames = true;
////            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("catabombs"));
//        }
    }

    public static void appendsecretline(String secretplayer){
        banana = banana + secretplayer;
    }



    private long SpiritStartTime = -1;
    private long SecretsStartTime = -1;
    private long BonzoStartTime = -1;
    private long PetStartTime = -1;
    private long PhoenixStartTime = -1;
    public boolean showmessages = false;
    public boolean petshowmessages = false;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            FontRenderer fontRenderer = mc.fontRendererObj;

            if (SpiritStartTime != -1 && System.currentTimeMillis() - SpiritStartTime <= 30000) {
                renderAlert(" Spirit mask proc!! ", SpiritStartTime, 3000, 30000, 40,false);
            }

            if (BonzoStartTime != -1 && System.currentTimeMillis() - BonzoStartTime <= 300000) {
                renderAlert(" Bozo mask proc!! ", BonzoStartTime, 3000, 300000, 60,true);
            }
            if (PetStartTime != -1 && System.currentTimeMillis() - PetStartTime <= 3000) {
                renderpetAlert(" Pet despawned :( ", PetStartTime, 3000);
            }
            if (SecretsStartTime != -1 && System.currentTimeMillis() - SecretsStartTime <= 7000) {
                rendersecretsAlert(banana+termshaha, SecretsStartTime, 7000);
            }
            if (PhoenixStartTime != -1 && System.currentTimeMillis() - PhoenixStartTime <= 60000) {
                renderAlert(" Phoenix pet proc!! ", PhoenixStartTime, 3500, 60000, 80,true);
            }
        }
    }

    private void renderAlert(String message, long startTime, long displayDuration, long cooldownDuration, int yOffset, boolean banana) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        if (System.currentTimeMillis() - startTime <= displayDuration&&showmessages) {
            GL11.glPushMatrix();
            GL11.glScalef(3.0f, 3.0f, 3.0f);

            int x = (int) ((screenWidth / 2 - 2.3*fontRenderer.getStringWidth(message) ) / 3.0f);
            int y = (int) ((3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f);
            if(banana){
                mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.OBFUSCATED + "aaa" + EnumChatFormatting.RESET +EnumChatFormatting.BOLD + message + EnumChatFormatting.RESET+EnumChatFormatting.OBFUSCATED + "aaa", x, y, 0xff8241);
            }
            else if(!banana){
                mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.OBFUSCATED + "aaa" + EnumChatFormatting.RESET +EnumChatFormatting.BOLD + message + EnumChatFormatting.RESET+EnumChatFormatting.OBFUSCATED + "aaa", x, y, 0xdbdcf6);
            }


            String countdown = String.valueOf(3 - (System.currentTimeMillis() - startTime) / 1000);
            int countdownX = (int) ((screenWidth / 2 - fontRenderer.getStringWidth(countdown) / 2) / 3.0f);
            int countdownY = (int) ((y + 120) / 3.0f);
            mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.BOLD + countdown, countdownX-2, countdownY+13, 0xFFFFFF);

            GL11.glPopMatrix();
        }
        String cooldownMessage = "";
        if(message.charAt(1) == 'P'){
            cooldownMessage = message.charAt(1) + ". Pet cooldown: " + (cooldownDuration / 1000 - (System.currentTimeMillis() - startTime) / 1000) + "s";
        }
        else {
            cooldownMessage = message.charAt(1) + ". Mask cooldown: " + (cooldownDuration / 1000 - (System.currentTimeMillis() - startTime) / 1000) + "s";
        }

        if(banana&&showmessages){
            mc.ingameGUI.drawString(fontRenderer,  EnumChatFormatting.BOLD + cooldownMessage, 11, yOffset+140, 0xff8241);
        }
        else if(!banana&&showmessages){
            mc.ingameGUI.drawString(fontRenderer,  EnumChatFormatting.BOLD + cooldownMessage, 11, yOffset+140, 0xdbdcf6);
        }
    }

    private void renderpetAlert(String message, long startTime, long displayDuration) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();


        if (System.currentTimeMillis() - startTime <= displayDuration&&showmessages) {
            GL11.glPushMatrix();
            GL11.glScalef(3.0f, 3.0f, 3.0f);

            int x = (int) ((screenWidth / 2 - 2.3*fontRenderer.getStringWidth(message) ) / 3.0f);
            int y = (int) ((3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f);
            mc.ingameGUI.drawString(fontRenderer, EnumChatFormatting.OBFUSCATED + "aaa" + EnumChatFormatting.RESET +EnumChatFormatting.BOLD + message + EnumChatFormatting.RESET+EnumChatFormatting.OBFUSCATED + "aaa", x, y, 0xd52a1b);

            GL11.glPopMatrix();
        }
    }

    private void rendersecretsAlert(String message, long startTime, long displayDuration) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
//        String pineapple = (""+
//                EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
//                EnumChatFormatting.GRAY + "           " +
//                EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Secrets count" +
//                EnumChatFormatting.GRAY +  "    \n" +
//                EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n");
//        pineapple = pineapple + (EnumChatFormatting.GRAY + "        " +
//                EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "2CS" + ": "+ "100"+ " secrets" +
//                EnumChatFormatting.GRAY +  "    \n" );

        if (System.currentTimeMillis() - startTime <= displayDuration&&showmessages) {
            GL11.glPushMatrix();
            GL11.glScalef(1.0f, 1.0f, 1.0f);

            int x = (int) 0;
            int y = (int) ((3 * screenHeight / 16 - fontRenderer.FONT_HEIGHT / 2) / 3.0f);
            for(String line: message.split("\n")) {
                mc.ingameGUI.drawString(fontRenderer, line, x, y+=fontRenderer.FONT_HEIGHT, 0xd52a1b);
            }

            GL11.glPopMatrix();
        }
    }

    public void spiritAlert() {
        showmessages = true;
        SpiritStartTime = System.currentTimeMillis();
    }
    public boolean secretsshowmessages = false;
    public void secretsAlert() {
        showmessages = true;
        SecretsStartTime = System.currentTimeMillis();
    }

    public void despawnAlert() {
        showmessages = true;
        PetStartTime = System.currentTimeMillis();
    }

    public void bonzoAlert() {
        showmessages=true;
        BonzoStartTime = System.currentTimeMillis();
    }

    public void phoenixAlert() {
        showmessages=true;
        PhoenixStartTime = System.currentTimeMillis();
    }


//    @SubscribeEvent
//    public void onSecretAlert(ClientChatReceivedEvent event) {
//        String message = event.message.getFormattedText();
//        if (message.contains("io54k6nkj43tnu80in")&&message.contains("2CS")) {
//            Minecraft.getMinecraft().thePlayer.sendChatMessage("Boy oh boy where do I even begin. 2CS... honey, my pookie bear. I have loved you ever since I ");
//            Minecraft.getMinecraft().thePlayer.sendChatMessage("first laid eyes on you. The way you drive into the paint and strike fear into your enemies eyes.");
//            Minecraft.getMinecraft().thePlayer.sendChatMessage("I would do anything for you. I wish it were possible to freeze time so I would never have to watch ");
//            Minecraft.getMinecraft().thePlayer.sendChatMessage("you retire. You're a great husband and father, sometimes I even call you dad. ");
//            Minecraft.getMinecraft().thePlayer.sendChatMessage("I love you pookie bear, my glorious king, 2CS.");
//
//        }
//    }
    public boolean haveinitialsecretcount = false;
    public boolean wantstodothething = false;
    public boolean wantstodothething2 = false;
    public boolean showmenames = false;
    public int initialsecrets;
    public int finalsecrets;
    public boolean thebossjustdied = false;
    public boolean manualtestme = false;
    public Boolean manualtestprint = false;


    @SubscribeEvent
    public void testcatacombsTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

//        if (wantstodothething){// initialise when cata starts
//            testoneofasync.getsecretsnow(false, "3e22209e686b4f50a45e07d656f935ac");
//            wantstodothething = false;//  what is this again?-- for purely the total secret count
//        }

//        else if (wantstodothething2){ // show difference in secrets when finish
//            testoneofasync.dosecretsofaname("2CS", true); // i am such a genious
//            wantstodothething2 = false;
//        }

//        else if (showmenames){ // show difference in secrets when finish
//            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Trying to show namez"));
//            scoreboardhandler YES = new scoreboardhandler();
//            YES.maybeprintnames();
//            showmenames = false;
//        }

        if(wearedone){
            for(int i =0;i<catateam.size();i++){
                testoneofasync.dosecretsofaname(catateam.get(i),false,i);
            }
            wearedone = false;
        }

        if(thebossjustdied||manualtestme){
            for(int i =0;i<catateam.size();i++){
                testoneofasync.dosecretsofaname(catateam.get(i),true,i);
                figureoutterminalscount(catateam.get(i),i);
            }
            thebossjustdied = false;
            manualtestme = false;
        }
        if(shouldprintnow||manualtestprint){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(banana));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(termshaha));
            shouldprintnow = false;
            manualtestprint = false;
        }
    }
    public static void printthis(String message){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public static void appendterminalsline(String secretplayer){
        termshaha = termshaha + secretplayer;
    }
    public void figureoutterminalscount(String name, int index){
        String newmessage = ""+ name + ": " + terminalarray[index] + " terms, ";
        newmessage = newmessage  + devicearray[index]+ " devs, ";
        newmessage = newmessage  + leverarray[index]+ " levs ";// need a nother void
        appendterminalsline(EnumChatFormatting.GRAY +"                 "+
                EnumChatFormatting.BLUE + EnumChatFormatting.BOLD +newmessage +
                EnumChatFormatting.GRAY +  "\n" );
    }

//    static int tickAmount = 1;

//    @SubscribeEvent
//    public void onTick(TickEvent.ClientTickEvent event) {
//        if (event.phase != TickEvent.Phase.START) return;
//        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
//
//        tickAmount++;
//        if (tickAmount % 20 == 0) {
//            if (player != null) {
////                utilititties.checkForSkyblock();
//                utilititties.checkForCatacombs();
//                tickAmount = 0;
//            }
//        }
//    }




}
