package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.juniorro.patientappointmentsystem.Service.PhysicianService;
import com.juniorro.patientappointmentsystem.model.Physician;
import com.juniorro.patientappointmentsystem.repo.PhysicianRepo;

@Service
@Transactional
public class PhysicianServiceImpl implements PhysicianService {
	
	@Autowired
	PhysicianRepo physicianRepo; 

	@Override
	public List<Physician> allPhysicians() {
		return physicianRepo.findAll();
	}

	@Override
	public Physician findByFirstName(String firstName) {
		return physicianRepo.findByFirstName(firstName);
	}

	@Override
	public Physician findByEmail(String email) {
		return physicianRepo.findByEmail(email);
	}

	@Override
	public Physician findById(Long id) {
		return physicianRepo.findOne(id);
	}

	@Override
	public long count() {
		return physicianRepo.count();
	}

	@Override
	public Physician savePhysician(Physician physician) {
		return physicianRepo.save(physician);
	}

}
