package com.csed.paintapp.service.shapeService;

import com.csed.paintapp.model.DTO.ShapeDto;

public interface ShapeServices {
    ShapeDto create (ShapeDto shapeDTO);

    void delete(Long id);

    ShapeDto update (ShapeDto shapeDto);

    ShapeDto copy (Long id) throws CloneNotSupportedException;





}
