package com.example.ntymod;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import java.io.*;

public class actualguinow extends GuiScreen {
    private final String TITLE = "Settings";
    private final int GUI_WIDTH = 200;
    private final int GUI_HEIGHT = 150;


    public static GuiSliderFixed mySlider1;
    public static GuiSliderFixed mySlider2;
    public static GuiSliderFixed mySlider3;

    public static GuiSliderFixed mySlider4;
    public static GuiSliderFixed mySlider5;
    public static GuiSliderFixed mySlider6;

    private GuiButton button;
    private GuiButton buttontest;

    private GuiButton button1;
    public Boolean buttonstate1=true;
    public String buttonstatetext1="ON";
    private GuiButton button2;
    private GuiButton button3;

    public int x1 ;
    public int x2 ;
    public int x3 ;

    public int y1 ;
    public int y2 ;
    public int y3 ;

    private Boolean boolBigNotif = true;
    private Boolean boolBigCountdown = true;
    private Boolean boolSmallCountdown = true;
    public Boolean shouldwithernotif= false;


    @Override
    public void initGui() {
        int values[] = eatmydog();
        super.initGui();
        int x = (width - GUI_WIDTH) / 2;
        int y = (height - GUI_HEIGHT) / 2;

        button = new GuiButton(0, this.width / 2 +10, height  -30, 150, 20, "Save & Exit");
        this.buttonList.add(button);

        buttontest = new GuiButton(0, this.width / 2- 75-75-10, height  -30, 150, 20, "Test");
        this.buttonList.add(buttontest);

        button1 = new GuiButton(0, this.width / 2 - 160, height / 3+10-40-25, 320, 20, "BigNotif: "+buttonstatetext1);
        this.buttonList.add(button1);
        button2 = new GuiButton(0, this.width / 2 -160, height / 3+20+20-80+80-25, 320, 20, "BigCountdown: ON");
        this.buttonList.add(button2);
        button3 = new GuiButton(0, this.width / 2 - 160, height / 3+40+30-80+40+80-25, 320, 20, "SmallCountdown: ON");
        this.buttonList.add(button3);

        mySlider4 = new GuiSliderFixed(3, width / 2 +10, height / 3+0+10-40, "BigNotif y ", values[3]/100F, 100.0F, 1.0F);//startingvalue is multiplied by max or range(?)
        buttonList.add(mySlider4);
        mySlider5 = new GuiSliderFixed(3, width / 2 +10, height / 3+20+20-80+80, "BigCountdown y ", values[4]/100F, 100.0F, 1.0F);
        buttonList.add(mySlider5);
        mySlider6 = new GuiSliderFixed(3, width / 2 +10, height / 3+40+30-80+40+80, "SmallCountdown y ", values[5]/100F, 100.0F, 1.0F);
        buttonList.add(mySlider6);

        mySlider1 = new GuiSliderFixed(3, width / 2 - 75-75-10, height / 3+0+10-40, "BigNotif x ", values[0]/100F, 100.0F, 1.0F);
        buttonList.add(mySlider1);
        mySlider2 = new GuiSliderFixed(3, width / 2 - 75-75-10, height / 3+20+20-80+80, "BigCountdown x ", values[1]/100F, 100.0F, 1.0F);
        buttonList.add(mySlider2);
        mySlider3 = new GuiSliderFixed(3, width / 2 - 75-75-10, height / 3+40+30-80+40+80, "SmallCountdown x ", values[2]/100F, 100.0F, 1.0F);
        buttonList.add(mySlider3);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @Override
    protected void actionPerformed(GuiButton abutton) throws IOException {
        if (abutton == button) {
            Dog.getallthepiecesofshit((int) (mySlider1.sliderValue*100),(int) (mySlider2.sliderValue*100),(int) (mySlider3.sliderValue*100),(int) (mySlider4.sliderValue*100),(int) (mySlider5.sliderValue*100),(int) (mySlider6.sliderValue*100));
            cookmydog();
            mc.thePlayer.closeScreen();

        }
        else if (abutton == button1) {
            buttonstate1=!buttonstate1;
            boolBigNotif=!boolBigNotif;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("justpressedbutton1"));
        }
        else if (abutton == button2) {
            Dog.getallthepiecesofshit((int) (mySlider1.sliderValue*100),(int) (mySlider2.sliderValue*100),(int) (mySlider3.sliderValue*100),(int) (mySlider4.sliderValue*100),(int) (mySlider5.sliderValue*100),(int) (mySlider6.sliderValue*100));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""+mySlider2.sliderValue*mySlider2.sliderMaxValue));
        }

        else if (abutton == buttontest) {
            shouldwithernotif = !shouldwithernotif;
            MinecraftForge.EVENT_BUS.register(new withercloak());
        }
    }

    public Boolean returnwitherguy(){
        return shouldwithernotif;
    }


    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean shouldBigNotif(){
        return boolBigNotif;
    }
    public boolean shouldBigCountdown(){
        return boolBigCountdown;
    }
    public boolean shouldSmallCountdown(){
        return boolSmallCountdown;
    }

    dog Dog = new dog();
    private void cookmydog() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Dog.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(Dog);
            objectOutputStream.close();
        }
        catch (IOException e) {
            ;
        }
    }

    public int[] eatmydog(){
        int banana[] = new int[6];
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Dog.ser"));
            dog restoredDog = (dog) objectInputStream.readObject();
            banana[0]=restoredDog.dogBigNotifx;
            banana[1]=restoredDog.dogBigCountdownx;
            banana[2]=restoredDog.dogSmallCountdownx;
            banana[3]=restoredDog.dogBigNotify ;
            banana[4]=restoredDog.dogBigCountdowny;
            banana[5]=restoredDog.dogSmallCountdowny;
        }catch (Exception e){
            e.printStackTrace();
        }
        return banana;
    }
}
