package com.juniorro.patientappointmentsystem.Service;

import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.security.Role;

@Service
public interface RoleService {

	Role findByName(final String name);
	
	Role save(final Role role);
}
