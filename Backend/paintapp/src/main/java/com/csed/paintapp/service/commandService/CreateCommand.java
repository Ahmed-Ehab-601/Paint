package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.csed.paintapp.service.shapeService.ShapeServiceImplementation;
import com.csed.paintapp.service.shapeService.ShapeServices;
import lombok.Data;

@Data
public class CreateCommand extends Command {
    private final ShapeRepository shapeRepository;
    private  final ShapeFactory shapeFactory;
    public CreateCommand(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
       this.shapeFactory=shapeFactory;
    }
    @Override
    public CommandDTO undo() {
            shapeRepository.deleteById(id);
             return new CommandDTO("delete",oldShapeDto);
    }

    @Override
    public CommandDTO redo() {
        shapeRepository.save(shapeFactory.getShape(oldShapeDto));
        return new CommandDTO("create",oldShapeDto);
    }

    @Override
    public ShapeDto execute(ShapeDto shapeDto) {

       shapeDto.setId(ShapeServiceImplementation.ID);
       shapeRepository.save(shapeFactory.getShape(shapeDto));
       ShapeServiceImplementation.ID++;
       setId(shapeDto.getId());
       setOldShapeDto(shapeDto);
       return shapeDto;

    }
}
