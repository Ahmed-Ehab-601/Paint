package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
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

     @PostMapping("/create")
     public ResponseEntity<ShapeDto> create(@RequestBody ShapeDto shapeDTO){
         ShapeDto cratedShape =  shapeServices.create(shapeDTO);
         return new ResponseEntity<>(cratedShape,HttpStatus.OK);

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
    public ResponseEntity<ShapeDto> copy(@PathVariable("id") Long id,@RequestBody ShapeDto shapeDto) throws CloneNotSupportedException {
        try{
            return new ResponseEntity<>(shapeServices.copy(id),HttpStatus.OK);
        }catch (CloneNotSupportedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }





}
