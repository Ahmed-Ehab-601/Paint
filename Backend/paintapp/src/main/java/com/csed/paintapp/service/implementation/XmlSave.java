package com.csed.paintapp.service.implementation;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import com.csed.paintapp.repository.ShapeRepository;
import com.csed.paintapp.service.SaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log

public class XmlSave implements SaveService { // ahmed


    @Autowired
    private ShapeRepository shapeRepository;


    @Override
    public boolean save(String path) {
        return false;
    }
}
