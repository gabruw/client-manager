package com.compasso.uol.gabriel.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorTest {

	private static final String TEXT = "Teste Text";
	private static final String TITLE = "Teste Title";

	@Test
	public void build_with_constructor() {
		Error error = new Error(TEXT, TITLE);

		Assertions.assertTrue(error.getTitle().equals(TITLE) && error.getText().equals(TEXT));
	}

	@Test
	public void build_with_setters() {
		Error error = new Error();
		error.setText(TEXT);
		error.setTitle(TITLE);

		Assertions.assertTrue(error.getTitle().equals(TITLE) && error.getText().equals(TEXT));
	}
}
