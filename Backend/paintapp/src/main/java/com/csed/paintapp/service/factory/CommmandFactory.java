package com.csed.paintapp.service.factory;

import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.commandService.Command;
import com.csed.paintapp.service.commandService.CreateCommad;
import com.csed.paintapp.service.commandService.DeleteCommand;
import com.csed.paintapp.service.commandService.EditCommand;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CommmandFactory {
private final ShapeRepository shapeRepository;
private final ShapeFactory shapeFactory;



    public CommmandFactory(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
    }

    public Command getCommand(String type){
        return switch (type) {
            case "delete" -> new DeleteCommand(shapeRepository,shapeFactory);
            case "edit" -> new EditCommand(shapeRepository,shapeFactory);
            case "create" -> new CreateCommad(shapeRepository,shapeFactory);
            default -> null;
        };
    }

}
