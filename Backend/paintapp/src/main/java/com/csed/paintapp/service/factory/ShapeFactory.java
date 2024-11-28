package com.csed.paintapp.service.factory;

import com.csed.paintapp.model.*;
import com.csed.paintapp.model.DTO.ShapeDto;
import org.springframework.stereotype.Component;

@Component
public class ShapeFactory {
    public Shape getShape(ShapeDto shapeDTO){
        return switch (shapeDTO.getType()) {
            case "circle" -> new Circle(shapeDTO);
            case "rectangle" -> new Rectangle(shapeDTO);
            case "triangle" -> new Triangle(shapeDTO);
            case "line" -> new Line(shapeDTO);
            case "ellipse" -> new Ellipse(shapeDTO);
            case "square" -> new Square(shapeDTO);
            default -> null;
        };

    }

}
