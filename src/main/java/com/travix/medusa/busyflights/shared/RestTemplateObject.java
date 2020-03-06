package com.travix.medusa.busyflights.shared;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateObject extends RestTemplate {

	public <T> Optional<ResponseEntity<T>> doGet(String url, ParameterizedTypeReference<T> responseType) {
		RequestEntity<Void> request;
		try {
			request = RequestEntity.get(new URI(url)).accept(MediaType.APPLICATION_JSON_UTF8).build();
			return Optional.of(exchange(request, responseType));
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
