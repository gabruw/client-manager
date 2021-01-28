package com.compasso.uol.gabriel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.compasso.uol.gabriel.enumerator.message.AuthenticationMessage;
import com.compasso.uol.gabriel.enumerator.message.CityMessage;
import com.compasso.uol.gabriel.enumerator.message.ClientMessage;
import com.compasso.uol.gabriel.response.Error;

public class MessagesTest {

	@Test
	public void get_non_existent_city() {
		Error error = Messages.getCity(CityMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void get_non_existent_client() {
		Error error = Messages.getClient(ClientMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void get_non_existent_authentication() {
		Error error = Messages.getAuthentication(AuthenticationMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}
}
