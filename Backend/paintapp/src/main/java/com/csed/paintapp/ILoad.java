package com.csed.paintapp;

import com.csed.paintapp.model.DTO.ShapeDto;

import java.io.IOException;
import java.util.List;

public interface ILoad {
    List<ShapeDto> load (String path) throws IOException;
}
