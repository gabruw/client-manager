package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

import com.compasso.uol.gabriel.enumerator.RoleEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncludeAuthenticationDTO implements Serializable {
	private static final long serialVersionUID = 3259716520308178951L;

	@Size(min = 6, max = 80, message = "O campo 'Email' deve conter entre 6 e 80 caracteres.")
	private String email;

	@Size(min = 6, message = "O campo 'Senha' deve conter no mínimo 6 caracteres.")
	private String password;

	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	@Override
	public String toString() {
		return "IncludeAuthenticationDTO [email=" + email + ", password=" + password + ", role=" + role + "]";
	}
}
