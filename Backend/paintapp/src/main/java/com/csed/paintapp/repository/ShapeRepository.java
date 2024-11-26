package com.csed.paintapp.repository;
import com.csed.paintapp.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ShapeRepository extends CrudRepository<Shape,Long> {

}