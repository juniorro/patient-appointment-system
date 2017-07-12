package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniorro.patientappointmentsystem.Service.VerificationTokenService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.VerificationToken;
import com.juniorro.patientappointmentsystem.repo.VerificationTokenRepo;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {
	
	@Autowired
	VerificationTokenRepo verificationTokenRepo;

	@Override
	public VerificationToken findByToken(String token) {
		return verificationTokenRepo.findByToken(token);
	}

	@Override
	public void createVerificationToken(Customer customer, String token) {
		final VerificationToken myToken = new VerificationToken(customer, token);
		verificationTokenRepo.save(myToken);
	}

	@Override
	public void saveToken(VerificationToken token) {
		verificationTokenRepo.save(token);
		
	}

	@Override
	public VerificationToken getVerificationToken(String token) {
		return verificationTokenRepo.findByToken(token);
	}

	@Override
	public void delete(long id) {
		verificationTokenRepo.delete(id);
	}



}
