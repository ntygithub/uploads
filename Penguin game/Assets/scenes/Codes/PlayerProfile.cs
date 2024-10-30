using System.Collections;
using System.Collections.Generic;
using UnityEngine;


[System.Serializable]
public class PlayerProfile{

    public int health;
    public float[] position;
    public bool checkjumpfeather;



    public PlayerProfile (mypenguin mypenguin){
        health = mypenguin.health;

        position = new float[2];
        position[0] = mypenguin.transform.position.x;
        position[1] = mypenguin.transform.position.y;
        // checkjumpfeather = mypenguin.checkjumpfeather;
    }








}
