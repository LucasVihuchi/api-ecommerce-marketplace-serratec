package com.grupo4.projetofinalapi.exceptions;

public class ProdutoExistenteException extends RuntimeException{

	public ProdutoExistenteException(String message) {
		super(message);
	}
}
