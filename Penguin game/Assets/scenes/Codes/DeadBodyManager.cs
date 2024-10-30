using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;

public static class DeadBodyManager{

    public static void Save(mypenguin mypenguin) {
        BinaryFormatter formatter = new BinaryFormatter();
        if(!Directory.Exists(Application.persistentDataPath + "/saves")){
            Directory.CreateDirectory(Application.persistentDataPath + "/saves");
        }

        string path = Application.persistentDataPath + "/saves/" + "deadbodies.save";
        FileStream file = File.Create(path);
        DeadBodyList data = new DeadBodyList(mypenguin);
        formatter.Serialize(file,data);

        
        file.Close();
    }

    public static DeadBodyList Load(){
        string path = Application.persistentDataPath + "/saves/" + "deadbodies.save";
        if (!File.Exists(path)){
            return null;
        }
        BinaryFormatter formatter = new BinaryFormatter();
        FileStream file = File.Open(path,FileMode.Open);

        try{
            DeadBodyList data = formatter.Deserialize(file) as DeadBodyList;
            file.Close();
            return data;
        }
        catch{
            Debug.LogErrorFormat("failed to load file at {0}", path);
            return null;
        }


    }








}
