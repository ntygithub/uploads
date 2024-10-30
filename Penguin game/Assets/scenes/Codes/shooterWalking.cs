using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class shooterWalking : StateMachineBehaviour
{   
    private float speed = 2.5f;
    public float attackingdistance1 =20f;
    Transform player;
    Rigidbody2D rb;
    shooter shooter;


    private float shootcooldown =2.0f;
	private float shootcooldownCounter;

    private bool wanttochase=true;
    private int counter;

    override public void OnStateEnter(Animator animator, AnimatorStateInfo stateInfo, int layerIndex){
       player = GameObject.FindGameObjectWithTag("Player").transform;
       rb= animator.GetComponent<Rigidbody2D>();
       shooter = animator.GetComponent<shooter>();
       wanttochase=true;
       counter =0;
    }

    override public void OnStateUpdate(Animator animator, AnimatorStateInfo stateInfo, int layerIndex){
        shooter.shooterLookatplayer();
        Vector2 target = new Vector2(player.position.x,rb.position.y);
        Vector2 newPos =Vector2.MoveTowards(rb.position,target,speed*Time.fixedDeltaTime);

        if((Vector2.Distance(player.position, rb.position)>attackingdistance1*2&&counter==0)|!shooter.isGrounded()|Vector2.Distance(player.position, rb.position)<attackingdistance1/2){
            wanttochase=false;
            animator.SetBool("wantstochase",false);
            counter +=1;
        }

        if(wanttochase && shooter.isGrounded()){rb.MovePosition(newPos);}

        shootcooldownCounter -=Time.deltaTime;
        if(Vector2.Distance(player.position, rb.position)<=attackingdistance1 && shootcooldownCounter<=0){ //add a cgeck for time
            animator.SetTrigger("shooterAttacks");
            shootcooldownCounter = shootcooldown;
        }
        else if(Vector2.Distance(player.position, rb.position)>=attackingdistance1){ 
            animator.ResetTrigger("shooterAttacks");
        }
    }

    override public void OnStateExit(Animator animator, AnimatorStateInfo stateInfo, int layerIndex)
    {
       animator.ResetTrigger("shooterAttacks");
    }
    
   
}
