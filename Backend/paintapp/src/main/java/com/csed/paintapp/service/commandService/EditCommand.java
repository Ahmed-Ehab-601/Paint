package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.repository.ShapeRepository;

public class EditCommand extends Command {
    private final ShapeRepository shapeRepository;
    public  EditCommand(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }
    @Override
    public CommandDTO undo() {
        return null;
    }

    @Override
    public CommandDTO redo() {
        return null;
    }

    @Override
    public ShapeDto execute() {
        return null;
    }
}
