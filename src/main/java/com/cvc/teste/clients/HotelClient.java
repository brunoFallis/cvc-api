package com.cvc.teste.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HotelClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final String hotelsUrl = "https://cvcbackendhotel.herokuapp.com/hotels/avail/{cityId}";
	
	public String getHotelsByCity(Integer idCity) {
		return restTemplate.getForEntity( hotelsUrl.replace( "{cityId}" , idCity.toString() ) , String.class ).getBody();
	}
	
}
