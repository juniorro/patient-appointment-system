package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.security.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
