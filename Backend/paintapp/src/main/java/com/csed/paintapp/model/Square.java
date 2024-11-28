package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Square extends Shape{
    private Double width;

    public Square(ShapeDto shapeDTO){
        super(shapeDTO);
        this.width = shapeDTO.getWidth();


    }

    @Override
    public ShapeDto getDTO(){
        ShapeDto shapeDTO = super.getDTO();
        shapeDTO.setType("square");
        shapeDTO.setWidth(width);
        return shapeDTO;
    }

    @Override
     public Square clone() throws CloneNotSupportedException {
       Square copy = (Square) super.clone();
       copy.setWidth(width);
       return copy;
     }

}
