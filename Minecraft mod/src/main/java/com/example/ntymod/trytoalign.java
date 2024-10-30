package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class trytoalign {
    String bananaW = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";// all are the same except for I off by 2
    String banana = "abcdefghijklmnopqrstuvwxyz";// all are the same as above except for i,k,l,t,, f+1,i+2,k-1,l+3
    String bananaHAHA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";// all are the same except for I off by 2
    String space;
    //boldspace = 5, space = 4
    boolean banana1 = false;
    @SubscribeEvent
    public void onAlertTwo(ClientChatReceivedEvent event) {

        String message2 = StringUtils.stripControlCodes(event.message.getUnformattedText());
//    if(message2.contains("water")) {
//        banana1 = true;
//
//    }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
//        if(banana1) {
//            for (int i = 0; i < 63; i++) {
//                if (i == 19) {///t
//                    space = EnumChatFormatting.BOLD + "  " + EnumChatFormatting.RESET + "  ";//big - small = 2 -2
//                } else if (i == 11) {//l
//                    space = EnumChatFormatting.BOLD + "   " + EnumChatFormatting.RESET + " ";// 3-1
//                } else if (i == 5) {//f
//                    space = EnumChatFormatting.BOLD + " " + EnumChatFormatting.RESET + "   ";// 1-3
//                } else if (i == 8) {//i
//                    space = EnumChatFormatting.BOLD + "    " + EnumChatFormatting.RESET + "";// 4-0
//                } else if (i == 10) {//k
//                    space = EnumChatFormatting.BOLD + " " + EnumChatFormatting.RESET + "   ";// 1-3
//                } else if (i == 34) {//I
//                    space = EnumChatFormatting.BOLD + "  " + EnumChatFormatting.RESET + "  ";// 2-2
//                } else {
//                    space = "    ";
//                }// before the else was for the if directly precedng, not cases else
//                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("" + bananaHAHA.charAt(i) + space + "|"));
//            }
//            banana1 = false;
//        }
        }

}
