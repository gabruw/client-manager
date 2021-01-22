package com.compasso.uol.gabriel.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.repository.ClientRepository;
import com.compasso.uol.gabriel.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public void deleteById(Long id) {
		log.info("Removendo um cliente pelo Id: {}", id);
		this.clientRepository.deleteById(id);
	}

	@Override
	public Optional<Client> findById(Long id) {
		log.info("Buscando um cliente pelo Id: {}", id);
		return this.clientRepository.findById(id);
	}

	@Override
	public Optional<Client> findByName(String name) {
		log.info("Buscando um cliente pelo nome: {}", name);
		return this.clientRepository.findByName(name);
	}

	@Override
	public Client persistir(Client client) {
		log.info("Persistindo cliente: {}", client);
		return this.clientRepository.save(client);
	}
}
