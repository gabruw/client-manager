package com.compasso.uol.gabriel.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.compasso.uol.gabriel.dto.LoginDTO;
import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.enumerator.GenderEnum;
import com.compasso.uol.gabriel.enumerator.RoleEnum;
import com.compasso.uol.gabriel.enumerator.message.AuthenticationMessage;
import com.compasso.uol.gabriel.response.Error;
import com.compasso.uol.gabriel.service.AuthenticationService;
import com.compasso.uol.gabriel.service.CityService;
import com.compasso.uol.gabriel.service.ClientService;
import com.compasso.uol.gabriel.utils.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private MockMvc mockMvc;

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

	private final String BASE_URL = "/authentication";

	private static final Long ID = 1L;
	private static final Date BIRTH = new Date();
	private static final String NAME = "Genisvaldo";
	private static final GenderEnum GENDER = GenderEnum.MALE;

	private static final String CITY_NAME = "Juiz de Fora";
	private static final String CITY_STATE = "Minas Gerais";

	private static final RoleEnum ROLE = RoleEnum.USER;
	private static final String PASSWORD = "teste@123";
	private static final String EMAIL = "genisvaldo@test.com";

	private static final String BEARER_PREFIX = "Bearer ";
	private static final String TOKEN_HEADER = "Authorization";

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
	}

	@Test
	public void login() throws Exception {
		client.setId(ID);
		when(this.clientService.findById(ID)).thenReturn(Optional.of(client));

		auth.setClient(client);
		LoginDTO login = mapper.map(auth, LoginDTO.class);

		String encodedPassword = new BCryptPasswordEncoder().encode(auth.getPassword());
		auth.setPassword(encodedPassword);

		when(this.authenticationService.findByEmail(EMAIL)).thenReturn(Optional.of(auth));

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(objMapper.writeValueAsString(login))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", equalTo(NAME))).andExpect(jsonPath("$.data.email", equalTo(EMAIL)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	public void login_without_valid_email() throws Exception {
		client.setId(ID);
		auth.setEmail("teste_123@etest.com");

		LoginDTO login = mapper.map(auth, LoginDTO.class);
		Error error = Messages.getAuthentication(AuthenticationMessage.INVALIDEMAIL.toString(), login.getEmail());

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(objMapper.writeValueAsString(login))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden()).andExpect(jsonPath("$.errors[0].title", equalTo(error.getTitle())))
				.andExpect(jsonPath("$.errors[0].text", equalTo(error.getText())))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void login_without_valid_password() throws Exception {
		client.setId(ID);
		when(this.clientService.findById(ID)).thenReturn(Optional.of(client));

		String encodedPassword = new BCryptPasswordEncoder().encode(auth.getPassword());
		auth.setPassword(encodedPassword);

		auth.setClient(client);
		when(this.authenticationService.findByEmail(EMAIL)).thenReturn(Optional.of(auth));

		auth.setPassword("Iks234#fefk@fek(vemd");
		LoginDTO login = mapper.map(auth, LoginDTO.class);

		Error error = Messages.getAuthentication(AuthenticationMessage.INVALIDPASSWORD.toString(), login.getEmail());

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(objMapper.writeValueAsString(login))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden()).andExpect(jsonPath("$.errors[0].title", equalTo(error.getTitle())))
				.andExpect(jsonPath("$.errors[0].text", equalTo(error.getText())))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	@WithMockUser
	public void refresh_token() throws Exception {
		when(this.cityService.persist(city)).thenReturn(city);
		when(this.cityService.findById(ID)).thenReturn(Optional.of(city));

		String token = BEARER_PREFIX + "ABCDE";
		mockMvc.perform(
				MockMvcRequestBuilders.get(BASE_URL).header(TOKEN_HEADER, token).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.token", equalTo(null)));
	}
}
