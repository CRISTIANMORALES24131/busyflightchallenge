package com.travix.medusa.busyflights.shared;

import java.util.Optional;
import java.util.StringJoiner;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.util.Strings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

public abstract class Gateway {

	private final RestTemplateObject restTemplate;
	private final String url;

	public Gateway(RestTemplateObject restTemplate, String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public <T> Optional<T> doGet(@NotNull String resource, ParameterizedTypeReference<T> responseType,
			Query query) {
		Optional<ResponseEntity<T>> response = restTemplate.doGet(getUrl(resource, query), responseType);
		if (response.isPresent()) {
			return Optional.of(response.get().getBody());
		}
		return Optional.empty();
	}

	private String getUrl(String resource, Query query) {
		String keyFormat = "{%s}";
		StringJoiner join = new StringJoiner(Strings.EMPTY);
		join.add(url);
		join.add(resource);
		return query.toMap().entrySet().stream().reduce(join.toString(),
				(s, e) -> s.replace(String.format(keyFormat, e.getKey()),
						(CharSequence) Optional.ofNullable(e.getValue()).orElse(Strings.EMPTY).toString()),
				(s1, s2) -> null);
	}

}
