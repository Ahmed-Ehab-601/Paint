package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.service.ShapeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
         return new ResponseEntity<>(cratedShape,HttpStatus.CREATED);

     }
     @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
         return new ResponseEntity<>(HttpStatus.ACCEPTED);
     }




}
