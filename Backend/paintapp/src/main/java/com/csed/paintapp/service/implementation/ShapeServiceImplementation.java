package com.csed.paintapp.service.implementation;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.ShapeServices;
import com.csed.paintapp.service.factory.ShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShapeServiceImplementation implements ShapeServices {

    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private ShapeFactory shapeFactory;
    @Override
    public ShapeDto create(ShapeDto shapeDTO) {
        Shape shapeCreated= shapeRepository.save(shapeFactory.getShape(shapeDTO));

        return shapeCreated.getDTO();
    }

    @Override
    public void delete(Long id) {
        shapeRepository.deleteById(id);
    }

    @Override
    public ShapeDto update(ShapeDto shapeDto) {
      Shape shapeUpdated= shapeFactory.getShape(shapeDto);
      shapeRepository.save(shapeUpdated);
      return shapeUpdated.getDTO();
    }

    @Override
    public ShapeDto copy(Long id) throws CloneNotSupportedException {

        Optional<Shape> shapeExist = shapeRepository.findById(id);
        if (shapeExist.isPresent()) {

            Shape originalShape = shapeExist.get();
            Shape copy = originalShape.clone();
            copy.setX(originalShape.getX() + 7);
            copy.setY(originalShape.getY() + 7);
            Shape savedCopy = shapeRepository.save(copy);

            return savedCopy.getDTO();
        }
        return null;
    }



}
