package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.service.ShapeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shape")
public class ShapeController {
     @Autowired
     private ShapeServices shapeServices;

     @PostMapping("/create")
     public ResponseEntity<ShapeDto> create(@RequestBody ShapeDto shapeDTO){
         ShapeDto cratedShape =  shapeServices.create(shapeDTO);
         return ResponseEntity.ok(cratedShape);


   }
}
