using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class triballscript : MonoBehaviour{

    private Rigidbody rb;
    public GameObject therobotss;
    public GameObject triballparent;

    [SerializeField] Transform triballcheck1;
    // [SerializeField] Transform CataCheck;
    [SerializeField] LayerMask groundmat;
    [SerializeField] LayerMask triball;
    [SerializeField] LayerMask thechecky;
    [SerializeField] LayerMask goalfloor;

    
    public Transform therobot;

    public float triballspheresize;
    public float speedaa;

    public bool canintaking;
    public bool wantstointaking = false;
    public bool wantstoouttaking = false;

    private bool cancel = false;

    //////////////////////////////////////////////////////////////////////////////////////




    void Start(){
        rb=GetComponent<Rigidbody>();
    }


    void Update(){   
        if(IsGoaled()){cancel=true;}
        if(Istriball1()&&wantstointaking&&!cancel){
            rb.transform.position =Vector3.MoveTowards(rb.transform.position,therobotss.transform.position,speedaa);

            }
        if(Istriball1()&&wantstoouttaking&&!cancel){
            rb.transform.position =Vector3.MoveTowards(rb.transform.position,therobotss.transform.position,-1*speedaa);

            }
        if(Input.GetButtonDown("Fire1")){wantstointaking = true;}
        else if (Input.GetButtonUp("Fire1")){wantstointaking=false;}

        // if(wantstointaking&& Input.GetButtonDown("Fire1")){wantstoouttaking=true;}
        // else{wantstoouttaking=false;}
    }
    
    bool Istriball1(){return Physics.CheckSphere(triballcheck1.position, triballspheresize, thechecky);}
    bool IsGoaled(){return Physics.CheckSphere(triballcheck1.position, triballspheresize, goalfloor);}

    
    // bool IsCata(){
    //         return Physics.CheckSphere(CataCheck.position, 0.2f, triball);
    //     }
    // }


}
     
