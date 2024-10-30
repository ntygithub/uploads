using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class triball22spawningcopy : MonoBehaviour
{
    public GameObject triballPrefab;
    public GameObject triballspawnerposition;

    
    void Start(){
        StartCoroutine(twentytwoTriballs(0.5f));
        
    }

    IEnumerator twentytwoTriballs(float delay) {
        for (int i = 0; i < 22; i++){
            Instantiate(triballPrefab,triballspawnerposition.transform.position,Quaternion.identity);
            yield return new WaitForSeconds(delay);
        }
        
    }

}
