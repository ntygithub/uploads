using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class exitbutton : MonoBehaviour
{
    // Start is called before the first frame update
    public void EndGame(){
        SceneManager.LoadScene(0);
    }
}
