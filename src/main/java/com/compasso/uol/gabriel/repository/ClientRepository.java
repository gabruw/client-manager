package com.compasso.uol.gabriel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	@Transactional(readOnly = true)
	Optional<Client> findByName(String name);
}
