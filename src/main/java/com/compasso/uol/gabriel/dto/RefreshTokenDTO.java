package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenDTO implements Serializable {
	private static final long serialVersionUID = -4787988584324791683L;

	@NotNull(message = "O campo 'Token' não pode ser vazio.")
	private String token;

	@Override
	public String toString() {
		return "RefreshTokenDTO [token=" + token + "]";
	}
}
