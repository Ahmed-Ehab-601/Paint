package com.csed.paintapp.service.shapeService;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.factory.ShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShapeServiceImplementation implements ShapeServices {


    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;

    public ShapeServiceImplementation(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
    }

    @Override
    public ShapeDto create(ShapeDto shapeDTO) {
        shapeDTO.setId(null);
        Shape shape=shapeFactory.getShape(shapeDTO);
        if(shape==null){
            return null;
        }
        Shape shapeCreated= shapeRepository.save(shape);
        return shapeCreated.getDTO();
    }

    @Override
    public void delete(Long id) {
        shapeRepository.deleteById(id);
    }

    @Override
    public ShapeDto update(ShapeDto shapeDto) {
        Shape shapeUpdated = shapeFactory.getShape(shapeDto);
        return shapeRepository.save(shapeUpdated).getDTO();
    }

    @Override
    public ShapeDto copy(Long id) throws CloneNotSupportedException {

        Optional<Shape> shapeExist = shapeRepository.findById(id);
        if (shapeExist.isPresent()) {

            Shape originalShape = shapeExist.get();
            Shape copy = originalShape.clone();
            copy.setX(originalShape.getX() + 7);
            copy.setY(originalShape.getY() + 7);
            copy.setId(null);
            Shape savedCopy = shapeRepository.save(copy);

            return savedCopy.getDTO();
        }
        return null;
    }



}
