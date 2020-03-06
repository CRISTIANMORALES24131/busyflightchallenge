package com.travix.medusa.busyflights.component.flight.io.web.v1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.travix.medusa.busyflights.component.model.Flight;

import lombok.Builder;
import lombok.Getter;

public interface FlightControllerResponses {

	@Getter
	@Builder
	@JsonInclude(value = Include.NON_NULL)
	public class FlightDetail implements Serializable {

		private static final long serialVersionUID = -7048481042966340682L;
		
		private String airline;
		private String supplier;
		private String fare;
		private String departureAirportCode;
		private String destinationAirportCode;
		private String departureDate;
		private String arrivalDate;

		public static FlightDetail of(Flight model) {
			return FlightDetail.builder()
					.airline(model.getAirline())
					.supplier(model.getSupplier().toString())
					.fare(model.getFare().setScale(2).toString())
					.departureAirportCode(model.getDepartureAirportCode())
					.destinationAirportCode(model.getDestinationAirportCode())
					.departureDate(model.getDepartureDate().toString())
					.arrivalDate(model.getArrivalDate().toString()).build();
		}
	}

}
