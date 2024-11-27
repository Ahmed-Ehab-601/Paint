package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import com.csed.paintapp.model.DTO.ShapeDto;

public  abstract  class Command {
    protected Long id;
    protected ShapeDto shapeDto;
    public abstract CommandDTO undo();
    public abstract CommandDTO redo();
    public abstract  ShapeDto  execute();


}
