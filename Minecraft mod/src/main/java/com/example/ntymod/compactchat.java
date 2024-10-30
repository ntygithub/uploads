package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class compactchat {
    public String LISTOFCHAT[][] = {
            {"Sending to server","\n"+"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                    EnumChatFormatting.RESET + "                  "+
                    EnumChatFormatting.GRAY + "        " +
                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Sending to server" +
                    EnumChatFormatting.GRAY +  "    \n" +
                    "                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"},
            {"There are blocks in the way!",""},
            {"This ability is on cooldown for",""},
            {"Warping using transfer token...","\n"+"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                    EnumChatFormatting.RESET + "                  "+
                    EnumChatFormatting.GRAY  + "                " +
                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Following" +
                    EnumChatFormatting.GRAY  + "         \n" +
                    "                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"},
            {"Profile ID:",""},
            {"Dungeon Rooms Mod is outdated. Please update to",""},
            {"Dungeon Rooms: An error has occured.",""},
            {"Warping...",""},
            {"You are playing on profile:",""},
            {"Request join for Hub #",""},
            {"Couldnt' warp you! Try again later.",""},
            {"(PLAYER_TRANSFER_COOLDOWN)","Transfer Cooldown"},
            {"Warping you to your SkyBlock island...","\n"+"                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                    EnumChatFormatting.RESET + "                  "+
                    EnumChatFormatting.GRAY + "        " +
                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Warping to island" +
                    EnumChatFormatting.GRAY +  "    \n" +
                    "                  "+
                    EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"},
    };
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
//        String unformattedMessage = event.message.getUnformattedText().replaceAll("[&§][0-9A-FM-RKa-fm-ok]", "");
        String message = event.message.getFormattedText();
        if (containswordofinterest(message)[0]!="true" ){// opposite of cointains here
            //working;
            return;
        }
        if(containswordofinterest(message)[1]!=""){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(containswordofinterest(message)[1]));
        }

        // have ann array in json string to string with detected: outputted, for input in detecteds if input == detected detected[1] or smth
        event.setCanceled(true);
    }

    public String[] containswordofinterest(String message){
        String output[] = {"",""};
        for(int i =0;i<LISTOFCHAT.length;i++){
            if(message.contains(LISTOFCHAT[i][0])){
                output[0]="true";
                output[1]=LISTOFCHAT[i][1];
            }
            else{
                ;
            }
        }
        return output;
    }
}
