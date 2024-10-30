using UnityEngine;

public class Interactable : MonoBehaviour {

	public float radius = 3f;				
	public Transform interactionTransform;	// The transform from where we interact in case you want to offset it
	public Transform player;		
	bool hasInteracted = false;	
	public Rigidbody2D rb;
	public float speede;

	public virtual void Interact ()
	{
		// Debug.Log("interacting");
	}

	void Update (){
		// If we are currently being focused
		// and we haven't already interacted with the object
		Vector2 target = new Vector2(player.position.x,rb.position.y);
        Vector2 newPos =Vector2.MoveTowards(rb.position,target,speede);
		if (!hasInteracted){
			// If we are close enough
			float distance = Vector3.Distance(player.position, interactionTransform.position);
			if (distance <= radius){
				rb.MovePosition(newPos);
				// Interact with the object
				if (distance <= radius/4){
					Interact();
					hasInteracted = true;
				}
			}
		}
	}

}