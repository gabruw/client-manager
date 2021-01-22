package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditCityDTO extends IncludeCityDTO implements Serializable {
	private static final long serialVersionUID = 972196049084511157L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;

	@Override
	public String toString() {
		return "EditCityDTO [id=" + id + ", name=" + getName() + ", state=" + getState() + "]";
	}
}
