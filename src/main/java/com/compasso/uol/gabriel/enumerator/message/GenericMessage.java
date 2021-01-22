package com.compasso.uol.gabriel.enumerator.message;

public enum GenericMessage {
	NONEXISTENT("nonexistent"), ALREADYEXISTENT("alreadyexistent");

	private final String text;

	GenericMessage(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
