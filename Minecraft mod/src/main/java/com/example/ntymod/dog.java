package com.example.ntymod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class dog implements Serializable {

    public Boolean dogBigNotif ;
    public Boolean dogBigCountdown ;
    public Boolean dogSmallCountdown ;
    public Boolean testwithercloak ;

    public int dogBigNotifx ;
    public int dogBigCountdownx ;
    public int dogSmallCountdownx ;
    public int dogBigNotify ;
    public int dogBigCountdowny ;
    public int dogSmallCountdowny ;

    public boolean doghaveinitialsecretcount;
    public int doginitialsecrets;

    public int[] initialsecrets = new int[5];

    public void getallthepiecesofshit(int onex,int twox,int threex,int oney,int twoy,int threey){
        dogBigNotifx =onex;
        dogBigCountdownx =twox;
        dogSmallCountdownx =threex;
        dogBigNotify = oney;
        dogBigCountdowny =twoy;
        dogSmallCountdowny =threey;

    }

    public void setmycatathingy(Boolean one, int two){
        doghaveinitialsecretcount = one;
        doginitialsecrets = two;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Dog.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (IOException e) {
            ;
        }
    }

    public void setmyinitialsecrets(int secrets, int index){
        initialsecrets[index] = secrets;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Dog.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (IOException e) {
            ;
        }
    }

//    dog Dog = new dog();
//
//    public void cookmydog() {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream("Dog.ser");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(Dog);
//            objectOutputStream.close();
//        }
//        catch (IOException e) {
//            ;
//        }
//    }

//    public void configuretest(Boolean thething){
//        testwithercloak = thething;
//    }
}
