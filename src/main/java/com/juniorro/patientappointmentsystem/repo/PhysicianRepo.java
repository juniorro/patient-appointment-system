package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.Physician;

public interface PhysicianRepo extends JpaRepository<Physician, Long> {

	Physician findByFirstName(String firstName);

	Physician findByEmail(String email);

	long count();

}
