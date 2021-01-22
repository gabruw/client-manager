package com.compasso.uol.gabriel.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.repository.ClientRepository;

@SpringBootTest
public class ClientServiceTest {

	@MockBean
	private ClientRepository clientRepository;

	@Autowired
	private ClientService clientService;

	private static final Long ID = 1L;
	private static final String NAME = "Genisvaldo";

	@BeforeEach
	public void init() throws Exception {
		when(this.clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Client()));
		when(this.clientRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(new Client()));

		when(this.clientRepository.save(Mockito.any(Client.class))).thenReturn(new Client());
	}

	@Test
	public void findClientById() {
		Optional<Client> client = this.clientService.findById(ID);
		assertTrue(client.isPresent());
	}

	@Test
	public void findClientByName() {
		Optional<Client> client = this.clientService.findByName(NAME);
		assertTrue(client.isPresent());
	}

	@Test
	public void presistClient() {
		Client client = this.clientService.persistir(new Client());
		assertNotNull(client);
	}
}
