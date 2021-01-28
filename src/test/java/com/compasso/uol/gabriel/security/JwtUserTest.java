package com.compasso.uol.gabriel.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.enumerator.RoleEnum;

public class JwtUserTest {

	@Mock
	private JwtUser user;

	private static final Long ID = 1L;
	private static final String PASSWORD = "teste@123";
	private static final RoleEnum ROLE = RoleEnum.USER;
	private static final String EMAIL = "genisvaldo@test.com";

	@BeforeEach
	public void setup() {
		user = new JwtUser();
		user.setId(ID);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
	}

	@Test
	public void authentication_to_jwtUser() {
		Authentication auth = new Authentication();
		auth.setId(ID);
		auth.setRole(ROLE);
		auth.setEmail(EMAIL);
		auth.setPassword(PASSWORD);

		JwtUser tUser = JwtUser.authenticationTojwtUser(auth);
		Assertions.assertTrue(tUser.getId() == auth.getId() && tUser.getUsername().equals(auth.getEmail())
				&& tUser.getPassword().equals(auth.getPassword())
				&& tUser.getAuthorities().stream().findFirst().get().getAuthority().equals(ROLE.toString()));
	}

	@Test
	public void is_enabled() {
		Assertions.assertTrue(user.isEnabled());
	}

	@Test
	public void is_account_non_locked() {
		Assertions.assertTrue(user.isAccountNonLocked());
	}

	@Test
	public void is_account_non_expired() {
		Assertions.assertTrue(user.isAccountNonExpired());
	}

	@Test
	public void is_credentials_non_expired() {
		Assertions.assertTrue(user.isCredentialsNonExpired());
	}
}
