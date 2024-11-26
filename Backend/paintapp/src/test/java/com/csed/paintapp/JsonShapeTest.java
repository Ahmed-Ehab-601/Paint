package com.csed.paintapp;


import aj.org.objectweb.asm.TypeReference;
import com.csed.paintapp.model.Circle;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Rectangle;
import com.csed.paintapp.model.Shape;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JsonShapeTest {

    @Test
    public void test1() throws IOException {

        ShapeDto shapeDto = new ShapeDto("Circle",2.0,3.0,4.6,6.9,null,null,null,null,null,null,null,null,null,null,null);

        Shape circle = new Circle(shapeDto);
        Shape rect = new Rectangle(3.5,8.0);

        List<Shape> shapes = new ArrayList<>();
        shapes.add(circle);
        shapes.add(rect);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("/Users/ahmedehab/CSED/Paint/Backend/paintapp/src/main/resources/try.json");
        objectMapper.writeValue(file,shapes);

       //List<Shape> shapes1 = objectMapper.readValue(file, new TypeReference<>() {});


    }


}

