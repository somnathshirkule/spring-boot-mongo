package com.somnathshirkule.mongoassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.somnathshirkule.mongoassessment.exceptions.CustomeException;
import com.somnathshirkule.mongoassessment.exceptions.UnauthorizedException;
import com.somnathshirkule.mongoassessment.exceptions.ValidationException;
import com.somnathshirkule.mongoassessment.models.User;
import com.somnathshirkule.mongoassessment.repositories.UserRepository;
import com.somnathshirkule.mongoassessment.utility.Constants;
import com.somnathshirkule.mongoassessment.utility.LocalStorage;
import com.somnathshirkule.mongoassessment.utility.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	LocalStorage storage;
	
	@Autowired
	UserRepository userRepository;
	
	/***
	 * This method is used to save user signup details.
	 * 
	 * @param jsonNode
	 * @return JsonNode
	 */
	public JsonNode signUp(JsonNode jsonNode) {
		if(log.isDebugEnabled()) {
			log.debug("Entered into signUp");
		}
		User user = (User) Utility.convertJsonToObject(jsonNode, User.class);
		if(!user.getConpassword().equals(user.getPassword())) {
			throw new CustomeException("400", "Password and confirm password values are not same.");
		}
		if(userRepository.findByEmail(user.getEmail()) != null)
			throw new ValidationException(Constants.USER_ALREADY_EXIST,412);
		userRepository.save(user);
		if(log.isDebugEnabled()) {
			log.debug("exited from signUp");
		}
		return Utility.createSuccessResponse(Constants.REGISTERED);
	}
	
	/***
	 * This method is used to valiate the user login.
	 * 
	 * @param jsonNode
	 * @return JsonNode
	 */
	public JsonNode login(JsonNode jsonNode) {
		if(log.isDebugEnabled()) {
			log.debug("Entered into login");
		}
		User user = (User) Utility.convertJsonToObject(jsonNode, User.class);
		User fetchedUser = userRepository.findByEmail(user.getEmail());
		if(fetchedUser != null && user.getPassword().equals(fetchedUser.getPassword())) {
			JsonNode node = Utility.createJsonNode(Constants.USER_NAME, fetchedUser.getFirstName() + " " + fetchedUser.getLastName());
			Utility.mergeToJsonNode(node, Constants.USER_NAME, fetchedUser.getFirstName() + " " + fetchedUser.getLastName());
			return Utility.createSuccessResponseWithData(node, Constants.LOGIN_SUCCESS);
		}else {
			throw new UnauthorizedException(Constants.FAILURE,Constants.UNAUTHORIZED_MSG);
		}
	}
}
