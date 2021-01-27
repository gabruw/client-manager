package com.compasso.uol.gabriel.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseTest {

	private static final String ERROR_TEXT = "Teste Text";
	private static final String ERROR_TITLE = "Teste Title";
	private static final String FIELD_ERROR = "Field error";

	@Test
	public void addError() {
		Error error = new Error(ERROR_TEXT, ERROR_TITLE);

		Response<Long> response = new Response<Long>();
		response.addError(error);

		Assertions.assertTrue(response.hasErrors());
	}

	@Test
	public void addFieldError() {
		Response<Long> response = new Response<Long>();
		response.addFieldError(FIELD_ERROR);

		Assertions.assertTrue(response.hasErrors());
	}

	@Test
	public void getErrors() {
		Response<Long> response = new Response<Long>();
		response.addFieldError(FIELD_ERROR);

		Assertions.assertTrue(response.getErrors().size() == 1);
	}
}
