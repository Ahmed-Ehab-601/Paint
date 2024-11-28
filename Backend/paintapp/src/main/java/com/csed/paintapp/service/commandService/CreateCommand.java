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
            commandDTO.setShapeDto(this.shapeDto);
            return commandDTO;

    }

    @Override
    public CommandDTO redo() {
        this.shapeDto.setId(null);
        Shape shape= shapeRepository.save(shapeFactory.getShape(this.shapeDto));
        this.shapeDto= shape.getDTO();
        this.id = shape.getId();
        CommandDTO commandDTO =new CommandDTO();
        commandDTO.setType("create");
        commandDTO.setShapeDto(shapeDto);
        return commandDTO;
    }

    @Override
    public ShapeDto execute(ShapeDto shapeDto) {
        shapeDto.setId(null); // set id null to make db generate it
        shapeDto.setDefaultValue();
       Shape shapeCreated  = shapeRepository.save(shapeFactory.getShape(shapeDto)); // save shpe
       shapeDto.setId(shapeCreated.getId());
       setId(shapeCreated.getId());
       setShapeDto(shapeDto);
       return shapeDto;

    }
}
