using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Events;
using System;
using System.Threading;

[System.Serializable]
public class mypenguin : MonoBehaviour
{	
	[SerializeField] private float movemult;
	[SerializeField] private float jumpmult;
	[SerializeField] private float sprintmult;
	[SerializeField] private float wallmult;
	private Rigidbody2D rb;
	[SerializeField] private LayerMask walls;
	[SerializeField] private LayerMask floors;
	[SerializeField] private LayerMask enemies;
	private bool isfacingright =true;
	private bool iswaddle= false;
	private bool isjumped = false;
	private bool alreadyjumped = false;
	private float moveinputdirection;
	public BoxCollider2D boxcollider;
	public BoxCollider2D boxcollider_slide;
	public BoxCollider2D boxcollider_walk;

	private Animator animator;

	private bool ishejump;
	private float jumpTimeCounter;
	public float jumpTime;
	
	private float coyoteTime = 0.2f;
	private float coyoteTimeCounter;
	private float jumpBufferTime =0.2f;
	private float jumpBufferCounter;
	private float shootBufferTime =0.5f;
	private float shootBufferCounter;
	private float slashBufferTime =0.5f;
	private float slashBufferCounter;

	private bool issliding = false;

	public Transform firepoint;
    public GameObject bulletPrefab;
	public GameObject shooterguy;
	public GameObject fakeslashs;
	public GameObject theinventory;

	private bool isslash = false;
	private bool inventoried =false;

	private bool isPaused =false;
	public UnityEvent GamePaused;
	public UnityEvent GameResumed;

	public Image healthbar;

	public int health = 100;

	public AudioSource src;
	public AudioClip slash1,waddleonsnow;

	[SerializeField] private Material flashMat;
	[SerializeField] private float flashDura;
	private SpriteRenderer spriteRenderer;
	private Material originalMat;
	private Coroutine flashRoutine;

	public pausing pausing;

	public bool checkjumpfeather =false;
	public bool checkattackfeather =false;
	public itempickup itempickup;

	public bool tookdamagealready = false;

	public UnityEvent Slide;
	public UnityEvent Unslide;

	public UnityEvent UpdateCanvases;

	public bool playerisdead = false;
	public int deathnumber = 0;
	public float[,] mydeadbodies;

	public bool activateonlyonedeadbodies = true;
	public GameObject deadbodyPrefab;
	public GameObject deadbodyfreshPrefab;

	public ParticleSystem traileffect;
	public UnityEvent traileffectaa;
	public UnityEvent traileffectab;
	

	public void Save(){
		SerializationManager.Save(this);
	}

	public void Load(){
		PlayerProfile data = SerializationManager.Load();

		health = data.health;
		Vector2 position;
		position.x = data.position[0];
		position.y = data.position[1];

		transform.position = position;
		// checkjumpfeather = data.checkjumpfeather;
		
	}

	public void ResetDeadBodies(){
		deathnumber = 0;
		mydeadbodies = new float[10,3];
		SaveDeadBodies();
	}

	public void SaveDeadBodies(){
		DeadBodyManager.Save(this);
		
	}

	public void LoadDeadBodies(){
		// bad if die in the air
		DeadBodyList data = DeadBodyManager.Load();

		deathnumber = data.deathnumber;
		Vector2 positionofdeadbody;
		for (int i = 0; i < 10 ; i++){
			for (int j = 0; j < 3 ; j++){
				mydeadbodies[i,j] = data.positions[i,j];
				
			}
			positionofdeadbody.x = data.positions[i,0];
			positionofdeadbody.y = data.positions[i,1] - 0.4f;
			if(data.positions[i,2]>=1 && data.positions[i,2]<= 2.5){	
				Instantiate(deadbodyPrefab,positionofdeadbody,Quaternion.identity);
			}
			else if(data.positions[i,2]>=2.5 && data.positions[i,2]<= 3.5){	
				Instantiate(deadbodyfreshPrefab,positionofdeadbody,Quaternion.identity);
			}
		}
		
	}

	public void PrintDeadBodies(){
		DeadBodyList data = DeadBodyManager.Load();
		for (int i = 0; i < 10 ; i++){
			for (int j = 0; j < 3 ; j++){
				// if(data.positions[i,2]>=1){
				// 	Debug.Log("Bodyno: " + i + " thingy " + data.positions[i,j] );
				// 	}
				Debug.Log("Bodyno: " + i + " thingy " + data.positions[i,j] );
				
			}
		}
	}

	private IEnumerator waitbeforetrailoff(){
		yield return new WaitForSeconds(0.5f);
		traileffectab.Invoke();

	}

