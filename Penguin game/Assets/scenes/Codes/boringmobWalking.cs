using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class boringmobWalking : StateMachineBehaviour
{   
    private float speed = 2.5f;
    public float attackingdistance1 =20f;
    Transform player;
    Rigidbody2D rb;
    boringmob boringmob;


    private bool wanttochase=true;
    private int counter;

    override public void OnStateEnter(Animator animator, AnimatorStateInfo stateInfo, int layerIndex){
       player = GameObject.FindGameObjectWithTag("Player").transform;
       rb= animator.GetComponent<Rigidbody2D>();
       boringmob = animator.GetComponent<boringmob>();
       wanttochase=true;
       counter =0;
    }

    override public void OnStateUpdate(Animator animator, AnimatorStateInfo stateInfo, int layerIndex){
        boringmob.boringmobLookatplayer();
        Vector2 target = new Vector2(player.position.x,rb.position.y);
        Vector2 newPos =Vector2.MoveTowards(rb.position,target,speed*Time.fixedDeltaTime);

        if((Vector2.Distance(player.position, rb.position)>attackingdistance1*2&&counter==0)|!boringmob.isGrounded()){
            wanttochase=false;
            animator.SetBool("wantstochase",false);
            counter +=1;
        }

        if(wanttochase && boringmob.isGrounded()){rb.MovePosition(newPos);}

    }

    override public void OnStateExit(Animator animator, AnimatorStateInfo stateInfo, int layerIndex)
    {

    }
    
   
}
