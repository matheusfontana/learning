package com.appsdeveloperblog.photoapp.api.users.ui.fallback;

import com.appsdeveloperblog.photoapp.api.users.ui.client.AlbumsServiceClient;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class AlbumsServiceClientFallback implements AlbumsServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public AlbumsServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {

		if (cause instanceof FeignException && ((FeignException) cause).status() == HttpStatus.NOT_FOUND.value()) {
			logger.error("404 error when getAlbums called with ID:" + id + ". Error message: " + cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}

		return new ArrayList<>();
	}
}
