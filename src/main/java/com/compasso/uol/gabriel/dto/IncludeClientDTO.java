package com.compasso.uol.gabriel.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.enumerator.GenderEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncludeClientDTO implements Serializable {
	private static final long serialVersionUID = 972196048494520957L;

	@Size(min = 1, max = 200, message = "O campo 'Nome' deve conter entre 1 e 200 caracteres.")
	private String name;

	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@NotNull(message = "O campo 'Data de Nascimento' é obrigatório.")
	private Date birth;

	@NotNull(message = "O dados da 'Cidade' são obrigatórios.")
	private City city;

	@Override
	public String toString() {
		return "IncludeClientDTO [name=" + name + ", gender=" + gender + ", birth=" + birth + ", city=" + city + "]";
	}
}
