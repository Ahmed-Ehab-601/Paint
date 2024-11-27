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
    private Double majorRadius;
    private Double minorRadius;
    private Double rotate;
    private double [] points;
}
