using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class trailscript : MonoBehaviour
{
    public ParticleSystem traileffect;

    void Update(){
    traileffect.Play();
	
    }
}