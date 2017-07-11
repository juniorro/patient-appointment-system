package com.juniorro.patientappointmentsystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Diagnosis;

@Service
public interface DiagnosisService {

	Diagnosis findByName(final String name);
	
	Diagnosis save(final Diagnosis diagnosis);
	List<Diagnosis> allDiagnosis ();
}
