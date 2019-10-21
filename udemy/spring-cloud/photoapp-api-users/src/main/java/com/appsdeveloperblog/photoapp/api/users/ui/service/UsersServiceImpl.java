package com.appsdeveloperblog.photoapp.api.users.ui.service;

import com.appsdeveloperblog.photoapp.api.users.ui.dto.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.ui.entity.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.ui.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class UsersServiceImpl implements UsersService {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UsersRepository usersRepository;

	@Override
	public UserDTO createUser(UserDTO userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);


		usersRepository.save(userEntity);
		return modelMapper.map(userEntity, UserDTO.class);
	}
}
