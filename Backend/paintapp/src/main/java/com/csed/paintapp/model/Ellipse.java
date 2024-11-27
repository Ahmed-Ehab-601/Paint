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
public class Ellipse extends Shape {
    private Double majorRadius;
    private Double minorRadius;
    public Ellipse(ShapeDto shapeDTO) {
        super(shapeDTO);
        this.majorRadius = shapeDTO.getMajorRadius();
        this.minorRadius = shapeDTO.getMinorRadius();

    }

    @Override
    public ShapeDto getDTO() {
        ShapeDto shapeDTO = super.getDTO();
        shapeDTO .setType("Ellipse");
        shapeDTO .setMajorRadius(this.majorRadius);
        shapeDTO .setMinorRadius(this.minorRadius);
        return shapeDTO ;
    }

    @Override
    public Ellipse clone() throws CloneNotSupportedException {
        Ellipse copy = (Ellipse) super.clone(); // Clone inherited fields
        copy.majorRadius = this.majorRadius;
        copy.minorRadius=this.minorRadius;
        return copy;
    }


}
