using System.Collections;
using System.Collections.Generic;
using UnityEngine;


[System.Serializable]
public class DeadBodyList{

    public float[,] positions;
    public int deathnumber;

    public DeadBodyList (mypenguin mypenguin){

        positions = new float[10,3];
        deathnumber = mypenguin.deathnumber;
        for (int i = 0; i < 10 ; i++){
			for (int j = 0; j < 3 ; j++){
				positions[i,j]= mypenguin.mydeadbodies[i,j];
				
			}
		}
        // if(deathnumber<=10){
        //     positions[deathnumber,0] = mypenguin.transform.position.x;
        //     positions[deathnumber,1] = mypenguin.transform.position.y;
        //     positions[deathnumber,2] = 2f;
        // }
        
    }








}
