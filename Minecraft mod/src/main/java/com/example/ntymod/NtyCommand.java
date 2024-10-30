package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class NtyCommand extends CommandBase {
    private boolean renderxpbartoggle = false;
    private boolean wanttoopengui = false;

    public boolean isxptoggle(){
        return renderxpbartoggle;
    }
    public boolean doopengui(){
        return wanttoopengui;
    }

    @Override
    public String getCommandName() {
        return "nty";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/nty";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "help", "withercloaknotif","autoarmour","bida","bina","cleanthechat","alerts","auctiontest");
        }

        return null;
    }



    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        try{
            if(args.length==0) {
                    sender.addChatMessage(new ChatComponentText(//this doesn't do what i want it stills ends with no slash just nty
                            "\n"+"                  "+
                                    EnumChatFormatting.GREEN+EnumChatFormatting.STRIKETHROUGH+" -----------------------------\n" +
                                    "                  "+
                                    EnumChatFormatting.GREEN+"list of commands:\n" +
                                    "                  "+
                                    EnumChatFormatting.GREEN+EnumChatFormatting.STRIKETHROUGH+" -----------------------------\n" +
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty autoarmour: rules settings\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty alerts: alerts settings \n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty withercloaknotif: creeper veil\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty bida start/end: bida's \n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty bina start/end: bina's\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty imbers: death message\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty imnotbers: no death msg\n"

                    ));
            }
            else if(args.length>0){
                if(args[0].equalsIgnoreCase("autoarmour")||args[0].equalsIgnoreCase("autoarmor")){
                    sender.addChatMessage(new ChatComponentText("autoarmour"));
                }
                else if(args[0].equalsIgnoreCase("orange")){
                    sender.addChatMessage(new ChatComponentText("orange"));
                }
                else if(args[0].equalsIgnoreCase("apple")){
                    sender.addChatMessage(new ChatComponentText("apple"));
                }
                else if(args[0].equalsIgnoreCase("bastart")){
                    sender.addChatMessage(new ChatComponentText("bastart"));
                }
                else if(args[0].equalsIgnoreCase("baend")){
                    sender.addChatMessage(new ChatComponentText("baend"));
                }
                else if(args[0].equalsIgnoreCase("cleanthechat")){
                    sender.addChatMessage(new ChatComponentText("cleanthechat"));
                }
                else if(args[0].equalsIgnoreCase("alerts")){
                    sender.addChatMessage(new ChatComponentText("alerts"));
                }
                else if(args[0].equalsIgnoreCase("help")){
                    sender.addChatMessage(new ChatComponentText(//this doesn't do what i want it stills ends with no slash just nty
                            "\n"+"                  "+
                                    EnumChatFormatting.GREEN+EnumChatFormatting.STRIKETHROUGH+" -----------------------------\n" +
                                    "                  "+
                                    EnumChatFormatting.GREEN+"list of commands:\n" +
                                    "                  "+
                                    EnumChatFormatting.GREEN+EnumChatFormatting.STRIKETHROUGH+" -----------------------------\n" +
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty autoarmour: rules settings\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty alerts: alerts settings \n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty withercloaknotif: creeper veil\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty bida start/end: bida's \n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty bina start/end: bina's\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty imbers: death message\n"+
                                    "                  "+
                                    EnumChatFormatting.GREEN+"/nty imnotbers: no death msg\n"

                    ));
                }
                else if(args[0].equalsIgnoreCase("withercloaknotif")){
                    wanttoopengui = true;
                    MinecraftForge.EVENT_BUS.register(this);
                }

                else if(args[0].equalsIgnoreCase("auctiontest")){
                    sender.addChatMessage(new ChatComponentText(
                            "\n"+"                  "+
                                    EnumChatFormatting.GOLD +EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                                    "                  "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                                    EnumChatFormatting.RESET + "                   "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------" +
                                    EnumChatFormatting.GOLD  + "                         " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
                                    EnumChatFormatting.RESET + "                   "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------" +
                                    EnumChatFormatting.GOLD +  "     " +
                                    EnumChatFormatting.GOLD  + EnumChatFormatting.UNDERLINE + EnumChatFormatting.BOLD + ">>ntymod<<" +
                                    EnumChatFormatting.GOLD +  "    " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
                                    EnumChatFormatting.RESET + "                   "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------" +
                                    EnumChatFormatting.GOLD + "                         " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
                                    "                  "+
                                    EnumChatFormatting.GOLD +EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                                    "                  "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"));
                }
                else if(args[0].equalsIgnoreCase("creeperface")){
//                    +EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
//                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
//                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
//                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------\n" +
                    sender.addChatMessage(new ChatComponentText(
                            "\n"+"                               "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "      " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                               "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "      " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                               "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "      " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                               "+
                                    EnumChatFormatting.RESET + "      " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "       \n" +"                               "+
                                    EnumChatFormatting.RESET + "   " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "--------\n"+"                               "+
                                    EnumChatFormatting.RESET + "   " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "--------\n"+"                               "+
                                    EnumChatFormatting.RESET + "   " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "--"+
                                    EnumChatFormatting.RESET + "      " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "--\n"+"                               "+
                                    EnumChatFormatting.RESET + "   " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "--"+
                                    EnumChatFormatting.RESET + "      " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "--\n"+
                                    EnumChatFormatting.RESET + "                   "));

                }

                else if(args[0].equalsIgnoreCase("2csface")){

                    sender.addChatMessage(new ChatComponentText(
                            "\n                              "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "         " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                              "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "         " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                              "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "         " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n\n\n\n\n"+"                    "+
                                    EnumChatFormatting.RESET + "   " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------------------------\n"+"                    "+
                                    EnumChatFormatting.RESET + "   " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "------------------------"+
                                    EnumChatFormatting.RESET + "  "));

                }
                else if(args[0].equalsIgnoreCase("thunderballface")){

                    sender.addChatMessage(new ChatComponentText(
                            "\n                "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "                             " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "                             " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                "+
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----"+
                                    EnumChatFormatting.RESET + "                             " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "----\n"+"                "+
                                    EnumChatFormatting.RESET + "            " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "---------------\n"+"                "+
                                    EnumChatFormatting.RESET + "            " +
                                    EnumChatFormatting.GOLD + EnumChatFormatting.OBFUSCATED + "---------------"+
                                    EnumChatFormatting.RESET + "  \n"));

                }

                else if(args[0].equalsIgnoreCase("mesecrets")){

//                    testoneofasync.getsecretsnow();


                }
                else if(args[0].equalsIgnoreCase("imbers")) {
                    ntymod nty = new ntymod();
                    nty.toggletell(true);
                    sender.addChatMessage(new ChatComponentText("telleveryone is now: " + nty.telleveryone));
                }
                else if(args[0].equalsIgnoreCase("imnotbers")) {
                    ntymod nty = new ntymod();
                    nty.toggletell(false);
                    sender.addChatMessage(new ChatComponentText("telleveryone is now: " + nty.telleveryone));
                }
            }
        }
        catch (Exception e){
            sender.addChatMessage(new ChatComponentText("someerrorprobably"));
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(wanttoopengui) {
            Minecraft.getMinecraft().displayGuiScreen(new actualguinow());
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // 0 means everyone can use this command
    }
}