package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDto;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Circle extends Shape {
    private Double radius;

    public Circle(ShapeDto shapeDTO) {
        super(shapeDTO);
        this.radius = shapeDTO.getRadius();
    }

    @Override
    public ShapeDto getDTO() {
        ShapeDto shapeDTO  = super.getDTO();
        shapeDTO .setType("circle");
        shapeDTO .setRadius(this.radius);
        return shapeDTO;
    }

    @Override
    public Circle clone() throws CloneNotSupportedException {
        Circle copy = (Circle) super.clone(); // Clone inherited fields
        copy.radius = this.radius; // Copy the radius field
        return copy;
    }


}
