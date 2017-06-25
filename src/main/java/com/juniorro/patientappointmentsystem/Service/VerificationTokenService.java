package com.juniorro.patientappointmentsystem.Service;

import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.VerificationToken;

@Service
public interface VerificationTokenService {

	VerificationToken findByToken(final String token);

	void createVerificationToken(Customer customer, String token);
	
	void saveToken(VerificationToken myToken);

	VerificationToken getVerificationToken(String token);
	
}
