package com.hidubai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.hidubai.service.ApplicationService;

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
}
