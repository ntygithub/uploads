using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class cameramove : MonoBehaviour
{	
    public float followspeed = 2f;
    public Transform target;
    public float ycameraoffset =2f;


    void Update(){
        Vector3 newPos = new Vector3(target.position.x,target.position.y +ycameraoffset,-10f);
        transform.position = Vector3.Slerp(transform.position, newPos , followspeed*Time.deltaTime);
	}

}
