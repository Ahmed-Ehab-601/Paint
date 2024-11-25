package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDTO;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Circle extends Shape {
    private double radius;

    public Circle(ShapeDTO shapeDTO) {
        super(shapeDTO);
        this.radius = shapeDTO.getRadius();
    }

    @Override
    public ShapeDTO getDTO() {
        ShapeDTO d = super.getDTO();
        d.setType(String.valueOf(this.getClass()));
        d.setRadius(this.radius);
        return d;
    }

    @Override
    public Circle clone() throws CloneNotSupportedException {
        Circle copy = (Circle) super.clone(); // Clone inherited fields
        copy.radius = this.radius; // Copy the radius field
        return copy;
    }


}
