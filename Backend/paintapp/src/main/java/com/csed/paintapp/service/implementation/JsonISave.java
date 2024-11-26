package com.csed.paintapp.service.implementation;

import com.csed.paintapp.ISave;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class JsonISave implements ISave {//samaa

    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private ShapeFactory shapeFactory;

    @Override
    public boolean save(String path) {

        List<Shape> shapes = (List<Shape>) shapeRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
     //   String jsonString = objectMapper.writeValueAsString(shapes);

  //      System.out.println(jsonString);
        return true;
    }
}
