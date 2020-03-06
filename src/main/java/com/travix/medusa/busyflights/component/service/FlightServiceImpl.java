package com.travix.medusa.busyflights.component.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.component.flight.io.gateway.FlightGateway;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.model.FlightsSupplier;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightServiceImpl implements FlightService {

	private Map<FlightsSupplier, FlightGateway> flightSuppliers = new HashMap<>();

	@Override
	public List<Flight> findFlights(FindFlightsQuery query) {
		log.info("exec findFlights query {} ", query);
		List<CompletableFuture<List<Flight>>> listFlights = flightSuppliers.entrySet().stream()
				.map(entry -> findFlights(query, entry)).collect(Collectors.toList());

		return listFlights.stream().map(CompletableFuture::join).flatMap(List::stream)
				.sorted(Comparator.comparing(Flight::getFare)).collect(Collectors.toList());
	}

	@Async
	private CompletableFuture<List<Flight>> findFlights(FindFlightsQuery query,
			Entry<FlightsSupplier, FlightGateway> supplier) {
		log.info("getting finds from supplier Query: {}, supplier {} ", query, supplier.getKey());
		return CompletableFuture.completedFuture(supplier.getValue().findFlights(query));
	}

	@Override
	public void addFlightsSupplier(FlightsSupplier supplier, FlightGateway service) {
		log.info("adding flight supplier Supplier: {} ", supplier);
		flightSuppliers.put(supplier, service);
	}

}
