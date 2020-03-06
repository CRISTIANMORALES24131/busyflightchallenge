package com.travix.medusa.busyflights.component.flight.io.web.v1;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.travix.medusa.busyflights.component.service.FlightService.FindFlightsQuery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public interface FlightControllerRequest {

	@Getter
	@Setter
	@ToString
	public class GetFlightsRequest implements Serializable {

		private static final long serialVersionUID = 8734889431350426814L;

		@NotBlank
		@Size(min = 3, max = 3)
		private String origin;
		@NotBlank
		@Size(min = 3, max = 3)
		private String destination;
		@NotNull
		@Future
		@DateTimeFormat(iso = ISO.DATE)
		private LocalDate departureDate;
		@Future
		@DateTimeFormat(iso = ISO.DATE)
		private LocalDate returnDate;
		@NotNull
		@Max(value = 4)
		private Integer numberOfPassengers;

		public FindFlightsQuery toQuery() {
			return FindFlightsQuery.builder()
					.origin(origin)
					.destination(destination)
					.departureDate(departureDate.toString())
					.returnDate(returnDate.toString())
					.numberOfPassengers(numberOfPassengers)
					.build();
		}
	}

}
