package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
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
            shapeRepository.deleteById(this.id);
            CommandDTO commandDTO =new CommandDTO();
            commandDTO.setType("delete");
            commandDTO.setShapeDto(this.oldShapeDto);
            return commandDTO;

    }

    @Override
    public CommandDTO redo() {
        this.oldShapeDto.setId(null);
        Shape shape= shapeRepository.save(shapeFactory.getShape(this.oldShapeDto));
        this.oldShapeDto.setId(shape.getId());
        this.id = shape.getId();
        CommandDTO commandDTO =new CommandDTO();
        commandDTO.setType("create");
        commandDTO.setShapeDto(oldShapeDto);
        return commandDTO;
    }

    @Override
    public ShapeDto execute(ShapeDto shapeDto) {
        shapeDto.setId(null); // set id null to make db generate it
        shapeDto.setDefaultValue();
       Shape shapeCreated  = shapeRepository.save(shapeFactory.getShape(shapeDto)); // save shpe
       shapeDto.setId(shapeCreated.getId());
       setId(shapeCreated.getId());
       setOldShapeDto(shapeDto);
       return shapeDto;

    }
}
