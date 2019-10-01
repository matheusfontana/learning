package com.appsdeveloperblog.app.ws.ui.services;

import com.appsdeveloperblog.app.ws.ui.Utils;
import com.appsdeveloperblog.app.ws.ui.model.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
	Map<String, UserRest> users;

	Utils utils;

	public UserServiceImpl() {
	}

	@Autowired
	public UserServiceImpl(Utils utils) {
		this.utils = utils;
	}

	@Override
	public UserRest createUser(UserDetailsRequestModel userDetailsRequestModel) {
		UserRest returnValue = new UserRest();
		returnValue.setEmail(userDetailsRequestModel.getEmail());
		returnValue.setFirstName(userDetailsRequestModel.getFirstName());
		returnValue.setLastName(userDetailsRequestModel.getLastName());

		String userId = utils.generateUserId();
		returnValue.setUserId(userId);

		if (users == null) users = new HashMap<>();
		users.put(userId, returnValue);

		return returnValue;
	}
}
