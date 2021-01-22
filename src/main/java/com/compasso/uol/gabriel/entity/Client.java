package com.compasso.uol.gabriel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.compasso.uol.gabriel.enumerator.GenderEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "client")
@Entity(name = "client")
public class Client implements Serializable {
	private static final long serialVersionUID = 3259874520308178951L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	@Size(min = 1, max = 200, message = "O campo 'Nome' deve conter entre 1 e 200 caracteres.")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private GenderEnum gender;

	@Column(name = "birth", nullable = false)
	@NotNull(message = "O campo 'Data de Nascimento' é obrigatório.")
	private Date birth;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message = "O dados da 'Cidade' são obrigatórios.")
	private City city;
	
	@OneToOne(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Authentication authentication;

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", gender=" + gender + ", birth=" + birth + ", city=" + city
				+ "]";
	}
}
