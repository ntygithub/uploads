using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class bullet : MonoBehaviour
{
    public float speed = 20f;
    public Rigidbody2D brb;
    public int hitcounter;
    public GameObject bulletimpactplayer;
    private bool killit =false;

    void Start(){
        killit =false;
        brb.velocity = transform.right*speed;
        StartCoroutine(Delaya(5f));
    }
    void Update(){
        if(killit){Destroy(gameObject);}
    }

    void OnTriggerEnter2D(Collider2D hitInfo){
    mypenguin player = hitInfo.GetComponent<mypenguin>();
    if (player!=null){
        player.TakeDamage(50);
        Destroy(gameObject);
        Instantiate(bulletimpactplayer,transform.position, transform.rotation);
    }
    }

    IEnumerator Delaya(float delay) {
		yield return new WaitForSeconds(delay);
        killit = true;
	}
    


}
