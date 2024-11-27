package com.csed.paintapp.service.factory;

import com.csed.paintapp.model.*;
import com.csed.paintapp.model.DTO.ShapeDto;
import org.springframework.stereotype.Component;

@Component
public class ShapeFactory {
    public Shape getShape(ShapeDto shapeDTO){
        return switch (shapeDTO.getType()) {
            case "Circle" -> new Circle(shapeDTO);
            case "Rectangle" -> new Rectangle(shapeDTO);
            case "Triangle" -> new Triangle(shapeDTO);
            case "Line" -> new Line(shapeDTO);
            case "Ellipse" -> new Ellipse(shapeDTO);
            case "Square" -> new Square(shapeDTO);
            default -> null;
        };

    }

}
