package com.juniorro.patientappointmentsystem.Service;

import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Customer;

@Service
public interface CustomerService {

	Customer findByUsername(final String Customer);

	Customer findByEmail(final String email);

	Customer findById(Long id);

	boolean checkUserExist(final String username, final String email);

	boolean checkUsernameExist(final String username);

	boolean checkEmailExist(final String email);

	Customer saveCustomer(final Customer customer);

	void saveConfirmCustomer(Customer customer);	

}
