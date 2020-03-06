package com.travix.medusa.busyflights.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.travix.medusa.busyflights.component.flight.io.gateway.FlightGateway;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.model.FlightsSupplier;
import com.travix.medusa.busyflights.component.service.FlightService;
import com.travix.medusa.busyflights.component.service.FlightService.FindFlightsQuery;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@ActiveProfiles("test")
// This is an standard unit test class, here one of the service is being tested, every validation
// or case have to be include as an individual test.
// All these tests are primary to develop quality software and reduce considerably bugs injection
// All the components have to be unit and integrated tested
// Only for time I'm not adding all code coverage, understanding that testing is a vital part of quality software
public class MovieServiceTest {

	@Autowired
	private FlightService service;

	@Test
	public void getFlights() {
		FindFlightsQuery query = FindFlightsQuery.builder().build();
		FlightGateway crazyAir = mock(FlightGateway.class);
		FlightGateway toughJet = mock(FlightGateway.class);
		service.addFlightsSupplier(FlightsSupplier.CRAZY_AIR, crazyAir);
		service.addFlightsSupplier(FlightsSupplier.TOUGH_JET, toughJet);

		when(crazyAir.findFlights(eq(query))).thenReturn(getFlights(FlightsSupplier.CRAZY_AIR, 2));
		when(toughJet.findFlights(eq(query))).thenReturn(getFlights(FlightsSupplier.TOUGH_JET, 3));

		List<Flight> result = service.findFlights(query);
		
		// Asserts
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(5);
	}

	private List<Flight> getFlights(FlightsSupplier supplier, int num) {
		List<Flight> flights = new ArrayList<>();
		IntStream.range(0, num).forEach(i -> flights.add(getFlight(supplier)));
		return flights;
	}

	private static Flight getFlight(FlightsSupplier supplier) {
		return Flight.builder().airline("Airline").arrivalDate(Instant.now()).departureAirportCode("ABC")
				.departureDate(Instant.now()).destinationAirportCode("BCD").fare(BigDecimal.ONE).supplier(supplier)
				.supplierData(Collections.emptyMap()).build();
	}
}
