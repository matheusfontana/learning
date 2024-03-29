package com.appsdeveloperblog.photoapp.api.users.ui.client;

import com.appsdeveloperblog.photoapp.api.users.ui.factory.AlbumsFallback;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ms", fallbackFactory=AlbumsFallback.class)
public interface AlbumsServiceClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);

}
