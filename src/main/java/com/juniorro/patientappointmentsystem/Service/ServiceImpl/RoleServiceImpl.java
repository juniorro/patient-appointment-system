package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.juniorro.patientappointmentsystem.Service.RoleService;
import com.juniorro.patientappointmentsystem.model.security.Role;
import com.juniorro.patientappointmentsystem.repo.RoleRepo;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepo roleRepo; 

	@Override
	public Role findByName(String name) {
		return roleRepo.findByName(name);
	}

	@Override
	public Role save(Role role) {
		return roleRepo.save(role);
	}
}
