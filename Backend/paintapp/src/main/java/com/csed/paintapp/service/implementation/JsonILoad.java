package com.csed.paintapp.service.implementation;

import com.csed.paintapp.ILoad;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonILoad implements ILoad { // ahmed

    @Autowired
    private ShapeRepository shapeRepository;

    @Autowired
    private ShapeFactory shapeFactory;
    @Override
    public List<ShapeDto> load(String path) throws IOException {

        File file = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ShapeDto> shapes = objectMapper.readValue(file, new TypeReference<>() {});
        for(ShapeDto shapeDto : shapes){
            shapeRepository.save(shapeFactory.getShape(shapeDto));
        }
        return shapes;
    }
}
