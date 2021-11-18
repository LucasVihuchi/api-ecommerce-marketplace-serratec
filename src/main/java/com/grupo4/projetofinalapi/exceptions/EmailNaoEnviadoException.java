package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um email não foi enviado
 */
public class EmailNaoEnviadoException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public EmailNaoEnviadoException(String message) {
		super(message);
	}
}
