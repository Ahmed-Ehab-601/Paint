package com.csed.paintapp.service;

import com.csed.paintapp.model.DTO.ShapeDto;

import java.util.List;

public interface LoadService {

    List<ShapeDto> load (String path);
}
