package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	Customer findByUsername(String username);

	Customer findByEmail(String email);
	
}
