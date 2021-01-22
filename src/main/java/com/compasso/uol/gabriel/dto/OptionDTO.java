package com.compasso.uol.gabriel.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO<T> implements Serializable {
	private static final long serialVersionUID = 4375746005702849636L;

	private T value;
	private String text;
	
	@Override
	public String toString() {
		return "Option [text=" + text + ", value=" + value + "]";
	}
}
