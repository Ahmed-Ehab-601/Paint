package com.csed.paintapp.service.SaveLoadService;
import com.csed.paintapp.ILoad;
import com.csed.paintapp.ISave;
import com.csed.paintapp.service.factory.SaveLoadFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SaveLoadService {
    @Autowired
    private SaveLoadFactory saveLoadFactory;
    public ISave getSaveByType(String type){
     return saveLoadFactory.getSaveDelegate(type);
   }
    public ILoad getLoadByType(String type){
        return saveLoadFactory.getLoadDelegate(type);
    }

}
