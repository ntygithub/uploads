package com.example.ntymod;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.codec.binary.Base64;

interface APICallback {
    void onAPIDataReceived(JsonObject data) throws IOException;
}

public class testoneofasync {

    String regex = "display:\\{(.+?)\\},Damage:";
    static String regexname = ",Name:\"(.+?)\"\\},";
    static String regextooltip = "Lore:\\[(.+?)\\],";
    Pattern pattern = Pattern.compile(regex);
    static Pattern patternname = Pattern.compile(regexname);
    static Pattern patterntooltip = Pattern.compile(regextooltip);
    static Pattern TThelp = Pattern.compile("\\d+:\"(.+)$");

    String[] armourpieces = new String[]{"","","",""};
    String[] armourTTs = new String[]{"","","",""};

    private static String getname(String string){
        String name;
        Matcher banana = patternname.matcher(string);
        if(banana.find()){
            name = banana.group(1);
        }
        else{name= "nonamefound";}
        return name;
    }

    private static String gettooltip(String string){
        String TT;
        Matcher justlike = patterntooltip.matcher(string);
        if(justlike.find()){
            TT = justlike.group(1);
        }
        else{TT= "noTTfound";}
        return TT;
    }

    private static String convertTT(String string){
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

    private static final String API_URL = "https://api.hypixel.net/skyblock/profiles?key=939e836d-b459-458f-90c0-6726c7babc58&uuid=";
    private static int[] initialsecrets = new int[5];

    public void fetchDataAsync(final APICallback callback,final String uuid) { // good job to me
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(API_URL+uuid);
                    HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
                    httpClient.setRequestMethod("GET");
                    httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                    reader.close();

                    if (jsonElement != null && jsonElement.isJsonObject()) {
                        callback.onAPIDataReceived(jsonElement.getAsJsonObject());
                    } else {
                        callback.onAPIDataReceived(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        callback.onAPIDataReceived(null);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public void fetchDataAsyncName(final APICallback callback,final String username) { // good job to me
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.mojang.com/users/profiles/minecraft/"+username);
                    HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
                    httpClient.setRequestMethod("GET");
                    httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                    reader.close();

                    if (jsonElement != null && jsonElement.isJsonObject()) {
                        callback.onAPIDataReceived(jsonElement.getAsJsonObject());
                    } else {
                        callback.onAPIDataReceived(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        callback.onAPIDataReceived(null);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }


    public static void getsecretsnow(final Boolean somethingbool, final String uuid, final String username, final int index1) {
        testoneofasync apiClient = new testoneofasync();
        apiClient.fetchDataAsync(new APICallback() {
            int banana = 0;
            int secrets1 = 1000000000;
            int mango = 0;

//            dog Dog = new dog();

            @Override
            public void onAPIDataReceived(JsonObject data) {


                if (data != null) {
                    JsonArray profiles = data.getAsJsonArray("profiles");
                    for (int i = 0; i < profiles.size(); i++) {
                        JsonObject thisProfile = profiles.get(i).getAsJsonObject();
                        if (thisProfile.get("selected").getAsBoolean()) {
                            banana = i;
                            String profileId = thisProfile.get("profile_id").getAsString();
                            JsonObject theProfile = profiles.get(banana).getAsJsonObject();
                            JsonObject members = theProfile.getAsJsonObject("members");
                            JsonObject me = members.getAsJsonObject(uuid);
                            JsonObject dungeons = me.getAsJsonObject("dungeons");
                            try {
                                int secrets = dungeons.get("secrets").getAsInt();
                                secrets1 = secrets;
//                                setsecrets(secrets);// replace this with the other thingy
                                if(somethingbool){//show at end
//                                    try {
//                                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Dog.ser"));
//                                        dog restoredDog = (dog) objectInputStream.readObject();
//                                        mango=restoredDog.initialsecrets[index1];
//
//
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//                                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[nty] Mango: "+(secrets1-mango)));
//                                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[nty] Delta Secrets for <" + username + ">, Index = " +index1+": "+(secrets-mango)));
                                    int tango = initialsecrets[index1];//future match the color of the username to thinigy
//                                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("                  "+
//                                                    EnumChatFormatting.GRAY + "        " +
//                                                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + username + ": "+ (secrets - tango)+ " secrets" +
//                                                    EnumChatFormatting.GRAY +  "    \n" ));

                                   ntymod.appendsecretline(EnumChatFormatting.GRAY +"                 "+
                                                    EnumChatFormatting.GRAY + "        " +
                                                    EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + username + ": "+ (secrets - tango)+
                                                    EnumChatFormatting.GRAY +  "    \n" );

                                }
                                if(!somethingbool){// load at begin
//                                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[nty] Secrets for <" + username + ">: "+secrets));
                                    initialsecrets[index1]=secrets;
                                }
//                                Dog.setmyinitialsecrets(secrets,index1);

//                                Dog.cookmydog();

//                                System.out.println("Secrets: " + secrets);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                } else {
                    System.out.println("Failed to fetch data.");
                }
            }
        }, uuid);

    }

    public static String convertusernametouuid(final String username){
        testoneofasync apiClient = new testoneofasync();
        final String[] ID = new String[1];
        apiClient.fetchDataAsyncName(new APICallback() {


            @Override
            public void onAPIDataReceived(JsonObject data) {


                if (data != null) {
                           String theID = data.get("id").getAsString();
                           ID[0] = theID;
//                           Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[nty] UUID: "+theID));

                } else {
                    System.out.println("Failed to fetch data.");
                    ID[0] = null;
                }
            }
        }, username);

        return ID[0];
    }

    public static void dosecretsofaname(final String username, final Boolean showornot, final int index){
        testoneofasync apiClient = new testoneofasync();
        final String[] ID = new String[1];
        apiClient.fetchDataAsyncName(new APICallback() {


            @Override
            public void onAPIDataReceived(JsonObject data) {


                if (data != null) {
                    String theID = data.get("id").getAsString();
                    ID[0] = theID;
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[nty] UUID: "+theID));
                    getsecretsnow(showornot, theID,username,index);

                } else {
                    System.out.println("Failed to fetch data.");
                    ID[0] = null;
                }
            }
        }, username);

    }

    public static void doarmourofaname(final String username){
        testoneofasync apiClient = new testoneofasync();
        final String[] ID = new String[1];
        apiClient.fetchDataAsyncName(new APICallback() {


            @Override
            public void onAPIDataReceived(JsonObject data) {


                if (data != null) {
                    String theID = data.get("id").getAsString();
                    ID[0] = theID;
                    givearmorofsomeplayer(theID,username);

                } else {
                    System.out.println("Failed to fetch data.");
                    ID[0] = null;
                }
            }
        }, username);

    }

    public static int currentsecrets = 0;

    public static void setsecrets(int triangle){
        currentsecrets = triangle ;
    }


    public static int[] eatmydog(){
        int banana[] = new int[1];
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Dog.ser"));
            dog restoredDog = (dog) objectInputStream.readObject();
            banana[0]=restoredDog.doginitialsecrets;

        }catch (Exception e){
            e.printStackTrace();
        }
        return banana;
    }

    public static void sendClickableChatMessage(String player) {
        ChatComponentText baseComponent = new ChatComponentText(                                        EnumChatFormatting.GRAY +"                  "+
                EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                EnumChatFormatting.GRAY + "                  "+
                EnumChatFormatting.GRAY + "         " +
                EnumChatFormatting.RED + EnumChatFormatting.BOLD + "Click here to kick" +
                EnumChatFormatting.GRAY +  "  \n" +
                EnumChatFormatting.GRAY +"                  "+
                EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n");

        ChatStyle style = new ChatStyle();
        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p kick " + player));
        baseComponent.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage(baseComponent);
    }


    public static void givearmorofsomeplayer(final String uuid, final String name) {
        testoneofasync apiClient = new testoneofasync();
        apiClient.fetchDataAsync(new APICallback() {
            int banana = 0;
            int secrets1 = 1000000000;

            @Override
            public void onAPIDataReceived(JsonObject data) throws IOException {
                String regex = "display:\\{(.+?)\\},Damage:";
                Pattern pattern = Pattern.compile(regex);

                String[] armourpieces = new String[]{"","","",""};
                String[] armourTTs = new String[]{"","","",""};


                if (data != null) {
                    JsonArray profiles = data.getAsJsonArray("profiles");
                    for (int i = 0; i < profiles.size(); i++) {
                        JsonObject thisProfile = profiles.get(i).getAsJsonObject();
                        if (thisProfile.get("selected").getAsBoolean()) {
                            banana = i;
                            JsonObject theProfile = profiles.get(banana).getAsJsonObject();
                            JsonObject members = theProfile.getAsJsonObject("members");
                            JsonObject me = members.getAsJsonObject(uuid);
                            JsonObject inv_armor = me.getAsJsonObject("inv_armor");
                            String base = inv_armor.get("data").getAsString();
                            String base64String = base;
                            byte[] decodedBytes = Base64.decodeBase64(base64String);
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
                            NBTTagCompound nbtTagCompound = CompressedStreamTools.readCompressed(byteArrayInputStream);

                            JsonObject accs = me.getAsJsonObject("accessory_bag_storage");
                            String pow = accs.get("highest_magical_power").getAsString();

                            JsonObject dungeons = me.getAsJsonObject("dungeons");
                            int secrets = dungeons.get("secrets").getAsInt();
                            try {
                                Matcher matcher = pattern.matcher(nbtTagCompound.toString());
                                int dummy = 0;
                                while(matcher.find()){
                                    armourpieces[3-dummy] = getname(matcher.group(1));
                                    armourTTs[3-dummy] = convertTT(gettooltip(matcher.group(1)));
                                    dummy++;

                                }
                                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY+"\n"+EnumChatFormatting.GRAY+"                  "+
                                        EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                                        EnumChatFormatting.GRAY + "                  "+
                                        EnumChatFormatting.GRAY + "          " +
                                        EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "This guy's stuff" +
                                        EnumChatFormatting.GRAY +  "  \n" +
                                        EnumChatFormatting.GRAY +"                  "+
                                        EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"));


                                for(int j=0;j<4;j++){
                                    sendHoverableMessage("                  "+armourpieces[j], armourpieces[j] + "\n" + "\n"+ armourTTs[j]);
//            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(armourpieces[i]));
                                }
                                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                                        EnumChatFormatting.GRAY +"\n                  "+
                                                EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                                                EnumChatFormatting.GRAY + "                  "+
                                                EnumChatFormatting.GRAY + "     " +
                                                EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + "Click here for inventory" +
                                                EnumChatFormatting.GRAY +  "  \n" +
                                                EnumChatFormatting.GRAY +"                  "+
                                                EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"+
                                       EnumChatFormatting.GRAY+"                  "+
                                        EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n" +
                                        EnumChatFormatting.GRAY + "                  "+
                                        EnumChatFormatting.GRAY + "                  " +
                                        EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + "Stats" +
                                        EnumChatFormatting.GRAY +  "  \n" +
                                        EnumChatFormatting.GRAY +"                  "+
                                        EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH + " -----------------------------\n"+
                                        EnumChatFormatting.GRAY + "                  "+
                                        EnumChatFormatting.GRAY + "               " +
                                        EnumChatFormatting.BLUE + EnumChatFormatting.BOLD +"MP: "+ pow+"\n"+
                                        EnumChatFormatting.GRAY + "                  "+
                                        EnumChatFormatting.GRAY + "         " +
                                        EnumChatFormatting.BLUE + EnumChatFormatting.BOLD +"Secrets: "+ secrets+"\n"));
                                sendClickableChatMessage(name);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                } else {
                    System.out.println("Failed to fetch data.");
                }
            }
        }, uuid);

    }
}
