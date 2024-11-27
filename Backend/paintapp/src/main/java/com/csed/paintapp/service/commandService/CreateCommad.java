package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;

public class CreateCommad extends Command {
    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;


    public CreateCommad(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
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
