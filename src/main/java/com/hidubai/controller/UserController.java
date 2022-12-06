package com.hidubai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.hidubai.annotations.Validate;
import com.hidubai.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/***
	 * This API used to save signup user details.
	 * 
	 * @param jsonNode
	 * @return respective response msg.
	 */
	
	@PostMapping("/signup")
	@Validate(apiName = "signup")
	public ResponseEntity<JsonNode> signUp(@RequestBody JsonNode jsonNode){
		log.debug("Entered into signUp");
		
		JsonNode responseNode = userService.signUp(jsonNode);
		if(log.isDebugEnabled()) {
			log.debug("exited from signUp");
		}
		return new ResponseEntity<>(responseNode,HttpStatus.OK);
	}
	
	/***
	 * This API used for user login.
	 * 
	 * @param jsonNode
	 * @return respective msg.
	 */
	@PostMapping("/login")
	@Validate(apiName = "login")
	public ResponseEntity<JsonNode> login(@RequestBody JsonNode jsonNode){
		if(log.isDebugEnabled()) {
			log.debug("Entered into login");
		}
		JsonNode responseNode = userService.login(jsonNode);
		if(log.isDebugEnabled()) {
			log.debug("exited from login");
		}
		return new ResponseEntity<>(responseNode,HttpStatus.OK);
	}
}
