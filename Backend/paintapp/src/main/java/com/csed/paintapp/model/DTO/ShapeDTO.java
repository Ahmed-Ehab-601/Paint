package com.csed.paintapp.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShapeDTO {
    private String type;
    private double x;
    private double y;
    private String fillColor;
    private String borderColor;
    private long id;
    private double  borderSize;
    private double width;
    private double height;
    private double radius;
    private double majorRadius;
    private double minorRadius;
    private double rotate;
    private ArrayList<Point> points;

}
