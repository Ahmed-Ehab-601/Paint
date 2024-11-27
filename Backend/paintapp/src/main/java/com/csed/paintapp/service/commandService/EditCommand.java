package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;

import java.util.Optional;

public class EditCommand extends Command {
    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;

    public EditCommand(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
    }

    @Override
    public CommandDTO undo() {
        Optional<Shape> shapeExist = shapeRepository.findById(id);
        Shape newShape = shapeExist.get();
        shapeRepository.save(shapeFactory.getShape(shapeDto));
        shapeDto = newShape.getDTO();
        return new CommandDTO("edit",shapeDto);
    }

    @Override
    public CommandDTO redo() {
        return this.undo();
    }

    @Override
    public ShapeDto execute(ShapeDto shapeDto) {
        Optional<Shape> shape = shapeRepository.findById(shapeDto.getId());
        if(shape.isPresent()){
            this.shapeDto = shape.get().getDTO();
            Shape shapeUpdated = shapeFactory.getShape(shapeDto);
            id = shapeUpdated.getId();
            return shapeRepository.save(shapeUpdated).getDTO();
        }
        return null;


    }
}
