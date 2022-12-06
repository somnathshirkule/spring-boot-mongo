package com.somnathshirkule.mongoassessment.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.somnathshirkule.mongoassessment.models.Car;
import com.somnathshirkule.mongoassessment.repositories.CarsRepository;
import com.somnathshirkule.mongoassessment.utility.Constants;
import com.somnathshirkule.mongoassessment.utility.LocalStorage;
import com.somnathshirkule.mongoassessment.utility.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplicationService {

	@Autowired
	LocalStorage storage;
	
	@Autowired
	CarsRepository carsRepository;
	
	/***
	 * This methdod is used to fetch the forms metadata.
	 * 
	 * @return JsonNode
	 */
	public JsonNode getMetadata(){
		if (log.isDebugEnabled()) {
			log.debug("Entered into getMetadata");
		}
		File folder;
		List<JsonNode> metadataList = new ArrayList<>();
		try {
			folder = new ClassPathResource("metadata").getFile();
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				String fileData = Utility.getFileData(file);
				JsonNode node = Utility.convertStringToJson(fileData);
				metadataList.add(node);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (log.isDebugEnabled()) {
			log.debug("exited from getMetadata");
		}
		return Utility.createSuccessResponseWithData(Utility.convertStringToJson(metadataList.toString()), Constants.SUCCESS);
	}
	
	public String getData() {
		return carsRepository.findAll().toString();
		//return "data";
	}
	
	public void addData(String carMan) {
		carsRepository.save(new Car(carMan,"fortuner","ford","6"));
		//return "data";
	}
}
