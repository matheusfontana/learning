package com.appsdeveloperblog.photoapp.api.users.ui.service;

import com.appsdeveloperblog.photoapp.api.users.ui.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
	UserDTO createUser(UserDTO userDetails);
	UserDTO getUserDetailsByEmail(String email);
}
