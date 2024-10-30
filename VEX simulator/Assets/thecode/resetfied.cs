using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class resetfied : MonoBehaviour
{
    // Start is called before the first frame update
    public void ResetGame(){
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
}
