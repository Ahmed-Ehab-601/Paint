package com.csed.paintapp.service.saveLoadService;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
public class XmlILoad implements ILoad { // samaa

    private final ShapeFactory shapeFactory;
    private final ShapeRepository shapeRepository;

    public XmlILoad(ShapeRepository shapeRepository,ShapeFactory shapeFactory) {
        this.shapeFactory = shapeFactory;
        this.shapeRepository = shapeRepository;
    }


    @Override
    public List<ShapeDto> load(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Wrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(path);
        Wrapper wrapperLoaded=(Wrapper) unmarshaller.unmarshal(file);
        List<ShapeDto>shapeDtos =wrapperLoaded.getShapes();
        shapeRepository.deleteAll();
        for(ShapeDto shapeDto : shapeDtos){
            shapeDto.setId(null);
            Shape shape = shapeRepository.save(shapeFactory.getShape(shapeDto));
            shapeDto.setId(shape.getId());
        }
        return shapeDtos;
    }
}
