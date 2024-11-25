package com.csed.paintapp.service.factory;

import com.csed.paintapp.service.LoadService;
import com.csed.paintapp.service.SaveService;
import com.csed.paintapp.service.implementation.JsonLoad;
import com.csed.paintapp.service.implementation.JsonSave;
import com.csed.paintapp.service.implementation.XmlLoad;
import com.csed.paintapp.service.implementation.XmlSave;

public class SaveLoadFactory {




    public SaveService getSaveDelegate(String saveType){

        switch (saveType){
            case "xml" : return new XmlSave();
            case "json": return new JsonSave();
            default: return null;
        }

    }
    public LoadService getLoadDelegate(String loadType){

        switch (loadType){
            case "xml" : return new XmlLoad();
            case "json": return new JsonLoad();
            default: return null;
        }

    }

}
