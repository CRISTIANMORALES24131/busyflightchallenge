package com.travix.medusa.busyflights.external.crazyair.io.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.travix.medusa.busyflights.component.model.Flight;
import com.travix.medusa.busyflights.component.model.FlightsSupplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CrazyAirFlightDetail implements Serializable {

	private static final long serialVersionUID = 7846896485322666576L;

	private String airline;
    private double price;
    private String cabinclass;
    private String departureAirportCode;
    private String destinationAirportCode;
	@DateTimeFormat(iso = ISO.DATE_TIME)
    private Instant departureDate;
	@DateTimeFormat(iso = ISO.DATE_TIME)
    private Instant arrivalDate;

    public Flight toModel() {
    	return Flight.builder()
    		.airline(airline)
    		.fare(new BigDecimal(price))
    		.departureAirportCode(departureAirportCode)
    		.destinationAirportCode(destinationAirportCode)
    		.departureDate(departureDate)
    		.arrivalDate(arrivalDate)
    		.supplierData(getAditionalInfo())
    		.supplier(FlightsSupplier.CRAZY_AIR)
    		.build();
    }

    private Map<String, String> getAditionalInfo() {
    	Map<String, String> properties = new HashMap<>();
    	properties.put("cabinclass", cabinclass);
    	return properties;
    }
}
