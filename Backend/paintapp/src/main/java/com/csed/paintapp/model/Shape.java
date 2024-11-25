package com.csed.paintapp.model;

import com.csed.paintapp.model.DTO.ShapeDto;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    private Long id;
    private double x;
    private double y;
    private double borderSize;
    private String borderColor;
    private String fillColor;
    private double rotate;

    public Shape(ShapeDto shapeDTO){
        this.id = shapeDTO.getId();
        this.x = shapeDTO.getX();
        this.y = shapeDTO.getY();
        this.borderSize = shapeDTO.getBorderSize();
        this.borderColor = shapeDTO.getBorderColor();
        this.fillColor = shapeDTO.getFillColor();
        this.rotate = shapeDTO.getRotate();

    }
    public ShapeDto getDTO(){
       return ShapeDto.builder()
               .id(id)
               .x(x)
               .y(y)
               .borderSize(borderSize)
               .fillColor(fillColor)
               .rotate(rotate)
               .build();

    }
    @Override
    public Shape clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }








}
