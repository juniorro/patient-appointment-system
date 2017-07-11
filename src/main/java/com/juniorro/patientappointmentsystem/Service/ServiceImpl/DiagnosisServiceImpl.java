package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.juniorro.patientappointmentsystem.Service.DiagnosisService;
import com.juniorro.patientappointmentsystem.model.Diagnosis;
import com.juniorro.patientappointmentsystem.repo.DiagnosisRepo;

@Service
@Transactional
public class DiagnosisServiceImpl implements DiagnosisService {
	
	@Autowired
	DiagnosisRepo diagnosisRepo;

	@Override
	public Diagnosis findByName(String name) {
		return diagnosisRepo.findByname(name);
	}

	@Override
	public Diagnosis save(Diagnosis diagnosis) {
		return diagnosisRepo.save(diagnosis);
	}

	@Override
	public List<Diagnosis> allDiagnosis() {
		return diagnosisRepo.findAll();
	} 

	
}
