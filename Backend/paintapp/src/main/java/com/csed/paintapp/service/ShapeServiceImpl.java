package com.csed.paintapp.service;

import com.csed.paintapp.model.DTO.ShapeDTO;
import com.csed.paintapp.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShapeServiceImpl implements ShapeServices{
    @Autowired
    private ShapeRepository shapeRepository;

    @Override
    public Long create(ShapeDTO shapeDTO) {


    }
}
