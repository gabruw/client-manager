package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditAuthenticationDTO extends IncludeAuthenticationDTO implements Serializable {
	private static final long serialVersionUID = 3259716520308178951L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;

	@Override
	public String toString() {
		return "EditAuthenticationDTO [id= " + id + ", email=" + getEmail() + ", password=" + getPassword() + ", role="
				+ getRole() + "]";
	}
}
