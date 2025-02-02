package com.csed.paintapp.service.shapeService;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.Commands.Command;
import com.csed.paintapp.service.Commands.UndoRedoService;
import com.csed.paintapp.service.factory.CommmandFactory;
import com.csed.paintapp.service.factory.ShapeFactory;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Data
@Service
public class ShapeServiceImplementation implements ShapeServices {


    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;
    private final CommmandFactory  commmandFactory;
    private final UndoRedoService undoRedoService;
    public static  Long ID =1L;
    public ShapeServiceImplementation(ShapeRepository shapeRepository, ShapeFactory shapeFactory, CommmandFactory commmandFactory,UndoRedoService undoRedoService) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
        this.commmandFactory = commmandFactory;
        this.undoRedoService = undoRedoService;;
    }



    @Override
    public ShapeDto create(ShapeDto shapeDTO) {
        shapeDTO.setDefaultValue();
        Command command = commmandFactory.getCommand("create");
        ShapeDto shapeCreated =  command.execute(shapeDTO);
        undoRedoService.pushUndo(command);
        return shapeCreated;
    }

    @Override
    public void delete(Long id) {
        Command command = commmandFactory.getCommand("delete");
        command.setId(id);
        command.execute(null);
        undoRedoService.pushUndo(command);
    }

    @Override
    public ShapeDto update(ShapeDto shapeDto) {

        Command command = commmandFactory.getCommand("edit");
        ShapeDto shapeCreated =  command.execute(shapeDto);
        undoRedoService.pushUndo(command);
        return shapeCreated;

    }

    @Override
    public ShapeDto copy(Long id) throws CloneNotSupportedException {

        Optional<Shape> shapeExist = shapeRepository.findById(id);
        if (shapeExist.isPresent()) {
            Random random = new Random();
            double randomDoubleX = 20 + (40 - 20) * random.nextDouble();
            double randomDoubleY = 20 + (40 - 20) * random.nextDouble();
            Shape originalShape = shapeExist.get();
            Shape copy = originalShape.clone();
            copy.setX(originalShape.getX() + randomDoubleX);
            copy.setY(originalShape.getY() + randomDoubleY);
            copy.setId(null);
            Command command = commmandFactory.getCommand("create");
            ShapeDto shapeCreated =  command.execute(copy.getDTO());
            undoRedoService.pushUndo(command);
            return shapeCreated;
        }
        return null;
    }

    public void deleteAll(){
        shapeRepository.deleteAll();
        undoRedoService.clearStacks();
        ID = 1L;

    }




}
