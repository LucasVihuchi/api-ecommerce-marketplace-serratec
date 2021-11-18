package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um usuário não existe no banco de dados
 */
public class UsuarioInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public UsuarioInexistenteException (String message) {
		super(message);
	}
}
