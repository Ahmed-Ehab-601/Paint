package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Line extends Shape {
    private double [] points;

    public Line(ShapeDto shapeDTO) {
        super(shapeDTO);
        this.points = shapeDTO.getPoints();
    }

    @Override
    public ShapeDto getDTO() {
        ShapeDto shapeDTO  = super.getDTO();
        shapeDTO.setType("line");
        shapeDTO.setPoints(points); // Deep copy the points list
        return shapeDTO;
    }

    @Override
    public Line clone() throws CloneNotSupportedException {
        Line copy = (Line) super.clone(); // Clone inherited fields
        double [] copyPoints = java.util.Arrays.copyOf(points, points.length);
        copy.setPoints(copyPoints);
        return copy;
    }
}
