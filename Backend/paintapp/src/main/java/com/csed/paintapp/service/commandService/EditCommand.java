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
    public CommandDTO undo() throws CloneNotSupportedException {
        Optional<Shape> shapeExist = shapeRepository.findById(oldShapeDto.getId());
        Shape OldShape = shapeExist.get().clone();
        Shape savedShape = shapeRepository.save(shapeFactory.getShape(this.oldShapeDto));
        this.oldShapeDto = OldShape.getDTO();
        return new CommandDTO("edit",savedShape.getDTO() );
    }

    @Override
    public CommandDTO redo() throws CloneNotSupportedException {
       return this.undo();
    }

    @Override
    public ShapeDto execute(ShapeDto newShapeDto) {
        Optional<Shape> shape = shapeRepository.findById(newShapeDto.getId());
        if(shape.isPresent()){
            this.oldShapeDto = shape.get().getDTO();
            Shape shapeUpdated = shapeFactory.getShape(newShapeDto);
            id = shapeUpdated.getId();
            this.oldShapeDto.setId(id);
            return shapeRepository.save(shapeUpdated).getDTO();
        }
        return null;


    }
}
