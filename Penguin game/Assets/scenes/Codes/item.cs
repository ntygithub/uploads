using UnityEngine;

[CreateAssetMenu(fileName="new item", menuName = "inventory/item")]

public class item: ScriptableObject {

	new public string name = "new item";
	public int Id = 0;
	public Sprite icon = null;

}