package com.juniorro.patientappointmentsystem.registrationlistener.listener;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.juniorro.patientappointmentsystem.Service.VerificationTokenService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.registrationlistener.OnRegistrationCompleteEvent;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final Customer customer = event.getCustomer();
		final String token = UUID.randomUUID().toString();
		verificationTokenService.createVerificationToken(customer, token);
		final SimpleMailMessage email = constructEmailMessage(event, customer, token);
		mailSender.send(email);
	}

	private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final Customer customer,
			final String token) {
		final String recipientAddress = customer.getEmail();
		final String subject = "Registration Confirmation - Please Confirm Your Email Address";
		final String confirmationUrl = event.getAppUrl()+"/confirm?token=" + token;
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("Please open the following URL to verify your account: \r\n\n\n" + confirmationUrl);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

}
