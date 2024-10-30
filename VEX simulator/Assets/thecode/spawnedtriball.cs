using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class spawnedtriball : MonoBehaviour
{
    
    private Rigidbody rb;
    public float thrust_x = -0.009f;
    public float thrust_y = 0.0125f;
    public float thrust_z = -0.006f;
    public float delay_t = 0.45f;
    private float banana = -1f;
    private float apple = -1f;
    private float pear = -1f;

    void Start(){
        System.Random rnd = new System.Random();
        System.Random rna = new System.Random();
        System.Random rne = new System.Random();
        if(rnd.NextDouble()<=0.5){banana=banana*-1;}
        if(rna.NextDouble()<=0.5){apple=apple*-1;}
        if(rne.NextDouble()<=0.9){pear=pear*-1;}
        
        rb = GetComponent<Rigidbody>();
        StartCoroutine(Delaya(delay_t));
        
    }

    IEnumerator Delaya(float delay) {
        System.Random rndo = new System.Random();
        System.Random rnda = new System.Random();
        System.Random rnde = new System.Random();
		yield return new WaitForSeconds(delay);

	    rb.AddForce(thrust_x + (float)(banana*rndo.NextDouble()/300), thrust_y+ (float)(pear*rnde.NextDouble()/200), thrust_z+ (float)(apple*rnda.NextDouble()/300), ForceMode.Impulse);
    }
}
