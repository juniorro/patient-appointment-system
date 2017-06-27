package com.juniorro.patientappointmentsystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.Patient;

@Service
public interface PatientService {

	List<Patient> allPatients();

	Patient findByFirstName(final String firstName);

	Patient findByEmail(final String email);

	Patient findById(final Long id);
	
	long count();

	/*boolean checkUserExist(final String username, final String email);

	boolean checkUsernameExist(final String username);

	boolean checkEmailExist(final String email);*/

	Patient savePatient(final Patient patient);

	/*void saveConfirmCustomer(Customer customer);*/

	Patient getOne(Long id);	

}
