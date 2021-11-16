package com.grupo4.projetofinalapi.exceptions;

public class UsuarioExistenteException extends RuntimeException{

	public UsuarioExistenteException(String message) {
		super(message);
	}
}
