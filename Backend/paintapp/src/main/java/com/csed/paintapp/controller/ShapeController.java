package com.csed.paintapp.controller;

import com.csed.paintapp.service.ShapeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public class ShapeController {
     @Autowired
     private ShapeServices shapeServices;

     @PostMapping(value = "/shapes")
    Long create(){


   }
}
