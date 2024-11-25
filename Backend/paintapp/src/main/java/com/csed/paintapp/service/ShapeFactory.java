package com.csed.paintapp.service;

import com.csed.paintapp.model.*;
import com.csed.paintapp.model.DTO.ShapeDTO;
import org.springframework.stereotype.Service;

@Service
public class ShapeFactory {
    public Shape getShape(ShapeDTO shapeDTO){
        switch (shapeDTO.getType()){
            case "Circle" : return new Circle (shapeDTO);
            case "Rectangle" : return new Rectangle (shapeDTO);
            case "Triangle" : return new Triangle (shapeDTO);
            case "Line" : return new Line (shapeDTO);
            case "Ellipse" : return new Ellipse (shapeDTO);
            case "Square" : return new Square (shapeDTO);
            default: return null;
        }

    }

}
