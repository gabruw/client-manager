package com.compasso.uol.gabriel.service;

import java.util.Optional;

import com.compasso.uol.gabriel.entity.Authentication;

public interface AuthenticationService {
	void deleteById(Long id);
	
	Optional<Authentication> findById(Long id);

	Optional<Authentication> findByEmail(String email);

	Authentication persistir(Authentication authentication);
}
