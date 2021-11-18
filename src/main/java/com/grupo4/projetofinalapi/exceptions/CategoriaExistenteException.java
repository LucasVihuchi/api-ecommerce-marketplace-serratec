package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que uma categoria já consta no banco de dados
 */
public class CategoriaExistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public CategoriaExistenteException (String message) {
		super(message);
	}
}
