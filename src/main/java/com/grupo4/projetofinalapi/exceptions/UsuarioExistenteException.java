package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um usuário já existe no banco de dados
 */
public class UsuarioExistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public UsuarioExistenteException(String message) {
		super(message);
	}
}
