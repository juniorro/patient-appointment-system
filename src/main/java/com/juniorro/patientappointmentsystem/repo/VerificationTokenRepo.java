package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juniorro.patientappointmentsystem.model.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(final String token);

	void save(String token);

}
