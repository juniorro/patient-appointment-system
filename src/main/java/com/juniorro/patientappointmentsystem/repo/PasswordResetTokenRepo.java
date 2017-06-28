package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorro.patientappointmentsystem.model.security.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(final String token);
}
