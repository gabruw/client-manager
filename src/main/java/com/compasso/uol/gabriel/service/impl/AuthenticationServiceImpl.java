package com.compasso.uol.gabriel.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.repository.AuthenticationRepository;
import com.compasso.uol.gabriel.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	private AuthenticationRepository authenticationRepository;

	@Override
	public void deleteById(Long id) {
		log.info("Removendo uma autenticação pelo Id: {}", id);
		this.authenticationRepository.deleteById(id);
	}

	@Override
	public Optional<Authentication> findById(Long id) {
		log.info("Buscando uma autenticação pelo Id: {}", id);
		return this.authenticationRepository.findById(id);
	}

	@Override
	public Optional<Authentication> findByEmail(String email) {
		log.info("Buscando uma autenticação pelo email: {}", email);
		return this.authenticationRepository.findByEmail(email);
	}

	@Override
	public Authentication persistir(Authentication authentication) {
		log.info("Persistindo autenticação: {}", authentication);
		return this.authenticationRepository.save(authentication);
	}
}
