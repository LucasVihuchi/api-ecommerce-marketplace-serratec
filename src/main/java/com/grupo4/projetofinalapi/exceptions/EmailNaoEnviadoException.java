package com.grupo4.projetofinalapi.exceptions;

public class EmailNaoEnviadoException extends RuntimeException{

	public EmailNaoEnviadoException(String message) {
		super(message);
	}
}
