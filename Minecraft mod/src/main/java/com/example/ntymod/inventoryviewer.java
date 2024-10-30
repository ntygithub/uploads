package com.example.ntymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.codec.binary.Base64;

public class inventoryviewer {

    String base64String = "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";

    byte[] decodedBytes = Base64.decodeBase64(base64String);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
    NBTTagCompound nbtTagCompound = CompressedStreamTools.readCompressed(byteArrayInputStream);

    String regex = "display:\\{(.+?)\\},Damage:";
    String regexname = ",Name:\"(.+?)\"\\},";
    String regextooltip = "Lore:\\[(.+?)\\],";
    Pattern pattern = Pattern.compile(regex);
    Pattern patternname = Pattern.compile(regexname);
    Pattern patterntooltip = Pattern.compile(regextooltip);
    Pattern TThelp = Pattern.compile("\\d+:\"(.+)$");

    String[] armourpieces = new String[]{"","","",""};
    String[] armourTTs = new String[]{"","","",""};

    public inventoryviewer() throws IOException {
    }


    @SubscribeEvent
    public void onAlertTwo(ClientChatReceivedEvent event)  {

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if(message.contains("hello")){
            sendClickableChatMessage("yes", "2cs");
        }
//        if(message.contains("banana")){
//            Matcher matcher = pattern.matcher(nbtTagCompound.toString());
//        int dummy = 0;
//        while(matcher.find()){
//            armourpieces[3-dummy] = getname(matcher.group(1));
//            armourTTs[3-dummy] = convertTT(gettooltip(matcher.group(1)));
//            dummy++;
//
//        }
//        for(int i=0;i<4;i++){
//            sendHoverableMessage(armourpieces[i], armourpieces[i] + "\n" + "\n"+ armourTTs[i]);
////            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(armourpieces[i]));
//        }
//
//        }

    }
    public void sendClickableChatMessage(String themessage, String player) {
        ChatComponentText baseComponent = new ChatComponentText(themessage);
        ChatStyle style = new ChatStyle();
        style.setColor(EnumChatFormatting.AQUA);
        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p kick" + player));
        baseComponent.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage(baseComponent);
    }

    private String getname(String string){
        String name;
        Matcher banana = patternname.matcher(string);
        if(banana.find()){
            name = banana.group(1);
        }
        else{name= "nonamefound";}
        return name;
    }

    private String gettooltip(String string){
        String TT;
        Matcher justlike = patterntooltip.matcher(string);
        if(justlike.find()){
            TT = justlike.group(1);
        }
        else{TT= "noTTfound";}
        return TT;
    }

    private String convertTT(String string){
        String newTT = "";
        for(String line: string.split("\",")) {
            Matcher something = TThelp.matcher(line);
            if(something.find()){
                newTT = newTT + something.group(1) +"\n";
            }
            else{newTT = newTT  +"\n";}

        }
        return newTT;
    }

    public static void sendHoverableMessage(String message, String hoverText) {
        IChatComponent chatComponent = new ChatComponentText(message);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(hoverText));
        chatComponent.setChatStyle(new ChatStyle().setChatHoverEvent(hoverEvent));
        Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
    }




    }



