package com.hidubai.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Utility {
	/***
	 * This method is used to convert Json object to Java object.
	 * 
	 * @param json
	 * @param className
	 * @return Object
	 */
	public static Object convertJsonToObject(JsonNode json,  Class<?> className) {
		ObjectMapper mapper = new ObjectMapper();
		if(log.isDebugEnabled()) {
			log.debug("Entered into convertJsonToObject");
		}
		Object obj = mapper.convertValue(json, className);
		if(log.isDebugEnabled()) {
			log.debug("exited from convertJsonToObject");
		}
		return obj;
	}
	
	/***
	 * This method is used to create generic success response msg.
	 * 
	 * @param msg
	 * @return
	 */
	public static JsonNode createSuccessResponse(String msg) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put(Constants.STATUS, Constants.SUCCESS);
		((ObjectNode) rootNode).put(Constants.MSG, msg);
		return rootNode;
	}
	
	/***
	 * This method is used to create generic success response msg.
	 * 
	 * @param msg
	 * @return
	 */
	public static JsonNode createSuccessResponse(JsonNode msg) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put(Constants.STATUS, Constants.SUCCESS);
		((ObjectNode) rootNode).put(Constants.MSG, msg);
		return rootNode;
	}
	
	/***
	 * This method used to create success response with data.
	 * 
	 * @param data
	 * @return JsonNode
	 */
	@SuppressWarnings("deprecation")
	public static JsonNode createSuccessResponseWithData(JsonNode data, String msg) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put(Constants.STATUS, Constants.SUCCESS);
		((ObjectNode) rootNode).put(Constants.MSG, msg);
		((ObjectNode) rootNode).put(Constants.DATA, data);
		return rootNode;
	}
	
	/***
	 * This method used to create generic error response msg.
	 * 
	 * @param msg
	 * @return
	 */
	public static JsonNode createErrorResponse(String msg) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put(Constants.STATUS, Constants.FAILURE);
		((ObjectNode) rootNode).put(Constants.MSG, msg);
		return rootNode;
	}
	
	/**
	 * This method used to create Json node with key and value.
	 * 
	 * @param key
	 * @param value
	 * @return JsonNode
	 */
	public static JsonNode createJsonNode(String key, String value) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put(key, value);
		return rootNode;
	}
	
	
	/**
	 * This method used to merge Json node with key and value.
	 * 
	 * @param key
	 * @param value
	 * @return JsonNode
	 */
	public static void mergeToJsonNode(JsonNode node, String key, String value) {
		((ObjectNode) node).put(key, value);
	}
	
	
	
	/***
	 * This method used to convert String to Json object.
	 * 
	 * @param str
	 * @return JsonNode
	 */
	public static JsonNode convertStringToJson(String str){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(str);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return actualObj;
	}	
	
	/**
	 * this method is used to get data of file in String
	 * 
	 * @param file
	 * @return String
	 */
	public static String getFileData(File file) {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			String str;
			while ((str = buffer.readLine()) != null) {
				builder.append(str).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
