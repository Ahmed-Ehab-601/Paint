package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDTO;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rectangle extends Shape{
    private double width;
    private double height;

    public Rectangle(ShapeDTO shapeDTO){
        super(shapeDTO);
        this.width = shapeDTO.getWidth();
        this.height = shapeDTO.getHeight();

    }

    @Override
    public ShapeDTO getDTO(){
        ShapeDTO shapeDTO = super.getDTO();
        shapeDTO.setType(String.valueOf(this.getClass()));
        shapeDTO.setWidth(width);
        shapeDTO.setHeight(height);
        return shapeDTO;
    }

    @Override
     public Rectangle clone() throws CloneNotSupportedException {
       Rectangle rectangle =(Rectangle) super.clone();
       rectangle.setHeight(height);
       rectangle.setWidth(width);
       return rectangle;
     }

}
