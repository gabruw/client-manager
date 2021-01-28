package com.compasso.uol.gabriel.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.enumerator.RoleEnum;
import com.compasso.uol.gabriel.repository.AuthenticationRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {

	@MockBean
	private AuthenticationRepository authenticationRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@Mock
	private Authentication auth;

	private static final Long ID = 1L;
	private static final RoleEnum ROLE = RoleEnum.USER;
	private static final String PASSWORD = "teste@123";
	private static final String EMAIL = "genisvaldo@test.com";

	@BeforeEach
	public void setup() {
		auth = new Authentication();
		auth.setRole(ROLE);
		auth.setEmail(EMAIL);
		auth.setPassword(PASSWORD);
	}

	@Test
	public void find_authentication_by_id() {
		when(this.authenticationRepository.findById(ID)).thenReturn(Optional.of(auth));

		Optional<Authentication> tAuth = this.authenticationService.findById(ID);
		Assertions.assertTrue(tAuth.isPresent());
	}

	@Test
	public void find_authentication_by_email() {
		when(this.authenticationRepository.findByEmail(EMAIL)).thenReturn(Optional.of(auth));

		Optional<Authentication> tAuth = this.authenticationService.findByEmail(EMAIL);
		Assertions.assertTrue(tAuth.isPresent());
	}

	@Test
	public void persist_authentication() {
		when(this.authenticationRepository.save(auth)).thenReturn(auth);

		Authentication tAuth = this.authenticationService.persist(auth);
		Assertions.assertEquals(tAuth, auth);
	}

	@Test
	public void delete_authentication() {
		when(this.authenticationRepository.findById(ID)).thenReturn(Optional.of(auth));

		this.authenticationService.deleteById(ID);
		verify(this.authenticationRepository, times(1)).deleteById(ID);
	}
}
