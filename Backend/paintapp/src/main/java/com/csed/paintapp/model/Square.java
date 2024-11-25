package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDTO;
import jakarta.persistence.DiscriminatorValue;
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
    private double width;

    public Square(ShapeDTO shapeDTO){
        super(shapeDTO);
        this.width = shapeDTO.getWidth();


    }

    @Override
    public ShapeDTO getDTO(){
        ShapeDTO shapeDTO = super.getDTO();
        shapeDTO.setType(String.valueOf(this.getClass()));
        shapeDTO.setWidth(width);
        return shapeDTO;
    }

    @Override
     public Square clone() throws CloneNotSupportedException {
       Square square =(Square) super.clone();
       square.setWidth(width);
       return square;
     }

}
