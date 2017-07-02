package com.juniorro.patientappointmentsystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Patient;
import com.juniorro.patientappointmentsystem.model.Physician;

@Service
public interface PhysicianService {

	List<Physician> allPhysicians();

	Physician findByFirstName(final String firstName);

	Physician findByEmail(final String email);

	Physician findById(final Long id);

	long count();

	Physician savePhysician(final Physician physician);

}
