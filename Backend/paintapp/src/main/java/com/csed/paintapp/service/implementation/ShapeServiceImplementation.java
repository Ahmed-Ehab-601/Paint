package com.csed.paintapp.service.implementation;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.ShapeServices;
import com.csed.paintapp.service.factory.ShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }

    @Override
    public ShapeDto update(ShapeDto shapeDto) {
        return null;
    }

    @Override
    public ShapeDto copy(Long id) {
        return null;
    }


}
