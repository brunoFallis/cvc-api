package com.cvc.teste.forms;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchHotelForm {

	private Integer cityCode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkin;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkout;

	private Integer adultAmount;
	private Integer childAmount;
	private Integer currentPaginate;
	private Integer firstRequest;

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public Integer getAdultAmount() {
		return adultAmount;
	}

	public void setAdultAmount(Integer adultAmount) {
		this.adultAmount = adultAmount;
	}

	public Integer getChildAmount() {
		return childAmount;
	}

	public void setChildAmount(Integer childAmount) {
		this.childAmount = childAmount;
	}

	public Integer getCurrentPaginate() {
		return currentPaginate;
	}

	public void setCurrentPaginate(Integer currentPaginate) {
		this.currentPaginate = currentPaginate;
	}

	public Integer getFirstRequest() {
		return firstRequest;
	}

	public void setFirstRequest(Integer firstRequest) {
		this.firstRequest = firstRequest;
	}

}
