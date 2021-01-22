package com.compasso.uol.gabriel.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Response<T> {

	@Getter
	@Setter
	private T data;
	private List<Error> errors;

	public Response() {
		if (this.errors == null) {
			this.errors = new ArrayList<Error>();
		}
	}

	public List<Error> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<Error>();
		}

		return errors;
	}

	public void addFieldError(String text) {
		String title = "Erro ao validar o campo";
		errors.add(Error.convert(title, text));
	}

	public void addError(Error responseError) {
		errors.add(responseError);
	}

	public boolean hasErrors() {
		return this.errors.size() > 0;
	}
}
