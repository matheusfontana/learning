package com.appsdeveloperblog.photoapp.api.users.ui.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Autowired
	Environment environment;

	@Override
	public Exception decode(String s, Response response) {
		if (response.status() == HttpStatus.BAD_REQUEST.value()){
			return null;
		} else if (response.status() == HttpStatus.NOT_FOUND.value()){
			if (s.contains("getAlbums")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("albums.exceptions.albums-not-found"));
			}
		}


		return new Exception(response.reason());
	}
}
