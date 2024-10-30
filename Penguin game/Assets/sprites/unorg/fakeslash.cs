using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class fakeslash : MonoBehaviour
{
    public float speed = 20f;
    public Rigidbody2D brb;
    public int hitcounter;
    public GameObject bulletimpactplayer;
    private bool killit =false;
    public float thesleed = 0.5f;
    public int thedamage;

    void Start(){
        killit =false;
        brb.velocity = transform.right*speed;
        StartCoroutine(Delaya(thesleed));
    }
    void Update(){
        if(killit){Destroy(gameObject);}
    }

    void OnTriggerEnter2D(Collider2D hitInfo){
    shooter shooter = hitInfo.GetComponent<shooter>();
    if (shooter!=null){
        shooter.TakeDamage(thedamage);
        Destroy(gameObject);
    }
    boringmob boringmob = hitInfo.GetComponent<boringmob>();
    if (boringmob!=null){
        boringmob.TakeDamage(thedamage);
        Destroy(gameObject);
    }
    breakableobject breakableobject = hitInfo.GetComponent<breakableobject>();
    if (breakableobject!=null){
        breakableobject.TakeDamage(thedamage);
        Destroy(gameObject);
    }
    }

    IEnumerator Delaya(float delay) {
		yield return new WaitForSeconds(delay);
        killit = true;
	}
    


}
