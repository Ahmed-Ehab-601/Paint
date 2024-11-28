package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  abstract  class Command {
    protected Long id;
    protected ShapeDto oldShapeDto;
    public abstract CommandDTO undo() throws CloneNotSupportedException;
    public abstract CommandDTO redo() throws CloneNotSupportedException;
    public abstract  ShapeDto  execute(ShapeDto shapeDto);


}
