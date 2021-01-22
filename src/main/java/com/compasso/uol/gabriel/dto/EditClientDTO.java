package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditClientDTO extends IncludeClientDTO implements Serializable {
	private static final long serialVersionUID = 972196048494520957L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", name=" + getName() + ", gender=" + getGender() + ", birth=" + getBirth()
				+ ", city=" + getCity() + "]";
	}
}
