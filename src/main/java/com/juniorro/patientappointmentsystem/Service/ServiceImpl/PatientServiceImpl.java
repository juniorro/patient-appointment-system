package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniorro.patientappointmentsystem.Service.PatientService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.Patient;
import com.juniorro.patientappointmentsystem.repo.PatientRepo;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepo patientRepo;

	@Override
	public List<Patient> allPatients() {
		return patientRepo.findAll();
	}

	@Override
	public Patient findByFirstName(String firstName) {
		return patientRepo.findByFirstName(firstName);
	}

	@Override
	public Patient findByEmail(String email) {
		return patientRepo.findByEmail(email);
	}

	@Override
	public Patient findById(Long id) {
		return patientRepo.findOne(id);
	}

	@Override
	public Patient savePatient(Patient patient) {
		return patientRepo.save(patient);
	}

	@Override
	public Patient getOne(Long id) {
		return patientRepo.findOne(id);
	}



	}
