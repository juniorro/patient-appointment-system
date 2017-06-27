package com.juniorro.patientappointmentsystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juniorro.patientappointmentsystem.model.Doctor;

@Service
public interface DoctorService {
	
	List<Doctor> allDoctors();

}
