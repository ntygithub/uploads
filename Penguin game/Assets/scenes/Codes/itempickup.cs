using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.Events;

public class itempickup : Interactable{
    public item item;
    public UnityEvent jumpyfeathery;

    public override void Interact(){
        base.Interact();

        PickUp();
    }

    void PickUp(){
        Debug.Log("picking up " + item.name + " ID: " + item.Id);
        inventory.instance.Add(item);
        if(item.Id==3){
            jumpyfeathery.Invoke();
            }
        Destroy(gameObject);
    }





}
