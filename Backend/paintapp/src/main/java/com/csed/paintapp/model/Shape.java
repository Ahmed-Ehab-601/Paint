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
    private Double x;
    private Double y;
    private Double scaleX;
    private Double scaleY;
    private Double borderSize;
    private String borderColor;
    private String fillColor;
    private Double rotate;

    public Shape(ShapeDto shapeDTO){
        this.id = shapeDTO.getId();
        this.x = shapeDTO.getX();
        this.y = shapeDTO.getY();
        this.scaleX = shapeDTO.getScaleX();
        this.scaleY = shapeDTO.getScaleY();
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
               .scaleX(scaleX)
               .scaleY(scaleY)
               .borderSize(borderSize)
               .borderColor(borderColor)
               .fillColor(fillColor)
               .rotate(rotate)
               .build();

    }
    @Override
    public Shape clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }








}
