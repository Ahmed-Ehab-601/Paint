package com.csed.paintapp.service.implementation;

import com.csed.paintapp.model.DTO.ShapeDto;
import com.csed.paintapp.model.Shape;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Wrapper {
    private List<ShapeDto> shapes;

}
