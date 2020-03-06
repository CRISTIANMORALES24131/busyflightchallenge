package com.travix.medusa.busyflights.component.flight.io.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travix.medusa.busyflights.component.model.FlightsSupplier;
import com.travix.medusa.busyflights.component.service.FlightService;
import com.travix.medusa.busyflights.external.crazyair.io.gateway.CrazyAirFlightGatewayImpl;
import com.travix.medusa.busyflights.external.toughjet.io.gateway.ToughJetFlightGatewayImpl;
import com.travix.medusa.busyflights.shared.RestTemplateObject;

@Configuration
public class FlightGatewayConfig {

	private final RestTemplateObject restTemplate;

	private final FlightService flightService;

	public FlightGatewayConfig (RestTemplateObject restTemplate, FlightService flightService) {
		this.restTemplate = restTemplate;
		this.flightService = flightService;
	}

	@Bean
	public FlightGateway crazyAirFlightGateway(@Value("${flight.supplier.crazyair.service.url}") String url) {
		CrazyAirFlightGatewayImpl bean = new CrazyAirFlightGatewayImpl(restTemplate, url);
		flightService.addFlightsSupplier(FlightsSupplier.CRAZY_AIR, bean);
		return bean;
	}

	@Bean
	public FlightGateway toughJetFlightGateway(@Value("${flight.supplier.toughjet.service.url}") String url) {
		ToughJetFlightGatewayImpl bean = new ToughJetFlightGatewayImpl(restTemplate, url);
		flightService.addFlightsSupplier(FlightsSupplier.TOUGH_JET, bean);
		return bean;
	}

}
