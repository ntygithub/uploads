using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;

public static class SerializationManager{

    public static void Save(mypenguin mypenguin) {
        BinaryFormatter formatter = new BinaryFormatter();
        if(!Directory.Exists(Application.persistentDataPath + "/saves")){
            Directory.CreateDirectory(Application.persistentDataPath + "/saves");
        }

        string path = Application.persistentDataPath + "/saves/" + "nevergonnagiveyouup.save";
        FileStream file = File.Create(path);
        PlayerProfile data = new PlayerProfile(mypenguin);
        formatter.Serialize(file,data);

        
        file.Close();
    }

    public static PlayerProfile Load(){
        string path = Application.persistentDataPath + "/saves/" + "nevergonnagiveyouup.save";
        if (!File.Exists(path)){
            return null;
        }
        BinaryFormatter formatter = new BinaryFormatter();
        FileStream file = File.Open(path,FileMode.Open);

        try{
            PlayerProfile data = formatter.Deserialize(file) as PlayerProfile;
            file.Close();
            return data;
        }
        catch{
            Debug.LogErrorFormat("failed to load file at {0}", path);
            return null;
        }


    }








}
