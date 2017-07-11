package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.Diagnosis;

public interface DiagnosisRepo extends JpaRepository<Diagnosis, Long> {

	Diagnosis findByname(String name);
	
}
