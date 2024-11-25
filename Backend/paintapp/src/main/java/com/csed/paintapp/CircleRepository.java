package com.csed.paintapp;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircleRepository extends CrudRepository<Circle,Integer> {
}
