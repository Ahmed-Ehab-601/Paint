package com.csed.paintapp.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDTO {
   private String type;
   private ShapeDto shapeDto;
}
