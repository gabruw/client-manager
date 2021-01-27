package com.compasso.uol.gabriel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.compasso.uol.gabriel.enumerator.message.AuthenticationMessage;
import com.compasso.uol.gabriel.enumerator.message.CityMessage;
import com.compasso.uol.gabriel.enumerator.message.ClientMessage;
import com.compasso.uol.gabriel.response.Error;

public class MessagesTest {

	@Test
	public void getCity_nonExistent() {
		Error error = Messages.getCity(CityMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void getClient_nonExistent() {
		Error error = Messages.getClient(ClientMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}

	@Test
	public void getAuthentication_nonExistent() {
		Error error = Messages.getAuthentication(AuthenticationMessage.NONEXISTENT.toString());

		Assertions.assertTrue(error.getTitle() != null && error.getText() != null);
	}
}
