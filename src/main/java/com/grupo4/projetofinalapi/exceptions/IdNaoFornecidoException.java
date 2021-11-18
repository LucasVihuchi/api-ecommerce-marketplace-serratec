package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que o id para acessar um recurso no banco de dados não foi fornecido
 */
public class IdNaoFornecidoException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public IdNaoFornecidoException(String message) {
		super(message);
	}
}
