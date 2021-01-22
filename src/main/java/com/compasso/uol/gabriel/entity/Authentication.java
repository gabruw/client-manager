package com.compasso.uol.gabriel.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.compasso.uol.gabriel.enumerator.RoleEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "authentication")
@Entity(name = "authentication")
public class Authentication implements Serializable {
	private static final long serialVersionUID = 3259716520308178951L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "email", unique = true, nullable = false)
	@Size(min = 6, max = 80, message = "O campo 'Email' deve conter entre 6 e 80 caracteres.")
	private String email;

	@Column(name = "password", nullable = false)
	@Size(min = 6, message = "O campo 'Senha' deve conter no mínimo 6 caracteres.")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private RoleEnum role;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Client client;

	@Override
	public String toString() {
		return "Authentication [id=" + id + ", email=" + email + ", password=" + password + ", client=" + client + "]";
	}
}
