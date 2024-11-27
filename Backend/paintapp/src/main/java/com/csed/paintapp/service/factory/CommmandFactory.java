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

    public CommmandFactory(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }

    public Command getCommand(String type){
        return switch (type) {
            case "delete" -> new DeleteCommand(shapeRepository);
            case "edit" -> new EditCommand(shapeRepository);
            case "create" -> new CreateCommad(shapeRepository);
            default -> null;
        };
    }

}
