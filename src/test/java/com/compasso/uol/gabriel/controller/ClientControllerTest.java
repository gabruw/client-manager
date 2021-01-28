package com.compasso.uol.gabriel.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.compasso.uol.gabriel.dto.EditAuthenticationDTO;
import com.compasso.uol.gabriel.dto.EditClientDTO;
import com.compasso.uol.gabriel.dto.IncludeAuthenticationDTO;
import com.compasso.uol.gabriel.dto.IncludeClientDTO;
import com.compasso.uol.gabriel.dto.RegisterClientDTO;
import com.compasso.uol.gabriel.dto.ReturnClientDTO;
import com.compasso.uol.gabriel.dto.UpdateClientDTO;
import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.enumerator.GenderEnum;
import com.compasso.uol.gabriel.enumerator.RoleEnum;
import com.compasso.uol.gabriel.enumerator.message.ClientMessage;
import com.compasso.uol.gabriel.response.Error;
import com.compasso.uol.gabriel.service.AuthenticationService;
import com.compasso.uol.gabriel.service.CityService;
import com.compasso.uol.gabriel.service.ClientService;
import com.compasso.uol.gabriel.utils.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ObjectMapper objMapper;

	@MockBean
	private CityService cityService;

	@MockBean
	private ClientService clientService;

	@MockBean
	private AuthenticationService authenticationService;

	@Mock
	private City city;

	@Mock
	private Client client;

	@Mock
	private Authentication auth;

	@Mock
	private ReturnClientDTO returnClient;

	private final String BASE_URL = "/client";

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

		city = new City();
		city.setId(ID);
		city.setName(CITY_NAME);
		city.setState(CITY_STATE);
		client.setCity(city);

		auth = new Authentication();
		auth.setRole(ROLE);
		auth.setEmail(EMAIL);
		auth.setPassword(PASSWORD);
		client.setAuthentication(auth);

		returnClient = new ReturnClientDTO();
		returnClient.setName(NAME);
		returnClient.setBirth(BIRTH);
		returnClient.setGender(GENDER);
	}

	@Test
	@WithMockUser
	public void find_all_clients() throws Exception {
		List<ReturnClientDTO> clients = new ArrayList<ReturnClientDTO>();
		clients.add(returnClient);

		when(this.clientService.findAll()).thenReturn(clients);

		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].name", equalTo(NAME))).andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void find_client_by_id() throws Exception {
		when(this.clientService.findById(ID)).thenReturn(Optional.of(client));

		final String URL = String.format("%s?id=%s", BASE_URL, client.getName());
		mockMvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void find_client_by_name() throws Exception {
		when(this.clientService.findByName(NAME)).thenReturn(Optional.of(client));

		final String URL = String.format("%s?name=%s", BASE_URL, client.getName());
		mockMvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", equalTo(NAME))).andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void find_client_by_name_with_invalid() throws Exception {
		when(this.clientService.findByName(NAME)).thenReturn(Optional.of(client));

		final String URL = String.format("%s?name=%s", BASE_URL, "teste");
		Error error = Messages.getClient(ClientMessage.NONEXISTENT.toString());

		mockMvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].title", equalTo(error.getTitle())))
				.andExpect(jsonPath("$.errors[0].text", equalTo(error.getText())))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	@WithMockUser
	public void include_client() throws Exception {
		IncludeClientDTO includeClient = mapper.map(client, IncludeClientDTO.class);
		IncludeAuthenticationDTO includeAuth = mapper.map(auth, IncludeAuthenticationDTO.class);

		RegisterClientDTO registerClient = new RegisterClientDTO();
		registerClient.setClient(includeClient);
		registerClient.setAuthentication(includeAuth);

		when(this.cityService.findById(ID)).thenReturn(Optional.of(city));
		when(this.authenticationService.persist(auth)).thenReturn(auth);

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(objMapper.writeValueAsString(registerClient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void edit_client() throws Exception {
		client.setId(ID);
		EditClientDTO editClient = mapper.map(client, EditClientDTO.class);

		auth.setId(ID);
		EditAuthenticationDTO editAuth = mapper.map(auth, EditAuthenticationDTO.class);

		UpdateClientDTO updateClient = new UpdateClientDTO();
		updateClient.setClient(editClient);
		updateClient.setAuthentication(editAuth);

		when(this.authenticationService.persist(auth)).thenReturn(auth);
		when(this.cityService.findById(ID)).thenReturn(Optional.of(city));
		when(this.clientService.findById(ID)).thenReturn(Optional.of(client));
		when(this.authenticationService.findById(ID)).thenReturn(Optional.of(auth));

		mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(objMapper.writeValueAsString(updateClient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void delete_client() throws Exception {
		auth.setId(ID);
		client.setId(ID);

		when(this.clientService.findById(ID)).thenReturn(Optional.of(client));

		final String URL = String.format("%s?id=%s", BASE_URL, client.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete(URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", equalTo(NAME))).andExpect(jsonPath("$.errors").isEmpty());

		verify(this.clientService, times(1)).deleteById(ID);
	}
}
