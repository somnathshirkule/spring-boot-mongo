package com.hidubai.advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hidubai.annotations.Validate;
import com.hidubai.exceptions.ValidationException;
import com.hidubai.service.ApplicationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ValidateAdvice {

	@Autowired
	ApplicationService applicationService;

	@Around("@annotation(com.hidubai.annotations.Validate)")
	public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
		String formName = null;
		Validate validateAnnotation = null;
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			validateAnnotation = method.getAnnotation(Validate.class);
			formName = validateAnnotation.apiName();
			JsonNode requestJson = (JsonNode) joinPoint.getArgs()[0];
			this.triggerValidation(formName, requestJson);
		} catch (Throwable t) {
			log.error("Error in validate for formname :: " + formName + "Error ::", t);
			throw t;
		}
		return joinPoint.proceed();
	}

	private void triggerValidation(String formName, JsonNode requestJson) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode allMetadata = applicationService.getMetadata();
		ArrayList<String> missingFieldsList = new ArrayList<>();
		ArrayList<String> lengthFieldsList = new ArrayList<>();
		ArrayNode metadataNode = (ArrayNode) allMetadata.get("data");
		for (JsonNode node : metadataNode) {
			if (node.get(formName) != null) {
				JsonNode metadata = node.get(formName);
				Map<String, Object> metadataMap = mapper.convertValue(metadata,
						new TypeReference<Map<String, Object>>() {
						});
				for (Entry<String, Object> entry : metadataMap.entrySet()) {
					String key = entry.getKey();
					LinkedHashMap<String, Object> valuesMap = (LinkedHashMap<String, Object>) entry.getValue();
					String mandatory = (String) valuesMap.get("MANDATORY");
					String enabled = (String) valuesMap.get("ENABLED");
					if(mandatory != null && enabled != null && mandatory.equals("Y") && enabled.equals("Y")){
						String nodeValue = requestJson.get(key) != null ? requestJson.get(key).asText() : "";
						if (mandatory != null && mandatory.equalsIgnoreCase("Y")
								&& (nodeValue == null || nodeValue.equals(""))) {
							missingFieldsList.add(key);
						}
						String minLength = (String) valuesMap.get("MIN_LENGTH");
						String maxLength = (String) valuesMap.get("MAX_LENGTH");
						if (minLength != null && (nodeValue.length() < Integer.parseInt(minLength))) {
							lengthFieldsList.add(key);
						}
						if (maxLength != null && (nodeValue.length() > Integer.parseInt(maxLength))) {
							lengthFieldsList.add(key);
						}
					}
				}
				break;
			}
		}
		Map<String, List<String>> responseMap = new HashMap<>();
		if (!missingFieldsList.isEmpty()) {
			responseMap.put("Required", missingFieldsList);
		}
		if (!lengthFieldsList.isEmpty()) {
			responseMap.put("Length", lengthFieldsList);
		}
		if (!missingFieldsList.isEmpty() || !lengthFieldsList.isEmpty()) {
			throw new ValidationException(responseMap.toString(), 412);
		}
	}
}
