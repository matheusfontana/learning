package com.appsdeveloperblog.photoapp.api.users.ui.service;

import com.appsdeveloperblog.photoapp.api.users.ui.dto.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.ui.entity.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.ui.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
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

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(s);

		if (userEntity == null) {
			throw new UsernameNotFoundException(s);
		}

		return new User(userEntity.getEmail(),
				userEntity.getEncryptedPassword(),
				true,
				true,
				true,
				true,
				new ArrayList<>());

	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity userEntity = usersRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		return modelMapper.map(userEntity, UserDTO.class);
	}
}
