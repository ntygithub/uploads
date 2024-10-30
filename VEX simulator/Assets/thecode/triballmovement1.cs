using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class triballmovement1 : MonoBehaviour{
    public float moveMult;
    public float turnMult;
    Rigidbody rb;

    [SerializeField] Transform CataCheck;
    [SerializeField] LayerMask triball;
    // Start is called before the first frame update
    void Start(){
        // Cursor.lockState = CursorLockMode.Locked;
        // Cursor.visible = false;
        rb=GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update(){   
        if(Input.GetKey(KeyCode.X)&& IsCata()){
            GetComponent<Rigidbody>().velocity = new Vector3(0,20,20);
        }
    }
    
    bool IsCata(){
        return Physics.CheckSphere(CataCheck.position, 0.2f, triball);
    }
}
