package com.compasso.uol.gabriel.enumerator.message;

public enum ClientMessage {
	NONEXISTENT("nonexistent"), ALREADYEXISTSNAME("alreadyexists.name");

	private final String text;

	ClientMessage(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
