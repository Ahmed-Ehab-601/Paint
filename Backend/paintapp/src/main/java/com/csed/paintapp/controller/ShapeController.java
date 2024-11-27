package com.csed.paintapp.controller;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.service.saveLoadService.SaveLoadService;
import com.csed.paintapp.service.shapeService.ShapeServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shape")
public class ShapeController {

     private final ShapeServices shapeServices;
     private final SaveLoadService saveLoadService;

    public ShapeController(ShapeServices shapeServices, SaveLoadService saveLoadService) {
        this.shapeServices = shapeServices;
        this.saveLoadService = saveLoadService;
    }

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
    @PutMapping("/save")
    public ResponseEntity<?>save (@RequestBody Map<String,String> requestBody){
        try {
            saveLoadService.getSaveByType(requestBody.get("type")).save(requestBody.get("path"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/load")
    public ResponseEntity<?> load (@RequestBody Map<String,String> requestBody){
        try {
           List<ShapeDto> shapeDtos = saveLoadService.getLoadByType(requestBody.get("type")).load(requestBody.get("path"));
            return new ResponseEntity<>(shapeDtos,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
