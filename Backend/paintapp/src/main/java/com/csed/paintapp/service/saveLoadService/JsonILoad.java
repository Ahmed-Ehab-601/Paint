package com.csed.paintapp.service.saveLoadService;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.commandService.UndoRedoService;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonILoad implements ILoad { // ahmed

    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;
    public UndoRedoService undoRedoService;

    public JsonILoad(ShapeRepository shapeRepository, ShapeFactory shapeFactory, UndoRedoService undoRedoService) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
        this.undoRedoService = undoRedoService;
    }

    @Override
    public List<ShapeDto> load(String path) throws IOException {

        File file = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ShapeDto> shapes = objectMapper.readValue(file, new TypeReference<>() {});
        shapeRepository.deleteAll();
        undoRedoService.clearStacks();

        for(ShapeDto shapeDto : shapes){
            shapeDto.setId(null);
            Shape shape = shapeRepository.save(shapeFactory.getShape(shapeDto));
            shapeDto.setId(shape.getId());
        }
        return shapes;
    }
}
