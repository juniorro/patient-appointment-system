package com.juniorro.patientappointmentsystem.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.PasswordResetToken;

@Service
public interface PasswordResetTokenService {

	PasswordResetToken findByToken(final String token);
	
	void createPasswordResetToken(Customer Customer, String token);
	
	SimpleMailMessage constructResetTokenEmail(final String contextPath, final String token, final Customer customer);

	void changePassword(Customer customer, String password);

	void delete(long id);
}
