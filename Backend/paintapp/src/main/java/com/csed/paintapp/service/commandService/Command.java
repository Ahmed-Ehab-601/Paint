package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;

public  abstract  class Command {
    private Long id;
    private ShapeDto shapeDto;
    public abstract CommandDTO undo();
    public abstract CommandDTO redo();
    public abstract  ShapeDto  execute();


}
