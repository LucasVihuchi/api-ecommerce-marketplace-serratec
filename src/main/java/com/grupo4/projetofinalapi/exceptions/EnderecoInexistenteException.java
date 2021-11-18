package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um endereço não existe no banco de dados
 */
public class EnderecoInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public EnderecoInexistenteException(String message) {
		super(message);
	}
}
