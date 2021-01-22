package com.compasso.uol.gabriel.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {
	private String text;
	private String title;

	public static Error convert(final String title, final String text) {
		return new Error(title, text);
	}
}
