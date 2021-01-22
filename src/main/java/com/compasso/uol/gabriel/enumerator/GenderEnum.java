package com.compasso.uol.gabriel.enumerator;

public enum GenderEnum {
	MALE("masculino"), FEMALE("feminino");

	private final String text;

	GenderEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
