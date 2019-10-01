package com.appsdeveloperblog.app.ws.ui.services;

import com.appsdeveloperblog.app.ws.ui.model.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.UserRest;

public interface UserService {
	UserRest createUser(UserDetailsRequestModel userDetailsRequestModel);
}
