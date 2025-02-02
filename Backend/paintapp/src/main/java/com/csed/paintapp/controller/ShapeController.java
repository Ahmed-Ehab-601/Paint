package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.service.Commands.UndoRedoService;
import com.csed.paintapp.service.saveLoadService.LoadService;
import com.csed.paintapp.service.shapeService.ShapeServices;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shape")
@CrossOrigin(origins = "http://localhost:5173")
public class ShapeController {

     private final ShapeServices shapeServices;
     private final LoadService loadService;
     private final UndoRedoService undoRedoService;

    public ShapeController(ShapeServices shapeServices, LoadService loadService, UndoRedoService undoRedoService) {
        this.shapeServices = shapeServices;
        this.loadService = loadService;
        this.undoRedoService = undoRedoService;
    }


    @PostMapping("/create")
     public ResponseEntity<?> create(@RequestBody ShapeDto shapeDTO){
        try {
            ShapeDto createdShape =  shapeServices.create(shapeDTO);
            if(createdShape==null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(createdShape,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("UnExpected error");
        }

    }
     @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
         try {
             shapeServices.delete(id);
             return new ResponseEntity<>(HttpStatus.OK);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                     .body("UnExpected error");
         }
     }
     @PutMapping("/edit/{id}")
     public ResponseEntity<ShapeDto> update(@PathVariable Long id,@RequestBody ShapeDto shapeDto){
         try {
             shapeDto.setId(id);
             ShapeDto UpdatedShapeDto = shapeServices.update(shapeDto);
             if(UpdatedShapeDto == null){
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
             }
             return new ResponseEntity<>(UpdatedShapeDto,HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }

     }
    @PutMapping("/copy/{id}")
    public ResponseEntity<?> copy(@PathVariable("id") Long id) throws CloneNotSupportedException {
        try {
            ShapeDto shapeDto1= shapeServices.copy(id);
            if(shapeDto1==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("NOT FOUND , PLEASE PROVIDE CORRECT ID!!");
            }

            return new ResponseEntity<>(shapeDto1,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("UnExpected error");
        }

    }

    @PutMapping("/load")
    public ResponseEntity<?> load (@RequestBody List<ShapeDto> shapeDtos){
        try {
          List<ShapeDto> shapes=  loadService.load(shapeDtos);
            return new ResponseEntity<>(shapes,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/undo")
    public ResponseEntity<?> undo (){
        try {
            return new ResponseEntity<>(undoRedoService.undo(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/redo")
    public ResponseEntity<?> redo (){
        try {
            return new ResponseEntity<>(undoRedoService.redo(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?>  deleteAll(){
        try {
            shapeServices.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("UnExpected error");
        }

    }




}
