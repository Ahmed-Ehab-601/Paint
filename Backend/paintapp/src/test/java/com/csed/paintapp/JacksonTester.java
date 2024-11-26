package com.csed.paintapp;

import com.csed.paintapp.model.Circle;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
@SpringBootTest
public class JacksonTester {
    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private ShapeFactory shapeFactory;

    @Test
    public void testConvertingFromObjectToJSON() throws JsonProcessingException {
        Long id = 1L;

        ShapeDto s = ShapeDto.builder()
                .type("Circle")
                .x(1.2)
                .y(22.0)
                .borderColor("red")
                .borderSize(12.3)
                .scaleX(1.2)
                .scaleY(1.2)
                .build();

        Shape circle = shapeFactory.getShape(s);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(circle);
        assertThat(res, equalTo("id\":null,\"x\":1.2,\"y\":22.0,\"scaleX\":1.2,\"scaleY\":1.2,\"borderSize\":12.3,\"borderColor\":\"red\",\"fillColor\":null,\"rotate\":null,\"radius\":null,\"dto\":{\"type\":\"Circle\",\"x\":1.2,\"y\":22.0,\"scaleX\":1.2,\"scaleY\":1.2,\"fillColor\":null,\"borderColor\":\"red\",\"id\":null,\"borderSize\":12.3,\"width\":null,\"height\":null,\"radius\":null,\"majorRadius\":null,\"minorRadius\":null,\"rotate\":null,\"points\":null"));

    }
}
