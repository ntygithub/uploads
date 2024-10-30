using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Events;
using System;
using System.Threading;

public class pausing : MonoBehaviour
{	

	private bool isPaused =false;
	public UnityEvent GamePaused;
	public UnityEvent GameResumed;
	public UnityEvent DMGPaused;
	public UnityEvent DMGResumed;
	private Coroutine flashRoutine;
	public float flashDura;


    void Update(){
	Pause();
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
	

	// public IEnumerator DMGRoutine(){
	// 	Time.timeScale = 0;
	// 	GamePaused.Invoke();
	// 	yield return new WaitForSeconds(flashDura);
	// 	// Debug.Log("thingy");
	// 	// Time.timeScale =1;
	// 	// GameResumed.Invoke();
	// 	flashRoutine=null;
	// }

	// public void DMG(){
	// 	if(flashRoutine!=null){
	// 		StopCoroutine(flashRoutine);

	// 	}
	// 	flashRoutine=StartCoroutine(DMGRoutine());
	// }
}
