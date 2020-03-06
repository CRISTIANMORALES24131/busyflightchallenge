package com.travix.medusa.busyflights.component.flight.io.web.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.component.flight.io.web.v1.FlightControllerRequest.GetFlightsRequest;
import com.travix.medusa.busyflights.component.flight.io.web.v1.FlightControllerResponses.FlightDetail;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.service.FlightService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/flights", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class FlightController {

	@Autowired
	private FlightService flightService;

	@GetMapping
	public ResponseEntity<List<FlightDetail>> findFlights(@ModelAttribute @Valid GetFlightsRequest request) {
		log.info("exec findFlights {} ", request);
		List<Flight> flights = flightService.findFlights(request.toQuery());
		List<FlightDetail> response = flights.parallelStream().map(FlightDetail::of).collect(Collectors.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
