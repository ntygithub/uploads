using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class boringmob : MonoBehaviour
{

    private bool isfacingleft = true;
    private float movedirection;
    
    public Animator boringmobanimator;
    private bool shootornot;
    public float thetime=0.43f;

    public float shootcooldown =0.90f;
	private float shootcooldownCounter;

    public int health = 200;

    Rigidbody2D rb;
    Transform player;

    public BoxCollider2D boxcollider;
    [SerializeField] private LayerMask floors;

    [SerializeField] private Material flashMat;
    [SerializeField] private float flashDura;
	private SpriteRenderer spriteRenderer;
	private Material originalMat;
	private Coroutine flashRoutine;



    void Start(){
        player = GameObject.FindGameObjectWithTag("Player").transform;
        spriteRenderer = GetComponent<SpriteRenderer>();
	    originalMat = spriteRenderer.material;
    }
    void Update(){


    }

    private IEnumerator FlashRoutine(){
		// this really lags the game
		spriteRenderer.material=flashMat;
		yield return new WaitForSeconds(flashDura);
		spriteRenderer.material=originalMat;
		

		flashRoutine=null;
	}

	public void Flash(){
		if(flashRoutine!=null){
			StopCoroutine(flashRoutine);

		}
		flashRoutine=StartCoroutine(FlashRoutine());
	}

    public void TakeDamage (int damage){
		health -= damage;
        Flash();
		if (health <=0){
			MeDie();
		}
	}

    void MeDie(){
        Destroy(gameObject);
	}


    
    public void boringmobLookatplayer(){
		if(isfacingleft && transform.position.x <player.position.x){
			Flip();
		}
		else if(!isfacingleft && transform.position.x >player.position.x){
			Flip();
		}
	}
	private void Flip(){
		isfacingleft =!isfacingleft;
		transform.Rotate(0.0f,180.0f,0.0f);
	}

    IEnumerator Delaya(float delay) {
		yield return new WaitForSeconds(delay);
	}

    public bool isGrounded(){
        RaycastHit2D raycastHit = Physics2D.BoxCast(boxcollider.bounds.center,boxcollider.bounds.size, 0, Vector2.down,0.1f, floors);
		return raycastHit.collider != null;
    }








}
