package com.csed.paintapp.service.implementation;

import com.csed.paintapp.ILoad;
import com.csed.paintapp.model.DTO.ShapeDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;

public class XmlILoad implements ILoad { // samaa
    @Override
    public List<ShapeDto> load(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Wrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(path);
        Wrapper wrapperLoaded=(Wrapper) unmarshaller.unmarshal(file);
        return wrapperLoaded.getShapes();
    }
}
