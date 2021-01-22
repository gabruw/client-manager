package com.compasso.uol.gabriel.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.repository.AuthenticationRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {

	@MockBean
	private AuthenticationRepository authenticationRepository;

	@Autowired
	private AuthenticationService authenticationService;

	private static final Long ID = 1L;
	private static final String EMAIL = "genisvaldo@test.com";

	@BeforeEach
	public void init() throws Exception {
		when(this.authenticationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Authentication()));
		when(this.authenticationRepository.findByEmail(Mockito.anyString()))
				.thenReturn(Optional.of(new Authentication()));

		when(this.authenticationRepository.save(Mockito.any(Authentication.class))).thenReturn(new Authentication());
	}

	@Test
	public void findCityById() {
		Optional<Authentication> auth = this.authenticationService.findById(ID);
		assertTrue(auth.isPresent());
	}

	@Test
	public void findCityByEmail() {
		Optional<Authentication> auth = this.authenticationService.findByEmail(EMAIL);
		assertTrue(auth.isPresent());
	}
}
