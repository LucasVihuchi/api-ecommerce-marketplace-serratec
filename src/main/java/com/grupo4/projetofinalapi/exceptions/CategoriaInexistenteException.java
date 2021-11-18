package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que a categoria não existe no banco de dados
 */
public class CategoriaInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public CategoriaInexistenteException(String message) {
		super(message);
	}
}
