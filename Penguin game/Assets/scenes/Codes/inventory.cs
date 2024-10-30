using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class inventory : MonoBehaviour {

    #region dunno
    public static inventory instance;

    void Awake(){
        if(instance!=null){Debug.LogWarning("more1instinventory");}
        instance=this;
    }

    #endregion 
    
    public List<item> items = new List<item>();
    
    public void Add (item item){
        items.Add(item);
    }
    public void Remove (item item){
        items.Remove(item);
    }




}
