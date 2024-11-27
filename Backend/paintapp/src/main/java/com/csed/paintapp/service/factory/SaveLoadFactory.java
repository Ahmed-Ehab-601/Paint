package com.csed.paintapp.service.factory;

import com.csed.paintapp.service.saveLoadService.ILoad;
import com.csed.paintapp.service.saveLoadService.ISave;
import com.csed.paintapp.service.saveLoadService.JsonILoad;
import com.csed.paintapp.service.saveLoadService.JsonISave;
import com.csed.paintapp.service.saveLoadService.XmlILoad;
import com.csed.paintapp.service.saveLoadService.XmlISave;
import org.springframework.stereotype.Component;

@Component
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
