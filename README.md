The domain provided was not change significantly, some datatypes (dates), and getters and setters were replaced to lombok anotations.

It took me a little bit more than 5 hours to complete, so I had to avoid give whole testing coverage to the project.

-- Unit tests for gateways, services and support classes
-- Component tests from controllers to gateways mocking external services 
-- End to end tests.

Application yml contains the url to the external services: http://www.mocky.io was used to get dummys for testing

flight.supplier:
  crazyair:
    service:
      url: http://www.mocky.io/v2/5e61c44530000093004d5515
	  
[
    {
        "airline": "American Airlines",
        "price": 1220000,
        "cabinclass": "E",
        "departureAirportCode": "LHR",
        "destinationAirportCode": "LYT",
        "departureDate": "2020-09-02T08:05:23.653Z",
        "arrivalDate": "2020-10-02T08:05:23.653Z"
    },
    {
        "airline": "avianca",
        "price": 2000000,
        "cabinclass": "B",
        "departureAirportCode": "STH",
        "destinationAirportCode": "HHY",
        "departureDate": "2020-09-02T08:05:23.653Z",
        "arrivalDate": "2020-10-02T08:05:23.653Z"
    }
]	  
	  
  toughjet:
    service:
      url: http://www.mocky.io/v2/5e61c42a30000053004d5514
	
[
    {
        "carrier": "Alaska Airlines",
        "basePrice": 312000,
        "tax": 220000,
        "discount": 10,
        "departureAirportName": "YFR",
        "arrivalAirportName": "UUN",
        "outboundDateTime": "2020-09-02T08:05:23.653Z",
        "inboundDateTime": "2020-10-02T08:05:23.653Z"
    },
    {
        "carrier": "Island Air Service",
        "basePrice": 150000,
        "tax": 442,
        "discount": 0,
        "departureAirportName": "LHR",
        "arrivalAirportName": "LYT",
        "outboundDateTime": "2020-09-02T08:05:23.653Z",
        "inboundDateTime": "2020-10-02T08:05:23.653Z"
    },
    {
        "carrier": "Allegiant Air LLC",
        "basePrice": 130000,
        "tax": 10000,
        "discount": 5,
        "departureAirportName": "LHR",
        "arrivalAirportName": "LYT",
        "outboundDateTime": "2020-09-02T08:05:23.653Z",
        "inboundDateTime": "2020-10-02T08:05:23.653Z"
    }
]