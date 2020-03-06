package com.travix.medusa.busyflights.component.service;

import java.io.Serializable;
import java.util.List;

import com.travix.medusa.busyflights.component.flight.io.gateway.FlightGateway;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.model.FlightsSupplier;
import com.travix.medusa.busyflights.shared.Query;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public interface FlightService {

	List<Flight> findFlights(FindFlightsQuery query);

	void addFlightsSupplier(FlightsSupplier supplier, FlightGateway service);

	@Builder
	@Getter
	@ToString
	public class FindFlightsQuery implements Query, Serializable {

		private static final long serialVersionUID = 276058678207447474L;

		private String origin;
		private String destination;
		private String departureDate;
		private String returnDate;
		private int numberOfPassengers;
	}
}