	private IEnumerator takedamageornot(){
		yield return new WaitForSeconds(2);
		tookdamagealready =false;

	}

	public void setJumpFeather(){
		checkjumpfeather =true;
	}

	public void checkJumpFeather(){
		if(checkjumpfeather){
			jumpTime = 0.35f;
		}
		else if (!checkjumpfeather){
			jumpTime = 0f;
		}
	}

	public void setAttackFeather(){
		checkattackfeather =true;
	}



	public void playSlash(){
		src.clip=slash1;
		src.Play();
	}
	
	public void playWaddle(){
		src.clip=waddleonsnow;
		src.Play();
	}

	public void TakeDamage (int damage){
		health -= damage;
		healthbar.fillAmount = health/500f;
		Flash();
		// if(pausing!=null){pausing.DMG();}
		if (health <=0){
			PlayerDie();
		}
	}

	void PlayerDie(){
		playerisdead =true;
		// Debug.Log("palyerisdead");
		deathnumber +=1;
		// Debug.Log(deathnumber);
		
	if(activateonlyonedeadbodies){
		if(deathnumber<10){
			mydeadbodies[deathnumber,0] = transform.position.x;
			mydeadbodies[deathnumber,1] = transform.position.y;
			mydeadbodies[deathnumber,2] = 2;
			}
		mydeadbodies[0,0] = transform.position.x;
		mydeadbodies[0,1] = transform.position.y;
		mydeadbodies[0,2] = 3;
		SaveDeadBodies();
		activateonlyonedeadbodies = false;
	}
		// 
		// Time.timeScale = 0;
		// GamePaused.Invoke();
	}

	public void GiveTrail(){
		if(iswaddle){traileffectaa.Invoke();}
		else if(!iswaddle){StartCoroutine(waitbeforetrailoff());}
	}

	private IEnumerator FlashRoutine(){
		// this really lags the game
		spriteRenderer.material=flashMat;
		yield return new WaitForSeconds(flashDura);
		spriteRenderer.material=originalMat;
		yield return new WaitForSeconds(flashDura);
		spriteRenderer.material=flashMat;
		yield return new WaitForSeconds(flashDura/2);
		spriteRenderer.material=originalMat;
		yield return new WaitForSeconds(flashDura/2);
		spriteRenderer.material=flashMat;
		yield return new WaitForSeconds(flashDura/2);
		spriteRenderer.material=originalMat;

		flashRoutine=null;
	}

	public void Flash(){
		if(flashRoutine!=null){
			StopCoroutine(flashRoutine);

		}
		flashRoutine=StartCoroutine(FlashRoutine());
	}

    void Start(){
	activateonlyonedeadbodies = true;
	mydeadbodies = new float[10,3];
	playerisdead = false;
	rb=GetComponent<Rigidbody2D>();
	animator = GetComponent<Animator>();
	spriteRenderer = GetComponent<SpriteRenderer>();
	originalMat = spriteRenderer.material;
	LoadDeadBodies();
    }

    void Update(){
	rb.velocity = new Vector2(Input.GetAxis("Horizontal")*movemult, rb.velocity.y);
	checkJumpFeather();
	CheckInput();
	CheckSlide();
	Checkmovedirection();
	CheckWaddle(); // needs the isGrounded()
	Jump();
	ShootFeather();
	Slash();
	UseInventory();
	// Pause();
	CollisionDamage();
	GiveTrail();
	
	tempSpawnShooter();
	if(isSprint()){
		rb.velocity = new Vector2(rb.velocity.x*sprintmult, rb.velocity.y);
		}

	if(Input.GetAxisRaw("Horizontal")!=0){
		playWaddle();
	}
	animator.SetBool("ismovings", iswaddle);
	animator.SetBool("jumpedd", isjumped);
	animator.SetBool("isslash", isslash);
	animator.SetBool("issliding", issliding);

	

	}

	private void CollisionDamage(){
		if(isEnemyDown()&&!tookdamagealready){
		TakeDamage(50);
		// Debug.Log("enemy");
		tookdamagealready=true;
		StartCoroutine(takedamageornot());
	}
	}
	
	private void Pause(){
		if(Input.GetKeyDown(KeyCode.Escape)){
			isPaused=!isPaused;
		}
		if(isPaused){
			Time.timeScale = 0;
			GamePaused.Invoke();
			}
		else{
			Time.timeScale =1;
			GameResumed.Invoke();
			}
	}

