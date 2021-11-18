package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que há uma inconsistência no produto
 */
public class ProdutoInconsistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public ProdutoInconsistenteException(String message) {
		super(message);
	}
}
