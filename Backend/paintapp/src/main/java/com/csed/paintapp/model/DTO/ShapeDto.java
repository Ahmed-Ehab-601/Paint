package com.csed.paintapp.model.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Double opacity;
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
    @JsonProperty("rotation")
    private Double rotate;
    private double [] points;
    @JsonIgnore
    public void setDefaultValue (){
        double basesize = 100;
        radius = basesize/2;
        minorRadius = basesize/ 1.5;
        majorRadius = basesize;
        width = basesize*1.5;
        height = basesize;
        rotate = 0.0;
        if(type.equals("square")){
            width = Double.valueOf(height);
        }
        if(type.equals("triangle")){
            points = new double[6];
            points[0] = 0;
            points[1] = -basesize/2;
            points[2] = basesize/2;
            points[3] = basesize/2;
            points[4] = -basesize/2;
            points[5] = basesize/2;

        }

    }

}
