package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;



import java.util.*;

public class utilititties {
    public static boolean inSkyblock = false;
    public static boolean inCatacombs = false;
    public static boolean dungeonOverride = false;

    public static void checkForSkyblock() {
        if (dungeonOverride) {
            inSkyblock = true;
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = scoreboardhandler.cleanSB(scoreboardObj.getDisplayName());
                if (scObjName.contains("SKYBLOCK")) {
                    inSkyblock = true;
                    return;
                }
            }
        }
        inSkyblock = false;
    }

    public static void checkForCatacombs() {
        if (dungeonOverride) {
            inCatacombs = true;
            return;
        }
        if (inSkyblock) {
            List<String> scoreboard = scoreboardhandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = scoreboardhandler.cleanSB(s);
                if (sCleaned.contains("The Catacombs")) {
                    inCatacombs = true;
                    return;
                }
            }
        }
        inCatacombs = false;
    }

    public static boolean mapExists() {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack mapSlot = mc.thePlayer.inventory.getStackInSlot(8); //check last slot where map should be
        if (mapSlot == null || mapSlot.getItem() != Items.filled_map || !mapSlot.hasDisplayName()) return false; //make sure it is a map, not SB Menu or Spirit Bow, etc
        return mapSlot.getDisplayName().contains("Magical Map");
    }
}
