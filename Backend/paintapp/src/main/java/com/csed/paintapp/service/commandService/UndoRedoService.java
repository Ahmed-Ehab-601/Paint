package com.csed.paintapp.service.commandService;

import com.csed.paintapp.model.DTO.CommandDTO;

import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class UndoRedoService {
    private final Stack<Command> undoStack  = new Stack<Command>();
    private final Stack<Command> redoStack = new Stack<Command>() ;
    public CommandDTO undo(){
        if (undoStack.isEmpty()) {
           return null;
        }

        Command command= undoStack.pop();
         CommandDTO res= command.undo();
         redoStack.push(command);
         return res;
    }
    public CommandDTO redo(){
        if(redoStack.isEmpty()){
            return null;
        }
        Command command = redoStack.pop();
        CommandDTO res= command.redo();
        undoStack.push(command);
        return res;
    }
    public void pushUndo(Command command){
        redoStack.clear();
        undoStack.push(command);
    }



}
