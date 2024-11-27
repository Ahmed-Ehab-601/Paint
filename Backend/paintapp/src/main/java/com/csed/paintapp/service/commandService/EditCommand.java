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
        Shape OldShape = shapeExist.get();
        Shape savedShape = shapeRepository.save(shapeFactory.getShape(this.shapeDto));
        this.shapeDto = OldShape.getDTO();
        return new CommandDTO("edit",savedShape.getDTO() );
    }

    @Override
    public CommandDTO redo() {
        Optional<Shape> shapeExist = shapeRepository.findById(id);
        Shape OldShape = shapeExist.get();
        Shape savedShape = shapeRepository.save(shapeFactory.getShape(this.shapeDto));
        this.shapeDto = OldShape.getDTO();
        return new CommandDTO("edit",savedShape.getDTO() );
    }

    @Override
    public ShapeDto execute(ShapeDto newShapeDto) {
        Optional<Shape> shape = shapeRepository.findById(newShapeDto.getId());
        if(shape.isPresent()){
            this.shapeDto = shape.get().getDTO();
            Shape shapeUpdated = shapeFactory.getShape(newShapeDto);
            id = shapeUpdated.getId();
            this.shapeDto.setId(id);
            return shapeRepository.save(shapeUpdated).getDTO();
        }
        return null;


    }
}
