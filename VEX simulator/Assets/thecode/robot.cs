using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class robot : MonoBehaviour{
    public float moveMult;
    public float turnMult;
    private Rigidbody rb;

    [SerializeField] Transform groundCheck;
    [SerializeField] Transform groundCheck2;
    [SerializeField] Transform groundCheck3;
    [SerializeField] LayerMask groundmat;
    [SerializeField] LayerMask triball;

    public GameObject triballPrefab;
    public GameObject triballspawnerposition;

    public GameObject wingsdown;
    public GameObject wingsup;
    private bool wingsbool = false;


    ////////////////////////////////////////////////////////////////////////////////////


    void Start(){
        rb=GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update(){   
        if(IsGrounded() | IsGrounded2() | IsGrounded3()){
            transform.Translate(Input.GetAxis("Horizontal")* moveMult*Time.deltaTime,0,Input.GetAxis("Vertical")*moveMult*Time.deltaTime);
            transform.Rotate(Vector3.up*Input.GetAxis("Mouse X")*turnMult*Time.deltaTime);
        }
        TheWings();
        
    }
    
    bool IsGrounded(){
        return Physics.CheckSphere(groundCheck.position, 0.2f, groundmat);
    }
    bool IsGrounded2(){
        return Physics.CheckSphere(groundCheck2.position, 0.2f, groundmat);
    }
    bool IsGrounded3(){
        return Physics.CheckSphere(groundCheck3.position, 0.2f, groundmat);
    }

    void TheWings(){
        if(Input.GetButtonDown("Fire2")){
            wingsbool = !wingsbool;
        }
        if(wingsbool){
            wingsup.SetActive(false);
            wingsdown.SetActive(true);
        }
        else if(!wingsbool){
            wingsup.SetActive(true);
            wingsdown.SetActive(false);
        }
    }



    IEnumerator twentytwoTriballs(float delay) {
        for (int i = 0; i < 22; i++){
            Instantiate(triballPrefab,triballspawnerposition.transform.position,Quaternion.identity);
            yield return new WaitForSeconds(delay);
        }
        
    }




}
//     
