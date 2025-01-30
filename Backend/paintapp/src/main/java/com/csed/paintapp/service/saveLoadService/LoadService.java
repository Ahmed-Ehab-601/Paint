package com.csed.paintapp.service.saveLoadService;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.Commands.UndoRedoService;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.csed.paintapp.service.shapeService.ShapeServiceImplementation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class LoadService {
    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;
    public UndoRedoService undoRedoService;

    public LoadService(ShapeRepository shapeRepository, ShapeFactory shapeFactory, UndoRedoService undoRedoService) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
        this.undoRedoService = undoRedoService;
    }

    public List<ShapeDto>  load( List<ShapeDto> shapes) throws IOException {
        shapeRepository.deleteAll();
        undoRedoService.clearStacks();
        ShapeServiceImplementation.ID = 1L;

        for(ShapeDto shapeDto : shapes){
            shapeDto.setId(ShapeServiceImplementation.ID);
            ShapeServiceImplementation.ID++;
            Shape shape = shapeRepository.save(shapeFactory.getShape(shapeDto));
            shapeDto.setId(shape.getId());
        }
       return shapes;
    }

}
