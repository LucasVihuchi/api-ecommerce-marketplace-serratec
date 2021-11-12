package com.grupo4.projetofinalapi.exceptions;

public class ProdutoInexistenteException extends RuntimeException{

	public ProdutoInexistenteException(String message) {
		super(message);
	}
}
