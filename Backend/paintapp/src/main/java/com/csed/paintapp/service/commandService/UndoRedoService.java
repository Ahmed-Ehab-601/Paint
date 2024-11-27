package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;
import org.hibernate.internal.util.collections.Stack;

public class UndoRedoService {
    private Stack<Command>undoStack ;
    private Stack<Command>redoStack ;
    public CommandDTO undo(){
        return null;
    }
    public CommandDTO redo(){
        return null;
    }
    public void pushUndo(){

    }



}
