package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.ui.model.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.UserRest;
import com.appsdeveloperblog.app.ws.ui.services.UserService;
import com.appsdeveloperblog.app.ws.ui.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	Map<String, UserRest> users;

	@GetMapping
	public String getUsers(@RequestParam(value="page", defaultValue = "1") int page,
						   @RequestParam(value="limit", defaultValue = "50") int limit,
						   @RequestParam(value="sort", defaultValue = "DESC", required = false) String sort){
		return "get users called with page " + page + " and limit " + limit + " and sort " + sort;
	}


	@GetMapping(path="/{userId}", produces = {
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> getUser(@PathVariable String userId){
		if(users.containsKey(userId)){
			return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(
			consumes = {
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_JSON_VALUE
			},
			produces = {
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_JSON_VALUE
			})
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetailsRequestModel){
		UserRest returnValue = userService.createUser(userDetailsRequestModel);
		return new ResponseEntity<>(returnValue, HttpStatus.OK);
	}

	@PutMapping(path="/{userId}",
			consumes = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
			},
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
			})
	public ResponseEntity<UserRest> updateUser(
			@PathVariable String userId,
			@Valid @RequestBody UpdateUserDetailsRequestModel updateUserDetailsRequestModel){
		if(users.containsKey(userId)){
			UserRest storedUser = users.get(userId);
			storedUser.setFirstName(updateUserDetailsRequestModel.getFirstName());
			storedUser.setLastName(updateUserDetailsRequestModel.getLastName());

			users.replace(storedUser.getUserId(), storedUser);
			return new ResponseEntity<>(storedUser, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping
	public ResponseEntity<UserRest> deleteUser(@PathVariable String userId){
		if(users.containsKey(userId)){
			users.remove(userId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
