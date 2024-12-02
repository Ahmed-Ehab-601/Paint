package com.csed.paintapp.service.saveLoadService;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.Commands.UndoRedoService;
import com.csed.paintapp.service.factory.ShapeFactory;
import com.csed.paintapp.service.shapeService.ShapeServiceImplementation;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
public class XmlILoad implements ILoad { // samaa

    private final ShapeFactory shapeFactory;
    private final ShapeRepository shapeRepository;
    private UndoRedoService undoRedoService;

    public XmlILoad(ShapeFactory shapeFactory, ShapeRepository shapeRepository, UndoRedoService undoRedoService) {
        this.shapeFactory = shapeFactory;
        this.shapeRepository = shapeRepository;
        this.undoRedoService = undoRedoService;
    }


    @Override
    public List<ShapeDto> load(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Wrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(path);
        Wrapper wrapperLoaded=(Wrapper) unmarshaller.unmarshal(file);
        List<ShapeDto>shapeDtos =wrapperLoaded.getShapes();
        shapeRepository.deleteAll();
        undoRedoService.clearStacks();
        ShapeServiceImplementation.ID = 1L;
        for(ShapeDto shapeDto : shapeDtos){
            shapeDto.setId(ShapeServiceImplementation.ID);
            ShapeServiceImplementation.ID++;
            Shape shape = shapeRepository.save(shapeFactory.getShape(shapeDto));
            shapeDto.setId(shape.getId());
        }
        return shapeDtos;
    }
}
