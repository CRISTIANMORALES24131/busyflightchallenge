package com.travix.medusa.busyflights.component.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Flight {

	private String airline;
	private FlightsSupplier supplier;
	private BigDecimal fare;
	private String departureAirportCode;
	private String destinationAirportCode;
	private Instant departureDate;
	private Instant arrivalDate;
	private Map<String, String> supplierData;

}
