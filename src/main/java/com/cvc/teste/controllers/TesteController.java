package com.cvc.teste.controllers;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import com.cvc.teste.business.HotelBusiness;
import com.cvc.teste.clients.HotelClient;
import com.cvc.teste.forms.SearchHotelForm;
import com.cvc.teste.json.HotelsJson;
import com.cvc.teste.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@CrossOrigin
@RestController
public class TesteController {
	
	@Autowired
	private HotelClient hotelClient;
	
	@Autowired
	private HotelBusiness hotelBusiness;
	
	@RequestMapping(value= "/home")
	public ModelAndView getHome() {
		return new ModelAndView( "index" ); 
	}
	
	@RequestMapping(value = "/hotels/prices/")
	public ModelAndView getTotalPrice(@Valid SearchHotelForm searchHotelForm, HttpServletResponse response ) {
		
		boolean errorInProcess = false;
		
		try {
			
			Type listHotels = new TypeToken<List<HotelsJson>>(){}.getType();
			
			String requisitionBody = hotelClient.getHotelsByCity( searchHotelForm.getCityCode() );
			
			if( StringUtils.isEmpty( requisitionBody ) ) {
				errorInProcess = true;
			}
			
			List<HotelsJson> hotelsFromApi = new Gson().fromJson( requisitionBody , listHotels );
			
			if( CollectionUtils.isEmpty( hotelsFromApi ) ) {
				errorInProcess = true;
			}
			
			if( errorInProcess ) {
				response.setStatus( HttpStatus.BAD_GATEWAY.value() );
				return null;
			}
			
			return mountModelAndView(searchHotelForm, hotelBusiness.mountHotels( hotelsFromApi, searchHotelForm ) );
			
		} catch (Throwable t) {
			response.setStatus( HttpStatus.BAD_GATEWAY.value() );
			return null;
		}
		
	}
	
	private ModelAndView mountModelAndView(SearchHotelForm searchHotelForm, List<HotelsJson> results ) {
		
		ModelAndView mav = new ModelAndView( "list" );
		
		Locale localeBR = new Locale("pt","br");
	     
	    NumberFormat formatter = NumberFormat.getInstance(localeBR);
	    formatter.setMinimumFractionDigits(2);
	    formatter.setMaximumFractionDigits(2);
		
		mav.addObject( "hotels" , 		results );
	    mav.addObject( "formatter" , 	formatter );
		mav.addObject( "first" , 		searchHotelForm.getFirstRequest() );
	    
		if( searchHotelForm.getCurrentPaginate() <= Constants.ZERO ) {
			mav.addObject( "paginateVal", Constants.PAGE_SIZE );
		}
		else {
			mav.addObject( "paginateVal" , searchHotelForm.getCurrentPaginate() );
		}
		
		return mav;
	}

}
