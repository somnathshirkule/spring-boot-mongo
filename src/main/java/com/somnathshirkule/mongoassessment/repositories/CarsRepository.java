package com.somnathshirkule.mongoassessment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.somnathshirkule.mongoassessment.models.Car;

public interface CarsRepository extends MongoRepository<Car, String>{

}
