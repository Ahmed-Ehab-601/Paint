package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shapes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Shape implements Cloneable{
    @Id
    private Long id;
    private Double x;
    private Double y;
    private Double borderSize;
    private String borderColor;
    private String fillColor;
    private Double rotate;
    private Double  opacity ;

    public Shape(ShapeDto shapeDTO){
        this.id = shapeDTO.getId();
        this.x = shapeDTO.getX();
        this.y = shapeDTO.getY();
        this.borderSize = shapeDTO.getBorderSize();
        this.borderColor = shapeDTO.getBorderColor();
        this.fillColor = shapeDTO.getFillColor();
        this.rotate = shapeDTO.getRotate();
        this.opacity=shapeDTO.getOpacity();

    }

    public ShapeDto getDTO(){
       return ShapeDto.builder()
               .id(id)
               .x(x)
               .y(y)
               .borderSize(borderSize)
               .borderColor(borderColor)
               .fillColor(fillColor)
               .rotate(rotate)
               .opacity(opacity)
               .build();

    }
    @Override
    public Shape clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }








}
