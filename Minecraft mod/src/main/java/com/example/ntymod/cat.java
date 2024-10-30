package com.example.ntymod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.*;
import java.util.Vector;

public class cat implements Serializable {

    Vector<String> waypointnames= new Vector<String>();
    String[][][] waypointstring= new String[7][20][5]; //nrooms, 20 wp per room, 5 datas per wp


    public void addroomwaypoint(String roomname, String title, String type, String x, String y, String z){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Cat.ser"));
            cat restoredCat = (cat) objectInputStream.readObject();
            waypointnames = restoredCat.waypointnames;
            waypointstring = restoredCat.waypointstring;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (!waypointnames.contains(roomname)) {
            waypointnames.add(roomname);
        }
        int check =0;
        while(waypointstring[waypointnames.indexOf(roomname)][check][0]!=null){
            check++;
        }
        waypointstring[waypointnames.indexOf(roomname)][check][0] = title;
        waypointstring[waypointnames.indexOf(roomname)][check][1] = type;
        waypointstring[waypointnames.indexOf(roomname)][check][2] = x;
        waypointstring[waypointnames.indexOf(roomname)][check][3] = y;
        waypointstring[waypointnames.indexOf(roomname)][check][4] = z;


        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Cat.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.out.println("coafasdgfdsdffind name");
        }
    }



    public void getroomwaypoints(String roomname){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Cat.ser"));
            cat restoredCat = (cat) objectInputStream.readObject();
            waypointnames = restoredCat.waypointnames;
            waypointstring = restoredCat.waypointstring;

        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (waypointnames.contains(roomname)) {
                for(int j=0;j<20;j++) {
                    if(waypointstring[waypointnames.indexOf(roomname)][j][0]==null){
                    break;
                }
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("FOUND AT: "+waypointnames.indexOf(roomname)+", "+ j));
                    for(int i =0;i<5;i++){

                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(waypointstring[waypointnames.indexOf(roomname)][j][i]));
                }
            }
        }
        else{
            System.out.println("couldnt find name");
        }

    }



}

