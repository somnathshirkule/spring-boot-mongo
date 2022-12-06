package com.somnathshirkule.mongoassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.somnathshirkule.mongoassessment.service.ApplicationService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/application")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;
	
	/***
	 * This API is used to retrive the form metadata.
	 * 
	 * @return metadata Json.
	 */
	@GetMapping("/metadata")
	public ResponseEntity<JsonNode> getMetadata(){
		if(log.isDebugEnabled()) {
			log.debug("Entered into getMetadata");
		}
		JsonNode responseNode = applicationService.getMetadata();
		if(log.isDebugEnabled()) {
			log.debug("exited from getMetadata");
		}
		return new ResponseEntity<>(responseNode,HttpStatus.OK);
	}
	
	@GetMapping("/cars")
	public ResponseEntity<String> getData(){
		String data = applicationService.getData();
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@PostMapping("/cars/{carManf}")
	public ResponseEntity<String> addData(@PathVariable("carManf") String carMan){
		applicationService.addData(carMan);
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
}
