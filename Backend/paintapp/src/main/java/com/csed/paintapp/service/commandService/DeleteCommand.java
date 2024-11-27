package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;

import java.util.Optional;

public class DeleteCommand extends Command {
    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;

    public DeleteCommand(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
    }



    @Override
    public CommandDTO undo() {
        shapeDto.setId(null);
        Shape shape = shapeRepository.save(shapeFactory.getShape(shapeDto));
        shapeDto = shape.getDTO();
        id = shape.getId();
        return new CommandDTO("create",shapeDto);

    }

    @Override
    public CommandDTO redo() {
        shapeRepository.deleteById(id);
        return new CommandDTO("delete",shapeDto);

    }

    @Override
    public ShapeDto execute(ShapeDto shapeDto) {
        Optional<Shape> shape = shapeRepository.findById(id);
        if(shape.isPresent()){
            this.shapeDto = shape.get().getDTO();
            shapeRepository.deleteById(id);
        }
        return null;

    }
}
