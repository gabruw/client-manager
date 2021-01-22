package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO implements Serializable {
	private static final long serialVersionUID = -861105215106807581L;

	@Email(message = "O campo 'Email' é inválido.")
	@Size(min = 6, max = 80, message = "O campo 'Email' deve conter entre 6 e 80 caracteres.")
	private String email;

	@Size(min = 6, message = "O campo 'Senha' deve conter no mínimo 6 caracteres.")
	private String password;

	@Override
	public String toString() {
		return "Login [email=" + email + ", password=" + password + "]";
	}
}
