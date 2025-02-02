package com.csed.paintapp.service.Commands;

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


        try {
            Command command= undoStack.pop();
            CommandDTO res= command.undo();
            redoStack.push(command);
            return res;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public CommandDTO redo(){
        if(redoStack.isEmpty()){
            return null;
        }
        try {
            Command command = redoStack.pop();
            CommandDTO res= command.redo();
            undoStack.push(command);
            return res;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public void pushUndo(Command command){
        redoStack.clear();
        undoStack.push(command);
    }

    public void clearStacks(){
        redoStack.clear();
        undoStack.clear();
    }




}
