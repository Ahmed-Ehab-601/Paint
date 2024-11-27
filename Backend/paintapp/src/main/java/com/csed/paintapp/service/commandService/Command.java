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
    protected ShapeDto shapeDto;
    public abstract CommandDTO undo();
    public abstract CommandDTO redo();
    public abstract  ShapeDto  execute(ShapeDto shapeDto);


}
