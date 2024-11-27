package com.csed.paintapp.service.saveLoadService;
import com.csed.paintapp.service.factory.SaveLoadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveLoadService {

    private final SaveLoadFactory saveLoadFactory;

    public SaveLoadService(SaveLoadFactory saveLoadFactory) {
        this.saveLoadFactory = saveLoadFactory;
    }

    public ISave getSaveByType(String type){
     return saveLoadFactory.getSaveDelegate(type);
   }
    public ILoad getLoadByType(String type){
        return saveLoadFactory.getLoadDelegate(type);
    }

}
