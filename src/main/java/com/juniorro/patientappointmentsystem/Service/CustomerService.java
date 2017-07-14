package com.juniorro.patientappointmentsystem.Service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.UserRole;

@Service
public interface CustomerService {

	List<Customer> allCustomers();

	Customer findByUsername(final String Customer);

	Customer findByEmail(final String email);

	Customer findById(Long id);

	boolean checkUserExist(final String username, final String email);

	boolean checkUsernameExist(final String username);

	boolean checkEmailExist(final String email);

	Customer saveCustomer(final Customer customer, Set<UserRole> userRoles);

	void saveConfirmCustomer(Customer customer);

	Customer getOne(Long id);

	void delete(long id);	
	
	long count();

}
