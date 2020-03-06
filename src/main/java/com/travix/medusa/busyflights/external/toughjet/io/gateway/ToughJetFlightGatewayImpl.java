package com.travix.medusa.busyflights.external.toughjet.io.gateway;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;

import com.travix.medusa.busyflights.component.flight.io.gateway.FlightGateway;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.service.FlightService.FindFlightsQuery;
import com.travix.medusa.busyflights.external.toughjet.io.dto.ToughJetFlightDetail;
import com.travix.medusa.busyflights.shared.Gateway;
import com.travix.medusa.busyflights.shared.RestTemplateObject;

public class ToughJetFlightGatewayImpl extends Gateway implements FlightGateway {

	private static final String GET_FLIGHTS_URI = "?from={origin}&to={destination}"
			+ "&outboundDate={departureDate}&inboundDate={returnDate}&numberOfAdults={numberOfPassengers}";

	public ToughJetFlightGatewayImpl(RestTemplateObject restTemplate, String url) {
		super(restTemplate, url);
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public List<Flight> findFlights(FindFlightsQuery query) {
		List<ToughJetFlightDetail> result = doGet(GET_FLIGHTS_URI,
				new ParameterizedTypeReference<List<ToughJetFlightDetail>>() {
				}, query).orElse(Collections.EMPTY_LIST);
		return result.parallelStream().map(ToughJetFlightDetail::toModel).collect(Collectors.toList());
	}

}
