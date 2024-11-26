package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDto;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rectangle extends Shape{
    private Double width;
    private Double height;

    public Rectangle(ShapeDto shapeDTO){
        super(shapeDTO);
        this.width = shapeDTO.getWidth();
        this.height = shapeDTO.getHeight();

    }

    @Override
    public ShapeDto getDTO(){
        ShapeDto shapeDTO = super.getDTO();
        shapeDTO.setType("Rectangle");
        shapeDTO.setWidth(width);
        shapeDTO.setHeight(height);
        return shapeDTO;
    }

    @Override
     public Rectangle clone() throws CloneNotSupportedException {
       Rectangle copy = (Rectangle) super.clone();
       copy.setHeight(height);
       copy.setWidth(width);
       return copy;
     }

}
