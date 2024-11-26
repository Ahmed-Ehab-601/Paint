package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.service.SaveLoadService.SaveLoadService;
import com.csed.paintapp.service.ShapeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shape")
public class ShapeController {
     @Autowired
     private ShapeServices shapeServices;
     @Autowired
     private SaveLoadService saveLoadService;

     @PostMapping("/create")
     public ResponseEntity<ShapeDto> create(@RequestBody ShapeDto shapeDTO){
         ShapeDto createdShape =  shapeServices.create(shapeDTO);
         if(createdShape==null){
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(createdShape,HttpStatus.OK);

     }
     @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
         shapeServices.delete(id);
         return new ResponseEntity<>(HttpStatus.OK);
     }
     @PutMapping("/update/{id}")
     public ResponseEntity<ShapeDto> update(@PathVariable Long id,@RequestBody ShapeDto shapeDto){
         shapeDto.setId(id);
         ShapeDto UpdatedShapeDto = shapeServices.update(shapeDto);
         if(UpdatedShapeDto == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(UpdatedShapeDto,HttpStatus.OK);

     }
    @PutMapping("/copy/{id}")
    public ResponseEntity<?> copy(@PathVariable("id") Long id) throws CloneNotSupportedException {
      ShapeDto shapeDto1= shapeServices.copy(id);
        if(shapeDto1==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("NOT FOUND , PLEASE PROVIDE CORRECT ID!!");
        }

        return new ResponseEntity<>(shapeDto1,HttpStatus.OK);

    }
    @PutMapping("/save/{type}/{path}")
    public ResponseEntity<?>save (@PathVariable("type") String type,@PathVariable("path") String path){
      boolean saved=saveLoadService.getSaveByType(type).save(path);
         if(saved){
             return new ResponseEntity<>(HttpStatus.OK);
        }
         return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
