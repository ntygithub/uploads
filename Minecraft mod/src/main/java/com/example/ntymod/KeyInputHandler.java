package com.example.ntymod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (ntymod.wardrobeKey.isPressed()) {
//            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Example key pressed!"));
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/wd");
        }
        if (ntymod.petsKey.isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pet");
        }
    }
}