	private void Slash(){
		slashBufferCounter -= Time.deltaTime;
		if(Input.GetButtonDown("Fire2")&&slashBufferCounter<=0){
			isslash=true;
			Instantiate(fakeslashs,firepoint.position,firepoint.rotation);
			playSlash();
			slashBufferCounter=slashBufferTime;
			StartCoroutine(Delaya(0.5f));
		}
	}

	private void ShootFeather(){
		if(checkattackfeather){shootBufferCounter -= Time.deltaTime;
		if(Input.GetButton("Fire1")&&shootBufferCounter<=0){
		Instantiate(bulletPrefab,firepoint.position,firepoint.rotation);
		shootBufferCounter=shootBufferTime;
		
		}}
	}

	private void tempSpawnShooter(){
		if(Input.GetKeyDown(KeyCode.F)){
			Instantiate(shooterguy,firepoint.position,firepoint.rotation);
		}
	}
	

	private void Jump(){
	// trying to implement no holding.. im a genius
	if(jumpBufferCounter>0f&& coyoteTimeCounter >0f&&!alreadyjumped){
		rb.velocity = new Vector2(rb.velocity.x, jumpmult);
		alreadyjumped = true;
		ishejump = true;
		jumpTimeCounter = jumpTime;
		jumpBufferCounter =0f;
	}
	if(Input.GetButton("Jump")&& ishejump ){
		if(jumpTimeCounter>0){
			rb.velocity = new Vector2(rb.velocity.x, jumpmult);
			jumpTimeCounter -= Time.deltaTime;
		}
		else{ishejump = false;}
	}
	if(Input.GetButton("Jump")){isjumped = true;}
	if(Input.GetButtonDown("Jump")){
		jumpBufferCounter = jumpBufferTime;
		alreadyjumped = true;
	}
	else{
		jumpBufferCounter -=Time.deltaTime;
		alreadyjumped = false;
	}
	if(Input.GetButtonUp("Jump")){
		isjumped = false;
		ishejump = false;
		coyoteTimeCounter =0f;
	}
	if(isGrounded()){
		isjumped = false;
		coyoteTimeCounter = coyoteTime;	
	}
	else{coyoteTimeCounter -=Time.deltaTime;}
	}


	private bool isGrounded(){
		RaycastHit2D raycastHit = Physics2D.BoxCast(boxcollider.bounds.center,boxcollider.bounds.size, 0, Vector2.down,0.1f, floors);
		return raycastHit.collider != null;
	}

	private bool isWalled(){
		RaycastHit2D raycastHit = Physics2D.BoxCast(boxcollider.bounds.center,boxcollider.bounds.size, 0, new Vector2(transform.localScale.x,0),0.1f, walls);
		return raycastHit.collider != null;
	}

	private bool isEnemyDown(){
		RaycastHit2D raycastHit = Physics2D.BoxCast(boxcollider.bounds.center,boxcollider.bounds.size, 0, Vector2.down,0.1f, enemies);
		return raycastHit.collider != null;
	}

	private bool isSprint(){
		if(Input.GetKey(KeyCode.LeftShift)|Input.GetKey(KeyCode.RightShift)){
			return true;
		}
		else{
			return false;
		}
	}

	private bool CheckSlide(){
		if(Input.GetAxisRaw("Vertical")<-0.75){
			Slide.Invoke();
			issliding=true;
			return true;
		}
		else if(Input.GetAxisRaw("Vertical")>=-0.75){
			Unslide.Invoke();
			issliding=false;
			return false;
		}
		else{return false;}
	}


	private void Checkmovedirection(){
		if(isfacingright && moveinputdirection<0){
			Flip();
		}
		else if(!isfacingright && moveinputdirection>0){
			Flip();
		}
	}


	private void Flip(){
		isfacingright =!isfacingright;
		transform.Rotate(0.0f,180.0f,0.0f);
		
		

	}

	private void UseInventory(){
		if(Input.GetKeyDown(KeyCode.E)){
			inventoried = !inventoried;
		}
		if(inventoried){
			theinventory.SetActive(true);
		}
		else if (!inventoried){
			theinventory.SetActive(false);
		}
	}


	private void CheckInput(){
		moveinputdirection = Input.GetAxisRaw("Horizontal");
	}
	private void CheckWaddle(){
		if(Input.GetAxisRaw("Horizontal")!=0&&isGrounded()){
			iswaddle = true;
		}
		else{
			iswaddle=false;
			// traileffect.Stop();
			}
	}

	IEnumerator Delaya(float delay) {
		yield return new WaitForSeconds(delay);
        isslash = false;
	}


	



}
