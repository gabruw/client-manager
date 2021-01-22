package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterClientDTO implements Serializable {
	private static final long serialVersionUID = 973336048411120957L;

	private EditCityDTO city;
	private IncludeClientDTO client;
	private IncludeAuthenticationDTO authentication;

	@Override
	public String toString() {
		return "RegisterClientDTO [city=" + city.toString() + ", client=" + client.toString() + ", authentication="
				+ authentication.toString() + "]";
	}
}
