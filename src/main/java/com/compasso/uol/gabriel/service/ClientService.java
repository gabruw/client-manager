package com.compasso.uol.gabriel.service;

import java.util.Optional;

import com.compasso.uol.gabriel.entity.Client;

public interface ClientService {
	void deleteById(Long id);
	
	Optional<Client> findById(Long id);
	
	Optional<Client> findByName(String name);

	Client persistir(Client client);
}
