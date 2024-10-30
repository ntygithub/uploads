using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class breakableobject : MonoBehaviour
{

    public int health = 10;

    Transform player;


    void Start(){
        player = GameObject.FindGameObjectWithTag("Player").transform;
    }
    void Update(){


    }


    public void TakeDamage (int damage){
		health -= damage;
		if (health <=0){
			MeDie();
		}
	}

    void MeDie(){
        Destroy(gameObject);
	}










}
