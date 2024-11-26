package com.csed.paintapp.service.implementation;

import com.csed.paintapp.ILoad;
import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlILoad implements ILoad { // samaa
    @Autowired
    private ShapeFactory shapeFactory;
    @Autowired
    private ShapeRepository shapeRepository;
    @Override
    public List<ShapeDto> load(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Wrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(path);
        Wrapper wrapperLoaded=(Wrapper) unmarshaller.unmarshal(file);
        List<ShapeDto>shapeDtos =wrapperLoaded.getShapes();
        for(ShapeDto shapeDto : shapeDtos){
            shapeRepository.save(shapeFactory.getShape(shapeDto));
        }
        return shapeDtos;
    }
}
