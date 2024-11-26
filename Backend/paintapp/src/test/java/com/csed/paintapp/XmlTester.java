package com.csed.paintapp;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.ShapeServices;
import com.csed.paintapp.service.factory.ShapeFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class XmlTester {
    @Mock
    private ShapeRepository shapeRepository;
    @InjectMocks
    private ShapeFactory shapeFactory;
    @Test
    public void convertFromJavaObjectToXml() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Shape.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        File file = new File("src/test/resources/testxml.xml");
        ShapeDto s = ShapeDto.builder()
                .type("Circle")
                .x(1.2)
                .y(22.0)
                .borderColor("red")
                .borderSize(12.3)
                .scaleX(1.2)
                .scaleY(1.2)
                .build();
        Shape shape= shapeFactory.getShape(s);

        when(shapeRepository.save(Mockito.any())).thenReturn(shape);
        marshaller.marshal(shape,file);



    }
}
