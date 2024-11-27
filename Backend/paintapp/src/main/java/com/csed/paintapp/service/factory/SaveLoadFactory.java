package com.csed.paintapp.service.factory;

import com.csed.paintapp.service.saveLoadService.*;
import org.springframework.stereotype.Component;

@Component
public class SaveLoadFactory {

    private final JsonISave jsonISave;
    private final JsonILoad jsonILoad;
    private final XmlISave xmlISave;
    private final XmlILoad xmlILoad;

    public SaveLoadFactory(JsonISave jsonISave, JsonILoad jsonILoad, XmlISave xmlISave, XmlILoad xmlILoad) {
        this.jsonISave = jsonISave;
        this.jsonILoad = jsonILoad;
        this.xmlISave = xmlISave;
        this.xmlILoad = xmlILoad;
    }

    public ISave getSaveDelegate(String saveType){

        return switch (saveType) {
            case "xml" -> xmlISave;
            case "json" -> jsonISave;
            default -> null;
        };

    }
    public ILoad getLoadDelegate(String loadType){

        return switch (loadType) {
            case "xml" -> xmlILoad;
            case "json" -> jsonILoad;
            default -> null;
        };

    }

}
