using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class bulletbreak : MonoBehaviour
{
    public Rigidbody2D brb;
    public GameObject banana;
    private bool killit =false;


    IEnumerator Delaya(float delay) {
		yield return new WaitForSeconds(delay);
        killit = true;
	}
    void Start(){
        killit = false;
        StartCoroutine(Delaya(0.8f));
    }
    void Update(){
        if(killit){Destroy(gameObject);}
    }

    
	

    


}
