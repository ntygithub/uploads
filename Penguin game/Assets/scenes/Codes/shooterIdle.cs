using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class shooterIdle : StateMachineBehaviour
{   
    private float speed = 2.5f;
    public float attackingdistance1 =20f;
    Transform player;
    Rigidbody2D rb;
    shooter shooter;
    private int counter;

    private float shootcooldown =2.0f;
	private float shootcooldownCounter;

    override public void OnStateEnter(Animator animator, AnimatorStateInfo stateInfo, int layerIndex){
       player = GameObject.FindGameObjectWithTag("Player").transform;
       rb= animator.GetComponent<Rigidbody2D>();
       shooter = animator.GetComponent<shooter>();
       counter=0;
    }

    override public void OnStateUpdate(Animator animator, AnimatorStateInfo stateInfo, int layerIndex){
        Vector2 target = new Vector2(player.position.x,rb.position.y);
        Vector2 newPos =Vector2.MoveTowards(rb.position,target,speed*Time.fixedDeltaTime);

        if(Vector2.Distance(player.position, rb.position)<=attackingdistance1*2&&counter==0 &&!(Vector2.Distance(player.position, rb.position)<attackingdistance1/2)){
            counter +=1;
            animator.SetBool("wantstochase",true);
        }

        shootcooldownCounter -=Time.deltaTime;
        if(Vector2.Distance(player.position, rb.position)<=attackingdistance1/2 && shootcooldownCounter<=0){ //add a cgeck for time
            shooter.shooterLookatplayer();
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
