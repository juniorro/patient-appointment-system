package com.juniorro.patientappointmentsystem.registrationlistener;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.juniorro.patientappointmentsystem.model.Customer;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final String appUrl;
	private final Customer customer;

	public OnRegistrationCompleteEvent(final Customer customer, final String appUrl) {
		super(customer);
		this.customer = customer;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Customer getCustomer() {
		return customer;
	}

}
