package com.compasso.uol.gabriel.enumerator;

public enum RoleEnum {
	ADMIN("administrador"), USER("usuario");

	private final String text;

	RoleEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
