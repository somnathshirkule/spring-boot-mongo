package com.hidubai.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hidubai.models.User;

@Component
public class LocalStorage {
	private List<User> users = new ArrayList<>();
	
	public List<User> getAllUsers(){
		return this.users;
	}
	
	public User getUserByUserByemail(User user) {
		int index = users.indexOf(user);
		if(index != -1)
			return users.get(index);
		return null;
	}
	
	public boolean addUser(User user) {
		if(!users.contains(user))
			return users.add(user);
		return false;
	}
}
