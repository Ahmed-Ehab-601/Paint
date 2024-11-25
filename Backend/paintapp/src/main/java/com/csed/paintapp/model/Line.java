package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.Point;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Line extends Shape {
    private ArrayList<Point> points;

    public Line(ShapeDTO shapeDTO) {
        super(shapeDTO);
        this.points = new ArrayList<>(shapeDTO.getPoints());
    }

    @Override
    public ShapeDTO getDTO() {
        ShapeDTO d = super.getDTO();
        d.setType("Line");
        d.setPoints(new ArrayList<>(this.points)); // Deep copy the points list
        return d;
    }

    @Override
    public Line clone() throws CloneNotSupportedException {
        Line copy = (Line) super.clone(); // Clone inherited fields
        if (this.points != null) {
            copy.points = new ArrayList<>(); // Deep copy the points list
            for (Point point : this.points) {
                copy.points.add(new Point(point)); // Clone each point
            }
        }
        return copy;
    }
}
