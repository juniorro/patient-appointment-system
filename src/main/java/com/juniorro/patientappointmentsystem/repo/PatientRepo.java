package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {

	Patient findByFirstName(String firstName);

	Patient findByEmail(String email);
	
}
