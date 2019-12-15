package com.appsdeveloperblog.photoapp.api.users.ui.factory;

import com.appsdeveloperblog.photoapp.api.users.ui.client.AlbumsServiceClient;
import com.appsdeveloperblog.photoapp.api.users.ui.fallback.AlbumsServiceClientFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AlbumsFallback implements FallbackFactory<AlbumsServiceClient> {
	@Override
	public AlbumsServiceClient create(Throwable throwable) {
		return new AlbumsServiceClientFallback(throwable);
	}
}
