package com.csed.paintapp.service.factory;

import com.csed.paintapp.ILoad;
import com.csed.paintapp.ISave;
import com.csed.paintapp.service.implementation.JsonILoad;
import com.csed.paintapp.service.implementation.JsonISave;
import com.csed.paintapp.service.implementation.XmlILoad;
import com.csed.paintapp.service.implementation.XmlISave;

public class SaveLoadFactory {

    public ISave getSaveDelegate(String saveType){

        switch (saveType){
            case "xml" : return new XmlISave();
            case "json": return new JsonISave();
            default: return null;
        }

    }
    public ILoad getLoadDelegate(String loadType){

        switch (loadType){
            case "xml" : return new XmlILoad();
            case "json": return new JsonILoad();
            default: return null;
        }

    }

}
