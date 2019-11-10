package com.cvc.teste.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.Period;
import org.springframework.stereotype.Service;

import com.cvc.teste.forms.SearchHotelForm;
import com.cvc.teste.json.HotelsJson;
import com.cvc.teste.json.PriceDetail;
import com.cvc.teste.json.Room;
import com.cvc.teste.utils.Constants;

@Service
public class HotelBusiness {
	
	public List<HotelsJson> mountHotels(List<HotelsJson> hotelsFromApi, @Valid SearchHotelForm searchHotelForm) {
		
		Period period = new Period( searchHotelForm.getCheckin().getTime(), searchHotelForm.getCheckout().getTime() );
		
		BigDecimal daysToStay 	= new BigDecimal( period.getDays() );
		BigDecimal numAdults 	= new BigDecimal( searchHotelForm.getAdultAmount() );
		BigDecimal totalPrice 	= new BigDecimal( Constants.ZERO );
		BigDecimal taxCom	  	= new BigDecimal( Constants.COMISSION );
		
		BigDecimal numChild		= null;
		if( searchHotelForm.getChildAmount() != null && searchHotelForm.getChildAmount() > Constants.ZERO ) {
			numChild = new BigDecimal( searchHotelForm.getChildAmount() );
		}
		
		List<HotelsJson> results = new ArrayList<HotelsJson>();
		
		for (HotelsJson hotel : hotelsFromApi.subList( searchHotelForm.getCurrentPaginate() , searchHotelForm.getCurrentPaginate() + Constants.PAGE_SIZE )) {
			
			for (Room room : hotel.getRooms()) {
				totalPrice = room.getPrice().getAdult().multiply( daysToStay ).multiply( numAdults );
				totalPrice = totalPrice.add( totalPrice.multiply( taxCom ) );
				
				if( numChild != null && numChild.intValue() > Constants.ZERO ) {
					totalPrice = room.getPrice().getChild().multiply( daysToStay ).multiply( numChild );
					totalPrice = totalPrice.add( totalPrice.multiply( taxCom ) );
				}
				
				PriceDetail priceDetail = new PriceDetail();
				
				priceDetail.setPricePerDayAdult( room.getPrice().getAdult() );
				priceDetail.setPricePerDayChild( room.getPrice().getChild() );
				
				room.setTotalPrice( totalPrice.setScale( 2, RoundingMode.HALF_UP ) );
				room.setPriceDetail( priceDetail );
				room.setPrice( null );
			}
			
			hotel.setCityCode( null );
			hotel.setName( null );
			
			totalPrice = null;
			
			results.add( hotel );
		}
		
		return results;
	}
	
}
