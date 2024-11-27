package com.csed.paintapp.service.saveLoadService;

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
    public void save(String path) throws IOException {
     // Iterable<Shape> shapes = shapeRepository.findAll();
        ShapeDto shapeDto = new ShapeDto("Circle",2.0,3.0,null,"hjk",null,null,null,null,null,null,null,null,null);
        List<ShapeDto>shapeDtos= new ArrayList<>();
//        for(Shape shape : shapes){
//            shapeDtos.add(shape.getDTO());
//        }
        shapeDtos.add(shapeDto);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path);
        objectMapper.writeValue(file,shapeDtos);

    }
}
