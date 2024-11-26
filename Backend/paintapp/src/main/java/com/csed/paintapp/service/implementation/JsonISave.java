package com.csed.paintapp.service.implementation;

import com.csed.paintapp.ISave;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class JsonISave implements ISave {//samaa

    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private ShapeFactory shapeFactory;

    @Override
    public boolean save(String path) throws IOException {
        List<Shape> shapes = (List<Shape>) shapeRepository.findAll();
        List<ShapeDto>shapeDtos= new ArrayList<>();
        for(Shape shape : shapes){
            shapeDtos.add(shape.getDTO());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path);
        objectMapper.writeValue(file,shapeDtos);
         return true;
    }
}
