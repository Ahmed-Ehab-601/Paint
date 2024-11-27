package com.csed.paintapp.model.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("fill")
    private String fillColor;
    @JsonProperty("stroke")
    private String borderColor;
    private Long id;
    @JsonProperty("strokeWidth")
    private Double borderSize;
    private Double width;
    private Double height;
    private Double radius;
    @JsonProperty("radiusX")
    private Double majorRadius;
    @JsonProperty("radiusY")
    private Double minorRadius;
    private Double rotate;
    private double [] points;

    public void setDto(){
        radius = 10.0;
        minorRadius = 10.0;
        majorRadius = 15.0;
        width = 17.9;
        height =12.2;
        points = new double[2];
        points[0] = 1.0;
        points[1] = 4.3;
        rotate = 0.0;

    }

}
