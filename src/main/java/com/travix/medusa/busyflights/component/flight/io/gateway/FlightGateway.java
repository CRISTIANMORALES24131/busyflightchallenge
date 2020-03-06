package com.travix.medusa.busyflights.component.flight.io.gateway;

import java.util.List;

import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.service.FlightService.FindFlightsQuery;

public interface FlightGateway {

	List<Flight> findFlights(FindFlightsQuery query);

}
