package com.appsdeveloperblog.photoapp.api.users.ui.service;

import com.appsdeveloperblog.photoapp.api.users.ui.client.AlbumsServiceClient;
import com.appsdeveloperblog.photoapp.api.users.ui.dto.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.ui.entity.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import com.appsdeveloperblog.photoapp.api.users.ui.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UsersRepository usersRepository;

	//@Autowired
	//RestTemplate restTemplate;

	@Autowired
	Environment environment;

	@Autowired
	AlbumsServiceClient albumsServiceClient;

	Logger logger = LoggerFactory.getLogger(this.getClass());


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

	@Override
	public UserDTO getUserById(String userId) {
		UserEntity userEntity = usersRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}

		UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

		/*
		String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
		ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(
				albumsUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<AlbumResponseModel>>() {
				});
		*/

		logger.info("Before calling albums Microservice");
		List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
		logger.info("After calling albums Microservice");
		userDTO.setAlbums(albumsList);
		return userDTO;
	}
}
