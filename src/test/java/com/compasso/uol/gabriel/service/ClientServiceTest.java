package com.compasso.uol.gabriel.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.compasso.uol.gabriel.dto.ReturnClientDTO;
import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.enumerator.GenderEnum;
import com.compasso.uol.gabriel.enumerator.RoleEnum;
import com.compasso.uol.gabriel.repository.ClientRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ClientServiceTest {

	@MockBean
	private ClientRepository clientRepository;

	@Autowired
	private ClientService clientService;

	@Mock
	private Client client;

	private static final Long ID = 1L;
	private static final Date BIRTH = new Date();
	private static final String NAME = "Genisvaldo";
	private static final GenderEnum GENDER = GenderEnum.MALE;

	private static final String CITY_NAME = "Juiz de Fora";
	private static final String CITY_STATE = "Minas Gerais";

	private static final RoleEnum ROLE = RoleEnum.USER;
	private static final String PASSWORD = "teste@123";
	private static final String EMAIL = "genisvaldo@test.com";

	@BeforeEach
	public void setup() {
		client = new Client();
		client.setName(NAME);
		client.setBirth(BIRTH);
		client.setGender(GENDER);

		City city = new City();
		city.setId(ID);
		city.setName(CITY_NAME);
		city.setState(CITY_STATE);
		client.setCity(city);

		Authentication auth = new Authentication();
		auth.setRole(ROLE);
		auth.setEmail(EMAIL);
		auth.setPassword(PASSWORD);
		client.setAuthentication(auth);
	}

	@Test
	public void find_all_clients() {
		List<Client> clients = new ArrayList<Client>();
		clients.add(client);

		when(this.clientRepository.findAll()).thenReturn(clients);

		List<ReturnClientDTO> options = this.clientService.findAll();
		Assertions.assertTrue(options.size() == 1);
	}

	@Test
	public void find_client_by_id() {
		when(this.clientRepository.findById(ID)).thenReturn(Optional.of(client));

		Optional<Client> tClient = this.clientService.findById(ID);
		Assertions.assertTrue(tClient.isPresent());
	}

	@Test
	public void find_client_by_name() {
		when(this.clientRepository.findByName(NAME)).thenReturn(Optional.of(client));

		Optional<Client> tClient = this.clientService.findByName(NAME);
		Assertions.assertTrue(tClient.isPresent());
	}

	@Test
	public void persist_client() {
		when(this.clientRepository.save(client)).thenReturn(client);

		Client tClient = this.clientService.persistir(client);
		Assertions.assertEquals(tClient, client);
	}

	@Test
	public void delete_client() {
		when(this.clientRepository.findById(ID)).thenReturn(Optional.of(client));

		clientService.deleteById(ID);
		verify(clientRepository, times(1)).deleteById(ID);
	}
}
