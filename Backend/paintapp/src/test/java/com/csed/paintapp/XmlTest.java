package com.csed.paintapp;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.service.implementation.Wrapper;
import jakarta.xml.bind.*;
import org.junit.jupiter.api.Test;


import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.SaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class XmlTest {

    @Test
    public void test2() throws JAXBException {

        ShapeDto shapeDto = new ShapeDto("Circle",2.0,3.0,4.6,6.9,null,null,null,null,null,null,null,null,null,null,null);
        List<ShapeDto> shapes = new ArrayList<>();
        shapes.add(shapeDto);
        Wrapper wrapper = new Wrapper(shapes);
        File file = new File("/Users/ahmedehab/CSED/Paint/Backend/paintapp/src/main/resources/try.xml");
        JAXBContext context =  JAXBContext.newInstance(Wrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(wrapper,file);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        Wrapper wrapper1 = (Wrapper) unmarshaller.unmarshal(file);

        assertThat(wrapper1).isEqualTo(wrapper);
    }

}
