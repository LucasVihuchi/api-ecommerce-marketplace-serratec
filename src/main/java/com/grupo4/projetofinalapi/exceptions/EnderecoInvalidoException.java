package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um endereço é inválido
 */
public class EnderecoInvalidoException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public EnderecoInvalidoException (String message) {
		super(message);
	}
}
