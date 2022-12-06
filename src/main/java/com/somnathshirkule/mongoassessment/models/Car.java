package com.somnathshirkule.mongoassessment.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Document("cars")
@ToString
public class Car {
	@Id
	private String id;
	
	private String carModel;
	private String carManf;
	private String carCapacity;
	
	public Car(String carModel, String carManf, String carCapacity) {
		super();
		this.carModel = carModel;
		this.carManf = carManf;
		this.carCapacity = carCapacity;
	}
}
