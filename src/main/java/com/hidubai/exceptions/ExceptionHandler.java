package com.hidubai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.fasterxml.jackson.databind.JsonNode;
import com.hidubai.utility.Constants;
import com.hidubai.utility.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {
		if(log.isErrorEnabled()) {
			log.error("EXCEPTION OCCURED :: ", ex);
		}
		JsonNode node = Utility.createJsonNode(Constants.MSG, Constants.INTERNAL_ERROR);
		return new ResponseEntity<>(node, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ServiceException.class)
	public ResponseEntity<Object> handleApi1Exception(ServiceException serviceException){
		if(log.isErrorEnabled()) {
			log.error("EXCEPTION OCCURED :: ", serviceException);
		}
		JsonNode node = Utility.createJsonNode(Constants.MSG, serviceException.getErrorMsg());
		return new ResponseEntity<>(node, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleApi2Exception(ValidationException validationException){
		if(log.isErrorEnabled()) {
			log.error("EXCEPTION OCCURED :: ", validationException);
		}
		JsonNode node = Utility.createJsonNode(Constants.MSG, validationException.getErrorMsg());
		return new ResponseEntity<>(node, HttpStatus.PRECONDITION_FAILED);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleApi3Exception(UnauthorizedException unauthorizedException){
		if(log.isErrorEnabled()) {
			log.error("EXCEPTION OCCURED :: ", unauthorizedException);
		}
		JsonNode node = Utility.createJsonNode(Constants.MSG, unauthorizedException.getErrorMsg());
		return new ResponseEntity<>(node, HttpStatus.UNAUTHORIZED);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(CustomeException.class)
	public ResponseEntity<Object> handleApi3Exception(CustomeException customeException){
		if(log.isErrorEnabled()) {
			log.error("EXCEPTION OCCURED :: ", customeException);
		}
		JsonNode node = Utility.createJsonNode(Constants.MSG, customeException.getErrorMsg());
		return new ResponseEntity<>(node, HttpStatus.BAD_REQUEST);
	}
}
