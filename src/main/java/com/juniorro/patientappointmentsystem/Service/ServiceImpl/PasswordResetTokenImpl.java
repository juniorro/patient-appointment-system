package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniorro.patientappointmentsystem.Service.PasswordResetTokenService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.PasswordResetToken;
import com.juniorro.patientappointmentsystem.repo.CustomerRepo;
import com.juniorro.patientappointmentsystem.repo.PasswordResetTokenRepo;

@Service
@Transactional
public class PasswordResetTokenImpl implements PasswordResetTokenService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PasswordResetTokenRepo passwordResetTokenRepo;
	
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public PasswordResetToken findByToken(String token) {
		return passwordResetTokenRepo.findByToken(token);
	}

	@Override
	public void createPasswordResetToken(final Customer customer, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, customer);
		passwordResetTokenRepo.save(myToken);
	}
	
	@Override
    public void changePassword(final Customer customer, final String password) {
		String encryptpassword = bCryptPasswordEncoder.encode(customer.getPassword());
		 customer.setPassword(encryptpassword);		
		customerRepo.save(customer);
    }

	@Override
	public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Customer customer) {
		final String url = contextPath + "/changePassword?id=" + customer.getId() + "&token=" + token;
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(customer.getEmail());
		email.setSubject("Reset Password");
		email.setText("Please open the following URL to reset your password: \r\n\n" + url);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	@Override
	public void delete(long id) {
		passwordResetTokenRepo.delete(id);
	}

}
