package com.travix.medusa.busyflights.shared;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface Query {

	default Map<String, Object> toMap() {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
		};

		return mapper.convertValue(this, mapType);
	}

}
