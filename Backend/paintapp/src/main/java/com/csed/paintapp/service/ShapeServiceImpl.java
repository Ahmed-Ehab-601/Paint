package com.csed.paintapp.service;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShapeServiceImpl implements ShapeServices {

    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private  ShapeFactory shapeFactory;
    @Override
    public ShapeDto create(ShapeDto shapeDTO) {
        Shape shapeCreated= shapeRepository.save(shapeFactory.getShape(shapeDTO));

        return shapeCreated.getDTO();
    }
}
