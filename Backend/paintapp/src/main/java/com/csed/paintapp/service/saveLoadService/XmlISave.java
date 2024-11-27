package com.csed.paintapp.service.saveLoadService;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Service
public class XmlISave implements ISave { // ahmed

    private final ShapeRepository shapeRepository;

    public XmlISave(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }

    @Override
    public void save(String path) throws JAXBException {
        File file = new File(path);
        Iterable<Shape> shapes = shapeRepository.findAll();
        List<ShapeDto> shapeDtos = new ArrayList<>();
        for(Shape shape : shapes){
            shapeDtos.add(shape.getDTO());
        }
        Wrapper wrapper = new Wrapper(shapeDtos);
        JAXBContext context = JAXBContext.newInstance(Wrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(wrapper,file);

    }
}
