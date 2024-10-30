package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class spiritleap {
    private static final Pattern leapPattern = Pattern.compile("^You have teleported to (.+)!$");// figuer out what this actaully is
    @SubscribeEvent
    public void onAlert(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains("You have teleported to")){//and check that it is not sent by a player -- but inherently dodes this by start regex
            Matcher matcher = leapPattern.matcher(message);
            if (matcher.find()) {
                String name = matcher.group(1);
                Minecraft.getMinecraft().thePlayer.sendChatMessage("leaped to " + name);
            }
        }
    }
}
