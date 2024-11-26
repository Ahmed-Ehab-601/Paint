package com.csed.paintapp.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShapeDto {
    private String type;
    private Double x;
    private Double y;
    private String fillColor;
    private String borderColor;
    private Long id;
    private Double  borderSize;
    private Double width;
    private Double height;
    private Double radius;
    private Double majorRadius;
    private Double minorRadius;
    private Double rotate;
    private double [] points;
}
