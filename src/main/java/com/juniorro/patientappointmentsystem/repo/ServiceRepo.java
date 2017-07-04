package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.Service;

public interface ServiceRepo extends JpaRepository<Service, Long> {

	Service findByName(String firstName);
	
	long count();
	
}
