package com.somnathshirkule.mongoassessment.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Document("cars")
public class Car {
	@Id
	private String id;
	
	private String car_model;
	private String car_manf;
	private String car_capacity; 
}
