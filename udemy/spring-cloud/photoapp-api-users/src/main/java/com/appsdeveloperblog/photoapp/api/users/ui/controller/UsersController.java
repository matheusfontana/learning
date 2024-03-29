package com.appsdeveloperblog.photoapp.api.users.ui.controller;

import com.appsdeveloperblog.photoapp.api.users.ui.dto.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.UserResponseModel;
import com.appsdeveloperblog.photoapp.api.users.ui.service.UsersService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment environment;

	@Autowired
	private UsersService usersService;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/status/check")
	public String status() {
		return "Working on port " + environment.getProperty("local.server.port") + ", with token " + environment.getProperty("token.secret");
	}

	@PostMapping(
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel createUserRequestModel) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(createUserRequestModel, UserDTO.class);

		UserDTO createdUser = usersService.createUser(userDTO);
		CreateUserResponseModel createUserResponseModel = modelMapper.map(createdUser, CreateUserResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
	}


	@GetMapping(
			value = "/{userId}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	) public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
		UserDTO userDTO = usersService.getUserById(userId);
		UserResponseModel returnValue = modelMapper.map(userDTO, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}

}
