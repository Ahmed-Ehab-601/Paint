package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDTO;
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
public class Triangle extends Shape{
    double [] points;

    public Triangle(ShapeDTO shapeDTO){
        super(shapeDTO);
        this.points = shapeDTO.getPoints();
    }

    @Override
    public ShapeDTO getDTO(){
        ShapeDTO shapeDTO = super.getDTO();
        shapeDTO.setType(String.valueOf(this.getClass()));
        shapeDTO.setPoints(points);
        return shapeDTO;
    }

    @Override
     public Triangle clone() throws CloneNotSupportedException {
       Triangle triangle =(Triangle) super.clone();
       double [] copyPoints = java.util.Arrays.copyOf(points, points.length);
       triangle.setPoints(copyPoints);
       return triangle;
     }

}
