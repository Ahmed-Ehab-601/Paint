package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDTO;
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
     public ResponseEntity<ShapeDTO> create(@RequestBody ShapeDTO shapeDTO){
         return ResponseEntity.ok((shapeServices.create(shapeDTO)).getDTO());// return


   }
}
