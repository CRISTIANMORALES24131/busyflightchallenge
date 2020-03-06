package com.travix.medusa.busyflights.external.crazyair.io.gateway;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;

import com.travix.medusa.busyflights.component.flight.io.gateway.FlightGateway;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.service.FlightService.FindFlightsQuery;
import com.travix.medusa.busyflights.external.crazyair.io.dto.CrazyAirFlightDetail;
import com.travix.medusa.busyflights.shared.Gateway;
import com.travix.medusa.busyflights.shared.RestTemplateObject;

public class CrazyAirFlightGatewayImpl extends Gateway implements FlightGateway {

	private static final String GET_FLIGHTS_URI = "?origin={origin}&destination={destination}"
			+ "&departureDate={departureDate}&returnDate={returnDate}&passengerCount={numberOfPassengers}";

	public CrazyAirFlightGatewayImpl(RestTemplateObject restTemplate, String url) {
		super(restTemplate, url);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Flight> findFlights(FindFlightsQuery query) {
		List<CrazyAirFlightDetail> result = doGet(GET_FLIGHTS_URI,
				new ParameterizedTypeReference<List<CrazyAirFlightDetail>>() {
				}, query).orElse(Collections.EMPTY_LIST);
		return result.parallelStream().map(CrazyAirFlightDetail::toModel).collect(Collectors.toList());
	}

}
