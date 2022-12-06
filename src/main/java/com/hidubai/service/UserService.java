package com.hidubai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hidubai.exceptions.CustomeException;
import com.hidubai.exceptions.UnauthorizedException;
import com.hidubai.models.User;
import com.hidubai.utility.Constants;
import com.hidubai.utility.LocalStorage;
import com.hidubai.utility.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	LocalStorage storage;
	
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
		if(user.getConpassword() != user.getPassword()) {
			throw new CustomeException("400", "Password and confirm password values are not same.");
		}
		if(!storage.addUser(user)) {
			return Utility.createErrorResponse(Constants.USER_ALREADY_EXIST);
		}
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
		User fetchedUser = storage.getUserByUserByemail(user);
		if(fetchedUser != null && user.getPassword().equals(fetchedUser.getPassword())) {
			JsonNode node = Utility.createJsonNode(Constants.USER_NAME, fetchedUser.getFirstName() + " " + fetchedUser.getLastName());
			Utility.mergeToJsonNode(node, Constants.USER_NAME, fetchedUser.getFirstName() + " " + fetchedUser.getLastName());
			return Utility.createSuccessResponseWithData(node, Constants.LOGIN_SUCCESS);
		}else {
			throw new UnauthorizedException(Constants.FAILURE,Constants.UNAUTHORIZED_MSG);
		}
	}
}
