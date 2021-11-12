package com.grupo4.projetofinalapi.exceptions;

public class UsuarioInexistenteException extends RuntimeException{
	
	public UsuarioInexistenteException (String message) {
		super(message);
	}
}
