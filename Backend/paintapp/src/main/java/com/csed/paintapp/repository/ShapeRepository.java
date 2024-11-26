package com.csed.paintapp.repository;
import com.csed.paintapp.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeRepository extends CrudRepository<Shape,Long> {

}