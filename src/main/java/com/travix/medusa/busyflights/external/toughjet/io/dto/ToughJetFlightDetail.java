package com.travix.medusa.busyflights.external.toughjet.io.dto;

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
public class ToughJetFlightDetail implements Serializable {

	private static final long serialVersionUID = 7846896485322666576L;

    private String carrier;
    private double basePrice;
    private double tax;
    private double discount;
    private String departureAirportName;
    private String arrivalAirportName;
	@DateTimeFormat(iso = ISO.DATE_TIME)
    private Instant outboundDateTime;
	@DateTimeFormat(iso = ISO.DATE_TIME)
    private Instant inboundDateTime;

    public Flight toModel() {
    	return Flight.builder()
    		.airline(carrier)
    		.fare(getFare(basePrice, tax, discount))
    		.departureAirportCode(departureAirportName)
    		.destinationAirportCode(arrivalAirportName)
    		.departureDate(outboundDateTime)
    		.arrivalDate(inboundDateTime)
    		.supplierData(getAditionalInfo())
    		.supplier(FlightsSupplier.TOUGH_JET)
    		.build();
    }

    private Map<String, String> getAditionalInfo() {
    	Map<String, String> properties = new HashMap<>();
    	properties.put("basePrice", new BigDecimal(basePrice).toString());
    	properties.put("tax", new BigDecimal(tax).toString());
    	properties.put("discount", new BigDecimal(discount).toString());
    	return properties;
    }

    private BigDecimal getFare(double basePrice, double tax, double discount) {
    	double baseDiscount = (basePrice + tax) * (discount / 100.0);
    	double result = (basePrice + tax) - baseDiscount;
    	return BigDecimal.valueOf(result);
    }
}
