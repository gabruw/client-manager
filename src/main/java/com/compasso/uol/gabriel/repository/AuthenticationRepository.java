package com.compasso.uol.gabriel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.Authentication;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

	@Transactional(readOnly = true)
	Optional<Authentication> findByEmail(String email);
}
