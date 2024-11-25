package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDTO;
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
public class Ellips extends Shape {
    private double majorRadius;
    private double minorRadius;
    public Ellips(ShapeDTO shapeDTO) {
        super(shapeDTO);
        this.majorRadius = shapeDTO.getRadius();
        this.minorRadius = shapeDTO.getRadius();

    }

    @Override
    public ShapeDTO getDTO() {
        ShapeDTO d = super.getDTO();
        d.setType(String.valueOf(this.getClass()));
        d.setMajorRadius(this.majorRadius);
        d.setMinorRadius(this.minorRadius);
        return d;
    }

    @Override
    public Ellips clone() throws CloneNotSupportedException {
        Ellips copy = (Ellips) super.clone(); // Clone inherited fields
        copy.majorRadius = this.majorRadius;
        copy.minorRadius=this.minorRadius;
        return copy;
    }


}
