package com.compasso.uol.gabriel.enumerator.message;

public enum CityMessage {
	NONEXISTENT("nonexistent"), ALREADYEXISTSNAME("alreadyexists.name");

	private final String text;

	CityMessage(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
